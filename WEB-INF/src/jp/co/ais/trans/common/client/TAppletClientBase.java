package jp.co.ais.trans.common.client;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.print.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * Applet画面コントロールのベースクラス.<br>
 * 画面操作の共通処理
 */
public class TAppletClientBase extends TRequestBase {

	/** ダウンロード用ファイルチューザー */
	private static JFileChooser openChooser = new JFileChooser();

	/** JOptionPaneの「了解」ボタンメッセージオプション */
	private static String okOption;

	/** JOptionPaneの「はい」「いいえ」ボタンメッセージオプション */
	private static String[] yesNoOptions;

	/** マルチスレッドブロック用のダミーインスタンス */
	private static String dummy = "";

	static {
		openChooser.setMultiSelectionEnabled(false);
		openChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		openChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		openChooser.setAcceptAllFileFilterUsed(false);

		okOption = ClientConfig.getOkButtonText();
		yesNoOptions = ClientConfig.getYesNoButtonWords();
	}

	/** ファイルアップロード元のPATH */
	private String filePath;

	/**
	 * ログインユーザID取得
	 * 
	 * @return ログインユーザID
	 */
	protected String getLoginUserID() {
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getCode())) {
			return TLoginInfo.getUser().getCode();
		}
		return TClientLoginInfo.getInstance().getUserCode();
	}

	/**
	 * ログインユーザの言語コード
	 * 
	 * @return 言語コード
	 */
	protected String getLoginLanguage() {
		String lang = null;
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getLanguage())) {
			lang = TLoginInfo.getUser().getLanguage();
		} else {
			lang = TClientLoginInfo.getInstance().getUserLanguage();
		}

		if (Util.isNullOrEmpty(lang)) {
			return "ja";
		}

		return lang;
	}

	/**
	 * ログインユーザ会社コード取得
	 * 
	 * @return 会社コード
	 */
	protected String getLoginUserCompanyCode() {
		if (TLoginInfo.getCompany() != null && !Util.isNullOrEmpty(TLoginInfo.getCompany().getCode())) {
			return TLoginInfo.getCompany().getCode();
		}
		return TClientLoginInfo.getInstance().getCompanyCode();
	}

	/**
	 * ログインユーザ名取得
	 * 
	 * @return ユーザ名
	 */
	protected String getLoginUserName() {
		if (TLoginInfo.getUser() != null && !Util.isNullOrEmpty(TLoginInfo.getUser().getName())) {
			return TLoginInfo.getUser().getName();
		}
		return TClientLoginInfo.getInstance().getUserName();
	}

	/**
	 * アップロード元のファイルパスを返す
	 * 
	 * @return ファイルパス
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 画面取得（override用）<br>
	 * コントロールする画面Panel or Dialogを返す.<br>
	 * 
	 * @return 画面
	 */
	public Container getView() {
		return getPanel();
	}

	/**
	 * メインパネルはTPanelCtrlBaseクラスを継承すること<br>
	 * <s>パネル取得（override用）</s>
	 * 
	 * @return パネル
	 * @deprecated メインパネルはTPanelCtrlBaseクラスを継承すること
	 */
	public JPanel getPanel() {
		return null;
	}

	/**
	 * 通信結果のエラー値用エラーハンドリング. <br>
	 * getView()を親コンポーネントとして扱う.
	 */
	public void errorHandler() {
		errorHandler(getView());
	}

	/**
	 * エラーハンドリング<br>
	 * getView()を親コンポーネントとして扱う.
	 * 
	 * @param ex 発生Exception
	 */
	public void errorHandler(Exception ex) {
		errorHandler(getView(), ex);
	}

	/**
	 * エラーハンドリング<br>
	 * getView()を親コンポーネントとして扱う.
	 * 
	 * @param ex 発生Exception
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void errorHandler(Exception ex, String messageID, Object... bindIds) {
		errorHandler(getView(), ex, messageID, bindIds);
	}

	/**
	 * 通信結果のエラー値用エラーハンドリング. <br>
	 * エラー結果は、自動的に取得.
	 * 
	 * @param parent 親コンポーネント
	 */
	public void errorHandler(Component parent) {

		String key = getErrorCode();
		String notice = getErrorMessage();

		if (!Util.isNullOrEmpty(notice)) {
			int category = MessageUtil.getCategory(getLoginLanguage(), key);
			showMessageDialog(parent, notice, category);

		} else if (!Util.isNullOrEmpty(key)) {

			showMessage(parent, key);
		} else {

			showMessage(parent, "E00009");
		}
	}

	/**
	 * エラーハンドリング<br>
	 * TExceptionの場合は、保持しているメッセージを表示.<br>
	 * それ以外は「正常に処理されませんでした」のメッセージを表示する.
	 * 
	 * @param parent 親コンポーネント
	 * @param ex 発生Exception
	 */
	public void errorHandler(Component parent, Throwable ex) {

		if (ex instanceof TRequestException) {
			errorHandler(parent);

		} else if (ex instanceof TException) {
			TException tex = (TException) ex;
			TRuntimeException trex = null;
			ClientLogger.debug(tex);

			while (tex != null) {
				if (tex.getCause() instanceof TException) {
					tex = (TException) tex.getCause();
					continue;
				} else if (tex.getCause() instanceof TRuntimeException) {
					trex = (TRuntimeException) tex.getCause();
					break;
				}
				break;
			}

			if (trex == null) {
				showMessage(parent, tex.getMessageID(), tex.getMessageArgs());
			} else {
				showMessage(parent, trex.getMessageID(), trex.getMessageArgs());
			}

		} else if (ex instanceof TRuntimeException) {
			TRuntimeException trex = (TRuntimeException) ex;
			ClientLogger.debug(trex);

			Throwable twe = trex.getCause();

			while (twe != null) {
				if (!(twe.getCause() instanceof TException || twe.getCause() instanceof TRuntimeException)) {
					break;
				}

				twe = twe.getCause();
			}

			if (twe != null) {
				if (twe instanceof TException) {
					TException tex = (TException) twe;

					showMessage(parent, tex.getMessageID(), tex.getMessageArgs());
					return;

				} else if (twe instanceof TRuntimeException) {
					trex = (TRuntimeException) twe;
				}
			}

			showMessage(parent, trex.getMessageID(), trex.getMessageArgs());

		} else {
			errorHandler(parent, ex, "E00009");
		}
	}

	/**
	 * エラーハンドリング
	 * 
	 * @param parent 親コンポーネント
	 * @param ex 発生Exception
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void errorHandler(Component parent, Throwable ex, String messageID, Object... bindIds) {
		ClientErrorHandler.handledException(ex);
		showMessage(parent, messageID, bindIds);
	}

	/**
	 * エラーハンドリング
	 * 
	 * @param parent 親コンポーネント
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void errorHandler(Component parent, String messageID, Object... bindIds) {
		this.showMessage(parent, messageID, bindIds);
	}

	/**
	 * 指定されたIDに対応するメッセージを表示
	 * 
	 * @param parent 親コンポーネント
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void showMessage(Component parent, String messageID, Object... bindIds) {

		if (Util.isNullOrEmpty(messageID)) {
			messageID = "E00009";
		}

		int category = MessageUtil.getCategory(getLoginLanguage(), messageID);
		String message = getMessage(messageID, bindIds);

		showMessageDialog(parent, message, category);
	}

	/**
	 * 指定されたIDに対応するメッセージを表示.<br>
	 * 親コンポーネントは getView() を利用
	 * 
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 */
	public void showMessage(String messageID, Object... bindIds) {
		showMessage(getView(), messageID, bindIds);
	}

	/**
	 * @param parent
	 * @param message
	 * @param category
	 */
	public void showMessageDialog(Component parent, String message, int category) {
		Component target = getTargetParent(parent);

		if (okOption == null) {
			JOptionPane.showMessageDialog(target, message, "", category);

		} else {
			JOptionPane.showOptionDialog(target, message, "", JOptionPane.DEFAULT_OPTION, category, null,
				new String[] { okOption }, okOption);
		}
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param msgs メッセージ
	 */
	public void showListMessage(Component comp, String... msgs) {
		this.showListMessage(comp, Arrays.asList(msgs));
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param msgList メッセージリスト
	 */
	public void showListMessage(Component comp, List<String> msgList) {

		MessageListDialog dialog;
		if (comp instanceof Frame) {
			dialog = new MessageListDialog((Frame) comp);

		} else if (comp instanceof Dialog) {
			dialog = new MessageListDialog((Dialog) comp);

		} else if (comp instanceof TPanel) {
			dialog = new MessageListDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = new MessageListDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = new MessageListDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		dialog.setMessageList(msgList);

		// メッセージ一覧表示ダイアログを表示する
		dialog.setVisible(true);
	}

	/**
	 * 指定されたIDに対応する確認メッセージを表示.<br>
	 * 親コンポーネントは getView() を利用
	 * 
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 * @return Yesが押下された場合trueが返る
	 */
	public boolean showConfirmMessage(String messageID, Object... bindIds) {
		return showConfirmMessage(getView(), messageID, bindIds);
	}

	/**
	 * 指定されたIDに対応する確認メッセージを表示
	 * 
	 * @param parent 親コンポーネント
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 * @return Yesが押下された場合trueが返る
	 */
	public boolean showConfirmMessage(Component parent, String messageID, Object... bindIds) {
		return showConfirmMessage(parent, FocusButton.YES, messageID, bindIds);
	}

	/**
	 * 指定されたIDに対応する確認メッセージを表示.<br>
	 * 親コンポーネントは getView()「getPanel()」 を利用
	 * 
	 * @param btn 初期フォーカス 0:はい 1:いいえ
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 * @return Yesが押下された場合trueが返る
	 */
	protected boolean showConfirmMessage(FocusButton btn, String messageID, Object... bindIds) {
		return showConfirmMessage(getView(), btn, messageID, bindIds);
	}

	/**
	 * 指定されたIDに対応する確認メッセージを表示
	 * 
	 * @param parent 親コンポーネント
	 * @param btn 初期フォーカス 0:はい 1:いいえ
	 * @param messageID メッセージID
	 * @param bindIds メッセージバインド文字の単語ID
	 * @return Yesが押下された場合trueが返る
	 */
	protected boolean showConfirmMessage(Component parent, FocusButton btn, String messageID, Object... bindIds) {

		Component target = getTargetParent(parent);

		String message = getMessage(messageID, bindIds);

		FocusButton def = btn;

		if (!FocusButton.YES.equals(btn) && !FocusButton.NO.equals(btn)) {

			def = FocusButton.YES;
		}

		int res = TOptionPane.showOptionDialog(target, message, "", JOptionPane.YES_NO_OPTION,
			JOptionPane.QUESTION_MESSAGE, null, yesNoOptions, def.value);

		return res == JOptionPane.YES_OPTION;
	}

	/**
	 * モーダル制御対象となる(上位)コンポーネントを取得
	 * 
	 * @param comp 対象コンポーネント
	 * @return 対象コンポーネント
	 */
	protected Component getTargetParent(Component comp) {

		if (comp == null) {
			return TAppletMain.instance;
		}
		return TGuiUtil.getParentFrameOrDialog2(comp);
	}

	/**
	 * ログインユーザの言語コードに対する、メッセージを返す. 指定単語ID、または単語をバインド.
	 * 
	 * @param messageID メッセージID
	 * @param bindIds 単語ID、または、単語のリスト
	 * @return 変換されたメッセージ
	 */
	public String getMessage(String messageID, Object... bindIds) {

		return MessageUtil.convertMessage(getLoginLanguage(), messageID, bindIds);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字
	 */
	public String getWord(String wordID) {

		return MessageUtil.getWord(getLoginLanguage(), wordID);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字(略称)を返す.
	 * 
	 * @param wordID 単語ID
	 * @return 単語文字(略称)
	 */
	public String getShortWord(String wordID) {

		return MessageUtil.getShortWord(getLoginLanguage(), wordID);
	}

	/**
	 * ログインユーザの言語コードに対する、単語文字リストを返す.
	 * 
	 * @param wordIDs 単語IDリスト
	 * @return 単語文字リスト
	 */
	public String[] getWordList(String[] wordIDs) {

		return MessageUtil.getWordList(getLoginLanguage(), wordIDs);
	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @return true:成功
	 */
	protected boolean download(Container cont, String servletName) {
		return download(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード＆表示
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @return true:成功
	 */
	protected boolean download(Container cont, String servletName, Map funcArgs) {
		try {

			downloadNative(servletName, funcArgs);

			// 新処理方法でファイル出力
			preview();

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * ファイルダウンロード.(作成のみ)
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @param dirPath ファイル保存ディレクトリパス
	 * @return true:成功
	 */
	protected boolean downloadFileCreateOnly(Container cont, String servletName, Map funcArgs, String dirPath) {

		try {
			// サーバ処理
			downloadNative(servletName, funcArgs);

			// ファイル展開.
			createResultFile(dirPath);

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * ファイルダウンロード＆印刷.<br>
	 * ユーザのデフォルトプリンタを仕様する.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @return true:成功
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName) {

		return this.downloadPDFAndPrint(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード＆印刷.<br>
	 * ユーザのデフォルトプリンタを仕様する.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @return true:成功
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName, Map funcArgs) {

		return this.downloadPDFAndPrint(cont, servletName, funcArgs, null);
	}

	/**
	 * ファイルダウンロード＆印刷.<br>
	 * クライアント情報のプリンタ設定からプリンタを識別する.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @return true:成功
	 */
	protected boolean downloadPDFAndPrintByPrinterSet(Container cont, String servletName) {

		return this.downloadPDFAndPrintByPrinterSet(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード＆印刷.<br>
	 * クライアント情報のプリンタ設定からプリンタを識別する.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @return true:成功
	 */
	protected boolean downloadPDFAndPrintByPrinterSet(Container cont, String servletName, Map funcArgs) {
		PrintService service = TClientLoginInfo.getPrintService();

		return this.downloadPDFAndPrint(cont, servletName, funcArgs, service == null ? null : service.getName());
	}

	/**
	 * ファイルダウンロード＆印刷
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @param printerName プリンタ名
	 * @return true:成功
	 */
	protected boolean downloadPDFAndPrint(Container cont, String servletName, Map funcArgs, String printerName) {
		try {
			downloadNative(servletName, funcArgs);

			// ファイル展開
			String fileName = createResultFile();

			if (!Util.isNullOrEmpty(printerName)) {
				SystemUtil.printPDF(fileName, printerName);
			} else {
				SystemUtil.printPDF(fileName);
			}

			return true;

		} catch (TRequestException e) {
			errorHandler(cont);
			return false;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());
			return false;

		} catch (TException ex) {
			errorHandler(cont, ex);
			return false;
		}
	}

	/**
	 * ファイルダウンロード処理.<br>
	 * 共通処理
	 * 
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @throws TException
	 */
	protected void downloadNative(String servletName, Map funcArgs) throws TException {

		try {

			ClientLogger.memory("download start");

			// サーバ処理
			this.addSendValues(funcArgs);

			if (!request(servletName)) {
				throw new TRequestException();
			}

			ClientLogger.memory("download end");

		} catch (IOException ex) {
			throw new TException(ex, "E00009");
		}
	}

	/**
	 * ファイルダウンロード結果の出力
	 * 
	 * @param cont 元コンポーネント
	 * @return true:成功
	 */
	protected boolean executeResultFile(Container cont) {

		try {
			String dir = createResultFile();

			// 直接印刷
			if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT) {

				// PDFプリント
				if ("pdf".equals(ResourceUtil.getExtension(new File(dir)))) {
					SystemUtil.printPDF(dir);
					return true;
				}
			}
			// ファイル起動
			SystemUtil.executeFile(dir);

			return true;

		} catch (IOException ex) {
			errorHandler(cont, ex, "W01079", ex.getMessage());

			return false;
		} catch (TException ex) {
			errorHandler(cont, ex);

			return false;
		}
	}

	/**
	 * ファイルダウンロード結果の出力(TEMPフォルダ保存)
	 * 
	 * @return 出力ファイル名
	 * @throws IOException
	 */
	protected String createResultFile() throws IOException {

		// 一時Tempファイル場所
		String dirPath = SystemUtil.getTemporaryDir();

		// ファイル展開.展開されたファイル名が戻る
		return createResultFile(dirPath);
	}

	/**
	 * ファイルダウンロード結果の出力(指定フォルダ保存)
	 * 
	 * @param dirPath ファイル出力場所
	 * @return 出力ファイル名
	 * @throws IOException
	 */
	protected String createResultFile(String dirPath) throws IOException {

		// バイナリ結果取得
		Map<String, Object> dataSet = super.getResultBinaryDataSet();

		String name = (String) dataSet.get(TRequestBase.RESULT_BYTE_FILENAME); // ファイル名
		String extension = (String) dataSet.get(TRequestBase.RESULT_BYTE_EXTENSION); // 拡張子

		String fileName = name + "." + extension;

		// ファイルが開かれている場合、別名を付けて保存する
		for (int i = 1; !ResourceUtil.canWrite(dirPath + fileName); i++) {
			fileName = name + "[" + i + "]." + extension;

			if (i == 100) {
				// パス指定ミス時の無限ループ回避
				throw new TRuntimeException("File count over100." + fileName);
			}
		}

		return createResultFileNative(dirPath, fileName);
	}

	/**
	 * ファイルダウンロード結果の出力(指定フォルダ、ファイル名保存).<br>
	 * 共通処理
	 * 
	 * @param dirPath ファイル出力場所
	 * @param fileName ファイル名(拡張子付き)
	 * @return 出力ファイル名
	 * @throws IOException
	 */
	protected String createResultFileNative(String dirPath, String fileName) throws IOException {

		// バイナリ結果取得
		Map<String, Object> dataSet = super.getResultBinaryDataSet();

		byte[] binary = (byte[]) dataSet.get(TRequestBase.RESULT_BYTE_DATA); // データ

		String fullPath = dirPath + fileName;

		ClientLogger.info("zip file binary=" + binary.length);
		ClientLogger.memory("write binay start");

		ResourceUtil.writeBinaryInZip(fullPath, binary);

		ClientLogger.memory("write binary end");
		ClientLogger.debug("create fileName=" + fullPath);

		return fullPath;
	}

	/**
	 * 入金データファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param para パラメータ
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadDAT(Container cont, String servletName, Map<String, Object> para) {

		// 入金ファイルフィルター
		TFileFilter datFilter = new TFileFilter(new String[] { "txt", "dat" }, "C10103");

		return executeUpload(cont, servletName, para, datFilter);
	}

	/**
	 * CSVファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param para パラメータ
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadCSV(Container cont, String servletName, Map<String, Object> para) {

		// CSVファイルフィルター
		TFileFilter csvFilter = new TFileFilter("csv", "C02770");

		return executeUpload(cont, servletName, para, csvFilter);
	}

	/**
	 * エクセルファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param para パラメータ
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadXLS(Container cont, String servletName, Map<String, Object> para) {

		// Excelファイルフィルター
		TFileFilter xlsFilter = new TFileFilter("xls", "C02315");

		return executeUpload(cont, servletName, para, xlsFilter);
	}

	/**
	 * テキストファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param para パラメータ
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadTXT(Container cont, String servletName, Map<String, Object> para) {

		// テキストファイル(CSV形式)フィルター
		TFileFilter txtFilter = new TFileFilter(new String[] { "csv", "txt" }, "C02771");

		return executeUpload(cont, servletName, para, txtFilter);
	}

	/**
	 * ファイルアップロード(パネル用)
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param para パラメータ
	 * @param filter ファイルフィルター
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	private boolean executeUpload(Container cont, String servletName, Map<String, Object> para, FileFilter filter) {
		try {

			// フィルターセット
			openChooser.resetChoosableFileFilters();
			openChooser.setFileFilter(filter);
			openChooser.updateUI();

			if (JFileChooser.APPROVE_OPTION != openChooser.showOpenDialog(cont)) {
				return false;
			}

			File file = openChooser.getSelectedFile();

			// 「*.*」と入力すると他のファイルも選択可能な為、再度チェック
			if (!filter.accept(file)) {
				// 正しいファイルを選択してください
				showMessage(cont, "W00278", "C01988");
				return false;
			}

			ClientLogger.debug(file.getPath());

			// フルパス名
			filePath = file.getPath();

			// 通信
			addSendValues("flag", "upload");
			addSendValues(para);

			if (!request(servletName, file)) {
				errorHandler(cont);
				return false;
			}

		} catch (IOException ex) {
			ClientLogger.error(getMessage("E00009", ex.getMessage()), ex);
			errorHandler(cont, "E00009", ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * TEXTファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param listener
	 * @deprecated TAsyncRequestを利用すること
	 */
	protected void uploadTXTAsync(Container cont, String servletName, TAsyncListener listener) {

		// テキストファイル(CSV形式)フィルター
		TFileFilter filter = new TFileFilter(new String[] { "csv", "txt" }, "C02771");

		// フィルターセット
		openChooser.resetChoosableFileFilters();
		openChooser.setFileFilter(filter);
		openChooser.updateUI();

		if (JFileChooser.APPROVE_OPTION != openChooser.showOpenDialog(cont)) {
			return;
		}

		File file = openChooser.getSelectedFile();

		// 「*.*」と入力すると他のファイルも選択可能な為、再度チェック
		if (!filter.accept(file)) {
			// 正しいファイルを選択してください
			showMessage(cont, "W00278", "C01988");
			return;
		}

		ClientLogger.debug(file.getPath());

		// フルパス名
		filePath = file.getPath();

		// 通信
		addSendValues("flag", "upload");

		List<File> list = new ArrayList<File>(1);
		list.add(file);

		requestAsync(servletName, list, listener);
	}

	/**
	 * ファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param files 送信ファイルリスト
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadFiles(Container cont, String servletName, List<File> files) {
		return uploadFiles(cont, servletName, files, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルアップロード
	 * 
	 * @param cont パネル(Panelコンポーネント)
	 * @param servletName 処理Servlet名
	 * @param files 送信ファイルリスト
	 * @param para パラメータ
	 * @return true:成功、false:失敗、または、キャンセル
	 */
	protected boolean uploadFiles(Container cont, String servletName, List<File> files, Map<String, Object> para) {
		try {

			// 通信
			addSendValues("flag", "upload");
			addSendValues(para);

			if (!request(servletName, files)) {
				errorHandler(cont);
				return false;
			}

		} catch (IOException ex) {
			ClientLogger.error(getMessage("E00009", ex.getMessage()), ex);
			errorHandler(cont, "E00009", ex.getMessage());

			return false;
		}

		return true;
	}

	/**
	 * 送信(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param path 実行パス
	 * @param listener リクエスト後の処理リスナー
	 * @deprecated TAsyncRequestを利用すること
	 */
	protected void requestAsync(final String path, final TAsyncListener listener) {
		requestAsync(path, Collections.EMPTY_LIST, listener);
	}

	/**
	 * 送信(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param path 実行パス
	 * @param files ファイルリスト
	 * @param listener リクエスト後の処理リスナー
	 * @deprecated TAsyncRequestを利用すること
	 */
	protected void requestAsync(final String path, final List<File> files, final TAsyncListener listener) {

		try {
			synchronized (dummy) {

				locked(true);

				new Thread() {

					@Override
					public void run() {
						try {
							final boolean result = request(path, files);

							locked(false);

							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									if (listener != null) {
										listener.after(result);
									}

								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (Exception e) {
							locked(false);
							errorHandler(e);

						} finally {
							locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			locked(false);

			throw ex;
		}
	}

	/**
	 * ファイルダウンロード＆表示(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @deprecated TAsyncRequestを利用すること
	 */
	protected void downloadAsync(final Container cont, final String servletName) {
		this.downloadAsync(cont, servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード＆表示(非同期処理).<br>
	 * getView()を実装していることが前提.
	 * 
	 * @param cont 元コンポーネント
	 * @param servletName 対象Servlet名
	 * @param funcArgs 引渡しパラメータ
	 * @deprecated TAsyncRequestを利用すること
	 */
	protected void downloadAsync(final Container cont, final String servletName, final Map funcArgs) {

		try {
			synchronized (dummy) {

				locked(true);

				new Thread() {

					@Override
					public void run() {
						try {

							downloadNative(servletName, funcArgs);

							// // イベントディスパッチスレッドで
							SwingUtilities.invokeLater(new Runnable() {

								public void run() {

									locked(false);

									// ファイル展開&実行
									executeResultFile(cont);
								}
							});

						} catch (NullPointerException e) {
							ClientLogger.error("null error.", e);

						} catch (TRequestException e) {
							locked(false);
							errorHandler(cont);

						} catch (TException ex) {
							locked(false);
							errorHandler(cont, ex);

						} finally {
							locked(false);
						}
					}
				}.start();
			}

		} catch (TRuntimeException ex) {
			locked(false);

			throw ex;
		}
	}

	/**
	 * 管理している画面を操作制御する
	 * 
	 * @param isLocked true:操作させない
	 */
	protected void locked(boolean isLocked) {
		Container cont = getView();

		if (cont == null) {
			ClientLogger.error("getView() doesn't suffice. " + this.getClass().getName());
			return;
		}

		Container root = TGuiUtil.getParentFrameOrDialog(cont);

		if (root != null) {
			root.setEnabled(!isLocked);

		} else {
			cont.setEnabled(!isLocked);
		}
	}

	/**
	 * デフォルトカーソルを設定
	 * 
	 * @param comp 対象コンテナ
	 */
	protected void setDefaultCursor(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	/**
	 * WAITカーソルを設定
	 * 
	 * @param comp 対象コンテナ
	 */
	protected void setWaitCursor(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		parent.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
	}

	/**
	 * 現在のカーソル状態がWAITかどうかを判定
	 * 
	 * @param comp 対象コンテナ
	 * @return WAIT状態である場合true
	 */
	protected boolean isWaitCursorNow(Container comp) {
		Component parent = TGuiUtil.getPanelBusiness(comp);

		if (parent == null) {
			parent = comp;
		}

		return Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR).equals(parent.getCursor());
	}

	/**
	 * FileChooser用フィルター
	 */
	private class TFileFilter extends FileFilter {

		/** 表示拡張子リスト */
		private String[] extentions;

		/** 説明 */
		private String description;

		/**
		 * コンストラクタ
		 * 
		 * @param extention 表示拡張子
		 * @param descriptionID 説明の単語ID
		 */
		public TFileFilter(String extention, String descriptionID) {
			this(new String[] { extention }, descriptionID);
		}

		/**
		 * コンストラクタ
		 * 
		 * @param extentions 表示拡張子リスト
		 * @param descriptionID 説明の単語ID
		 */
		public TFileFilter(String[] extentions, String descriptionID) {
			this.extentions = extentions;

			StringBuilder asterExt = new StringBuilder();
			for (String extention : extentions) {
				if (asterExt.length() != 0) {
					asterExt.append(", ");
				}
				asterExt.append("*.").append(extention);
			}

			this.description = getWord(descriptionID) + " (" + asterExt.toString() + ")";
		}

		/**
		 * 拡張子判定
		 */
		@Override
		public boolean accept(File f) {
			if (f.isDirectory()) {
				return true;
			}

			String ext = ResourceUtil.getExtension(f);

			boolean result = false;
			for (String extention : extentions) {
				result |= ext.equals(extention);
			}

			return result;
		}

		/**
		 * 説明文
		 */
		@Override
		public String getDescription() {
			return description;
		}

	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param servletName サーブレット名
	 * @throws TException
	 */
	protected void download(String servletName) throws TException {
		download(servletName, Collections.EMPTY_MAP);
	}

	/**
	 * ファイルダウンロード
	 * 
	 * @param servletName
	 * @param funcArgs
	 * @throws TException
	 */
	protected void download(String servletName, Map funcArgs) throws TException {

		downloadNative(servletName, funcArgs);

		// 新処理方法でファイル出力
		preview();

	}

	/**
	 * 新処理方法でファイル出力
	 * 
	 * @throws TException
	 */
	protected void preview() throws TException {
		// 新処理方法
		// バイナリ結果取得
		Map<String, Object> dataSet = super.getResultBinaryDataSet();
		byte[] data = (byte[]) dataSet.get(TRequestBase.RESULT_BYTE_DATA); // データ

		String name = (String) dataSet.get(TRequestBase.RESULT_BYTE_FILENAME); // ファイル名
		String extension = (String) dataSet.get(TRequestBase.RESULT_BYTE_EXTENSION); // 拡張子

		String fileName = name + "." + extension;

		TPrinter printer = new TPrinter(true);

		// 直接印刷
		if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT
			&& "pdf".equals(Util.avoidNull(extension).toLowerCase())) {
			printer.print(data, fileName);
		} else {
			printer.preview(data, fileName);
		}
	}

	/**
	 * ファイル展開&実行
	 * 
	 * @throws TException
	 */
	protected void executeResultFile() throws TException {

		try {

			String dir = createResultFile();

			// 直接印刷
			if (TClientLoginInfo.getCompanyInfo().getDirectPrintKbn() == CMP_MST.DIRECT_PRINT) {

				// PDFプリント
				if ("pdf".equals(ResourceUtil.getExtension(new File(dir)))) {
					SystemUtil.printPDF(dir);
					return;
				}
			}

			// ファイル起動
			SystemUtil.executeFile(dir);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

}

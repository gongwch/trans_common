package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 添付ボタンのコントローラ
 */
public class TAttachButtonCtrl extends TController {

	/** 添付ボタン */
	protected TAttachButton btn = null;

	/** 最大ファイルサイズ(MB) */
	public static int maxFileSize = 4;

	/** 拡張子 */
	public static String[] supportFileExts = null;

	/** 添付ファイル */
	protected List<SWK_ATTACH> attachments = null;

	static {
		try {
			maxFileSize = Util.avoidNullAsInt(ClientConfig.getProperty("trans.slip.use.attachment.max.size"));
		} catch (Throwable e) {
			// 処理なし
		}

		try {
			supportFileExts = ClientConfig.getProperties("trans.slip.attachment.support.file.exts");
		} catch (Throwable e) {
			supportFileExts = new String[] { ExtensionType.PDF.value, ExtensionType.XLS.value, ExtensionType.XLSX.value };
		}

	}

	/**
	 * コンストラクター
	 * 
	 * @param btn
	 */
	public TAttachButtonCtrl(TAttachButton btn) {

		this.btn = btn;

		// イベント初期化
		addEvents();

		// 初期化
		init();

		// ツールチップの表示遅れ0.1秒
		ToolTipManager.sharedInstance().setInitialDelay(100);

	}

	/**
	 * イベント初期化
	 */
	protected void addEvents() {

		btn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// 添付追加
				attachFile();
			}
		});
	}

	/**
	 * 初期化
	 */
	public void init() {
		// ファイルマップの初期化
		attachments = new ArrayList<SWK_ATTACH>();

		// 最新反映
		refresh();
	}

	/**
	 * 添付追加
	 */
	protected void attachFile() {
		try {
			TAttachListDialogCtrl ctrl = createAttachListDialogCtrl();
			ctrl.setData(getAttachments());
			ctrl.show();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return 添付一覧ダイアログCtrl
	 */
	protected TAttachListDialogCtrl createAttachListDialogCtrl() {
		if (btn.parent != null) {
			return new TAttachListDialogCtrl(btn.parent, getProgramInfo(), btn);
		} else {
			return new TAttachListDialogCtrl(btn.dialog, getProgramInfo(), btn);
		}
	}

	/**
	 * 添付ファイルの取得
	 * 
	 * @return files 添付ファイル
	 */
	public List<SWK_ATTACH> getAttachments() {
		return attachments;
	}

	/**
	 * 添付ファイルの設定
	 * 
	 * @param attachments 添付ファイル情報
	 */
	public void setAttachments(List<SWK_ATTACH> attachments) {

		// ローカルに保存する
		if (attachments != null) {

			try {
				TPrinter printer = new TPrinter(true);

				for (SWK_ATTACH bean : attachments) {
					if (bean.getFileData() != null) {
						byte[] data = (byte[]) bean.getFileData();

						String tempFileName = printer.createResultFile(bean.getFILE_NAME(), data);
						File file = new File(tempFileName);
						bean.setFile(file);
						bean.setFileData(null); // バイナリをクリア
					}
				}
			} catch (Exception ex) {
				errorHandler(ex);
			}
		}

		this.attachments = attachments;
		refresh();
	}

	/**
	 * 添付ファイルの削除
	 * 
	 * @param list 添付ファイル情報
	 */
	public void removeAttachment(List<SWK_ATTACH> list) {

		if (this.attachments != null) {
			for (SWK_ATTACH bean : list) {
				this.attachments.remove(bean);
			}
		}
		refresh();
	}

	/**
	 * ファイル追加
	 * 
	 * @param filename キー
	 * @param file ファイル
	 */
	public void addFile(String filename, File file) {

		if (this.attachments == null) {
			// ファイルマップの初期化
			attachments = new ArrayList<SWK_ATTACH>();
		}

		this.attachments.add(createEntity(filename, file));
		refresh();
	}

	/**
	 * 添付エンティティの作成
	 * 
	 * @param filename
	 * @param file
	 * @return 添付
	 */
	protected SWK_ATTACH createEntity(String filename, File file) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(null); // 新規
		bean.setFILE_NAME(filename);
		bean.setFile(file);

		return bean;
	}

	/**
	 * ファイル追加<br>
	 * File直接送信を使ってください
	 * 
	 * @param filename キー
	 * @param data バイナリ
	 */
	@Deprecated
	public void addFile(String filename, byte[] data) {

		if (this.attachments == null) {
			// ファイルマップの初期化
			attachments = new ArrayList<SWK_ATTACH>();
		}

		this.attachments.add(createEntity(filename, data));
		refresh();
	}

	/**
	 * 添付エンティティの作成<br>
	 * File直接送信を使ってください
	 * 
	 * @param filename
	 * @param data
	 * @return 添付
	 */
	@Deprecated
	protected SWK_ATTACH createEntity(String filename, byte[] data) {
		SWK_ATTACH bean = createBean();
		if (getUser() != null && getUser().getEmployee() != null) {
			bean.setEMP_CODE(getUser().getEmployee().getCode());
			bean.setEMP_NAME(getUser().getEmployee().getName());
			bean.setEMP_NAME_S(getUser().getEmployee().getNames());
		}
		bean.setINP_DATE(null); // 新規
		bean.setFILE_NAME(filename);
		bean.setFILE_DATA(data);

		return bean;
	}

	/**
	 * @return エンティティ作成
	 */
	protected SWK_ATTACH createBean() {
		return new SWK_ATTACH();
	}

	/**
	 * 最新反映処理
	 */
	public void refresh() {

		// 画像反映
		if (attachments != null && !attachments.isEmpty()) {
			btn.setIconType(IconType.ATTACHMENT_COMPLETE);
		} else {
			btn.setIconType(IconType.ATTACHMENT);
		}

		// ツールチップ反映
		setToolTipText();
	}

	/**
	 * クリア処理
	 */
	public void clear() {
		init();
	}

	/**
	 * ToolTipの設定
	 */
	public void setToolTipText() {
		String noneWord = getWord("C11611"); // 添付ファイルなし
		String tooltip = "<html><font color=red><b>" + noneWord + "</b></font></html>"; // 添付ファイルなし

		if (attachments != null && !attachments.isEmpty()) {

			String rightWord = getWord("C11613"); // 添付ファイル

			StringBuilder sb = new StringBuilder();
			sb.append("<html>");
			sb.append("<b>" + rightWord + "</b>"); // 添付ファイル
			sb.append("<br>");

			int max = 50;

			for (SWK_ATTACH bean : attachments) {
				String key = bean.getFILE_NAME();
				max = Math.max(max, key.getBytes().length + 1);
			}

			int i = 1;

			for (SWK_ATTACH bean : attachments) {
				String key = bean.getFILE_NAME();
				String no = StringUtil.fillHtmlSpace(Integer.toString(i++), 4);
				String filename = StringUtil.fillHtmlSpace(key, max);

				sb.append("<b>" + no + "</b>");
				sb.append("<font color=blue>" + filename + "</font>");
				sb.append("<br>");
			}

			sb.append("</html>");

			tooltip = sb.toString();
		}

		btn.setToolTipText(tooltip);
	}

	/**
	 * @return FolderKeyNameを戻します。
	 */
	protected String getFolderKeyName() {
		if (btn.parent != null) {
			return btn.parent.getClass().getName() + ".slip.attachment";
		} else if (btn.dialog != null) {
			return btn.dialog.getClass().getName() + ".slip.attachment";
		}
		return "slip.attachment";
	}

	/**
	 * 前回保存したフォルダ情報を返す。
	 * 
	 * @param type 種類
	 * @return 前回保存したフォルダ情報
	 */
	protected File getPreviousFolder(String type) {

		String path = SystemUtil.getAisSettingDir();
		File dir = (File) FileUtil.getObject(path + File.separator + getFolderKeyName() + type);

		return dir;
	}

	/**
	 * 指定のフォルダ情報を保存する
	 * 
	 * @param dir フォルダ情報
	 */
	protected void saveFolder(File dir) {
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(dir, path + File.separator + getFolderKeyName() + ".chooser");
	}

}

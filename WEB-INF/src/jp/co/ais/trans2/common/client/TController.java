package jp.co.ais.trans2.common.client;

import java.awt.*;
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * コントローラ基底
 * 
 * @author AIS
 */
public abstract class TController extends TPanelCtrlBase {

	/** true:ZIPモード */
	protected static boolean isZipMode = false;

	/** システムバージョン */
	public static String jarUpdateDate = null;

	/** システムバージョン */
	public static String jarVersion = "";

	static {
		try {
			// (true:ZIPモード)
			isZipMode = ClientConfig.isFlagOn("trans.response.zip.mode");
		} catch (MissingResourceException e) {
			// 処理なし
		}
	}

	/**
	 * プログラムを開始する(メイン画面のOrverride用)
	 */
	public void start() {
		//
	}

	/**
	 * バージョン初期化
	 */
	public static void initClientVersion() {

		try {
			Class clazz = TController.class;

			try {
				// 個別指定(カスタマイズユーザー対応のため)
				String clazzName = ClientConfig.getProperty("trans.version.check.class");
				clazz = Class.forName(clazzName);

			} catch (Exception e) {
				// 処理なし
			}

			String[] info = Util.getSystemVersion(clazz);
			jarUpdateDate = info[0];
			jarVersion = info[1];

		} catch (Exception e) {
			// エラー表示
			ClientLogger.error("version initial error.");
		}
	}

	/**
	 * パネル取得 (メイン画面のOrverride用)
	 * 
	 * @return パネル
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * Serverとの通信
	 * 
	 * @param cls クラス
	 * @param methodName メソッド
	 * @param arg 引数
	 * @return 結果
	 * @throws TException
	 */
	public Object request(Class cls, String methodName, Object... arg) throws TException {

		try {
			String programCode = getProgramCode();
			String realUID = getRealUID();
			String realInfo = getRealInfo();

			// Webアプリケーションの場合
			if (isWeb()) {
				addSendValues("programCode", programCode);
				addSendValues("realUID", realUID);
				addSendValues("realInfo", realInfo);
				addSendValues("className", cls != null ? cls.getName() : "");
				addSendValues("methodName", methodName);
				addSendValues("systemVersion", jarVersion); // クライアント側で持っているバージョン

				// ファイルを切り分け
				List<Object> objectList = new ArrayList<Object>();
				List<File> fileList = new ArrayList<File>();
				List<Integer> fileIndexs = new ArrayList<Integer>();

				// WEB送信対象リストを整備
				FileTransferUtil.reflectWebObjectList(objectList, fileList, fileIndexs, arg);

				addSendObject("methodArg", objectList);
				// addSendObject("fileIndex", fileIndexs);

				addSendObject("trans.system.company", getCompany());
				addSendObject("trans.system.user", getUser());

				send(getServletName(), fileList);

				// エラーハンドリング
				if (getRespMode() == RESP_ERROR || getRespMode() == RESP_ERROR_SYNTAX) {

					if (Util.isNullOrEmpty(getErrorMessage())) {
						throw new TException(getErrorCode());

					} else {
						throw new TException(getErrorMessage());
					}
				}

				Object ret = getResultObject();

				if (ret instanceof Throwable) {
					throw (Exception) ret;
				}

				// バイナリは圧縮を解凍
				if (ret instanceof byte[] && isZipMode) {
					ret = ResourceUtil.toBinaryInZip((byte[]) ret);
				}

				return ret;
			}

			// 非Webアプリケーションの場合
			canTransfer(arg); // 開発時通信チェック

			Object ret = null;

			Class clazz = Class.forName(cls.getName());

			TContainer container = TContainerFactory.getContainer();
			Object instance = container.getComponent(clazz);

			if (instance instanceof TModel) {
				TModel model = (TModel) instance;
				model.setCompany(TLoginInfo.getCompany());
				model.setUser(TLoginInfo.getUser());
				model.setProgramCode(programCode);
				model.setNow(Util.getCurrentDate());

				String url = TLoginCtrl.getClientSaveKey();
				model.setServerInstanceName(url);

				// 画面識別子及びログ用情報
				model.setRealUID(realUID);
				model.setRealInfo(realInfo);
			}

			if (arg == null || arg.length == 0) {
				Method method = clazz.getMethod(methodName);
				ret = method.invoke(instance);

			} else {
				Method method = getMethod(clazz, methodName, arg);

				if (method == null) {
					throw new TException(getMessage("E00038") + clazz.getName() + "#" + methodName + "()");// 呼び出し先メソッドが見つかりません:
				}

				ret = method.invoke(instance, arg);
			}

			canTransfer(new Object[] { ret }); // 開発時通信チェック

			return ret;

		} catch (InvocationTargetException e) {
			if (e.getTargetException() instanceof TException) {
				throw (TException) e.getTargetException();

			} else if (e.getTargetException() instanceof TRuntimeException) {
				throw (TRuntimeException) e.getTargetException();

			} else if (e.getTargetException() instanceof RuntimeException) {
				throw (RuntimeException) e.getTargetException();
			}

			throw new RuntimeException(e);

		} catch (Throwable e) {
			if (e instanceof TException) {
				throw (TException) e;

			} else if (e instanceof TRuntimeException || e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}

			Throwable cause = e.getCause();
			if (cause != null) {
				if (cause instanceof TException) {
					throw (TException) cause;

				} else if (cause instanceof TRuntimeException) {
					throw (TRuntimeException) cause;
				}
			}

			if (e instanceof RuntimeException) {
				throw (RuntimeException) e;
			}

			throw new RuntimeException(e);
		}
	}

	/**
	 * Webモード判定
	 * 
	 * @return true:Webモード
	 */
	protected boolean isWeb() {
		return ClientConfig.isWeb();
	}

	/**
	 * 呼び出しメソッドの特定
	 * 
	 * @param clazz クラス
	 * @param name メソッド名
	 * @param args 引数
	 * @return メソッド
	 */
	protected Method getMethod(Class clazz, String name, Object[] args) {

		// 単純呼び出し
		try {
			Class classes[] = new Class[args.length];
			for (int i = 0; i < args.length; i++) {
				classes[i] = args[i].getClass();
			}
			return clazz.getMethod(name, classes);

		} catch (Exception ex) {
			// 次へ
		}

		// クラスにあるメソッド観点からの割り出し
		Method[] methods = clazz.getMethods();

		Map<Method, Class[]> map = new HashMap<Method, Class[]>();

		// 名前、引数の数が一致するメソッドのみピックアップ
		for (Method method : methods) {
			if (name.equals(method.getName()) && method.getParameterTypes().length == args.length) {
				map.put(method, method.getParameterTypes());
			}
		}

		if (map.isEmpty()) {
			// 該当なし
			return null;

		} else if (map.size() == 1) {
			// 該当1件なので返してしまう
			return map.keySet().toArray(new Method[1])[0];
		}

		// 引数Classの候補一覧を順に纏める
		List<List<Class>> argClassList = new ArrayList<List<Class>>(args.length);

		for (Object arg : args) {
			List<Class> classList = new ArrayList<Class>();

			Class aclass = arg.getClass();
			classList.add(aclass); // 元
			classList.add(aclass.getSuperclass()); // 親

			// interface
			for (Class iclass : aclass.getInterfaces()) {
				classList.add(iclass);
			}

			argClassList.add(classList);
		}

		// マッチ検索
		m1: for (Map.Entry<Method, Class[]> entry : map.entrySet()) {
			Class[] values = entry.getValue();

			// 引数先頭からマッチするか確認
			m2: for (int i = 0; i < values.length; i++) {
				Class value = values[i];

				// 候補リスト
				List<Class> kList = argClassList.get(i);

				for (Class kc : kList) {
					if (value.equals(kc)) {
						// 候補にあれば次の引数へ
						continue m2;
					}
				}

				// 候補になければ、次のメソッドへ
				continue m1;
			}

			// オールクリアでMethod返す
			return entry.getKey();
		}

		// 結局見つからない
		return null;
	}

	/**
	 * 通信先Servlet名
	 * 
	 * @return Servlet名
	 */
	protected String getServletName() {
		return "TServlet";
	}

	/**
	 * 通信可能かどうかチェック.NGはエラー(非通信ローカル開発用)
	 * 
	 * @param args 対象
	 */
	protected void canTransfer(Object[] args) {

		boolean not = false;

		// Selealizableチェック
		Object notObject = null;
		for (Object arg : args) {
			if (arg == null) {
				continue;
			}

			if (arg instanceof List) {
				List list = (List) arg;
				if (list.isEmpty()) {
					continue;
				}

				if (!(list.get(0) instanceof Serializable)) {
					notObject = list.get(0);
					not = true;
					break;
				}

			} else if (arg instanceof Map) {
				Map map = ((Map) arg);
				if (map.isEmpty()) {
					continue;
				}

				Map.Entry[] arry = (Map.Entry[]) map.entrySet().toArray(new Map.Entry[0]);

				if (!(arry[0].getKey() instanceof Serializable)) {
					notObject = arry[0].getKey();
					not = true;
					break;
				}

				if (!(arry[0].getValue() instanceof Serializable)) {
					notObject = arry[0].getValue();
					not = true;
					break;
				}

			} else if (arg instanceof Set) {
				Set set = ((Set) arg);
				if (set.isEmpty()) {
					continue;
				}

				Object[] arry = set.toArray(new Object[0]);
				if (!(arry[0] instanceof Serializable) || !(arry[0] instanceof Serializable)) {
					notObject = arry[0];
					not = true;
					break;
				}

			} else {
				if (!(arg instanceof Serializable)) {
					notObject = arg;
					not = true;
					break;
				}
			}
		}

		if (not) {
			String className = notObject == null ? "" : notObject.getClass().getName();
			throw new TRuntimeException("E00039", className);// 通信オブジェクトはSerializableにする必要があります。{0}
		}
	}

	/**
	 * ログイン会社情報を戻します。
	 * 
	 * @return 会社情報
	 */
	public Company getCompany() {
		return TLoginInfo.getCompany();
	}

	/**
	 * ログイン会社コードを戻します。
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return getCompany().getCode();
	}

	/**
	 * ログインユーザー情報を戻します。
	 * 
	 * @return ユーザー情報
	 */
	public User getUser() {
		return TLoginInfo.getUser();
	}

	/**
	 * ログインユーザーコードを戻します。
	 * 
	 * @return ユーザーコード
	 */
	public String getUserCode() {
		return getUser().getCode();
	}

	/**
	 * プログラム情報の取得
	 * 
	 * @return プログラム情報
	 */
	@Override
	public TClientProgramInfo getProgramInfo() {

		return this.prgInfo;
	}

	/**
	 * プログラムID取得
	 * 
	 * @return プログラムID
	 */
	@Override
	public String getProgramCode() {

		if (prgInfo == null) {
			return "";
		}

		return this.getProgramInfo().getProgramCode();
	}

	/**
	 * プログラム名称取得
	 * 
	 * @return プログラム名称
	 */
	@Override
	public String getProgramName() {

		if (prgInfo == null) {
			return "";
		}

		return this.getProgramInfo().getProgramName();
	}

	/**
	 * エラーハンドリング<br>
	 * TExceptionの場合は、保持しているメッセージを表示.<br>
	 * それ以外は「正常に処理されませんでした」のメッセージを表示する.
	 * 
	 * @param parent 親コンポーネント
	 * @param ex 発生Exception
	 */
	@Override
	public void errorHandler(Component parent, Throwable ex) {

		if (ex instanceof TMessageListException) {
			TMessageListException tex = (TMessageListException) ex;
			showMessageList(parent, tex.getMessage(), tex.getList());

		} else {
			super.errorHandler(parent, ex);
		}
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 */
	public void showList(String message, List<String> list) {
		List<Message> mlist = new ArrayList<Message>(list.size());

		for (String str : list) {
			mlist.add(new Message(str));
		}

		showMessageList(getView(), message, mlist);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param list メッセージリスト
	 */
	public void showMessageList(List<Message> list) {
		showMessageList(getView(), list);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param list メッセージリスト
	 */
	public void showMessageList(Component comp, List<Message> list) {
		showMessageList(comp, "", list);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 */
	public void showMessageList(String message, List<Message> list) {
		showMessageList(getView(), message, list);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 */
	public void showMessageList(Component comp, String message, List<Message> list) {

		MessageListDialog dialog;
		if (comp instanceof TPanel) {
			dialog = createMessageDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = createMessageDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = createMessageDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		// メッセージ変換
		List<String[]> msgList = new ArrayList<String[]>();
		LinkedHashSet<String> msgs = new LinkedHashSet<String>();

		for (Message bean : list) {
			if (bean.hasMessage()) {
				String[] args = new String[2];
				args[0] = getMessage(bean.getSubMessageID());
				args[1] = getMessage(bean.getMessageID(), bean.getBindIds());

				// 同一メッセージは除外
				String msg = args[0] + "<>" + args[1];
				if (msgs.contains(msg)) {
					continue;
				}
				msgs.add(msg);

				msgList.add(args);
			}
		}

		dialog.setMessagesList(msgList);

		// メッセージ一覧表示ダイアログを表示する
		dialog.show(getMessage(message));
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 * @return 選択値
	 */
	public int showConfermList(String message, List<String> list) {
		return showConfermList(getView(), message, list);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 * @return 選択値
	 */
	public int showConfermList(Component comp, String message, List<String> list) {
		List<Message> mlist = new ArrayList<Message>(list.size());

		for (String str : list) {
			mlist.add(new Message(str));
		}

		return showConfermMessageList(comp, message, mlist);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 * @return 選択値
	 */
	public int showConfermMessageList(String message, List<Message> list) {
		return showConfermMessageList(getView(), message, list);
	}

	/**
	 * 指定されたメッセージを一覧で表示する
	 * 
	 * @param comp 呼び出し元コンポーネント
	 * @param message ラベルメッセージ
	 * @param list メッセージリスト
	 * @return 選択値
	 */
	public int showConfermMessageList(Component comp, String message, List<Message> list) {

		ConfermMessageListDialog dialog;
		if (comp instanceof TPanel) {
			dialog = createConfermDialog(((TPanel) comp).getParentFrame());

		} else {
			Component parent = TGuiUtil.getParentFrameOrDialog2(comp);

			if (parent instanceof Frame) {
				dialog = createConfermDialog((Frame) parent);

			} else if (parent instanceof Dialog) {
				dialog = createConfermDialog((Dialog) parent);

			} else {
				throw new IllegalArgumentException("Wrong parent window");
			}
		}

		List<String[]> msgList = new ArrayList<String[]>();
		LinkedHashSet<String> msgs = new LinkedHashSet<String>();

		for (Message bean : list) {
			if (bean.hasMessage()) {
				String[] args = new String[2];
				args[0] = getMessage(bean.getSubMessageID());
				args[1] = getMessage(bean.getMessageID(), bean.getBindIds());

				// 同一メッセージは除外
				String msg = args[0] + "<>" + args[1];
				if (msgs.contains(msg)) {
					continue;
				}
				msgs.add(msg);

				msgList.add(args);
			}
		}

		dialog.setMessageList(msgList);

		// メッセージ一覧表示ダイアログを表示する
		return dialog.show(getMessage(message));
	}

	/**
	 * @param comp
	 * @return メッセージダイアログ
	 */
	protected MessageListDialog createMessageDialog(Frame comp) {
		return new MessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return メッセージダイアログ
	 */
	protected MessageListDialog createMessageDialog(Dialog comp) {
		return new MessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return 確認ダイアログ
	 */
	protected ConfermMessageListDialog createConfermDialog(Frame comp) {
		return new ConfermMessageListDialog(comp);
	}

	/**
	 * @param comp
	 * @return 確認ダイアログ
	 */
	protected ConfermMessageListDialog createConfermDialog(Dialog comp) {
		return new ConfermMessageListDialog(comp);
	}

	/**
	 * ログを取る
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 */
	public void log(Company company, User user, String programCode, String message) {
		log(company, user, programCode, message, "");
	}

	/**
	 * ログを取る
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @param info
	 */
	public void log(Company company, User user, String programCode, String message, String info) {

		try {

			// ログ生成
			logE(company, user, programCode, message, info);

		} catch (TException e) {
			errorHandler(e);
		}
	}

	/**
	 * ログを取る
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @throws TException
	 */
	public void logE(Company company, User user, String programCode, String message) throws TException {
		logE(company, user, programCode, message, "");
	}

	/**
	 * ログを取る
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @param message
	 * @param info
	 * @throws TException
	 */
	public void logE(Company company, User user, String programCode, String message, String info) throws TException {

		// ログ生成
		Log log = new Log();

		log.setDate(Util.getCurrentDate());
		log.setCompany(company);
		log.setUser(user);
		Program program = new Program();
		program.setCode(programCode);
		log.setProgram(program);
		try {
			log.setIp(InetAddress.getLocalHost().getHostAddress());
		} catch (Exception e) {
			log.setIp("Unknown");
		}
		log.setMessage(message);
		log.setInfo(info);

		request(LogManager.class, "log", log);
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param data
	 * @param fileName 拡張子（.xls）不要
	 * @throws TException
	 */
	protected void printOutExcel(byte[] data, String fileName) throws TException {
		printOut(data, fileName + ".xls");
	}

	/**
	 * PDFを出力する
	 * 
	 * @param data
	 * @param fileName 拡張子（.pdf）不要
	 * @throws TException
	 */
	protected void printOutPDF(byte[] data, String fileName) throws TException {
		printOut(data, fileName + ".pdf");
	}

	/**
	 * ファイル名を指定して出力する。
	 * 
	 * @param data
	 * @param fileName (拡張子が必要)
	 * @throws TException
	 */
	protected void printOut(byte[] data, String fileName) throws TException {
		if (data == null || data.length == 0) {
			showMessage("I00022");
			return;
		}

		// プレビュー
		TPrinter printer = new TPrinter();
		printer.preview(data, fileName);
	}

	/**
	 * バックアップフォルダにファイルを移動する.
	 * 
	 * @param file 対象ファイル
	 */
	protected void moveToBackup(TFile file) {
		try {
			String oldPath = file.getFile().getPath();
			String newPath = file.getFile().getParent() + File.separator + "backup" + File.separator
					+ file.getFileName();

			if (new File(newPath).exists()) {
				// バックアップ先に同名ファイルが存在します。上書きしますか？
				if (!showConfirmMessage("Q00065")) {
					return;
				}
			}

			FileUtil.moveFile(oldPath, newPath);

		} catch (Exception ex) {
			// 画面上には特に通知しない
			ClientErrorHandler.handledException(ex);
		}

	}

}

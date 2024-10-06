package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ログインのコントローラ。<BR>
 * アプリケーションの起動、ログイン画面の制御、ログインの成否判定を行う。
 * 
 * @author AIS
 */
public class TLoginCtrl extends TController {

	/** 曜日ラベル */
	protected static final String[] CALENDAR_WEEK_IDS = { "C90001", "C90002", "C90003", "C90004", "C90005", "C90006",
			"C90007" };

	/** テキストフィールドポップアップメニューラベル */
	protected static final String[] TXT_POPUP_IDS = { "C90008", "C90009", "C90010", "C90011" };

	/**
	 * スプレッドシートポップアップメニューラベル C11278 表示設定クリア C11438 自動幅調整(ヘッダーと値) C11439 自動幅調整(ヘッダー) C11440 自動幅調整(値)
	 */
	protected static final String[] TBL_POPUP_IDS = { "C11278", "C11438", "C11439", "C11440" };

	/** ログイン画面 */
	protected TLogin loginView = null;

	/** true:前回入力値表示 */
	protected static final boolean isLoadPrevInputted = ClientConfig.isFlagOn("trans.login.default.prev");

	/** true:完了メッセージを非表示 */
	protected static final boolean isShowProgressBar = ClientConfig.isFlagOn("trans.login.show.progress.bar");

	/** ログインServletへのアクセスかどうか */
	protected boolean isLoginServlet;

	/** アイコン */
	protected static BufferedImage icon = null;

	/** PDF直接印刷するかどうか、true:PDF直接印刷する */
	protected static final boolean isDirectPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

	/** ログインキャンセル利用するかどうか、true:ログインキャンセル利用 */
	protected static final boolean isUseLoginCancel = ClientConfig.isFlagOn("trans.use.login.cancel");

	/** 会社固定及び非表示、true:会社固定及び非表示 */
	protected static final boolean isNotShowCompany = ClientConfig.isFlagOn("trans.not.show.company");

	/** 固定会社コード */
	protected String fixedCompanyCode;

	/** 処理中フラグ */
	protected boolean processing = false;

	/**
	 * プログラムエントリポイント<BR>
	 * アプリケーションを起動する。
	 */
	@Override
	public void start() {

		// UIの設定
		try {
			// L&F 初期化
			TUIManager.setLookAndFeel(LookAndFeelType.WINDOWS.value);

			// ログイン画面生成
			loginView = createLoginView();

			// ログイン画面初期化
			initLoginView();

			TUIManager.setUI(loginView.btnLogin, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClear, LookAndFeelType.MINT, LookAndFeelColor.White);
			TUIManager.setUI(loginView.btnClose, LookAndFeelType.MINT, LookAndFeelColor.White);

			if (isShowProgressBar) {
				TUIManager.setUI(loginView.progressBar, LookAndFeelType.MINT, LookAndFeelColor.White);
			} else {
				loginView.remove(loginView.progressBar);
			}

			// ログイン画面表示
			loginView.setLocationRelativeTo(null);
			loginView.setVisible(true);

			// デフォルト値設定
			setDefaultLoginInfo();

		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * ログイン画面を初期化する
	 */
	protected void initLoginView() {

		if (Util.isNullOrEmpty(TController.jarVersion)) {
			TController.initClientVersion();
		}

		if (icon == null) {
			initIcon();
		}

		if (icon != null) {
			loginView.setIconImage(icon);
		}

		// ログイン画面初期化
		loginView.init();

		if (isNotShowCompany) {
			loginView.txCompanyCode.setVisible(false);
		}

		// ログイン画面のイベント定義
		initLoginViewEvent();

		// タイトル
		loginView.setTitle(ClientConfig.getTitle());

		loginView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// GlassPane
		loginView.setGlassPane(new LockingGlassPane());
		loginView.getGlassPane().setVisible(false);
	}

	/**
	 * 
	 */
	protected void initIcon() {
		try {
			icon = ResourceUtil.getImage("images/" + ClientConfig.getProperty("favicon.file.name"));
		} catch (Throwable ex) {
			// エラーなし
		}
	}

	/**
	 * デフォルト値設定
	 */
	protected void setDefaultLoginInfo() {
		String companyCode = "";
		String userCode = "";

		if (isLoadPrevInputted) {
			// 前回入力値表示
			User user = getLoginInfo();
			if (user != null) {
				companyCode = user.getCompanyCode();
				userCode = user.getCode();
			}
		} else {

			try {
				// プロパティーファイル設定値
				companyCode = ClientConfig.getProperty("trans.fixed.login.company.code");
			} catch (Exception e) {
				// エラーなし
			}
		}

		if (isNotShowCompany) {
			// 会社コード非表示

			fixedCompanyCode = null;

			try {
				// プロパティーファイル設定値
				fixedCompanyCode = ClientConfig.getProperty("trans.fixed.login.company.code");
			} catch (Exception e) {
				// エラーなし
			}

			if (!Util.isNullOrEmpty(fixedCompanyCode)) {
				// 固定会社コード指定済み
				companyCode = fixedCompanyCode;
				loginView.txCompanyCode.setVisible(false);
			} else {
				loginView.txCompanyCode.setVisible(true);
			}
		}

		loginView.txCompanyCode.setValue(companyCode);
		loginView.txUserCode.setValue(userCode);

		// フォーカス設定
		if (!Util.isNullOrEmpty(companyCode)) {
			if (!Util.isNullOrEmpty(userCode)) {
				loginView.txPassword.requestFocus();
			} else {
				loginView.txUserCode.requestFocus();
			}
		} else {
			loginView.txCompanyCode.requestFocus();
		}

		// メモ情報
		if (ClientConfig.isFlagOn("trans.login.show.title")) {
			Color fontColor = loginView.lblRemark.getForeground();

			try {
				fontColor = Util.toColor(StringUtil.split(ClientConfig.getProperty("login.title.fontcolor"), ","));
			} catch (Exception ex) {
				//
			}

			loginView.lblRemark.setText(ClientConfig.getTitle());
			loginView.lblRemark.setForeground(fontColor);
			loginView.lblRemark.setVisible(true);
		} else {
			loginView.lblRemark.setVisible(false);
		}

	}

	/**
	 * ログイン画面のイベント定義
	 */
	protected void initLoginViewEvent() {

		// ログイン押下
		loginView.btnLogin.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				loginView.btnLogin.setEnabled(false);
				btnLogin_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				loginView.btnLogin.setEnabled(true);
			}
		});

		// クリア押下
		loginView.btnClear.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClear_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 閉じる押下
		loginView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				loginView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * ログイン画面のファクトリ
	 * 
	 * @return ログイン画面
	 */
	protected TLogin createLoginView() {
		return new TLogin();
	}

	/**
	 * [ログイン]ボタン押下
	 */
	protected void btnLogin_Click() {

		try {

			if (processing) {
				return;
			}

			processing = true;

			// 入力チェック
			if (!isInputCorrect()) {
				processing = false;
				return;
			}

			// ログイン処理
			login();

		} catch (Exception e) {
			errorHandler(loginView, e);
		}
	}

	/**
	 * ログイン処理
	 */
	protected void login() {

		int processCount = 12;

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {

				boolean isStarted = false;

				try {

					// 砂時計
					loginView.getGlassPane().setVisible(true);

					if (isShowProgressBar) {
						loginView.progressBar.setVisible(true);
					}

					{// 1
						setProgressText("Session ID intial...");// setProgressValue(i++);

						// ユーザー認証
						try {
							String sessionID = authenticateUser(loginView.txCompanyCode.getValue(),
								loginView.txUserCode.getValue(), loginView.txPassword.getValue());

							// ログアウトでセッション開放考慮
							TClientLoginInfo.setSessionID(sessionID);

						} catch (TException e) {
							showMessage(loginView, e.getMessageID());
							return;
						}
					}

					User user = null;

					{// 2
						setProgressText("User initial...");// setProgressValue(i++);

						// ユーザー情報取得
						user = getUser(loginView.txCompanyCode.getValue(), loginView.txUserCode.getValue());

						if (user == null) {
							showMessage(loginView, "Error:Can not find User");
							return;
						}
					}

					{// 3
						setProgressText("User info setting...");// setProgressValue(i++);

						if (isDirectPrint) {
							// 直接印刷の場合、プリンタの名称取得
							String printerName = getPrinterName(loginView.txCompanyCode.getValue(),
								loginView.txUserCode.getValue());
							user.setPrinterName(printerName);
						}

						TLoginInfo.setUser(user);
					}

					{// 4
						setProgressText("Language initial...");// setProgressValue(i++);

						// 多言語対応
						String lang = user.getLanguage();
						TClientLoginInfo.getInstance().setUserLanguage(lang);

						TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());

						// 各部品内単語
						TCalendar.setDayOfWeekWords(getWordList(CALENDAR_WEEK_IDS));
						TGuiUtil.setLightPopupMenuWords(getWordList(TXT_POPUP_IDS));
						TTable.setLightPopupMenuWords(getWordList(TBL_POPUP_IDS));
					}

					Company company = null;

					{// 5
						setProgressText("Company initial...");// setProgressValue(i++);

						// 会社情報取得
						company = getCompany(loginView.txCompanyCode.getValue());

						if (company == null) {
							showMessage(loginView, "Error:Can not find Company");
							return;
						}
						TLoginInfo.setCompany(company);
					}

					{ // 7
						setProgressText("Client info initial...");// setProgressValue(i++);

						// 旧バージョン用
						TClientLoginInfo.getInstance().setCompanyCode(company.getCode());
						TClientLoginInfo.getInstance().setUserCode(user.getCode());

						if (ClientConfig.isWeb()) {
							TClientLoginInfo.reflesh();
						}
					}

					{// 8
						setProgressText("Cancel exclude...");// setProgressValue(i++);

						// パスワード有効期間チェック
						if (!checkPassword(company, user)) {
							// 排他を解除してから、処理中断
							request(UserExclusiveManager.class, "cancelExclude", company.getCode(), user.getCode());
							return;
						}
					}

					// スタイル適用 (現状はLook and Feelの設定)
					final LookAndFeelType type;
					final LookAndFeelColor color;

					{// 9
						setProgressText("Change user setting...");// setProgressValue(i++);

						if (Util.isNullOrEmpty(user.getLfName())) {
							// ユーザ登録されていない場合、デフォルトスタイル適用
							String style = ClientConfig.getSystemStyle();
							type = LookAndFeelType.getStyle(style);
							color = LookAndFeelColor.White;

						} else {
							type = LookAndFeelType.get(user.getLfName());
							color = LookAndFeelColor.get(user.getLfColorType());
						}

						SwingUtilities.invokeLater(new Runnable() {

							public void run() {
								// 表示切替
								try {
									TUIManager.setUI(loginView, type, color);
								} catch (Exception e) {
									e.printStackTrace();
								}

							}
						});
					}

					{// 10
						setProgressText("UI updating...");// setProgressValue(i++);

						// 表示切替
						TUIManager.setMenuType(user.getMenuType());

						// 前回入力値表示モードの場合、ログイン情報保存
						if (isLoadPrevInputted) {
							saveLoginInfo(company.getCode(), user.getCode());
						}

					}

					{// 11
						// 関連情報の初期化
						initInvoice();
					}

					{// 12
						setProgressText("Login initial...");// setProgressValue(i++);

						// 初期化処理
						doInit();
					}

					{// 13
						setProgressText("Loading...");// setProgressValue(i++);

						// グランドメニュー起動
						TMainCtrl ctrl = createTMainCtrl();

						// アイコン設定
						ctrl.setIcon(icon);

						// 開始
						ctrl.start();
					}

					// ログイン画面を閉じる
					loginView.setVisible(false);

					{// 14
						setProgressText("Login completed.");// setProgressValue(i++);

						// ログインログ記録
						log(getCompany(), getUser(), null, "Login");
					}

					isStarted = true;
				} catch (Exception e) {
					errorHandler(e);
				} finally {

					processing = false;

					if (!isStarted) {
						// 起動失敗、ログイン画面再表示
						loginView.setVisible(true);
					}

					// 処理中断
					loginView.progressBar.setVisible(false);

					loginView.getGlassPane().setVisible(false);
				}
			}

		});

		loginView.progressBar.setMaximum(processCount);
		thread.start();

		if (!loginView.isVisible()) {
			loginView.dispose();
		} else {
			loginView.btnLogin.requestFocus();
		}
	}

	/**
	 * プログレスバー現在値＋１、表示文字設定.
	 * 
	 * @param str 進捗バーの現在表示文字
	 */
	public void setProgressText(String str) {
		loginView.progressBar.setValue(loginView.progressBar.getValue() + 1);
		loginView.progressBar.setString(str);
	}

	/**
	 * @throws Exception
	 */
	public static void doInit() throws Exception {
		// ログイン時に初期処理
		List<Class> clzList = getLoginInitialClasses();
		if (!clzList.isEmpty()) {
			// 初期処理あり
			System.out.println("login initial");

			for (Class clz : clzList) {
				Object obj = clz.newInstance();
				if (obj instanceof TLoginInitialInterface) {
					TLoginInitialInterface clazz = (TLoginInitialInterface) obj;
					System.out.println(clazz.getName());

					// 初期化処理
					clazz.init();
				}
			}
		}
	}

	/**
	 * @return ログイン初期化クラス一覧取得
	 */
	protected static List<Class> getLoginInitialClasses() {
		String k = "trans.login.init.class.";
		List<Class> list = getInitialClasses(k);
		return list;
	}

	/**
	 * ログイン/Main画面
	 * 
	 * @param k キー
	 * @return 初期化クラス一覧取得
	 */
	public static List<Class> getInitialClasses(String k) {

		List<Class> list = new ArrayList<Class>();

		try {
			for (int i = 1; i <= 10; i++) {
				String key = k + i;
				String className = ClientConfig.getProperty(key);
				if (!Util.isNullOrEmpty(className)) {
					Class clazz = Class.forName(className);
					list.add(clazz);
				}
			}

		} catch (Throwable ex) {
			// エラーなし、処理中断
		}

		return list;
	}

	/**
	 * メインコントロール生成
	 * 
	 * @return メインコントロール
	 */
	protected TMainCtrl createTMainCtrl() {
		if (ClientConfig.isFlagOn("trans.bi.use")) {
			return new TBIMainCtrl();
		}
		return new TMainCtrl();
	}

	/**
	 * [クリア]ボタン押下
	 */
	protected void btnClear_Click() {

		try {

			// フィールドをクリアする
			if (loginView.txCompanyCode.isVisible()) {
				loginView.txCompanyCode.setValue(null);
			} else {
				loginView.txCompanyCode.setValue(fixedCompanyCode);
			}
			loginView.txUserCode.setValue(null);
			loginView.txPassword.setValue(null);

		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * [閉じる]ボタン押下
	 */
	protected void btnClose_Click() {

		try {
			loginView.setVisible(false);
			loginView.dispose();
			System.exit(0);
		} catch (Exception e) {
			errorHandler(loginView, e);
		}

	}

	/**
	 * 入力が正しいかを返す
	 * 
	 * @return true(正常) / false(エラー)
	 */
	protected boolean isInputCorrect() {

		// 会社コードが未入力の場合エラー
		if (Util.isNullOrEmpty(loginView.txCompanyCode.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txCompanyCode.requestFocus();
			return false;
		}

		// ユーザーコードチェック
		if (Util.isNullOrEmpty(loginView.txUserCode.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txUserCode.requestFocus();
			return false;
		}

		// パスワードチェック
		if (Util.isNullOrEmpty(loginView.txPassword.getValue())) {
			showMessage(loginView, "Please Input All Items");
			loginView.txPassword.requestFocus();
			return false;
		}

		if (isUseLoginCancel && ClientConfig.isWeb()) {
			// 強制解除処理

			String companyCode = loginView.txCompanyCode.getValue();
			String userCode = loginView.txUserCode.getValue();
			String password = loginView.txPassword.getValue();

			try {
				UserExclusive userExclusive = (UserExclusive) request(null, "canEntry", companyCode, userCode, password);

				if (UserExclusive.Locked != userExclusive) {
					// 処理なし
					return true;
				}

				// ロックされた場合、強制解除確認メッセージ出す
				if (showConfirmMessage("Q00097")) {
					request(null, "cancelExclude", companyCode, userCode);
					return true;
				} else {
					return false;
				}

			} catch (TException e) {
				// 処理なし
				return true;
			}
		}

		return true;

	}

	/**
	 * ユーザー認証
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param password
	 * @return セッションID
	 * @throws TWarningException
	 * @throws TException
	 */
	protected String authenticateUser(String companyCode, String userCode, String password) throws TWarningException,
		TException {

		if (ClientConfig.isWeb()) {
			return (String) this.request(null, "authenticateUser", companyCode, userCode, password);
		}

		this.request(UserAuthenticater.class, "authenticateUser", companyCode, userCode, password);

		return "";
	}

	/**
	 * ログインユーザー情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return ログインユーザー情報
	 * @throws TException
	 */
	protected User getUser(String companyCode, String userCode) throws TException {
		return (User) request(UserManager.class, "get", companyCode, userCode);
	}

	/**
	 * プリンタ名称を返す(プリンタ設定がある場合)
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return プリンタ名称
	 * @throws TException
	 */
	protected String getPrinterName(String companyCode, String userCode) throws TException {
		return (String) request(UserManager.class, "getPrinterName", companyCode, userCode);
	}

	/**
	 * ログイン会社情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return ログイン会社情報
	 * @throws TException
	 */
	protected Company getCompany(String companyCode) throws TException {
		return (Company) super.request(CompanyManager.class, "get", companyCode);
	}

	/**
	 * @see TController#request(Class, String, Object[])
	 */
	@Override
	public Object request(Class cls, String methodName, Object... arg) throws TException {
		isLoginServlet = cls == null;
		return super.request(cls, methodName, arg);
	}

	/**
	 * 通信先Servlet名
	 * 
	 * @return Servlet名
	 */
	@Override
	protected String getServletName() {
		return isLoginServlet ? "TLoginServlet" : super.getServletName();
	}

	/**
	 * パスワードチェック
	 * 
	 * @param company
	 * @param user
	 * @return false:NG
	 * @throws TException
	 */
	protected boolean checkPassword(Company company, User user) throws TException {

		// 有効期間チェック
		try {

			request(PasswordManager.class, "isInTerm", company, user);

		} catch (TPasswordTermComeThroughException e) {

			int remainDays = e.getDaysBeforePasswordTermComeThrough();

			// 期限未到来の場合、確認メッセージ
			if (remainDays > 0) {

				if (showConfirmMessage(loginView, "Q90003", Integer.toString(remainDays))) {

					// パスワード更新
					TPasswordCtrl ctrl = createPasswordCtrl();
					ctrl.start();

				}

				// 期限到来
			} else {

				showMessage(loginView, "I00026");

				// パスワード更新
				TPasswordCtrl ctrl = createPasswordCtrl();
				ctrl.start();

				if (!ctrl.isPasswordChanged()) {
					return false;
				}
			}

		} catch (TException e) {
			throw e;
		}

		return true;

	}

	/**
	 * パスワード変更コントローラ生成
	 * 
	 * @return パスワード変更コントローラ
	 */
	protected TPasswordCtrl createPasswordCtrl() {
		return new TPasswordCtrl(loginView);
	}

	/**
	 * ログイン情報取得
	 * 
	 * @return ログイン情報
	 */
	protected User getLoginInfo() {
		String path = SystemUtil.getAisSettingDir();
		User user = (User) FileUtil.getObject(path + File.separator + getSaveKey());
		return user;
	}

	/**
	 * ログイン情報保存
	 * 
	 * @param companyCode
	 * @param userCode
	 */
	protected void saveLoginInfo(String companyCode, String userCode) {

		User user = new User();
		user.setCompanyCode(companyCode);
		user.setCode(userCode);

		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(user, path + File.separator + getSaveKey());
	}

	/**
	 * インボイス制度対応関連情報の初期化
	 * 
	 * @throws TException
	 */
	protected void initInvoice() throws TException {
		if (TLoginInfo.getCompany() == null) {
			return;
		}
		if (!TLoginInfo.getCompany().isCMP_INV_CHK_FLG()) {
			// インボイス制度未使用
			return;
		}
		// ログイン会社の情報取得
		String companyCode = TLoginInfo.getCompany().getCode();
		{
			// 自動仕訳科目
			int[] types = new int[] { AutoJornalAccountType.PAY_TAX.value, AutoJornalAccountType.RECEIVE_TAX.value };
			List<AutoJornalAccount> list = (List<AutoJornalAccount>) request(SlipManager.class,
				"getAutoJornalAccounts", companyCode, types);
			Map<Integer, AutoJornalAccount> map = new LinkedHashMap<Integer, AutoJornalAccount>();
			if (list != null) {
				for (AutoJornalAccount bean : list) {
					map.put(bean.getKind(), bean);
				}
			}
			TLoginInfo.setAutoJournalMap(map);
		}
	}

	/**
	 * 保存キーの取得
	 * 
	 * @return 保存キー
	 */
	protected String getSaveKey() {

		return "login_" + getClientSaveKey() + ".log";
	}

	/**
	 * @return クライアントへ保持する時、当該製品の識別子
	 */
	public static String getClientSaveKey() {
		String url = jp.co.ais.trans.common.config.ClientConfig.getBaseAddress();
		url = url.replaceAll("/", "_");
		url = url.replaceAll(":", "_");
		url = url.replaceAll("\\\\", "");

		if (Util.isNullOrEmpty(url)) {
			url = ClientConfig.getTitle();
		}

		return url;
	}

}

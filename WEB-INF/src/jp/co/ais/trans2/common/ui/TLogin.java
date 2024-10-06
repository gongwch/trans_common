package jp.co.ais.trans2.common.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * ログイン画面
 * 
 * @author AIS
 */
public class TLogin extends TUndecoratedFrame {

	/** 背景ラベル */
	public JLabel backGroundImageLabel;

	/** 情報 */
	public TLabel lblRemark;

	/** 会社コード */
	public TLabelField txCompanyCode;

	/** ユーザーコード */
	public TLabelField txUserCode;

	/** パスワード */
	public TLabelPasswordField txPassword;

	/** プログレスバー */
	public JProgressBar progressBar;

	/** ログインボタン */
	public TButton btnLogin;

	/** クリアボタン */
	public TButton btnClear;

	/** 閉じるボタン */
	public TButton btnClose;

	/**
	 * 初期処理
	 */
	public void init() {

		// コンポーネント初期化
		initComponents();

		// コンポーネント配置
		allocateComponents();

		// タブ順設定
		setTabIndex();

		// フォント色設定
		initFontColor();

		initFrame();

	}

	/**
	 * コンポーネントを初期化する
	 */
	public void initComponents() {
		backGroundImageLabel = new JLabel(getBackGroundImage());
		lblRemark = new TLabel();
		txCompanyCode = new TLabelField();
		txUserCode = new TLabelField();
		txPassword = new TLabelPasswordField();
		progressBar = new JProgressBar();
		btnLogin = new TButton();
		btnClear = new TButton();
		btnClose = new TButton();
	}

	/**
	 * コンポーネントを配置する
	 */
	public void allocateComponents() {

		setLayout(new GridBagLayout());

		// 背景
		backGroundImageLabel.setLayout(null);
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		add(backGroundImageLabel, gc);

		int x = 5;
		int y = 0;

		// メモ情報
		lblRemark.setSize(getWidth(), 20);
		lblRemark.setLocation(x, y);
		backGroundImageLabel.add(lblRemark);

		int labelWidth = getIntProperty("login.input.label.width", 200);
		int startInputX = getIntProperty("login.input.start.x", 180);
		int startInputY = getIntProperty("login.input.start.y", 110);
		int startButtonX = getIntProperty("login.button.start.x", 155);
		int startButtonY = getIntProperty("login.button.start.y", 210);

		String lblCompanyWord = getStringProperty("login.input.company.label", "Company Code");
		String lblUserWord = getStringProperty("login.input.user.label", "User Code");
		String lblPasswordWord = getStringProperty("login.input.pw.label", "Password");

		String btnLoginWord = getStringProperty("login.button.login.label", "LOGIN");
		String btnClearWord = getStringProperty("login.button.clear.label", "CLEAR");
		String btnCloseWord = getStringProperty("login.button.close.label", "CLOSE");

		x = startInputX;
		y = startInputY;

		// 会社コード
		txCompanyCode.getLabel().setForeground(Color.white);
		txCompanyCode.getLabel().setFont(txCompanyCode.getLabel().getFont().deriveFont(Font.BOLD));
		txCompanyCode.setOpaque(false);
		txCompanyCode.getLabel().setText(lblCompanyWord);
		txCompanyCode.setImeMode(false);
		txCompanyCode.setMaxLength(10);
		txCompanyCode.setSize(labelWidth, 20);
		txCompanyCode.setFieldSize(80);
		txCompanyCode.setLocation(x, y);
		backGroundImageLabel.add(txCompanyCode);

		y += 30;

		// ユーザーコード
		txUserCode.getLabel().setForeground(Color.white);
		txUserCode.getLabel().setFont(txUserCode.getLabel().getFont().deriveFont(Font.BOLD));
		txUserCode.setOpaque(false);
		txUserCode.getLabel().setText(lblUserWord);
		txUserCode.setImeMode(false);
		txUserCode.setMaxLength(10);
		txUserCode.setSize(labelWidth, 20);
		txUserCode.setFieldSize(80);
		txUserCode.setLocation(x, y);
		backGroundImageLabel.add(txUserCode);

		y += 30;

		// パスワード
		txPassword.getLabel().setForeground(Color.white);
		txPassword.getLabel().setFont(txPassword.getLabel().getFont().deriveFont(Font.BOLD));
		txPassword.setOpaque(false);
		txPassword.getLabel().setText(lblPasswordWord);
		txPassword.setSize(labelWidth, 20);
		txPassword.setFieldSize(80);
		txPassword.setLocation(x, y);
		backGroundImageLabel.add(txPassword);

		x = startButtonX;
		y = startButtonY;

		// ログインボタン
		btnLogin.setOpaque(false);
		btnLogin.setText(btnLoginWord);
		btnLogin.setSize(20, 80);
		btnLogin.setLocation(x, y);
		backGroundImageLabel.add(btnLogin);

		x += btnLogin.getWidth() + 10;

		// クリアボタン
		btnClear.setOpaque(false);
		btnClear.setText(btnClearWord);
		btnClear.setSize(20, 80);
		btnClear.setLocation(x, y);
		backGroundImageLabel.add(btnClear);

		x += btnClear.getWidth() + 10;

		// 閉じるボタン
		btnClose.setOpaque(false);
		btnClose.setText(btnCloseWord);
		btnClose.setSize(20, 80);
		btnClose.setLocation(x, y);
		backGroundImageLabel.add(btnClose);

		x = btnLogin.getX();
		y += 25;
		int w = btnClose.getX() + btnClose.getWidth() - x;

		TGuiUtil.setComponentSize(progressBar, w, 20);
		progressBar.setVisible(false);
		progressBar.setStringPainted(true);
		progressBar.setLocation(x, y);
		backGroundImageLabel.add(progressBar);

	}

	/**
	 * tab順を設定する
	 */
	public void setTabIndex() {
		int i = 0;
		txCompanyCode.setTabControlNo(i++);
		txUserCode.setTabControlNo(i++);
		txPassword.setTabControlNo(i++);
		btnLogin.setTabControlNo(i++);
		btnLogin.setEnterFocusable(true);
		btnClear.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * 背景画像を取得する
	 * 
	 * @return 背景画像
	 */
	protected Icon getBackGroundImage() {
		Icon logo = ResourceUtil.getImage(this.getClass(), getImagePath());
		return logo;
	}

	/**
	 * 背景画像を取得する
	 * 
	 * @return 背景画像
	 */
	@Override
	protected String getImagePath() {
		String backGroundImageFilePath = "login.jpg";

		try {
			backGroundImageFilePath = ClientConfig.getProperty("login.background.image");
		} catch (Throwable ex) {
			// 処理なし
		}
		return "images/" + backGroundImageFilePath;
	}

	/**
	 * ラベルフォント色設定
	 */
	protected void initFontColor() {
		try {
			String[] forecolor = ClientConfig.getProperty("login.label.forecolor").split(",");
			if (!Util.isNullOrEmpty(forecolor)) {
				Color color = Util.toColor(forecolor);
				txCompanyCode.getLabel().setForeground(color);
				txUserCode.getLabel().setForeground(color);
				txPassword.getLabel().setForeground(color);
			}
		} catch (Throwable ex) {
			// 処理なし
		}

	}

	/**
	 * 設定値取得
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 設定値
	 */
	protected String getStringProperty(String key, String defaultValue) {
		String value = defaultValue;
		try {
			String valueProperty = ClientConfig.getProperty(key);
			if (!Util.isNullOrEmpty(valueProperty)) {
				value = valueProperty;
			}
		} catch (Throwable ex) {
			// 処理なし
		}
		return value;
	}

	/**
	 * 設定値取得
	 * 
	 * @param key
	 * @param defaultValue
	 * @return 設定値
	 */
	protected int getIntProperty(String key, int defaultValue) {
		int value = defaultValue;
		try {
			String valueProperty = ClientConfig.getProperty(key);
			if (!Util.isNullOrEmpty(valueProperty)) {
				value = Integer.parseInt(valueProperty);
			}
		} catch (Throwable ex) {
			// 処理なし
		}
		return value;
	}

}

package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * プログラムウインドウ <br>
 * （多重ログイン専用）
 * 
 * @author hosoya
 */
public class TAppletWindow extends TPanel implements Runnable {

	/** フレームに表示するベースパネル */
	protected TPanel basePanel;

	/** ヘッダー表示パネル */
	protected TPanel headerPanel;

	/** 中央パネル */
	public TPanel detailPanel;

	/** 会社設定色 表示ラインパネル */
	protected TPanel footerPanel;

	/** プログラム名表示ラベル */
	public TLabel lblProgramName;

	/** 時刻表示ラベル */
	TLabel lblTime;

	/** ログアウトボタン */
	TButton btnLogout;

	/** パスワード変更ボタン */
	TButton btnPassword;

	/** メニュー表示タブペイン */
	JTabbedPane tabbedPane;

	/** 時刻表示スレッド */
	volatile Thread timethread;

	/** 時刻表示切替用String */
	private String strTime;

	/** 時刻表示切替用Date */
	private Date date;

	/** システムロゴ */
	Icon logo;

	/**
	 * コンストラクタ
	 */
	public TAppletWindow() {

		// パネル初期設定
		initComponents();

		// 時間の変更
		timethread = new Thread(this);
		timethread.start();
	}

	/**
	 * パネル初期設定
	 */
	protected void initComponents() {

		this.setLayout(new GridLayout(1, 1));

		// 基盤パネル
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// ヘッダパネル
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // 詳細構築

		headerPanel.setSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), logo.getIconHeight() + 10));

		// 中央パネル
		detailPanel = new TPanel();
		detailPanel.setLayout(new BorderLayout(0, 0));
		detailPanel.setBackground(new Color(240, 240, 240));

		// フッタ
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// フッタ 会社設定色表現ライン設定
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		// パネル貼り付け
		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(detailPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);
		this.add(basePanel);
	}

	/**
	 * ヘッダー設定
	 */
	protected void makeHeaderPanel() {

		TPanel panel = new TPanel();
		panel.setLayout(new BorderLayout(10, 5));
		panel.setBackground(new Color(255, 255, 255));

		// イメージラベルの設定
		TLabel lblLogo = new TLabel();
		logo = ResourceUtil.getImage(TAppletMain.class, "images/Series" + ClientConfig.getImageSuffix() + ".png");
		lblLogo.setIcon(logo);
		lblLogo.setPreferredSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMinimumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMaximumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));

		// 情報パネル
		TPanel infoPanel = new TPanel();
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.setBackground(new Color(255, 255, 255));

		// ユーザー情報の取得
		TUserInfo userInfo = TClientLoginInfo.getInstance();

		// プログラム名の表示
		lblProgramName = new TLabel();
		lblProgramName.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblProgramName, 2);

		// 会社名の表示
		TLabel lblCompanyName = new TLabel();
		lblCompanyName.setText(userInfo.getCompanyInfo().getCompanyName());
		lblCompanyName.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblCompanyName, 2);

		// ユーザー情報の表示
		TLabel lblUserInfo = new TLabel();
		lblUserInfo.setText(userInfo.getDepartmentShortName() + "  /   " + userInfo.getUserName());
		lblUserInfo.setText(userInfo.getDepartmentShortName() + "  /   " + userInfo.getUserName());
		lblUserInfo.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblUserInfo, 2);

		// 現在時刻の表示
		lblTime = new TLabel();
		lblTime.setText(DateUtil.toYMDHMString(new Date()));
		lblTime.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblTime, 2);

		infoPanel.add(lblCompanyName);
		infoPanel.add(lblProgramName);
		infoPanel.add(lblUserInfo);
		infoPanel.add(lblTime);

		// ボタンパネル
		TPanel buttonPanel = new TPanel();
		FlowLayout fLayout = new FlowLayout(5);
		fLayout.setAlignment(FlowLayout.RIGHT);
		buttonPanel.setLayout(fLayout);
		buttonPanel.setBackground(new Color(255, 255, 255));

		buttonPanel.setSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setPreferredSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setMinimumSize(new Dimension(80, buttonPanel.getHeight()));
		buttonPanel.setMaximumSize(new Dimension(80, buttonPanel.getHeight()));

		// パスワード変更ボタンの設定
		btnPassword = new TButton();
		btnPassword.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/pass.png"));
		btnPassword.setPreferredSize(new Dimension(30, 30));
		btnPassword.setMinimumSize(new Dimension(30, 30));
		btnPassword.setMaximumSize(new Dimension(30, 30));
		btnPassword.setBackground(new Color(255, 255, 255));
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.setVisible(false); // レイアウトが崩れないようにボタンは隠して残す

		// ログアウトボタンの設定
		btnLogout = new TButton();
		btnLogout.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/logout.png"));
		btnLogout.setPreferredSize(new Dimension(30, 30));
		btnLogout.setMinimumSize(new Dimension(30, 30));
		btnLogout.setMaximumSize(new Dimension(30, 30));
		btnLogout.setBackground(new Color(255, 255, 255));
		btnLogout.setBorderPainted(false);
		btnLogout.setOpaque(false);
		btnLogout.setVisible(false);// レイアウトが崩れないようにボタンは隠して残す

		buttonPanel.add(btnPassword);
		buttonPanel.add(btnLogout);

		// 会社設定色表現ライン設定
		TPanel pnlLine = new TPanel();
		pnlLine.setPreferredSize(new Dimension(pnlLine.getWidth(), 3));
		pnlLine.setMinimumSize(new Dimension(pnlLine.getWidth(), 3));
		pnlLine.setMaximumSize(new Dimension(pnlLine.getWidth(), 3));

		// 背景色の取得
		int[] RGBcode = Util.toRGBColorCode(userInfo.getCompanyInfo().getForeColor());
		pnlLine.setBackground(new Color(RGBcode[0], RGBcode[1], RGBcode[2]));

		// ヘッダーパネルの設定
		panel.add(lblLogo, BorderLayout.WEST);
		panel.add(infoPanel, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.EAST);

		headerPanel.add(panel, BorderLayout.CENTER);
		headerPanel.add(pnlLine, BorderLayout.SOUTH);

	}

	/**
	 * 時刻表示スレッドをスタートする
	 */
	public void run() {
		Thread me = Thread.currentThread();
		while (timethread == me) {
			try {
				Thread.sleep(1000);
				changeTime();
			} catch (InterruptedException e) {
				// 通知無し
			}
		}
	}

	/**
	 * TOP画面ヘッダの時刻を最新に更新
	 */
	private void changeTime() {
		date = new Date();
		// Get the date to print at the bottom
		strTime = DateUtil.toYMDHMString(date);

		if (lblTime != null) {
			lblTime.setText(strTime);
		}
	}

}

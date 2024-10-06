package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * TAppletを継承した、main applet.
 */
public class TAppletMain extends TApplet implements Runnable {

	/** メインApplet */
	public static TAppletMain instance = null;

	/** コントロールクラス */
	protected TAppletMainCtrl ctrl;

	/** アプレットに表示するベースパネル */
	protected TPanel basePanel;

	/** ヘッダー表示パネル */
	protected TPanel headerPanel;

	/** 中央パネル */
	protected TPanel detailPanel;

	/** 切替パネル */
	protected TPanel rdoPanel;

	/** 各プログラム画面を表示するメインパネル */
	protected TPanel mainPanel;

	/** 会社設定色 表示ラインパネル */
	protected TPanel footerPanel;

	/** メニューとパネル間のスプリッドPane */
	protected JSplitPane spPanel;

	/** ロゴラベル */
	protected TLabel lblLogo;

	/** 会社情報ラベル */
	protected TLabel lblCompanyName;

	/** ユーザ情報ラベル */
	protected TLabel lblUserInfo;

	/** プログラム名表示ラベル */
	protected TLabel lblProgramName;

	/** 時刻表示ラベル */
	protected TLabel lblTime;

	/** ログアウトボタン */
	protected TButton btnLogout;

	/** パスワード変更ボタン */
	protected TButton btnPassword;

	/** メニュー表示タブペイン */
	protected JTabbedPane tabbedPane;

	/** 時刻表示スレッド */
	private volatile Thread timethread;

	/** 時刻表示切替用String */
	private String strTime;

	/** 時刻表示切替用Date */
	private Date date;

	/** システムロゴ */
	protected Icon logo;

	/** プログラム 表示切替グループ */
	protected ButtonGroup rdoPrgGroup;

	/** プログラム フレーム表示ボタン */
	protected JRadioButton rdoPrgFrame;

	/** プログラム ウインドウ表示ボタン */
	protected JRadioButton rdoPrgWind;

	/** ヘッダカラー */
	protected Color headerColor = Color.WHITE;

	/** 画面横サイズ */
	protected int width;

	/** 画面縦サイズ */
	protected int height;

	/** メニューの横サイズ */
	protected int menuWidthSize = 200;

	/** プログラム 表示切替の縦サイズ */
	private int prgHeightSize = 20;

	/** プログラム 表示切替の縦サイズ */
	private int prgWidthSize = menuWidthSize - 3;

	/**
	 * 初期設定とLook & Feel設定
	 */
	public void init() {

		try {
			System.gc();

			// 画面構築
			TUIManager.setUI(this);

			// Appletサイズ指定(AppletViewer用)
			this.width = ClientConfig.getAppletWidht();
			this.height = ClientConfig.getAppletHeight();

			// メニューサイズ指定
			String menuSize = getParameter("menuSize");
			if (!Util.isNullOrEmpty(menuSize)) {
				menuWidthSize = Integer.parseInt(menuSize);
				prgWidthSize = menuWidthSize - 3;
			}

			EventQueue.invokeAndWait(new Runnable() {

				public void run() {
					try {
						String sid = getParameter("trans.scd");
						String kaiCode = getParameter("trans.ccd");
						String usrCode = getParameter("trans.ucd");
						String url = getParameter("trans.url");

						ClientLogger.info(sid + ", " + kaiCode + ", " + usrCode);
						ClientLogger.info(url);

						ClientConfig.setBaseAddress(url);

						initControl(sid, kaiCode, usrCode);
					} catch (Throwable e) {
						e.printStackTrace();
					}
				}
			});

			showStatus("");

			instance = this;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * コントロールの呼び出し
	 * 
	 * @param sid セッションID
	 * @param kaiCode ログイン会社コード
	 * @param usrCode ログインユーザコード
	 */
	public void initControl(String sid, String kaiCode, String usrCode) {
		ctrl = new TAppletMainCtrl(this);
		ctrl.init(sid, kaiCode, usrCode);
	}

	/**
	 * 別スレッドで時刻表示を始動
	 */
	public void start() {
		timethread = new Thread(this);
		timethread.start();
	}

	/**
	 * パネル初期設定
	 * 
	 * @param isMultiWindowMode true:マルチウィンドウモード
	 */
	public void initComponents(boolean isMultiWindowMode) {

		this.setSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		// 基盤パネル
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// ヘッダパネル
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // 詳細構築

		headerPanel.setSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), 43));

		GridBagConstraints gridBagConstraints;

		// 中央パネル
		detailPanel = new TPanel();
		detailPanel.setLayout(new GridBagLayout());

		// 切替パネル
		rdoPanel = new TPanel();
		rdoPanel.setLayout(new GridBagLayout());
		rdoPanel.setMaximumSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setMinimumSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setPreferredSize(new Dimension(prgWidthSize, prgHeightSize));
		rdoPanel.setBorder(BorderFactory.createEtchedBorder());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(1, 1, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.NORTHWEST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		detailPanel.add(rdoPanel, gridBagConstraints);

		// プログラム表示切替
		rdoPrgFrame = new JRadioButton("Frame", true);
		rdoPrgWind = new JRadioButton("Window", false);
		rdoPrgGroup = new ButtonGroup();
		rdoPrgGroup.add(rdoPrgFrame);
		rdoPrgGroup.add(rdoPrgWind);

		// プログラム表示 フレーム
		rdoPrgFrame.setMaximumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgFrame.setMinimumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgFrame.setPreferredSize(new Dimension(90, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.WEST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		rdoPanel.add(rdoPrgFrame, gridBagConstraints);

		// プログラム表示 ウインドウ
		rdoPrgWind.setMaximumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgWind.setMinimumSize(new Dimension(90, prgHeightSize - 4));
		rdoPrgWind.setPreferredSize(new Dimension(90, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, -4, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.EAST;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		rdoPanel.add(rdoPrgWind, gridBagConstraints);

		// メニュータブペイン
		tabbedPane = new JTabbedPane();
		tabbedPane.setMaximumSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.setMinimumSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.setPreferredSize(new Dimension(menuWidthSize, tabbedPane.getHeight()));
		tabbedPane.addChangeListener(ctrl);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setTabPlacement(SwingConstants.LEFT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

		if (isMultiWindowMode) {
			gridBagConstraints.insets = new Insets(prgHeightSize, 0, 0, 0);
		} else {
			rdoPanel.setVisible(false);
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		}

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 1;
		detailPanel.add(tabbedPane, gridBagConstraints);

		// プログラムパネル
		mainPanel = new TPanel();
		mainPanel.setLayout(new GridBagLayout());
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		detailPanel.add(mainPanel, gridBagConstraints);

		// フッタ
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// 会社設定色表現ライン設定
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(detailPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);

		this.getContentPane().add(basePanel);
	}

	/**
	 * パネル初期設定(スプリットパネル版)
	 * 
	 * @param isMultiWindowMode true:マルチウィンドウモード
	 */
	public void initComponentsSplit(boolean isMultiWindowMode) {
		this.setSize(new Dimension(width, height));
		this.setMinimumSize(new Dimension(width, height));

		// 基盤パネル
		basePanel = new TPanel();
		basePanel.setLayout(new BorderLayout(0, 0));
		basePanel.setBackground(new Color(240, 240, 240));

		// ヘッダパネル
		headerPanel = new TPanel();
		headerPanel.setLayout(new BorderLayout(5, 0));
		headerPanel.setBackground(new Color(255, 255, 255));

		makeHeaderPanel(); // 詳細構築
		headerPanel.setSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setPreferredSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMinimumSize(new Dimension(headerPanel.getWidth(), 43));
		headerPanel.setMaximumSize(new Dimension(headerPanel.getWidth(), 43));

		GridBagConstraints gridBagConstraints;

		// 中央パネル
		detailPanel = new TPanel();
		detailPanel.setLayout(new GridBagLayout());

		// 切替パネル
		rdoPanel = new TPanel();
		rdoPanel.setLayout(new GridBagLayout());
		rdoPanel.setMaximumSize(new Dimension(menuWidthSize, 20));
		rdoPanel.setMinimumSize(new Dimension(0, 20));
		rdoPanel.setPreferredSize(new Dimension(menuWidthSize, 20));
		rdoPanel.setBorder(BorderFactory.createEtchedBorder());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;
		detailPanel.add(rdoPanel, gridBagConstraints);

		// プログラム表示切替
		rdoPrgFrame = new JRadioButton("Frame", true);
		rdoPrgWind = new JRadioButton("Window", false);
		rdoPrgGroup = new ButtonGroup();
		rdoPrgGroup.add(rdoPrgFrame);
		rdoPrgGroup.add(rdoPrgWind);

		// プログラム表示 フレーム
		rdoPrgFrame.setMaximumSize(new Dimension(80, prgHeightSize - 4));
		rdoPrgFrame.setMinimumSize(new Dimension(0, prgHeightSize - 4));
		rdoPrgFrame.setPreferredSize(new Dimension(80, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;

		rdoPanel.add(rdoPrgFrame, gridBagConstraints);

		// プログラム表示 ウインドウ
		rdoPrgWind.setMaximumSize(new Dimension(80, prgHeightSize - 4));
		rdoPrgWind.setMinimumSize(new Dimension(0, prgHeightSize - 4));
		rdoPrgWind.setPreferredSize(new Dimension(80, prgHeightSize - 4));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 0;

		rdoPanel.add(rdoPrgWind, gridBagConstraints);

		// メニュータブペイン
		tabbedPane = new JTabbedPane();
		tabbedPane.setMaximumSize(new Dimension(menuWidthSize, tabbedPane.getHeight() - 5));
		tabbedPane.setMinimumSize(new Dimension(0, tabbedPane.getHeight() - 5));
		tabbedPane.setPreferredSize(new Dimension(menuWidthSize, tabbedPane.getHeight() - 5));
		tabbedPane.addChangeListener(ctrl);
		tabbedPane.setTabLayoutPolicy(JTabbedPane.WRAP_TAB_LAYOUT);
		tabbedPane.setTabPlacement(SwingConstants.LEFT);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;

		if (!isMultiWindowMode) {
			rdoPanel.setVisible(false);
			gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		}

		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		detailPanel.add(tabbedPane, gridBagConstraints);

		// プログラムパネル
		mainPanel = new TPanel();
		mainPanel.setPreferredSize(new Dimension(width - menuWidthSize - 110, prgHeightSize));
		mainPanel.setMaximumSize(new Dimension(width, prgHeightSize));
		mainPanel.setMinimumSize(new Dimension(0, prgHeightSize));
		mainPanel.setLayout(new GridBagLayout());

		spPanel = new JSplitPane();
		spPanel = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, detailPanel, mainPanel);
		spPanel.setContinuousLayout(true);
		spPanel.setDividerSize(6);
		spPanel.setDividerLocation(menuWidthSize);
		InputMap im = spPanel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).getParent();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0), null);
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0), null);

		// フッタ
		footerPanel = new TPanel();
		footerPanel.setPreferredSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMinimumSize(new Dimension(footerPanel.getWidth(), 3));
		footerPanel.setMaximumSize(new Dimension(footerPanel.getWidth(), 3));

		// 会社設定色表現ライン設定
		int[] rgbCode = Util.toRGBColorCode(TClientLoginInfo.getInstance().getCompanyInfo().getForeColor());
		footerPanel.setBackground(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));

		basePanel.add(headerPanel, BorderLayout.NORTH);
		basePanel.add(spPanel, BorderLayout.CENTER);
		basePanel.add(footerPanel, BorderLayout.SOUTH);

		this.getContentPane().add(basePanel);
	}

	/**
	 * ヘッダー設定
	 */
	protected void makeHeaderPanel() {

		// ヘッダーパネル背景色変更
		if (!Util.isNullOrEmpty(ClientConfig.getHeaderBackColor())) {
			String[] code = ClientConfig.getHeaderBackColor().split(",");
			headerColor = new Color(Integer.parseInt(code[0]), Integer.parseInt(code[1]), Integer.parseInt(code[2]));
		}

		TPanel panel = new TPanel();
		panel.setLayout(new BorderLayout(10, 5));
		panel.setBackground(headerColor);

		// イメージラベルの設定
		lblLogo = new TLabel();
		logo = ResourceUtil.getImage(TAppletMain.class, "images/Series" + ClientConfig.getImageSuffix() + ".png");
		lblLogo.setIcon(logo);
		lblLogo.setPreferredSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMinimumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));
		lblLogo.setMaximumSize(new Dimension(logo.getIconWidth() + 5, logo.getIconHeight()));

		// 情報パネル
		TPanel infoPanel = new TPanel();
		infoPanel.setLayout(new GridLayout(2, 2));
		infoPanel.setBackground(headerColor);

		// ユーザー情報の取得
		TUserInfo userInfo = TClientLoginInfo.getInstance();

		// プログラム名の表示
		lblProgramName = new TLabel();
		lblProgramName.setHorizontalAlignment(SwingConstants.RIGHT);
		TUIManager.addFontSize(lblProgramName, 2);

		// 会社名の表示
		lblCompanyName = new TLabel();
		lblCompanyName.setText(userInfo.getCompanyInfo().getCompanyName());
		lblCompanyName.setHorizontalAlignment(SwingConstants.LEFT);
		TUIManager.addFontSize(lblCompanyName, 2);

		// ユーザー情報の表示
		lblUserInfo = new TLabel();
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
		buttonPanel.setBackground(headerColor);

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
		btnPassword.setBackground(headerColor);
		btnPassword.setBorderPainted(false);
		btnPassword.setOpaque(false);
		btnPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.openDialog();
			}
		});

		// ログアウトボタンの設定
		btnLogout = new TButton();
		btnLogout.setIcon(ResourceUtil.getImage(TAppletMain.class, "images/logout.png"));
		btnLogout.setPreferredSize(new Dimension(30, 30));
		btnLogout.setMinimumSize(new Dimension(30, 30));
		btnLogout.setMaximumSize(new Dimension(30, 30));
		btnLogout.setBackground(headerColor);
		btnLogout.setBorderPainted(false);
		btnLogout.setOpaque(false);
		btnLogout.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.logout();
			}
		});

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
	 * Appletを閉じる際に呼ばれる.
	 * 
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void destroy() {
		super.destroy();

		ClientLogger.debug("Applet destroy");

		this.ctrl.logoutForClose();

		System.gc();
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
	public void changeTime() {
		date = new Date();
		// Get the date to print at the bottom
		strTime = DateUtil.toYMDHMString(date);

		if (lblTime != null) {
			lblTime.setText(strTime);
		}
	}

	/**
	 * 親フレームの取得
	 * 
	 * @return 親フレーム
	 */
	public Frame getParentFrame() {
		for (Container p = this.getParent(); p != null; p = p.getParent()) {
			if (p instanceof Frame) {
				return (Frame) p;
			}
		}
		return null;
	}
}
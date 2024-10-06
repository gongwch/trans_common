package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import javax.print.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.tree.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * メイン画面のコントローラ
 * 
 * @author AIS
 */
public class TMainCtrl extends TController {

	/** true:メニュータブの順序を保存する */
	protected static boolean IS_SAVE_MENU = ClientConfig.isFlagOn("trans.ui.menu.save.status");

	/** インスタンス */
	protected static TMainCtrl instance = null;

	/** 最小メインタイトルサイズ */
	public static int minMenuSize = 45;

	/** メイン画面 */
	public TMain mainView;

	/** 現在起動中のプログラム */
	public Map<String, TAppletClientBase> selectedProgram;

	/** 現在起動中のフレーム形式のプログラム */
	public Map<String, TPanelBusiness> frameProgram;

	/** 現在起動中のフレーム */
	public Map<String, TFrame> frames;

	/** メインタイトルサイズ */
	protected int maxMenuTitleLength = 0;

	/** メインタイトル補足用ストリング */
	protected String maxMenuTitleSpaces = "";

	/** メニュー幅均一にするかどうか(英語のみ)、true:幅均一にする */
	protected boolean isMenuWidthFixed = false;

	/** プログラムコードを表示するかどうか、true:表示する */
	protected boolean isShowProgramCode = false;

	/** メニューロード形式、true:メニューマスタ、false:プログラムマスタ */
	public boolean isUseMenuMaster = false;

	/** フレームのスクロール形式、true:スクロール追加、false:スクロールなし */
	protected boolean isUseScroll = false;

	/** カスタマイズフレームの保存 */
	public List<TFrame> customerizeFrames = new ArrayList<TFrame>();

	/** アイコン */
	public BufferedImage icon = null;

	/** PDF直接印刷設定があるかどうか、true:PDF直接印刷する */
	protected boolean isDirectPrint = false;

	/** 初期化用Instanceリスト */
	public List<TMainInitialInterface> instanceList;

	/** メニューTAB押下時の連動処理マップ */
	public Map<String, List<TMainInitialInterface>> tabLinkInstanceMap;

	/** 各種保存マップ */
	public Map<String, Object> saveMap;

	/**
	 * 今メイン画面のインスタンスの取得
	 * 
	 * @return 今メイン画面のインスタンス
	 */
	public static TMainCtrl getInstance() {
		return instance;
	}

	/**
	 * @param icon アイコン指定
	 */
	public void setIcon(BufferedImage icon) {
		this.icon = icon;
	}

	/**
	 * カスタマイズフレームの保存
	 * 
	 * @param frame
	 */
	public void addFrame(TFrame frame) {
		customerizeFrames.add(frame);
	}

	/**
	 * カスタマイズフレームの保存
	 * 
	 * @param frame
	 */
	public void removeFrame(TFrame frame) {
		customerizeFrames.remove(frame);
	}

	/**
	 * カスタマイズフレームの保存
	 */
	public void clearCustomerizeFrames() {
		customerizeFrames.clear();
	}

	/**
	 * プログラムの開始。メイン画面を生成する。
	 */
	@Override
	public void start() {

		try {
			// メイン画面初期化クラスの初期処理
			doMainInit();

			// メイン画面を生成し、初期化する
			mainView = createMainView();
			initMainView();

			selectedProgram = new TreeMap<String, TAppletClientBase>();
			frameProgram = new TreeMap<String, TPanelBusiness>();
			frames = new TreeMap<String, TFrame>();

			// インスタンス持つ
			instance = this;

			// メイン画面初期化クラスの開始処理
			doMainAfterCreate(instanceList);

			// メイン画面を表示
			mainView.setExtendedState(Frame.MAXIMIZED_BOTH);
			mainView.setVisible(true);

		} catch (Exception e) {
			// 致命的エラー。アプリケーションを閉じる
			e.printStackTrace();
		}

	}

	/**
	 * メイン画面初期化クラスリスト
	 * 
	 * @throws Exception
	 */
	protected void doMainInit() throws Exception {

		try {
			String fileName = getSaveFileKey();
			String path = SystemUtil.getAisSettingDir();
			Map<String, Object> map = (Map<String, Object>) FileUtil.getObject(path + File.separator + fileName);
			saveMap = map;

		} catch (Throwable ex) {
			// エラーなし
		}

		if (saveMap == null) {
			saveMap = new LinkedHashMap<String, Object>();
		}

		List<Class> clzList = getMainInitialClasses();
		instanceList = new ArrayList<TMainInitialInterface>();

		if (!clzList.isEmpty()) {
			// 初期処理あり
			System.out.println("main initial");

			for (Class clz : clzList) {
				Object obj = clz.newInstance();
				if (obj instanceof TMainInitialInterface) {
					TMainInitialInterface clazz = (TMainInitialInterface) obj;
					System.out.println(clazz.getName());
					instanceList.add(clazz);

					// 初期化処理
					clazz.init();
				}
			}
		}

		// 初期化
		tabLinkInstanceMap = new LinkedHashMap<String, List<TMainInitialInterface>>();

	}

	/**
	 * メイン画面表示後初期化
	 * 
	 * @param instanceList
	 */
	public static void doMainAfterCreate(List<TMainInitialInterface> instanceList) {
		// ログイン時に初期処理

		if (!instanceList.isEmpty()) {
			// 初期処理あり

			for (TMainInitialInterface clazz : instanceList) {

				// 初期化処理
				clazz.afterCreate();
			}
		}
	}

	/**
	 * @return メイン画面初期化クラス一覧取得
	 */
	protected static List<Class> getMainInitialClasses() {
		String k = "trans.main.init.class.";
		return getMainInitialClasses(k);
	}

	/**
	 * @param key
	 * @return メイン画面指定キーの初期化クラス一覧取得
	 */
	protected static List<Class> getMainInitialClasses(String key) {
		List<Class> list = TLoginCtrl.getInitialClasses(key);
		return list;
	}

	/**
	 * メイン画面のファクトリ
	 * 
	 * @return メイン画面
	 */
	protected TMain createMainView() {
		return new TMain(instanceList);
	}

	/**
	 * メイン画面の初期化
	 * 
	 * @throws Exception
	 */
	protected void initMainView() throws Exception {

		// 初期最大化のため、パック
		if (icon != null) {
			mainView.setIconImage(icon);
		}
		mainView.pack();

		mainView.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

		// タイトル
		if (ClientConfig.isFlagOn("trans.title.not.show.company.code")) {
			mainView.setTitle(ClientConfig.getTitle());
		} else {
			mainView.setTitle(TLoginInfo.getCompany().getCode() + " " + ClientConfig.getTitle());
		}

		// サイズ指定
		int width = ClientConfig.getMainViewWidth();
		int height = ClientConfig.getMainViewHeight();
		mainView.setSize(width, height);

		// メニュー幅均一のフラグ設定
		isMenuWidthFixed = ClientConfig.isFlagOn("trans.menu.width.fixed") && getUser().isEnglish();

		// プログラムコードを表示するかどうか、true:表示する
		isShowProgramCode = ClientConfig.isFlagOn("show.prg.code");

		// メニューロード形式、true:メニューマスタ、false:プログラムマスタ
		isUseMenuMaster = ClientConfig.isFlagOn("trans.menu.use");

		// フレームのスクロール形式、true:スクロール追加、false:スクロールなし
		isUseScroll = ClientConfig.isFlagOn("trans.mainframe.scroll.use");

		// PDF直接印刷、true:する、false:しない
		isDirectPrint = ClientConfig.isFlagOn("trans.pdf.direct.print");

		if (isUseScroll) {
			System.out.println("trans.mainframe.scroll.use is true");
		}

		// メイン画面のヘッダー初期化
		initMainViewHeader();

		// プログラムツリー生成
		createMenuTree();

		// イベント定義
		initMainViewEvent();

		// Windows風の場合は配色変更ボタンを無効
		if (LookAndFeelType.MINT != TUIManager.getLookAndFeelType()) {
			mainView.pnlLf.setVisible(false);
		}

		// GlassPane
		mainView.setGlassPane(new LockingGlassPane());
		mainView.getGlassPane().setVisible(false);

		// 英語のみメニュータブの表示を切替
		if (getUser().isEnglish()) {
			mainView.menuTab.setTabPlacement(SwingConstants.NORTH);
			mainView.btnType1.setVisible(false);
			mainView.btnType2.setVisible(false);
			mainView.btnType3.setVisible(false);

			if (isMenuWidthFixed) {

				char[] chs = new char[maxMenuTitleLength];
				for (int i = 0; i < maxMenuTitleLength; i++) {
					chs[i] = ' ';
				}
				maxMenuTitleSpaces = new String(chs);

				for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
					String title = mainView.menuTab.getTitleAt(i);
					title = StringUtil.leftBX(title + maxMenuTitleSpaces, maxMenuTitleLength);
					mainView.menuTab.setTitleAt(i, title);
				}
			} else {
				System.out.println("trans.menu.width.no.fixed is true");
			}
		}

		if (isDirectPrint) {
			String printerName = getUser().getPrinterName();

			for (PrintService service : PrinterJob.lookupPrintServices()) {
				if (service.getName().equals(printerName)) {
					TClientLoginInfo.setPrintService(service);
					break;
				}
			}
		}

		// ツールチップの表示遅れ0.1秒
		ToolTipManager.sharedInstance().setInitialDelay(100);

		int seconds = 10;

		try {
			seconds = Util.avoidNullAsInt(ClientConfig.getProperty("trans.tooltip.dismiss.delay.seconds"));

		} catch (Exception ex) {
			// エラーなし
		}

		if (seconds <= 0) {
			seconds = 4; // Javaデフォルトは4秒
		}

		// ツールチップの表示時間、単位は秒
		ToolTipManager.sharedInstance().setDismissDelay(seconds * 1000);

		// メニュー表示順復元
		restoreMenuOrder();
	}

	/**
	 * メイン画面のイベント定義
	 */
	protected void initMainViewEvent() {

		// プログラムツリーからプログラム選択
		for (int i = 0; i < mainView.menuTrees.size(); i++) {

			mainView.menuTrees.get(i).addMouseListener(new MouseAdapter() {

				@Override
				public void mouseClicked(MouseEvent e) {
					menuTree_mouseClicked(e);
				}
			});
		}

		// 配置ファイルがtrueの場合、イベントを追加する
		if (isMenuWidthFixed) {
			// メニューサイズ変更
			mainView.menuTab.addComponentListener(new ComponentAdapter() {

				/**
				 * メニューサイズ変更
				 */
				@Override
				public void componentResized(ComponentEvent arg0) {
					menuResized();
				}

			});
		}

		// L&Fボタン（Blue）押下
		mainView.btnBlue.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.Blue);
			}
		});

		// L&Fボタン（Pink）押下
		mainView.btnPink.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.Pink);
			}
		});

		// L&Fボタン（Orange）押下
		mainView.btnOrange.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.Orange);
			}
		});

		// L&Fボタン（Green）押下
		mainView.btnGreen.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.Green);
			}
		});

		// L&Fボタン（Gray）押下
		mainView.btnGray.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.Gray);
			}
		});

		// L&Fボタン（White）押下
		mainView.btnWhite.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				// Looｋ And Feelの変更
				btnLF_Click(LookAndFeelColor.White);
			}
		});

		// L&Fボタン（Type1）押下
		mainView.btnType1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE1);
			}
		});

		// L&Fボタン（Type2）押下
		mainView.btnType2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE2);
			}
		});

		// L&Fボタン（Type3）押下
		mainView.btnType3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLF_Click(MenuType.TYPE3);
			}
		});

		mainView.ctrlFontSize.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					changedFontSizeComboBox();
				}
			}
		});

		// プリンタ設定ボタン押下
		mainView.btnPrintSetup.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnPrintSetup_Click();
			}
		});

		// パスワード変更ボタン押下
		mainView.btnPassword.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnPassword_Click();
			}
		});

		// バージョンボタン押下
		mainView.btnVersion.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnVersion_Click();
			}
		});

		// ログアウトボタン押下
		mainView.btnLogOut.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnLogOut_Click();
			}
		});

		// 閉じる時
		mainView.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				maybeExit();
			}

		});

		// メニュータブ変更時処理
		mainView.menuTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// メニュータブステータス変更処理
				changedMenuTabState();
			}
		});

		// タブを閉じた場合の処理
		mainView.mainTab.addCancelButtonCloseCallBackListener(new CallBackListener() {

			@Override
			public void after(Component component) {
				closeProgram(component);
			}
		});

		// タブを再選択した時のフォーカス設定
		mainView.mainTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// メインタブステータス変更処理
				changedMainTabState();
			}
		});

		// タブを再選択した時のフォーカス設定
		mainView.menuTab.addChangeListener(new ChangeListener() {

			public void stateChanged(ChangeEvent e) {
				// メインタブステータス変更処理
				changedMenuTabState();
			}
		});

		mainView.mainTab.addMouseListener(new MouseAdapter() {

			/** メニュー */
			protected JPopupMenu popup = new JPopupMenu();

			/** クリックイベント */
			@Override
			public void mouseClicked(MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				Point tabPoint = e.getPoint();
				final int index = mainView.mainTab.indexAtLocation(tabPoint.x, tabPoint.y);

				if (index < 0) {
					return;
				}

				if (SwingUtilities.isMiddleMouseButton(e)) {
					// ホイールクリック
					removeTabAt(index);

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// 右クリック
					popup.removeAll();

					JMenuItem closeAll = new JMenuItem(getWord("C10796"));// 全てのタブを閉じる
					closeAll.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {

							for (int i = mainView.mainTab.getTabCount(); 0 < i; i--) {
								removeTabAt(i - 1);
							}
						}
					});

					JMenuItem closeOther = new JMenuItem(getWord("C10797"));// 他のタブを全て閉じる
					closeOther.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {

							for (int i = mainView.mainTab.getTabCount(); 0 < i; i--) {
								if (index == i - 1) {
									continue;
								}

								removeTabAt(i - 1);
							}
						}
					});

					JMenuItem closeOwn = new JMenuItem(getWord("C02374"));// 閉じる
					closeOwn.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {
							removeTabAt(index);
						}
					});

					JMenuItem otherFrame = new JMenuItem(getWord("C11401"));// 切り離し
					otherFrame.addActionListener(new ActionListener() {

						@SuppressWarnings("hiding")
						public void actionPerformed(ActionEvent e) {
							createNewFrame(index);
						}
					});

					popup.add(closeAll);
					popup.add(closeOther);
					popup.add(closeOwn);
					popup.add(otherFrame);

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * メニュータブの順序復元
	 */
	protected void restoreMenuOrder() {
		if (!IS_SAVE_MENU) {
			return;
		}

		boolean isEnglish = getUser().isEnglish();
		String key = isEnglish ? "ui.menu.tab.list.en" : "ui.menu.tab.list";
		Object obj = getSaveValue(key);
		if (obj == null) {
			return;
		}

		if (!(obj instanceof List)) {
			return;
		}

		List<String> titleList = (List<String>) obj;

		List<String> nowTitleList = new ArrayList<String>();
		List<MenuTabData> nowMenuList = new ArrayList<MenuTabData>();

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = Util.avoidNullNT(mainView.menuTab.getTitleAt(i));
			nowTitleList.add(title);

			nowMenuList.add(new MenuTabData(mainView.menuTab, i));
		}

		mainView.menuTab.removeAll();

		for (int i = 0; i < titleList.size(); i++) {
			String title = titleList.get(i);
			if (i >= nowTitleList.size()) {
				// 現在タブの件数が少ない、処理中断
				break;
			}

			if (nowTitleList.contains(title)) {
				// 現在タブに保存されたタブが存在する
				int src = nowTitleList.indexOf(title); // 移動元
				addMenuTab(nowMenuList.get(src));

				// 重複追加を防止する
				nowTitleList.set(src, null);
			}
		}

		for (int i = 0; i < nowTitleList.size(); i++) {
			String title = nowTitleList.get(i);
			if (!Util.isNullOrEmpty(title)) {
				// 未処理メニューはそのまま追加
				int src = i; // 移動元
				addMenuTab(nowMenuList.get(src));
			}
		}
	}

	/**
	 * @param menuTabData
	 */
	protected void addMenuTab(MenuTabData menuTabData) {
		if (menuTabData == null) {
			// 処理済みデータは処理なし
			return;
		}

		mainView.menuTab.addTab(menuTabData.label, menuTabData.iconSrc, menuTabData.comp, menuTabData.tooltip);

		int dst = mainView.menuTab.getTabCount() - 1;

		mainView.menuTab.setDisabledIconAt(dst, menuTabData.iconDis);
		mainView.menuTab.setEnabledAt(dst, menuTabData.enabled);
		mainView.menuTab.setMnemonicAt(dst, menuTabData.keycode);
		mainView.menuTab.setDisplayedMnemonicIndexAt(dst, menuTabData.mnemonicLoc);
		mainView.menuTab.setForegroundAt(dst, menuTabData.fg);
		mainView.menuTab.setBackgroundAt(dst, menuTabData.bg);

	}

	/**
	 * メニュータブデータ
	 */
	protected class MenuTabData {

		/** comp */
		protected Component comp;

		/** label */
		protected String label;

		/** iconSrc */
		protected Icon iconSrc;

		/** iconDis */
		protected Icon iconDis;

		/** tooltip */
		protected String tooltip;

		/** enabled */
		protected boolean enabled;

		/** keycode */
		protected int keycode;

		/** mnemonicLoc */
		protected int mnemonicLoc;

		/** fg */
		protected Color fg;

		/** bg */
		protected Color bg;

		/**
		 * コンストラクター
		 * 
		 * @param pane
		 * @param src
		 */
		public MenuTabData(TLeftColorTabbedPane pane, int src) {
			this.comp = pane.getComponentAt(src);
			this.label = pane.getTitleAt(src);
			this.iconSrc = pane.getIconAt(src);
			this.iconDis = pane.getDisabledIconAt(src);
			this.tooltip = pane.getToolTipTextAt(src);
			this.enabled = pane.isEnabledAt(src);
			this.keycode = pane.getMnemonicAt(src);
			this.mnemonicLoc = pane.getDisplayedMnemonicIndexAt(src);
			this.fg = pane.getForegroundAt(src);
			this.bg = pane.getBackgroundAt(src);
		}
	}

	/**
	 * メインタブステータス変更処理
	 */
	protected void changedMainTabState() {
		int index = mainView.mainTab.getSelectedIndex();

		if (0 <= index) {
			// スクロール対応
			Component comp = mainView.mainTab.getComponentAt(index);
			TPanelBusiness panel = null;
			if (comp instanceof TPanelBusiness) {
				panel = (TPanelBusiness) comp;
			} else if (comp instanceof TScrollPane) {
				panel = ((TScrollPane) comp).getPanel();
			}

			if (panel != null) {
				panel.requestFocus();
				panel.transferFocusTopItem();

				if (panel instanceof TTabbedPanel) {
					((TTabbedPanel) panel).active();
				}
			}

			if (comp != null && comp instanceof TTabbedDialog) {
				((TTabbedDialog) comp).active();
			}
		}
	}

	/**
	 * メニュー(左側)タブステータス変更処理
	 */
	protected void changedMenuTabState() {
		if (!IS_SAVE_MENU) {
			return;
		}

		boolean isEnglish = getUser().isEnglish();
		List<String> titleList = new ArrayList<String>();

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = Util.avoidNullNT(mainView.menuTab.getTitleAt(i));
			titleList.add(title);
		}

		// 記憶する
		String key = isEnglish ? "ui.menu.tab.list.en" : "ui.menu.tab.list";
		setSaveValue(key, titleList);
		saveFile();

		int index = mainView.menuTab.getSelectedIndex();

		if (0 <= index) {
			// スクロール対応
			TTree menu = getSelectedTree();

			if (menu != null && menu.getModel() != null && menu.getModel().getRoot() != null && menu.getModel().getRoot() instanceof TMenuTreeNode) {
				TMenuTreeNode parent = (TMenuTreeNode) menu.getModel().getRoot();

				if (parent.getPrgGroup() != null) {
					SystemizedProgram sp = parent.getPrgGroup();

					String sysCode = sp.getProgramGroupCode();

					List<TMainInitialInterface> clsList = tabLinkInstanceMap.get(sysCode);
					if (clsList != null && !clsList.isEmpty()) {
						// 起動処理

						for (TMainInitialInterface clazz : clsList) {
							try {

								// 初期化処理
								clazz.afterCreate();

							} catch (Exception ex) {
								ex.printStackTrace();
							}
						}
					}
				}
			}

		}
	}

	/**
	 * メニューサイズ変更(幅均一)
	 */
	protected void menuResized() {

		int len = mainView.menuTab.getWidth() - minMenuSize;
		int titleSize = len / 5 - 2;

		if (titleSize < maxMenuTitleLength) {
			titleSize = maxMenuTitleLength;
		}

		char[] chs = new char[titleSize];
		for (int i = 0; i < titleSize; i++) {
			chs[i] = ' ';
		}
		String addSpaces = new String(chs);

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {
			String title = mainView.menuTab.getTitleAt(i);
			title += addSpaces;
			mainView.menuTab.setTitleAt(i, StringUtil.leftBX(title, titleSize));
		}
	}

	/**
	 * 指定タブを閉じる
	 * 
	 * @param tabIndex タブIndex
	 */
	public void removeTabAt(int tabIndex) {
		// 閉じるイベント
		Component comp = mainView.mainTab.getComponentAt(tabIndex);
		closeProgram(comp);

		mainView.mainTab.remove(comp);
	}

	/**
	 * 指定タブを切り離す
	 * 
	 * @param tabIndex タブIndex
	 */
	public void createNewFrame(int tabIndex) {

		// 切り離すイベント
		Component component = mainView.mainTab.getComponentAt(tabIndex);

		// スクロール対応
		if (component == null || (!(component instanceof TPanelBusiness) && !(component instanceof TScrollPane))) {
			return;
		}

		final TPanelBusiness panel;
		if (component instanceof TPanelBusiness) {
			panel = (TPanelBusiness) component;
		} else {
			panel = ((TScrollPane) component).getPanel();
		}

		// メイン画面のタブを消す
		mainView.mainTab.removeTabAt(tabIndex);

		// 指定パネルをFrameに表示する
		showPanelWithFrame(panel);

	}

	/**
	 * 指定パネルをFrameに表示する
	 * 
	 * @param panel TPanelBusiness
	 */
	public void showPanelWithFrame(final TPanelBusiness panel) {

		// コントローラ
		TAppletClientBase controller = null;
		String uid = null;

		if (panel instanceof TTabbedPanel) {
			uid = ((TTabbedPanel) panel).getDialog().getUID();
			controller = selectedProgram.get(uid);
		} else {
			controller = selectedProgram.get(panel.getProgramCode());
		}

		// プログラム情報
		TClientProgramInfo programInfo = null;

		// TRANS2.0以上の場合
		if (controller instanceof TController) {
			programInfo = ((TController) controller).getProgramInfo();
		}
		// TRANS1.0の場合
		else if (controller instanceof TPanelCtrlBase) {
			programInfo = ((TPanelCtrlBase) controller).getProgramInfo();
		}
		// 以外
		else {
			return;
		}

		final String programCode = programInfo.getProgramCode();
		final String programName = programInfo.getProgramName();

		TFrame frame = createFrame(panel, programName);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		// 画面サイズ
		int width = panel.getSize().width;
		int height = panel.getSize().height;
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // スクリーンサイズ
		if (width == 0) {
			width = mainView.mainTab.getWidth();
		}
		if (height == 0) {
			height = mainView.mainTab.getHeight();
		}

		width = Math.min(width, dim.width);
		height = Math.min(height, dim.height);

		frame.setSize(width, height);

		// スクロール対応

		if (isUseScroll) {
			TScrollPane jsp = createScrollPane(panel);
			jsp.setAutoscrolls(true);
			frame.add(jsp);
		} else {
			frame.add(panel);
		}

		frame.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosed(WindowEvent ev) {

				// プログラムを閉じる
				closeProgram(panel);

				// フレーム形式プログラムから消す
				frameProgram.remove(programCode);
				frames.remove(programCode);
			}
		});

		// フレーム形式プログラムに追加する
		if (!Util.isNullOrEmpty(uid)) {
			frameProgram.put(uid, panel);
			frames.put(uid, frame);
		} else {
			frameProgram.put(programCode, panel);
			frames.put(programCode, frame);
		}

		// フレーム表示
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	/**
	 * @param panel
	 * @param programName
	 * @return フレーム
	 */
	public TFrame createFrame(final TPanelBusiness panel, final String programName) {
		TFrame frame = new TFrame(panel, programName);
		if (icon != null) {
			frame.setIconImage(icon);
			frame.pack();
		}
		return frame;
	}

	/**
	 * 終了するかの確認
	 */
	protected void maybeExit() {
		if (!showConfirmMessage(mainView, "Q00055")) {// 終了しますか？
			return;
		}

		String tmpVersion = jarVersion; // 一旦退避
		jarVersion = ""; // ログアウトはチェック不要

		try {

			// 排他解除
			for (int i = mainView.mainTab.getTabCount() - 1; i >= 0; i--) { // 逆順
				closeProgramE(mainView.mainTab.getComponentAt(i));
			}

			// フレーム形式のプログラムも排他解除
			TPanelBusiness[] pnls = frameProgram.values().toArray(new TPanelBusiness[0]);
			for (int i = pnls.length - 1; i >= 0; i--) { // 逆順
				TPanelBusiness panel = pnls[i];
				closeProgramE(panel);
			}

			// ログアウトログ記録
			logE(getCompany(), getUser(), null, "OUT");

			// 終了
			request(UserExclusiveManager.class, "cancelExclude");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		} finally {
			mainView.setVisible(false);
			mainView.dispose();
			jarVersion = tmpVersion;
		}

		System.exit(0);
	}

	/**
	 * プログラムツリーからプログラム選択
	 * 
	 * @param e
	 */
	public void menuTree_mouseClicked(MouseEvent e) {

		// 左クリックで起動
		if (SwingUtilities.isLeftMouseButton(e)) {

			// 選択タブに対するツリーを正確に取得する
			TTree tree = getSelectedTree();
			if (tree == null) {
				return;
			}

			// クリックされた場所からTreePath を取得する
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path == null) {
				return;
			}

			TMenuTreeNode node = (TMenuTreeNode) tree.getLastSelectedPathComponent();

			if (node == null) {
				return;
			}

			TTreeItem item = (TTreeItem) node.getUserObject();

			// true:Frameへ表示
			boolean isFrame = e.isAltDown();

			if (isUseMenuMaster) {

				MenuDisp program = (MenuDisp) item.getObj();

				if (program.getProgram() == null || Util.isNullOrEmpty(program.getProgram().getLoadClassName())) {
					return;
				}

				startProgram(program.getCode(), program.getName(), program.getProgram().getLoadClassName(), isFrame);

			} else {

				// TODO:プログラムマスタによるメニュー構築⇒削除予定

				Program program = (Program) item.getObj();

				if (program == null || Util.isNullOrEmpty(program.getLoadClassName())) {
					return;
				}

				startProgram(program.getCode(), program.getName(), program.getLoadClassName(), isFrame);
			}
		}
	}

	/**
	 * 選択タブに対するツリーを正確に取得する
	 * 
	 * @return 選択タブ
	 */
	protected TTree getSelectedTree() {
		if (!(mainView.menuTab.getSelectedComponent() instanceof TScrollPane)) {
			return null;
		}
		TScrollPane sp = (TScrollPane) mainView.menuTab.getSelectedComponent();
		if (sp.getViewport().getComponentCount() == 0 || !(sp.getViewport().getComponent(0) instanceof TPanel)) {
			return null;
		}
		TPanel pnl = (TPanel) sp.getViewport().getComponent(0);
		if (pnl.getComponentCount() == 0 || !(pnl.getComponent(0) instanceof TTree)) {
			return null;
		}
		TTree tree = (TTree) pnl.getComponent(0);
		return tree;
	}

	/**
	 * 編集ダイアログをTAB機能で開く
	 * 
	 * @param view
	 */
	@SuppressWarnings("deprecation")
	public void startTabbedProgram(TTabbedDialog view) {
		startTabbedProgram(view, false);
	}

	/**
	 * 編集ダイアログをTAB機能で開く
	 * 
	 * @param view
	 * @param isFrame true:フレームモード
	 */
	@SuppressWarnings("deprecation")
	public void startTabbedProgram(TTabbedDialog view, boolean isFrame) {

		String uid = view.getUID();
		String prgName = view.getProgramName();

		// 既に選択されている場合、そのタブに移動
		if (isStartedProgram(uid)) {

			boolean hasTab = false;

			for (int tabCount = 0; tabCount < mainView.mainTab.getTabCount(); tabCount++) {

				// スクロール対応
				Component comp = mainView.mainTab.getComponentAt(tabCount);
				TDialog dialog = null;
				String currentUID = null;

				if (comp instanceof TTabbedPanel) {
					currentUID = ((TTabbedPanel) comp).getDialog().getUID();

				} else if (comp instanceof TPanelBusiness) {
					// 処理なし

				} else if (comp instanceof TScrollPane) {
					dialog = ((TScrollPane) comp).getDialog();
				}

				if (dialog != null && dialog instanceof TTabbedDialog) {
					currentUID = ((TTabbedDialog) dialog).getUID();
				}

				Container cont = (Container) comp;

				if (uid.equals(currentUID)) {
					TInputVerifier.setActiveChildren(cont, false);
					mainView.mainTab.setSelectedIndex(tabCount);
					TInputVerifier.setActiveChildren(cont, true);
					hasTab = true;
					break;
				}
			}

			// フレームがあれば、重複起動不要
			if (!hasTab && frameProgram.get(uid) != null) {
				TPanelBusiness panel = frameProgram.get(uid);
				// 復元＆フォーカス設定
				if (panel.getParentFrame().getExtendedState() == 7) {
					// 最大化⇒最小化
					panel.getParentFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
				}
				if (panel.getParentFrame().getExtendedState() == Frame.ICONIFIED) {
					// 普通⇒最小化
					panel.getParentFrame().setExtendedState(Frame.NORMAL);
				}
				panel.transferFocusTopItem();
			}

			return;
		}

		// プログラムを起動し、指示画面をメイン領域にセット
		try {

			String prgCode = view.getProgramCode();

			// 砂時計
			mainView.getGlassPane().setVisible(true);

			if (Util.isNullOrEmpty(prgCode)) {
				return;
			}

			TClientProgramInfo prgInfo_ = TClientProgramInfo.getInstance();
			prgInfo_.setProgramCode(prgCode);
			prgInfo_.setProgramName(prgName);

			TController controller = view.getController();
			String realUID = controller.getRealUID(); // 画面識別子
			String realInfo = controller.getRealInfo(); // 備考

			// Trans2.0の場合

			controller.setProgramInfo(prgInfo_.clone());

			// 排他制御
			if (controller instanceof TExclusive) {
				TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
				try {
					ec.exclude();

				} catch (Exception e) {
					errorHandler(mainView, e);
					return;
				}

			}

			controller.start();
			selectedProgram.put(uid, controller);

			TTabbedPanel pnl = view.getDialogBasePanel();
			TDialog dialog = view.getDialog();
			if (isUseScroll) {
				TScrollPane jsp = createScrollPane((TTabbedDialog) dialog);
				jsp.setAutoscrolls(true);
				if (jsp.getViewPanel() != null) {
					TGuiUtil.setComponentSize(jsp.getViewPanel(), 800, 600);
				}
				if (!isFrame) {
					mainView.mainTab.addTab(prgName, jsp, true);
				} else {
					showPanelWithFrame(pnl);
				}
			} else {
				if (!isFrame) {
					mainView.mainTab.addTab(prgName, pnl, true);
				} else {
					showPanelWithFrame(pnl);
				}
			}

			TInputVerifier.setActiveChildren(dialog, false);
			mainView.mainTab.setSelectedIndex(mainView.mainTab.getTabCount() - 1);
			TInputVerifier.setActiveChildren(dialog, true);

			if (!Util.isNullOrEmpty(realUID)) {
				// 画面識別子指定あり→プログラム起動記録
				logE(getCompany(), getUser(), realUID, "IN", realInfo);
			}

		} catch (Exception ex) {
			errorHandler(mainView, ex);
			selectedProgram.remove(uid); // エラー発生した場合、選択済みプログラムから外す

		} finally {
			mainView.getGlassPane().setVisible(false);
		}
	}

	/**
	 * TABタイトル変更
	 * 
	 * @param ori
	 * @param title
	 */
	public void changedTabTitle(TTabbedPanel ori, String title) {
		if (ori == null) {
			return;
		}

		int tabIndex = mainView.mainTab.indexOfComponent(ori);

		if (tabIndex >= 0 && tabIndex < mainView.mainTab.getTabCount()) {
			// 有効なTAB

			TTabbedTitle pnl = mainView.mainTab.getTab(tabIndex);
			if (pnl != null) {
				pnl.setTitle(title);
			}

			mainView.mainTab.repaint();
		}

		if (ori.getDialog() != null && !Util.isNullOrEmpty(ori.getDialog().getUID())) {
			TFrame frame = frames.get(ori.getDialog().getUID());
			if (frame != null) {
				frame.setTitle(title);
			}
		}
	}

	/**
	 * プログラムを開始する
	 * 
	 * @param prgCode プログラムコード
	 * @param prgName プログラム名称
	 * @param ldName ロードクラス名
	 */
	public void startProgram(String prgCode, String prgName, String ldName) {
		startProgram(prgCode, prgName, ldName, false);
	}

	/**
	 * プログラムを開始する
	 * 
	 * @param prgCode プログラムコード
	 * @param prgName プログラム名称
	 * @param ldName ロードクラス名
	 * @param isFrame true:Frameへ表示
	 */
	@SuppressWarnings("deprecation")
	public void startProgram(String prgCode, String prgName, String ldName, boolean isFrame) {

		// 既に選択されている場合、そのタブに移動
		if (isStartedProgram(prgCode)) {

			boolean hasTab = false;

			for (int tabCount = 0; tabCount < mainView.mainTab.getTabCount(); tabCount++) {

				// スクロール対応
				Component comp = mainView.mainTab.getComponentAt(tabCount);
				TPanelBusiness panel = null;
				String currentProgramCode = null;

				if (comp instanceof TTabbedPanel) {
					currentProgramCode = ((TTabbedPanel) comp).getProgramCode();

				} else if (comp instanceof TPanelBusiness) {
					panel = (TPanelBusiness) comp;
					currentProgramCode = panel.getProgramCode();

				} else if (comp instanceof TScrollPane) {
					panel = ((TScrollPane) comp).getPanel();
					currentProgramCode = panel.getProgramCode();
				}

				if (prgCode.equals(currentProgramCode)) {
					TInputVerifier.setActiveChildren(panel, false);
					mainView.mainTab.setSelectedIndex(tabCount);
					panel.transferFocusTopItem();
					TInputVerifier.setActiveChildren(panel, true);
					hasTab = true;
					break;
				}
			}

			// フレームがあれば、重複起動不要
			if (!hasTab && frameProgram.get(prgCode) != null) {
				TPanelBusiness panel = frameProgram.get(prgCode);
				// 復元＆フォーカス設定
				if (panel.getParentFrame().getExtendedState() == 7) {
					// 最大化⇒最小化
					panel.getParentFrame().setExtendedState(Frame.MAXIMIZED_BOTH);
				}
				if (panel.getParentFrame().getExtendedState() == Frame.ICONIFIED) {
					// 普通⇒最小化
					panel.getParentFrame().setExtendedState(Frame.NORMAL);
				}
				panel.transferFocusTopItem();
			}

			return;
		}

		// プログラムを起動し、指示画面をメイン領域にセット
		try {
			// 砂時計
			mainView.getGlassPane().setVisible(true);

			if (Util.isNullOrEmpty(ldName)) {
				return;
			}

			// 既定サブシステム利用ユーザ数チェック
			if (isOverSystemRegulatedNumber(getCompany(), getUser(), prgCode)) {
				return;
			}

			Class cls = Class.forName(ldName);

			TClientProgramInfo prgInfo_ = TClientProgramInfo.getInstance();
			prgInfo_.setProgramCode(prgCode);
			prgInfo_.setProgramName(prgName);

			TPanelBusiness panel;

			Object ctlInstance = cls.newInstance();

			// Trans2.0の場合
			if (ctlInstance instanceof TController) {

				TController controller = (TController) ctlInstance;
				controller.setProgramInfo(prgInfo_.clone());

				// 排他制御
				if (controller instanceof TExclusive) {
					TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
					try {
						ec.exclude();

					} catch (Exception e) {
						errorHandler(mainView, e);
						return;
					}

				}

				controller.start();
				selectedProgram.put(prgCode, controller);

				panel = controller.getPanel();
				panel.setProgramCode(prgCode);

				TGradationPanel dummy = createGradationPanel(panel);

				if (isUseScroll) {
					TScrollPane jsp = createScrollPane(dummy);
					jsp.setAutoscrolls(true);
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, jsp, true);
					} else {
						showPanelWithFrame(panel);
					}
				} else {
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, dummy, true);
					} else {
						showPanelWithFrame(panel);
					}
				}

				int tabIndex[] = new int[1];
				tabIndex[0] = mainView.mainTab.getTabCount();

			}
			// Trans1.0の場合
			else {

				TAppletClientBase controller = (TAppletClientBase) ctlInstance;
				selectedProgram.put(prgCode, controller);

				if (controller instanceof TPanelCtrlBase) {
					((TPanelCtrlBase) controller).setProgramInfo(prgInfo_.clone());
				}

				panel = (TPanelBusiness) controller.getPanel();
				panel.setProgramCode(prgCode);

				// グラデーション用にダミーパネルに乗せる
				panel.setOpaque(false);
				TGradationPanel dummy = createGradationPanel(panel);

				// スクロール対応
				if (isUseScroll) {
					TScrollPane jsp = createScrollPane(dummy);
					jsp.setAutoscrolls(true);
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, jsp, true);
					} else {
						showPanelWithFrame(panel);
					}
				} else {
					if (!isFrame) {
						mainView.mainTab.addTab(prgName, dummy, true);
					} else {
						showPanelWithFrame(panel);
					}
				}

			}

			panel.setTabPolicy();
			TInputVerifier.setActiveChildren(panel, false);
			if (!isFrame) {
				mainView.mainTab.setSelectedIndex(mainView.mainTab.getTabCount() - 1);
			}
			panel.transferFocusTopItem();
			TInputVerifier.setActiveChildren(panel, true);

			// プログラム起動記録
			logE(getCompany(), getUser(), prgCode, "IN");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
			selectedProgram.remove(prgCode); // エラー発生した場合、選択済みプログラムから外す

		} finally {
			mainView.getGlassPane().setVisible(false);
		}
	}

	/**
	 * @param panel
	 * @return TRANS1プログラム用ダミー背景のグラデーションパネル
	 */
	protected TGradationPanel createGradationPanel(TPanelBusiness panel) {
		return new TGradationPanel(panel);
	}

	/**
	 * @param dialog
	 * @return スクロールパネル
	 */
	protected TScrollPane createScrollPane(TTabbedDialog dialog) {
		return new TScrollPane(dialog);
	}

	/**
	 * @param panel
	 * @return スクロールパネル
	 */
	protected TScrollPane createScrollPane(TPanel panel) {
		return new TScrollPane(panel);
	}

	/**
	 * @param panel
	 * @return スクロールパネル
	 */
	protected TScrollPane createScrollPane(TPanelBusiness panel) {
		return new TScrollPane(panel);
	}

	/**
	 * 既定サブシステム利用ユーザ数チェック
	 * 
	 * @param company
	 * @param user
	 * @param programCode
	 * @return true:規定サブシステム利用ユーザ数が超えた
	 */
	protected boolean isOverSystemRegulatedNumber(Company company, User user, String programCode) {
		try {

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

			boolean result = (Boolean) request(SystemManager.class, "canOpenProgram", log);
			if (!result) {
				// W01155 規定のサブモジュールのライセンスが足りません。
				// You need more licenses to use this module.
				showMessage("W01155");
				return true;
			}

		} catch (TException ex) {
			errorHandler(ex);
			return true;
		}
		return false;
	}

	/**
	 * TRANS1プログラム用ダミー背景のグラデーションパネル
	 */
	static class TGradationPanel extends TPanelBusiness {

		/** グラデーション始点色 */
		protected Color startColor = Color.white;

		/** グラデーション始点色 */
		protected Color endColor;

		/** 元パネル */
		protected TPanelBusiness panel;

		/**
		 * コンストラクタ.
		 * 
		 * @param panel 元パネル
		 */
		public TGradationPanel(TPanelBusiness panel) {
			this.panel = panel;

			setLayout(new BorderLayout());
			add(panel, BorderLayout.CENTER);
		}

		@Override
		public String getProgramCode() {
			return panel.getProgramCode();
		}

		@Override
		public void requestFocus() {
			panel.requestFocus();
		}

		@Override
		public void transferFocusTopItem() {
			panel.transferFocusTopItem();
		}

		@Override
		public void setTabPolicy() {
			panel.setTabPolicy();
		}

		@Override
		public void paintComponent(Graphics g) {

			endColor = getBackground();

			Graphics2D g2 = (Graphics2D) g;

			// 上(白)⇒下(色)
			GradientPaint gradient = new GradientPaint(getWidth() / 4, 0.0f, startColor, getWidth() / 4,
				getHeight() / 4, endColor);
			g2.setPaint(gradient);

			g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
		}
	}

	/**
	 * プログラムが既に起動されていないかチェックする
	 * 
	 * @param prgCode プログラムコード
	 * @return true:起動
	 */
	public boolean isStartedProgram(String prgCode) {

		if (Util.isNullOrEmpty(prgCode)) {
			return false;
		}

		return selectedProgram.containsKey(prgCode);
	}

	/**
	 * プログラムの終了ボタン押下時
	 * 
	 * @param component
	 */
	public void closeProgram(Component component) {
		try {
			closeProgramE(component, false);
		} catch (TException e) {
			// エラーなし
		}
	}

	/**
	 * プログラムの終了ボタン押下時
	 * 
	 * @param component
	 * @throws TException
	 */
	public void closeProgramE(Component component) throws TException {
		closeProgramE(component, true);
	}

	/**
	 * プログラムの終了ボタン押下時
	 * 
	 * @param component
	 * @param doThrow
	 * @throws TException
	 */
	public void closeProgramE(Component component, boolean doThrow) throws TException {

		if (component == null) {
			return;
		}

		TPanelBusiness panel = null;
		TDialog dialog = null;
		String prgCode = null;

		// スクロール対応
		if (component instanceof TScrollPane) {
			panel = ((TScrollPane) component).getPanel();

			if (panel == null) {
				dialog = ((TScrollPane) component).getDialog();
			}

		} else if (component instanceof TTabbedPanel) {
			prgCode = ((TTabbedPanel) component).getDialog().getUID();

		} else if (component instanceof TPanelBusiness) {
			panel = (TPanelBusiness) component;
		}

		if (panel != null) {
			if (panel instanceof TTabbedPanel) {
				prgCode = ((TTabbedPanel) panel).getDialog().getUID();
			} else {
				prgCode = panel.getProgramCode();
			}
		} else if (dialog != null && dialog instanceof TTabbedDialog) {
			prgCode = ((TTabbedDialog) dialog).getUID();
		}

		// 排他解除
		TAppletClientBase controller = selectedProgram.get(prgCode);
		if (controller instanceof TExclusive) {
			TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
			try {
				ec.cancelExclude();
			} catch (TException e) {
				errorHandler(mainView, e);
				return;
			}
		}

		if (controller instanceof IManagementCtrl) {
			// 管理項目あり
			((IManagementCtrl) controller).saveManagementSetting();
			// 出力単位あり
			((IManagementCtrl) controller).saveDepartmentSetting();
		}

		if (panel != null) {
			// プログラムアウトログ
			if (doThrow) {
				logE(getCompany(), getUser(), prgCode, "OUT");
			} else {
				log(getCompany(), getUser(), prgCode, "OUT");
			}
		}

		if (component instanceof TTabbedPanel) {
			// 対象パネルからタブを閉じたい場合に処理を行う
			{

				String realUID = null;
				String realInfo = null;

				TTabbedPanel pnl = (TTabbedPanel) component;

				if (pnl.getDialog() != null && pnl.getDialog().getController() != null) {
					realUID = pnl.getDialog().getController().getRealUID();
					realInfo = pnl.getDialog().getController().getRealInfo();
				}

				if (!Util.isNullOrEmpty(realUID)) {

					// プログラムアウトログ
					if (doThrow) {
						logE(getCompany(), getUser(), realUID, "OUT", realInfo);
					} else {
						log(getCompany(), getUser(), realUID, "OUT", realInfo);
					}
				}
			}

			for (int i = 0; i < mainView.mainTab.getTabCount(); i++) {

				Component comp = mainView.mainTab.getComponentAt(i);

				if (comp == null) {
					continue;
				}

				if (comp instanceof TScrollPane) {
					TTabbedPanel basePanel = ((TScrollPane) comp).getDialog().getDialogBasePanel();
					if (component.equals(basePanel)) {
						mainView.mainTab.removeTabAt(i);
						break;
					}
				} else if (comp instanceof TTabbedPanel) {
					if (comp.equals(component)) {
						mainView.mainTab.removeTabAt(i);
						break;
					}
				}
			}

			if (frames.get(prgCode) != null) {
				// frameにより判定
				TFrame frame = frames.get(prgCode);
				frame.setVisible(false);

				frameProgram.remove(prgCode);
				frames.remove(prgCode);
			}
		}

		// 起動プログラムリストから削除
		selectedProgram.remove(prgCode);

		System.gc();
	}

	/**
	 * L&Fボタン押下
	 * 
	 * @param color
	 */
	@SuppressWarnings("deprecation")
	public void btnLF_Click(LookAndFeelColor color) {

		try {

			// Look and Feelの設定
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, color);

			// 選択したL&Fを保存
			User user = TLoginInfo.getUser();
			user.setLfName(type.value);
			user.setLfColorType(color.name());

			// フレーム形式のプログラムL&Fも設定
			for (TPanelBusiness panel : frameProgram.values()) {
				TUIManager.setUI(panel, type, color);
			}

			// 1.0のスプレッドシートの色を戻す
			for (TAppletClientBase ctrl : selectedProgram.values()) {
				resetColor(ctrl.getPanel());
			}

			request(UserManager.class, "entryLookandFeel", user);

			drawImages(mainView);

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}
	}

	/**
	 * コンポーネント描画を再表示する
	 * 
	 * @param container
	 */
	protected void drawImages(Container container) {
		for (Component c : container.getComponents()) {
			if (c instanceof TTitlePanel) {
				if (((TTitlePanel) c).isTitleVisible) {

					// 図の再表示
					((TTitlePanel) c).setImage();
				}
			}
			if (c instanceof Container) {
				drawImages((Container) c);
			}
		}
	}

	/**
	 * カラーリセット
	 * 
	 * @param cont コンテナ.
	 */
	protected void resetColor(Container cont) {
		if (cont == null) {
			return;
		}

		if (cont instanceof TTable) {
			((TTable) cont).resetColor();
		}

		if (cont instanceof TTextField) {
			TTextField txt = (TTextField) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		if (cont instanceof TTextArea) {
			TTextArea txt = (TTextArea) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		if (cont instanceof TPasswordField) {
			TPasswordField txt = (TPasswordField) cont;
			if (txt.isFocusOwner()) {
				txt.focusGainedBackColor();
			}
		}

		// 子のコンポーネントを検索.
		Component[] arrComp = cont.getComponents();
		if (arrComp == null) {
			return;
		}

		// すべての子について、再帰的に検索する.
		for (Component comp : arrComp) {
			if (comp instanceof Container) {
				resetColor((Container) comp);
			}
		}
	}

	/**
	 * L&F menuType ボタン押下
	 * 
	 * @param menuType タブタイプ
	 */
	@SuppressWarnings("deprecation")
	public void btnLF_Click(MenuType menuType) {

		try {
			// タイプ切替
			TUIManager.setMenuType(menuType);

			// 選択したL&Fを取得する
			User user = TLoginInfo.getUser();
			// 選択したL&F Typeを保存
			user.setMenuType(menuType);

			// Look and Feelの設定
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, LookAndFeelColor.get(user.getLfColorType()));

			// フレーム形式のプログラムL&Fも設定
			for (TPanelBusiness panel : frameProgram.values()) {
				TUIManager.setUI(panel, type, LookAndFeelColor.get(user.getLfColorType()));
			}

			// 1.0のスプレッドシートの色を戻す
			for (TAppletClientBase ctrl : selectedProgram.values()) {
				resetColor(ctrl.getPanel());
			}

			request(UserManager.class, "entryLookandFeel", user);

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}
	}

	/**
	 * フォントサイズコンボ変更処理
	 */
	public void changedFontSizeComboBox() {
		changedFontSizeComboBox(true);
	}

	/**
	 * フォントサイズコンボ変更処理
	 * 
	 * @param doSave true:保存処理を行う
	 */
	public void changedFontSizeComboBox(boolean doSave) {

		try {

			int fontSize = Util.avoidNullAsInt(mainView.ctrlFontSize.getSelectedItemValue());

			TUIManager.setLookAndFeel(LookAndFeelType.MINT.value);

			// fontMap初期化
			TUIManager.initDefaultFontMap(fontSize);

			// Look and Feelの設定
			LookAndFeelType type = TUIManager.getLookAndFeelType();
			TUIManager.setUI(mainView, type, LookAndFeelColor.get(TLoginInfo.getUser().getLfColorType()));

			if (doSave) {
				// 成功なら、保存
				setSaveValue("ui.font.size", fontSize);
				saveFile();
			}

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		}

	}

	/**
	 * プリンター設定ボタン押下
	 */
	public void btnPrintSetup_Click() {
		selectPrinter();
	}

	/**
	 * プリンタ選択
	 */
	public void selectPrinter() {

		try {
			PrinterJob printJob = PrinterJob.getPrinterJob();

			PrintService service = TClientLoginInfo.getPrintService();
			if (service != null) {
				printJob.setPrintService(service);
			}

			if (printJob.printDialog()) {

				PrintService selectService = printJob.getPrintService();

				TClientLoginInfo.setPrintService(selectService);

				if (service != null && service.equals(selectService)) {
					return;
				}

				String printName = selectService.getName();
				User user = new User();
				user.setCompanyCode(getCompanyCode());
				user.setCode(getUserCode());
				user.setPrinterName(printName);

				// 登録
				request(getPrinterModelClass(), "updatePrinter", user);

				// ログイン情報更新
				TLoginInfo.getUser().setPrinterName(printName);

			}

		} catch (Exception ex) {
			errorHandler(ex);
		}
	}

	/**
	 * Servletクラスを返す
	 * 
	 * @return UserManager
	 */
	protected Class getPrinterModelClass() {
		return UserManager.class;
	}

	/**
	 * パスワード変更ボタン押下
	 */
	public void btnPassword_Click() {

		TPasswordCtrl ctrl = createPasswordCtrl();
		ctrl.start();

	}

	/**
	 * バージョンボタン押下
	 */
	public void btnVersion_Click() {
		TVersionDialog dialog = new TVersionDialog(mainView);
		dialog.setLocationRelativeTo(null);
		dialog.setVisible(true);
	}

	/**
	 * ログアウトボタン押下
	 */
	public void btnLogOut_Click() {
		// 確認メッセージ
		if (!showConfirmMessage(mainView, "Q90001")) {
			return;
		}

		String tmpVersion = jarVersion; // 一旦退避
		jarVersion = ""; // ログアウトはチェック不要

		try {

			// 排他解除
			for (int i = mainView.mainTab.getTabCount() - 1; i >= 0; i--) { // 逆順
				closeProgramE(mainView.mainTab.getComponentAt(i));
			}

			// フレーム形式のプログラムも排他解除
			TPanelBusiness[] pnls = frameProgram.values().toArray(new TPanelBusiness[0]);
			for (int i = pnls.length - 1; i >= 0; i--) { // 逆順
				TPanelBusiness panel = pnls[i];
				closeProgramE(panel);
			}

			// フレーム非表示
			for (TFrame frame : frames.values()) {
				frame.setVisible(false);
			}

			// カスタマイズフレーム非表示
			for (TFrame frame : customerizeFrames) {
				frame.setVisible(false);
			}

			// ログアウトログ
			logE(getCompany(), getUser(), null, "OUT");

			// ログイン解除
			request(UserExclusiveManager.class, "cancelExclude");

		} catch (Exception ex) {
			errorHandler(mainView, ex);
		} finally {

			// ログイン情報クリア
			TLoginInfo.setCompany(null);
			TLoginInfo.setUser(null);
			TClientLoginInfo.getInstance().setUserLanguage(System.getProperty("user.language"));

			// 閉じる
			mainView.setVisible(false);
			mainView.dispose();
			jarVersion = tmpVersion;

			// ログインコントローラ起動
			TLoginCtrl ctrl = createLoginController();

			// ログイン開始
			ctrl.start();
		}
	}

	/**
	 * コントローラ生成
	 * 
	 * @return コントローラ
	 */
	public TLoginCtrl createLoginController() {
		return new TLoginCtrl();
	}

	/**
	 * パスワード変更コントローラ生成
	 * 
	 * @return パスワード変更コントローラ
	 */
	protected TPasswordCtrl createPasswordCtrl() {
		return new TPasswordCtrl(mainView);
	}

	/**
	 * メイン画面のヘッダー初期化
	 */
	protected void initMainViewHeader() {

		// ロゴ
		mainView.logo.setIcon(getCompanyLogo());

		if (!isDirectPrint) {
			mainView.btnPrintSetup.setVisible(false);
		}

		changeTime();
		createWatchThread();

		// ログイン情報の更新
		setupLoginInfo();
	}

	/**
	 * 会社のアイコンを返す
	 * 
	 * @return 会社のアイコン
	 */
	protected Icon getCompanyLogo() {

		String logoFilePath = ClientConfig.getProperty("logo.file.name");
		Icon logo = ResourceUtil.getImage(this.getClass(), "images/" + logoFilePath);
		return logo;

	}

	/**
	 * ログイン情報の更新
	 */
	public void setupLoginInfo() {

		// 会社名
		mainView.lblCompanyName.setText(TLoginInfo.getCompany().getName());

		// ログインユーザーの所属部門とユーザー名
		mainView.lblUserName.setText(TLoginInfo.getUser().getDepartment().getName() + "    "
			+ TLoginInfo.getUser().getName());

		// 会社カラー
		if (TLoginInfo.getCompany().getColor() != null) {
			mainView.pnlCompanyColor.setStartColor(TLoginInfo.getCompany().getColor());
		}

		Color companyColor = getCompanyColor();
		if (companyColor != null) {
			mainView.pnlCompanyColor.setStartColor(companyColor);
		}

		Object obj = getSaveValue("ui.font.size");
		if (obj != null) {
			if (obj instanceof Integer) {
				mainView.ctrlFontSize.setSelectedItemValue(obj);
				changedFontSizeComboBox(false);
			}
		}
	}

	/**
	 * @return 保存ファイルキー
	 */
	protected String getSaveFileKey() {
		return "ui_map_" + TLoginCtrl.getClientSaveKey() + ".log";
	}

	/**
	 * 各種保存
	 */
	protected void saveFile() {
		String fileName = getSaveFileKey();
		String path = SystemUtil.getAisSettingDir();
		FileUtil.saveObject(saveMap, path + File.separator + fileName);
	}

	/**
	 * @param key
	 * @return 保存値の取得
	 */
	public Object getSaveValue(String key) {
		if (saveMap == null) {
			return null;
		}
		return saveMap.get(key);
	}

	/**
	 * @param key キー
	 * @param value 保存値
	 */
	public void setSaveValue(String key, Object value) {
		if (saveMap == null) {
			saveMap = new LinkedHashMap<String, Object>();
		}
		saveMap.put(key, value);
	}

	/**
	 * 会社カラー取得
	 * 
	 * @return 会社カラー
	 */
	public static Color getCompanyColor() {
		try {
			String[] rgb = StringUtil.split(ClientConfig.getProperty("trans.main.company.color"), ",");

			int r = Integer.parseInt(rgb[0]);
			int g = Integer.parseInt(rgb[1]);
			int b = Integer.parseInt(rgb[2]);

			return new Color(r, g, b);
		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * プログラムツリー生成
	 * 
	 * @throws Exception
	 */
	protected void createMenuTree() throws Exception {

		List<SystemizedProgram> prgGroupList = null;
		if (isUseMenuMaster) {
			prgGroupList = getMenuPrograms(TLoginInfo.getCompany(), TLoginInfo.getUser());
		} else {

			// TODO:プログラムマスタによるメニュー構築⇒削除予定
			prgGroupList = getMenuProgramsOld(TLoginInfo.getCompany(), TLoginInfo.getUser());
		}

		if (prgGroupList == null || prgGroupList.isEmpty()) {
			return;
		}

		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.BOTH;
		gc.weightx = 1.0d;
		gc.weighty = 1.0d;
		gc.anchor = GridBagConstraints.NORTHWEST;
		gc.insets = new Insets(5, 5, 5, 0);

		for (SystemizedProgram prgGroup : prgGroupList) {

			// タブに属するプログラムを配置
			TMenuTreeNode root = new TMenuTreeNode();
			root.setPrgGroup(prgGroup);

			if (prgGroup != null) {
				String sysCode = prgGroup.getProgramGroupCode();

				List<Class> clsList = getMainInitialClasses("trans.menu.tab.class." + sysCode + ".");
				List<TMainInitialInterface> initList = new ArrayList<TMainInitialInterface>();
				if (clsList != null) {
					for (Class clazz : clsList) {
						try {
							Object obj = clazz.newInstance();
							if (obj instanceof TMainInitialInterface) {
								// 初期化処理
								TMainInitialInterface clz = (TMainInitialInterface) obj;
								clz.init();

								initList.add(clz);
							}
						} catch (Exception e) {
							// エラーなし
							e.printStackTrace();
						}
					}
				}
				tabLinkInstanceMap.put(sysCode, initList);
			}

			if (isUseMenuMaster) {
				// メニューリスト
				List<MenuDisp> menuList = prgGroup.getMenuDisp();

				// ルートとなるレコードを取得
				List<MenuDisp> rootMenuList = this.getRootMenus(menuList);

				// ルートを元から削除する
				for (MenuDisp prg : rootMenuList) {
					menuList.remove(prg);
				}

				// ルート以下のTreeを構築
				for (MenuDisp parent : rootMenuList) {
					TTreeItem item = createTreeItem(parent);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);

					// 親コードと紐付いている子を設定
					if (makeMenuNode(menuList, treeNode, parent.getCode())) {
						root.add(treeNode);
					}

				}

				// 親がないメニューを表示
				for (MenuDisp prg : menuList) {

					// ブランクタブの場合、構築不要
					if (prg.getName() == null) {
						continue;
					}

					TTreeItem item = createTreeItem(prg);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);
					root.add(treeNode);
				}

			} else {

				// TODO:プログラムマスタによるメニュー構築⇒削除予定

				// メニューリスト
				List<Program> menuList = prgGroup.getPrograms();

				// ルートとなるレコードを取得
				List<Program> rootMenuList = this.getRootMenusOld(menuList);

				// ルートを元から削除する
				for (Program prg : rootMenuList) {
					menuList.remove(prg);
				}

				// ルート以下のTreeを構築
				for (Program parent : rootMenuList) {
					TTreeItem item = createTreeItemOld(parent);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);

					// 親コードと紐付いている子を設定
					if (makeMenuNodeOld(menuList, treeNode, parent.getCode())) {
						root.add(treeNode);
					}
				}

				// 親がないメニューを表示
				for (Program prg : menuList) {
					TTreeItem item = createTreeItemOld(prg);
					TMenuTreeNode treeNode = new TMenuTreeNode(item);
					root.add(treeNode);
				}
			}

			// 当該ルートの子ノードが存在してない場合、タブの追加不要
			if (root.getChildCount() > 0) {
				// プログラムグループタブ追加
				TPanel pnl = new TPanel();
				pnl.setLayout(new GridBagLayout());
				pnl.setBackground(Color.white);
				TScrollPane sp = createScrollPane(pnl);
				sp.getVerticalScrollBar().setUnitIncrement(40);

				mainView.menuTab.addTab(getTabChar(prgGroup.getProgramGroupName()), sp);
				mainView.menuTab.setBackgroundAt(mainView.menuTab.getTabCount() - 1, prgGroup.getMenuColor());

				TTree tree = createTree(root);
				if (ClientConfig.isFlagOn("trans.menu.default.expand.all")) {
					tree.expandAll();
				}
				tree.setRootVisible(false);
				mainView.menuTrees.add(tree);
				pnl.add(tree, gc);
			}
		}
	}

	/**
	 * ツリーの作成
	 * 
	 * @param root
	 * @return ツリー
	 */
	protected TTree createTree(TMenuTreeNode root) {
		return new TToolTipTree(root);
	}

	/**
	 * Tree構築
	 * 
	 * @param prgList 元ネタ
	 * @param parent 親フォルダ名
	 * @param code 親コード
	 * @return true:子項目あり、false:子項目なし
	 */
	protected boolean makeMenuNode(List<MenuDisp> prgList, TMenuTreeNode parent, String code) {

		List<MenuDisp> children = this.getChildrenMenus(prgList, code);

		// ヒットした子要素を元から削除する
		for (MenuDisp prg : children) {
			prgList.remove(prg);
		}

		// 子要素の階層構築
		for (MenuDisp child : children) {

			TTreeItem item = createTreeItem(child);
			TMenuTreeNode itemNew = new TMenuTreeNode(item);

			// メニューの場合、さらにその下の階層を構築
			if (child.isMenu()) {
				if (!makeMenuNode(prgList, itemNew, child.getCode())) {
					continue;
				}
			}

			parent.add(itemNew);
		}

		if (parent.getChildCount() == 0) {
			return false;
		}

		return true;
	}

	/**
	 * ツリー項目の作成
	 * 
	 * @param prg
	 * @return ツリー項目
	 */
	protected TTreeItem createTreeItem(MenuDisp prg) {

		// メニュー表示名は設定ファイルに従う
		String name = prg.getName();
		if (isShowProgramCode && !prg.isMenu()) {
			name = prg.getCode() + " " + prg.getName();
		}

		TTreeItem item = new TTreeItem(name, prg);
		return item;
	}

	/**
	 * ルートノード要素だけを抽出する
	 * 
	 * @param prgList 対象リスト
	 * @return ルートのプログラムコード配列
	 */
	protected List<MenuDisp> getRootMenus(List<MenuDisp> prgList) {
		List<MenuDisp> parentList = new LinkedList<MenuDisp>();

		for (MenuDisp prg : prgList) {
			String parntCode = prg.getParentCode();

			// 親プログラムコードが設定されていない場合、ルートとする
			if (Util.isNullOrEmpty(parntCode) && prg.isMenu()) {
				parentList.add(prg);
			}
		}

		return parentList;
	}

	/**
	 * 指定した親コードを持つメニューだけを抽出する
	 * 
	 * @param prgList メニューリスト
	 * @param parentCode 親コード
	 * @return 子メニューリスト
	 */
	protected List<MenuDisp> getChildrenMenus(List<MenuDisp> prgList, String parentCode) {
		List<MenuDisp> childList = new LinkedList<MenuDisp>();

		for (MenuDisp prg : prgList) {
			// 親コードと子の親プログラムコードが一致する場合
			if (parentCode.equals(prg.getParentCode())) {
				childList.add(prg);
			}
		}

		return childList;
	}

	/**
	 * 時刻スレッドを生成
	 */
	public void createWatchThread() {

		Thread t = new Thread(new Runnable() {

			public void run() {
				try {
					while (true) {
						Thread.sleep(1000);
						changeTime();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		});
		t.start();

	}

	/**
	 * ヘッダーの時刻を最新に変更する
	 */
	public void changeTime() {

		String date = DateUtil.toYMDHMString(new Date());
		mainView.lblTime.setText(date);

	}

	/**
	 * 指定会社、ユーザーが使用可能なプログラム体系を返す
	 * 
	 * @param company 会社
	 * @param user ユーザー
	 * @return 指定会社、ユーザーが使用可能なプログラム体系
	 * @throws TException
	 */
	@SuppressWarnings("unchecked")
	public List<SystemizedProgram> getMenuPrograms(Company company, User user) throws TException {

		MenuSearchCondition condition = new MenuSearchCondition();
		condition.setCompanyCode(company.getCode());
		condition.setProgramRoleCode(user.getProgramRole().getCode());

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(MenuManager.class, "getSystemizedProgram",
			condition);

		return list;

	}

	/**
	 * Tree構築
	 * 
	 * @param prgList 元ネタ
	 * @param parent 親フォルダ名
	 * @param code 親コード
	 * @return true:子項目あり、false:子項目なし
	 */
	protected boolean makeMenuNodeOld(List<Program> prgList, TMenuTreeNode parent, String code) {

		List<Program> children = this.getChildrenMenusOld(prgList, code);

		// ヒットした子要素を元から削除する
		for (Program prg : children) {
			prgList.remove(prg);
		}

		// 子要素の階層構築
		for (Program child : children) {

			TTreeItem item = createTreeItemOld(child);
			TMenuTreeNode itemNew = new TMenuTreeNode(item);

			// メニューの場合、さらにその下の階層を構築
			if (child.isMenu()) {
				if (!makeMenuNodeOld(prgList, itemNew, child.getCode())) {
					continue;
				}
			}

			parent.add(itemNew);
		}

		if (parent.getChildCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * ツリー項目の作成
	 * 
	 * @param prg
	 * @return ツリー項目
	 */
	protected TTreeItem createTreeItemOld(Program prg) {

		// メニュー表示名は設定ファイルに従う
		String name = prg.getName();
		if (isShowProgramCode && !prg.isMenu()) {
			name = prg.getCode() + " " + prg.getName();
		}

		TTreeItem item = new TTreeItem(name, prg);
		return item;
	}

	/**
	 * ルートノード要素だけを抽出する
	 * 
	 * @param prgList 対象リスト
	 * @return ルートのプログラムコード配列
	 */
	protected List<Program> getRootMenusOld(List<Program> prgList) {
		List<Program> parentList = new LinkedList<Program>();

		for (Program prg : prgList) {
			String parntCode = prg.getParentPrgCode();

			// 親プログラムコードが設定されていない場合、ルートとする
			if (Util.isNullOrEmpty(parntCode) && prg.isMenu()) {
				parentList.add(prg);
			}
		}

		return parentList;
	}

	/**
	 * 指定した親コードを持つメニューだけを抽出する
	 * 
	 * @param prgList メニューリスト
	 * @param parentCode 親コード
	 * @return 子メニューリスト
	 */
	protected List<Program> getChildrenMenusOld(List<Program> prgList, String parentCode) {
		List<Program> childList = new LinkedList<Program>();

		for (Program prg : prgList) {
			// 親コードと子の親プログラムコードが一致する場合
			if (parentCode.equals(prg.getParentPrgCode())) {
				childList.add(prg);
			}
		}

		return childList;
	}

	/**
	 * 指定会社、ユーザーが使用可能なプログラム体系を返す
	 * 
	 * @param company 会社
	 * @param user ユーザー
	 * @return 指定会社、ユーザーが使用可能なプログラム体系
	 * @throws TException
	 */
	public List<SystemizedProgram> getMenuProgramsOld(Company company, User user) throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(company.getCode());
		condition.setProgramRoleCode(user.getProgramRole().getCode());

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(ProgramManager.class, "getSystemizedProgram",
			condition);

		return list;

	}

	/**
	 * メニュータブにセットする文字列を返す
	 * 
	 * @param title タイトル
	 * @return メニュータブにセットする文字列
	 */
	protected String getTabChar(String title) {
		// 英語はそのまま
		if (getUser().isEnglish()) {

			// 最大バイト桁数取得
			if (title.getBytes().length > maxMenuTitleLength) {
				maxMenuTitleLength = title.getBytes().length;
			}
			return title;
		}

		String rt = "<html><br>";

		for (int i = 0; i < title.length(); i++) {
			rt = rt + "<center>" + title.charAt(i) + "</center>";
		}
		rt = rt + "<br>";

		return rt;
	}
}

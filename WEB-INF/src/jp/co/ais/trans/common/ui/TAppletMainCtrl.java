package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.tree.*;

import sun.applet.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.client.http.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.server.dao.*;
import jp.co.ais.trans.common.util.*;

/**
 * メインアプレットコントロール
 * 
 * @author mizoguchi
 */
public class TAppletMainCtrl extends TAppletClientBase implements TreeSelectionListener, ChangeListener {

	/** マルチウィンドウ起動上限数 */
	private static final int MAX_WINDOW = ClientConfig.getMaxWindowCount();

	/** 曜日ラベル */
	private static final String[] CALENDAR_WEEK_IDS = { "C90001", "C90002", "C90003", "C90004", "C90005", "C90006",
			"C90007" };

	/** ポップアップメニューラベル */
	private static final String[] TXT_POPUP_IDS = { "C90008", "C90009", "C90010", "C90011" };

	/** タブの並び順(システム区分コードのリスト) */
	protected static List<String> menuTabOrderList = Arrays.asList(ClientConfig.getMenuTabOrder());

	/** プログラム切り替え通知用 */
	protected TRequestProgram prgRequest = new TRequestProgram(this);

	/** メインアプレット */
	protected TAppletMain appMain;

	/** 選択中のタブNo */
	protected int tabNum = -1;

	/** 表示中業務パネル */
	protected JPanel currentPanel = null;

	/** フレームで起動中のプログラムコード */
	protected String runFramePrgCode = "";

	/** マルチウィンドウモードかどうか */
	private boolean isMultiWindowMode = MAX_WINDOW > 0;

	/** ウィンドウで起動中のプログラムコード */
	protected Map<String, Frame> runWindowPrograms = new TreeMap<String, Frame>();

	/** Tree選択中のタブNo */
	protected int runFrameTabNum;

	/** Tree選択中のノード */
	protected TreePath runFrameTreePath;

	/**
	 * コンストラクタ
	 * 
	 * @param appMain メインApplet
	 */
	public TAppletMainCtrl(TAppletMain appMain) {
		this.appMain = appMain;
	}

	/**
	 * 別ウィンドウの画面サイズ
	 * 
	 * @return サイズ
	 */
	protected Dimension getWindowSize() {
		// メイン画面サイズから取得
		Dimension dim = appMain.getSize();
		return new Dimension(dim.width - appMain.menuWidthSize + 10, dim.height + 30);
	}

	/**
	 * 初期化処理.
	 * 
	 * @param sid セッションID
	 * @param kaiCode ログイン会社コード
	 * @param usrCode ログインユーザコード
	 */
	public void init(String sid, String kaiCode, String usrCode) {
		try {

			// 開発用
			if (Util.isNullOrEmpty(kaiCode)) {
				kaiCode = TClientLoginInfo.getInstance().getCompanyCode();
			}
			if (Util.isNullOrEmpty(usrCode)) {
				usrCode = TClientLoginInfo.getInstance().getUserCode();
			}

			// ユーザー情報の保持
			TClientLoginInfo.setSessionID(sid);
			TClientLoginInfo.getInstance().setCompanyCode(kaiCode);
			TClientLoginInfo.getInstance().setUserCode(usrCode);

			TClientLoginInfo.reflesh();

			// ログインの通知(セッションでIPアドレス保持の為、ユーザ情報構築の後)
			super.addSendValues("flag", "noticeLogin");
			if (!super.request(ClientConfig.getRootDirectory() + "/login")) {
				errorHandler(appMain);
			}

			// 画面デフォルト設定
			TUIManager.setUILocale(TClientLoginInfo.getInstance().getUserLocale());
			JOptionPane.setRootFrame(this.appMain.getParentFrame());

			// カレンダーの曜日設定
			String lang = TClientLoginInfo.getInstance().getUserLanguage();
			TCalendar.setDayOfWeekWords(MessageUtil.getWordList(lang, CALENDAR_WEEK_IDS));
			TGuiUtil.setLightPopupMenuWords(MessageUtil.getWordList(lang, TXT_POPUP_IDS));

			// メニュー可変
			if (ClientConfig.isMenuSplit()) {
				this.appMain.initComponentsSplit(isMultiWindowMode);

			} else {
				this.appMain.initComponents(isMultiWindowMode);
			}

			this.makeMenuTree(appMain.tabbedPane);

			this.setIEFrameWindowListener();

			// タブ制御、タブ移動ループをIEに制御を渡さないようにするために必要。
			this.appMain.setFocusCycleRoot(true);

		} catch (Exception e) {
			errorHandler(this.appMain, e, "E00009");
		}
	}

	/**
	 * ツリーに必要なデータの取得
	 * 
	 * @param tabbedPane 反映先のタブパネル
	 * @throws IOException データ取得失敗
	 */
	protected void makeMenuTree(JTabbedPane tabbedPane) throws IOException {

		// メニューリクエスト
		TRequestMenuManager menuRequest = new TRequestMenuManager(this);

		TUserInfo userInfo = TClientLoginInfo.getInstance();
		if (!menuRequest.request(userInfo.getCompanyCode(), userInfo.getUserCode())) {
			errorHandler(appMain);
			return;
		}

		// サブシステム毎に分ける
		Map<String, List> sysMap = new TreeMap<String, List>();

		List list = menuRequest.getMenuList();
		for (Iterator iter = list.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			String sysCode = element.getSYS_CODE();
			if (!sysMap.containsKey(sysCode)) {
				sysMap.put(sysCode, new LinkedList<Object>());
			}

			sysMap.get(sysCode).add(element);
		}

		// サブシステムのタブ順対応
		Object[] objs = new Object[menuTabOrderList.size()];

		// サブシステム毎にTreeを構築
		for (Iterator iter = sysMap.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			List menuList = sysMap.get(key);

			MenuTreeNode root = new MenuTreeNode();

			// ルートコードを判別
			MenuBean[] parents = this.getRootMenus(menuList);

			// ルートコード以下のTreeを構築
			for (int i = 0; i < parents.length; i++) {
				MenuBean parent = parents[i];

				MenuTreeNode category = new MenuTreeNode(parent.getPRG_CODE(), parent.getPRG_NAME(), parent.getCOM());
				makeMenuNode(category, menuList);

				root.add(category);

				// プログラムの無いメニューを削除
				removeNotChildNode(root);
			}

			int index = menuTabOrderList.indexOf(key);

			if (index != -1) {
				objs[index] = new Object[] { key, root };
			}
		}

		// タブ順を考えてAddTab
		for (Object object : objs) {
			if (object == null) {
				continue;
			}
			Object[] keyRoot = (Object[]) object;

			TreeNode root = (TreeNode) keyRoot[1];

			if (root.getChildCount() != 0) {
				addTab(tabbedPane, (String) keyRoot[0], root);
			}
		}
	}

	/**
	 * Tree構築
	 * 
	 * @param parent 親コード
	 * @param menuList 元ネタ
	 */
	protected void makeMenuNode(MenuTreeNode parent, List<MenuBean> menuList) {

		MenuBean[] children = getChildrenMenus(parent.getProgramCode(), menuList);
		for (int i = 0; i < children.length; i++) {
			MenuBean child = children[i];

			String panelName = child.getLD_NAME();

			MenuTreeNode item;
			if (Util.isNullOrEmpty(panelName)) {
				item = new MenuTreeNode(child.getPRG_CODE(), child.getPRG_NAME(), child.getCOM());
			} else {
				item = new MenuTreeNode(child.getPRG_CODE(), child.getPRG_NAME(), child.getCOM(), panelName, child
					.getKEN());
			}

			if (parent.isMenu) {
				makeMenuNode(item, menuList);
			}

			parent.add(item);
		}
	}

	/**
	 * メニューで子ノードを持たないノードを削除する
	 * 
	 * @param node 対象ノード
	 */
	protected void removeNotChildNode(MenuTreeNode node) {

		int count = node.getChildCount();
		for (int i = count - 1; i >= 0; i--) {
			MenuTreeNode child = (MenuTreeNode) node.getChildAt(i);

			if (!child.isMenu) {
				continue;
			}

			removeNotChildNode(child);

			if (child.getChildCount() == 0) {
				node.remove(child);
			}
		}
	}

	/**
	 * リスト上からルートノード要素だけを抽出する
	 * 
	 * @param menuList 対象リスト
	 * @return ルートのプログラムコード配列
	 */
	protected MenuBean[] getRootMenus(List menuList) {
		List<MenuBean> parentList = new LinkedList<MenuBean>();

		for (Iterator iter = menuList.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			String parntCode = element.getPARENT_PRG_CODE();
			if (Util.isNullOrEmpty(parntCode)) {
				parentList.add(element);
			}
		}

		return parentList.toArray(new MenuBean[parentList.size()]);
	}

	/**
	 * リスト上から、指定した親コードを持つメニューだけを抽出する
	 * 
	 * @param parentCode 親コード
	 * @param menuList メニューリスト
	 * @return 子メニューリスト
	 */
	protected MenuBean[] getChildrenMenus(String parentCode, List<MenuBean> menuList) {

		List<MenuBean> childList = new LinkedList<MenuBean>();

		for (Iterator iter = menuList.iterator(); iter.hasNext();) {
			MenuBean element = (MenuBean) iter.next();
			if (parentCode.equals(element.getPARENT_PRG_CODE())) {
				childList.add(element);
			}
		}

		return childList.toArray(new MenuBean[childList.size()]);
	}

	/**
	 * メニューツリー選択イベント. パネルの切り替え、排他制御解除を行う
	 */
	public void valueChanged(TreeSelectionEvent e) {

		try {
			// 選択されている要素を取得します。
			JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getSelectedComponent();

			JTree tree = (JTree) scrollpane.getViewport().getComponent(0);

			// ツリーを選択したときの入力チェックを行わないように設定する。
			tree.setVerifyInputWhenFocusTarget(false);
			final MenuTreeNode current = (MenuTreeNode) tree.getLastSelectedPathComponent();

			// プログラムノードからメニューノードを選択すると一度選択解除をはさむため、nullが返ってくる
			// メニューフォルダノードか、プログラムノードか判断
			if (current == null || current.isMenu()) {
				return;
			}

			String prgCode = current.getProgramCode();

			// ユーザー情報から権限レベル情報を取得
			TUserInfo userInfo = TClientLoginInfo.getInstance();

			// ユーザー権限とプログラム権限の比較
			if (userInfo.getProcessLevel() > current.getProcessLevel()) {
				// ノードを押下しても反応無し
				ClientLogger.debug("no authenticate:" + prgCode);

				setCurrentSelectionTree();
				return;
			}

			// Frame上プログラム二重起動防止
			if (runFramePrgCode.equals(prgCode)) {
				ClientLogger.debug(prgCode + " has already been displaying in the frame.");
				return;
			}

			// Windowプログラム二重起動防止
			if (runWindowPrograms.containsKey(prgCode)) {
				ClientLogger.debug(prgCode + " has already been displaying in the window.");

				Frame frame = runWindowPrograms.get(prgCode);
				if (frame.getExtendedState() == Frame.ICONIFIED) {
					frame.setExtendedState(Frame.NORMAL);
				}
				frame.toFront();

				setCurrentSelectionTree();
				return;
			}

			// フォーカスを選択プログラムに当ててから、クラスをロード
			SwingUtilities.invokeLater(new Runnable() {

				public void run() {
					try {
						// WAITカーソル設定
						appMain.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

						// プログラム終了を通知(前のプログラムが存在する場合）
						if (!isShowProgramForWindow() && !"".equals(runFramePrgCode)) {
							if (isMultiWindowMode) {
								prgRequest.endPrg(runFramePrgCode);

							} else {
								prgRequest.end(runFramePrgCode);
							}
						}

						// 表示プログラムのロード
						loadControl(current);

						// プログラム開始の通知
						prgRequest.start(current.getProgramCode());

					} catch (Exception ex) {
						errorHandler(appMain, ex, "E00009");

					} finally {
						// 通常カーソルに戻す
						appMain.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
					}
				}
			});

		} catch (Exception ex) {
			errorHandler(this.appMain, ex, "E00009");
		}
	}

	/**
	 * クラスを読み込み、メインパネルに設定
	 * 
	 * @param className クラス名
	 */
	protected void loadControl(String className) {
		MenuTreeNode dummy = new MenuTreeNode();
		dummy.setPanelName(className);

		loadControl(dummy);
	}

	/**
	 * クラスを読み込み、メインパネルに設定
	 * 
	 * @param current 選択TreeNode
	 */
	@SuppressWarnings("deprecation")
	protected void loadControl(MenuTreeNode current) {

		// classをロード
		try {
			final String programCode = current.getProgramCode();

			// インスタンス化のためにプログラム情報を設定
			TClientProgramInfo prgInfoTmp = TClientProgramInfo.getInstance();
			prgInfoTmp.setProgramCode(programCode);
			prgInfoTmp.setProgramName(current.getProgramName());
			prgInfoTmp.setProgramName(current.getProgramShortName());
			prgInfoTmp.setProcessLevel(current.getProcessLevel());

			Class cl = this.getClass().getClassLoader().loadClass(current.getPanelName());

			TAppletClientBase ctrl = (TAppletClientBase) cl.newInstance();

			if (ctrl == null) {
				errorHandler(appMain.getParentFrame(), "panel control class not found.");
				return;
			}

			JPanel panel = ctrl.getPanel();
			if (panel == null) {
				errorHandler(appMain.getParentFrame(), "Panel  not found");
				return;
			}

			// 新プログラム起動前にGC
			System.gc();

			// 選択したプログラムの情報を、選択プログラム情報保持クラスに設定
			if (ctrl instanceof TPanelCtrlBase) {
				// Dialogで利用されるのを防ぐ為にPanelCtrlBaseに限定
				((TPanelCtrlBase) ctrl).setProgramInfo(prgInfoTmp.clone());
			}

			// プログラムオープン
			if (isShowProgramForWindow()) {
				// ウインドウモード
				TAppletWindow windowPanel = createTAppletWindow();
				windowPanel.detailPanel.add(panel);

				// プログラム名の表示
				windowPanel.lblProgramName.setText(current.getProgramName());

				JFrame frame = new JFrame(current.getProgramName());
				frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

				frame.add(windowPanel);

				// 画面サイズ
				frame.setSize(getWindowSize()); // メイン画面のメニュー抜きと同じサイズ

				frame.addWindowListener(new WindowAdapter() {

					public void windowClosed(WindowEvent e) {
						doWindowEnd(programCode);

						if (currentPanel != null) {
							currentPanel.requestFocusInWindow();
						}
					}
				});

				doWindowStart(programCode, frame);

				frame.setVisible(true);

			} else {
				// フレーム

				// パネル追加
				panel.setVisible(false);
				panel.setMaximumSize(new Dimension(Short.MAX_VALUE, Short.MAX_VALUE));
				GridBagConstraints gridBagConstraints = new GridBagConstraints();
				gridBagConstraints.fill = GridBagConstraints.BOTH;
				gridBagConstraints.weightx = 1;
				gridBagConstraints.weighty = 1;
				this.appMain.mainPanel.add(panel, gridBagConstraints);
				panel.setVisible(true);

				if (panel instanceof TPanelBusiness) {
					((TPanelBusiness) panel).setTabPolicy();
					((TPanelBusiness) panel).transferFocusTopItem();
				}

				boolean isFirstPanel = (currentPanel == null);

				// 直前パネルを削除
				if (!isFirstPanel) {
					currentPanel.setVisible(false);
					this.appMain.mainPanel.remove(currentPanel);
				}

				// 直前パネルに設定
				currentPanel = panel;
				currentPanel.setFocusable(true);

				// プログラム名の表示
				appMain.lblProgramName.setText(current.getProgramName());

				// 最初起動時、マウスリスナーを登録
				if (isFirstPanel) {

					MouseListener listener = new MouseAdapter() {

						public void mousePressed(MouseEvent e) {
							currentPanel.requestFocusInWindow();
						}
					};

					appMain.addMouseListener(listener);
					appMain.tabbedPane.addMouseListener(listener);
					appMain.mainPanel.addMouseListener(listener);
				}

				// プログラムのコードを起動重複チェック用に保持
				runFramePrgCode = programCode;

				// FrameのTree選択状態を保持する
				this.runFrameTabNum = tabNum;
				JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getSelectedComponent();
				JTree tree = (JTree) scrollpane.getViewport().getComponent(0);
				this.runFrameTreePath = tree.getSelectionPath();
			}

		} catch (Exception e) {
			errorHandler(this.appMain, e, "E00009");

		} finally {
			setCurrentSelectionTree();
		}
	}

	/**
	 * 別Windowのフレームクラスを返す
	 * 
	 * @return 別WindowFrame
	 */
	protected TAppletWindow createTAppletWindow() {
		return new TAppletWindow();
	}

	/**
	 * Frameに表示されているTreeNodeを選択状態にする
	 */
	protected void setCurrentSelectionTree() {

		if (runFrameTreePath == null) {
			return;
		}

		JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getComponentAt(tabNum);
		JTree tree = (JTree) scrollpane.getViewport().getComponent(0);

		if (tabNum == runFrameTabNum) {
			tree.setSelectionPath(runFrameTreePath);
		} else {
			tree.clearSelection();
		}
	}

	/**
	 * 親コンテナをたどり、java.awt.Window, java.awt.Appletを探す.
	 * 
	 * @param p 元コンテナ
	 * @return 親コンテナ
	 */
	public Container getParentRoot(Container p) {

		if (p == null) {
			return null;
		}

		for (; p.getParent() != null; p = p.getParent()) {
			if (p instanceof AppletViewer) {
				return p;
			}
		}
		return p;
	}

	/**
	 * プログラムのオープンモードを取得
	 * 
	 * @return true:ウインドウ false:フレーム
	 */
	protected boolean isShowProgramForWindow() {

		// フレームにプログラムがなければ強制的にフレームに表示。
		if (runFramePrgCode.equals("")) {
			return false;
		}

		// ウインドウが選択＆有効
		return appMain.rdoPrgWind.isSelected() && appMain.rdoPrgWind.isEnabled();
	}

	/**
	 * ウインドウを開いている時は制御ボタンを無効にする。
	 */
	protected void ctrlOpenModeEnabled() {
		if (runWindowPrograms.size() < MAX_WINDOW) {
			appMain.rdoPrgFrame.setEnabled(true);
			appMain.rdoPrgWind.setEnabled(true);

		} else {
			appMain.rdoPrgFrame.setEnabled(false);
			appMain.rdoPrgWind.setEnabled(false);
		}
	}

	/**
	 * ウィンドウプログラムの開始
	 * 
	 * @param prgCode プログラムコード
	 * @param frame 起動Frame
	 */
	protected void doWindowStart(String prgCode, Frame frame) {

		// プログラムのコードを起動重複チェック用に保持
		runWindowPrograms.put(prgCode, frame);

		ctrlOpenModeEnabled();
	}

	/**
	 * ウィンドウプログラムの終了
	 * 
	 * @param prgCode プログラムコード
	 */
	protected void doWindowEnd(String prgCode) {

		if (!runWindowPrograms.containsKey(prgCode)) {
			return;
		}

		try {
			prgRequest.endPrg(prgCode);

		} catch (IOException ex) {
			// 処理なし
		}

		runWindowPrograms.remove(prgCode);

		ctrlOpenModeEnabled();
	}

	/**
	 * ログアウト処理を行うJavaScriptの呼び出し
	 */
	public void logout() {
		try {
			String msgID = runWindowPrograms.isEmpty() ? "Q90001" : "Q90002";

			if (!showConfirmMessage(appMain, msgID)) {
				return;
			}

			// ウィンドウプログラムの終了通知
			String[] prgCodes = runWindowPrograms.keySet().toArray(new String[runWindowPrograms.size()]);
			for (String prgCode : prgCodes) {
				doWindowEnd(prgCode);
			}

			// フレームプログラム終了通知
			if (!"".equals(runFramePrgCode)) {
				prgRequest.end(runFramePrgCode);
			}

			// ログアウトの通知
			super.addSendValues("flag", "1");
			super.request(ClientConfig.getRootDirectory() + "/login");

			TAccessJScript jscript = new TAccessJScript();
			jscript.setApplet(appMain); // jscript access object に applet設定
			jscript.executeJScriptFunctionOnThread("logoutprocess", new String[0]);

		} catch (Exception ex) {
			ClientLogger.error(ex.getMessage(), ex);
			errorHandler(appMain);
		}
	}

	/**
	 * ×ボタンログアウト処理
	 */
	public void logoutForClose() {

		try {
			// ウィンドウプログラムの終了通知
			String[] prgCodes = runWindowPrograms.keySet().toArray(new String[runWindowPrograms.size()]);
			for (String prgCode : prgCodes) {
				doWindowEnd(prgCode);
			}

			// フレームプログラム終了通知
			if (!"".equals(runFramePrgCode)) {
				prgRequest.end(runFramePrgCode);
			}

			// ログアウトの通知
			super.addSendValues("flag", "1");
			super.request(ClientConfig.getRootDirectory() + "/login");

		} catch (IOException ex) {
			ClientLogger.error(ex.getMessage(), ex);
		}
	}

	/**
	 * パスワード変更ダイアログの呼び出し
	 */
	public void openDialog() {

		PasswordUpdateDialogCtrl dialogCtrl = new PasswordUpdateDialogCtrl(appMain.getParentFrame());
		dialogCtrl.show();
	}

	/**
	 * tree情報の入ったタブを tabbedPaneに追加
	 * 
	 * @param tabbedPane 反映先のタブ
	 * @param name タブ名称
	 * @param rootNode 反映Tree
	 */
	protected void addTab(JTabbedPane tabbedPane, String name, TreeNode rootNode) {
		TTree tree = new TTree(rootNode) {

			public String getToolTipText(MouseEvent evt) {
				if (getRowForLocation(evt.getX(), evt.getY()) == -1) {
					return null;
				}

				TreePath curPath = getPathForLocation(evt.getX(), evt.getY());
				return ((MenuTreeNode) curPath.getLastPathComponent()).getToolTipText();
			}
		};

		ToolTipManager.sharedInstance().registerComponent(tree);

		// treeをscrollPaneに設定
		JScrollPane scrollPane = new JScrollPane(tree);

		// tree設定
		tree.setRootVisible(false);
		tree.addTreeSelectionListener(this);
		tree.setShowsRootHandles(false);

		DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
		renderer.setOpenIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku07.gif"));
		renderer.setClosedIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku07.gif"));
		renderer.setLeafIcon(ResourceUtil.getImage(TAppletMain.class, "images/sikaku01.gif"));

		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);

		try {
			Icon icon = ResourceUtil.getImage(TAppletMain.class, "images/" + name + ClientConfig.getImageSuffix()
				+ ".png");

			tabbedPane.addTab(null, icon, scrollPane);

		} catch (TRuntimeException ex) {
			ClientLogger.error(getMessage(ex.getMessageID(), ex.getMessageArgs()), ex);

			tabbedPane.addTab(name, null, scrollPane);
		}
	}

	/**
	 * タブチェンジでそれまで選択していたタブのツリー選択を解除する
	 */
	public void stateChanged(ChangeEvent e) {
		if (tabNum != -1) {
			JScrollPane scrollpane = (JScrollPane) appMain.tabbedPane.getComponentAt(tabNum);
			JTree tree = (JTree) scrollpane.getViewport().getComponent(0);
			tree.clearSelection();
		}

		tabNum = appMain.tabbedPane.getSelectedIndex();

		setCurrentSelectionTree();
	}

	/**
	 * ×ボタン押下時
	 */
	protected void setIEFrameWindowListener() {

		Window window = (Frame) TGuiUtil.getParentFrameOrDialog(this.appMain);

		window.addWindowListener(new TWindowListener(null));
	}

	/**
	 * メニュー用Node
	 */
	public class MenuTreeNode extends DefaultMutableTreeNode {

		private String prgCode = "";

		private String prgName = "";

		private String panelName = "";

		private boolean isMenu = true;

		private int procLevel;

		private String comment;

		/**
		 * ルート用
		 */
		public MenuTreeNode() {
			super("");
		}

		/**
		 * メニュー用
		 * 
		 * @param programCode プログラムコード
		 * @param programName 表示名称
		 * @param comment コメント
		 */
		public MenuTreeNode(String programCode, String programName, String comment) {
			super(programName);

			this.prgCode = programCode;
			this.prgName = programName;
			this.comment = comment;

			// 文字が無い場合は、null（空白を入れると、変な表示が出る為）
			if (Util.isNullOrEmpty(comment)) {
				this.comment = null;
			}
		}

		/**
		 * 画面用
		 * 
		 * @param programCode プログラムコード
		 * @param programName 表示名称
		 * @param comment コメント
		 * @param panelName ロードパネル名
		 * @param procLevel プログラムレベル
		 */
		public MenuTreeNode(String programCode, String programName, String comment, String panelName, int procLevel) {
			this(programCode, programName, comment);

			if (ClientConfig.isShowPrgramCode()) {
				setUserObject(programCode + ":" + programName);
			}

			this.panelName = panelName;
			this.isMenu = false;
			this.procLevel = procLevel;
		}

		/**
		 * プログラムコード取得
		 * 
		 * @return プログラムコード
		 */
		public String getProgramCode() {
			return this.prgCode;
		}

		/**
		 * プログラム名称取得
		 * 
		 * @return プログラム名称
		 */
		public String getProgramName() {
			return this.prgName;
		}

		/**
		 * プログラム略称取得
		 * 
		 * @return プログラム略称
		 */
		public String getProgramShortName() {
			return this.prgName;
		}

		/**
		 * ロードパネル名取得
		 * 
		 * @return ロードパネル名
		 */
		public String getPanelName() {
			return this.panelName;
		}

		/**
		 * ロードパネル名取得
		 * 
		 * @param panelName ロードパネル名
		 */
		public void setPanelName(String panelName) {
			this.panelName = panelName;
		}

		/**
		 * メニューか画面かを判断する.
		 * 
		 * @return true: メニュー、false: 画面
		 */
		public boolean isMenu() {
			return this.isMenu;
		}

		/**
		 * プログラムへのアクセス権限レベル
		 * 
		 * @return アクセス権限レベル
		 */
		public int getProcessLevel() {
			return this.procLevel;
		}

		/**
		 * Tooltipテキスト（コメント）を返す.
		 * 
		 * @return Tooltipテキスト
		 */
		public String getToolTipText() {
			return this.comment;
		}
	}
}

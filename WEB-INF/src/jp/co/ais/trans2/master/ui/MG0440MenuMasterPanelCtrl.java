package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.program.ProgramTransferData.SourceType;

/**
 * メニュー表示マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0440MenuMasterPanelCtrl extends TController {

	/** 操作モード */
	public enum Mode {
		/** 新規 */
		NEW
	}

	/** 新規タブダミーコード */
	public static String dummyTabCode = "999";

	/** 新規フォルダダミーコード */
	public static String dummyFolderCode = "99999999";

	/** グループコード最大値 */
	public static String grpCodeMax = "99";

	/** グループコード最大桁数 */
	public static String grpCodeMaxFormat = "00";

	/** メニューコード最大値 */
	public static String menuCodeMax = "99999";

	/** メニューコード最大桁数 */
	public static String menuCodeMaxFormat = "00000";

	/** 検出用REGEX */
	protected final static String MENU_CODE_REGEX = "\\d+";

	/** 検出用PARTTEN */
	protected final static Pattern MENU_CODE_PATTERN = Pattern.compile(MENU_CODE_REGEX);

	/** 指示画面 */
	protected MG0440MenuMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0440MenuMasterDialog editView = null;

	/** tree -> spread : false、tree -> tree : true */
	protected boolean treeToTree = true;

	/** true:新規タブ */
	protected boolean isNewTab = false;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** 処理中ツリー(左側) */
	protected TDnDTree selectedTree = null;

	/** 新規タブ作成時のタブ名 */
	public String newTabName = null;

	/** タブの削除 */
	public String tabTitleDelete = null;

	/** タブの編集 */
	public String tabTitleModify = null;

	/** 新規フォルダ作成 */
	public String folderTitleNew = null;

	/** メニューの削除 */
	public String folderTitleDelete = null;

	/** 名前の変更 */
	public String folderTitleNameChange = null;

	/** 新規フォルダ作成時のフォルダ名 */
	public String newFolderName = null;

	/** タブ色ダイアログタイトル */
	public String colorDialogName = null;

	/** ルート名 */
	public String rootName = null;

	/**
	 * 処理を開始する
	 */
	@Override
	public void start() {

		try {

			// 指示画面生成
			createMainView();

			// 指示画面を初期化する
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * パネルの取得
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * 指示画面のファクトリ。新規に指示画面を生成し、イベントを定義する。
	 */
	protected void createMainView() {
		mainView = new MG0440MenuMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [タブ新規作成]ボタン押下
		mainView.btnNewTab.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNewTab_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 一覧ドラッグ＆ドロップイベント
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 一覧からドラッグデータの取得処理を行う
				List<ProgramTreeNode> nodeList = dragFromTable(evt);

				ProgramTransferData transferData = new ProgramTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TABLE);

				selectedTree = null;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// 一覧へドロップ処理を行う
				dropToTable(value);
			}

		});

		// メニュータブのマウスイベント
		mainView.menuTab.addMouseListener(new MouseAdapter() {

			/** メニュー */
			protected JPopupMenu popup = new JPopupMenu();

			/** クリックイベント */
			@Override
			public void mouseClicked(MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				// ダブルクリック処理
				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 2) {

					Point p = e.getPoint();
					int index = mainView.menuTab.indexAtLocation(p.x, p.y);

					if (index < 0) {
						return;
					}

					showTabEditDialog();

				} else if (SwingUtilities.isRightMouseButton(e)) {
					// 右クリック
					popup.removeAll();

					// タブの削除
					JMenuItem tabDelete = new JMenuItem(tabTitleDelete);
					tabDelete.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							int index = mainView.menuTab.getSelectedIndex();

							// 削除対象タブの項目を復元する
							JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(index);
							TDnDTree tree1 = (TDnDTree) panel.getViewport().getComponent(0);
							deleteTreeDataAndRestoreTable(tree1);

							mainView.menuTab.remove(index);
						}
					});

					// タブの編集
					JMenuItem tabModify = new JMenuItem(tabTitleModify);
					tabModify.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							showTabEditDialog();
						}
					});

					popup.add(tabDelete);
					popup.add(tabModify);

					popup.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可にする
		setMainButtonEnabled(false);

		// 単語初期化
		initWord();

		// 一覧設定処理
		initList();

		// プログラム階層設定処理
		initTree();

		// メインボタンを押下可にする
		setMainButtonEnabled(true);
	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * 単語初期化
	 */
	protected void initWord() {

		// 新規タブ作成時のタブ名
		newTabName = getWord("C11409"); // 新しいタブ

		// タブの削除
		tabTitleDelete = getWord("C11410"); // タブの削除

		// タブの編集
		tabTitleModify = getWord("C11411"); // タブの編集

		// 新規フォルダ作成
		folderTitleNew = getWord("C11412"); // 新規フォルダ作成

		// メニューの削除
		folderTitleDelete = getWord("C11405"); // メニューの削除

		// 名前の変更
		folderTitleNameChange = getWord("C11408"); // 名前の変更

		// 新規フォルダ作成時のフォルダ名
		newFolderName = getWord("C11407"); // 新しいフォルダ

		// タブ色ダイアログタイトル
		colorDialogName = getWord("C11413"); // タブ色

		// ルート名
		rootName = getWord("C11406"); // ルート

	}

	/**
	 * プログラムマスタよりデータを取得し、一覧に設定する。
	 */
	protected void initList() {

		try {

			// 一覧をクリアする
			mainView.dndTable.removeRow();

			// プログラム情報を取得
			ProgramSearchCondition condition = getSearchCondition();
			List<Program> list = getProgram(condition);

			// 検索条件に該当するプログラムが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				return;
			}

			// プログラム情報を一覧に表示する
			for (Program program : list) {
				mainView.dndTable.addRow(getProgramDataRow(program));
			}

			// 1行目を選択する
			mainView.dndTable.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * プログラムの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ProgramSearchCondition getSearchCondition() {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setExecutable(false);
		condition.setNotExecutable(false);
		condition.setWithoutMenu(true);

		condition.setValidTerm(Util.getCurrentDate());

		return condition;

	}

	/**
	 * 検索条件に該当するプログラムのリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するプログラムのリスト
	 * @throws Exception
	 */
	protected List<Program> getProgram(ProgramSearchCondition condition) throws Exception {

		List<Program> list = (List<Program>) request(getProgramModelClass(), "get", condition);

		return list;
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getProgramModelClass() {
		return ProgramManager.class;
	}

	/**
	 * プログラム情報を一覧に表示する形式に変換し返す
	 * 
	 * @param program プログラム
	 * @return 一覧に表示する形式に変換されたプログラム
	 */
	protected Object[] getProgramDataRow(Program program) {
		return new Object[] { program.getCode(), program.getNames(), program };
	}

	/**
	 * メニュー表示マスタよりデータを取得し、プログラム階層に設定する。
	 */
	protected void initTree() {

		try {

			// メニュー情報を取得
			MenuSearchCondition condition = getTreeSearchCondition();
			List<SystemizedProgram> list = getMenu(condition);

			// メニュー表示マスタが存在しない場合
			if (list == null || list.isEmpty()) {
				return;
			}

			for (SystemizedProgram prgGroup : list) {

				// メニュータブを追加する
				addNewTab(prgGroup);
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * メニューの検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected MenuSearchCondition getTreeSearchCondition() {

		MenuSearchCondition condition = new MenuSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setProgramRoleCode(getUser().getProgramRole().getCode());
		condition.setWithoutProgramRole(true);
		condition.setWithBlank(true);

		return condition;

	}

	/**
	 * 検索条件に該当するメニューのリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当するメニューのリスト
	 * @throws Exception
	 */
	protected List<SystemizedProgram> getMenu(MenuSearchCondition condition) throws Exception {

		List<SystemizedProgram> list = (List<SystemizedProgram>) request(getMenuModelClass(), "getSystemizedProgram",
			condition);

		return list;
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getMenuModelClass() {
		return MenuManager.class;
	}

	/**
	 * 一覧へドロップ処理
	 * 
	 * @param value ProgramTransferData 渡すプログラムノードデータ
	 */
	protected void dropToTable(Object value) {

		// 処理中ツリーがない場合、処理不要
		if (selectedTree == null) {
			return;
		}

		// 希望のプログラムノードデータじゃない場合、処理不要
		if (!(value instanceof ProgramTransferData)) {
			return;
		}

		ProgramTransferData transferData = (ProgramTransferData) value;

		// リストが0件の場合、処理不要
		if (transferData.getNodeList().isEmpty()) {
			return;
		}

		for (ProgramTreeNode node : transferData.getNodeList()) {

			// メニュー情報とプログラム情報がnullの場合、処理不要
			if (node.getMenuDisp() == null || node.getMenuDisp().getProgram() == null
				|| Util.isNullOrEmpty(node.getMenuDisp().getProgram().getCode()) || node.getMenuDisp().isMenu()) {
				continue;
			}

			// ツリーから削除(選択のみ)＆スプレッドへ復元
			deleteTreeDataAndRestoreTable(selectedTree);

		}

	}

	/**
	 * メニュータブ追加
	 * 
	 * @param prgGroup プログラム情報
	 */
	public void addNewTab(SystemizedProgram prgGroup) {

		final TDnDTree tree = new TDnDTree();

		JScrollPane sp = new JScrollPane();

		TGuiUtil.setComponentSize(sp, 270, 400);
		sp.getViewport().add(tree, null);

		// 新規タブのイベント追加
		addNewTabEvents(tree);

		List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

		String title = Util.avoidNullNT(prgGroup.getProgramGroupCode()) + " "
			+ Util.avoidNullNT(prgGroup.getProgramGroupName());

		if (Util.isNullOrEmpty(prgGroup) || Util.isNullOrEmpty(prgGroup.getMenuDisp())) {

			mainView.menuTab.addTab(title, sp);

			listDnDData = getRootMenus(null, prgGroup.getProgramGroupCode());

			// タブ選択を設定する。
			int tabSelect = mainView.menuTab.getTabCount() - 1;

			mainView.menuTab.setSelectedIndex(tabSelect);
			mainView.menuTab.setBackgroundAt(tabSelect,
				prgGroup.getMenuColor() == null ? Color.GRAY : prgGroup.getMenuColor());

		} else {

			// タブ選択を設定する。
			int tabSelect = mainView.menuTab.getTabCount();

			mainView.menuTab.addTab(title, sp);
			mainView.menuTab.setBackgroundAt(tabSelect, prgGroup.getMenuColor());

			listDnDData = getRootMenus(prgGroup.getMenuDisp(), prgGroup.getProgramGroupCode());

		}

		// プログラム階層表示
		setTreeData(listDnDData, tree);

	}

	/**
	 * 新規タブのイベント追加
	 * 
	 * @param tree
	 */
	protected void addNewTabEvents(final TDnDTree tree) {

		// プログラム階層マウスイベント
		tree.addMouseListener(new MouseAdapter() {

			/** メニュー */
			protected JPopupMenu popup = new JPopupMenu();

			/** クリックイベント */
			@Override
			public void mouseClicked(final MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				if (SwingUtilities.isRightMouseButton(e)) {
					// 右クリック
					popup.removeAll();

					// 新規フォルダ作成
					JMenuItem folderNew = new JMenuItem(folderTitleNew);
					folderNew.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							addNewFolder(tree, e);
						}
					});

					// メニューの削除
					JMenuItem folderDelete = new JMenuItem(folderTitleDelete);
					folderDelete.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							deleteTreeDataAndRestoreTable(tree);
						}
					});

					// 名前の変更
					JMenuItem folderNameChange = new JMenuItem(folderTitleNameChange);
					folderNameChange.addActionListener(new ActionListener() {

						public void actionPerformed(ActionEvent evt) {
							changeFolderName(tree);
						}
					});

					popup.add(folderNew);
					popup.add(folderDelete);
					popup.add(folderNameChange);

					popup.show(e.getComponent(), e.getX(), e.getY());

					// 右クリック時、マウスオーバーのノードのフォーカスを当てる
					tree.setSelectionRow(tree.getRowForLocation(e.getX(), e.getY()));
				}
			}
		});

		// プログラム階層ドラッグ＆ドロップイベント
		tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 処理中ツリーを一時保存する
				selectedTree = tree;

				List<ProgramTreeNode> nodeList = dragFromTree(evt, tree);
				ProgramTransferData transferData = new ProgramTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TREE);

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// ツリーへのドロップ処理
				dropToTree(evt, tree, value);
			}

			@Override
			public boolean isLeaf(TreeNode node) {
				if (node instanceof ProgramTreeNode) {

					ProgramTreeNode pnode = ((ProgramTreeNode) node);

					if (pnode.getMenuDisp() == null) {
						return true;
					}

					return !pnode.getMenuDisp().isMenu();

				} else {
					return node.isLeaf();
				}
			}
		});
	}

	/**
	 * ルートノードを作成。親コードを設定する。
	 * 
	 * @param menuList 対象リスト
	 * @param currentGroupCode グループコード
	 * @return ルートのプログラムコード配列
	 */
	public List<MenuDisp> getRootMenus(List<MenuDisp> menuList, String currentGroupCode) {
		List<MenuDisp> parentList = new LinkedList<MenuDisp>();

		// 親プログラムコード取得
		String parentCode = currentGroupCode + "00000";

		// ルートを作成（プログラム階層に表示されない）
		MenuDisp data = new MenuDisp();
		data.setParentCode("");
		data.setCode(parentCode);
		data.setName(rootName); // ルート
		data.setMenuDivision(MenuDivision.MENU);

		SystemClassification sys = new SystemClassification();
		sys.setCode(currentGroupCode);
		data.setSystemClassification(sys);

		parentList.add(data);

		if (menuList == null || menuList.isEmpty()) {
			return parentList;
		}

		for (MenuDisp menu : menuList) {

			String parntCode = menu.getParentCode();
			String code = menu.getCode();

			// 親プログラムコードが設定されていない場合、ルートとする
			if (Util.isNullOrEmpty(parntCode) && !code.equals(parentCode)) {
				menu.setParentCode(parentCode);
				parentList.add(menu);
			} else {
				parentList.add(menu);
			}
		}

		return parentList;
	}

	/**
	 * 名前の変更
	 * 
	 * @param tree プログラム階層リスト
	 */
	protected void changeFolderName(TDnDTree tree) {
		tree.startEditingAtPath(tree.getSelectionPath());
	}

	/**
	 * プログラム階層ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @param tree プログラム階層リスト
	 * @return TTeeの階層データ
	 */
	protected List<ProgramTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<ProgramTreeNode> listTreeNode = new ArrayList<ProgramTreeNode>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// drag位置にTreeNodeが存在しない場合、又はルートノードの場合、処理を終了
			Point pt = evt.getDragOrigin();
			TreePath checkPathCheck = tree.getPathForLocation(pt.x, pt.y);
			if (checkPathCheck == null || checkPathCheck.getParentPath() == null) {
				return null;
			}

			// 左クリック時
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				// drag位置にあるTreeNodeを取得する。
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					TreePath[] paths = tree.getSelectionPaths();
					if (paths == null) {
						return null;
					}

					// 選択されたパスすべて含む
					for (TreePath path : paths) {

						ProgramTreeNode node = (ProgramTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							ProgramTreeNode eachNode = (ProgramTreeNode) enumNode.nextElement();
							listTreeNode.add(eachNode);
						}
					}
				}
			}

			// Tree → Spreadにドラック＆ドロップしてないか判定フラグを初期化。
			treeToTree = true;
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		}

		return listTreeNode;
	}

	/**
	 * プログラム階層ドロップ処理
	 * 
	 * @param evt ドロップターゲットイベント
	 * @param tree プログラム階層リスト
	 * @param value 指定されたデータ
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// 転送をチェック
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop位置にあるTreeNodeを取得する。ドロップ位置にノードが存在しない場合、処理を終了する。
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			ProgramTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (ProgramTreeNode) model.getRoot();
			} else {
				targetNode = (ProgramTreeNode) targetPath.getLastPathComponent();
			}

			// 移動先ノード
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// 移動先パス取得

			// 変更済みのノードリスト（親区別用）
			List<ProgramTreeNode> insertedNodeList = new ArrayList<ProgramTreeNode>();

			if (!(value instanceof ProgramTransferData)) {
				return;
			}

			// 渡すプログラムノードデータ
			ProgramTransferData transferData = (ProgramTransferData) value;

			// プログラムノードリスト
			List<ProgramTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// チェック、目標はドラッグ中であれば、ドロップ不要
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (ProgramTreeNode node : list) {

				// 自分の親ノード
				ProgramTreeNode parentNode = (ProgramTreeNode) node.getParent();

				insertedNodeList.add(node);

				// 親が既に移動対象に含んでる場合、処理不要
				if (insertedNodeList.contains(parentNode)) {
					continue;
				}

				if (parentNode != null) {
					// tree -> treeの場合

					// 同コンポーネント内のドラッグ＆ドロップがないか、 以下の項目をチェックする。
					treeToTree = true;

					if (targetNode.equals(node)) {
						evt.dropComplete(false);
						return;
					}

					// 転送したノードをTreeから削除
					model.removeNodeFromParent(node);

					ProgramTreeNode targetParentNode = (ProgramTreeNode) targetNode.getParent();

					if (targetParentNode != null
						&& MenuDivision.PROGRAM.equals(targetNode.getMenuDisp().getMenuDivision())) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> treeの場合

					ProgramTreeNode targetParentNode = (ProgramTreeNode) targetNode.getParent();

					if (targetParentNode != null
						&& MenuDivision.PROGRAM.equals(targetNode.getMenuDisp().getMenuDivision())) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}
				}

			}

			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			if (SourceType.TABLE.equals(transferData.getSource())) {

				// ソースがスプレッドの場合、一覧から削除
				mainView.dndTable.removeSelectedRows();
			}

			// 更新します
			model.reload(list.get(0));

			// 展開します
			tree.expandAll(dropPath, true);

			// 選択
			tree.setSelectionPath(dropPath);

			// ドロップ処理を完了する。
			evt.dropComplete(true);

		} else {
			evt.rejectDrop();
		}
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param listDnDData 階層データ
	 * @param tree プログラム階層リスト
	 */
	public void setTreeData(List<MenuDisp> listDnDData, TDnDTree tree) {

		int rootNum = 0;
		/** 階層データのルートデータ */
		MenuDisp baseDndData = null;
		ProgramTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// 上位階層コード = ブランク のデータはルートノードに設定する。
		for (MenuDisp ndnData : listDnDData) {
			if (Util.isNullOrEmpty(ndnData.getParentCode())) {
				baseDndData = ndnData;

				baseNode = new ProgramTreeNode(ndnData);
				model = new DefaultTreeModel(baseNode);
				tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			return;
		}

		// 子階層データを設定する
		createTree(baseNode, baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(baseNode));
		tree.setSelectionPath(visiblePath);

		tree.setRootVisible(false);
		tree.setEditable(true);

		// すべて展開します
		TreeNode root = (TreeNode) tree.getModel().getRoot();
		tree.expandAll(new TreePath(root), true);
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listData 階層データ
	 */
	protected void createTree(ProgramTreeNode parentNode, MenuDisp parent, List<MenuDisp> listData) {
		for (MenuDisp dndData : listData) {

			if (dndData.getName() == null) {
				continue;
			}

			if (parent.getCode().equals(dndData.getParentCode())) {
				ProgramTreeNode childNode = new ProgramTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [タブ新規作成]ボタン押下
	 */
	protected void btnNewTab_Click() {

		try {

			isNewTab = true;
			SystemizedProgram sys = showTabEditDialog();

			if (sys != null) {
				// メニュータブを追加する
				addNewTab(sys);
			}

		} catch (Exception e) {
			errorHandler(e);
		}

		isNewTab = false;

	}

	/**
	 * [検索]ボタン押下
	 */
	protected void btnSearch_Click() {

		// 左側タブのクリア
		mainView.menuTab.removeAll();

		// 一覧設定処理
		initList();

		// プログラム階層設定処理
		initTree();
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isInputRight()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00004")) {
				return;
			}

			List<SystemizedProgram> list = new LinkedList<SystemizedProgram>();
			int menuSeq = 1;

			// グループコードは手入力
			// タブ数処理を行う。
			for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

				SystemizedProgram sys = new SystemizedProgram();

				TDnDTree tree = getDndTree(i);
				List<MenuDisp> menuList = getTreeDataWithoutRoot(tree, menuSeq);

				sys.setCompanyCode(getCompanyCode());

				// グループコード、名称の設定
				String[] key = mainView.menuTab.getTitleAt(i).split(" ", 2);
				String code = key[0];
				String name = key[1];
				sys.setProgramGroupCode(code);
				sys.setProgramGroupName(name);

				sys.setMenuColor(mainView.menuTab.getBackgroundAt(i));
				sys.setDispIndex(i + 1);
				sys.setMenuDisp(menuList);

				list.add(sys);
			}

			// 登録処理
			request(getMenuModelClass(), "entry", list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ツリーの取得
	 * 
	 * @param i タブインデックス
	 * @return TDnDTree
	 */
	protected TDnDTree getDndTree(int i) {
		JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(i);
		TDnDTree tree = (TDnDTree) panel.getViewport().getComponent(0);
		return tree;
	}

	/**
	 * メイン画面で入力した値が妥当かをチェックする
	 * 
	 * @return メイン画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isInputRight() {

		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

			TDnDTree tree = getDndTree(i);

			ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

			Enumeration e = root.breadthFirstEnumeration();
			while (e.hasMoreElements()) {

				ProgramTreeNode node = (ProgramTreeNode) e.nextElement();

				if (node.equals(root)) {
					continue;
				}

				// メニュー名称桁数チェック
				if (node.getMenuDisp() == null) {
					continue;
				}

				if (node.getMenuDisp().isMenu() && Util.avoidNull(node.toString()).getBytes().length > 40) {
					// メニュー名称は半角40文字以内で入力してください。
					showMessage(editView, "I00394", "C11424", 40);
					mainView.menuTab.setSelectedIndex(i);
					return false;
				}

				if (Util.avoidNull(node.toString()).getBytes().length <= 0) {
					// メニュー名称を入力してください。
					showMessage(editView, "I00037", "C11424");
					mainView.menuTab.setSelectedIndex(i);
					return false;
				}

			}
		}

		return true;
	}

	/**
	 * プログラム階層ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @return TTableの階層データ
	 */
	public List<ProgramTreeNode> dragFromTable(DragGestureEvent evt) {

		List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// 左クリック時
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// 一覧.選択行のデータを取得。複数行選択されている場合は全て取得する。

					// 選択行のデータを取得。
					for (Program program : getSelectedProgram()) {

						MenuDisp data = new MenuDisp();
						data.setParentCode("");
						data.setCode(program.getCode());
						data.setName(program.getNames());
						data.setProgram(program);
						data.setMenuDivision(MenuDivision.PROGRAM);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		} catch (Exception ex) {
			// 処理なし
		}

		return createProgramTree(listDnDData);
	}

	/**
	 * 一覧で選択されているプログラムを返す
	 * 
	 * @return 一覧で選択されているプログラム
	 */
	protected List<Program> getSelectedProgram() {

		List<Program> list = new ArrayList<Program>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Program) mainView.dndTable.getRowValueAt(i, MG0440MenuMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * コンストラクタ Tree→Table、Tree→Tree用
	 * 
	 * @param list
	 * @return TTeeの階層データ
	 */
	public List<ProgramTreeNode> createProgramTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof MenuDisp) {
			List<ProgramTreeNode> tmpList = new ArrayList<ProgramTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				MenuDisp dnDData = (MenuDisp) list.get(i);
				ProgramTreeNode tProgramTreeNode = new ProgramTreeNode(dnDData);
				tmpList.add(tProgramTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * フォルダ新規作成
	 * 
	 * @param tree プログラム階層リスト
	 * @param evt マウスイベント
	 */
	public void addNewFolder(TDnDTree tree, MouseEvent evt) {

		try {

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			TreePath targetPath = tree.getPathForLocation(evt.getX(), evt.getY());

			ProgramTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (ProgramTreeNode) tree.getModel().getRoot();
			} else {
				targetNode = (ProgramTreeNode) targetPath.getLastPathComponent();
			}

			TreePath dropPath = new TreePath(((DefaultTreeModel) tree.getModel()).getPathToRoot(targetNode));// 移動先パス取得

			List<MenuDisp> listDnDData = new ArrayList<MenuDisp>();

			MenuDisp data = new MenuDisp();

			if (MenuDivision.MENU.equals(targetNode.getMenuDisp().getMenuDivision())) {
				data.setCompanyCode(targetNode.getMenuDisp().getCompanyCode());
				data.setParentCode(targetNode.getMenuDisp().getCode());
				data.setCode("");
				data.setName(newFolderName);
				data.setMenuDivision(MenuDivision.MENU); // 1:メニュー

				SystemClassification sys = new SystemClassification();
				sys.setCode(targetNode.getMenuDisp().getSystemClassification().getCode());
				data.setSystemClassification(sys);

				listDnDData.add(data);
			} else {
				return;
			}

			// ノードの追加
			ProgramTreeNode node = new ProgramTreeNode(data);
			model.insertNodeInto(node, targetNode, targetNode.getChildCount());

			// 更新します
			((DefaultTreeModel) tree.getModel()).reload(node);

			// 展開します
			tree.expandAll(dropPath, true);

			// 選択
			tree.setSelectionPath(dropPath);

			// 選択をクリア
			tree.clearSelection();
		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * メニューの削除＆スプレッドへ復元
	 * 
	 * @param tree プログラム階層リスト
	 */
	public void deleteTreeDataAndRestoreTable(TDnDTree tree) {

		try {

			// 複数削除も可能
			if (tree == null || tree.getModel() == null) {
				return;
			}

			TreePath[] deletePaths = null;

			if (tree.getSelectionPaths() == null) {
				deletePaths = tree.getAllPaths();
			} else {
				deletePaths = tree.getSelectionPaths();
			}

			if (deletePaths == null) {
				return;
			}

			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot(); // ルートノード

			if (root == null || root.getMenuDisp() == null) {
				return;
			}

			String rootCode = root.getMenuDisp().getCode(); // ルートコード

			// 既に復元したノードを記録する
			List<ProgramTreeNode> listFinish = new ArrayList<ProgramTreeNode>();

			// すべての選択肢を削除する
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof ProgramTreeNode)) {
					continue;
				}

				ProgramTreeNode selectedNode = (ProgramTreeNode) obj;
				if (selectedNode.getMenuDisp() == null) {

					continue;

				} else {

					// メニューの場合、下の全ノードをスプレッドに復元する

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						ProgramTreeNode node = (ProgramTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getMenuDisp().getProgram() != null
							&& !Util.isNullOrEmpty(node.getMenuDisp().getProgram().getCode())) {

							// 一覧に追加する
							mainView.dndTable.addRow(getProgramDataRow(node.getMenuDisp().getProgram()));
						}
					}

				}

				// ルートノードが選択されている処理を中断する。
				String selectCode = selectedNode.getMenuDisp().getCode();

				if (rootCode.equals(selectCode)) {
					continue;
				}

				// 選択ノードを削除し
				model.removeNodeFromParent(selectedNode);
			}

			// 削除後、階層データではルートノードを選択状態にする。
			TreePath visiblePath = new TreePath(model.getPathToRoot(root));
			tree.setSelectionPath(visiblePath);

			// 初期化
			selectedTree = null;

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 対象タブのグループ情報設定
	 * 
	 * @param tabIndex タブインデックス
	 * @param sys グループ情報
	 */
	public void syncMenuGroup(int tabIndex, SystemClassification sys) {

		// 対象タブのグループ情報設定を行う。
		JScrollPane panel = (JScrollPane) mainView.menuTab.getComponentAt(tabIndex);

		TDnDTree tree = (TDnDTree) panel.getViewport().getComponent(0);
		ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

		root.getMenuDisp().setSystemClassification(sys);
	}

	/**
	 * ルートノードを含めない階層データを取得する
	 * 
	 * @param tree プログラム階層リスト
	 * @param menuSeq
	 * @return 階層データ
	 */
	public List<MenuDisp> getTreeDataWithoutRoot(TDnDTree tree, int menuSeq) {

		int dispIndex = 1;

		List<MenuDisp> list = new ArrayList<MenuDisp>();

		ProgramTreeNode root = (ProgramTreeNode) tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			ProgramTreeNode node = (ProgramTreeNode) e.nextElement();
			if (!node.equals(root)) {
				ProgramTreeNode parentNode = (ProgramTreeNode) (node.getParent());

				// 上位階層コード = ルートノード

				MenuDisp menu = node.getMenuDisp();
				menu.setCompanyCode(getCompanyCode());
				menu.setCode(node.getMenuDisp().getCode());
				menu.setName(node.toString()); // Tree上での名称変更対応

				// 親がルートノードの場合、親コードを設定しない。
				menu.setParentCode("");

				// 表示順設定
				menu.setDispIndex(dispIndex++);

				// グループ情報設定
				menu.setSystemClassification(root.getMenuDisp().getSystemClassification());

				// コードがnullの場合、採番
				if (menu.isMenu() || Util.isNullOrEmpty(menu.getCode())) {
					menu.setCode(getMenuCode(menuSeq++));
				}

				// 親がルートノードの場合、親コードを設定しない。
				menu.setParentCode(parentNode.equals(root) ? "" : parentNode.getMenuDisp().getCode()); // 親コード設定

				list.add(menu);

			}
		}

		return list;
	}

	/**
	 * メニューコードの採番
	 * 
	 * @param seq
	 * @return メニューコード
	 */
	protected String getMenuCode(int seq) {
		String prefix = "MENU";
		return prefix + StringUtil.fillLeft(String.valueOf(seq), 10 - prefix.length(), '0');
	}

	/**
	 * タブ名を変更する。
	 * 
	 * @return タブ情報
	 */
	public SystemizedProgram showTabEditDialog() {

		try {

			// 編集画面を生成
			createEditView();

			// 編集画面を初期化する
			initEditView();

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

			return editView.getSystemizedProgram();

		} catch (Exception e) {
			errorHandler(e);
		}

		return null;
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0440MenuMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();
	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 編集画面を初期化する
	 */
	protected void initEditView() {
		editView.setTitle(getWord("C11414")); // タブ名設定画面

		if (!isNewTab) {

			int index = mainView.menuTab.getSelectedIndex();

			// グループコード、名称の設定
			String[] key = mainView.menuTab.getTitleAt(index).split(" ", 2);
			String code = key[0];
			String name = key[1];
			editView.ctrlTabCode.setValue(code);
			editView.ctrlTabName.setValue(name);

			// 色をセット
			editView.colorChooser.setColor(mainView.menuTab.getBackgroundAt(index));

		} else {

			// 色をセット
			editView.colorChooser.setColor(Color.GRAY);

		}
	}

	/**
	 * 編集画面[確定]ボタン押下
	 */
	protected void btnEditViewSettle_Click() {

		try {

			// 入力チェックを行う。
			if (!isEditViewInputRight()) {
				return;
			}

			if (!isNewTab) {
				// タブに名称を設定する。
				int tabIndex = mainView.menuTab.getSelectedIndex();

				String key = editView.ctrlTabCode.getValue() + " " + editView.ctrlTabName.getValue();
				mainView.menuTab.setTitleAt(tabIndex, key);

				SystemClassification classification = new SystemClassification();
				classification.setCode(editView.ctrlTabCode.getValue());
				syncMenuGroup(tabIndex, classification);

				// 色設定
				mainView.menuTab.setBackgroundAt(tabIndex, editView.colorChooser.getColor());

			} else {

				// タブ追加用
				SystemizedProgram sys = new SystemizedProgram();
				sys.setProgramGroupCode(editView.ctrlTabCode.getValue());
				sys.setProgramGroupName(editView.ctrlTabName.getValue());
				sys.setMenuColor(editView.colorChooser.getColor());

				editView.setSystemizedProgram(sys);

			}

			// 編集画面を閉じる
			btnEditViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面[戻る]ボタン押下
	 */
	protected void btnEditViewClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * 編集画面で入力した値が妥当かをチェックする
	 * 
	 * @return 編集画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isEditViewInputRight() {

		// グループコードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTabCode.getValue())) {
			showMessage(editView, "I00037", "C11402"); // グループコード
			editView.ctrlTabCode.requestFocus();
			return false;
		}

		// グループ名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlTabName.getValue())) {
			showMessage(editView, "I00037", "C11404"); // グループ名称
			editView.ctrlTabName.requestFocus();
			return false;
		}

		boolean isTabExists = false;
		String code = editView.ctrlTabCode.getValue();

		// タブ数処理を行う。
		for (int i = 0; i < mainView.menuTab.getTabCount(); i++) {

			if (i == mainView.menuTab.getSelectedIndex() && !isNewTab) {
				// 選択中は対象外
				continue;
			}

			String name = mainView.menuTab.getTitleAt(i).split(" ", 2)[0];
			if (name.equals(code)) {
				isTabExists = true;
				break;
			}
		}

		if (isTabExists) {
			// 存在の場合、エラー:I00055">既に存在します。 （グループコード）
			showMessage(editView, "I00055", "C11402");
			editView.ctrlTabCode.requestFocus();
			return false;
		}

		return true;
	}

}

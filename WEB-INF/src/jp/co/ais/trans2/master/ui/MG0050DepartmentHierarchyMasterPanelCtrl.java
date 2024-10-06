package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.department.DepartmentTransferData.SourceType;

/**
 * 部門階層マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterPanelCtrl extends TController {

	/** 操作モード */
	public enum Mode {
		/** 新規 */
		NEW
		/** 複写 */
		, COPY
		/** 削除 */
		, DELETE
		/** エクセル */
		, EXCEL
		/** 確定 */
		, SETTLE
	}

	/** 指示画面 */
	protected MG0050DepartmentHierarchyMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0050DepartmentHierarchyDialog editView = null;

	/** tree -> spread : false、tree -> tree : true */
	protected boolean treeToTree = true;

	/** 操作モード。現在操作中のモードを把握するために使用する。 */
	protected Mode mode = null;

	/** ルート名 */
	public String rootName = null;

	/** ツリーからのデータ移動処理 */
	protected boolean isDragFromTree = false;

	/** 前の組織コード */
	public String oldSskCode = null;

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
		mainView = new MG0050DepartmentHierarchyMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [新規]ボタン押下
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [複写]ボタン押下
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [削除]ボタン押下
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [エクセル]ボタン押下
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [確定]ボタン押下
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// 組織コード変更処理
		mainView.ctrlOrganizationCode.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent evt) {
				changedOrganizationCode(evt.getStateChange());
			}
		});

		// 一覧ドラッグ＆ドロップイベント
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 一覧からドラッグデータの取得処理を行う
				List<DepartmentTreeNode> nodeList = dragFromTable(evt);

				DepartmentTransferData transferData = new DepartmentTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TABLE);

				isDragFromTree = false;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// 一覧へドロップ処理を行う
				dropToTable(value);
			}

		});

		// 部門階層マウスイベント
		mainView.tree.addMouseListener(new MouseAdapter() {

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

					popup.show(e.getComponent(), e.getX(), e.getY());

					// 右クリック時、マウスオーバーのノードのフォーカスを当てる
					mainView.tree.setSelectionRow(mainView.tree.getRowForLocation(e.getX(), e.getY()));
				}
			}
		});

		// 部門階層ドラッグ＆ドロップイベント
		mainView.tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 処理中ツリーを一時保存する

				List<DepartmentTreeNode> nodeList = dragFromTree(evt, mainView.tree);
				DepartmentTransferData transferData = new DepartmentTransferData();
				transferData.setNodeList(nodeList);
				transferData.setSource(SourceType.TREE);

				isDragFromTree = true;

				return transferData;
			}

			@Override
			public void dropData(DropTargetDropEvent evt, Object value) {

				// ツリーへのドロップ処理
				dropToTree(evt, mainView.tree, value);
			}

			@Override
			public boolean isLeaf(TreeNode node) {
				return true;
			}
		});

	}

	/**
	 * 組織コード変更処理
	 * 
	 * @param state
	 */
	protected void changedOrganizationCode(int state) {
		try {
			if (Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedOrganizationCode())) {

				// レベル０初期化
				mainView.ctrlDepartment.clear();

				initMainView();

				return;
			}

			if (ItemEvent.SELECTED != state) {
				return;
			}

			// 組織検索条件の定義
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			List<Department> depList = getDepartment(depCondition);

			// レベル０初期化
			mainView.ctrlDepartment.clear();

			// レベル０
			for (DepartmentOrganization org : orgList) {
				if (org.getLevel() == 0) {
					String departmentCode = org.getDepCode();
					mainView.ctrlDepartment.setCode(departmentCode);
					mainView.ctrlDepartment.setNames(org.getDepNames());
					break;
				}
			}

			// ツリー構築
			initTree(orgList);

			// フィルター
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			// 部門一覧へ反映
			initList(depList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlOrganizationCode.getSelectedItemValue()));

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 指示画面を初期化する
	 */
	protected void initMainView() {

		// メインボタンを押下不可にする
		setMainButtonEnabled(false);

		// 一覧設定処理
		initList(null);

		// 部門階層設定処理
		initTree(null);

	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
		mainView.btnExcel.setEnabled(bln);
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * 指示画面[新規]ボタン押下
	 */
	protected void btnNew_Click() {

		try {

			// 編集画面を生成
			createEditView();

			// 編集画面を初期化する
			initEditView(Mode.NEW, null);

			// 編集画面を表示
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [複写]ボタン押下
	 */
	protected void btnCopy_Click() {

		try {

			// 複写対象の組織コードを取得する
			DepartmentOrganization org = getSelectedDepOrg();

			// 複写画面を生成し、複写対象の部門情報をセットする
			createEditView();
			initEditView(Mode.COPY, org);

			// 複写画面を表示する
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 部門マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 部門
	 */
	protected void initList(List<Department> list) {

		// 一覧をクリアする
		mainView.dndTable.removeRow();

		// 部門階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 部門情報を一覧に表示する
		for (Department department : list) {
			mainView.dndTable.addRow(getDepartmentDataRow(department));
		}

		// 1行目を選択する
		mainView.dndTable.setRowSelection(0);

	}

	/**
	 * 部門の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentSearchCondition getSearchCondition() {
		DepartmentSearchCondition condition = new DepartmentSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumDepartment(true);

		return condition;
	}

	/**
	 * 部門階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentOrganizationSearchCondition getOrganizationSearchCondition() {
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;
	}

	/**
	 * 検索条件に該当する部門のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する部門のリスト
	 * @throws TException
	 */
	protected List<Department> getDepartment(DepartmentSearchCondition condition) throws TException {
		return (List<Department>) request(getModelClass(), "get", condition);
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getModelClass() {
		return DepartmentManager.class;
	}

	/**
	 * 部門情報を一覧に表示する形式に変換し返す
	 * 
	 * @param department 部門
	 * @return 一覧に表示する形式に変換された部門
	 */
	protected Object[] getDepartmentDataRow(Department department) {
		return new Object[] { department.getCode(), department.getNames(), department };
	}

	/**
	 * 部門階層マスタよりデータを取得し、部門階層に設定する。
	 * 
	 * @param list 部門階層
	 */
	protected void initTree(List<DepartmentOrganization> list) {

		// ツリーのクリア処理
		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();
		if (root != null) {
			root.removeAllChildren();
			mainView.tree.reload();
		}

		// 部門階層マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 部門階層表示
		setTreeDepartmentOrganizationData(list);
	}

	/**
	 * 部門階層の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected DepartmentOrganizationSearchCondition getTreeSearchCondition() {

		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());

		return condition;

	}

	/**
	 * 検索条件に該当する部門階層のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する部門階層のリスト
	 * @throws TException
	 */
	protected List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException {
		return (List<DepartmentOrganization>) request(getModelClass(), "getDepartmentOrganizationData", condition);
	}

	/**
	 * 一覧へドロップ処理
	 * 
	 * @param value DepartmentTransferData 渡す部門ノードデータ
	 */
	protected void dropToTable(Object value) {

		if (!isDragFromTree) {
			return;
		}

		// 希望の部門ノードデータじゃない場合、処理不要
		if (!(value instanceof DepartmentTransferData)) {
			return;
		}

		DepartmentTransferData transferData = (DepartmentTransferData) value;

		// リストが0件の場合、処理不要
		if (transferData.getNodeList() == null || transferData.getNodeList().isEmpty()) {
			return;
		}

		// ツリーから削除(選択のみ)＆スプレッドへ復元
		deleteTreeDataAndRestoreTable();

	}

	/**
	 * 部門階層ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @param tree 部門階層リスト
	 * @return TTeeの階層データ
	 */
	protected List<DepartmentTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<DepartmentTreeNode> listTreeNode = new ArrayList<DepartmentTreeNode>();

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

						DepartmentTreeNode node = (DepartmentTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							DepartmentTreeNode eachNode = (DepartmentTreeNode) enumNode.nextElement();
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
	 * 部門階層ドロップ処理
	 * 
	 * @param evt ドロップターゲットイベント
	 * @param tree 部門階層リスト
	 * @param value 指定されたデータ
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// 転送をチェック
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop位置にあるTreeNodeを取得する。ドロップ位置にノードが存在しない場合、処理を終了する。
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			DepartmentTreeNode targetNode = null;
			if (targetPath == null) {
				targetNode = (DepartmentTreeNode) model.getRoot();
			} else {
				targetNode = (DepartmentTreeNode) targetPath.getLastPathComponent();
			}

			// 移動先ノード
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// 移動先パス取得

			// 変更済みのノードリスト（親区別用）
			List<DepartmentTreeNode> insertedNodeList = new ArrayList<DepartmentTreeNode>();

			if (!(value instanceof DepartmentTransferData)) {
				return;
			}

			// 渡す部門階層ノードデータ
			DepartmentTransferData transferData = (DepartmentTransferData) value;

			// 部門階層ノードリスト
			List<DepartmentTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// チェック、目標はドラッグ中であれば、ドロップ不要
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (DepartmentTreeNode node : list) {

				// 自分の親ノード
				DepartmentTreeNode parentNode = (DepartmentTreeNode) node.getParent();

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

					DepartmentTreeNode targetParentNode = (DepartmentTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));
					} else {
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> treeの場合

					DepartmentTreeNode targetParentNode = (DepartmentTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
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
	 * @param list 階層データ
	 */
	public void setTreeDepartmentOrganizationData(List<DepartmentOrganization> list) {
		List<DepartmentDisp> listDnDData = new ArrayList<DepartmentDisp>();

		for (DepartmentOrganization org : list) {
			DepartmentDisp disp = new DepartmentDisp();
			disp.setCompanyCode(org.getCompanyCode());
			disp.setCode(org.getDepCode());
			disp.setName(org.getDepNames());
			disp.setParentCode(getParentCode(org));

			if (Util.isNullOrEmpty(disp.getParentCode())) {

				disp.setInpDate(org.getInpDate());
			}

			Department department = new Department();
			department.setCode(org.getDepCode());
			department.setNames(org.getDepNames());
			disp.setDepartment(department);

			listDnDData.add(disp);
		}

		setTreeData(listDnDData);
	}

	/**
	 * @param org
	 * @return 親コード
	 */
	protected String getParentCode(DepartmentOrganization org) {
		switch (org.getLevel()) {
			case 1:
				return org.getLevel0();
			case 2:
				return org.getLevel1();
			case 3:
				return org.getLevel2();
			case 4:
				return org.getLevel3();
			case 5:
				return org.getLevel4();
			case 6:
				return org.getLevel5();
			case 7:
				return org.getLevel6();
			case 8:
				return org.getLevel7();
			case 9:
				return org.getLevel8();
		}
		return null;
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param listDnDData 階層データ
	 */
	public void setTreeData(List<DepartmentDisp> listDnDData) {

		int rootNum = 0;
		/** 階層データのルートデータ */
		DepartmentDisp baseDndData = null;
		DepartmentTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// 上位階層コード = ブランク のデータはルートノードに設定する。
		for (DepartmentDisp disp : listDnDData) {
			if (Util.isNullOrEmpty(disp.getParentCode())) {
				baseDndData = disp;

				baseNode = new DepartmentTreeNode(disp);
				model = new DefaultTreeModel(baseNode);
				mainView.tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			return;
		}

		// 子階層データを設定する
		createTree(baseNode, baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) mainView.tree.getModel()).getPathToRoot(baseNode));
		mainView.tree.setSelectionPath(visiblePath);

		mainView.tree.setRootVisible(false);
		mainView.tree.setEditable(false);

		// すべて展開します
		TreeNode root = (TreeNode) mainView.tree.getModel().getRoot();
		mainView.tree.expandAll(new TreePath(root), true);
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listData 階層データ
	 */
	protected void createTree(DepartmentTreeNode parentNode, DepartmentDisp parent, List<DepartmentDisp> listData) {
		for (DepartmentDisp dndData : listData) {

			if (dndData.getName() == null) {
				continue;
			}

			if (parent.getCode().equals(dndData.getParentCode())) {
				DepartmentTreeNode childNode = new DepartmentTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [削除]ボタン押下
	 */
	protected void btnDelete_Click() {
		try {
			// 確認メッセージ
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// 削除しますか？
				return;
			}

			DepartmentOrganization bean = getSelectedDepOrg();

			// 部門階層情報を取得
			request(getModelClass(), "deleteDepartmentOrganization", bean);

			if (bean == null) {
				// 選択されたデータは他ユーザーにより削除されました
				throw new TException("I00193");
			}

			// コンボボックスリフレッシュ
			mainView.ctrlOrganizationCode.getController().initList();

			// レベル０初期化
			mainView.ctrlDepartment.clear();

			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 部門階層を返す
	 * 
	 * @return 部門階層
	 * @throws Exception
	 */
	protected DepartmentOrganization getSelectedDepOrg() throws Exception {

		DepartmentOrganizationSearchCondition condition = createDepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * @return 部門階層情報の検索条件
	 */
	protected DepartmentOrganizationSearchCondition createDepartmentOrganizationSearchCondition() {
		return new DepartmentOrganizationSearchCondition();
	}

	/**
	 * 指示画面[エクセル]ボタン押下
	 */
	protected void btnExcel_Click() {
		try {

			// 確認メッセージ
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// データ抽出
			DepartmentOrganizationSearchCondition condition = getOrganizationSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getDepartmentOrganizationExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02327") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 確定Check
			if (!checkSettle()) {
				return;
			}

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<DepartmentOrganization> list = getTreeData();

			// 登録処理
			request(getModelClass(), "entryDepartmentOrganization",
				mainView.ctrlOrganizationCode.getSelectedOrganizationCode(), list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 確定Check
	 * 
	 * @return true:エラーなし
	 */
	protected boolean checkSettle() {
		if (getMaxLevel() > 9) {
			showMessage(mainView, "W00153");
			return false;
		}

		return true;
	}

	/**
	 * 部門階層ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @return TTableの階層データ
	 */
	public List<DepartmentTreeNode> dragFromTable(DragGestureEvent evt) {

		List<DepartmentDisp> listDnDData = new ArrayList<DepartmentDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// 左クリック時
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// 一覧.選択行のデータを取得。複数行選択されている場合は全て取得する。

					// 選択行のデータを取得。
					for (Department department : getSelectedDepartment()) {

						DepartmentDisp data = new DepartmentDisp();
						data.setParentCode("");
						data.setCode(department.getCode());
						data.setName(department.getNames());
						data.setDepartment(department);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		} catch (Exception ex) {
			// 処理なし
		}

		return createDepOrgTree(listDnDData);
	}

	/**
	 * 一覧で選択されている部門を返す
	 * 
	 * @return 一覧で選択されている部門
	 */
	protected List<Department> getSelectedDepartment() {

		List<Department> list = new ArrayList<Department>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Department) mainView.dndTable.getRowValueAt(i, MG0050DepartmentHierarchyMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * コンストラクタ Tree→Table、Tree→Tree用
	 * 
	 * @param list
	 * @return TTeeの階層データ
	 */
	public List<DepartmentTreeNode> createDepOrgTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof DepartmentDisp) {
			List<DepartmentTreeNode> tmpList = new ArrayList<DepartmentTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				DepartmentDisp dnDData = (DepartmentDisp) list.get(i);
				DepartmentTreeNode departmentTreeNode = new DepartmentTreeNode(dnDData);
				tmpList.add(departmentTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * 組織コードの削除＆スプレッドへ復元
	 */
	public void deleteTreeDataAndRestoreTable() {

		try {

			// 複数削除も可能
			if (mainView.tree == null || mainView.tree.getModel() == null) {
				return;
			}

			TreePath[] deletePaths = null;

			if (mainView.tree.getSelectionPaths() == null) {
				deletePaths = mainView.tree.getAllPaths();
			} else {
				deletePaths = mainView.tree.getSelectionPaths();
			}

			if (deletePaths == null) {
				return;
			}

			DefaultTreeModel model = (DefaultTreeModel) mainView.tree.getModel();
			DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot(); // ルートノード

			if (root == null || root.getDepDisp() == null) {
				return;
			}

			String rootCode = root.getDepDisp().getCode(); // ルートコード

			// 既に復元したノードを記録する
			List<DepartmentTreeNode> listFinish = new ArrayList<DepartmentTreeNode>();

			// すべての選択肢を削除する
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof DepartmentTreeNode)) {
					continue;
				}

				DepartmentTreeNode selectedNode = (DepartmentTreeNode) obj;
				if (selectedNode.getDepDisp() == null) {

					continue;

				} else {

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						DepartmentTreeNode node = (DepartmentTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getDepDisp().getDepartment() != null
							&& !Util.isNullOrEmpty(node.getDepDisp().getDepartment().getCode())) {

							// 一覧に追加する
							mainView.dndTable.addRow(getDepartmentDataRow(node.getDepDisp().getDepartment()));
						}
					}

				}

				// ルートノードが選択されている処理を中断する。
				String selectCode = selectedNode.getDepDisp().getCode();

				if (rootCode.equals(selectCode)) {
					continue;
				}

				// 選択ノードを削除し
				model.removeNodeFromParent(selectedNode);
			}

			// 削除後、階層データではルートノードを選択状態にする。
			TreePath visiblePath = new TreePath(model.getPathToRoot(root));
			mainView.tree.setSelectionPath(visiblePath);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * 最大レベルを取得する
	 * 
	 * @return 最大レベル
	 */
	public int getMaxLevel() {
		int maxLevel = 0;

		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DepartmentTreeNode node = (DepartmentTreeNode) e.nextElement();

			TreeNode[] path = node.getPath();
			maxLevel = Math.max(maxLevel, path.length);
		}

		return maxLevel - 1;
	}

	/**
	 * ルートノードを含めない階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<DepartmentOrganization> getTreeData() {

		List<DepartmentOrganization> list = new ArrayList<DepartmentOrganization>();

		DepartmentTreeNode root = (DepartmentTreeNode) mainView.tree.getModel().getRoot();
		DepartmentDisp level0 = null;

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			DepartmentTreeNode node = (DepartmentTreeNode) e.nextElement();

			TreeNode[] path = node.getPath();
			int level = path.length - 1;

			if (level == 0) {
				level0 = ((DepartmentTreeNode) path[0]).getDepDisp();
				continue;
			}

			DepartmentOrganization bean = new DepartmentOrganization();
			bean.setCompanyCode(getCompanyCode());
			bean.setCode(mainView.ctrlOrganizationCode.getSelectedOrganizationCode());
			bean.setDepCode(getNodeDepartmentCode(path[level]));
			bean.setLevel(level);
			bean.setInpDate(level0.getInpDate());

			bean.setLevel0(getNodeDepartmentCode(path[0]));
			if (level >= 1) {
				bean.setLevel1(getNodeDepartmentCode(path[1]));
			}
			if (level >= 2) {
				bean.setLevel2(getNodeDepartmentCode(path[2]));
			}
			if (level >= 3) {
				bean.setLevel3(getNodeDepartmentCode(path[3]));
			}
			if (level >= 4) {
				bean.setLevel4(getNodeDepartmentCode(path[4]));
			}
			if (level >= 5) {
				bean.setLevel5(getNodeDepartmentCode(path[5]));
			}
			if (level >= 6) {
				bean.setLevel6(getNodeDepartmentCode(path[6]));
			}
			if (level >= 7) {
				bean.setLevel7(getNodeDepartmentCode(path[7]));
			}
			if (level >= 8) {
				bean.setLevel8(getNodeDepartmentCode(path[8]));
			}
			if (level >= 9) {
				bean.setLevel9(getNodeDepartmentCode(path[9]));
			}

			list.add(bean);

		}

		return list;
	}

	/**
	 * @param node
	 * @return 部門コード
	 */
	protected String getNodeDepartmentCode(TreeNode node) {
		return ((DepartmentTreeNode) node).getDepDisp().getCode();
	}

	/**
	 * 編集画面のファクトリ。新規に編集画面を生成し、イベントを定義する。
	 */
	protected void createEditView() {

		// 編集画面を生成
		editView = new MG0050DepartmentHierarchyDialog(getCompany(), mainView.getParentFrame(), true);

		// 編集画面のイベント定義
		addEditViewEvent();

	}

	/**
	 * 編集画面のイベント定義。
	 */
	protected void addEditViewEvent() {

		// [確定]ボタン押下
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditViewClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 編集画面を初期化する
	 * 
	 * @param mode_ 新規
	 * @param bean
	 */
	protected void initEditView(Mode mode_, DepartmentOrganization bean) {

		this.mode = mode_;
		switch (mode) {
			case NEW:
				editView.setTitle(getWord("C01698"));

				break;

			case COPY:
				editView.setTitle(getWord("C01738"));
				editView.ctrlDepartmentReference.setEditable(false);

				oldSskCode = bean.getCode();

				editView.ctrlOrganizationCode.setValue(oldSskCode);
				editView.ctrlDepartmentReference.code.setValue(bean.getDepCode());
				editView.ctrlDepartmentReference.name.setValue(bean.getDepNames());

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

			// 入力された組織コード情報を取得
			DepartmentOrganization departmentOrganization = getInputedOrg();

			if (Mode.NEW == mode) {
				// 新規登録
				request(getModelClass(), "entryDepartmentOrganization", departmentOrganization);
			} else if (Mode.COPY == mode) {
				// 複写
				request(getModelClass(), "entryCopyDepartmentOrganization", departmentOrganization, oldSskCode);
			}

			// 編集画面を閉じる
			btnEditViewClose_Click();

			// 追加分をコンボボックスに反映する
			mainView.ctrlOrganizationCode.getController().initList();

			mainView.ctrlOrganizationCode.setSelectedItemValue(departmentOrganization.getCode());
			mainView.ctrlDepartment.setCode(departmentOrganization.getDepCode());
			mainView.ctrlDepartment.setNames(departmentOrganization.getDepNames());

			// 組織検索条件の定義
			DepartmentOrganizationSearchCondition orgCondition = getTreeSearchCondition();

			// DBから組織情報を取得する
			List<DepartmentOrganization> orgList = getDepartmentOrganization(orgCondition);

			// 部門検索条件の定義
			DepartmentSearchCondition depCondition = getSearchCondition();

			// DBから部門情報を取得する
			List<Department> depList = getDepartment(depCondition);

			// フィルター
			if (depList != null) {
				for (DepartmentOrganization org : orgList) {
					String departmentCode = org.getDepCode();

					for (int i = depList.size() - 1; i >= 0; i--) {
						if (departmentCode.equals(depList.get(i).getCode())) {
							depList.remove(i);
							break;
						}
					}
				}
			}

			if (Mode.NEW == mode) {
				initTree(null);
			} else if (Mode.COPY == mode) {
				initTree(orgList);
			}

			initList(depList);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力された組織コードを返す
	 * 
	 * @return 編集画面で入力された組織コード情報
	 */
	protected DepartmentOrganization getInputedOrg() {

		// 組織コード情報
		DepartmentOrganization org = new DepartmentOrganization();
		org.setCode(editView.ctrlOrganizationCode.getValue());
		org.setDepCode(editView.ctrlDepartmentReference.getCode());
		org.setDepNames(editView.ctrlDepartmentReference.getNames());
		return org;

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
	 * @throws Exception
	 */
	protected boolean isEditViewInputRight() throws Exception {

		// 組織コードが未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlOrganizationCode.getValue())) {
			showMessage(editView, "I00037", "C00335"); // 組織コード
			editView.ctrlOrganizationCode.requestFocus();
			return false;
		}

		// レベル０が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlDepartmentReference.getCode())) {
			showMessage(editView, "I00037", "C00722"); // レベル０
			editView.ctrlDepartmentReference.btn.requestFocus();
			return false;
		}

		// 組織コードが重複していたらエラー
		DepartmentOrganizationSearchCondition condition = new DepartmentOrganizationSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setCode(editView.ctrlOrganizationCode.getValue());

		List<DepartmentOrganization> list = getDepartmentOrganization(condition);

		if (list != null && !list.isEmpty()) {
			showMessage(editView, "I00084", "C00335");
			editView.ctrlOrganizationCode.requestTextFocus();
			return false;
		}

		return true;
	}

}

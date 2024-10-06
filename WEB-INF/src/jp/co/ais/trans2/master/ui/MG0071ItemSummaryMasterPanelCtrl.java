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
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.gui.dnd.*;
import jp.co.ais.trans2.common.gui.event.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans2.model.item.summary.*;
import jp.co.ais.trans2.model.item.summary.ItemSummaryTransferData.SourceType;

/**
 * 科目集計マスタのコントローラ
 * 
 * @author AIS
 */
public class MG0071ItemSummaryMasterPanelCtrl extends TController {

	/** 操作モード */
	public enum Mode {
		/** 編集 */
		MODIFY
		/** 複写 */
		, COPY
		/** エクセル */
		, EXCEL
		/** 確定 */
		, SETTLE
	}

	/** 指示画面 */
	protected MG0071ItemSummaryMasterPanel mainView = null;

	/** 編集画面 */
	protected MG0071ItemSummaryMasterDialog editView = null;

	/** 複写画面 */
	protected MG0071ItemSummaryMasterCopyDialog copyView = null;

	/** ノード */
	protected ItemSummaryTreeNode editNode = null;

	/** tree -> spread : false、tree -> tree : true */
	protected boolean treeToTree = true;

	/** ルート名 */
	public String rootName = null;

	/** ルートのダミーコード */
	public static String dummyRootCode = "<  dummy  >";

	/** ツリーからのデータ移動処理 */
	protected boolean isDragFromTree = false;

	/** 科目体系コード（検索条件.科目体系変更時使用） */
	public String kmtCode = null;

	/** 科目体系略称（検索条件.科目体系変更時使用） */
	public String kmtName = null;

	/** 前回科目体系コード（複写時使用） */
	public String preKmtCode = null;

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
		mainView = new MG0071ItemSummaryMasterPanel(this);
		addMainViewEvent();
	}

	/**
	 * 編集画面作成
	 */
	protected void createEditView() {
		editView = new MG0071ItemSummaryMasterDialog(getCompany(), mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * 複写画面作成
	 */
	protected void createCopyView() {
		copyView = new MG0071ItemSummaryMasterCopyDialog(getCompany(), mainView.getParentFrame(), true);
		addCopyViewEvent();
	}

	/**
	 * 指示画面のイベント定義。
	 */
	protected void addMainViewEvent() {

		// [検索]ボタン押下
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
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

		// [科目体系]verify
		mainView.ctrlItemOrganizationReference.addCallBackListener(new TCallBackListener() {

			@Override
			public void after(boolean flag) {
				if (!flag) {
					return;
				}

				ctrlItemOrganizationReference_after();
			}
		});

		// 一覧ドラッグ＆ドロップイベント
		mainView.dndTable.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 一覧からドラッグデータの取得処理を行う
				List<ItemSummaryTreeNode> nodeList = dragFromTable(evt);

				ItemSummaryTransferData transferData = new ItemSummaryTransferData();
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

		// 科目集計マウスイベント
		mainView.tree.addMouseListener(new MouseAdapter() {

			/** クリックイベント */
			@Override
			public void mouseClicked(final MouseEvent e) {

				Component comp = e.getComponent();

				if (comp == null || !comp.isEnabled()) {
					return;
				}

				if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() > 1) {

					if (mainView.tree.getSelectionRows() != null) {

						// 左ダブルクリック
						doLeftDoubleClick();
					}
				}

			}
		});

		// 科目集計ドラッグ＆ドロップイベント
		mainView.tree.setDnDListener(new TDnDAdapter() {

			@Override
			public Object getDragData(DragGestureEvent evt) {

				// 処理中ツリーを一時保存する
				List<ItemSummaryTreeNode> nodeList = dragFromTree(evt, mainView.tree);
				ItemSummaryTransferData transferData = new ItemSummaryTransferData();
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
	 * ダブルクリックで編集画面を表示
	 */
	protected void doLeftDoubleClick() {

		// 編集画面を生成
		editView = new MG0071ItemSummaryMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// editNodeを初期化
		editNode = (ItemSummaryTreeNode) mainView.tree.getSelectionPath().getLastPathComponent();

		// 編集画面のイベント定義
		addEditViewEvent();

		// 編集画面を初期化する
		initEditView();

		// Expand
		mainView.tree.expandPath(mainView.tree.getSelectionPath());

		// 編集画面を表示
		editView.setLocationRelativeTo(null);
		editView.setVisible(true);
	}

	/**
	 * 指示画面[科目体系]verify
	 */
	protected void ctrlItemOrganizationReference_after() {

		if (mainView.ctrlItemOrganizationReference.getEntity() != null
			&& mainView.ctrlItemOrganizationReference.isValueChanged()) {

			if (!showConfirmMessage(mainView, "Q00018")) {
				mainView.ctrlItemOrganizationReference.code.setValue(kmtCode);
				mainView.ctrlItemOrganizationReference.name.setValue(kmtName);

				if (Util.isNullOrEmpty(kmtCode)) {
					mainView.ctrlItemOrganizationReference.setEntity(null);
				} else {
					mainView.ctrlItemOrganizationReference.getEntity().setCode(kmtCode);
				}

				return;
			}

			// 指示画面を再反映
			refresh();
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

		// 科目集計設定処理
		initTree(null);

	}

	/**
	 * メインボタンの押下制御
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnCopy.setEnabled(bln);
		mainView.btnSettle.setEnabled(bln);
	}

	/**
	 * 指示画面[検索]ボタン押下
	 */
	protected void btnSearch_Click() {

		try {

			// 科目体系を入力してください。
			if (Util.isNullOrEmpty(mainView.ctrlItemOrganizationReference.getCode())) {
				showMessage(mainView, "I00037", "C00609"); // 科目体系
				mainView.ctrlItemOrganizationReference.btn.requestFocus();
				return;
			}

			// 指示画面を再反映
			refresh();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 科目マスタよりデータを取得し、一覧に設定する。
	 * 
	 * @param list 科目
	 */
	protected void initList(List<Item> list) {

		// 一覧をクリアする
		mainView.dndTable.removeRow();

		// 科目マスタが存在しない場合
		if (list == null || list.isEmpty()) {
			return;
		}

		// 科目情報を一覧に表示する
		for (Item item : list) {
			mainView.dndTable.addRow(getItemDataRow(item));
		}

		// 1行目を選択する
		mainView.dndTable.setRowSelection(0);

	}

	/**
	 * 科目の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ItemSearchCondition getSearchCondition() {
		ItemSearchCondition condition = new ItemSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setSumItem(true);
		condition.setTitleItem(true);

		return condition;
	}

	/**
	 * 科目集計の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ItemSummarySearchCondition getItemSummarySearchCondition() {
		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());

		return condition;
	}

	/**
	 * 検索条件に該当する科目のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する科目のリスト
	 * @throws TException
	 */
	protected List<Item> getItem(ItemSearchCondition condition) throws TException {
		return (List<Item>) request(getItemModelClass(), "get", condition);
	}

	/**
	 * 科目モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getItemModelClass() {
		return ItemManager.class;
	}

	/**
	 * モデルクラスを返す
	 * 
	 * @return モデルクラス
	 */
	protected Class getItemSummaryModelClass() {
		return ItemSummaryManager.class;
	}

	/**
	 * 科目情報を一覧に表示する形式に変換し返す
	 * 
	 * @param item 科目
	 * @return 一覧に表示する形式に変換された科目
	 */
	protected Object[] getItemDataRow(Item item) {
		return new Object[] { item.getCode(), item.getNames(), getWord(ItemSumType.getName(item.getItemSumType())),
				getWord(ItemType.getName(item.getItemType())), getWord(Dc.getName(item.getDc())), item };
	}

	/**
	 * 科目集計マスタよりデータを取得し、科目集計に設定する。
	 * 
	 * @param list 科目集計
	 */
	protected void initTree(List<ItemSummary> list) {

		// ツリーのクリア処理
		ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot();
		if (root != null) {
			root.removeAllChildren();
			mainView.tree.reload();
		}

		// 科目集計表示
		setTreeItemSummaryData(list);
	}

	/**
	 * 科目集計の検索条件を返す
	 * 
	 * @return 検索条件
	 */
	protected ItemSummarySearchCondition getTreeSearchCondition() {

		ItemSummarySearchCondition condition = new ItemSummarySearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());

		return condition;

	}

	/**
	 * 検索条件に該当する科目集計のリストを返す
	 * 
	 * @param condition 検索条件
	 * @return 検索条件に該当する科目集計のリスト
	 * @throws TException
	 */
	protected List<ItemSummary> getItemSummary(ItemSummarySearchCondition condition) throws TException {
		return (List<ItemSummary>) request(getItemSummaryModelClass(), "get", condition);
	}

	/**
	 * 一覧へドロップ処理
	 * 
	 * @param value DepartmentTransferData 渡す科目ノードデータ
	 */
	protected void dropToTable(Object value) {

		if (!isDragFromTree) {
			return;
		}

		// 希望の科目ノードデータじゃない場合、処理不要
		if (!(value instanceof ItemSummaryTransferData)) {
			return;
		}

		ItemSummaryTransferData transferData = (ItemSummaryTransferData) value;

		// リストが0件の場合、処理不要
		if (transferData.getNodeList() == null || transferData.getNodeList().isEmpty()) {
			return;
		}

		// ツリーから削除(選択のみ)＆スプレッドへ復元
		deleteTreeDataAndRestoreTable();

	}

	/**
	 * 科目集計ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @param tree 科目集計リスト
	 * @return TTeeの階層データ
	 */
	protected List<ItemSummaryTreeNode> dragFromTree(DragGestureEvent evt, TDnDTree tree) {

		ArrayList<ItemSummaryTreeNode> listTreeNode = new ArrayList<ItemSummaryTreeNode>();

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

						ItemSummaryTreeNode node = (ItemSummaryTreeNode) path.getLastPathComponent();

						Enumeration enumNode = node.breadthFirstEnumeration();
						while (enumNode.hasMoreElements()) {
							ItemSummaryTreeNode eachNode = (ItemSummaryTreeNode) enumNode.nextElement();
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
	 * 科目集計ドロップ処理
	 * 
	 * @param evt ドロップターゲットイベント
	 * @param tree 科目集計リスト
	 * @param value 指定されたデータ
	 */
	protected void dropToTree(DropTargetDropEvent evt, TDnDTree tree, Object value) {

		// 転送をチェック
		if (evt.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {

			// drop位置にあるTreeNodeを取得する。ドロップ位置にノードが存在しない場合、処理を終了する。
			DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
			Point p = evt.getLocation();
			TreePath targetPath = tree.getPathForLocation(p.x, p.y);

			ItemSummaryTreeNode targetNode = null;

			if (targetPath == null) {
				targetNode = (ItemSummaryTreeNode) model.getRoot();
			} else {
				targetNode = (ItemSummaryTreeNode) targetPath.getLastPathComponent();
			}

			// 移動先ノード
			TreePath dropPath = new TreePath(model.getPathToRoot(targetNode.getParent() == null ? targetNode
				: targetNode.getParent()));// 移動先パス取得

			// 変更済みのノードリスト（親区別用）
			List<ItemSummaryTreeNode> insertedNodeList = new ArrayList<ItemSummaryTreeNode>();

			if (!(value instanceof ItemSummaryTransferData)) {
				return;
			}

			// 渡す科目集計ノードデータ
			ItemSummaryTransferData transferData = (ItemSummaryTransferData) value;

			// 科目集計ノードリスト
			List<ItemSummaryTreeNode> list = transferData.getNodeList();

			if (list == null || list.isEmpty()) {
				return;
			}

			// チェック、目標はドラッグ中であれば、ドロップ不要
			if (list.contains(targetNode)) {
				evt.dropComplete(false);
				return;
			}

			for (ItemSummaryTreeNode node : list) {

				// 自分の親ノード
				ItemSummaryTreeNode parentNode = (ItemSummaryTreeNode) node.getParent();

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

					// 科目集計の編集
					ItemSummary bean = node.getItemSummaryDisp().getItemSummary();// (ノードに科目集計情報はすでに持っている)

					ItemSummaryTreeNode targetParentNode = (ItemSummaryTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						// dropするtargetの親データのKMK_CODEをセット
						bean.setSumCode(targetParentNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetParentNode.getItemSummaryDisp().getItem().getNames());
						targetParentNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));

					} else {
						// dropするtargetデータのKMK_CODEをセット
						bean.setSumCode(targetNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetNode.getItemSummaryDisp().getItem().getNames());
						targetNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}

				} else {
					// spread -> treeの場合

					// 科目集計の作成
					Item item = node.getItemSummaryDisp().getItem();// (ノードに科目情報はすでに持っている)
					ItemSummary bean = new ItemSummary();// (科目集計情報の新規作成)

					// 初期値の設定
					bean.setCompanyCode(getCompanyCode());
					bean.setKmtCode(mainView.ctrlItemOrganizationReference.getCode());
					bean.setKmtName(mainView.ctrlItemOrganizationReference.getNames());
					bean.setKmkCode(item.getCode());
					bean.setKmkName(item.getNames());
					bean.setKokName(item.getNames());
					bean.setOdr("1");
					bean.setHyjKbn(1);
					bean.setDateFrom(DateUtil.getDate(1900, 1, 1));
					bean.setDateTo(DateUtil.getDate(2099, 12, 31));

					ItemSummaryTreeNode targetParentNode = (ItemSummaryTreeNode) targetNode.getParent();

					if (targetParentNode != null && evt.getDropAction() != DnDConstants.ACTION_MOVE) {
						// dropするtargetの親データのKMK_CODEをセット
						bean.setSumCode(targetParentNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetParentNode.getItemSummaryDisp().getItem().getNames());
						targetParentNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetParentNode, targetParentNode.getIndex(targetNode));

					} else {
						// dropするtargetデータのKMK_CODEをセット
						bean.setSumCode(targetNode.getItemSummaryDisp().getItem().getCode());
						bean.setSumName(targetNode.getItemSummaryDisp().getItem().getNames());
						targetNode.getItemSummaryDisp().setParentCode(bean.getSumCode());
						model.insertNodeInto(node, targetNode, targetNode.getChildCount());
					}
					node.getItemSummaryDisp().setItemSummary(bean);// (ノードに科目集計情報の新規作成)
				}
				node.setUserObject(ItemSummaryTreeNode.getName(node.getItemSummaryDisp()));

				// ツリーを更新します
				model.reload(node);
			}

			evt.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			if (SourceType.TABLE.equals(transferData.getSource())) {

				// ソースがスプレッドの場合、一覧から削除
				mainView.dndTable.removeSelectedRows();
			}

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
	public void setTreeItemSummaryData(List<ItemSummary> list) {
		List<ItemSummaryDisp> listDnDData = new ArrayList<ItemSummaryDisp>();

		// root
		ItemSummaryDisp root = new ItemSummaryDisp();
		root.setCode(dummyRootCode);
		root.setParentCode(null);

		Item rootItem = new Item();
		rootItem.setCode(null);
		root.setItem(rootItem);
		listDnDData.add(root);

		if (list != null) {
			for (ItemSummary itemSummary : list) {
				ItemSummaryDisp disp = new ItemSummaryDisp();
				disp.setCompanyCode(itemSummary.getCompanyCode());
				disp.setCode(itemSummary.getKmkCode());
				disp.setParentCode(getParentCode(itemSummary));

				Item item = new Item();
				item.setCode(itemSummary.getKmkCode());
				item.setNames(itemSummary.getKmkName());
				item.setItemSumType(itemSummary.getItemSumType());
				item.setItemType(itemSummary.getItemType());
				item.setDc(itemSummary.getDc());
				disp.setItem(item);

				disp.setItemSummary(itemSummary);

				listDnDData.add(disp);
			}
		}

		setTreeData(listDnDData);
	}

	/**
	 * @param itemSummary
	 * @return 親コード
	 */
	protected String getParentCode(ItemSummary itemSummary) {

		if (!Util.isNullOrEmpty(itemSummary.getSumCode())) {
			// 集計コードがnullじゃない場合はひとつ前の親のKMK_CODEを設定する。

			return itemSummary.getSumCode();

		}
		return dummyRootCode;
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param listDnDData 階層データ
	 */
	public void setTreeData(List<ItemSummaryDisp> listDnDData) {

		int rootNum = 0;
		/** 階層データのルートデータ */
		ItemSummaryDisp baseDndData = null;
		ItemSummaryTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// 上位階層コード = ブランク のデータはルートノードに設定する。
		for (ItemSummaryDisp disp : listDnDData) {
			if (Util.isNullOrEmpty(disp.getParentCode())) {
				baseDndData = disp;

				baseNode = new ItemSummaryTreeNode(disp);
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
	protected void createTree(ItemSummaryTreeNode parentNode, ItemSummaryDisp parent, List<ItemSummaryDisp> listData) {
		for (ItemSummaryDisp dndData : listData) {

			if (parent.getCode().equals(dndData.getParentCode())) {
				ItemSummaryTreeNode childNode = new ItemSummaryTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listData);
			}
		}
	}

	/**
	 * [複写]ボタン押下
	 */
	protected void btnCopy_Click() {

		// 編集画面を生成
		copyView = new MG0071ItemSummaryMasterCopyDialog(getCompany(), mainView.getParentFrame(), true);

		// editNodeを初期化
		editNode = (ItemSummaryTreeNode) mainView.tree.getSelectionPath().getLastPathComponent();

		// 編集画面のイベント定義
		addCopyViewEvent();

		// 編集画面を初期化する
		initCopyView();

		// Expand
		mainView.tree.expandPath(mainView.tree.getSelectionPath());

		// 編集画面を表示
		copyView.setLocationRelativeTo(null);
		copyView.setVisible(true);
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
			ItemSummarySearchCondition condition = getItemSummarySearchCondition();
			byte[] data = (byte[]) request(getItemSummaryModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// 出力
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02421") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [確定]ボタン押下
	 */
	protected void btnSettle_Click() {

		try {

			// 確認メッセージを表示する
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			List<ItemSummary> list = getTreeData();

			// 登録処理
			request(getItemSummaryModelClass(), "entryItemSummary", mainView.ctrlItemOrganizationReference.getCode(),
				list);

			// 処理完了メッセージ
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 科目集計ドラッグ処理
	 * 
	 * @param evt ドラッグターゲットイベント
	 * @return TTableの階層データ
	 */
	public List<ItemSummaryTreeNode> dragFromTable(DragGestureEvent evt) {

		List<ItemSummaryDisp> listDnDData = new ArrayList<ItemSummaryDisp>();

		InputEvent inputEvent = evt.getTriggerEvent();
		try {

			// 左クリック時
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((evt.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

					// 一覧.選択行のデータを取得。複数行選択されている場合は全て取得する。

					// 選択行のデータを取得。
					for (Item item : getSelectedItem()) {
						ItemSummaryDisp data = new ItemSummaryDisp();
						data.setCode(item.getCode());
						data.setItem(item);
						listDnDData.add(data);
					}

				}
			}
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		} catch (Exception ex) {
			// 処理なし
		}

		return createItemSummaryTree(listDnDData);
	}

	/**
	 * 一覧で選択されている科目を返す
	 * 
	 * @return 一覧で選択されている科目
	 */
	protected List<Item> getSelectedItem() {

		List<Item> list = new ArrayList<Item>();
		for (int i : mainView.dndTable.getSelectedRows()) {
			list.add((Item) mainView.dndTable.getRowValueAt(i, MG0071ItemSummaryMasterPanel.SC.bean));
		}

		return list;
	}

	/**
	 * コンストラクタ Tree→Table、Tree→Tree用
	 * 
	 * @param list
	 * @return TTeeの階層データ
	 */
	public List<ItemSummaryTreeNode> createItemSummaryTree(List list) {

		if (list == null || list.isEmpty()) {
			return null;
		}

		Object obj = list.get(0);

		if (obj instanceof ItemSummaryDisp) {
			List<ItemSummaryTreeNode> tmpList = new ArrayList<ItemSummaryTreeNode>();
			for (int i = 0; i < list.size(); i++) {

				ItemSummaryDisp dnDData = (ItemSummaryDisp) list.get(i);
				ItemSummaryTreeNode itemSummaryTreeNode = new ItemSummaryTreeNode(dnDData);
				tmpList.add(itemSummaryTreeNode);
			}
			return tmpList;
		}

		return list;
	}

	/**
	 * 科目体系コードの削除＆スプレッドへ復元
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
			ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot(); // ルートノード

			if (root == null || root.getItemSummaryDisp() == null) {
				return;
			}

			String rootCode = root.getItemSummaryDisp().getCode(); // ルートコード

			// 既に復元したノードを記録する
			List<ItemSummaryTreeNode> listFinish = new ArrayList<ItemSummaryTreeNode>();

			// すべての選択肢を削除する
			for (TreePath path : deletePaths) {

				if (path == null) {
					return;
				}

				Object obj = path.getLastPathComponent();

				if (!(obj instanceof ItemSummaryTreeNode)) {
					continue;
				}

				ItemSummaryTreeNode selectedNode = (ItemSummaryTreeNode) obj;
				if (selectedNode.getItemSummaryDisp() == null) {

					continue;

				} else {

					Enumeration enumNode = selectedNode.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						ItemSummaryTreeNode node = (ItemSummaryTreeNode) enumNode.nextElement();

						if (listFinish.contains(node)) {
							continue;
						} else {
							listFinish.add(node);
						}

						if (node.getItemSummaryDisp().getItem() != null
							&& !Util.isNullOrEmpty(node.getItemSummaryDisp().getItem().getCode())) {

							// 一覧に追加する
							mainView.dndTable.addRow(getItemDataRow(node.getItemSummaryDisp().getItem()));
						}
					}

				}

				// ルートノードが選択されている処理を中断する。
				String selectCode = selectedNode.getItemSummaryDisp().getCode();

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
	 * ルートノードを含めない階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<ItemSummary> getTreeData() {

		List<ItemSummary> list = new ArrayList<ItemSummary>();

		ItemSummaryTreeNode root = (ItemSummaryTreeNode) mainView.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			ItemSummaryTreeNode node = (ItemSummaryTreeNode) e.nextElement();

			if (node.getItemSummaryDisp().getItemSummary() == null) {
				continue;
			}

			ItemSummaryDisp disp = node.getItemSummaryDisp().clone();// 科目、科目集計の２つのデータを持つ
			ItemSummary bean = disp.getItemSummary();

			if (dummyRootCode.equals(bean.getSumCode())) {
				bean.setSumCode(null);
			}

			list.add(bean);

		}

		if (list.isEmpty()) {
			return null;
		}

		return list;
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
	 */
	protected void initEditView() {
		editView.setTitle(getWord("C00977"));// 編集画面

		// 情報設定
		editView.ctrlKmtCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getKmtCode());
		editView.ctrlKmtCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getKmtName());
		editView.ctrlKmkCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getKmkCode());
		editView.ctrlKmkCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getKmkName());
		editView.ctrlKokName.setValue(editNode.getItemSummaryDisp().getItemSummary().getKokName());
		editView.ctrlSumCode.setCode(editNode.getItemSummaryDisp().getItemSummary().getSumCode());
		editView.ctrlSumCode.setNames(editNode.getItemSummaryDisp().getItemSummary().getSumName());
		editView.ctrlOdr.setValue(editNode.getItemSummaryDisp().getItemSummary().getOdr());
		if (editNode.getItemSummaryDisp().getItemSummary().getHyjKbn() == 1) {
			editView.ctrlHyjKbnVisible.setSelected(true);
		} else {
			editView.ctrlHyjKbnInvisible.setSelected(true);
		}
		editView.ctrlBeginDate.setValue(editNode.getItemSummaryDisp().getItemSummary().getDateFrom());
		editView.ctrlEndDate.setValue(editNode.getItemSummaryDisp().getItemSummary().getDateTo());

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

			// 入力された科目集計情報を取得
			ItemSummary bean = getInputedItemSummary();

			// 入力された科目集計情報をノードに反映
			editNode.getItemSummaryDisp().setItemSummary(bean);

			editNode.setUserObject(ItemSummaryTreeNode.getName(editNode.getItemSummaryDisp()));

			// ノードデータをツリーに反映（データも表示も）
			mainView.tree.reload();
			mainView.tree.repaint();
			mainView.tree.expandAll();

			// 編集画面を閉じる
			btnEditViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 編集画面で入力された科目集計を返す
	 * 
	 * @return 編集画面で入力された科目集計
	 */
	protected ItemSummary getInputedItemSummary() {

		ItemSummary bean = new ItemSummary();

		bean.setCompanyCode(super.getCompanyCode());
		bean.setKmtCode(editView.ctrlKmtCode.getCode());
		bean.setKmtName(editView.ctrlKmtCode.getNames());
		bean.setKmkCode(editView.ctrlKmkCode.getCode());
		bean.setKmkName(editView.ctrlKmkCode.getNames());
		bean.setKokName(editView.ctrlKokName.getValue());
		bean.setSumCode(editView.ctrlSumCode.getCode());
		bean.setSumName(editView.ctrlSumCode.getNames());
		bean.setOdr(editView.ctrlOdr.getValue());

		if (editView.ctrlHyjKbnVisible.isSelected()) {
			bean.setHyjKbn(1);
		} else {
			bean.setHyjKbn(0);
		}

		bean.setDateFrom(editView.ctrlBeginDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		return bean;
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

		// 公表科目名称が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlKokName.getValue())) {
			showMessage(editView, "I00037", "C00624"); // 公表科目名称
			editView.ctrlKokName.requestTextFocus();
			return false;
		}

		// 出力順序が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlOdr.getValue())) {
			showMessage(editView, "I00037", "C00776"); // レベル０
			editView.ctrlOdr.requestTextFocus();
			return false;
		}

		// 開始年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055"); // 開始年月日を入力してください。
			editView.ctrlBeginDate.requestTextFocus();
			return false;
		}

		// 終了年月日が未入力の場合エラー
		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261"); // 終了年月日を入力してください。
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// 開始年月日,終了年月日チェック
		if (!Util.isSmallerThenByYMD(editView.ctrlBeginDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlBeginDate.requestFocus();
			return false;
		}

		return true;
	}

	/**
	 * 複写画面のイベント定義。
	 */
	protected void addCopyViewEvent() {

		// [確定]ボタン押下
		copyView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopyViewSettle_Click();
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [戻る]ボタン押下
		copyView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopyViewClose_Click();
				copyView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * 複写画面を初期化する
	 */
	protected void initCopyView() {
		copyView.setTitle(getWord("C01738"));// 複写画面

		// 情報設定
		copyView.ctrlItemOrganizationReference.code.setValue(mainView.ctrlItemOrganizationReference.getCode());
		copyView.ctrlItemOrganizationReference.name.setValue(mainView.ctrlItemOrganizationReference.getNames());

		preKmtCode = mainView.ctrlItemOrganizationReference.getCode();
	}

	/**
	 * 複写画面[確定]ボタン押下
	 */
	protected void btnCopyViewSettle_Click() {

		try {

			// 入力チェックを行う
			if (!isCopyViewInputRight()) {
				return;
			}

			// 確認メッセージ
			// Q00078 データが上書きされますが、よろしいですか？
			if (!showConfirmMessage(copyView, "Q00078")) {
				return;
			}

			// 科目体系コード変更なしの場合処理不要
			if (copyView.ctrlItemOrganizationReference.getCode().equals(preKmtCode)) {
				btnCopyViewClose_Click();
				return;
			}

			// 登録処理
			request(getItemSummaryModelClass(), "entryCopyItemSummary", preKmtCode,
				copyView.ctrlItemOrganizationReference.getCode());

			// 入力された科目集計情報を反映
			mainView.ctrlItemOrganizationReference.code.setValue(copyView.ctrlItemOrganizationReference.getCode());
			mainView.ctrlItemOrganizationReference.name.setValue(copyView.ctrlItemOrganizationReference.getNames());

			// 指示画面を再反映
			refresh();

			// 複写画面を閉じる
			btnCopyViewClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * 複写画面[戻る]ボタン押下
	 */
	protected void btnCopyViewClose_Click() {
		copyView.setVisible(false);
	}

	/**
	 * 複写画面で入力した値が妥当かをチェックする
	 * 
	 * @return 複写画面で入力した値が妥当か。trueの場合正常、falseの場合エラーあり。
	 */
	protected boolean isCopyViewInputRight() {

		// 科目体系が未入力の場合エラー
		if (Util.isNullOrEmpty(copyView.ctrlItemOrganizationReference.code.getValue())) {
			showMessage(copyView, "I00037", "C00609"); // 科目体系
			copyView.ctrlItemOrganizationReference.btn.requestFocus();
			return false;
		}

		// 科目体系コードが変更ない場合エラー
		if (copyView.ctrlItemOrganizationReference.getCode().equals(preKmtCode)) {
			// I00595 同じ科目体系に複写できません。
			showMessage(copyView, "I00595");
			copyView.ctrlItemOrganizationReference.btn.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * 科目体系コードに紐づくデータを指示画面に再反映する
	 */
	protected void refresh() {

		try {
			// 科目集計検索条件の定義
			ItemSummarySearchCondition itemSumCondition = getTreeSearchCondition();

			// DBから科目集計情報を取得する
			List<ItemSummary> itemSumList;

			itemSumList = getItemSummary(itemSumCondition);

			// 科目検索条件の定義
			ItemSearchCondition itemCondition = getSearchCondition();

			// DBから科目情報を取得する
			List<Item> itemList = getItem(itemCondition);

			// ツリー構築
			initTree(itemSumList);

			// フィルター
			if (itemList != null) {
				for (ItemSummary itemSum : itemSumList) {
					String itemSumCode = itemSum.getKmkCode();

					for (int i = itemList.size() - 1; i >= 0; i--) {
						if (itemSumCode.equals(itemList.get(i).getCode())) {
							itemList.remove(i);
							break;
						}
					}
				}
			}

			// 科目一覧へ反映
			initList(itemList);

			setMainButtonEnabled(!Util.isNullOrEmpty(mainView.ctrlItemOrganizationReference.getCode()));

			kmtCode = mainView.ctrlItemOrganizationReference.getCode();
			kmtName = mainView.ctrlItemOrganizationReference.getNames();

		} catch (Exception e) {
			errorHandler(e);
		}
	}
}

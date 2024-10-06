package jp.co.ais.trans.common.gui.dnd;

import java.util.*;

import javax.swing.tree.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * 階層データ作成基盤コンポーンネントコントロール
 */
public class TDnDHierarchyUnitCtrl extends TAppletClientBase {

	/** 階層データ作成基盤コンポーンネントPanle */
	protected TDnDHierarchyUnit panel;

	/**
	 * コンストラクタ
	 * 
	 * @param panel
	 */
	public TDnDHierarchyUnitCtrl(TDnDHierarchyUnit panel) {
		try {

			this.panel = panel;
		} catch (Exception e) {
			errorHandler(panel, e);
		}
	}

	/**
	 * 階層データを削除する <br>
	 * DELETEキー 押下時の処理。階層データから選択ノードを削除する。
	 * 
	 * @return 階層データから選択ノード
	 */
	public List<DnDData> doDeleteTreeData() {
		THierarchyTreeNode selectedNode = (THierarchyTreeNode) panel.tree.getLastSelectedPathComponent();

		// ノードが選択されていない,処理を中断する。
		if (selectedNode == null) {
			return null;
		}

		// ルートノードが選択されている,処理を中断する。
		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();
		String rootCode = root.getDndData().getCode();
		String selectCode = selectedNode.getDndData().getCode();
		if (rootCode.equals(selectCode)) {
			return null;
		}

		// Q：選択された項目以下を削除しますか？
		if (!showConfirmMessage(panel, "Q00051")) {
			return null;
		}

		// 一覧に追加する
		List<DnDData> listDndData = new ArrayList<DnDData>();

		Enumeration e = selectedNode.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			listDndData.add(node.getDndData());
		}
		// 一覧にデータを追加する
		for (DnDData dndData : listDndData) {
			panel.ssJournal.addRow(dndData);
		}
		// 一覧をソートする
		panel.ssJournal.doSort();

		// 選択ノードを削除し
		DefaultTreeModel model = (DefaultTreeModel) panel.tree.getModel();
		model.removeNodeFromParent(selectedNode);

		// 削除後、階層データではルートノードを選択状態にする。
		TreePath visiblePath = new TreePath(model.getPathToRoot(root));
		panel.tree.setSelectionPath(visiblePath);

		return listDndData;
	}

	/**
	 * 階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<DnDData> getTreeData() {
		List<DnDData> listDndData = new ArrayList<DnDData>();

		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			DnDData dndData = node.getDndData();
			if (!node.equals(root)) {
				dndData.setTopCode(((THierarchyTreeNode) node.getParent()).getDndData().getCode());
			} else {
				dndData.setTopCode("");
			}
			listDndData.add(dndData);
		}

		return listDndData;
	}

	/**
	 * ルートノードを含めない階層データを取得する
	 * 
	 * @return 階層データ
	 */
	public List<DnDData> getTreeDataNoRoot() {
		List<DnDData> listDndData = new ArrayList<DnDData>();

		THierarchyTreeNode root = (THierarchyTreeNode) panel.tree.getModel().getRoot();

		Enumeration e = root.breadthFirstEnumeration();
		while (e.hasMoreElements()) {
			THierarchyTreeNode node = (THierarchyTreeNode) e.nextElement();
			if (!node.equals(root)) {
				THierarchyTreeNode parentNode = (THierarchyTreeNode) (node.getParent());

				// 上位階層コード = ルートノード
				if (parentNode.equals(root)) {
					DnDData dndData = new DnDData();
					dndData.setCode(node.getDndData().getCode());
					dndData.setName(node.getDndData().getName());
					dndData.setTopCode(""); // 上位階層コード = ブランク
					listDndData.add(dndData);
				} else {
					DnDData dndData1 = node.getDndData();
					dndData1.setTopCode(parentNode.getDndData().getCode());
					listDndData.add(dndData1);
				}
			}
		}

		return listDndData;
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param listDnDData 階層データ
	 */
	public void setTreeData(List<DnDData> listDnDData) {
		int rootNum = 0;
		THierarchyTreeNode baseNode = null;

		DefaultTreeModel model = null;

		// 上位階層コード = ブランク のデータはルートノードに設定する。
		for (DnDData ndnData : listDnDData) {
			if (Util.isNullOrEmpty(ndnData.getTopCode())) {
				panel.baseDndData = ndnData;

				baseNode = new THierarchyTreeNode(ndnData);
				model = new DefaultTreeModel(baseNode);
				panel.tree.setModel(model);
				rootNum = rootNum + 1;
			}
		}

		if (baseNode == null || rootNum > 1) {
			this.errorHandler(panel, "E00009");
			return;
		}

		// 子階層データを設定する
		createTree(baseNode, panel.baseDndData, listDnDData);

		model.reload();
		TreePath visiblePath = new TreePath(((DefaultTreeModel) panel.tree.getModel()).getPathToRoot(baseNode));
		panel.tree.setSelectionPath(visiblePath);

		// すべて展開します
		TreeNode root = (TreeNode) panel.tree.getModel().getRoot();
		panel.tree.expandAll(new TreePath(root), true);
	}

	/**
	 * 階層データを設定する
	 * 
	 * @param parentNode
	 * @param parent
	 * @param listDnDData 階層データ
	 */
	private void createTree(THierarchyTreeNode parentNode, DnDData parent, List<DnDData> listDnDData) {
		for (DnDData dndData : listDnDData) {

			if (parent.getCode().equals(dndData.getTopCode())) {
				THierarchyTreeNode childNode = new THierarchyTreeNode(dndData);
				parentNode.add(childNode);

				createTree(childNode, dndData, listDnDData);
			}
		}
	}
}

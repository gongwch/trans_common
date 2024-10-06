package jp.co.ais.trans.common.gui.dnd;

import java.awt.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTreeの機能拡張クラス。<BR>
 * ドラッグ＆ドロップ操作を行なうためのメソッドを追加する。
 * 
 * @author Yanwei
 */
public class TDnDTree extends TTree implements DropTargetListener, DragGestureListener, DragSourceListener {

	/** tree -> spread :false tree -> tree:true */
	public boolean treeTotree = false;

	/**
	 * コンストラクタ ドラッグ＆ドロップ用の設定を行なう。
	 * 
	 * @param dndData TTｒeeの階層データ受け渡しに使用するクラス
	 */
	public TDnDTree(DnDData dndData) {
		// ①継承元のコンストラクタを呼ぶ。
		super();

		// ②ドラッグ＆ドロップを実現するためのメソッドを呼ぶ。
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
			this);
		new DropTarget(this, this);

		// ③メソッド引数.ルートデータを階層データのルートノードに設定する。
		THierarchyTreeNode baseNode = new THierarchyTreeNode(dndData);
		DefaultTreeModel model = new DefaultTreeModel(baseNode);
		super.setModel(model);

		TreePath visiblePath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(baseNode));
		this.setSelectionPath(visiblePath);

		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * コンストラクタ.
	 */
	public TDnDTree() {
		super();
		DefaultTreeModel model = new DefaultTreeModel(null);
		super.setModel(model);
		// ドラッグ＆ドロップを実現するためのメソッドを呼ぶ。
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
			this);
		new DropTarget(this, this);

		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * ドラッグ開始時に呼ばれるメソッド ドラッグしたデータを転送する。
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		InputEvent inputEvent = e.getTriggerEvent();
		try {
			// ②drag位置にTHierarchyTreeNodeが存在しない場合,①.THierarchyTreeNodeがルートノードの場合,処理を終了
			Point pt = e.getDragOrigin();
			TreePath checkPathCheck = getPathForLocation(pt.x, pt.y);
			if (checkPathCheck == null || checkPathCheck.getParentPath() == null) {
				return;
			}

			// ①drag位置にあるTHierarchyTreeNodeを取得する。
			if (SwingUtilities.isLeftMouseButton((MouseEvent) inputEvent)) {
				if ((e.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {
					TreePath path = getLeadSelectionPath();
					THierarchyTreeNode node = (THierarchyTreeNode) path.getLastPathComponent();

					ArrayList<THierarchyTreeNode> listTreeNode = new ArrayList<THierarchyTreeNode>();
					Enumeration enumNode = node.breadthFirstEnumeration();
					while (enumNode.hasMoreElements()) {
						THierarchyTreeNode eachNode = (THierarchyTreeNode) enumNode.nextElement();
						listTreeNode.add(eachNode);
					}
					// ③ ①.THierarchyTreeNodeを引数にTTransferableクラスをインスタンス化する
					TTransferable transfer = new TTransferable(listTreeNode);

					// ④ドラッグを開始する。
					e.startDrag(DragSource.DefaultCopyDrop, transfer, this);
				}
			}

			// init
			treeTotree = false;
		} catch (InvalidDnDOperationException ioe) {
			// 処理なし
		}
	}

	/**
	 * ドロップ時に呼ばれるメソッド 転送データを設定する。
	 */
	public void drop(DropTargetDropEvent e) {
		// 転送をチェック(転送データがTTransferable.nodeFlavor形式でない場合)
		if (e.isDataFlavorSupported(TTransferable.nodeFavor)) {
			// ①転送データ：List<THierarchyTreeNode>を取得する。
			List<THierarchyTreeNode> list = TTransferable.getTHerarchyTreeNodeList(e.getTransferable());

			// ② 転送データが存在しない場合は処理終了
			if (list == null || list.isEmpty()) {
				e.rejectDrop();
				return;
			}

			// ③drop位置にあるTHierarchyTreeNodeを取得する。ドロップ位置にノードが存在しない場合、処理を終了する。
			DefaultTreeModel model = (DefaultTreeModel) getModel();
			Point p = e.getLocation();
			TreePath targetPath = getPathForLocation(p.x, p.y);
			if (targetPath == null) {
				e.dropComplete(false);
				return;
			}

			// 目標ノード
			THierarchyTreeNode targetNode = (THierarchyTreeNode) targetPath.getLastPathComponent();
			TreePath dropPath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(targetNode));// 目標パス

			// 自分の親ノード
			THierarchyTreeNode parentNode = (THierarchyTreeNode) list.get(0).getParent();

			// tree -> tree
			if (parentNode != null) {
				// ④ 同コンポーネント内のドラッグ＆ドロップがないか、 以下の項目をチェックする。
				treeTotree = true;
				// 親ノードを子ノードにドロップしようとしている場合、処理を終了
				TreePath dragPath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(list.get(0)));// 源パス

				String strDragPath = dragPath.toString();// 源パス
				String strDropPath = dropPath.toString();// 目標パス
				String subDragPath = strDragPath.substring(0, strDragPath.length() - 1);

				if (strDropPath.startsWith(subDragPath)) {
					e.dropComplete(false);
					return;
				}
				// 自分に自分をドロップしようとしている場合、処理を終了
				if (parentNode.equals(targetNode)) {
					e.dropComplete(false);
					return;
				}
				if (targetNode.equals(list.get(0))) {
					e.dropComplete(false);
					return;
				}
			} else {
				// spread -> tree
				treeTotree = false;
			}

			e.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

			// ⑤ ③.THierarchyTreeNodeに①.転送データを子ノードとして追加する。
			if (parentNode != null) {
				// tree -> tree
				// 転送したノードをTreeから削除
				model.removeNodeFromParent(list.get(0));
				model.insertNodeInto(list.get(0), targetNode, targetNode.getChildCount());
			} else {
				// spread -> tree
				for (THierarchyTreeNode treeNode : list) {
					model.insertNodeInto(treeNode, targetNode, targetNode.getChildCount());
				}
			}

			// 更新します
			((DefaultTreeModel) this.getModel()).reload(list.get(0));

			// 展開します
			expandAll(dropPath, true);

			// 選択
			this.setSelectionPath(dropPath);

			// ⑥ ドロップ処理を完了する。
			e.dropComplete(true);

		} else {
			e.rejectDrop();
		}
	}

	/**
	 * ドロップ終了時に呼ばれるメソッド ドラッグ＆ドロップを完了する。
	 */
	public void dragDropEnd(DragSourceDropEvent e) {

		// ①ドロップが正常に完了したかをチェックする。
		if (!e.getDropSuccess()) {
			return;
		}

		// ②転送データ：List<THierarchyTreeNode>を取得する。
		List<THierarchyTreeNode> list = TTransferable.getTHerarchyTreeNodeList(e.getDragSourceContext()
			.getTransferable());

		// ②.転送データが存在しない場合は処理終了
		if (list == null || list.isEmpty()) {
			return;
		}

		// tree -> spread
		if (treeTotree == false) {
			// 転送したノードをTreeから削除
			DefaultTreeModel model = (DefaultTreeModel) getModel();
			model.removeNodeFromParent(list.get(0));

		} else {
			// tree -> tree
			return;
		}
		// 更新します
		((DefaultTreeModel) this.getModel()).reload();

		TreePath rootPath = new TreePath(((DefaultTreeModel) this.getModel()).getRoot());
		this.setSelectionPath(rootPath);

		// 展開します
		expandAll(rootPath, true);
		// 選択
		this.setSelectionPath(rootPath);

	}

	// @Override
	public void dragEnter(DragSourceDragEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	// @Override
	public void dragExit(DragSourceEvent e) {
		e.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}

	public void dragOver(DragSourceDragEvent e) {
		// 未使用
	}

	public void dropActionChanged(DragSourceDragEvent e) {
		// 未使用
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// 未使用

	}

	public void dragExit(DropTargetEvent dte) {
		// 未使用

	}

	public void dragOver(DropTargetDragEvent dtde) {
		// 未使用

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// 未使用

	}

	/**
	 * 展開します
	 * 
	 * @param parent TreePath
	 * @param expand 展開:true
	 */
	public void expandAll(TreePath parent, boolean expand) {
		TreeNode node = (TreeNode) parent.getLastPathComponent();
		if (node.getChildCount() >= 0) {
			for (Enumeration e = node.children(); e.hasMoreElements();) {
				TreeNode n = (TreeNode) e.nextElement();
				TreePath path = parent.pathByAddingChild(n);
				expandAll(path, expand);
			}
		}

		if (expand) {
			this.expandPath(parent);
		} else {
			this.collapsePath(parent);
		}
	}
}
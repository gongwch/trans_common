package jp.co.ais.trans2.common.gui.dnd;

import java.awt.*;
import java.awt.datatransfer.*;
import java.awt.dnd.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;

/**
 * Objectをドラッグ＆ドロップで受け渡す為のPanel
 */
public class TDnDTree extends TTree implements DragSourceListener, DragGestureListener, DropTargetListener {

	/** 名称 */
	public static Color rectColor = null;

	/** リスナー */
	protected TDnDListener listener;

	/** ドロップの目標ノード */
	public TreeNode dropTargetNode = null;

	/** ドラッグ中ノード */
	public TreeNode draggedNode = null;

	/**
	 * コンストラクタ.
	 */
	public TDnDTree() {
		this(null);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param root ルートノード
	 */
	public TDnDTree(TreeNode root) {
		super(root);
		setCellRenderer(new DnDTreeCellRenderer());

		// ドラッグ＆ドロップを実現するためのメソッドを呼ぶ。
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		new DropTarget(this, this);

		// ドラッグ＆ドロップの色設定
		initDragAndDropColor();
	}

	/**
	 * ドラッグ＆ドロップの色初期化
	 */
	public void initDragAndDropColor() {

		// RECT色設定
		rectColor = TUIManager.getTextDefaultForeground();
	}

	/**
	 * ドラッグ＆ドロップの色設定
	 * 
	 * @param color
	 */
	public void setDragAndDropColor(Color color) {

		// RECT色設定
		rectColor = color;
	}

	/**
	 * ドラッグ＆ドロップ実装のセット
	 * 
	 * @param listener リスナー
	 */
	public void setDnDListener(TDnDListener listener) {
		this.listener = listener;
	}

	/**
	 * ドラッグ処理.<br>
	 * Objectを受け渡す.
	 * 
	 * @see java.awt.dnd.DragGestureListener#dragGestureRecognized(java.awt.dnd.DragGestureEvent)
	 */
	public void dragGestureRecognized(DragGestureEvent dge) {

		try {
			if (!SwingUtilities.isLeftMouseButton((MouseEvent) dge.getTriggerEvent())) {
				return;
			}

			if ((dge.getDragAction() | DnDConstants.ACTION_COPY_OR_MOVE) != 0) {

				Transferable trans = new ObjectTransferable(listener.getDragData(dge));

				Object data = trans.getTransferData(ObjectTransferable.FLAVOR);
				// ドラッグするターゲットが存在しない場合
				if (data == null) {
					return;
				}
				dge.startDrag(DragSource.DefaultCopyDrop, trans);
			}

		} catch (Exception e) {
			// 処理なし
			// TODO:DEBUG, e.printStackTrace();
		}
	}

	/**
	 * ドロップ処理.<br>
	 * Objectを受け取る.
	 * 
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent dtde) {

		boolean gotData = false;

		try {
			// 転送をチェック
			if (!dtde.isDataFlavorSupported(ObjectTransferable.FLAVOR)) {
				return;
			}

			Object data = dtde.getTransferable().getTransferData(ObjectTransferable.FLAVOR);
			if (data == null) {
				return;
			}

			listener.dropData(dtde, data);
			gotData = true;

		} catch (Exception ex) {
			// TODO:DEBUG, ex.printStackTrace();
			// 処理なし

		} finally {
			dtde.dropComplete(gotData);
			dropTargetNode = null;
			draggedNode = null;
			repaint();
		}
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragDropEnd(java.awt.dnd.DragSourceDropEvent)
	 */
	public void dragDropEnd(DragSourceDropEvent dsde) {
		dropTargetNode = null;
		draggedNode = null;
		repaint();
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragEnter(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragEnter(DragSourceDragEvent dsde) {
		dsde.getDragSourceContext().setCursor(DragSource.DefaultMoveDrop);
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragExit(java.awt.dnd.DragSourceEvent)
	 */
	public void dragExit(DragSourceEvent dse) {
		dse.getDragSourceContext().setCursor(DragSource.DefaultMoveNoDrop);
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dragOver(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dragOver(DragSourceDragEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent dtde) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent dte) {
		// Override用
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent dtde) {

		try {
			// dropTargetNode は、描画用(Rectangle2D、Line)のflag
			Point pt = dtde.getLocation();
			TreePath path = getPathForLocation(pt.x, pt.y);

			if (path == null) {
				dropTargetNode = null;
				return;
			}

			DefaultMutableTreeNode targetNode = (DefaultMutableTreeNode) path.getLastPathComponent();
			dropTargetNode = targetNode;
			dtde.acceptDrag(dtde.getDropAction());

		} catch (Exception e) {
			// TODO:DEBUG, e.printStackTrace();
		} finally {
			repaint();
		}
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dropActionChanged(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dropActionChanged(DropTargetDragEvent dtde) {
		// Override用
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

	/**
	 * 弾出
	 * 
	 * @param dtde
	 */
	protected void rejectDrag(DropTargetDragEvent dtde) {
		dtde.rejectDrag();
		dropTargetNode = null; // dropTargetNode(flag)をnullにして
		repaint(); // Rectangle2D、Lineを消すためJTreeを再描画
	}

	/**
	 * セルRenderer
	 */
	protected class DnDTreeCellRenderer extends DefaultTreeCellRenderer {

		/** 下のパッド */
		protected static final int BOTTOM_PAD = 30;

		/** 目標ノードかどうか */
		protected boolean isTargetNode;

		/** ノードかどうか、false:フォルダ */
		protected boolean isTargetNodeLeaf;

		/** ノードのRECT */
		protected Insets normalInsets;

		/**  */
		public DnDTreeCellRenderer() {
			super();
			normalInsets = super.getInsets();
			new Insets(normalInsets.top, normalInsets.left, normalInsets.bottom + BOTTOM_PAD, normalInsets.right);
		}

		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean isSelected, boolean isExpanded,
			boolean isLeaf, int row, boolean hasFocus1) {
			isTargetNode = value == dropTargetNode;
			isTargetNodeLeaf = listener.isLeaf((TreeNode) value);
			return super.getTreeCellRendererComponent(tree, value, isSelected, isExpanded, isLeaf, row, hasFocus1);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			if (isTargetNode) {
				g.setColor(rectColor);
				if (isTargetNodeLeaf) {
					g.drawLine(0, 0, getSize().width, 0);
				} else {
					g.drawRect(0, 0, getSize().width - 1, getSize().height - 1);
				}
			}
		}
	}
}

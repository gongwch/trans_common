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
 * Object���h���b�O���h���b�v�Ŏ󂯓n���ׂ�Panel
 */
public class TDnDTree extends TTree implements DragSourceListener, DragGestureListener, DropTargetListener {

	/** ���� */
	public static Color rectColor = null;

	/** ���X�i�[ */
	protected TDnDListener listener;

	/** �h���b�v�̖ڕW�m�[�h */
	public TreeNode dropTargetNode = null;

	/** �h���b�O���m�[�h */
	public TreeNode draggedNode = null;

	/**
	 * �R���X�g���N�^.
	 */
	public TDnDTree() {
		this(null);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param root ���[�g�m�[�h
	 */
	public TDnDTree(TreeNode root) {
		super(root);
		setCellRenderer(new DnDTreeCellRenderer());

		// �h���b�O���h���b�v���������邽�߂̃��\�b�h���ĂԁB
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.CONTIGUOUS_TREE_SELECTION);
		new DragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE, this);
		new DropTarget(this, this);

		// �h���b�O���h���b�v�̐F�ݒ�
		initDragAndDropColor();
	}

	/**
	 * �h���b�O���h���b�v�̐F������
	 */
	public void initDragAndDropColor() {

		// RECT�F�ݒ�
		rectColor = TUIManager.getTextDefaultForeground();
	}

	/**
	 * �h���b�O���h���b�v�̐F�ݒ�
	 * 
	 * @param color
	 */
	public void setDragAndDropColor(Color color) {

		// RECT�F�ݒ�
		rectColor = color;
	}

	/**
	 * �h���b�O���h���b�v�����̃Z�b�g
	 * 
	 * @param listener ���X�i�[
	 */
	public void setDnDListener(TDnDListener listener) {
		this.listener = listener;
	}

	/**
	 * �h���b�O����.<br>
	 * Object���󂯓n��.
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
				// �h���b�O����^�[�Q�b�g�����݂��Ȃ��ꍇ
				if (data == null) {
					return;
				}
				dge.startDrag(DragSource.DefaultCopyDrop, trans);
			}

		} catch (Exception e) {
			// �����Ȃ�
			// TODO:DEBUG, e.printStackTrace();
		}
	}

	/**
	 * �h���b�v����.<br>
	 * Object���󂯎��.
	 * 
	 * @see java.awt.dnd.DropTargetListener#drop(java.awt.dnd.DropTargetDropEvent)
	 */
	public void drop(DropTargetDropEvent dtde) {

		boolean gotData = false;

		try {
			// �]�����`�F�b�N
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
			// �����Ȃ�

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
		// Override�p
	}

	/**
	 * @see java.awt.dnd.DragSourceListener#dropActionChanged(java.awt.dnd.DragSourceDragEvent)
	 */
	public void dropActionChanged(DragSourceDragEvent dsde) {
		// Override�p
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragEnter(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragEnter(DropTargetDragEvent dtde) {
		// Override�p
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragExit(java.awt.dnd.DropTargetEvent)
	 */
	public void dragExit(DropTargetEvent dte) {
		// Override�p
	}

	/**
	 * @see java.awt.dnd.DropTargetListener#dragOver(java.awt.dnd.DropTargetDragEvent)
	 */
	public void dragOver(DropTargetDragEvent dtde) {

		try {
			// dropTargetNode �́A�`��p(Rectangle2D�ALine)��flag
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
		// Override�p
	}

	/**
	 * �W�J���܂�
	 * 
	 * @param parent TreePath
	 * @param expand �W�J:true
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
	 * �e�o
	 * 
	 * @param dtde
	 */
	protected void rejectDrag(DropTargetDragEvent dtde) {
		dtde.rejectDrag();
		dropTargetNode = null; // dropTargetNode(flag)��null�ɂ���
		repaint(); // Rectangle2D�ALine����������JTree���ĕ`��
	}

	/**
	 * �Z��Renderer
	 */
	protected class DnDTreeCellRenderer extends DefaultTreeCellRenderer {

		/** ���̃p�b�h */
		protected static final int BOTTOM_PAD = 30;

		/** �ڕW�m�[�h���ǂ��� */
		protected boolean isTargetNode;

		/** �m�[�h���ǂ����Afalse:�t�H���_ */
		protected boolean isTargetNodeLeaf;

		/** �m�[�h��RECT */
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

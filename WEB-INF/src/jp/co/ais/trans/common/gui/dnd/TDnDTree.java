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
 * TTree�̋@�\�g���N���X�B<BR>
 * �h���b�O���h���b�v������s�Ȃ����߂̃��\�b�h��ǉ�����B
 * 
 * @author Yanwei
 */
public class TDnDTree extends TTree implements DropTargetListener, DragGestureListener, DragSourceListener {

	/** tree -> spread :false tree -> tree:true */
	public boolean treeTotree = false;

	/**
	 * �R���X�g���N�^ �h���b�O���h���b�v�p�̐ݒ���s�Ȃ��B
	 * 
	 * @param dndData TT��ee�̊K�w�f�[�^�󂯓n���Ɏg�p����N���X
	 */
	public TDnDTree(DnDData dndData) {
		// �@�p�����̃R���X�g���N�^���ĂԁB
		super();

		// �A�h���b�O���h���b�v���������邽�߂̃��\�b�h���ĂԁB
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
			this);
		new DropTarget(this, this);

		// �B���\�b�h����.���[�g�f�[�^���K�w�f�[�^�̃��[�g�m�[�h�ɐݒ肷��B
		THierarchyTreeNode baseNode = new THierarchyTreeNode(dndData);
		DefaultTreeModel model = new DefaultTreeModel(baseNode);
		super.setModel(model);

		TreePath visiblePath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(baseNode));
		this.setSelectionPath(visiblePath);

		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * �R���X�g���N�^.
	 */
	public TDnDTree() {
		super();
		DefaultTreeModel model = new DefaultTreeModel(null);
		super.setModel(model);
		// �h���b�O���h���b�v���������邽�߂̃��\�b�h���ĂԁB
		DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY_OR_MOVE,
			this);
		new DropTarget(this, this);

		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * �h���b�O�J�n���ɌĂ΂�郁�\�b�h �h���b�O�����f�[�^��]������B
	 */
	public void dragGestureRecognized(DragGestureEvent e) {
		InputEvent inputEvent = e.getTriggerEvent();
		try {
			// �Adrag�ʒu��THierarchyTreeNode�����݂��Ȃ��ꍇ,�@.THierarchyTreeNode�����[�g�m�[�h�̏ꍇ,�������I��
			Point pt = e.getDragOrigin();
			TreePath checkPathCheck = getPathForLocation(pt.x, pt.y);
			if (checkPathCheck == null || checkPathCheck.getParentPath() == null) {
				return;
			}

			// �@drag�ʒu�ɂ���THierarchyTreeNode���擾����B
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
					// �B �@.THierarchyTreeNode��������TTransferable�N���X���C���X�^���X������
					TTransferable transfer = new TTransferable(listTreeNode);

					// �C�h���b�O���J�n����B
					e.startDrag(DragSource.DefaultCopyDrop, transfer, this);
				}
			}

			// init
			treeTotree = false;
		} catch (InvalidDnDOperationException ioe) {
			// �����Ȃ�
		}
	}

	/**
	 * �h���b�v���ɌĂ΂�郁�\�b�h �]���f�[�^��ݒ肷��B
	 */
	public void drop(DropTargetDropEvent e) {
		// �]�����`�F�b�N(�]���f�[�^��TTransferable.nodeFlavor�`���łȂ��ꍇ)
		if (e.isDataFlavorSupported(TTransferable.nodeFavor)) {
			// �@�]���f�[�^�FList<THierarchyTreeNode>���擾����B
			List<THierarchyTreeNode> list = TTransferable.getTHerarchyTreeNodeList(e.getTransferable());

			// �A �]���f�[�^�����݂��Ȃ��ꍇ�͏����I��
			if (list == null || list.isEmpty()) {
				e.rejectDrop();
				return;
			}

			// �Bdrop�ʒu�ɂ���THierarchyTreeNode���擾����B�h���b�v�ʒu�Ƀm�[�h�����݂��Ȃ��ꍇ�A�������I������B
			DefaultTreeModel model = (DefaultTreeModel) getModel();
			Point p = e.getLocation();
			TreePath targetPath = getPathForLocation(p.x, p.y);
			if (targetPath == null) {
				e.dropComplete(false);
				return;
			}

			// �ڕW�m�[�h
			THierarchyTreeNode targetNode = (THierarchyTreeNode) targetPath.getLastPathComponent();
			TreePath dropPath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(targetNode));// �ڕW�p�X

			// �����̐e�m�[�h
			THierarchyTreeNode parentNode = (THierarchyTreeNode) list.get(0).getParent();

			// tree -> tree
			if (parentNode != null) {
				// �C ���R���|�[�l���g���̃h���b�O���h���b�v���Ȃ����A �ȉ��̍��ڂ��`�F�b�N����B
				treeTotree = true;
				// �e�m�[�h���q�m�[�h�Ƀh���b�v���悤�Ƃ��Ă���ꍇ�A�������I��
				TreePath dragPath = new TreePath(((DefaultTreeModel) this.getModel()).getPathToRoot(list.get(0)));// ���p�X

				String strDragPath = dragPath.toString();// ���p�X
				String strDropPath = dropPath.toString();// �ڕW�p�X
				String subDragPath = strDragPath.substring(0, strDragPath.length() - 1);

				if (strDropPath.startsWith(subDragPath)) {
					e.dropComplete(false);
					return;
				}
				// �����Ɏ������h���b�v���悤�Ƃ��Ă���ꍇ�A�������I��
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

			// �D �B.THierarchyTreeNode�ɇ@.�]���f�[�^���q�m�[�h�Ƃ��Ēǉ�����B
			if (parentNode != null) {
				// tree -> tree
				// �]�������m�[�h��Tree����폜
				model.removeNodeFromParent(list.get(0));
				model.insertNodeInto(list.get(0), targetNode, targetNode.getChildCount());
			} else {
				// spread -> tree
				for (THierarchyTreeNode treeNode : list) {
					model.insertNodeInto(treeNode, targetNode, targetNode.getChildCount());
				}
			}

			// �X�V���܂�
			((DefaultTreeModel) this.getModel()).reload(list.get(0));

			// �W�J���܂�
			expandAll(dropPath, true);

			// �I��
			this.setSelectionPath(dropPath);

			// �E �h���b�v��������������B
			e.dropComplete(true);

		} else {
			e.rejectDrop();
		}
	}

	/**
	 * �h���b�v�I�����ɌĂ΂�郁�\�b�h �h���b�O���h���b�v����������B
	 */
	public void dragDropEnd(DragSourceDropEvent e) {

		// �@�h���b�v������Ɋ������������`�F�b�N����B
		if (!e.getDropSuccess()) {
			return;
		}

		// �A�]���f�[�^�FList<THierarchyTreeNode>���擾����B
		List<THierarchyTreeNode> list = TTransferable.getTHerarchyTreeNodeList(e.getDragSourceContext()
			.getTransferable());

		// �A.�]���f�[�^�����݂��Ȃ��ꍇ�͏����I��
		if (list == null || list.isEmpty()) {
			return;
		}

		// tree -> spread
		if (treeTotree == false) {
			// �]�������m�[�h��Tree����폜
			DefaultTreeModel model = (DefaultTreeModel) getModel();
			model.removeNodeFromParent(list.get(0));

		} else {
			// tree -> tree
			return;
		}
		// �X�V���܂�
		((DefaultTreeModel) this.getModel()).reload();

		TreePath rootPath = new TreePath(((DefaultTreeModel) this.getModel()).getRoot());
		this.setSelectionPath(rootPath);

		// �W�J���܂�
		expandAll(rootPath, true);
		// �I��
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
		// ���g�p
	}

	public void dropActionChanged(DragSourceDragEvent e) {
		// ���g�p
	}

	public void dragEnter(DropTargetDragEvent dtde) {
		// ���g�p

	}

	public void dragExit(DropTargetEvent dte) {
		// ���g�p

	}

	public void dragOver(DropTargetDragEvent dtde) {
		// ���g�p

	}

	public void dropActionChanged(DropTargetDragEvent dtde) {
		// ���g�p

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
}
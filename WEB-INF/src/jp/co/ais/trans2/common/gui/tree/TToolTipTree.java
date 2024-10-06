package jp.co.ais.trans2.common.gui.tree;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �c�[���`�b�v�\���Ńc���[
 */
public class TToolTipTree extends TTree {

	/**
	 * �R���X�g���N�^�[
	 */
	public TToolTipTree() {
		super();

		// ������
		init();
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param node
	 */
	public TToolTipTree(TreeNode node) {
		super(node);

		// ������
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		// ���W�X�g
		ToolTipManager.sharedInstance().registerComponent(this);

		// �����I��s��
		this.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
	}

	/**
	 * @see javax.swing.JTree#getToolTipText(java.awt.event.MouseEvent)
	 */
	@Override
	public String getToolTipText(MouseEvent e) {

		TreePath path = getPathForLocation(e.getX(), e.getY());
		if (path == null || path.getLastPathComponent() == null
			|| !(path.getLastPathComponent() instanceof TMenuTreeNode)) {
			return null;
		}

		TMenuTreeNode node = (TMenuTreeNode) path.getLastPathComponent();
		return node.getToolTipText();
	}

}

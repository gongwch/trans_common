package jp.co.ais.trans.common.gui;

import java.awt.event.*;

import javax.swing.*;
import javax.swing.tree.*;

import jp.co.ais.trans2.common.config.*;

/**
 * JTree�ɁA�^�u���C���^�[�t�F�C�X��ǉ�����TreeBox.
 */
public class TTree extends JTree implements TInterfaceTabControl {

	/** �V���A��UID */
	protected static final long serialVersionUID = 5130879826202293920L;

	/** �^�u */
	protected int tabControlNo = -1;

	/** �^�u�g�p�� */
	protected boolean isTabEnabled = true;

	/**
	 * Constructor.
	 */
	public TTree() {
		super();

		init();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param node TreeNode
	 */
	public TTree(TreeNode node) {
		super(node);

		init();
	}

	/**
	 * ����������
	 */
	protected void init() {

		// �t�@���N�V�����L�[�p���X�i
		this.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent evt) {
				handleKeyPressed(evt);
			}
		});
	}

	/************************************************
	 * �t�@���N�V�����L�[����.
	 * 
	 * @param evt �L�[�C�x���g
	 ***********************************************/
	protected void handleKeyPressed(KeyEvent evt) {

		TGuiUtil.transferFocusByEnterKey(this, evt);
		TGuiUtil.dispatchPanelBusinessFunctionKey(this, evt);
	}

	/**
	 * @see javax.swing.JTree#getCellRenderer()
	 */
	@Override
	public TreeCellRenderer getCellRenderer() {
		if (ClientConfig.isFlagOn("trans.tree.view.icons.use.circle")
			|| ClientConfig.isFlagOn("trans.tree.view.icons.use.rect")) {
			return new TTreeCellRenderer();
		} else {
			return super.getCellRenderer();
		}

	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#getTabControlNo()
	 */
	public int getTabControlNo() {
		return this.tabControlNo;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabControlNo(int)
	 */
	public void setTabControlNo(int no) {
		this.tabControlNo = no;
	}

	/**
	 * 
	 */
	public boolean isTabEnabled() {
		return this.isTabEnabled;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.TInterfaceTabControl#setTabEnabled(boolean)
	 */
	public void setTabEnabled(boolean bool) {
		this.isTabEnabled = bool;
	}

	/**
	 * �m�[�h��S�ēW�J����
	 */
	public void expandAll() {

		int row = 0;
		while (row < getRowCount()) {
			expandRow(row);
			row++;
		}
	}

	/**
	 * �m�[�h�ǉ����̕ύX��UI�ɔ��f����
	 */
	public void reload() {
		DefaultTreeModel model = (DefaultTreeModel) getModel();
		model.reload();
	}

	/**
	 * �w��͈͂̃p�X���擾����
	 * 
	 * @param from �J�n
	 * @param to �I��
	 * @return �w��͈͂̃p�X
	 */
	@Override
	public TreePath[] getPathBetweenRows(int from, int to) {
		return super.getPathBetweenRows(from, to);
	}

	/**
	 * �S�Ẵp�X���擾����
	 * 
	 * @return �w��͈͂̃p�X
	 */
	public TreePath[] getAllPaths() {

		int from = 0;
		int to = getRowCount();

		if (to == 0) {
			return null;
		}

		return getPathBetweenRows(from, to);
	}

}

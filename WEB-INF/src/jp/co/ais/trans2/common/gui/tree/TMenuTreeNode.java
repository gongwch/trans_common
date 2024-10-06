package jp.co.ais.trans2.common.gui.tree;

import javax.swing.tree.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.program.*;

/**
 * 
 */
public class TMenuTreeNode extends DefaultMutableTreeNode {

	/** �c���[���� */
	protected TTreeItem item = null;

	/** �v���O������� */
	protected SystemizedProgram prgGroup = null;

	/**
	 * �R���X�g���N�^�[
	 */
	public TMenuTreeNode() {
		super();
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param item �c���[����
	 * @param flag �q�c���[�ǉ��ł��邩�ǂ���
	 */
	public TMenuTreeNode(TTreeItem item, boolean flag) {
		super(item, flag);

		this.item = item;
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param item �c���[����
	 */
	public TMenuTreeNode(TTreeItem item) {
		super(item);

		this.item = item;
	}

	/**
	 * �c�[���`�b�v�̎擾
	 * 
	 * @return �c�[���`�b�v
	 */
	public String getToolTipText() {

		if (this.item == null || !(item.getObj() instanceof MenuDisp)) {
			return null;
		}

		MenuDisp menu = (MenuDisp) item.getObj();
		if (menu == null) {
			return null;
		}

		Program prg = menu.getProgram();
		if (prg == null || Util.isNullOrEmpty(prg.getComment())) {
			return menu.getViewName();
		}

		return prg.getComment();
	}

	/**
	 * �c���[���ڂ̎擾
	 * 
	 * @return item �c���[����
	 */
	public TTreeItem getItem() {
		return item;
	}

	/**
	 * �c���[���ڂ̐ݒ�
	 * 
	 * @param item �c���[����
	 */
	public void setItem(TTreeItem item) {
		this.item = item;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return prgGroup �v���O�������
	 */
	public SystemizedProgram getPrgGroup() {
		return prgGroup;
	}

	/**
	 * �v���O�������̐ݒ�
	 * 
	 * @param prgGroup �v���O�������
	 */
	public void setPrgGroup(SystemizedProgram prgGroup) {
		this.prgGroup = prgGroup;
	}

}

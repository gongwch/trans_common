package jp.co.ais.trans2.model.department;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNode�N���X���p�����č쐬�B<br>
 * Drag��Drop���ADepartmentTreeNode��]�����邽�߂Ɏg�p����B
 * 
 * @author Yanwei
 */
public class DepartmentTreeNode extends DefaultMutableTreeNode {

	/** �m�[�h��� */
	protected DepartmentDisp depDisp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param depDisp �m�[�h���
	 */
	public DepartmentTreeNode(DepartmentDisp depDisp) {

		// �@�p�����̃R���X�g���N�^���ĂԁB
		super(getName(depDisp));

		// �A������DnDData���v���p�e�B�ɃZ�b�g����B
		this.depDisp = depDisp;
	}

	/**
	 * ����K�w���̎擾
	 * 
	 * @param depDisp
	 * @return ����K�w��
	 */
	protected static String getName(DepartmentDisp depDisp) {

		return depDisp.getViewName();

	}

	/**
	 * �m�[�h���
	 * 
	 * @return �m�[�h���
	 */
	public DepartmentDisp getDepDisp() {
		return depDisp;
	}

	/**
	 * �m�[�h���
	 * 
	 * @param depDisp �m�[�h���ݒ肷��
	 */
	public void setDepDisp(DepartmentDisp depDisp) {
		this.depDisp = depDisp;
	}

}
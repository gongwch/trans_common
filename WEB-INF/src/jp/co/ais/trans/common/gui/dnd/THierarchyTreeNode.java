package jp.co.ais.trans.common.gui.dnd;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNode�N���X���p�����č쐬�B<br>
 * Drag��Drop���ATHierarchyTreeNode��]�����邽�߂Ɏg�p����B
 * 
 * @author Yanwei
 */
public class THierarchyTreeNode extends DefaultMutableTreeNode {

	/** �m�[�h��� */
	private DnDData dndData;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param dData �m�[�h���
	 */
	public THierarchyTreeNode(DnDData dData) {

		// �@�p�����̃R���X�g���N�^���ĂԁB
		super(dData.getViewName());

		// �A������DnDData���v���p�e�B�ɃZ�b�g����B
		this.dndData = dData;
	}

	/**
	 * �m�[�h���
	 * 
	 * @return �m�[�h���
	 */
	public DnDData getDndData() {
		return dndData;
	}

	/**
	 * �m�[�h���
	 * 
	 * @param dndData �m�[�h���ݒ肷��
	 */
	public void setDndData(DnDData dndData) {
		this.dndData = dndData;
	}

}
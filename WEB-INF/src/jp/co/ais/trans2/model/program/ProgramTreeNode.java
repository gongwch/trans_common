package jp.co.ais.trans2.model.program;

import javax.swing.tree.*;

/**
 * DefaultMutableTreeNode�N���X���p�����č쐬�B<br>
 * Drag��Drop���AProgramTreeNode��]�����邽�߂Ɏg�p����B
 * 
 * @author Yanwei
 */
public class ProgramTreeNode extends DefaultMutableTreeNode {

	/** �m�[�h��� */
	private MenuDisp menuDisp;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param menuDisp �m�[�h���
	 */
	public ProgramTreeNode(MenuDisp menuDisp) {

		// �@�p�����̃R���X�g���N�^���ĂԁB
		super(getName(menuDisp));

		// �A������DnDData���v���p�e�B�ɃZ�b�g����B
		this.menuDisp = menuDisp;
	}

	/**
	 * ���j���[�\�����̎擾
	 * 
	 * @param menuDisp
	 * @return ���j���[�\����
	 */
	protected static String getName(MenuDisp menuDisp) {

		// TODO:�v���O�����R�[�h�\���@�\�ۗ�
		// if (menuDisp.isMenu() || menuDisp.getProgram() == null) {
		return menuDisp.getViewName();
		// } else {
		// return menuDisp.getCode() + " " + menuDisp.getProgram().getNames();
		// }
	}

	/**
	 * �m�[�h���
	 * 
	 * @return �m�[�h���
	 */
	public MenuDisp getMenuDisp() {
		return menuDisp;
	}

	/**
	 * �m�[�h���
	 * 
	 * @param menuDisp �m�[�h���ݒ肷��
	 */
	public void setMenuDisp(MenuDisp menuDisp) {
		this.menuDisp = menuDisp;
	}

}
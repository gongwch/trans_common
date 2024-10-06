package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * �V�K/�C���I���t�B�[���h
 * 
 * @author AIS
 */
public class TCheckStatusSelectedField extends TTitlePanel {

	/** �V�K */
	public TCheckBox chkNew;

	/** �C�� */
	public TCheckBox chkMod;

	/** ���� */
	public static final int height = 20;

	/**
	 * �R���X�g���N�^.
	 */
	public TCheckStatusSelectedField() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

	}

	/**
	 * �R���|�[�l���g
	 */
	protected void initComponents() {
		chkNew = new TCheckBox();
		chkMod = new TCheckBox();
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setSize(100, 75);

		// �I��
		setLangMessageID("C00324");

		chkNew.setSelected(true);
		chkNew.setSize(70, height);
		// �V�K
		chkNew.setLangMessageID("C00303");
		chkNew.setLocation(15, 5);
		add(chkNew);

		chkMod.setSelected(true);
		chkMod.setSize(70, height);
		// �C��
		chkMod.setLangMessageID("C01760");
		chkMod.setLocation(15, 30);
		add(chkMod);

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		chkNew.setTabControlNo(tabControlNo);
		chkMod.setTabControlNo(tabControlNo);
	}

	/**
	 * @return chkNew
	 */
	public TCheckBox getChkNew() {
		return chkNew;
	}

	/**
	 * @return chkMod
	 */
	public TCheckBox getChkMod() {
		return chkMod;
	}

	/**
	 * �ҏW��Ԃ�ݒ肷��
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		chkNew.setEnabled(isEditable);
		chkMod.setEnabled(isEditable);
	}

	/**
	 * �N���A
	 */
	public void clear() {
		chkNew.setSelected(false);
		chkMod.setSelected(false);
	}

	/**
	 * �����ꂩ���I������Ă��邩��Ԃ�
	 * 
	 * @return �����ꂩ���I������Ă��邩
	 */
	public boolean isSelected() {
		return chkNew.isSelected() || chkMod.isSelected();
	}

}

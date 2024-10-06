package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * �͈͌����t�B�[���h
 * 
 * @author AIS
 */
public abstract class TReferenceRange extends TPanel {

	/**
	 * 
	 *
	 */
	public TReferenceRange() {
		initComponents();
		allocateComponents();
		init();
	}

	/**
	 * ������
	 */
	protected void init() {
		// for override
	}

	/**
	 * �J�n�t�B�[���h��getter
	 * 
	 * @return �J�n�t�B�[���h
	 */
	public abstract TReference getFieldFrom();

	/**
	 * �I���t�B�[���h��getter
	 * 
	 * @return �I���t�B�[���h
	 */
	public abstract TReference getFieldTo();

	/**
	 * ������
	 */
	public abstract void initComponents();

	/**
	 * �R���|�[�l���g�̔z�u���s���B
	 */
	public void allocateComponents() {

		setLayout(null);
		setOpaque(false);

		getFieldFrom().setLocation(0, 0);
		// �J�@�@�n
		getFieldFrom().btn.setLangMessageID("C01012");
		add(getFieldFrom());
		getFieldTo().setLocation(0, getFieldTo().getHeight());
		// �I�@�@��
		getFieldTo().btn.setLangMessageID("C01143");
		add(getFieldTo());

		reSize();

		getFieldFrom().setCheckExist(false);
		getFieldTo().setCheckExist(false);
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	public void reSize() {
		setSize(getFieldFrom().getWidth(), getFieldFrom().getHeight() + getFieldTo().getHeight());

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		getFieldFrom().setTabControlNo(tabControlNo);
		getFieldTo().setTabControlNo(tabControlNo);
	}

	/**
	 * ���͂��ꂽ�J�n�R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�J�n�R�[�h
	 */
	public String getCodeFrom() {
		return getFieldFrom().code.getText();
	}

	/**
	 * ���͂��ꂽ�I���R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�I���R�[�h
	 */
	public String getCodeTo() {
		return getFieldTo().code.getText();
	}

	/**
	 * �J�n�R�[�h�̃Z�b�g
	 * 
	 * @param txt �J�n�R�[�h
	 */
	public void setCodeFrom(String txt) {
		getFieldFrom().code.setText(txt);
	}

	/**
	 * �I���R�[�h�̃Z�b�g
	 * 
	 * @param txt �I���R�[�h
	 */
	public void setCodeTo(String txt) {
		getFieldTo().code.setText(txt);
	}

	/**
	 * �J�n�R���|��entity������
	 */
	public void refleshEntityFrom() {
		getFieldFrom().refleshAndShowEntity();
	}

	/**
	 * �I���R���|��entity������
	 */
	public void refleshEntityTo() {
		getFieldTo().refleshAndShowEntity();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		getFieldFrom().clear();
		getFieldTo().clear();
	}

	/**
	 * Editable�̐ݒ�
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getFieldFrom().setEditable(editable);
		getFieldTo().setEditable(editable);
	}

	/**
	 * �召�`�F�b�N
	 * 
	 * @return true(��薳��) / false(�G���[����)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getFieldFrom().getCode(), getFieldTo().getCode()));
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩�ݒ肵�܂�
	 * 
	 * @param checkExist true:�`�F�b�N����
	 */
	public void setCheckExist(boolean checkExist) {
		getFieldFrom().setCheckExist(checkExist);
		getFieldTo().setCheckExist(checkExist);
	}
}

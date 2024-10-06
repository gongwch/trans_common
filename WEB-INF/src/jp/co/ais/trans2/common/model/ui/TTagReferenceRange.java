package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * �tⳃ��t�@�����X�����t�B�[���h
 * 
 * @author AIS
 */
public class TTagReferenceRange extends TPanel {

	/** �t�1�t�B�[���h */
	public TTagReference ctrlTagReference1;

	/** �t�2�t�B�[���h */
	public TTagReference ctrlTagReference2;

	/**
	 *
	 *
	 */
	public TTagReferenceRange() {
		initComponents();
		allocateComponents();
	}

	/**
	 * ������
	 */
	public void initComponents() {
		ctrlTagReference1 = new TTagReference();
		ctrlTagReference2 = new TTagReference();
	}

	/**
	 * �R���|�[�l���g�̔z�u���s���B
	 */
	public void allocateComponents() {

		setLayout(null);
		setOpaque(false);

		getField1().setLocation(0, 0);
		// �t�1���t�@�����X
		getField1().btn.setLangMessageID("CM0084");
		add(getField1());
		getField2().setLocation(0, getField2().getHeight());
		// �t�2���t�@�����X
		getField2().btn.setLangMessageID("CM0085");
		add(getField2());

		reSize();

		getField1().setCheckExist(false);
		getField2().setCheckExist(false);
	}

	/**
	 * �T�C�Y�̍Ĕ��f
	 */
	public void reSize() {
		setSize(getField1().getWidth() + 50, getField1().getHeight() + getField2().getHeight());

	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo
	 */
	public void setTabControlNo(int tabControlNo) {
		getField1().setTabControlNo(tabControlNo);
		getField2().setTabControlNo(tabControlNo);
	}

	/**
	 * ���͂��ꂽ�t�1�R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�t�1�R�[�h
	 */
	public String getCode1() {
		return getField1().code.getText();
	}

	/**
	 * ���͂��ꂽ�t�2�R�[�h��Ԃ�
	 * 
	 * @return ���͂��ꂽ�t�2�R�[�h
	 */
	public String getCode2() {
		return getField2().code.getText();
	}

	/**
	 * �t�1�R�[�h�̃Z�b�g
	 * 
	 * @param txt �t�1�R�[�h
	 */
	public void setCode1(String txt) {
		getField1().code.setText(txt);
	}

	/**
	 * �t�2�R�[�h�̃Z�b�g
	 * 
	 * @param txt �t�2�R�[�h
	 */
	public void setCode2(String txt) {
		getField2().code.setText(txt);
	}

	/**
	 * �t�1�R���|��entity������
	 */
	public void refleshEntity1() {
		getField1().refleshAndShowEntity();
	}

	/**
	 * �t�2�R���|��entity������
	 */
	public void refleshEntity2() {
		getField2().refleshAndShowEntity();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		getField1().clear();
		getField2().clear();
	}

	/**
	 * Editable�̐ݒ�
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getField1().setEditable(editable);
		getField2().setEditable(editable);
	}

	/**
	 * �R�[�h�̑��݃`�F�b�N�����邩�ݒ肵�܂�
	 * 
	 * @param checkExist true:�`�F�b�N����
	 */
	public void setCheckExist(boolean checkExist) {
		getField1().setCheckExist(checkExist);
		getField2().setCheckExist(checkExist);
	}

	/**
	 * @return ctrlTagReference1
	 */
	public TTagReference getField1() {
		return ctrlTagReference1;
	}

	/**
	 * @return ctrlTagReference2
	 */
	public TTagReference getField2() {
		return ctrlTagReference2;
	}

	/**
	 * �t�1�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �t�1�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Tag getEntity1() {
		return ctrlTagReference1.getEntity();
	}

	/**
	 * �t�2�t�B�[���h�őI�����ꂽ�Ȗ�Entity��Ԃ�
	 * 
	 * @return �t�2�t�B�[���h�őI�����ꂽ�Ȗ�Entity
	 */
	public Tag getEntity2() {
		return ctrlTagReference2.getEntity();
	}

}

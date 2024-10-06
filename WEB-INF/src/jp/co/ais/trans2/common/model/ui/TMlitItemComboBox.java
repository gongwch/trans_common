package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * �A�����ё�i�ڃR���{�{�b�N�X
 */
public class TMlitItemComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TMlitItemComboBoxController controller;

	/** �A�����уT�u�i�ڃR���{�{�b�N�X */
	protected TMlitSubItemComboBox subCombo;

	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitItemComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitItemComboBox(boolean hasBlank) {

		this.hasBlank = hasBlank;

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		// �R���g���[����z�u����
		controller = createController();
	}

	/**
	 * @return �R���g���[��
	 */
	protected TMlitItemComboBoxController createController() {
		return new TMlitItemComboBoxController(this);
	}

	/**
	 * 
	 */
	protected void initComponents() {
		//
	}

	/**
	 * 
	 */
	protected void allocateComponents() {

		label.setLangMessageID("COP1232"); // MLIT ITEM
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return �R���g���[��
	 */
	public TMlitItemComboBoxController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�i�ڃR�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�i�ڃR�[�h
	 */
	public String getSelectedCode() {
		return controller.getSelectedCode();
	}

	/**
	 * �i�ڃR�[�h��ݒ肷��
	 * 
	 * @param code �i�ڃR�[�h
	 */
	public void setSelectedCode(String code) {
		controller.setSelectedCode(code);
	}

	/**
	 * �I�����ꂽ�i�ږ��̂�Ԃ�
	 * 
	 * @return �i�ږ���
	 */
	public String getSelectedName() {
		return controller.getSelectedName();
	}

	/**
	 * �T�u�i�ڃR���{�{�b�N�X��ݒ�
	 * 
	 * @param combo
	 */
	public void setSubComboBox(TMlitSubItemComboBox combo) {
		this.subCombo = combo;
	}
}

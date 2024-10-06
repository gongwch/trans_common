package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * �A�����ђn��R���{�{�b�N�X
 */
public class TMlitRegionComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TMlitRegionComboBoxController controller;

	/** �A�����э��R���{�{�b�N�X */
	protected TMlitCountryComboBox couCombo;

	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitRegionComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitRegionComboBox(boolean hasBlank) {

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
	protected TMlitRegionComboBoxController createController() {
		return new TMlitRegionComboBoxController(this);
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

		label.setLangMessageID("CBL480"); // MLIT Region
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return �R���g���[��
	 */
	public TMlitRegionComboBoxController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�n��R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�n��R�[�h
	 */
	public String getSelectedRegionCode() {
		return controller.getSelectedRegionCode();
	}

	/**
	 * �n��R�[�h��ݒ肷��
	 * 
	 * @param code �n��R�[�h
	 */
	public void setSelectedRegionCode(String code) {
		controller.setSelectedRegionCode(code);
	}

	/**
	 * ���R���{�{�b�N�X��ݒ�
	 * 
	 * @param combo
	 */
	public void setCountryComboBox(TMlitCountryComboBox combo) {
		this.couCombo = combo;
	}
}

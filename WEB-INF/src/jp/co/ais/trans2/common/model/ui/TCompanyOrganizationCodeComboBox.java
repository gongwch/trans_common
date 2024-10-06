package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * ��Бg�D�R�[�h�݂̂̃R���{�{�b�N�X
 */
public class TCompanyOrganizationCodeComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TCompanyOrganizationCodeComboBoxController controller;

	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TCompanyOrganizationCodeComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TCompanyOrganizationCodeComboBox(boolean hasBlank) {

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
	protected TCompanyOrganizationCodeComboBoxController createController() {
		return new TCompanyOrganizationCodeComboBoxController(this);
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

		label.setLangMessageID("C00335");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return �R���g���[��
	 */
	public TCompanyOrganizationCodeComboBoxController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�g�D�R�[�h
	 */
	public String getSelectedOrganizationCode() {
		return controller.getSelectedOrganizationCode();
	}

	/**
	 * �g�D�R�[�h��ݒ肷��
	 * 
	 * @param code �g�D�R�[�h
	 */
	public void setSelectedOrganizationCode(String code) {
		controller.setSelectedOrganizationCode(code);
	}

}
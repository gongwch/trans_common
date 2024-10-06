package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * ��Бg�D�R���{�{�b�N�X
 */
public class TCompanyOrganizationComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TCompanyOrganizationComboBoxController controller;

	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TCompanyOrganizationComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TCompanyOrganizationComboBox(boolean hasBlank) {

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
	protected TCompanyOrganizationComboBoxController createController() {
		return new TCompanyOrganizationComboBoxController(this);
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
	public TCompanyOrganizationComboBoxController getController() {
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

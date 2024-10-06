package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����ђn��R���{�{�b�N�X
 */
public class TMlitCountryComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TMlitCountryComboBoxController controller;
	
	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitCountryComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitCountryComboBox(boolean hasBlank) {

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
	protected TMlitCountryComboBoxController createController() {
		return new TMlitCountryComboBoxController(this);
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

		label.setLangMessageID("CBL481"); // MLIT Country
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return �R���g���[��
	 */
	public TMlitCountryComboBoxController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public YJRegionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �R���{�{�b�N�X�����݂̏󋵂ɍ��킹���t���b�V��
	 */
	public void reflesh() {
		controller.refleshCombo();
	}
	
	/**
	 * �I�����ꂽ���R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ���R�[�h
	 */
	public String getSelectedCountryCode() {
		return controller.getSelectedCountryCode();
	}

	/**
	 * ���R�[�h��ݒ肷��
	 * 
	 * @param code ���R�[�h
	 */
	public void setSelectedCountryCode(String code) {
		controller.setSelectedCountryCode(code);
	}
}

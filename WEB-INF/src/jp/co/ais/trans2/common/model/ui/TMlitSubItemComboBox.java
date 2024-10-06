package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * �A�����уT�u�i�ڃR���{�{�b�N�X
 */
public class TMlitSubItemComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TMlitSubItemComboBoxController controller;

	/** true:�󔒑I���������� */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitSubItemComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitSubItemComboBox(boolean hasBlank) {

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
	protected TMlitSubItemComboBoxController createController() {
		return new TMlitSubItemComboBoxController(this);
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

		label.setLangMessageID("COP1233"); // MLIT SUB ITEM
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return �R���g���[��
	 */
	public TMlitSubItemComboBoxController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public YJItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �R���{�{�b�N�X�����݂̏󋵂ɍ��킹���t���b�V��
	 */
	public void reflesh() {
		controller.refleshCombo();
	}

	/**
	 * �I�����ꂽ�T�u�i�ڃR�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�T�u�i�ڃR�[�h
	 */
	public String getSelectedCode() {
		return controller.getSelectedCode();
	}

	/**
	 * �T�u�i�ڃR�[�h��ݒ肷��
	 * 
	 * @param code �T�u�i�ڃR�[�h
	 */
	public void setSelectedCode(String code) {
		controller.setSelectedCode(code);
	}

	/**
	 * �I�����ꂽ�T�u�i�ږ��̂�Ԃ�
	 * 
	 * @return �T�u�i�ږ���
	 */
	public String getSelectedName() {
		return controller.getSelectedName();
	}

}

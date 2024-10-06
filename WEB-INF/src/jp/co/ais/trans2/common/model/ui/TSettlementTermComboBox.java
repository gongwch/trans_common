package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.SettlementTerm;

/**
 * ���Z���ԑI���R���{�{�b�N�X
 */
public class TSettlementTermComboBox extends TLabelComboBox {

	/** �R���g���[�� */
	protected TSettlementTermComboBoxController controller;

	/**
	 * 
	 */
	public TSettlementTermComboBox() {

		// �R���|�[�l���g������������
		initComponents();

		// �R���|�[�l���g��z�u����
		allocateComponents();

		controller = new TSettlementTermComboBoxController(this);

	}

	protected void initComponents() {
		// 
	}

	protected void allocateComponents() {

		// ���Z����
		label.setLangMessageID("C11133");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return
	 */
	public TSettlementTermComboBoxController getController() {
		return controller;
	}

	/**
	 * �I�����ꂽ���Z���Ԃ�Ԃ�
	 * 
	 * @return �I�����ꂽ���Z����
	 */
	public SettlementTerm getSelectedSettlementTerm() {
		return controller.getSelectedSettlementTerm();
	}

	/**
	 * �w��̌��Z���Ԃ�I������
	 */
	public void setSelectedSettlementTerm(SettlementTerm term) {
		controller.setSelectedSettlementTerm(term);
	}

}

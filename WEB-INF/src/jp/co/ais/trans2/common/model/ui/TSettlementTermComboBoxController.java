package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.SettlementTerm;

/**
 * ���Z���ԑI���R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TSettlementTermComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TSettlementTermComboBox comboBox;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TSettlementTermComboBoxController(TSettlementTermComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * ������
	 */
	protected void init() {

		// �R���{�{�b�N�X����
		getList();

	}

	/**
	 * �R���{�{�b�N�X�̃��X�g��Ԃ�
	 * 
	 */
	protected void getList() {

		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.YEAR)),
				SettlementTerm.YEAR));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.HALF)),
				SettlementTerm.HALF));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.QUARTER)),
				SettlementTerm.QUARTER));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.MONTH)),
				SettlementTerm.MONTH));

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I�����ꂽ�g�D�R�[�h��Ԃ�
	 * 
	 * @return �I�����ꂽ�g�D�R�[�h
	 */
	public SettlementTerm getSelectedSettlementTerm() {
		return (SettlementTerm) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * �w��̌��Z���Ԃ�I������
	 * @param term 
	 */
	public void setSelectedSettlementTerm(SettlementTerm term) {
		comboBox.getComboBox().setSelectedItemValue(term);
	}

}

package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.CurrencyType;

/**
 * �\���ʉݑI���R���{�{�b�N�X�̃R���g���[��
 * 
 * @author AIS
 */
public class TBookCurrencyComboBoxController extends TController {

	/** �R���{�{�b�N�X */
	protected TBookCurrencyComboBox comboBox;

	/**
	 * @param comboBox �t�B�[���h
	 */
	public TBookCurrencyComboBoxController(TBookCurrencyComboBox comboBox) {

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
	 * �\���ʉݐ���
	 *
	 */
	protected void getList() {

		comboBox.getComboBox().addTextValueItem(
				CurrencyType.KEY, getWord(CurrencyType.getCurrencyTypeName(CurrencyType.KEY)));
		comboBox.getComboBox().addTextValueItem(
				CurrencyType.FUNCTIONAL, getWord(CurrencyType.getCurrencyTypeName(CurrencyType.FUNCTIONAL)));

	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �I������Ă���\���ʉ݂�Ԃ�
	 * @return �I������Ă���\���ʉ�
	 */
	public CurrencyType getCurrencyType() {
		return (CurrencyType)comboBox.getSelectedItemValue();
	}

}

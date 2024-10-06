package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * �䒠�ʉݑI���R���|�[�l���g�̃R���g���[��
 * 
 * @author AIS
 */
public class TBookCurrencySelectorController extends TController {

	/** �䒠�ʉݑI���R���|�[�l���g */
	protected TBookCurrencySelector field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �t�B�[���h
	 */
	public TBookCurrencySelectorController(TBookCurrencySelector field) {
		this.field = field;
		init();
	}

	/**
	 * ������
	 */
	public void init() {
		field.rdoKey.setSelected(true);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * �\���ʉ݂�Ԃ�
	 * 
	 * @return �\���ʉ�
	 */
	public CurrencyType getCurrencyType() {
		if (field.rdoKey.isSelected()) {
			return CurrencyType.KEY;
		} else if (field.rdoFunctional.isSelected()) {
			return CurrencyType.FUNCTIONAL;
		}
		return null;
	}

	/**
	 * �\���ʉ݂̐ݒ�
	 * 
	 * @param currencyType �\���ʉ�
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		if (CurrencyType.FUNCTIONAL == currencyType) {
			field.rdoFunctional.setSelected(true);
		} else {
			field.rdoKey.setSelected(true);
		}
	}

}

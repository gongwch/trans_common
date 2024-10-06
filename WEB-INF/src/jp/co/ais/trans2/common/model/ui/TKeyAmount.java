package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TNumericField;
import jp.co.ais.trans2.common.ui.TLoginInfo;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ��ʉ݋��z�t�B�[���h
 * @author AIS
 *
 */
public class TKeyAmount extends TNumericField {

	public TKeyAmount() {

		initComponents();

		allocateComponents();

	}

	public void initComponents() {
		Currency keyCurrency = TLoginInfo.getCompany().getAccountConfig().getKeyCurrency();
		setDecimalPoint(keyCurrency.getDecimalPoint());
		setMaxLength(13 + keyCurrency.getDecimalPoint(), keyCurrency.getDecimalPoint());
	}

	public void allocateComponents() {
		//
	}

}

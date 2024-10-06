package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * �ʉ݌����͈̓t�B�[���h
 */
public class TCurrencyRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1355561921215608682L;

	/**
	 * �R���X�g���N�^
	 */
	public TCurrencyRangeField() {
		super.panelMsgId = "C00371";

		super.init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TCurrencyField field = new TCurrencyField();

		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCurrencyRangeField.super.getBeginCode();
				((TCurrencyField) TCurrencyRangeField.super.getEndField()).setBeginCode(code);
			}
		});

		return field;
	}

	/**
	 * �I���t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �I���t�B�[���h
	 */
	protected TButtonField createEndField() {
		TCurrencyField field = new TCurrencyField();

		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCurrencyRangeField.super.getEndCode();
				((TCurrencyField) TCurrencyRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}

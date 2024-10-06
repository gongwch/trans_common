package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * �Ј������͈̓t�B�[���h
 */
public class TEmployeeRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1355561921215608682L;

	/**
	 * �R���X�g���N�^
	 */
	public TEmployeeRangeField() {
		super.panelMsgId = "C00246";

		super.init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TEmployeeField field = new TEmployeeField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TEmployeeRangeField.super.getBeginCode();
				((TEmployeeField) TEmployeeRangeField.super.getEndField()).setBeginCode(code);
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
		TEmployeeField field = new TEmployeeField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TEmployeeRangeField.super.getEndCode();
				((TEmployeeField) TEmployeeRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}

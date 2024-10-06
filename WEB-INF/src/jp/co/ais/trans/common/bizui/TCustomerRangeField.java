package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * �����͈͎w��t�B�[���h
 */
public class TCustomerRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -1448740112699566917L;

	/**
	 * �R���X�g���N�^
	 */
	public TCustomerRangeField() {
		super.panelMsgId = "C10338";

		super.init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TCustomerField field = new TCustomerField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCustomerRangeField.super.getBeginCode();
				((TCustomerField) TCustomerRangeField.super.getEndField()).setBeginCode(code);
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
		TCustomerField field = new TCustomerField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TCustomerRangeField.super.getEndCode();
				((TCustomerField) TCustomerRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}

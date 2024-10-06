package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * �Ȗڔ͈͎w��t�B�[���h
 */
public class TItemRangeField extends TRangeField {
	
	/** �Ȗڑ̌n�R�[�h */
	protected String itemSystem = "";

	/**
	 * �R���X�g���N�^
	 */
	public TItemRangeField() {
		super.panelMsgId = "C01009";

		super.init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TItemField field = new TItemField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TItemRangeField.super.getBeginCode();
				((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setBeginCode(code);
				((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setItemSystemCode(
					getItemSystem());
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
		TItemField field = new TItemField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TItemRangeField.super.getEndCode();
				((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setEndCode(code);
				((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setItemSystemCode(
					getItemSystem());
			}
		});

		return field;
	}

	/**
	 * �Ȗڑ̌n�R�[�h���擾����
	 * 
	 * @return itemSystem
	 */
	public String getItemSystem() {
		return itemSystem;
	}

	/**
	 * �Ȗڑ̌n�R�[�h��ݒ肷��
	 * 
	 * @param itemSystem �Ȗڑ̌n�R�[�h
	 */
	public void setItemSystem(String itemSystem) {
		this.itemSystem = itemSystem;
		((TItemField) TItemRangeField.super.getBeginField()).getInputParameter().setItemSystemCode(
			getItemSystem());
		((TItemField) TItemRangeField.super.getEndField()).getInputParameter().setItemSystemCode(
			getItemSystem());
	}
	
}

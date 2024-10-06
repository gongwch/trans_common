package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * �Ǘ�1�`6�͈͎w��t�B�[���h
 */
public class TManagementRangeField extends TRangeField {

	/** �Ǘ�1�`6 */
	protected int type;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �Ǘ��^�C�v�iTManagement�j
	 */
	public TManagementRangeField(int type) {

		this.type = type;
		super.panelMsgId = getWord("C00094") + this.type + getWord("C03186");

		init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TManagementField field = new TManagementField(type);

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TManagementRangeField.super.getBeginCode();
				((TManagementField) TManagementRangeField.super.getEndField()).setBeginCode(code);
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
		TManagementField field = new TManagementField(type);

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TManagementRangeField.super.getEndCode();
				((TManagementField) TManagementRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}
}

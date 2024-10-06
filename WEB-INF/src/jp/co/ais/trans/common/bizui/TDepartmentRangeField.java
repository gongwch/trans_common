package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.gui.*;

/**
 * ����͈͎w��t�B�[���h
 */
public class TDepartmentRangeField extends TRangeField {

	/** UID */
	private static final long serialVersionUID = -4842044557647905736L;

	/**
	 * �R���X�g���N�^
	 */
	public TDepartmentRangeField() {
		super.panelMsgId = getWord("C00467") + getWord("C03186");

		super.init();
	}

	/**
	 * �J�n�t�B�[���h�𐶐�����(Orverride�p)
	 * 
	 * @return �J�n�t�B�[���h
	 */
	protected TButtonField createBeginField() {
		TDepartmentField field = new TDepartmentField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TDepartmentRangeField.super.getBeginCode();
				((TDepartmentField) TDepartmentRangeField.super.getEndField()).setBeginCode(code);
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
		TDepartmentField field = new TDepartmentField();

		// �I���ȖڂɊJ�n�R�[�h��ݒ�
		field.addCallControl(new CallBackListener() {

			public void after() {
				String code = TDepartmentRangeField.super.getEndCode();
				((TDepartmentField) TDepartmentRangeField.super.getBeginField()).setEndCode(code);
			}
		});

		return field;
	}

	/**
	 * �W�v�敪�ݒ�
	 * 
	 * @param sumDepartment true:�W�v����̂�
	 */
	public void setSumDepartment(boolean sumDepartment) {
		((TDepartmentField) TDepartmentRangeField.super.getBeginField()).setSumDepartment(sumDepartment);
		((TDepartmentField) TDepartmentRangeField.super.getEndField()).setSumDepartment(sumDepartment);
	}
}

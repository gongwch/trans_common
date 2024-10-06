package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Item Monthly Basis Division
 */
public enum OPMonthlyBasisDivision implements TEnumRadio {

	/** 0:����� **/
	APPORTION(0),

	/** 1:����� **/
	COMPLETE(1),

	/** 2:������� **/
	ACCRUAL(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	OPMonthlyBasisDivision(int value) {
		this.value = value;
	}

	/**
	 * Enum��Ԃ�
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPMonthlyBasisDivision get(int value) {
		for (OPMonthlyBasisDivision em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}

	public String getName() {
		return getName(this);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param div
	 * @return ����
	 */
	public static String getName(OPMonthlyBasisDivision div) {

		if (div == null) {
			return "";
		}

		switch (div) {
			case COMPLETE:
				return "�����";// TODO ������Ή�
			case ACCRUAL:
				return "�������";
			case APPORTION:
				return "�����";
		}

		return "";
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param value
	 * @return ����
	 */
	public static String getName(int value) {

		OPMonthlyBasisDivision div = get(value);
		if (div == null) {
			return "";
		}

		return getName(div);
	}
}

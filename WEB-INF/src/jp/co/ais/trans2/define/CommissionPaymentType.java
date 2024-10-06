package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * �萔���x���敪
 */
public enum CommissionPaymentType {

	/** ���l */
	Receiver(1),

	/** �x���l */
	Payer(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CommissionPaymentType(int value) {
		this.value = value;
	}

	/**
	 * �^�C�v�擾
	 * 
	 * @param commissionPaymentType �l
	 * @return �^�C�v
	 */
	public static CommissionPaymentType get(int commissionPaymentType) {
		for (CommissionPaymentType em : values()) {
			if (em.value == commissionPaymentType) {
				return em;
			}
		}
		return null;
	}

	/**
	 * @return �l
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̎擾
	 * 
	 * @param commissionPaymentType �l
	 * @return ����
	 */
	public static String getName(int commissionPaymentType) {
		return getName(get(commissionPaymentType));
	}

	/**
	 * ���̎擾
	 * 
	 * @param commissionPaymentType �^�C�v
	 * @return ����
	 */
	public static String getName(CommissionPaymentType commissionPaymentType) {
		if (commissionPaymentType == null) {
			return "";
		}

		switch (commissionPaymentType) {
			case Receiver:
				return "C00021"; // ���l

			case Payer:
				return "C02319"; // �x���l

			default:
				return "";
		}
	}
}

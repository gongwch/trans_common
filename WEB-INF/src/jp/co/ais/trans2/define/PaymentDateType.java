package jp.co.ais.trans2.define;

/**
 * �x���敪
 * 
 * @author AIS
 */
public enum PaymentDateType {

	/** �����Ȃ� */
	NONE(""),

	/** 00:�Վ� */
	TEMPORARY("00"),

	/** 10:�莞 */
	REGULAR("10"),

	/** 20:���� */
	BOTH("20");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private PaymentDateType(String value) {
		this.value = value;
	}

	/**
	 * �x���敪���擾����
	 * 
	 * @return �x���敪
	 */
	public String getPaymentDateType() {
		return value;
	}

	/**
	 * �x���敪��Ԃ�
	 * 
	 * @param paymentDateType �x���敪
	 * @return �x���敪��
	 */
	public static PaymentDateType getPaymentDateType(String paymentDateType) {
		for (PaymentDateType em : values()) {
			if (em.value.equals(paymentDateType)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getPaymentDateTypeName(this);
	}

	/**
	 * �x���敪���̂�Ԃ�
	 * 
	 * @param paymentDateType �x���敪
	 * @return �x���敪����
	 */
	public static String getPaymentDateTypeName(PaymentDateType paymentDateType) {

		if (paymentDateType == null) {
			return null;
		}

		switch (paymentDateType) {
			case NONE:
				return "";
			case TEMPORARY:
				return "C00555"; // �Վ�

			case REGULAR:
				return "C00380"; // �莞

			default:
				return null;
		}
	}

	/**
	 * �x���敪���̂�Ԃ�
	 * 
	 * @param paymentDateType �x���敪
	 * @return �x���敪����
	 */
	public static String getPaymentDateTypeName(String paymentDateType) {
		return getPaymentDateTypeName(getPaymentDateType(paymentDateType));
	}

}

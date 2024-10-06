package jp.co.ais.trans2.model.item;

/**
 * AP�Ȗڐ���敪
 */
public enum APType {

	/** �ʏ� */
	NOMAL("00"),

	/** ���Ǘ��Ȗ� */
	DEBIT("01"),

	/** �]�ƈ��������Ȗ� */
	EMPLOYEE("02");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private APType(String value) {
		this.value = value;
	}

	/**
	 * AP�Ȗڐ���敪��Ԃ�
	 * 
	 * @param ap AP�Ȗڐ���敪
	 * @return �l
	 */
	public static APType get(String ap) {
		for (APType em : values()) {
			if (em.value.equals(ap)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * AP�Ȗڐ���敪��Ԃ�
	 * 
	 * @param ap AP�Ȗڐ���敪
	 * @return AP�Ȗڐ���敪
	 */
	public static String getName(APType ap) {

		if (ap == null) {
			return "";
		}

		switch (ap) {
			case NOMAL:
				return "C00372"; // �ʏ�

			case DEBIT:
				return "C02118"; // ���Ǘ��Ȗ�

			case EMPLOYEE:
				return "C02119"; // �]�ƈ������Ȗ�

			default:
				return "";
		}

	}
}
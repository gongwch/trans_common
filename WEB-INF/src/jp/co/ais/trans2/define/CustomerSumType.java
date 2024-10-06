package jp.co.ais.trans2.define;

/**
 * �����W�v�敪
 */
public enum CustomerSumType {

	/** ���� */
	INPUT(0),

	/** �W�v */
	SUM(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 *
	 * @param value �l
	 */
	private CustomerSumType(int value) {
		this.value = value;
	}

	/**
	 * �敪��Ԃ�
	 *
	 * @param value �l
	 * @return �敪
	 */
	public static CustomerSumType get(int value) {
		for (CustomerSumType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	/**
	 * �敪���̂�Ԃ�
	 *
	 * @param sumType �敪
	 * @return �敪����
	 */
	public static String getName(CustomerSumType sumType) {

		switch (sumType) {
			case INPUT:
				return "C01275";//����

			case SUM:
				return "C00255";//�W�v

			default:
				return null;
		}
	}
}

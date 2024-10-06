package jp.co.ais.trans2.define;


/**
 * ���v����
 */
public enum NonAccountingDivision {
	/** �Ȃ� */
	NONE(0),

	/** ���l */
	NUMBER(1),
	
	/** ���� */
	CHAR(2),

	/** ���t */
	YMD_DATE(3),

	/** ���� */
	YMDHM_DATE(4);

	/** �l */
	private int value;

	
	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private NonAccountingDivision(int value) {
		this.value = value; 
	}

	/**
	 * �l����^�擾
	 * 
	 * @param value �l
	 * @return �^
	 */
	public static NonAccountingDivision get(int value) {
		for (NonAccountingDivision div : values()) {
			if (div.value == value) {
				return div;
			}
		}
		return NONE;
	}

	/**
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * ���v���ڂ�Ԃ�
	 * 
	 * @param nonAccountDivision ���v����
	 * @return ���v����
	 */
	public static String getNonAccountName(NonAccountingDivision nonAccountDivision) {

		switch (nonAccountDivision) {
			case NONE:
				return "C00282";//�g�p���Ȃ�
			case NUMBER:
				return "C02160";//���l
			case CHAR:
				return "C02161";//����
			case YMD_DATE:
				return "C00446";//���t
			case YMDHM_DATE:
				return "C02906";//����
			default:
				return null;
		}
	}

}

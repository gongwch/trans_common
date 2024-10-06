package jp.co.ais.trans2.define;

/**
 * �������ԍ� �����ݒ荀��
 */
public enum InvoiceNoAdopt {
	/** �Ȃ� */
	NONE(0),
	
	/** �Œ蕶�� */
	FIXED_CHAR(1),
	
	/** �N�x(YY) */
	YY_DATE(2),
	
	/** �N�x(YYYY) */
	YYYY_DATE(3),
	
	/** ���x(MM) */
	MM_DATE(4),
	
	/** �N��(YYMM) */
	YYMM_DATE(5),
	
	/** �N��(YYYYMM) */
	YYYYMM_DATE(6),
	
	/** ���� */
	DEPARTMENT(7),
	
	/** ���[�U�[ */
	USER(8),
	
	/** ��ЃR�[�h */
	CODE(9);
	
	/** �l */
	private int value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 */
	private InvoiceNoAdopt(int value) {
		this.value = value;
	}
	
	/**
	 * �l����^�擾
	 * 
	 * @param value �l
	 * @return �^
	 */
	public static InvoiceNoAdopt get(int value) {
		for (InvoiceNoAdopt invoiceNoAdopt : values()) {
			if (invoiceNoAdopt.value == value) {
				return invoiceNoAdopt;
			}
		}
		return NONE;
	}
	
	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * �������ԍ� �̔ԍ��ڂ�Ԃ�
	 * 
	 * @param invoiceNoAdopt �������ԍ�����
	 * @return �̔ԍ���
	 */
	public static String getInvoiceNoAdoptName(InvoiceNoAdopt invoiceNoAdopt) {

		switch (invoiceNoAdopt) {
			case NONE:
				// �Ȃ�
				return "C00412";
			case FIXED_CHAR:
			// �Œ蕶��:
				return "C12192";
			case YY_DATE:
			// �N�x(YY):
				return "C02162";
			case YYYY_DATE:
				// �N�x(YYYY)
				return "C02163";
			case MM_DATE:
				// ���x(MM)
				return "C12193";
			case YYMM_DATE:
				// �N��(YYMM)
				return "C02164";
			case YYYYMM_DATE:
				// �N��(YYYYMM)
				return "C02165";
			case USER:
				// ���[�U�[
				return "C00528";
			case DEPARTMENT:
				// ����
				return "C00467";
			case CODE:
				// ��ЃR�[�h
				return "C00596";
			default:
				return null;
		}
	}
	
}

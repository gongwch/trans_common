package jp.co.ais.trans2.define;

/**
 * �����ݒ荀��
 */
public enum SlipNoAdopt {
	/** �Ȃ� */
	NONE(0),
	
	/** �N�x(YY) */
	YY_DATE(1),
	
	/** �N�x(YYYY) */
	YYYY_DATE(2),
	
	/** �N��(YYMM) */
	YYMM_DATE(3),
	
	/** �N��(YYYYMM) */
	YYYYMM_DATE(4),
	
	/** ���[�U�[ */
	USER(5),
	
	/** ���� */
	DEPARTMENT(6),
	
	/** �V�X�e���敪 */
	DIVISION(7),
	
	/** ��ЃR�[�h */
	CODE(8),
	
	/** �`�[��� */
	SLIPTYPE(9);
	
	/** �l */
	private int value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 */
	private SlipNoAdopt(int value) {
		this.value = value;
	}
	
	/**
	 * �l����^�擾
	 * 
	 * @param value �l
	 * @return �^
	 */
	public static SlipNoAdopt get(int value) {
		for (SlipNoAdopt slipNoAdopt : values()) {
			if (slipNoAdopt.value == value) {
				return slipNoAdopt;
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
	 * �`�[�̔ԍ��ڂ�Ԃ�
	 * 
	 * @param slipNoAdopt �`�[�̔ԍ���
	 * @return �̔ԍ���
	 */
	public static String getSlipNoAdoptName(SlipNoAdopt slipNoAdopt) {

		switch (slipNoAdopt) {
			case NONE:
				// �Ȃ�
				return "C00412";
			case YY_DATE:
			// �N�x(YY):
				return "C02162";
			case YYYY_DATE:
				// �N�x(YYYY)
				return "C02163";
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
			case DIVISION:
				// �V�X�e���敪
				return "C00980";
			case CODE:
				// ��ЃR�[�h
				return "C00596";
			case SLIPTYPE:
				// �`�[���
				return "C00917";
			default:
				return null;
		}
	}
	
}

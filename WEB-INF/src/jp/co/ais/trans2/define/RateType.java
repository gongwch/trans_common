package jp.co.ais.trans2.define;

/**
 * ���[�g�敪
 * 
 * @author AIS
 */
public enum RateType {
	
	/** �В背�[�g */
	COMPANY(0),
	
	/** ���Z�����[�g */
	SETTLEMENT(1),
	
	/** �В背�[�g�i�@�\�ʉ݁j */
	FNC_COMPANY(2),
	
	/** ���Z�����[�g�i�@�\�ʉ݁j */
	FNC_SETTLEMENT(3);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private RateType(int value) {
		this.value = value;
	}

	/**
	 * ���[�g�敪��Ԃ�
	 * 
	 * @param rateType ���[�g�敪
	 * @return �l
	 */
	public static RateType getRateType(int rateType) {
		for (RateType em : values()) {
			if (em.value == rateType) {
				return em;
			}
		}
		
		return null;
	}
	

	/**
	 * ���[�g�敪��Ԃ�
	 * 
	 * @param rateType ���[�g�敪
	 * @return ���[�g�敪
	 */
	public static String getName(RateType rateType) {

		switch (rateType) {
			case COMPANY:
				return "C11167";//�В背�[�g
			case SETTLEMENT:
				return "C02820";//���Z�����[�g
			case FNC_COMPANY:
				return "C11216";//�В背�[�g�i�@�\�ʉ݁j
			case FNC_SETTLEMENT:
				return "C11217";//���Z�����[�g�i�@�\�ʉ݁j
			default:
				return null;
		}
		
	}
	
}


package jp.co.ais.trans2.define;

/**
 * ���Z�敪
 * 
 * @author AIS
 */
public enum ConvertType {
	
	/** �|�Z */
	MULTIPLICATION(0),
	
	/** ���Z */
	DIVIDE(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ConvertType(int value) {
		this.value = value;
	}

	/**
	 * ���Z�敪��Ԃ�
	 * 
	 * @param type �l
	 * @return ���Z�敪
	 */
	public static ConvertType get(int type) {
		for (ConvertType em : values()) {
			if (em.value == type) {
				return em;
			}
		}
		
		return null;
	}

	
	/**
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	
	/**
	 *
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}
}


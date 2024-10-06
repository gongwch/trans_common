package jp.co.ais.trans2.define;

/**
 * Š·ŽZ‹æ•ª
 * 
 * @author AIS
 */
public enum ConvertType {
	
	/** Š|ŽZ */
	MULTIPLICATION(0),
	
	/** Š„ŽZ */
	DIVIDE(1);

	/** ’l */
	public int value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 * 
	 * @param value ’l
	 */
	private ConvertType(int value) {
		this.value = value;
	}

	/**
	 * Š·ŽZ‹æ•ª‚ð•Ô‚·
	 * 
	 * @param type ’l
	 * @return Š·ŽZ‹æ•ª
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


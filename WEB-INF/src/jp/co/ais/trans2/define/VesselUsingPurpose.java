package jp.co.ais.trans2.define;

/**
 * 船機械使用目的区分
 */
public enum VesselUsingPurpose implements OPEnum {

	/** 0:ENGINE(エンジン) */
	ENGINE(0),

	/** 1:AUX(補助) */
	AUX(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	VesselUsingPurpose(int value) {
		this.value = value;
	}

	/**
	 * Enumを返す
	 * 
	 * @param value
	 * @return Enum
	 */
	public static VesselUsingPurpose get(int value) {
		for (VesselUsingPurpose em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}

	/**
	 * @see jp.co.ais.trans2.define.OPEnum#getCodeDivision()
	 */
	public OPCodeDivision getCodeDivision() {
		return OPCodeDivision.USING_PURPOSE;
	}

	/**
	 * @see jp.co.ais.trans2.define.OPEnum#getCode()
	 */
	public String getCode() {
		return Integer.toString(value);
	}
}

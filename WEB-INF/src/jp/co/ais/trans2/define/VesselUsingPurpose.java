package jp.co.ais.trans2.define;

/**
 * �D�@�B�g�p�ړI�敪
 */
public enum VesselUsingPurpose implements OPEnum {

	/** 0:ENGINE(�G���W��) */
	ENGINE(0),

	/** 1:AUX(�⏕) */
	AUX(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	VesselUsingPurpose(int value) {
		this.value = value;
	}

	/**
	 * Enum��Ԃ�
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

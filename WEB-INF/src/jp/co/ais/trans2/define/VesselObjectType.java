package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * The Enum Object Type.
 */
public enum VesselObjectType implements TEnumRadio {

	/** cmb. */
	Cargo(0),

	/** cft. */
	Passenger(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �l
	 */
	VesselObjectType(int value) {
		this.value = value;
	}

	/**
	 * ���̎擾
	 */
	public String getName() {
		return getName(this);
	}


	/**
	 * ���̎擾.
	 * 
	 * @param type
	 * @return name
	 */
	public static String getName(VesselObjectType type) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case Cargo:
				return "COP1229";
			case Passenger:
				return "COP1230";
			default:
				return "";
		}
	}
	
	/**
	 * Enum��Ԃ�
	 * 
	 * @param value
	 * @return Enum
	 */
	public static VesselObjectType get(int value) {
		for (VesselObjectType em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}

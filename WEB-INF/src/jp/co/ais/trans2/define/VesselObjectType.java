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

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	VesselObjectType(int value) {
		this.value = value;
	}

	/**
	 * 名称取得
	 */
	public String getName() {
		return getName(this);
	}


	/**
	 * 名称取得.
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
	 * Enumを返す
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

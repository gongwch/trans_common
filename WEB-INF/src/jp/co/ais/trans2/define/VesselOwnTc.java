package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * VCVesselOwnTc
 */
@Deprecated
public enum VesselOwnTc implements TEnumRadio {

	/** 0:OWN */
	OWN(0),

	/** 1:TC */
	TC(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	VesselOwnTc(int value) {
		this.value = value;
	}

	/**
	 * 名称取得
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称を返す
	 * 
	 * @param value
	 * @return 名称
	 */
	public static String getName(int value) {
		return getName(get(value));
	}

	/**
	 * 名称を返す
	 * 
	 * @param type
	 * @return 名称
	 */
	public static String getName(VesselOwnTc type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case OWN:
				return "OWN";
			case TC:
				return "TC";
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
	public static VesselOwnTc get(int value) {
		for (VesselOwnTc em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}

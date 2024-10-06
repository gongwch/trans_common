package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * VesselType
 */
@Deprecated
public enum VesselType implements TEnumRadio {

	/** 0: BULK CARRIER */
	BULK_CARRIER(0),

	/** 1: LOG CARRIER */
	LOG_CARRIER(1),

	/** 2: CHIP CARRIER */
	CHIP_CARRIER(2),

	/** 3: COAL CARRIER */
	COAL_CARRIER(3),

	/** 4: ORE CARRIER */
	ORE_CARRIER(4),

	/** 5: ORE/COAL CARRIER */
	ORE_COAL_CARRIER(5),

	/** 6: OIL TANKER */
	OIL_TANKER(6),

	/** 7: PRODUCT TANKER */
	PRODUCT_TANKER(7),

	/** 8: CHEMICAL TANKER */
	CHEMICAL_TANKER(8),

	/** 9: LNG CARRIER */
	LNG_CARRIER(9),

	/** 10: LPG CARRIER */
	LPG_CARRIER(10),

	/** 11: PCC */
	PCC(11),

	/** 12: CARGO SHIP */
	CARGO_SHIP(12),

	/** 13: HEAVY LIFTER */
	HEAVY_LIFTER(13),

	/** 14: MULTI-PURPOSE CARGO SHIP */
	MULTI_PURPOSE_CARGO_SHIP(14),

	/** 15: REEFER CARRIER */
	REEFER_CARRIER(15);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	VesselType(int value) {
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
	public static String getName(VesselType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case BULK_CARRIER:
				return "BULK CARRIER";
			case LOG_CARRIER:
				return "LOG CARRIER";
			case CHIP_CARRIER:
				return "CHIP CARRIER";
			case COAL_CARRIER:
				return "COAL CARRIER";
			case ORE_CARRIER:
				return "ORE CARRIER";
			case ORE_COAL_CARRIER:
				return "ORE/COAL CARRIER";
			case OIL_TANKER:
				return "OIL TANKER";
			case PRODUCT_TANKER:
				return "PRODUCT TANKER";
			case CHEMICAL_TANKER:
				return "CHEMICAL TANKER";
			case LNG_CARRIER:
				return "LNG CARRIER";
			case LPG_CARRIER:
				return "LPG CARRIER";
			case PCC:
				return "PCC";
			case CARGO_SHIP:
				return "CARGO SHIP";
			case HEAVY_LIFTER:
				return "HEAVY LIFTER";
			case MULTI_PURPOSE_CARGO_SHIP:
				return "MULTI-PURPOSE CARGO SHIP";
			case REEFER_CARRIER:
				return "REEFER CARRIER";
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
	public static VesselType get(int value) {
		for (VesselType em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}

	/**
	 * Enumを返す
	 * 
	 * @param str
	 * @return Enum
	 */
	public static VesselType get(String str) {
		// FIXME:今後の考えは？
		return VesselType.BULK_CARRIER;
	}
}

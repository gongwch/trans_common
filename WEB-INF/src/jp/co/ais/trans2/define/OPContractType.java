package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Contract Type
 */
public enum OPContractType implements TEnumRadio {

	/** 0:TC-OUT */
	TC_OUT(0),

	/** 1:TC-IN */
	TC_IN(1),

	/** 3:VC */
	VC(3),

	/** 4:VC(個品) */
	IC(4),

	/** 5:VR */
	VR(5),

	/** 6:VR(個品) */
	IR(6),

	/** 7:BL 表示名：VC(個品) */
	BL(7),

	/** 8:VR(BL-Liner) 表示名：VR(個品) */
	BR(8),

	/** 10:運航委託 */
	OS(10),

	/** 9:共通 */
	COM(9);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	OPContractType(int value) {
		this.value = value;
	}

	/**
	 * 名称取得
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 略称取得
	 * 
	 * @return 略称
	 */
	public String getNames() {
		return getNames(this);
	}

	/**
	 * 名称を返す
	 * 
	 * @param type
	 * @return 名称
	 */
	public static String getName(OPContractType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case TC_OUT:
				return "COP190"; // TC-OUT
			case TC_IN:
				return "COP506"; // TC-IN
			case VC:
				return "COP191"; // VC
			case IC:
				return "COP1150"; // VC(個品)
			case VR:
				return "COP1151"; // VR
			case IR:
				return "COP1152"; // VR(個品)
			case BL:
				return "COP1150"; // VC(個品) LINER利用区分によりIC共存不可
			case BR:
				return "COP1152"; // VR(個品) LINER利用区分によりIR共存不可
			case OS:
				return "COP1512";//運航委託
			case COM:
				return "COP898"; // COMM
			default:
				return "";
		}
	}

	/**
	 * 略称を返す
	 * 
	 * @param type
	 * @return 略称
	 */
	public static String getNames(OPContractType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case TC_OUT:
				return "COP344"; // TO
			case TC_IN:
				return "COP526"; // TI
			case VC:
				return "COP191"; // VC
			case IC:
				return "COP1150"; // VC(個品)
			case VR:
				return "COP1151"; // VR
			case IR:
				return "COP1152"; // VR(個品)
			case BL:
				return "COP1150"; // VC(個品) LINER利用区分によりIC共存不可
			case BR:
				return "COP1152"; // VR(個品) LINER利用区分によりIR共存不可
			case OS:
				return "COP1512";//運航委託
			case COM:
				return "COP898"; // COMM
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
	public static OPContractType get(int value) {
		for (OPContractType em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}
}

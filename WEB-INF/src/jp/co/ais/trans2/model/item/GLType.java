package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * GLÈÚ§äæªR{{bNXÚè`
 */
public enum GLType {

	/** Êí */
	NOMAL("00"),

	/** àÈÚ */
	FUND("04"),

	/** ãÈÚ */
	SALES("05"),

	/** ¼¨è */
	TEMPORARY("07");

	/** l */
	public String value;

	/**
	 * RXgN^.
	 * 
	 * @param value l
	 */
	private GLType(String value) {
		this.value = value;
	}

	/**
	 * GLÈÚ§äæªðÔ·
	 * 
	 * @param gl GLÈÚ§äæª
	 * @return l
	 */
	public static GLType getGl(String gl) {
		for (GLType em : values()) {
			if (em.value.equals(gl)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * @return l
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}

	/**
	 * GLÈÚ§äæªðÔ·
	 * 
	 * @param gl GLÈÚ§äæª
	 * @return GLÈÚ§äæª
	 */
	public static String getName(GLType gl) {

		if (gl == null) {
			return "";
		}

		switch (gl) {
			case NOMAL:
				return "C00372"; // Êí

			case FUND:
				return "C02114"; // àÈÚ

			case SALES:
				return "C02115"; // ãÈÚ

			case TEMPORARY:
				return "C02117"; // ¼¨è

			default:
				return "";
		}
	}
}
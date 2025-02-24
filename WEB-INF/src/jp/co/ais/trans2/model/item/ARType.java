package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * ARΘΪ§δζͺR{{bNXΪθ`
 */
public enum ARType {

	/** Κν */
	NOMAl("00"),

	/** Β ΗΘΪ */
	AR("01"),

	/** Β ρϋΌ¨θ */
	AR_COLLECT("02");

	/** l */
	public String value;

	/**
	 * RXgN^.
	 * 
	 * @param value l
	 */
	private ARType(String value) {
		this.value = value;
	}

	/**
	 * ARΘΪ§δζͺπΤ·
	 * 
	 * @param ar ARΘΪ§δζͺ
	 * @return l
	 */
	public static ARType get(String ar) {
		for (ARType em : values()) {
			if (em.value.equals(ar)) {
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
	 * ARΘΪ§δζͺπΤ·
	 * 
	 * @param ar ARΘΪ§δζͺ
	 * @return ARΘΪ§δζͺ
	 */
	public static String getName(ARType ar) {

		if (ar == null) {
			return "";
		}

		switch (ar) {
			case NOMAl:
				return "C00372"; // Κν

			case AR:
				return "C02120"; // Β ΗΘΪ

			case AR_COLLECT:
				return "C02121"; // Β ρϋΌ¨θ

			default:
				return "";
		}
	}
}
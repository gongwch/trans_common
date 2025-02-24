package jp.co.ais.trans2.model.item;

/**
 * APÈÚ§äæª
 */
public enum APType {

	/** Êí */
	NOMAL("00"),

	/** Â±ÇÈÚ */
	DEBIT("01"),

	/** ]Æõ¼¥¢ÈÚ */
	EMPLOYEE("02");

	/** l */
	public String value;

	/**
	 * RXgN^.
	 * 
	 * @param value l
	 */
	private APType(String value) {
		this.value = value;
	}

	/**
	 * APÈÚ§äæªðÔ·
	 * 
	 * @param ap APÈÚ§äæª
	 * @return l
	 */
	public static APType get(String ap) {
		for (APType em : values()) {
			if (em.value.equals(ap)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * APÈÚ§äæªðÔ·
	 * 
	 * @param ap APÈÚ§äæª
	 * @return APÈÚ§äæª
	 */
	public static String getName(APType ap) {

		if (ap == null) {
			return "";
		}

		switch (ap) {
			case NOMAL:
				return "C00372"; // Êí

			case DEBIT:
				return "C02118"; // Â±ÇÈÚ

			case EMPLOYEE:
				return "C02119"; // ]Æõ¼¥ÈÚ

			default:
				return "";
		}

	}
}
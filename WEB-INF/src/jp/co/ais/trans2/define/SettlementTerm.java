package jp.co.ais.trans2.define;

/**
 * Z`[üÍæª
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public enum SettlementTerm {
	/** êN */
	YEAR(0),
	/** ¼ú */
	HALF(1),
	/** l¼ú */
	QUARTER(2),
	/**  */
	MONTH(3);

	/** DBl */
	private int value;

	/**
	 * RXgN^
	 * 
	 * @param value
	 */
	private SettlementTerm(int value) {
		this.value = value;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 * @return term
	 */
	public static SettlementTerm getSettlementTerm(int value) {
		for (SettlementTerm term : SettlementTerm.values()) {
			if (term.getValue() == value) {
				return term;
			}
		}
		return null;
	}

	/**
	 * Z`[üÍæª¼ÌðÔ·
	 * 
	 * @param term Z`[üÍæª
	 * @return Z`[üÍæª¼Ì
	 */
	public static String getSettlementTermName(SettlementTerm term) {

		if (term == null) {
			return "";
		}

		switch (term) {
			case YEAR:
				return "C11145";// N

			case HALF:
				return "C00435";// ¼ú

			case QUARTER:
				return "C10592";// l¼ú

			case MONTH:
				return "C00147";// 
			default:
				return "";
		}
	}

}

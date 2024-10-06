package jp.co.ais.trans2.define;

/**
 * ŒˆZ“`•[“ü—Í‹æ•ª
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public enum SettlementTerm {
	/** ˆê”N */
	YEAR(0),
	/** ”¼Šú */
	HALF(1),
	/** l”¼Šú */
	QUARTER(2),
	/** ŒŸ */
	MONTH(3);

	/** DB’l */
	private int value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^
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
	 * ŒˆZ“`•[“ü—Í‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @param term ŒˆZ“`•[“ü—Í‹æ•ª
	 * @return ŒˆZ“`•[“ü—Í‹æ•ª–¼Ì
	 */
	public static String getSettlementTermName(SettlementTerm term) {

		if (term == null) {
			return "";
		}

		switch (term) {
			case YEAR:
				return "C11145";// ”NŸ

			case HALF:
				return "C00435";// ”¼Šú

			case QUARTER:
				return "C10592";// l”¼Šú

			case MONTH:
				return "C00147";// ŒŸ
			default:
				return "";
		}
	}

}

package jp.co.ais.trans2.define;

/**
 * —a‹àí–Ú
 * 
 * @author AIS
 */
public enum DepositKind {

	/** 1:•’Ê—a‹à */
	ORDINARY(1),

	/** 2:“–À—a‹à */
	CURRENT(2),

	/** 3:ŠO‰İ */
	FOREIGN_CURRENCY(3),

	/** 4:’™’~ */
	SAVINGS(4),

	/** 5:’èŠú */
	FIXED(5),

	/** 9:‚»‚Ì‘¼ */
	OTHERS(9);

	/** ’l */
	public int value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 * 
	 * @param value ’l
	 */
	private DepositKind(int value) {
		this.value = value;
	}

	/**
	 * —a‹àí–Ú‚ğæ“¾‚·‚é
	 * 
	 * @return —a‹àí–Ú
	 */
	public int getDepositKind() {
		return value;
	}

	/**
	 * —a‹àí–Ú‚ğ•Ô‚·
	 * 
	 * @param depositKind —a‹àí–Ú
	 * @return —a‹àí–Ú–¼
	 */
	public static DepositKind getDepositKind(int depositKind) {
		for (DepositKind em : values()) {
			if (em.value == depositKind) {
				return em;
			}
		}

		return null;
	}

	/**
	 * —a‹àí–Ú–¼Ì‚ğ•Ô‚·
	 * 
	 * @return —a‹àí–Ú–¼Ì
	 */
	public String getDepositKindName() {
		return getDepositKindName(this);
	}

	/**
	 * –¼Ì‚ğ•Ô‚·
	 * 
	 * @return –¼ÌID
	 */
	public String getName() {
		return getDepositKindName(this);
	}

	/**
	 * —a‹àí–Ú–¼Ì‚ğ•Ô‚·
	 * 
	 * @param depositKind —a‹àí–Ú
	 * @return —a‹àí–Ú–¼Ì
	 */
	public static String getDepositKindName(DepositKind depositKind) {

		if (depositKind == null) {
			return "";
		}

		switch (depositKind) {
			case ORDINARY:
				return "C00463"; // •’Ê

			case CURRENT:
				return "C00397"; // “–À

			case FOREIGN_CURRENCY:
				return "C00045"; // ŠO‰İ

			case SAVINGS:
				return "C00368"; // ’™’~

			case FIXED:
				return "C11057"; // ’èŠú

			case OTHERS:
				return "C00338"; // ‚»‚Ì‘¼

			default:
				return "";
		}
	}

	/**
	 * —a‹àí–Ú–¼Ì‚ğ•Ô‚·
	 * 
	 * @param depositKind —a‹àí–Ú
	 * @return —a‹àí–Ú–¼Ì
	 */
	public static String getDepositKindName(int depositKind) {
		return getDepositKindName(getDepositKind(depositKind));
	}

}

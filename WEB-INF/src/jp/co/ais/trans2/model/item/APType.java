package jp.co.ais.trans2.model.item;

/**
 * AP科目制御区分
 */
public enum APType {

	/** 通常 */
	NOMAL("00"),

	/** 債務管理科目 */
	DEBIT("01"),

	/** 従業員仮払い科目 */
	EMPLOYEE("02");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private APType(String value) {
		this.value = value;
	}

	/**
	 * AP科目制御区分を返す
	 * 
	 * @param ap AP科目制御区分
	 * @return 値
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
	 * AP科目制御区分を返す
	 * 
	 * @param ap AP科目制御区分
	 * @return AP科目制御区分
	 */
	public static String getName(APType ap) {

		if (ap == null) {
			return "";
		}

		switch (ap) {
			case NOMAL:
				return "C00372"; // 通常

			case DEBIT:
				return "C02118"; // 債務管理科目

			case EMPLOYEE:
				return "C02119"; // 従業員仮払科目

			default:
				return "";
		}

	}
}
package jp.co.ais.trans2.model.item;

/**
 * BG科目制御区分コンボボックス項目定義
 */
public enum BGType {

	/** 通常 */
	NOMAL("00"),

	/** 予算管理対象 */
	BG("11");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private BGType(String value) {
		this.value = value;
	}

	/**
	 * BG科目制御区分を返す
	 * 
	 * @param bg 補助科目
	 * @return 値
	 */
	public static BGType get(String bg) {
		for (BGType em : values()) {
			if (em.value.equals(bg)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * BG科目制御区分を返す
	 * 
	 * @param bg BG科目制御区分
	 * @return BG科目制御区分
	 */
	public static String getName(BGType bg) {

		if (bg == null) {
			return "";
		}

		switch (bg) {
			case NOMAL:
				return "C00372"; // 通常
			case BG:
				return "C02125"; // 予算管理対象
			default:
				return "";
		}

	}
}
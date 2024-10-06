package jp.co.ais.trans2.model.item;

/**
 * 科目種別コンボボックス項目定義
 */
public enum ItemType {

	/** 貸借科目 */
	DC(0),

	/** 損益科目 */
	PL(1),

	/** 利益処分科目 */
	APPROPRIATION(2),

	/** 製造原価科目 */
	MANUFACTURING_COSTS(3),

	/** 非会計科目 */
	NON_ACCOUNT(9);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ItemType(int value) {
		this.value = value;
	}

	/**
	 * 科目種別を返す
	 * 
	 * @param kmk 科目種別
	 * @return 値
	 */
	public static ItemType get(int kmk) {
		for (ItemType em : values()) {
			if (em.value == kmk) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 科目種別を返す
	 * 
	 * @param kmk 科目種別
	 * @return 科目種別
	 */
	public static String getName(ItemType kmk) {
		if (kmk == null) {
			return "";
		}

		switch (kmk) {
			case DC:
				return "C02108"; // 貸借科目

			case PL:
				return "C02109"; // 損益科目

			case APPROPRIATION:
				return "C02110"; // 利益処分科目

			case MANUFACTURING_COSTS:
				return "C02111"; // 製造原価科目

			case NON_ACCOUNT:
				return "C02112"; // 非会計科目

			default:
				return null;
		}
	}

}
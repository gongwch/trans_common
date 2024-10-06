package jp.co.ais.trans2.define;

/**
 * 科目集計区分
 * @author AIS
 *
 */
public enum ItemSumType {
	/** 入力 */
	INPUT(0),

	/** 集計 */
	SUM(1),

	/** 見出し */
	TITLE(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ItemSumType(int value) {
		this.value = value;
	}

	/**
	 * 区分を返す
	 * 
	 * @param value 値
	 * @return 区分
	 */
	public static ItemSumType get(int value) {
		for (ItemSumType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}
	
	
	/**
	 * @param itemSumType
	 * @return 科目集計区分名称
	 */
	public static String getName(ItemSumType itemSumType){

		switch (itemSumType) {
			case INPUT:
				return "C02157";//入力科目
			case SUM:
				return "C02158";//集計科目
			case TITLE:
				return "C02159";//見出科目 
			default:
				break;
		}
		return null;
	}

}

package jp.co.ais.trans2.define;


/**
 * 非会計明細
 */
public enum NonAccountingDivision {
	/** なし */
	NONE(0),

	/** 数値 */
	NUMBER(1),
	
	/** 文字 */
	CHAR(2),

	/** 日付 */
	YMD_DATE(3),

	/** 日時 */
	YMDHM_DATE(4);

	/** 値 */
	private int value;

	
	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private NonAccountingDivision(int value) {
		this.value = value; 
	}

	/**
	 * 値から型取得
	 * 
	 * @param value 値
	 * @return 型
	 */
	public static NonAccountingDivision get(int value) {
		for (NonAccountingDivision div : values()) {
			if (div.value == value) {
				return div;
			}
		}
		return NONE;
	}

	/**
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * 非会計項目を返す
	 * 
	 * @param nonAccountDivision 非会計項目
	 * @return 非会計項目
	 */
	public static String getNonAccountName(NonAccountingDivision nonAccountDivision) {

		switch (nonAccountDivision) {
			case NONE:
				return "C00282";//使用しない
			case NUMBER:
				return "C02160";//数値
			case CHAR:
				return "C02161";//文字
			case YMD_DATE:
				return "C00446";//日付
			case YMDHM_DATE:
				return "C02906";//日時
			default:
				return null;
		}
	}

}

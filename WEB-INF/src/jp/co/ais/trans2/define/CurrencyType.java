package jp.co.ais.trans2.define;

/**
 * 通貨タイプ
 * 
 * @author AIS
 */
public enum CurrencyType {
	
	/** 基軸通貨 */
	KEY(1) {
		@Override
		public  boolean isKey() {
			return true;
		}
	},
	
	/** 機能通貨 */
	FUNCTIONAL(2){
		@Override
		public boolean isKey() {
			return false;
		}
	};

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CurrencyType(int value) {
		this.value = value;
	}
	
	/**
	 * タイプ取得
	 * 
	 * @param type 実値
	 * @return タイプ
	 */
	public static CurrencyType get(int type) {
		for (CurrencyType em : values()) {
			if (em.value == type) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 通貨名称を返す
	 * 
	 * @param currencyType 通貨タイプ
	 * @return 通貨名称
	 */
	public static String getCurrencyTypeName(CurrencyType currencyType) {
		
		switch (currencyType) {
			case KEY:
				return "C11083"; // "自国通貨"
			case FUNCTIONAL:
				return "C11084"; // "機能通貨"
			default:
				return null;
		}
	}
	
	/**
	 * @return 基軸通貨：ture 機能通貨：false
	 */
	public abstract boolean isKey();

}

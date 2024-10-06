package jp.co.ais.trans2.define;

/**
 * 売上仕入区分
 * 
 * @author AIS
 */
public enum TaxType {
	
	/** 対象外 */
	NOT(0),
	
	/** 売上 */
	SALES(1),
	
	/** 仕入 */
	PURCHAESE(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private TaxType(int value) {
		this.value = value;
	}

	/**
	 * 売上仕入区分を返す
	 * 
	 * @param type 伝票の状態
	 * @return 更新区分名
	 */
	public static TaxType get(int type) {
		for (TaxType em : values()) {
			if (em.value == type) {
				return em;
			}
		}
		
		return null;
	}
	
	/**
	 * @param TaxType
	 * @return 科目集計区分名称 追加しました
	 */
	public static String getName(TaxType taxType){

		switch (taxType) {
			case NOT:
				return "C02103";//対象外
			case SALES:
				return "C00026";//売上
			case PURCHAESE:
				return "C00201";//仕入 
			default:
				break;
		}
		
		return null;
	}
	/**
	 * データ区分のenumを返す
	 * 
	 * @param dataDiv
	 * @return データ区分名称　追加
	 */
	public static TaxType getTaxType(String taxType) {

		if (taxType == null) {
			return null;
		}

		if ("0".equals(taxType)) {
			return NOT;
		} else if ("1".equals(taxType)) {
			return SALES;
		} else if ("2".equals(taxType)) {
			return PURCHAESE;
		} 
		return null;
	}

}

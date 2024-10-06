package jp.co.ais.trans2.define;

/**
 * レート区分
 * 
 * @author AIS
 */
public enum RateType {
	
	/** 社定レート */
	COMPANY(0),
	
	/** 決算日レート */
	SETTLEMENT(1),
	
	/** 社定レート（機能通貨） */
	FNC_COMPANY(2),
	
	/** 決算日レート（機能通貨） */
	FNC_SETTLEMENT(3);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private RateType(int value) {
		this.value = value;
	}

	/**
	 * レート区分を返す
	 * 
	 * @param rateType レート区分
	 * @return 値
	 */
	public static RateType getRateType(int rateType) {
		for (RateType em : values()) {
			if (em.value == rateType) {
				return em;
			}
		}
		
		return null;
	}
	

	/**
	 * レート区分を返す
	 * 
	 * @param rateType レート区分
	 * @return レート区分
	 */
	public static String getName(RateType rateType) {

		switch (rateType) {
			case COMPANY:
				return "C11167";//社定レート
			case SETTLEMENT:
				return "C02820";//決算日レート
			case FNC_COMPANY:
				return "C11216";//社定レート（機能通貨）
			case FNC_SETTLEMENT:
				return "C11217";//決算日レート（機能通貨）
			default:
				return null;
		}
		
	}
	
}


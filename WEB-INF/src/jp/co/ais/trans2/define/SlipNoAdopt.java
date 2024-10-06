package jp.co.ais.trans2.define;

/**
 * 自動設定項目
 */
public enum SlipNoAdopt {
	/** なし */
	NONE(0),
	
	/** 年度(YY) */
	YY_DATE(1),
	
	/** 年度(YYYY) */
	YYYY_DATE(2),
	
	/** 年月(YYMM) */
	YYMM_DATE(3),
	
	/** 年月(YYYYMM) */
	YYYYMM_DATE(4),
	
	/** ユーザー */
	USER(5),
	
	/** 部門 */
	DEPARTMENT(6),
	
	/** システム区分 */
	DIVISION(7),
	
	/** 会社コード */
	CODE(8),
	
	/** 伝票種別 */
	SLIPTYPE(9);
	
	/** 値 */
	private int value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private SlipNoAdopt(int value) {
		this.value = value;
	}
	
	/**
	 * 値から型取得
	 * 
	 * @param value 値
	 * @return 型
	 */
	public static SlipNoAdopt get(int value) {
		for (SlipNoAdopt slipNoAdopt : values()) {
			if (slipNoAdopt.value == value) {
				return slipNoAdopt;
			}
		}
		return NONE;
	}
	
	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 伝票採番項目を返す
	 * 
	 * @param slipNoAdopt 伝票採番項目
	 * @return 採番項目
	 */
	public static String getSlipNoAdoptName(SlipNoAdopt slipNoAdopt) {

		switch (slipNoAdopt) {
			case NONE:
				// なし
				return "C00412";
			case YY_DATE:
			// 年度(YY):
				return "C02162";
			case YYYY_DATE:
				// 年度(YYYY)
				return "C02163";
			case YYMM_DATE:
				// 年月(YYMM)
				return "C02164";
			case YYYYMM_DATE:
				// 年月(YYYYMM)
				return "C02165";
			case USER:
				// ユーザー
				return "C00528";
			case DEPARTMENT:
				// 部門
				return "C00467";
			case DIVISION:
				// システム区分
				return "C00980";
			case CODE:
				// 会社コード
				return "C00596";
			case SLIPTYPE:
				// 伝票種別
				return "C00917";
			default:
				return null;
		}
	}
	
}

package jp.co.ais.trans2.define;

/**
 * 請求書番号 自動設定項目
 */
public enum InvoiceNoAdopt {
	/** なし */
	NONE(0),
	
	/** 固定文字 */
	FIXED_CHAR(1),
	
	/** 年度(YY) */
	YY_DATE(2),
	
	/** 年度(YYYY) */
	YYYY_DATE(3),
	
	/** 月度(MM) */
	MM_DATE(4),
	
	/** 年月(YYMM) */
	YYMM_DATE(5),
	
	/** 年月(YYYYMM) */
	YYYYMM_DATE(6),
	
	/** 部門 */
	DEPARTMENT(7),
	
	/** ユーザー */
	USER(8),
	
	/** 会社コード */
	CODE(9);
	
	/** 値 */
	private int value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private InvoiceNoAdopt(int value) {
		this.value = value;
	}
	
	/**
	 * 値から型取得
	 * 
	 * @param value 値
	 * @return 型
	 */
	public static InvoiceNoAdopt get(int value) {
		for (InvoiceNoAdopt invoiceNoAdopt : values()) {
			if (invoiceNoAdopt.value == value) {
				return invoiceNoAdopt;
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
	 * 請求書番号 採番項目を返す
	 * 
	 * @param invoiceNoAdopt 請求書番号項目
	 * @return 採番項目
	 */
	public static String getInvoiceNoAdoptName(InvoiceNoAdopt invoiceNoAdopt) {

		switch (invoiceNoAdopt) {
			case NONE:
				// なし
				return "C00412";
			case FIXED_CHAR:
			// 固定文字:
				return "C12192";
			case YY_DATE:
			// 年度(YY):
				return "C02162";
			case YYYY_DATE:
				// 年度(YYYY)
				return "C02163";
			case MM_DATE:
				// 月度(MM)
				return "C12193";
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
			case CODE:
				// 会社コード
				return "C00596";
			default:
				return null;
		}
	}
	
}

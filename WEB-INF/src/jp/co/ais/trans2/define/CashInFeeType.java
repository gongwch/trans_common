package jp.co.ais.trans2.define;

/**
 * 入金手数料区分
 */
public enum CashInFeeType {

	/** 当社負担 */
	Our(1),

	/** 先方負担 */
	Other(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CashInFeeType(int value) {
		this.value = value;
	}

	/**
	 * タイプ取得
	 * 
	 * @param cashInFeeType 値
	 * @return タイプ
	 */
	public static CashInFeeType getCashInFeeType(int cashInFeeType) {
		for (CashInFeeType em : values()) {
			if (em.value == cashInFeeType) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 名称取得
	 * 
	 * @return 名称ID
	 */
	public String getName() {
		return getCashInFeeTypeName(this);
	}

	/**
	 * 名称取得
	 * 
	 * @param cashInFeeType タイプ
	 * @return 名称
	 */
	public static String getCashInFeeTypeName(CashInFeeType cashInFeeType) {
		if (cashInFeeType == null) {
			return "";
		}

		return getCashInFeeTypeName(cashInFeeType.value);
	}

	/**
	 * 名称取得
	 * 
	 * @param cashInFeeType 値
	 * @return 名称
	 */
	public static String getCashInFeeTypeName(int cashInFeeType) {

		if (Our.value == cashInFeeType) {
			return "C00399"; // 当社負担

		} else if (Other.value == cashInFeeType) {
			return "C00327"; // 先方負担
		}
		return null;

	}

}

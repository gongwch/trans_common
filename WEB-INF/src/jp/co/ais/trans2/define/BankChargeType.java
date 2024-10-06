package jp.co.ais.trans2.define;

/**
 * バンクチャージ区分
 */
public enum BankChargeType {

	/** 0:SHA(受取人負担) */
	SHA(0),

	/** 1:BEN(手数料全額受取人負担) */
	BEN(1),

	/** 2:OUR(依頼人負担) */
	OUR(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private BankChargeType(int value) {
		this.value = value;
	}

	/**
	 * 定義取得
	 * 
	 * @param value 値
	 * @return 定義
	 */
	public static BankChargeType get(int value) {
		for (BankChargeType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 名称を返す
	 * 
	 * @return 名称ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称取得
	 * 
	 * @param type タイプ
	 * @return 名称ID
	 */
	public static String getName(BankChargeType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case SHA:
				return "C02600"; // 受取人負担

			case BEN:
				return "C11430"; // 手数料全額受取人負担

			case OUR:
				return "C02601"; // 依頼人負担

			default:
				return "";
		}
	}
}

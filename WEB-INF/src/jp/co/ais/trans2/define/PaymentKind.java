package jp.co.ais.trans2.define;

/**
 * 支払処理 支払方法内部コード
 * 
 * @author AIS
 */
public enum PaymentKind {

	/** 01:現金(社員) */
	EMP_PAY_CASH("01"),
	/** 03:未払振込(社員) */
	EMP_PAY_UNPAID("03"),
	/** 04:クレジット(社員) */
	EMP_PAY_CREDIT("04"),
	/** 10:社員未収(社員) */
	EMP_PAY_ACCRUED("10"),
	/** 11:現金 */
	EX_PAY_CASH("11"),
	/** 12:振込(銀行窓口) */
	EX_PAY_BANK("12"),
	/** 13:振込(FB作成) */
	EX_PAY_FB("13"),
	/** 14:小切手 */
	EX_PAY_CHECK("14"),
	/** 15:支払手形 */
	EX_PAY_BILL("15"),
	/** 16:消込 */
	EX_PAY_ERASE("16"),
	/** 17:相殺 */
	EX_PAY_OFFSET("17"),
	/** 18:外国送金 */
	EX_PAY_REMITTANCE_ABROAD("18"),
	/** 19:振込(国外用窓口) */
	EX_PAY_BANK_ABROAD("19"),
	/** 99:その他 */
	OTHER("99");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private PaymentKind(String value) {
		this.value = value;
	}

	/**
	 * 支払方法内部コードを取得する
	 * 
	 * @return 支払方法内部コード
	 */
	public String getPaymentKind() {
		return value;
	}

	/**
	 * 支払方法内部コードを返す
	 * 
	 * @param paymentKind 支払方法内部コード
	 * @return 支払方法内部コード名
	 */
	public static PaymentKind getPaymentKind(String paymentKind) {
		for (PaymentKind em : values()) {
			if (em.value.equals(paymentKind)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 銀行口座からの振出かを返す
	 * 
	 * @param paymentKind 支払方法
	 * @return true:振り出す
	 */
	public static boolean isBankAccountPayment(PaymentKind paymentKind) {
		if (paymentKind == null) {
			return false;
		}

		switch (paymentKind) {
			case EX_PAY_BANK:
			case EX_PAY_FB:
			case EX_PAY_CHECK:
			case EX_PAY_REMITTANCE_ABROAD:
			case EX_PAY_BANK_ABROAD:
			case EX_PAY_BILL:
				return true;

			default:
				return false;
		}
	}

	/**
	 * 銀行口座からの振出かを返す
	 * 
	 * @param paymentKind 支払方法
	 * @return true:振り出す
	 */
	public static boolean isBankAccountPayment(String paymentKind) {
		return isBankAccountPayment(getPaymentKind(paymentKind));
	}

	/**
	 * データ区分名称を返す
	 * 
	 * @param paymentKind
	 * @return データ区分名称
	 */
	public static String getPaymentKindName(PaymentKind paymentKind) {

		if (paymentKind == null) {
			return null;
		}

		switch (paymentKind) {
			case EMP_PAY_CASH:
				return "C02135";// 現金(社員)

			case EMP_PAY_UNPAID:
				return "C02136";// 未払振込(社員)

			case EMP_PAY_CREDIT:
				return "C02137";// クレジット(社員)

			case EMP_PAY_ACCRUED:
				return "C02138";// 社員未収(社員)

			case EX_PAY_CASH:
				return "C00154";// 現金

			case EX_PAY_BANK:
				return "C02139";// 振込(銀行窓口)

			case EX_PAY_FB:
				return "C02140";// 振込(FB作成)

			case EX_PAY_CHECK:
				return "C02141";// 小切手

			case EX_PAY_BILL:
				return "C00230";// 支払手形

			case EX_PAY_ERASE:
				return "C02142";// 消込

			case EX_PAY_OFFSET:
				return "C00331";// 相殺

			case EX_PAY_REMITTANCE_ABROAD:
				return "C02143";// 外国送金

			case EX_PAY_BANK_ABROAD:
				return "C02144";// 振込(国外用窓口)

			case OTHER:
				return "C00338";// その他

			default:
				return null;

		}
	}
}

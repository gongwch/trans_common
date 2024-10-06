package jp.co.ais.trans2.define;

/**
 * x•¥‹æ•ª
 * 
 * @author AIS
 */
public enum PaymentDateType {

	/** ğŒ‚È‚µ */
	NONE(""),

	/** 00:—Õ */
	TEMPORARY("00"),

	/** 10:’è */
	REGULAR("10"),

	/** 20:‹¤’Ê */
	BOTH("20");

	/** ’l */
	public String value;

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 * 
	 * @param value ’l
	 */
	private PaymentDateType(String value) {
		this.value = value;
	}

	/**
	 * x•¥‹æ•ª‚ğæ“¾‚·‚é
	 * 
	 * @return x•¥‹æ•ª
	 */
	public String getPaymentDateType() {
		return value;
	}

	/**
	 * x•¥‹æ•ª‚ğ•Ô‚·
	 * 
	 * @param paymentDateType x•¥‹æ•ª
	 * @return x•¥‹æ•ª–¼
	 */
	public static PaymentDateType getPaymentDateType(String paymentDateType) {
		for (PaymentDateType em : values()) {
			if (em.value.equals(paymentDateType)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * –¼Ì‚ğ•Ô‚·
	 * 
	 * @return –¼ÌID
	 */
	public String getName() {
		return getPaymentDateTypeName(this);
	}

	/**
	 * x•¥‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @param paymentDateType x•¥‹æ•ª
	 * @return x•¥‹æ•ª–¼Ì
	 */
	public static String getPaymentDateTypeName(PaymentDateType paymentDateType) {

		if (paymentDateType == null) {
			return null;
		}

		switch (paymentDateType) {
			case NONE:
				return "";
			case TEMPORARY:
				return "C00555"; // —Õ

			case REGULAR:
				return "C00380"; // ’è

			default:
				return null;
		}
	}

	/**
	 * x•¥‹æ•ª–¼Ì‚ğ•Ô‚·
	 * 
	 * @param paymentDateType x•¥‹æ•ª
	 * @return x•¥‹æ•ª–¼Ì
	 */
	public static String getPaymentDateTypeName(String paymentDateType) {
		return getPaymentDateTypeName(getPaymentDateType(paymentDateType));
	}

}

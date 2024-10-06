package jp.co.ais.trans2.define;

/**
 * ©“®d–ó‰È–Ú §Œä‹æ•ª
 */
public enum AutoJornalAccountType {

	/** ‘OŠúŒJ‰z—˜‰v */
	PREVIOUS_EARNING_CARRIED_FORWARD(1),

	/** ‰¼•¥Á”ïÅ */
	PAY_TAX(2),

	/** ‰¼óÁ”ïÅ */
	RECEIVE_TAX(3),

	/** ˆ×‘Ö·‘¹ */
	EXCHANGE_LOSE(4),

	/** ˆ×‘Ö·‰v */
	EXCHANGE_GAIN(5),

	/** ]‹Æˆõ‰¼•¥‹à */
	EMPLOYEE_PAYMENT(6),

	/** ÂŒ ‰ñû‰¼Š¨’è */
	DEBT_COLLECTION(7),

	/** ‘ŠE¸ZÂ–±Š¨’è */
	OFFSET_PAYMENT(8),

	/** ‘ŠE¸ZÂŒ Š¨’è */
	OFFSET_RECEIVABLE(9),

	/** –¢ÀŒ»ˆ×‘Ö·‘¹ */
	UNREALIZED_EXCHANGE_LOSE(10),

	/** –¢ÀŒ»ˆ×‘Ö·‰v */
	UNREALIZED_EXCHANGE_GAIN(11);

	/** ‰È–Ú§Œä‹æ•ª */
	public int value;

	/**
	 * ƒRƒ“ƒgƒ[ƒ‹ƒNƒ‰ƒX
	 *
	 * @param value ‰È–Ú§Œä‹æ•ª
	 */
	private AutoJornalAccountType(int value) {
		this.value = value;
	}

	/**
	 * ©“®d–ó‰È–Ú§Œä‹æ•ª‚ğ•Ô‚·
	 *
	 * @param type ©“®d–ó‰È–Ú§Œä‹æ•ª’è”
	 * @return ©“®d–ó‰È–Ú§Œä‹æ•ª
	 */
	public static AutoJornalAccountType get(int type) {
		for (AutoJornalAccountType em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}


	/**
	 * –¼Ìæ“¾
	 *
	 * @return –¼Ì
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * –¼Ìæ“¾
	 *
	 * @param type ƒ^ƒCƒv
	 * @return –¼Ì
	 */
	public static String getName(AutoJornalAccountType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case PREVIOUS_EARNING_CARRIED_FORWARD:
				return "C02113";// ‘OŠúŒJ‰z—˜‰v

			case PAY_TAX:
				return "C01626";// ‰¼•¥Á”ïÅ

			case RECEIVE_TAX:
				return "C01623";// ‰¼óÁ”ïÅ

			case EXCHANGE_LOSE:
				return "C01782";// ˆ×‘Ö·‘¹

			case EXCHANGE_GAIN:
				return "C01783";// ˆ×‘Ö·‰v

			case EMPLOYEE_PAYMENT:
				return "C02107";// ]‹Æˆõ‰¼•¥‹à

			case DEBT_COLLECTION:
				return "C02121";// ÂŒ ‰ñû‰¼Š¨’è

			case OFFSET_PAYMENT:
				return "C02595";// ‘ŠE¸ZÂ–±Š¨’è

			case OFFSET_RECEIVABLE:
				return "C02596";// ‘ŠE¸ZÂŒ Š¨’è

			case UNREALIZED_EXCHANGE_LOSE:
				return "C11221";// –¢ÀŒ»ˆ×‘Ö·‘¹

			case UNREALIZED_EXCHANGE_GAIN:
				return "C11222";// –¢ÀŒ»ˆ×‘Ö·‰v

			default:
				return "";
		}
	}
}

package jp.co.ais.trans2.define;

/**
 * üàè¿æª
 */
public enum CashInFeeType {

	/** ÐS */
	Our(1),

	/** æûS */
	Other(2);

	/** l */
	public int value;

	/**
	 * RXgN^.
	 * 
	 * @param value l
	 */
	private CashInFeeType(int value) {
		this.value = value;
	}

	/**
	 * ^Cvæ¾
	 * 
	 * @param cashInFeeType l
	 * @return ^Cv
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
	 * ¼Ìæ¾
	 * 
	 * @return ¼ÌID
	 */
	public String getName() {
		return getCashInFeeTypeName(this);
	}

	/**
	 * ¼Ìæ¾
	 * 
	 * @param cashInFeeType ^Cv
	 * @return ¼Ì
	 */
	public static String getCashInFeeTypeName(CashInFeeType cashInFeeType) {
		if (cashInFeeType == null) {
			return "";
		}

		return getCashInFeeTypeName(cashInFeeType.value);
	}

	/**
	 * ¼Ìæ¾
	 * 
	 * @param cashInFeeType l
	 * @return ¼Ì
	 */
	public static String getCashInFeeTypeName(int cashInFeeType) {

		if (Our.value == cashInFeeType) {
			return "C00399"; // ÐS

		} else if (Other.value == cashInFeeType) {
			return "C00327"; // æûS
		}
		return null;

	}

}

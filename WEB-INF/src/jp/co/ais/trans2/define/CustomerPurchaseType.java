package jp.co.ais.trans2.define;

/**
 * 仕入先区分
 * @author AIS
 *
 */
public enum CustomerPurchaseType {

	/** 非仕入先 */
	NotPurchase(0),

	/** 仕入先 */
	Purchase(1);

	/** 値 */
	public int value;

	private CustomerPurchaseType(int value) {
		this.value = value;
	}

	public static String getName(CustomerPurchaseType customerPurchaseType) {
		if (NotPurchase == customerPurchaseType) {
			return "C01295";//非仕入先
		}
		return "C00203";//仕入先
	}

	public static CustomerPurchaseType get(int customerPurchaseType) {
		for (CustomerPurchaseType em : values()) {
			if (em.value == customerPurchaseType) {
				return em;
			}
		}
		return null;		
	}

}

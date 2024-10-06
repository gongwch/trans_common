package jp.co.ais.trans2.define;

/**
 * �d����敪
 * @author AIS
 *
 */
public enum CustomerPurchaseType {

	/** ��d���� */
	NotPurchase(0),

	/** �d���� */
	Purchase(1);

	/** �l */
	public int value;

	private CustomerPurchaseType(int value) {
		this.value = value;
	}

	public static String getName(CustomerPurchaseType customerPurchaseType) {
		if (NotPurchase == customerPurchaseType) {
			return "C01295";//��d����
		}
		return "C00203";//�d����
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

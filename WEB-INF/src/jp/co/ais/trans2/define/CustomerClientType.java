package jp.co.ais.trans2.define;

/**
 * “¾ˆÓæ‹æ•ª
 * @author AIS
 *
 */
public enum CustomerClientType {

	/** ”ñ“¾ˆÓæ */
	NotClient(0),

	/** “¾ˆÓæ */
	Client(1);

	/** ’l */
	public int value;

	private CustomerClientType(int value) {
		this.value = value;
	}

	public static String getName(CustomerClientType customerClientType) {	
		if (NotClient == customerClientType) {
			return "C01296";//”ñ“¾ˆÓæ
		}
		return "C00401";//“¾ˆÓæ
	}

	public static CustomerClientType get(int customerClientType) {
		for (CustomerClientType em : values()) {
			if (em.value == customerClientType) {
				return em;
			}
		}
		return null;
	}

}

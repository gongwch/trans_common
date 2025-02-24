package jp.co.ais.trans2.define;

/**
 * 得意先区分
 * @author AIS
 *
 */
public enum CustomerClientType {

	/** 非得意先 */
	NotClient(0),

	/** 得意先 */
	Client(1);

	/** 値 */
	public int value;

	private CustomerClientType(int value) {
		this.value = value;
	}

	public static String getName(CustomerClientType customerClientType) {	
		if (NotClient == customerClientType) {
			return "C01296";//非得意先
		}
		return "C00401";//得意先
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

package jp.co.ais.trans2.define;

/**
 * ���Ӑ�敪
 * @author AIS
 *
 */
public enum CustomerClientType {

	/** �񓾈Ӑ� */
	NotClient(0),

	/** ���Ӑ� */
	Client(1);

	/** �l */
	public int value;

	private CustomerClientType(int value) {
		this.value = value;
	}

	public static String getName(CustomerClientType customerClientType) {	
		if (NotClient == customerClientType) {
			return "C01296";//�񓾈Ӑ�
		}
		return "C00401";//���Ӑ�
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

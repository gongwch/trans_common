package jp.co.ais.trans2.define;

/**
 * �ȖڏW�v�敪
 * @author AIS
 *
 */
public enum ItemSumType {
	/** ���� */
	INPUT(0),

	/** �W�v */
	SUM(1),

	/** ���o�� */
	TITLE(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ItemSumType(int value) {
		this.value = value;
	}

	/**
	 * �敪��Ԃ�
	 * 
	 * @param value �l
	 * @return �敪
	 */
	public static ItemSumType get(int value) {
		for (ItemSumType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}
	
	
	/**
	 * @param itemSumType
	 * @return �ȖڏW�v�敪����
	 */
	public static String getName(ItemSumType itemSumType){

		switch (itemSumType) {
			case INPUT:
				return "C02157";//���͉Ȗ�
			case SUM:
				return "C02158";//�W�v�Ȗ�
			case TITLE:
				return "C02159";//���o�Ȗ� 
			default:
				break;
		}
		return null;
	}

}

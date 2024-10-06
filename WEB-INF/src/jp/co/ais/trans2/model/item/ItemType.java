package jp.co.ais.trans2.model.item;

/**
 * �Ȗڎ�ʃR���{�{�b�N�X���ڒ�`
 */
public enum ItemType {

	/** �ݎ؉Ȗ� */
	DC(0),

	/** ���v�Ȗ� */
	PL(1),

	/** ���v�����Ȗ� */
	APPROPRIATION(2),

	/** ���������Ȗ� */
	MANUFACTURING_COSTS(3),

	/** ���v�Ȗ� */
	NON_ACCOUNT(9);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ItemType(int value) {
		this.value = value;
	}

	/**
	 * �Ȗڎ�ʂ�Ԃ�
	 * 
	 * @param kmk �Ȗڎ��
	 * @return �l
	 */
	public static ItemType get(int kmk) {
		for (ItemType em : values()) {
			if (em.value == kmk) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �Ȗڎ�ʂ�Ԃ�
	 * 
	 * @param kmk �Ȗڎ��
	 * @return �Ȗڎ��
	 */
	public static String getName(ItemType kmk) {
		if (kmk == null) {
			return "";
		}

		switch (kmk) {
			case DC:
				return "C02108"; // �ݎ؉Ȗ�

			case PL:
				return "C02109"; // ���v�Ȗ�

			case APPROPRIATION:
				return "C02110"; // ���v�����Ȗ�

			case MANUFACTURING_COSTS:
				return "C02111"; // ���������Ȗ�

			case NON_ACCOUNT:
				return "C02112"; // ���v�Ȗ�

			default:
				return null;
		}
	}

}
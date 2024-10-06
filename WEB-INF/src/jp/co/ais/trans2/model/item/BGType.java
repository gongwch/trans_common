package jp.co.ais.trans2.model.item;

/**
 * BG�Ȗڐ���敪�R���{�{�b�N�X���ڒ�`
 */
public enum BGType {

	/** �ʏ� */
	NOMAL("00"),

	/** �\�Z�Ǘ��Ώ� */
	BG("11");

	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private BGType(String value) {
		this.value = value;
	}

	/**
	 * BG�Ȗڐ���敪��Ԃ�
	 * 
	 * @param bg �⏕�Ȗ�
	 * @return �l
	 */
	public static BGType get(String bg) {
		for (BGType em : values()) {
			if (em.value.equals(bg)) {
				return em;
			}
		}

		return null;
	}

	/**
	 * BG�Ȗڐ���敪��Ԃ�
	 * 
	 * @param bg BG�Ȗڐ���敪
	 * @return BG�Ȗڐ���敪
	 */
	public static String getName(BGType bg) {

		if (bg == null) {
			return "";
		}

		switch (bg) {
			case NOMAL:
				return "C00372"; // �ʏ�
			case BG:
				return "C02125"; // �\�Z�Ǘ��Ώ�
			default:
				return "";
		}

	}
}
package jp.co.ais.trans2.define;

/**
 * �o���N�`���[�W�敪
 */
public enum BankChargeType {

	/** 0:SHA(���l���S) */
	SHA(0),

	/** 1:BEN(�萔���S�z���l���S) */
	BEN(1),

	/** 2:OUR(�˗��l���S) */
	OUR(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private BankChargeType(int value) {
		this.value = value;
	}

	/**
	 * ��`�擾
	 * 
	 * @param value �l
	 * @return ��`
	 */
	public static BankChargeType get(int value) {
		for (BankChargeType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̎擾
	 * 
	 * @param type �^�C�v
	 * @return ����ID
	 */
	public static String getName(BankChargeType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case SHA:
				return "C02600"; // ���l���S

			case BEN:
				return "C11430"; // �萔���S�z���l���S

			case OUR:
				return "C02601"; // �˗��l���S

			default:
				return "";
		}
	}
}

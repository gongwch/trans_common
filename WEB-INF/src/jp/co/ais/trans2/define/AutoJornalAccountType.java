package jp.co.ais.trans2.define;

/**
 * �����d��Ȗ� ����敪
 */
public enum AutoJornalAccountType {

	/** �O���J�z���v */
	PREVIOUS_EARNING_CARRIED_FORWARD(1),

	/** ��������� */
	PAY_TAX(2),

	/** �������� */
	RECEIVE_TAX(3),

	/** �ב֍��� */
	EXCHANGE_LOSE(4),

	/** �ב֍��v */
	EXCHANGE_GAIN(5),

	/** �]�ƈ������� */
	EMPLOYEE_PAYMENT(6),

	/** ����������� */
	DEBT_COLLECTION(7),

	/** ���E���Z������ */
	OFFSET_PAYMENT(8),

	/** ���E���Z������ */
	OFFSET_RECEIVABLE(9),

	/** �������ב֍��� */
	UNREALIZED_EXCHANGE_LOSE(10),

	/** �������ב֍��v */
	UNREALIZED_EXCHANGE_GAIN(11);

	/** �Ȗڐ���敪 */
	public int value;

	/**
	 * �R���g���[���N���X
	 *
	 * @param value �Ȗڐ���敪
	 */
	private AutoJornalAccountType(int value) {
		this.value = value;
	}

	/**
	 * �����d��Ȗڐ���敪��Ԃ�
	 *
	 * @param type �����d��Ȗڐ���敪�萔
	 * @return �����d��Ȗڐ���敪
	 */
	public static AutoJornalAccountType get(int type) {
		for (AutoJornalAccountType em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}


	/**
	 * ���̎擾
	 *
	 * @return ����
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * ���̎擾
	 *
	 * @param type �^�C�v
	 * @return ����
	 */
	public static String getName(AutoJornalAccountType type) {
		if (type == null) {
			return "";
		}

		switch (type) {
			case PREVIOUS_EARNING_CARRIED_FORWARD:
				return "C02113";// �O���J�z���v

			case PAY_TAX:
				return "C01626";// ���������

			case RECEIVE_TAX:
				return "C01623";// ��������

			case EXCHANGE_LOSE:
				return "C01782";// �ב֍���

			case EXCHANGE_GAIN:
				return "C01783";// �ב֍��v

			case EMPLOYEE_PAYMENT:
				return "C02107";// �]�ƈ�������

			case DEBT_COLLECTION:
				return "C02121";// �����������

			case OFFSET_PAYMENT:
				return "C02595";// ���E���Z������

			case OFFSET_RECEIVABLE:
				return "C02596";// ���E���Z������

			case UNREALIZED_EXCHANGE_LOSE:
				return "C11221";// �������ב֍���

			case UNREALIZED_EXCHANGE_GAIN:
				return "C11222";// �������ב֍��v

			default:
				return "";
		}
	}
}

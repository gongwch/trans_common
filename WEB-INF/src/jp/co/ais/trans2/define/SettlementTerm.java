package jp.co.ais.trans2.define;

/**
 * ���Z�`�[���͋敪
 * 
 * @author AIS
 */
@SuppressWarnings("unused")
public enum SettlementTerm {
	/** ��N */
	YEAR(0),
	/** ���� */
	HALF(1),
	/** �l���� */
	QUARTER(2),
	/** ���� */
	MONTH(3);

	/** DB�l */
	private int value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value
	 */
	private SettlementTerm(int value) {
		this.value = value;
	}

	/**
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * @param value
	 * @return term
	 */
	public static SettlementTerm getSettlementTerm(int value) {
		for (SettlementTerm term : SettlementTerm.values()) {
			if (term.getValue() == value) {
				return term;
			}
		}
		return null;
	}

	/**
	 * ���Z�`�[���͋敪���̂�Ԃ�
	 * 
	 * @param term ���Z�`�[���͋敪
	 * @return ���Z�`�[���͋敪����
	 */
	public static String getSettlementTermName(SettlementTerm term) {

		if (term == null) {
			return "";
		}

		switch (term) {
			case YEAR:
				return "C11145";// �N��

			case HALF:
				return "C00435";// ����

			case QUARTER:
				return "C10592";// �l����

			case MONTH:
				return "C00147";// ����
			default:
				return "";
		}
	}

}

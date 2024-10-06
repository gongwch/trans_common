package jp.co.ais.trans2.define;

/**
 * �a�����
 * 
 * @author AIS
 */
public enum DepositKind {

	/** 1:���ʗa�� */
	ORDINARY(1),

	/** 2:�����a�� */
	CURRENT(2),

	/** 3:�O�� */
	FOREIGN_CURRENCY(3),

	/** 4:���~ */
	SAVINGS(4),

	/** 5:��� */
	FIXED(5),

	/** 9:���̑� */
	OTHERS(9);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private DepositKind(int value) {
		this.value = value;
	}

	/**
	 * �a����ڂ��擾����
	 * 
	 * @return �a�����
	 */
	public int getDepositKind() {
		return value;
	}

	/**
	 * �a����ڂ�Ԃ�
	 * 
	 * @param depositKind �a�����
	 * @return �a����ږ�
	 */
	public static DepositKind getDepositKind(int depositKind) {
		for (DepositKind em : values()) {
			if (em.value == depositKind) {
				return em;
			}
		}

		return null;
	}

	/**
	 * �a����ږ��̂�Ԃ�
	 * 
	 * @return �a����ږ���
	 */
	public String getDepositKindName() {
		return getDepositKindName(this);
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getDepositKindName(this);
	}

	/**
	 * �a����ږ��̂�Ԃ�
	 * 
	 * @param depositKind �a�����
	 * @return �a����ږ���
	 */
	public static String getDepositKindName(DepositKind depositKind) {

		if (depositKind == null) {
			return "";
		}

		switch (depositKind) {
			case ORDINARY:
				return "C00463"; // ����

			case CURRENT:
				return "C00397"; // ����

			case FOREIGN_CURRENCY:
				return "C00045"; // �O��

			case SAVINGS:
				return "C00368"; // ���~

			case FIXED:
				return "C11057"; // ���

			case OTHERS:
				return "C00338"; // ���̑�

			default:
				return "";
		}
	}

	/**
	 * �a����ږ��̂�Ԃ�
	 * 
	 * @param depositKind �a�����
	 * @return �a����ږ���
	 */
	public static String getDepositKindName(int depositKind) {
		return getDepositKindName(getDepositKind(depositKind));
	}

}

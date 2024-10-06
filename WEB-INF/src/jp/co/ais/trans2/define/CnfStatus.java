package jp.co.ais.trans2.define;

/**
 * �m�F�X�e�[�^�X �A���J�[�J�X�^�}�C�Y
 * 
 * @author AIS
 */
public enum CnfStatus {
	/** �� */
	NOT_YET(0),

	/** �� */
	ALREADY(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CnfStatus(int value) {
		this.value = value;
	}

	/**
	 * �敪��Ԃ�
	 * 
	 * @param value �l
	 * @return �敪
	 */
	public static CnfStatus get(int value) {
		for (CnfStatus em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	/**
	 * int��薼�̂�Ԃ�
	 * 
	 * @param value
	 * @return �m�F�X�e�[�^�X ����
	 */
	public static String getName(int value) {
		for (CnfStatus em : values()) {
			if (em.value == value) {
				return getName(em);
			}
		}
		return null;
	}

	/**
	 * enum��薼�̂�Ԃ�
	 * 
	 * @param cnfStatus
	 * @return �m�F�X�e�[�^�X ����
	 */
	public static String getName(CnfStatus cnfStatus) {

		switch (cnfStatus) {
			case NOT_YET:
				return "C00503"; // ��
			case ALREADY:
				return "C01701"; // ��
			default:
				break;
		}
		return null;
	}

}

package jp.co.ais.trans2.define;

/**
 * �d��C���^�[�t�F�[�X�敪
 */
public enum JornalIfType {

	/** �o�^ */
	ENTRY(0),
	
	/** ���F */
	APPROVE(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private JornalIfType(int value) {
		this.value = value;
	}

	/**
	 * �敪��Ԃ�
	 * 
	 * @param value �l
	 * @return �敪
	 */
	public static JornalIfType get(int value) {
		for (JornalIfType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		
		return null;
	}
}

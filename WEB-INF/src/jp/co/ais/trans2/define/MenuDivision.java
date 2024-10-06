package jp.co.ais.trans2.define;

/**
 * ���j���[�敪
 */
public enum MenuDivision {

	/** 1:���j���[ */
	MENU("1"),
	/** 2:�v���O���� */
	PROGRAM("2");

	/** �ݒ�l */
	public String value;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param value �ݒ�l
	 */
	private MenuDivision(String value) {
		this.value = value;
	}

	/**
	 * ���Z�敪��Ԃ�
	 * 
	 * @param value �l
	 * @return ���j���[�敪
	 */
	public static MenuDivision get(String value) {
		for (MenuDivision em : values()) {
			if (em.value.equals(value)) {
				return em;
			}
		}

		return null;
	}
}
package jp.co.ais.trans2.define;


/**
 * �o���S���ҋ敪
 */
public enum UserAccountRole {
	
	/** �o���S���� */
	ACCOUNT(1),
	
	/** �o���S���҈ȊO */
	OTHER(0);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private UserAccountRole(int value) {
		this.value = value;
	}

	/**
	 * �o���S���ҋ敪��Ԃ�
	 * 
	 * @param value �l
	 * @return �o���S���ҋ敪
	 */
	public static UserAccountRole get(int value) {
		for (UserAccountRole em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		
		return null;
	}

	/**
	 * �o���S���ҋ敪���̂�Ԃ�
	 * 
	 * @param userAccountRole �o���S���ҋ敪
	 * @return �o���S���ҋ敪���̂�Ԃ�
	 */
	public static String getUserAccountRoleName(UserAccountRole userAccountRole) {

		if (userAccountRole == null) {
			return null;
		}

		switch (userAccountRole) {
			case ACCOUNT:
				return "C01050";//�o���S����

			case OTHER:
				return "C00140";//�o���S���҈ȊO

			default:
				return null;

		}
	}

}

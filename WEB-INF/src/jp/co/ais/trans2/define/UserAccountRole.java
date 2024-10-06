package jp.co.ais.trans2.define;


/**
 * 経理担当者区分
 */
public enum UserAccountRole {
	
	/** 経理担当者 */
	ACCOUNT(1),
	
	/** 経理担当者以外 */
	OTHER(0);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private UserAccountRole(int value) {
		this.value = value;
	}

	/**
	 * 経理担当者区分を返す
	 * 
	 * @param value 値
	 * @return 経理担当者区分
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
	 * 経理担当者区分名称を返す
	 * 
	 * @param userAccountRole 経理担当者区分
	 * @return 経理担当者区分名称を返す
	 */
	public static String getUserAccountRoleName(UserAccountRole userAccountRole) {

		if (userAccountRole == null) {
			return null;
		}

		switch (userAccountRole) {
			case ACCOUNT:
				return "C01050";//経理担当者

			case OTHER:
				return "C00140";//経理担当者以外

			default:
				return null;

		}
	}

}

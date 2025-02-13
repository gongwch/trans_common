package jp.co.ais.trans2.define;

/**
 * 伝票に対する権限
 * 
 * @author AIS
 */
public enum SlipRole {
	/** 全て */
	ALL(0),
	
	/** 入力者 */
	SELF(1),
	
	/** 所属部門 */
	DEPARTMENT(2);

	/** 値 */
	public int value;

	private SlipRole(int value) {
		this.value = value;
	}

	/**
	 * 伝票に対する権限を返す
	 * 
	 * @param slipRole 伝票に対する権限
	 * @return 伝票に対する権限
	 */
	public static SlipRole getSlipRole(int slipRole) {

		for (SlipRole em : values()) {
			if (em.value == slipRole) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 伝票に対する権限名称を返す
	 * 
	 * @param slipRole 伝票に対する権限
	 * @return 伝票に対する権限名称
	 */
	public static String getSlipRoleName(SlipRole slipRole) {

		if (slipRole == null) {
			return null;
		}

		switch (slipRole) {
			case ALL:
				// 全て
				return "C00310";

			case SELF:
				// 入力者
				return "C01278";
				
			case DEPARTMENT:
				// 所属部門
				return "C00295";

			default:
				return null;

		}
	}

}

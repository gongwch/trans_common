package jp.co.ais.trans2.define;

/**
 * メニュー区分
 */
public enum MenuDivision {

	/** 1:メニュー */
	MENU("1"),
	/** 2:プログラム */
	PROGRAM("2");

	/** 設定値 */
	public String value;

	/**
	 * コンストラクター
	 * 
	 * @param value 設定値
	 */
	private MenuDivision(String value) {
		this.value = value;
	}

	/**
	 * 換算区分を返す
	 * 
	 * @param value 値
	 * @return メニュー区分
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
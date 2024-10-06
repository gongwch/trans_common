package jp.co.ais.trans2.define;

/**
 * 仕訳インターフェース区分
 */
public enum JornalIfType {

	/** 登録 */
	ENTRY(0),
	
	/** 承認 */
	APPROVE(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private JornalIfType(int value) {
		this.value = value;
	}

	/**
	 * 区分を返す
	 * 
	 * @param value 値
	 * @return 区分
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

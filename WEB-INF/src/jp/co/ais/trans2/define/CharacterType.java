package jp.co.ais.trans2.define;

/**
 * 文字コード
 * 
 * @author AIS
 */
public enum CharacterType  {
	
	/** 1:JIS */
	JIS(0),

	/** 1:EBCDIC */
	EBCDIC(1);
	
	
	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CharacterType(int value) {
		this.value = value;
	}

	/**
	 * 文字コードを取得する
	 * 
	 * @return 文字コード
	 */
	public int getDepositKind() {
		return value;
	}
	
	/**
	 * 文字コードを返す
	 * 
	 * @param characterType 文字コード
	 * @return 文字コード名
	 */
	public static CharacterType getCharacterType(int characterType) {
		for (CharacterType em : values()) {
			if (em.value == characterType) {
				return em;
			}
		}
		
		return null;
	}
	


}

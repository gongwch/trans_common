package jp.co.ais.trans2.define;

/**
 * �����R�[�h
 * 
 * @author AIS
 */
public enum CharacterType  {
	
	/** 1:JIS */
	JIS(0),

	/** 1:EBCDIC */
	EBCDIC(1);
	
	
	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CharacterType(int value) {
		this.value = value;
	}

	/**
	 * �����R�[�h���擾����
	 * 
	 * @return �����R�[�h
	 */
	public int getDepositKind() {
		return value;
	}
	
	/**
	 * �����R�[�h��Ԃ�
	 * 
	 * @param characterType �����R�[�h
	 * @return �����R�[�h��
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

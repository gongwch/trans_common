package jp.co.ais.trans2.define;

/**
 * �ʉ݃^�C�v
 * 
 * @author AIS
 */
public enum CurrencyType {
	
	/** ��ʉ� */
	KEY(1) {
		@Override
		public  boolean isKey() {
			return true;
		}
	},
	
	/** �@�\�ʉ� */
	FUNCTIONAL(2){
		@Override
		public boolean isKey() {
			return false;
		}
	};

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CurrencyType(int value) {
		this.value = value;
	}
	
	/**
	 * �^�C�v�擾
	 * 
	 * @param type ���l
	 * @return �^�C�v
	 */
	public static CurrencyType get(int type) {
		for (CurrencyType em : values()) {
			if (em.value == type) {
				return em;
			}
		}
		return null;
	}

	/**
	 * �ʉݖ��̂�Ԃ�
	 * 
	 * @param currencyType �ʉ݃^�C�v
	 * @return �ʉݖ���
	 */
	public static String getCurrencyTypeName(CurrencyType currencyType) {
		
		switch (currencyType) {
			case KEY:
				return "C11083"; // "�����ʉ�"
			case FUNCTIONAL:
				return "C11084"; // "�@�\�ʉ�"
			default:
				return null;
		}
	}
	
	/**
	 * @return ��ʉ݁Fture �@�\�ʉ݁Ffalse
	 */
	public abstract boolean isKey();

}

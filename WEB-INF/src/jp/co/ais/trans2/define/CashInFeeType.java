package jp.co.ais.trans2.define;

/**
 * �����萔���敪
 */
public enum CashInFeeType {

	/** ���Е��S */
	Our(1),

	/** ������S */
	Other(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CashInFeeType(int value) {
		this.value = value;
	}

	/**
	 * �^�C�v�擾
	 * 
	 * @param cashInFeeType �l
	 * @return �^�C�v
	 */
	public static CashInFeeType getCashInFeeType(int cashInFeeType) {
		for (CashInFeeType em : values()) {
			if (em.value == cashInFeeType) {
				return em;
			}
		}
		return null;
	}

	/**
	 * ���̎擾
	 * 
	 * @return ����ID
	 */
	public String getName() {
		return getCashInFeeTypeName(this);
	}

	/**
	 * ���̎擾
	 * 
	 * @param cashInFeeType �^�C�v
	 * @return ����
	 */
	public static String getCashInFeeTypeName(CashInFeeType cashInFeeType) {
		if (cashInFeeType == null) {
			return "";
		}

		return getCashInFeeTypeName(cashInFeeType.value);
	}

	/**
	 * ���̎擾
	 * 
	 * @param cashInFeeType �l
	 * @return ����
	 */
	public static String getCashInFeeTypeName(int cashInFeeType) {

		if (Our.value == cashInFeeType) {
			return "C00399"; // ���Е��S

		} else if (Other.value == cashInFeeType) {
			return "C00327"; // ������S
		}
		return null;

	}

}

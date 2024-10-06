package jp.co.ais.trans2.model.term;

/**
 * �������Z
 */
public enum Half {

	/** �㔼�� */
	FisrtHalf(6),
	/** ������ */
	SecondHalf(12);

	/**  */
	public int maxTukido;

	/**
	 * @param maxTukido
	 */
	Half(int maxTukido) {
		this.maxTukido = maxTukido;

	}

	/**
	 * @param tukido
	 * @return ��v��
	 */
	public static Enum getAccountTerm(int tukido) {

		for (Half one : values()) {
			if (tukido <= one.maxTukido) {
				return one;
			}
		}
		return null;
	}
}

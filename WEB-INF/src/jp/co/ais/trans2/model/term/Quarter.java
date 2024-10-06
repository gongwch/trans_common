package jp.co.ais.trans2.model.term;

/**
 * �l�������Z
 */
public enum Quarter {

	/** ���l���� */
	FirstQuarter(3),
	/** ���l���� */
	SecondQuarter(6),
	/** ��O�l���� */
	ThirdQuarter(9),
	/** ��l�l���� */
	FourthQuarter(12);

	/**  */
	public int maxTukido;

	/**
	 * @param maxTukido
	 */
	Quarter(int maxTukido) {
		this.maxTukido = maxTukido;
	}

	/**
	 * @param tukido
	 * @return ��v��
	 */
	public static Enum getAccountTerm(int tukido) {

		for (Quarter one : values()) {
			if (tukido <= one.maxTukido) {
				return one;
			}
		}
		return null;
	}

}

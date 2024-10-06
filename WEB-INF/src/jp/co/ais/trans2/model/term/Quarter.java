package jp.co.ais.trans2.model.term;

/**
 * 四半期決算
 */
public enum Quarter {

	/** 第一四半期 */
	FirstQuarter(3),
	/** 第二四半期 */
	SecondQuarter(6),
	/** 第三四半期 */
	ThirdQuarter(9),
	/** 第四四半期 */
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
	 * @return 会計期
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

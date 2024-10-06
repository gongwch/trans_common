package jp.co.ais.trans2.model.term;

/**
 * ”¼ŠúŒˆZ
 */
public enum Half {

	/** ã”¼Šú */
	FisrtHalf(6),
	/** ‰º”¼Šú */
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
	 * @return ‰ïŒvŠú
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

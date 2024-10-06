package jp.co.ais.trans2.model.term;

/**
 * 年次決算
 */
public enum Year {

	/** 当年度 */
	YEAR(12);

	/**  */
	public int maxTukido;

	/**
	 * @param maxTukido
	 */
	Year(int maxTukido) {
		this.maxTukido = maxTukido;
	}

	/**
	 * @return 会計期
	 */
	public static Enum getAccountTerm() {

		return YEAR;
	}

}

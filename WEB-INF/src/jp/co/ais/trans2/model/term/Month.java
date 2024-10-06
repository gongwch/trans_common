package jp.co.ais.trans2.model.term;

/**
 * 月次決算
 */
public enum Month {
	/** 1月度 */
	_1st(1),
	/** 2月度 */
	_2nd(2),
	/** 3月度 */
	_3rd(3),
	/** 4月度 */
	_4th(4),
	/** 5月度 */
	_5th(5),
	/** 6月度 */
	_6th(6),
	/** 7月度 */
	_7th(7),
	/** 8月度 */
	_8th(8),
	/** 9月度 */
	_9th(9),
	/** 10月度 */
	_10th(10),
	/** 11月度 */
	_11th(11),
	/** 12月度 */
	_12th(12);

	/**  */
	public int maxTukido;

	/**
	 * @param maxTukido
	 */
	Month(int maxTukido) {
		this.maxTukido = maxTukido;
	}

	/**
	 * @param tukido
	 * @return 会計期
	 */
	public static Enum getAccountTerm(int tukido) {

		for (Month one : values()) {
			if (tukido <= one.maxTukido) {
				return one;
			}
		}
		return null;
	}
}

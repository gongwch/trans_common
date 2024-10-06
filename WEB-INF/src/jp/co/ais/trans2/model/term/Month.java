package jp.co.ais.trans2.model.term;

/**
 * �������Z
 */
public enum Month {
	/** 1���x */
	_1st(1),
	/** 2���x */
	_2nd(2),
	/** 3���x */
	_3rd(3),
	/** 4���x */
	_4th(4),
	/** 5���x */
	_5th(5),
	/** 6���x */
	_6th(6),
	/** 7���x */
	_7th(7),
	/** 8���x */
	_8th(8),
	/** 9���x */
	_9th(9),
	/** 10���x */
	_10th(10),
	/** 11���x */
	_11th(11),
	/** 12���x */
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
	 * @return ��v��
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

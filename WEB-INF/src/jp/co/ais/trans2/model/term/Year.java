package jp.co.ais.trans2.model.term;

/**
 * �N�����Z
 */
public enum Year {

	/** ���N�x */
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
	 * @return ��v��
	 */
	public static Enum getAccountTerm() {

		return YEAR;
	}

}

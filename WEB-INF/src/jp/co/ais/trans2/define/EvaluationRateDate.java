package jp.co.ais.trans2.define;

/**
 * 評価替のレート適用日
 * @author AIS
 *
 */
public enum EvaluationRateDate {
	/** 当月末日 */
	LAST_DATE(0),
	/** 翌月月初 */
	FIRST_DATE(1);

		/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private EvaluationRateDate(int value) {
		this.value = value;
	}

	/**
	 * 評価替のレート適用日を返す
	 * 
	 * @param evaluationRateDate 値
	 * @return 評価替のレート適用日
	 */
	public static EvaluationRateDate getEvaluationRateDate(int evaluationRateDate) {
		for (EvaluationRateDate em : values()) {
			if (em.value == evaluationRateDate) {
				return em;
			}
		}
		return null;
	}

	
	/**
	 *
	 * @return value
	 */
	public int getValue() {
		return value;
	}

	
	/**
	 *
	 * @param value
	 */
	public void setValue(int value) {
		this.value = value;
	}

}

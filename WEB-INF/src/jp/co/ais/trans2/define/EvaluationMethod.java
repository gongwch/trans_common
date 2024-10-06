package jp.co.ais.trans2.define;

/**
 * 評価替方式
 * 
 * @author AIS
 */
public enum EvaluationMethod {

	/** しない */
	NOT_ISSUE(0),

	/** 洗い替 */
	CANCEL(1),

	/** 切り離し */
	NOT_CANCEL(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private EvaluationMethod(int value) {
		this.value = value;
	}

	/**
	 * 評価替方式を返す
	 * 
	 * @param evaluationMethod 値
	 * @return 評価替方式
	 */
	public static EvaluationMethod getEvaluationMethod(int evaluationMethod) {
		for (EvaluationMethod em : values()) {
			if (em.value == evaluationMethod) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 評価替対象フラグを返す
	 * 
	 * @param eva 評価替対象フラグ
	 * @return 評価替対象フラグ
	 */
	public static String getName(EvaluationMethod eva) {

		if (eva == null) {
			return "";
		}

		switch (eva) {
			case NOT_ISSUE:
				return "C02099";//しない

			case CANCEL:
				return "C02123";//洗替法

			case NOT_CANCEL:
				return "C02124";//切離法

			default:
				return "";
		}
	}
}

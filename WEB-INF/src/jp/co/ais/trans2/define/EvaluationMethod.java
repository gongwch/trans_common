package jp.co.ais.trans2.define;

/**
 * �]���֕���
 * 
 * @author AIS
 */
public enum EvaluationMethod {

	/** ���Ȃ� */
	NOT_ISSUE(0),

	/** �􂢑� */
	CANCEL(1),

	/** �؂藣�� */
	NOT_CANCEL(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private EvaluationMethod(int value) {
		this.value = value;
	}

	/**
	 * �]���֕�����Ԃ�
	 * 
	 * @param evaluationMethod �l
	 * @return �]���֕���
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
	 * �]���֑Ώۃt���O��Ԃ�
	 * 
	 * @param eva �]���֑Ώۃt���O
	 * @return �]���֑Ώۃt���O
	 */
	public static String getName(EvaluationMethod eva) {

		if (eva == null) {
			return "";
		}

		switch (eva) {
			case NOT_ISSUE:
				return "C02099";//���Ȃ�

			case CANCEL:
				return "C02123";//���֖@

			case NOT_CANCEL:
				return "C02124";//�ؗ��@

			default:
				return "";
		}
	}
}

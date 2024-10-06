package jp.co.ais.trans2.define;

/**
 * �]���ւ̃��[�g�K�p��
 * @author AIS
 *
 */
public enum EvaluationRateDate {
	/** �������� */
	LAST_DATE(0),
	/** �������� */
	FIRST_DATE(1);

		/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private EvaluationRateDate(int value) {
		this.value = value;
	}

	/**
	 * �]���ւ̃��[�g�K�p����Ԃ�
	 * 
	 * @param evaluationRateDate �l
	 * @return �]���ւ̃��[�g�K�p��
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

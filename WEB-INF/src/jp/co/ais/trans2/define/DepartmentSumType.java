package jp.co.ais.trans2.define;

/**
 * ����敪
 * @author AIS
 *
 */
public enum DepartmentSumType {
	/** ���� */
	INPUT(0),

	/** �W�v */
	SUM(1);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private DepartmentSumType(int value) {
		this.value = value;
	}

	/**
	 * �敪��Ԃ�
	 * 
	 * @param value �l
	 * @return �敪
	 */
	public static DepartmentSumType get(int value) {
		for (DepartmentSumType em : values()) {
			if (em.value == value) {
				return em;
			}
		}
		return null;
	}

	 /**
	  * �敪���̂�Ԃ�
	  * 
	  * @param sumType �敪
	  * @return �敪����
	  */
	 public static String getName(DepartmentSumType sumType) {

		 switch (sumType) {
			 case INPUT:
				 return "C01275";//����
			 case SUM:
				 return "C00255";//�W�v
			 default:
				 return null;
		 }
	 }
}

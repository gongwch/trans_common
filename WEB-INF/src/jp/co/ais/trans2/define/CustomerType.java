package jp.co.ais.trans2.define;

import jp.co.ais.trans2.define.*;

/**
 * �����敪
 */
public enum CustomerType {

	/** ���͕s�� */
	NONE(0),

	/** ���Ӑ� */
	CUSTOMER(2),

	/** �d���� */
	VENDOR(3),

	/** ���Ӑ恕�d���� */
	BOTH(4);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private CustomerType(int value) {
		this.value = value;
	}

	/**
	 * �����敪���擾����
	 * 
	 * @return �����敪
	 */
	public int getValue() {
		return value;
	}

	/**
	 * �����敪��Ԃ�
	 * 
	 * @param type �^�C�v
	 * @return �����敪
	 */
	public static CustomerType get(int type) {
		for (CustomerType em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}
	
	/**
	 * @param customerType
	 * @return ���Ӑ�敪����
	 */
	public static String getCustomerName(CustomerType customerType) {

		if (CustomerType.BOTH == customerType || CustomerType.CUSTOMER == customerType) {
			return "C00401"; // ���Ӑ�
		}

		return "C01296"; // �񓾈Ӑ�
	}

	/**
	 * @param customerType
	 * @return �d����敪����
	 */
	public static String getVenderName(CustomerType customerType) {

		if (CustomerType.BOTH == customerType || CustomerType.VENDOR == customerType) {
			return "C00203"; // �d����
		}

		return "C01295"; // ��d����
	}
	
	/**
	 * 
	 * 
	 * @param customerType
	 * @return customerType
	 */
	public static String getName(CustomerType customerType) {
		
		if (customerType == null) {
			return "";
		}
		
		switch (customerType) {
			case NONE:
				return "C01279";//���͕s��
			case CUSTOMER:
				return "C00401";//���Ӑ�
			case VENDOR:
				return "C00203";//�d����
			case BOTH:
				return "C02122";//���Ӑ恕�d����
			default:
				return "";
		}
		
	}

}

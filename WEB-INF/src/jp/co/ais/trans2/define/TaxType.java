package jp.co.ais.trans2.define;

/**
 * ����d���敪
 * 
 * @author AIS
 */
public enum TaxType {
	
	/** �ΏۊO */
	NOT(0),
	
	/** ���� */
	SALES(1),
	
	/** �d�� */
	PURCHAESE(2);

	/** �l */
	public int value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private TaxType(int value) {
		this.value = value;
	}

	/**
	 * ����d���敪��Ԃ�
	 * 
	 * @param type �`�[�̏��
	 * @return �X�V�敪��
	 */
	public static TaxType get(int type) {
		for (TaxType em : values()) {
			if (em.value == type) {
				return em;
			}
		}
		
		return null;
	}
	
	/**
	 * @param TaxType
	 * @return �ȖڏW�v�敪���� �ǉ����܂���
	 */
	public static String getName(TaxType taxType){

		switch (taxType) {
			case NOT:
				return "C02103";//�ΏۊO
			case SALES:
				return "C00026";//����
			case PURCHAESE:
				return "C00201";//�d�� 
			default:
				break;
		}
		
		return null;
	}
	/**
	 * �f�[�^�敪��enum��Ԃ�
	 * 
	 * @param dataDiv
	 * @return �f�[�^�敪���́@�ǉ�
	 */
	public static TaxType getTaxType(String taxType) {

		if (taxType == null) {
			return null;
		}

		if ("0".equals(taxType)) {
			return NOT;
		} else if ("1".equals(taxType)) {
			return SALES;
		} else if ("2".equals(taxType)) {
			return PURCHAESE;
		} 
		return null;
	}

}

package jp.co.ais.trans2.define;

/**
 * ���� �Ɩ����
 * 
 * @author AIS
 */
public enum ReceiptType  {
	
	/** 01�F�U���������� */
	TRANSFER("01"),

	/** 03�F���o��������� */
	CASH_INOUT("03");
	
	
	/** �l */
	public String value;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param value �l
	 */
	private ReceiptType(String value) {
		this.value = value;
	}

	/**
	 * �a����ڂ��擾����
	 * 
	 * @return �a�����
	 */
	public String getDepositKind() {
		return value;
	}
	
	/**
	 * �a����ڂ�Ԃ�
	 * 
	 * @param receiptType �a�����
	 * @return �a����ږ�
	 */
	public static ReceiptType getReceiptType(String receiptType) {
		for (ReceiptType em : values()) {
			if (em.value == receiptType) {
				return em;
			}
		}
		
		return null;
	}
	



}

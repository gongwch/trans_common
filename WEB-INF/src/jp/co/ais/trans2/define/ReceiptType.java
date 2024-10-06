package jp.co.ais.trans2.define;

/**
 * 入金 業務種別
 * 
 * @author AIS
 */
public enum ReceiptType  {
	
	/** 01：振込入金明細 */
	TRANSFER("01"),

	/** 03：入出金取引明細 */
	CASH_INOUT("03");
	
	
	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ReceiptType(String value) {
		this.value = value;
	}

	/**
	 * 預金種目を取得する
	 * 
	 * @return 預金種目
	 */
	public String getDepositKind() {
		return value;
	}
	
	/**
	 * 預金種目を返す
	 * 
	 * @param receiptType 預金種目
	 * @return 預金種目名
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

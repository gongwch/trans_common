package jp.co.ais.trans2.define;

import jp.co.ais.trans2.define.*;

/**
 * 取引先区分
 */
public enum CustomerType {

	/** 入力不可 */
	NONE(0),

	/** 得意先 */
	CUSTOMER(2),

	/** 仕入先 */
	VENDOR(3),

	/** 得意先＆仕入先 */
	BOTH(4);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CustomerType(int value) {
		this.value = value;
	}

	/**
	 * 取引先区分を取得する
	 * 
	 * @return 取引先区分
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 取引先区分を返す
	 * 
	 * @param type タイプ
	 * @return 取引先区分
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
	 * @return 得意先区分名称
	 */
	public static String getCustomerName(CustomerType customerType) {

		if (CustomerType.BOTH == customerType || CustomerType.CUSTOMER == customerType) {
			return "C00401"; // 得意先
		}

		return "C01296"; // 非得意先
	}

	/**
	 * @param customerType
	 * @return 仕入先区分名称
	 */
	public static String getVenderName(CustomerType customerType) {

		if (CustomerType.BOTH == customerType || CustomerType.VENDOR == customerType) {
			return "C00203"; // 仕入先
		}

		return "C01295"; // 非仕入先
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
				return "C01279";//入力不可
			case CUSTOMER:
				return "C00401";//得意先
			case VENDOR:
				return "C00203";//仕入先
			case BOTH:
				return "C02122";//得意先＆仕入先
			default:
				return "";
		}
		
	}

}

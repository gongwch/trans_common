package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * 手数料支払区分
 */
public enum CommissionPaymentType {

	/** 受取人 */
	Receiver(1),

	/** 支払人 */
	Payer(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private CommissionPaymentType(int value) {
		this.value = value;
	}

	/**
	 * タイプ取得
	 * 
	 * @param commissionPaymentType 値
	 * @return タイプ
	 */
	public static CommissionPaymentType get(int commissionPaymentType) {
		for (CommissionPaymentType em : values()) {
			if (em.value == commissionPaymentType) {
				return em;
			}
		}
		return null;
	}

	/**
	 * @return 値
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}

	/**
	 * 名称を返す
	 * 
	 * @return 名称ID
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称取得
	 * 
	 * @param commissionPaymentType 値
	 * @return 名称
	 */
	public static String getName(int commissionPaymentType) {
		return getName(get(commissionPaymentType));
	}

	/**
	 * 名称取得
	 * 
	 * @param commissionPaymentType タイプ
	 * @return 名称
	 */
	public static String getName(CommissionPaymentType commissionPaymentType) {
		if (commissionPaymentType == null) {
			return "";
		}

		switch (commissionPaymentType) {
			case Receiver:
				return "C00021"; // 受取人

			case Payer:
				return "C02319"; // 支払人

			default:
				return "";
		}
	}
}

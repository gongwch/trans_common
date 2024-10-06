package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * AR科目制御区分コンボボックス項目定義
 */
public enum ARType {

	/** 通常 */
	NOMAl("00"),

	/** 債権管理科目 */
	AR("01"),

	/** 債権回収仮勘定 */
	AR_COLLECT("02");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ARType(String value) {
		this.value = value;
	}

	/**
	 * AR科目制御区分を返す
	 * 
	 * @param ar AR科目制御区分
	 * @return 値
	 */
	public static ARType get(String ar) {
		for (ARType em : values()) {
			if (em.value.equals(ar)) {
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
	 * AR科目制御区分を返す
	 * 
	 * @param ar AR科目制御区分
	 * @return AR科目制御区分
	 */
	public static String getName(ARType ar) {

		if (ar == null) {
			return "";
		}

		switch (ar) {
			case NOMAl:
				return "C00372"; // 通常

			case AR:
				return "C02120"; // 債権管理科目

			case AR_COLLECT:
				return "C02121"; // 債権回収仮勘定

			default:
				return "";
		}
	}
}
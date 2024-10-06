package jp.co.ais.trans2.model.item;

import jp.co.ais.trans.common.util.*;

/**
 * GL科目制御区分コンボボックス項目定義
 */
public enum GLType {

	/** 通常 */
	NOMAL("00"),

	/** 資金科目 */
	FUND("04"),

	/** 売上科目 */
	SALES("05"),

	/** 仮勘定 */
	TEMPORARY("07");

	/** 値 */
	public String value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private GLType(String value) {
		this.value = value;
	}

	/**
	 * GL科目制御区分を返す
	 * 
	 * @param gl GL科目制御区分
	 * @return 値
	 */
	public static GLType getGl(String gl) {
		for (GLType em : values()) {
			if (em.value.equals(gl)) {
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
	 * GL科目制御区分を返す
	 * 
	 * @param gl GL科目制御区分
	 * @return GL科目制御区分
	 */
	public static String getName(GLType gl) {

		if (gl == null) {
			return "";
		}

		switch (gl) {
			case NOMAL:
				return "C00372"; // 通常

			case FUND:
				return "C02114"; // 資金科目

			case SALES:
				return "C02115"; // 売上科目

			case TEMPORARY:
				return "C02117"; // 仮勘定

			default:
				return "";
		}
	}
}
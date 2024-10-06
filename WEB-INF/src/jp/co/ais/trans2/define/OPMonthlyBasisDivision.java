package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * OP Item Monthly Basis Division
 */
public enum OPMonthlyBasisDivision implements TEnumRadio {

	/** 0:日割基準 **/
	APPORTION(0),

	/** 1:完了基準 **/
	COMPLETE(1),

	/** 2:発生日基準 **/
	ACCRUAL(2);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	OPMonthlyBasisDivision(int value) {
		this.value = value;
	}

	/**
	 * Enumを返す
	 * 
	 * @param value
	 * @return Enum
	 */
	public static OPMonthlyBasisDivision get(int value) {
		for (OPMonthlyBasisDivision em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return null;
	}

	public String getName() {
		return getName(this);
	}

	/**
	 * 名称を返す
	 * 
	 * @param div
	 * @return 名称
	 */
	public static String getName(OPMonthlyBasisDivision div) {

		if (div == null) {
			return "";
		}

		switch (div) {
			case COMPLETE:
				return "完了基準";// TODO 多言語対応
			case ACCRUAL:
				return "発生日基準";
			case APPORTION:
				return "日割基準";
		}

		return "";
	}

	/**
	 * 名称を返す
	 * 
	 * @param value
	 * @return 名称
	 */
	public static String getName(int value) {

		OPMonthlyBasisDivision div = get(value);
		if (div == null) {
			return "";
		}

		return getName(div);
	}
}

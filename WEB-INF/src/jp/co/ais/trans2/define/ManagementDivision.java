package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.util.*;

/**
 * 管理コントロール項目定義
 */
public enum ManagementDivision {

	/** なし */
	None(0),

	/** 部門 */
	Department(1),

	/** 管理1 */
	Management1(2),

	/** 管理2 */
	Management2(3),

	/** 管理3 */
	Management3(4),

	/** 管理4 */
	Management4(5),

	/** 管理5 */
	Management5(6),

	/** 管理6 */
	Management6(7);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ManagementDivision(int value) {
		this.value = value;
	}

	/**
	 * 管理コントロールを取得する
	 * 
	 * @param division
	 * @return 値
	 */
	public static ManagementDivision getDivision(int division) {
		for (ManagementDivision em : values()) {
			if (em.value == division) {
				return em;
			}
		}
		return null;
	}

	/**
	 * 管理コントロールを取得する
	 * @return 値
	 */
	public String getValue() {
		return Util.avoidNull(value);
	}
}
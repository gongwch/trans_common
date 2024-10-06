package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * B/L 自動設定項目
 */
public enum BLNoAdopt implements TEnumRadio {

	/** 0:なし */
	NONE(0),

	/** 1:会社コード */
	CODE(1),

	/** 2:部門 */
	DEPARTMENT(2),

	/** 3:年月(YYMM) */
	YYMM_DATE(3),

	/** 4:年月(YYYYMM) */
	YYYYMM_DATE(4),

	/** 5:日付(YYYYMMDD) */
	YYYYMMDD_DATE(5);

	/** 値 */
	public int value;

	/**
	 * コンストラクター
	 * 
	 * @param value 値
	 */
	BLNoAdopt(int value) {
		this.value = value;
	}

	/**
	 * 名称取得
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称取得.
	 * 
	 * @param type
	 * @return name
	 */
	public static String getName(BLNoAdopt type) {
		if (type == null) {
			return "";
		}
		switch (type) {
			case NONE:
				// なし
				return "C00412";
			case CODE:
				// 会社コード
				return "C00596";
			case DEPARTMENT:
				// 部門
				return "C00467";
			case YYMM_DATE:
				// 年月(YYMM)
				return "C02164";
			case YYYYMM_DATE:
				// 年月(YYYYMM)
				return "C02165";
			case YYYYMMDD_DATE:
				// 日付(YYYYMMDD)
				return "CBL837";
			default:
				return "";
		}
	}

	/**
	 * Enumを返す
	 * 
	 * @param value
	 * @return Enum
	 */
	public static BLNoAdopt get(int value) {
		for (BLNoAdopt em : values()) {
			if (em.value == value) {
				return em;
			}
		}

		return BLNoAdopt.NONE;
	}
}

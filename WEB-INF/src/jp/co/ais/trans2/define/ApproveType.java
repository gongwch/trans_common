package jp.co.ais.trans2.define;

import jp.co.ais.trans2.common.gui.*;

/**
 * 承認タイプ
 */
public enum ApproveType implements TEnumRadio {

	/** 通常 */
	REGULAR(0),
	/** ワークフロー承認 */
	WORKFLOW(1);

	/** DB値 */
	private int value;

	/**
	 * コンストラクタ
	 * 
	 * @param value DB値
	 */
	private ApproveType(int value) {
		this.value = value;
	}

	/**
	 * 名称を取得
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 名称を取得
	 * 
	 * @param type
	 * @return 名称
	 */
	public static String getName(ApproveType type) {
		if (type == null) {
			return null;
		}
		switch (type) {
			case REGULAR:
				return "通常";
			case WORKFLOW:
				return "ワークフロー承認";
		}
		return null;
	}

	/**
	 * DB値を取得
	 * 
	 * @return DB値
	 */
	public int getDBValue() {
		return this.value;
	}

	/**
	 * int値に対応するEnum値取得
	 * 
	 * @param val
	 * @return Enum値
	 */
	public static ApproveType get(int val) {
		for (ApproveType t : values()) {
			if (t.getDBValue() == val) {
				return t;
			}
		}
		return null;
	}
}

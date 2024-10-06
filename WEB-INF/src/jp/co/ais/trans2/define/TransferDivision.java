package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * 付替区分
 * 
 * @author AIS
 */
public enum TransferDivision {

	/** 通常 */
	NORMAL(0),

	/** 付替 */
	TRANSFER(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private TransferDivision(int value) {
		this.value = value;
	}

	/**
	 * 付替区分を返す
	 * 
	 * @param type 付替区分
	 * @return 付替区分
	 */
	public static TransferDivision get(int type) {
		for (TransferDivision em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 付替区分名を返す
	 * 
	 * @return 付替区分名
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 付替区分名を返す
	 * 
	 * @param type 付替区分
	 * @return 付替区分名
	 */
	public static String getName(TransferDivision type) {

		switch (type) {
			case NORMAL: // 通常
				return "C00372";

			case TRANSFER: // 付替
				return "C00375";

			default:
				return null;
		}
	}

	/**
	 * 付替区分のEnum名称取得<br>
	 * コード：名称、コード：名称形式
	 * 
	 * @param lang 言語コード
	 * @return 付替区分のEnum名称
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (TransferDivision type : TransferDivision.values()) {
			sb.append(type.value);
			sb.append("：");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("、");
		}

		return sb.substring(0, sb.length() - 1);

	}

}

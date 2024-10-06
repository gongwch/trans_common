package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * 保留区分
 * 
 * @author AIS
 */
public enum ReservationDivsion {

	/** 保留しない */
	NORMAL(0),

	/** 保留する */
	RESERVATION(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private ReservationDivsion(int value) {
		this.value = value;
	}

	/**
	 * 保留区分を返す
	 * 
	 * @param type 保留区分
	 * @return 保留区分
	 */
	public static ReservationDivsion get(int type) {
		for (ReservationDivsion em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 保留区分名を返す
	 * 
	 * @return 保留区分名
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 保留区分名を返す
	 * 
	 * @param type 保留区分
	 * @return 保留区分名
	 */
	public static String getName(ReservationDivsion type) {

		switch (type) {
			case NORMAL: // 保留しない
				return "C01981";

			case RESERVATION: // 保留する
				return "C01982";

			default:
				return null;
		}
	}

	/**
	 * 保留区分のEnum名称取得<br>
	 * コード：名称、コード：名称形式
	 * 
	 * @param lang 言語コード
	 * @return 保留区分のEnum名称
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (ReservationDivsion type : ReservationDivsion.values()) {
			sb.append(type.value);
			sb.append("：");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("、");
		}

		return sb.substring(0, sb.length() - 1);

	}
}

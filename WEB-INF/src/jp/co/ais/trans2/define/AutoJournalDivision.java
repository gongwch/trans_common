package jp.co.ais.trans2.define;

import jp.co.ais.trans.common.message.*;

/**
 * 自動仕訳区分
 * 
 * @author AIS
 */
public enum AutoJournalDivision {

	/** 通常 */
	NORMAL(0),

	/** 自動 */
	AUTO(1);

	/** 値 */
	public int value;

	/**
	 * コンストラクタ.
	 * 
	 * @param value 値
	 */
	private AutoJournalDivision(int value) {
		this.value = value;
	}

	/**
	 * 自動仕訳区分を返す
	 * 
	 * @param type 自動仕訳区分
	 * @return 自動仕訳区分
	 */
	public static AutoJournalDivision get(int type) {
		for (AutoJournalDivision em : values()) {
			if (em.value == type) {
				return em;
			}
		}

		return null;
	}

	/**
	 * 自動仕訳区分名を返す
	 * 
	 * @return 自動仕訳区分名
	 */
	public String getName() {
		return getName(this);
	}

	/**
	 * 自動仕訳区分名を返す
	 * 
	 * @param type 自動仕訳区分
	 * @return 自動仕訳区分名
	 */
	public static String getName(AutoJournalDivision type) {

		switch (type) {
			case NORMAL: // 通常
				return "C00372";

			case AUTO: // 自動
				return "C01107";

			default:
				return null;
		}
	}

	/**
	 * 自動仕訳区分のEnum名称取得<br>
	 * コード：名称、コード：名称形式
	 * 
	 * @param lang 言語コード
	 * @return 自動仕訳区分のEnum名称
	 */
	public static String getCodeAndNames(String lang) {

		StringBuilder sb = new StringBuilder();

		for (AutoJournalDivision type : AutoJournalDivision.values()) {
			sb.append(type.value);
			sb.append("：");
			sb.append(MessageUtil.getWord(lang, type.getName()));
			sb.append("、");
		}

		return sb.substring(0, sb.length() - 1);

	}

}

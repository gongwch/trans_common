package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.message.*;

/**
 * 決算段階のフォーマッタ
 * 
 * @author AIS
 */
public class SettlementLevelFormat {

	/**
	 * 決算段階をフォーマットする
	 * 
	 * @param settlementLevel レベル
	 * @param lang 言語コード
	 * @return フォーマット後文字
	 */
	public String format(int settlementLevel, String lang) {
		if (settlementLevel == 0) {
			return MessageUtil.getShortWord(lang, "C00372"); // 通常
		}

		return Integer.toString(settlementLevel) + MessageUtil.getShortWord(lang, "C00373"); // 次
	}

	/**
	 * 決算段階の出力<br>
	 * 通常の場合はブランク<br>
	 * 以外：n次決算
	 * 
	 * @param settlementLevel レベル
	 * @param lang 言語コード
	 * @return 決算段階
	 */
	public String formatSettlement(int settlementLevel, String lang) {
		if (settlementLevel == 0) {
			return ""; // 通常の場合はブランク
		}

		// n次決算
		return format(settlementLevel, lang) + MessageUtil.getShortWord(lang, "C00142");
	}

}

package jp.co.ais.trans.common.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * 帳票規定クラス
 */
public abstract class LayoutBase {

	/** 言語 * */
	protected String lang;

	/**
	 * 帳票クラスを構築
	 * 
	 * @param lang 言語コード
	 */
	public LayoutBase(String lang) {
		this.setLanguageCode(lang);
	}

	/**
	 * 指定IDの文字を取得する.
	 * 
	 * @param wordId 文字ID
	 * @return 文字
	 */
	protected String getWord(String wordId) {
		return MessageUtil.getWord(lang, wordId);
	}

	/**
	 * 指定IDの文字を取得する.(複数)
	 * 
	 * @param wordIds 文字IDのリスト
	 * @return 引数で指定された文字IDに対する単語のリスト
	 */
	protected String[] getWordList(String[] wordIds) {
		return MessageUtil.getWordList(lang, wordIds);
	}

	/**
	 * メッセージを返す. 指定単語ID、または単語をバインド.
	 * 
	 * @param messageID メッセージID
	 * @param bindIds 単語ID、または、単語のリスト
	 * @return 変換されたメッセージ
	 */
	public String getMessage(String messageID, Object... bindIds) {

		return MessageUtil.convertMessage(lang, messageID, bindIds);
	}

	/**
	 * Dateを「年月日」形式文字列に変換
	 * 
	 * @param date キャストするDate
	 * @return 「年月日」形式String
	 */
	protected String formatYMD(Date date) {
		return DateUtil.toYMDString(lang, date);
	}

	/**
	 * Dateを「年月」形式文字列に変換
	 * 
	 * @param date キャストするDate
	 * @return 「年月」形式String
	 */
	protected String formatYM(Date date) {
		return DateUtil.toYMString(lang, date);
	}

	/**
	 * Dateを「年月」形式文字列に変換
	 * 
	 * @param date キャストするDate
	 * @return 「年月」形式String
	 */
	protected String formatMD(Date date) {
		return DateUtil.toString(lang, date, DateUtil.TYPE_DATE_FORMAT_MD);
	}

	/**
	 * Dateを「時分秒」形式文字列に変換
	 * 
	 * @param date キャストするDate
	 * @return 「時分秒」形式String
	 */
	protected String formatHMS(Date date) {
		return DateUtil.toHMSString(lang, date);
	}

	/**
	 * フォントの取得
	 * 
	 * @return フォント
	 */
	protected abstract String getFont();

	/**
	 * 言語コードの設定
	 * 
	 * @param lang 言語コード
	 */
	public void setLanguageCode(String lang) {
		this.lang = lang;
	}

	/**
	 * 言語コードの取得
	 * 
	 * @return 言語コード
	 */
	public String getLanguageCode() {
		return this.lang;
	}

	/**
	 * メッセージ(略称)取得
	 * 
	 * @param messageId メッセージID
	 * @return 変換後のメッセージ
	 */
	public String getShortWord(String messageId) {
		return MessageUtil.getShortWord(lang, messageId);
	}

	/**
	 * 指定月の単語の取得
	 * 
	 * @param month 指定月
	 * @return 指定月の単語<br>
	 *         C03406 1月 Jan.<br>
	 *         C03407 2月 Feb.<br>
	 *         C03408 3月 Mar.<br>
	 *         C03409 4月 Apr.<br>
	 *         C03410 5月 May<br>
	 *         C03411 6月 Jun.<br>
	 *         C03412 7月 Jul.<br>
	 *         C03413 8月 Aug.<br>
	 *         C03414 9月 Sept.<br>
	 *         C03415 10月 Oct.<br>
	 *         C03416 11月 Nov.<br>
	 *         C03417 12月 Dec.<br>
	 */
	public String getMonthWord(int month) {

		String monthWord = "";

		switch (month) {
			case 1:
				monthWord = getShortWord("C03406"); // 1月 Jan.
				break;
			case 2:
				monthWord = getShortWord("C03407"); // 2月 Feb.
				break;
			case 3:
				monthWord = getShortWord("C03408"); // 3月 Mar.
				break;
			case 4:
				monthWord = getShortWord("C03409"); // 4月 Apr.
				break;
			case 5:
				monthWord = getShortWord("C03410"); // 5月 May
				break;
			case 6:
				monthWord = getShortWord("C03411"); // 6月 Jun.
				break;
			case 7:
				monthWord = getShortWord("C03412"); // 7月 Jul.
				break;
			case 8:
				monthWord = getShortWord("C03413"); // 8月 Aug.
				break;
			case 9:
				monthWord = getShortWord("C03414"); // 9月 Sept.
				break;
			case 10:
				monthWord = getShortWord("C03415"); // 10月 Oct.
				break;
			case 11:
				monthWord = getShortWord("C03416"); // 11月 Nov.
				break;
			case 12:
				monthWord = getShortWord("C03417"); // 12月 Dec.
				break;
		}
		return monthWord;
	}

}

package jp.co.ais.trans.common.message;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * メッセージ取得用ユーティリティ
 */
public class MessageUtil {

	/**
	 * メッセージ取得
	 * 
	 * @param lang 言語コード
	 * @param messageId メッセージID
	 * @return メッセージ
	 */
	public static Message getMessage(String lang, String messageId) {
		return MessageManager.getInstance(lang).getMessage(messageId);
	}

	/**
	 * メッセージカテゴリ取得
	 * 
	 * @param lang 言語コード
	 * @param messageId 単語ID
	 * @return カテゴリ
	 */
	public static int getCategory(String lang, String messageId) {
		return MessageManager.getInstance(lang).getMessage(messageId).getCategory();
	}

	/**
	 * 単語取得
	 * 
	 * @param lang 言語コード
	 * @param wordId 単語ID
	 * @return 単語
	 */
	public static String getWord(String lang, String wordId) {
		return WordManager.getInstance(lang).getWord(wordId);
	}

	/**
	 * 指定IDの文字を取得する.(複数)
	 * 
	 * @param lang 言語コード
	 * @param wordIds 単語IDのリスト
	 * @return 引数で指定された文字IDに対する単語のリスト
	 */
	public static String[] getWordList(String lang, String[] wordIds) {
		return getWordList(lang, (Object[]) wordIds);
	}

	/**
	 * 指定IDの文字を取得する.(複数)<br>
	 * 単語ID以外の値を含む場合、こちらを利用.
	 * 
	 * @param lang 言語コード
	 * @param words 単語(文字ID含む)のリスト
	 * @return 引数で指定された文字IDに対する単語のリスト
	 */
	public static String[] getWordList(String lang, Object[] words) {
		ArrayList<String> list = new ArrayList<String>(words.length);

		WordManager manager = WordManager.getInstance(lang);

		for (int i = 0; i < words.length; i++) {
			list.add(manager.getWord(Util.avoidNull(words[i])));
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * 指定IDの省略文字を取得する.
	 * 
	 * @param lang 言語コード
	 * @param wordId 単語ID
	 * @return 引数で指定された文字IDに対する単語
	 */
	public static String getShortWord(String lang, String wordId) {
		return getWord(lang + ("en".equals(lang) ? "_s" : ""), wordId);
	}

	/**
	 * 指定IDの省略文字を取得する.(複数)
	 * 
	 * @param lang 言語コード
	 * @param wordIds 単語IDのリスト
	 * @return 引数で指定された文字IDに対する単語のリスト
	 */
	public static String[] getShortWordList(String lang, String[] wordIds) {
		return getWordList(lang + ("en".equals(lang) ? "_s" : ""), wordIds);
	}

	/**
	 * メッセージを取得する.<br>
	 * 単語ID以外の値を含む場合、こちらを利用.
	 * 
	 * @param lang 言語コード
	 * @param messageID メッセージID
	 * @param words 単語(文字ID含む)のリスト
	 * @return 変換後メッセージ
	 */
	public static String convertMessage(String lang, String messageID, Object... words) {

		Message msg = getMessage(lang, messageID);

		return msg.toString((Object[]) getWordList(lang, words));
	}

	/**
	 * メッセージを取得する
	 * 
	 * @param lang 言語コード
	 * @param messageID メッセージID
	 * @param wordIds バインド単語IDのリスト
	 * @return 変換後メッセージ
	 */
	public static String convertMessage(String lang, String messageID, String... wordIds) {
		return convertMessage(lang, messageID, (Object[]) wordIds);
	}

}

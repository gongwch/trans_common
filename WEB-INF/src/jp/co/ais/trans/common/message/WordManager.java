package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 単語を管理する
 */
public class WordManager {

	/** インスタンス */
	private static Map instances;

	/** ファイル名 */
	private static String fileName = "C:/ais/Word.xml";

	/** ファイル名:カスタマイズ分 */
	private static String patchFileName = "C:/ais/Word_Customize.xml";

	/** 言語コード */
	protected String lang;

	/** メッセージリスト */
	protected Map list;

	static {
		// マルチスレッド制御
		instances = Collections.synchronizedMap(new HashMap<String, WordManager>());

		if (!new File(fileName).exists()) {
			fileName = "Word.xml";
			patchFileName = "Word_Customize.xml";
		}
	}

	/**
	 * 指定した単語ID、文字でマネージャを構築する. 差し替え可能.
	 * 
	 * @param lang 言語コード
	 * @param wordMap 単語ID、文字マップ
	 */
	public static void initManager(String lang, Map wordMap) {

		WordManager instance = new WordManager(lang);
		instance.list = wordMap;
		instances.put(lang, instance);
	}

	/**
	 * 言語コードに対応したインスタンスを取得する
	 * 
	 * @param lang 言語コード ja:日本語 など
	 * @return マネージャ
	 */
	public static WordManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {
				WordManager instance = new WordManager(lang);
				// XML読み込み
				instance.list = MessageBuilder.buildMessageList(lang, fileName);
				try {
					// カスタマイズがあれば、上書き
					instance.list.putAll(MessageBuilder.buildMessageList(lang, patchFileName));
				} catch (Throwable ex) {
					// 処理なし
				}

				instances.put(lang, instance);
			}
			return (WordManager) instances.get(lang);

		} catch (IOException e) {
			throw new TEnvironmentException(e);
		}
	}

	/**
	 * キャッシュメッセージをクリアする.
	 */
	public static void clear() {
		instances.clear();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param lang 言語コード
	 */
	private WordManager(String lang) {
		this.lang = lang;
		this.list = new TreeMap();
	}

	/**
	 * 単語IDに対応した文字の取得
	 * 
	 * @param wordID 単語ID
	 * @return 文字
	 */
	public String getWord(String wordID) {

		if (Util.isNullOrEmpty(wordID) || !list.containsKey(wordID)) {
			return wordID;
		}
		return (String) list.get(wordID);
	}

	/**
	 * 全単語の取得
	 * 
	 * @return 単語リスト
	 */
	public Map getList() {
		return list;
	}
}
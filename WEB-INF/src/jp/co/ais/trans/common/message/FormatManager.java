package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * マネージャを管理する
 */
public class FormatManager {

	/** インスタンス */
	private static Map instances;

	/** 言語コード */
	protected String lang;

	/** フォーマットリスト */
	protected Map list;

	static {
		// マルチスレッド制御
		instances = Collections.synchronizedMap(new HashMap<String, FormatManager>());
	}

	/**
	 * 指定したフォーマットID、文字でマネージャを構築する. 差し替え可能.
	 * 
	 * @param lang 言語コード
	 * @param wordMap 単語ID、文字マップ
	 */
	public static void initManager(String lang, Map wordMap) {

		FormatManager instance = new FormatManager(lang);
		instance.list = wordMap;
		instances.put(lang, instance);
	}

	/**
	 * 言語コードに対応したインスタンスを取得する
	 * 
	 * @param lang 言語コード ja:日本語 など
	 * @return マネージャ
	 */
	public static FormatManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {
				FormatManager instance = new FormatManager(lang);
				// XML読み込み
				instance.list = MessageBuilder.buildMessageList(lang, "Format.xml");

				instances.put(lang, instance);
			}

			return (FormatManager) instances.get(lang);
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
	private FormatManager(String lang) {
		this.lang = lang;
		this.list = new TreeMap();
	}

	/**
	 * キーに対応したフォーマットの取得
	 * 
	 * @param key キー
	 * @return フォーマット
	 */
	public String get(String key) {

		if (Util.isNullOrEmpty(key) || !list.containsKey(key)) {
			return "";
		}
		return (String) list.get(key);
	}

	/**
	 * 全フォーマットの取得
	 * 
	 * @return フォーマットリスト
	 */
	public Map getList() {
		return list;
	}
}
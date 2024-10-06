package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * メッセージを管理する
 * 
 * @version 1.0
 * @created 17-11-2006 20:45:15
 */
public class MessageManager {

	/** インスタンス */
	private static Map instances;

	/** ファイル名 */
	private static String fileName = "C:/ais/Message.xml";

	/** ファイル名:カスタマイズ分 */
	private static String patchFileName = "C:/ais/Message_Customize.xml";

	/** 言語コード */
	protected String lang;

	/** メッセージ元リスト */
	protected Map messageStrList;

	/** メッセージクラスリスト */
	protected Map messageList;

	static {
		// マルチスレッド制御
		instances = Collections.synchronizedMap(new HashMap<String, MessageManager>());

		if (!new File(fileName).exists()) {
			fileName = "Message.xml";
			patchFileName = "Message_Customize.xml";
		}
	}

	/**
	 * 指定したメッセージID、文字でマネージャを構築する. 差し替え可能.
	 * 
	 * @param lang 言語コード
	 * @param msgMap メッセージID、文字マップ
	 */
	public static void initManager(String lang, Map msgMap) {

		MessageManager instance = new MessageManager(lang);
		instance.makeMessageList(msgMap);
		instances.put(lang, instance);
	}

	/**
	 * 言語コードに対応したインスタンスを取得する
	 * 
	 * @param lang 言語コード ja:日本語 など
	 * @return マネージャ
	 */
	public static MessageManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {

				// XML読み込み
				MessageManager instance = new MessageManager(lang);

				Map map = MessageBuilder.buildMessageList(lang, fileName);

				try {
					// カスタマイズがあれば、上書き
					map.putAll(MessageBuilder.buildMessageList(lang, patchFileName));
				} catch (Throwable ex) {
					// 処理なし
				}

				instance.makeMessageList(map);

				instances.put(lang, instance);
			}
			return (MessageManager) instances.get(lang);

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
	private MessageManager(String lang) {
		this.lang = lang;
		this.messageList = new TreeMap();
	}

	/**
	 * メッセージを構築する.
	 * 
	 * @param map メッセージID、文字
	 */
	private void makeMessageList(Map map) {
		messageStrList = map;

		// Message(インスタンス)を作る
		for (Iterator keys = map.keySet().iterator(); keys.hasNext();) {
			String messageID = (String) keys.next();
			String messageStr = (String) map.get(messageID);

			Message msg = new Message(messageID, messageStr);

			// 作ったMessageを箱に入れる
			messageList.put(messageID, msg);
		}
	}

	/**
	 * メッセージIDに対応したメッセージの取得
	 * 
	 * @param messageID メッセージID
	 * @return IDに対応したメッセージクラス. 該当IDが存在しない場合、擬似的にIDを文字としたメッセージクラスが返る.
	 */
	public Message getMessage(String messageID) {

		// IDに紐づくMessageクラスを返す
		if (Util.isNullOrEmpty(messageID) || !messageList.containsKey(messageID)) {

			// 登録が無い場合は、IDをそのまま返す
			return new Message(Util.avoidNull(messageID));
		}

		return (Message) messageList.get(messageID);
	}

	/**
	 * 元となるメッセージ文字列リスト取得
	 * 
	 * @return メッセージ文字列リスト
	 */
	public Map getList() {
		return messageStrList;
	}
}
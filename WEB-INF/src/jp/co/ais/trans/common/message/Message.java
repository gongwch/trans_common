package jp.co.ais.trans.common.message;

import java.text.*;
import java.util.*;

import javax.swing.*;

/**
 * メッセージ本体
 */
public class Message {

	/** エラー区分 */
	public static final int ERROR = JOptionPane.ERROR_MESSAGE;

	/** 警告 */
	public static final int WORNING = JOptionPane.WARNING_MESSAGE;

	/** インフォメーション */
	public static final int INFORMATION = JOptionPane.INFORMATION_MESSAGE;

	/** クエスチョン */
	public static final int QUESTION = JOptionPane.QUESTION_MESSAGE;

	/** メッセージID */
	private String messageID;

	/** カテゴリ */
	private int category = INFORMATION;

	/** メッセージ */
	private String message;

	/**
	 * コンストラクタ
	 * 
	 * @param message メッセージ
	 */
	public Message(String message) {
		this.messageID = message;
		this.message = message;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param messageID メッセージID
	 * @param messageStr メッセージ
	 */
	public Message(String messageID, String messageStr) {
		this.messageID = messageID;
		this.message = messageStr;

		String head = messageID.substring(0, 1);

		if ("E".equals(head)) {
			category = ERROR; // エラー
		} else if ("W".equals(head)) {
			category = WORNING; // 警告
		} else if ("Q".equals(head)) {
			category = QUESTION; // クエスチョン
		} else {
			category = INFORMATION; // インフォメーション
		}
	}

	/**
	 * メッセージIDを取得する
	 * 
	 * @return メッセージID
	 */
	public String getID() {
		return messageID;
	}

	/**
	 * カテゴリ(メッセージ区分)を取得する
	 * 
	 * @return カテゴリ
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * MessaageクラスをStringクラスにコンバート
	 */
	@Override
	public String toString() {

		return this.toString("");
	}

	/**
	 * MessaageクラスをStringクラスにコンバート. <br>
	 * 不足挿入箇所には、空白を補填する
	 * 
	 * @param bindObjs バインドオブジェクト
	 * @return バインドされた文字列
	 */
	public String toString(Object... bindObjs) {
		int bindCount = message.split("}").length;

		// バインド文字数よりバインド値が少ない場合、空白を足す
		if (bindObjs.length < bindCount) {
			List bindList = new ArrayList(bindCount);
			bindList.addAll(Arrays.asList(bindObjs));

			for (int i = 0; i < (bindCount - bindObjs.length); i++) {
				bindList.add("");
			}

			bindObjs = bindList.toArray();
		}
		
		// 文字列「'」を「''」に変換する
		message = message.replaceAll("'", "''");

		return MessageFormat.format(message, bindObjs).trim();
	}
}
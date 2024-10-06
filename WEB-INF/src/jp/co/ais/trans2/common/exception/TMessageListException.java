package jp.co.ais.trans2.common.exception;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 複数メッセージ保有可能なException
 */
public class TMessageListException extends TException {

	/** メッセージリスト */
	public List<Message> list;

	/**
	 * コンストラクタ.
	 * 
	 * @param list メッセージリスト
	 */
	public TMessageListException(List<Message> list) {
		this.list = list;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param messageID メッセージID
	 * @param list メッセージリスト
	 */
	public TMessageListException(String messageID, List<Message> list) {
		super(messageID);

		this.list = list;
	}

	/**
	 * メッセージリスト
	 * 
	 * @return list メッセージリスト
	 */
	public List<Message> getList() {
		return list;
	}
}

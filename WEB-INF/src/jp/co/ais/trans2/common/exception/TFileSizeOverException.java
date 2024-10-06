package jp.co.ais.trans2.common.exception;

import jp.co.ais.trans.common.except.*;

/**
 * ファイルサイズが設定値を超えた場合のException
 */
public class TFileSizeOverException extends TException {

	/**
	 * コンストラクタ
	 */
	public TFileSizeOverException() {
		super();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param message
	 */
	public TFileSizeOverException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param messageId
	 * @param message
	 */
	public TFileSizeOverException(String messageId, String message) {
		super(messageId, message);
	}

}

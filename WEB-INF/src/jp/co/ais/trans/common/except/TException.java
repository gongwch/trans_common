package jp.co.ais.trans.common.except;

/**
 * システム用ApplicationException
 */
public class TException extends Exception {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** バインド文字 */
	private Object[] args = new Object[0];

	/**
	 * コンストラクタ.
	 */
	public TException() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param message メッセージ
	 */
	public TException(String message) {
		super(message);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param messageId メッセージID
	 * @param objects バインド文字
	 */
	public TException(String messageId, Object... objects) {
		this(messageId);

		this.args = objects;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause オリジナル
	 */
	public TException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause オリジナル
	 * @param messageId メッセージID
	 * @param objects バインド文字
	 */
	public TException(Throwable cause, String messageId, Object... objects) {
		super(messageId, cause);

		this.args = objects;
	}

	/**
	 * メッセージIDを返す.
	 * 
	 * @return メッセージID
	 */
	public String getMessageID() {
		return super.getMessage();
	}

	/**
	 * メッセージに挿入される値リスト
	 * 
	 * @return 値リスト
	 */
	public Object[] getMessageArgs() {
		return this.args;
	}
}

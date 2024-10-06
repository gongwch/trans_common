package jp.co.ais.trans.common.except;

/**
 * Runtimeエラー用
 */
public class TRuntimeException extends RuntimeException {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/** バインド文字 */
	private Object[] args = new Object[0];

	/**
	 * デフォルトコンストラクタ
	 */
	public TRuntimeException() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param messageId	メッセージID
	 * @param objects		バインド文字
	 */
	public TRuntimeException(String messageId, Object... objects) {
		super(messageId);

		this.args = objects;
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause	オリジナル
	 */
	public TRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause		オリジナル
	 * @param messageId	メッセージID
	 * @param objects		バインド文字
	 */
	public TRuntimeException(Throwable cause, String messageId, Object... objects) {
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
	 * メッセージに挿入されるバインド値リストを.
	 * @return	バインド値リスト
	 */
	public Object[] getMessageArgs() {
		return args;
	}
}

package jp.co.ais.trans.common.except;

/**
 * 環境エラー用RuntimeException
 */
public class TEnvironmentException extends TRuntimeException {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/**
	 * デフォルトコンストラクタ
	 */
	public TEnvironmentException() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param messageId メッセージID
	 * @param objects バインド文字
	 */
	public TEnvironmentException(String messageId, Object... objects) {
		super(messageId, objects);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause オリジナル
	 */
	public TEnvironmentException(Throwable cause) {
		super(cause);
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param cause オリジナル
	 * @param messageId メッセージID
	 * @param objects バインド文字
	 */
	public TEnvironmentException(Throwable cause, String messageId, Object... objects) {
		super(cause, messageId, objects);
	}
}

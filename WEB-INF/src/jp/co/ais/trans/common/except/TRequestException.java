package jp.co.ais.trans.common.except;

/**
 * クライアントサイドでサーバからのエラーをやり取りする際の例外
 */
public class TRequestException extends TException {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	/**
	 * コンストラクタ.
	 */
	public TRequestException() {
		super();
	}
}

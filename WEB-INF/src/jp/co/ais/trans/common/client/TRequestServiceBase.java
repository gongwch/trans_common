package jp.co.ais.trans.common.client;

import java.io.*;

/**
 * 共通通信サービスの基軸クラス
 */
public class TRequestServiceBase {

	private final static String SERVICE_NAME = "common/";

	/** server access object. */
	protected TRequestBase access = null;

	protected String entry = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param access リクエストクラス
	 * @param entry エントリー
	 */
	protected TRequestServiceBase(TRequestBase access, String entry) {
		super();
		this.access = access;
		this.entry = entry;
	}

	/**
	 * set access object.
	 * 
	 * @param access リクエストクラス
	 */
	public void setRequestBase(TRequestBase access) {
		this.access = access;
	}

	/**
	 * request
	 * 
	 * @return false:失敗
	 * @throws IOException
	 */
	protected boolean request() throws IOException {

		if (this.access == null) return false;

		boolean b = access.request(SERVICE_NAME + entry);

		return b;
	}
}

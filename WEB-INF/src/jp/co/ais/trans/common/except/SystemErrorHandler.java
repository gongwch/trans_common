package jp.co.ais.trans.common.except;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * システムエラーハンドラー<br>
 * クライアントサイド処理かサーバサイド処理か不明な場合に利用.<br>
 * ※システム共通以外は利用しないこと.
 */
public class SystemErrorHandler {

	/**
	 * エラーハンドリング
	 * 
	 * @param e 対象エラー
	 */
	public static void handledException(Throwable e) {

		if (Util.isNullOrEmpty(TClientLoginInfo.getSessionID())) {
			ServerErrorHandler.handledException(e);
		} else {
			ClientErrorHandler.handledException(e);
		}
	}
}
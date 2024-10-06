package jp.co.ais.trans.common.except;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;

/**
 * クライアントサイドエラーハンドラー
 */
public class ClientErrorHandler implements Thread.UncaughtExceptionHandler {

	/**
	 * エラーのハンドリング
	 * 
	 * @param t 発生スレッド
	 * @param e エラー
	 */
	public void uncaughtException(Thread t, Throwable e) {

		if (e instanceof OutOfMemoryError) {
			// ユーザ情報の取得.
			String error = MessageUtil.convertMessage(TClientLoginInfo.getInstance().getUserLanguage(), "S00001");
			JOptionPane.showMessageDialog(null, error + e.toString());
		}
	}

	/**
	 * エラーハンドリング
	 * 
	 * @param e 対象エラー
	 */
	public static void handledException(Throwable e) {

		for (int i = 0; e != null && i < 5; i++) {
			ClientLogger.error("", e);
			e = e.getCause();
		}
	}
}
package jp.co.ais.trans.common.log;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * クライアントサイドログ出力
 */
public final class ClientLogger {

	// ServerLoggerへのdelegate

	/**
	 * DEBUGレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void debug(String log) {
		ServerLogger.debug(log);
	}

	/**
	 * DEBUGレベルログ出力
	 * 
	 * @param ex 例外
	 */
	public static void debug(Throwable ex) {
		ServerLogger.debug(ex);
	}

	/**
	 * INFOレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void info(String log) {
		ServerLogger.info(log);
	}

	/**
	 * WARNINGレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void warning(String log) {
		ServerLogger.warning(log);
	}

	/**
	 * ERRORレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void error(String log) {
		ServerLogger.error(log);
	}

	/**
	 * ERRORレベルログ出力
	 * 
	 * @param log ログ
	 * @param ex 例外
	 */
	public static void error(String log, Throwable ex) {
		ServerLogger.error(log, ex);
	}

	/**
	 * ERRORレベルログ出力(TException用).<br>
	 * TException内のメッセージIDを変換して出力.
	 * 
	 * @param ex 例外
	 */
	public static void error(TException ex) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
	}

	/**
	 * ERRORレベルログ出力(TRuntimeException用).<br>
	 * TException内のメッセージIDを変換して出力.
	 * 
	 * @param ex 例外
	 */
	public static void error(TRuntimeException ex) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
	}

	/**
	 * 状況メモリログ出力
	 * 
	 * @param comment コメント
	 */
	public static void memory(String comment) {
		StringBuilder buff = new StringBuilder();
		buff.append("[").append(Util.getCurrentDateString()).append("]");
		buff.append(comment).append(":");
		buff.append(Runtime.getRuntime().freeMemory()).append("\t\t");
		buff.append(Runtime.getRuntime().totalMemory()).append("\t\t");
		buff.append(Runtime.getRuntime().maxMemory()).append(" (");
		buff.append(Runtime.getRuntime().freeMemory() * 100 / Runtime.getRuntime().totalMemory()).append("%)");

		ClientLogger.debug(buff.toString());
	}
}

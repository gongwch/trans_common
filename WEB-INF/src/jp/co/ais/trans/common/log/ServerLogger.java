package jp.co.ais.trans.common.log;

import org.apache.logging.log4j.*;

import jp.co.ais.trans.common.util.*;

/**
 * サーバサイドログ出力
 */
public class ServerLogger {

	/** Logger */
	private static Logger logger = LogManager.getLogger("trans");

	/**
	 * DEBUGレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void debug(String log) {
		logger.debug(log);
	}

	/**
	 * DEBUGレベルログ出力
	 * 
	 * @param ex 例外
	 */
	public static void debug(Throwable ex) {
		logger.debug(ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.debug(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * INFOレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void info(String log) {
		logger.info(log);
	}

	/**
	 * WARNINGレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void warning(String log) {
		logger.warn(log);
	}

	/**
	 * ERRORレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void error(String log) {
		logger.error(log);
	}

	/**
	 * ERRORレベルログ出力
	 * 
	 * @param log ログ
	 * @param ex 例外
	 */
	public static void error(String log, Throwable ex) {
		logger.error(log + " : " + ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.error(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * FATALレベルログ出力
	 * 
	 * @param log ログ
	 */
	public static void fatal(String log) {
		logger.fatal(log);
	}

	/**
	 * FATALレベルログ出力
	 * 
	 * @param log ログ
	 * @param ex 例外
	 */
	public static void fatal(String log, Throwable ex) {
		logger.fatal(log + " : " + ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.fatal(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * DEBUGレベルかどうかの判定
	 * 
	 * @return DEBUGレベルの場合true
	 */
	public static boolean isDebugLevel() {
		return Level.DEBUG.equals(logger.getLevel());
	}
}

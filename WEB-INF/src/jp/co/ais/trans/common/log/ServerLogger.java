package jp.co.ais.trans.common.log;

import org.apache.logging.log4j.*;

import jp.co.ais.trans.common.util.*;

/**
 * �T�[�o�T�C�h���O�o��
 */
public class ServerLogger {

	/** Logger */
	private static Logger logger = LogManager.getLogger("trans");

	/**
	 * DEBUG���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void debug(String log) {
		logger.debug(log);
	}

	/**
	 * DEBUG���x�����O�o��
	 * 
	 * @param ex ��O
	 */
	public static void debug(Throwable ex) {
		logger.debug(ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.debug(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * INFO���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void info(String log) {
		logger.info(log);
	}

	/**
	 * WARNING���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void warning(String log) {
		logger.warn(log);
	}

	/**
	 * ERROR���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void error(String log) {
		logger.error(log);
	}

	/**
	 * ERROR���x�����O�o��
	 * 
	 * @param log ���O
	 * @param ex ��O
	 */
	public static void error(String log, Throwable ex) {
		logger.error(log + " : " + ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.error(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * FATAL���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void fatal(String log) {
		logger.fatal(log);
	}

	/**
	 * FATAL���x�����O�o��
	 * 
	 * @param log ���O
	 * @param ex ��O
	 */
	public static void fatal(String log, Throwable ex) {
		logger.fatal(log + " : " + ex.getMessage());

		for (int i = 0; ex != null && i < 5; i++) {
			logger.fatal(Util.makeStackString(ex));

			ex = ex.getCause();
		}
	}

	/**
	 * DEBUG���x�����ǂ����̔���
	 * 
	 * @return DEBUG���x���̏ꍇtrue
	 */
	public static boolean isDebugLevel() {
		return Level.DEBUG.equals(logger.getLevel());
	}
}

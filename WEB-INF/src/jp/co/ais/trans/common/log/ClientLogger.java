package jp.co.ais.trans.common.log;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * �N���C�A���g�T�C�h���O�o��
 */
public final class ClientLogger {

	// ServerLogger�ւ�delegate

	/**
	 * DEBUG���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void debug(String log) {
		ServerLogger.debug(log);
	}

	/**
	 * DEBUG���x�����O�o��
	 * 
	 * @param ex ��O
	 */
	public static void debug(Throwable ex) {
		ServerLogger.debug(ex);
	}

	/**
	 * INFO���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void info(String log) {
		ServerLogger.info(log);
	}

	/**
	 * WARNING���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void warning(String log) {
		ServerLogger.warning(log);
	}

	/**
	 * ERROR���x�����O�o��
	 * 
	 * @param log ���O
	 */
	public static void error(String log) {
		ServerLogger.error(log);
	}

	/**
	 * ERROR���x�����O�o��
	 * 
	 * @param log ���O
	 * @param ex ��O
	 */
	public static void error(String log, Throwable ex) {
		ServerLogger.error(log, ex);
	}

	/**
	 * ERROR���x�����O�o��(TException�p).<br>
	 * TException���̃��b�Z�[�WID��ϊ����ďo��.
	 * 
	 * @param ex ��O
	 */
	public static void error(TException ex) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
	}

	/**
	 * ERROR���x�����O�o��(TRuntimeException�p).<br>
	 * TException���̃��b�Z�[�WID��ϊ����ďo��.
	 * 
	 * @param ex ��O
	 */
	public static void error(TRuntimeException ex) {
		String lang = TClientLoginInfo.getInstance().getUserLanguage();
		ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
	}

	/**
	 * �󋵃��������O�o��
	 * 
	 * @param comment �R�����g
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

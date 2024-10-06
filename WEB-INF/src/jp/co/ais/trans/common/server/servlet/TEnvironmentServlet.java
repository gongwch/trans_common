package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���\�zServlet
 */
public class TEnvironmentServlet extends HttpServlet {

	/**
	 * �K�v�T�[�o�T�C�h�����\�z����
	 */
	@Override
	public void init() {
		ServerLogger.info(this.getClass().getName() + "#init()");

		// �T�[�o�T�C�h���[�g�p�X�ݒ�
		ServerConfig.setRootPath(super.getServletContext().getRealPath(""));

		try {
			// �������Ď�
			String isMemoryWatch = ServerConfig.getProperty("trans.memory.watch");
			if (!Util.isNullOrEmpty(isMemoryWatch) && Boolean.valueOf(isMemoryWatch)) {

				String watchTime = ServerConfig.getProperty("trans.memory.watch.time");
				long loopTime = !Util.isNullOrEmpty(isMemoryWatch) ? Long.parseLong(watchTime) : 36000;

				new MemoryStat(loopTime).start();
			}

		} catch (MissingResourceException e) {
			// �����Ȃ�
		}

		try {
			// �^�C���]�[���ݒ�
			String timeZone = ServerConfig.getProperty("trans.time.zone");
			TimeZone.setDefault(TimeZone.getTimeZone(timeZone));

			System.out.println("#set timezone=" + timeZone);

		} catch (MissingResourceException e) {
			// �����Ȃ�
		}

		// �o�[�W����������
		initServerVersion();

	}

	/**
	 * �o�[�W����������
	 */
	public static void initServerVersion() {

		try {
			Class clazz = TEnvironmentServlet.class;

			try {
				// �ʎw��(�J�X�^�}�C�Y���[�U�[�Ή��̂���)
				String clazzName = ServerConfig.getProperty("trans.version.check.class");
				clazz = Class.forName(clazzName);

			} catch (Exception e) {
				// �����Ȃ�
			}

			ServerLogger.debug("use class:" + clazz);
			TServletBase.version = Util.getSystemVersion(clazz)[1];

			ServerLogger.debug("systemVersion:" + TServletBase.version);
		} catch (Exception e) {
			// �G���[�\��
			ServerLogger.error("version initial error.");
		}
	}

	/** �������Ď� */
	protected class MemoryStat extends Thread {

		/** Sleep Time */
		private long sleepTime;

		/**
		 * �R���X�g���N�^
		 * 
		 * @param sleepTime Sleep(���[�v)����
		 */
		public MemoryStat(long sleepTime) {
			this.sleepTime = sleepTime;
		}

		/**
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			ServerLogger.fatal(this.getClass().getName() + "#memory watch start. " + sleepTime);
			ServerLogger.fatal("totalMemory  maxMemory  freeMemory");

			while (true) {
				try {
					// �X���b�h���ƃ��[�v����\��
					StringBuilder buff = new StringBuilder();
					buff.append(Runtime.getRuntime().totalMemory()).append("\t");
					buff.append(Runtime.getRuntime().maxMemory()).append("\t");
					buff.append(Runtime.getRuntime().freeMemory()).append("\t");

					ServerLogger.fatal(buff.toString());

					sleep(sleepTime);
				} catch (InterruptedException e) {
					//
				}
			}
		}
	}
}

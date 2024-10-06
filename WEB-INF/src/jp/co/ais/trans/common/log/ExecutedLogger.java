package jp.co.ais.trans.common.log;

import java.io.*;
import java.util.*;

import org.apache.logging.log4j.*;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.*;
import org.apache.logging.log4j.core.config.*;
import org.apache.logging.log4j.core.layout.*;
import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * ��Аݒ�����̃��K�[�N���X
 */
public abstract class ExecutedLogger {

	/** �v���O�����J�n */
	public final static String IN = "IN";

	/** �v���O�����I�� */
	public final static String OUT = "OUT";

	/** ���O�C�� */
	public final static String LOGIN = "LOGIN";

	/** ���O�A�E�g */
	public final static String LOGOUT = "LOGOUT";

	/** ��Еʃ��K�[���X�g */
	protected static Map<String, ExecutedFileLogger> loggerList = Collections
			.synchronizedMap(new TreeMap<String, ExecutedFileLogger>());

	/**
	 * ��ЃR�[�h�ɑΉ������C���X�^���X���擾����
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return TLogger�N���X
	 */
	public static ExecutedLogger getInstance(String companyCode) {

		ExecutedFileLogger logger = loggerList.get(companyCode);

		if (logger == null) {
			// ��Аݒ�̃��K�[��������Ȃ������ꍇ�A���O�N���X�𐶐����A���X�g�ɒǉ�����B
			logger = new ExecutedFileLogger(companyCode);
			loggerList.put(companyCode, logger);
		}

		return logger;
	}

	/**
	 * ���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 * @param prgCode �v���O����ID
	 * @param state ���
	 */
	public abstract void log(String userCode, String userName, String ipAddress, String prgCode, String state);

	/**
	 * ���O�C�����O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public abstract void logLogin(String userCode, String userName, String ipAddress);

	/**
	 * ���O�A�E�g���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public abstract void logLogout(String userCode, String userName, String ipAddress);

	/**
	 * ���O�t�@�C���p���K�[
	 */
	public static class ExecutedFileLogger extends ExecutedLogger {

		/** �f�t�H���g�ݒ� ���C�A�E�g�p�^�[�� */
		public static AbstractOutputStreamAppender settingApender;

		/** �f�t�H���g�ݒ� ���O���x�� */
		public static Level settingLevel = Level.INFO;

		/** ���t�p�^�[�� */
		public static String datePattern = "'.'yyyy-MM";

		/** Logger�N���X */
		protected Logger logger;

		static {

			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			Configuration config = context.getConfiguration();
			settingApender = config.getAppender("exeLogAppender");

			// �v���p�e�B�擾�p���K�[�𐶐�
			Logger propertyLogger = LogManager.getLogger("exeLog");
			if (config.getAppender("exeLogAppender") != null) {
				settingApender = config.getAppender("exeLogAppender");
			} else {
				// �v���p�e�B�ݒ肪�Ȃ��ꍇ�f�t�H���g�l��ݒ�
				PatternLayout layout = PatternLayout.newBuilder().withPattern("%d<>%m%n").build();
				settingApender = FileAppender.newBuilder() // 
						.withFileName("C:\\temp\\dummy.log") //
						.setImmediateFlush(true) //
						.setLayout(layout) //
						.setName("exeLogAppender") //
						.build();
				settingApender.start();
				updateLoggers(settingApender, config);
			}

			if (propertyLogger.getLevel() != null) {
				settingLevel = propertyLogger.getLevel();
			}

		}

		/**
		* @param appender
		* @param config
		*/
		private static void updateLoggers(final Appender appender, final Configuration config) {
			for (final LoggerConfig loggerConfig : config.getLoggers().values()) {
				loggerConfig.addAppender(appender, null, null);
			}
			config.getRootLogger().addAppender(appender, null, null);
		}

		/**
		 * �R���X�g���N�^�i��ЃR�[�h�Ń��K�[���̂ƋL�^PATH���قȂ�j
		 * 
		 * @param companyCode ��ЃR�[�h
		 */
		protected ExecutedFileLogger(String companyCode) {

			try {
				logger = LogManager.getLogger(companyCode);

				// �T�[�o�̃��O�t�H���_
				String rootPath = TServerEnv.USER_LOG_DIR.toString();

				File path = new File(rootPath);
				if (!path.exists()) {
					if (!path.createNewFile()) {
						return;
					}
				}

				LoggerContext context = (LoggerContext) LogManager.getContext(false);
				Configuration config = context.getConfiguration();
				// �v���p�e�B�[��APPENDER�����擾
				PatternLayout layout = (PatternLayout) settingApender.getLayout();
				if (settingApender instanceof RollingFileAppender) {
					// ���O�t�@�C���̃��C�A�E�g�APATH�A�X�V�p�^�����w��A
					RollingFileAppender appender = RollingFileAppender.newBuilder() //
							.setLayout(layout) //
							.withFilePattern(datePattern) // 
							.withFileName(rootPath + companyCode + ".log")
							.setName(companyCode)
							.build();
					settingApender.start();
					// ���K�[��Appender��ǉ�
					config.addAppender(appender);
				} else {
					FileAppender appender = FileAppender.newBuilder() //
							.setLayout(layout) //
							.withFileName(rootPath + companyCode + ".log") //
							.setName(companyCode)
							.build();

					settingApender.start();
					// ���K�[��Appender��ǉ�
					config.addAppender(appender);
				}

				// ���x���ݒ�. WARNING�ȏ�Ȃ���s���O�o�͂��Ȃ�
				logger.atLevel(settingLevel);

			} catch (IOException e) {
				throw new TRuntimeException(e);
			}
		}

		/**
		 * ���O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 * @param prgCode �v���O����ID
		 * @param state ���
		 */
		@Override
		public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

			// ���O������؂肪������String�ɕϊ�
			String[] logArray = { userCode, userName, ipAddress, prgCode, "", state };

			// ���O���L�^
			logger.info(StringUtil.toDelimitString(logArray));

		}

		/**
		 * ���O�C�����O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 */
		@Override
		public void logLogin(String userCode, String userName, String ipAddress) {

			// ���O������؂肪������String�ɕϊ�
			String[] logArray = { userCode, userName, ipAddress, LOGIN, "", "" };

			// ���O���L�^
			logger.info(StringUtil.toDelimitString(logArray));
		}

		/**
		 * ���O�A�E�g���O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 */
		@Override
		public void logLogout(String userCode, String userName, String ipAddress) {

			// ���O������؂肪������String�ɕϊ�
			String[] logArray = { userCode, userName, ipAddress, LOGOUT, "", "" };

			// ���O���L�^
			logger.info(StringUtil.toDelimitString(logArray));
		}
	}

	/**
	 * DB�p���K�[
	 */
	public static class ExecutedDBLogger extends ExecutedLogger {

		/** ��ЃR�[�h */
		protected String companyCode;

		/** ���W�b�N */
		protected ExecutedLogLogic logic;

		/**
		 * �R���X�g���N�^.
		 */
		public ExecutedDBLogger() {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			this.logic = (ExecutedLogLogic) container.getComponent("ExecutedLogLogic");
		}

		/**
		 * ��ЃR�[�h�̐ݒ�
		 * 
		 * @param companyCode ��ЃR�[�h
		 */
		protected void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		/**
		 * ���O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 * @param prgCode �v���O����ID
		 * @param state ���
		 */
		@Override
		public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

			logic.setCompanyCode(companyCode);
			logic.log(userCode, userName, ipAddress, prgCode, state);
		}

		/**
		 * ���O�C�����O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 */
		@Override
		public void logLogin(String userCode, String userName, String ipAddress) {

			logic.setCompanyCode(companyCode);
			logic.logLogin(userCode, userName, ipAddress);
		}

		/**
		 * ���O�A�E�g���O���s
		 * 
		 * @param userCode ���[�U�R�[�h
		 * @param userName ���[�U����
		 * @param ipAddress IP�A�h���X
		 */
		@Override
		public void logLogout(String userCode, String userName, String ipAddress) {

			logic.setCompanyCode(companyCode);
			logic.logLogout(userCode, userName, ipAddress);
		}
	}
}

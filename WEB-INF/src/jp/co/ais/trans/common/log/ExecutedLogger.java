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
 * 会社設定を持つのロガークラス
 */
public abstract class ExecutedLogger {

	/** プログラム開始 */
	public final static String IN = "IN";

	/** プログラム終了 */
	public final static String OUT = "OUT";

	/** ログイン */
	public final static String LOGIN = "LOGIN";

	/** ログアウト */
	public final static String LOGOUT = "LOGOUT";

	/** 会社別ロガーリスト */
	protected static Map<String, ExecutedFileLogger> loggerList = Collections
			.synchronizedMap(new TreeMap<String, ExecutedFileLogger>());

	/**
	 * 会社コードに対応したインスタンスを取得する
	 * 
	 * @param companyCode 会社コード
	 * @return TLoggerクラス
	 */
	public static ExecutedLogger getInstance(String companyCode) {

		ExecutedFileLogger logger = loggerList.get(companyCode);

		if (logger == null) {
			// 会社設定のロガーが見つからなかった場合、ログクラスを生成し、リストに追加する。
			logger = new ExecutedFileLogger(companyCode);
			loggerList.put(companyCode, logger);
		}

		return logger;
	}

	/**
	 * ログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 * @param prgCode プログラムID
	 * @param state 状態
	 */
	public abstract void log(String userCode, String userName, String ipAddress, String prgCode, String state);

	/**
	 * ログインログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public abstract void logLogin(String userCode, String userName, String ipAddress);

	/**
	 * ログアウトログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public abstract void logLogout(String userCode, String userName, String ipAddress);

	/**
	 * ログファイル用ロガー
	 */
	public static class ExecutedFileLogger extends ExecutedLogger {

		/** デフォルト設定 レイアウトパターン */
		public static AbstractOutputStreamAppender settingApender;

		/** デフォルト設定 ログレベル */
		public static Level settingLevel = Level.INFO;

		/** 日付パターン */
		public static String datePattern = "'.'yyyy-MM";

		/** Loggerクラス */
		protected Logger logger;

		static {

			LoggerContext context = (LoggerContext) LogManager.getContext(false);
			Configuration config = context.getConfiguration();
			settingApender = config.getAppender("exeLogAppender");

			// プロパティ取得用ロガーを生成
			Logger propertyLogger = LogManager.getLogger("exeLog");
			if (config.getAppender("exeLogAppender") != null) {
				settingApender = config.getAppender("exeLogAppender");
			} else {
				// プロパティ設定がない場合デフォルト値を設定
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
		 * コンストラクタ（会社コードでロガー名称と記録PATHが異なる）
		 * 
		 * @param companyCode 会社コード
		 */
		protected ExecutedFileLogger(String companyCode) {

			try {
				logger = LogManager.getLogger(companyCode);

				// サーバのログフォルダ
				String rootPath = TServerEnv.USER_LOG_DIR.toString();

				File path = new File(rootPath);
				if (!path.exists()) {
					if (!path.createNewFile()) {
						return;
					}
				}

				LoggerContext context = (LoggerContext) LogManager.getContext(false);
				Configuration config = context.getConfiguration();
				// プロパティーのAPPENDER情報を取得
				PatternLayout layout = (PatternLayout) settingApender.getLayout();
				if (settingApender instanceof RollingFileAppender) {
					// ログファイルのレイアウト、PATH、更新パタンを指定、
					RollingFileAppender appender = RollingFileAppender.newBuilder() //
							.setLayout(layout) //
							.withFilePattern(datePattern) // 
							.withFileName(rootPath + companyCode + ".log")
							.setName(companyCode)
							.build();
					settingApender.start();
					// ロガーにAppenderを追加
					config.addAppender(appender);
				} else {
					FileAppender appender = FileAppender.newBuilder() //
							.setLayout(layout) //
							.withFileName(rootPath + companyCode + ".log") //
							.setName(companyCode)
							.build();

					settingApender.start();
					// ロガーにAppenderを追加
					config.addAppender(appender);
				}

				// レベル設定. WARNING以上なら実行ログ出力しない
				logger.atLevel(settingLevel);

			} catch (IOException e) {
				throw new TRuntimeException(e);
			}
		}

		/**
		 * ログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 * @param prgCode プログラムID
		 * @param state 状態
		 */
		@Override
		public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

			// ログ情報を区切りが入ったStringに変換
			String[] logArray = { userCode, userName, ipAddress, prgCode, "", state };

			// ログを記録
			logger.info(StringUtil.toDelimitString(logArray));

		}

		/**
		 * ログインログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 */
		@Override
		public void logLogin(String userCode, String userName, String ipAddress) {

			// ログ情報を区切りが入ったStringに変換
			String[] logArray = { userCode, userName, ipAddress, LOGIN, "", "" };

			// ログを記録
			logger.info(StringUtil.toDelimitString(logArray));
		}

		/**
		 * ログアウトログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 */
		@Override
		public void logLogout(String userCode, String userName, String ipAddress) {

			// ログ情報を区切りが入ったStringに変換
			String[] logArray = { userCode, userName, ipAddress, LOGOUT, "", "" };

			// ログを記録
			logger.info(StringUtil.toDelimitString(logArray));
		}
	}

	/**
	 * DB用ロガー
	 */
	public static class ExecutedDBLogger extends ExecutedLogger {

		/** 会社コード */
		protected String companyCode;

		/** ロジック */
		protected ExecutedLogLogic logic;

		/**
		 * コンストラクタ.
		 */
		public ExecutedDBLogger() {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			this.logic = (ExecutedLogLogic) container.getComponent("ExecutedLogLogic");
		}

		/**
		 * 会社コードの設定
		 * 
		 * @param companyCode 会社コード
		 */
		protected void setCompanyCode(String companyCode) {
			this.companyCode = companyCode;
		}

		/**
		 * ログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 * @param prgCode プログラムID
		 * @param state 状態
		 */
		@Override
		public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

			logic.setCompanyCode(companyCode);
			logic.log(userCode, userName, ipAddress, prgCode, state);
		}

		/**
		 * ログインログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 */
		@Override
		public void logLogin(String userCode, String userName, String ipAddress) {

			logic.setCompanyCode(companyCode);
			logic.logLogin(userCode, userName, ipAddress);
		}

		/**
		 * ログアウトログ実行
		 * 
		 * @param userCode ユーザコード
		 * @param userName ユーザ名称
		 * @param ipAddress IPアドレス
		 */
		@Override
		public void logLogout(String userCode, String userName, String ipAddress) {

			logic.setCompanyCode(companyCode);
			logic.logLogout(userCode, userName, ipAddress);
		}
	}
}

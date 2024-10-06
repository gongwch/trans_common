package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;

/**
 * 環境構築Servlet
 */
public class TEnvironmentServlet extends HttpServlet {

	/**
	 * 必要サーバサイド環境を構築する
	 */
	@Override
	public void init() {
		ServerLogger.info(this.getClass().getName() + "#init()");

		// サーバサイドルートパス設定
		ServerConfig.setRootPath(super.getServletContext().getRealPath(""));

		try {
			// メモリ監視
			String isMemoryWatch = ServerConfig.getProperty("trans.memory.watch");
			if (!Util.isNullOrEmpty(isMemoryWatch) && Boolean.valueOf(isMemoryWatch)) {

				String watchTime = ServerConfig.getProperty("trans.memory.watch.time");
				long loopTime = !Util.isNullOrEmpty(isMemoryWatch) ? Long.parseLong(watchTime) : 36000;

				new MemoryStat(loopTime).start();
			}

		} catch (MissingResourceException e) {
			// 処理なし
		}

		try {
			// タイムゾーン設定
			String timeZone = ServerConfig.getProperty("trans.time.zone");
			TimeZone.setDefault(TimeZone.getTimeZone(timeZone));

			System.out.println("#set timezone=" + timeZone);

		} catch (MissingResourceException e) {
			// 処理なし
		}

		// バージョンを持つ
		initServerVersion();

	}

	/**
	 * バージョン初期化
	 */
	public static void initServerVersion() {

		try {
			Class clazz = TEnvironmentServlet.class;

			try {
				// 個別指定(カスタマイズユーザー対応のため)
				String clazzName = ServerConfig.getProperty("trans.version.check.class");
				clazz = Class.forName(clazzName);

			} catch (Exception e) {
				// 処理なし
			}

			ServerLogger.debug("use class:" + clazz);
			TServletBase.version = Util.getSystemVersion(clazz)[1];

			ServerLogger.debug("systemVersion:" + TServletBase.version);
		} catch (Exception e) {
			// エラー表示
			ServerLogger.error("version initial error.");
		}
	}

	/** メモリ監視 */
	protected class MemoryStat extends Thread {

		/** Sleep Time */
		private long sleepTime;

		/**
		 * コンストラクタ
		 * 
		 * @param sleepTime Sleep(ループ)時間
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
					// スレッド名とループ数を表示
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

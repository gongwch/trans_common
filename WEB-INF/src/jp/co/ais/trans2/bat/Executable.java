package jp.co.ais.trans2.bat;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.db.*;

/**
 * バッチ処理ベース
 */
public abstract class Executable {

	/** コンテナ */
	protected S2Container container;

	/** TRANS DB接続設定ファイル */
	protected String transDBj2ee = "j2ee.dicon";

	/**
	 * 開始
	 */
	public void start() {
		// 初期準備
		if (!init()) {
			return;
		}

		// 処理実行
		execute();
	}

	/**
	 * 実行準備<br>
	 * 接続確認、設定ファイル読込＆整合性チェック
	 * 
	 * @return false:失敗
	 */
	protected boolean init() {

		try {
			container = S2ContainerFactory.create(getContainerName());
			SingletonS2ContainerFactory.setContainer(container);

			// 設定ファイルの読込
			if (!readConfig()) {
				return false;
			}

			// DB接続確認
			if (!canConnectDB()) {
				writeError("データベースへの接続に失敗しました。");
				return false;
			}

			// 設定検証
			return verifyConfig();

		} catch (Exception ex) {
			writeError(ex);
			return false;
		}
	}

	/**
	 * コンテナ定義名
	 * 
	 * @return コンテナ定義名
	 */
	protected abstract String getContainerName();

	/**
	 * DB接続確認
	 * 
	 * @return true:接続状態、false:非接続状態
	 */
	protected boolean canConnectDB() {
		Connection con = null;

		try {
			for (String name : getDBConfigNames()) {
				S2Container dbContainer = S2ContainerFactory.create(name);
				DataSource dataSource = (DataSource) dbContainer.getComponent(DataSource.class);
				con = dataSource.getConnection();

				if (con == null) {
					writeError("DB接続に失敗しました。");
					return false;
				}

				DBUtil.close(con);
			}

			return true;

		} catch (Exception ex) {
			writeError(ex, "DB接続に失敗しました。");
			return false;

		} finally {
			try {
				DBUtil.close(con);
			} catch (Throwable e) {
				writeError(e, "DB接続に失敗しました。");

			}
		}
	}

	/**
	 * DB接続設定名
	 * 
	 * @return DB接続設定名
	 */
	protected String[] getDBConfigNames() {
		return new String[] { getTransDBConfigName() };
	}

	/**
	 * DB接続設定名
	 * 
	 * @return DB接続設定名
	 */
	protected String getTransDBConfigName() {
		return transDBj2ee;
	}

	/**
	 * 設定ファイル読込
	 * 
	 * @return false:失敗
	 */
	protected abstract boolean readConfig();

	/**
	 * 設定検証
	 * 
	 * @return false:NG
	 */
	protected abstract boolean verifyConfig();

	/**
	 * 実行
	 */
	protected abstract void execute();

	/**
	 * 設定読込.
	 * 
	 * @param key キー
	 * @return 設定値
	 * @throws TException 設定が無い
	 */
	protected String getProperty(String key) throws TException {
		try {
			return ServerConfig.getProperty(key);

		} catch (MissingResourceException e) {
			throw new TException("設定が見つかりません。{0}", key);
		}
	}

	/**
	 * 設定読込.
	 * 
	 * @param key キー
	 * @return 設定値のリスト
	 * @throws TException 設定が無い
	 */
	protected String[] getProperties(String key) throws TException {
		try {
			return ServerConfig.getProperties(key);

		} catch (MissingResourceException e) {
			throw new TException("設定が見つかりません。{0}", key);
		}
	}

	/**
	 * メッセージ取得
	 * 
	 * @param id メッセージID
	 * @param words バインド単語
	 * @return メッセージ
	 */
	protected String getMessage(String id, Object... words) {
		return MessageUtil.convertMessage("ja", id, words);
	}

	/**
	 * 単語取得
	 * 
	 * @param word 単語ID
	 * @return 単語
	 */
	protected String getWord(String word) {
		return MessageUtil.getWord("ja", word);
	}

	/**
	 * デバッグ用ログ
	 * 
	 * @param msg メッセージ
	 */
	protected void writeDebug(String msg) {
		ServerLogger.debug(msg);
	}

	/**
	 * 警告ログ出力
	 * 
	 * @param id メッセージID
	 * @param word バインド文字
	 */
	protected void writeWarning(String id, Object... word) {
		ServerLogger.warning(getMessage(id, word));
	}

	/**
	 * エラーログ出力(例外ベース)
	 * 
	 * @param ex 発生例外
	 * @param id メッセージID
	 * @param word バインド文字
	 */
	protected void writeError(Throwable ex, String id, Object... word) {
		writeError(id, word);
		writeError(ex);
	}

	/**
	 * エラーログ出力
	 * 
	 * @param id メッセージID
	 * @param word バインド文字
	 */
	protected void writeError(String id, Object... word) {
		ServerLogger.error(getMessage(id, word));
	}

	/**
	 * エラーログ出力(例外ベース)
	 * 
	 * @param ex 発生例外
	 */
	protected void writeError(Throwable ex) {
		Throwable e = ex;

		while (e != null) {
			String message;
			if (e instanceof TException) {
				String id = ((TException) e).getMessageID();
				Object[] words = ((TException) e).getMessageArgs();
				message = getMessage(id, words);

			} else {
				message = e.getMessage();
			}

			ServerLogger.error(message);

			StringBuilder buff = new StringBuilder("\n" + e.getClass().getName());

			StackTraceElement[] els = e.getStackTrace();
			for (StackTraceElement el : els) {
				buff.append("\n").append("  at ").append(el.getClassName());
				buff.append(".").append(el.getMethodName());
				buff.append("(").append(el.getFileName()).append(":").append(el.getLineNumber()).append(")");
			}

			ServerLogger.error(buff.toString());

			e = e.getCause();
		}
	}

}

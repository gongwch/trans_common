package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * 実行ログ管理インターフェース
 * @author AIS
 *
 */
public interface LogManager {

	/**
	 * ログを取る
	 * @param log
	 * @throws TException
	 */
	public void log(Log log) throws TException;

}

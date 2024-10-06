package jp.co.ais.trans2.model.executedlog;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 実行ログ参照Manager
 */
public interface ExecutedLogManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return 実行ログ参照情報
	 * @throws TException
	 */
	public List<ExecutedLog> get(ExecutedLogSearchCondition condition) throws TException;

	/**
	 * Excel出力
	 * 
	 * @param condition 出力条件
	 * @return 実行ログ参照情報
	 * @throws TException
	 */
	public byte[] getExcel(ExecutedLogSearchCondition condition) throws TException;

}

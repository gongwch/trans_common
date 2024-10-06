package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.logic.system.impl.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログDao
 */
public interface ExecutedLogDao {

	/** BEAN */
	public Class BEAN = ExecutedLog.class;

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insertLog(ExecutedLog dto);
	

	/**
	 * 実行ログ一覧取得
	 * 
	 * @param param 検索パラメータ
	 * @return 実行ログ一覧
	 */
	public List<ExecutedLog> getExecutedLogList(ExecutedLogSearchParam param);

}

package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 油種共通マネージャ
 */
public interface BunkerTypeManager {

	/**
	 * 油種リストの取得
	 * 
	 * @param condition
	 * @return 油種リスト
	 * @throws TException
	 */
	public List<CM_BNKR_TYPE_MST> get(BunkerTypeSearchCondition condition) throws TException;

	/**
	 * Insert
	 * 
	 * @param list
	 * @throws TException
	 */
	public void insert(List<CM_BNKR_TYPE_MST> list) throws TException;

	/**
	 * Get excel
	 * 
	 * @param condition
	 * @return byte excel
	 * @throws TException
	 */
	public byte[] getExcel(BunkerTypeSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(BunkerTypeSearchCondition condition) throws TException;
}

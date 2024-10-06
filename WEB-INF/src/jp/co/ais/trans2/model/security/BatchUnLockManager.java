package jp.co.ais.trans2.model.security;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * バッチ排他制御を制御するクラス
 */
public interface BatchUnLockManager {

	/**
	 * バッチを検索する.
	 * 
	 * @return list バッチクラス
	 * @throws TException
	 */
	public List<BatchUnLock> get() throws TException;

	/**
	 * バッチ排他を削除する.<br>
	 * 
	 * @param list バッチクラス
	 * @throws TException
	 */
	public void delete(List<BatchUnLock> list) throws TException;

}

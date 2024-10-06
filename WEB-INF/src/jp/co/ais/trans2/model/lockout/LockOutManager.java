package jp.co.ais.trans2.model.lockout;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0027 - ロックアウト管理
 * 
 * @author AIS
 */
public interface LockOutManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @return ロックアウト管理マスタ情報
	 * @throws TException
	 */
	public List<LockOut> get() throws TException;

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param lockoutList 選択情報
	 * @throws TException
	 */
	public void delete(List<LockOut> lockoutList) throws TException;

}
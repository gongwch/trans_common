package jp.co.ais.trans2.model.userunlock;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ログイン解除情報インターフェース。
 * 
 * @author AIS
 */
public interface UserUnLockManager {

	/**
	 * 指定条件に該当するログイン解除情報を返す
	 * 
	 * @return 指定条件に該当するログイン解除情報
	 * @throws TException
	 */
	public List<UserUnLock> get() throws TException;

	/**
	 * ログイン解除情報を削除する。（複数版）
	 * 
	 * @param list
	 * @throws TException
	 */
	public void delete(List<UserUnLock> list) throws TException;

	/**
	 * ログイン解除情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(UserUnLock bean) throws TException;
}

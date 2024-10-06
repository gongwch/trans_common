package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ユーザーロールマネージャ
 * 
 * @author AIS
 */
public interface UserRoleManager {

	/**
	 * 指定条件に該当するデータを返す
	 * 
	 * @param condition 検索条件
	 * @return 対象データリスト
	 * @throws TException
	 */
	public List<UserRole> get(UserSearchCondition condition) throws TException;

	/**
	 * 登録する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(UserRole bean) throws TException;

	/**
	 * 修正する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(UserRole bean) throws TException;

	/**
	 * 削除する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(UserRole bean) throws TException;

	/**
	 * ユーザーロール(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return 対象データリスト
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException;

}

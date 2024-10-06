package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 承認権限ロールインターフェース。
 * 
 * @author AIS
 */
public interface AprvRoleGroupManager {

	/**
	 * 指定条件に該当する情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する取引先情報
	 * @throws TException
	 */
	public List<AprvRoleGroup> get(AprvRoleGroupSearchCondition condition) throws TException;

	/**
	 * 情報を登録する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(AprvRoleGroup bean) throws TException;

	/**
	 * 情報を修正する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(AprvRoleGroup bean) throws TException;

	/**
	 * 情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(AprvRoleGroup bean) throws TException;

	/**
	 * エクセルを出力する。
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcel(AprvRoleGroupSearchCondition condition) throws TException;

}

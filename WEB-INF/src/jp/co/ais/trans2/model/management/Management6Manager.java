package jp.co.ais.trans2.model.management;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 管理6インターフェース。
 * 
 * @author AIS
 */
public interface Management6Manager {

	/**
	 * 指定条件に該当する管理6情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理6情報
	 * @throws TException
	 */
	public List<Management6> get(Management6SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理6情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理6情報
	 * @throws TException
	 */
	public List<Management6> getRef(Management6SearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する管理6情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する管理6情報
	 * @throws TException
	 */
	public byte[] getExcel(Management6SearchCondition condition) throws TException;

	/**
	 * 管理6を登録する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void entry(Management6 management) throws TException;

	/**
	 * 管理6を修正する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void modify(Management6 management) throws TException;

	/**
	 * 管理6を削除する。
	 * 
	 * @param management
	 * @throws TException
	 */
	public void delete(Management6 management) throws TException;

}

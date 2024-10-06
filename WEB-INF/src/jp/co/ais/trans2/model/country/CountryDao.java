package jp.co.ais.trans2.model.country;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 国情報インターフェース。
 */
public interface CountryDao {

	/**
	 * 指定条件に該当する国情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する国情報
	 * @throws TException
	 */
	public List<Country> get(CountrySearchCondition condition) throws TException;

	/**
	 * 国を登録する。
	 * 
	 * @param customer 国
	 * @throws TException
	 */
	public void entry(Country customer) throws TException;

	/**
	 * 国を修正する。
	 * 
	 * @param customer 国
	 * @throws TException
	 */
	public void modify(Country customer) throws TException;

	/**
	 * 国を削除する。
	 * 
	 * @param customer 国
	 * @throws TException
	 */
	public void delete(Country customer) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CountrySearchCondition condition) throws TException;
}

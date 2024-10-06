package jp.co.ais.trans2.model.currency;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 通貨情報インターフェース。
 * 
 * @author AIS
 */
public interface CurrencyManager {

	/**
	 * 指定条件に該当する通貨情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する通貨情報
	 * @throws TException
	 */
	public List<Currency> get(CurrencySearchCondition condition) throws TException;

	/**
	 * 通貨情報を登録する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(Currency bean) throws TException;

	/**
	 * 通貨情報を修正する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(Currency bean) throws TException;

	/**
	 * 通貨情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(Currency bean) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CurrencySearchCondition condition) throws TException;

	/**
	 * エクセル
	 * 
	 * @param condition
	 * @return 通貨情報
	 * @throws TException
	 */
	public byte[] getExcel(CurrencySearchCondition condition) throws TException;

}

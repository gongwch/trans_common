package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 銀行口座インターフェース。
 * 
 * @author AIS
 */
public interface BankAccountManager {

	/**
	 * 指定条件に該当する銀行口座情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座情報
	 * @throws TException
	 */
	public List<BankAccount> get(BankAccountSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する銀行口座情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座情報
	 * @throws TException
	 */
	public List<BankAccount> getRef(BankAccountSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する銀行口座コードを返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座コード
	 * @throws TException
	 */
	public BankAccount getBankAccount(BankAccountSearchCondition condition) throws TException;

	/**
	 * 銀行口座を登録する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void entry(BankAccount bean) throws TException;

	/**
	 * 銀行口座を修正する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void modify(BankAccount bean) throws TException;

	/**
	 * 銀行口座を削除する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void delete(BankAccount bean) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(BankAccountSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(BankAccountSearchCondition condition) throws TException;

}

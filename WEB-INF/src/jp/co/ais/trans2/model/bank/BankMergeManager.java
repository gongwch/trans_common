package jp.co.ais.trans2.model.bank;

import java.util.*;

import jp.co.ais.trans.common.except.*;



/**
 * 銀行統廃合のインターフェース
 */
public interface BankMergeManager {

	/**
	 * 銀行統廃合のデータを検索
	 * @return 銀行統廃合のデータ
	 * 
	 * @throws TException
	 */
	public List<BankMerge> get() throws TException;
	
	/**
	 * 銀行統廃合のデータを登録する。
	 * 
	 * @param bankMe
	 * @param i 
	 * @throws TException
	 */
	public void entry(BankMerge bankMe, int i) throws TException;

	/**
	 * @param bankMe
	 * @return 追加、更新行数
	 * @throws TException
	 */
	public List<BankMerge> seachcount(List<BankMerge> bankMe) throws TException;
	
	/**
	 * データ更新。
	 * 
	 * @param bankMe
	 * @throws TException
	 */
	public void updata(List<BankMerge> bankMe) throws TException;
	
}

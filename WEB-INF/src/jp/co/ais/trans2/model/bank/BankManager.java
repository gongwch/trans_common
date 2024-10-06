package jp.co.ais.trans2.model.bank;

import java.util.*;
import jp.co.ais.trans.common.except.*;

/**
 * 銀行情報インターフェース。
 * 
 * @author AIS
 */
public interface BankManager {

	/**
	 * 指定条件に該当する銀行情報を返す
	 * 支店情報は含まれない。
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行情報
	 * @throws TException
	 */
	public List<Bank> get(BankSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する銀行・支店情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行・支店情報
	 * @throws TException
	 */
	public List<Bank> get(BranchSearchCondition condition) throws TException;

	/**
	 * 銀行を登録する。
	 * 
	 * @param bean 銀行
	 * @throws TException
	 */
	public void entry(Bank bean) throws TException;

	/**
	 * 銀行を修正する。
	 * 
	 * @param bean 銀行
	 * @throws TException
	 */
	public void modify(Bank bean) throws TException;

	/**
	 * 銀行を削除する。
	 * 
	 * @param bean 銀行
	 * @throws TException
	 */
	public void delete(Bank bean) throws TException;
	
	/**
	 * エクセル
	 * 
	 * @param condition
	 * @return エクセル形式の銀行一覧
	 * @throws TException
	 */
	public byte[] getExcel(BranchSearchCondition condition) throws TException;


}

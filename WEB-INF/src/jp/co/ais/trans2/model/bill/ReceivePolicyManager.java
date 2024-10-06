package jp.co.ais.trans2.model.bill;

import jp.co.ais.trans.common.except.*;

/**
 * 入金方針マスタインターフェース。
 * 
 * @author AIS
 */
public interface ReceivePolicyManager {

	/**
	 * 対象会社コードの入金方針マスタ情報を返す
	 * 
	 * @param companyCode 対象会社コード
	 * @return 指定条件に該当する入金方針マスタ情報
	 * @throws TException
	 */
	public ReceivePolicy get(String companyCode) throws TException;

	/**
	 * ログイン会社コードの入金方針マスタ情報を返す
	 * 
	 * @return 入金方針マスタ情報
	 * @throws TException
	 */
	public ReceivePolicy get() throws TException;
	
	/**
	 * 入金方針マスタを登録する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void entry(ReceivePolicy bean) throws TException;

	/**
	 * 入金方針マスタを修正する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void modify(ReceivePolicy bean) throws TException;

	/**
	 * 入金方針マスタを削除する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void delete(ReceivePolicy bean) throws TException;

}

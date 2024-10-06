package jp.co.ais.trans2.model.remittance;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 送金目的インターフェース
 * 
 * @author AIS
 */
public interface RemittanceManager {

	/**
	 * 指定条件に該当する送金目的情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する摘要情報
	 * @throws TException
	 */
	public List<Remittance> get(RemittanceSearchCondition condition) throws TException;

	/**
	 * 送金目的情報を登録する。
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void entry(Remittance entity) throws TException;

	/**
	 * 送金目的情報を修正する。
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void modify(Remittance entity) throws TException;

	/**
	 * 送金目的情報を削除する。
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void delete(Remittance entity) throws TException;

	/**
	 * エクセルを出力する。
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcel(RemittanceSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する送金目的情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する送金目的情報
	 * @throws TException
	 */
	public List<Remittance> getRemittance(RemittanceSearchCondition condition) throws TException;

	/**
	 * 送金目的情報を登録する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryRemittance(Remittance bean) throws TException;

	/**
	 * 送金目的情報を修正する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modifyRemittance(Remittance bean) throws TException;

	/**
	 * 送金目的情報を削除する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteRemittance(Remittance bean) throws TException;

	/**
	 * エクセルを出力する。
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcelRemittance(RemittanceSearchCondition condition) throws TException;

	/**
	 * エクセルを出力する。
	 * 
	 * @param condition
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcelRemittancePurpose(RemittanceSearchCondition condition) throws TException;

}

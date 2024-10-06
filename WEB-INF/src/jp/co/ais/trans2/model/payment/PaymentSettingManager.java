package jp.co.ais.trans2.model.payment;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * 支払条件インターフェース。
 * 
 * @author AIS
 */
public interface PaymentSettingManager {

	/**
	 * 指定条件に該当する支払方法情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する支払方法情報
	 * @throws TException
	 */
	public List<PaymentSetting> get(PaymentSettingSearchCondition condition) throws TException;

	/**
	 * 支払方法を登録する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void entry(PaymentSetting bean) throws TException;

	/**
	 * 支払方法を修正する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void modify(PaymentSetting bean) throws TException;

	/**
	 * 支払方法を削除する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void delete(PaymentSetting bean) throws TException;

	/**
	 * エクセルを返す
	 * 
	 * @param condition 検索条件
	 * @return 支払条件情報
	 * @throws TException
	 */
	public byte[] getExcel(PaymentSettingSearchCondition condition) throws TException;

	/**
	 * 支払情報一覧をエクセル形式で返す(海外用)
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の一覧
	 * @throws TException
	 */
	public byte[] getExcelForGlobal(PaymentSettingSearchCondition condition) throws TException;

}

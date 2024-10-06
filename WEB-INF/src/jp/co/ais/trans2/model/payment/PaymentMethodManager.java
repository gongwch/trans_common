package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 支払方法インターフェース。
 * 
 * @author AIS
 */
public interface PaymentMethodManager {

	/**
	 * 指定条件に該当する支払方法情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する支払方法情報
	 * @throws TException
	 */
	public List<PaymentMethod> get(PaymentMethodSearchCondition condition) throws TException;

	/**
	 * 支払方法を登録する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void entry(PaymentMethod bean) throws TException;

	/**
	 * 支払方法を修正する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void modify(PaymentMethod bean) throws TException;

	/**
	 * 支払方法を削除する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void delete(PaymentMethod bean) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PaymentMethodSearchCondition condition) throws TException;

	/**
	 * エクセルファイルを作成する
	 * 
	 * @param condition
	 * @return エクセルファイル
	 * @throws TException
	 */
	public byte[] getExcel(PaymentMethodSearchCondition condition) throws TException;

}

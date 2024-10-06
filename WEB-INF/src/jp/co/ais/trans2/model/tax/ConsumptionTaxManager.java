package jp.co.ais.trans2.model.tax;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 消費税情報インターフェース。
 * 
 * @author AIS
 */
public interface ConsumptionTaxManager {

	/**
	 * 指定条件に該当する消費税情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する消費税情報
	 * @throws TException
	 */
	public List<ConsumptionTax> get(ConsumptionTaxSearchCondition condition) throws TException;

	/**
	 * 消費税情報を登録する。
	 * 
	 * @param tax
	 * @throws TException
	 */
	public void entry(ConsumptionTax tax) throws TException;

	/**
	 * 消費税情報を修正する。
	 * 
	 * @param tax
	 * @param isTriMstUpdate インボイス用
	 * @throws TException
	 */
	public void modify(ConsumptionTax tax, boolean isTriMstUpdate) throws TException;

	/**
	 * 消費税情報を削除する。
	 * 
	 * @param tax
	 * @throws TException
	 */
	public void delete(ConsumptionTax tax) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(ConsumptionTaxSearchCondition condition) throws TException;

	/**
	 * プログラム(エクセル)を返す
	 * 
	 * @param condition 検索条件
	 * @return プログラム
	 * @throws TException
	 */
	public byte[] getExcel(ConsumptionTaxSearchCondition condition) throws TException;

}

package jp.co.ais.trans2.model.employee;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0160EmployeeMaster - 社員マスタ - Interface Class
 * 
 * @author AIS
 */
public interface EmployeeManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return 社員マスタ情報
	 * @throws TException
	 */
	public List<Employee> get(EmployeeSearchCondition condition) throws TException;

	/**
	 * 情報検索 (SELECT)<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 社員マスタ情報
	 * @throws TException
	 */
	public List<Employee> getRef(EmployeeSearchCondition condition) throws TException;

	/**
	 * 情報登録 (INSERT)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void entry(Employee bean) throws TException;

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(Employee bean) throws TException;

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 選択情報
	 * @throws TException
	 */
	public void delete(Employee bean) throws TException;

	/**
	 * 情報検索-Excel出力 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return 社員マスタ情報
	 * @throws TException
	 */
	public byte[] getExcel(EmployeeSearchCondition condition) throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(EmployeeSearchCondition condition) throws TException;
}
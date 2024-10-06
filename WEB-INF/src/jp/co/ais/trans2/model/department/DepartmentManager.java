package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 部門インターフェース。
 * 
 * @author AIS
 */
public interface DepartmentManager {

	/**
	 * 指定条件に該当する部門情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門情報
	 * @throws TException
	 */
	public List<Department> get(DepartmentSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する部門情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門情報
	 * @throws TException
	 */
	public List<Department> getRef(DepartmentSearchCondition condition) throws TException;

	/**
	 * 部門を登録する。
	 * 
	 * @param department 部門
	 * @throws TException
	 */
	public void entry(Department department) throws TException;

	/**
	 * 部門を修正する。
	 * 
	 * @param department 部門
	 * @throws TException
	 */
	public void modify(Department department) throws TException;

	/**
	 * 部門を削除する。
	 * 
	 * @param department 部門
	 * @throws TException
	 */
	public void delete(Department department) throws TException;

	/**
	 * エクセル
	 * 
	 * @param condition
	 * @return 部門情報
	 * @throws TException
	 */
	public byte[] getExcel(DepartmentSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当する部門組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門組織情報
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * 指定条件に該当する部門組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門組織情報(部門階層マスタ用)
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganizationData(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * 部門階層LEVEL0を登録する。(新規)
	 * 
	 * @param departmentOrganization 部門階層LEVEL0
	 * @throws TException
	 */
	public void entryDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * 部門階層LEVEL0、前の組織コードの部門階層→新組織コードの部門階層へコピーする。(複写)
	 * 
	 * @param departmentOrganization 部門階層LEVEL0、新組織コード
	 * @param oldSskCode 前の組織コード
	 * @throws TException
	 */
	public void entryCopyDepartmentOrganization(DepartmentOrganization departmentOrganization, String oldSskCode)
		throws TException;

	/**
	 * 部門階層を登録する。
	 * 
	 * @param sskCode
	 * @param list 部門階層
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, List<DepartmentOrganization> list) throws TException;

	/**
	 * 部門階層を削除する。
	 * 
	 * @param departmentOrganization 部門階層
	 * @throws TException
	 */
	public void deleteDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * 部門階層エクセル
	 * 
	 * @param condition
	 * @return 部門階層情報
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationExcel(DepartmentOrganizationSearchCondition condition) throws TException;

	/**
	 * 部門階層マスタに登録されていない部門リスト取得
	 * 
	 * @param companyCode
	 * @param depCondition
	 * @return 部門階層マスタに登録されていない部門リスト
	 * @throws TException
	 */
	public List<String> getNotExistDepartmentList(String companyCode, DepartmentOutputCondition depCondition)
		throws TException;

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(DepartmentSearchCondition condition) throws TException;
}

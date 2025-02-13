package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 部門階層インターフェース。
 * 
 * @author AIS
 */
public interface DepartmentOrganizationManager {

	/**
	 * 指定条件に該当する部門情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門情報
	 * @throws TException
	 */
	public List<Department> get(DepartmentSearchCondition condition) throws TException;

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
	 * 指定条件に該当する部門組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する部門組織情報(部門階層マスタ用)
	 * @throws TException
	 */
	public DepartmentOrganization getDepartmentOrganizationName(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * 部門階層LEVEL0を登録する。(新規)
	 * 
	 * @param departmentOrganization 部門階層LEVEL0
	 * @throws TException
	 */
	public void entryDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * 部門階層と組織名称を登録する。
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list 部門階層
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, String sskName, List<DepartmentOrganization> list)
		throws TException;

	/**
	 * 部門階層LEVEL名称登録(新規)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentOrganizationName(DepartmentOrganization bean) throws TException;

	/**
	 * 部門階層を削除する。
	 * 
	 * @param departmentOrganization 部門階層
	 * @throws TException
	 */
	public void deleteDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * 部門階層名称一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の部門階層一覧
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationNameExcel(DepartmentOrganizationSearchCondition condition) throws TException;

	/**
	 * 部門階層名称を登録する。
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentNameOrganization(DepartmentOrganization bean) throws TException;
}

package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans2.model.*;

/**
 * 部門の出力単位条件
 * 
 * @author AIS
 */
public class DepartmentOutputCondition extends BasicOutputCondition<Department> {

	/**
	 * @return 開始部門
	 */
	public Department getDepartmentFrom() {
		return getFrom();
	}

	/**
	 * 開始部門
	 * 
	 * @param departmentFrom
	 */
	public void setDepartmentFrom(Department departmentFrom) {
		setFrom(departmentFrom);
	}

	/**
	 * @return 組織コード
	 */
	public String getDepartmentOrganizationCode() {
		return getOrganizationCode();
	}

	/**
	 * 組織コード
	 * 
	 * @param departmentOrganizationCode
	 */
	public void setDepartmentOrganizationCode(String departmentOrganizationCode) {
		setOrganizationCode(departmentOrganizationCode);
	}

	/**
	 * @return 終了部門
	 */
	public Department getDepartmentTo() {
		return getTo();
	}

	/**
	 * 終了部門
	 * 
	 * @param departmentTo
	 */
	public void setDepartmentTo(Department departmentTo) {
		setTo(departmentTo);
	}

	/**
	 * @return 配下部門を含むか
	 */
	public boolean isIncludeUnderDepartment() {
		return isIncludeUnder();
	}

	/**
	 * 配下部門を含むか
	 * 
	 * @param includeUnderDepartment
	 */
	public void setIncludeUnderDepartment(boolean includeUnderDepartment) {
		setIncludeUnder(includeUnderDepartment);
	}

	/**
	 * @return 個別選択部門
	 */
	public List<Department> getOptionalDepartments() {
		return getOptionalEntities();
	}

	/**
	 * 個別選択部門
	 * 
	 * @param optionalDepartments
	 */
	public void setOptionalDepartments(List<Department> optionalDepartments) {
		setOptionalEntities(optionalDepartments);
	}

	/**
	 * @return 上位部門
	 */
	public Department getSuperiorDepartment() {
		return getSuperior();
	}

	/**
	 * 上位部門
	 * 
	 * @param superiorDepartment
	 */
	public void setSuperiorDepartment(Department superiorDepartment) {
		setSuperior(superiorDepartment);
	}

	/**
	 * 個別選択された部門のコードリストを返す
	 * 
	 * @return 個別選択された部門のコードリスト
	 */
	public List<String> getOptionalDepartmentsCode() {
		return getOptionalEntitiesCode();
	}

	/**
	 * テーブルIDの取得
	 * 
	 * @return tableID テーブルID
	 */
	@Override
	public String getTableID() {
		return "DPK_MST";
	}

}

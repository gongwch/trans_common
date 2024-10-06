package jp.co.ais.trans.master.entity;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * 財務諸表に対するユーザーの開示セキュリティです
 * @author AIS
 *
 */
public class DisclosureRegulationOfFinancialStatement extends TransferBase {

	private static final long serialVersionUID = 8207616769170731748L;

	/** 会社コード */
	protected String companyCode = null;

	/** ユーザーコード */
	protected String userCode = null;

	/** 科目体系コード */
	protected String itemOrganizationCode = null;

	/** 組織コード */
	protected String organizationCode = null;

	/** 階層レベル */
	protected int departmentLevel = -1;

	/** 上位部門コード */
	protected String upperDepartmentCode = null;

	/** 部門コード */
	protected String departmentCode = null;

	/** 配下部門を含むか */
	protected boolean includeLowerDepartment = false;

	/**
	 * @return 会社コードを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode 会社コードを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return 部門コードを戻します。
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * @param departmentCode 部門コードを設定します。
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * @return 階層レベルを戻します。
	 */
	public int getDepartmentLevel() {
		return departmentLevel;
	}

	/**
	 * @param departmentLevel 階層レベルを設定します。
	 */
	public void setDepartmentLevel(int departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	/**
	 * @return 配下部門を含むかを戻します。
	 */
	public boolean isIncludeLowerDepartment() {
		return includeLowerDepartment;
	}

	/**
	 * @param includeLowerDepartment 配下部門を含むかを設定します。
	 */
	public void setIncludeLowerDepartment(boolean includeLowerDepartment) {
		this.includeLowerDepartment = includeLowerDepartment;
	}

	/**
	 * @return 科目体系コードを戻します。
	 */
	public String getItemOrganizationCode() {
		return itemOrganizationCode;
	}

	/**
	 * @param itemOrganizationCode 科目体系コードを設定します。
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		this.itemOrganizationCode = itemOrganizationCode;
	}

	/**
	 * @return 組織コードを戻します。
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * @param organizationCode 組織コードを設定します。
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return 上位部門コードを戻します。
	 */
	public String getUpperDepartmentCode() {
		return upperDepartmentCode;
	}

	/**
	 * @param upperDepartmentCode 上位部門コードを設定します。
	 */
	public void setUpperDepartmentCode(String upperDepartmentCode) {
		this.upperDepartmentCode = upperDepartmentCode;
	}

	/**
	 * @return ユーザーコードを戻します。
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode ユーザーコードを設定します。
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}

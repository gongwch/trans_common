package jp.co.ais.trans2.model.department;

import jp.co.ais.trans.common.dt.*;

/**
 * 部門組織の検索条件
 * 
 * @author AIS
 */
public class DepartmentOrganizationSearchCondition extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/**
	 * @return codeを戻します。
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code codeを設定します。
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return companyCodeを戻します。
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCodeを設定します。
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}

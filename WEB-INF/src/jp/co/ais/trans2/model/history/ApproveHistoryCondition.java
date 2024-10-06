package jp.co.ais.trans2.model.history;

import jp.co.ais.trans.common.dt.*;

/**
 * 承認履歴条件
 */
public class ApproveHistoryCondition extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 社員コード */
	protected String employeeCode = null;

	/** 伝票番号 */
	protected String slipNo = null;

	/** 承認ロールコード */
	protected String aprvRoleCode = null;

	/**
	 * 会社コードの取得
	 * 
	 * @return companyCode 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 社員コードの取得
	 * 
	 * @return employeeCode 社員コード
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 社員コードの設定
	 * 
	 * @param employeeCode 社員コード
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 伝票番号の取得
	 * 
	 * @return slipNo 伝票番号
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param slipNo 伝票番号
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * 承認ロールコードの取得
	 * 
	 * @return aprvRoleCode 承認ロールコード
	 */
	public String getAprvRoleCode() {
		return aprvRoleCode;
	}

	/**
	 * 承認ロールコードの設定
	 * 
	 * @param aprvRoleCode 承認ロールコード
	 */
	public void setAprvRoleCode(String aprvRoleCode) {
		this.aprvRoleCode = aprvRoleCode;
	}

}

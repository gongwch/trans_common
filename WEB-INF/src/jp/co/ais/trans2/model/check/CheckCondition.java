package jp.co.ais.trans2.model.check;

import jp.co.ais.trans.common.dt.*;

/**
 * チェック条件
 */
public class CheckCondition extends TransferBase {

	/**
	 * チェックタイプ
	 */
	public static class CHECK_TYPE {

		/** 科目 */
		public static final int ITEM = 1;

		/** 補助科目 */
		public static final int SUB_ITEM = 2;

		/** 内訳科目 */
		public static final int DETAIL_ITEM = 3;

		/** 計上部門 */
		public static final int DEPARTMENT = 4;

		/** 取引先 */
		public static final int CUSTOMER = 5;

		/** 取引先支払条件 */
		public static final int PAYMENT_SETTING = 6;

		/** 社員 */
		public static final int EMPLOYEE = 7;

		/** 管理１ */
		public static final int MANAGEMENT_1 = 8;

		/** 管理２ */
		public static final int MANAGEMENT_2 = 9;

		/** 管理３ */
		public static final int MANAGEMENT_3 = 10;

		/** 管理４ */
		public static final int MANAGEMENT_4 = 11;

		/** 管理５ */
		public static final int MANAGEMENT_5 = 12;

		/** 管理６ */
		public static final int MANAGEMENT_6 = 13;
	}

	/** チェックタイプ */
	protected int type = -1;

	/** 会社コード */
	protected String companyCode = null;

	/** 科目コード */
	protected String itemCode = null;

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 内訳科目コード */
	protected String detailItemCode = null;

	/** 計上部門コード */
	protected String departmentCode = null;

	/** 取引先コード */
	protected String customerCode = null;

	/** 取引先支払条件コード */
	protected String paymentSettingCode = null;

	/** 社員コード */
	protected String employeeCode = null;

	/** 管理１コード */
	protected String management1Code = null;

	/** 管理２コード */
	protected String management2Code = null;

	/** 管理３コード */
	protected String management3Code = null;

	/** 管理４コード */
	protected String management4Code = null;

	/** 管理５コード */
	protected String management5Code = null;

	/** 管理６コード */
	protected String management6Code = null;

	/**
	 * コンストラクター
	 * 
	 * @param type
	 */
	public CheckCondition(int type) {
		this.type = type;
	}

	/**
	 * チェックタイプの取得
	 * 
	 * @return type チェックタイプ
	 */
	public int getType() {
		return type;
	}

	/**
	 * チェックタイプの設定
	 * 
	 * @param type チェックタイプ
	 */
	public void setType(int type) {
		this.type = type;
	}

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
	 * 科目コードの取得
	 * 
	 * @return itemCode 科目コード
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * 科目コードの設定
	 * 
	 * @param itemCode 科目コード
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * 補助科目コードの取得
	 * 
	 * @return subItemCode 補助科目コード
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * 補助科目コードの設定
	 * 
	 * @param subItemCode 補助科目コード
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * 内訳科目コードの取得
	 * 
	 * @return detailItemCode 内訳科目コード
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * 内訳科目コードの設定
	 * 
	 * @param detailItemCode 内訳科目コード
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * 計上部門コードの取得
	 * 
	 * @return departmentCode 計上部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 計上部門コードの設定
	 * 
	 * @param departmentCode 計上部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 取引先コードの取得
	 * 
	 * @return customerCode 取引先コード
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * 取引先コードの設定
	 * 
	 * @param customerCode 取引先コード
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * 取引先支払条件コードの取得
	 * 
	 * @return paymentSettingCode 取引先支払条件コード
	 */
	public String getPaymentSettingCode() {
		return paymentSettingCode;
	}

	/**
	 * 取引先支払条件コードの設定
	 * 
	 * @param paymentSettingCode 取引先支払条件コード
	 */
	public void setPaymentSettingCode(String paymentSettingCode) {
		this.paymentSettingCode = paymentSettingCode;
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
	 * 管理１コードの取得
	 * 
	 * @return management1Code 管理１コード
	 */
	public String getManagement1Code() {
		return management1Code;
	}

	/**
	 * 管理１コードの設定
	 * 
	 * @param management1Code 管理１コード
	 */
	public void setManagement1Code(String management1Code) {
		this.management1Code = management1Code;
	}

	/**
	 * 管理２コードの取得
	 * 
	 * @return management2Code 管理２コード
	 */
	public String getManagement2Code() {
		return management2Code;
	}

	/**
	 * 管理２コードの設定
	 * 
	 * @param management2Code 管理２コード
	 */
	public void setManagement2Code(String management2Code) {
		this.management2Code = management2Code;
	}

	/**
	 * 管理３コードの取得
	 * 
	 * @return management3Code 管理３コード
	 */
	public String getManagement3Code() {
		return management3Code;
	}

	/**
	 * 管理３コードの設定
	 * 
	 * @param management3Code 管理３コード
	 */
	public void setManagement3Code(String management3Code) {
		this.management3Code = management3Code;
	}

	/**
	 * 管理４コードの取得
	 * 
	 * @return management4Code 管理４コード
	 */
	public String getManagement4Code() {
		return management4Code;
	}

	/**
	 * 管理４コードの設定
	 * 
	 * @param management4Code 管理４コード
	 */
	public void setManagement4Code(String management4Code) {
		this.management4Code = management4Code;
	}

	/**
	 * 管理５コードの取得
	 * 
	 * @return management5Code 管理５コード
	 */
	public String getManagement5Code() {
		return management5Code;
	}

	/**
	 * 管理５コードの設定
	 * 
	 * @param management5Code 管理５コード
	 */
	public void setManagement5Code(String management5Code) {
		this.management5Code = management5Code;
	}

	/**
	 * 管理６コードの取得
	 * 
	 * @return management6Code 管理６コード
	 */
	public String getManagement6Code() {
		return management6Code;
	}

	/**
	 * 管理６コードの設定
	 * 
	 * @param management6Code 管理６コード
	 */
	public void setManagement6Code(String management6Code) {
		this.management6Code = management6Code;
	}

}

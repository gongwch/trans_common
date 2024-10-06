package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.*;

/**
 * 付替情報
 */
public class TransferConfig extends TransferBase {

	/** 会社コード */
	private String companyCode;

	/** 付替会社コード */
	private String transferCompanyCode;

	/** 付替計上部門コード */
	private String transferDepertmentCode;

	/** 付替計上部門名称 */
	private String transferDepertmentName;

	/** 付替計上部門略称 */
	private String transferDepertmentNames;

	/** 付替科目コード */
	private String transferItemCode;

	/** 付替科目名称 */
	private String transferItemName;

	/** 付替科目略称 */
	private String transferItemNames;

	/** 付替補助科目コード */
	private String transferSubItemCode;

	/** 付替補助科目名称 */
	private String transferSubItemName;

	/** 付替補助科目略称 */
	private String transferSubItemNames;

	/** 付替内訳科目コード */
	private String transferDetailItemCode;

	/** 付替内訳科目名称 */
	private String transferDetailItemName;

	/** 付替内訳科目略称 */
	private String transferDetailItemNames;

	/** 付替取引先コード */
	private String transferCustomerCode;

	/** 付替取引先名称 */
	private String transferCustomerName;

	/** 付替取引先略称 */
	private String transferCustomerNames;

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コード
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 付替会社コード
	 * 
	 * @return 付替会社コード
	 */
	public String getTransferCompanyCode() {
		return transferCompanyCode;
	}

	/**
	 * 付替会社コード
	 * 
	 * @param transferCompanyCode 付替会社コード
	 */
	public void setTransferCompanyCode(String transferCompanyCode) {
		this.transferCompanyCode = transferCompanyCode;
	}

	/**
	 * 付替計上部門コード
	 * 
	 * @return 付替計上部門コード
	 */
	public String getTransferDepertmentCode() {
		return transferDepertmentCode;
	}

	/**
	 * 付替計上部門コード
	 * 
	 * @param transferDepertmentCode 付替計上部門コード
	 */
	public void setTransferDepertmentCode(String transferDepertmentCode) {
		this.transferDepertmentCode = transferDepertmentCode;
	}

	/**
	 * 付替内訳科目コード
	 * 
	 * @return 付替内訳科目コード
	 */
	public String getTransferDetailItemCode() {
		return transferDetailItemCode;
	}

	/**
	 * 付替内訳科目コード
	 * 
	 * @param transferDetailItemCode 付替内訳科目コード
	 */
	public void setTransferDetailItemCode(String transferDetailItemCode) {
		this.transferDetailItemCode = transferDetailItemCode;
	}

	/**
	 * 付替科目コード
	 * 
	 * @return 付替科目コード
	 */
	public String getTransferItemCode() {
		return transferItemCode;
	}

	/**
	 * 付替科目コード
	 * 
	 * @param transferItemCode 付替科目コード
	 */
	public void setTransferItemCode(String transferItemCode) {
		this.transferItemCode = transferItemCode;
	}

	/**
	 * 付替補助科目コード
	 * 
	 * @return 付替補助科目コード
	 */
	public String getTransferSubItemCode() {
		return transferSubItemCode;
	}

	/**
	 * 付替補助科目コード
	 * 
	 * @param transferSubItemCode 付替補助科目コード
	 */
	public void setTransferSubItemCode(String transferSubItemCode) {
		this.transferSubItemCode = transferSubItemCode;
	}

	/**
	 * 付替計上部門名称
	 * 
	 * @return 付替計上部門名称
	 */
	public String getTransferDepertmentName() {
		return transferDepertmentName;
	}

	/**
	 * 付替計上部門名称
	 * 
	 * @param transferDepertmentName 付替計上部門名称
	 */
	public void setTransferDepertmentName(String transferDepertmentName) {
		this.transferDepertmentName = transferDepertmentName;
	}

	/**
	 * 付替計上部門略称
	 * 
	 * @return 付替計上部門略称
	 */
	public String getTransferDepertmentNames() {
		return transferDepertmentNames;
	}

	/**
	 * 付替計上部門略称
	 * 
	 * @param transferDepertmentNames 付替計上部門略称
	 */
	public void setTransferDepertmentNames(String transferDepertmentNames) {
		this.transferDepertmentNames = transferDepertmentNames;
	}

	/**
	 * 付替内訳科目名称
	 * 
	 * @return 付替内訳科目名称
	 */
	public String getTransferDetailItemName() {
		return transferDetailItemName;
	}

	/**
	 * 付替内訳科目名称
	 * 
	 * @param transferDetailItemName 付替内訳科目名称
	 */
	public void setTransferDetailItemName(String transferDetailItemName) {
		this.transferDetailItemName = transferDetailItemName;
	}

	/**
	 * 付替内訳科目略称
	 * 
	 * @return 付替内訳科目略称
	 */
	public String getTransferDetailItemNames() {
		return transferDetailItemNames;
	}

	/**
	 * 付替内訳科目略称
	 * 
	 * @param transferDetailItemNames 付替内訳科目略称
	 */
	public void setTransferDetailItemNames(String transferDetailItemNames) {
		this.transferDetailItemNames = transferDetailItemNames;
	}

	/**
	 * 付替科目名称
	 * 
	 * @return 付替科目名称
	 */
	public String getTransferItemName() {
		return transferItemName;
	}

	/**
	 * 付替科目名称
	 * 
	 * @param transferItemName 付替科目名称
	 */
	public void setTransferItemName(String transferItemName) {
		this.transferItemName = transferItemName;
	}

	/**
	 * 付替科目略称
	 * 
	 * @return 付替科目略称
	 */
	public String getTransferItemNames() {
		return transferItemNames;
	}

	/**
	 * 付替科目略称
	 * 
	 * @param transferItemNames 付替科目略称
	 */
	public void setTransferItemNames(String transferItemNames) {
		this.transferItemNames = transferItemNames;
	}

	/**
	 * 付替補助科目名称
	 * 
	 * @return 付替補助科目名称
	 */
	public String getTransferSubItemName() {
		return transferSubItemName;
	}

	/**
	 * 付替補助科目名称
	 * 
	 * @param transferSubItemName 付替補助科目名称
	 */
	public void setTransferSubItemName(String transferSubItemName) {
		this.transferSubItemName = transferSubItemName;
	}

	/**
	 * 付替補助科目略称
	 * 
	 * @return 付替補助科目略称
	 */
	public String getTransferSubItemNames() {
		return transferSubItemNames;
	}

	/**
	 * 付替補助科目略称
	 * 
	 * @param transferSubItemNames 付替補助科目略称
	 */
	public void setTransferSubItemNames(String transferSubItemNames) {
		this.transferSubItemNames = transferSubItemNames;
	}

	/**
	 * 付替取引先コード
	 * 
	 * @return 付替取引先コード
	 */
	public String getTransferCustomerCode() {
		return transferCustomerCode;
	}

	/**
	 * 付替取引先コード
	 * 
	 * @param transferCustomerCode 付替取引先コード
	 */
	public void setTransferCustomerCode(String transferCustomerCode) {
		this.transferCustomerCode = transferCustomerCode;
	}

	/**
	 * 付替取引先名称
	 * 
	 * @return 付替取引先名称
	 */
	public String getTransferCustomerName() {
		return transferCustomerName;
	}

	/**
	 * 付替取引先名称
	 * 
	 * @param transferCustomerName 付替取引先名称
	 */
	public void setTransferCustomerName(String transferCustomerName) {
		this.transferCustomerName = transferCustomerName;
	}

	/**
	 * 付替取引先略称
	 * 
	 * @return 付替取引先略称
	 */
	public String getTransferCustomerNames() {
		return transferCustomerNames;
	}

	/**
	 * 付替取引先略称
	 * 
	 * @param transferCustomerNames 付替取引先略称
	 */
	public void setTransferCustomerNames(String transferCustomerNames) {
		this.transferCustomerNames = transferCustomerNames;
	}
}

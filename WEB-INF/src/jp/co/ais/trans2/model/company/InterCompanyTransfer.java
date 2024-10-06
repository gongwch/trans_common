package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;

/**
 * 付替会社情報
 * 
 * @author AIS
 */
public class InterCompanyTransfer extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 付替会社情報 */
	protected Company transferCompany = null;

	/** 付替部門情報 */
	protected Department department = null;

	/** 付替科目情報 */
	protected Item item = null;

	/** 付替取引先情報 */
	protected Customer customer = null;

	public Company getTransferCompany() {
		return transferCompany;
	}

	public void setTransferCompany(Company transferCompany) {
		this.transferCompany = transferCompany;
	}

	/**
	 * @return department
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * @param department
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * @return item
	 */
	public Item getItem() {
		return item;
	}

	/**
	 * @param item
	 */
	public void setItem(Item item) {
		this.item = item;
	}

	/**
	 * @return customer
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * @param customer
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

}

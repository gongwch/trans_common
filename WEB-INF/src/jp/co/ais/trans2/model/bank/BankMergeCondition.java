package jp.co.ais.trans2.model.bank;

import java.io.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.user.*;


/**
 * 銀行統廃合輸出条件
 */
public class BankMergeCondition extends TransferBase{

	/** 会社コード */
	protected Company company;
	
	/** ユーザーコード */
	protected User user;
	
	/** 旧銀行コード */
	protected String oldBankCode;
	
	/** 旧銀行支店コード */
	protected String oldBankOfficeCode;
	
	/** 新銀行コード */
	protected String NewBankCode;
	
	/** 新銀行支店コード */
	protected String NewBankOfficeCode;
	
	/** エクセルファイル */
	protected File excelFile;

	
	/**
	 * @return company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * @param company
	 */
	public void setCompany(Company company) {
	this.company = company;}
	

	
	/**
	 * @return user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * @param user
	 */
	public void setUser(User user) {
	this.user = user;}
	

	
	/**
	 * @return oldBankCode
	 */
	public String getOldBankCode() {
		return oldBankCode;
	}

	/**
	 * @param oldBankCode
	 */
	public void setOldBankCode(String oldBankCode) {
	this.oldBankCode = oldBankCode;}
	

	
	/**
	 * @return oldBankOfficeCode
	 */
	public String getOldBankOfficeCode() {
		return oldBankOfficeCode;
	}

	/**
	 * @param oldBankOfficeCode
	 */
	public void setOldBankOfficeCode(String oldBankOfficeCode) {
	this.oldBankOfficeCode = oldBankOfficeCode;}
	

	
	/**
	 * @return NewBankCode
	 */
	public String getNewBankCode() {
		return NewBankCode;
	}

	
	/**
	 * @param newBankCode
	 */
	public void setNewBankCode(String newBankCode) {
		this.NewBankCode = newBankCode;
	}

	
	/**
	 * @return NewBankOfficeCode
	 */
	public String getNewBankOfficeCode() {
		return NewBankOfficeCode;
	}

	
	/**
	 * @param newBankOfficeCode
	 */
	public void setNewBankOfficeCode(String newBankOfficeCode) {
		this.NewBankOfficeCode = newBankOfficeCode;
	}

	
	/**
	 * @return excelFile
	 */
	public File getExcelFile() {
		return excelFile;
	}

	/**
	 * @param excelFile
	 */
	public void setExcelFile(File excelFile) {
		this.excelFile = excelFile;
		
	}
	
	
	
	
}

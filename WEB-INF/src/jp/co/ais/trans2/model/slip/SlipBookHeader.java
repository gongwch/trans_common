package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.define.*;

/**
 * 伝票帳票ヘッダー
 * 
 * @author AIS
 */
public class SlipBookHeader extends LedgerSheetHeader {

	/** タイトル */
	protected String title = null;

	/** 伝票番号 */
	protected String SlipNo = null;

	/** 伝票日付 */
	protected Date slipDate = null;

	/** 入力部門コード */
	protected String departmentCode = null;

	/** 入力部門名称 */
	protected String departmentName = null;

	/** 入力者コード */
	protected String employeeCode = null;

	/** 入力者名称 */
	protected String employeeName = null;

	/** 証憑番号 */
	protected String voucherNo = null;

	/** 摘要コード */
	protected String remarkCode = null;

	/** 摘要 */
	protected String remark = null;

	/** 修正回数 */
	protected int updateCount = 0;

	/** 会計帳簿 */
	protected AccountBook accountBook = null;

	/** 決算段階 */
	protected int settlementStage = 0;

	/**
	 * タイトルの取得
	 * 
	 * @return title タイトル
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * タイトルの設定
	 * 
	 * @param title タイトル
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * 伝票番号の取得
	 * 
	 * @return SlipNo 伝票番号
	 */
	public String getSlipNo() {
		return SlipNo;
	}

	/**
	 * 伝票番号の設定
	 * 
	 * @param SlipNo 伝票番号
	 */
	public void setSlipNo(String SlipNo) {
		this.SlipNo = SlipNo;
	}

	/**
	 * 伝票日付の取得
	 * 
	 * @return slipDate 伝票日付
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * 伝票日付の設定
	 * 
	 * @param slipDate 伝票日付
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * 入力部門コードの取得
	 * 
	 * @return departmentCode 入力部門コード
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * 入力部門コードの設定
	 * 
	 * @param departmentCode 入力部門コード
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 入力部門名称の取得
	 * 
	 * @return departmentName 入力部門名称
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 入力部門名称の設定
	 * 
	 * @param departmentName 入力部門名称
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 入力者コードの取得
	 * 
	 * @return employeeCode 入力者コード
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * 入力者コードの設定
	 * 
	 * @param employeeCode 入力者コード
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * 入力者名称の取得
	 * 
	 * @return employeeName 入力者名称
	 */
	public String getEmployeeName() {
		return employeeName;
	}

	/**
	 * 入力者名称の設定
	 * 
	 * @param employeeName 入力者名称
	 */
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	/**
	 * 証憑番号の取得
	 * 
	 * @return voucherNo 証憑番号
	 */
	public String getVoucherNo() {
		return voucherNo;
	}

	/**
	 * 証憑番号の設定
	 * 
	 * @param voucherNo 証憑番号
	 */
	public void setVoucherNo(String voucherNo) {
		this.voucherNo = voucherNo;
	}

	/**
	 * 摘要コードの取得
	 * 
	 * @return remarkCode 摘要コード
	 */
	public String getRemarkCode() {
		return remarkCode;
	}

	/**
	 * 摘要コードの設定
	 * 
	 * @param remarkCode 摘要コード
	 */
	public void setRemarkCode(String remarkCode) {
		this.remarkCode = remarkCode;
	}

	/**
	 * 摘要の取得
	 * 
	 * @return remark 摘要
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 摘要の設定
	 * 
	 * @param remark 摘要
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * 修正回数の取得
	 * 
	 * @return updateCount 修正回数
	 */
	public int getUpdateCount() {
		return updateCount;
	}

	/**
	 * 修正回数の設定
	 * 
	 * @param updateCount 修正回数
	 */
	public void setUpdateCount(int updateCount) {
		this.updateCount = updateCount;
	}

	/**
	 * 会計帳簿の取得
	 * 
	 * @return accountBook 会計帳簿
	 */
	public AccountBook getAccountBook() {
		return accountBook;
	}

	/**
	 * 会計帳簿の設定
	 * 
	 * @param accountBook 会計帳簿
	 */
	public void setAccountBook(AccountBook accountBook) {
		this.accountBook = accountBook;
	}

	/**
	 * @return 決算段階を戻します。
	 */
	public int getSettlementStage() {
		return settlementStage;
	}

	/**
	 * @param settlementStage 決算段階を設定します。
	 */
	public void setSettlementStage(int settlementStage) {
		this.settlementStage = settlementStage;
	}

}
package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 銀行口座情報
 * 
 * @author AIS
 */
public class BankAccount extends TransferBase implements AutoCompletable, FilterableEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1201636486328477363L;

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 銀行口座名称 */
	protected String name = null;

	/** 銀行口座略称 */
	protected String nameS = null;

	/** 通貨コード */
	protected String currencyCode = null;

	/** 通貨情報 */
	protected Currency currency = null;

	/** 通貨桁 */
	protected int decimalPoint = 0;

	/** 銀行コード */
	protected String bankCode = null;

	/** 銀行名称 */
	protected String bankName = null;

	/** 銀行カナ */
	protected String bankKana = null;

	/** 支店コード */
	protected String branchCode = null;

	/** 支店名称 */
	protected String branchName = null;

	/** 支店カナ */
	protected String branchKana = null;

	/** 銀行検索名称 */
	protected String bankNamek = null;

	/** 支店検索名称 */
	protected String branchNamek = null;

	/** 銀行名称（英字） */
	protected String bankNameE = null;

	/** 支店名称（英字） */
	protected String branchNameE = null;

	/** 振込依頼人コード */
	protected String clientCode = null;

	/** 振込依頼人名 */
	protected String clientName = null;

	/** 振込依頼人名（漢字） */
	protected String clientNameJ = null;

	/** 振込依頼人名（英字） */
	protected String clientNameE = null;

	/** 預金種目 */
	protected DepositKind depositKind = null;

	/** 口座番号 */
	protected String accountNo = null;

	/** 計上部門コード */
	protected String departmentCode = null;

	/** 計上部門名称 */
	protected String departmentName = null;

	/** 計上部門略称 */
	protected String departmentNames = null;

	/** 計上部門検索名称 */
	protected String departmentNamek = null;

	/** 科目コード */
	protected String itemCode = null;

	/** 科目名称 */
	protected String itemName = null;

	/** 科目略称 */
	protected String itemNames = null;

	/** 科目検索名称 */
	protected String itemNamek = null;

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 補助科目名称 */
	protected String subItemName = null;

	/** 補助科目略称 */
	protected String subItemNames = null;

	/** 補助科目検索名称 */
	protected String subItemNamek = null;

	/** 内訳科目コード */
	protected String detailItemCode = null;

	/** 内訳科目名称 */
	protected String detailItemName = null;

	/** 内訳科目略称 */
	protected String detailItemNames = null;

	/** 内訳科目検索名称 */
	protected String detailItemNamek = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 社員ＦＢで使用するか */
	protected boolean useEmployeePayment = false;

	/** 社外ＦＢで使用するか */
	protected boolean useExPayment = false;

	/** IBANコード */
	protected String iBan = null;

	/** 銀行識別子 */
	protected String bankIndentify = null;

	/** 口座識別子 */
	protected String bankAccountIndentify = null;

	/** SWIFTコード */
	protected String swift = null;
	
	/** BANK COUNTRY */
	protected String bankCountry = null;

	/** 住所1 */
	protected String bnkAdr1 = null;

	/** 住所2 */
	protected String bnkAdr2 = null;

	/** 住所1（英字） */
	protected String bnkAdr1E = null;

	/** 住所2（英字） */
	protected String bnkAdr2E = null;

	/**
	 * @return names
	 */
	public String getNames() {
		return getNameS();
	}

	/**
	 * @return namek
	 */
	public String getNamek() {
		return getName();
	}

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * 口座番号を取得する。
	 * 
	 * @return String accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 口座番号を設定する。
	 * 
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 銀行コードを取得する。
	 * 
	 * @return String bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 銀行コードを設定する。
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 銀行名を取得する。
	 * 
	 * @return String bankname
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 銀行名を設定する。
	 * 
	 * @param bankname
	 */
	public void setBankName(String bankname) {
		this.bankName = bankname;
	}

	/**
	 * 銀行カナの取得
	 * 
	 * @return bankKana 銀行カナ
	 */
	public String getBankKana() {
		return bankKana;
	}

	/**
	 * 銀行カナの設定
	 * 
	 * @param bankKana 銀行カナ
	 */
	public void setBankKana(String bankKana) {
		this.bankKana = bankKana;
	}

	/**
	 * 銀行検索名称の取得
	 * 
	 * @return bankNamek 銀行検索名称
	 */
	public String getBankNamek() {
		return bankNamek;
	}

	/**
	 * 銀行検索名称の設定
	 * 
	 * @param bankNamek 銀行検索名称
	 */
	public void setBankNamek(String bankNamek) {
		this.bankNamek = bankNamek;
	}

	/**
	 * branchCodeを取得する。
	 * 
	 * @return String branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * branchCodeを設定する。
	 * 
	 * @param branchCode
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * branchNameを取得する。
	 * 
	 * @return String branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * branchNameを設定する。
	 * 
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * 支店カナの取得
	 * 
	 * @return branchKana 支店カナ
	 */
	public String getBranchKana() {
		return branchKana;
	}

	/**
	 * 支店カナの設定
	 * 
	 * @param branchKana 支店カナ
	 */
	public void setBranchKana(String branchKana) {
		this.branchKana = branchKana;
	}

	/**
	 * 支店検索名称の取得
	 * 
	 * @return branchNamek 支店検索名称
	 */
	public String getBranchNamek() {
		return branchNamek;
	}

	/**
	 * 支店検索名称の設定
	 * 
	 * @param branchNamek 支店検索名称
	 */
	public void setBranchNamek(String branchNamek) {
		this.branchNamek = branchNamek;
	}

	/**
	 * 銀行名（英字）を取得する。
	 * 
	 * @return String bankNameE
	 */
	public String getBankNameE() {
		return bankNameE;
	}

	/**
	 * 銀行名（英字）を設定する。
	 * 
	 * @param bankNameE
	 */
	public void setBankNameE(String bankNameE) {
		this.bankNameE = bankNameE;
	}

	/**
	 * 支店名（英字）を取得する。
	 * 
	 * @return String branchNameE
	 */
	public String getBranchNameE() {
		return branchNameE;
	}

	/**
	 * 支店名（英字）を設定する。
	 * 
	 * @param branchNameE
	 */
	public void setBranchNameE(String branchNameE) {
		this.branchNameE = branchNameE;
	}

	/**
	 * 預金種別を取得する。
	 * 
	 * @return String 預金種別
	 */
	public String getDepositKindName() {
		return DepositKind.getDepositKindName(depositKind);
	}

	/**
	 * 預金種別 + 口座番号を取得する。
	 * 
	 * @return String 銀行名 + 支店名
	 */
	public String getDepositKindAndAcountNoNo() {
		return DepositKind.getDepositKindName(depositKind) + " " + Util.avoidNull(accountNo);
	}

	/**
	 * 銀行名 + 支店名を取得する。
	 * 
	 * @return String 銀行名 + 支店名
	 */
	public String getBankAndBranchName() {
		return Util.avoidNull(bankName) + " " + Util.avoidNull(branchName);
	}

	/**
	 * 振込依頼人コードを取得する。
	 * 
	 * @return String clientCode
	 */
	public String getClientCode() {
		return clientCode;
	}

	/**
	 * 振込依頼人コードを設定する。
	 * 
	 * @param clientCode
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * 振込依頼人名を取得する。
	 * 
	 * @return String clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * 振込依頼人名を設定する。
	 * 
	 * @param clientName
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * 振込依頼人名（英字）を取得する。
	 * 
	 * @return String clientNameE
	 */
	public String getClientNameE() {
		return clientNameE;
	}

	/**
	 * 振込依頼人名（英字）を設定する。
	 * 
	 * @param clientNameE
	 */
	public void setClientNameE(String clientNameE) {
		this.clientNameE = clientNameE;
	}

	/**
	 * 振込依頼人名（漢字）を取得する。
	 * 
	 * @return String clientNameJ
	 */
	public String getClientNameJ() {
		return clientNameJ;
	}

	/**
	 * 振込依頼人名（漢字）を設定する。
	 * 
	 * @param clientNameJ
	 */
	public void setClientNameJ(String clientNameJ) {
		this.clientNameJ = clientNameJ;
	}

	/**
	 * codeを取得する。
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * codeを設定する。
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCodeを取得する。
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCodeを設定する。
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * currencyCodeを取得する。
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * currencyCodeを設定する。
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 通貨情報の取得
	 * 
	 * @return currency 通貨情報
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * 通貨情報の設定
	 * 
	 * @param currency 通貨情報
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * dateFromを取得する。
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFromを設定する。
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateToを取得する。
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateToを設定する。
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * departmentCodeを取得する。
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCodeを設定する。
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * 預金種目を取得する。
	 * 
	 * @return DepositKind depositKind
	 */
	public DepositKind getDepositKind() {
		return depositKind;
	}

	/**
	 * 預金種目を設定する。
	 * 
	 * @param depositKind
	 */
	public void setDepositKind(DepositKind depositKind) {
		this.depositKind = depositKind;
	}

	/**
	 * detailItemCodeを取得する。
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCodeを設定する。
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCodeを取得する。
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCodeを設定する。
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * nameを取得する。
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * nameを設定する。
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * subItemCodeを取得する。
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCodeを設定する。
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * useEmployeePaymentを取得する。
	 * 
	 * @return boolean useEmployeePayment
	 */
	public boolean isUseEmployeePayment() {
		return useEmployeePayment;
	}

	/**
	 * useEmployeePaymentを設定する。
	 * 
	 * @param useEmployeePayment
	 */
	public void setUseEmployeePayment(boolean useEmployeePayment) {
		this.useEmployeePayment = useEmployeePayment;
	}

	/**
	 * useExPaymentを取得する。
	 * 
	 * @return boolean useExPayment
	 */
	public boolean isUseExPayment() {
		return useExPayment;
	}

	/**
	 * useExPaymentを設定する。
	 * 
	 * @param useExPayment
	 */
	public void setUseExPayment(boolean useExPayment) {
		this.useExPayment = useExPayment;
	}

	/**
	 * 通貨桁の取得
	 * 
	 * @return decimalPoint 通貨桁
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * 通貨桁の設定
	 * 
	 * @param decimalPoint 通貨桁
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * 銀行口座略称の取得
	 * 
	 * @return nameS 銀行口座略称
	 */
	public String getNameS() {
		return nameS;
	}

	/**
	 * 銀行口座略称の設定
	 * 
	 * @param nameS 銀行口座略称
	 */
	public void setNameS(String nameS) {
		this.nameS = nameS;
	}

	/**
	 * 計上部門名称の取得
	 * 
	 * @return departmentName 計上部門名称
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * 計上部門名称の設定
	 * 
	 * @param departmentName 計上部門名称
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * 計上部門略称の取得
	 * 
	 * @return departmentNames 計上部門略称
	 */
	public String getDepartmentNames() {
		return departmentNames;
	}

	/**
	 * 計上部門略称の設定
	 * 
	 * @param departmentNames 計上部門略称
	 */
	public void setDepartmentNames(String departmentNames) {
		this.departmentNames = departmentNames;
	}

	/**
	 * 計上部門検索名称の取得
	 * 
	 * @return departmentNamek 計上部門検索名称
	 */
	public String getDepartmentNamek() {
		return departmentNamek;
	}

	/**
	 * 計上部門検索名称の設定
	 * 
	 * @param departmentNamek 計上部門検索名称
	 */
	public void setDepartmentNamek(String departmentNamek) {
		this.departmentNamek = departmentNamek;
	}

	/**
	 * 科目名称の取得
	 * 
	 * @return itemName 科目名称
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * 科目名称の設定
	 * 
	 * @param itemName 科目名称
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * 科目略称の取得
	 * 
	 * @return itemNames 科目略称
	 */
	public String getItemNames() {
		return itemNames;
	}

	/**
	 * 科目略称の設定
	 * 
	 * @param itemNames 科目略称
	 */
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	/**
	 * 科目検索名称の取得
	 * 
	 * @return itemNamek 科目検索名称
	 */
	public String getItemNamek() {
		return itemNamek;
	}

	/**
	 * 科目検索名称の設定
	 * 
	 * @param itemNamek 科目検索名称
	 */
	public void setItemNamek(String itemNamek) {
		this.itemNamek = itemNamek;
	}

	/**
	 * 補助科目名称の取得
	 * 
	 * @return subItemName 補助科目名称
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * 補助科目名称の設定
	 * 
	 * @param subItemName 補助科目名称
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * 補助科目略称の取得
	 * 
	 * @return subItemNames 補助科目略称
	 */
	public String getSubItemNames() {
		return subItemNames;
	}

	/**
	 * 補助科目略称の設定
	 * 
	 * @param subItemNames 補助科目略称
	 */
	public void setSubItemNames(String subItemNames) {
		this.subItemNames = subItemNames;
	}

	/**
	 * 補助科目検索名称の取得
	 * 
	 * @return subItemNamek 補助科目検索名称
	 */
	public String getSubItemNamek() {
		return subItemNamek;
	}

	/**
	 * 補助科目検索名称の設定
	 * 
	 * @param subItemNamek 補助科目検索名称
	 */
	public void setSubItemNamek(String subItemNamek) {
		this.subItemNamek = subItemNamek;
	}

	/**
	 * 内訳科目名称の取得
	 * 
	 * @return detailItemName 内訳科目名称
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * 内訳科目名称の設定
	 * 
	 * @param detailItemName 内訳科目名称
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * 内訳科目略称の取得
	 * 
	 * @return detailItemNames 内訳科目略称
	 */
	public String getDetailItemNames() {
		return detailItemNames;
	}

	/**
	 * 内訳科目略称の設定
	 * 
	 * @param detailItemNames 内訳科目略称
	 */
	public void setDetailItemNames(String detailItemNames) {
		this.detailItemNames = detailItemNames;
	}

	/**
	 * 内訳科目検索名称の取得
	 * 
	 * @return detailItemNamek 内訳科目検索名称
	 */
	public String getDetailItemNamek() {
		return detailItemNamek;
	}

	/**
	 * 内訳科目検索名称の設定
	 * 
	 * @param detailItemNamek 内訳科目検索名称
	 */
	public void setDetailItemNamek(String detailItemNamek) {
		this.detailItemNamek = detailItemNamek;
	}

	/**
	 * IBANコードの取得
	 * 
	 * @return iBan IBANコード
	 */
	public String getIBan() {
		return iBan;
	}

	/**
	 * IBANコードの設定
	 * 
	 * @param iBan IBANコード
	 */
	public void setIBan(String iBan) {
		this.iBan = iBan;
	}

	/**
	 * 銀行識別子の取得
	 * 
	 * @return bankIndentify 銀行識別子
	 */
	public String getBankIndentify() {
		return bankIndentify;
	}

	/**
	 * 銀行識別子の設定
	 * 
	 * @param bankIndentify 銀行識別子
	 */
	public void setBankIndentify(String bankIndentify) {
		this.bankIndentify = bankIndentify;
	}

	/**
	 * 口座識別子の取得
	 * 
	 * @return bankAccountIndentify 口座識別子
	 */
	public String getBankAccountIndentify() {
		return bankAccountIndentify;
	}

	/**
	 * 口座識別子の設定
	 * 
	 * @param bankAccountIndentify 口座識別子
	 */
	public void setBankAccountIndentify(String bankAccountIndentify) {
		this.bankAccountIndentify = bankAccountIndentify;
	}

	/**
	 * SWIFTコードの取得
	 * 
	 * @return swift SWIFTコード
	 */
	public String getSwift() {
		return swift;
	}

	/**
	 * SWIFTコードの設定
	 * 
	 * @param swift SWIFTコード
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	/**
	 * 住所1の取得
	 * 
	 * @return bnkAdr1 住所1
	 */
	public String getBnkAdr1() {
		return bnkAdr1;
	}

	/**
	 * 住所1の設定
	 * 
	 * @param bnkAdr1 住所1
	 */
	public void setBnkAdr1(String bnkAdr1) {
		this.bnkAdr1 = bnkAdr1;
	}

	/**
	 * 住所2の取得
	 * 
	 * @return bnkAdr2 住所2
	 */
	public String getBnkAdr2() {
		return bnkAdr2;
	}

	/**
	 * 住所2の設定
	 * 
	 * @param bnkAdr2 住所2
	 */
	public void setBnkAdr2(String bnkAdr2) {
		this.bnkAdr2 = bnkAdr2;
	}

	/**
	 * 住所1（英字）の取得
	 * 
	 * @return bnkAdr1E 住所1（英字）
	 */
	public String getBnkAdr1E() {
		return bnkAdr1E;
	}

	/**
	 * 住所1（英字）の設定
	 * 
	 * @param bnkAdr1E 住所1（英字）
	 */
	public void setBnkAdr1E(String bnkAdr1E) {
		this.bnkAdr1E = bnkAdr1E;
	}

	/**
	 * 住所2（英字）の取得
	 * 
	 * @return bnkAdr2E 住所2（英字）
	 */
	public String getBnkAdr2E() {
		return bnkAdr2E;
	}

	/**
	 * 住所2（英字）の設定
	 * 
	 * @param bnkAdr2E 住所2（英字）
	 */
	public void setBnkAdr2E(String bnkAdr2E) {
		this.bnkAdr2E = bnkAdr2E;
	}

	/**
	 * BANK COUNTRYの取得
	 * @return bankCountry
	 */
	public String getBankCountry() {
		return bankCountry;
	}

	
	/**
	 * BANK COUNTRYの設定
	 * @param bankCountry
	 */
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
}

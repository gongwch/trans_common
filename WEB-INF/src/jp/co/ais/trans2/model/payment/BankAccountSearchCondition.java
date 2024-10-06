package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 銀行口座の検索条件
 * 
 * @author AIS
 */
public class BankAccountSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
	OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 銀行名（銀行名 + 支店名）like */
	protected String nameLike = null;

	/** 口座番号 like */
	protected String accountNokLike = null;

	/** 口座番号 */
	protected String accountNo = null;

	/** 社員ＦＢ区分 */
	protected boolean useEmployeePayment = false;

	/** 社外ＦＢ区分 */
	protected boolean useExPayment = false;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 銀行コード */
	protected String bankCode = null;

	/** 支店コード */
	protected String branchCode = null;

	/** 預金種目 */
	protected DepositKind depositKind = null;

	/** 口座番号(10桁0埋め) */
	protected String accountNoFillZero = null;

	/** 通貨コード */
	protected String currencyCode = null;

	/** 銀行口座コード最小値取得区分 */
	protected boolean minimumOnly = false;

	/** IBANコード */
	protected String iBanCode = null;

	/** 銀行識別子 */
	protected String bankIdentify = null;

	/** 口座識別子 */
	protected String accountIdentify = null;

	/** SWIFTコード */
	protected String swiftCode = null;

	/** 住所1 */
	protected String bnkAdr1 = null;

	/** 住所2 */
	protected String bnkAdr2 = null;

	/** 科目コード */
	protected String itemCode = null;

	/** 補助科目コード */
	protected String subItemCode = null;

	/** 内訳科目コード */
	protected String detailItemCode = null;

	/** 受付番号 */
	protected String acceptNo = null;

	/** 銀行口座コード(複数) */
	protected List<String> bankAccountCodeList = new LinkedList<String>();

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;
	
	/** 部門コード */
	protected String deptCode;

	/** 現在件数 */
	protected int nowCount = 0;

	/**
	 * currencyCodeを取得する。
	 * 
	 * @return currencyCode
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
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BankAccountSearchCondition clone() {
		try {
			return (BankAccountSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * accountNokLikeを取得する。
	 * 
	 * @return String accountNokLike
	 */
	public String getAccountNokLike() {
		return accountNokLike;
	}

	/**
	 * accountNokLikeを設定する。
	 * 
	 * @param accountNokLike
	 */
	public void setAccountNokLike(String accountNokLike) {
		this.accountNokLike = accountNokLike;
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
	 * codeFromを取得する。
	 * 
	 * @return String codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * codeFromを設定する。
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * codeLikeを取得する。
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLikeを設定する。
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * codeToを取得する。
	 * 
	 * @return String codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * codeToを設定する。
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * nameLikeを取得する。
	 * 
	 * @return String nameLike
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * nameLikeを設定する。
	 * 
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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
	 * validTermを取得する。
	 * 
	 * @return Date validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * validTermを設定する。
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * 口座番号の取得
	 * 
	 * @return accountNo 口座番号
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * 口座番号の設定
	 * 
	 * @param accountNo 口座番号
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * 口座番号(10桁0埋め)の取得
	 * 
	 * @return accountNoFillZero 口座番号(10桁0埋め)
	 */
	public String getAccountNoFillZero() {
		return accountNoFillZero;
	}

	/**
	 * 口座番号(10桁0埋め)の設定
	 * 
	 * @param accountNoFillZero 口座番号(10桁0埋め)
	 */
	public void setAccountNoFillZero(String accountNoFillZero) {
		this.accountNoFillZero = accountNoFillZero;
	}

	/**
	 * 銀行コードの取得
	 * 
	 * @return bankCode 銀行コード
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * 銀行コードの設定
	 * 
	 * @param bankCode 銀行コード
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * 支店コードの取得
	 * 
	 * @return branchCode 支店コード
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * 支店コードの設定
	 * 
	 * @param branchCode 支店コード
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * 預金種目の取得
	 * 
	 * @return depositKind 預金種目
	 */
	public DepositKind getDepositKind() {
		return depositKind;
	}

	/**
	 * 預金種目の設定
	 * 
	 * @param depositKind 預金種目
	 */
	public void setDepositKind(DepositKind depositKind) {
		this.depositKind = depositKind;
	}

	/**
	 * minimumOnlyを取得する。
	 * 
	 * @return boolean minimumOnly
	 */
	public boolean isMinimumOnly() {
		return minimumOnly;
	}

	/**
	 * minimumOnlyを設定する。
	 * 
	 * @param minimumOnly
	 */
	public void setMinimumOnly(boolean minimumOnly) {
		this.minimumOnly = minimumOnly;
	}

	/**
	 * IBANコードの取得
	 * 
	 * @return iBanCode IBANコード
	 */
	public String getIBanCode() {
		return iBanCode;
	}

	/**
	 * IBANコードの設定
	 * 
	 * @param iBanCode IBANコード
	 */
	public void setIBanCode(String iBanCode) {
		this.iBanCode = iBanCode;
	}

	/**
	 * 銀行識別子の取得
	 * 
	 * @return bankIdentify 銀行識別子
	 */
	public String getBankIdentify() {
		return bankIdentify;
	}

	/**
	 * 銀行識別子の設定
	 * 
	 * @param bankIdentify 銀行識別子
	 */
	public void setBankIdentify(String bankIdentify) {
		this.bankIdentify = bankIdentify;
	}

	/**
	 * 口座識別子の取得
	 * 
	 * @return accountIdentify 口座識別子
	 */
	public String getAccountIdentify() {
		return accountIdentify;
	}

	/**
	 * 口座識別子の設定
	 * 
	 * @param accountIdentify 口座識別子
	 */
	public void setAccountIdentify(String accountIdentify) {
		this.accountIdentify = accountIdentify;
	}

	/**
	 * SWIFTコードの取得
	 * 
	 * @return swiftCode SWIFTコード
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * SWIFTコードの設定
	 * 
	 * @param swiftCode SWIFTコード
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * 住所1
	 * 
	 * @return bnkAdr1 住所1
	 */
	public String getBnkAdr1() {
		return bnkAdr1;
	}

	/**
	 * 住所1
	 * 
	 * @param bnkAdr1 住所1
	 */
	public void setBnkAdr1(String bnkAdr1) {
		this.bnkAdr1 = bnkAdr1;
	}

	/**
	 * 住所2
	 * 
	 * @return bnkAdr2 住所2
	 */
	public String getBnkAdr2() {
		return bnkAdr2;
	}

	/**
	 * 住所2
	 * 
	 * @param bnkAdr2 住所2
	 */
	public void setBnkAdr2(String bnkAdr2) {
		this.bnkAdr2 = bnkAdr2;
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
	 * 受付番号の取得
	 * 
	 * @return acceptNo 受付番号
	 */
	public String getAcceptNo() {
		return acceptNo;
	}

	/**
	 * 受付番号の設定
	 * 
	 * @param acceptNo 受付番号
	 */
	public void setAcceptNo(String acceptNo) {
		this.acceptNo = acceptNo;
	}

	/**
	 * @return あいまい
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * @return あいまい
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * 銀行口座コード(複数)の取得
	 * 
	 * @return 銀行口座コード(複数)
	 */
	public List<String> getCodeList() {
		return this.bankAccountCodeList;
	}

	/**
	 * 銀行口座コード(複数)の取得
	 * 
	 * @return 銀行口座コード(複数)
	 */
	public String[] getBankAccountCodeList() {
		if (bankAccountCodeList.isEmpty()) {
			return null;
		}

		return bankAccountCodeList.toArray(new String[bankAccountCodeList.size()]);
	}

	/**
	 * 銀行口座コード(複数)の設定
	 * 
	 * @param bankAccountCodeList 銀行口座コード(複数)
	 */
	public void setBankAccountCodeList(String[] bankAccountCodeList) {
		addBankAccountSettingCode(bankAccountCodeList);
	}

	/**
	 * 銀行口座コード(複数)の設定
	 * 
	 * @param list 銀行口座コード(複数)
	 */
	public void addBankAccountSettingCode(String... list) {
		for (String bankAccountCode : list) {
			this.bankAccountCodeList.add(bankAccountCode);
		}
	}

	/**
	 * 銀行口座コード(複数)のクリア
	 */
	public void clearBankAccountCodeList() {
		bankAccountCodeList.clear();
	}

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	/**
	 * 部門コードの取得
	 * 
	 * @return 部門コード
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * 部門コードの設定
	 * 
	 * @param deptCode
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}

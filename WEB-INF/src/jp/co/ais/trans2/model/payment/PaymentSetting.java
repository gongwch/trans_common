package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 支払条件マスタ情報
 * 
 * @author AIS
 */
public class PaymentSetting extends TransferBase implements Cloneable, AutoCompletable, FilterableEntity {

	/** 会社コード */
	protected String companyCode = null;

	/** 取引先 */
	protected Customer customer;

	/** 取引先条件コード */
	protected String code;

	/** 振込手数料区分 */
	protected CashInFeeType cashInFeeType;

	/** 支払条件締め日 */
	protected String closeDay;

	/** 支払条件締め後月 */
	protected String nextMonth;

	/** 支払条件支払日 */
	protected String cashDay;

	/** 支払区分 */
	protected PaymentDateType paymentDateType;

	/** 支払方法 */
	protected PaymentMethod paymentMethod;

	/** 振込振出銀行口座 */
	protected BankAccount bankAccount;

	/** 銀行コード */
	protected String bankCode;

	/** 銀行名 */
	protected String bankName;

	/** 支店コード */
	protected String branchCode;

	/** 支店名 */
	protected String branchName;

	/** 預金種目 */
	protected DepositKind depositKind;

	/** 預金種目名称 */
	protected String depositKindName;

	/** 口座番号 */
	protected String accountNo;

	/** 口座名義 */
	protected String accountName;

	/** 口座名義カナ */
	protected String accountNameKana;

	/** 送金目的 */
	protected Remittance remittancePurpose;

	/** 被仕向支店名称 */
	protected String sendBranchName;

	/** 被仕向銀行名称 */
	protected String sendBankName;

	/** 手数料区分 */
	protected CommissionPaymentType commissionPaymentType;

	/** 支払銀行名称 */
	protected String payBankName;

	/** 支払支店名称 */
	protected String payBranchName;

	/** 支払銀行住所 */
	protected String payBankAddress;

	/** 経由銀行名称 */
	protected String viaBankName;

	/** 経由支店名称 */
	protected String viaBranchName;

	/** 経由銀行住所 */
	protected String viaBankAddress;

	/** 受取人住所 */
	protected String receiverAddress;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 銀行国 */
	protected Country bankCountry;

	/** 銀行SWIFTコード */
	protected String bankSwiftCode;

	/** 支払銀行国 */
	protected Country paymentBankCountry;

	/** 支払銀行SWIFTコード */
	protected String paymentBankSwiftCode;

	/** 経由銀行国 */
	protected Country viaBankCountry;

	/** 経由銀行SWIFTコード */
	protected String viaBankSwiftCode;

	/** 受取人国 */
	protected Country recipientCountry;

	/** バンクチャージ区分 */
	protected BankChargeType bankChargeType;

	/**
	 * オブジェクトのClone
	 */
	@Override
	public PaymentSetting clone() {
		try {

			return (PaymentSetting) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return name
	 */
	public String getName() {
		return getBankAndBranchName();
	}

	/**
	 * @return names
	 */
	public String getNames() {
		return getBankAndBranchName();
	}

	/**
	 * @return namek
	 */
	public String getNamek() {
		return getBankAndBranchName();
	}

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {
		return getCode() + " " + getBankAndBranchName() + " " + getDepositKindAndAcountNo();
	}

	/**
	 * 預金種別 + 口座番号を取得する。
	 * 
	 * @return String 預金種別 + 口座番号
	 */
	public String getDepositKindAndAcountNoNo() {
		return DepositKind.getDepositKindName(depositKind) + " " + Util.avoidNull(accountNo);
	}

	/**
	 * 預金種別 + 口座番号を取得する。
	 * 
	 * @return String 預金種別 + 口座番号
	 */
	public String getDepositKindAndAcountNo() {
		return depositKindName + " " + Util.avoidNull(accountNo);
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
	 * 取引先の取得
	 * 
	 * @return customer 取引先
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * 取引先の設定
	 * 
	 * @param customer 取引先
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * 取引先条件コードの取得
	 * 
	 * @return code 取引先条件コード
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 取引先条件コードの設定
	 * 
	 * @param code 取引先条件コード
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 振込手数料区分の取得
	 * 
	 * @return cashInFeeType 振込手数料区分
	 */
	public CashInFeeType getCashInFeeType() {
		return cashInFeeType;
	}

	/**
	 * 振込手数料区分の設定
	 * 
	 * @param cashInFeeType 振込手数料区分
	 */
	public void setCashInFeeType(CashInFeeType cashInFeeType) {
		this.cashInFeeType = cashInFeeType;
	}

	/**
	 * 支払条件締め日の取得
	 * 
	 * @return closeDay 支払条件締め日
	 */
	public String getCloseDay() {
		return closeDay;
	}

	/**
	 * 支払条件締め日の設定
	 * 
	 * @param closeDay 支払条件締め日
	 */
	public void setCloseDay(String closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * 支払条件締め後月の取得
	 * 
	 * @return nextMonth 支払条件締め後月
	 */
	public String getNextMonth() {
		return nextMonth;
	}

	/**
	 * 支払条件締め後月の設定
	 * 
	 * @param nextMonth 支払条件締め後月
	 */
	public void setNextMonth(String nextMonth) {
		this.nextMonth = nextMonth;
	}

	/**
	 * 支払条件支払日の取得
	 * 
	 * @return cashDay 支払条件支払日
	 */
	public String getCashDay() {
		return cashDay;
	}

	/**
	 * 支払条件支払日の設定
	 * 
	 * @param cashDay 支払条件支払日
	 */
	public void setCashDay(String cashDay) {
		this.cashDay = cashDay;
	}

	/**
	 * 支払区分の取得
	 * 
	 * @return paymentDateType 支払区分
	 */
	public PaymentDateType getPaymentDateType() {
		return paymentDateType;
	}

	/**
	 * 支払区分の設定
	 * 
	 * @param paymentDateType 支払区分
	 */
	public void setPaymentDateType(PaymentDateType paymentDateType) {
		this.paymentDateType = paymentDateType;
	}

	/**
	 * 支払方法の取得
	 * 
	 * @return paymentMethod 支払方法
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * 支払方法の設定
	 * 
	 * @param paymentMethod 支払方法
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * 振込振出銀行口座の取得
	 * 
	 * @return bankAccount 振込振出銀行口座
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * 振込振出銀行口座の設定
	 * 
	 * @param bankAccount 振込振出銀行口座
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
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
	 * 銀行名の取得
	 * 
	 * @return bankName 銀行名
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * 銀行名の設定
	 * 
	 * @param bankName 銀行名
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * 支店名の取得
	 * 
	 * @return branchName 支店名
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * 支店名の設定
	 * 
	 * @param branchName 支店名
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * 預金種目名称の取得
	 * 
	 * @return depositKindName 預金種目名称
	 */
	public String getDepositKindName() {
		return depositKindName;
	}

	/**
	 * 預金種目名称の設定
	 * 
	 * @param depositKindName 預金種目名称
	 */
	public void setDepositKindName(String depositKindName) {
		this.depositKindName = depositKindName;
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
	 * 口座名義の取得
	 * 
	 * @return accountName 口座名義
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * 口座名義の設定
	 * 
	 * @param accountName 口座名義
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * 口座名義カナの取得
	 * 
	 * @return accountNameKana 口座名義カナ
	 */
	public String getAccountNameKana() {
		return accountNameKana;
	}

	/**
	 * 口座名義カナの設定
	 * 
	 * @param accountNameKana 口座名義カナ
	 */
	public void setAccountNameKana(String accountNameKana) {
		this.accountNameKana = accountNameKana;
	}

	/**
	 * 送金目的の取得
	 * 
	 * @return remittancePurpose 送金目的
	 */
	public Remittance getRemittancePurpose() {
		return remittancePurpose;
	}

	/**
	 * 送金目的の設定
	 * 
	 * @param remittancePurpose 送金目的
	 */
	public void setRemittancePurpose(Remittance remittancePurpose) {
		this.remittancePurpose = remittancePurpose;
	}

	/**
	 * 被仕向支店名称の取得
	 * 
	 * @return sendBranchName 被仕向支店名称
	 */
	public String getSendBranchName() {
		return sendBranchName;
	}

	/**
	 * 被仕向支店名称の設定
	 * 
	 * @param sendBranchName 被仕向支店名称
	 */
	public void setSendBranchName(String sendBranchName) {
		this.sendBranchName = sendBranchName;
	}

	/**
	 * 被仕向銀行名称の取得
	 * 
	 * @return sendBankName 被仕向銀行名称
	 */
	public String getSendBankName() {
		return sendBankName;
	}

	/**
	 * 被仕向銀行名称の設定
	 * 
	 * @param sendBankName 被仕向銀行名称
	 */
	public void setSendBankName(String sendBankName) {
		this.sendBankName = sendBankName;
	}

	/**
	 * 手数料区分の取得
	 * 
	 * @return commissionPaymentType 手数料区分
	 */
	public CommissionPaymentType getCommissionPaymentType() {
		return commissionPaymentType;
	}

	/**
	 * 手数料区分の設定
	 * 
	 * @param commissionPaymentType 手数料区分
	 */
	public void setCommissionPaymentType(CommissionPaymentType commissionPaymentType) {
		this.commissionPaymentType = commissionPaymentType;
	}

	/**
	 * 支払銀行名称の取得
	 * 
	 * @return payBankName 支払銀行名称
	 */
	public String getPayBankName() {
		return payBankName;
	}

	/**
	 * 支払銀行名称の設定
	 * 
	 * @param payBankName 支払銀行名称
	 */
	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	/**
	 * 支払支店名称の取得
	 * 
	 * @return payBranchName 支払支店名称
	 */
	public String getPayBranchName() {
		return payBranchName;
	}

	/**
	 * 支払支店名称の設定
	 * 
	 * @param payBranchName 支払支店名称
	 */
	public void setPayBranchName(String payBranchName) {
		this.payBranchName = payBranchName;
	}

	/**
	 * 支払銀行住所の取得
	 * 
	 * @return payBankAddress 支払銀行住所
	 */
	public String getPayBankAddress() {
		return payBankAddress;
	}

	/**
	 * 支払銀行住所の設定
	 * 
	 * @param payBankAddress 支払銀行住所
	 */
	public void setPayBankAddress(String payBankAddress) {
		this.payBankAddress = payBankAddress;
	}

	/**
	 * 経由銀行名称の取得
	 * 
	 * @return viaBankName 経由銀行名称
	 */
	public String getViaBankName() {
		return viaBankName;
	}

	/**
	 * 経由銀行名称の設定
	 * 
	 * @param viaBankName 経由銀行名称
	 */
	public void setViaBankName(String viaBankName) {
		this.viaBankName = viaBankName;
	}

	/**
	 * 経由支店名称の取得
	 * 
	 * @return viaBranchName 経由支店名称
	 */
	public String getViaBranchName() {
		return viaBranchName;
	}

	/**
	 * 経由支店名称の設定
	 * 
	 * @param viaBranchName 経由支店名称
	 */
	public void setViaBranchName(String viaBranchName) {
		this.viaBranchName = viaBranchName;
	}

	/**
	 * 経由銀行住所の取得
	 * 
	 * @return viaBankAddress 経由銀行住所
	 */
	public String getViaBankAddress() {
		return viaBankAddress;
	}

	/**
	 * 経由銀行住所の設定
	 * 
	 * @param viaBankAddress 経由銀行住所
	 */
	public void setViaBankAddress(String viaBankAddress) {
		this.viaBankAddress = viaBankAddress;
	}

	/**
	 * 受取人住所の取得
	 * 
	 * @return receiverAddress 受取人住所
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}

	/**
	 * 受取人住所の設定
	 * 
	 * @param receiverAddress 受取人住所
	 */
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * 有効期間開始の取得
	 * 
	 * @return dateFrom 有効期間開始
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * 有効期間開始の設定
	 * 
	 * @param dateFrom 有効期間開始
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 有効期間終了の取得
	 * 
	 * @return dateTo 有効期間終了
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * 有効期間終了の設定
	 * 
	 * @param dateTo 有効期間終了
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * 銀行国の取得
	 * 
	 * @return bankCountry 銀行国
	 */
	public Country getBankCountry() {
		return bankCountry;
	}

	/**
	 * 銀行国の設定
	 * 
	 * @param bankCountry 銀行国
	 */
	public void setBankCountry(Country bankCountry) {
		this.bankCountry = bankCountry;
	}

	/**
	 * 銀行SWIFTコードの取得
	 * 
	 * @return bankSwiftCode 銀行SWIFTコード
	 */
	public String getBankSwiftCode() {
		return bankSwiftCode;
	}

	/**
	 * 銀行SWIFTコードの設定
	 * 
	 * @param bankSwiftCode 銀行SWIFTコード
	 */
	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}

	/**
	 * 支払銀行国の取得
	 * 
	 * @return paymentBankCountry 支払銀行国
	 */
	public Country getPaymentBankCountry() {
		return paymentBankCountry;
	}

	/**
	 * 支払銀行国の設定
	 * 
	 * @param paymentBankCountry 支払銀行国
	 */
	public void setPaymentBankCountry(Country paymentBankCountry) {
		this.paymentBankCountry = paymentBankCountry;
	}

	/**
	 * 支払銀行SWIFTコードの取得
	 * 
	 * @return paymentBankSwiftCode 支払銀行SWIFTコード
	 */
	public String getPaymentBankSwiftCode() {
		return paymentBankSwiftCode;
	}

	/**
	 * 支払銀行SWIFTコードの設定
	 * 
	 * @param paymentBankSwiftCode 支払銀行SWIFTコード
	 */
	public void setPaymentBankSwiftCode(String paymentBankSwiftCode) {
		this.paymentBankSwiftCode = paymentBankSwiftCode;
	}

	/**
	 * 経由銀行国の取得
	 * 
	 * @return viaBankCountry 経由銀行国
	 */
	public Country getViaBankCountry() {
		return viaBankCountry;
	}

	/**
	 * 経由銀行国の設定
	 * 
	 * @param viaBankCountry 経由銀行国
	 */
	public void setViaBankCountry(Country viaBankCountry) {
		this.viaBankCountry = viaBankCountry;
	}

	/**
	 * 経由銀行SWIFTコードの取得
	 * 
	 * @return viaBankSwiftCode 経由銀行SWIFTコード
	 */
	public String getViaBankSwiftCode() {
		return viaBankSwiftCode;
	}

	/**
	 * 経由銀行SWIFTコードの設定
	 * 
	 * @param viaBankSwiftCode 経由銀行SWIFTコード
	 */
	public void setViaBankSwiftCode(String viaBankSwiftCode) {
		this.viaBankSwiftCode = viaBankSwiftCode;
	}

	/**
	 * 受取人国の取得
	 * 
	 * @return recipientCountry 受取人国
	 */
	public Country getRecipientCountry() {
		return recipientCountry;
	}

	/**
	 * 受取人国の設定
	 * 
	 * @param recipientCountry 受取人国
	 */
	public void setRecipientCountry(Country recipientCountry) {
		this.recipientCountry = recipientCountry;
	}

	/**
	 * バンクチャージ区分の取得
	 * 
	 * @return bankChargeType バンクチャージ区分
	 */
	public BankChargeType getBankChargeType() {
		return bankChargeType;
	}

	/**
	 * バンクチャージ区分の設定
	 * 
	 * @param bankChargeType バンクチャージ区分
	 */
	public void setBankChargeType(BankChargeType bankChargeType) {
		this.bankChargeType = bankChargeType;
	}

	/**
	 * 支払日算出 締め日、後月を元に定時支払日を算出
	 * 
	 * @param date 基準日
	 * @return 定時支払日
	 */
	public Date getRegularPaymentDate(Date date) {

		int closeDay_ = Integer.parseInt(closeDay);
		int nextMonth_ = Integer.parseInt(nextMonth);
		int cashDay_ = Integer.parseInt(cashDay);

		// 基準日の日を取得
		int baseday = DateUtil.getDay(date);

		// 29日以降は全て月末扱い(⇒99)に変換
		if (closeDay_ > 29) {
			closeDay_ = 99;
		}

		// 比較 締め日を超えた日の場合、翌月が基準
		if (closeDay_ < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// 後月分ずらす
		date = DateUtil.addMonth(date, nextMonth_);

		// 日を結合 29日以降は全て月末扱い
		if (cashDay_ > 28) {
			date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));
		} else {
			date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay_);
		}

		return date;
	}
}

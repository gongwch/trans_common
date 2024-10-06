package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 取引先情報
 */
public class Customer extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	/**
	 * @return インクリメントサーチ表示値
	 */
	public String getDisplayText() {

		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNamek()));

		return sb.toString();
	}

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** 略称 */
	protected String names = null;

	/** 検索名称 */
	protected String namek = null;

	/** 入金条件締め日 */
	protected int closeDay = 0;

	/** 入金条件締め後月 */
	protected int nextMonth = 0;

	/** 入金条件入金日 */
	protected int cashDay = 0;

	/** 事務所名 */
	protected String officeName = null;

	/** 郵便番号 */
	protected String zipCode = null;

	/** 住所カナ */
	protected String addressKana = null;

	/** 住所１ */
	protected String address = null;

	/** 住所２ */
	protected String address2 = null;

	/** EMail Address */
	protected String emailAddress = null;

	/** 電話番号 */
	protected String tel = null;

	/** FAX番号 */
	protected String fax = null;

	/** 集計相手先コード */
	protected String sumCode = null;

	/** 集計相手先名称 */
	protected String sumName = null;

	/** 仕入区分 */
	protected boolean purchase = false;

	/** 得意先区分 */
	protected boolean client = false;

	/** 入金銀行口座コード */
	protected String bankAccountCode = null;

	/** 振込依頼人名 */
	protected String clientName = null;

	/** 入金手数料区分 */
	protected CashInFeeType cashInFeeType = null;

	/** 有効期間開始 */
	protected Date dateFrom = null;

	/** 有効期間終了 */
	protected Date dateTo = null;

	/** 取引先区分 */
	protected CustomerType customerType = null;

	/** 取引先条件 */
	protected PaymentSetting paymentSetting = null;

	/** 銀行口座 */
	protected BankAccount bankAccount = null;

	/** 国コード */
	protected String countryCode = null;

	/** 国 */
	protected Country country = null;

	/** companyNo */
	protected String companyNo = null;

	/** CompanyType */
	protected String companyType = null;

	/** CHARTERERフラグ */
	protected boolean charterer = false;

	/** OWNERフラグ */
	protected boolean owner = false;

	/** PORT AGENTフラグ */
	protected boolean portAgent = false;

	/** SUPPLIERフラグ */
	protected boolean supplier = false;

	/** BROKERフラグ */
	protected boolean broker = false;

	/** BANKフラグ */
	protected boolean bank = false;

	/** OTHERフラグ */
	protected boolean other = false;

	/** Shipper フラグ */
	protected boolean shipper = false;

	/** Consignee フラグ */
	protected boolean consignee = false;

	/** Notify party フラグ */
	protected boolean notifyParty = false;

	/** Fowarderフラグ */
	protected boolean fowarder = false;

	/** Bunker Trader フラグ */
	protected boolean bunkerTrader = false;

	/** Bunker Supplier フラグ */
	protected boolean bunkerSupplier = false;

	/** Group Company フラグ */
	protected boolean groupCompany = false;

	/** 個人取引先 */
	protected boolean isPersonalCustomer = false;

	/** 非適格請求書発行事業者フラグ */
	protected boolean NO_INV_REG_FLG = false;

	/** 非適格請求書事業者消費税コード */
	protected String NO_INV_REG_ZEI_CODE = null;

	/** 非適格請求書発行事業者消費税名称 */
	protected String NO_INV_REG_ZEI_NAME = null;

	/** 適格請求書発行事業者登録番号 */
	protected String INV_REG_NO = null;

	/**
	 * 銀行口座を取得する。
	 * 
	 * @return BankAccount bankAccount
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * 銀行口座を設定する。
	 * 
	 * @param bankAccount
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
	}

	/**
	 * paymentSettingを取得する。
	 * 
	 * @return CustomerPaymentSetting paymentSetting
	 */
	public PaymentSetting getPaymentSetting() {
		return paymentSetting;
	}

	/**
	 * paymentSettingを設定する。
	 * 
	 * @param paymentSetting
	 */
	public void setPaymentSetting(PaymentSetting paymentSetting) {
		this.paymentSetting = paymentSetting;
	}

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

	/**
	 * @return dateFromを戻します。
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFromを設定します。
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateToを戻します。
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateToを設定します。
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * @return nameを戻します。
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name nameを設定します。
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namekを戻します。
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namekを設定します。
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return namesを戻します。
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names namesを設定します。
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * 日払いを取得する。
	 * 
	 * @return String cashDay
	 */
	public int getCashDay() {
		return cashDay;
	}

	/**
	 * 日払いを設定する。
	 * 
	 * @param cashDay
	 */
	public void setCashDay(int cashDay) {
		this.cashDay = cashDay;
	}

	/**
	 * 締め日を取得する。
	 * 
	 * @return String closeDay
	 */
	public int getCloseDay() {
		return closeDay;
	}

	/**
	 * 締め日を設定する。
	 * 
	 * @param closeDay
	 */
	public void setCloseDay(int closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * ヶ月後を取得する。
	 * 
	 * @return String nextMonth
	 */
	public int getNextMonth() {
		return nextMonth;
	}

	/**
	 * ヶ月後を設定する。
	 * 
	 * @param nextMonth
	 */
	public void setNextMonth(int nextMonth) {
		this.nextMonth = nextMonth;
	}

	/**
	 * 出納日算出 締め日、後月を元に入金予定日を算出
	 * 
	 * @param date 基準日
	 * @return 入金予定日
	 */
	public Date getBalanceDate(Date date) {

		// 基準日の日を取得
		int baseday = DateUtil.getDay(date);

		// 29日以降は全て月末扱い(⇒99)に変換
		if (closeDay > 29) {
			closeDay = 99;
		}

		// 比較 締め日を超えた日の場合、翌月が基準
		if (closeDay < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// 後月分ずらす
		date = DateUtil.addMonth(date, nextMonth);

		// 日を結合 29日以降は全て月末扱い

		int month = DateUtil.getMonth(date);
		date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));

		if (month == 2) {

			if (cashDay < 28) {
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
			} else if (DateUtil.getDay(date) == 29) {
				if (cashDay <= 28) {
					// 28日以降は全て月末扱い
					date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
				}
			} else if (DateUtil.getDay(date) == 28) {
				if (cashDay <= 27) {
					// 28日以降は全て月末扱い
					date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
				}
			}

			// 4,6,9,11月
		} else if (month == 4 || month == 6 || month == 9 || month == 11) {
			if (cashDay <= 29) {
				// 29日以降は全て月末扱い
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);
			}
			// 1,3,5,7,8,10,12月
		} else {
			if (cashDay <= 30) {
				// 30日以降は全て月末扱い
				date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay);

			}

		}

		return date;
	}

	/**
	 * 取引先区分の取得
	 * 
	 * @return 取引先区分
	 */
	public CustomerType getCustomerType() {
		return customerType;
	}

	/**
	 * 取引先区分の設定
	 * 
	 * @param customerType
	 */
	public void setCustomerType(CustomerType customerType) {
		this.customerType = customerType;
	}

	/**
	 * 事業所を取得する。
	 * 
	 * @return String officeName
	 */
	public String getOfficeName() {
		return officeName;
	}

	/**
	 * 事業所を設定する。
	 * 
	 * @param officeName
	 */
	public void setOfficeName(String officeName) {
		this.officeName = officeName;
	}

	/**
	 * 住所1を取得する。
	 * 
	 * @return String address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * 住所1を設定する。
	 * 
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * 住所2を取得する。
	 * 
	 * @return String address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * 住所2を設定する。
	 * 
	 * @param address2
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * 住所カナを取得する。
	 * 
	 * @return String addressKana
	 */
	public String getAddressKana() {
		return addressKana;
	}

	/**
	 * 住所カナを設定する。
	 * 
	 * @param addressKana
	 */
	public void setAddressKana(String addressKana) {
		this.addressKana = addressKana;
	}

	/**
	 * telを取得する。
	 * 
	 * @return String tel
	 */
	public String getTel() {
		return tel;
	}

	/**
	 * telを設定する。
	 * 
	 * @param tel
	 */
	public void setTel(String tel) {
		this.tel = tel;
	}

	/**
	 * 郵便番号を取得する。
	 * 
	 * @return String zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}

	/**
	 * 郵便番号を設定する。
	 * 
	 * @param zipCode
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	/**
	 * @return fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	/**
	 * @return sumCode
	 */
	public String getSumCode() {
		return sumCode;
	}

	/**
	 * @param sumCode
	 */
	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}

	/**
	 * @return sumName
	 */
	public String getSumName() {
		return sumName;
	}

	/**
	 * @param sumName
	 */
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}

	/**
	 * 仕入先か
	 * 
	 * @return true:仕入先
	 */
	public boolean isPurchase() {
		return purchase;
	}

	/**
	 * 仕入区分
	 * 
	 * @return 仕入区分
	 */
	public int getPurchase() {
		if (purchase) {
			return 1;
		}
		return 0;
	}

	/**
	 * 仕入区分
	 * 
	 * @param purchase 仕入区分
	 */
	public void setPurchase(boolean purchase) {
		this.purchase = purchase;
	}

	/**
	 * 得意先か
	 * 
	 * @return true:得意先
	 */
	public boolean isClient() {
		return client;
	}

	/**
	 * 得意先区分
	 * 
	 * @return 得意先区分
	 */
	public int getClient() {
		if (client) {
			return 1;
		}
		return 0;
	}

	/**
	 * 得意先
	 * 
	 * @param client 得意先
	 */
	public void setClient(boolean client) {
		this.client = client;
	}

	/**
	 * 入金銀行口座コード
	 * 
	 * @return 入金銀行口座コード
	 */
	public String getBankAccountCode() {
		return bankAccountCode;
	}

	/**
	 * 入金銀行口座コード
	 * 
	 * @param bankAccountCode 入金銀行口座コード
	 */
	public void setBankAccountCode(String bankAccountCode) {
		this.bankAccountCode = bankAccountCode;
	}

	/**
	 * 入金手数料区分
	 * 
	 * @return 入金手数料区分
	 */
	public CashInFeeType getCashInFeeType() {
		return cashInFeeType;
	}

	/**
	 * 入金手数料区分
	 * 
	 * @param cashInFeeType 入金手数料区分
	 */
	public void setCashInFeeType(CashInFeeType cashInFeeType) {
		this.cashInFeeType = cashInFeeType;
	}

	/**
	 * 振り込依頼人名
	 * 
	 * @return 振り込依頼人名
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * 振り込依頼人名
	 * 
	 * @param clientName 振り込依頼人名
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * 国コードの取得
	 * 
	 * @return countryCode 国コード
	 */
	public String getCountryCode() {
		return countryCode;
	}

	/**
	 * 国コードの設定
	 * 
	 * @param countryCode 国コード
	 */
	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	/**
	 * 国の取得
	 * 
	 * @return country 国
	 */
	public Country getCountry() {
		return country;
	}

	/**
	 * 国の設定
	 * 
	 * @param country 国
	 */
	public void setCountry(Country country) {
		this.country = country;
	}

	/**
	 * companyNoの取得
	 * 
	 * @return companyNo companyNo
	 */
	public String getCompanyNo() {
		return companyNo;
	}

	/**
	 * companyNoの設定
	 * 
	 * @param companyNo companyNo
	 */
	public void setCompanyNo(String companyNo) {
		this.companyNo = companyNo;
	}

	/**
	 * CompanyTypeの取得
	 * 
	 * @return companyType CompanyType
	 */
	public String getCompanyType() {
		return companyType;
	}

	/**
	 * CompanyTypeの設定
	 * 
	 * @param companyType CompanyType
	 */
	public void setCompanyType(String companyType) {
		this.companyType = companyType;
	}

	/**
	 * CHARTERERの取得
	 * 
	 * @return charterer CHARTERER
	 */
	public boolean isCharterer() {
		return charterer;
	}

	/**
	 * CHARTERERの設定
	 * 
	 * @param charterer CHARTERER
	 */
	public void setCharterer(boolean charterer) {
		this.charterer = charterer;
	}

	/**
	 * OWNERの取得
	 * 
	 * @return owner OWNER
	 */
	public boolean isOwner() {
		return owner;
	}

	/**
	 * OWNERの設定
	 * 
	 * @param owner OWNER
	 */
	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	/**
	 * PORT_AGENTの取得
	 * 
	 * @return portAgent PORT_AGENT
	 */
	public boolean isPortAgent() {
		return portAgent;
	}

	/**
	 * PORT_AGENTの設定
	 * 
	 * @param portAgent PORT_AGENT
	 */
	public void setPortAgent(boolean portAgent) {
		this.portAgent = portAgent;
	}

	/**
	 * SUPPLIERの取得
	 * 
	 * @return supplier SUPPLIER
	 */
	public boolean isSupplier() {
		return supplier;
	}

	/**
	 * SUPPLIERの設定
	 * 
	 * @param supplier SUPPLIER
	 */
	public void setSupplier(boolean supplier) {
		this.supplier = supplier;
	}

	/**
	 * BROKERの取得
	 * 
	 * @return broker BROKER
	 */
	public boolean isBroker() {
		return broker;
	}

	/**
	 * BROKERの設定
	 * 
	 * @param broker BROKER
	 */
	public void setBroker(boolean broker) {
		this.broker = broker;
	}

	/**
	 * OTHERの取得
	 * 
	 * @return other OTHER
	 */
	public boolean isOther() {
		return other;
	}

	/**
	 * OTHERの設定
	 * 
	 * @param other OTHER
	 */
	public void setOther(boolean other) {
		this.other = other;
	}

	/**
	 * EMail Addressを取得する。
	 * 
	 * @return String emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * EMail Addressを設定する。
	 * 
	 * @param emailAddress
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Shipperの取得
	 * 
	 * @return shipper Shipper
	 */
	public boolean isShipper() {
		return shipper;
	}

	/**
	 * Shipperの設定
	 * 
	 * @param shipper Shipper
	 */
	public void setShipper(boolean shipper) {
		this.shipper = shipper;
	}

	/**
	 * Consigneeの取得
	 * 
	 * @return consignee Consignee
	 */
	public boolean isConsignee() {
		return consignee;
	}

	/**
	 * Consigneeの設定
	 * 
	 * @param consignee Consignee
	 */
	public void setConsignee(boolean consignee) {
		this.consignee = consignee;
	}

	/**
	 * Notify partyの取得
	 * 
	 * @return notifyParty Notify party
	 */
	public boolean isNotifyParty() {
		return notifyParty;
	}

	/**
	 * Notify partyの設定
	 * 
	 * @param notifyParty Notify party
	 */
	public void setNotifyParty(boolean notifyParty) {
		this.notifyParty = notifyParty;
	}

	/**
	 * Fowarderの取得
	 * 
	 * @return fowarder Fowarder
	 */
	public boolean isFowarder() {
		return fowarder;
	}

	/**
	 * Fowarderの設定
	 * 
	 * @param fowarder Fowarder
	 */
	public void setFowarder(boolean fowarder) {
		this.fowarder = fowarder;
	}

	/**
	 * Bunker Traderの取得
	 * 
	 * @return bunkerTrader Bunker Trader
	 */
	public boolean isBunkerTrader() {
		return bunkerTrader;
	}

	/**
	 * Bunker Traderの設定
	 * 
	 * @param bunkerTrader Bunker Trader
	 */
	public void setBunkerTrader(boolean bunkerTrader) {
		this.bunkerTrader = bunkerTrader;
	}

	/**
	 * Bunker Supplierの取得
	 * 
	 * @return bunkerSupplier Bunker Supplier
	 */
	public boolean isBunkerSupplier() {
		return bunkerSupplier;
	}

	/**
	 * Bunker Supplierの設定
	 * 
	 * @param bunkerSupplier Bunker Supplier
	 */
	public void setBunkerSupplier(boolean bunkerSupplier) {
		this.bunkerSupplier = bunkerSupplier;
	}

	/**
	 * BANKの取得
	 * 
	 * @return bank
	 */
	public boolean isBank() {
		return bank;
	}

	/**
	 * BANKの設定
	 * 
	 * @param bank
	 */
	public void setBank(boolean bank) {
		this.bank = bank;
	}

	/**
	 * Group Companyの取得
	 * 
	 * @return groupCompany
	 */
	public boolean isGroupCompany() {
		return groupCompany;
	}

	/**
	 * Group Companyの設定
	 * 
	 * @param groupCompany
	 */
	public void setGroupCompany(boolean groupCompany) {
		this.groupCompany = groupCompany;
	}

	/**
	 * 個人取引先を取得
	 * 
	 * @return isPersonalInfo 個人取引先 trueの場合銀行口座情報を非表示に
	 */
	public boolean isPersonalCustomer() {
		return isPersonalCustomer;
	}

	/**
	 * 個人取引先をセットする
	 * 
	 * @param isPersonalInfo isPersonalInfo
	 */
	public void setPersonalCustomer(boolean isPersonalInfo) {
		this.isPersonalCustomer = isPersonalInfo;
	}

	/** 担当部署名 */
	protected String CHARGE_DEP_NAME = null;

	/** 担当者名 */
	protected String CHARGE_EMP_NAME = null;

	/** 取引先敬称 */
	protected HonorType TRI_TITLE_TYPE = null;

	/** 担当者敬称 */
	protected HonorType EMP_TITLE_TYPE = null;

	/**
	 * 担当部署名 を取得する。
	 * 
	 * @return String CHARGE_DEP_NAME
	 */
	public String getCHARGE_DEP_NAME() {
		return CHARGE_DEP_NAME;
	}

	/**
	 * 担当部署名 を設定する。
	 * 
	 * @param CHARGE_DEP_NAME
	 */
	public void setCHARGE_DEP_NAME(String CHARGE_DEP_NAME) {
		this.CHARGE_DEP_NAME = CHARGE_DEP_NAME;
	}

	/**
	 * 担当者名 を取得する。
	 * 
	 * @return String CHARGE_EMP_NAME
	 */
	public String getCHARGE_EMP_NAME() {
		return CHARGE_EMP_NAME;
	}

	/**
	 * 担当者名 を設定する。
	 * 
	 * @param CHARGE_EMP_NAME
	 */
	public void setCHARGE_EMP_NAME(String CHARGE_EMP_NAME) {
		this.CHARGE_EMP_NAME = CHARGE_EMP_NAME;
	}

	/**
	 * 取引先敬称 を取得する。
	 * 
	 * @return String CHARGE_EMP_NAME
	 */
	public HonorType getTRI_TITLE_TYPE() {
		return TRI_TITLE_TYPE;
	}

	/**
	 * 取引先敬称 を設定する。
	 * 
	 * @param TRI_TITLE_TYPE
	 */
	public void setTRI_TITLE_TYPE(HonorType TRI_TITLE_TYPE) {
		this.TRI_TITLE_TYPE = TRI_TITLE_TYPE;
	}

	/**
	 * 担当者敬称 を取得する。
	 * 
	 * @return String EMP_TITLE_TYPE
	 */
	public HonorType getEMP_TITLE_TYPE() {
		return EMP_TITLE_TYPE;
	}

	/**
	 * 担当者敬称 を設定する。
	 * 
	 * @param EMP_TITLE_TYPE
	 */
	public void setEMP_TITLE_TYPE(HonorType EMP_TITLE_TYPE) {
		this.EMP_TITLE_TYPE = EMP_TITLE_TYPE;
	}

	/**
	 * 非適格請求書発行事業者フラグの取得
	 * 
	 * @return NO_INV_REG_FLG 非適格請求書発行事業者フラグ
	 */
	public boolean isNO_INV_REG_FLG() {
		return NO_INV_REG_FLG;
	}

	/**
	 * 非適格請求書発行事業者フラグの設定
	 * 
	 * @param NO_INV_REG_FLG 非適格請求書発行事業者フラグ
	 */
	public void setNO_INV_REG_FLG(boolean NO_INV_REG_FLG) {
		this.NO_INV_REG_FLG = NO_INV_REG_FLG;
	}

	/**
	 * 非適格請求書事業者消費税コードの取得
	 * 
	 * @return NO_INV_REG_ZEI_CODE 非適格請求書事業者消費税コード
	 */
	public String getNO_INV_REG_ZEI_CODE() {
		return NO_INV_REG_ZEI_CODE;
	}

	/**
	 * 非適格請求書事業者消費税コードの設定
	 * 
	 * @param NO_INV_REG_ZEI_CODE 非適格請求書事業者消費税コード
	 */
	public void setNO_INV_REG_ZEI_CODE(String NO_INV_REG_ZEI_CODE) {
		this.NO_INV_REG_ZEI_CODE = NO_INV_REG_ZEI_CODE;
	}

	/**
	 * 非適格請求書発行事業者消費税名称の取得
	 * 
	 * @return NO_INV_REG_ZEI_NAME 非適格請求書発行事業者消費税名称
	 */
	public String getNO_INV_REG_ZEI_NAME() {
		return NO_INV_REG_ZEI_NAME;
	}

	/**
	 * 非適格請求書発行事業者消費税名称の設定
	 * 
	 * @param NO_INV_REG_ZEI_NAME 非適格請求書発行事業者消費税名称
	 */
	public void setNO_INV_REG_ZEI_NAME(String NO_INV_REG_ZEI_NAME) {
		this.NO_INV_REG_ZEI_NAME = NO_INV_REG_ZEI_NAME;
	}

	/**
	 * 適格請求書発行事業者登録番号の取得
	 * 
	 * @return INV_REG_NO 適格請求書発行事業者登録番号
	 */
	public String getINV_REG_NO() {
		return INV_REG_NO;
	}

	/**
	 * 適格請求書発行事業者登録番号の設定
	 * 
	 * @param INV_REG_NO 適格請求書発行事業者登録番号
	 */
	public void setINV_REG_NO(String INV_REG_NO) {
		this.INV_REG_NO = INV_REG_NO;
	}

}

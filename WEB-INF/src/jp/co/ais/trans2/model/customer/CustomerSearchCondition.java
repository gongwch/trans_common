package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * 取引先マスタ検索条件
 */
public class CustomerSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** 会社コード */
	protected String companyCode = null;

	/** コード */
	protected String code = null;

	/** 名称 */
	protected String name = null;

	/** コード前方一致 */
	protected String codeLike = null;

	/** 略称like */
	protected String namesLike = null;

	/** 検索名称like */
	protected String namekLike = null;

	/** 開始コード */
	protected String codeFrom = null;

	/** 終了コード */
	protected String codeTo = null;

	/** 有効期間 */
	protected Date validTerm = null;

	/** 集計コードを検索するか */
	protected boolean searchSumCode = false;

	/** 集計名称を検索用フラグ */
	protected boolean searchSumFlg = false;

	/** 取引先区分 */
	private CustomerType type;

	/** 取引先区分:両方の時、どちらもチェックされているもののみ抽出するか */
	protected boolean customerTypeBoth = false;

	/** 取引先コード(複数) */
	protected List<String> customerCodeList = new LinkedList<String>();

	/** 集計区分 */
	protected CustomerSumType customerSumType;

	/** CHARTERERフラグ */
	protected Boolean charterer = null;

	/** OWNERフラグ */
	protected Boolean owner = null;

	/** PORT AGENTフラグ */
	protected Boolean portAgent = null;

	/** SUPPLIERフラグ */
	protected Boolean supplier = null;

	/** BROKERフラグ */
	protected Boolean broker = null;

	/** BANKフラグ */
	protected Boolean bank = null;

	/** OTHERフラグ */
	protected Boolean other = null;

	/** Shipperフラグ */
	protected Boolean shipper = null;

	/** Consigneeフラグ */
	protected Boolean consignee = null;

	/** Notify partyフラグ */
	protected Boolean notifyParty = null;

	/** Fowarderフラグ */
	protected Boolean fowarder = null;

	/** Bunker Traderフラグ */
	protected Boolean bunkerTrader = null;

	/** Bunker Supplierフラグ */
	protected Boolean bunkerSupplier = null;

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** 現在件数 */
	protected int nowCount = 0;

	/** 対象会社リスト(getRefの場合のみ使用) */
	protected List<String> companyCodeList = null;

	/** インボイス制度フラグ */
	protected boolean invoiceFlg = false;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CustomerSearchCondition clone() {
		try {
			return (CustomerSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * @return codeFromを戻します。
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFromを設定します。
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeToを戻します。
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeToを設定します。
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * @return validTermを戻します。
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTermを設定します。
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * @return あいまい
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return あいまい
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return あいまい
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * 取引先タイプ
	 * 
	 * @param type タイプ
	 */
	public void setType(CustomerType type) {
		this.type = type;
	}

	/**
	 * 取引先タイプ
	 * 
	 * @return type 取引先タイプ
	 */
	public CustomerType getType() {
		return type;
	}

	/**
	 * 取引先区分:両方必須かどうかの判定
	 * 
	 * @return true:両方必須
	 */
	public boolean isCustomerTypeBoth() {
		return customerTypeBoth;
	}

	/**
	 * 取引先区分:両方必須の設定
	 * 
	 * @param customerTypeBoth
	 */
	public void setCustomerTypeBoth(boolean customerTypeBoth) {
		this.customerTypeBoth = customerTypeBoth;
	}

	/**
	 * searchSumCodeを取得する。
	 * 
	 * @return boolean searchSumCode
	 */
	public boolean isSearchSumCode() {
		return searchSumCode;
	}

	/**
	 * searchSumCodeを設定する。
	 * 
	 * @param searchSumCode
	 */
	public void setSearchSumCode(boolean searchSumCode) {
		this.searchSumCode = searchSumCode;
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return searchSumFlg
	 */
	public boolean isSearchSumFlg() {
		return searchSumFlg;
	}

	/**
	 * @param searchSumFlg
	 */
	public void setSearchSumFlg(boolean searchSumFlg) {
		this.searchSumFlg = searchSumFlg;
	}

	/**
	 * 取引先コード(複数)の取得
	 * 
	 * @return 取引先コード(複数)
	 */
	public List<String> getCodeList() {
		return this.customerCodeList;
	}

	/**
	 * 取引先コード(複数)の取得
	 * 
	 * @return 取引先コード(複数)
	 */
	public String[] getCustomerCodeList() {
		if (customerCodeList.isEmpty()) {
			return null;
		}

		return customerCodeList.toArray(new String[customerCodeList.size()]);
	}

	/**
	 * 取引先コード(複数)の設定
	 * 
	 * @param customerCodeList 取引先コード(複数)
	 */
	public void setCustomerCodeList(String[] customerCodeList) {
		addCustomerCode(customerCodeList);
	}

	/**
	 * 取引先コード(複数)の設定
	 * 
	 * @param list 取引先コード(複数)
	 */
	public void addCustomerCode(String... list) {
		for (String customerCode : list) {
			this.customerCodeList.add(customerCode);
		}
	}

	/**
	 * 取引先コード(複数)のクリア
	 */
	public void clearCustomerCodeList() {
		customerCodeList.clear();
	}

	/**
	 * 集計区分の取得
	 * 
	 * @return 集計区分
	 */
	public CustomerSumType getCustomerSumType() {
		return customerSumType;
	}

	/**
	 * 集計区分の設定
	 * 
	 * @param sumType
	 */
	public void setCustomerSumType(CustomerSumType sumType) {
		this.customerSumType = sumType;
	}

	/**
	 * CHARTERERの取得
	 * 
	 * @return charterer CHARTERER
	 */
	public Boolean isCharterer() {
		return charterer;
	}

	/**
	 * CHARTERERの設定
	 * 
	 * @param charterer CHARTERER
	 */
	public void setCharterer(Boolean charterer) {
		this.charterer = charterer;
	}

	/**
	 * OWNERの取得
	 * 
	 * @return owner OWNER
	 */
	public Boolean isOwner() {
		return owner;
	}

	/**
	 * OWNERの設定
	 * 
	 * @param owner OWNER
	 */
	public void setOwner(Boolean owner) {
		this.owner = owner;
	}

	/**
	 * PORT_AGENTの取得
	 * 
	 * @return portAgent PORT_AGENT
	 */
	public Boolean isPortAgent() {
		return portAgent;
	}

	/**
	 * PORT_AGENTの設定
	 * 
	 * @param portAgent PORT_AGENT
	 */
	public void setPortAgent(Boolean portAgent) {
		this.portAgent = portAgent;
	}

	/**
	 * SUPPLIERの取得
	 * 
	 * @return supplier SUPPLIER
	 */
	public Boolean isSupplier() {
		return supplier;
	}

	/**
	 * SUPPLIERの設定
	 * 
	 * @param supplier SUPPLIER
	 */
	public void setSupplier(Boolean supplier) {
		this.supplier = supplier;
	}

	/**
	 * BROKERの取得
	 * 
	 * @return broker BROKER
	 */
	public Boolean isBroker() {
		return broker;
	}

	/**
	 * BROKERの設定
	 * 
	 * @param broker BROKER
	 */
	public void setBroker(Boolean broker) {
		this.broker = broker;
	}

	/**
	 * BANKの取得
	 * 
	 * @return bank BANK
	 */
	public Boolean isBank() {
		return bank;
	}

	/**
	 * BANKの設定
	 * 
	 * @param bank BANK
	 */
	public void setBank(Boolean bank) {
		this.bank = bank;
	}

	/**
	 * OTHERの取得
	 * 
	 * @return other OTHER
	 */
	public Boolean isOther() {
		return other;
	}

	/**
	 * OTHERの設定
	 * 
	 * @param other OTHER
	 */
	public void setOther(Boolean other) {
		this.other = other;
	}

	/**
	 * Shipperの取得
	 * 
	 * @return shipper Shipper
	 */
	public Boolean isShipper() {
		return shipper;
	}

	/**
	 * Shipperの設定
	 * 
	 * @param shipper Shipper
	 */
	public void setShipper(Boolean shipper) {
		this.shipper = shipper;
	}

	/**
	 * Consigneeの取得
	 * 
	 * @return consignee Consignee
	 */
	public Boolean isConsignee() {
		return consignee;
	}

	/**
	 * Consigneeの設定
	 * 
	 * @param consignee Consignee
	 */
	public void setConsignee(Boolean consignee) {
		this.consignee = consignee;
	}

	/**
	 * Notify partyの取得
	 * 
	 * @return notifyParty Notify party
	 */
	public Boolean isNotifyParty() {
		return notifyParty;
	}

	/**
	 * Notify partyの設定
	 * 
	 * @param notifyParty Notify party
	 */
	public void setNotifyParty(Boolean notifyParty) {
		this.notifyParty = notifyParty;
	}

	/**
	 * Fowarderの取得
	 * 
	 * @return fowarder Fowarder
	 */
	public Boolean isFowarder() {
		return fowarder;
	}

	/**
	 * Fowarderの設定
	 * 
	 * @param fowarder Fowarder
	 */
	public void setFowarder(Boolean fowarder) {
		this.fowarder = fowarder;
	}

	/**
	 * Bunker Traderの取得
	 * 
	 * @return bunkerTrader Bunker Trader
	 */
	public Boolean isBunkerTrader() {
		return bunkerTrader;
	}

	/**
	 * Bunker Traderの設定
	 * 
	 * @param bunkerTrader Bunker Trader
	 */
	public void setBunkerTrader(Boolean bunkerTrader) {
		this.bunkerTrader = bunkerTrader;
	}

	/**
	 * Bunker Supplierの取得
	 * 
	 * @return bunkerSupplier Bunker Supplier
	 */
	public Boolean isBunkerSupplier() {
		return bunkerSupplier;
	}

	/**
	 * Bunker Supplierの設定
	 * 
	 * @param bunkerSupplier Bunker Supplier
	 */
	public void setBunkerSupplier(Boolean bunkerSupplier) {
		this.bunkerSupplier = bunkerSupplier;
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

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の取得
	 * 
	 * @return companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * 対象会社リスト(getRefの場合のみ使用)の設定
	 * 
	 * @param companyCodeList 対象会社リスト(getRefの場合のみ使用)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

	/**
	 * インボイス制度フラグの取得
	 * 
	 * @return invoiceFlg インボイス制度フラグ
	 */
	public boolean isInvoiceFlg() {
		return invoiceFlg;
	}

	/**
	 * インボイス制度フラグの設定
	 * 
	 * @param invoiceFlg インボイス制度フラグ
	 */
	public void setInvoiceFlg(boolean invoiceFlg) {
		this.invoiceFlg = invoiceFlg;
	}

}

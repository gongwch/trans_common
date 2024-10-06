package jp.co.ais.trans2.model.item.summary;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.item.*;

/**
 * MG0070ItemSummaryMaster - 科目集計マスタ - Bean Class
 * 
 * @author AIS
 */
public class ItemSummary extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 科目体系コード */
	protected String kmtCode;

	/** 科目体系略称 */
	protected String kmtName;

	/** 科目コード */
	protected String kmkCode;

	/** 科目略称 */
	protected String kmkName;

	/** 科目検索名称 */
	protected String kmkNamek;

	/** 公表科目名称 */
	protected String kokName;

	/** 集計相手科目コード */
	protected String sumCode;

	/** 集計相手科目略称 */
	protected String sumName;

	/** 科目出力順序 */
	protected String odr;

	/** 表示区分 */
	protected int hyjKbn;

	/** 開始年月日 */
	protected Date dateFrom;

	/** 終了年月日 */
	protected Date dateTo;

	/** 登録日付 */
	protected Date inpDate;

	/** 集計区分 */
	protected ItemSumType itemSumType = null;

	/** 貸借区分 */
	protected Dc dc = null;

	/** 科目種別 */
	protected ItemType itemType = null;

	/**
	 * @return 会社コードを取得
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * 会社コードをセット
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * 開始年月日を取得
	 * 
	 * @return dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * 開始年月日をセット
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 終了年月日を取得
	 * 
	 * @return dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * 終了年月日をセット
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * 表示区分を取得
	 * 
	 * @return hyjKbn
	 */
	public int getHyjKbn() {
		return hyjKbn;
	}

	/**
	 * 表示区分をセット
	 * 
	 * @param hyjKbn
	 */
	public void setHyjKbn(int hyjKbn) {
		this.hyjKbn = hyjKbn;
	}

	/**
	 * 科目コードを取得
	 * 
	 * @return kmkCode
	 */
	public String getKmkCode() {
		return kmkCode;
	}

	/**
	 * 科目コードをセット
	 * 
	 * @param kmkCode
	 */
	public void setKmkCode(String kmkCode) {
		this.kmkCode = kmkCode;
	}

	/**
	 * 科目略称を取得
	 * 
	 * @return kmkName
	 */
	public String getKmkName() {
		return kmkName;
	}

	/**
	 * 科目略称をセット
	 * 
	 * @param kmkName
	 */
	public void setKmkName(String kmkName) {
		this.kmkName = kmkName;
	}

	/**
	 * 科目検索名称を取得
	 * 
	 * @return kmkNamek
	 */
	public String getKmkNamek() {
		return kmkNamek;
	}

	/**
	 * 科目検索名称をセット
	 * 
	 * @param kmkNamek
	 */
	public void setKmkNamek(String kmkNamek) {
		this.kmkNamek = kmkNamek;
	}

	/**
	 * 科目体系コードを取得
	 * 
	 * @return kmtCode
	 */
	public String getKmtCode() {
		return kmtCode;
	}

	/**
	 * 科目体系コードをセット
	 * 
	 * @param kmtCode
	 */
	public void setKmtCode(String kmtCode) {
		this.kmtCode = kmtCode;
	}

	/**
	 * 科目体系略称を取得
	 * 
	 * @return kmtName
	 */
	public String getKmtName() {
		return kmtName;
	}

	/**
	 * 科目体系略称をセット
	 * 
	 * @param kmtName
	 */
	public void setKmtName(String kmtName) {
		this.kmtName = kmtName;
	}

	/**
	 * 公表科目名称を取得
	 * 
	 * @return kokName
	 */
	public String getKokName() {
		return kokName;
	}

	/**
	 * 公表科目名称をセット
	 * 
	 * @param kokName
	 */
	public void setKokName(String kokName) {
		this.kokName = kokName;
	}

	/**
	 * 科目出力順序を取得
	 * 
	 * @return odr
	 */
	public String getOdr() {
		return odr;
	}

	/**
	 * 科目出力順序をセット
	 * 
	 * @param odr
	 */
	public void setOdr(String odr) {
		this.odr = odr;
	}

	/**
	 * 集計相手科目コードを取得
	 * 
	 * @return sumCode
	 */
	public String getSumCode() {
		return sumCode;
	}

	/**
	 * 集計相手科目コードをセット
	 * 
	 * @param sumCode
	 */
	public void setSumCode(String sumCode) {
		this.sumCode = sumCode;
	}

	/**
	 * 集計相手科目略称を取得
	 * 
	 * @return sumName
	 */
	public String getSumName() {
		return sumName;
	}

	/**
	 * 集計相手科目略称をセット
	 * 
	 * @param sumName
	 */
	public void setSumName(String sumName) {
		this.sumName = sumName;
	}

	/**
	 * 登録日付を取得
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * 登録日付をセット
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

	/**
	 * 集計区分を取得
	 * 
	 * @return 集計区分
	 */
	public ItemSumType getItemSumType() {
		return this.itemSumType;
	}

	/**
	 * 集計区分をセット
	 * 
	 * @param itemSumType 集計区分
	 */
	public void setItemSumType(ItemSumType itemSumType) {
		this.itemSumType = itemSumType;
	}

	/**
	 * 貸借区分を取得
	 * 
	 * @return 貸借区分
	 */
	public Dc getDc() {
		return this.dc;
	}

	/**
	 * 貸借区分をセット
	 * 
	 * @param dc 貸借区分
	 */
	public void setDc(Dc dc) {
		this.dc = dc;
	}

	/**
	 * 科目種別を取得
	 * 
	 * @return 科目種別
	 */
	public ItemType getItemType() {
		return this.itemType;
	}

	/**
	 * 科目種別をセット
	 * 
	 * @param itemType 科目種別
	 */
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
}

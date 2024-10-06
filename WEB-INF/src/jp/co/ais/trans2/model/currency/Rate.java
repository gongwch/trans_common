package jp.co.ais.trans2.model.currency;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * 通貨レート情報
 */
public class Rate extends TransferBase {

	/** 会社コード */
	protected String companyCode = null;

	/** 通貨 */
	protected Currency currency;

	/** 通貨レート */
	protected BigDecimal currencyRate;
	
	/** レート区分 */
	protected RateType rateType;
	
	/** 適用開始日付 */
	protected Date termFrom;

	/**
	 * 会社コード
	 *
	 * @return companyCode 会社コード
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
	 * 通貨レート
	 *
	 * @return currencyRate 通貨レート
	 */
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * 通貨レート
	 *
	 * @param currencyRate 通貨レート
	 */
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * レート区分
	 *
	 * @return rateType レート区分
	 */
	public RateType getRateType() {
		return rateType;
	}

	/**
	 * レート区分
	 *
	 * @param rateType レート区分
	 */
	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	/**
	 * 通貨
	 *
	 * @return currency 通貨 
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * 通貨
	 *
	 * @param currency 通貨
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * 適用開始日付
	 *
	 * @return termFrom 適用開始日付
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * 適用開始日付
	 *
	 * @param termFrom 適用開始日付
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

}

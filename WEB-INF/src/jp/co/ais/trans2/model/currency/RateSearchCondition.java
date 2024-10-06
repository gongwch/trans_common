package jp.co.ais.trans2.model.currency;

import java.util.*;
import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.RateType;

/**
 * 通貨レートマスタ検索条件
 */
public class RateSearchCondition extends TransferBase implements Cloneable {

	/** serialVersionUID */
	private static final long serialVersionUID = -2837114825950169565L;

	/** 会社コード */
	protected String companyCode = null;

	/** 通貨コード */
	protected String currencyCode = null;

	/** 適用開始日付 */
	protected Date termFrom;

	/** 適用開始日付（開始）（検索条件） */
	protected Date dateFrom;
	
	/** 適用開始日付（終了）（検索条件） */
	protected Date dateTo;

	/** 通貨コード開始 */
	protected String currencyCodeFrom = null;

	/** 通貨コード終了 */
	protected String currencyCodeTo = null;

	/** レートタイプ */
	protected RateType rateType = null;

	/** 社定レートを含むか */
	protected boolean companyRate;

	/** 決算日レート含むか */
	protected boolean settlementRate;

	/** 社定レート（機能通貨）を含むか */
	protected boolean funcCompanyRate;
	
	/** 決算日レート（機能通貨）を含むか */
	protected boolean funcSettlementRate;
	
	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public RateSearchCondition clone() {
		try {
			return (RateSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * 通貨コード
	 *
	 * @return currencyCode 通貨コード
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}
	
	/**
	 * 通貨コード
	 *
	 * @param currencyCode 通貨コード
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * 社定レートチェック
	 *
	 * @return companyRate 社定レートチェック
	 */
	public boolean isCompanyRate() {
		return companyRate;
	}

	/**
	 * 社定レートチェック
	 *
	 * @param companyRate 社定レートチェック
	 */
	public void setCompanyRate(boolean companyRate) {
		this.companyRate = companyRate;
	}

	/**
	 * 社定レート（機能通貨）チェック
	 *
	 * @return funcCompanyRate 社定レート（機能通貨）
	 */
	public boolean isFuncCompanyRate() {
		return funcCompanyRate;
	}

	/**
	 * 社定レート（機能通貨）チェック
	 *
	 * @param funcCompanyRate 社定レート（機能通貨）チェック
	 */
	public void setFuncCompanyRate(boolean funcCompanyRate) {
		this.funcCompanyRate = funcCompanyRate;
	}

	/**
	 * 決算レート（機能通貨）チェック
	 *
	 * @return funcSettlementRate 決算レート（機能通貨）チェック
	 */
	public boolean isFuncSettlementRate() {
		return funcSettlementRate;
	}

	/**
	 * 決算レート（機能通貨）チェック
	 *
	 * @param funcSettlementRate 決算レート（機能通貨）チェック
	 */
	public void setFuncSettlementRate(boolean funcSettlementRate) {
		this.funcSettlementRate = funcSettlementRate;
	}

	/**
	 * 決算レートチェック
	 *
	 * @return settlementRate 決算レートチェック
	 */
	public boolean isSettlementRate() {
		return settlementRate;
	}

	/**
	 * 決算レートチェック
	 *
	 * @param settlementRate 決算レートチェック
	 */
	public void setSettlementRate(boolean settlementRate) {
		this.settlementRate = settlementRate;
	}

	/**
	 * 適用開始日付（開始）（検索条件）
	 *
	 * @return dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * 適用開始日付（開始）（検索条件）
	 *
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * 適用開始日付（終了）（検索条件）
	 *
	 * @return dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * 適用開始日付（終了）（検索条件）
	 *
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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

	public String getCurrencyCodeFrom() {
		return currencyCodeFrom;
	}

	public void setCurrencyCodeFrom(String currencyCodeFrom) {
		this.currencyCodeFrom = currencyCodeFrom;
	}

	public String getCurrencyCodeTo() {
		return currencyCodeTo;
	}

	public void setCurrencyCodeTo(String currencyCodeTo) {
		this.currencyCodeTo = currencyCodeTo;
	}

	public RateType getRateType() {
		return rateType;
	}

	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

}

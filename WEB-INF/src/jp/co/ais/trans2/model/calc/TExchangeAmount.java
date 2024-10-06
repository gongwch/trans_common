package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 通貨換算コンポーネントentity
 */
public class TExchangeAmount {

	/** 邦貨金額 */
	protected BigDecimal keyAmount;

	/** 外貨金額 */
	protected BigDecimal foreignAmount;

	/** レート */
	protected BigDecimal rate;

	/** レート係数 */
	protected int ratePow;

	/** 小数点桁数 */
	protected int digit;

	/** レート換算端数処理 */
	protected ExchangeFraction exchangeFraction = null;

	/** 換算区分 */
	protected ConvertType convertType = null;

	/**
	 * 邦貨金額
	 * 
	 * @return 邦貨金額
	 */
	public BigDecimal getKeyAmount() {
		return this.keyAmount;
	}

	/**
	 * 邦貨金額
	 * 
	 * @param keyAmount 邦貨金額
	 */
	public void setKeyAmount(BigDecimal keyAmount) {
		this.keyAmount = keyAmount;
	}

	/**
	 * レート換算端数処理を取得する
	 * 
	 * @return レート換算端数処理
	 */
	public ExchangeFraction getExchangeFraction() {
		return exchangeFraction;
	}

	/**
	 * レート換算端数処理を設定する
	 * 
	 * @param fraction
	 */
	public void setExchangeFraction(ExchangeFraction fraction) {
		this.exchangeFraction = fraction;
	}

	/**
	 * 換算区分を取得する
	 * 
	 * @return 換算区分
	 */
	public ConvertType getConvertType() {
		return convertType;
	}

	/**
	 * 換算区分を設定する
	 * 
	 * @param type
	 */
	public void setConvertType(ConvertType type) {
		this.convertType = type;
	}

	/**
	 * 小数点桁数を取得する
	 * 
	 * @return digit
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * 小数点桁数を設定する
	 * 
	 * @param digit 小数点桁数
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * 外貨金額を取得する
	 * 
	 * @return foreignMoney
	 */
	public BigDecimal getForeignAmount() {
		return foreignAmount;
	}

	/**
	 * 外貨金額を設定する
	 * 
	 * @param foreignMoney 外貨金額
	 */
	public void setForeignAmount(BigDecimal foreignMoney) {
		this.foreignAmount = foreignMoney;
	}

	/**
	 * レートを取得する
	 * 
	 * @return rate
	 */
	public BigDecimal getRate() {
		return rate;
	}

	/**
	 * レートを設定する
	 * 
	 * @param rate レート
	 */
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}

	/**
	 * レート係数を取得する
	 * 
	 * @return ratePow
	 */
	public int getRatePow() {
		return ratePow;
	}

	/**
	 * レート係数を設定する
	 * 
	 * @param ratePow レート係数
	 */
	public void setRatePow(int ratePow) {
		this.ratePow = ratePow;
	}

	/**
	 * 通貨マスタから換算情報を設定する
	 * 
	 * @param currency 通貨マスタ
	 */
	public void setCurrency(Currency currency) {

		this.ratePow = currency.getRatePow();
		this.digit = currency.getDecimalPoint();

	}

	/**
	 * 会計情報から換算情報を設定する
	 * 
	 * @param config 会計情報
	 */
	public void setAccountConfig(AccountConfig config) {

		this.exchangeFraction = config.getExchangeFraction();
		this.convertType = config.getConvertType();

	}
}
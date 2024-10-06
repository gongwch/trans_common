package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 税額計算用エンティティ
 */
public class TTaxCalculation {

	/** 内税/外税 */
	protected boolean inside;

	/** 対象金額 */
	protected BigDecimal amount;

	/** 税情報 */
	protected ConsumptionTax tax;

	/** 桁数 */
	protected int digit;

	/** 仮払消費税端数処理 */
	protected ExchangeFraction paymentFunction;

	/** 仮受消費税端数処理 */
	protected ExchangeFraction receivingFunction;

	/**
	 * 内税/外税
	 * 
	 * @return true:内税、false:外税
	 */
	public boolean isInside() {
		return inside;
	}

	/**
	 * 内税/外税
	 * 
	 * @param inside true:内税、false:外税
	 */
	public void setInside(boolean inside) {
		this.inside = inside;
	}

	/**
	 * 対象金額
	 * 
	 * @return 対象金額
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * 対象金額
	 * 
	 * @param amount 対象金額
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 桁数
	 * 
	 * @return digit 桁数
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * 桁数
	 * 
	 * @param digit 桁数
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * 税情報
	 * 
	 * @return 税情報
	 */
	public ConsumptionTax getTax() {
		return tax;
	}

	/**
	 * 税情報
	 * 
	 * @param tax 税情報
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;
	}

	/**
	 * 仮払消費税端数処理
	 * 
	 * @return 仮払消費税端数処理
	 */
	public ExchangeFraction getPaymentFunction() {
		return paymentFunction;
	}

	/**
	 * 仮払消費税端数処理
	 * 
	 * @param paymentFunction 仮払消費税端数処理
	 */
	public void setPaymentFunction(ExchangeFraction paymentFunction) {
		this.paymentFunction = paymentFunction;
	}

	/**
	 * 仮受消費税端数処理
	 * 
	 * @return 仮受消費税端数処理
	 */
	public ExchangeFraction getReceivingFunction() {
		return receivingFunction;
	}

	/**
	 * 仮受消費税端数処理
	 * 
	 * @param receivingFunction 仮受消費税端数処理
	 */
	public void setReceivingFunction(ExchangeFraction receivingFunction) {
		this.receivingFunction = receivingFunction;
	}
}

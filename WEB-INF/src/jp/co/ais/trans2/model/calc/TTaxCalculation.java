package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * ΕzvZpGeBeB
 */
public class TTaxCalculation {

	/** ΰΕ/OΕ */
	protected boolean inside;

	/** ΞΫΰz */
	protected BigDecimal amount;

	/** Εξρ */
	protected ConsumptionTax tax;

	/**  */
	protected int digit;

	/** Ό₯ΑοΕ[ */
	protected ExchangeFraction paymentFunction;

	/** ΌσΑοΕ[ */
	protected ExchangeFraction receivingFunction;

	/**
	 * ΰΕ/OΕ
	 * 
	 * @return true:ΰΕAfalse:OΕ
	 */
	public boolean isInside() {
		return inside;
	}

	/**
	 * ΰΕ/OΕ
	 * 
	 * @param inside true:ΰΕAfalse:OΕ
	 */
	public void setInside(boolean inside) {
		this.inside = inside;
	}

	/**
	 * ΞΫΰz
	 * 
	 * @return ΞΫΰz
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * ΞΫΰz
	 * 
	 * @param amount ΞΫΰz
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * 
	 * 
	 * @return digit 
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * 
	 * 
	 * @param digit 
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * Εξρ
	 * 
	 * @return Εξρ
	 */
	public ConsumptionTax getTax() {
		return tax;
	}

	/**
	 * Εξρ
	 * 
	 * @param tax Εξρ
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;
	}

	/**
	 * Ό₯ΑοΕ[
	 * 
	 * @return Ό₯ΑοΕ[
	 */
	public ExchangeFraction getPaymentFunction() {
		return paymentFunction;
	}

	/**
	 * Ό₯ΑοΕ[
	 * 
	 * @param paymentFunction Ό₯ΑοΕ[
	 */
	public void setPaymentFunction(ExchangeFraction paymentFunction) {
		this.paymentFunction = paymentFunction;
	}

	/**
	 * ΌσΑοΕ[
	 * 
	 * @return ΌσΑοΕ[
	 */
	public ExchangeFraction getReceivingFunction() {
		return receivingFunction;
	}

	/**
	 * ΌσΑοΕ[
	 * 
	 * @param receivingFunction ΌσΑοΕ[
	 */
	public void setReceivingFunction(ExchangeFraction receivingFunction) {
		this.receivingFunction = receivingFunction;
	}
}

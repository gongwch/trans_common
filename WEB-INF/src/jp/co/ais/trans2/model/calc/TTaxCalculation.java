package jp.co.ais.trans2.model.calc;

import java.math.*;

import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * �Ŋz�v�Z�p�G���e�B�e�B
 */
public class TTaxCalculation {

	/** ����/�O�� */
	protected boolean inside;

	/** �Ώۋ��z */
	protected BigDecimal amount;

	/** �ŏ�� */
	protected ConsumptionTax tax;

	/** ���� */
	protected int digit;

	/** ��������Œ[������ */
	protected ExchangeFraction paymentFunction;

	/** �������Œ[������ */
	protected ExchangeFraction receivingFunction;

	/**
	 * ����/�O��
	 * 
	 * @return true:���ŁAfalse:�O��
	 */
	public boolean isInside() {
		return inside;
	}

	/**
	 * ����/�O��
	 * 
	 * @param inside true:���ŁAfalse:�O��
	 */
	public void setInside(boolean inside) {
		this.inside = inside;
	}

	/**
	 * �Ώۋ��z
	 * 
	 * @return �Ώۋ��z
	 */
	public BigDecimal getAmount() {
		return amount;
	}

	/**
	 * �Ώۋ��z
	 * 
	 * @param amount �Ώۋ��z
	 */
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	/**
	 * ����
	 * 
	 * @return digit ����
	 */
	public int getDigit() {
		return digit;
	}

	/**
	 * ����
	 * 
	 * @param digit ����
	 */
	public void setDigit(int digit) {
		this.digit = digit;
	}

	/**
	 * �ŏ��
	 * 
	 * @return �ŏ��
	 */
	public ConsumptionTax getTax() {
		return tax;
	}

	/**
	 * �ŏ��
	 * 
	 * @param tax �ŏ��
	 */
	public void setTax(ConsumptionTax tax) {
		this.tax = tax;
	}

	/**
	 * ��������Œ[������
	 * 
	 * @return ��������Œ[������
	 */
	public ExchangeFraction getPaymentFunction() {
		return paymentFunction;
	}

	/**
	 * ��������Œ[������
	 * 
	 * @param paymentFunction ��������Œ[������
	 */
	public void setPaymentFunction(ExchangeFraction paymentFunction) {
		this.paymentFunction = paymentFunction;
	}

	/**
	 * �������Œ[������
	 * 
	 * @return �������Œ[������
	 */
	public ExchangeFraction getReceivingFunction() {
		return receivingFunction;
	}

	/**
	 * �������Œ[������
	 * 
	 * @param receivingFunction �������Œ[������
	 */
	public void setReceivingFunction(ExchangeFraction receivingFunction) {
		this.receivingFunction = receivingFunction;
	}
}

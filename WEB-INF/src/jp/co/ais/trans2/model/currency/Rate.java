package jp.co.ais.trans2.model.currency;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * �ʉ݃��[�g���
 */
public class Rate extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �ʉ� */
	protected Currency currency;

	/** �ʉ݃��[�g */
	protected BigDecimal currencyRate;
	
	/** ���[�g�敪 */
	protected RateType rateType;
	
	/** �K�p�J�n���t */
	protected Date termFrom;

	/**
	 * ��ЃR�[�h
	 *
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}
	
	/**
	 * ��ЃR�[�h
	 *
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �ʉ݃��[�g
	 *
	 * @return currencyRate �ʉ݃��[�g
	 */
	public BigDecimal getCurrencyRate() {
		return currencyRate;
	}

	/**
	 * �ʉ݃��[�g
	 *
	 * @param currencyRate �ʉ݃��[�g
	 */
	public void setCurrencyRate(BigDecimal currencyRate) {
		this.currencyRate = currencyRate;
	}

	/**
	 * ���[�g�敪
	 *
	 * @return rateType ���[�g�敪
	 */
	public RateType getRateType() {
		return rateType;
	}

	/**
	 * ���[�g�敪
	 *
	 * @param rateType ���[�g�敪
	 */
	public void setRateType(RateType rateType) {
		this.rateType = rateType;
	}

	/**
	 * �ʉ�
	 *
	 * @return currency �ʉ� 
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * �ʉ�
	 *
	 * @param currency �ʉ�
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * �K�p�J�n���t
	 *
	 * @return termFrom �K�p�J�n���t
	 */
	public Date getTermFrom() {
		return termFrom;
	}

	/**
	 * �K�p�J�n���t
	 *
	 * @param termFrom �K�p�J�n���t
	 */
	public void setTermFrom(Date termFrom) {
		this.termFrom = termFrom;
	}

}

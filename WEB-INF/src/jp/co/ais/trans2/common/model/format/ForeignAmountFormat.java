package jp.co.ais.trans2.common.model.format;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �O�݂̃t�H�[�}�b�^
 * 
 * @author AIS
 */
public class ForeignAmountFormat {

	/**
	 * �O�݂��t�H�[�}�b�g����
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return �O�݂��t�H�[�}�b�g����BigDecimal
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency, BigDecimal amount) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (amount == null) {
			return "";
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return "";
		}

		return getPrefix() + NumberFormatUtil.formatNumber(amount, currency.getDecimalPoint()) + getSuffix();

	}

	/**
	 * �O�݂��t�H�[�}�b�g����
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @param decimalPoint
	 * @return �O�݂��t�H�[�}�b�g����BigDecimal
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode, BigDecimal amount, int decimalPoint)
		throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (amount == null) {
			return "";
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return "";
		}

		return getPrefix() + NumberFormatUtil.formatNumber(amount, decimalPoint) + getSuffix();

	}

	/**
	 * �O�݂��t�H�[�}�b�g��BigDecimal�ŕԂ�
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return �O�݂��t�H�[�}�b�g����BigDecimal
	 * @throws TException
	 */
	public BigDecimal formatDecimal(Currency keyCurrency, Currency currency, BigDecimal amount) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (amount == null) {
			return null;
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return null;
		}

		return amount;

	}

	/**
	 * �O�݂��t�H�[�}�b�g����
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @return �O�݂��t�H�[�}�b�g����BigDecimal
	 * @throws TException
	 */
	public BigDecimal formatDecimal(String keyCurrencyCode, String currencyCode, BigDecimal amount) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (amount == null) {
			return null;
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return null;
		}

		return amount;

	}

	/**
	 * �O�݂��t�H�[�}�b�g����
	 * 
	 * @param amount
	 * @param decimalPoint
	 * @return �O�݂��t�H�[�}�b�g����BigDecimal
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	public String format(BigDecimal amount, int decimalPoint) throws TException {

		if (amount == null) {
			return "";
		}

		return getPrefix() + NumberFormatUtil.formatNumber(amount, decimalPoint) + getSuffix();

	}

	/**
	 * @return �O�ݐ擪
	 */
	protected String getPrefix() {
		return "(";
	}

	/**
	 * @return �O�ݖ�
	 */
	protected String getSuffix() {
		return ")";
	}

}

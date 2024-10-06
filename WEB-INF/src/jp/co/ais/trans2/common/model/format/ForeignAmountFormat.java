package jp.co.ais.trans2.common.model.format;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 外貨のフォーマッタ
 * 
 * @author AIS
 */
public class ForeignAmountFormat {

	/**
	 * 外貨をフォーマットする
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return 外貨をフォーマットしたBigDecimal
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency, BigDecimal amount) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
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
	 * 外貨をフォーマットする
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @param decimalPoint
	 * @return 外貨をフォーマットしたBigDecimal
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode, BigDecimal amount, int decimalPoint)
		throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
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
	 * 外貨をフォーマットしBigDecimalで返す
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return 外貨をフォーマットしたBigDecimal
	 * @throws TException
	 */
	public BigDecimal formatDecimal(Currency keyCurrency, Currency currency, BigDecimal amount) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
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
	 * 外貨をフォーマットする
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @return 外貨をフォーマットしたBigDecimal
	 * @throws TException
	 */
	public BigDecimal formatDecimal(String keyCurrencyCode, String currencyCode, BigDecimal amount) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
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
	 * 外貨をフォーマットする
	 * 
	 * @param amount
	 * @param decimalPoint
	 * @return 外貨をフォーマットしたBigDecimal
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
	 * @return 外貨先頭
	 */
	protected String getPrefix() {
		return "(";
	}

	/**
	 * @return 外貨末
	 */
	protected String getSuffix() {
		return ")";
	}

}

package jp.co.ais.trans2.common.model.format;

import java.math.BigDecimal;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.NumberFormatUtil;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * レートのフォーマッタ
 * 
 * @author AIS
 */
public class RateFormat {

	/**
	 * レートの小数点以下桁数を返す
	 * 
	 * @return
	 */
	public static int getRateDecimalPoint() {
		return 4;
	}

	/**
	 * レートをフォーマットする
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param rate
	 * @return
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (rate == null) {
			return "";
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return "";
		}

		return "@" + NumberFormatUtil.formatNumber(rate, 4);

	}

	/**
	 * レートをフォーマットした値を返す
	 * 
	 * @param keyCurrencyCode 基軸通貨
	 * @param currencyCode 取引通貨
	 * @param rate レート
	 * @return フォーマットされたレート
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (rate == null) {
			return "";
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return "";
		}

		return "@" + NumberFormatUtil.formatNumber(rate, 4);

	}

	/**
	 * レートをフォーマットする
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param rate
	 * @return
	 * @throws TException
	 */
	public BigDecimal formatDecimal(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (rate == null) {
			return null;
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return null;
		}

		return rate;

	}

	/**
	 * レートをフォーマットした値を返す
	 * 
	 * @param keyCurrencyCode 基軸通貨
	 * @param currencyCode 取引通貨
	 * @param rate レート
	 * @return フォーマットされたレート
	 * @throws TException
	 */
	public BigDecimal formatDecimal(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (rate == null) {
			return null;
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return null;
		}

		return rate;

	}

	/**
	 * レートをフォーマットする
	 * 
	 * @param rate
	 * @return
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	public String format(BigDecimal rate) throws TException {

		if (rate == null) {
			return "";
		}

		return "@" + NumberFormatUtil.formatNumber(rate, 4);

	}

}

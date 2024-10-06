package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 通貨のフォーマッタ
 * 
 * @author AIS
 */
public class CurrencyFormat {

	/**
	 * 通貨をフォーマットする
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @return
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency) throws TException {
		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return "";
		}

		return currency.getCode();

	}

	/**
	 * 通貨をフォーマットする
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @return
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// フォーマットエラー：基軸通貨が取得できません。
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return "";
		}

		return currencyCode;

	}

}

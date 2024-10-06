package jp.co.ais.trans2.common.model.format;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * �ʉ݂̃t�H�[�}�b�^
 * 
 * @author AIS
 */
public class CurrencyFormat {

	/**
	 * �ʉ݂��t�H�[�}�b�g����
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @return
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency) throws TException {
		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (keyCurrency.getCode().equals(currency.getCode())) {
			return "";
		}

		return currency.getCode();

	}

	/**
	 * �ʉ݂��t�H�[�}�b�g����
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @return
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
		}

		if (keyCurrencyCode.equals(currencyCode)) {
			return "";
		}

		return currencyCode;

	}

}

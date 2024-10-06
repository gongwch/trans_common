package jp.co.ais.trans2.common.model.format;

import java.math.BigDecimal;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.NumberFormatUtil;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ���[�g�̃t�H�[�}�b�^
 * 
 * @author AIS
 */
public class RateFormat {

	/**
	 * ���[�g�̏����_�ȉ�������Ԃ�
	 * 
	 * @return
	 */
	public static int getRateDecimalPoint() {
		return 4;
	}

	/**
	 * ���[�g���t�H�[�}�b�g����
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param rate
	 * @return
	 * @throws TException
	 */
	public String format(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
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
	 * ���[�g���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrencyCode ��ʉ�
	 * @param currencyCode ����ʉ�
	 * @param rate ���[�g
	 * @return �t�H�[�}�b�g���ꂽ���[�g
	 * @throws TException
	 */
	public String format(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
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
	 * ���[�g���t�H�[�}�b�g����
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param rate
	 * @return
	 * @throws TException
	 */
	public BigDecimal formatDecimal(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		if (keyCurrency == null || keyCurrency.getCode() == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
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
	 * ���[�g���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrencyCode ��ʉ�
	 * @param currencyCode ����ʉ�
	 * @param rate ���[�g
	 * @return �t�H�[�}�b�g���ꂽ���[�g
	 * @throws TException
	 */
	public BigDecimal formatDecimal(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		if (keyCurrencyCode == null) {
			throw new TException("E00040");// �t�H�[�}�b�g�G���[�F��ʉ݂��擾�ł��܂���B
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
	 * ���[�g���t�H�[�}�b�g����
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

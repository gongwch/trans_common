package jp.co.ais.trans2.common.model.format;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * �Ɩ��֘A�t�H�[�}�b�g�̃��[�e�B���e�B
 * 
 * @author AIS
 */
public class FormatUtil {

	/** �O�݃t�H�[�}�b�g */
	protected static ForeignAmountFormat foreignAmountFormat;

	/** ���[�g�t�H�[�}�b�g */
	protected static RateFormat rateFormat;

	/** �ʉ݃t�H�[�}�b�g */
	protected static CurrencyFormat currencyFormat;

	/** ���Z�i�K�̃t�H�[�}�b�g */
	protected static SettlementLevelFormat settlementLevelFormat;

	static {
		foreignAmountFormat = FormatFactory.getForeignAmountFormat();
		rateFormat = FormatFactory.getRateFormat();
		currencyFormat = FormatFactory.getCurrencyFormat();
		settlementLevelFormat = FormatFactory.getSettlementLevelFormat();
	}

	/**
	 * �O�݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static String foreingAmountFormat(Currency keyCurrency, Currency currency, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.format(keyCurrency, currency, amount);
	}

	/**
	 * �O�݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static BigDecimal foreingAmountDecimalFormat(Currency keyCurrency, Currency currency, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.formatDecimal(keyCurrency, currency, amount);
	}

	/**
	 * �O�݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @param decimalPoint
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static String foreingAmountFormat(String keyCurrencyCode, String currencyCode, BigDecimal amount,
		int decimalPoint) throws TException {
		return foreignAmountFormat.format(keyCurrencyCode, currencyCode, amount, decimalPoint);
	}

	/**
	 * �O�݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param amount
	 * @param decimalPoint
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static String foreingAmountFormat(BigDecimal amount, int decimalPoint) throws TException {
		return foreignAmountFormat.format(amount, decimalPoint);
	}

	/**
	 * �O�݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static BigDecimal foreingAmountDecimalFormat(String keyCurrencyCode, String currencyCode, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.formatDecimal(keyCurrencyCode, currencyCode, amount);
	}

	/**
	 * ���[�g���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrency ��ʉ�
	 * @param currency ����ʉ�
	 * @param rate ���[�g
	 * @return �t�H�[�}�b�g���ꂽ���[�g
	 * @throws TException
	 */
	public static String rateFormat(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		return rateFormat.format(keyCurrency, currency, rate);

	}

	/**
	 * ���[�g���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrency ��ʉ�
	 * @param currency ����ʉ�
	 * @param rate ���[�g
	 * @return �t�H�[�}�b�g���ꂽ���[�g
	 * @throws TException
	 */
	public static BigDecimal rateDecimalFormat(Currency keyCurrency, Currency currency, BigDecimal rate)
		throws TException {

		return rateFormat.formatDecimal(keyCurrency, currency, rate);

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
	public static String rateFormat(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		return rateFormat.format(keyCurrencyCode, currencyCode, rate);

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
	public static BigDecimal rateDecimalFormat(String keyCurrencyCode, String currencyCode, BigDecimal rate)
		throws TException {

		return rateFormat.formatDecimal(keyCurrencyCode, currencyCode, rate);

	}

	/**
	 * ���[�g���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param rate ���[�g
	 * @return �t�H�[�}�b�g���ꂽ���[�g
	 * @throws TException
	 */
	public static String rateFormat(BigDecimal rate) throws TException {
		return rateFormat.format(rate);
	}

	/**
	 * ���[�g�̏����_�ȉ�������Ԃ�
	 * 
	 * @return �����_����
	 */
	public static int getRateDecimalPoint() {
		return RateFormat.getRateDecimalPoint();
	}

	/**
	 * �ʉ݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static String currencyFormat(Currency keyCurrency, Currency currency) throws TException {
		return currencyFormat.format(keyCurrency, currency);
	}

	/**
	 * �ʉ݂��t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @return �t�H�[�}�b�g�㕶��
	 * @throws TException
	 */
	public static String currencyFormat(String keyCurrencyCode, String currencyCode) throws TException {
		return currencyFormat.format(keyCurrencyCode, currencyCode);
	}

	/**
	 * ���Z�i�K���t�H�[�}�b�g�����l��Ԃ�
	 * 
	 * @param settlementLevel
	 * @param lang ����R�[�h
	 * @return �t�H�[�}�b�g�㕶��
	 */
	public static String settlementLevelFormat(int settlementLevel, String lang) {
		return settlementLevelFormat(settlementLevel, lang, false);
	}

	/**
	 * ���Z�i�K���t�H�[�}�b�g�����l��Ԃ�<br>
	 * �ʏ�̏ꍇ�̓u�����N<br>
	 * �ȊO�Fn�����Z
	 * 
	 * @param settlementLevel
	 * @param lang ����R�[�h
	 * @param normalBlank true�̏ꍇ<br>
	 *            �ʏ�̏ꍇ�̓u�����N<br>
	 *            �ȊO�Fn�����Z
	 * @return �t�H�[�}�b�g�㕶��
	 */
	public static String settlementLevelFormat(int settlementLevel, String lang, boolean normalBlank) {
		if (normalBlank) {
			return settlementLevelFormat.formatSettlement(settlementLevel, lang);
		}
		return settlementLevelFormat.format(settlementLevel, lang);
	}

	/**
	 * �w�蒠��̕\���ʉ݂̏����_�ȉ�������Ԃ�
	 * 
	 * @param currencyType �\���ʉ�
	 * @param company ��Џ��
	 * @return �w�蒠��̕\���ʉ݂̏����_�ȉ�����
	 */
	public static int getDecimalPoint(CurrencyType currencyType, Company company) {

		int decimalPoint = company.getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (CurrencyType.FUNCTIONAL == currencyType) {
			decimalPoint = company.getAccountConfig().getFunctionalCurrency().getDecimalPoint();
		}
		return decimalPoint;
	}

}

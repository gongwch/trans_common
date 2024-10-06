package jp.co.ais.trans2.common.model.format;

import java.math.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;

/**
 * 業務関連フォーマットのユーティリティ
 * 
 * @author AIS
 */
public class FormatUtil {

	/** 外貨フォーマット */
	protected static ForeignAmountFormat foreignAmountFormat;

	/** レートフォーマット */
	protected static RateFormat rateFormat;

	/** 通貨フォーマット */
	protected static CurrencyFormat currencyFormat;

	/** 決算段階のフォーマット */
	protected static SettlementLevelFormat settlementLevelFormat;

	static {
		foreignAmountFormat = FormatFactory.getForeignAmountFormat();
		rateFormat = FormatFactory.getRateFormat();
		currencyFormat = FormatFactory.getCurrencyFormat();
		settlementLevelFormat = FormatFactory.getSettlementLevelFormat();
	}

	/**
	 * 外貨をフォーマットした値を返す
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static String foreingAmountFormat(Currency keyCurrency, Currency currency, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.format(keyCurrency, currency, amount);
	}

	/**
	 * 外貨をフォーマットした値を返す
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @param amount
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static BigDecimal foreingAmountDecimalFormat(Currency keyCurrency, Currency currency, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.formatDecimal(keyCurrency, currency, amount);
	}

	/**
	 * 外貨をフォーマットした値を返す
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @param decimalPoint
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static String foreingAmountFormat(String keyCurrencyCode, String currencyCode, BigDecimal amount,
		int decimalPoint) throws TException {
		return foreignAmountFormat.format(keyCurrencyCode, currencyCode, amount, decimalPoint);
	}

	/**
	 * 外貨をフォーマットした値を返す
	 * 
	 * @param amount
	 * @param decimalPoint
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static String foreingAmountFormat(BigDecimal amount, int decimalPoint) throws TException {
		return foreignAmountFormat.format(amount, decimalPoint);
	}

	/**
	 * 外貨をフォーマットした値を返す
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @param amount
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static BigDecimal foreingAmountDecimalFormat(String keyCurrencyCode, String currencyCode, BigDecimal amount)
		throws TException {
		return foreignAmountFormat.formatDecimal(keyCurrencyCode, currencyCode, amount);
	}

	/**
	 * レートをフォーマットした値を返す
	 * 
	 * @param keyCurrency 基軸通貨
	 * @param currency 取引通貨
	 * @param rate レート
	 * @return フォーマットされたレート
	 * @throws TException
	 */
	public static String rateFormat(Currency keyCurrency, Currency currency, BigDecimal rate) throws TException {

		return rateFormat.format(keyCurrency, currency, rate);

	}

	/**
	 * レートをフォーマットした値を返す
	 * 
	 * @param keyCurrency 基軸通貨
	 * @param currency 取引通貨
	 * @param rate レート
	 * @return フォーマットされたレート
	 * @throws TException
	 */
	public static BigDecimal rateDecimalFormat(Currency keyCurrency, Currency currency, BigDecimal rate)
		throws TException {

		return rateFormat.formatDecimal(keyCurrency, currency, rate);

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
	public static String rateFormat(String keyCurrencyCode, String currencyCode, BigDecimal rate) throws TException {

		return rateFormat.format(keyCurrencyCode, currencyCode, rate);

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
	public static BigDecimal rateDecimalFormat(String keyCurrencyCode, String currencyCode, BigDecimal rate)
		throws TException {

		return rateFormat.formatDecimal(keyCurrencyCode, currencyCode, rate);

	}

	/**
	 * レートをフォーマットした値を返す
	 * 
	 * @param rate レート
	 * @return フォーマットされたレート
	 * @throws TException
	 */
	public static String rateFormat(BigDecimal rate) throws TException {
		return rateFormat.format(rate);
	}

	/**
	 * レートの小数点以下桁数を返す
	 * 
	 * @return 小数点桁数
	 */
	public static int getRateDecimalPoint() {
		return RateFormat.getRateDecimalPoint();
	}

	/**
	 * 通貨をフォーマットした値を返す
	 * 
	 * @param keyCurrency
	 * @param currency
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static String currencyFormat(Currency keyCurrency, Currency currency) throws TException {
		return currencyFormat.format(keyCurrency, currency);
	}

	/**
	 * 通貨をフォーマットした値を返す
	 * 
	 * @param keyCurrencyCode
	 * @param currencyCode
	 * @return フォーマット後文字
	 * @throws TException
	 */
	public static String currencyFormat(String keyCurrencyCode, String currencyCode) throws TException {
		return currencyFormat.format(keyCurrencyCode, currencyCode);
	}

	/**
	 * 決算段階をフォーマットした値を返す
	 * 
	 * @param settlementLevel
	 * @param lang 言語コード
	 * @return フォーマット後文字
	 */
	public static String settlementLevelFormat(int settlementLevel, String lang) {
		return settlementLevelFormat(settlementLevel, lang, false);
	}

	/**
	 * 決算段階をフォーマットした値を返す<br>
	 * 通常の場合はブランク<br>
	 * 以外：n次決算
	 * 
	 * @param settlementLevel
	 * @param lang 言語コード
	 * @param normalBlank trueの場合<br>
	 *            通常の場合はブランク<br>
	 *            以外：n次決算
	 * @return フォーマット後文字
	 */
	public static String settlementLevelFormat(int settlementLevel, String lang, boolean normalBlank) {
		if (normalBlank) {
			return settlementLevelFormat.formatSettlement(settlementLevel, lang);
		}
		return settlementLevelFormat.format(settlementLevel, lang);
	}

	/**
	 * 指定帳簿の表示通貨の小数点以下桁数を返す
	 * 
	 * @param currencyType 表示通貨
	 * @param company 会社情報
	 * @return 指定帳簿の表示通貨の小数点以下桁数
	 */
	public static int getDecimalPoint(CurrencyType currencyType, Company company) {

		int decimalPoint = company.getAccountConfig().getKeyCurrency().getDecimalPoint();
		if (CurrencyType.FUNCTIONAL == currencyType) {
			decimalPoint = company.getAccountConfig().getFunctionalCurrency().getDecimalPoint();
		}
		return decimalPoint;
	}

}

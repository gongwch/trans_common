package jp.co.ais.trans.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * 数値フォーマット用ユーティリティ
 */
public final class NumberFormatUtil {

	/**
	 * 小数点桁数指定の追加用フォーマット文字列を取得する
	 * 
	 * @param digit 小数点桁数
	 * @return 金額用フォーマット
	 */
	public static String makeExcelNumberFormat(int digit) {
		String nf = makeFormatStyle("#,##0", digit, "0");
		return nf;
	}

	/**
	 * 小数点桁数指定の追加用フォーマット文字列を取得する
	 * 
	 * @param digit 小数点桁数
	 * @return 金額用フォーマット
	 */
	public static String makeNumberFormat(int digit) {
		return makeFormatStyle("###,###,###,###,##0", digit, "0");
	}

	/**
	 * 小数点桁数指定の追加用フォーマット文字列を取得する
	 * 
	 * @param digit 小数点桁数
	 * @return 金額用フォーマット
	 */
	public static String makeNumberSharpFormat(int digit) {
		return makeFormatStyle("###,###,###,###,##0", digit, "#");
	}

	/**
	 * 小数点桁数指定の追加用フォーマット文字列を取得する
	 * 
	 * @param integerStr 整数部フォーマット
	 * @param digit 小数点桁数
	 * @param digitsStr 少数点部分が 0 か # か
	 * @return 金額用フォーマット
	 */
	private static String makeFormatStyle(String integerStr, int digit, String digitsStr) {

		StringBuilder buff = new StringBuilder();

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append(digitsStr);
			}
		}

		return integerStr + buff.toString();
	}

	/**
	 * 数値フォーマットして返す.
	 * 
	 * @param value 数値
	 * @param digit 小数点桁数
	 * @return 数値フォーマット変換
	 */
	public static String formatNumber(BigDecimal value, int digit) {

		BigDecimal dec = (value == null) ? BigDecimal.ZERO : value;

		// 形式を設定
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setParseIntegerOnly(true);
		numberFormat.setMaximumFractionDigits(digit);
		numberFormat.setMinimumFractionDigits(digit);

		return numberFormat.format(dec);
	}

	/**
	 * 数値フォーマットして返す.
	 * 
	 * @param value 数値
	 * @param digit 小数点桁数
	 * @return 数値フォーマット変換
	 */
	public static String formatNumber(double value, int digit) {
		if (value == -0d) {
			// -0表示を防ぐ
			value = 0d;
		}

		// 形式を設定
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setParseIntegerOnly(true);
		numberFormat.setMaximumFractionDigits(digit);
		numberFormat.setMinimumFractionDigits(digit);

		return numberFormat.format(value);
	}

	/**
	 * 小数点桁数0のフォーマットをして返す. <br>
	 * 空文字を指定された場合は、0を返す.
	 * 
	 * @param value 文字列型数値
	 * @return フォーマット数値文字列
	 */
	public static String formatNumber(String value) {
		return NumberFormatUtil.formatNumber(value, 0);
	}

	/**
	 * 小数点桁数0のフォーマットをして返す.
	 * 
	 * @param value 数値
	 * @return フォーマット数値文字列
	 */
	public static String formatNumber(BigDecimal value) {
		return formatNumber(value, 0);
	}

	/**
	 * 小数点桁数0のフォーマットをして返す.
	 * 
	 * @param value 数値
	 * @return フォーマット数値文字列
	 */
	public static String formatNumber(double value) {
		return formatNumber(value, 0);
	}

	/**
	 * 文字列型数値を数値フォーマットして返す. <br>
	 * 空文字を指定された場合は、0を返す.
	 * 
	 * @param value 文字列型数値
	 * @param digit 小数点桁数（文字列）
	 * @return 数値フォーマット変換
	 */
	public static String formatNumber(Object value, Object digit) {

		// 小数点
		int intDigit;
		if (digit instanceof Integer) {
			intDigit = ((Integer) digit).intValue();
		} else {
			String strDigit = Util.avoidNull(digit);
			intDigit = "".equals(strDigit) ? 0 : Integer.parseInt(strDigit);
		}

		if (value instanceof Double) {
			return formatNumber(((Double) value).doubleValue(), intDigit);
		}

		// BigDecimalの場合
		BigDecimal decimalValue;
		if (value instanceof BigDecimal) {
			decimalValue = (BigDecimal) value;

		} else {

			String strValue = String.valueOf(value).trim();
			decimalValue = Util.isNullOrEmpty(strValue) || !Util.isNumber(strValue) ? BigDecimal.ZERO
					: new BigDecimal(
							strValue);
		}

		return formatNumber(decimalValue, intDigit);
	}

	/**
	 * 数字のフォーマット
	 * 
	 * @param value 数値
	 * @param digit 小数点桁数
	 * @return formatStyle フォーマット形式
	 */
	public static String formatNumberNonComma(String value, int digit) {

		StringBuilder buff = new StringBuilder("##0");

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append("0");
			}
		}

		DecimalFormat decimalFormat = new DecimalFormat(buff.toString());

		BigDecimal decimalValue;

		if (Util.isNullOrEmpty(value)) {

			decimalValue = BigDecimal.ZERO;
		} else {

			String strValue = Util.avoidNull(value).trim().replace(",", "");
			decimalValue = Util.isNullOrEmpty(strValue) || !Util.isNumber(strValue) ? BigDecimal.ZERO
					: new BigDecimal(
							strValue);
		}

		return decimalFormat.format(decimalValue);
	}

	/**
	 * 数字のフォーマット.#,##0.####形式<br>
	 * 
	 * @param value 数値
	 * @param digit 小数点桁数
	 * @return formatStyle フォーマット形式
	 */
	public static String formatNumberAbsentZero(Object value, int digit) {

		BigDecimal decimalValue = BigDecimal.ZERO;

		if (value != null) {
			if (value instanceof BigDecimal) {
				decimalValue = (BigDecimal) value;

			} else {
				decimalValue = DecimalUtil.toBigDecimal(String.valueOf(value));
			}
		}

		String format = NumberFormatUtil.makeNumberSharpFormat(digit);
		DecimalFormat decimalFormat = new DecimalFormat(format);

		return decimalFormat.format(decimalValue);
	}

	/**
	 * 数値を指定された桁数のゼロフィル文字として返す
	 * 
	 * @param number 対象数値
	 * @param keta 桁数
	 * @return ゼロフィル文字
	 */
	public static String zeroFill(Number number, int keta) {
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < keta; i++) {
			buff.append("0");
		}

		DecimalFormat formatter = new DecimalFormat(buff.toString());

		return formatter.format(number);
	}

	/**
	 * ゼロ値、NULL値の場合に空文字を返す.<br>
	 * ゼロ値、NULL値じゃない場合はフォーマットして返す.
	 * 
	 * @param num 数値
	 * @param digit 小数点
	 * @return 変換文字
	 */
	public static String zeroToEmptyFormat(BigDecimal num, int digit) {
		if (DecimalUtil.isNullOrZero(num)) {
			return "";

		} else {
			return NumberFormatUtil.formatNumber(num, digit);
		}
	}

	/**
	 * NULL値の場合に0としてフォーマットして返す.
	 * 
	 * @param num 数値
	 * @param digit 小数点
	 * @return 変換文字
	 */
	public static String nullToZeroFormat(BigDecimal num, int digit) {
		if (num == null) {
			return NumberFormatUtil.formatNumber(BigDecimal.ZERO, digit);

		} else {
			return NumberFormatUtil.formatNumber(num, digit);
		}
	}

	/**
	 * 指定通貨記号付与形式の金額フォーマットに変換する.<br>
	 * マイナス値の場合、通貨コードの前にマイナスを付与.<br>
	 * 例) -USD1,000.00、-$1,000.00 等<br>
	 * ゼロ値、NULL値の場合に空文字を返す.
	 * 
	 * @param currency 通貨記号
	 * @param num 数値
	 * @param digit 小数点
	 * @return 変換文字
	 */
	public static String zeroToEmptyCurrencyFormat(String currency, BigDecimal num, int digit) {

		if (DecimalUtil.isNullOrZero(num)) {
			return "";
		}

		BigDecimal amount = num;
		String muinus = "";

		if (num.compareTo(BigDecimal.ZERO) < 0) {
			muinus = "-";
			amount = amount.abs(); // 絶対値に変換
		}

		return muinus + currency + zeroToEmptyFormat(amount, digit);
	}

	/**
	 * 指定通貨記号付与形式の金額フォーマットに変換する.<br>
	 * マイナス値の場合、通貨コードの前にマイナスを付与.<br>
	 * 例) -USD1,000.00、-$1,000.00 等<br>
	 * NULL値の場合、0としてフォーマットして返す.
	 * 
	 * @param currency 通貨記号
	 * @param num 数値
	 * @param digit 小数点
	 * @return 変換文字
	 */
	public static String nullToZeroCurrencyFormat(String currency, BigDecimal num, int digit) {

		BigDecimal amount = (DecimalUtil.isNullOrZero(num)) ? BigDecimal.ZERO : num;
		String muinus = "";

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			muinus = "-";
			amount = amount.abs(); // 絶対値に変換
		}

		return muinus + currency + nullToZeroFormat(amount, digit);
	}
}

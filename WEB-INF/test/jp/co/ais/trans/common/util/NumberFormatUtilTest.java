package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

class NumberFormatUtilTest {

	@Test
	public void testMakeExcelNumberFormat() {
		// 異なる桁数のテストケース
		assertEquals("#,##0", NumberFormatUtil.makeExcelNumberFormat(0));
		assertEquals("#,##0.0", NumberFormatUtil.makeExcelNumberFormat(1));
		assertEquals("#,##0.00", NumberFormatUtil.makeExcelNumberFormat(2));
		assertEquals("#,##0.000", NumberFormatUtil.makeExcelNumberFormat(3));

		// エッジケース
		assertEquals("#,##0", NumberFormatUtil.makeExcelNumberFormat(-1)); // 負の桁数でも基本フォーマットが返されるべき
		//TODO: java.lang.OutOfMemoryError
		//assertEquals("#,##0", NumberFormatUtil.makeExcelNumberFormat(Integer.MAX_VALUE)); // 大きな桁数のテスト

		// 追加のケース
		assertEquals("#,##0.000000", NumberFormatUtil.makeExcelNumberFormat(6)); // 小数点以下の桁数が多い場合
	}

	@Test
	void testMakeNumberFormat() {
		// 異なる桁数のテストケース
		assertEquals("###,###,###,###,##0", NumberFormatUtil.makeNumberFormat(0));
		assertEquals("###,###,###,###,##0.0", NumberFormatUtil.makeNumberFormat(1));
		assertEquals("###,###,###,###,##0.00", NumberFormatUtil.makeNumberFormat(2));
		assertEquals("###,###,###,###,##0.000", NumberFormatUtil.makeNumberFormat(3));
		// エッジケース
		assertEquals("###,###,###,###,##0", NumberFormatUtil.makeNumberFormat(-1)); // 負の桁数でも基本フォーマットが返されるべき
	}

	@Test
	void testMakeNumberSharpFormat() {
		// 異なる桁数のテストケース
		assertEquals("###,###,###,###,##0", NumberFormatUtil.makeNumberSharpFormat(0));
		assertEquals("###,###,###,###,##0.#", NumberFormatUtil.makeNumberSharpFormat(1));
		assertEquals("###,###,###,###,##0.##", NumberFormatUtil.makeNumberSharpFormat(2));
		assertEquals("###,###,###,###,##0.###", NumberFormatUtil.makeNumberSharpFormat(3));
	}

	@Test
	void testFormatNumberBigDecimalInt() {
		// 小数点以下2桁のnull値のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber(null, 2));

		// 小数点以下0桁のゼロ値のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber(BigDecimal.ZERO, 2));

		// 小数点以下2桁の正の値のテスト
		assertEquals("123.46", NumberFormatUtil.formatNumber(new BigDecimal("123.456"), 2));

		// 小数点以下0桁の正の値のテスト
		assertEquals("124", NumberFormatUtil.formatNumber(new BigDecimal("123.856"), 0));

		// 小数点以下3桁の負の値のテスト
		assertEquals("-123.457", NumberFormatUtil.formatNumber(new BigDecimal("-123.4567"), 3));

		// 小数点以下2桁の大きな値のテスト
		assertEquals("1,234,567,890.12", NumberFormatUtil.formatNumber(new BigDecimal("1234567890.123456"), 2));
	}

	@Test
	void testFormatNumberDoubleInt() {
		// 小数点以下2桁の-0のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber(-0d, 2));

		// 小数点以下0桁のゼロ値のテスト
		assertEquals("0", NumberFormatUtil.formatNumber(0d, 0));

		// 小数点以下2桁の正の値のテスト
		assertEquals("123.46", NumberFormatUtil.formatNumber(123.456, 2));

		// 小数点以下0桁の正の値のテスト
		assertEquals("123", NumberFormatUtil.formatNumber(123.456, 0));

		// 小数点以下3桁の負の値のテスト
		assertEquals("-123.457", NumberFormatUtil.formatNumber(-123.4567, 3)); // 四捨五入

		// 小数点以下2桁の大きな値のテスト
		assertEquals("1,234,567,890.12", NumberFormatUtil.formatNumber(1234567890.123456, 2));

		// 小数部がない値で小数点以下2桁のテスト
		assertEquals("123.00", NumberFormatUtil.formatNumber(123d, 2));
	}

	@Test
	void testFormatNumberString() {
		// 通常の整数値のテスト
		assertEquals("12,345", NumberFormatUtil.formatNumber("12345"));

		// 小数値のテスト; 小数部は切り捨てられるべき
		assertEquals("12,346", NumberFormatUtil.formatNumber("12345.6789"));

		// 空文字列のテスト; "0"が返されるべき
		assertEquals("0", NumberFormatUtil.formatNumber(""));

		// 数字以外の入力のテスト; "0"が返されるべき
		assertEquals("0", NumberFormatUtil.formatNumber("abc"));

		// 負の値のテスト
		assertEquals("-12,346", NumberFormatUtil.formatNumber("-12345.6789"));

		// 非常に大きな数値のテスト
		assertEquals("123,456,789,012,345", NumberFormatUtil.formatNumber("123456789012345"));

		// 前後に空白がある数値のテスト
		assertEquals("12,345", NumberFormatUtil.formatNumber("   12345   "));
	}

	@Test
	void testFormatNumberBigDecimal() {
		// 正のBigDecimal値のテスト
		assertEquals("12,346", NumberFormatUtil.formatNumber(new BigDecimal("12345.6789")));

		// 負のBigDecimal値のテスト
		assertEquals("-12,346", NumberFormatUtil.formatNumber(new BigDecimal("-12345.6789")));

		// ゼロ値のテスト
		assertEquals("0", NumberFormatUtil.formatNumber(BigDecimal.ZERO));

		// 非常に大きなBigDecimal値のテスト
		assertEquals("123,456,789,012,345", NumberFormatUtil.formatNumber(new BigDecimal("123456789012345")));

		// 非常に小さなBigDecimal値のテスト
		assertEquals("0", NumberFormatUtil.formatNumber(new BigDecimal("0.0001")));

		// 小数部がない値のテスト
		assertEquals("1,000", NumberFormatUtil.formatNumber(new BigDecimal("1000")));

		// 大きな値と小さな値を一つのテストケースで
		assertEquals("1", NumberFormatUtil.formatNumber(new BigDecimal("0.9999999999")));
		assertEquals("1,000,000,000", NumberFormatUtil.formatNumber(new BigDecimal("1000000000.00000001")));
	}

	@Test
	void testFormatNumberDouble() {
		// -0のテスト
		assertEquals("0", NumberFormatUtil.formatNumber(-0d));

		// ゼロ値のテスト
		assertEquals("0", NumberFormatUtil.formatNumber(0d));

		// 正の値のテスト
		assertEquals("124", NumberFormatUtil.formatNumber(123.756d));

		// 負の値のテスト
		assertEquals("-123", NumberFormatUtil.formatNumber(-123.4567d)); // 四捨五入

		// 大きな値のテスト
		assertEquals("1,234,567,890", NumberFormatUtil.formatNumber(1234567890.123456d));

		// 小数部がない値のテスト
		assertEquals("123", NumberFormatUtil.formatNumber(123d));
	}

	@Test
	public void testFormatNumberObjectObject() {
		// 文字列の数値入力と整数桁のテスト
		assertEquals("12,346", NumberFormatUtil.formatNumber("12345.6789", 0));
		assertEquals("12,345.68", NumberFormatUtil.formatNumber("12345.6789", 2));

		// 文字列の非数値入力と整数桁のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber("abc", 2));

		// 空文字列と整数桁のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber("", 2));

		// null桁のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber(null, 2));

		// null値のテスト
		assertEquals("0.00", NumberFormatUtil.formatNumber(null, 2));

		// 正のBigDecimalのテスト
		assertEquals("12,346", NumberFormatUtil.formatNumber(new BigDecimal("12345.6789"), 0));
		assertEquals("12,345.68", NumberFormatUtil.formatNumber(new BigDecimal("12345.6789"), 2));

		// 負のBigDecimalのテスト
		assertEquals("-12,346", NumberFormatUtil.formatNumber(new BigDecimal("-12345.6789"), 0));

		// 小数点以下が少ない場合のテスト
		assertEquals("0.12", NumberFormatUtil.formatNumber(new BigDecimal("0.123456"), 2));

		// 小数点以下が多い場合のテスト
		assertEquals("0.123456", NumberFormatUtil.formatNumber(new BigDecimal("0.123456"), 6));
	}

	@Test
	void testFormatNumberNonComma() {
		// 通常の数値文字列と正の桁数でテスト
		assertEquals("12345.68", NumberFormatUtil.formatNumberNonComma("12345.6789", 2));
		assertEquals("12345", NumberFormatUtil.formatNumberNonComma("12,345", 0));

		// コンマを含む数値文字列でテスト
		assertEquals("12345.68", NumberFormatUtil.formatNumberNonComma("12,345.6789", 2));
		assertEquals("12345", NumberFormatUtil.formatNumberNonComma("12,345", 0));

		// コンマと小数点がない数値文字列でテスト
		assertEquals("12345", NumberFormatUtil.formatNumberNonComma("12,345.000", 0));
		assertEquals("12345.68", NumberFormatUtil.formatNumberNonComma("12,345.6789", 2));

		// 空の文字列入力でテスト
		assertEquals("0.00", NumberFormatUtil.formatNumberNonComma("", 2));

		// null 入力でテスト
		assertEquals("0.00", NumberFormatUtil.formatNumberNonComma(null, 2));

		// 非数値文字列入力でテスト
		assertEquals("0.00", NumberFormatUtil.formatNumberNonComma("abc", 2));

		// ゼロ値入力と様々な桁数でテスト
		assertEquals("0", NumberFormatUtil.formatNumberNonComma("0", 0));
		assertEquals("0.0", NumberFormatUtil.formatNumberNonComma("0", 1));
		assertEquals("0.00", NumberFormatUtil.formatNumberNonComma("0", 2));

		// 負の数値文字列でテスト
		assertEquals("-12345.68", NumberFormatUtil.formatNumberNonComma("-12345.6789", 2));
		assertEquals("-12345", NumberFormatUtil.formatNumberNonComma("-12345", 0));

		// 数値文字列の前後に空白がある場合でテスト
		assertEquals("12345.68", NumberFormatUtil.formatNumberNonComma(" 12345.6789 ", 2));
		assertEquals("12345", NumberFormatUtil.formatNumberNonComma(" 12345 ", 0));
	}

	// TODO:
	// makeNumberFormat と makeNumberFormat の違いの概要
	// プレースホルダー：
	// makeNumberFormat は "0" を使用し、小数部が指定された桁数で常に正確に表示される。
	// makeNumberSharpFormat は "#" を使用し、小数部が有意な桁数のみ表示し、不必要な末尾のゼロを省略する。
	// 出力例：
	// 数値が 1234.5 の場合
	// makeNumberFormat(2) は "1,234.50" とフォーマットします。
	// makeNumberSharpFormat(2) は "1,234.5" とフォーマットします。

	@Test
	void testFormatNumberAbsentZero() {

		// BigDecimal 入力でテスト
		assertEquals("12,345.679", NumberFormatUtil.formatNumberAbsentZero(new BigDecimal("12345.6789"), 3));
		assertEquals("12,345", NumberFormatUtil.formatNumberAbsentZero(new BigDecimal("12345"), 0));

		// Double 入力でテスト
		assertEquals("12,345.6", NumberFormatUtil.formatNumberAbsentZero(12345.60000, 2));
		assertEquals("12,345", NumberFormatUtil.formatNumberAbsentZero(12345.0, 1));

		// Integer 入力でテスト
		assertEquals("12,345", NumberFormatUtil.formatNumberAbsentZero(12345, 1));
		assertEquals("12,345.6", NumberFormatUtil.formatNumberAbsentZero(12345.6000, 2));

		// String 入力でテスト
		assertEquals("12,345.68", NumberFormatUtil.formatNumberAbsentZero("12345.6789", 2));
		assertEquals("12,345", NumberFormatUtil.formatNumberAbsentZero("12345", 0));
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero("0", 2));

		// null 入力でテスト
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero(null, 0));
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero(null, 2));

		// 空の文字列入力でテスト
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero("", 0));
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero("", 2));

		// ゼロ値入力でテスト
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero(0, 0));
		assertEquals("0", NumberFormatUtil.formatNumberAbsentZero(0, 2));

		// 負の値でテスト
		assertEquals("-12,345.68", NumberFormatUtil.formatNumberAbsentZero(-12345.6789, 2));
		assertEquals("-12,345", NumberFormatUtil.formatNumberAbsentZero(-12345, 0));
		assertEquals("-12,345.68", NumberFormatUtil.formatNumberAbsentZero("-12345.6789", 2));
	}

	@Test
	void testZeroFill() {
		// 異なる桁数でゼロフィルをテスト

		// 整数値と様々な幅でテスト
		assertEquals("00001", NumberFormatUtil.zeroFill(1, 5), "整数 1 幅 5");
		assertEquals("00123", NumberFormatUtil.zeroFill(123, 5), "整数 123 幅 5");
		assertEquals("123", NumberFormatUtil.zeroFill(123, 3), "整数 123 幅 3");
		assertEquals("000123", NumberFormatUtil.zeroFill(123, 6), "整数 123 幅 6");

		// ゼロ値と様々な幅でテスト
		assertEquals("00000", NumberFormatUtil.zeroFill(0, 5), "ゼロ 幅 5");
		assertEquals("000000", NumberFormatUtil.zeroFill(0, 6), "ゼロ 幅 6");

		// ゼロフィル幅が数値の長さより小さい場合でテスト
		assertEquals("123", NumberFormatUtil.zeroFill(123, 3), "数値 123 幅 3（パディング不要）");

		// ゼロフィル幅が 0 の場合でテスト
		assertEquals("0", NumberFormatUtil.zeroFill(0, 0), "数値 0 幅 0");

		// 大きな数値でテスト
		assertEquals("000000123456789", NumberFormatUtil.zeroFill(123456789, 15),
				"大きな数値 123456789 幅 15");
	}

	@Test
	void testZeroToEmptyFormat() {
		// ゼロ値でテスト
		BigDecimal zero = BigDecimal.ZERO;
		int digit = 2;
		String result = NumberFormatUtil.zeroToEmptyFormat(zero, digit);
		assertEquals("", result, "ゼロ値の場合、空の文字列を返すべきです。");

		// NULL 値でテスト
		BigDecimal nullValue = null;
		result = NumberFormatUtil.zeroToEmptyFormat(nullValue, digit);
		assertEquals("", result, "NULL 値の場合、空の文字列を返すべきです。");

		// 非ゼロ値でテスト
		BigDecimal nonZero = new BigDecimal("1234.5678");
		result = NumberFormatUtil.zeroToEmptyFormat(nonZero, digit);
		assertEquals("1,234.57", result,
				"非ゼロ値の場合、指定された小数点以下の桁数でフォーマットするべきです。");

		// 異なる小数点以下の桁数でテスト
		digit = 0;
		result = NumberFormatUtil.zeroToEmptyFormat(nonZero, digit);
		assertEquals("1,235", result,
				"小数点以下の桁数が異なる場合のフォーマット結果を確認するべきです。");

		// ゼロ値で異なる小数点以下の桁数でテスト
		BigDecimal zeroNonZeroDigit = BigDecimal.ZERO;
		digit = 3;
		result = NumberFormatUtil.zeroToEmptyFormat(zeroNonZeroDigit, digit);
		assertEquals("", result,
				"ゼロ値の場合、小数点以下の桁数が異なっても空の文字列を返すべきです。");

		// 非ゼロ値で異なる小数点以下の桁数でテスト
		BigDecimal anotherNonZero = new BigDecimal("987.654321");
		digit = 4;
		result = NumberFormatUtil.zeroToEmptyFormat(anotherNonZero, digit);
		assertEquals("987.6543", result,
				"非ゼロ値の場合、異なる小数点以下の桁数でフォーマットするべきです。");
	}

	@Test
	void testZeroToEmptyCurrencyFormat() {
		// null 値でのテスト
		String result = NumberFormatUtil.zeroToEmptyCurrencyFormat("$", null, 2);
		assertEquals("", result, "null 値の場合、空文字列を返すべきです。");

		// ゼロ値でのテスト
		BigDecimal zero = BigDecimal.ZERO;
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("$", zero, 2);
		assertEquals("", result, "ゼロ値の場合、空文字列を返すべきです。");

		// 正の値でのテスト
		BigDecimal positiveValue = new BigDecimal("1234.5678");
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("$", positiveValue, 2);
		assertEquals("$1,234.57", result, "正の値の場合、通貨記号と2桁の小数点でフォーマットするべきです。");

		// 負の値でのテスト
		BigDecimal negativeValue = new BigDecimal("-987.6543");
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("$", negativeValue, 2);
		assertEquals("-$987.65", result, "負の値の場合、通貨記号、負の符号、2桁の小数点でフォーマットするべきです。");

		// 小数点ゼロでのテスト
		BigDecimal valueWithZeroDecimalPlaces = new BigDecimal("456.789");
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("€", valueWithZeroDecimalPlaces, 0);
		assertEquals("€457", result, "小数点ゼロの場合、通貨記号のみでフォーマットするべきです。");

		// 異なる通貨記号でのテスト
		BigDecimal anotherValue = new BigDecimal("1234.5678");
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("£", anotherValue, 3);
		assertEquals("£1,234.568", result, "異なる通貨記号の場合、指定された記号と3桁の小数点でフォーマットするべきです。");

		// ゼロ値と異なる小数点でのテスト
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("¥", zero, 3);
		assertEquals("", result, "ゼロ値で異なる小数点の場合、空文字列を返すべきです。");

		// 負の値と異なる小数点でのテスト
		BigDecimal negativeValueWithDifferentDecimals = new BigDecimal("-1234.56789");
		result = NumberFormatUtil.zeroToEmptyCurrencyFormat("₹", negativeValueWithDifferentDecimals, 4);
		assertEquals("-₹1,234.5679", result, "負の値で異なる小数点の場合、通貨記号、負の符号、指定された小数点でフォーマットするべきです。");
	}

	@Test
	void testNullToZeroCurrencyFormat() {
		// null 値でのテスト
		String result = NumberFormatUtil.nullToZeroCurrencyFormat("$", null, 2);
		assertEquals("$0.00", result, "null 値の場合、$0.00 としてフォーマットするべきです。");

		// ゼロ値でのテスト
		BigDecimal zero = BigDecimal.ZERO;
		result = NumberFormatUtil.nullToZeroCurrencyFormat("$", zero, 2);
		assertEquals("$0.00", result, "ゼロ値の場合、$0.00 としてフォーマットするべきです。");

		// 正の値でのテスト
		BigDecimal positiveValue = new BigDecimal("1234.5678");
		result = NumberFormatUtil.nullToZeroCurrencyFormat("$", positiveValue, 2);
		assertEquals("$1,234.57", result, "正の値の場合、通貨記号と2桁の小数点でフォーマットするべきです。");

		// 負の値でのテスト
		BigDecimal negativeValue = new BigDecimal("-987.6543");
		result = NumberFormatUtil.nullToZeroCurrencyFormat("$", negativeValue, 2);
		assertEquals("-$987.65", result, "負の値の場合、通貨記号、負の符号、2桁の小数点でフォーマットするべきです。");

		// 小数点ゼロでのテスト
		BigDecimal valueWithZeroDecimalPlaces = new BigDecimal("456.789");
		result = NumberFormatUtil.nullToZeroCurrencyFormat("€", valueWithZeroDecimalPlaces, 0);
		assertEquals("€457", result, "小数点ゼロの場合、通貨記号のみでフォーマットするべきです。");

		// 異なる通貨記号でのテスト
		BigDecimal anotherValue = new BigDecimal("1234.5678");
		result = NumberFormatUtil.nullToZeroCurrencyFormat("£", anotherValue, 3);
		assertEquals("£1,234.568", result, "異なる通貨記号の場合、指定された記号と3桁の小数点でフォーマットするべきです。");

		// ゼロ値と異なる小数点でのテスト
		result = NumberFormatUtil.nullToZeroCurrencyFormat("¥", zero, 3);
		assertEquals("¥0.000", result, "ゼロ値で異なる小数点の場合、通貨記号と指定された小数点でフォーマットするべきです。");

		// 負の値と異なる小数点でのテスト
		BigDecimal negativeValueWithDifferentDecimals = new BigDecimal("-1234.56789");
		result = NumberFormatUtil.nullToZeroCurrencyFormat("₹", negativeValueWithDifferentDecimals, 4);
		assertEquals("-₹1,234.5679", result, "負の値で異なる小数点の場合、通貨記号、負の符号、指定された小数点でフォーマットするべきです。");
	}

}

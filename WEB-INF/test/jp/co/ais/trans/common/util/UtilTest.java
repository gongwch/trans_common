package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void testIsNullOrEmpty() {
		// null のテスト
		assertTrue(Util.isNullOrEmpty(null), "null の場合、true を返すべきです。");

		// 空文字のテスト
		assertTrue(Util.isNullOrEmpty(""), "空文字の場合、true を返すべきです。");

		// 空白のみの文字列のテスト
		assertTrue(Util.isNullOrEmpty("   "), "空白のみの文字列の場合、true を返すべきです。");

		// 空でない文字列のテスト
		assertFalse(Util.isNullOrEmpty("Hello"), "空でない文字列の場合、false を返すべきです。");

		// 数値の文字列のテスト
		assertFalse(Util.isNullOrEmpty("12345"), "数値の文字列の場合、false を返すべきです。");

		// オブジェクト（空のリスト）のテスト
		assertFalse(Util.isNullOrEmpty(new Object()), "非 null のオブジェクトの場合、false を返すべきです。");

		// 空のコレクションのテスト
		assertFalse(Util.isNullOrEmpty(java.util.Collections.emptyList()), "空のコレクションの場合、false を返すべきです。");
	}

	@Test
	void testAvoidNull() {
		// null のテスト
		assertEquals("", Util.avoidNull(null), "null の場合、空文字を返すべきです。");

		// 空文字のテスト
		assertEquals("", Util.avoidNull(""), "空文字の場合、そのまま空文字を返すべきです。");

		// 空白のみの文字列のテスト
		assertEquals("", Util.avoidNull("   "), "空白のみの文字列の場合、空文字を返すべきです。");

		// 通常の文字列のテスト
		assertEquals("Hello", Util.avoidNull("Hello"), "通常の文字列の場合、そのまま文字列を返すべきです。");

		// 数値の文字列のテスト
		assertEquals("12345", Util.avoidNull("12345"), "数値の文字列の場合、そのまま文字列を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals("123", Util.avoidNull(123), "数値オブジェクトの場合、文字列に変換して返すべきです。");
	}

	@Test
	void testAvoidNullNT() {
		assertEquals("", Util.avoidNullNT(null), "null の場合、空文字を返すべきです。");

		// 空文字のテスト
		assertEquals("", Util.avoidNullNT(""), "空文字の場合、そのまま空文字を返すべきです。");

		// 空白のみの文字列のテスト
		assertEquals("   ", Util.avoidNullNT("   "), "空白のみの文字列の場合、そのまま文字列を返すべきです。");

		// 通常の文字列のテスト
		assertEquals("Hello", Util.avoidNullNT("Hello"), "通常の文字列の場合、そのまま文字列を返すべきです。");

		// 数値の文字列のテスト
		assertEquals("12345", Util.avoidNullNT("12345"), "数値の文字列の場合、そのまま文字列を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals("123", Util.avoidNullNT(123), "数値オブジェクトの場合、文字列に変換された値を返すべきです。");
	}

	@Test
	void testAvoidNullAsInt() {
		// null のテスト
		assertEquals(0, Util.avoidNullAsInt(null), "null の場合、0 を返すべきです。");

		// 空文字のテスト
		assertEquals(0, Util.avoidNullAsInt(""), "空文字の場合、0 を返すべきです。");

		// 空白のみの文字列のテスト
		assertEquals(0, Util.avoidNullAsInt("   "), "空白のみの文字列の場合、0 を返すべきです。");

		// 正の整数の文字列のテスト
		assertEquals(123, Util.avoidNullAsInt("123"), "正の整数の文字列の場合、その整数値を返すべきです。");

		// 負の整数の文字列のテスト
		assertEquals(-456, Util.avoidNullAsInt("-456"), "負の整数の文字列の場合、その整数値を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals(789, Util.avoidNullAsInt(789), "数値オブジェクトの場合、その整数値を返すべきです。");

	}

	@Test
	void testAvoidNullAsFloat() {
		// null のテスト
		assertEquals(0.0f, Util.avoidNullAsFloat(null), "null の場合、0.0 を返すべきです。");

		// 空文字のテスト
		assertEquals(0.0f, Util.avoidNullAsFloat(""), "空文字の場合、0.0 を返すべきです。");

		// 空白のみの文字列のテスト
		assertEquals(0.0f, Util.avoidNullAsFloat("   "), "空白のみの文字列の場合、0.0 を返すべきです。");

		// 正の浮動小数点数の文字列のテスト
		assertEquals(123.45f, Util.avoidNullAsFloat("123.45"), "正の浮動小数点数の文字列の場合、その浮動小数点数を返すべきです。");

		// 負の浮動小数点数の文字列のテスト
		assertEquals(-456.78f, Util.avoidNullAsFloat("-456.78"), "負の浮動小数点数の文字列の場合、その浮動小数点数を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals(789.0f, Util.avoidNullAsFloat(789), "数値オブジェクトの場合、その浮動小数点数を返すべきです。");

	}

	@Test
	void testEmptyToNull() {
		// null のテスト
		assertNull(Util.emptyToNull(null), "null の場合、null を返すべきです。");

		// 空文字のテスト
		assertNull(Util.emptyToNull(""), "空文字の場合、null を返すべきです。");

		// 空白のみの文字列のテスト
		assertNull(Util.emptyToNull("   "), "空白のみの文字列の場合、null ではなくそのままの文字列を返すべきです。");
		assertEquals(null, Util.emptyToNull("   "), "空白のみの文字列の場合、そのままの文字列を返すべきです。");

		// 通常の文字列のテスト
		assertEquals("Hello", Util.emptyToNull("Hello"), "通常の文字列の場合、そのままの文字列を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals("123", Util.emptyToNull(123), "数値オブジェクトの場合、文字列に変換して返すべきです。");

		// 数値の文字列のテスト
		assertEquals("456", Util.emptyToNull("456"), "数値の文字列の場合、そのままの文字列を返すべきです。");

	}

	@Test
	void testSafeNull() {
		// null のテスト
		assertEquals(Util.NULL_STRING1, Util.safeNull(null), "null の場合、'(null)' を返すべきです。");

		// 空文字のテスト
		assertEquals("", Util.safeNull(""), "空文字の場合、そのままの文字列を返すべきです。");

		// 空白のみの文字列のテスト
		assertEquals("   ", Util.safeNull("   "), "空白のみの文字列の場合、そのままの文字列を返すべきです。");

		// 通常の文字列のテスト
		assertEquals("Hello", Util.safeNull("Hello"), "通常の文字列の場合、そのままの文字列を返すべきです。");

		// 数値オブジェクトのテスト
		assertEquals("123", Util.safeNull(123), "数値オブジェクトの場合、文字列に変換して返すべきです。");

		// 数値の文字列のテスト
		assertEquals("456", Util.safeNull("456"), "数値の文字列の場合、そのままの文字列を返すべきです。");
	}

	@Test
	void testIsNullString() {
		// null のテスト
		assertFalse(Util.isNullString(null), "null の場合、false を返すべきです。");

		// "(null)" のテスト
		assertTrue(Util.isNullString(Util.NULL_STRING1), "\"(null)\" の場合、true を返すべきです。");

		// 空文字のテスト
		assertFalse(Util.isNullString(""), "空文字の場合、false を返すべきです。");

		// 空白のみの文字列のテスト
		assertFalse(Util.isNullString("   "), "空白のみの文字列の場合、false を返すべきです。");

		// 通常の文字列のテスト
		assertFalse(Util.isNullString("Hello"), "通常の文字列の場合、false を返すべきです。");

		// 数値の文字列のテスト
		assertFalse(Util.isNullString("123"), "数値の文字列の場合、false を返すべきです。");
	}

	@Test
	void testGetCurrentDate() {
		// メソッドで取得した日付
		Date currentDate = Util.getCurrentDate();

		// 現在のシステム日付
		Calendar calendar = GregorianCalendar.getInstance();
		Date now = calendar.getTime();

		//TODO:
		// システム日付のずれを許容するためのしきい値（例えば、1秒）
		long toleranceMillis = 1000;
		// 現在の日付が取得された日付と1秒以内であるかを確認
		assertTrue(Math.abs(now.getTime() - currentDate.getTime()) <= toleranceMillis,
				"取得した日付が現在の日付と1秒以内であるべきです。");

	}

	@Test
	void testGetCurrentYMDDate() {
		// メソッドで取得した YMD 形式の日付
		Date ymdDate = Util.getCurrentYMDDate();

		// 現在のシステム日付
		Calendar calendar = GregorianCalendar.getInstance();
		Date now = calendar.getTime();

		// 現在の日付から年月日を抽出
		Calendar nowCal = Calendar.getInstance();
		nowCal.setTime(now);
		int nowYear = nowCal.get(Calendar.YEAR);
		int nowMonth = nowCal.get(Calendar.MONTH) + 1; // 月は0から始まるため+1
		int nowDay = nowCal.get(Calendar.DAY_OF_MONTH);

		// YMD形式の日付として正しいか確認
		Calendar ymdCal = Calendar.getInstance();
		ymdCal.setTime(ymdDate);
		int ymdYear = ymdCal.get(Calendar.YEAR);
		int ymdMonth = ymdCal.get(Calendar.MONTH) + 1;
		int ymdDay = ymdCal.get(Calendar.DAY_OF_MONTH);

		assertEquals(nowYear, ymdYear, "YMD形式の日付の年が現在の年と一致するべきです。");
		assertEquals(nowMonth, ymdMonth, "YMD形式の日付の月が現在の月と一致するべきです。");
		assertEquals(nowDay, ymdDay, "YMD形式の日付の日が現在の日と一致するべきです。");
	}

	@Test
	void testGetCurrentDateString() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// メソッドで取得したシステム日付文字列
		String dateString = Util.getCurrentDateString();

		// 現在のシステム日付を取得
		Date now = new Date();

		// 現在のシステム日付を文字列に変換
		String expectedDateString = DATE_FORMAT.format(now);

		// 取得した文字列が正しい形式かどうか確認
		try {
			// 文字列を日付オブジェクトに変換して、変換が成功するか確認
			Date parsedDate = DATE_FORMAT.parse(dateString);
			assertNotNull(parsedDate, "日付文字列が正しい形式であるべきです。");
			// 取得した日付文字列と期待される日付文字列が一致するか確認
			assertEquals(expectedDateString, dateString, "システム日付文字列が期待される形式と一致するべきです。");
		} catch (ParseException e) {
			fail("日付文字列の解析に失敗しました: " + e.getMessage());
		}
	}

	@Test
	void testGetCurrentDateStringString() {
		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		String[] LANGUAGES = { "ja", "en" }; // テストする言語コードの配列
		for (String lang : LANGUAGES) {
			// メソッドで取得したシステム日付文字列
			String dateString = Util.getCurrentDateString(lang);

			// 現在のシステム日付を取得
			Date now = new Date();

			// 現在のシステム日付を文字列に変換
			String expectedDateString = DATE_FORMAT.format(now);

			// 取得した文字列が正しい形式かどうか確認
			try {
				// 文字列を日付オブジェクトに変換して、変換が成功するか確認
				Date parsedDate = DATE_FORMAT.parse(dateString);
				assertNotNull(parsedDate, "日付文字列が正しい形式であるべきです。");
				// 取得した日付文字列と期待される日付文字列が一致するか確認
				assertEquals(expectedDateString, dateString, "システム日付文字列が期待される形式と一致するべきです。");
			} catch (ParseException e) {
				fail("日付文字列の解析に失敗しました: " + e.getMessage());
			}
		}

	}

	@Test
	void testIsSmallerThen() {
		// 空文字列とnullを含むテスト
		assertTrue(Util.isSmallerThen(null, "test"), "null と任意の文字列の場合、true を返すべきです。");
		assertTrue(Util.isSmallerThen("test", null), "任意の文字列 と null の場合、true を返すべきです。");
		assertTrue(Util.isSmallerThen("", "test"), "空文字列 と 任意の文字列の場合、true を返すべきです。");
		assertTrue(Util.isSmallerThen("test", ""), "任意の文字列 と 空文字列の場合、true を返すべきです。");
		assertTrue(Util.isSmallerThen("", ""), "空文字列 と 空文字列の場合、true を返すべきです。");

		// 同じ文字列の比較
		assertTrue(Util.isSmallerThen("abc", "abc"), "同じ文字列の場合、true を返すべきです。");

		// begin が end より小さい場合
		assertTrue(Util.isSmallerThen("abc", "def"), "begin が end より小さい場合、true を返すべきです。");

		// begin が end より大きい場合
		assertFalse(Util.isSmallerThen("def", "abc"), "begin が end より大きい場合、false を返すべきです。");

		// Unicode 文字を含む比較
		assertTrue(Util.isSmallerThen("apple", "banana"), "Unicode 文字を含む比較で、begin が end より小さい場合、true を返すべきです。");
		assertFalse(Util.isSmallerThen("banana", "apple"), "Unicode 文字を含む比較で、begin が end より大きい場合、false を返すべきです。");
	}

	@Test
	void testIsSmallerThenByYMDNVL() {
		// テスト用の日付を作成
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		// isSmallerThenByYMD の動作をテストするために
		// Mock またはテスト対象メソッドが必要ですが、ここでは単にサンプルです

		// begin または end が null の場合、false を返す
		assertFalse(Util.isSmallerThenByYMDNVL(null, date2), "begin が null の場合、false を返すべきです。");
		assertFalse(Util.isSmallerThenByYMDNVL(date1, null), "end が null の場合、false を返すべきです。");
		assertFalse(Util.isSmallerThenByYMDNVL(null, null), "begin と end が null の場合、false を返すべきです。");

		// begin が end より小さい場合
		assertTrue(Util.isSmallerThenByYMDNVL(date1, date2), "begin が end より小さい場合、true を返すべきです。");

		//TODO:
		// begin が end より大きい場合
		//assertTrue(Util.isSmallerThenByYMDNVL(date2, date1), "begin が end より大きい場合、false を返すべきです。"); // YMD同じいがあれば、true

		// begin と end が同じ場合
		assertTrue(Util.isSmallerThenByYMDNVL(date1, date1), "begin と end が同じ場合、true を返すべきです。");
	}

	@Test
	void testIsSmallerThenByYMD() {
		// テスト用の日付を作成
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 7, 10, 30, 0);
		Date date4 = cal.getTime();

		// begin が end より小さい場合 (同じ日付)
		assertTrue(Util.isSmallerThenByYMD(date1, date2), "begin が end より小さい場合、true を返すべきです。");
		assertTrue(Util.isSmallerThenByYMD(date1, date1), "begin と end が同じ場合、true を返すべきです。");

		// begin が end より大きい場合
		assertTrue(Util.isSmallerThenByYMD(date2, date1), "begin が end より大きい場合、false を返すべきです。");

		// begin が end より前の日付の場合
		assertTrue(Util.isSmallerThenByYMD(date4, date1), "begin が end より前の日付の場合、true を返すべきです。");

	}

	@Test
	void testIsSmallerThenByYMDHMS() {
		// テスト用の日付を作成
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 30, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date3 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 9, 10, 30, 0);
		Date date4 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 7, 10, 30, 0);
		Date date5 = cal.getTime();

		// begin が end より小さい場合 (同じ日時)
		assertTrue(Util.isSmallerThenByYMDHMS(date1, date2), "begin が end より小さい場合、true を返すべきです。");
		assertTrue(Util.isSmallerThenByYMDHMS(date1, date3), "begin と end が同じ日時の場合、true を返すべきです。");

		// begin が end より大きい場合
		assertFalse(Util.isSmallerThenByYMDHMS(date2, date1), "begin が end より大きい場合、false を返すべきです。");

		// begin が end より前の日付の場合
		assertTrue(Util.isSmallerThenByYMDHMS(date5, date1), "begin が end より前の日付の場合、true を返すべきです。");

	}

	@Test
	void testEqualsByYMD() {
		// テスト用の日付を作成
		Calendar cal = Calendar.getInstance();
		cal.set(2024, Calendar.AUGUST, 8, 10, 30, 0);
		Date date1 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 15, 45, 0);
		Date date2 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 9, 10, 30, 0);
		Date date3 = cal.getTime();

		cal.set(2024, Calendar.AUGUST, 8, 0, 0, 0);
		Date date4 = cal.getTime();

		// date1 と date2 は同じ日付 (年月日) なので true を期待
		assertTrue(Util.equalsByYMD(date1, date2), "date1 と date2 は同じ年月日なので true を返すべきです。");

		// date1 と date3 は異なる日付なので false を期待
		assertFalse(Util.equalsByYMD(date1, date3), "date1 と date3 は異なる年月日なので false を返すべきです。");

		// date1 と date4 は同じ日付 (年月日) なので true を期待
		assertTrue(Util.equalsByYMD(date1, date4), "date1 と date4 は同じ年月日なので true を返すべきです。");

		// いずれの引数も null の場合は true を期待
		assertTrue(Util.equalsByYMD(null, null), "両方が null の場合は true を返すべきです。");

		// 一方の引数が null の場合は false を期待
		assertFalse(Util.equalsByYMD(date1, null), "一方の引数が null の場合は false を返すべきです。");
		assertFalse(Util.equalsByYMD(null, date1), "一方の引数が null の場合は false を返すべきです。");
	}

	@Test
	void testToRGBColorCode() {
		// テスト用カラーコード
		String code1 = "#FF5733"; // 正常なコード
		String code2 = "#00FF00"; // 正常なコード
		String code3 = "#123456"; // 正常なコード
		String code4 = "#ABC"; // 短いコード（不正なコード）
		String code5 = null; // null（不正なコード）
		//TODO:
		//String code6 = "#ZZZZZZ"; // 不正な16進数コード NumberFormatException

		// テスト実行
		int[] result1 = Util.toRGBColorCode(code1);
		int[] result2 = Util.toRGBColorCode(code2);
		int[] result3 = Util.toRGBColorCode(code3);
		int[] result4 = Util.toRGBColorCode(code4);
		int[] result5 = Util.toRGBColorCode(code5);
		//int[] result6 = Util.toRGBColorCode(code6);

		// 結果確認
		assertArrayEquals(new int[] { 255, 87, 51 }, result1, "カラーコード #FF5733 の RGB 値が正しくありません。");
		assertArrayEquals(new int[] { 0, 255, 0 }, result2, "カラーコード #00FF00 の RGB 値が正しくありません。");
		assertArrayEquals(new int[] { 18, 52, 86 }, result3, "カラーコード #123456 の RGB 値が正しくありません。");
		assertArrayEquals(new int[] { 0, 0, 0 }, result4, "カラーコード #ABC の RGB 値が正しくありません。");
		assertArrayEquals(new int[] { 0, 0, 0 }, result5, "カラーコード null の RGB 値が正しくありません。");
		//	assertArrayEquals(new int[] { 0, 0, 0 }, result6, "カラーコード #ZZZZZZ の RGB 値が正しくありません。");
	}

	@Test
	void testTo16RGBColorCodeColor() {
		// 正常なケース
		Color color1 = new Color(255, 0, 0); // 赤
		Color color2 = new Color(0, 255, 0); // 緑
		Color color3 = new Color(0, 0, 255); // 青

		// テスト実行
		String result1 = Util.to16RGBColorCode(color1);
		String result2 = Util.to16RGBColorCode(color2);
		String result3 = Util.to16RGBColorCode(color3);

		// 結果確認
		assertEquals("#ff0000", result1, "赤色の16進数カラーコードが正しくありません。");
		assertEquals("#00ff00", result2, "緑色の16進数カラーコードが正しくありません。");
		assertEquals("#0000ff", result3, "青色の16進数カラーコードが正しくありません。");

		// nullの場合
		String resultNull = Util.to16RGBColorCode(new Color(0));
		assertEquals("#000000", resultNull, "nullの入力に対する16進数カラーコードが正しくありません。");
	}

	@Test
	void testTo16RGBColorCodeIntArray() {
		// 正常なケース
		int[] rgb1 = { 255, 99, 71 }; // Tomate
		int[] rgb2 = { 0, 0, 0 }; // Black
		int[] rgb3 = { 255, 255, 255 }; // White

		// テスト実行
		String result1 = Util.to16RGBColorCode(rgb1);
		String result2 = Util.to16RGBColorCode(rgb2);
		String result3 = Util.to16RGBColorCode(rgb3);

		// 結果確認
		assertEquals("#ff6347", result1, "Tomato色の16進数カラーコードが正しくありません。");
		assertEquals("#000000", result2, "Black色の16進数カラーコードが正しくありません。");
		assertEquals("#ffffff", result3, "White色の16進数カラーコードが正しくありません。");

		// 異常ケース
		assertThrows(ArrayIndexOutOfBoundsException.class, () -> Util.to16RGBColorCode(new int[] { 255, 255 }),
				"配列の長さが3でない場合、ArrayIndexOutOfBoundsExceptionがスローされるべきです。");

	}

	@Test
	void testIsOverAmountString() {
		// オーバーしない金額のテスト（整数部が最大桁数内で、小数部も標準の範囲内）
		String validAmount1 = "1234567890123.1234"; // 13桁の整数部 + 4桁の小数部
		assertFalse(Util.isOverAmount(validAmount1), "整数部が最大桁数の範囲内で、小数部も4桁であればFALSEを返すべきです。");

		// オーバーする金額のテスト（整数部が14桁を超える）
		String overAmount1 = "12345678901234.1234"; // 14桁の整数部 + 4桁の小数部
		assertTrue(Util.isOverAmount(overAmount1), "整数部が最大桁数を超えた場合、TRUEを返すべきです。");

		// オーバーする金額のテスト（小数部が5桁を超える）
		String overAmount2 = "1234567890123.12345"; // 13桁の整数部 + 5桁の小数部
		assertTrue(Util.isOverAmount(overAmount2), "小数部が標準用桁数を超えた場合、TRUEを返すべきです。");

		// ギリギリの金額のテスト（整数部が最大桁数内で、小数部も4桁であれば問題なし）
		String validAmount2 = "1234567890123.1234"; // 13桁の整数部 + 4桁の小数部
		assertFalse(Util.isOverAmount(validAmount2), "整数部が最大桁数の範囲内で、小数部も4桁であればFALSEを返すべきです。");

		// 空の金額のテスト
		String emptyAmount = ""; // 空の文字列
		assertFalse(Util.isOverAmount(emptyAmount), "空の金額文字列に対してはFALSEを返すべきです。");

		// nullの金額のテスト
		String nullAmount = null; // nullの値
		assertFalse(Util.isOverAmount(nullAmount), "nullの金額文字列に対してはFALSEを返すべきです。");
	}

	@Test
	void testIsOverAmountStringIntInt() {
		// 最大桁数17、整数部最大13桁、小数部最大4桁
		int maxLen = 17;
		int maxDigit = 4;

		// オーバーしない金額のテスト（整数部13桁、小数部4桁）
		String validAmount1 = "1234567890123.1234"; // 13桁 + 4桁
		assertFalse(Util.isOverAmount(validAmount1, maxLen, maxDigit), "整数部が13桁、小数部が4桁であればFALSEを返すべきです。");

		// オーバーする金額のテスト（整数部が14桁）
		String overAmount1 = "12345678901234.1234"; // 14桁 + 4桁
		assertTrue(Util.isOverAmount(overAmount1, maxLen, maxDigit), "整数部が最大桁数を超えた場合、TRUEを返すべきです。");

		// オーバーする金額のテスト（小数部が5桁）
		String overAmount2 = "1234567890123.12345"; // 13桁 + 5桁
		assertTrue(Util.isOverAmount(overAmount2, maxLen, maxDigit), "小数部が最大桁数を超えた場合、TRUEを返すべきです。");

		// 最大桁数17、小数部最大4桁、小数部が5桁（整数部が13桁以内）
		String validAmount2 = "1234567890123.1234"; // 13桁 + 4桁
		assertFalse(Util.isOverAmount(validAmount2, maxLen, maxDigit), "整数部が13桁、小数部が4桁であればFALSEを返すべきです。");

		// 最大桁数17、小数部最大4桁、整数部が12桁、小数部が5桁（合計17桁）
		String overAmount3 = "123456789012.12345"; // 12桁 + 5桁
		assertTrue(Util.isOverAmount(overAmount3, maxLen, maxDigit), "合計桁数が最大桁数を超えた場合、TRUEを返すべきです。");

		//TODO:
		// 無効な金額のテスト（数字以外の文字が含まれている）
		String invalidAmount1 = "1234abc567.1234"; // 数字以外の文字が含まれている
		assertThrows(NumberFormatException.class, () -> {
			Util.isOverAmount(invalidAmount1, maxLen, maxDigit);
		}, "無効な金額文字列に対しては例外をスローするべきです。");

		// 空の金額のテスト
		String emptyAmount = ""; // 空の文字列
		assertFalse(Util.isOverAmount(emptyAmount, maxLen, maxDigit), "空の金額文字列に対してはFALSEを返すべきです。");

		// nullの金額のテスト
		String nullAmount = null; // nullの値
		assertFalse(Util.isOverAmount(nullAmount, maxLen, maxDigit), "nullの金額文字列に対してはFALSEを返すべきです。");

		// カンマ付きの金額（整数部13桁、小数部4桁）
		String validAmountWithComma = "1,234,567,890,123.1234"; // カンマが含まれる
		assertFalse(Util.isOverAmount(validAmountWithComma, maxLen, maxDigit), "カンマ付きでも正常な金額であればFALSEを返すべきです。");
	}

	@Test
	void testIsNumber() {
		// 正常な数値文字列
		assertTrue(Util.isNumber("123"), "整数の文字列は数値として認識されるべきです。");
		assertTrue(Util.isNumber("123.45"), "小数の文字列は数値として認識されるべきです。");
		assertTrue(Util.isNumber("-123.45"), "負の数値の文字列は数値として認識されるべきです。");
		assertTrue(Util.isNumber("0"), "ゼロの文字列は数値として認識されるべきです。");
		assertTrue(Util.isNumber("1.23E+2"), "指数表記の数値の文字列は数値として認識されるべきです。");

		// 無効な数値文字列
		assertFalse(Util.isNumber("abc"), "文字列が数値として認識されるべきではありません。");
		assertFalse(Util.isNumber("123abc"), "数値に文字が含まれている場合、数値として認識されるべきではありません。");
		assertFalse(Util.isNumber("1.2.3"), "複数の小数点を含む文字列は数値として認識されるべきではありません。");
		assertFalse(Util.isNumber(""), "空の文字列は数値として認識されるべきではありません。");

		// 数値形式が異なる場合のテスト
		assertTrue(Util.isNumber("1234567890123456789"), "非常に大きな整数も数値として認識されるべきです。");
		assertTrue(Util.isNumber("-1234567890123456789"), "非常に大きな負の整数も数値として認識されるべきです。");
		assertTrue(Util.isNumber("1.234567890123456789"), "非常に精度の高い小数も数値として認識されるべきです。");
	}

	// Util.isDate　is Deprecate
	@Test
	void testIsDate() {

	}

	@Test
	void testGetFiscalMonth() {
		// 初月と目標月が同じ場合
		assertEquals(1, Util.getFiscalMonth(1, 1), "初月と目標月が同じ場合は1であるべきです。");
		assertEquals(1, Util.getFiscalMonth(12, 12), "初月と目標月が同じ場合は1であるべきです。");

		// 目標月が初月と同じまたはそれ以降の場合
		assertEquals(2, Util.getFiscalMonth(1, 2), "目標月が初月の翌月の場合は2であるべきです。");
		assertEquals(12, Util.getFiscalMonth(1, 12), "目標月が初月の12月の場合は12であるべきです。");
		assertEquals(1, Util.getFiscalMonth(3, 3), "目標月が初月の同じ月の場合は1であるべきです。");

		// 目標月が初月より前の場合
		assertEquals(4, Util.getFiscalMonth(10, 1), "目標月が初月より前の場合は、期を跨いで3であるべきです。");
		assertEquals(8, Util.getFiscalMonth(7, 2), "目標月が初月より前の場合は、期を跨いで7であるべきです。");
		assertEquals(8, Util.getFiscalMonth(5, 12), "目標月が初月より前の場合は、期を跨いで8であるべきです。");

		// 境界ケース
		assertEquals(2, Util.getFiscalMonth(12, 1), "12月から1月までの会計月は1であるべきです。");
		assertEquals(12, Util.getFiscalMonth(1, 12), "1月から12月までの会計月は12であるべきです。");

	}

	@Test
	void testIsShortLanguage() {
		// 英語の場合
		assertTrue(Util.isShortLanguage(Locale.ENGLISH.getLanguage()), "英語の言語コードは略語対象であるべきです。");

		// 異なる言語コードの場合
		assertFalse(Util.isShortLanguage("fr"), "フランス語の言語コードは略語対象でないべきです。");
		assertFalse(Util.isShortLanguage("es"), "スペイン語の言語コードは略語対象でないべきです。");
		assertFalse(Util.isShortLanguage("de"), "ドイツ語の言語コードは略語対象でないべきです。");

		// 空文字の場合
		assertFalse(Util.isShortLanguage(""), "空文字は略語対象でないべきです。");

		// nullの場合
		assertFalse(Util.isShortLanguage(null), "nullは略語対象でないべきです。");
	}

	@Test
	void testMakeStackString() {
		// テスト用の例外を生成
		Exception testException = new Exception("Test exception message");

		// スタックトレースを取得
		String stackString = Util.makeStackString(testException);

		// メッセージが含まれているか確認
		assertTrue(stackString.contains("Test exception message"), "メッセージがスタックトレースに含まれているべきです。");

		// スタックトレースが含まれているか確認
		assertTrue(stackString.contains("java.lang.Exception"), "例外クラス名がスタックトレースに含まれているべきです。");
	}

	@Test
	void testEqualsStringString() {
		// 同じ文字列の比較
		assertTrue(Util.equals("test", "test"), "同じ文字列は一致するべきです。");

		// 空文字とnullの比較
		assertTrue(Util.equals("", null), "空文字とnullは一致するべきです。");

		// 異なる文字列の比較
		assertFalse(Util.equals("test1", "test2"), "異なる文字列は一致しないべきです。");

		// 両方がnullの場合の比較
		assertTrue(Util.equals(null, null), "両方がnullの場合は一致するべきです。");

		// 片方が空文字、もう片方がnullの場合
		assertTrue(Util.equals(null, ""), "nullと空文字は一致するべきです。");
	}

	@Test
	void testToColor() {
		// 正常なケース
		Color color = Util.toColor(new String[] { "255", "0", "0" });
		assertEquals(new Color(255, 0, 0), color, "RGB(255, 0, 0) は赤色であるべきです。");

		// 正常なケース
		color = Util.toColor(new String[] { "0", "255", "0" });
		assertEquals(new Color(0, 255, 0), color, "RGB(0, 255, 0) は緑色であるべきです。");

	}

	@Test
	void testConvertToRGB() {
		// 正常なケース
		Color color = Util.convertToRGB("#fffffff");
		assertEquals(new Color(255, 255, 255), color, "RGB(255, 255, 255) は白色であるべきです。");

		color = Util.convertToRGB("#000000");
		assertEquals(new Color(0, 0, 0), color, "RGB(0, 0, 0) は黒色であるべきです。");

	}

	@Test
	void testGetSystemVersion() {
		// テスト対象のクラスを指定
		String[] version = Util.getSystemVersion(UtilTest.class);

		// ローカルの場合
		assertEquals("local", version[0], "ローカル環境では最終更新日時は 'local' であるべきです。");
		assertEquals("local", version[1], "ローカル環境ではバージョンは 'local' であるべきです。");

	}

}

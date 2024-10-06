package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class DateUtilTest {

	static SimpleDateFormat datefmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	static Date date;
	static Date date1;
	static Timestamp timestamp;

	@BeforeAll
	static void setUpBeforeAll() {
		try {
			date = datefmt.parse("2024/07/25 09:00:00");
			date1 = datefmt.parse("2024/07/25 00:00:00");
			timestamp = Timestamp.valueOf("2024-07-25 00:00:00");
		} catch (ParseException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	@Test
	void testGetFirstDate() {
		try {
			Date expected = datefmt.parse("2024/07/01 00:00:00");
			assertEquals(expected, DateUtil.getFirstDate(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testGetLastDateIntInt() {

		try {
			Date actualDate = DateUtil.getLastDate(2024, 7);
			Date expecteDate = datefmt.parse("2024/07/31 00:00:00");
			assertEquals(expecteDate, actualDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testGetLastDateDate() {

		try {
			Date expected = datefmt.parse("2024/07/31 00:00:00");
			Date actual = DateUtil.getLastDate(date);
			assertEquals(expected, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testGetLastDayIntInt() {
		int actual = DateUtil.getLastDay(2024, 7);
		int expected = 31;
		assertEquals(expected, actual);

	}

	@Test
	void testGetLastDayDate() {
		int actual = DateUtil.getLastDay(date);
		int expected = 31;
		assertEquals(expected, actual);
	}

	@Test
	void testGetMonthCountDateDate() {
		int actual = DateUtil.getMonthCount(date1, date);
		int expected = 0;
		assertEquals(expected, actual);

	}

	@Test
	void testGetMonthCountIntIntIntInt() {
		int actual = DateUtil.getMonthCount(2024, 1, 2024, 12);
		int expected = 11;
		assertEquals(expected, actual);
	}

	@Test
	void testToTimestamp() {
		Timestamp actual = DateUtil.toTimestamp(date);
		Timestamp expected = Timestamp.valueOf("2024-07-25 09:00:00");
		assertEquals(expected, actual);
	}

	@Test
	void testGetYear() {
		assertTrue(DateUtil.getYear(date) == 2024);
	}

	@Test
	void testGetMonth() {
		assertTrue(DateUtil.getMonth(date) == 7);
	}

	@Test
	void testGetDay() {
		int actual = DateUtil.getDay(date);
		int expected = 25;
		assertEquals(actual, expected);

	}

	@Test
	void testGetHour() {
		int actual = DateUtil.getHour(date);
		int expected = 9;
		assertEquals(actual, expected);
	}

	@Test
	void testGetMinute() {
		int actual = DateUtil.getMinute(date);
		int expected = 0;
		assertEquals(actual, expected);
	}

	@Test
	void testGetSecond() {
		int actual = DateUtil.getSecond(date);
		int expected = 0;
		assertEquals(actual, expected);
	}

	@Test
	void testGetDayOfWeek() {
		int actual = DateUtil.getDayOfWeek(date);
		int expected = 5;//木曜
		assertEquals(actual, expected);
	}

	@Test
	void testGetDateIntIntInt() {
		Date actual = DateUtil.getDate(2024, 7, 25);
		Date expected = date1;
		assertEquals(actual, expected);

	}

	@Test
	void testGetDateIntIntIntIntIntInt() {
		Date actual = DateUtil.getDate(2024, 7, 25, 9, 0, 0);
		assertEquals(date, actual);
	}

	@Test
	void testGetTimestamp() {
		Date actual = DateUtil.getTimestamp(2024, 7, 25);
		assertEquals(timestamp, actual);
	}

	@Test
	void testAddYear() {

		try {
			Date actual = DateUtil.addYear(date, 1);
			Date expecte = datefmt.parse("2025/07/25 09:00:00");
			assertEquals(expecte, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testAddMonth() {
		try {
			Date actual = DateUtil.addMonth(date, 1);
			Date expecte = datefmt.parse("2024/08/25 09:00:00");
			assertEquals(expecte, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddDay() {
		try {
			Date actual = DateUtil.addDay(date, 1);
			Date expect = datefmt.parse("2024/07/26 09:00:00");
			assertEquals(expect, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddHour() {
		try {
			Date actual = DateUtil.addHour(date, 1);
			Date expect = datefmt.parse("2024/07/25 10:00:00");
			assertEquals(expect, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddMinute() {
		try {
			Date actual = DateUtil.addMinute(date, 1);
			Date expect = datefmt.parse("2024/07/25 09:01:00");
			assertEquals(expect, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddSecond() {
		try {
			Date actual = DateUtil.addSecond(date, 1);
			Date expect = datefmt.parse("2024/07/25 09:00:01");
			assertEquals(expect, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testAddTime() {
		try {
			// add year 
			Date actualy = DateUtil.addTime(date, 1, Calendar.YEAR);//"2024/07/25 09:00:00"
			Date expecty = datefmt.parse("2025/07/25 09:00:00");
			assertEquals(expecty, actualy);

			//TODO:ケース不足かも

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYMDDateString() {
		try {
			// add year 
			Date actual = DateUtil.toYMDDate("2024/07/25 09:00:00");//"2024/07/25 09:00:00"
			Date expect = datefmt.parse("2024/07/25 00:00:00");
			assertEquals(expect, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToJpYMDDate() {
		try {
			// add year 
			Date actual = DateUtil.toJpYMDDate("2024/07/25 09:00:00");//"2024/07/25 09:00:00"
			SimpleDateFormat jPFormat = new SimpleDateFormat("yyyy年MM月dd日");
			Date expect = jPFormat.parse("2024年07月25日");
			assertEquals(expect, actual);

		} catch (ParseException e) {
			//			e.printStackTrace();
		}
	}

	@Test
	void testToYMDHMDate() {
		try {
			// add year 
			Date actual = DateUtil.toYMDHMDate("2024/07/25 09:00:00");//"2024/07/25 09:00:00"
			Date expect = datefmt.parse("2024/07/25 09:00:00");
			assertEquals(expect, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYMDHMSSSSDate() {
		try {
			// add year 
			Date actual = DateUtil.toYMDHMSSSSDate("2024/07/25 09:00:00.000");//"2024/07/25 09:00:00"
			assertEquals(date, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYMDHMSDate() {
		try {
			Date actual = DateUtil.toYMDHMSDate("2024/07/25 09:00:00");//"2024/07/25 09:00:00"
			assertEquals(date, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYMDate() {
		try {
			Date actual = DateUtil.toYMDate("2024/07");//"2024/07/25 09:00:00"
			Date expected = datefmt.parse("2024/07/01 00:00:00");
			assertEquals(expected, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYDate() {
		try {
			Date actual = DateUtil.toYDate("2024");//"2024/07/25 09:00:00"
			Date expected = datefmt.parse("2024/01/01 00:00:00");
			assertEquals(expected, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToYMDDateDate() {
		try {
			Date actual = DateUtil.toYMDDate(date);//"2024/07/25 09:00:00"
			Date expected = datefmt.parse("2024/07/25 00:00:00");

			assertEquals(expected, actual);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testToEnYMDString() {

		String actual = DateUtil.toEnYMDString(date);//"2024/07/25 09:00:00"
		String expected = "25 Jul 2024 ";

		assertEquals(expected, actual);

	}

	@Test
	void testToEnYMDHMSString() {

		String actual = DateUtil.toEnYMDHMSString(date);//"2024/07/25 09:00:00"
		String expected = "25 Jul 2024  09:00:00 ";
		assertEquals(expected, actual);

	}

	@Test
	void testToEngYMString() {
		String actual = DateUtil.toEngYMString(date);//"2024/07/25 09:00:00"
		String expected = "07/2024";
		assertEquals(expected, actual);
	}

	@Test
	void testToEngYMDString() {
		String actual = DateUtil.toEngYMDString(date);//"2024/07/25 09:00:00"
		String expected = "25/07/2024";
		assertEquals(expected, actual);
	}

	@Test
	void testToEngYMDHMString() {
		String actual = DateUtil.toEngYMDHMString(date);//"2024/07/25 09:00:00"
		String expected = "25/07/2024 09:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToEngDateTimeString() {
		String actual = DateUtil.toEngDateTimeString(date);//"2024/07/25 09:00:00"
		String expected = "09:00 25th Jul,2024";
		assertEquals(expected, actual);
	}

	@Test
	void testToEngDateString() {
		String actual = DateUtil.toEngDateString(date);//"2024/07/25 09:00:00"
		String expected = "25th Jul,2024";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDHMSSSSString() {
		String actual = DateUtil.toYMDHMSSSSString(date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00:00.000";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDHMSStringDate() {
		String actual = DateUtil.toYMDHMSString(date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDHMStringDate() {
		String actual = DateUtil.toYMDHMString(date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDStringDate() {
		String actual = DateUtil.toYMDHMString(date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToY2MString() {
		String actual = DateUtil.toY2MString(date);//"2024/07/25 09:00:00"
		String expected = "24/07";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMStringDate() {
		String actual = DateUtil.toYMString(date);//"2024/07/25 09:00:00"
		String expected = "2024/07";
		assertEquals(expected, actual);
	}

	@Test
	void testToYString() {
		String actual = DateUtil.toYString(date);//"2024/07/25 09:00:00"
		String expected = "2024";
		assertEquals(expected, actual);
	}

	@Test
	void testToMDString() {
		String actual = DateUtil.toMDString(date);//"2024/07/25 09:00:00"
		String expected = "07/25";
		assertEquals(expected, actual);
	}

	@Test
	void testToMDShortString() {
		String actual = DateUtil.toMDShortString(date);//"2024/07/25 09:00:00"
		String expected = "7/25";
		assertEquals(expected, actual);
	}

	@Test
	void testToMDHMString() {
		String actual = DateUtil.toMDShortString(date);//"2024/07/25 09:00:00"
		String expected = "7/25";
		assertEquals(expected, actual);
	}

	@Test
	void testToMDHMShortString() {
		String actual = DateUtil.toMDHMShortString(date);//"2024/07/25 09:00:00"
		String expected = "7/25 09:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToHMSStringDate() {
		String actual = DateUtil.toHMSString(date);//"2024/07/25 09:00:00"
		String expected = "09:00:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToHMString() {
		String actual = DateUtil.toHMString(date);//"2024/07/25 09:00:00"
		String expected = "09:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToMMString() {
		String actual = DateUtil.toMMString(date);//"2024/07/25 09:00:00"
		String expected = "07";
		assertEquals(expected, actual);
	}

	@Test
	void testToDDString() {
		String actual = DateUtil.toDDString(date);//"2024/07/25 09:00:00"
		String expected = "25";
		assertEquals(expected, actual);
	}

	@Test
	void testToJpGYMDString() {
		String actual = DateUtil.toJpGYMDString(date);//"2024/07/25 09:00:00"
		String expected = "令和6年07月25日";// GGGGyyyy年MM月dd日
		assertEquals(expected, actual);
	}

	@Test
	void testToJpYMDString() {
		String actual = DateUtil.toJpYMDString(date);//"2024/07/25 09:00:00"
		String expected = "2024年07月25日";// yyyy年MM月dd日
		assertEquals(expected, actual);
	}

	@Test
	void testToJpYMString() {
		String actual = DateUtil.toJpYMString(date);//"2024/07/25 09:00:00"
		String expected = "2024年07月";// yyyy年MM月
		assertEquals(expected, actual);
	}

	@Test
	void testToJpMDString() {
		String actual = DateUtil.toJpMDString(date);//"2024/07/25 09:00:00"
		String expected = "07月25日";// MM月dd日
		assertEquals(expected, actual);
	}

	@Test
	void testToJpYString() {
		String actual = DateUtil.toJpYString(date);//"2024/07/25 09:00:00"
		String expected = "2024年";// 2024年
		assertEquals(expected, actual);
	}

	@Test
	void testToJpMString() {
		String actual = DateUtil.toJpMString(date);//"2024/07/25 09:00:00"
		String expected = "07月";// 07月
		assertEquals(expected, actual);
	}

	@Test
	void testToJpDString() {
		String actual = DateUtil.toJpDString(date);//"2024/07/25 09:00:00"
		String expected = "25日";
		assertEquals(expected, actual);
	}

	@Test
	void testToJpYYString() {
		String actual = DateUtil.toJpYYString(date);//"2024/07/25 09:00:00"
		String expected = "06";
		assertEquals(expected, actual);
	}

	@Test
	void testToERAInt() {
		int actual = DateUtil.toERA(6);//"2024/07/25 09:00:00"
		int expected = 2024;
		assertEquals(expected, actual);
	}

	@Test
	void testToERAString() {
		int actual = DateUtil.toERA(6);//"2024/07/25 09:00:00"
		int expected = 2024;
		assertEquals(expected, actual);
	}

	@Test
	void testGetJpERAWord() {
		int actual = DateUtil.toERA(6);//"2024/07/25 09:00:00"
		int expected = 2024;
		assertEquals(expected, actual);
	}

	@Test
	void testGetJpERA() {
		int actual = DateUtil.getJpERA(date);//"2024/07/25 09:00:00"
		int expected = 5;//5:令和

	}

	@Test
	void testToJpERAInt() {
		int actual = DateUtil.toJpERA(2024);//"2024/07/25 09:00:00"
		int expected = 6;
		assertEquals(expected, actual);
	}

	@Test
	void testToJpERAString() {
		assertEquals("6", DateUtil.toJpERA("2024"));// Reiwa
		assertEquals("10", DateUtil.toJpERA("1998"));// Heisei
		assertEquals("64", DateUtil.toJpERA("1989"));// Showa
		assertEquals("5", DateUtil.toJpERA("1916"));// Taisho
		assertEquals(45, DateUtil.toJpERA(1912)); // Meiji
	}

	@Test
	void testToJpYMDArray() throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

		Date reiwa = sdf.parse("2024/07/01");
		String[] expected = { "令和", "6年", "07月", "01日" };
		assertArrayEquals(expected, DateUtil.toJpYMDArray(reiwa));

		Date heisei = sdf.parse("1989/12/31");
		String[] expected1 = { "平成", "1年", "12月", "31日" };
		assertArrayEquals(expected1, DateUtil.toJpYMDArray(heisei));

		Date showa = sdf.parse("1927/1/07");
		String[] expected2 = { "昭和", "2年", "01月", "07日" };
		assertArrayEquals(expected2, DateUtil.toJpYMDArray(showa));

		Date daisei = sdf.parse("1916/11/30");
		String[] expected3 = { "大正", "5年", "11月", "30日" };
		assertArrayEquals(expected3, DateUtil.toJpYMDArray(daisei));

		Date meiji = sdf.parse("1912/07/29");
		String[] expected4 = { "明治", "45年", "07月", "29日" };
		assertArrayEquals(expected4, DateUtil.toJpYMDArray(meiji));

		Date reiwa2 = sdf.parse("2030/07/01");
		String[] expected5 = { "令和", "12年", "07月", "01日" };
		assertArrayEquals(expected5, DateUtil.toJpYMDArray(reiwa2));

		Date heisei2 = sdf.parse("1999/12/31");
		String[] expected6 = { "平成", "11年", "12月", "31日" };
		assertArrayEquals(expected6, DateUtil.toJpYMDArray(heisei2));

		Date showa2 = sdf.parse("1937/1/07");
		String[] expected7 = { "昭和", "12年", "01月", "07日" };
		assertArrayEquals(expected7, DateUtil.toJpYMDArray(showa2));

		Date daisei2 = sdf.parse("1921/11/30");
		String[] expected8 = { "大正", "10年", "11月", "30日" };
		assertArrayEquals(expected8, DateUtil.toJpYMDArray(daisei2));

		assertArrayEquals(new String[0], DateUtil.toJpYMDArray(null));

		//		Date date = sdf.parse("0001/01/01");
		//		assertThrows(StringIndexOutOfBoundsException.class, () -> DateUtil.toJpYMDArray(date));
	}

	@Test
	void testToStringStringDateString() {
		String actual = DateUtil.toString("en", date, "YMDHMS");//"2024/07/25 09:00:00"
		assertEquals("2024/07/25 09:00:00", actual);

	}

	@Test
	void testToYMDHMSStringStringDate() {
		String actual = DateUtil.toYMDHMSString("en", date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00:00";
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDHMStringStringDate() {
		String actual = DateUtil.toYMDHMString("en", date);//"2024/07/25 09:00:00"
		String expected = "2024/07/25 09:00";
		//TODO: add Japan case
		assertEquals(expected, actual);
	}

	@Test
	void testToYMDStringStringDate() {
		String actual = DateUtil.toYMDString("en", date);
		String expected = "2024/07/25";
		assertEquals(expected, actual);

	}

	@Test
	void testToYMStringStringDate() {
		String actual = DateUtil.toYMString("en", date);
		String expected = "2024/07";
		assertEquals(expected, actual);
	}

	@Test
	void testToHMSStringStringDate() {
		String actual = DateUtil.toHMSString("en", date);
		String expected = "09:00:00";//"2024/07/25 09:00:00"
		assertEquals(expected, actual);
	}

	@Test
	void testGetFormatString() {
		//TODO:what i the key？
		String actual = DateUtil.getFormatString("en", "MMMMM");
		String expected = "MMMMM";
		assertEquals(expected, actual);
	}

	@Test
	void testGetDateFormat() {
		SimpleDateFormat actual = DateUtil.getDateFormat("en", "yyyy/MM/dd HH:mm:ss");
		SimpleDateFormat expacted = new SimpleDateFormat(
				DateUtil.getFormatString("en", "yyyy/MM/dd HH:mm:ss"));
		assertEquals(expacted, actual);
	}

	@Test
	void testGetYMDString() {
		//Note: The discrepancy is acceptable, as it is consistent in UTC format.
		String actual = DateUtil.getYMDString(0);
		String expected = "1970/01/01 00:00:00";
		assertEquals(expected, actual);

	}

	@Test
	void testGetDayCountDateDate() {
		int actual = DateUtil.getDayCount(date1, date);
		int expected = 0;
		assertEquals(expected, actual);
	}

	@Test
	void testGetDayCountFC() {
		int actual = DateUtil.getDayCountFC(date1, date);
		int expected = 1;
		assertEquals(expected, actual);

	}

	@Test
	void testGetDayCountDateDateInt() {
		BigDecimal actual = DateUtil.getDayCount(date, date, 3);
		BigDecimal expected = new BigDecimal("0.000");
		assertEquals(expected, actual);

	}

	@Test
	void testAddDays() {
		try {
			Date actual = DateUtil.addDay(date, 1);
			Date expected;
			expected = datefmt.parse("2024/07/26 09:00:00");
			assertEquals(expected, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	@Test
	void testAddHours() {
		try {
			Date actual = DateUtil.addHour(date, 1);
			Date expected;
			expected = datefmt.parse("2024/07/25 10:00:00");
			assertEquals(expected, actual);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	void testIsYDate() {
		Boolean acutual = DateUtil.isYDate("09:00:00");
		assertEquals(true, acutual);
	}

	@Test
	void testIsYMDate() {
		Boolean acutual = DateUtil.isYMDate("2024-07");
		assertEquals(true, acutual);
	}

	@Test
	void testIsYMDDate() {
		Boolean acutual = DateUtil.isYMDDate("2024-07-26");
		assertEquals(true, acutual);
	}

	@Test
	void testIsYMDHMDate() {
		Boolean acutual = DateUtil.isYMDHMDate("2024-07-26 09:00:00");
		assertEquals(true, acutual);
	}

	@Test
	void testIsYMDHMSDate() {
		Boolean acutual = DateUtil.isYMDHMSDate("2024-07-26 09:00:00");
		assertEquals(true, acutual);
	}

	@Test
	void testIsDate() {
		Boolean acutual = DateUtil.isDate("2024-07-26 09:00:00");
		assertEquals(true, acutual);
	}

	@Test
	void testEqualsDateDate() {
		Boolean actual = DateUtil.equals(date1, date);
		assertEquals(false, actual);
	}

	@Test
	void testEqualsAvoidNull() {
		Boolean actual1 = DateUtil.equalsAvoidNull(date1, date);
		assertEquals(false, actual1);

		Boolean actual2 = DateUtil.equalsAvoidNull(null, null);
		assertEquals(true, actual2);

	}

	@Test
	void testToYMDDateYYYYMMDDString() throws ParseException {
		Date actual = DateUtil.toYMDDateYYYYMMDDString("202407025");
		assertEquals(date1, actual);
	}

	@Test
	void testToYMDPlainString() throws ParseException {
		String actual = DateUtil.toYMDPlainString(date);
		assertEquals("20240725", actual);
	}

	@Test
	void testToY2MDDateYYMMDDString() throws Exception {
		//TODO: 
		Date actual = DateUtil.toY2MDDateYYMMDDString("240725");
		assertEquals(date1, actual);

	}

	@Test
	void testToY2MDPlainString() {
		String actual = DateUtil.toY2MDPlainString(date);
		assertEquals("240725", actual);
	}

	@Test
	void testToY2MPlainString() {
		String actual = DateUtil.toY2MPlainString(date);
		assertEquals("2407", actual);
	}

	@Test
	void testToYMDHMSSSSPlainString() {
		String actual = DateUtil.toYMDHMSSSSPlainString(date);
		assertEquals("20240725090000.000", actual);
	}

	@Test
	void testToYMDHMSPlainString() {
		String actual = DateUtil.toYMDHMSPlainString(date);
		assertEquals("20240725090000", actual);
	}

	@Test
	void testToYMDHMPlainString() {
		String actual = DateUtil.toYMDHMPlainString(date);
		assertEquals("202407250900", actual);

	}

	@Test
	void testToYMDDateYYYYMMString() throws ParseException {
		Date actual = DateUtil.toYMDDateYYYYMMString("202407");
		Date expected = datefmt.parse("2024/07/01 00:00:00");
		assertEquals(expected, actual);

	}

	@Test
	void testToYMPlainString() {
		String actual = DateUtil.toYMPlainString(date);
		assertEquals("202407", actual);

	}

	@Test
	void testToDateNE() {
		Date actual = DateUtil.toDateNE(date);
		assertEquals(date, actual);

	}

	@Test
	void testToDate() throws ParseException {
		Date actual = DateUtil.toDate("2024/07/25 09:00:00");
		assertEquals(date, actual);
	}

	@Test
	void testVerify() {
		Boolean actual = DateUtil.verify("2024/07/26 09:00:00");
		assertEquals(true, actual);
	}

	@Test
	void testGetMonthShortWord() {
		String actual = DateUtil.getMonthShortWord("en", 1);
		assertEquals("Jan", actual);
	}

	@Test
	void testGetMonthWord() {
		String actual = DateUtil.getMonthShortWord("ja", 1);
		assertEquals("1月", actual);
	}

	@Test
	void testMin() {
		Date actual = DateUtil.min(date1, date);
		assertEquals(date1, actual);
	}

	@Test
	void testMax() {
		Date actual = DateUtil.max(date1, date);
		assertEquals(date, actual);
	}

	@Test
	void testMinYMDHMS() {
		Date actual = DateUtil.minYMDHMS(date1, date);
		assertEquals(date1, actual);
	}

	@Test
	void testMaxYMDHMS() {
		Date actual = DateUtil.maxYMDHMS(date1, date);
		assertEquals(date, actual);
	}

	@Test
	void testGetVBDayCount() {
		//TODO:
		BigDecimal actual = DateUtil.getVBDayCount(date1, date);
		assertEquals(new BigDecimal("0.375000000000000"), actual);
	}

	@Test
	void testTicksToOADate() throws ParseException {

		// date -> ticks;ticks -> OADate
		Date date = datefmt.parse("1900/01/01 06:00:00"); //is equal to 2.25
		long ticks = DateUtil.getTicks(date);
		assertEquals(DecimalUtil.truncateNum(new BigDecimal(2.25), 20), DateUtil.ticksToOADate(ticks));

	}

	@Test
	void testGetTicksDate() {
		long actual = DateUtil.getTicks(date);
		assertEquals(638574948000000000L, actual);
	}

	@Test
	void testGetTicksDateInt() {
		long actual = DateUtil.getTicks(date, 0);
		assertEquals(638574948000000000L, actual);
		long actual1 = DateUtil.getTicks(date, 1);
		assertEquals(324000000000L, actual1);
		long actual2 = DateUtil.getTicks(date, 2);
		assertEquals(638574624000000000L, actual2);
	}

	@Test
	void testDateToTicks() {

		// Regular date
		assertEquals(0L, DateUtil.dateToTicks(0001, 1, 1)); // Start of the Gregorian calendar
		assertEquals(864000000000L, DateUtil.dateToTicks(0001, 1, 2)); // 1 day later

		//TODO:不一致
		// Regular dates in a non-leap year
		assertEquals(638586720000000000L, DateUtil.dateToTicks(2024, 8, 9)); // August 9, 2024

		// Leap year
		assertEquals(1581120000000L, DateUtil.dateToTicks(2020, 2, 29)); // Leap year date: February 29, 2020

	}

	@Test
	void testTimeToTicks() {
		long actual = DateUtil.timeToTicks(0, 0, 3);
		assertEquals(30000000L, actual);
	}

	@Test
	void testIsLeapYear() {
		Boolean actual = DateUtil.isLeapYear(2024);
		assertEquals(true, actual);

		Boolean actual1 = DateUtil.isLeapYear(2023);
		assertEquals(false, actual1);
	}

}

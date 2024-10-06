package jp.co.ais.trans2.common.excel;

import static org.junit.jupiter.api.Assertions.*;

import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.DateUtil;
import org.junit.jupiter.api.*;

import jp.co.ais.trans.common.util.*;

/**
 * TExcel Test Class
 */
class TExcelTest {

	/**  */
	final Date baseDate = jp.co.ais.trans.common.util.DateUtil.getDate(1900, 1, 0);

	/***/
	final boolean isDebug = false;

	/**
	 */
	@BeforeAll
	static void setUpBeforeClass() {
		// NONE
	}

	//	@Test
	//	void testGetCorrectExcelSheetName() {
	//		fail("まだ実装されていません");
	//	}

	/**
	 * Excel日付取得の検証
	 */
	@Test
	void testGetExcelDate() {
		boolean failed = false;
		boolean isFull = false;
		int strDay = 0;
		//		 strDay = 17655;
		int maxDayTerm = isFull ? 365 * 100 : 800;
		for (int d = strDay; d < maxDayTerm; d++) {
			if (isFull) {
				for (int h = 0; h < 24; h++) {
					// TODO: 17655.04166666前後にエラーあり
					for (int m = 0; m < 60; m++) {
						try {
							if (!testGetExcelDateDay(d, h, m)) {
								fail();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			} else {
				try {
					if (!testGetExcelDateDay(d, 0, 0)) {
						failed = true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		if (failed) {
			fail();
		}
	}

	/**
	 * 時刻部分のExcel値検証
	 */
	@Test
	void testGetExcelDateTime() {
		boolean failed = false;
		for (int h = 0; h < 24; h++) {
			for (int m = 0; m < 60; m++) {
				for (int s = 0; s < 60; s++) {
					try {
						if (!testGetExcelDateHMS(h, m, s)) {
							failed = true;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		if (failed) {
			fail();
		}

	}

	/**
	 * @param d 日
	 * @param h 時
	 * @param m 分
	 * @return 検証NG:false
	 */
	protected boolean testGetExcelDateDay(int d, int h, int m) {
		String hh = h < 10 ? "0" + h : "" + h;
		String mm = m < 10 ? "0" + m : "" + m;

		// HH:MM:SS 検証
		{
			String hmStr = hh + ":" + mm;
			double hmsTime = DateUtil.convertTime(hmStr);
			hmsTime += d;
			BigDecimal hmsDec = new BigDecimal(hmsTime);
			String ymdhmsResult = jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(TExcel.getExcelDate(hmsDec));
			// yyyy/mm/dd hh:mm:ss 検証
			// エクセル日付うるう年対応 1900/2/29は存在しないがエクセル内では存在するためその日付以降はさらに-1
			d -= (d > 60 ? 1 : 0);
			Date expected = jp.co.ais.trans.common.util.DateUtil.addDay(baseDate, d);
			expected = jp.co.ais.trans.common.util.DateUtil.addHour(expected, h);
			expected = jp.co.ais.trans.common.util.DateUtil.addMinute(expected, m);
			String ymdhmsStr = jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(expected);
			String result = ymdhmsStr + "<>" + ymdhmsResult + " " + hmsDec + " " + d + "." + hmStr;
			if (isDebug) {
				System.out.println(result);
			}
			if (!Util.equals(ymdhmsStr, ymdhmsResult)) {
				System.out.println("NGケース [yyyy/mm/dd hh:mm:ss] :" + result);
				// return false;
			}
			assertEquals(expected, TExcel.getExcelDate(hmsDec));
		}
		return true;
	}

	/**
	 * @param h 時
	 * @param m 分
	 * @param s 秒
	 * @return 検証NG:false
	 */
	protected boolean testGetExcelDateHMS(int h, int m, int s) {
		String hh = h < 10 ? "0" + h : "" + h;
		String mm = m < 10 ? "0" + m : "" + m;
		String ss = s < 10 ? "0" + s : "" + s;

		// HH:MM 検証
		{
			String hmStr = hh + ":" + mm;
			double hmTime = DateUtil.convertTime(hmStr);
			BigDecimal hmDec = new BigDecimal(hmTime);
			String hmResult = jp.co.ais.trans.common.util.DateUtil.toHMString(TExcel.getExcelDate(hmDec));
			if (!Util.equals(hmStr, hmResult)) {
				Date dt = baseDate;
				System.out.println("NGケース [hh:mm]:" + hmStr + "<>" + hmResult);
				System.out.println(jp.co.ais.trans.common.util.DateUtil.toYMDHMString(dt));
				System.out.println(jp.co.ais.trans.common.util.DateUtil.toYMDHMString(TExcel.getExcelDate(hmDec)));
			}
			assertEquals(hmStr, hmResult);
		}

		// HH:MM:SS 検証
		{
			String hmsStr = hh + ":" + mm + ":" + ss;
			double hmsTime = DateUtil.convertTime(hmsStr);
			BigDecimal hmsDec = new BigDecimal(hmsTime);
			String hmsResult = jp.co.ais.trans.common.util.DateUtil.toHMSString(TExcel.getExcelDate(hmsDec));
			if (!Util.equals(hmsStr, hmsResult)) {
				Date dt = baseDate;
				dt = jp.co.ais.trans.common.util.DateUtil.addHour(dt, h);
				dt = jp.co.ais.trans.common.util.DateUtil.addMinute(dt, m);
				dt = jp.co.ais.trans.common.util.DateUtil.addSecond(dt, s);
				System.out.println("NGケース [hh:mm:ss] :" + hmsStr + "<>" + hmsResult);
				System.out.println(jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(dt));
				System.out.println(jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(TExcel.getExcelDate(hmsDec)));
			}
			assertEquals(hmsStr, hmsResult);
			// yyyy/mm/dd hh:mm:ss 検証
			Date dt = baseDate;
			dt = jp.co.ais.trans.common.util.DateUtil.addHour(dt, h);
			dt = jp.co.ais.trans.common.util.DateUtil.addMinute(dt, m);
			dt = jp.co.ais.trans.common.util.DateUtil.addSecond(dt, s);
			String ymdhmsStr = jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(dt);
			String ymdhmsResult = jp.co.ais.trans.common.util.DateUtil.toYMDHMSString(TExcel.getExcelDate(hmsDec));
			if (!Util.equals(ymdhmsStr, ymdhmsResult)) {
				System.out.println("NGケース [yyyy/mm/dd hh:mm:ss] :" + ymdhmsStr + "<>" + ymdhmsResult + " " + hmsDec);
				System.out.println("NGケース [yyyy/mm/dd hh:mm:ss] :" + dt + "<>" + TExcel.getExcelDate(hmsDec));
			}
			assertEquals(dt, TExcel.getExcelDate(hmsDec));
		}
		return true;
	}

	//	@Test
	//	void testConvertCmToInch() {
	//		fail("まだ実装されていません");
	//	}
	//
	//	@Test
	//	void testEvaluateAll() {
	//		fail("まだ実装されていません");
	//	}

}

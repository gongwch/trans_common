package jp.co.ais.trans.common.util;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import jp.co.ais.trans.common.except.TRuntimeException;
import jp.co.ais.trans.common.message.FormatManager;
import jp.co.ais.trans.common.message.MessageManager;

/**
 * 日付系ユーティリティ
 */
public final class DateUtil {

	/** 和暦ロケール */
	public static final Locale JP_LOCALE = new Locale("ja", "JP", "JP");

	/** システム固定フォーマッタ(年月日時分秒ミリ秒) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMSSSS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	/** システム固定フォーマッタ(年月日時分秒) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/** システム固定フォーマッタ(年月日時) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHM = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	/** システム固定フォーマッタ(年月日) */
	public static final SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy/MM/dd");

	/** システム固定フォーマッタ(年月) */
	public static final SimpleDateFormat DATE_FORMAT_YM = new SimpleDateFormat("yyyy/MM");

	/** システム固定フォーマッタ(年2月) */
	public static final SimpleDateFormat DATE_FORMAT_Y2M = new SimpleDateFormat("yy/MM");

	/** システム固定フォーマッタ(月日 時刻) */
	public static final SimpleDateFormat DATE_FORMAT_MDHM = new SimpleDateFormat("MM/dd HH:mm");

	/** システム固定フォーマッタ(月日 時刻(短縮版)) */
	public static final SimpleDateFormat DATE_FORMAT_MDHM_SHORT = new SimpleDateFormat("M/d HH:mm");

	/** システム固定フォーマッタ(年月) */
	public static final SimpleDateFormat DATE_FORMAT_Y = new SimpleDateFormat("yyyy");

	/** システム固定フォーマッタ(月日) */
	public static final SimpleDateFormat DATE_FORMAT_MD = new SimpleDateFormat("MM/dd");

	/** システム固定フォーマッタ(月日(短縮版)) */
	public static final SimpleDateFormat DATE_FORMAT_MD_SHORT = new SimpleDateFormat("M/d");

	/** システム固定フォーマッタ(時刻) */
	public static final SimpleDateFormat DATE_FORMAT_HMS = new SimpleDateFormat("HH:mm:ss");

	/** システム固定フォーマッタ(時刻：秒なし) */
	public static final SimpleDateFormat DATE_FORMAT_HM = new SimpleDateFormat("HH:mm");

	/** システム固定フォーマット（月） */
	public static final SimpleDateFormat DATE_FORMAT_MM = new SimpleDateFormat("MM");

	/** システム固定フォーマット（日） */
	public static final SimpleDateFormat DATE_FORMAT_DD = new SimpleDateFormat("dd");

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_ENGLISH = new SimpleDateFormat("d MMM yyyy ", Locale.ENGLISH);

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_ENGLISH_YMDHMS = new SimpleDateFormat("d MMM yyyy  HH:mm:ss ",
			Locale.ENGLISH);

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_EN_YMD = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_EN_YMDHM = new SimpleDateFormat("dd/MM/yyyy HH:mm",
			Locale.ENGLISH);

	/** システム固定フォーマット（英国仕様） */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM2 = new SimpleDateFormat("MMM,yyyy", Locale.ENGLISH);

	/** システム固定フォーマット（和暦年月日） */
	public static final DateFormat DATE_FORMAT_JP_GYMD = new SimpleDateFormat("GGGGyyyy年MM月dd日", JP_LOCALE);

	/** システム固定フォーマット（年月日） */
	public static final DateFormat DATE_FORMAT_JP_YMD = new SimpleDateFormat("yyyy年MM月dd日");

	/** システム固定フォーマット（年月） */
	public static final DateFormat DATE_FORMAT_JP_YM = new SimpleDateFormat("yyyy年MM月");

	/** システム固定フォーマット（月日） */
	public static final DateFormat DATE_FORMAT_JP_MD = new SimpleDateFormat("MM月dd日");

	/** システム固定フォーマット（年） */
	public static final DateFormat DATE_FORMAT_JP_Y = new SimpleDateFormat("yyyy年");

	/** システム固定フォーマット（月） */
	public static final DateFormat DATE_FORMAT_JP_M = new SimpleDateFormat("MM月");

	/** システム固定フォーマット（日） */
	public static final DateFormat DATE_FORMAT_JP_D = new SimpleDateFormat("dd日");

	/** システム固定フォーマット（和暦元号） */
	public static final SimpleDateFormat DATE_FORMAT_JP_G = new SimpleDateFormat("GGGG", JP_LOCALE);

	/** システム固定フォーマット（和暦年） */
	public static final SimpleDateFormat DATE_FORMAT_JP_YY = new SimpleDateFormat("yy", JP_LOCALE);

	/** システム固定フォーマット（和暦年YYYY） */
	public static final SimpleDateFormat DATE_FORMAT_JP_YYYY = new SimpleDateFormat("yyyy", JP_LOCALE);

	/** システム固定フォーマット（年月日時分秒ミリ秒スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMSSSS_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmmss.SSS");

	/** システム固定フォーマット（年月日時分秒スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMS_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmmss");

	/** システム固定フォーマット（年月日時分スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_YMDHM_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmm");

	/** システム固定フォーマット（年月日スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_YMD_NO_SLASH = new SimpleDateFormat("yyyyMMdd");

	/** システム固定フォーマット（年月日スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_Y2MD_NO_SLASH = new SimpleDateFormat("yyMMdd");

	/** システム固定フォーマット（年月スラッシュ無し：年は下二桁） */
	public static final SimpleDateFormat DATE_FORMAT_Y2M_NO_SLASH = new SimpleDateFormat("yyMM");

	/** システム固定フォーマット（年月スラッシュ無し） */
	public static final SimpleDateFormat DATE_FORMAT_YM_NO_SLASH = new SimpleDateFormat("yyyyMM");

	/** 日時フォーマッタ(yyyy/MM/dd HH:mm) */
	public static final String TYPE_DATE_FORMAT_YMDHM = "YMDHM";

	/** 日時フォーマッタ(yyyy/MM/dd HH:mm:ss) */
	public static final String TYPE_DATE_FORMAT_YMDHMS = "YMDHMS";

	/** 日付フォーマッタ(yyyy/MM/dd) */
	public static final String TYPE_DATE_FORMAT_YMD = "YMD";

	/** 日付フォーマッタ(yyyy/MM) */
	public static final String TYPE_DATE_FORMAT_YM = "YM";

	/** 日付フォーマッタ(MM/dd) */
	public static final String TYPE_DATE_FORMAT_MD = "MD";

	/** 日付フォーマッタ(yyyy) */
	public static final String TYPE_DATE_FORMAT_YYYY = "YYYY";

	/** 日付フォーマッタ(MM) */
	public static final String TYPE_DATE_FORMAT_MM = "MM";

	/** 日付フォーマッタ(M) */
	public static final String TYPE_DATE_FORMAT_M = "M";

	/** 日付フォーマッタ(dd) */
	public static final String TYPE_DATE_FORMAT_DD = "DD";

	/** 日付フォーマッタ(d) */
	public static final String TYPE_DATE_FORMAT_D = "D";

	/** 日付フォーマッタ(yyyy/MM) */
	public static final String TYPE_DATE_FORMAT_HMS = "HMS";

	/** 日付フォーマッタ(yyyy年MM月dd日) */
	public static final String TYPE_DATE_FORMAT_YMD_C_1 = "YMD_C_1";

	/** 日付フォーマッタ(yyyy年M月d日) */
	public static final String TYPE_DATE_FORMAT_YMD_C_2 = "YMD_C_2";

	/** 時間フォーマッタ(HH時mm分ss秒) */
	public static final String TYPE_DATE_FORMAT_HMS_C_1 = "HMS_C_1";

	/** 時間フォーマッタ(H時m分s秒) */
	public static final String TYPE_DATE_FORMAT_HMS_C_2 = "HMS_C_2";

	/** フォーマット */
	public static Map formatters;

	/** yyyy(年)かどうかの正規表現パターン */
	public static final Pattern PATTERN_Y = Pattern.compile("\\d\\d\\d\\d");

	/** yyyy/MM(年月)かどうかの正規表現パターン */
	public static final Pattern PATTERN_YM = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d");

	/** yyyy/MM/dd(年月日)かどうかの正規表現パターン */
	public static final Pattern PATTERN_YMD = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d");

	/** yyyy/MM/dd HH:mm(年月日時)かどうかの正規表現パターン */
	public static final Pattern PATTERN_YMDHM = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d \\d\\d:\\d\\d");

	/** yyyy/MM/dd HH:mm:ss(年月日時分秒)かどうかの正規表現パターン */
	public static final Pattern PATTERN_YMDHMS = Pattern
			.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d \\d\\d:\\d\\d:\\d\\d");

	/** ONE_DAY_MILLISECOND */
	public static final BigDecimal ONE_DAY_MILLISECOND = new BigDecimal(24 * 60 * 60 * 1000);

	/** ONE_DAY_SECOND */
	public static final BigDecimal ONE_DAY_SECOND = new BigDecimal(24 * 60 * 60);

	/** ONE_HOUR_SECOND */
	public static final BigDecimal ONE_HOUR_SECOND = new BigDecimal(60 * 60);

	/** 通常年の月の日数 */
	public static int[] DaysToMonth365 = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

	/** 閏年の月の日数 */
	public static int[] DaysToMonth366 = { 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366 };

	/** 日付形式YMD */
	public static final SimpleDateFormat DATE_FORMAT_YMD_SHORT = new SimpleDateFormat("yyyy/M/d");

	/** 日付形式YMD2 */
	public static final SimpleDateFormat DATE_FORMAT_YMD2 = new SimpleDateFormat("yyyy/MM/dd");

	/** 日付形式MMMM,yyyy */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM3 = new SimpleDateFormat("MMMM,yyyy", Locale.US);

	/** 日付形式MDY */
	public static final SimpleDateFormat DATE_FORMAT_MDY = new SimpleDateFormat("M/d/yyyy");

	/** 日付形式M.D */
	public static final SimpleDateFormat DATE_FORMAT_MD_DOT = new SimpleDateFormat("M.d");

	/** 日付形式HHmmss */
	public static final SimpleDateFormat TIME_FORMAT_HMS_NO_SLASH = new SimpleDateFormat("HHmmss");

	/** yyyy年M月d日 */
	public static final DateFormat DATE_FORMAT_JP_YMD_SHORT = new SimpleDateFormat("yyyy年M月d日");

	/** yyyy年M月d日 HH時 mm分 */
	public static final DateFormat DATE_FORMAT_JP_YMDHM = new SimpleDateFormat("yyyy年 M月 d日 HH時 mm分");

	/** 年M月d日 */
	public static final DateFormat DATE_FORMAT_JP_MD_SHORT = new SimpleDateFormat("M月d日");

	static {
		// マルチスレッド制御
		formatters = Collections.synchronizedMap(new TreeMap<String, MessageManager>());

		DATE_FORMAT_Y.setLenient(false);
		DATE_FORMAT_YM.setLenient(false);
		DATE_FORMAT_Y2M.setLenient(false);
		DATE_FORMAT_MDHM.setLenient(false);
		DATE_FORMAT_MDHM_SHORT.setLenient(false);
		DATE_FORMAT_YMD.setLenient(false);
		DATE_FORMAT_MD.setLenient(false);
		DATE_FORMAT_MD_SHORT.setLenient(false);
		DATE_FORMAT_JP_YMD.setLenient(false);
		DATE_FORMAT_JP_YM.setLenient(false);
		DATE_FORMAT_YMDHM.setLenient(false);
		DATE_FORMAT_YMDHMS.setLenient(false);
		DATE_FORMAT_YMDHMSSSS.setLenient(false);
		DATE_FORMAT_HMS.setLenient(false);
		DATE_FORMAT_HM.setLenient(false);
		DATE_FORMAT_ENGLISH.setLenient(false);
		DATE_FORMAT_ENGLISH_YMDHMS.setLenient(false);
		DATE_FORMAT_EN_YM.setLenient(false);
		DATE_FORMAT_EN_YMD.setLenient(false);
		DATE_FORMAT_EN_YMDHM.setLenient(false);
		DATE_FORMAT_EN_YM2.setLenient(false);
		DATE_FORMAT_YMDHMSSSS_NO_SLASH.setLenient(false);

		DATE_FORMAT_YMD_SHORT.setLenient(false);
		DATE_FORMAT_YMD2.setLenient(false);
		DATE_FORMAT_EN_YM3.setLenient(false);
		DATE_FORMAT_MDY.setLenient(false);
		DATE_FORMAT_MD_DOT.setLenient(false);
		TIME_FORMAT_HMS_NO_SLASH.setLenient(false);
		DATE_FORMAT_JP_YMD_SHORT.setLenient(false);
		DATE_FORMAT_JP_YMDHM.setLenient(false);
		DATE_FORMAT_JP_MD_SHORT.setLenient(false);

	}

	/**
	 * 指定した日付の年月の初日を返す
	 * 
	 * @param date 対象日付
	 * @return 指定月の初日(1日)
	 */
	public static Date getFirstDate(Date date) {

		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		int lastday = gc.getActualMinimum(Calendar.DATE);

		return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), lastday).getTime();
	}

	/**
	 * 指定した年月の最終日付を返す
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 指定月の最終日
	 */
	public static Date getLastDate(int year, int month) {
		int lastday = DateUtil.getLastDay(year, month);

		return new GregorianCalendar(year, month - 1, lastday).getTime();
	}

	/**
	 * 指定した日付の年月の最終日付を返す
	 * 
	 * @param date 対象日付
	 * @return 指定月の最終日
	 */
	public static Date getLastDate(Date date) {

		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		int lastday = gc.getActualMaximum(Calendar.DATE);

		return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), lastday).getTime();
	}

	/**
	 * 指定した年月の最終日を返す
	 * 
	 * @param year 年
	 * @param month 月
	 * @return 指定月の最終日
	 */
	public static int getLastDay(int year, int month) {
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1);
		return gc.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 指定した年月の最終日を返す
	 * 
	 * @param date 日付
	 * @return 指定月の最終日
	 */
	public static int getLastDay(Date date) {
		return getLastDay(getYear(date), getMonth(date));
	}

	/**
	 * 開始年月から終了年月の月数取得
	 * 
	 * @param startDate 開始
	 * @param endDate 終了
	 * @return 月数
	 */
	public static int getMonthCount(Date startDate, Date endDate) {

		Calendar cl = Calendar.getInstance();
		cl.setTime(startDate);

		int startYear = cl.get(Calendar.YEAR);
		int startMonth = cl.get(Calendar.MONTH) + 1;

		cl.setTime(endDate);

		int endYear = cl.get(Calendar.YEAR);
		int endMonth = cl.get(Calendar.MONTH) + 1;

		return getMonthCount(startYear, startMonth, endYear, endMonth);
	}

	/**
	 * 開始年月から終了年月の月数取得
	 * 
	 * @param startYear 開始年
	 * @param startMonth 開始月
	 * @param endYear 終了年
	 * @param endMonth 終了月
	 * @return 月数
	 */
	public static int getMonthCount(int startYear, int startMonth, int endYear, int endMonth) {
		return (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
	}

	/**
	 * util.Date型をsql.Timestamp型に変換する
	 * 
	 * @param dt Date
	 * @return 変換後Timestamp
	 */
	public static Timestamp toTimestamp(Date dt) {
		return new Timestamp(dt.getTime());
	}

	/**
	 * Dateクラスから年を取得
	 * 
	 * @param dt Dateクラス
	 * @return 年
	 */
	public static int getYear(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.YEAR);
	}

	/**
	 * Dateクラスから月を取得
	 * 
	 * @param dt Dateクラス
	 * @return 月
	 */
	public static int getMonth(Date dt) {
		Calendar cl = new GregorianCalendar();
		cl.setTime(dt);

		return cl.get(Calendar.MONTH) + 1;
	}

	/**
	 * Dateクラスから日にちを取得
	 * 
	 * @param dt Dateクラス
	 * @return 日にち
	 */
	public static int getDay(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.DATE);
	}

	/**
	 * Dateクラスから時間(24H)を取得
	 * 
	 * @param dt Dateクラス
	 * @return 時間
	 */
	public static int getHour(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Dateクラスから分を取得
	 * 
	 * @param dt Dateクラス
	 * @return 分
	 */
	public static int getMinute(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.MINUTE);
	}

	/**
	 * Dateクラスから秒を取得
	 * 
	 * @param dt Dateクラス
	 * @return 秒
	 */
	public static int getSecond(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.SECOND);
	}

	/**
	 * Dateクラスから曜日を取得
	 * 
	 * @param dt Dateクラス
	 * @return 曜日(1:日曜～7:土曜)
	 */
	public static int getDayOfWeek(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Dateを取得する.
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 指定された年、月、日に対応するDate
	 */
	public static Date getDate(int year, int month, int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	/**
	 * Dateを取得する.
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @param hourOfDay 時(24時間表記)
	 * @param minute 秒
	 * @param second 分
	 * @return 指定された年、月、日、時、分、秒に対応するDate
	 */
	public static Date getDate(int year, int month, int day, int hourOfDay, int minute, int second) {
		return new GregorianCalendar(year, month - 1, day, hourOfDay, minute, second).getTime();
	}

	/**
	 * Timestampを取得する.
	 * 
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 指定された年、月、日に対応するTimestamp
	 */
	public static Timestamp getTimestamp(int year, int month, int day) {
		return toTimestamp(getDate(year, month, day));
	}

	/**
	 * 年数加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する年数
	 * @return 加算後の日付
	 */
	public static Date addYear(Date dt, int n) {
		return addTime(dt, n, Calendar.YEAR);
	}

	/**
	 * 月数加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する月数
	 * @return 加算後の日付
	 */
	public static Date addMonth(Date dt, int n) {
		return addTime(dt, n, Calendar.MONTH);
	}

	/**
	 * 日数加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する日数
	 * @return 加算後の日付
	 */
	public static Date addDay(Date dt, int n) {
		return addTime(dt, n, Calendar.DAY_OF_MONTH);
	}

	/**
	 * 時加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する日数
	 * @return 加算後の日付
	 */
	public static Date addHour(Date dt, int n) {
		return addTime(dt, n, Calendar.HOUR_OF_DAY);
	}

	/**
	 * 分加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する日数
	 * @return 加算後の日付
	 */
	public static Date addMinute(Date dt, int n) {
		return addTime(dt, n, Calendar.MINUTE);
	}

	/**
	 * 秒加算
	 * 
	 * @param dt 対象日付
	 * @param n 加算する日数
	 * @return 加算後の日付
	 */
	public static Date addSecond(Date dt, int n) {
		return addTime(dt, n, Calendar.SECOND);
	}

	/**
	 * @param dt 対象日付
	 * @param n 加算する日数
	 * @param kind 種類(カレンダフィールド). Calendar.YEARなど
	 * @return 加算後の日付
	 */
	public static Date addTime(Date dt, int n, int kind) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);
		cl.add(kind, n);

		return cl.getTime();
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy/MM/dd" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYMDDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_YMD) {
			return DATE_FORMAT_YMD.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy年MM月dd日" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toJpYMDDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_JP_YMD) {
			return DATE_FORMAT_JP_YMD.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy/MM/dd HH:mm" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYMDHMDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_YMDHM) {
			return DATE_FORMAT_YMDHM.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy/MM/dd HH:mm:ss" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYMDHMSSSSDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_YMDHMSSSS) {
			return DATE_FORMAT_YMDHMSSSS.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy/MM/dd HH:mm:ss" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYMDHMSDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_YMDHMS) {
			return DATE_FORMAT_YMDHMS.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy/MM" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYMDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_YM) {
			return DATE_FORMAT_YM.parse(str.replace("-", "/"));
		}
	}

	/**
	 * StringをDateにキャスト
	 * 
	 * @param str キャストするString "yyyy" 形式の日付
	 * @return キャストされたDate
	 * @throws ParseException 日付文字列が不正な時
	 */
	public static Date toYDate(String str) throws ParseException {
		if (Util.isNullOrEmpty(str)) {
			return null;
		}

		synchronized (DATE_FORMAT_Y) {
			return DATE_FORMAT_Y.parse(str.replace("-", "/"));
		}
	}

	/**
	 * 指定されたDateを、時間が0の日付(YYYY/MM/DD 00:00:00.000)に置き換える
	 * 
	 * @param date 変換対象日付
	 * @return 時間が0の日付
	 */
	public static Date toYMDDate(Date date) {
		if (date == null) {
			return null;
		}

		Calendar gc = Calendar.getInstance();
		gc.setTime(date);

		Date result = new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH),
				gc.get(Calendar.DAY_OF_MONTH)).getTime();

		if (date instanceof Timestamp) {
			return new Timestamp(result.getTime());
		}

		return result;
	}

	/**
	 * 日付の文字列変換("d MMM yyyy ", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return d MMM yyyy(例：29 Mar 2007) 形式文字列
	 */
	public static String toEnYMDString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_ENGLISH) {
			return DATE_FORMAT_ENGLISH.format(dt);
		}
	}

	/**
	 * 日付の文字列変換("d MMM yyyy HH:mm:ss ", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return d MMM yyyy HH:mm:ss(例：29 Mar 2007 12:31:12) 形式文字列
	 */
	public static String toEnYMDHMSString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_ENGLISH_YMDHMS) {
			return DATE_FORMAT_ENGLISH_YMDHMS.format(dt);
		}
	}

	/**
	 * 日付の文字列変換("MM/yyyy", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return MM/yyyy(例：03/2007) 形式文字列
	 */
	public static String toEngYMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_EN_YM) {
			return DATE_FORMAT_EN_YM.format(dt);
		}
	}

	/**
	 * 日付の文字列変換("dd/MM/yyyy", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return dd/MM/yyyy(例：29/03/2007) 形式文字列
	 */
	public static String toEngYMDString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_EN_YMD) {
			return DATE_FORMAT_EN_YMD.format(dt);
		}
	}

	/**
	 * 日付の文字列変換("dd/MM/yyyy HH:mm", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return dd/MM/yyyy HH:mm(例：29/03/2007 12:31) 形式文字列
	 */
	public static String toEngYMDHMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_EN_YMDHM) {
			return DATE_FORMAT_EN_YMDHM.format(dt);
		}
	}

	/**
	 * 日付の文字列変換("HH:mm dd(th) MMM,yyyy", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return HH:mm dd(th) MMM,yyyy(例：12:31 29th MARCH,2007) 形式文字列
	 */
	public static String toEngDateTimeString(Date dt) {
		if (dt == null) {
			return "";
		}

		return toHMString(dt) + " " + toEngDateString(dt);
	}

	/**
	 * 日付の文字列変換("dd(th) MMM,yyyy", Locale.ENGLISH)
	 * 
	 * @param dt 日付
	 * @return dd(th) MMM,yyyy(例：29th MARCH,2007) 形式文字列
	 */
	public static String toEngDateString(Date dt) {
		if (dt == null) {
			return "";
		}

		int dayOfMonth = DateUtil.getDay(dt);
		StringBuilder sb = new StringBuilder();
		sb.append(dayOfMonth);

		switch (dayOfMonth) {
		case 1:
		case 21:
		case 31:
			sb.append("st");
			break;
		case 2:
		case 22:
			sb.append("nd");
			break;
		case 3:
		case 23:
			sb.append("rd");
			break;
		default:
			sb.append("th");
			break;
		}

		synchronized (DATE_FORMAT_EN_YM2) {
			sb.append(" ");
			sb.append(DATE_FORMAT_EN_YM2.format(dt));
		}

		return sb.toString();
	}

	/**
	 * 日付の文字列変換(yyyy/MM/dd HH:mm:ss)
	 * 
	 * @param dt 日付
	 * @return yyyy/MM/dd HH:mm:ss 形式文字列
	 */
	public static String toYMDHMSSSSString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHMSSSS) {
			return DATE_FORMAT_YMDHMSSSS.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyy/MM/dd HH:mm:ss)
	 * 
	 * @param dt 日付
	 * @return yyyy/MM/dd HH:mm:ss 形式文字列
	 */
	public static String toYMDHMSString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHMS) {
			return DATE_FORMAT_YMDHMS.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyy/MM/dd HH:mm)
	 * 
	 * @param dt 日付
	 * @return yyyy/MM/dd HH:mm 形式文字列
	 */
	public static String toYMDHMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHM) {
			return DATE_FORMAT_YMDHM.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyy/MM)
	 * 
	 * @param dt 日付
	 * @return yyyy/MM/dd 形式文字列
	 */
	public static String toYMDString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMD) {
			return DATE_FORMAT_YMD.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yy/MM)
	 * 
	 * @param dt 日付
	 * @return yy/MM 形式文字列
	 */
	public static String toY2MString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_Y2M) {
			return DATE_FORMAT_Y2M.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyy/MM)
	 * 
	 * @param dt 日付
	 * @return yyyy/MM 形式文字列
	 */
	public static String toYMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YM) {
			return DATE_FORMAT_YM.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyy)
	 * 
	 * @param dt 日付
	 * @return yyyy 形式文字列
	 */
	public static String toYString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_Y) {
			return DATE_FORMAT_Y.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(MM/dd)
	 * 
	 * @param dt 日付
	 * @return MM/dd 形式文字列
	 */
	public static String toMDString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_MD) {
			return DATE_FORMAT_MD.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(M/d)
	 * 
	 * @param dt 日付
	 * @return M/d 形式文字列(短縮版)
	 */
	public static String toMDShortString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_MD_SHORT) {
			return DATE_FORMAT_MD_SHORT.format(dt);
		}
	}

	/**
	 * 日付時刻の文字列変換(MM/dd HH:mm)
	 * 
	 * @param dt 日付
	 * @return MM/dd HH:mm 形式文字列
	 */
	public static String toMDHMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_MDHM) {
			return DATE_FORMAT_MDHM.format(dt);
		}
	}

	/**
	 * 日付時刻の文字列変換(M/d HH:mm)
	 * 
	 * @param dt 日付
	 * @return M/d HH:mm 形式文字列(短縮版)
	 */
	public static String toMDHMShortString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_MDHM_SHORT) {
			return DATE_FORMAT_MDHM_SHORT.format(dt);
		}
	}

	/**
	 * 時刻の文字列変換(HH:mm:ss)
	 * 
	 * @param dt 日付
	 * @return HH:mm:ss 形式文字列
	 */
	public static String toHMSString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_HMS) {
			return DATE_FORMAT_HMS.format(dt);
		}
	}

	/**
	 * 時刻の文字列変換(HH:mm)
	 * 
	 * @param dt 日付
	 * @return HH:mm 形式文字列
	 */
	public static String toHMString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_HM) {
			return DATE_FORMAT_HM.format(dt);
		}
	}

	/**
	 * Dateを「月(MM)」形式文字列に変換
	 * 
	 * @param date 対象日付
	 * @return MM形式文字
	 */
	public static String toMMString(Date date) {
		return DATE_FORMAT_MM.format(date);
	}

	/**
	 * Dateを「日(DD)」形式文字列に変換
	 * 
	 * @param date 対象日付
	 * @return dd形式文字
	 */
	public static String toDDString(Date date) {
		return DATE_FORMAT_DD.format(date);
	}

	/**
	 * 和暦YY年MM月DD日フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return 和暦YMD文字
	 */
	public static String toJpGYMDString(Date ymdDate) {
		return DATE_FORMAT_JP_GYMD.format(ymdDate);
	}

	/**
	 * YYYY年MM月DD日フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return YMD文字
	 */
	public static String toJpYMDString(Date ymdDate) {
		return DATE_FORMAT_JP_YMD.format(ymdDate);
	}

	/**
	 * YYYY年MM月フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return YM文字
	 */
	public static String toJpYMString(Date ymdDate) {
		return DATE_FORMAT_JP_YM.format(ymdDate);
	}

	/**
	 * MM月DD日フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return MD文字
	 */
	public static String toJpMDString(Date ymdDate) {
		return DATE_FORMAT_JP_MD.format(ymdDate);
	}

	/**
	 * YYYY年フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return Y文字
	 */
	public static String toJpYString(Date ymdDate) {
		return DATE_FORMAT_JP_Y.format(ymdDate);
	}

	/**
	 * MM月フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return M文字
	 */
	public static String toJpMString(Date ymdDate) {
		return DATE_FORMAT_JP_M.format(ymdDate);
	}

	/**
	 * DD日フォーマット変換
	 * 
	 * @param ymdDate 対象日付
	 * @return D文字
	 */
	public static String toJpDString(Date ymdDate) {
		return DATE_FORMAT_JP_D.format(ymdDate);
	}

	/**
	 * 和暦年(YY)フォーマット変換
	 * 
	 * @param date 対象日付
	 * @return 和暦YY文字
	 */
	public static String toJpYYString(Date date) {
		return DATE_FORMAT_JP_YY.format(date);
	}

	/**
	 * 和暦→西暦変換<br>
	 * 指定する和暦は、現在の年号での年
	 * 
	 * @param jpYear 和暦(YY)
	 * @return 西暦(YYYY) [取得できない場合は0]
	 */
	public static int toERA(int jpYear) {
		try {
			Date date = DATE_FORMAT_JP_YYYY.parse(String.valueOf(jpYear));

			return DateUtil.getYear(date);
		} catch (ParseException ex) {
			return 0;
		}
	}

	/**
	 * 和暦→西暦変換<br>
	 * 指定する和暦は、現在の年号
	 * 
	 * @param jpYear 和暦(YY)
	 * @return 西暦(YYYY) [取得できない場合は0000]
	 */
	public static String toERA(String jpYear) {

		return String.valueOf(NumberFormatUtil.zeroFill(toERA(Integer.parseInt(jpYear)), 4));
	}

	/**
	 * 和暦の年号を返す. <br>
	 * 西暦(明治以前)、明治、大正、昭和、平成
	 * 
	 * @param date 対象日時
	 * @return 年号の文字
	 */
	public static String getJpERAWord(Date date) {
		return DATE_FORMAT_JP_G.format(date);
	}

	/**
	 * 和暦の年号を返す.<br>
	 * 0:平成から明治に該当しない<br>
	 * 1:明治<br>
	 * 2:大正<br>
	 * 3:昭和<br>
	 * 4:平成<br>
	 * 5:令和<br>
	 * 
	 * @param date 対象日時
	 * @return 年号固定値
	 */
	public static int getJpERA(Date date) {
		Calendar cal = Calendar.getInstance(JP_LOCALE);
		cal.setTime(date);

		return cal.get(Calendar.ERA);
	}

	/**
	 * 西暦→和暦変換<br>
	 * 
	 * @param year 西暦(YYYY)
	 * @return 和暦(YY)
	 */
	public static int toJpERA(int year) {
		String jpYear = DATE_FORMAT_JP_YYYY.format(DateUtil.getDate(year, 1, 1));

		return Integer.parseInt(jpYear);
	}

	/**
	 * 西暦→和暦変換<br>
	 * 
	 * @param year 西暦(YYYY)
	 * @return 和暦(YY)
	 */
	public static String toJpERA(String year) {
		return String.valueOf(toJpERA(Integer.parseInt(year)));
	}

	/**
	 * 和暦YMDフォーマット変換.<br>
	 * 年号、YY年、MM月、DD日を分けた配列形式で取得する.
	 * 
	 * @param ymdDate 対象日付
	 * @return 和暦YMD文字の分割配列
	 */
	public static String[] toJpYMDArray(Date ymdDate) {
		if (ymdDate == null) {
			return new String[0];
		}

		String era = getJpERAWord(ymdDate);//和歴の年号
		String eraYear = toJpYYString(ymdDate);//和歴の年
		String month = toMMString(ymdDate);//MM
		String day = toDDString(ymdDate);//dd

		String[] result = new String[4];
		result[0] = era; // 年号
		result[1] = String.format(Integer.parseInt(eraYear) + "年"); // Y年
		result[2] = String.format(month + "月"); // MM月
		result[3] = String.format(day + "日"); // DD日

		return result;
	}

	/**
	 * Dateを「年月日時分秒」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @param type フォーマットタイプ
	 * @return キャストされたString
	 */
	public static String toString(String lang, Date dt, String type) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, type).format(dt);
	}

	/**
	 * Dateを「年月日時分秒」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @return キャストされたString
	 */
	public static String toYMDHMSString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMDHMS).format(dt);
	}

	/**
	 * Dateを「年月日時分」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @return キャストされたString
	 */
	public static String toYMDHMString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMDHM).format(dt);
	}

	/**
	 * Dateを「年月日」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @return キャストされたString
	 */
	public static String toYMDString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMD).format(dt);
	}

	/**
	 * Dateを「年月」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @return キャストされたString
	 */
	public static String toYMString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YM).format(dt);
	}

	/**
	 * Dateを「時分秒」形式文字列に変換
	 * 
	 * @param lang 言語コード
	 * @param dt キャストするDate
	 * @return キャストされたString
	 */
	public static String toHMSString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_HMS).format(dt);
	}

	/**
	 * フォーマット取得
	 * 
	 * @param lang 言語コード
	 * @param key フォーマットキー
	 * @return フォーマット文字
	 */
	public static String getFormatString(String lang, String key) {
		return FormatManager.getInstance(lang).get(key);
	}

	/**
	 * 日時フォーマットの取得
	 * 
	 * @param lang 言語コード
	 * @param type フォーマットタイプ
	 * @return 日時フォーマット
	 */
	public static SimpleDateFormat getDateFormat(String lang, String type) {

		String pk = lang + ":" + type;
		if (!formatters.containsKey(pk)) {
			formatters.put(pk, new SimpleDateFormat(getFormatString(lang, type)));
		}

		return (SimpleDateFormat) formatters.get(pk);
	}

	/**
	 * 日付の文字列取得（yyyy/MM/dd HH:mm:ss）
	 * 
	 * @param millisecond UTC milliseconds
	 * @return yyyy/MM/dd HH:mm:ss 形式文字列
	 */
	public static String getYMDString(long millisecond) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(millisecond);

		return toYMDHMSString(cl.getTime());
	}

	/**
	 * 開始日付と終了日付を比較し、その期間を日数で取得する.<br>
	 * 時間が指定されている場合は、無視する.<br>
	 * 開始日当日はカウントしない.<br>
	 * <br>
	 * ex) 2008/04/23～2008/04/25 → 2日
	 * 
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @return 日数
	 */
	public static int getDayCount(Date fromDate, Date toDate) {
		if (fromDate == null || toDate == null) {
			return 0;
		}

		// 時間のクリア(日数のみにする)
		Calendar fromCal = Calendar.getInstance();
		fromCal.setTime(fromDate);
		fromCal.set(Calendar.HOUR_OF_DAY, 0);
		fromCal.set(Calendar.MINUTE, 0);
		fromCal.set(Calendar.SECOND, 0);
		fromCal.set(Calendar.MILLISECOND, 0);

		Calendar toCal = Calendar.getInstance();
		toCal.setTime(toDate);
		toCal.set(Calendar.HOUR_OF_DAY, 0);
		toCal.set(Calendar.MINUTE, 0);
		toCal.set(Calendar.SECOND, 0);
		toCal.set(Calendar.MILLISECOND, 0);

		return (int) ((toCal.getTimeInMillis() - fromCal.getTimeInMillis()) / (1000 * 60 * 60 * 24));
	}

	/**
	 * 開始日付と終了日付を比較し、その期間を日数で取得する.<br>
	 * 時間が指定されている場合は、無視する.<br>
	 * 開始日当日をカウントする.<br>
	 * <br>
	 * ex) 2008/04/23～2008/04/25 → 3日
	 * 
	 * @param fromDate 開始日付
	 * @param toDate 終了日付
	 * @return 日数
	 */
	public static int getDayCountFC(Date fromDate, Date toDate) {
		return getDayCount(fromDate, toDate) + 1;
	}

	/**
	 * FROM日時とTO日時の差の取得（小数点以下制御） <br>
	 * <br>
	 * ex) 2011/01/01～2011/09/27 10:23:00(小数点以下6桁数) → 269.432639
	 * 
	 * @param from 開始日時
	 * @param to 終了日時
	 * @param decimalPoint 小数点以下桁数
	 * @return days 時間差
	 */
	public static BigDecimal getDayCount(Date from, Date to, int decimalPoint) {

		BigDecimal days = BigDecimal.ZERO;

		if (from == null || to == null) {
			return days;
		}

		long daysecond = to.getTime() - from.getTime();
		days = DecimalUtil.divideRound((new BigDecimal(daysecond)), ONE_DAY_MILLISECOND, decimalPoint);

		return days;
	}

	/**
	 * 指定日付に日数を加算する<br>
	 * 精度は秒まで<br>
	 * 0.000015 → 1秒<br>
	 * 0.00001 → 0秒
	 * 
	 * @param dt
	 * @param days 日数。例）1.23456
	 * @return 日付
	 */
	public static Date addDays(Date dt, BigDecimal days) {
		int seconds = DecimalUtil.roundNum(days.multiply(ONE_DAY_SECOND), 0).intValue();
		return addSecond(dt, seconds);
	}

	/**
	 * 指定日付に時間数を加算する<br>
	 * 精度は秒まで
	 * 
	 * @param dt
	 * @param hours 時間数。例）0.5
	 * @return 日付
	 */
	public static Date addHours(Date dt, BigDecimal hours) {
		int seconds = DecimalUtil.roundNum(hours.multiply(ONE_HOUR_SECOND), 0).intValue();
		return addSecond(dt, seconds);
	}

	/**
	 * 指定文字がyyyy形式であるかどうかを判断する.
	 * 
	 * @param str 対象文字
	 * @return true:yyyy文字
	 */
	public static boolean isYDate(String str) {
		try {
			toYDate(str);

			return true;

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 指定文字がyyyy/MM形式であるかどうかを判断する.
	 * 
	 * @param str 対象文字
	 * @return true:yyyy/MM文字
	 */
	public static boolean isYMDate(String str) {
		try {
			toYMDate(str);

			return true;

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 指定文字がyyyy/MM/DD形式であるかどうかを判断する.
	 * 
	 * @param str 対象文字
	 * @return true:yyyy/MM/dd文字
	 */
	public static boolean isYMDDate(String str) {
		try {
			toYMDDate(str);

			return true;

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 指定文字がyyyy/MM/dd HH:mm形式であるかどうかを判断する.
	 * 
	 * @param str 対象文字
	 * @return true:yyyy/MM/dd文字
	 */
	public static boolean isYMDHMDate(String str) {
		try {
			toYMDHMDate(str);

			return true;

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * 指定文字がyyyy/MM/dd HH:mm形式であるかどうかを判断する.
	 * 
	 * @param str 対象文字
	 * @return true:yyyy/MM/dd文字
	 */
	public static boolean isYMDHMSDate(String str) {
		try {
			toYMDHMSDate(str);

			return true;

		} catch (ParseException ex) {
			return false;
		}
	}

	/**
	 * <b>
	 * 
	 * <pre>
	 * ※注意：「/-」が含んだ日付判断はverify(String)推奨
	 * </pre>
	 * 
	 * </b><br>
	 * 指定文字が日付(yyyy/MM/dd)形式であるかどうかを判断する.<br>
	 * /を含めた形で文字列の長さで判定する.<br>
	 * 4文字 → yyyy <br>
	 * 7文字 → yyyy/MM <br>
	 * 10文字 → yyyy/MM/dd <br>
	 * 16文字 → yyyy/MM/dd HH:mm <br>
	 * 19文字 → yyyy/MM/dd HH:mm:ss <br>
	 * 
	 * @param str 対象文字
	 * @return true:日付文字
	 */
	public static boolean isDate(String str) {

		int length = str.length();

		switch (length) {
		case 4:
			return PATTERN_Y.matcher(str).matches() && DateUtil.isYDate(str);

		case 7:
			return PATTERN_YM.matcher(str).matches() && DateUtil.isYMDate(str);

		case 10:
			return PATTERN_YMD.matcher(str).matches() && DateUtil.isYMDDate(str);

		case 16:
			return PATTERN_YMDHM.matcher(str).matches() && DateUtil.isYMDHMDate(str);

		case 19:
			return PATTERN_YMDHMS.matcher(str).matches() && DateUtil.isYMDHMSDate(str);

		default:
			return false;
		}
	}

	/**
	 * 対象1の日時と対象2の日時が同一かどうかを判断する.<br>
	 * 文字で判断する為、
	 * 
	 * @param date1 対象1
	 * @param date2 対象2
	 * @return true:同一時刻
	 */
	public static boolean equals(Date date1, Date date2) {
		return DateUtil.toYMDHMSString(date1).equals(DateUtil.toYMDHMSString(date2));
	}

	/**
	 * 対象1の日時と対象2の日時が同一かどうかを判断する.<br>
	 * 文字で判断する為、<br>
	 * 対象1と対象2もnullの場合、trueを戻る
	 * 
	 * @param date1 対象1
	 * @param date2 対象2
	 * @return true:同一時刻
	 */
	public static boolean equalsAvoidNull(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		}

		return equals(date1, date2);
	}

	/**
	 * YYYYMMDD形式の文字列をYYYY/MM/DDに変換
	 * 
	 * @param strDate 対象文字
	 * @return 変換後文字
	 * @throws ParseException
	 */
	public static Date toYMDDateYYYYMMDDString(String strDate) throws ParseException {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}
		Date date = DATE_FORMAT_YMD_NO_SLASH.parse(strDate);
		return DateUtil.toYMDDate(date);
	}

	/**
	 * 日付の文字列変換(yyyymmdd)
	 * 
	 * @param dt 日付
	 * @return yyyymmdd 形式文字列
	 */
	public static String toYMDPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMD_NO_SLASH) {
			return DATE_FORMAT_YMD_NO_SLASH.format(dt);
		}
	}

	/**
	 * YYMMDD形式の文字列をYYYY/MM/DDに変換<br>
	 * 年は20XXとする
	 * 
	 * @param strDate 対象文字
	 * @return 変換後文字
	 * @throws ParseException
	 */
	public static Date toY2MDDateYYMMDDString(String strDate) throws ParseException {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}
		Date date = DATE_FORMAT_YMD_NO_SLASH.parse("20" + strDate);
		return DateUtil.toYMDDate(date);
	}

	/**
	 * 日付の文字列変換(yymmdd)
	 * 
	 * @param dt 日付
	 * @return yymmdd 形式文字列
	 */
	public static String toY2MDPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_Y2MD_NO_SLASH) {
			return DATE_FORMAT_Y2MD_NO_SLASH.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yymm)
	 * 
	 * @param dt 日付
	 * @return yymm 形式文字列
	 */
	public static String toY2MPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_Y2M_NO_SLASH) {
			return DATE_FORMAT_Y2M_NO_SLASH.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyyMMddHHmmss.SSS)
	 * 
	 * @param dt 日付
	 * @return yyyyMMddHHmmss.SSS 形式文字列
	 */
	public static String toYMDHMSSSSPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHMSSSS_NO_SLASH) {
			return DATE_FORMAT_YMDHMSSSS_NO_SLASH.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyyMMddHHmmss)
	 * 
	 * @param dt 日付
	 * @return yyyyMMddHHmmss 形式文字列
	 */
	public static String toYMDHMSPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHMS_NO_SLASH) {
			return DATE_FORMAT_YMDHMS_NO_SLASH.format(dt);
		}
	}

	/**
	 * 日付の文字列変換(yyyyMMddHHmm)
	 * 
	 * @param dt 日付
	 * @return yyyyMMddHHmm 形式文字列
	 */
	public static String toYMDHMPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YMDHM_NO_SLASH) {
			return DATE_FORMAT_YMDHM_NO_SLASH.format(dt);
		}
	}

	/**
	 * YYYYMM形式の文字列をYYYY/MM/DDに変換
	 * 
	 * @param strDate 対象文字
	 * @return 変換後文字
	 * @throws ParseException
	 */
	public static Date toYMDDateYYYYMMString(String strDate) throws ParseException {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}
		Date date = DATE_FORMAT_YM_NO_SLASH.parse(strDate);
		return DateUtil.toYMDDate(date);
	}

	/**
	 * 日付の文字列変換(yyyymm)
	 * 
	 * @param dt 日付
	 * @return yyyymm 形式文字列
	 */
	public static String toYMPlainString(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (DATE_FORMAT_YM_NO_SLASH) {
			return DATE_FORMAT_YM_NO_SLASH.format(dt);
		}
	}

	/**
	 * ※Exceptionなし<br>
	 * 指定の対象をyyyy/MM/dd HH:mm:ss.SSS日付に変換<br>
	 * 以下のフォーマットは受入できる<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * 「/」、「-」は自動消す処理あり
	 * 
	 * @param obj 対象
	 * @return 変換後文字<br>
	 *         日付ではない場合、nullを戻す
	 */
	public static Date toDateNE(Object obj) {
		try {

			if (obj == null) {
				return null;
			}

			if (obj instanceof Date) {
				return (Date) obj;
			}

			return toDate(Util.avoidNull(obj));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 指定の文字列をyyyy/MM/dd HH:mm:ss.SSS日付に変換<br>
	 * 以下のフォーマットは受入できる<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * 「/」、「-」は自動消す処理あり
	 * 
	 * @param strDate 対象文字
	 * @return 変換後文字
	 * @throws ParseException
	 */
	public static Date toDate(String strDate) throws ParseException {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}

		String str = strDate.replaceAll("/", "");
		str = str.replaceAll("T", ""); // 英文Time記号
		str = str.replaceAll("-", "");
		str = str.replaceAll(":", "");
		str = str.replaceAll(" ", "");

		int size = str.length();
		switch (size) {
		case 2: // yy
			str = "20" + str + "0101000000.000";
			break;

		case 4: // yyyy
			str += "0101000000.000";
			break;

		case 6: // yyyyMM
			str += "01000000.000";
			break;

		case 8: // yyyyMMdd
			str += "000000.000";
			break;

		case 12: // yyyyMMddHHmm
			str += "00.000";
			break;

		case 14: // yyyyMMddHHmmss
			str += ".000";
			break;

		case 18: // yyyyMMddHHmmss.SSS
			break;

		default:
			throw new ParseException("Unparseable date: \"" + strDate + "\"", 0);
		}

		Date date = DATE_FORMAT_YMDHMSSSS_NO_SLASH.parse(str);
		return date;
	}

	/**
	 * 指定の文字列を日付に変換出来るかどうか<br>
	 * 以下のフォーマットは受入できる<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * 「/」、「-」は自動消す処理あり
	 * 
	 * @param strDate 対象文字
	 * @return true:日付に変換できる、false:日付に変換できない
	 */
	public static boolean verify(String strDate) {
		try {
			toDate(strDate);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 月の略語を返す ex1: 日本語 1月、2月... ex2: 英語 Jun、Feb...
	 * 
	 * @param lang 言語コード
	 * @param month
	 * @return 月単語
	 */
	public static String getMonthShortWord(String lang, int month) {
		Date dt = getDate(2000, month, 1);

		String f = getFormatString(lang, "MMM");
		Locale locale = new Locale(lang);
		SimpleDateFormat formatter = new SimpleDateFormat(f, locale);

		return formatter.format(dt.getTime());
	}

	/**
	 * 月単語を返す ex1: 日本語 1月、2月... ex2: 英語 Junuary、February...
	 * 
	 * @param lang 言語コード
	 * @param month
	 * @return 月単語
	 */
	public static String getMonthWord(String lang, int month) {
		Date dt = getDate(2000, month, 1);

		String f = getFormatString(lang, "MMMMM");
		Locale locale = new Locale(lang);
		SimpleDateFormat formatter = new SimpleDateFormat(f, locale);

		return formatter.format(dt.getTime());
	}

	/**
	 * 小さな日付の取得
	 * 
	 * @param d1
	 * @param d2
	 * @return 小さな日付
	 */
	public static Date min(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}

		if (Util.isSmallerThenByYMD(d1, d2)) {
			return d1;
		} else {
			return d2;
		}
	}

	/**
	 * 大きな日付の取得
	 * 
	 * @param d1
	 * @param d2
	 * @return 大きな日付
	 */
	public static Date max(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}

		if (Util.isSmallerThenByYMD(d1, d2)) {
			return d2;
		} else {
			return d1;
		}
	}

	/**
	 * 小さな日時の取得
	 * 
	 * @param d1
	 * @param d2
	 * @return 小さな日時
	 */
	public static Date minYMDHMS(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}

		if (Util.isSmallerThenByYMDHMS(d1, d2)) {
			return d1;
		} else {
			return d2;
		}
	}

	/**
	 * 大きな日時の取得
	 * 
	 * @param d1
	 * @param d2
	 * @return 大きな日時
	 */
	public static Date maxYMDHMS(Date d1, Date d2) {
		if (d1 == null) {
			return d2;
		}
		if (d2 == null) {
			return d1;
		}

		if (Util.isSmallerThenByYMDHMS(d1, d2)) {
			return d2;
		} else {
			return d1;
		}
	}

	/**
	 * VB日数カウントの取得
	 * 
	 * @param from
	 * @param to
	 * @return 日数カウント
	 */
	public static BigDecimal getVBDayCount(Date from, Date to) {
		long ticksFrom = getTicks(from);
		long ticksTo = getTicks(to);

		BigDecimal val = ticksToOADate(ticksTo).subtract(ticksToOADate(ticksFrom));

		int maxDecimalPoint = 15;
		int intLength = DecimalUtil.getIntLength(val);

		BigDecimal diff = DecimalUtil.roundNum(val, maxDecimalPoint - intLength);
		return diff;
	}

	/**
	 * VBAのOA日付計算
	 * 
	 * @param ticks
	 * @return OLE Auto Date
	 */
	public static BigDecimal ticksToOADate(long ticks) {
		if (ticks == 0L) {
			return BigDecimal.ZERO;
		}
		if (ticks < 864000000000L) {
			ticks += 599264352000000000L;
		}
		if (ticks < 31241376000000000L) {
			throw new TRuntimeException("ticks overflow." + ticks);
		}
		long num = (ticks - 599264352000000000L) / 10000L;
		if (num < 0L) {
			long num2 = num % 86400000L;
			if (num2 != 0L) {
				num -= (86400000L + num2) * 2L;
			}
		}

		return DecimalUtil.truncateNum(new BigDecimal(num / 86400000.0), 20);
	}

	/**
	 * VBのTicks取得
	 * 
	 * @param dt
	 * @return ticks
	 */
	public static long getTicks(Date dt) {
		return getTicks(dt, 0);
	}

	/**
	 * VBのTicks取得
	 * 
	 * @param dt
	 * @param i 0:DateTime, 1:Time, 2:Date
	 * @return ticks
	 */
	public static long getTicks(Date dt, int i) {
		if (i == 0) {
			return dateToTicks(DateUtil.getYear(dt), DateUtil.getMonth(dt), DateUtil.getDay(dt))
					+ timeToTicks(DateUtil.getHour(dt), DateUtil.getMinute(dt), DateUtil.getSecond(dt));
		} else if (i == 1) {
			return timeToTicks(DateUtil.getHour(dt), DateUtil.getMinute(dt), DateUtil.getSecond(dt));
		} else {
			return dateToTicks(DateUtil.getYear(dt), DateUtil.getMonth(dt), DateUtil.getDay(dt));
		}
	}

	/**
	 * 日付からVBのTicks取得
	 * 
	 * @param year
	 * @param month
	 * @param day
	 * @return Tick
	 */
	public static long dateToTicks(int year, int month, int day) {
		if (((year >= 1) && (year <= 9999)) && ((month >= 1) && (month <= 12))) {
			int[] numArray = isLeapYear(year) ? DaysToMonth366 : DaysToMonth365;
			if ((day >= 1) && (day <= (numArray[month] - numArray[month - 1]))) {
				int num = year - 1;
				int num2 = ((((((num * 365) + (num / 4)) - (num / 100)) + (num / 400)) + numArray[month - 1]) + day)
						- 1;
				return (num2 * 864000000000L);
			}
		}
		throw new TRuntimeException("date overflow." + year + "," + month + "," + day);
	}

	/**
	 * 時刻からVBのTicks取得
	 * 
	 * @param hour
	 * @param minute
	 * @param second
	 * @return tick
	 */
	public static long timeToTicks(int hour, int minute, int second) {
		long num = ((hour * 3600L) + (minute * 60L)) + second;
		if ((num > 922337203685L) || (num < -922337203685L)) {
			throw new TRuntimeException("time overflow." + hour + "," + minute + "," + second);
		}
		return (num * 10000000L);
	}

	/**
	 * 閏年判定
	 * 
	 * @param year
	 * @return true:閏年
	 */
	public static boolean isLeapYear(int year) {
		if ((year < 1) || (year > 9999)) {
			throw new TRuntimeException("year overflow." + year);
		}
		if ((year % 4) != 0) {
			return false;
		}
		if ((year % 100) == 0) {
			return ((year % 400) == 0);
		}
		return true;
	}

	/**
	 * 日付形式YMDを文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toYMDShortString(Date dt) {
		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_YMD_SHORT) {
			return DATE_FORMAT_YMD_SHORT.format(dt);
		}
	}

	/**
	 * 日付形式YMD2を文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toYMD2String(Date dt) {
		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_YMD2) {
			return DATE_FORMAT_YMD2.format(dt);
		}
	}

	/**
	 * 日付形式MDYを文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toMDYString(Date dt) {
		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_MDY) {
			return DATE_FORMAT_MDY.format(dt);
		}
	}

	/**
	 * 日付形式M.Dを文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toMDDotString(Date dt) {
		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_MD_DOT) {
			return DATE_FORMAT_MD_DOT.format(dt);
		}
	}

	/**
	 * 日付形式FullYMDを文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toFullDMYString(Date dt) {
		if (Util.isNullOrEmpty(dt)) {
			return "";
		} else {
			int dayOfMonth = DateUtil.getDay(dt);
			StringBuilder sb = new StringBuilder();
			sb.append(dayOfMonth);
			switch (dayOfMonth) {
			case 1:
			case 21:
			case 31:
				sb.append("st");
				break;
			case 2:
			case 22:
				sb.append("nd");
				break;
			case 3:
			case 23:
				sb.append("rd");
				break;
			default:
				sb.append("th");
			}

			synchronized (DATE_FORMAT_EN_YM3) {
				sb.append(" ");
				sb.append(DATE_FORMAT_EN_YM3.format(dt));
			}

			return sb.toString();
		}
	}

	/**
	 * 数値（YYYYMM）を日付に変換
	 * 
	 * @param number
	 * @return Date
	 */
	public static Date convertIntToDate(int number) {
		return convertIntToDate(String.valueOf(number));
	}

	/**
	 * 文字列（YYYYMM）を日付に変換する
	 * 
	 * @param str
	 * @return Date
	 */
	public static Date convertIntToDate(String str) {
		try {
			return DateUtil.toYMDDateYYYYMMString(str);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日付を整数に変換
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYMPlainString(date));
	}

	/**
	 * 日付を整数に変換 (YMD)
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateYMDToInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYMDPlainString(date));
	}

	/**
	 * 日時を組み合わせる <br>
	 * Ex.)<br>
	 * <p>
	 * combineDateTime(new Date(), "23:45")<br>
	 * </p>
	 * 
	 * @param date
	 * @param time
	 * @return Date
	 */
	public static Date combineDateTime(Date date, String time) {

		Date result = Util.isNullOrEmpty(date) ? new Date() : date;

		try {

			if (!Util.isNullOrEmpty(time)) {
				result = DateUtil.toYMDHMDate(DateUtil.toYMDString(result) + " " + time);
			}

		} catch (ParseException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 日付形式HHmmssを文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toHHMMSSPlainString(Date dt) {

		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (TIME_FORMAT_HMS_NO_SLASH) {
			return TIME_FORMAT_HMS_NO_SLASH.format(dt);
		}
	}

	/**
	 * yyyy年M月d日を文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toJPYMDShortString(Date dt) {

		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_JP_YMD_SHORT) {
			return DATE_FORMAT_JP_YMD_SHORT.format(dt);
		}
	}

	/**
	 * yyyy年M月d日を文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toJPMDShortString(Date dt) {

		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_JP_MD_SHORT) {
			return DATE_FORMAT_JP_MD_SHORT.format(dt);
		}
	}

	/**
	 * yyyy年M月d日 HH時 mm分を文字列に変換する
	 * 
	 * @param dt
	 * @return String
	 */
	public static String toJPYMDHMPlainString(Date dt) {

		if (Util.isNullOrEmpty(dt)) {
			return "";
		}

		synchronized (DATE_FORMAT_JP_YMDHM) {
			return DATE_FORMAT_JP_YMDHM.format(dt);
		}
	}

	/**
	 * 日付を年の整数に変換
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToYearInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYString(date));
	}

	/**
	 * 日付を月の整数に変換
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToMonthInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toMMString(date));
	}
}

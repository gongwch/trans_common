package jp.co.ais.trans.common.util;

import java.awt.Color;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

import jp.co.ais.trans.common.log.ServerLogger;

/**
 * 標準ユーティリティ
 */
public class Util {

	/** Null文字(null) */
	public static final String NULL_STRING1 = "(null)";

	/** Null文字 NULL */
	public static final String NULL_STRING2 = "NULL";

	/** エスケープ文字 */
	public static final String ESCAPE_STRING = "/";

	/**
	 * Null,空文字チェック
	 * 
	 * @param obj 対象値
	 * @return Nullまたは空文字の場合、True
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		return String.valueOf(obj).trim().length() == 0;
	}

	/**
	 * Null値回避.(trimあり)<br>
	 * Null値の場合は空白を返す.
	 * 
	 * @param obj 対象値
	 * @return Nullの場合、空文字
	 */
	public static String avoidNull(Object obj) {
		// Null値の場合は空白を返す
		return (obj == null) ? "" : String.valueOf(obj).trim();
	}

	/**
	 * Null値回避. (trimなし)<br>
	 * Null値の場合は空白を返す.
	 * 
	 * @param obj 対象値
	 * @return Nullの場合、空文字
	 */
	public static String avoidNullNT(Object obj) {
		// Null値の場合は空白を返す
		return (obj == null) ? "" : String.valueOf(obj);
	}

	/**
	 * Null値回避. 指定のオブジェクト値をintに変換して返す.<br>
	 * Null値、または空文字の場合は0を返す.
	 * 
	 * @param obj 対象値
	 * @return int値. Null、空文字の場合0
	 */
	public static int avoidNullAsInt(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return 0;
		}

		return Integer.parseInt(String.valueOf(obj));
	}

	/**
	 * Null値回避. 指定のオブジェクト値をFloatに変換して返す. <br>
	 * Null値、または空文字の場合は0を返す.
	 * 
	 * @param obj 対象値
	 * @return Float値. Null、空文字の場合0
	 */
	public static float avoidNullAsFloat(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return 0;
		}

		return Float.parseFloat(String.valueOf(obj));
	}

	/**
	 * 空文字の場合、nullに変換して返す. <br>
	 * 空文字でない場合は、そのまま文字列変換して返す.
	 * 
	 * @param obj 対象オブジェクト
	 * @return null、または、文字列
	 */
	public static String emptyToNull(Object obj) {
		return isNullOrEmpty(obj) ? null : String.valueOf(obj);
	}

	/**
	 * nullの場合、"(null)"を返す.
	 * 
	 * @param obj 対象オブジェクト
	 * @return 変換された文字列
	 */
	public static String safeNull(Object obj) {

		return (obj == null) ? NULL_STRING1 : String.valueOf(obj);
	}

	/**
	 * nullの文字列表現"(null)"であるかどうかの判定.<br>
	 * "(null)"文字列である場合、trueを返す.
	 * 
	 * @param str 対象文字列
	 * @return true:null表現文字列
	 */
	public static boolean isNullString(String str) {
		return (str == null) ? false : NULL_STRING1.equals(str);
	}

	/**
	 * システム日付を取得する
	 * 
	 * @return システム日付
	 */
	public static Date getCurrentDate() {
		return new GregorianCalendar().getTime();
	}

	/**
	 * システム日付を取得する
	 * 
	 * @return システム日付
	 */
	public static Date getCurrentYMDDate() {
		return DateUtil.toYMDDate(getCurrentDate());
	}

	/**
	 * システム日付文字列をyyyy/MM/dd HH:mm:ss形式で取得する
	 * 
	 * @return システム日付文字列
	 */
	public static String getCurrentDateString() {
		return DateUtil.toYMDHMSString(getCurrentDate());
	}

	/**
	 * システム日付文字列をyyyy/MM/dd HH:mm:ss形式で取得する
	 * 
	 * @param lang 言語コード
	 * @return システム日付文字列
	 */
	public static String getCurrentDateString(String lang) {

		return DateUtil.toYMDHMSString(lang, getCurrentDate());
	}

	/**
	 * 開始 ≦ 終了 比較.<br>
	 * 文字列の大小比較は、Javaの単純Compare.
	 * 
	 * @param begin 開始文字
	 * @param end 終了文字
	 * @return 開始文字が終了文字より小さい、または同じであればtrue
	 */
	public static boolean isSmallerThen(String begin, String end) {

		if (isNullOrEmpty(begin) || isNullOrEmpty(end)) {
			return true;
		}

		return begin.compareTo(end) <= 0;
	}

	/**
	 * 開始日付 ≦ 終了日付 比較.<br>
	 * <b>null日付の場合、false</b>
	 * 
	 * @param begin 開始日付
	 * @param end 終了日付
	 * @return 開始日付が終了日付より前、または同じであればtrue
	 */
	public static boolean isSmallerThenByYMDNVL(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		return isSmallerThenByYMD(begin, end);
	}

	/**
	 * 開始日付 ≦ 終了日付 比較.
	 * 
	 * @param begin 開始日付
	 * @param end 終了日付
	 * @return 開始日付が終了日付より前、または同じであればtrue
	 */
	public static boolean isSmallerThenByYMD(Date begin, Date end) {

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(begin);
		bcal.set(Calendar.HOUR_OF_DAY, 0);
		bcal.set(Calendar.MINUTE, 0);
		bcal.set(Calendar.SECOND, 0);
		bcal.set(Calendar.MILLISECOND, 0);

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(end);
		ecal.set(Calendar.HOUR_OF_DAY, 0);
		ecal.set(Calendar.MINUTE, 0);
		ecal.set(Calendar.SECOND, 0);
		ecal.set(Calendar.MILLISECOND, 0);

		return bcal.before(ecal) || bcal.equals(ecal);
	}

	/**
	 * 開始日時 ≦ 終了日時 比較.
	 * 
	 * @param begin 開始日時
	 * @param end 終了日時
	 * @return 開始日時が終了日時より前、または同じであればtrue
	 */
	public static boolean isSmallerThenByYMDHMS(Date begin, Date end) {

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(begin);

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(end);

		return bcal.before(ecal) || bcal.equals(ecal);
	}

	/**
	 * 日付1と日付2が同じかどうかの判定.
	 * 
	 * @param date1 日付1
	 * @param date2 日付2
	 * @return 同一である場合true
	 */
	public static boolean equalsByYMD(Date date1, Date date2) {
		if (date1 == null || date2 == null) {
			return date1 == null && date2 == null;
		}

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(date1);
		bcal.set(Calendar.HOUR_OF_DAY, 0);
		bcal.set(Calendar.MINUTE, 0);
		bcal.set(Calendar.SECOND, 0);
		bcal.set(Calendar.MILLISECOND, 0);

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(date2);
		ecal.set(Calendar.HOUR_OF_DAY, 0);
		ecal.set(Calendar.MINUTE, 0);
		ecal.set(Calendar.SECOND, 0);
		ecal.set(Calendar.MILLISECOND, 0);

		return bcal.equals(ecal);
	}

	/**
	 * 10進数のカラーコード（RGB）に変換
	 * 
	 * @param code 16進数のカラーコード(#FFFFFF)
	 * @return R、G、B数値配列
	 */
	public static int[] toRGBColorCode(String code) {
		int[] colorRGB = new int[3];
		if (code == null || code.length() < 7) {
			colorRGB[0] = 0;
			colorRGB[1] = 0;
			colorRGB[2] = 0;
		} else {
			colorRGB[0] = Integer.parseInt(code.substring(1, 3), 16);
			colorRGB[1] = Integer.parseInt(code.substring(3, 5), 16);
			colorRGB[2] = Integer.parseInt(code.substring(5, 7), 16);
		}
		return colorRGB;
	}

	/**
	 * 16進数のカラーコード（#FFFFFF）に変換
	 * 
	 * @param color カラー
	 * @return 16進数カラーコード（#FFFFFF）
	 */
	public static String to16RGBColorCode(Color color) {
		int[] rbg = { color.getRed(), color.getGreen(), color.getBlue() };
		return to16RGBColorCode(rbg);
	}

	/**
	 * 16進数のカラーコード（#FFFFFF）に変換
	 * 
	 * @param rgb RGBコード
	 * @return 16進数カラーコード（#FFFFFF）
	 */
	public static String to16RGBColorCode(int[] rgb) {
		String code = "#";
		String rcode = Integer.toHexString(rgb[0]);
		String gcode = Integer.toHexString(rgb[1]);
		String bcode = Integer.toHexString(rgb[2]);

		code += (rcode.length() > 1 ? "" : "0") + rcode;
		code += (gcode.length() > 1 ? "" : "0") + gcode;
		code += (bcode.length() > 1 ? "" : "0") + bcode;

		return code;
	}

	/**
	 * 金額オーバー判定(標準用桁数-17,4)
	 * 
	 * @param amount 金額
	 * @return 金額オーバーの場合はTRUE
	 */
	public static boolean isOverAmount(String amount) {

		return isOverAmount(amount, 17, 4);
	}

	/**
	 * 金額オーバー判定(桁数指定)
	 * 
	 * @param amount 金額
	 * @param maxLen 最大桁数
	 * @param digit 小数部桁数
	 * @return 金額オーバーの場合はTRUE
	 */
	public static boolean isOverAmount(String amount, int maxLen, int digit) {
		//TODO:数字以外の文字が含まれている場合
		amount = avoidNull(amount).replace(",", "");


		int lenInt = 0; // 整数部桁数
		int lenDec = 0; // 小数部桁数

		String[] amountArrey = amount.split("\\.");

		for (int i = 0; i < amountArrey.length; i++) {
			if (i == 0) {
				lenInt = amountArrey[i].length();
			}

			if (i == 1) {
				lenDec = amountArrey[i].length();
			}
		}

		// 整数部は最大桁数 - 小数部桁数
		if (lenInt > maxLen - digit) {
			return true;
		}

		// 小数部は最大digit桁
		if (lenDec > digit) {
			return true;
		}

		// 最大桁数
		if (lenInt + lenDec > maxLen) {
			return true;
		}

		return false;
	}

	/**
	 * 指定された文字列が数値に変換かどうかの判断を行う
	 * 
	 * @param value 文字列
	 * @return 数値に変換可能な場合true
	 */
	public static boolean isNumber(String value) {

		try {
			// 数値かどうかをFormatExceptionで判断
			new BigDecimal(value);

			return true;

		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * <s> 指定された文字列が日付に変換可能かどうかの判断を行う</s> <br>
	 * <br>
	 * 以下を利用すること<br>
	 * {@link DateUtil#isYMDDate(String)}
	 * 
	 * @param value 文字列
	 * @return 日付に変換可能な場合true
	 * @deprecated DateUtilを利用すること
	 */
	public static boolean isDate(String value) {

		try {
			// 日付かどうかをFormatExceptionで判断
			DateUtil.toYMDDate(value);

			return true;

		} catch (ParseException ex) {
			// 数値に変換できなかった場合
			return false;
		}
	}

	/**
	 * 対象月が何月度に当たるか
	 * 
	 * @param initialMonth 期首月
	 * @param targetMonth 対象月
	 * @return 月度
	 */
	public static int getFiscalMonth(int initialMonth, int targetMonth) {
		if (targetMonth >= initialMonth) {
			return targetMonth - initialMonth + 1;
		} else {
			return targetMonth + 12 - initialMonth + 1;
		}
	}

	/**
	 * 指定言語コードが略語対象(英語)かどうかを判定する
	 * 
	 * @param langCode 言語コード
	 * @return true:略語対象
	 */
	public static boolean isShortLanguage(String langCode) {

		// 現状は英語のみ
		return Locale.ENGLISH.getLanguage().equals(langCode);
	}

	/**
	 * 例外のStackTrace文字列表現
	 * 
	 * @param e 対象エラー
	 * @return StackTrace文字列表現
	 */
	public static String makeStackString(Throwable e) {
		StringBuilder buff = new StringBuilder(Util.avoidNull(e.getMessage()));
		buff.append("\r\n").append(e.getClass().getName());

		StackTraceElement[] els = e.getStackTrace();
		for (StackTraceElement el : els) {
			buff.append("\r\n").append("  at ").append(el.getClassName());
			buff.append(".").append(el.getMethodName());
			buff.append("(").append(el.getFileName());
			buff.append(":").append(el.getLineNumber()).append(")");
		}

		return buff.toString();
	}

	/**
	 * null→空文字変換してから比較
	 * 
	 * @param value1 値1
	 * @param value2 値2
	 * @return true:同一
	 */
	public static boolean equals(String value1, String value2) {
		return Util.avoidNull(value1).equals(Util.avoidNull(value2));
	}

	/**
	 * 色変換<br>
	 * 255,255,255⇒Color
	 * 
	 * @param abg RBG
	 * @return 色
	 */
	public static Color toColor(String[] abg) {
		int color0 = Integer.parseInt(abg[0]);
		int color1 = Integer.parseInt(abg[1]);
		int color2 = Integer.parseInt(abg[2]);

		return new Color(color0, color1, color2);
	}

	/**
	 * 16進法カラー値をRGBへ転換する <br>
	 * #FFFFFF⇒255,255,255のカラー
	 * 
	 * @param menuColor
	 * @return color RGBカラー
	 */
	public static Color convertToRGB(String menuColor) {

		if (menuColor == null || menuColor.isEmpty()) {
			return null;
		}

		String r = menuColor.substring(1, 3);
		int red = Integer.parseInt(r, 16);
		String g = menuColor.substring(3, 5);
		int green = Integer.parseInt(g, 16);
		String b = menuColor.substring(5, 7);
		int blue = Integer.parseInt(b, 16);
		Color color = new Color(red, green, blue);
		return color;
	}

	/**
	 * システムバージョン情報を取得する
	 * 
	 * @param clazz ライブラリ検索用元クラス
	 * @return 0:最終更新日時、1:バージョン
	 */
	public static String[] getSystemVersion(Class clazz) {
		String[] values = new String[2];

		try {
			// カレントディレクトリにあるjarファイルを指定
			URL url = clazz.getResource(clazz.getSimpleName() + ".class");
			String form = url.toExternalForm();

			// jarファイル名
			String jar = form.substring(0, form.lastIndexOf(clazz.getPackage().getName().replace('.', '/')));

			URLConnection con = new URL(jar).openConnection();

			// ローカル(開発)の場合
			if (con == null || !(con instanceof JarURLConnection)) {
				values[0] = "local";
				values[1] = "local";

				return values;
			}

			JarURLConnection jarConnection = (JarURLConnection) con;

			// 最終更新日時、JARファイルから取得
			Date date = new Date(jarConnection.getLastModified());
			values[0] = DateUtil.toYMDHMString(date);

			// バージョン、マニフェストから取得
			Manifest manifest = jarConnection.getManifest();
			Attributes att = manifest.getMainAttributes();
			values[1] = att.getValue("Specification-Version");

		} catch (Exception ex) {
			ServerLogger.error("jar read error.", ex);
			values[0] = "err";
			values[1] = "err";
		}

		return values;
	}

	/**
	 * @param a
	 * @param b
	 * @return NVL処理
	 */
	public static String nvl(String a, String b) {
		if (Util.isNullOrEmpty(a)) {
			return Util.avoidNull(b);
		}
		return a;
	}
}

package jp.co.ais.trans.common.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.apache.commons.text.*;

/**
 * 文字操作系ユーティリティ
 */
public final class StringUtil {

	/** 文字コード種類 JIS */
	public final static String CHARSET_KIND_JIS = "ISO2022JP";

	/** 文字コード種類 SJIS */
	public final static String CHARSET_KIND_SJIS = "Shift_JIS";

	/** 文字コード種類 EUC */
	public final static String CHARSET_KIND_EUC = "EUC_JP";

	/** 文字コード種類 UTF8 */
	public final static String CHARSET_KIND_UTF8 = "UTF-8";

	/** 文字コード種類 UTF16 */
	public final static String CHARSET_KIND_UTF16 = "UTF-16";

	/** 文字コード種類 ISO8859-1 */
	public final static String CHARSET_KIND_ISO8859_1 = "ISO-8859-1";

	/** 配列区切り表現文字(オリジナル) */
	public static final String DELIMITER = "<>";

	/** 改行表現文字(オリジナル) */
	public static final String LINESEPARATOR = "<LINESEP>";

	/** 半角スペース */
	public static final String SINGLE_BYTE_SPACE = " ";

	/** カンマ */
	public static final String COMMA = ",";

	/** 大文字英字かどうかの正規表現パターン */
	public static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z| ]+");

	/** 小文字英字かどうかの正規表現パターン */
	public static final Pattern PATTERN_LOWER = Pattern.compile("[a-z| ]+");

	/** 半角カナのみかどうかの正規表現パターン */
	public static final Pattern PATTERN_KANA = Pattern.compile("^[\\uFF65-\\uFF9F]+$");

	/**
	 * 入力文字列を指定した区切り文字で分割した文字列配列を返します。<br>
	 * 長さ０の文字列を入力文字列として渡された場合は 長さ０の文字列の要素を１つ持つ配列を返します。<br>
	 * nullを入力文字列として渡された場合はサイズ０の配列を返します。<br>
	 * 区切り文字にnullもしくは長さ０の文字列を指定した場合は、 入力文字列をそのまま返します。<br>
	 * <br>
	 * タブ(\t)で分割 <br>
	 * StringUtil.split(&quot;a\tb\tc&quot;,&quot;\t&quot;); <br>
	 * 戻り値：{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}
	 * 
	 * @param s 入力文字列
	 * @param delimiter 区切り文字(正規表現に対応していません)
	 * @return 区切り文字で分割した結果の文字列配列
	 */
	public static String[] split(String s, String delimiter) {

		if (Util.isNullOrEmpty(s)) {
			return new String[0];
		}

		if (Util.isNullOrEmpty(delimiter)) {
			return new String[] { s };
		}

		List<String> l = new LinkedList<String>();
		int pos = 0;
		int delpos = 0;
		while ((delpos = s.indexOf(delimiter, pos)) != -1) {
			l.add(s.substring(pos, delpos));
			pos = delpos + delimiter.length();
		}
		if (pos <= s.length()) {
			l.add(s.substring(pos));
		}

		return l.toArray(new String[l.size()]);

	}

	/**
	 * 入力文字列を指定した区切り文字で分割した指定上限数の要素数文字列配列として返します。<br>
	 * 先頭から分割します。<br>
	 * 長さ０の文字列を入力文字列として渡された場合は 長さ０の文字列の要素を１つ持つ配列を返します。<br>
	 * nullを入力文字列として渡された場合はサイズ０の配列を返します。<br>
	 * 区切り文字にnullもしくは長さ０の文字列を指定した場合は、 入力文字列をそのまま返します。<br>
	 * <br>
	 * タブ(\t)で分割 <br>
	 * StringUtil.split(&quot;a\tb\tc&quot;,&quot;\t&quot;); <br>
	 * 戻り値：{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}
	 * 
	 * @param s 入力文字列
	 * @param delimiter 区切り文字(正規表現に対応していません)
	 * @param num 分割数
	 * @return 区切り文字で分割した結果の文字列配列
	 */
	public static String[] split(String s, String delimiter, int num) {

		if (Util.isNullOrEmpty(s) || num <= 0) {
			return new String[0];
		}

		if (Util.isNullOrEmpty(delimiter)) {
			return new String[] { s };
		}

		List<String> l = new LinkedList<String>();
		int pos = 0;
		int delpos = 0;
		for (int cnt = 0; (delpos = s.indexOf(delimiter, pos)) != -1 && cnt < num - 1; cnt += 1) {
			l.add(s.substring(pos, delpos));
			pos = delpos + delimiter.length();
		}
		if (pos <= s.length()) {
			l.add(s.substring(pos));
		}

		return l.toArray(new String[l.size()]);
	}

	/**
	 * HTML用エスケープ文字変換
	 * 
	 * @param str 対象文字
	 * @return エスケープ文字
	 */
	public static String escapeHtml(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}
		String escapeStr = StringEscapeUtils.escapeHtml4(str);
		escapeStr = StringEscapeUtils.escapeJava(escapeStr);
		// 改行コードを変換
		return escapeStr;
	}

	/**
	 * HTML用エスケープ文字逆変換
	 * 
	 * @param str エスケープ文字
	 * @return 通常文字
	 */
	public static String unescapeHtml(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}
		str = StringEscapeUtils.unescapeJava(str);
		return StringEscapeUtils.unescapeHtml4(str);
	}

	/**
	 * 文字間に半角スペースを挿入する
	 * 
	 * @param str 対象文字
	 * @return スペースが入った文字列
	 */
	public static String addSpace(String str) {
		return addCharacter(str, SINGLE_BYTE_SPACE);
	}

	/**
	 * 文字間に指定文字を挿入する
	 * 
	 * @param str 対象文字
	 * @param addStr 挿入文字
	 * @return 挿入文字が入った文字列
	 */
	public static String addCharacter(String str, String addStr) {
		StringBuilder buff = new StringBuilder();

		char[] chars = str.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char chr = chars[i];
			buff.append(chr);
			if (chr != ' ' && i != (chars.length - 1)) {
				buff.append(addStr);
			}
		}

		return buff.toString().trim();
	}

	/**
	 * 指定文字が全て英字大文字であるかどうかを判定する. <br>
	 * スペースは有効文字.<br>
	 * スペースのみは無効.<br>
	 * ex1) isUpperCase → false <br>
	 * ex2) IS UPPER CASE → true
	 * 
	 * @param str 対象文字
	 * @return true:英字大文字のみ
	 */
	public static boolean isUpperCase(String str) {
		if (Util.isNullOrEmpty(str)) {
			return false;
		}

		synchronized (PATTERN_UPPER) {
			return PATTERN_UPPER.matcher(str).matches();
		}
	}

	/**
	 * 指定文字が全て英字小文字であるかどうかを判定する. <br>
	 * スペースは有効文字.<br>
	 * スペースのみは無効.<br>
	 * ex1) isUpperCase → false <br>
	 * ex2) is lower case → true<br>
	 * 
	 * @param str 対象文字
	 * @return true:英字小文字のみ
	 */
	public static boolean isLowerCase(String str) {
		if (Util.isNullOrEmpty(str)) {
			return false;
		}

		synchronized (PATTERN_LOWER) {
			return PATTERN_LOWER.matcher(str).matches();
		}
	}

	/**
	 * 指定文字がすべて半角カナ文字であるかどうか判断する<br>
	 * 空文字はTrue
	 * 
	 * @param str
	 * @return true:半角カナのみ
	 */
	public static boolean isHalfKana(String str) {
		if (Util.isNullOrEmpty(str)) {
			return true;
		}
		synchronized (PATTERN_KANA) {
			return PATTERN_KANA.matcher(str).matches();
		}
	}

	/**
	 * 小文字→大文字 変換を行い、単語間の接合部分にスペースを挿入する. <br>
	 * ex) toUpperCase → TO UPPER CASE <br>
	 * <br>
	 * 全て大文字の場合は、そのまま返す. <br>
	 * ex) TO UPPER CASE → TO UPPER CASE
	 * 
	 * @param str 対象文字
	 * @return 変換文字
	 */
	public static String toUpperCase(String str) {

		// 全て大文字の場合は、そのまま返す
		boolean isAllUpper = true;
		for (char chr : str.toCharArray()) {
			if (chr != ' ') {
				isAllUpper &= Character.isUpperCase(chr);
			}
		}
		if (isAllUpper) {
			return str;
		}

		// 変換
		StringBuilder buff = new StringBuilder();

		char[] chars = str.toCharArray();
		for (char chr : chars) {
			// 大文字の場合、その前にスペースを挿入
			if (Character.isUpperCase(chr)) {
				buff.append(SINGLE_BYTE_SPACE);
			}
			buff.append(chr);
		}

		return buff.toString().toUpperCase();
	}

	/**
	 * 大文字→小文字 変換を行い、スペースを排除して各単語の先頭を大文字とする.<br>
	 * ex) TO LOWER CASE → toLowerCase <br>
	 * <br>
	 * スペースを含まない場合は、そのまま返す. <br>
	 * ex) toLowerCase → toLowerCase
	 * 
	 * @param str 対象文字
	 * @return 変換文字
	 */
	public static String toLowerCase(String str) {

		// スペースを含まない場合は、そのまま返す.
		boolean hasSpace = false;
		for (char chr : str.toCharArray()) {
			hasSpace |= (chr == ' ');
		}
		if (!hasSpace) {
			return str;
		}

		// 変換
		StringBuilder buff = new StringBuilder();

		// 一度、小文字に変換
		str = str.toLowerCase();

		boolean flag = false;
		char[] chars = str.toCharArray();
		for (char chr : chars) {
			if (chr == ' ') {
				flag = true;
			} else {
				// 前の文字がスペースの場合、次の文字を大文字にして追加
				String s = String.valueOf(chr);
				buff.append(flag ? s.toUpperCase() : s);
				flag = false;
			}
		}

		return buff.toString();
	}

	/**
	 * 指定したバイト数毎に、対象文字列に改行を加える
	 * 
	 * @param maxLength 改行指定バイト数
	 * @param str 対象文字列
	 * @return 改行を加えた対象文字列
	 */
	public static String appendLineSeparator(int maxLength, String str) {
		// 切り取った文字列格納バッファ
		StringBuffer apendSb = new StringBuffer();
		String strSpace = " ";
		int byteCnt = 0;
		for (int i = 0; i < str.length(); i++) {

			// Stringクラスから一文字取得してStringクラスに格納'
			String s = str.substring(i, i + 1);

			// 取得した１文字のバイト数
			byteCnt += s.getBytes().length;
			if (maxLength < byteCnt) {
				apendSb.append(strSpace + s);
				byteCnt = s.getBytes().length;
			} else {
				apendSb.append(s);
			}
		}
		return apendSb.toString();
	}

	/**
	 * 指定されたリストをカンマ区切り表現文字列に変換する.<br>
	 * toArrayByComma()でString配列へ戻す.
	 * 
	 * @param args 対象文字リスト
	 * @return カンマ区切り文字列
	 */
	public static String toCommaString(String[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(COMMA);
			}
			buff.append(Util.safeNull(args[i]));
		}

		return buff.toString();
	}

	/**
	 * 指定されたリストをカンマ区切り表現文字列に変換する.<br>
	 * 
	 * @param args 対象文字リスト
	 * @return カンマ区切り文字列
	 */
	public static String toCommaString(boolean[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(COMMA);
			}
			buff.append(String.valueOf(args[i]));
		}

		return buff.toString();
	}

	/**
	 * 指定されたリストをカンマ区切り表現文字列に変換する.<br>
	 * 
	 * @param args 対象文字リスト
	 * @return カンマ区切り文字列
	 */
	public static String toCommaString(int[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(COMMA);
			}
			buff.append(String.valueOf(args[i]));
		}

		return buff.toString();
	}

	/**
	 * 指定されたリストをカンマ区切り表現文字列に変換する.<br>
	 * 
	 * @param list 対象文字リスト
	 * @return カンマ区切り文字列
	 */
	public static String toCommaString(List list) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				buff.append(COMMA);
			}
			buff.append(String.valueOf(list.get(i)));
		}

		return buff.toString();
	}

	/**
	 * 指定されたリストをカンマ区切り表現文字列に変換する.<br>
	 * 値は「"」を囲む
	 * 
	 * @param list 対象文字リスト
	 * @return カンマ区切り文字列
	 */
	public static String toDQuoCommaString(List list) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < list.size(); i++) {
			if (i != 0) {
				buff.append(StringUtil.COMMA);
			}
			buff.append(StringUtil.addDQuo(String.valueOf(list.get(i))));
		}

		return buff.toString();
	}

	/**
	 * カンマ区切り表現文字列をString配列へ戻す. <br>
	 * toCommaString()で変換された文字列が対象.
	 * 
	 * @param str カンマ区切り文字列
	 * @return 文字配列
	 */
	public static String[] toArrayByComma(String str) {
		if (Util.isNullOrEmpty(str)) {
			return new String[0];
		}

		String[] array = StringUtil.split(str, COMMA);

		for (int i = 0; i < array.length; i++) {
			array[i] = array[i].trim();
			if (Util.NULL_STRING1.equals(array[i])) {
				array[i] = null;
			}
		}
		return array;
	}

	/**
	 * カンマ区切り表現文字列をint配列へ戻す. <br>
	 * toCommaString()で変換された文字列が対象.
	 * 
	 * @param str カンマ区切り文字列
	 * @return 文字配列
	 */
	public static int[] toIntArrayByComma(String str) {
		String[] strArray = toArrayByComma(str);
		int[] array = new int[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			array[i] = Integer.parseInt(strArray[i]);
		}

		return array;
	}

	/**
	 * リスト表現文字列を返す. <br>
	 * toArrayFromDelimitString(String)でString配列へ戻す.
	 * 
	 * @param args 対象文字リスト
	 * @return リスト表現文字列
	 */
	public static String toDelimitString(String[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(DELIMITER);
			}
			buff.append(Util.safeNull(args[i]));
		}

		return buff.toString();
	}

	/**
	 * リスト表現文字列をString配列へ戻す. <br>
	 * toDelimitString(String[])で変換された文字列が対象.
	 * 
	 * @param str リスト表現文字列
	 * @return 文字配列
	 */
	public static String[] toArrayFromDelimitString(String str) {
		if (Util.isNullOrEmpty(str)) {
			return new String[0];
		}

		String[] array = StringUtil.split(str, DELIMITER);

		for (int i = 0; i < array.length; i++) {
			if (Util.NULL_STRING1.equals(array[i])) {
				array[i] = null;
			}
		}
		return array;
	}

	/**
	 * リスト表現文字列を返す. <br>
	 * toBooleanArrayFromDelimitString(String)でboolean配列へ戻す.
	 * 
	 * @param args 対象Booleanリスト
	 * @return リスト表現文字列
	 */
	public static String toDelimitString(boolean[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(DELIMITER);
			}
			buff.append(BooleanUtil.toString(args[i]));
		}

		return buff.toString();
	}

	/**
	 * リスト表現文字列をboolean配列へ戻す. <br>
	 * toDelimitString(boolean[])で変換された文字列が対象.
	 * 
	 * @param str リスト表現文字列
	 * @return boolean配列
	 */
	public static boolean[] toBooleanArrayFromDelimitString(String str) {
		if (Util.isNullOrEmpty(str)) {
			return new boolean[0];
		}

		String[] strArray = StringUtil.split(str, DELIMITER);
		boolean[] array = new boolean[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			array[i] = BooleanUtil.toBoolean(strArray[i]);
		}

		return array;
	}

	/**
	 * リスト表現文字列を返す. <br>
	 * toIntArrayFromDelimitString(String)でint配列へ戻す.
	 * 
	 * @param args 対象intリスト
	 * @return リスト表現文字列
	 */
	public static String toDelimitString(int[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(DELIMITER);
			}
			buff.append(String.valueOf(args[i]));
		}

		return buff.toString();
	}

	/**
	 * リスト表現文字列をint配列へ戻す. <br>
	 * toDelimitString(int[])で変換された文字列が対象.
	 * 
	 * @param str リスト表現文字列
	 * @return int配列
	 */
	public static int[] toIntArrayFromDelimitString(String str) {
		if (Util.isNullOrEmpty(str)) {
			return new int[0];
		}

		String[] strArray = StringUtil.split(str, DELIMITER);
		int[] array = new int[strArray.length];

		for (int i = 0; i < strArray.length; i++) {
			array[i] = Integer.parseInt(strArray[i]);
		}

		return array;
	}

	/**
	 * 右空白削除
	 * 
	 * @param str 対象文字列
	 * @return 右空白が削除された文字列
	 */
	public static String rightTrim(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		return str.replaceAll("\\s+$", "");
	}

	/**
	 * 左空白削除
	 * 
	 * @param str 対象文字列
	 * @return 左空白が削除された文字列
	 */
	public static String leftTrim(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		return str.replaceAll("^\\s+", "");
	}

	/**
	 * バイト単位で左から文字取得<br>
	 * ※nullの場合ブランクを返す
	 * 
	 * @param str 対象文字列
	 * @param length 取得バイト数
	 * @return 指定バイト分文字列(左から)
	 */
	public static String leftBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// 切り取った文字列格納バッファ
		StringBuffer apendSb = new StringBuffer();

		int byteCnt = 0;
		for (int i = 0; i < str.length(); i++) {

			// Stringクラスから一文字取得してStringクラスに格納'
			String s = str.substring(i, i + 1);

			// 取得した１文字のバイト数
			byteCnt += s.getBytes().length;
			if (length < byteCnt) {
				break;
			}
			apendSb.append(s);
		}
		return apendSb.toString();
	}

	/**
	 * バイト単位で右から文字列取得
	 * 
	 * @param str 対象文字列
	 * @param length 取得バイト数
	 * @return 指定バイト分文字列(右から)
	 */
	public static String rightBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// 切り取った文字列格納バッファ
		StringBuffer apendSb = new StringBuffer();

		int byteCnt = 0;
		for (int i = str.length() - 1; i >= 0; i--) {

			// Stringクラスから一文字取得してStringクラスに格納'
			String s = str.substring(i, i + 1);

			// 取得した１文字のバイト数
			byteCnt += s.getBytes().length;
			if (length < byteCnt) {
				break;
			}
			// 文字を先頭に挿入
			apendSb.insert(0, s);
		}
		return apendSb.toString();
	}

	/**
	 * 指定バイト以降の文字を取得.
	 * 
	 * @param str 対象文字列
	 * @param length バイト数
	 * @return 指定Index移行の文字
	 */
	public static String afterBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		int bytes = 0;
		int index = 0;
		for (; index < str.length(); index++) {

			// Stringクラスから一文字取得
			String s = str.substring(index, index + 1);

			// 取得した１文字のバイト数
			bytes += s.getBytes().length;

			if (length < bytes) {
				break;
			}
		}

		if (bytes <= length) {
			return ""; // 指定バイトよりそもそも小さい
		}

		return str.substring(index, str.length());
	}

	/**
	 * 指定バイトから指定バイトまでの文字を取得.
	 * 
	 * @param str 対象文字列
	 * @param start 開始バイト位置
	 * @param end 終了バイト位置
	 * @return 切り取り文字
	 */
	public static String cutBX(String str, int start, int end) {
		String cut = afterBX(str, start - 1); // 指定バイトから後ろ取得
		cut = leftBX(cut, end - start + 1); // 左からバイト指定で切る
		return cut;
	}

	/**
	 * HTMLエンコードしたリスト表現文字列を返す.<br>
	 * convertStringToList()でStringリストへ戻す.
	 * 
	 * @param args 対象文字リスト
	 * @return リスト表現文字列
	 */
	public static String toHTMLEscapeString(List<String> args) {
		return toHTMLEscapeString(args.toArray(new String[args.size()]));
	}

	/**
	 * HTMLエンコードしたリスト表現文字列を返す.<br>
	 * convertStringToArray()でString配列へ戻す.
	 * 
	 * @param args 対象文字リスト
	 * @return リスト表現文字列
	 */
	public static String toHTMLEscapeString(String[] args) {
		StringBuilder buff = new StringBuilder();

		for (int i = 0; i < args.length; i++) {
			if (i != 0) {
				buff.append(DELIMITER);
			}
			buff.append(escapeHtml(Util.safeNull(args[i])));
		}

		return buff.toString();
	}

	/**
	 * HTMLエンコードされたリスト表現文字列をStringリストへ戻す. <br>
	 * convertListToString()で変換された文字列が対象.
	 * 
	 * @param str リスト表現文字列
	 * @return 文字リスト
	 */
	public static List<String> toListFromHTMLEscapeString(String str) {
		List<String> list = new LinkedList<String>();

		if (Util.isNullOrEmpty(str)) {
			return list;
		}

		String[] array = split(str, DELIMITER);

		for (int i = 0; i < array.length; i++) {
			String value = unescapeHtml(array[i]).trim();
			if (Util.NULL_STRING1.equals(value)) {
				value = null;
			}

			list.add(value);
		}

		return list;
	}

	/**
	 * HTMLエンコードされたリスト表現文字列をString配列へ戻す. <br>
	 * convertArrayToString()で変換された文字列が対象.
	 * 
	 * @param str リスト表現文字列
	 * @return 文字配列
	 */
	public static String[] toArrayFromHTMLEscapeString(String str) {
		if (Util.isNullOrEmpty(str)) {
			return new String[] { str };
		}

		String[] array = split(str, DELIMITER);

		for (int i = 0; i < array.length; i++) {
			array[i] = unescapeHtml(array[i]).trim();
			if (Util.NULL_STRING1.equals(array[i])) {
				array[i] = null;
			}
		}
		return array;
	}

	/**
	 * 文字列変換. <br>
	 * '* 機能 ：対象の文字列に"'"が含まれていたら文字列を括る"'"と区別するため"'"を重ねる '* 対象の文字列に"\"が含まれていたらESCAPEオプションの"\"と区別するため"\"を重ねる '*
	 * 対象の文字列に"_"が含まれていたらLIKEの特殊文字と区別するためESCAPEオプションの"\"をつける '* 対象の文字列に"%"が含まれていたらLIKEの特殊文字と区別するためESCAPEオプションの"\"をつける
	 * 
	 * @param str 対象文字列
	 * @return 変換文字列
	 */
	public static String convertPrm(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		String retStr = new String(str);

		// それぞれの文字列を変換
		retStr = retStr.replace("'", "''");
		retStr = retStr.replace(Util.ESCAPE_STRING, Util.ESCAPE_STRING + Util.ESCAPE_STRING);
		retStr = retStr.replace("_", Util.ESCAPE_STRING + "_");
		retStr = retStr.replace("%", Util.ESCAPE_STRING + "%");

		return retStr;
	}

	/**
	 * バイナリデータをURLエンコード化した文字列に変換する
	 * 
	 * @param bytes バイナリデータ
	 * @return 変換後文字列
	 * @throws UnsupportedEncodingException
	 */
	public static String toURLEncodeString(byte[] bytes) throws UnsupportedEncodingException {
		return URLEncoder.encode(new String(bytes, CHARSET_KIND_ISO8859_1), CHARSET_KIND_UTF8);
	}

	/**
	 * URLエンコード化文字列をバイナリデータに変換する
	 * 
	 * @param str 対象文字列
	 * @return 変換後バイナリデータ
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toURLDecodeBytes(String str) throws UnsupportedEncodingException {
		String decStr = URLDecoder.decode(str, CHARSET_KIND_UTF8);
		return decStr.getBytes(CHARSET_KIND_ISO8859_1);
	}

	/**
	 * String配列を統合したStringへ変換する
	 * 
	 * @param strs String配列
	 * @return 統合文字
	 */
	public static String toJoinString(String[] strs) {
		StringBuilder buff = new StringBuilder();
		for (String str : strs) {
			buff.append(str);
		}

		return buff.toString();
	}

	/**
	 * Stringリストを統合したStringへ変換する
	 * 
	 * @param list Stringリスト
	 * @return 統合文字
	 */
	public static String toJoinString(List<String> list) {
		StringBuilder buff = new StringBuilder();
		for (String str : list) {
			buff.append(str);
		}

		return buff.toString();
	}

	/**
	 * Stringリストを統合したStringへ変換する
	 * 
	 * @param list Stringリスト
	 * @param split
	 * @return 統合文字
	 */
	public static String join(List<String> list, String split) {
		StringBuilder buff = new StringBuilder();
		for (String str : list) {
			if (buff.length() > 0) {
				buff.append(split);
			}
			buff.append(str);
		}

		return buff.toString();
	}

	/**
	 * 指定のバイト数で切り取った文字列をリストで返します。<br>
	 * 指定文字が指定段落に満たない場合は、残りの段落にはブランクが入る.<br>
	 * 【使用例】<br>
	 * getParagraphString("あいうえおかき", 6, 4) の場合<br>
	 * 戻り値のListの要素0には６バイト分の"あいう"、要素1には"えおか", 要素2には"き"、要素3には""が入ります。<br>
	 * ["あいう", "えおか", "き", ""]
	 * 
	 * @param str 文字列
	 * @param byteNo バイト数
	 * @param paragraphNo 段数
	 * @return 指定バイト数で区切られた文字列
	 */
	public static List<String> getParagraphString(String str, int byteNo, int paragraphNo) {

		List<String> rtList = new ArrayList<String>(paragraphNo);

		String rt = Util.avoidNull(str);

		for (int i = 0; i < paragraphNo; i++) {

			// 切り取った文字を追加
			rtList.add(Util.avoidNull(StringUtil.leftBX(rt, byteNo)));

			// 切り取った後の文字をセット
			rt = StringUtil.rightBX(rt, rt.getBytes().length - StringUtil.leftBX(rt, byteNo).getBytes().length);
		}

		return rtList;
	}

	/**
	 * 指定桁数のデータ取得
	 * 
	 * @param str 指定文字列
	 * @param length 渡すのは指定のバイト数
	 * @return 指定桁数のデータ
	 */
	public static String spaceFill(String str, int length) {

		// 文字列の桁数
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// ブランクのの桁数
			int trimLenth = length - lengthStr;

			StringBuilder buff = new StringBuilder(str);
			for (int i = 0; i < trimLenth; i++) {
				buff.append(SINGLE_BYTE_SPACE);
			}

			return buff.toString();
		}

		return str;
	}

	/**
	 * 指定桁数のデータ取得<br>
	 * 例:("ABC", 5)⇒ABC&nbsp;&nbsp;
	 * 
	 * @param str 指定文字列
	 * @param length 渡すのは指定のバイト数
	 * @return 指定桁数のデータ
	 */
	public static String fillHtmlSpace(String str, int length) {

		// 文字列の桁数
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// ブランクのの桁数
			int trimLenth = length - lengthStr;

			StringBuilder buff = new StringBuilder(str);
			for (int i = 0; i < trimLenth; i++) {
				buff.append("&nbsp;");
			}

			return buff.toString();
		}

		return str;
	}

	/**
	 * 指定桁数で指定された埋め文字で埋めたデータ取得[右埋め]
	 * 
	 * @param str 指定文字列
	 * @param length 渡すのは指定のバイト数
	 * @param fillChar 埋め文字
	 * @return 指定桁数のデータ[右埋め]
	 */
	public static String fill(String str, int length, char fillChar) {

		// 文字列の桁数
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// 埋め桁数
			int trimLenth = length - lengthStr;

			StringBuilder buff = new StringBuilder(str);
			for (int i = 0; i < trimLenth; i++) {
				buff.append(fillChar);
			}

			return buff.toString();
		}

		return str;
	}

	/**
	 * 指定桁数で指定された埋め文字で埋めたデータ取得[左埋め]
	 * 
	 * @param str 指定文字列
	 * @param length 渡すのは指定のバイト数
	 * @param fillChar 埋め文字
	 * @return 指定桁数のデータ[左埋め]
	 */
	public static String fillLeft(String str, int length, char fillChar) {

		// 文字列の桁数
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// 埋め桁数
			int trimLenth = length - lengthStr;

			StringBuilder buff = new StringBuilder();
			for (int i = 0; i < trimLenth; i++) {
				buff.append(fillChar);
			}
			buff.append(str);

			return buff.toString();
		}

		return str;
	}

	/**
	 * 埋めたデータの左から指定されたトリム文字を消す
	 * 
	 * @param str 指定文字列
	 * @param trimChar トリム文字
	 * @return トリム文字を消したデータ[左トリム]
	 */
	public static String trimLeft(String str, char trimChar) {

		// 文字列の桁数
		char[] data = str.toCharArray();
		boolean endFlag = false;

		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			if (data[i] == trimChar && !endFlag) {
				continue;
			}
			endFlag = true;
			buff.append(data[i]);
		}

		return buff.toString();
	}

	/**
	 * HTMLデータを取得する
	 * 
	 * @param data データ
	 * @param horizontalAlign 水平方向表示位置
	 * @return String HTMLデータ
	 */
	public static String getHtmlString(int horizontalAlign, Object[] data) {

		String str = "";
		for (Object htmlStr : data) {
			str = str + htmlStr + "<br>";
		}

		if (SwingConstants.RIGHT == horizontalAlign) {
			// 右寄せ
			str = "<div style=\"text-align:right;white-space:nowrap;\">" + str;

		} else if (SwingConstants.CENTER == horizontalAlign) {
			// 中央寄せ
			str = "<div style=\"text-align:center;white-space:nowrap;\">" + str;
		} else {
			str = "<div style=\"text-align:left;white-space:nowrap; \">" + str;
		}
		return str = "<html>" + str + "</div></html>";
	}

	/**
	 * 全角のチェック処理
	 * 
	 * @param str チェック値
	 * @return <br>
	 *         true:全角文字が含み<br>
	 *         false:空白、NULLおよび半角カナ以外の半角英数、符号のみ
	 */
	public static boolean containsFullSize(String str) {

		// 空白およびNULLの場合、問題なし
		if (Util.isNullOrEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);

			// 全角禁止で全角が含まれている場合はNG
			if (s.getBytes().length > 1) {
				return true;
			}

			// 正規表現チェック : 半角カナはNG
			if (s.matches("[｡-ﾟ]")) {
				return true;
			}

		}

		return false;

	}

	/**
	 * 半角英数のチェック処理
	 * 
	 * @param str チェック値
	 * @return <br>
	 *         true:空白、NULLおよび半角英数のみ<br>
	 *         false:半角英数以外あり
	 */
	public static boolean isHalfCharOrNumber(String str) {

		// 空白およびNULLの場合、問題なし
		if (Util.isNullOrEmpty(str)) {
			return true;
		}

		// 半角英数以外の文字があれば、NG
		String s = str.replaceAll("[^A-Za-z0-9]", "");

		return s.equals(str);

	}

	/**
	 * ダブルクォーテーションで囲む
	 * 
	 * @param str 対象文字
	 * @return 加工後文字
	 */
	public static String addDQuo(String str) {
		return "\"" + Util.avoidNullNT(str) + "\"";
	}

	/**
	 * 指定文字数をオーバーしているかどうか<br>
	 * <b>※注意：バイト数ではない！</b>
	 * 
	 * @param str 文字
	 * @param length 文字数
	 * @return true:オーバー
	 */
	public static boolean isOver(String str, int length) {
		return Util.avoidNullNT(str).length() > length;
	}

	/**
	 * 指定文字数をオーバーしているかどうか
	 * 
	 * @param str 文字
	 * @param length 文字数
	 * @return true:オーバー
	 */
	public static boolean isBytesOver(String str, int length) {
		return Util.avoidNullNT(str).getBytes().length > length;
	}

	/**
	 * This is really a debugging version of <code>System.arraycopy()</code>. Use it to provide better exception
	 * messages when copying arrays around. For production use it's better to use the original for speed.
	 * 
	 * @param src
	 * @param src_position
	 * @param dst
	 * @param dst_position
	 * @param length
	 */
	public static void arraycopy(byte[] src, int src_position, byte[] dst, int dst_position, int length) {
		if (src_position < 0)
			throw new IllegalArgumentException("src_position was less than 0.  Actual value "
					+ src_position);
		if (src_position >= src.length)
			throw new IllegalArgumentException(
					"src_position was greater than src array size.  Tried to write starting at position " + src_position
							+ " but the array length is " + src.length);
		if (src_position + length > src.length)
			throw new IllegalArgumentException(
					"src_position + length would overrun the src array.  Expected end at " + (src_position + length)
							+ " actual end at " + src.length);
		if (dst_position < 0)
			throw new IllegalArgumentException("dst_position was less than 0.  Actual value "
					+ dst_position);
		if (dst_position >= dst.length)
			throw new IllegalArgumentException(
					"dst_position was greater than dst array size.  Tried to write starting at position " + dst_position
							+ " but the array length is " + dst.length);
		if (dst_position + length > dst.length)
			throw new IllegalArgumentException(
					"dst_position + length would overrun the dst array.  Expected end at " + (dst_position + length)
							+ " actual end at " + dst.length);

		System.arraycopy(src, src_position, dst, dst_position, length);
	}

	/**
	 * 上限にあう配列を取得する<br>
	 * <br>
	 * 入力文字列を指定した区切り文字で分割した指定上限数の要素数文字列配列として返します。<br>
	 * 先頭から分割します。<br>
	 * 上限数が＜＝０として渡された場合は、<b>入力文字列をそのまま</b>を返します。<br>
	 * nullもしくは長さ０の文字列を入力文字列として渡された場合は、<b>指定上限数の要素数文字列配列</b>を返します。<br>
	 * 区切り文字にnullもしくは長さ０の文字列を指定した場合は、<br>
	 * <b>入力文字列を配列０要素として指定上限数の要素数文字列配列</b>を返します。<br>
	 * <br>
	 * タブ(\t)で分割 <br>
	 * StringUtil.splitLimit(&quot;a\tb\tc&quot;,&quot;\t&quot;, 3); <br>
	 * 戻り値：{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;} <br>
	 * <br>
	 * StringUtil.splitLimit(&quot;a9b9c&quot;, &quot;9&quot;, 5); <br>
	 * 戻り値：{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;,null,null}
	 * 
	 * @param str 入力文字列
	 * @param delimiter 区切り文字(正規表現に対応していません)
	 * @param limit 分割上限数
	 * @return 区切り文字で分割した結果の文字列配列
	 */
	public static String[] splitLimit(String str, String delimiter, int limit) {

		if (limit <= 0) {
			return new String[] { str };
		}

		String[] arr = new String[limit];

		if (Util.isNullOrEmpty(str)) {
			return arr;
		}

		if (Util.isNullOrEmpty(delimiter)) {
			arr[0] = str;
			return arr;
		}

		String[] values = split(str, delimiter, limit);

		if (values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				arr[i] = values[i];
			}
		}

		return arr;
	}

	/**
	 * ASCII : [0-9a-zA-Z$]以外の文字列を[_]へ変換した結果を返す
	 * 
	 * @param str 入力文字列
	 * @return 変換後の値
	 */
	public static String replaceToUnderscore(String str) {
		if (Util.isNullOrEmpty(str)) {
			return str;
		}

		return str.replaceAll("[^0-9a-zA-Z$]", "_");
	}

	/**
	 * @return ランダムUIDを返す
	 */
	public static String createUID() {
		return UUID.randomUUID().toString();
	}
}

package jp.co.ais.trans2.common.file;

import java.util.*;
import java.util.regex.*;

/**
 * CSVデータ分析
 * 
 * @author TRANS(D) YF.CONG
 */
public class TCsvTokenizer {

	/** 正規表現 */
	public static final String REG_EXP = "(?:^|,)(\\\"(?:[^\\\"]+|\\\"[^,])*\\\"?|[^,]*)";

	/**
	 * CSVデータ行読込<br>
	 * データ行を分析してから配列を戻る
	 * 
	 * @param line CSVデータ行
	 * @return 行List
	 */
	public static List<String> readLine(String line) {
		List<String> list = new ArrayList<String>();

		String regExp = REG_EXP;
		String str = "";

		// 一つ目正しく分析ため、「,」開始の場合、「""」ブランクを追加
		if (line.startsWith(",")) {
			line = "\"\"" + line;
		}

		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(line);
		while (matcher.find()) {
			str = matcher.group();
			str = trimOne(str, '"');
			str = trimOne(str, ',');
			str = trimOne(str, '"');
			str = str.trim();

			list.add(str);

		}
		return list;
	}

	/**
	 * 頭と末が同じcharがあれば、消す
	 * 
	 * @param value 対象ストリング
	 * @param trimValue 検索対象
	 * @return 検索対象消したストリング
	 */
	public static String trimPairs(String value, char trimValue) {

		return trim(value, trimValue, false);
	}

	/**
	 * 頭また末は指定charがあれば、消す
	 * 
	 * @param value 対象ストリング
	 * @param trimValue 検索対象
	 * @return 検索対象消したストリング
	 */
	public static String trimOne(String value, char trimValue) {

		return trim(value, trimValue, true);
	}

	/**
	 * 頭また末は指定charがあれば、消す
	 * 
	 * @param value 対象ストリング
	 * @param trimValue 検索対象
	 * @param flag <br>
	 *            true:頭また末は指定charがあれば、消す<br>
	 *            false:頭と末が同じcharがあれば、消す
	 * @return 検索対象消したストリング
	 */
	public static String trim(String value, char trimValue, boolean flag) {
		int count = value.length();
		int i = count;
		int j = 0;
		int k = 0;
		char[] arrayOfChar = value.toCharArray();

		if ((j < i) && (arrayOfChar[(k + j)] == trimValue)) {
			++j;
		}

		if ((j < i) && (arrayOfChar[(k + i - 1)] == trimValue)) {
			--i;
		}

		if (j != 0 && i != count || flag) {
			return (((j > 0) || (i < count)) ? value.substring(j, i) : value);
		} else {
			return value;
		}
	}

}
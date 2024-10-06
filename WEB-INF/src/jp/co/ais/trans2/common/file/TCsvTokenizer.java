package jp.co.ais.trans2.common.file;

import java.util.*;
import java.util.regex.*;

/**
 * CSV�f�[�^����
 * 
 * @author TRANS(D) YF.CONG
 */
public class TCsvTokenizer {

	/** ���K�\�� */
	public static final String REG_EXP = "(?:^|,)(\\\"(?:[^\\\"]+|\\\"[^,])*\\\"?|[^,]*)";

	/**
	 * CSV�f�[�^�s�Ǎ�<br>
	 * �f�[�^�s�𕪐͂��Ă���z���߂�
	 * 
	 * @param line CSV�f�[�^�s
	 * @return �sList
	 */
	public static List<String> readLine(String line) {
		List<String> list = new ArrayList<String>();

		String regExp = REG_EXP;
		String str = "";

		// ��ڐ��������͂��߁A�u,�v�J�n�̏ꍇ�A�u""�v�u�����N��ǉ�
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
	 * ���Ɩ�������char������΁A����
	 * 
	 * @param value �ΏۃX�g�����O
	 * @param trimValue �����Ώ�
	 * @return �����Ώۏ������X�g�����O
	 */
	public static String trimPairs(String value, char trimValue) {

		return trim(value, trimValue, false);
	}

	/**
	 * ���܂����͎w��char������΁A����
	 * 
	 * @param value �ΏۃX�g�����O
	 * @param trimValue �����Ώ�
	 * @return �����Ώۏ������X�g�����O
	 */
	public static String trimOne(String value, char trimValue) {

		return trim(value, trimValue, true);
	}

	/**
	 * ���܂����͎w��char������΁A����
	 * 
	 * @param value �ΏۃX�g�����O
	 * @param trimValue �����Ώ�
	 * @param flag <br>
	 *            true:���܂����͎w��char������΁A����<br>
	 *            false:���Ɩ�������char������΁A����
	 * @return �����Ώۏ������X�g�����O
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
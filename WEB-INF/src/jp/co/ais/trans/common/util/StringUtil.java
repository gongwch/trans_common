package jp.co.ais.trans.common.util;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.regex.*;

import javax.swing.*;

import org.apache.commons.text.*;

/**
 * ��������n���[�e�B���e�B
 */
public final class StringUtil {

	/** �����R�[�h��� JIS */
	public final static String CHARSET_KIND_JIS = "ISO2022JP";

	/** �����R�[�h��� SJIS */
	public final static String CHARSET_KIND_SJIS = "Shift_JIS";

	/** �����R�[�h��� EUC */
	public final static String CHARSET_KIND_EUC = "EUC_JP";

	/** �����R�[�h��� UTF8 */
	public final static String CHARSET_KIND_UTF8 = "UTF-8";

	/** �����R�[�h��� UTF16 */
	public final static String CHARSET_KIND_UTF16 = "UTF-16";

	/** �����R�[�h��� ISO8859-1 */
	public final static String CHARSET_KIND_ISO8859_1 = "ISO-8859-1";

	/** �z���؂�\������(�I���W�i��) */
	public static final String DELIMITER = "<>";

	/** ���s�\������(�I���W�i��) */
	public static final String LINESEPARATOR = "<LINESEP>";

	/** ���p�X�y�[�X */
	public static final String SINGLE_BYTE_SPACE = " ";

	/** �J���} */
	public static final String COMMA = ",";

	/** �啶���p�����ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_UPPER = Pattern.compile("[A-Z| ]+");

	/** �������p�����ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_LOWER = Pattern.compile("[a-z| ]+");

	/** ���p�J�i�݂̂��ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_KANA = Pattern.compile("^[\\uFF65-\\uFF9F]+$");

	/**
	 * ���͕�������w�肵����؂蕶���ŕ�������������z���Ԃ��܂��B<br>
	 * �����O�̕��������͕�����Ƃ��ēn���ꂽ�ꍇ�� �����O�̕�����̗v�f���P���z���Ԃ��܂��B<br>
	 * null����͕�����Ƃ��ēn���ꂽ�ꍇ�̓T�C�Y�O�̔z���Ԃ��܂��B<br>
	 * ��؂蕶����null�������͒����O�̕�������w�肵���ꍇ�́A ���͕���������̂܂ܕԂ��܂��B<br>
	 * <br>
	 * �^�u(\t)�ŕ��� <br>
	 * StringUtil.split(&quot;a\tb\tc&quot;,&quot;\t&quot;); <br>
	 * �߂�l�F{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}
	 * 
	 * @param s ���͕�����
	 * @param delimiter ��؂蕶��(���K�\���ɑΉ����Ă��܂���)
	 * @return ��؂蕶���ŕ����������ʂ̕�����z��
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
	 * ���͕�������w�肵����؂蕶���ŕ��������w�������̗v�f��������z��Ƃ��ĕԂ��܂��B<br>
	 * �擪���番�����܂��B<br>
	 * �����O�̕��������͕�����Ƃ��ēn���ꂽ�ꍇ�� �����O�̕�����̗v�f���P���z���Ԃ��܂��B<br>
	 * null����͕�����Ƃ��ēn���ꂽ�ꍇ�̓T�C�Y�O�̔z���Ԃ��܂��B<br>
	 * ��؂蕶����null�������͒����O�̕�������w�肵���ꍇ�́A ���͕���������̂܂ܕԂ��܂��B<br>
	 * <br>
	 * �^�u(\t)�ŕ��� <br>
	 * StringUtil.split(&quot;a\tb\tc&quot;,&quot;\t&quot;); <br>
	 * �߂�l�F{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;}
	 * 
	 * @param s ���͕�����
	 * @param delimiter ��؂蕶��(���K�\���ɑΉ����Ă��܂���)
	 * @param num ������
	 * @return ��؂蕶���ŕ����������ʂ̕�����z��
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
	 * HTML�p�G�X�P�[�v�����ϊ�
	 * 
	 * @param str �Ώە���
	 * @return �G�X�P�[�v����
	 */
	public static String escapeHtml(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}
		String escapeStr = StringEscapeUtils.escapeHtml4(str);
		escapeStr = StringEscapeUtils.escapeJava(escapeStr);
		// ���s�R�[�h��ϊ�
		return escapeStr;
	}

	/**
	 * HTML�p�G�X�P�[�v�����t�ϊ�
	 * 
	 * @param str �G�X�P�[�v����
	 * @return �ʏ핶��
	 */
	public static String unescapeHtml(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}
		str = StringEscapeUtils.unescapeJava(str);
		return StringEscapeUtils.unescapeHtml4(str);
	}

	/**
	 * �����Ԃɔ��p�X�y�[�X��}������
	 * 
	 * @param str �Ώە���
	 * @return �X�y�[�X��������������
	 */
	public static String addSpace(String str) {
		return addCharacter(str, SINGLE_BYTE_SPACE);
	}

	/**
	 * �����ԂɎw�蕶����}������
	 * 
	 * @param str �Ώە���
	 * @param addStr �}������
	 * @return �}��������������������
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
	 * �w�蕶�����S�ĉp���啶���ł��邩�ǂ����𔻒肷��. <br>
	 * �X�y�[�X�͗L������.<br>
	 * �X�y�[�X�݂͖̂���.<br>
	 * ex1) isUpperCase �� false <br>
	 * ex2) IS UPPER CASE �� true
	 * 
	 * @param str �Ώە���
	 * @return true:�p���啶���̂�
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
	 * �w�蕶�����S�ĉp���������ł��邩�ǂ����𔻒肷��. <br>
	 * �X�y�[�X�͗L������.<br>
	 * �X�y�[�X�݂͖̂���.<br>
	 * ex1) isUpperCase �� false <br>
	 * ex2) is lower case �� true<br>
	 * 
	 * @param str �Ώە���
	 * @return true:�p���������̂�
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
	 * �w�蕶�������ׂĔ��p�J�i�����ł��邩�ǂ������f����<br>
	 * �󕶎���True
	 * 
	 * @param str
	 * @return true:���p�J�i�̂�
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
	 * ���������啶�� �ϊ����s���A�P��Ԃ̐ڍ������ɃX�y�[�X��}������. <br>
	 * ex) toUpperCase �� TO UPPER CASE <br>
	 * <br>
	 * �S�đ啶���̏ꍇ�́A���̂܂ܕԂ�. <br>
	 * ex) TO UPPER CASE �� TO UPPER CASE
	 * 
	 * @param str �Ώە���
	 * @return �ϊ�����
	 */
	public static String toUpperCase(String str) {

		// �S�đ啶���̏ꍇ�́A���̂܂ܕԂ�
		boolean isAllUpper = true;
		for (char chr : str.toCharArray()) {
			if (chr != ' ') {
				isAllUpper &= Character.isUpperCase(chr);
			}
		}
		if (isAllUpper) {
			return str;
		}

		// �ϊ�
		StringBuilder buff = new StringBuilder();

		char[] chars = str.toCharArray();
		for (char chr : chars) {
			// �啶���̏ꍇ�A���̑O�ɃX�y�[�X��}��
			if (Character.isUpperCase(chr)) {
				buff.append(SINGLE_BYTE_SPACE);
			}
			buff.append(chr);
		}

		return buff.toString().toUpperCase();
	}

	/**
	 * �啶���������� �ϊ����s���A�X�y�[�X��r�����Ċe�P��̐擪��啶���Ƃ���.<br>
	 * ex) TO LOWER CASE �� toLowerCase <br>
	 * <br>
	 * �X�y�[�X���܂܂Ȃ��ꍇ�́A���̂܂ܕԂ�. <br>
	 * ex) toLowerCase �� toLowerCase
	 * 
	 * @param str �Ώە���
	 * @return �ϊ�����
	 */
	public static String toLowerCase(String str) {

		// �X�y�[�X���܂܂Ȃ��ꍇ�́A���̂܂ܕԂ�.
		boolean hasSpace = false;
		for (char chr : str.toCharArray()) {
			hasSpace |= (chr == ' ');
		}
		if (!hasSpace) {
			return str;
		}

		// �ϊ�
		StringBuilder buff = new StringBuilder();

		// ��x�A�������ɕϊ�
		str = str.toLowerCase();

		boolean flag = false;
		char[] chars = str.toCharArray();
		for (char chr : chars) {
			if (chr == ' ') {
				flag = true;
			} else {
				// �O�̕������X�y�[�X�̏ꍇ�A���̕�����啶���ɂ��Ēǉ�
				String s = String.valueOf(chr);
				buff.append(flag ? s.toUpperCase() : s);
				flag = false;
			}
		}

		return buff.toString();
	}

	/**
	 * �w�肵���o�C�g�����ɁA�Ώە�����ɉ��s��������
	 * 
	 * @param maxLength ���s�w��o�C�g��
	 * @param str �Ώە�����
	 * @return ���s���������Ώە�����
	 */
	public static String appendLineSeparator(int maxLength, String str) {
		// �؂�����������i�[�o�b�t�@
		StringBuffer apendSb = new StringBuffer();
		String strSpace = " ";
		int byteCnt = 0;
		for (int i = 0; i < str.length(); i++) {

			// String�N���X����ꕶ���擾����String�N���X�Ɋi�['
			String s = str.substring(i, i + 1);

			// �擾�����P�����̃o�C�g��
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
	 * �w�肳�ꂽ���X�g���J���}��؂�\��������ɕϊ�����.<br>
	 * toArrayByComma()��String�z��֖߂�.
	 * 
	 * @param args �Ώە������X�g
	 * @return �J���}��؂蕶����
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
	 * �w�肳�ꂽ���X�g���J���}��؂�\��������ɕϊ�����.<br>
	 * 
	 * @param args �Ώە������X�g
	 * @return �J���}��؂蕶����
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
	 * �w�肳�ꂽ���X�g���J���}��؂�\��������ɕϊ�����.<br>
	 * 
	 * @param args �Ώە������X�g
	 * @return �J���}��؂蕶����
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
	 * �w�肳�ꂽ���X�g���J���}��؂�\��������ɕϊ�����.<br>
	 * 
	 * @param list �Ώە������X�g
	 * @return �J���}��؂蕶����
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
	 * �w�肳�ꂽ���X�g���J���}��؂�\��������ɕϊ�����.<br>
	 * �l�́u"�v���͂�
	 * 
	 * @param list �Ώە������X�g
	 * @return �J���}��؂蕶����
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
	 * �J���}��؂�\���������String�z��֖߂�. <br>
	 * toCommaString()�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str �J���}��؂蕶����
	 * @return �����z��
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
	 * �J���}��؂�\���������int�z��֖߂�. <br>
	 * toCommaString()�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str �J���}��؂蕶����
	 * @return �����z��
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
	 * ���X�g�\���������Ԃ�. <br>
	 * toArrayFromDelimitString(String)��String�z��֖߂�.
	 * 
	 * @param args �Ώە������X�g
	 * @return ���X�g�\��������
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
	 * ���X�g�\���������String�z��֖߂�. <br>
	 * toDelimitString(String[])�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str ���X�g�\��������
	 * @return �����z��
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
	 * ���X�g�\���������Ԃ�. <br>
	 * toBooleanArrayFromDelimitString(String)��boolean�z��֖߂�.
	 * 
	 * @param args �Ώ�Boolean���X�g
	 * @return ���X�g�\��������
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
	 * ���X�g�\���������boolean�z��֖߂�. <br>
	 * toDelimitString(boolean[])�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str ���X�g�\��������
	 * @return boolean�z��
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
	 * ���X�g�\���������Ԃ�. <br>
	 * toIntArrayFromDelimitString(String)��int�z��֖߂�.
	 * 
	 * @param args �Ώ�int���X�g
	 * @return ���X�g�\��������
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
	 * ���X�g�\���������int�z��֖߂�. <br>
	 * toDelimitString(int[])�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str ���X�g�\��������
	 * @return int�z��
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
	 * �E�󔒍폜
	 * 
	 * @param str �Ώە�����
	 * @return �E�󔒂��폜���ꂽ������
	 */
	public static String rightTrim(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		return str.replaceAll("\\s+$", "");
	}

	/**
	 * ���󔒍폜
	 * 
	 * @param str �Ώە�����
	 * @return ���󔒂��폜���ꂽ������
	 */
	public static String leftTrim(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		return str.replaceAll("^\\s+", "");
	}

	/**
	 * �o�C�g�P�ʂō����當���擾<br>
	 * ��null�̏ꍇ�u�����N��Ԃ�
	 * 
	 * @param str �Ώە�����
	 * @param length �擾�o�C�g��
	 * @return �w��o�C�g��������(������)
	 */
	public static String leftBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// �؂�����������i�[�o�b�t�@
		StringBuffer apendSb = new StringBuffer();

		int byteCnt = 0;
		for (int i = 0; i < str.length(); i++) {

			// String�N���X����ꕶ���擾����String�N���X�Ɋi�['
			String s = str.substring(i, i + 1);

			// �擾�����P�����̃o�C�g��
			byteCnt += s.getBytes().length;
			if (length < byteCnt) {
				break;
			}
			apendSb.append(s);
		}
		return apendSb.toString();
	}

	/**
	 * �o�C�g�P�ʂŉE���當����擾
	 * 
	 * @param str �Ώە�����
	 * @param length �擾�o�C�g��
	 * @return �w��o�C�g��������(�E����)
	 */
	public static String rightBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		// �؂�����������i�[�o�b�t�@
		StringBuffer apendSb = new StringBuffer();

		int byteCnt = 0;
		for (int i = str.length() - 1; i >= 0; i--) {

			// String�N���X����ꕶ���擾����String�N���X�Ɋi�['
			String s = str.substring(i, i + 1);

			// �擾�����P�����̃o�C�g��
			byteCnt += s.getBytes().length;
			if (length < byteCnt) {
				break;
			}
			// ������擪�ɑ}��
			apendSb.insert(0, s);
		}
		return apendSb.toString();
	}

	/**
	 * �w��o�C�g�ȍ~�̕������擾.
	 * 
	 * @param str �Ώە�����
	 * @param length �o�C�g��
	 * @return �w��Index�ڍs�̕���
	 */
	public static String afterBX(String str, int length) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		int bytes = 0;
		int index = 0;
		for (; index < str.length(); index++) {

			// String�N���X����ꕶ���擾
			String s = str.substring(index, index + 1);

			// �擾�����P�����̃o�C�g��
			bytes += s.getBytes().length;

			if (length < bytes) {
				break;
			}
		}

		if (bytes <= length) {
			return ""; // �w��o�C�g��肻������������
		}

		return str.substring(index, str.length());
	}

	/**
	 * �w��o�C�g����w��o�C�g�܂ł̕������擾.
	 * 
	 * @param str �Ώە�����
	 * @param start �J�n�o�C�g�ʒu
	 * @param end �I���o�C�g�ʒu
	 * @return �؂��蕶��
	 */
	public static String cutBX(String str, int start, int end) {
		String cut = afterBX(str, start - 1); // �w��o�C�g������擾
		cut = leftBX(cut, end - start + 1); // ������o�C�g�w��Ő؂�
		return cut;
	}

	/**
	 * HTML�G���R�[�h�������X�g�\���������Ԃ�.<br>
	 * convertStringToList()��String���X�g�֖߂�.
	 * 
	 * @param args �Ώە������X�g
	 * @return ���X�g�\��������
	 */
	public static String toHTMLEscapeString(List<String> args) {
		return toHTMLEscapeString(args.toArray(new String[args.size()]));
	}

	/**
	 * HTML�G���R�[�h�������X�g�\���������Ԃ�.<br>
	 * convertStringToArray()��String�z��֖߂�.
	 * 
	 * @param args �Ώە������X�g
	 * @return ���X�g�\��������
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
	 * HTML�G���R�[�h���ꂽ���X�g�\���������String���X�g�֖߂�. <br>
	 * convertListToString()�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str ���X�g�\��������
	 * @return �������X�g
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
	 * HTML�G���R�[�h���ꂽ���X�g�\���������String�z��֖߂�. <br>
	 * convertArrayToString()�ŕϊ����ꂽ�����񂪑Ώ�.
	 * 
	 * @param str ���X�g�\��������
	 * @return �����z��
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
	 * ������ϊ�. <br>
	 * '* �@�\ �F�Ώۂ̕������"'"���܂܂�Ă����當���������"'"�Ƌ�ʂ��邽��"'"���d�˂� '* �Ώۂ̕������"\"���܂܂�Ă�����ESCAPE�I�v�V������"\"�Ƌ�ʂ��邽��"\"���d�˂� '*
	 * �Ώۂ̕������"_"���܂܂�Ă�����LIKE�̓��ꕶ���Ƌ�ʂ��邽��ESCAPE�I�v�V������"\"������ '* �Ώۂ̕������"%"���܂܂�Ă�����LIKE�̓��ꕶ���Ƌ�ʂ��邽��ESCAPE�I�v�V������"\"������
	 * 
	 * @param str �Ώە�����
	 * @return �ϊ�������
	 */
	public static String convertPrm(String str) {
		if (Util.isNullOrEmpty(str)) {
			return "";
		}

		String retStr = new String(str);

		// ���ꂼ��̕������ϊ�
		retStr = retStr.replace("'", "''");
		retStr = retStr.replace(Util.ESCAPE_STRING, Util.ESCAPE_STRING + Util.ESCAPE_STRING);
		retStr = retStr.replace("_", Util.ESCAPE_STRING + "_");
		retStr = retStr.replace("%", Util.ESCAPE_STRING + "%");

		return retStr;
	}

	/**
	 * �o�C�i���f�[�^��URL�G���R�[�h������������ɕϊ�����
	 * 
	 * @param bytes �o�C�i���f�[�^
	 * @return �ϊ��㕶����
	 * @throws UnsupportedEncodingException
	 */
	public static String toURLEncodeString(byte[] bytes) throws UnsupportedEncodingException {
		return URLEncoder.encode(new String(bytes, CHARSET_KIND_ISO8859_1), CHARSET_KIND_UTF8);
	}

	/**
	 * URL�G���R�[�h����������o�C�i���f�[�^�ɕϊ�����
	 * 
	 * @param str �Ώە�����
	 * @return �ϊ���o�C�i���f�[�^
	 * @throws UnsupportedEncodingException
	 */
	public static byte[] toURLDecodeBytes(String str) throws UnsupportedEncodingException {
		String decStr = URLDecoder.decode(str, CHARSET_KIND_UTF8);
		return decStr.getBytes(CHARSET_KIND_ISO8859_1);
	}

	/**
	 * String�z��𓝍�����String�֕ϊ�����
	 * 
	 * @param strs String�z��
	 * @return ��������
	 */
	public static String toJoinString(String[] strs) {
		StringBuilder buff = new StringBuilder();
		for (String str : strs) {
			buff.append(str);
		}

		return buff.toString();
	}

	/**
	 * String���X�g�𓝍�����String�֕ϊ�����
	 * 
	 * @param list String���X�g
	 * @return ��������
	 */
	public static String toJoinString(List<String> list) {
		StringBuilder buff = new StringBuilder();
		for (String str : list) {
			buff.append(str);
		}

		return buff.toString();
	}

	/**
	 * String���X�g�𓝍�����String�֕ϊ�����
	 * 
	 * @param list String���X�g
	 * @param split
	 * @return ��������
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
	 * �w��̃o�C�g���Ő؂���������������X�g�ŕԂ��܂��B<br>
	 * �w�蕶�����w��i���ɖ����Ȃ��ꍇ�́A�c��̒i���ɂ̓u�����N������.<br>
	 * �y�g�p��z<br>
	 * getParagraphString("��������������", 6, 4) �̏ꍇ<br>
	 * �߂�l��List�̗v�f0�ɂ͂U�o�C�g����"������"�A�v�f1�ɂ�"������", �v�f2�ɂ�"��"�A�v�f3�ɂ�""������܂��B<br>
	 * ["������", "������", "��", ""]
	 * 
	 * @param str ������
	 * @param byteNo �o�C�g��
	 * @param paragraphNo �i��
	 * @return �w��o�C�g���ŋ�؂�ꂽ������
	 */
	public static List<String> getParagraphString(String str, int byteNo, int paragraphNo) {

		List<String> rtList = new ArrayList<String>(paragraphNo);

		String rt = Util.avoidNull(str);

		for (int i = 0; i < paragraphNo; i++) {

			// �؂�����������ǉ�
			rtList.add(Util.avoidNull(StringUtil.leftBX(rt, byteNo)));

			// �؂�������̕������Z�b�g
			rt = StringUtil.rightBX(rt, rt.getBytes().length - StringUtil.leftBX(rt, byteNo).getBytes().length);
		}

		return rtList;
	}

	/**
	 * �w�茅���̃f�[�^�擾
	 * 
	 * @param str �w�蕶����
	 * @param length �n���͎̂w��̃o�C�g��
	 * @return �w�茅���̃f�[�^
	 */
	public static String spaceFill(String str, int length) {

		// ������̌���
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// �u�����N�̂̌���
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
	 * �w�茅���̃f�[�^�擾<br>
	 * ��:("ABC", 5)��ABC&nbsp;&nbsp;
	 * 
	 * @param str �w�蕶����
	 * @param length �n���͎̂w��̃o�C�g��
	 * @return �w�茅���̃f�[�^
	 */
	public static String fillHtmlSpace(String str, int length) {

		// ������̌���
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// �u�����N�̂̌���
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
	 * �w�茅���Ŏw�肳�ꂽ���ߕ����Ŗ��߂��f�[�^�擾[�E����]
	 * 
	 * @param str �w�蕶����
	 * @param length �n���͎̂w��̃o�C�g��
	 * @param fillChar ���ߕ���
	 * @return �w�茅���̃f�[�^[�E����]
	 */
	public static String fill(String str, int length, char fillChar) {

		// ������̌���
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// ���ߌ���
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
	 * �w�茅���Ŏw�肳�ꂽ���ߕ����Ŗ��߂��f�[�^�擾[������]
	 * 
	 * @param str �w�蕶����
	 * @param length �n���͎̂w��̃o�C�g��
	 * @param fillChar ���ߕ���
	 * @return �w�茅���̃f�[�^[������]
	 */
	public static String fillLeft(String str, int length, char fillChar) {

		// ������̌���
		int lengthStr = str.getBytes().length;

		if (lengthStr < length) {
			// ���ߌ���
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
	 * ���߂��f�[�^�̍�����w�肳�ꂽ�g��������������
	 * 
	 * @param str �w�蕶����
	 * @param trimChar �g��������
	 * @return �g�����������������f�[�^[���g����]
	 */
	public static String trimLeft(String str, char trimChar) {

		// ������̌���
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
	 * HTML�f�[�^���擾����
	 * 
	 * @param data �f�[�^
	 * @param horizontalAlign ���������\���ʒu
	 * @return String HTML�f�[�^
	 */
	public static String getHtmlString(int horizontalAlign, Object[] data) {

		String str = "";
		for (Object htmlStr : data) {
			str = str + htmlStr + "<br>";
		}

		if (SwingConstants.RIGHT == horizontalAlign) {
			// �E��
			str = "<div style=\"text-align:right;white-space:nowrap;\">" + str;

		} else if (SwingConstants.CENTER == horizontalAlign) {
			// ������
			str = "<div style=\"text-align:center;white-space:nowrap;\">" + str;
		} else {
			str = "<div style=\"text-align:left;white-space:nowrap; \">" + str;
		}
		return str = "<html>" + str + "</div></html>";
	}

	/**
	 * �S�p�̃`�F�b�N����
	 * 
	 * @param str �`�F�b�N�l
	 * @return <br>
	 *         true:�S�p�������܂�<br>
	 *         false:�󔒁ANULL����є��p�J�i�ȊO�̔��p�p���A�����̂�
	 */
	public static boolean containsFullSize(String str) {

		// �󔒂����NULL�̏ꍇ�A���Ȃ�
		if (Util.isNullOrEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			String s = str.substring(i, i + 1);

			// �S�p�֎~�őS�p���܂܂�Ă���ꍇ��NG
			if (s.getBytes().length > 1) {
				return true;
			}

			// ���K�\���`�F�b�N : ���p�J�i��NG
			if (s.matches("[�-�]")) {
				return true;
			}

		}

		return false;

	}

	/**
	 * ���p�p���̃`�F�b�N����
	 * 
	 * @param str �`�F�b�N�l
	 * @return <br>
	 *         true:�󔒁ANULL����є��p�p���̂�<br>
	 *         false:���p�p���ȊO����
	 */
	public static boolean isHalfCharOrNumber(String str) {

		// �󔒂����NULL�̏ꍇ�A���Ȃ�
		if (Util.isNullOrEmpty(str)) {
			return true;
		}

		// ���p�p���ȊO�̕���������΁ANG
		String s = str.replaceAll("[^A-Za-z0-9]", "");

		return s.equals(str);

	}

	/**
	 * �_�u���N�H�[�e�[�V�����ň͂�
	 * 
	 * @param str �Ώە���
	 * @return ���H�㕶��
	 */
	public static String addDQuo(String str) {
		return "\"" + Util.avoidNullNT(str) + "\"";
	}

	/**
	 * �w�蕶�������I�[�o�[���Ă��邩�ǂ���<br>
	 * <b>�����ӁF�o�C�g���ł͂Ȃ��I</b>
	 * 
	 * @param str ����
	 * @param length ������
	 * @return true:�I�[�o�[
	 */
	public static boolean isOver(String str, int length) {
		return Util.avoidNullNT(str).length() > length;
	}

	/**
	 * �w�蕶�������I�[�o�[���Ă��邩�ǂ���
	 * 
	 * @param str ����
	 * @param length ������
	 * @return true:�I�[�o�[
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
	 * ����ɂ����z����擾����<br>
	 * <br>
	 * ���͕�������w�肵����؂蕶���ŕ��������w�������̗v�f��������z��Ƃ��ĕԂ��܂��B<br>
	 * �擪���番�����܂��B<br>
	 * ������������O�Ƃ��ēn���ꂽ�ꍇ�́A<b>���͕���������̂܂�</b>��Ԃ��܂��B<br>
	 * null�������͒����O�̕��������͕�����Ƃ��ēn���ꂽ�ꍇ�́A<b>�w�������̗v�f��������z��</b>��Ԃ��܂��B<br>
	 * ��؂蕶����null�������͒����O�̕�������w�肵���ꍇ�́A<br>
	 * <b>���͕������z��O�v�f�Ƃ��Ďw�������̗v�f��������z��</b>��Ԃ��܂��B<br>
	 * <br>
	 * �^�u(\t)�ŕ��� <br>
	 * StringUtil.splitLimit(&quot;a\tb\tc&quot;,&quot;\t&quot;, 3); <br>
	 * �߂�l�F{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;} <br>
	 * <br>
	 * StringUtil.splitLimit(&quot;a9b9c&quot;, &quot;9&quot;, 5); <br>
	 * �߂�l�F{&quot;a&quot;,&quot;b&quot;,&quot;c&quot;,null,null}
	 * 
	 * @param str ���͕�����
	 * @param delimiter ��؂蕶��(���K�\���ɑΉ����Ă��܂���)
	 * @param limit ���������
	 * @return ��؂蕶���ŕ����������ʂ̕�����z��
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
	 * ASCII : [0-9a-zA-Z$]�ȊO�̕������[_]�֕ϊ��������ʂ�Ԃ�
	 * 
	 * @param str ���͕�����
	 * @return �ϊ���̒l
	 */
	public static String replaceToUnderscore(String str) {
		if (Util.isNullOrEmpty(str)) {
			return str;
		}

		return str.replaceAll("[^0-9a-zA-Z$]", "_");
	}

	/**
	 * @return �����_��UID��Ԃ�
	 */
	public static String createUID() {
		return UUID.randomUUID().toString();
	}
}

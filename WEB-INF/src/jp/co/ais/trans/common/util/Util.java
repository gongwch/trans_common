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
 * �W�����[�e�B���e�B
 */
public class Util {

	/** Null����(null) */
	public static final String NULL_STRING1 = "(null)";

	/** Null���� NULL */
	public static final String NULL_STRING2 = "NULL";

	/** �G�X�P�[�v���� */
	public static final String ESCAPE_STRING = "/";

	/**
	 * Null,�󕶎��`�F�b�N
	 * 
	 * @param obj �Ώےl
	 * @return Null�܂��͋󕶎��̏ꍇ�ATrue
	 */
	public static boolean isNullOrEmpty(Object obj) {
		if (obj == null) {
			return true;
		}

		return String.valueOf(obj).trim().length() == 0;
	}

	/**
	 * Null�l���.(trim����)<br>
	 * Null�l�̏ꍇ�͋󔒂�Ԃ�.
	 * 
	 * @param obj �Ώےl
	 * @return Null�̏ꍇ�A�󕶎�
	 */
	public static String avoidNull(Object obj) {
		// Null�l�̏ꍇ�͋󔒂�Ԃ�
		return (obj == null) ? "" : String.valueOf(obj).trim();
	}

	/**
	 * Null�l���. (trim�Ȃ�)<br>
	 * Null�l�̏ꍇ�͋󔒂�Ԃ�.
	 * 
	 * @param obj �Ώےl
	 * @return Null�̏ꍇ�A�󕶎�
	 */
	public static String avoidNullNT(Object obj) {
		// Null�l�̏ꍇ�͋󔒂�Ԃ�
		return (obj == null) ? "" : String.valueOf(obj);
	}

	/**
	 * Null�l���. �w��̃I�u�W�F�N�g�l��int�ɕϊ����ĕԂ�.<br>
	 * Null�l�A�܂��͋󕶎��̏ꍇ��0��Ԃ�.
	 * 
	 * @param obj �Ώےl
	 * @return int�l. Null�A�󕶎��̏ꍇ0
	 */
	public static int avoidNullAsInt(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return 0;
		}

		return Integer.parseInt(String.valueOf(obj));
	}

	/**
	 * Null�l���. �w��̃I�u�W�F�N�g�l��Float�ɕϊ����ĕԂ�. <br>
	 * Null�l�A�܂��͋󕶎��̏ꍇ��0��Ԃ�.
	 * 
	 * @param obj �Ώےl
	 * @return Float�l. Null�A�󕶎��̏ꍇ0
	 */
	public static float avoidNullAsFloat(Object obj) {
		if (Util.isNullOrEmpty(obj)) {
			return 0;
		}

		return Float.parseFloat(String.valueOf(obj));
	}

	/**
	 * �󕶎��̏ꍇ�Anull�ɕϊ����ĕԂ�. <br>
	 * �󕶎��łȂ��ꍇ�́A���̂܂ܕ�����ϊ����ĕԂ�.
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return null�A�܂��́A������
	 */
	public static String emptyToNull(Object obj) {
		return isNullOrEmpty(obj) ? null : String.valueOf(obj);
	}

	/**
	 * null�̏ꍇ�A"(null)"��Ԃ�.
	 * 
	 * @param obj �ΏۃI�u�W�F�N�g
	 * @return �ϊ����ꂽ������
	 */
	public static String safeNull(Object obj) {

		return (obj == null) ? NULL_STRING1 : String.valueOf(obj);
	}

	/**
	 * null�̕�����\��"(null)"�ł��邩�ǂ����̔���.<br>
	 * "(null)"������ł���ꍇ�Atrue��Ԃ�.
	 * 
	 * @param str �Ώە�����
	 * @return true:null�\��������
	 */
	public static boolean isNullString(String str) {
		return (str == null) ? false : NULL_STRING1.equals(str);
	}

	/**
	 * �V�X�e�����t���擾����
	 * 
	 * @return �V�X�e�����t
	 */
	public static Date getCurrentDate() {
		return new GregorianCalendar().getTime();
	}

	/**
	 * �V�X�e�����t���擾����
	 * 
	 * @return �V�X�e�����t
	 */
	public static Date getCurrentYMDDate() {
		return DateUtil.toYMDDate(getCurrentDate());
	}

	/**
	 * �V�X�e�����t�������yyyy/MM/dd HH:mm:ss�`���Ŏ擾����
	 * 
	 * @return �V�X�e�����t������
	 */
	public static String getCurrentDateString() {
		return DateUtil.toYMDHMSString(getCurrentDate());
	}

	/**
	 * �V�X�e�����t�������yyyy/MM/dd HH:mm:ss�`���Ŏ擾����
	 * 
	 * @param lang ����R�[�h
	 * @return �V�X�e�����t������
	 */
	public static String getCurrentDateString(String lang) {

		return DateUtil.toYMDHMSString(lang, getCurrentDate());
	}

	/**
	 * �J�n �� �I�� ��r.<br>
	 * ������̑召��r�́AJava�̒P��Compare.
	 * 
	 * @param begin �J�n����
	 * @param end �I������
	 * @return �J�n�������I��������菬�����A�܂��͓����ł����true
	 */
	public static boolean isSmallerThen(String begin, String end) {

		if (isNullOrEmpty(begin) || isNullOrEmpty(end)) {
			return true;
		}

		return begin.compareTo(end) <= 0;
	}

	/**
	 * �J�n���t �� �I�����t ��r.<br>
	 * <b>null���t�̏ꍇ�Afalse</b>
	 * 
	 * @param begin �J�n���t
	 * @param end �I�����t
	 * @return �J�n���t���I�����t���O�A�܂��͓����ł����true
	 */
	public static boolean isSmallerThenByYMDNVL(Date begin, Date end) {
		if (begin == null || end == null) {
			return false;
		}
		return isSmallerThenByYMD(begin, end);
	}

	/**
	 * �J�n���t �� �I�����t ��r.
	 * 
	 * @param begin �J�n���t
	 * @param end �I�����t
	 * @return �J�n���t���I�����t���O�A�܂��͓����ł����true
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
	 * �J�n���� �� �I������ ��r.
	 * 
	 * @param begin �J�n����
	 * @param end �I������
	 * @return �J�n�������I���������O�A�܂��͓����ł����true
	 */
	public static boolean isSmallerThenByYMDHMS(Date begin, Date end) {

		Calendar bcal = Calendar.getInstance();
		bcal.setTime(begin);

		Calendar ecal = Calendar.getInstance();
		ecal.setTime(end);

		return bcal.before(ecal) || bcal.equals(ecal);
	}

	/**
	 * ���t1�Ɠ��t2���������ǂ����̔���.
	 * 
	 * @param date1 ���t1
	 * @param date2 ���t2
	 * @return ����ł���ꍇtrue
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
	 * 10�i���̃J���[�R�[�h�iRGB�j�ɕϊ�
	 * 
	 * @param code 16�i���̃J���[�R�[�h(#FFFFFF)
	 * @return R�AG�AB���l�z��
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
	 * 16�i���̃J���[�R�[�h�i#FFFFFF�j�ɕϊ�
	 * 
	 * @param color �J���[
	 * @return 16�i���J���[�R�[�h�i#FFFFFF�j
	 */
	public static String to16RGBColorCode(Color color) {
		int[] rbg = { color.getRed(), color.getGreen(), color.getBlue() };
		return to16RGBColorCode(rbg);
	}

	/**
	 * 16�i���̃J���[�R�[�h�i#FFFFFF�j�ɕϊ�
	 * 
	 * @param rgb RGB�R�[�h
	 * @return 16�i���J���[�R�[�h�i#FFFFFF�j
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
	 * ���z�I�[�o�[����(�W���p����-17,4)
	 * 
	 * @param amount ���z
	 * @return ���z�I�[�o�[�̏ꍇ��TRUE
	 */
	public static boolean isOverAmount(String amount) {

		return isOverAmount(amount, 17, 4);
	}

	/**
	 * ���z�I�[�o�[����(�����w��)
	 * 
	 * @param amount ���z
	 * @param maxLen �ő包��
	 * @param digit ����������
	 * @return ���z�I�[�o�[�̏ꍇ��TRUE
	 */
	public static boolean isOverAmount(String amount, int maxLen, int digit) {
		//TODO:�����ȊO�̕������܂܂�Ă���ꍇ
		amount = avoidNull(amount).replace(",", "");


		int lenInt = 0; // ����������
		int lenDec = 0; // ����������

		String[] amountArrey = amount.split("\\.");

		for (int i = 0; i < amountArrey.length; i++) {
			if (i == 0) {
				lenInt = amountArrey[i].length();
			}

			if (i == 1) {
				lenDec = amountArrey[i].length();
			}
		}

		// �������͍ő包�� - ����������
		if (lenInt > maxLen - digit) {
			return true;
		}

		// �������͍ő�digit��
		if (lenDec > digit) {
			return true;
		}

		// �ő包��
		if (lenInt + lenDec > maxLen) {
			return true;
		}

		return false;
	}

	/**
	 * �w�肳�ꂽ�����񂪐��l�ɕϊ����ǂ����̔��f���s��
	 * 
	 * @param value ������
	 * @return ���l�ɕϊ��\�ȏꍇtrue
	 */
	public static boolean isNumber(String value) {

		try {
			// ���l���ǂ�����FormatException�Ŕ��f
			new BigDecimal(value);

			return true;

		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * <s> �w�肳�ꂽ�����񂪓��t�ɕϊ��\���ǂ����̔��f���s��</s> <br>
	 * <br>
	 * �ȉ��𗘗p���邱��<br>
	 * {@link DateUtil#isYMDDate(String)}
	 * 
	 * @param value ������
	 * @return ���t�ɕϊ��\�ȏꍇtrue
	 * @deprecated DateUtil�𗘗p���邱��
	 */
	public static boolean isDate(String value) {

		try {
			// ���t���ǂ�����FormatException�Ŕ��f
			DateUtil.toYMDDate(value);

			return true;

		} catch (ParseException ex) {
			// ���l�ɕϊ��ł��Ȃ������ꍇ
			return false;
		}
	}

	/**
	 * �Ώی��������x�ɓ����邩
	 * 
	 * @param initialMonth ����
	 * @param targetMonth �Ώی�
	 * @return ���x
	 */
	public static int getFiscalMonth(int initialMonth, int targetMonth) {
		if (targetMonth >= initialMonth) {
			return targetMonth - initialMonth + 1;
		} else {
			return targetMonth + 12 - initialMonth + 1;
		}
	}

	/**
	 * �w�茾��R�[�h������Ώ�(�p��)���ǂ����𔻒肷��
	 * 
	 * @param langCode ����R�[�h
	 * @return true:����Ώ�
	 */
	public static boolean isShortLanguage(String langCode) {

		// ����͉p��̂�
		return Locale.ENGLISH.getLanguage().equals(langCode);
	}

	/**
	 * ��O��StackTrace������\��
	 * 
	 * @param e �ΏۃG���[
	 * @return StackTrace������\��
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
	 * null���󕶎��ϊ����Ă����r
	 * 
	 * @param value1 �l1
	 * @param value2 �l2
	 * @return true:����
	 */
	public static boolean equals(String value1, String value2) {
		return Util.avoidNull(value1).equals(Util.avoidNull(value2));
	}

	/**
	 * �F�ϊ�<br>
	 * 255,255,255��Color
	 * 
	 * @param abg RBG
	 * @return �F
	 */
	public static Color toColor(String[] abg) {
		int color0 = Integer.parseInt(abg[0]);
		int color1 = Integer.parseInt(abg[1]);
		int color2 = Integer.parseInt(abg[2]);

		return new Color(color0, color1, color2);
	}

	/**
	 * 16�i�@�J���[�l��RGB�֓]������ <br>
	 * #FFFFFF��255,255,255�̃J���[
	 * 
	 * @param menuColor
	 * @return color RGB�J���[
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
	 * �V�X�e���o�[�W���������擾����
	 * 
	 * @param clazz ���C�u���������p���N���X
	 * @return 0:�ŏI�X�V�����A1:�o�[�W����
	 */
	public static String[] getSystemVersion(Class clazz) {
		String[] values = new String[2];

		try {
			// �J�����g�f�B���N�g���ɂ���jar�t�@�C�����w��
			URL url = clazz.getResource(clazz.getSimpleName() + ".class");
			String form = url.toExternalForm();

			// jar�t�@�C����
			String jar = form.substring(0, form.lastIndexOf(clazz.getPackage().getName().replace('.', '/')));

			URLConnection con = new URL(jar).openConnection();

			// ���[�J��(�J��)�̏ꍇ
			if (con == null || !(con instanceof JarURLConnection)) {
				values[0] = "local";
				values[1] = "local";

				return values;
			}

			JarURLConnection jarConnection = (JarURLConnection) con;

			// �ŏI�X�V�����AJAR�t�@�C������擾
			Date date = new Date(jarConnection.getLastModified());
			values[0] = DateUtil.toYMDHMString(date);

			// �o�[�W�����A�}�j�t�F�X�g����擾
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
	 * @return NVL����
	 */
	public static String nvl(String a, String b) {
		if (Util.isNullOrEmpty(a)) {
			return Util.avoidNull(b);
		}
		return a;
	}
}

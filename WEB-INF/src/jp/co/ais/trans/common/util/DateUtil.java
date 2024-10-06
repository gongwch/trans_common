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
 * ���t�n���[�e�B���e�B
 */
public final class DateUtil {

	/** �a��P�[�� */
	public static final Locale JP_LOCALE = new Locale("ja", "JP", "JP");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N���������b�~���b) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMSSSS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N���������b) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMS = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N������) */
	public static final SimpleDateFormat DATE_FORMAT_YMDHM = new SimpleDateFormat("yyyy/MM/dd HH:mm");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N����) */
	public static final SimpleDateFormat DATE_FORMAT_YMD = new SimpleDateFormat("yyyy/MM/dd");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N��) */
	public static final SimpleDateFormat DATE_FORMAT_YM = new SimpleDateFormat("yyyy/MM");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N2��) */
	public static final SimpleDateFormat DATE_FORMAT_Y2M = new SimpleDateFormat("yy/MM");

	/** �V�X�e���Œ�t�H�[�}�b�^(���� ����) */
	public static final SimpleDateFormat DATE_FORMAT_MDHM = new SimpleDateFormat("MM/dd HH:mm");

	/** �V�X�e���Œ�t�H�[�}�b�^(���� ����(�Z�k��)) */
	public static final SimpleDateFormat DATE_FORMAT_MDHM_SHORT = new SimpleDateFormat("M/d HH:mm");

	/** �V�X�e���Œ�t�H�[�}�b�^(�N��) */
	public static final SimpleDateFormat DATE_FORMAT_Y = new SimpleDateFormat("yyyy");

	/** �V�X�e���Œ�t�H�[�}�b�^(����) */
	public static final SimpleDateFormat DATE_FORMAT_MD = new SimpleDateFormat("MM/dd");

	/** �V�X�e���Œ�t�H�[�}�b�^(����(�Z�k��)) */
	public static final SimpleDateFormat DATE_FORMAT_MD_SHORT = new SimpleDateFormat("M/d");

	/** �V�X�e���Œ�t�H�[�}�b�^(����) */
	public static final SimpleDateFormat DATE_FORMAT_HMS = new SimpleDateFormat("HH:mm:ss");

	/** �V�X�e���Œ�t�H�[�}�b�^(�����F�b�Ȃ�) */
	public static final SimpleDateFormat DATE_FORMAT_HM = new SimpleDateFormat("HH:mm");

	/** �V�X�e���Œ�t�H�[�}�b�g�i���j */
	public static final SimpleDateFormat DATE_FORMAT_MM = new SimpleDateFormat("MM");

	/** �V�X�e���Œ�t�H�[�}�b�g�i���j */
	public static final SimpleDateFormat DATE_FORMAT_DD = new SimpleDateFormat("dd");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_ENGLISH = new SimpleDateFormat("d MMM yyyy ", Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_ENGLISH_YMDHMS = new SimpleDateFormat("d MMM yyyy  HH:mm:ss ",
			Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM = new SimpleDateFormat("MM/yyyy", Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_EN_YMD = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_EN_YMDHM = new SimpleDateFormat("dd/MM/yyyy HH:mm",
			Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�p���d�l�j */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM2 = new SimpleDateFormat("MMM,yyyy", Locale.ENGLISH);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�a��N�����j */
	public static final DateFormat DATE_FORMAT_JP_GYMD = new SimpleDateFormat("GGGGyyyy�NMM��dd��", JP_LOCALE);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N�����j */
	public static final DateFormat DATE_FORMAT_JP_YMD = new SimpleDateFormat("yyyy�NMM��dd��");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���j */
	public static final DateFormat DATE_FORMAT_JP_YM = new SimpleDateFormat("yyyy�NMM��");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�����j */
	public static final DateFormat DATE_FORMAT_JP_MD = new SimpleDateFormat("MM��dd��");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N�j */
	public static final DateFormat DATE_FORMAT_JP_Y = new SimpleDateFormat("yyyy�N");

	/** �V�X�e���Œ�t�H�[�}�b�g�i���j */
	public static final DateFormat DATE_FORMAT_JP_M = new SimpleDateFormat("MM��");

	/** �V�X�e���Œ�t�H�[�}�b�g�i���j */
	public static final DateFormat DATE_FORMAT_JP_D = new SimpleDateFormat("dd��");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�a����j */
	public static final SimpleDateFormat DATE_FORMAT_JP_G = new SimpleDateFormat("GGGG", JP_LOCALE);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�a��N�j */
	public static final SimpleDateFormat DATE_FORMAT_JP_YY = new SimpleDateFormat("yy", JP_LOCALE);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�a��NYYYY�j */
	public static final SimpleDateFormat DATE_FORMAT_JP_YYYY = new SimpleDateFormat("yyyy", JP_LOCALE);

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���������b�~���b�X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMSSSS_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmmss.SSS");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���������b�X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_YMDHMS_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmmss");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���������X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_YMDHM_NO_SLASH = new SimpleDateFormat("yyyyMMddHHmm");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N�����X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_YMD_NO_SLASH = new SimpleDateFormat("yyyyMMdd");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N�����X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_Y2MD_NO_SLASH = new SimpleDateFormat("yyMMdd");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���X���b�V�������F�N�͉��񌅁j */
	public static final SimpleDateFormat DATE_FORMAT_Y2M_NO_SLASH = new SimpleDateFormat("yyMM");

	/** �V�X�e���Œ�t�H�[�}�b�g�i�N���X���b�V�������j */
	public static final SimpleDateFormat DATE_FORMAT_YM_NO_SLASH = new SimpleDateFormat("yyyyMM");

	/** �����t�H�[�}�b�^(yyyy/MM/dd HH:mm) */
	public static final String TYPE_DATE_FORMAT_YMDHM = "YMDHM";

	/** �����t�H�[�}�b�^(yyyy/MM/dd HH:mm:ss) */
	public static final String TYPE_DATE_FORMAT_YMDHMS = "YMDHMS";

	/** ���t�t�H�[�}�b�^(yyyy/MM/dd) */
	public static final String TYPE_DATE_FORMAT_YMD = "YMD";

	/** ���t�t�H�[�}�b�^(yyyy/MM) */
	public static final String TYPE_DATE_FORMAT_YM = "YM";

	/** ���t�t�H�[�}�b�^(MM/dd) */
	public static final String TYPE_DATE_FORMAT_MD = "MD";

	/** ���t�t�H�[�}�b�^(yyyy) */
	public static final String TYPE_DATE_FORMAT_YYYY = "YYYY";

	/** ���t�t�H�[�}�b�^(MM) */
	public static final String TYPE_DATE_FORMAT_MM = "MM";

	/** ���t�t�H�[�}�b�^(M) */
	public static final String TYPE_DATE_FORMAT_M = "M";

	/** ���t�t�H�[�}�b�^(dd) */
	public static final String TYPE_DATE_FORMAT_DD = "DD";

	/** ���t�t�H�[�}�b�^(d) */
	public static final String TYPE_DATE_FORMAT_D = "D";

	/** ���t�t�H�[�}�b�^(yyyy/MM) */
	public static final String TYPE_DATE_FORMAT_HMS = "HMS";

	/** ���t�t�H�[�}�b�^(yyyy�NMM��dd��) */
	public static final String TYPE_DATE_FORMAT_YMD_C_1 = "YMD_C_1";

	/** ���t�t�H�[�}�b�^(yyyy�NM��d��) */
	public static final String TYPE_DATE_FORMAT_YMD_C_2 = "YMD_C_2";

	/** ���ԃt�H�[�}�b�^(HH��mm��ss�b) */
	public static final String TYPE_DATE_FORMAT_HMS_C_1 = "HMS_C_1";

	/** ���ԃt�H�[�}�b�^(H��m��s�b) */
	public static final String TYPE_DATE_FORMAT_HMS_C_2 = "HMS_C_2";

	/** �t�H�[�}�b�g */
	public static Map formatters;

	/** yyyy(�N)���ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_Y = Pattern.compile("\\d\\d\\d\\d");

	/** yyyy/MM(�N��)���ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_YM = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d");

	/** yyyy/MM/dd(�N����)���ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_YMD = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d");

	/** yyyy/MM/dd HH:mm(�N������)���ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_YMDHM = Pattern.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d \\d\\d:\\d\\d");

	/** yyyy/MM/dd HH:mm:ss(�N���������b)���ǂ����̐��K�\���p�^�[�� */
	public static final Pattern PATTERN_YMDHMS = Pattern
			.compile("\\d\\d\\d\\d[/-]\\d\\d[/-]\\d\\d \\d\\d:\\d\\d:\\d\\d");

	/** ONE_DAY_MILLISECOND */
	public static final BigDecimal ONE_DAY_MILLISECOND = new BigDecimal(24 * 60 * 60 * 1000);

	/** ONE_DAY_SECOND */
	public static final BigDecimal ONE_DAY_SECOND = new BigDecimal(24 * 60 * 60);

	/** ONE_HOUR_SECOND */
	public static final BigDecimal ONE_HOUR_SECOND = new BigDecimal(60 * 60);

	/** �ʏ�N�̌��̓��� */
	public static int[] DaysToMonth365 = { 0, 31, 59, 90, 120, 151, 181, 212, 243, 273, 304, 334, 365 };

	/** �[�N�̌��̓��� */
	public static int[] DaysToMonth366 = { 0, 31, 60, 91, 121, 152, 182, 213, 244, 274, 305, 335, 366 };

	/** ���t�`��YMD */
	public static final SimpleDateFormat DATE_FORMAT_YMD_SHORT = new SimpleDateFormat("yyyy/M/d");

	/** ���t�`��YMD2 */
	public static final SimpleDateFormat DATE_FORMAT_YMD2 = new SimpleDateFormat("yyyy/MM/dd");

	/** ���t�`��MMMM,yyyy */
	public static final SimpleDateFormat DATE_FORMAT_EN_YM3 = new SimpleDateFormat("MMMM,yyyy", Locale.US);

	/** ���t�`��MDY */
	public static final SimpleDateFormat DATE_FORMAT_MDY = new SimpleDateFormat("M/d/yyyy");

	/** ���t�`��M.D */
	public static final SimpleDateFormat DATE_FORMAT_MD_DOT = new SimpleDateFormat("M.d");

	/** ���t�`��HHmmss */
	public static final SimpleDateFormat TIME_FORMAT_HMS_NO_SLASH = new SimpleDateFormat("HHmmss");

	/** yyyy�NM��d�� */
	public static final DateFormat DATE_FORMAT_JP_YMD_SHORT = new SimpleDateFormat("yyyy�NM��d��");

	/** yyyy�NM��d�� HH�� mm�� */
	public static final DateFormat DATE_FORMAT_JP_YMDHM = new SimpleDateFormat("yyyy�N M�� d�� HH�� mm��");

	/** �NM��d�� */
	public static final DateFormat DATE_FORMAT_JP_MD_SHORT = new SimpleDateFormat("M��d��");

	static {
		// �}���`�X���b�h����
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
	 * �w�肵�����t�̔N���̏�����Ԃ�
	 * 
	 * @param date �Ώۓ��t
	 * @return �w�茎�̏���(1��)
	 */
	public static Date getFirstDate(Date date) {

		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		int lastday = gc.getActualMinimum(Calendar.DATE);

		return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), lastday).getTime();
	}

	/**
	 * �w�肵���N���̍ŏI���t��Ԃ�
	 * 
	 * @param year �N
	 * @param month ��
	 * @return �w�茎�̍ŏI��
	 */
	public static Date getLastDate(int year, int month) {
		int lastday = DateUtil.getLastDay(year, month);

		return new GregorianCalendar(year, month - 1, lastday).getTime();
	}

	/**
	 * �w�肵�����t�̔N���̍ŏI���t��Ԃ�
	 * 
	 * @param date �Ώۓ��t
	 * @return �w�茎�̍ŏI��
	 */
	public static Date getLastDate(Date date) {

		Calendar gc = Calendar.getInstance();
		gc.setTime(date);
		int lastday = gc.getActualMaximum(Calendar.DATE);

		return new GregorianCalendar(gc.get(Calendar.YEAR), gc.get(Calendar.MONTH), lastday).getTime();
	}

	/**
	 * �w�肵���N���̍ŏI����Ԃ�
	 * 
	 * @param year �N
	 * @param month ��
	 * @return �w�茎�̍ŏI��
	 */
	public static int getLastDay(int year, int month) {
		GregorianCalendar gc = new GregorianCalendar(year, month - 1, 1);
		return gc.getActualMaximum(Calendar.DATE);
	}

	/**
	 * �w�肵���N���̍ŏI����Ԃ�
	 * 
	 * @param date ���t
	 * @return �w�茎�̍ŏI��
	 */
	public static int getLastDay(Date date) {
		return getLastDay(getYear(date), getMonth(date));
	}

	/**
	 * �J�n�N������I���N���̌����擾
	 * 
	 * @param startDate �J�n
	 * @param endDate �I��
	 * @return ����
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
	 * �J�n�N������I���N���̌����擾
	 * 
	 * @param startYear �J�n�N
	 * @param startMonth �J�n��
	 * @param endYear �I���N
	 * @param endMonth �I����
	 * @return ����
	 */
	public static int getMonthCount(int startYear, int startMonth, int endYear, int endMonth) {
		return (endYear * 12 + endMonth) - (startYear * 12 + startMonth);
	}

	/**
	 * util.Date�^��sql.Timestamp�^�ɕϊ�����
	 * 
	 * @param dt Date
	 * @return �ϊ���Timestamp
	 */
	public static Timestamp toTimestamp(Date dt) {
		return new Timestamp(dt.getTime());
	}

	/**
	 * Date�N���X����N���擾
	 * 
	 * @param dt Date�N���X
	 * @return �N
	 */
	public static int getYear(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.YEAR);
	}

	/**
	 * Date�N���X���猎���擾
	 * 
	 * @param dt Date�N���X
	 * @return ��
	 */
	public static int getMonth(Date dt) {
		Calendar cl = new GregorianCalendar();
		cl.setTime(dt);

		return cl.get(Calendar.MONTH) + 1;
	}

	/**
	 * Date�N���X������ɂ����擾
	 * 
	 * @param dt Date�N���X
	 * @return ���ɂ�
	 */
	public static int getDay(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.DATE);
	}

	/**
	 * Date�N���X���玞��(24H)���擾
	 * 
	 * @param dt Date�N���X
	 * @return ����
	 */
	public static int getHour(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.HOUR_OF_DAY);
	}

	/**
	 * Date�N���X���番���擾
	 * 
	 * @param dt Date�N���X
	 * @return ��
	 */
	public static int getMinute(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.MINUTE);
	}

	/**
	 * Date�N���X����b���擾
	 * 
	 * @param dt Date�N���X
	 * @return �b
	 */
	public static int getSecond(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.SECOND);
	}

	/**
	 * Date�N���X����j�����擾
	 * 
	 * @param dt Date�N���X
	 * @return �j��(1:���j�`7:�y�j)
	 */
	public static int getDayOfWeek(Date dt) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);

		return cl.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Date���擾����.
	 * 
	 * @param year �N
	 * @param month ��
	 * @param day ��
	 * @return �w�肳�ꂽ�N�A���A���ɑΉ�����Date
	 */
	public static Date getDate(int year, int month, int day) {
		return new GregorianCalendar(year, month - 1, day).getTime();
	}

	/**
	 * Date���擾����.
	 * 
	 * @param year �N
	 * @param month ��
	 * @param day ��
	 * @param hourOfDay ��(24���ԕ\�L)
	 * @param minute �b
	 * @param second ��
	 * @return �w�肳�ꂽ�N�A���A���A���A���A�b�ɑΉ�����Date
	 */
	public static Date getDate(int year, int month, int day, int hourOfDay, int minute, int second) {
		return new GregorianCalendar(year, month - 1, day, hourOfDay, minute, second).getTime();
	}

	/**
	 * Timestamp���擾����.
	 * 
	 * @param year �N
	 * @param month ��
	 * @param day ��
	 * @return �w�肳�ꂽ�N�A���A���ɑΉ�����Timestamp
	 */
	public static Timestamp getTimestamp(int year, int month, int day) {
		return toTimestamp(getDate(year, month, day));
	}

	/**
	 * �N�����Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z����N��
	 * @return ���Z��̓��t
	 */
	public static Date addYear(Date dt, int n) {
		return addTime(dt, n, Calendar.YEAR);
	}

	/**
	 * �������Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z���錎��
	 * @return ���Z��̓��t
	 */
	public static Date addMonth(Date dt, int n) {
		return addTime(dt, n, Calendar.MONTH);
	}

	/**
	 * �������Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z�������
	 * @return ���Z��̓��t
	 */
	public static Date addDay(Date dt, int n) {
		return addTime(dt, n, Calendar.DAY_OF_MONTH);
	}

	/**
	 * �����Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z�������
	 * @return ���Z��̓��t
	 */
	public static Date addHour(Date dt, int n) {
		return addTime(dt, n, Calendar.HOUR_OF_DAY);
	}

	/**
	 * �����Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z�������
	 * @return ���Z��̓��t
	 */
	public static Date addMinute(Date dt, int n) {
		return addTime(dt, n, Calendar.MINUTE);
	}

	/**
	 * �b���Z
	 * 
	 * @param dt �Ώۓ��t
	 * @param n ���Z�������
	 * @return ���Z��̓��t
	 */
	public static Date addSecond(Date dt, int n) {
		return addTime(dt, n, Calendar.SECOND);
	}

	/**
	 * @param dt �Ώۓ��t
	 * @param n ���Z�������
	 * @param kind ���(�J�����_�t�B�[���h). Calendar.YEAR�Ȃ�
	 * @return ���Z��̓��t
	 */
	public static Date addTime(Date dt, int n, int kind) {
		Calendar cl = Calendar.getInstance();
		cl.setTime(dt);
		cl.add(kind, n);

		return cl.getTime();
	}

	/**
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy/MM/dd" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy�NMM��dd��" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy/MM/dd HH:mm" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy/MM/dd HH:mm:ss" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy/MM/dd HH:mm:ss" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy/MM" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * String��Date�ɃL���X�g
	 * 
	 * @param str �L���X�g����String "yyyy" �`���̓��t
	 * @return �L���X�g���ꂽDate
	 * @throws ParseException ���t�����񂪕s���Ȏ�
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
	 * �w�肳�ꂽDate���A���Ԃ�0�̓��t(YYYY/MM/DD 00:00:00.000)�ɒu��������
	 * 
	 * @param date �ϊ��Ώۓ��t
	 * @return ���Ԃ�0�̓��t
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
	 * ���t�̕�����ϊ�("d MMM yyyy ", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return d MMM yyyy(��F29 Mar 2007) �`��������
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
	 * ���t�̕�����ϊ�("d MMM yyyy HH:mm:ss ", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return d MMM yyyy HH:mm:ss(��F29 Mar 2007 12:31:12) �`��������
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
	 * ���t�̕�����ϊ�("MM/yyyy", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return MM/yyyy(��F03/2007) �`��������
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
	 * ���t�̕�����ϊ�("dd/MM/yyyy", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return dd/MM/yyyy(��F29/03/2007) �`��������
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
	 * ���t�̕�����ϊ�("dd/MM/yyyy HH:mm", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return dd/MM/yyyy HH:mm(��F29/03/2007 12:31) �`��������
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
	 * ���t�̕�����ϊ�("HH:mm dd(th) MMM,yyyy", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return HH:mm dd(th) MMM,yyyy(��F12:31 29th MARCH,2007) �`��������
	 */
	public static String toEngDateTimeString(Date dt) {
		if (dt == null) {
			return "";
		}

		return toHMString(dt) + " " + toEngDateString(dt);
	}

	/**
	 * ���t�̕�����ϊ�("dd(th) MMM,yyyy", Locale.ENGLISH)
	 * 
	 * @param dt ���t
	 * @return dd(th) MMM,yyyy(��F29th MARCH,2007) �`��������
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
	 * ���t�̕�����ϊ�(yyyy/MM/dd HH:mm:ss)
	 * 
	 * @param dt ���t
	 * @return yyyy/MM/dd HH:mm:ss �`��������
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
	 * ���t�̕�����ϊ�(yyyy/MM/dd HH:mm:ss)
	 * 
	 * @param dt ���t
	 * @return yyyy/MM/dd HH:mm:ss �`��������
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
	 * ���t�̕�����ϊ�(yyyy/MM/dd HH:mm)
	 * 
	 * @param dt ���t
	 * @return yyyy/MM/dd HH:mm �`��������
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
	 * ���t�̕�����ϊ�(yyyy/MM)
	 * 
	 * @param dt ���t
	 * @return yyyy/MM/dd �`��������
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
	 * ���t�̕�����ϊ�(yy/MM)
	 * 
	 * @param dt ���t
	 * @return yy/MM �`��������
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
	 * ���t�̕�����ϊ�(yyyy/MM)
	 * 
	 * @param dt ���t
	 * @return yyyy/MM �`��������
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
	 * ���t�̕�����ϊ�(yyyy)
	 * 
	 * @param dt ���t
	 * @return yyyy �`��������
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
	 * ���t�̕�����ϊ�(MM/dd)
	 * 
	 * @param dt ���t
	 * @return MM/dd �`��������
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
	 * ���t�̕�����ϊ�(M/d)
	 * 
	 * @param dt ���t
	 * @return M/d �`��������(�Z�k��)
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
	 * ���t�����̕�����ϊ�(MM/dd HH:mm)
	 * 
	 * @param dt ���t
	 * @return MM/dd HH:mm �`��������
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
	 * ���t�����̕�����ϊ�(M/d HH:mm)
	 * 
	 * @param dt ���t
	 * @return M/d HH:mm �`��������(�Z�k��)
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
	 * �����̕�����ϊ�(HH:mm:ss)
	 * 
	 * @param dt ���t
	 * @return HH:mm:ss �`��������
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
	 * �����̕�����ϊ�(HH:mm)
	 * 
	 * @param dt ���t
	 * @return HH:mm �`��������
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
	 * Date���u��(MM)�v�`��������ɕϊ�
	 * 
	 * @param date �Ώۓ��t
	 * @return MM�`������
	 */
	public static String toMMString(Date date) {
		return DATE_FORMAT_MM.format(date);
	}

	/**
	 * Date���u��(DD)�v�`��������ɕϊ�
	 * 
	 * @param date �Ώۓ��t
	 * @return dd�`������
	 */
	public static String toDDString(Date date) {
		return DATE_FORMAT_DD.format(date);
	}

	/**
	 * �a��YY�NMM��DD���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return �a��YMD����
	 */
	public static String toJpGYMDString(Date ymdDate) {
		return DATE_FORMAT_JP_GYMD.format(ymdDate);
	}

	/**
	 * YYYY�NMM��DD���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return YMD����
	 */
	public static String toJpYMDString(Date ymdDate) {
		return DATE_FORMAT_JP_YMD.format(ymdDate);
	}

	/**
	 * YYYY�NMM���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return YM����
	 */
	public static String toJpYMString(Date ymdDate) {
		return DATE_FORMAT_JP_YM.format(ymdDate);
	}

	/**
	 * MM��DD���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return MD����
	 */
	public static String toJpMDString(Date ymdDate) {
		return DATE_FORMAT_JP_MD.format(ymdDate);
	}

	/**
	 * YYYY�N�t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return Y����
	 */
	public static String toJpYString(Date ymdDate) {
		return DATE_FORMAT_JP_Y.format(ymdDate);
	}

	/**
	 * MM���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return M����
	 */
	public static String toJpMString(Date ymdDate) {
		return DATE_FORMAT_JP_M.format(ymdDate);
	}

	/**
	 * DD���t�H�[�}�b�g�ϊ�
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return D����
	 */
	public static String toJpDString(Date ymdDate) {
		return DATE_FORMAT_JP_D.format(ymdDate);
	}

	/**
	 * �a��N(YY)�t�H�[�}�b�g�ϊ�
	 * 
	 * @param date �Ώۓ��t
	 * @return �a��YY����
	 */
	public static String toJpYYString(Date date) {
		return DATE_FORMAT_JP_YY.format(date);
	}

	/**
	 * �a�����ϊ�<br>
	 * �w�肷��a��́A���݂̔N���ł̔N
	 * 
	 * @param jpYear �a��(YY)
	 * @return ����(YYYY) [�擾�ł��Ȃ��ꍇ��0]
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
	 * �a�����ϊ�<br>
	 * �w�肷��a��́A���݂̔N��
	 * 
	 * @param jpYear �a��(YY)
	 * @return ����(YYYY) [�擾�ł��Ȃ��ꍇ��0000]
	 */
	public static String toERA(String jpYear) {

		return String.valueOf(NumberFormatUtil.zeroFill(toERA(Integer.parseInt(jpYear)), 4));
	}

	/**
	 * �a��̔N����Ԃ�. <br>
	 * ����(�����ȑO)�A�����A�吳�A���a�A����
	 * 
	 * @param date �Ώۓ���
	 * @return �N���̕���
	 */
	public static String getJpERAWord(Date date) {
		return DATE_FORMAT_JP_G.format(date);
	}

	/**
	 * �a��̔N����Ԃ�.<br>
	 * 0:�������疾���ɊY�����Ȃ�<br>
	 * 1:����<br>
	 * 2:�吳<br>
	 * 3:���a<br>
	 * 4:����<br>
	 * 5:�ߘa<br>
	 * 
	 * @param date �Ώۓ���
	 * @return �N���Œ�l
	 */
	public static int getJpERA(Date date) {
		Calendar cal = Calendar.getInstance(JP_LOCALE);
		cal.setTime(date);

		return cal.get(Calendar.ERA);
	}

	/**
	 * ����a��ϊ�<br>
	 * 
	 * @param year ����(YYYY)
	 * @return �a��(YY)
	 */
	public static int toJpERA(int year) {
		String jpYear = DATE_FORMAT_JP_YYYY.format(DateUtil.getDate(year, 1, 1));

		return Integer.parseInt(jpYear);
	}

	/**
	 * ����a��ϊ�<br>
	 * 
	 * @param year ����(YYYY)
	 * @return �a��(YY)
	 */
	public static String toJpERA(String year) {
		return String.valueOf(toJpERA(Integer.parseInt(year)));
	}

	/**
	 * �a��YMD�t�H�[�}�b�g�ϊ�.<br>
	 * �N���AYY�N�AMM���ADD���𕪂����z��`���Ŏ擾����.
	 * 
	 * @param ymdDate �Ώۓ��t
	 * @return �a��YMD�����̕����z��
	 */
	public static String[] toJpYMDArray(Date ymdDate) {
		if (ymdDate == null) {
			return new String[0];
		}

		String era = getJpERAWord(ymdDate);//�a���̔N��
		String eraYear = toJpYYString(ymdDate);//�a���̔N
		String month = toMMString(ymdDate);//MM
		String day = toDDString(ymdDate);//dd

		String[] result = new String[4];
		result[0] = era; // �N��
		result[1] = String.format(Integer.parseInt(eraYear) + "�N"); // Y�N
		result[2] = String.format(month + "��"); // MM��
		result[3] = String.format(day + "��"); // DD��

		return result;
	}

	/**
	 * Date���u�N���������b�v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @param type �t�H�[�}�b�g�^�C�v
	 * @return �L���X�g���ꂽString
	 */
	public static String toString(String lang, Date dt, String type) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, type).format(dt);
	}

	/**
	 * Date���u�N���������b�v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @return �L���X�g���ꂽString
	 */
	public static String toYMDHMSString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMDHMS).format(dt);
	}

	/**
	 * Date���u�N���������v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @return �L���X�g���ꂽString
	 */
	public static String toYMDHMString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMDHM).format(dt);
	}

	/**
	 * Date���u�N�����v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @return �L���X�g���ꂽString
	 */
	public static String toYMDString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YMD).format(dt);
	}

	/**
	 * Date���u�N���v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @return �L���X�g���ꂽString
	 */
	public static String toYMString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_YM).format(dt);
	}

	/**
	 * Date���u�����b�v�`��������ɕϊ�
	 * 
	 * @param lang ����R�[�h
	 * @param dt �L���X�g����Date
	 * @return �L���X�g���ꂽString
	 */
	public static String toHMSString(String lang, Date dt) {
		if (dt == null) {
			return "";
		}

		return getDateFormat(lang, TYPE_DATE_FORMAT_HMS).format(dt);
	}

	/**
	 * �t�H�[�}�b�g�擾
	 * 
	 * @param lang ����R�[�h
	 * @param key �t�H�[�}�b�g�L�[
	 * @return �t�H�[�}�b�g����
	 */
	public static String getFormatString(String lang, String key) {
		return FormatManager.getInstance(lang).get(key);
	}

	/**
	 * �����t�H�[�}�b�g�̎擾
	 * 
	 * @param lang ����R�[�h
	 * @param type �t�H�[�}�b�g�^�C�v
	 * @return �����t�H�[�}�b�g
	 */
	public static SimpleDateFormat getDateFormat(String lang, String type) {

		String pk = lang + ":" + type;
		if (!formatters.containsKey(pk)) {
			formatters.put(pk, new SimpleDateFormat(getFormatString(lang, type)));
		}

		return (SimpleDateFormat) formatters.get(pk);
	}

	/**
	 * ���t�̕�����擾�iyyyy/MM/dd HH:mm:ss�j
	 * 
	 * @param millisecond UTC milliseconds
	 * @return yyyy/MM/dd HH:mm:ss �`��������
	 */
	public static String getYMDString(long millisecond) {
		Calendar cl = Calendar.getInstance();
		cl.setTimeInMillis(millisecond);

		return toYMDHMSString(cl.getTime());
	}

	/**
	 * �J�n���t�ƏI�����t���r���A���̊��Ԃ�����Ŏ擾����.<br>
	 * ���Ԃ��w�肳��Ă���ꍇ�́A��������.<br>
	 * �J�n�������̓J�E���g���Ȃ�.<br>
	 * <br>
	 * ex) 2008/04/23�`2008/04/25 �� 2��
	 * 
	 * @param fromDate �J�n���t
	 * @param toDate �I�����t
	 * @return ����
	 */
	public static int getDayCount(Date fromDate, Date toDate) {
		if (fromDate == null || toDate == null) {
			return 0;
		}

		// ���Ԃ̃N���A(�����݂̂ɂ���)
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
	 * �J�n���t�ƏI�����t���r���A���̊��Ԃ�����Ŏ擾����.<br>
	 * ���Ԃ��w�肳��Ă���ꍇ�́A��������.<br>
	 * �J�n���������J�E���g����.<br>
	 * <br>
	 * ex) 2008/04/23�`2008/04/25 �� 3��
	 * 
	 * @param fromDate �J�n���t
	 * @param toDate �I�����t
	 * @return ����
	 */
	public static int getDayCountFC(Date fromDate, Date toDate) {
		return getDayCount(fromDate, toDate) + 1;
	}

	/**
	 * FROM������TO�����̍��̎擾�i�����_�ȉ�����j <br>
	 * <br>
	 * ex) 2011/01/01�`2011/09/27 10:23:00(�����_�ȉ�6����) �� 269.432639
	 * 
	 * @param from �J�n����
	 * @param to �I������
	 * @param decimalPoint �����_�ȉ�����
	 * @return days ���ԍ�
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
	 * �w����t�ɓ��������Z����<br>
	 * ���x�͕b�܂�<br>
	 * 0.000015 �� 1�b<br>
	 * 0.00001 �� 0�b
	 * 
	 * @param dt
	 * @param days �����B��j1.23456
	 * @return ���t
	 */
	public static Date addDays(Date dt, BigDecimal days) {
		int seconds = DecimalUtil.roundNum(days.multiply(ONE_DAY_SECOND), 0).intValue();
		return addSecond(dt, seconds);
	}

	/**
	 * �w����t�Ɏ��Ԑ������Z����<br>
	 * ���x�͕b�܂�
	 * 
	 * @param dt
	 * @param hours ���Ԑ��B��j0.5
	 * @return ���t
	 */
	public static Date addHours(Date dt, BigDecimal hours) {
		int seconds = DecimalUtil.roundNum(hours.multiply(ONE_HOUR_SECOND), 0).intValue();
		return addSecond(dt, seconds);
	}

	/**
	 * �w�蕶����yyyy�`���ł��邩�ǂ����𔻒f����.
	 * 
	 * @param str �Ώە���
	 * @return true:yyyy����
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
	 * �w�蕶����yyyy/MM�`���ł��邩�ǂ����𔻒f����.
	 * 
	 * @param str �Ώە���
	 * @return true:yyyy/MM����
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
	 * �w�蕶����yyyy/MM/DD�`���ł��邩�ǂ����𔻒f����.
	 * 
	 * @param str �Ώە���
	 * @return true:yyyy/MM/dd����
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
	 * �w�蕶����yyyy/MM/dd HH:mm�`���ł��邩�ǂ����𔻒f����.
	 * 
	 * @param str �Ώە���
	 * @return true:yyyy/MM/dd����
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
	 * �w�蕶����yyyy/MM/dd HH:mm�`���ł��邩�ǂ����𔻒f����.
	 * 
	 * @param str �Ώە���
	 * @return true:yyyy/MM/dd����
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
	 * �����ӁF�u/-�v���܂񂾓��t���f��verify(String)����
	 * </pre>
	 * 
	 * </b><br>
	 * �w�蕶�������t(yyyy/MM/dd)�`���ł��邩�ǂ����𔻒f����.<br>
	 * /���܂߂��`�ŕ�����̒����Ŕ��肷��.<br>
	 * 4���� �� yyyy <br>
	 * 7���� �� yyyy/MM <br>
	 * 10���� �� yyyy/MM/dd <br>
	 * 16���� �� yyyy/MM/dd HH:mm <br>
	 * 19���� �� yyyy/MM/dd HH:mm:ss <br>
	 * 
	 * @param str �Ώە���
	 * @return true:���t����
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
	 * �Ώ�1�̓����ƑΏ�2�̓��������ꂩ�ǂ����𔻒f����.<br>
	 * �����Ŕ��f����ׁA
	 * 
	 * @param date1 �Ώ�1
	 * @param date2 �Ώ�2
	 * @return true:���ꎞ��
	 */
	public static boolean equals(Date date1, Date date2) {
		return DateUtil.toYMDHMSString(date1).equals(DateUtil.toYMDHMSString(date2));
	}

	/**
	 * �Ώ�1�̓����ƑΏ�2�̓��������ꂩ�ǂ����𔻒f����.<br>
	 * �����Ŕ��f����ׁA<br>
	 * �Ώ�1�ƑΏ�2��null�̏ꍇ�Atrue��߂�
	 * 
	 * @param date1 �Ώ�1
	 * @param date2 �Ώ�2
	 * @return true:���ꎞ��
	 */
	public static boolean equalsAvoidNull(Date date1, Date date2) {

		if (date1 == null && date2 == null) {
			return true;
		}

		return equals(date1, date2);
	}

	/**
	 * YYYYMMDD�`���̕������YYYY/MM/DD�ɕϊ�
	 * 
	 * @param strDate �Ώە���
	 * @return �ϊ��㕶��
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
	 * ���t�̕�����ϊ�(yyyymmdd)
	 * 
	 * @param dt ���t
	 * @return yyyymmdd �`��������
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
	 * YYMMDD�`���̕������YYYY/MM/DD�ɕϊ�<br>
	 * �N��20XX�Ƃ���
	 * 
	 * @param strDate �Ώە���
	 * @return �ϊ��㕶��
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
	 * ���t�̕�����ϊ�(yymmdd)
	 * 
	 * @param dt ���t
	 * @return yymmdd �`��������
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
	 * ���t�̕�����ϊ�(yymm)
	 * 
	 * @param dt ���t
	 * @return yymm �`��������
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
	 * ���t�̕�����ϊ�(yyyyMMddHHmmss.SSS)
	 * 
	 * @param dt ���t
	 * @return yyyyMMddHHmmss.SSS �`��������
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
	 * ���t�̕�����ϊ�(yyyyMMddHHmmss)
	 * 
	 * @param dt ���t
	 * @return yyyyMMddHHmmss �`��������
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
	 * ���t�̕�����ϊ�(yyyyMMddHHmm)
	 * 
	 * @param dt ���t
	 * @return yyyyMMddHHmm �`��������
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
	 * YYYYMM�`���̕������YYYY/MM/DD�ɕϊ�
	 * 
	 * @param strDate �Ώە���
	 * @return �ϊ��㕶��
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
	 * ���t�̕�����ϊ�(yyyymm)
	 * 
	 * @param dt ���t
	 * @return yyyymm �`��������
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
	 * ��Exception�Ȃ�<br>
	 * �w��̑Ώۂ�yyyy/MM/dd HH:mm:ss.SSS���t�ɕϊ�<br>
	 * �ȉ��̃t�H�[�}�b�g�͎���ł���<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * �u/�v�A�u-�v�͎���������������
	 * 
	 * @param obj �Ώ�
	 * @return �ϊ��㕶��<br>
	 *         ���t�ł͂Ȃ��ꍇ�Anull��߂�
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
	 * �w��̕������yyyy/MM/dd HH:mm:ss.SSS���t�ɕϊ�<br>
	 * �ȉ��̃t�H�[�}�b�g�͎���ł���<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * �u/�v�A�u-�v�͎���������������
	 * 
	 * @param strDate �Ώە���
	 * @return �ϊ��㕶��
	 * @throws ParseException
	 */
	public static Date toDate(String strDate) throws ParseException {
		if (Util.isNullOrEmpty(strDate)) {
			return null;
		}

		String str = strDate.replaceAll("/", "");
		str = str.replaceAll("T", ""); // �p��Time�L��
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
	 * �w��̕��������t�ɕϊ��o���邩�ǂ���<br>
	 * �ȉ��̃t�H�[�}�b�g�͎���ł���<br>
	 * yy<br>
	 * yyyy<br>
	 * yyyyMM<br>
	 * yyyyMMdd<br>
	 * yyyyMMddHHmm<br>
	 * yyyyMMddHHmmss<br>
	 * yyyyMMddHHmmss.SSS<br>
	 * �u/�v�A�u-�v�͎���������������
	 * 
	 * @param strDate �Ώە���
	 * @return true:���t�ɕϊ��ł���Afalse:���t�ɕϊ��ł��Ȃ�
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
	 * ���̗����Ԃ� ex1: ���{�� 1���A2��... ex2: �p�� Jun�AFeb...
	 * 
	 * @param lang ����R�[�h
	 * @param month
	 * @return ���P��
	 */
	public static String getMonthShortWord(String lang, int month) {
		Date dt = getDate(2000, month, 1);

		String f = getFormatString(lang, "MMM");
		Locale locale = new Locale(lang);
		SimpleDateFormat formatter = new SimpleDateFormat(f, locale);

		return formatter.format(dt.getTime());
	}

	/**
	 * ���P���Ԃ� ex1: ���{�� 1���A2��... ex2: �p�� Junuary�AFebruary...
	 * 
	 * @param lang ����R�[�h
	 * @param month
	 * @return ���P��
	 */
	public static String getMonthWord(String lang, int month) {
		Date dt = getDate(2000, month, 1);

		String f = getFormatString(lang, "MMMMM");
		Locale locale = new Locale(lang);
		SimpleDateFormat formatter = new SimpleDateFormat(f, locale);

		return formatter.format(dt.getTime());
	}

	/**
	 * �����ȓ��t�̎擾
	 * 
	 * @param d1
	 * @param d2
	 * @return �����ȓ��t
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
	 * �傫�ȓ��t�̎擾
	 * 
	 * @param d1
	 * @param d2
	 * @return �傫�ȓ��t
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
	 * �����ȓ����̎擾
	 * 
	 * @param d1
	 * @param d2
	 * @return �����ȓ���
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
	 * �傫�ȓ����̎擾
	 * 
	 * @param d1
	 * @param d2
	 * @return �傫�ȓ���
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
	 * VB�����J�E���g�̎擾
	 * 
	 * @param from
	 * @param to
	 * @return �����J�E���g
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
	 * VBA��OA���t�v�Z
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
	 * VB��Ticks�擾
	 * 
	 * @param dt
	 * @return ticks
	 */
	public static long getTicks(Date dt) {
		return getTicks(dt, 0);
	}

	/**
	 * VB��Ticks�擾
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
	 * ���t����VB��Ticks�擾
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
	 * ��������VB��Ticks�擾
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
	 * �[�N����
	 * 
	 * @param year
	 * @return true:�[�N
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
	 * ���t�`��YMD�𕶎���ɕϊ�����
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
	 * ���t�`��YMD2�𕶎���ɕϊ�����
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
	 * ���t�`��MDY�𕶎���ɕϊ�����
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
	 * ���t�`��M.D�𕶎���ɕϊ�����
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
	 * ���t�`��FullYMD�𕶎���ɕϊ�����
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
	 * ���l�iYYYYMM�j����t�ɕϊ�
	 * 
	 * @param number
	 * @return Date
	 */
	public static Date convertIntToDate(int number) {
		return convertIntToDate(String.valueOf(number));
	}

	/**
	 * ������iYYYYMM�j����t�ɕϊ�����
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
	 * ���t�𐮐��ɕϊ�
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYMPlainString(date));
	}

	/**
	 * ���t�𐮐��ɕϊ� (YMD)
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateYMDToInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYMDPlainString(date));
	}

	/**
	 * ������g�ݍ��킹�� <br>
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
	 * ���t�`��HHmmss�𕶎���ɕϊ�����
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
	 * yyyy�NM��d���𕶎���ɕϊ�����
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
	 * yyyy�NM��d���𕶎���ɕϊ�����
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
	 * yyyy�NM��d�� HH�� mm���𕶎���ɕϊ�����
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
	 * ���t��N�̐����ɕϊ�
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToYearInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toYString(date));
	}

	/**
	 * ���t�����̐����ɕϊ�
	 * 
	 * @param date
	 * @return int
	 */
	public static int convertDateToMonthInt(Date date) {
		return Util.avoidNullAsInt(DateUtil.toMMString(date));
	}
}

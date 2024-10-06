package jp.co.ais.trans.common.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * ���l�t�H�[�}�b�g�p���[�e�B���e�B
 */
public final class NumberFormatUtil {

	/**
	 * �����_�����w��̒ǉ��p�t�H�[�}�b�g��������擾����
	 * 
	 * @param digit �����_����
	 * @return ���z�p�t�H�[�}�b�g
	 */
	public static String makeExcelNumberFormat(int digit) {
		String nf = makeFormatStyle("#,##0", digit, "0");
		return nf;
	}

	/**
	 * �����_�����w��̒ǉ��p�t�H�[�}�b�g��������擾����
	 * 
	 * @param digit �����_����
	 * @return ���z�p�t�H�[�}�b�g
	 */
	public static String makeNumberFormat(int digit) {
		return makeFormatStyle("###,###,###,###,##0", digit, "0");
	}

	/**
	 * �����_�����w��̒ǉ��p�t�H�[�}�b�g��������擾����
	 * 
	 * @param digit �����_����
	 * @return ���z�p�t�H�[�}�b�g
	 */
	public static String makeNumberSharpFormat(int digit) {
		return makeFormatStyle("###,###,###,###,##0", digit, "#");
	}

	/**
	 * �����_�����w��̒ǉ��p�t�H�[�}�b�g��������擾����
	 * 
	 * @param integerStr �������t�H�[�}�b�g
	 * @param digit �����_����
	 * @param digitsStr �����_������ 0 �� # ��
	 * @return ���z�p�t�H�[�}�b�g
	 */
	private static String makeFormatStyle(String integerStr, int digit, String digitsStr) {

		StringBuilder buff = new StringBuilder();

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append(digitsStr);
			}
		}

		return integerStr + buff.toString();
	}

	/**
	 * ���l�t�H�[�}�b�g���ĕԂ�.
	 * 
	 * @param value ���l
	 * @param digit �����_����
	 * @return ���l�t�H�[�}�b�g�ϊ�
	 */
	public static String formatNumber(BigDecimal value, int digit) {

		BigDecimal dec = (value == null) ? BigDecimal.ZERO : value;

		// �`����ݒ�
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setParseIntegerOnly(true);
		numberFormat.setMaximumFractionDigits(digit);
		numberFormat.setMinimumFractionDigits(digit);

		return numberFormat.format(dec);
	}

	/**
	 * ���l�t�H�[�}�b�g���ĕԂ�.
	 * 
	 * @param value ���l
	 * @param digit �����_����
	 * @return ���l�t�H�[�}�b�g�ϊ�
	 */
	public static String formatNumber(double value, int digit) {
		if (value == -0d) {
			// -0�\����h��
			value = 0d;
		}

		// �`����ݒ�
		NumberFormat numberFormat = NumberFormat.getNumberInstance();
		numberFormat.setParseIntegerOnly(true);
		numberFormat.setMaximumFractionDigits(digit);
		numberFormat.setMinimumFractionDigits(digit);

		return numberFormat.format(value);
	}

	/**
	 * �����_����0�̃t�H�[�}�b�g�����ĕԂ�. <br>
	 * �󕶎����w�肳�ꂽ�ꍇ�́A0��Ԃ�.
	 * 
	 * @param value ������^���l
	 * @return �t�H�[�}�b�g���l������
	 */
	public static String formatNumber(String value) {
		return NumberFormatUtil.formatNumber(value, 0);
	}

	/**
	 * �����_����0�̃t�H�[�}�b�g�����ĕԂ�.
	 * 
	 * @param value ���l
	 * @return �t�H�[�}�b�g���l������
	 */
	public static String formatNumber(BigDecimal value) {
		return formatNumber(value, 0);
	}

	/**
	 * �����_����0�̃t�H�[�}�b�g�����ĕԂ�.
	 * 
	 * @param value ���l
	 * @return �t�H�[�}�b�g���l������
	 */
	public static String formatNumber(double value) {
		return formatNumber(value, 0);
	}

	/**
	 * ������^���l�𐔒l�t�H�[�}�b�g���ĕԂ�. <br>
	 * �󕶎����w�肳�ꂽ�ꍇ�́A0��Ԃ�.
	 * 
	 * @param value ������^���l
	 * @param digit �����_�����i������j
	 * @return ���l�t�H�[�}�b�g�ϊ�
	 */
	public static String formatNumber(Object value, Object digit) {

		// �����_
		int intDigit;
		if (digit instanceof Integer) {
			intDigit = ((Integer) digit).intValue();
		} else {
			String strDigit = Util.avoidNull(digit);
			intDigit = "".equals(strDigit) ? 0 : Integer.parseInt(strDigit);
		}

		if (value instanceof Double) {
			return formatNumber(((Double) value).doubleValue(), intDigit);
		}

		// BigDecimal�̏ꍇ
		BigDecimal decimalValue;
		if (value instanceof BigDecimal) {
			decimalValue = (BigDecimal) value;

		} else {

			String strValue = String.valueOf(value).trim();
			decimalValue = Util.isNullOrEmpty(strValue) || !Util.isNumber(strValue) ? BigDecimal.ZERO
					: new BigDecimal(
							strValue);
		}

		return formatNumber(decimalValue, intDigit);
	}

	/**
	 * �����̃t�H�[�}�b�g
	 * 
	 * @param value ���l
	 * @param digit �����_����
	 * @return formatStyle �t�H�[�}�b�g�`��
	 */
	public static String formatNumberNonComma(String value, int digit) {

		StringBuilder buff = new StringBuilder("##0");

		if (digit > 0) {
			buff.append(".");
			for (int i = 1; i <= digit; i++) {
				buff.append("0");
			}
		}

		DecimalFormat decimalFormat = new DecimalFormat(buff.toString());

		BigDecimal decimalValue;

		if (Util.isNullOrEmpty(value)) {

			decimalValue = BigDecimal.ZERO;
		} else {

			String strValue = Util.avoidNull(value).trim().replace(",", "");
			decimalValue = Util.isNullOrEmpty(strValue) || !Util.isNumber(strValue) ? BigDecimal.ZERO
					: new BigDecimal(
							strValue);
		}

		return decimalFormat.format(decimalValue);
	}

	/**
	 * �����̃t�H�[�}�b�g.#,##0.####�`��<br>
	 * 
	 * @param value ���l
	 * @param digit �����_����
	 * @return formatStyle �t�H�[�}�b�g�`��
	 */
	public static String formatNumberAbsentZero(Object value, int digit) {

		BigDecimal decimalValue = BigDecimal.ZERO;

		if (value != null) {
			if (value instanceof BigDecimal) {
				decimalValue = (BigDecimal) value;

			} else {
				decimalValue = DecimalUtil.toBigDecimal(String.valueOf(value));
			}
		}

		String format = NumberFormatUtil.makeNumberSharpFormat(digit);
		DecimalFormat decimalFormat = new DecimalFormat(format);

		return decimalFormat.format(decimalValue);
	}

	/**
	 * ���l���w�肳�ꂽ�����̃[���t�B�������Ƃ��ĕԂ�
	 * 
	 * @param number �Ώې��l
	 * @param keta ����
	 * @return �[���t�B������
	 */
	public static String zeroFill(Number number, int keta) {
		StringBuilder buff = new StringBuilder();
		for (int i = 0; i < keta; i++) {
			buff.append("0");
		}

		DecimalFormat formatter = new DecimalFormat(buff.toString());

		return formatter.format(number);
	}

	/**
	 * �[���l�ANULL�l�̏ꍇ�ɋ󕶎���Ԃ�.<br>
	 * �[���l�ANULL�l����Ȃ��ꍇ�̓t�H�[�}�b�g���ĕԂ�.
	 * 
	 * @param num ���l
	 * @param digit �����_
	 * @return �ϊ�����
	 */
	public static String zeroToEmptyFormat(BigDecimal num, int digit) {
		if (DecimalUtil.isNullOrZero(num)) {
			return "";

		} else {
			return NumberFormatUtil.formatNumber(num, digit);
		}
	}

	/**
	 * NULL�l�̏ꍇ��0�Ƃ��ăt�H�[�}�b�g���ĕԂ�.
	 * 
	 * @param num ���l
	 * @param digit �����_
	 * @return �ϊ�����
	 */
	public static String nullToZeroFormat(BigDecimal num, int digit) {
		if (num == null) {
			return NumberFormatUtil.formatNumber(BigDecimal.ZERO, digit);

		} else {
			return NumberFormatUtil.formatNumber(num, digit);
		}
	}

	/**
	 * �w��ʉ݋L���t�^�`���̋��z�t�H�[�}�b�g�ɕϊ�����.<br>
	 * �}�C�i�X�l�̏ꍇ�A�ʉ݃R�[�h�̑O�Ƀ}�C�i�X��t�^.<br>
	 * ��) -USD1,000.00�A-$1,000.00 ��<br>
	 * �[���l�ANULL�l�̏ꍇ�ɋ󕶎���Ԃ�.
	 * 
	 * @param currency �ʉ݋L��
	 * @param num ���l
	 * @param digit �����_
	 * @return �ϊ�����
	 */
	public static String zeroToEmptyCurrencyFormat(String currency, BigDecimal num, int digit) {

		if (DecimalUtil.isNullOrZero(num)) {
			return "";
		}

		BigDecimal amount = num;
		String muinus = "";

		if (num.compareTo(BigDecimal.ZERO) < 0) {
			muinus = "-";
			amount = amount.abs(); // ��Βl�ɕϊ�
		}

		return muinus + currency + zeroToEmptyFormat(amount, digit);
	}

	/**
	 * �w��ʉ݋L���t�^�`���̋��z�t�H�[�}�b�g�ɕϊ�����.<br>
	 * �}�C�i�X�l�̏ꍇ�A�ʉ݃R�[�h�̑O�Ƀ}�C�i�X��t�^.<br>
	 * ��) -USD1,000.00�A-$1,000.00 ��<br>
	 * NULL�l�̏ꍇ�A0�Ƃ��ăt�H�[�}�b�g���ĕԂ�.
	 * 
	 * @param currency �ʉ݋L��
	 * @param num ���l
	 * @param digit �����_
	 * @return �ϊ�����
	 */
	public static String nullToZeroCurrencyFormat(String currency, BigDecimal num, int digit) {

		BigDecimal amount = (DecimalUtil.isNullOrZero(num)) ? BigDecimal.ZERO : num;
		String muinus = "";

		if (amount.compareTo(BigDecimal.ZERO) < 0) {
			muinus = "-";
			amount = amount.abs(); // ��Βl�ɕϊ�
		}

		return muinus + currency + nullToZeroFormat(amount, digit);
	}
}

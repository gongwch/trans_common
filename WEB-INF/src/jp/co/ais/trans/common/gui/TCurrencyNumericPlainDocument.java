package jp.co.ais.trans.common.gui;

import java.math.*;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * VT���l�h�L�������g����
 */
public class TCurrencyNumericPlainDocument extends TNumericPlainDocument {

	/** USD */
	protected boolean USD = false;

	/** JPY */
	protected boolean JPY = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field) {
		super(field);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength) {
		super(field, maxLength);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 * @param digits ����������
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength, int digits) {
		super(field, maxLength, digits);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 * @param digits ����������
	 * @param isPositive true:�}�C�i�X�������Ȃ�(�����̂�)
	 */
	public TCurrencyNumericPlainDocument(JTextComponent field, int maxLength, int digits, boolean isPositive) {
		super(field, maxLength, digits, isPositive);
	}

	/**
	 * USD�̎擾
	 * 
	 * @return USD USD
	 */
	public boolean isUSD() {
		return USD;
	}

	/**
	 * USD�̐ݒ�
	 * 
	 * @param USD USD
	 */
	public void setUSD(boolean USD) {
		this.USD = USD;
	}

	/**
	 * JPY�̎擾
	 * 
	 * @return JPY JPY
	 */
	public boolean isJPY() {
		return JPY;
	}

	/**
	 * JPY�̐ݒ�
	 * 
	 * @param JPY JPY
	 */
	public void setJPY(boolean JPY) {
		this.JPY = JPY;
	}

	@Override
	public void remove(int offs, int len) throws BadLocationException {

		String text = field.getText();
		text = new StringBuilder(text).delete(offs, offs + len).toString();

		// ���l�����͂���Ă���ꍇ�A�`�F�b�N���s��
		if (!Util.isNullOrEmpty(text.replace("-", "").replace(".", ""))) {
			if (!isDefinedNumber(text)) {
				return;
			}
		}

		super.remove(offs, len);
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// ���݂̃e�L�X�g
		String nowText = field.getText();

		if (str.length() == 1) {

			// ���͒l�`�F�b�N : -.0-9�����ȊO�͓��͋֎~
			if (!str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)) {
				return;
			}

			if ("-".equals(str)) {
				// �����̂݁A�擪����Ȃ��A����-������ꍇ�͏��O
				if (isPositive || offs != 0 || nowText.contains("-")) {
					return;
				}
			} else if (".".equals(str)) {
				// �擪�A����.������A�����_����0�A-�̎��̏ꍇ�͏��O
				if (offs == 0 || nowText.contains(".") || decimalPoint == 0
					|| (offs == 1 && "-".equals(nowText.subSequence(0, 1)))) {
					return;
				}
			}

		} else {
			// ","�t�������񂪏㏑�������Ƃ��B
			// 2�����ȏ�paste�����Ƃ��B
			str = checkNumStr(str);
		}

		// ���͌�̃e�L�X�g�ɕϊ�
		String text = new StringBuilder(nowText).insert(offs, str).toString();

		// ���l�����͂���Ă���ꍇ�A�`�F�b�N���s��
		if (!Util.isNullOrEmpty(text.replace("-", "").replace(".", ""))) {
			if (!isDefinedNumber(text)) {
				return;
			}
		}

		super.insertString(offs, str, a);
	}

	/**
	 * ���͉\�����`�F�b�N
	 * 
	 * @param s ���͒l
	 * @return ���͕s�������O
	 */
	@Override
	protected String checkNumStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		String sign = isUSD() ? "$" : "\\";

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// ���͒l�`�F�b�N : -.0-9�����ȊO�͓��͋֎~
			if (!sign.equals(str) && !",".equals(str) && !str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)
				&& !"(".equals(str) && !")".equals(str)) {
				continue;
			}

			// �����̂݃��[�h�̏ꍇ�A- �͏��O
			if (isPositive && "-".equals(str)) {
				continue;
			}

			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * ���͐��l�`�F�b�N
	 * 
	 * @param num ���͐��l
	 * @return ���͐��l���v���p�e�B�ݒ�ɓ��Ă͂܂�ꍇ��true
	 */
	@Override
	protected boolean isDefinedNumber(final String num) {
		// ���l���ǂ���
		String text = num.replace(",", "");

		if (isUSD()) {
			text = text.replace("$", "");
		} else if (isJPY()) {
			text = text.replace("\\", "");
		}

		boolean isMinus = false;

		if (text.contains("(")) {
			isMinus = true;
		}

		text = text.replace("(", "");
		text = text.replace(")", "");

		if (isMinus) {
			text = "-" + text;
		}

		if (!Util.isNumber(text)) {
			return false;
		}

		// �o�C�g���ɂ����͐���(MaxLength���ݒ肳��Ă���ꍇ)
		if (maxLength >= 0) {
			// �P���o�C�g���`�F�b�N
			if (text.replace("-", "").replace(".", "").getBytes().length > maxLength) {
				return false;
			}

			// �����`�F�b�N
			String[] splits = StringUtil.split(text.replace("-", ""), ".");

			// ������
			if (integralPoint != -1 && splits[0].length() > integralPoint) {
				return false;
			}

			// ������
			if (decimalPoint != -1 && (splits.length >= 2 && splits[1].length() > decimalPoint)) {
				return false;
			}

			// �ő�l�A�ŏ��l�`�F�b�N
			BigDecimal val = DecimalUtil.toBigDecimalNULL(text);

			if (maxValue != null && val != null && maxValue.compareTo(val) == -1) {
				return false;
			}
			if (minValue != null && val != null && minValue.compareTo(val) == 1) {
				return false;
			}
		}

		return true;
	}

}

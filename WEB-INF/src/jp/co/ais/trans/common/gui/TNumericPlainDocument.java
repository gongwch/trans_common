package jp.co.ais.trans.common.gui;

import java.math.*;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���l�p��text document
 */
public class TNumericPlainDocument extends PlainDocument {

	/** UID */
	protected final static long serialVersionUID = 0L;

	/** �Ώۃt�B�[���h */
	protected JTextComponent field;

	/** �ő���͐� */
	protected int maxLength;

	/** �����_���� */
	protected int decimalPoint = 0;

	/** ���������� */
	protected int integralPoint = 0;

	/** �����̂� */
	protected boolean isPositive = false;

	/** �ő�l */
	protected BigDecimal maxValue = null;

	/** �ŏ��l */
	protected BigDecimal minValue = null;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 */
	public TNumericPlainDocument(JTextComponent field) {
		this(field, 17);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength) {
		this(field, maxLength, -1);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 * @param digits ����������
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength, int digits) {
		this.field = field;
		this.maxLength = maxLength;

		this.setDecimalPoint(digits);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 * @param digits ����������
	 * @param isPositive true:�}�C�i�X�������Ȃ�(�����̂�)
	 */
	public TNumericPlainDocument(JTextComponent field, int maxLength, int digits, boolean isPositive) {
		this(field, maxLength, digits);
		this.isPositive = isPositive;
	}

	/**
	 * �ő���͐�
	 * 
	 * @return �ő���͐�
	 */
	public int getMaxLength() {
		return maxLength;
	}

	/**
	 * �ő���͐�
	 * 
	 * @param maxLength �ő���͐�
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;

		// maxLength���������ꍇ�A�������̐����͖���
		if (decimalPoint <= 0) {
			this.integralPoint = maxLength;

		} else if (maxLength - decimalPoint > 0) {
			this.integralPoint = maxLength - decimalPoint;
		}
	}

	/**
	 * �����_����
	 * 
	 * @return �����_����
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �����_����
	 * 
	 * @param digits �����_����
	 */
	public void setDecimalPoint(int digits) {
		this.decimalPoint = digits;

		if (decimalPoint <= 0) {
			this.integralPoint = maxLength;

		} else if (maxLength - decimalPoint > 0) {
			this.integralPoint = maxLength - decimalPoint;
		}
	}

	/**
	 * �����̂݃��[�h�ݒ�
	 * 
	 * @param isPositive true�̏ꍇ�A�����̂�
	 */
	public void setPositiveOnly(boolean isPositive) {
		this.isPositive = isPositive;
	}

	/**
	 * �����̂݃��[�h�擾
	 * 
	 * @return isPositive true�̏ꍇ�A�����̂�
	 */
	public boolean isPositive() {
		return isPositive;
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
	protected String checkNumStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// ���͒l�`�F�b�N : -.0-9�����ȊO�͓��͋֎~
			if (!",".equals(str) && !str.matches("[0-9]") && !"-".equals(str) && !".".equals(str)) {
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
	protected boolean isDefinedNumber(final String num) {
		// ���l���ǂ���
		String text = num.replace(",", "");
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

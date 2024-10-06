package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���t�p��text document
 */
public class TDatePlainDocument extends PlainDocument {

	/** �N(YYYY) */
	public static final int TYPE_Y = 1;

	/** �N��(YYYY/MM) */
	public static final int TYPE_YM = 2;

	/** �N����(YYYY/MM/DD) */
	public static final int TYPE_YMD = 3;

	/** �ő包�� */
	protected int maxLength;

	/** �Ώۃt�B�[���h */
	protected JTextComponent field;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 */
	public TDatePlainDocument(JTextComponent field) {
		this(field, TYPE_YMD);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param type ���t�^�C�v(TYPE_XX)
	 */
	public TDatePlainDocument(JTextComponent field, int type) {
		this.field = field;

		switch (type) {
			case TYPE_Y:
				maxLength = 4;
				break;

			case TYPE_YM:
				maxLength = 7;
				break;

			default:
				maxLength = 10;
				break;
		}
	}

	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// ���݂̃e�L�X�g
		String nowText = field.getText();

		if (str.length() == 1) {

			// ���͒l�`�F�b�N : /.1-9�����ȊO�͓��͋֎~
			if (!str.matches("[0-9]") && !"/".equals(str)) {
				return;
			}

		} else {
			// 2�����ȏ�paste�����Ƃ��B
			str = checkDateStr(str);
		}

		// ���͌�̃e�L�X�g�ɕϊ�
		String text = new StringBuilder(nowText).insert(offs, str).toString();

		if (!isDefinedNumber(text)) {
			return;
		}

		super.insertString(offs, str, a);
	}

	/**
	 * ���͉\�����`�F�b�N
	 * 
	 * @param s ���͒l
	 * @return ���͕s�������O
	 */
	protected String checkDateStr(final String s) {
		StringBuffer sb = new StringBuffer("");

		for (int cnt = 0; cnt < s.length(); cnt++) {

			String str = s.substring(cnt, cnt + 1);

			// ���͒l�`�F�b�N : /,0-9�����ȊO�͓��͋֎~
			if (!str.matches("[0-9]") && !"/".equals(str)) {
				continue;
			}
			sb.append(str);
		}
		return sb.toString();
	}

	/**
	 * ���͐��l�`�F�b�N
	 * 
	 * @param text ���͐��l
	 * @return ���͐��l���v���p�e�B�ݒ�ɓ��Ă͂܂�ꍇ��true
	 */
	protected boolean isDefinedNumber(final String text) {

		// �P���o�C�g���`�F�b�N
		if (text.getBytes().length > maxLength) {
			return false;

		} else if (text.getBytes().length == maxLength) {
			return DateUtil.isDate(text);
		}

		return true;
	}

}
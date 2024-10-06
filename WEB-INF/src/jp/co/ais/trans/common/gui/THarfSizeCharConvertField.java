package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���p�ϊ��e�L�X�g�t�B�[���h�i�e�L�X�g�t�B�[���h�g���j
 */
public class THarfSizeCharConvertField extends TTextField {

	/** �S�p�J�^�J�i */
	public static final char[] ZENKAKU_KATAKANA = { '�@', '�A', '�B', '�C', '�D', '�E', '�F', '�G', '�H', '�I', '�J', '�K', '�L',
			'�M', '�N', '�O', '�P', '�Q', '�R', '�S', '�T', '�U', '�V', '�W', '�X', '�Y', '�Z', '�[', '�\', '�]', '�^', '�_', '�`', '�a',
			'�b', '�c', '�d', '�e', '�f', '�g', '�h', '�i', '�j', '�k', '�l', '�m', '�n', '�o', '�p', '�q', '�r', '�s', '�t', '�u', '�v',
			'�w', '�x', '�y', '�z', '�{', '�|', '�}', '�~', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��', '��',
			'��', '��', '��', '��', '��', '��', '��', '��', '��', '��' };

	/** ���p�J�^�J�i */
	public static final String[] HANKAKU_KATAKANA = { "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "��", "�",
			"��", "�", "��", "�", "��", "�", "��", "�", "��", "�", "��", "�", "��", "�", "��", "�", "��", "�", "��",
			"�", "��", "�", "�", "��", "�", "��", "�", "��", "�", "�", "�", "�", "�", "�", "��", "��", "�", "��", "��",
			"�", "��", "��", "�", "��", "��", "�", "��", "��", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�",
			"�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "�", "��", "�", "�" };

	/** �S�p�J�^�J�i��� */
	public static final char ZENKAKU_KATAKANA_FIRST_CHAR = ZENKAKU_KATAKANA[0];

	/** �S�p�J�^�J�i�ŏI�� */
	public static final char ZENKAKU_KATAKANA_LAST_CHAR = ZENKAKU_KATAKANA[ZENKAKU_KATAKANA.length - 1];

	/**
	 * �R���X�g���N�^.
	 */
	public THarfSizeCharConvertField() {
		super();
		// ���K�\���ł̓��͐���
		super.setRegex("[ -/:-@\\[-\\`\\{-\\~��-߃@-��]");
	}

	/**
	 * ���͐���
	 * 
	 * @return PlainDocument
	 */
	@Override
	protected PlainDocument createPlainDocument() {
		return new TStringPlainDocument(this) {

			/**
			 * @see PlainDocument#insertString(int, String, AttributeSet)
			 */
			@Override
			public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

				// �o�C�g���ɂ����͐���
				if (maxLength >= 0) {

					if (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute)) {

						// ���݂̃e�L�X�g
						String nowText = field.getText();
						if (nowText.getBytes().length + str.getBytes().length > maxLength) {

							if (str.getBytes().length > 2) {
								str = zenkakuToHankaku(str);
							}

							super.insertString(offs, StringUtil.leftBX(str, maxLength - nowText.getBytes().length), a);
							return;
						}

						str = zenkakuToHankaku(str);
					}
				}

				super.insertString(offs, str, a);
			}
		};
	}

	/**
	 * ���p�ϊ�
	 * 
	 * @param c
	 * @return ���p�J�^�J�i
	 */
	public static String zenkakuKatakanaToHankakuKatakana(char c) {
		if (c >= ZENKAKU_KATAKANA_FIRST_CHAR && c <= ZENKAKU_KATAKANA_LAST_CHAR) {
			return HANKAKU_KATAKANA[c - ZENKAKU_KATAKANA_FIRST_CHAR];
		} else {
			return String.valueOf(c);
		}
	}

	/**
	 * �S�p�J�^�J�i���ɔ��p�J�^�J�i�ϊ����郁�\�b�h
	 * 
	 * @param s ���͂̕���
	 * @return ���p�J�^�J�i
	 */
	public static String zenkakuToHankaku(String s) {
		StringBuffer sb = new StringBuffer(s);
		StringBuffer sb2 = new StringBuffer();
		for (int i = 0; i < sb.length(); i++) {
			char c = sb.charAt(i);

			if (c >= '��' && c <= '��') {
				sb.setCharAt(i, (char) (c - '��' + 'a'));
			} else if (c >= '�`' && c <= '�y') {
				sb.setCharAt(i, (char) (c - '�`' + 'A'));
			} else if (c >= '�O' && c <= '�X') {
				sb.setCharAt(i, (char) (c - '�O' + '0'));
			} else if (c == '�[') {
				sb.setCharAt(i, (char) (c - '�[' + '�'));
			}
		}

		for (int i = 0; i < sb.length(); i++) {
			char originalChar = sb.charAt(i);
			String convertedChar = zenkakuKatakanaToHankakuKatakana(originalChar);
			sb2.append(convertedChar);
		}

		return sb2.toString();
	}
}

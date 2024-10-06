package jp.co.ais.trans.common.gui;

import javax.swing.text.*;

import jp.co.ais.trans.common.util.*;

/**
 * ������p��text document
 */
public class TStringPlainDocument extends PlainDocument {

	/** �Ώۃt�B�[���h */
	protected JTextComponent field;

	/** �X�y�[�X���͂������邩�ǂ��� */
	protected boolean isAllowedSpace = true;

	/** IME���[�h(�S�p����) */
	protected boolean isImeMode = true;

	/** �ő咷 */
	protected int maxLength = 128;

	/** �֑��������X�g */
	protected String[] prohibitionWords = new String[0];

	/** ���͉\�����K�\������ */
	protected String regex;

	/**
	 * �R���X�g���N�^.<br>
	 * �f�t�H���g�̍ő���͐���128
	 * 
	 * @param field �Ώۃt�B�[���h
	 */
	public TStringPlainDocument(JTextComponent field) {
		this(field, 128, true);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 */
	public TStringPlainDocument(JTextComponent field, int maxLength) {
		this(field, maxLength, true);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param field �Ώۃt�B�[���h
	 * @param maxLength �ő���͐�
	 * @param imeFlag IME����t���O
	 */
	public TStringPlainDocument(JTextComponent field, int maxLength, boolean imeFlag) {
		this.field = field;
		this.maxLength = maxLength;
		field.enableInputMethods(imeFlag);
	}

	/**
	 * @see PlainDocument#insertString(int, String, AttributeSet)
	 */
	@Override
	public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {

		// �X�y�[�X���͕s�Ȃ�X�y�[�X���폜
		if (!isAllowedSpace) {
			str = str.replace(" ", "");
		}

		// �^�u�����֎~
		str = str.replace("\t", "");

		// IME���[�hOFF
		if (!isImeMode) {
			for (int i = 0; i < str.length(); i++) {
				String s = str.substring(i, i + 1);

				// �S�p�֎~�őS�p���܂܂�Ă���ꍇ��NG
				if (s.getBytes().length > 1) {
					return;
				}

				// ���K�\���`�F�b�N : ���p�J�i��NG
				if (s.matches("[�-�]")) {
					return;
				}
			}
		}

		if (a == null || !a.isDefined(StyleConstants.ComposedTextAttribute)) {

			// ���K�\���w��ɂ����͕����̐���
			if (regex != null && !"".equals(regex)) {
				String replaceStr = str;
				for (int i = 0; i < str.length(); i++) {
					String s = str.substring(i, i + 1);

					// ���K�\���`�F�b�N : �w�蕶���ȊO�͍폜
					if (!s.matches(regex)) {
						replaceStr = replaceStr.replace(s, "");
					}
				}
				str = replaceStr;
			}

			// �֑��������w�肳��Ă���ꍇ�͍폜
			for (String word : prohibitionWords) {
				str = str.replace(word, "");
			}
		}

		// �o�C�g���ɂ����͐���
		if (maxLength >= 0) {

			// IME�œ��͎��ɁA
			// ���m��(ComposedText)�̂Ƃ��͂P�����Âǉ��B
			// �m�莞�ɖ��m�肪�폜����A�ēx�S�������ǉ������
			// ���������͍ēx�ǉ����ꂽ�Ƃ��Ƀ`�F�b�N����B

			if ((a != null) && (a.isDefined(StyleConstants.ComposedTextAttribute))) {
				super.insertString(offs, str, a);
				return;
			}

			// ���݂̃e�L�X�g
			String nowText = field.getText();
			if (nowText.getBytes().length + str.getBytes().length > maxLength) {

				super.insertString(offs, StringUtil.leftBX(str, maxLength - nowText.getBytes().length), a);
				return;
			}
		}

		super.insertString(offs, str, a);
	}

	/**
	 * IME���[�h�ݒ�.true�Ȃ�S�p��
	 * 
	 * @return true:�S�p�܂�
	 */
	public boolean isImeMode() {
		return this.isImeMode;
	}

	/**
	 * IME���[�h�ݒ�.true�Ȃ�S�p��
	 * 
	 * @param imeMode true:�S�p�܂�
	 */
	public void setImeMode(boolean imeMode) {
		this.isImeMode = imeMode;
	}

	/**
	 * space���͋���
	 * 
	 * @return true:����
	 */
	public boolean isAllowedSpace() {
		return this.isAllowedSpace;
	}

	/**
	 * space���͋���
	 * 
	 * @param isAllowedSpace true:����
	 */
	public void setAllowedSpace(boolean isAllowedSpace) {
		this.isAllowedSpace = isAllowedSpace;
	}

	/**
	 * �ő包��
	 * 
	 * @return �ő包��
	 */
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * �ő包��
	 * 
	 * @param maxLength �ő包��
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}

	/**
	 * �֑�����(���͂����Ȃ�����)�̐ݒ�
	 * 
	 * @param words �֑��������X�g
	 */
	public void setProhibitionWords(String... words) {
		this.prohibitionWords = words;
	}

	/**
	 * ���͉\�����𐳋K�\���Ŏw�肷��.
	 * 
	 * @param regex ���K�\������
	 */
	public void setRegex(String regex) {
		this.regex = regex;
	}
}

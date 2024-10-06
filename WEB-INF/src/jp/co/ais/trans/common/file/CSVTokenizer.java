package jp.co.ais.trans.common.file;

/*
 * ���̃N���X�͉��L�̈�������̍쐬���ꂽ���̂ł� ========================================== �����F�� �t���k���w���Z����w�}���� tomoharu@wakhok.ac.jp
 * http://www.wakhok.ac.jp/~tomoharu/ ==========================================
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * 1�s��CSV�`���̃f�[�^����͂��A���ꂼ��̍��ڂɕ�������N���X�B CSV�`���ɑΉ����� java.util.StringTokenizer �̂悤�Ȃ��́B
 * 
 * @version 1.0.1 (1999.4.6)
 * @author TAMURA Kent <kent@muraoka.info.waseda.ac.jp>
 * @author ANDOH Tomoharu <tomoharu@wakhok.ac.jp>
 */

public class CSVTokenizer implements Enumeration {

	/** COPYRIGHT */
	public static final String copyright = "Copyright 1997 TAMURA Kent" + "\n" + "Copyright 1999 ANDOH Tomoharu";

	private String source; // �ΏۂƂȂ镶����

	private int currentPosition; // ���̓ǂݏo���ʒu

	private int maxPosition;

	/**
	 * CSV �`���� line ����͂��� CSVTokenizer �̃C���X�^���X�� �쐬����B
	 * 
	 * @param line CSV�`���̕����� ���s�R�[�h���܂܂Ȃ��B
	 */
	public CSVTokenizer(String line) {
		source = line;
		currentPosition = 0;
		maxPosition = line.length();
	}

	/**
	 * ���̃J���}������ʒu��Ԃ��B �J���}���c���Ă��Ȃ��ꍇ�� nextComma() == maxPosition �ƂȂ�B �܂��Ō�̍��ڂ���̏ꍇ�� nextComma() == maxPosition �ƂȂ�B
	 * 
	 * @param ind �������J�n����ʒu
	 * @return ���̃J���}������ʒu�B�J���}���Ȃ��ꍇ�́A������� �����̒l�ƂȂ�B
	 */
	private int nextComma(int ind) {
		boolean inquote = false;
		while (ind < maxPosition) {
			char ch = source.charAt(ind);
			if (!inquote && ch == ',') {
				break;
			} else if ('"' == ch) {
				inquote = !inquote; // ""�̏����������OK
			}
			ind++;
		}
		return ind;
	}

	/**
	 * �܂܂�Ă��鍀�ڂ̐���Ԃ��B
	 * 
	 * @return �܂܂�Ă��鍀�ڂ̐�
	 */
	public int countTokens() {
		int i = 0;
		int ret = 1;
		while ((i = nextComma(i)) < maxPosition) {
			i++;
			ret++;
		}
		return ret;
	}

	/**
	 * ���̍��ڂ̕������Ԃ��B
	 * 
	 * @return ���̍���
	 * @exception NoSuchElementException ���ڂ��c���Ă��Ȃ��Ƃ�
	 */
	public String nextToken() {
		// ">=" �ł͖����̍��ڂ𐳂��������ł��Ȃ��B
		// �����̍��ڂ���i�J���}��1�s���I���j�ꍇ�A��O����������
		// ���܂��̂ŁB
		if (currentPosition > maxPosition) throw new NoSuchElementException(toString() + "#nextToken");

		int st = currentPosition;
		currentPosition = nextComma(currentPosition);

		StringBuffer strb = new StringBuffer();
		while (st < currentPosition) {
			char ch = source.charAt(st++);
			if (ch == '"') {
				// "���P�ƂŌ��ꂽ�Ƃ��͉������Ȃ�
				if ((st < currentPosition) && (source.charAt(st) == '"')) {
					strb.append(ch);
					st++;
				}
			} else {
				strb.append(ch);
			}
		}
		currentPosition++;
		return new String(strb);
	}

	/**
	 * <code>nextToken</code>���\�b�h�Ɠ����ŁA ���̍��ڂ̕������Ԃ��B<br>
	 * �������Ԓl�́AString�^�ł͂Ȃ��AObject�^�ł���B<br>
	 * java.util.Enumeration���������Ă��邽�߁A���̃��\�b�h�� ����B
	 * 
	 * @return ���̍���
	 * @exception NoSuchElementException ���ڂ��c���Ă��Ȃ��Ƃ�
	 * @see java.util.Enumeration
	 */
	public Object nextElement() {
		return nextToken();
	}

	/**
	 * �܂����ڂ��c���Ă��邩�ǂ������ׂ�B
	 * 
	 * @return �܂����ڂ��̂����Ă���Ȃ�true
	 */
	public boolean hasMoreTokens() {
		// "<=" �łȂ��A"<" ���Ɩ����̍��ڂ𐳂��������ł��Ȃ��B
		return (nextComma(currentPosition) <= maxPosition);
	}

	/**
	 * <code>hasMoreTokens</code>���\�b�h�Ɠ����ŁA �܂����ڂ��c���Ă��邩�ǂ������ׂ�B<br>
	 * java.util.Enumeration���������Ă��邽�߁A���̃��\�b�h�� ����B
	 * 
	 * @return �܂����ڂ��̂����Ă���Ȃ�true
	 * @see java.util.Enumeration
	 */
	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	/**
	 * �C���X�^���X�̕�����\����Ԃ��B
	 * 
	 * @return �C���X�^���X�̕�����\���B
	 */
	public String toString() {
		return "CSVTokenizer(\"" + source + "\")";
	}
}

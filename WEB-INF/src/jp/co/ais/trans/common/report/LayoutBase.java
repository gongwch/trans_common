package jp.co.ais.trans.common.report;

import java.util.*;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���[�K��N���X
 */
public abstract class LayoutBase {

	/** ���� * */
	protected String lang;

	/**
	 * ���[�N���X���\�z
	 * 
	 * @param lang ����R�[�h
	 */
	public LayoutBase(String lang) {
		this.setLanguageCode(lang);
	}

	/**
	 * �w��ID�̕������擾����.
	 * 
	 * @param wordId ����ID
	 * @return ����
	 */
	protected String getWord(String wordId) {
		return MessageUtil.getWord(lang, wordId);
	}

	/**
	 * �w��ID�̕������擾����.(����)
	 * 
	 * @param wordIds ����ID�̃��X�g
	 * @return �����Ŏw�肳�ꂽ����ID�ɑ΂���P��̃��X�g
	 */
	protected String[] getWordList(String[] wordIds) {
		return MessageUtil.getWordList(lang, wordIds);
	}

	/**
	 * ���b�Z�[�W��Ԃ�. �w��P��ID�A�܂��͒P����o�C���h.
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param bindIds �P��ID�A�܂��́A�P��̃��X�g
	 * @return �ϊ����ꂽ���b�Z�[�W
	 */
	public String getMessage(String messageID, Object... bindIds) {

		return MessageUtil.convertMessage(lang, messageID, bindIds);
	}

	/**
	 * Date���u�N�����v�`��������ɕϊ�
	 * 
	 * @param date �L���X�g����Date
	 * @return �u�N�����v�`��String
	 */
	protected String formatYMD(Date date) {
		return DateUtil.toYMDString(lang, date);
	}

	/**
	 * Date���u�N���v�`��������ɕϊ�
	 * 
	 * @param date �L���X�g����Date
	 * @return �u�N���v�`��String
	 */
	protected String formatYM(Date date) {
		return DateUtil.toYMString(lang, date);
	}

	/**
	 * Date���u�N���v�`��������ɕϊ�
	 * 
	 * @param date �L���X�g����Date
	 * @return �u�N���v�`��String
	 */
	protected String formatMD(Date date) {
		return DateUtil.toString(lang, date, DateUtil.TYPE_DATE_FORMAT_MD);
	}

	/**
	 * Date���u�����b�v�`��������ɕϊ�
	 * 
	 * @param date �L���X�g����Date
	 * @return �u�����b�v�`��String
	 */
	protected String formatHMS(Date date) {
		return DateUtil.toHMSString(lang, date);
	}

	/**
	 * �t�H���g�̎擾
	 * 
	 * @return �t�H���g
	 */
	protected abstract String getFont();

	/**
	 * ����R�[�h�̐ݒ�
	 * 
	 * @param lang ����R�[�h
	 */
	public void setLanguageCode(String lang) {
		this.lang = lang;
	}

	/**
	 * ����R�[�h�̎擾
	 * 
	 * @return ����R�[�h
	 */
	public String getLanguageCode() {
		return this.lang;
	}

	/**
	 * ���b�Z�[�W(����)�擾
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @return �ϊ���̃��b�Z�[�W
	 */
	public String getShortWord(String messageId) {
		return MessageUtil.getShortWord(lang, messageId);
	}

	/**
	 * �w�茎�̒P��̎擾
	 * 
	 * @param month �w�茎
	 * @return �w�茎�̒P��<br>
	 *         C03406 1�� Jan.<br>
	 *         C03407 2�� Feb.<br>
	 *         C03408 3�� Mar.<br>
	 *         C03409 4�� Apr.<br>
	 *         C03410 5�� May<br>
	 *         C03411 6�� Jun.<br>
	 *         C03412 7�� Jul.<br>
	 *         C03413 8�� Aug.<br>
	 *         C03414 9�� Sept.<br>
	 *         C03415 10�� Oct.<br>
	 *         C03416 11�� Nov.<br>
	 *         C03417 12�� Dec.<br>
	 */
	public String getMonthWord(int month) {

		String monthWord = "";

		switch (month) {
			case 1:
				monthWord = getShortWord("C03406"); // 1�� Jan.
				break;
			case 2:
				monthWord = getShortWord("C03407"); // 2�� Feb.
				break;
			case 3:
				monthWord = getShortWord("C03408"); // 3�� Mar.
				break;
			case 4:
				monthWord = getShortWord("C03409"); // 4�� Apr.
				break;
			case 5:
				monthWord = getShortWord("C03410"); // 5�� May
				break;
			case 6:
				monthWord = getShortWord("C03411"); // 6�� Jun.
				break;
			case 7:
				monthWord = getShortWord("C03412"); // 7�� Jul.
				break;
			case 8:
				monthWord = getShortWord("C03413"); // 8�� Aug.
				break;
			case 9:
				monthWord = getShortWord("C03414"); // 9�� Sept.
				break;
			case 10:
				monthWord = getShortWord("C03415"); // 10�� Oct.
				break;
			case 11:
				monthWord = getShortWord("C03416"); // 11�� Nov.
				break;
			case 12:
				monthWord = getShortWord("C03417"); // 12�� Dec.
				break;
		}
		return monthWord;
	}

}

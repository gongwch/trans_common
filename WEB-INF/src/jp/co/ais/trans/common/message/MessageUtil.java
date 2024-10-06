package jp.co.ais.trans.common.message;

import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * ���b�Z�[�W�擾�p���[�e�B���e�B
 */
public class MessageUtil {

	/**
	 * ���b�Z�[�W�擾
	 * 
	 * @param lang ����R�[�h
	 * @param messageId ���b�Z�[�WID
	 * @return ���b�Z�[�W
	 */
	public static Message getMessage(String lang, String messageId) {
		return MessageManager.getInstance(lang).getMessage(messageId);
	}

	/**
	 * ���b�Z�[�W�J�e�S���擾
	 * 
	 * @param lang ����R�[�h
	 * @param messageId �P��ID
	 * @return �J�e�S��
	 */
	public static int getCategory(String lang, String messageId) {
		return MessageManager.getInstance(lang).getMessage(messageId).getCategory();
	}

	/**
	 * �P��擾
	 * 
	 * @param lang ����R�[�h
	 * @param wordId �P��ID
	 * @return �P��
	 */
	public static String getWord(String lang, String wordId) {
		return WordManager.getInstance(lang).getWord(wordId);
	}

	/**
	 * �w��ID�̕������擾����.(����)
	 * 
	 * @param lang ����R�[�h
	 * @param wordIds �P��ID�̃��X�g
	 * @return �����Ŏw�肳�ꂽ����ID�ɑ΂���P��̃��X�g
	 */
	public static String[] getWordList(String lang, String[] wordIds) {
		return getWordList(lang, (Object[]) wordIds);
	}

	/**
	 * �w��ID�̕������擾����.(����)<br>
	 * �P��ID�ȊO�̒l���܂ޏꍇ�A������𗘗p.
	 * 
	 * @param lang ����R�[�h
	 * @param words �P��(����ID�܂�)�̃��X�g
	 * @return �����Ŏw�肳�ꂽ����ID�ɑ΂���P��̃��X�g
	 */
	public static String[] getWordList(String lang, Object[] words) {
		ArrayList<String> list = new ArrayList<String>(words.length);

		WordManager manager = WordManager.getInstance(lang);

		for (int i = 0; i < words.length; i++) {
			list.add(manager.getWord(Util.avoidNull(words[i])));
		}

		return list.toArray(new String[list.size()]);
	}

	/**
	 * �w��ID�̏ȗ��������擾����.
	 * 
	 * @param lang ����R�[�h
	 * @param wordId �P��ID
	 * @return �����Ŏw�肳�ꂽ����ID�ɑ΂���P��
	 */
	public static String getShortWord(String lang, String wordId) {
		return getWord(lang + ("en".equals(lang) ? "_s" : ""), wordId);
	}

	/**
	 * �w��ID�̏ȗ��������擾����.(����)
	 * 
	 * @param lang ����R�[�h
	 * @param wordIds �P��ID�̃��X�g
	 * @return �����Ŏw�肳�ꂽ����ID�ɑ΂���P��̃��X�g
	 */
	public static String[] getShortWordList(String lang, String[] wordIds) {
		return getWordList(lang + ("en".equals(lang) ? "_s" : ""), wordIds);
	}

	/**
	 * ���b�Z�[�W���擾����.<br>
	 * �P��ID�ȊO�̒l���܂ޏꍇ�A������𗘗p.
	 * 
	 * @param lang ����R�[�h
	 * @param messageID ���b�Z�[�WID
	 * @param words �P��(����ID�܂�)�̃��X�g
	 * @return �ϊ��チ�b�Z�[�W
	 */
	public static String convertMessage(String lang, String messageID, Object... words) {

		Message msg = getMessage(lang, messageID);

		return msg.toString((Object[]) getWordList(lang, words));
	}

	/**
	 * ���b�Z�[�W���擾����
	 * 
	 * @param lang ����R�[�h
	 * @param messageID ���b�Z�[�WID
	 * @param wordIds �o�C���h�P��ID�̃��X�g
	 * @return �ϊ��チ�b�Z�[�W
	 */
	public static String convertMessage(String lang, String messageID, String... wordIds) {
		return convertMessage(lang, messageID, (Object[]) wordIds);
	}

}

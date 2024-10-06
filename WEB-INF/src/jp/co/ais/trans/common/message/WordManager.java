package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �P����Ǘ�����
 */
public class WordManager {

	/** �C���X�^���X */
	private static Map instances;

	/** �t�@�C���� */
	private static String fileName = "C:/ais/Word.xml";

	/** �t�@�C����:�J�X�^�}�C�Y�� */
	private static String patchFileName = "C:/ais/Word_Customize.xml";

	/** ����R�[�h */
	protected String lang;

	/** ���b�Z�[�W���X�g */
	protected Map list;

	static {
		// �}���`�X���b�h����
		instances = Collections.synchronizedMap(new HashMap<String, WordManager>());

		if (!new File(fileName).exists()) {
			fileName = "Word.xml";
			patchFileName = "Word_Customize.xml";
		}
	}

	/**
	 * �w�肵���P��ID�A�����Ń}�l�[�W�����\�z����. �����ւ��\.
	 * 
	 * @param lang ����R�[�h
	 * @param wordMap �P��ID�A�����}�b�v
	 */
	public static void initManager(String lang, Map wordMap) {

		WordManager instance = new WordManager(lang);
		instance.list = wordMap;
		instances.put(lang, instance);
	}

	/**
	 * ����R�[�h�ɑΉ������C���X�^���X���擾����
	 * 
	 * @param lang ����R�[�h ja:���{�� �Ȃ�
	 * @return �}�l�[�W��
	 */
	public static WordManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {
				WordManager instance = new WordManager(lang);
				// XML�ǂݍ���
				instance.list = MessageBuilder.buildMessageList(lang, fileName);
				try {
					// �J�X�^�}�C�Y������΁A�㏑��
					instance.list.putAll(MessageBuilder.buildMessageList(lang, patchFileName));
				} catch (Throwable ex) {
					// �����Ȃ�
				}

				instances.put(lang, instance);
			}
			return (WordManager) instances.get(lang);

		} catch (IOException e) {
			throw new TEnvironmentException(e);
		}
	}

	/**
	 * �L���b�V�����b�Z�[�W���N���A����.
	 */
	public static void clear() {
		instances.clear();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param lang ����R�[�h
	 */
	private WordManager(String lang) {
		this.lang = lang;
		this.list = new TreeMap();
	}

	/**
	 * �P��ID�ɑΉ����������̎擾
	 * 
	 * @param wordID �P��ID
	 * @return ����
	 */
	public String getWord(String wordID) {

		if (Util.isNullOrEmpty(wordID) || !list.containsKey(wordID)) {
			return wordID;
		}
		return (String) list.get(wordID);
	}

	/**
	 * �S�P��̎擾
	 * 
	 * @return �P�ꃊ�X�g
	 */
	public Map getList() {
		return list;
	}
}
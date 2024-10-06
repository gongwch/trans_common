package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * �}�l�[�W�����Ǘ�����
 */
public class FormatManager {

	/** �C���X�^���X */
	private static Map instances;

	/** ����R�[�h */
	protected String lang;

	/** �t�H�[�}�b�g���X�g */
	protected Map list;

	static {
		// �}���`�X���b�h����
		instances = Collections.synchronizedMap(new HashMap<String, FormatManager>());
	}

	/**
	 * �w�肵���t�H�[�}�b�gID�A�����Ń}�l�[�W�����\�z����. �����ւ��\.
	 * 
	 * @param lang ����R�[�h
	 * @param wordMap �P��ID�A�����}�b�v
	 */
	public static void initManager(String lang, Map wordMap) {

		FormatManager instance = new FormatManager(lang);
		instance.list = wordMap;
		instances.put(lang, instance);
	}

	/**
	 * ����R�[�h�ɑΉ������C���X�^���X���擾����
	 * 
	 * @param lang ����R�[�h ja:���{�� �Ȃ�
	 * @return �}�l�[�W��
	 */
	public static FormatManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {
				FormatManager instance = new FormatManager(lang);
				// XML�ǂݍ���
				instance.list = MessageBuilder.buildMessageList(lang, "Format.xml");

				instances.put(lang, instance);
			}

			return (FormatManager) instances.get(lang);
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
	private FormatManager(String lang) {
		this.lang = lang;
		this.list = new TreeMap();
	}

	/**
	 * �L�[�ɑΉ������t�H�[�}�b�g�̎擾
	 * 
	 * @param key �L�[
	 * @return �t�H�[�}�b�g
	 */
	public String get(String key) {

		if (Util.isNullOrEmpty(key) || !list.containsKey(key)) {
			return "";
		}
		return (String) list.get(key);
	}

	/**
	 * �S�t�H�[�}�b�g�̎擾
	 * 
	 * @return �t�H�[�}�b�g���X�g
	 */
	public Map getList() {
		return list;
	}
}
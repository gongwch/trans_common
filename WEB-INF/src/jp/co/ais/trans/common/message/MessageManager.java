package jp.co.ais.trans.common.message;

import java.util.*;
import java.io.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���b�Z�[�W���Ǘ�����
 * 
 * @version 1.0
 * @created 17-11-2006 20:45:15
 */
public class MessageManager {

	/** �C���X�^���X */
	private static Map instances;

	/** �t�@�C���� */
	private static String fileName = "C:/ais/Message.xml";

	/** �t�@�C����:�J�X�^�}�C�Y�� */
	private static String patchFileName = "C:/ais/Message_Customize.xml";

	/** ����R�[�h */
	protected String lang;

	/** ���b�Z�[�W�����X�g */
	protected Map messageStrList;

	/** ���b�Z�[�W�N���X���X�g */
	protected Map messageList;

	static {
		// �}���`�X���b�h����
		instances = Collections.synchronizedMap(new HashMap<String, MessageManager>());

		if (!new File(fileName).exists()) {
			fileName = "Message.xml";
			patchFileName = "Message_Customize.xml";
		}
	}

	/**
	 * �w�肵�����b�Z�[�WID�A�����Ń}�l�[�W�����\�z����. �����ւ��\.
	 * 
	 * @param lang ����R�[�h
	 * @param msgMap ���b�Z�[�WID�A�����}�b�v
	 */
	public static void initManager(String lang, Map msgMap) {

		MessageManager instance = new MessageManager(lang);
		instance.makeMessageList(msgMap);
		instances.put(lang, instance);
	}

	/**
	 * ����R�[�h�ɑΉ������C���X�^���X���擾����
	 * 
	 * @param lang ����R�[�h ja:���{�� �Ȃ�
	 * @return �}�l�[�W��
	 */
	public static MessageManager getInstance(String lang) {
		try {
			if (!instances.containsKey(lang)) {

				// XML�ǂݍ���
				MessageManager instance = new MessageManager(lang);

				Map map = MessageBuilder.buildMessageList(lang, fileName);

				try {
					// �J�X�^�}�C�Y������΁A�㏑��
					map.putAll(MessageBuilder.buildMessageList(lang, patchFileName));
				} catch (Throwable ex) {
					// �����Ȃ�
				}

				instance.makeMessageList(map);

				instances.put(lang, instance);
			}
			return (MessageManager) instances.get(lang);

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
	private MessageManager(String lang) {
		this.lang = lang;
		this.messageList = new TreeMap();
	}

	/**
	 * ���b�Z�[�W���\�z����.
	 * 
	 * @param map ���b�Z�[�WID�A����
	 */
	private void makeMessageList(Map map) {
		messageStrList = map;

		// Message(�C���X�^���X)�����
		for (Iterator keys = map.keySet().iterator(); keys.hasNext();) {
			String messageID = (String) keys.next();
			String messageStr = (String) map.get(messageID);

			Message msg = new Message(messageID, messageStr);

			// �����Message�𔠂ɓ����
			messageList.put(messageID, msg);
		}
	}

	/**
	 * ���b�Z�[�WID�ɑΉ��������b�Z�[�W�̎擾
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @return ID�ɑΉ��������b�Z�[�W�N���X. �Y��ID�����݂��Ȃ��ꍇ�A�[���I��ID�𕶎��Ƃ������b�Z�[�W�N���X���Ԃ�.
	 */
	public Message getMessage(String messageID) {

		// ID�ɕR�Â�Message�N���X��Ԃ�
		if (Util.isNullOrEmpty(messageID) || !messageList.containsKey(messageID)) {

			// �o�^�������ꍇ�́AID�����̂܂ܕԂ�
			return new Message(Util.avoidNull(messageID));
		}

		return (Message) messageList.get(messageID);
	}

	/**
	 * ���ƂȂ郁�b�Z�[�W�����񃊃X�g�擾
	 * 
	 * @return ���b�Z�[�W�����񃊃X�g
	 */
	public Map getList() {
		return messageStrList;
	}
}
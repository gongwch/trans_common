package jp.co.ais.trans.common.message;

import java.text.*;
import java.util.*;

import javax.swing.*;

/**
 * ���b�Z�[�W�{��
 */
public class Message {

	/** �G���[�敪 */
	public static final int ERROR = JOptionPane.ERROR_MESSAGE;

	/** �x�� */
	public static final int WORNING = JOptionPane.WARNING_MESSAGE;

	/** �C���t�H���[�V���� */
	public static final int INFORMATION = JOptionPane.INFORMATION_MESSAGE;

	/** �N�G�X�`���� */
	public static final int QUESTION = JOptionPane.QUESTION_MESSAGE;

	/** ���b�Z�[�WID */
	private String messageID;

	/** �J�e�S�� */
	private int category = INFORMATION;

	/** ���b�Z�[�W */
	private String message;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message ���b�Z�[�W
	 */
	public Message(String message) {
		this.messageID = message;
		this.message = message;
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param messageStr ���b�Z�[�W
	 */
	public Message(String messageID, String messageStr) {
		this.messageID = messageID;
		this.message = messageStr;

		String head = messageID.substring(0, 1);

		if ("E".equals(head)) {
			category = ERROR; // �G���[
		} else if ("W".equals(head)) {
			category = WORNING; // �x��
		} else if ("Q".equals(head)) {
			category = QUESTION; // �N�G�X�`����
		} else {
			category = INFORMATION; // �C���t�H���[�V����
		}
	}

	/**
	 * ���b�Z�[�WID���擾����
	 * 
	 * @return ���b�Z�[�WID
	 */
	public String getID() {
		return messageID;
	}

	/**
	 * �J�e�S��(���b�Z�[�W�敪)���擾����
	 * 
	 * @return �J�e�S��
	 */
	public int getCategory() {
		return category;
	}

	/**
	 * Messaage�N���X��String�N���X�ɃR���o�[�g
	 */
	@Override
	public String toString() {

		return this.toString("");
	}

	/**
	 * Messaage�N���X��String�N���X�ɃR���o�[�g. <br>
	 * �s���}���ӏ��ɂ́A�󔒂��U����
	 * 
	 * @param bindObjs �o�C���h�I�u�W�F�N�g
	 * @return �o�C���h���ꂽ������
	 */
	public String toString(Object... bindObjs) {
		int bindCount = message.split("}").length;

		// �o�C���h���������o�C���h�l�����Ȃ��ꍇ�A�󔒂𑫂�
		if (bindObjs.length < bindCount) {
			List bindList = new ArrayList(bindCount);
			bindList.addAll(Arrays.asList(bindObjs));

			for (int i = 0; i < (bindCount - bindObjs.length); i++) {
				bindList.add("");
			}

			bindObjs = bindList.toArray();
		}
		
		// ������u'�v���u''�v�ɕϊ�����
		message = message.replaceAll("'", "''");

		return MessageFormat.format(message, bindObjs).trim();
	}
}
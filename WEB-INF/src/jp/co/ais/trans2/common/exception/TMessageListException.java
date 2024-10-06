package jp.co.ais.trans2.common.exception;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �������b�Z�[�W�ۗL�\��Exception
 */
public class TMessageListException extends TException {

	/** ���b�Z�[�W���X�g */
	public List<Message> list;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param list ���b�Z�[�W���X�g
	 */
	public TMessageListException(List<Message> list) {
		this.list = list;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param messageID ���b�Z�[�WID
	 * @param list ���b�Z�[�W���X�g
	 */
	public TMessageListException(String messageID, List<Message> list) {
		super(messageID);

		this.list = list;
	}

	/**
	 * ���b�Z�[�W���X�g
	 * 
	 * @return list ���b�Z�[�W���X�g
	 */
	public List<Message> getList() {
		return list;
	}
}

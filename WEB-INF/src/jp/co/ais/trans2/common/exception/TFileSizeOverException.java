package jp.co.ais.trans2.common.exception;

import jp.co.ais.trans.common.except.*;

/**
 * �t�@�C���T�C�Y���ݒ�l�𒴂����ꍇ��Exception
 */
public class TFileSizeOverException extends TException {

	/**
	 * �R���X�g���N�^
	 */
	public TFileSizeOverException() {
		super();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param message
	 */
	public TFileSizeOverException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param messageId
	 * @param message
	 */
	public TFileSizeOverException(String messageId, String message) {
		super(messageId, message);
	}

}

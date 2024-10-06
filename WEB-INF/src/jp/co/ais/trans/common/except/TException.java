package jp.co.ais.trans.common.except;

/**
 * �V�X�e���pApplicationException
 */
public class TException extends Exception {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �o�C���h���� */
	private Object[] args = new Object[0];

	/**
	 * �R���X�g���N�^.
	 */
	public TException() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param message ���b�Z�[�W
	 */
	public TException(String message) {
		super(message);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @param objects �o�C���h����
	 */
	public TException(String messageId, Object... objects) {
		this(messageId);

		this.args = objects;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause �I���W�i��
	 */
	public TException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause �I���W�i��
	 * @param messageId ���b�Z�[�WID
	 * @param objects �o�C���h����
	 */
	public TException(Throwable cause, String messageId, Object... objects) {
		super(messageId, cause);

		this.args = objects;
	}

	/**
	 * ���b�Z�[�WID��Ԃ�.
	 * 
	 * @return ���b�Z�[�WID
	 */
	public String getMessageID() {
		return super.getMessage();
	}

	/**
	 * ���b�Z�[�W�ɑ}�������l���X�g
	 * 
	 * @return �l���X�g
	 */
	public Object[] getMessageArgs() {
		return this.args;
	}
}

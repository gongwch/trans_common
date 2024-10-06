package jp.co.ais.trans.common.except;

/**
 * ���G���[�pRuntimeException
 */
public class TEnvironmentException extends TRuntimeException {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public TEnvironmentException() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @param objects �o�C���h����
	 */
	public TEnvironmentException(String messageId, Object... objects) {
		super(messageId, objects);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause �I���W�i��
	 */
	public TEnvironmentException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause �I���W�i��
	 * @param messageId ���b�Z�[�WID
	 * @param objects �o�C���h����
	 */
	public TEnvironmentException(Throwable cause, String messageId, Object... objects) {
		super(cause, messageId, objects);
	}
}

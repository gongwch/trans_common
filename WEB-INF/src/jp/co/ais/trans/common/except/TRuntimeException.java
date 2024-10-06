package jp.co.ais.trans.common.except;

/**
 * Runtime�G���[�p
 */
public class TRuntimeException extends RuntimeException {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �o�C���h���� */
	private Object[] args = new Object[0];

	/**
	 * �f�t�H���g�R���X�g���N�^
	 */
	public TRuntimeException() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param messageId	���b�Z�[�WID
	 * @param objects		�o�C���h����
	 */
	public TRuntimeException(String messageId, Object... objects) {
		super(messageId);

		this.args = objects;
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause	�I���W�i��
	 */
	public TRuntimeException(Throwable cause) {
		super(cause);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cause		�I���W�i��
	 * @param messageId	���b�Z�[�WID
	 * @param objects		�o�C���h����
	 */
	public TRuntimeException(Throwable cause, String messageId, Object... objects) {
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
	 * ���b�Z�[�W�ɑ}�������o�C���h�l���X�g��.
	 * @return	�o�C���h�l���X�g
	 */
	public Object[] getMessageArgs() {
		return args;
	}
}

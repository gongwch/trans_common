package jp.co.ais.trans.logic.system;

/**
 * ���[�U�̃��b�N�A�E�g���W�b�N
 */
public interface UserLockout {

	/**
	 * �R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param compCode ��ЃR�[�h
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * ���b�N�A�E�g�Ǘ����s�����ǂ���
	 * 
	 * @return true:���b�N�A�E�g�Ǘ�����
	 */
	public boolean isLockoutManaged();

	/**
	 * �Y�����[�U�����b�N�A�E�g���ǂ����𔻕�<br>
	 * 
	 * @return boolean true:���b�N�A�E�g���
	 */
	public boolean isLockoutStatus();

	/**
	 * ���b�N�A�E�g�J�E���g�A�b�v
	 */
	public void countUp();

	/**
	 * ���b�N�A�E�g����
	 */
	public void clearLockout();
}

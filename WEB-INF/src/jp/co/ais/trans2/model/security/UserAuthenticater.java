package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.exception.TWarningException;

/**
 * ���[�U�[�F�؃C���^�[�t�F�[�X
 * @author AIS
 *
 */
public interface UserAuthenticater {

	/**
	 * ���[�U�[�F��
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param password �p�X���[�h
	 */
	public boolean authenticateUser(
			String companyCode,
			String userCode,
			String password) throws TWarningException, TException;

	/**
	 * �F�؎��s����
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void failedAuthenticateUser(String companyCode, String userCode) throws TException;

	/**
	 * ���b�N�A�E�g����
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException;


}

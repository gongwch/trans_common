package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * ���O�C���̃��b�N�A�E�g�Ɋւ���Dao
 * @author AIS
 *
 */
public interface LoginLockOutDao {

	/**
	 * ���O�C�����s����
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void failedAuthenticateUser(String companyCode, String userCode) throws TException;

	/**
	 * ���b�N�A�E�g����Ԃ�
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws TException
	 */
	public LoginLockOutInformation getLoginLockOutInformation(
			String companyCode,
			String userCode) throws TException;

	/**
	 * ���O�C�����b�N�A�E�g����
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException;

}

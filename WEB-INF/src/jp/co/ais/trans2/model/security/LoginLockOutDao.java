package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * ログインのロックアウトに関するDao
 * @author AIS
 *
 */
public interface LoginLockOutDao {

	/**
	 * ログイン失敗処理
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void failedAuthenticateUser(String companyCode, String userCode) throws TException;

	/**
	 * ロックアウト情報を返す
	 * @param companyCode
	 * @param userCode
	 * @return
	 * @throws TException
	 */
	public LoginLockOutInformation getLoginLockOutInformation(
			String companyCode,
			String userCode) throws TException;

	/**
	 * ログインロックアウト解除
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException;

}

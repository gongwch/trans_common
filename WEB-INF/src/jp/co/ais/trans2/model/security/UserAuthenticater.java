package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.exception.TWarningException;

/**
 * ユーザー認証インターフェース
 * @author AIS
 *
 */
public interface UserAuthenticater {

	/**
	 * ユーザー認証
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 */
	public boolean authenticateUser(
			String companyCode,
			String userCode,
			String password) throws TWarningException, TException;

	/**
	 * 認証失敗処理
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void failedAuthenticateUser(String companyCode, String userCode) throws TException;

	/**
	 * ロックアウト解除
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException;


}

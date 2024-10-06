package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ユーザー認証インターフェース実装
 * 
 * @author AIS
 */
public class UserAuthenticaterImpl extends TModel implements UserAuthenticater {

	/**
	 * ユーザー認証
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 */
	public boolean authenticateUser(String companyCode, String userCode, String password) throws TWarningException,
		TException {

		// パスワードポリシー取得
		PasswordManager pm = (PasswordManager) getComponent(PasswordManager.class);
		PasswordPolicy pp = pm.getPasswordPolicy(companyCode);

		// ユーザーが見つからない場合エラー
		UserManager um = (UserManager) getComponent(UserManager.class);
		UserSearchCondition condition = new UserSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(userCode);
		List<User> list = um.get(condition);

		if (list == null || list.isEmpty()) {
			throw new TWarningException("I00156");// 入力情報に誤りがあります。
		}

		User user = list.get(0);

		if (user == null) {
			throw new TWarningException("I00156");// 入力情報に誤りがあります。
		}

		// パスワード間違いの場合
		if (!user.getPassword().equals(password)) {

			// ロックアウトする場合、失敗回数をインクリメント
			if (pp != null && pp.getLockOutAllowedCount() > 0) {
				failedAuthenticateUser(companyCode, userCode);
			}
			throw new TWarningException("I00156");// 入力情報に誤りがあります。
		}

		// 有効期限チェック
		Date validTerm = Util.getCurrentYMDDate();

		if (validTerm != null) {
			Date from = user.getDateFrom();
			Date to = user.getDateTo();
			if ((from != null && !Util.isSmallerThenByYMD(from, validTerm))
				|| (to != null && !Util.isSmallerThenByYMD(validTerm, to))) {
				throw new TWarningException("I00442");// ログイン有効期限が過ぎているため、ログインできません。
			}
		}

		// ロックアウトされている場合エラー
		if (pp != null && pp.getLockOutAllowedCount() > 0) {

			LoginLockOutDao loDao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
			LoginLockOutInformation lockOutInfo = loDao.getLoginLockOutInformation(companyCode, userCode);

			if (lockOutInfo != null) {

				// 失敗回数が既に既定回数に達していればログイン不可
				if (pp.getLockOutAllowedCount() <= lockOutInfo.getFailedCount()) {

					// ロックアウト後一定時間が経過していなければログイン不可
					// 更新からの期間
					Date lockoutReleaseTime = DateUtil.addMinute(lockOutInfo.getFailedTimestamp(),
						pp.getLockOutReleaseTime());
					Date currentDate = Util.getCurrentDate();
					boolean isRelease = currentDate.compareTo(lockoutReleaseTime) > 0;

					if (pp.getLockOutReleaseTime() == 0 || !isRelease) {
						throw new TWarningException("W01009");
					}

				}

				// ログイン成功した場合ロックアウト解除
				releaseLoginLockOut(companyCode, userCode);

			}

		}

		return true;

	}

	public void failedAuthenticateUser(String companyCode, String userCode) throws TException {

		LoginLockOutDao dao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
		dao.failedAuthenticateUser(companyCode, userCode);

	}

	/**
	 * ロックアウト解除
	 * 
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException {

		LoginLockOutDao dao = (LoginLockOutDao) getComponent(LoginLockOutDao.class);
		dao.releaseLoginLockOut(companyCode, userCode);

	}

}

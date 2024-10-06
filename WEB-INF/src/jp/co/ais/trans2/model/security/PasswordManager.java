package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.common.exception.TPasswordTermComeThroughException;
import jp.co.ais.trans2.common.exception.TWarningException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * パスワード管理インターフェース
 * @author AIS
 *
 */
public interface PasswordManager {

	/**
	 * パスワードポリシーを返す
	 * @param company 会社
	 * @return パスワードポリシー
	 * @throws TException
	 */
	public PasswordPolicy getPasswordPolicy(String companyCode) throws TException;
		
	/**
	 * パスワードが有効期間内かをチェックする。
	 * @param company 会社
	 * @param user ユーザー
	 * @throws TPasswordTermComeThroughException 有効期間切れException
	 */
	public void isInTerm(Company company, User user) throws TPasswordTermComeThroughException, TException;

	/**
	 * パスワードを更新する
	 * @param company 会社
	 * @param user ユーザー
	 * @param password パスワード
	 * @throws TWarningException, TException
	 */
	public void updatePassword(Company company, User user, String password) throws TInformationException, TException;

}

package jp.co.ais.trans2.model.security;

import java.util.List;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * パスワードポリシーに関するDao
 * @author AIS
 *
 */
public interface PasswordPolicyDao {

	/**
	 * 指定会社のパスワードポリシーを返す
	 * @param company 会社
	 * @return パスワードポリシー
	 * @throws TException
	 */
	public PasswordPolicy get(Company company) throws TException;

	/**
	 * 指定ユーザーのパスワード履歴を返す
	 * @param company 会社
	 * @param user ユーザー
	 * @return 指定ユーザーのパスワード履歴
	 * @throws TException
	 */
	public List<Password> getPasswordHistory(Company company, User user) throws TException;

	/**
	 * パスワード更新
	 * @param company
	 * @param user
	 * @param password
	 */
	public void updatePassword(Company company, User user, String password) throws TException;

	/**
	 * パスワード履歴登録
	 * @param company
	 * @param user
	 * @param password
	 */
	public void entryPasswordHistory(Company company, User user, String password) throws TException;

}

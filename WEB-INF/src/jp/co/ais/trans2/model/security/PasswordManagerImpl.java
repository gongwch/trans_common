package jp.co.ais.trans2.model.security;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.exception.TInformationException;
import jp.co.ais.trans2.common.exception.TPasswordTermComeThroughException;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * パスワード管理実装
 * 
 * @author AIS
 */
public class PasswordManagerImpl extends TModel implements PasswordManager {

	public PasswordPolicy getPasswordPolicy(String companyCode) throws TException {

		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		Company company = new Company();
		company.setCode(companyCode);
		PasswordPolicy pp = ppDao.get(company);

		return pp;
	}

	public void isInTerm(Company company, User user) throws TPasswordTermComeThroughException, TException {

		// 1.パスワード管理を行うかをチェック
		PasswordPolicy pp = getPasswordPolicy(company.getCode());

		// 管理しない場合はチェック終了
		if (pp == null) {
			return;
		}

		// パスワード有効期間チェック. 0の場合はチェックなし
		if (pp.getPasswordTerm() == 0) {
			return;
		}

		// ユーザーのパスワード履歴を取得
		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		List<Password> passwordHistory = ppDao.getPasswordHistory(company, user);

		// 履歴が無い場合は強制変更
		if (passwordHistory == null || passwordHistory.isEmpty()) {
			throw new TPasswordTermComeThroughException();
		}

		Date lastDate = passwordHistory.get(0).getUpdateDate();

		// 期限切れチェック
		Date currentDate = DateUtil.addDay(lastDate, pp.getPasswordTerm());
		int remainDays = DateUtil.getDayCount(getNow(), currentDate);

		if (remainDays <= 0) {
			TPasswordTermComeThroughException e = new TPasswordTermComeThroughException();
			e.setDaysBeforePasswordTermComeThrough(remainDays);
			throw e;
		}

		// 変更日前通知
		if (pp.getDaysNoticePasswordTermComeThrough() != 0) {

			// 通知日付内であれば通知
			if (remainDays <= pp.getDaysNoticePasswordTermComeThrough()) {
				TPasswordTermComeThroughException e = new TPasswordTermComeThroughException();
				e.setDaysBeforePasswordTermComeThrough(remainDays);
				throw e;
			}
		}
	}

	public void updatePassword(Company company, User user, String password) throws TInformationException, TException {

		// パスワードポリシー取得
		PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
		PasswordPolicy pp = ppDao.get(company);

		// パスワードチェック
		if (pp != null) {
			checkPassword(company, user, pp, password);
		}

		// パスワード更新
		ppDao.updatePassword(company, user, password);
		ppDao.entryPasswordHistory(company, user, password);

	}

	/**
	 * パスワードの有効チェック
	 * 
	 * @param company
	 * @param user
	 * @param pp
	 * @param password
	 * @throws TInformationException
	 * @throws TException
	 */
	protected void checkPassword(Company company, User user, PasswordPolicy pp, String password)
		throws TInformationException, TException {

		// 文字数チェック
		if (pp.getPasswordMinLength() > 0) {
			if (password.length() < pp.getPasswordMinLength()) {
				throw new TInformationException("I00027", Integer.toString(pp.getPasswordMinLength()));
			}
		}

		// 複雑レベル
		// それぞれの文字をアスキコードで分別、complicateリストにその区分をいれる。
		Set<Integer> complicateList = new HashSet<Integer>();

		// 各文字を判定用int配列に変える。
		for (char schar : password.toCharArray()) {
			String str = Character.toString(schar);

			if (str.matches("[A-Z]")) {
				complicateList.add(1); // 大文字

			} else if (str.matches("[a-z]")) {
				complicateList.add(2); // 小文字

			} else if (str.matches("[0-9]")) {
				complicateList.add(3); // 数字

			} else if (str.matches("[!-~]")) {
				complicateList.add(4); // 記号
			}
		}

		// 複雑レベルが設定より低い場合エラーを出す。
		if (complicateList.size() < pp.getComplicateLevel()) {
			throw new TInformationException("I00028", Integer.toString(pp.getComplicateLevel()));
		}

		// 履歴チェック
		if (pp.getHistoryMaxCount() > 0) {

			PasswordPolicyDao ppDao = (PasswordPolicyDao) getComponent(PasswordPolicyDao.class);
			List<Password> passwordHistory = ppDao.getPasswordHistory(company, user);

			if (passwordHistory != null && passwordHistory.size() >= pp.getHistoryMaxCount()) {
				for (int i = 0; i < pp.getHistoryMaxCount(); i++) {
					Password ph = passwordHistory.get(i);
					if (ph.getPassword().equals(password)) {
						throw new TInformationException("I00029");
					}
				}
			}

		}

	}
}

package jp.co.ais.trans2.model.security;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.db.SQLUtil;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.company.Company;
import jp.co.ais.trans2.model.user.User;

/**
 * パスワードポリシーに関するDao実装
 * @author AIS
 *
 */
public class PasswordPolicyDaoImpl extends TModel implements PasswordPolicyDao {

	public PasswordPolicy get(Company company) throws TException {

		Connection conn = DBUtil.getConnection();
		PasswordPolicy policy = null; 

		try {

			String sql =
				" SELECT " +
					" KAI_CODE, " +
					" LOCK_OUT_ARR_CNT, " +
					" LOCK_OUT_RELEASE_TIME, " +
					" PWD_MIN_LENGTH, " +
					" PWD_TERM, " +
					" COMPLICATE_LVL, " +
					" HISTORY_MAX_CNT, " +
					" PWD_EXP_BEFORE_DAYS " +
				" FROM " +
					" USR_AUTH_MST " +
				" WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				policy = mapping(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return policy;
	}

	/**
	 * O/Rマッピング
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected PasswordPolicy mapping(ResultSet rs) throws Exception {

		PasswordPolicy bean = new PasswordPolicy();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setLockOutAllowedCount(rs.getInt("LOCK_OUT_ARR_CNT"));
		bean.setLockOutReleaseTime(rs.getInt("LOCK_OUT_RELEASE_TIME"));
		bean.setPasswordMinLength(rs.getInt("PWD_MIN_LENGTH"));
		bean.setPasswordTerm(rs.getInt("PWD_TERM"));
		bean.setComplicateLevel(rs.getInt("COMPLICATE_LVL"));
		bean.setHistoryMaxCount(rs.getInt("HISTORY_MAX_CNT"));
		bean.setDaysNoticePasswordTermComeThrough(rs.getInt("PWD_EXP_BEFORE_DAYS"));

		return bean;

	}

	/**
	 * 指定ユーザーのパスワード履歴を返す
	 * @param company 会社
	 * @param user ユーザー
	 * @return 指定ユーザーのパスワード履歴
	 * @throws TException
	 */
	public List<Password> getPasswordHistory(Company company, User user) throws TException {

		Connection conn = DBUtil.getConnection();
		List<Password> list = new ArrayList<Password>(); 

		try {

			String sql =
				" SELECT " +
					" KAI_CODE, " +
					" USR_CODE, " +
					" INP_DATE, " +
					" PASSWORD " +
				" FROM " +
					" PWD_HST_TBL " +
				" WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" AND	USR_CODE = " + SQLUtil.getParam(user.getCode()) +
				" ORDER BY " + 
					" INP_DATE DESC ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingPasswordHistory(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * O/Rマッピング
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected Password mappingPasswordHistory(ResultSet rs) throws Exception {

		Password bean = new Password();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setUserCode(rs.getString("USR_CODE"));
		bean.setUpdateDate(rs.getDate("INP_DATE"));
		bean.setPassword(rs.getString("PASSWORD"));

		return bean;

	}

	/**
	 * パスワード更新
	 * @param company
	 * @param user
	 * @param password
	 */
	public void updatePassword(Company company, User user, String password) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" UPDATE USR_MST " +
				" SET " +
					" PASSWORD = " + SQLUtil.getParam(password) + ", " +
					" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
					" USR_ID = " + SQLUtil.getParam(getUserCode()) +
				" WHERE KAI_CODE = " + SQLUtil.getParam(company.getCode()) +
				" AND	USR_CODE = " + SQLUtil.getParam(user.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * パスワード履歴登録
	 * @param company
	 * @param user
	 * @param password
	 */
	public void entryPasswordHistory(Company company, User user, String password) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" INSERT INTO PWD_HST_TBL ( " +
					" KAI_CODE, " +
					" USR_CODE, " +
					" INP_DATE, " +
					" PASSWORD " +
				" ) VALUES ( " +
					SQLUtil.getParam(company.getCode()) + ", " +
					SQLUtil.getParam(user.getCode()) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(password) +
				") ";

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}

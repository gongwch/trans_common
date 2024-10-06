package jp.co.ais.trans2.model.security;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * ログインのロックアウトに関するDao実装
 * @author AIS
 *
 */
public class LoginLockOutDaoImpl extends TModel implements LoginLockOutDao {

	public void failedAuthenticateUser(String companyCode, String userCode) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" UPDATE LOCK_OUT_TBL " +
				" SET " +
					" FAIL_COUNT = FAIL_COUNT + 1, " +
					" FAIL_DATE = " + SQLUtil.getYMDHMSParam(Util.getCurrentDate()) +
				" WHERE KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND	USR_CODE = " + SQLUtil.getParam(userCode);

			int updateCount = DBUtil.execute(conn, sql);

			if (updateCount == 0) {

				sql =
					" INSERT INTO LOCK_OUT_TBL ( " +
						" KAI_CODE, " +
						" USR_CODE, " +
						" FAIL_COUNT, " +
						" FAIL_DATE, " +
						" INP_DATE " +
					" ) VALUES ( " +
						SQLUtil.getParam(companyCode) + ", " +
						SQLUtil.getParam(userCode) + ", " +
						" 1, " +
						SQLUtil.getYMDHMSParam(Util.getCurrentDate()) + ", " +
						SQLUtil.getYMDHMSParam(Util.getCurrentDate()) +
					" ) ";

				DBUtil.execute(conn, sql);

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public LoginLockOutInformation getLoginLockOutInformation(
			String companyCode,
			String userCode) throws TException {
		
		Connection conn = DBUtil.getConnection();
		LoginLockOutInformation llo = null; 

		try {

			String sql =
				" SELECT " +
					" KAI_CODE, " +
					" USR_CODE, " +
					" FAIL_COUNT, " +
					" FAIL_DATE " +
				" FROM " +
					" LOCK_OUT_TBL " +
				" WHERE KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND	USR_CODE = " + SQLUtil.getParam(userCode);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				llo = mapping(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return llo;

	}

	/**
	 * O/Rマッピング
	 * @param rs
	 * @return
	 * @throws Exception
	 */
	protected LoginLockOutInformation mapping(ResultSet rs) throws Exception {

		LoginLockOutInformation bean = new LoginLockOutInformation();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setUserCode(rs.getString("USR_CODE"));
		bean.setFailedCount(rs.getInt("FAIL_COUNT"));
		bean.setFailedTimestamp(rs.getTimestamp("FAIL_DATE"));
		return bean;

	}

	/**
	 * ロックアウト解除
	 * @param companyCode
	 * @param userCode
	 * @throws TException
	 */
	public void releaseLoginLockOut(String companyCode, String userCode) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE FROM LOCK_OUT_TBL " +
				" WHERE KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND	USR_CODE = " + SQLUtil.getParam(userCode);

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}

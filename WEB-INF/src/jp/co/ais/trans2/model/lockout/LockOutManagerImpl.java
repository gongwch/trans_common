package jp.co.ais.trans2.model.lockout;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * MG0027 - ロックアウト管理
 * 
 * @author AIS
 */
public class LockOutManagerImpl extends TModel implements LockOutManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @return List 検索結果
	 * @throws TException
	 */
	public List<LockOut> get() throws TException {
		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		List<LockOut> list = new ArrayList<LockOut>();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("SELECT");
			sql.add("    lout.KAI_CODE");
			sql.add("    ,lout.USR_CODE");
			sql.add("    ,usr.USR_NAME_S");
			sql.add("    ,lout.FAIL_DATE");
			sql.add("FROM LOCK_OUT_TBL lout");
			sql.add("    LEFT OUTER JOIN USR_MST usr");
			sql.add("      ON lout.KAI_CODE = usr.KAI_CODE");
			sql.add("      AND lout.USR_CODE = usr.USR_CODE");
			sql.add("WHERE 1 = 1");
			sql.add(" AND   lout.KAI_CODE = ?", getCompanyCode());
			sql.add("ORDER BY");
			sql.add("    lout.USR_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(statement);
			DBUtil.close(rs);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @throws TException
	 */
	public void delete(List<LockOut> lockoutList) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			for (LockOut lockout : lockoutList) {
				sql.add("");
				sql.add("DELETE FROM");
				sql.add("    LOCK_OUT_TBL ");
				sql.add("WHERE");
				sql.add("    KAI_CODE = ? ", lockout.getCompanyCode());
				sql.add("AND");
				sql.add("    USR_CODE = ? ", lockout.getUserCode());

				DBUtil.execute(conn, sql);
				sql.clear();
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 出力値をbeanにセット
	 * 
	 * @param rs ResultSet SELECT結果
	 * @return bean
	 * @throws Exception
	 */
	protected LockOut mapping(ResultSet rs) throws Exception {

		LockOut bean = new LockOut();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setUserCode(rs.getString("USR_CODE"));
		bean.setUserNames(rs.getString("USR_NAME_S"));
		bean.setLogFailure(rs.getTimestamp("FAIL_DATE"));

		return bean;
	}

}
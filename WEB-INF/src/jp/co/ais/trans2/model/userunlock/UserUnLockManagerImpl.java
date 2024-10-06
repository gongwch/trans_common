package jp.co.ais.trans2.model.userunlock;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * ログイン解除マスタ情報の実装。
 * 
 * @author AIS
 */
public class UserUnLockManagerImpl extends TModel implements UserUnLockManager {

	public List<UserUnLock> get() throws TException {

		Connection conn = DBUtil.getConnection();

		List<UserUnLock> list = new ArrayList<UserUnLock>();

		try {
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT pci.USR_ID ");
			sql.add("       ,usr.USR_NAME ");
			sql.add("       ,pci.PCI_CHECK_IN_TIME ");
			sql.add("       ,pci.KAI_CODE ");
			sql.add(" FROM PCI_CHK_CTL pci ");
			sql.add("     LEFT OUTER JOIN USR_MST usr ");
			sql.add("     ON  pci.KAI_CODE = usr.KAI_CODE ");
			sql.add("     AND pci.USR_ID   = usr.USR_CODE");
			sql.add(" WHERE pci.KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
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
	 * 情報削除 (DELETE)
	 * 
	 * @param list 情報削除
	 * @throws TException
	 */
	public void delete(List<UserUnLock> list) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {
			for (UserUnLock bean : list) {
				delete(conn, bean);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param bean 情報削除
	 * @throws TException
	 */
	public void delete(UserUnLock bean) throws TException {
		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {
			delete(conn, bean);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 情報削除 (DELETE)
	 * 
	 * @param conn
	 * @param bean 情報削除
	 * @throws TException
	 */
	public void delete(Connection conn, UserUnLock bean) throws TException {

		SQLCreator sql = new SQLCreator();
		sql.add(" DELETE FROM PCI_CHK_CTL");
		sql.add(" WHERE KAI_CODE = ? ", bean.getKAI_CODE());
		sql.add(" AND   USR_ID = ? ", bean.getUSR_ID());
		DBUtil.execute(conn, sql);

		sql = new SQLCreator();
		sql.add(" DELETE FROM PCI_SUB_CHK_CTL");
		sql.add(" WHERE KAI_CODE = ? ", bean.getKAI_CODE());
		sql.add(" AND   USR_ID = ? ", bean.getUSR_ID());
		DBUtil.execute(conn, sql);

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return ConsumptionTax
	 * @throws Exception
	 */
	protected UserUnLock mapping(ResultSet rs) throws Exception {

		UserUnLock bean = new UserUnLock();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setPCI_CHECK_IN_TIME(rs.getTimestamp("PCI_CHECK_IN_TIME"));
		bean.setUSR_NAME(rs.getString("USR_NAME"));

		return bean;

	}

	/**
	 * SQL creator
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}

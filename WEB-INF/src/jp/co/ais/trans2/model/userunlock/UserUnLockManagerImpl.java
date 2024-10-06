package jp.co.ais.trans2.model.userunlock;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * ���O�C�������}�X�^���̎����B
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
	 * ���폜 (DELETE)
	 * 
	 * @param list ���폜
	 * @throws TException
	 */
	public void delete(List<UserUnLock> list) throws TException {

		// DB Connection ����
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
	 * ���폜 (DELETE)
	 * 
	 * @param bean ���폜
	 * @throws TException
	 */
	public void delete(UserUnLock bean) throws TException {
		// DB Connection ����
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
	 * ���폜 (DELETE)
	 * 
	 * @param conn
	 * @param bean ���폜
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
	 * O/R�}�b�s���O
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
		 * �R���X�g���N�^�[
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}

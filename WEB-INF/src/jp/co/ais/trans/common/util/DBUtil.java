package jp.co.ais.trans.common.util;

import java.sql.*;
import java.util.*;

import javax.sql.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;

/**
 * DB�A�N�Z�X���[�e�B���e�B
 */
public final class DBUtil {

	/** 1:Varchar2% */
	public static final int NORMAL_CHAR = 1;

	/** 1:%Varchar2% */
	public static final int NORMAL_CHAR2 = 4;

	/** 2:%nVarchar2% */
	public static final int UNICODE_CHAR = 2;

	/** 3:nVarchar2% */
	public static final int UNICODE_CHAR2 = 3;

	/** ���t�t�H�[�}�b�g�w�� */
	public static boolean isSetNlsFormat = false;

	static {
		try {
			isSetNlsFormat = BooleanUtil.toBoolean(ServerConfig.getProperty("trans.system.nls.format"));

			if (isSetNlsFormat) {
				System.out.println("## setup NLS_DATE_FORMAT!(OLD)");
			}

		} catch (MissingResourceException e) {
			// �Ȃ�
		}
	}

	/**
	 * PreparedStatement�擾
	 * 
	 * @param con DB�R�l�N�V����
	 * @param sql SQL��
	 * @param args ����
	 * @return PreparedStatement
	 */
	public static PreparedStatement createPreparedStatement(Connection con, String sql, List args) {
		return createPreparedStatement(con, sql, args.toArray(new Object[args.size()]));
	}

	/**
	 * PreparedStatement�擾
	 * 
	 * @param con DB�R�l�N�V����
	 * @param sql SQL��
	 * @param args ����
	 * @return PreparedStatement
	 */
	public static PreparedStatement createPreparedStatement(Connection con, String sql, Object... args) {

		try {
			log(sql, args); // ���O�o��

			PreparedStatement statement = con.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				statement.setObject(i + 1, args[i]);
			}

			return statement;
		} catch (SQLException ex) {
			throw new TRuntimeException(ex, "E00009");
		}
	}

	/**
	 * Connection�擾
	 * 
	 * @return Connection
	 * @throws SQLException
	 */
	public static Connection getConnection() throws SQLException {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		DataSource dataSource = (DataSource) container.getComponent(DataSource.class);
		Connection con = dataSource.getConnection();

		setNlsFormat(con);

		return con;
	}

	/**
	 * NLS_DATE�t�H�[�}�b�g�ݒ�(�T�[�o�Ή�)
	 * 
	 * @param con �R�l�N�V����
	 */
	protected static void setNlsFormat(Connection con) {

		if (isSetNlsFormat) {
			execute(con, "ALTER SESSION SET NLS_DATE_FORMAT='YYYY/MM/DD HH24:MI:SS'");
			execute(con, "ALTER SESSION SET NLS_TIME_FORMAT='HH24:MI:SSXFF'");
			execute(con, "ALTER SESSION SET NLS_TIMESTAMP_FORMAT='YYYY/MM/DD HH24:MI:SSXFF'");
		}
	}

	/**
	 * �X�V�n���s
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return ���ʌ���
	 */
	public static int execute(Connection conn, String sql) {

		Statement statement = null;
		try {
			statement = conn.createStatement();
			ServerLogger.debug(sql);

			int count = statement.executeUpdate(sql);
			log(Integer.toString(count)); // �Ώی����o��

			return count;

		} catch (SQLException ex) {
			ServerErrorHandler.handledException(ex);
			return 0;

		} finally {
			close(statement);
		}
	}

	/**
	 * ResultSet��close����
	 * 
	 * @param rs ResultSet
	 */
	public static void close(ResultSet rs) {

		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * �X�e�[�g�����g��close����
	 * 
	 * @param st Statement
	 */
	public static void close(Statement st) {

		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * �R�l�N�V������close����
	 * 
	 * @param con Connection
	 */
	public static void close(Connection con) {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * �R�l�N�V�����ƃX�e�[�g�����g��close����
	 * 
	 * @param con Connection
	 * @param st Statement
	 */
	public static void close(Connection con, Statement st) {
		close(st);
		close(con);
	}

	/**
	 * SQL���s(�X�V�n)
	 * 
	 * @param sql SQL��
	 * @param args ����
	 * @return �X�V����
	 */
	public static int execute(String sql, List args) {
		return execute(sql, args.toArray(new Object[args.size()]));
	}

	/**
	 * SQL���s(�X�V�n)
	 * 
	 * @param sql SQL��
	 * @param args ����
	 * @return �X�V����
	 */
	public static int execute(String sql, Object... args) {

		Connection con = null;
		PreparedStatement statement = null;

		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			DataSource dataSource = (DataSource) container.getComponent(DataSource.class);
			con = dataSource.getConnection();

			setNlsFormat(con);

			log(sql, args); // ���O�o��

			statement = con.prepareStatement(sql);

			for (int i = 0; i < args.length; i++) {
				statement.setObject(i + 1, args[i]);
			}

			int count = statement.executeUpdate();
			log(Integer.toString(count)); // �Ώی����o��

			return count;

		} catch (SQLException ex) {
			throw new TRuntimeException(ex, "E00009");

		} finally {
			close(statement);
			close(con);
		}
	}

	/**
	 * ���O�o�͗p
	 * 
	 * @param sql SQL
	 * @param args �o�C���h����
	 */
	protected static void log(String sql, Object... args) {
		if (!ServerLogger.isDebugLevel()) {
			return;
		}

		for (int i = 0; i < args.length; i++) {

			sql = sql.replaceFirst("\\?", "'" + Util.avoidNull(args[i]) + "'");
		}

		ServerLogger.debug(sql);
	}

	/**
	 * Ref�Ɋ֘A����B�������́A�u%�v�����������̈ꕔ�Ƃ��ĔF��������
	 * 
	 * @param str ��������
	 * @param type 0: Code����% 1:%Name����% 2:Name����%
	 * @return ����SQL
	 */
	public static String getLikeStatement(String str, int type) {
		String result = "";
		if (Util.isNullOrEmpty(str)) {
			return result;
		}

		// TODO LIKE���͐ݒ�l�����݂��Ȃ��Ă������ɒǉ������ׂ����B
		Connection con = null;
		try {
			con = getConnection();// Connection�擾
			String dbName = con.getMetaData().getDatabaseProductName();

			if ("Oracle".equals(dbName)) {

				switch (type) {

					case NORMAL_CHAR:
						result = " LIKE '" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NORMAL_CHAR2:
						result = " LIKE '%" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case UNICODE_CHAR:
						result = " LIKEC '%" + str + "%'" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					case UNICODE_CHAR2:
						result = " LIKEC '" + str + "%'" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}

			} else {
				// TODO ��Oracle
				switch (type) {

					case NORMAL_CHAR:
					case UNICODE_CHAR2:
						result = " LIKE '" + str + "%'";
						break;

					case UNICODE_CHAR:
					case NORMAL_CHAR2:
						result = " LIKE '%" + str + "%'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}
			}

			return result;

		} catch (SQLException ex) {
			throw new TRuntimeException(ex, "E00009");

		} finally {
			close(con);
		}

	}
}

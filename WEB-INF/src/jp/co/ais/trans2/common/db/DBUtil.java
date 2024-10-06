package jp.co.ais.trans2.common.db;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;
import java.util.Map.*;

import javax.sql.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.fw.*;

/**
 * DB関連ユーティリティ
 * 
 * @author AIS
 */
public class DBUtil {

	/** 日付フォーマット指定 */
	public static boolean isSetNlsFormat = false;

	/** MySQL指定 */
	public static boolean isUseMySQL = false;

	/** SQL Server指定 */
	public static boolean isUseSQLServer = false;

	/** 変換マップ */
	public static Map<String, String> convertMap = null;

	static {
		try {
			isSetNlsFormat = ServerConfig.isFlagOn("trans.system.nls.format");
			isUseMySQL = ServerConfig.isFlagOn("trans.system.use.mysql");
			isUseSQLServer = ServerConfig.isFlagOn("trans.system.use.sql.server");

			if (isSetNlsFormat) {
				System.out.println("## setup NLS_DATE_FORMAT!");
			}
			if (isUseMySQL) {
				System.out.println("## setup MySQL mode!");

				// NLSは無効
				isSetNlsFormat = false;
			}

		} catch (MissingResourceException e) {
			// なし
		}
	}

	/**
	 * Connection取得
	 * 
	 * @return Connection
	 * @throws TException
	 */
	public static Connection getConnection() throws TException {

		try {
			TContainer container = TContainerFactory.getContainer();
			DataSource dataSource = (DataSource) container.getComponent(DataSource.class);
			Connection conn = dataSource.getConnection();

			if (isSetNlsFormat) {
				execute(conn, "ALTER SESSION SET NLS_DATE_FORMAT='YYYY/MM/DD HH24:MI:SS'");
				execute(conn, "ALTER SESSION SET NLS_TIME_FORMAT='HH24:MI:SSXFF'");
				execute(conn, "ALTER SESSION SET NLS_TIMESTAMP_FORMAT='YYYY/MM/DD HH24:MI:SSXFF'");
			}

			return conn;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Connection取得
	 * 
	 * @param dataSourceKey
	 * @return Connection
	 * @throws TException
	 */
	public static Connection getConnection(String dataSourceKey) throws TException {

		if (Util.isNullOrEmpty(dataSourceKey)) {
			return getConnection();
		}

		try {
			TContainer container = TContainerFactory.getContainer();
			DataSource dataSource = (DataSource) container.getComponent(dataSourceKey);
			Connection conn = dataSource.getConnection();

			if (isSetNlsFormat) {
				execute(conn, "ALTER SESSION SET NLS_DATE_FORMAT='YYYY/MM/DD HH24:MI:SS'");
				execute(conn, "ALTER SESSION SET NLS_TIME_FORMAT='HH24:MI:SSXFF'");
				execute(conn, "ALTER SESSION SET NLS_TIMESTAMP_FORMAT='YYYY/MM/DD HH24:MI:SSXFF'");
			}

			return conn;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Statementを返す<br>
	 * 大量データ返す用
	 * 
	 * @param conn Connection
	 * @return 結果
	 * @throws TException
	 */
	public static Statement getBigDataStatement(Connection conn) throws TException {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			if (isUseMySQL) {
				statement.setFetchSize(Integer.MIN_VALUE);
			} else {
				statement.setFetchSize(100);
			}
			return statement;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Statementを返す
	 * 
	 * @param conn Connection
	 * @return 結果
	 * @throws TException
	 */
	public static Statement getStatement(Connection conn) throws TException {
		try {
			Statement statement = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY);
			statement.setFetchSize(100);
			return statement;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SELECT発行
	 * 
	 * @param statement Statement
	 * @param sql SQL
	 * @return 結果
	 * @throws TException
	 */
	public static ResultSet select(Statement statement, String sql) throws TException {

		try {
			ResultSet result = statement.executeQuery(convert(sql));
			return result;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SELECT発行
	 * 
	 * @param statement Statement
	 * @param sc SQL
	 * @return 結果
	 * @throws TException
	 */
	public static ResultSet select(Statement statement, SQLCreator sc) throws TException {

		try {
			return select(statement, sc.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * SELECT発行 件数制限版
	 * 
	 * @param statement Statement
	 * @param sc SQL
	 * @param limit 件数
	 * @return 結果
	 * @throws TException
	 */
	public static ResultSet selectLimit(Statement statement, SQLCreator sc, int limit) throws TException {
		try {

			// MySQLモード
			if (ServerConfig.isFlagOn("trans.system.use.mysql")) {
				sc.add("  limit ? ", limit);
			}

			return select(statement, sc.toSQL());

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 更新系発行
	 * 
	 * @param sc SQL
	 * @return 結果件数
	 * @throws TException
	 */
	public static int execute(SQLCreator sc) throws TException {
		Connection conn = getConnection();

		try {
			return execute(conn, sc);

		} finally {
			close(conn);
		}
	}

	/**
	 * 更新系発行
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 結果件数
	 * @throws TException
	 */
	public static int execute(Connection conn, String sql) throws TException {

		try {
			if (isUseMySQL && sql.contains("LOCK TABLE")) {
				// MySQLのために無条件に退避
				return 0;
			} else if (isUseSQLServer && sql.contains("LOCK TABLE")) {
				return 0;
			}

			Statement statement = conn.createStatement();
			int count = statement.executeUpdate(convert(sql));
			close(statement);
			ServerLogger.debug(Integer.toString(count)); // 対象件数出力

			return count;

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 更新系バッチ発行
	 * 
	 * @param conn Connection
	 * @param list SQLリスト
	 * @return 結果件数
	 * @throws TException
	 */
	public static int[] executeBatch(Connection conn, List list) throws TException {
		if (list == null || list.isEmpty()) {
			return new int[0];
		}

		Statement st = null;

		try {

			st = conn.createStatement();
			boolean hasData = false;

			for (Object obj : list) {

				if (obj == null) {
					continue;
				}

				hasData = true;
				String sql = null;

				if (obj instanceof SQLCreator) {
					sql = ((SQLCreator) obj).toSQL();
				} else if (obj instanceof String) {
					sql = Util.avoidNullNT(obj);
				}

				if (sql != null) {
					if (isUseMySQL && sql.contains("LOCK TABLE")) {
						// MySQLのために無条件に退避
						continue;
					} else if (isUseSQLServer && sql.contains("LOCK TABLE")) {
						continue;
					}

					st.addBatch(convert(sql));
				}
			}

			if (hasData) {
				int[] rt = st.executeBatch();
				ServerLogger.debug(Arrays.toString(rt)); // 対象件数出力
				return rt;
			} else {
				return new int[0];
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			close(st);
		}
	}

	/**
	 * 指定テーブルをフラッシュする(一時テーブルのインデックス初期化)<br>
	 * ON COMMIT DELETE ROWSを実現する<br>
	 * 測定Perfアップ：<br>
	 * 60秒→40秒、1万件財務帳票出力<br>
	 * 180秒→90秒、5万件財務帳票出力<br>
	 * 
	 * @param conn Connection
	 * @param tableName
	 * @throws TException
	 */
	public static void flush(Connection conn, String tableName) throws TException {

		if (!isUseMySQL) {
			return;
		}

		try {

			Statement statement = conn.createStatement();
			statement.executeUpdate(convert(" TRUNCATE TABLE " + tableName));
			statement.executeUpdate(convert(" DELETE FROM " + tableName));
			close(statement);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 更新系発行
	 * 
	 * @param conn Connection
	 * @param sc SQL
	 * @return 結果件数
	 * @throws TException
	 */
	public static int execute(Connection conn, SQLCreator sc) throws TException {
		return execute(conn, sc.toSQL());
	}

	/**
	 * コミット
	 * 
	 * @param conn Connection
	 * @throws TException
	 */
	public static void commit(Connection conn) throws TException {
		try {
			conn.commit();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * Resultsetのclose処理
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
	 * Statementのclose処理
	 * 
	 * @param statement Statement
	 */
	public static void close(Statement statement) {

		try {
			if (statement != null) {
				statement.close();
			}
		} catch (SQLException ex) {
			ServerErrorHandler.handledException(ex);
		}
	}

	/**
	 * コネクションのclose処理
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
	 * SELECT発行
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @param mapper Mapper
	 * @return 結果
	 * @throws TException
	 */
	public static Object select(Connection conn, String sql, ORMapper mapper) throws TException {

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = getStatement(conn);
			rs = DBUtil.select(statement, sql);

			Object rt = mapper.mapping(rs);

			close(rs);
			close(statement);

			return rt;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			close(rs);
			close(statement);
		}
	}

	/**
	 * 特定の値(1行目の1カラム目)だけを取得し返す。
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static Date selectOneDate(Connection conn, String sql) throws TException {

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = getStatement(conn);
			rs = DBUtil.select(statement, sql);

			Date rt = null;
			while (rs.next()) {

				// 対象出力
				StringBuilder sb = new StringBuilder();
				sb.append("result=");
				sb.append(Util.avoidNull(rs.getDate(1)));
				ServerLogger.debug(sb.toString());

				rt = rs.getDate(1);
				break;
			}

			close(rs);
			close(statement);

			return rt;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			close(rs);
			close(statement);
		}
	}

	/**
	 * 特定の値(1行目の1カラム目)だけを取得し返す。
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static BigDecimal selectOneDecimal(Connection conn, String sql) throws TException {

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = getStatement(conn);
			rs = DBUtil.select(statement, sql);

			BigDecimal rt = null;
			while (rs.next()) {

				// 対象出力
				StringBuilder sb = new StringBuilder();
				sb.append("result=");
				sb.append(Util.avoidNull(rs.getBigDecimal(1)));
				ServerLogger.debug(sb.toString());

				rt = rs.getBigDecimal(1);
				break;
			}

			close(rs);
			close(statement);

			return rt;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			close(rs);
			close(statement);
		}
	}

	/**
	 * 特定の値(1行目の1カラム目)だけを取得し返す。
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static String selectOneString(Connection conn, String sql) throws TException {

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = getStatement(conn);
			rs = DBUtil.select(statement, sql);

			String rt = null;
			while (rs.next()) {

				// 対象出力
				StringBuilder sb = new StringBuilder();
				sb.append("result=");
				sb.append(Util.avoidNull(rs.getString(1)));
				ServerLogger.debug(sb.toString());

				rt = rs.getString(1);
				break;
			}

			close(rs);
			close(statement);

			return rt;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			close(rs);
			close(statement);
		}
	}

	/**
	 * 明示的に型を指定したメソッドを使用すること(selectOneDate/Decimal/Int/String
	 * [利用非推奨]特定の値(1行目の1カラム目)だけを取得し返す。
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	 @Deprecated 
	public static Object selectOne(Connection conn, String sql) throws TException {

		Statement statement = null;
		ResultSet rs = null;

		try {

			statement = getStatement(conn);
			rs = DBUtil.select(statement, sql);

			Object rt = null;
			while (rs.next()) {

				// 対象出力
				StringBuilder sb = new StringBuilder();
				sb.append("result=");
				sb.append(Util.avoidNull(rs.getObject(1)));
				ServerLogger.debug(sb.toString());

				rt = rs.getObject(1);
				break;
			}

			close(rs);
			close(statement);

			return rt;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			close(rs);
			close(statement);
		}
	}

	/**
	 * 明示的に型を指定したメソッドを使用すること
	 * [利用非推奨]特定の値(1行目の1カラム目)だけを取得し返す。
	 * 
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static Object selectOne(SQLCreator sql) throws TException {
		Connection conn = getConnection();

		try {
			return selectOne(conn, sql.toSQL());

		} finally {
			close(conn);
		}
	}

	/**
	 * 特定の値(1行目の1カラム目)だけを取得しint型で返す。
	 * 
	 * @param conn Connection
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static int selectOneInt(Connection conn, String sql) throws TException {

		Object obj = selectOne(conn, sql);
		if (obj == null) {
			return 0;
		}

		if (obj instanceof Number) {
			return ((Number) obj).intValue();
		}

		String str = Util.avoidNull(obj);

		if (Util.isNumber(str)) {
			return DecimalUtil.toInt(str);
		}

		return 0;
	}

	/**
	 * 特定の値(1行目の1カラム目)だけを取得しint型で返す。
	 * 
	 * @param sql SQL
	 * @return 値
	 * @throws TException
	 */
	public static int selectOneInt(SQLCreator sql) throws TException {
		Connection conn = getConnection();

		try {
			return selectOneInt(conn, sql.toSQL());

		} finally {
			close(conn);
		}
	}

	/**
	 * 変換処理
	 * 
	 * @param sql
	 * @return 変換後
	 */
	public static String convert(String sql) {

		// TODO:機能をOFF
		if (isUseMySQL || isUseSQLServer) {
			if (convertMap == null) {
				initConvertMap();
			}

			String str = sql;
			for (Entry<String, String> entry : convertMap.entrySet()) {
				str = str.replaceAll(entry.getKey(), entry.getValue());
			}
			ServerLogger.debug(str);
			return str;
		} else {
			ServerLogger.debug(sql);
			return sql;
		}

		// ServerLogger.debug(sql);
		// return sql;
	}

	/**
	 * 変換マップ初期化
	 */
	public static void initConvertMap() {
		convertMap = new HashMap<String, String>();

		File file = null;
		FileReader fr = null;
		BufferedReader br = null;

		try {

			file = ResourceUtil.getResourceAsFile("convert.rule");
			fr = new FileReader(file);
			br = new BufferedReader(fr);
			String n = null;

			while ((n = br.readLine()) != null) {
				String arr[] = n.split("\t", 2);
				if (arr.length > 1) {
					convertMap.put(arr[0], arr[1]);
				}
			}

			ServerLogger.debug("Convert rule : " + convertMap.size());

		} catch (Throwable ex) {
			ServerLogger.debug(ex);
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException ex) {
				ServerLogger.debug(ex);
			}
			try {
				if (fr != null) {
					fr.close();
				}
			} catch (IOException ex) {
				ServerLogger.debug(ex);
			}
		}
	}
}

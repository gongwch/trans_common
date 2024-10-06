package jp.co.ais.trans2.model.executedlog;

import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.log.ExecutedLogger.ExecutedFileLogger;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.program.*;

/**
 * 実行ログ参照ManagerImple
 */
public class ExecutedLogManagerImpl extends TModel implements ExecutedLogManager {

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return 実行ログ参照情報
	 * @throws TException
	 */
	public List<ExecutedLog> get(ExecutedLogSearchCondition condition) throws TException {
		if (ServerConfig.isExeLogDBMode()) {
			return getDb(condition);
		} else {
			return getFile(condition);
		}

	}

	/**
	 * 情報検索-Excel出力 (SELECT)
	 * 
	 * @param condition
	 * @return 実行ログ参照情報
	 * @throws TException
	 */
	public byte[] getExcel(ExecutedLogSearchCondition condition) throws TException {

		List<ExecutedLog> dbList = get(condition);

		try {

			if (Util.isNullOrEmpty(dbList)) {
				return null;
			}
			ExecutedLogExcel exl = new ExecutedLogExcel(getUser().getLanguage());

			return exl.getExcel(dbList);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 実行ログ一覧を取得(ファイルから)
	 * 
	 * @param condition
	 * @return 実行ログ一覧
	 */
	public List<ExecutedLog> getFile(ExecutedLogSearchCondition condition) {
		// ファイルモード
		BufferedReader br = null;

		try {
			List<ExecutedLog> logList = new ArrayList<ExecutedLog>();
			String fileName = getCompanyCode() + "." + "log";
			File file = new File(TServerEnv.USER_LOG_DIR.toString() + fileName);
			if (file.isFile()) {
				br = mappingFile(logList, file, condition);
				br.close();
			}

			int months = DateUtil.getMonthCount(condition.dateFrom, condition.dateTo);
			Date from = DateUtil.getFirstDate(condition.dateFrom);

			for (int i = 0; i <= months; i++) {

				String ext = "";

				SimpleDateFormat dateFormat = new SimpleDateFormat(ExecutedFileLogger.datePattern);
				ext = dateFormat.format(DateUtil.addMonth(from, i));

				fileName = getCompanyCode() + "." + "log" + ext;
				file = new File(TServerEnv.USER_LOG_DIR.toString() + fileName);

				if (file.isFile()) {
					br = mappingFile(logList, file, condition);
					br.close();
				} else {
					// 指定のPATHが存在しないと処理不要
					continue;
				}
			}

			// 名称を設定する。
			List<ExecutedLog> dtoList = setProgramName(logList);

			if (condition.sort == 1) {
				Collections.sort(dtoList, new DateComparator());
			} else if (condition.sort == 2) {
				Collections.sort(dtoList, new UsrCodeComparator());
			} else {
				Collections.sort(dtoList, new PrgCodeComparator());
			}

			return dtoList;

		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException ex) {
					ServerErrorHandler.handledException(ex);
				}
			}
		}
	}

	/**
	 * @param logList
	 * @param file
	 * @param condition
	 * @return BufferedReader
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected BufferedReader mappingFile(List<ExecutedLog> logList, File file, ExecutedLogSearchCondition condition)
		throws FileNotFoundException, IOException, ParseException {
		BufferedReader br;

		br = new BufferedReader(new FileReader(file));

		String line;
		while ((line = br.readLine()) != null) {
			String[] logString = StringUtil.toArrayFromDelimitString(line);

			String excutedDate = logString[0]; // 実行日付
			String usrCode = logString[1]; // ユーザコード
			String usrName = logString[2]; // ユーザ名称
			String ip = logString[3]; // IPアドレス
			String prgCode = logString[4]; // プログラムコード
			String prgName = logString[5]; // プログラム名称
			String state = logString[6]; // 開始・終了

			// 指定の期間条件を満足するか
			if (!isSearchDate(excutedDate, condition)) {
				continue;
			}

			// ユーザ検索条件に当たるか否か。
			if (!isSearchUsrCode(usrCode, condition)) {
				continue;
			}

			// ログインログアウトを表示しないと、普通にプログラムコード条件比較を行う
			if (condition.isLogin == false) {
				if (prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT)) {
					continue;
				}

				// プログラム検索条件を満足するか。
				if (!isSearchPrgCode(prgCode, condition)) {
					continue;
				}

				// ログインログアウトを表示すると、普通にプログラムコード条件比較でログインは残す処理を追加
			} else {
				// プログラム検索条件を満足するか。
				if (!isSearchPrgCode(prgCode, condition)) {
					// 条件を満足しない場合も、ログインは例外とする。
					if (!(prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT))) {
						continue;
					}
				}
			}

			// ログ値クラスの情報構築
			ExecutedLog exeFile = new ExecutedLog();
			exeFile.setExcDate(DateUtil.toYMDHMSDate(excutedDate));
			exeFile.setExcCode(usrCode);
			exeFile.setExcNames(usrName);
			exeFile.setIpAddress(ip);
			exeFile.setProCode(prgCode);
			exeFile.setProName(prgName);
			exeFile.setStste(state);

			// ログリストに追加
			logList.add(exeFile);

		}

		return br;
	}

	/**
	 * ログリストにプログラム名称を設定
	 * 
	 * @param logList
	 * @return ログリスト
	 * @throws TException
	 */
	protected List<ExecutedLog> setProgramName(List<ExecutedLog> logList) throws TException {

		List<ExecutedLog> list = logList;

		// プログラム情報を持ってくる
		List prgList = getProgramMstList();

		// それぞれのログ情報に名称をセット
		for (ExecutedLog log : list) {
			String prgName = "";
			for (int i = 0; i < prgList.size(); i++) {
				Program prg = (Program) prgList.get(i);
				if (log.getProCode().equals(prg.getCode())) {
					prgName = prg.getName();
					i = prgList.size();
				}
			}
			log.setProName(prgName);
		}

		return list;
	}

	/**
	 * プログラムリスト取得
	 * 
	 * @return プログラムリスト
	 * @throws TException
	 */
	protected List getProgramMstList() throws TException {

		ProgramSearchCondition condition = new ProgramSearchCondition();
		condition.setCompanyCode(getCompanyCode());

		ProgramManager programManager = (ProgramManager) getComponent(ProgramManager.class);
		List<Program> prgList = programManager.get(condition);

		// ログイン情報を設定
		Program login = new Program();
		login.setCompanyCode(getCompanyCode());
		login.setCode(ExecutedLogger.LOGIN);
		login.setName(getWord("C03187"));// ログイン
		prgList.add(login);

		// ログイン情報を設定
		Program logout = new Program();
		logout.setCompanyCode(getCompanyCode());
		logout.setCode(ExecutedLogger.LOGOUT);
		logout.setName(getWord("C03188"));// ログアウト
		prgList.add(logout);

		return prgList;

	}

	/**
	 * 日付検索条件によるファイル判定
	 * 
	 * @param excutedDate 検索された日付
	 * @param condition
	 * @return boolean 比較判定
	 * @throws ParseException
	 */
	protected boolean isSearchDate(String excutedDate, ExecutedLogSearchCondition condition) throws ParseException {
		Date logDate = DateUtil.toYMDDate(excutedDate);

		// ログ日付が検索範囲内だったらTrueを返す。
		if (Util.isSmallerThenByYMD(condition.getDateFrom(), logDate)
			&& Util.isSmallerThenByYMD(logDate, condition.getDateTo())) {
			return true;
		}
		return false;
	}

	/**
	 * ユーザコード検索条件によるファイル判定
	 * 
	 * @param usrCode 検索されたユーザコード
	 * @param condition
	 * @return boolean 比較判定
	 */
	protected boolean isSearchUsrCode(String usrCode, ExecutedLogSearchCondition condition) {
		if (!Util.isNullOrEmpty(condition.getUserFrom())) {
			if (condition.getUserFrom().compareTo(usrCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(condition.getUserTo())) {
			if (condition.getUserTo().compareTo(usrCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * プログラムコード検索条件によるファイル判定
	 * 
	 * @param prgCode 検索されたプログラムコード
	 * @param condition
	 * @return boolean 比較判定
	 */
	protected boolean isSearchPrgCode(String prgCode, ExecutedLogSearchCondition condition) {
		if (!Util.isNullOrEmpty(condition.getProgramFrom())) {
			if (condition.getProgramFrom().compareTo(prgCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(condition.getProgramTo())) {
			if (condition.getProgramTo().compareTo(prgCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 日付並び順の比較クラス
	 * 
	 * @author roh
	 */
	protected class DateComparator implements Comparator {

		/**
		 * 日付比較条件
		 * 
		 * @param first 比較対象の最初のオブジェクト
		 * @param second 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object first, Object second) {

			Date date1 = ((ExecutedLog) first).getExcDate();
			Date date2 = ((ExecutedLog) second).getExcDate();

			return date1.compareTo(date2);
		}
	}

	/**
	 * ユーザ並び順の比較クラス
	 * 
	 * @author roh
	 */
	protected class UsrCodeComparator implements Comparator {

		/**
		 * ユーザコード比較条件
		 * 
		 * @param first 比較対象の最初のオブジェクト
		 * @param second 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object first, Object second) {
			String user1 = ((ExecutedLog) first).getExcCode().toUpperCase();
			String user2 = ((ExecutedLog) second).getExcCode().toUpperCase();

			return user1.compareTo(user2);
		}
	}

	/**
	 * プログラムコード並び順の比較クラス
	 * 
	 * @author roh
	 */
	protected class PrgCodeComparator implements Comparator {

		/**
		 * プログラムコード比較条件
		 * 
		 * @param first 比較対象の最初のオブジェクト
		 * @param second 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object first, Object second) {
			String prg1 = ((ExecutedLog) first).getProCode().toUpperCase();
			String prg2 = ((ExecutedLog) second).getProCode().toUpperCase();

			return prg1.compareTo(prg2);
		}
	}

	/**
	 * 情報検索 (SELECT)
	 * 
	 * @param condition 検索条件
	 * @return List 検索結果
	 * @throws TException
	 */
	public List<ExecutedLog> getDb(ExecutedLogSearchCondition condition) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		// 検索結果格納用配列生成
		List<ExecutedLog> list = new ArrayList<ExecutedLog>();
		SQLCreator sql = new SQLCreator();
		try {
			sql.add("");
			sql.add("SELECT");
			sql.add("    log.EXCUTED_DATE");
			sql.add("    ,log.KAI_CODE");
			sql.add("    ,log.USR_CODE");
			sql.add("    ,log.USR_NAME");
			sql.add("    ,usr.USR_NAME_S");
			sql.add("    ,log.IP_ADDRESS");
			sql.add("    ,log.PROGRAM_CODE");
			sql.add("    ,prg.PRG_NAME");
			sql.add("    ,log.STATE");
			sql.add("FROM EXE_LOG_TBL log");
			sql.add("    LEFT OUTER JOIN USR_MST usr");
			sql.add("      ON log.KAI_CODE = usr.KAI_CODE");
			sql.add("      AND log.USR_CODE = usr.USR_CODE");
			sql.add("    LEFT OUTER JOIN PRG_MST prg");
			sql.add("      ON log.KAI_CODE = prg.KAI_CODE");
			sql.add("      AND log.PROGRAM_CODE = prg.PRG_CODE");
			sql.add("WHERE 1 = 1");

			// 検索条件：会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND");
				sql.add("    log.KAI_CODE = ?", condition.getCompanyCode());
			}

			// 検索条件：対象年月日
			sql.add("AND");
			sql.adt("    log.EXCUTED_DATE >= ?", condition.getDateFrom());
			sql.add("AND");
			sql.adt("    log.EXCUTED_DATE <= ?", condition.getDateTo());

			// 検索条件：ユーザー開始コード
			if (!Util.isNullOrEmpty(condition.getUserFrom())) {
				sql.add("AND");
				sql.add("    log.USR_CODE >= ?", condition.getUserFrom());
			}
			// 検索条件：ユーザー終了コード
			if (!Util.isNullOrEmpty(condition.getUserTo())) {
				sql.add("AND");
				sql.add("    log.USR_CODE <= ?", condition.getUserTo());
			}
			// 検索条件：プログラム開始コード
			if (!Util.isNullOrEmpty(condition.getProgramFrom())) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE >= ?", condition.getProgramFrom());
			}
			// 検索条件：プログラム終了コード
			if (!Util.isNullOrEmpty(condition.getProgramTo())) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE <= ?", condition.getProgramTo());
			}

			if (condition.isLogin == false) {
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE NOT LIKE 'LOGIN'");
				sql.add("AND");
				sql.add("    log.PROGRAM_CODE NOT LIKE 'LOGOUT'");
			}

			sql.add("ORDER BY");
			if (condition.getSort() == 1) {
				sql.add(" log.EXCUTED_DATE ");
			} else if (condition.getSort() == 2) {
				sql.add(" log.USR_CODE ");
				sql.add(" ,log.EXCUTED_DATE ");
			} else {
				sql.add(" log.PROGRAM_CODE");
				sql.add(" ,log.EXCUTED_DATE");
			}

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
	 * 検索結果値をBeanにセット
	 * 
	 * @param rs ResultSet 検索結果
	 * @return Employee 検索結果値がセットされたBean
	 * @throws Exception
	 */
	protected ExecutedLog mapping(ResultSet rs) throws Exception {

		ExecutedLog bean = new ExecutedLog();

		bean.setExcDate(rs.getTimestamp("EXCUTED_DATE"));
		bean.setKaiCode(rs.getString("KAI_CODE"));
		bean.setExcCode(rs.getString("USR_CODE"));
		bean.setExcName(rs.getString("USR_NAME"));
		bean.setExcNames(rs.getString("USR_NAME_S"));
		bean.setIpAddress(rs.getString("IP_ADDRESS"));
		bean.setProCode(rs.getString("PROGRAM_CODE"));
		bean.setProName(rs.getString("PRG_NAME"));
		bean.setStste(rs.getString("STATE"));

		return bean;
	}
}

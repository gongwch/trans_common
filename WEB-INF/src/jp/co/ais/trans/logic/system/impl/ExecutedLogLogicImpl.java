package jp.co.ais.trans.logic.system.impl;

import java.io.*;
import java.text.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.log.ExecutedLogger.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 実行ログ参照ロジック
 */
public class ExecutedLogLogicImpl implements ExecutedLogLogic {

	/** プログラムマスタDao */
	protected PRG_MSTDao prgDao;

	/** 実行ログDao */
	protected ExecutedLogDao exeLogDao;

	/** 拡張子 */
	protected final String FILE_EXTENSION = "log";

	/** 日付基準ソート */
	protected final int SORT_DATE = 1;

	/** ユーザコード基準ソード */
	protected final int SORT_USR_CODE = 2;

	/** プログラム基準ソード */
	protected final int SORT_PRG_CODE = 3;

	/** 検索開始日付 */
	protected Date startDate;

	/** 検索終了日付 */
	protected Date endDate;

	/** 検索開始ユーザ */
	protected String startUser;

	/** 検索終了ユーザ */
	protected String endUser;

	/** 検索開始プログラム */
	protected String startPrg;

	/** 検索終了プログラム */
	protected String endPrg;

	/** 会社コード */
	protected String companyCode;

	/** ログインアウト区分 */
	protected boolean isIncludeLogin = false;

	/** ソートキー */
	protected int sort;

	/** 言語コード */
	protected String langCode;

	/**
	 * ログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 * @param prgCode プログラムID
	 * @param state 状態
	 */
	public void log(String userCode, String userName, String ipAddress, String prgCode, String state) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(prgCode);
		logData.setSTATE(state);
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * ログインログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public void logLogin(String userCode, String userName, String ipAddress) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(ExecutedLogger.LOGIN);
		logData.setSTATE("");
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * ログアウトログ実行
	 * 
	 * @param userCode ユーザコード
	 * @param userName ユーザ名称
	 * @param ipAddress IPアドレス
	 */
	public void logLogout(String userCode, String userName, String ipAddress) {

		ExecutedLog logData = new ExecutedLog();
		logData.setEXCUTED_DATE(Util.getCurrentDate());
		logData.setIP_ADDRESS(ipAddress);
		logData.setPROGRAM_CODE(ExecutedLogger.LOGOUT);
		logData.setSTATE("");
		logData.setUSR_CODE(userCode);
		logData.setUSR_NAME(userName);
		logData.setKAI_CODE(companyCode);

		exeLogDao.insertLog(logData);
	}

	/**
	 * 実行ログ一覧を取得
	 * 
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#getExecutedLogList()
	 */

	public List<ExecutedLog> getExecutedLogList() {

		// DBモードの場合
		if (ServerConfig.isExeLogDBMode()) {
			return getExecutedLogDBList();
		}

		// ファイルモードの場合
		return getExecutedFileLogList();
	}

	/**
	 * 実行ログ一覧を取得(DBから)
	 * 
	 * @return 実行ログ一覧
	 */
	protected List<ExecutedLog> getExecutedLogDBList() {

		ExecutedLogSearchParam param = new ExecutedLogSearchParam();

		param.setCompanyCode(companyCode);
		param.setEndDate(endDate);
		param.setEndPrg(endPrg);
		param.setEndUser(endUser);
		param.setIsIncludeLogin(BooleanUtil.toInt(isIncludeLogin));
		param.setStartDate(startDate);
		param.setStartPrg(startPrg);
		param.setStartUser(startUser);

		String strSort = "";

		// 並び順処理
		switch (sort) {

		// 日付順
			case SORT_DATE:
				strSort = "log.EXCUTED_DATE";
				break;

			// ユーザコード順
			case SORT_USR_CODE:
				strSort = "log.USR_CODE,log.EXCUTED_DATE";
				break;

			// プログラムコード順
			case SORT_PRG_CODE:
				strSort = "log.PROGRAM_CODE,log.EXCUTED_DATE";
				break;
		}

		param.setOrderBy(strSort);

		List<ExecutedLog> list = exeLogDao.getExecutedLogList(param);

		for (ExecutedLog log : list) {
			if (ExecutedLogger.LOGIN.equals(log.getPROGRAM_CODE())) {
				log.setPROGRAM_NAME(MessageUtil.getWord(langCode, "C03187")); // ログイン

			} else if (ExecutedLogger.LOGOUT.equals(log.getPROGRAM_CODE())) {

				log.setPROGRAM_NAME(MessageUtil.getWord(langCode, "C03188")); // ログアウト
			}
		}

		return list;
	}

	/**
	 * 実行ログ一覧を取得(ファイルから)
	 * 
	 * @return 実行ログ一覧
	 */
	protected List<ExecutedLog> getExecutedFileLogList() {
		// ファイルモード
		BufferedReader br = null;

		try {
			List<ExecutedLog> logList = new ArrayList<ExecutedLog>();

			String filename = companyCode + "." + FILE_EXTENSION;
			File file = new File(TServerEnv.USER_LOG_DIR.toString() + filename);
			if (file.isFile()) {
				br = mappingFileLog(logList, file);
				br.close();
			}

			int months = DateUtil.getMonthCount(startDate, endDate);
			Date from = DateUtil.getFirstDate(startDate);

			for (int i = 0; i <= months; i++) {

				String ext = "";

				SimpleDateFormat DATE_FORMAT_YM = new SimpleDateFormat(ExecutedFileLogger.datePattern);
				ext = DATE_FORMAT_YM.format(DateUtil.addMonth(from, i));

				filename = companyCode + "." + FILE_EXTENSION + ext;
				file = new File(TServerEnv.USER_LOG_DIR.toString() + filename);

				if (file.isFile()) {
					br = mappingFileLog(logList, file);
					br.close();
				} else {
					// 指定のPATHが存在しないと処理不要
					continue;
				}
			}

			// 入力ストリームを閉じる
			// 名称を設定する。
			List<ExecutedLog> dtoList = setProgramName(logList);

			// 並び順処理
			switch (sort) {

			// 日付順
				case SORT_DATE:
					sortByDate(dtoList);
					break;

				// ユーザコード順
				case SORT_USR_CODE:
					sortByUserCode(dtoList);
					break;

				// プログラムコード順
				case SORT_PRG_CODE:
					sortByProgramCode(dtoList);
					break;
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
	 * @return BufferedReader
	 * @throws FileNotFoundException
	 * @throws IOException
	 * @throws ParseException
	 */
	protected BufferedReader mappingFileLog(List<ExecutedLog> logList, File file) throws FileNotFoundException,
		IOException, ParseException {
		BufferedReader br;
		br = new BufferedReader(new FileReader(file));

		String pLine;
		while ((pLine = br.readLine()) != null) {

			String[] logString = StringUtil.toArrayFromDelimitString(pLine);

			String excutedDate = logString[0]; // 実行日付
			String usrCode = logString[1]; // ユーザコード
			String usrName = logString[2]; // ユーザ名称
			String ip = logString[3]; // IPアドレス
			String prgCode = logString[4]; // プログラムコード
			String prgName = logString[5]; // プログラム名称
			String state = logString[6]; // 開始・終了

			// 指定の期間条件を満足するか
			if (!isSearchDate(excutedDate)) {
				continue;
			}

			// ユーザ検索条件に当たるか否か。
			if (!isSearchUsrCode(usrCode)) {
				continue;
			}

			// ログインログアウトを表示しないと、普通にプログラムコード条件比較を行う
			if (!isIncludeLogin) {
				if (prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT)) {
					continue;
				}

				// プログラム検索条件を満足するか。
				if (!isSearchPrgCode(prgCode)) {
					continue;
				}

				// ログインログアウトを表示すると、普通にプログラムコード条件比較でログインは残す処理を追加
			} else {
				// プログラム検索条件を満足するか。
				if (!isSearchPrgCode(prgCode)) {
					// 条件を満足しない場合も、ログインは例外とする。
					if (!(prgCode.equals(ExecutedLogger.LOGIN) || prgCode.equals(ExecutedLogger.LOGOUT))) {
						continue;
					}
				}
			}

			// ログ値クラスの情報構築
			ExecutedLog logObj = new ExecutedLog();
			logObj.setEXCUTED_DATE(DateUtil.toYMDHMSDate(excutedDate));
			logObj.setUSR_CODE(usrCode);
			logObj.setUSR_NAME(usrName);
			logObj.setIP_ADDRESS(ip);
			logObj.setPROGRAM_CODE(prgCode);
			logObj.setPROGRAM_NAME(prgName);
			logObj.setSTATE(state);

			// ログリストに追加
			logList.add(logObj);
		}
		return br;
	}

	/**
	 * 日付検索条件によるファイル検索判定
	 * 
	 * @param excutedDate 検索された日付
	 * @return boolean 比較判定
	 * @throws ParseException
	 */
	protected boolean isSearchDate(String excutedDate) throws ParseException {
		Date logDate = DateUtil.toYMDDate(excutedDate);

		// ログ日付が検索範囲内だったらTrueを返す。
		if (Util.isSmallerThenByYMD(startDate, logDate) && Util.isSmallerThenByYMD(logDate, endDate)) {
			return true;
		}
		return false;
	}

	/**
	 * ユーザコード検索条件によるリスト追加判定
	 * 
	 * @param usrCode 検索されたユーザコード
	 * @return boolean 比較判定
	 */
	protected boolean isSearchUsrCode(String usrCode) {
		if (!Util.isNullOrEmpty(startUser)) {
			if (startUser.compareTo(usrCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(endUser)) {
			if (endUser.compareTo(usrCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * プログラムコード検索条件によるリスト追加判定
	 * 
	 * @param prgCode 検索されたプログラムコード
	 * @return boolean 比較判定
	 */
	protected boolean isSearchPrgCode(String prgCode) {
		if (!Util.isNullOrEmpty(startPrg)) {
			if (startPrg.compareTo(prgCode) > 0) {
				return false;
			}
		}

		if (!Util.isNullOrEmpty(endPrg)) {
			if (endPrg.compareTo(prgCode) < 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 日付でソートします
	 * 
	 * @param list 対象リスト
	 */
	public void sortByDate(List<ExecutedLog> list) {

		Collections.sort(list, new DateComparator());
	}

	/**
	 * ユーザコードでソートします
	 * 
	 * @param list 対象リスト
	 */
	public void sortByUserCode(List<ExecutedLog> list) {

		Collections.sort(list, new UsrCodeComparator());
	}

	/**
	 * プログラムコードでソートします
	 * 
	 * @param list 対象リスト
	 */
	public void sortByProgramCode(List<ExecutedLog> list) {

		Collections.sort(list, new PrgCodeComparator());
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setCompanyCode(String)
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartDate(Date)
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndDate(Date)
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartProgramCode(String)
	 */
	public void setStartProgramCode(String prgCode) {
		this.startPrg = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndProgramCode(String)
	 */
	public void setEndProgramCode(String prgCode) {
		this.endPrg = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setStartUserCode(String)
	 */
	public void setStartUserCode(String userCode) {
		this.startUser = userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#setEndUserCode(String)
	 */
	public void setEndUserCode(String userCode) {
		this.endUser = userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#onLogin()
	 */
	public void onLogin() {
		this.isIncludeLogin = true;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.ExecutedLogLogic#offLogin()
	 */
	public void offLogin() {
		this.isIncludeLogin = false;
	}

	/**
	 * 言語コード設定
	 * 
	 * @param langCode
	 */
	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	/**
	 * @param prgDao
	 */
	public void setprgDao(PRG_MSTDao prgDao) {
		this.prgDao = prgDao;
	}

	/**
	 * ログリストにプログラム名称を設定
	 * 
	 * @param logList
	 * @return ログリスト
	 */
	protected List<ExecutedLog> setProgramName(List<ExecutedLog> logList) {
		List<ExecutedLog> list = logList;

		// プログラム一覧を持ってくる
		List prgList = getProgramMstList();

		// それぞれのログ情報に名称をセット
		for (ExecutedLog log : list) {
			String prgName = "";
			for (int i = 0; i < prgList.size(); i++) {
				PRG_MST prgMst = (PRG_MST) prgList.get(i);
				if (log.getPROGRAM_CODE().equals(prgMst.getPRG_CODE())) {
					prgName = prgMst.getPRG_NAME();
					i = prgList.size();
				}
			}
			log.setPROGRAM_NAME(prgName);
		}

		return list;
	}

	/**
	 * プログラムリスト取得
	 * 
	 * @return プログラムリスト
	 */
	protected List getProgramMstList() {
		// 全プログラムリスト取得
		List list = prgDao.getAllPRG_MST2(companyCode);

		// ログイン情報を設定
		PRG_MST loginLogObj = new PRG_MST();

		loginLogObj.setPRG_CODE(ExecutedLogger.LOGIN);
		loginLogObj.setPRG_NAME(MessageUtil.getWord(langCode, "C03187"));
		list.add(loginLogObj);

		// ログイン情報を設定
		PRG_MST logoutLogObj = new PRG_MST();

		logoutLogObj.setPRG_CODE(ExecutedLogger.LOGOUT);
		logoutLogObj.setPRG_NAME(MessageUtil.getWord(langCode, "C03188"));
		list.add(logoutLogObj);

		list.add(logoutLogObj);

		return list;

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
		 * @param o1 比較対象の最初のオブジェクト
		 * @param o2 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object o1, Object o2) {

			Date o1Date = ((ExecutedLog) o1).getEXCUTED_DATE();
			Date o2Date = ((ExecutedLog) o2).getEXCUTED_DATE();

			return o1Date.compareTo(o2Date);
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
		 * @param o1 比較対象の最初のオブジェクト
		 * @param o2 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object o1, Object o2) {
			String o1Code = ((ExecutedLog) o1).getUSR_CODE().toUpperCase();
			String o2Code = ((ExecutedLog) o2).getUSR_CODE().toUpperCase();

			return o1Code.compareTo(o2Code);
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
		 * @param o1 比較対象の最初のオブジェクト
		 * @param o2 比較対象の 2 番目のオブジェクト
		 * @return boolean 比較結果
		 */
		public int compare(Object o1, Object o2) {
			String o1Code = ((ExecutedLog) o1).getPROGRAM_CODE().toUpperCase();
			String o2Code = ((ExecutedLog) o2).getPROGRAM_CODE().toUpperCase();

			return o1Code.compareTo(o2Code);
		}
	}

	/**
	 * exeLogDao設定
	 * 
	 * @param exeLogDao
	 */
	public void setExeLogDao(ExecutedLogDao exeLogDao) {
		this.exeLogDao = exeLogDao;
	}

	/**
	 * sort設定
	 * 
	 * @param sort
	 */
	public void setSort(int sort) {
		this.sort = sort;
	}

}

package jp.co.ais.trans2.model.security;

import java.sql.Connection;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.db.SQLUtil;

/**
 * ログに関するDao実装
 * @author AIS
 *
 */
public class LogDaoImpl implements LogDao {

	/**
	 * ログを登録する
	 * @param log
	 * @throws TException
	 */
	public void entry(Log log) throws TException {

		Connection conn = DBUtil.getConnection();

		String prgCode = log.getProgram().getCode(); // プログラムコード
		String message = log.getMessage(); // メッセージ

		if (Util.isNullOrEmpty(prgCode)) {
			if ("Login".equals(message)) {
				prgCode = "LOGIN";
				message = null;
			} else {
				prgCode = "LOGOUT";
				message = null;
			}
		}

		try {

			String sql =
				" INSERT INTO EXE_LOG_TBL ( " +
					" EXCUTED_DATE, " +
					" KAI_CODE, " +
					" USR_CODE, " +
					" USR_NAME, " +
					" IP_ADDRESS, " +
					" PROGRAM_CODE, " +
					" STATE " +
				" ) VALUES ( " +
					SQLUtil.getYMDHMSParam(log.getDate()) + ", " +
					SQLUtil.getParam(log.getCompany().getCode()) + ", " +
					SQLUtil.getParam(log.getUser().getCode()) + ", " +
					SQLUtil.getParam(log.getUser().getName()) + ", " +
					SQLUtil.getParam(log.getIp()) + ", " +
					SQLUtil.getParam(prgCode) + ", " +
					SQLUtil.getParam(message) +
				" ) ";

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

}

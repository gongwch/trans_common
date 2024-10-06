package jp.co.ais.trans2.model.security;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * プログラム排他の実装
 * @author AIS
 *
 */
public class ProgramExclusiveManagerImpl extends TModel implements ProgramExclusiveManager {

	/**
	 * 排他する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode) throws TException {
		this.exclude(companyCode,  userCode,  programCode, programCode);
	}
	
	/**
	 * 排他する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param programCode プログラムコード
	 * @param processID 処理ID
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode, String processID) throws TException {

		Connection conn = DBUtil.getConnection();
		Statement st = null;

		try {

			// バッチコントロールマスタをロック
			try {
				DBUtil.execute(conn, "LOCK TABLE BAT_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
//				 只今混み合っております。しばらくお待ちください。
				throw new TException("W01133");
			}

			String sql =
				" SELECT	USR_ID, PRG_ID " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND		BAT_ID = " + SQLUtil.getParam(processID);

			st = conn.createStatement();
			ResultSet rs = DBUtil.select(st, sql);

			if (rs.next()) {
				if (!userCode.equals(rs.getString("USR_ID"))) {
					throw new TException("W01134");// 他ユーザーが使用中です。
				}

				if (!programCode.equals(rs.getString("PRG_ID"))) {
					throw new TException("W01135");// 他のプログラムで使用中です。
				}

				
				// 自身の排他の場合、INSERT無しで終了
				return;
			}

			sql =
				" INSERT INTO BAT_CTL ( " +
					" KAI_CODE, " +
					" BAT_ID, " +
					" BAT_STR_DATE, " +
					" INP_DATE, " +
					" PRG_ID, " +
					" USR_ID " +
				" ) VALUES ( " +
					SQLUtil.getParam(companyCode) + ", " +
					SQLUtil.getParam(processID) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getYMDHMSParam(getNow()) + ", " +
					SQLUtil.getParam(programCode) + ", " +
					SQLUtil.getParam(userCode) +
				" ) ";

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
			
		} catch (Exception e) {
			throw new TException(e);
			
		} finally {
			DBUtil.close(st);
			DBUtil.close(conn);
		}

	}

	/**
	 * 排他解除する
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException {
		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(getCompanyCode()) + 
				" AND		USR_ID = " + SQLUtil.getParam(getUserCode()) +
				" AND		PRG_ID = " + SQLUtil.getParam(getProgramCode());

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
	
	/**
	 * 排他解除する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザコード
	 * @param processID 処理ID
	 * @throws TException
	 */
	public void cancelExclude(String companyCode, @SuppressWarnings("unused") String userCode, String processID) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			String sql =
				" DELETE " +
				" FROM		BAT_CTL " +
				" WHERE		KAI_CODE = " + SQLUtil.getParam(companyCode) +
				" AND		BAT_ID = " + SQLUtil.getParam(processID);

			DBUtil.execute(conn, sql);

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}
}

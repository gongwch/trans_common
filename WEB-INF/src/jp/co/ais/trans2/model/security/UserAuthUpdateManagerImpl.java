package jp.co.ais.trans2.model.security;

import java.sql.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * ユーザー認証インターフェース実装
 * 
 * @author AIS
 */
public class UserAuthUpdateManagerImpl extends TModel implements UserAuthUpdateManager {

	/**
	 * @see ユーザ認証管理マスタ検索処理
	 */
	public USR_AUTH_MST get() throws TException {

		Connection conn = DBUtil.getConnection();
		USR_AUTH_MST bean = new USR_AUTH_MST();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add(" KAI_CODE ");
			sql.add(" ,LOCK_OUT_ARR_CNT");
			sql.add(" ,LOCK_OUT_RELEASE_TIME");
			sql.add(" ,PWD_MIN_LENGTH");
			sql.add(" ,PWD_TERM");
			sql.add(" ,COMPLICATE_LVL");
			sql.add(" ,HISTORY_MAX_CNT");
			sql.add(" ,INP_DATE");
			sql.add(" ,UPD_DATE");
			sql.add(" ,PRG_ID");
			sql.add(" ,USR_ID");
			sql.add(" ,PWD_EXP_BEFORE_DAYS");
			sql.add(" FROM ");
			sql.add(" USR_AUTH_MST uau ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND uau.KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bean = (mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return bean;
	}

	/**
	 * @see ユーザ認証管理マスタ更新処理
	 */
	public void modify(USR_AUTH_MST userauthupdate) throws TException {

		USR_AUTH_MST usr_auth_mst = get();// 検索してデータの存在チェック

		if (Util.isNullOrEmpty(usr_auth_mst.getKAI_CODE())) {// 既存データが存在しなければ、新規登録

			Connection conn = DBUtil.getConnection();
			try {

				SQLCreator sql = new SQLCreator();

				sql.add(" INSERT INTO USR_AUTH_MST ( ");
				sql.add(" KAI_CODE ");
				sql.add(" ,LOCK_OUT_ARR_CNT");
				sql.add(" ,LOCK_OUT_RELEASE_TIME");
				sql.add(" ,PWD_MIN_LENGTH");
				sql.add(" ,PWD_TERM");
				sql.add(" ,COMPLICATE_LVL");
				sql.add(" ,HISTORY_MAX_CNT");
				sql.add(" ,INP_DATE");
				sql.add(" ,UPD_DATE");
				sql.add(" ,PRG_ID");
				sql.add(" ,USR_ID");
				sql.add(" ,PWD_EXP_BEFORE_DAYS");
				sql.add(" ) VALUES (");
				sql.add(" ?,", userauthupdate.getKAI_CODE());
				sql.add(" ?,", userauthupdate.getLOCK_OUT_ARR_CNT());
				sql.add(" ?,", userauthupdate.getLOCK_OUT_RELEASE_TIME());
				sql.add(" ?,", userauthupdate.getPWD_MIN_LENGTH());
				sql.add(" ?,", userauthupdate.getPWD_TERM());
				sql.add(" ?,", userauthupdate.getCOMPLICATE_LVL());
				sql.add(" ?,", userauthupdate.getHISTORY_MAX_CNT());
				sql.adt(" ?,", getNow());
				sql.add(" NULL, ");
				sql.add(" ?,", getProgramCode());
				sql.add(" ?, ", getUserCode());
				sql.add(" ?", userauthupdate.getPWD_EXP_BEFORE_DAYS());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);

			} catch (Exception e) {
				throw new TException(e);
			} finally {
				DBUtil.close(conn);
			}
		} else {// 既存データが存在しているなら、更新

			Connection conn = DBUtil.getConnection();
			try {

				SQLCreator sql = new SQLCreator();

				sql.add(" UPDATE USR_AUTH_MST ");
				sql.add(" SET ");
				sql.add(" KAI_CODE = ?", userauthupdate.getKAI_CODE());
				sql.add(" ,LOCK_OUT_ARR_CNT = ?", userauthupdate.getLOCK_OUT_ARR_CNT());
				sql.add(" ,LOCK_OUT_RELEASE_TIME = ?", userauthupdate.getLOCK_OUT_RELEASE_TIME());
				sql.add(" ,PWD_MIN_LENGTH = ?", userauthupdate.getPWD_MIN_LENGTH());
				sql.add(" ,PWD_TERM = ?", userauthupdate.getPWD_TERM());
				sql.add(" ,COMPLICATE_LVL = ?", userauthupdate.getCOMPLICATE_LVL());
				sql.add(" ,HISTORY_MAX_CNT = ?", userauthupdate.getHISTORY_MAX_CNT());
				sql.adt(" ,UPD_DATE = ?", getNow());
				sql.add(" ,PRG_ID = ?", getProgramCode());
				sql.add(" ,USR_ID = ?", getUserCode());
				sql.add(" ,PWD_EXP_BEFORE_DAYS = ?", userauthupdate.getPWD_EXP_BEFORE_DAYS());
				sql.add(" WHERE KAI_CODE = ?", userauthupdate.getKAI_CODE());

				DBUtil.execute(conn, sql);

			} catch (Exception e) {
				throw new TException(e);
			} finally {
				DBUtil.close(conn);
			}
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected USR_AUTH_MST mapping(ResultSet rs) throws Exception {

		USR_AUTH_MST bean = new USR_AUTH_MST();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setLOCK_OUT_ARR_CNT(rs.getInt("LOCK_OUT_ARR_CNT"));
		bean.setLOCK_OUT_RELEASE_TIME(rs.getString("LOCK_OUT_RELEASE_TIME"));
		bean.setPWD_MIN_LENGTH(rs.getInt("PWD_MIN_LENGTH"));
		bean.setPWD_TERM(rs.getString("PWD_TERM"));
		bean.setCOMPLICATE_LVL(rs.getInt("COMPLICATE_LVL"));
		bean.setHISTORY_MAX_CNT(rs.getInt("HISTORY_MAX_CNT"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setPWD_EXP_BEFORE_DAYS(rs.getString("PWD_EXP_BEFORE_DAYS"));

		return bean;

	}

}

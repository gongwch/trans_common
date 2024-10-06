package jp.co.ais.trans2.model.security;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * バッチ排他解除マスタインターフェース実装
 * 
 * @author AIS
 */
public class BatchUnLockManagerImpl extends TModel implements BatchUnLockManager {

	/**
	 * バッチ排他解除マスタ検索処理
	 */
	public List<BatchUnLock> get() throws TException {

		Connection conn = DBUtil.getConnection();
		List<BatchUnLock> list = new ArrayList<BatchUnLock>();

		String prgTableName = getProgramTableName(); // プログラムマスタ
		
		try {

			// 検索処理
			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("  bat.BAT_ID ");
			sql.add(" ,pgm.PRG_NAME");
			sql.add(" ,bat.USR_ID");
			sql.add(" ,usm.USR_NAME");
			sql.add(" ,bat.BAT_STR_DATE");
			sql.add(" ,bat.KAI_CODE ");
			sql.add(" FROM BAT_CTL bat ");
			sql.add(" LEFT OUTER JOIN USR_MST usm");
			sql.add(" ON  bat.KAI_CODE = usm.KAI_CODE ");
			sql.add(" AND bat.USR_ID   = usm.USR_CODE ");
			sql.add(" LEFT OUTER JOIN " + prgTableName + " pgm  ");
			sql.add(" ON  bat.KAI_CODE = pgm.KAI_CODE ");
			sql.add(" AND bat.BAT_ID = pgm.PRG_CODE ");
			sql.add(" WHERE bat.KAI_CODE = ? ", getCompanyCode());
			sql.add(" ORDER BY bat.USR_ID ");

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
	 * バッチ排他解除マスタ更新処理ループ
	 */
	public void delete(List<BatchUnLock> list) throws TException {

		Connection conn = DBUtil.getConnection();
		try {

			for (BatchUnLock bean : list) {
				SQLCreator sql = new SQLCreator();
				sql.add(" DELETE FROM BAT_CTL");
				sql.add(" WHERE KAI_CODE = ?", bean.getKAI_CODE());
				sql.add(" AND   BAT_ID   = ?", bean.getBAT_ID());
				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected BatchUnLock mapping(ResultSet rs) throws Exception {

		BatchUnLock bean = new BatchUnLock();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setBAT_ID(rs.getString("BAT_ID"));
		bean.setPRG_NAME(rs.getString("PRG_NAME"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setUSR_NAME(rs.getString("USR_NAME"));
		bean.setBAT_STR_DATE(DateUtil.toYMDHMSString(rs.getTimestamp("BAT_STR_DATE")));

		return bean;

	}

	/**
	 * プログラムマスタ テーブル名称
	 * 
	 * @return テーブル名
	 * @throws TException
	 */
	protected String getProgramTableName() throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			String tableName = "PRG_MST_" +  Util.avoidNull(getUser().getLanguage()).toUpperCase();
			DBUtil.selectOneInt(conn, "SELECT COUNT(*) FROM " + tableName);

			// ユーザ言語に紐付くテーブルが存在していれば言語付きテーブル
			return tableName;

		} catch (Exception e) {
			// 無ければデフォルト
			return "PRG_MST";

		} finally {
			DBUtil.close(conn);
		}
	}
}

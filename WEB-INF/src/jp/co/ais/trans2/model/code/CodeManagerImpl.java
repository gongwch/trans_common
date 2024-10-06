package jp.co.ais.trans2.model.code;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;

/**
 * コードマスタ共通マネージャ実現
 */
public class CodeManagerImpl extends TModel implements CodeManager {

	/**
	 * コードマスタリストの取得
	 * 
	 * @param condition
	 * @return コードマスタリスト
	 * @throws TException
	 */
	public List<OP_CODE_MST> get(CodeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<OP_CODE_MST> list = new ArrayList<OP_CODE_MST>();
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT cm.* ");
			sql.add(" FROM   OP_CODE_MST cm ");
			sql.add(" WHERE  cm.KAI_CODE = ? ", condition.getKAI_CODE()); // 会社コード

			if (!Util.isNullOrEmpty(condition.getCODE_DIV())) {
				sql.add(" AND    cm.CODE_DIV = ? ", condition.getCODE_DIV()); // コード区分
			}

			if (!Util.isNullOrEmpty(condition.getCODE())) {
				sql.add(" AND    cm.CODE = ? ", condition.getCODE()); // コード
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cm.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR cm.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			// 内航・外航個別取得
			if (condition.getLocal() != null) {
				sql.add(" AND cm.LCL_KBN = ? ", BooleanUtil.toInt(condition.getLocal()));
			}

			sql.add(" ORDER BY cm.KAI_CODE ");
			sql.add("         ,cm.CODE_DIV ");
			sql.add("         ,cm.DISP_ODR ");
			sql.add("         ,cm.CODE ");

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
	 * マッピング
	 * 
	 * @param rs
	 * @return 油種
	 * @throws Exception
	 */
	protected OP_CODE_MST mapping(ResultSet rs) throws Exception {

		OP_CODE_MST bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // 会社コード
		bean.setCODE(rs.getString("CODE")); // コード
		bean.setCODE_DIV(rs.getString("CODE_DIV")); // コード区分
		bean.setDISP_ODR(rs.getInt("DISP_ODR")); // 表示順
		bean.setCODE_NAME(rs.getString("CODE_NAME")); // コード名
		bean.setLCL_KBN(rs.getInt("LCL_KBN")); // 内航区分（1:内航）
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // 登録年月日
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // 更新年月日
		bean.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
		bean.setUSR_ID(rs.getString("USR_ID")); // ユーザーID

		return bean;
	}

	/**
	 * @return 油種
	 */
	protected OP_CODE_MST createEntity() {
		return new OP_CODE_MST();
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(CodeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   OP_CODE_MST cm ");
			sql.add(" WHERE  cm.KAI_CODE = ? ", condition.getKAI_CODE()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cm.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cm.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cm.INP_DATE IS NULL AND cm.UPD_DATE IS NULL) ");
			}

			// 削除あり＝現在持っている件数はDBの過去の件数と不一致
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

	/**
	 * SQL用
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

package jp.co.ais.trans2.model.bunkertype;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.code.*;

/**
 * Bunker Type Manage rImpl
 */
public class BunkerTypeManagerImpl extends TModel implements BunkerTypeManager {

	/**
	 * 油種リストの取得
	 * 
	 * @param condition
	 * @return 油種リスト
	 * @throws TException
	 */
	public List<CM_BNKR_TYPE_MST> get(BunkerTypeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<CM_BNKR_TYPE_MST> list = new ArrayList<CM_BNKR_TYPE_MST>();
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT bnk.* ");
			sql.add(" FROM   CM_BNKR_TYPE_MST bnk ");
			sql.add(" WHERE  bnk.KAI_CODE = ? ", condition.getKAI_CODE()); // 会社コード

			if (!Util.isNullOrEmpty(condition.getBNKR_TYPE_CODE())) {
				sql.add(" AND    bnk.BNKR_TYPE_CODE = ? ", condition.getBNKR_TYPE_CODE()); // 油種コード
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (bnk.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR bnk.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add(" ORDER BY bnk.KAI_CODE ");
			sql.add("         ,bnk.DISP_ODR ");
			sql.add("         ,bnk.BNKR_TYPE_CODE ");

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
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(BunkerTypeSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   CM_BNKR_TYPE_MST bnk ");
			sql.add(" WHERE  bnk.KAI_CODE = ? ", condition.getKAI_CODE()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (bnk.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR bnk.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR bnk.INP_DATE IS NULL AND bnk.UPD_DATE IS NULL) ");
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
	 * Insert
	 * 
	 * @param list
	 * @throws TException
	 */
	public void insert(List<CM_BNKR_TYPE_MST> list) throws TException {
		if (list == null) {
			return;
		}

		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			// DELETE -> INSERT
			delete(conn);

			// 登録
			insert(conn, list);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * Insert
	 * 
	 * @param conn
	 * @param list
	 * @throws TException
	 */
	public void insert(Connection conn, List<CM_BNKR_TYPE_MST> list) throws TException {
		if (list == null) {
			return;
		}

		for (CM_BNKR_TYPE_MST dto : list) {
			VCreator sql = new VCreator();

			sql.add("INSERT INTO CM_BNKR_TYPE_MST(  ");
			sql.add("  KAI_CODE ");
			sql.add(" ,BNKR_TYPE_CODE ");
			sql.add(" ,DISP_ODR ");
			sql.add(" ,ENG_PRI_NOR ");
			sql.add(" ,AUX_PRI_NOR ");
			sql.add(" ,ENG_PRI_ECA ");
			sql.add(" ,AUX_PRI_ECA ");
			sql.add(" ,SC_ENG_PRI_NOR ");
			sql.add(" ,SC_AUX_PRI_NOR ");
			sql.add(" ,SC_ENG_PRI_ECA ");
			sql.add(" ,SC_AUX_PRI_ECA ");
			sql.add(" ,LCL_DEC_KETA ");
			sql.add(" ,INP_DATE ");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");
			sql.add(")  ");
			sql.add("VALUES (  ");
			sql.add("  ? ", dto.getKAI_CODE());
			sql.add(" ,? ", dto.getBNKR_TYPE_CODE());
			sql.add(" ,? ", dto.getDISP_ODR());
			sql.add(" ,? ", dto.getENG_PRI_NOR());
			sql.add(" ,? ", dto.getAUX_PRI_NOR());
			sql.add(" ,? ", dto.getENG_PRI_ECA());
			sql.add(" ,? ", dto.getAUX_PRI_ECA());
			sql.add(" ,? ", dto.getSC_ENG_PRI_NOR());
			sql.add(" ,? ", dto.getSC_AUX_PRI_NOR());
			sql.add(" ,? ", dto.getSC_ENG_PRI_ECA());
			sql.add(" ,? ", dto.getSC_AUX_PRI_ECA());
			sql.add(" ,? ", dto.getLCL_DEC_KETA());
			sql.addYMDHMS(" ,? ", getNow());
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.add(")  ");

			DBUtil.execute(conn, sql);
		}
	}

	/**
	 * Delete ALL BUNKER TYPE
	 * 
	 * @param conn
	 * @throws TException
	 */
	protected void delete(Connection conn) throws TException {
		VCreator sql = new VCreator();

		sql.add("DELETE FROM CM_BNKR_TYPE_MST ");
		sql.add("  WHERE KAI_CODE  = ? ", getCompanyCode());

		DBUtil.execute(conn, sql);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */

	public byte[] getExcel(BunkerTypeSearchCondition condition) throws TException {

		try {

			// 油種情報データを抽出
			List<CM_BNKR_TYPE_MST> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			CodeManager cm = (CodeManager) getComponent(CodeManager.class);

			CodeSearchCondition param = new CodeSearchCondition();
			param.setCODE_DIV(OPCodeDivision.USING_PURPOSE.getValue());
			param.setLocal(false); // 通常のみ
			List<OP_CODE_MST> codeList = cm.get(param);

			BunkerTypeMasterExcel exl = new BunkerTypeMasterExcel(getUser().getLanguage());
			return exl.getExcel(list, codeList);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * マッピング
	 * 
	 * @param rs
	 * @return 油種
	 * @throws Exception
	 */
	protected CM_BNKR_TYPE_MST mapping(ResultSet rs) throws Exception {

		CM_BNKR_TYPE_MST bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // 会社コード
		bean.setBNKR_TYPE_CODE(rs.getString("BNKR_TYPE_CODE")); // BUNKERタイプコード
		bean.setENG_PRI_NOR(rs.getInt("ENG_PRI_NOR"));
		bean.setAUX_PRI_NOR(rs.getInt("AUX_PRI_NOR"));
		bean.setENG_PRI_ECA(rs.getInt("ENG_PRI_ECA"));
		bean.setAUX_PRI_ECA(rs.getInt("AUX_PRI_ECA"));
		bean.setSC_ENG_PRI_NOR(rs.getInt("SC_ENG_PRI_NOR"));
		bean.setSC_AUX_PRI_NOR(rs.getInt("SC_AUX_PRI_NOR"));
		bean.setSC_ENG_PRI_ECA(rs.getInt("SC_ENG_PRI_ECA"));
		bean.setSC_AUX_PRI_ECA(rs.getInt("SC_AUX_PRI_ECA"));
		bean.setLCL_DEC_KETA(rs.getInt("LCL_DEC_KETA"));
		bean.setDISP_ODR(rs.getInt("DISP_ODR")); // ORDER
		bean.setSTR_DATE(rs.getDate("STR_DATE")); // 開始年月日
		bean.setEND_DATE(rs.getDate("END_DATE")); // 終了年月日
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // 登録年月日
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // 更新年月日
		bean.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
		bean.setUSR_ID(rs.getString("USR_ID")); // ユーザーID

		return bean;
	}

	/**
	 * @return 油種
	 */
	protected CM_BNKR_TYPE_MST createEntity() {
		return new CM_BNKR_TYPE_MST();
	}

	/**
	 * SQL creator
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

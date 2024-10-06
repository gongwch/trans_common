package jp.co.ais.trans2.model.cargo;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * Cargo共通マネージャ実現
 */
public class CargoManagerImpl extends TModel implements CargoManager {

	/** Liner使用 */
	protected boolean isUseBl = ServerConfig.isFlagOn("trans.use.bl");

	/**
	 * Cargoリストの取得
	 * 
	 * @param condition
	 * @return Cargoリスト
	 * @throws TException
	 */
	public List<Cargo> get(CargoSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Cargo> list = new ArrayList<Cargo>();
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT crg.*  ");
			sql.add("       ,codeMst.CODE_NAME AS CODE_NAME ");
			sql.add("       ,codeMst_N.CODE_NAME AS CODE_NAME_N ");
			sql.add("       ,opMst.CODE_NAME   AS CODE_NAME_GRP ");
			sql.add("       ,catMst.CODE_NAME  AS CATEGORY_NAME ");
			if (isUseBl) {
				sql.add("       ,itm.ITEM_NAME        AS MLIT_ITEM_NAME ");
				sql.add("       ,subItm.ITEM_SUB_NAME AS MLIT_SUB_ITEM_NAME");
			} else {
				sql.add("       ,NULL                 AS MLIT_ITEM_NAME ");
				sql.add("       ,NULL                 AS MLIT_SUB_ITEM_NAME");
			}
			sql.add(" FROM   OP_CRG_MST crg ");
			sql.add(" LEFT JOIN OP_CODE_MST codeMst ON codeMst.KAI_CODE = ? ", getCompanyCode());
			sql.add("                       AND        codeMst.CODE = crg.CRG_UNIT_CODE");
			sql.add("                       AND        codeMst.LCL_KBN = 0");
			sql.add("                       AND        codeMst.CODE_DIV = ? ", OPCodeDivision.QTY_UNIT.toString());
			// 内航用
			sql.add(" LEFT JOIN OP_CODE_MST codeMst_N ON codeMst_N.KAI_CODE = ? ", getCompanyCode());
			sql.add("                       AND        codeMst_N.CODE = crg.CRG_UNIT_CODE_N");
			sql.add("                       AND        codeMst_N.LCL_KBN = 1");
			sql.add("                       AND        codeMst_N.CODE_DIV = ? ", OPCodeDivision.QTY_UNIT.toString());
			
			sql.add(" LEFT JOIN OP_CODE_MST opMst ON opMst.KAI_CODE = ? ", getCompanyCode());
			sql.add("                       AND      opMst.CODE = crg.CRG_GRP_CODE");
			sql.add("                       AND      opMst.CODE_DIV = ? ", OPCodeDivision.CRG_GRP.toString());
			sql.add("                       AND      opMst.LCL_KBN = 0 ");

			sql.add(" LEFT JOIN OP_CODE_MST catMst ON catMst.KAI_CODE = ? ", getCompanyCode());
			sql.add("                       AND      catMst.CODE = crg.CATEGORY");
			sql.add("                       AND      catMst.CODE_DIV = ? ", OPCodeDivision.BL_CRG_TYPE.toString());
			sql.add("                       AND      catMst.LCL_KBN = 0 ");
			
			if (isUseBl) {
				sql.add(" LEFT JOIN YJ_ITEM_MST itm ON itm.KAI_CODE  = ? ", getCompanyCode());
				sql.add("                       AND    itm.ITEM_CODE = crg.MLIT_ITEM");
				sql.add(" LEFT JOIN YJ_ITEM_SUB_MST subItm ON subItm.KAI_CODE  = ? ", getCompanyCode());
				sql.add("                       AND           subItm.ITEM_CODE     = crg.MLIT_ITEM");
				sql.add("                       AND           subItm.ITEM_SUB_CODE = crg.MLIT_SUB_ITEM");
			}

			sql.add(" WHERE 1 = 1 ");

			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND  crg.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード
			} else {
				sql.add(" AND  crg.KAI_CODE = ? ", getCompanyCode()); // 会社コード
			}

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND    crg.CRG_CODE = ? ", condition.getCode()); // Cargoコード
			}

			if (!Util.isNullOrEmpty(condition.getCodeLike())) { // 貨物コードあいまい
				sql.addLikeAmbi("  AND crg.CRG_CODE ?  ", condition.getCodeLike());
			}

			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.addLikeAmbi("  AND crg.CRG_NAME ?  ", condition.getNameLike());
			}

			if (!Util.isNullOrEmpty(condition.getCategory())) {
				sql.addLikeAmbi("  AND crg.CATEGORY ?  ", condition.getCategory());
			}

			if (!Util.isNullOrEmpty(condition.getGroupNameLike())) {
				sql.add("         AND crg.CRG_GRP_CODE IN  ");
				sql.add("         (");
				sql.add(" SELECT crg_cd.CODE  ");
				sql.add(" FROM OP_CODE_MST crg_cd ");
				sql.add(" WHERE ");
				sql.add(" crg_cd.KAI_CODE = ? ", condition.getCompanyCode());
				sql.addLikeAmbi(" AND crg_cd.CODE_NAME ? ", condition.getGroupNameLike());
				sql.add("         )");
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (crg.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR crg.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add(" ORDER BY crg.KAI_CODE ");
			sql.add("         ,crg.CRG_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs, true));
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
	public boolean hasDelete(CargoSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   OP_CRG_MST crg ");
			sql.add(" WHERE  crg.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (crg.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR crg.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR crg.INP_DATE IS NULL AND crg.UPD_DATE IS NULL) ");
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
	 * New, Copy and modify
	 * 
	 * @param bean
	 * @param oldBean
	 * @return Cargoリスト
	 * @throws TException
	 */
	@Override
	public Cargo entry(Cargo bean, Cargo oldBean) throws TException {
		// NEW or COPY
		if (oldBean == null) {
			bean.setINP_DATE(getNow());
			bean.setUPD_DATE(null);

			// TODO: Handle addition business for CRG_CODE
			if (Util.isNullOrEmpty(bean.getCRG_CODE())) {
				String prefix = "CRG_UID";
				String id = BizUtil.getAutoId(getCompanyCode(), getUserCode(), getProgramCode(), Util.getCurrentDate(),
					prefix, 3);
				bean.setCRG_CODE(id);
			}

			// MODIFY
		} else {
			bean.setINP_DATE(oldBean.getINP_DATE());
			bean.setUPD_DATE(getNow());
		}
		bean.setPRG_ID(getProgramCode());
		bean.setUSR_ID(getUserCode());

		// Persist cargo master
		if (!isExistCargoMaster(bean.getKAI_CODE(), bean.getCRG_CODE())) {
			insert(bean);
		} else {
			if (oldBean != null) {
				// Mode MODIFY then have update
				modify(bean);
			} else {
				// Mode NEW/COPY if found exist record then no update.
				return null;
			}
		}

		CargoSearchCondition condition = getCargoSearchCondition();
		condition.setCompanyCode(bean.getKAI_CODE());
		condition.setCode(bean.getCRG_CODE());

		List<Cargo> cargoRet = get(condition);

		return (cargoRet == null || cargoRet.isEmpty()) ? null : cargoRet.get(0);
	}

	/**
	 * Insert data
	 * 
	 * @param bean
	 * @throws TException
	 */
	@Override
	public void insert(Cargo bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql;
			String tableName = "OP_CRG_MST";

			sql = new VCreator();
			sql.add(" INSERT INTO  " + tableName);
			sql.add(" ( ");
			sql.add(" KAI_CODE ");
			sql.add(" , CRG_CODE  ");
			sql.add(" , CRG_GRP_CODE ");
			sql.add(" , CRG_NAME ");
			sql.add(" , CRG_NAME_N ");
			sql.add(" , STOWAGE_FACTOR ");
			sql.add(" , CRG_UNIT_CODE ");
			sql.add(" , CRG_UNIT_CODE_N ");
			sql.add(" , CATEGORY ");
			sql.add(" , MLIT_ITEM ");
			sql.add(" , MLIT_SUB_ITEM ");
			sql.add(" , BG_COLOR ");
			sql.add(" , TEXT_COLOR ");
			sql.add(" , STR_DATE ");
			sql.add(" , END_DATE ");
			sql.add(" , INP_DATE ");
			sql.add(" , UPD_DATE ");
			sql.add(" , PRG_ID ");
			sql.add(" , USR_ID ");
			sql.add(" , MIN_CALC_FLG ");
			sql.add(" ) VALUES (");
			sql.add(" ? ", bean.getKAI_CODE());
			sql.add(" ,? ", bean.getCRG_CODE());
			sql.add(" ,? ", bean.getCRG_GRP_CODE());
			sql.add(" ,? ", bean.getCRG_NAME());
			sql.add(" ,? ", bean.getCRG_NAME_N());

			if (bean.getSTOWAGE_FACTOR() == null) {
				sql.add(" ,NULL ");
			} else {
				sql.add(" ,? ", bean.getSTOWAGE_FACTOR());
			}

			sql.add(" ,? ", bean.getCRG_UNIT_CODE());
			sql.add(" ,? ", bean.getCRG_UNIT_CODE_N());
			sql.add(" ,? ", bean.getCATEGORY());
			sql.add(" ,? ", bean.getMLIT_ITEM_CODE());
			sql.add(" ,? ", bean.getMLIT_SUB_ITEM_CODE());
			sql.add(" ,? ", bean.getBG_COLOR());
			sql.add(" ,? ", bean.getTEXT_COLOR());
			sql.adt(" ,? ", bean.getSTR_DATE());
			sql.adt(" ,? ", bean.getEND_DATE());
			sql.adt(" ,? ", bean.getINP_DATE());
			sql.adt(" ,? ", bean.getUPD_DATE());
			sql.add(" ,? ", bean.getPRG_ID());
			sql.add(" ,? ", bean.getUSR_ID());
			sql.add(" ,? ", bean.getMIN_CALC_FLG());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Modify data
	 * 
	 * @param bean
	 * @throws TException
	 */
	@Override
	public void modify(Cargo bean) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql;
			String tableName = "OP_CRG_MST";

			sql = new VCreator();
			sql.add(" UPDATE  " + tableName);
			sql.add(" SET ");
			sql.add("  CRG_GRP_CODE = ? ", bean.getCRG_GRP_CODE());
			sql.add(" , CRG_NAME    = ? ", bean.getCRG_NAME());
			sql.add(" , CRG_NAME_N    = ? ", bean.getCRG_NAME_N());
			sql.add(" , STOWAGE_FACTOR = ? ", bean.getSTOWAGE_FACTOR());
			sql.add(" , CRG_UNIT_CODE = ? ", bean.getCRG_UNIT_CODE());
			sql.add(" , CRG_UNIT_CODE_N = ? ", bean.getCRG_UNIT_CODE_N());
			sql.add(" , CATEGORY = ? ", bean.getCATEGORY());
			sql.add(" , MLIT_ITEM = ? ", bean.getMLIT_ITEM_CODE());
			sql.add(" , MLIT_SUB_ITEM = ? ", bean.getMLIT_SUB_ITEM_CODE());
			sql.add(" , BG_COLOR = ? ", bean.getBG_COLOR());
			sql.add(" , TEXT_COLOR = ? ", bean.getTEXT_COLOR());
			sql.adt(" , STR_DATE = ? ", bean.getSTR_DATE());
			sql.adt(" , END_DATE = ? ", bean.getEND_DATE());
			sql.adt(" , INP_DATE = ? ", bean.getINP_DATE());
			sql.adt(" , UPD_DATE = ? ", bean.getUPD_DATE());
			sql.add(" , PRG_ID = ? ", bean.getPRG_ID());
			sql.add(" , USR_ID = ? ", bean.getUSR_ID());
			sql.add(" ,MIN_CALC_FLG =? ", bean.getMIN_CALC_FLG());
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND   KAI_CODE   = ?", bean.getKAI_CODE());
			sql.add(" AND   CRG_CODE    = ?", bean.getCRG_CODE());

			DBUtil.execute(conn, sql);

		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Delete cargo master.
	 * 
	 * @param kaiCODE company code
	 * @param crgCd cargo code
	 * @throws TException
	 */
	public void delete(String kaiCODE, String crgCd) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			if (Util.isNullOrEmpty(kaiCODE) || Util.isNullOrEmpty(crgCd)) {
				return;
			}

			VCreator sql = new VCreator();

			sql.add(" DELETE FROM OP_CRG_MST ");
			sql.add(" WHERE  KAI_CODE = ? ", kaiCODE);
			sql.add(" AND    CRG_CODE = ? ", crgCd);

			DBUtil.execute(conn, sql);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Check data exist
	 * 
	 * @param kaiCODE
	 * @param crgCd
	 * @return boolean
	 * @throws TException
	 */
	@Override
	public boolean isExistCargoMaster(String kaiCODE, String crgCd) throws TException {
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   OP_CRG_MST ");
			sql.add(" WHERE  KAI_CODE = ? ", kaiCODE);
			sql.add(" AND    CRG_CODE = ? ", crgCd);

			int count = DBUtil.selectOneInt(conn, sql.toSQL());
			if (count != 0) {
				return true;
			}

			return false;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Get list data excel
	 * 
	 * @param condition
	 * @return boolean
	 * @throws TException
	 */
	@Override
	public byte[] getExcel(CargoSearchCondition condition) throws TException {
		List<Cargo> cargMstLst = get(condition);
		if (cargMstLst == null || cargMstLst.isEmpty()) {
			return null;
		}

		CargoExcel layout = new CargoExcel(getUser().getLanguage());
		byte[] data = layout.getExcel(cargMstLst);

		return data;
	}

	/**
	 * Get list data
	 * 
	 * @param kaiCODE
	 * @param crgCd
	 * @return Cargo
	 * @throws TException
	 */
	@Override
	public Cargo getByCode(String kaiCODE, String crgCd) throws TException {

		CargoSearchCondition con = new CargoSearchCondition();
		con.setCompanyCode(kaiCODE);
		con.setCode(crgCd);
		List<Cargo> list = get(con);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * マッピング
	 * 
	 * @param rs
	 * @param flag
	 * @return Cargo
	 * @throws Exception
	 */
	protected Cargo mapping(ResultSet rs, boolean flag) throws Exception {

		Cargo bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE")); // 会社コード
		bean.setCRG_CODE(rs.getString("CRG_CODE")); // CARGOコード
		bean.setCRG_GRP_CODE(rs.getString("CRG_GRP_CODE")); // CARGOグループコード
		bean.setCRG_NAME(rs.getString("CRG_NAME")); // CARGO名(内航)
		bean.setCRG_NAME_N(rs.getString("CRG_NAME_N")); // CARGO名
		bean.setSTOWAGE_FACTOR(rs.getBigDecimal("STOWAGE_FACTOR")); // サイズ係数
		bean.setCRG_UNIT_CODE(rs.getString("CRG_UNIT_CODE")); // 貨物単位(内航)
		bean.setCRG_UNIT_CODE_N(rs.getString("CRG_UNIT_CODE_N")); // 貨物単位
		bean.setCATEGORY(rs.getString("CATEGORY")); // CATEGORY
		bean.setCATEGORY_NAME(rs.getString("CATEGORY_NAME")); // CATEGORY
		bean.setMLIT_ITEM_CODE(rs.getString("MLIT_ITEM")); // MLIT ITEM
		bean.setMLIT_ITEM_NAME(rs.getString("MLIT_ITEM_NAME")); // MLIT ITEM
		bean.setMLIT_SUB_ITEM_CODE(rs.getString("MLIT_SUB_ITEM")); // MLIT SUB ITEM
		bean.setMLIT_SUB_ITEM_NAME(rs.getString("MLIT_SUB_ITEM_NAME")); // MLIT SUB ITEM
		bean.setBG_COLOR(rs.getString("BG_COLOR")); // 背景色
		bean.setTEXT_COLOR(rs.getString("TEXT_COLOR")); // 文字色
		bean.setSTR_DATE(rs.getTimestamp("STR_DATE")); // 開始年月日
		bean.setEND_DATE(rs.getTimestamp("END_DATE")); // 終了年月日
		bean.setINP_DATE(rs.getTimestamp("INP_DATE")); // 登録年月日
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE")); // 更新年月日
		bean.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
		bean.setUSR_ID(rs.getString("USR_ID")); // ユーザーID
		bean.setMIN_CALC_FLG(rs.getInt("MIN_CALC_FLG")); // Minimum計算
		if (flag) {
			bean.setCODE_NAME(rs.getString("CODE_NAME"));
			bean.setCODE_NAME_GRP(rs.getString("CODE_NAME_GRP"));
		}
		return bean;
	}

	/**
	 * @return Cargo
	 */
	protected Cargo createEntity() {
		return new Cargo();
	}

	/**
	 * Gets the {@link CargoSearchCondition}.
	 * 
	 * @return the cargo search condition
	 */
	protected CargoSearchCondition getCargoSearchCondition() {
		return new CargoSearchCondition();
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

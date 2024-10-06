package jp.co.ais.trans2.model.port;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * PortマスタDao実装
 * 
 * @author AIS
 */
public class PortDaoImpl extends TModel implements PortDao {

	/** BLユーザー使用可 */
	public static boolean isUseBL = ServerConfig.isFlagOn("trans.use.bl");

	/** T/S港 使用可 */
	public static boolean isTsPort = ServerConfig.isFlagOn("trans.use.ts.port");

	/** 概算港費項目利用フラグ */
	public static boolean isUseEstPortCharge = ServerConfig.isFlagOn("trans.op.use.port.est.amt");

	/** 概算代理店設定利用フラグ */
	public static boolean isUseEstAgent = ServerConfig.isFlagOn("trans.op.use.port.std.agent");

	/**
	 * 指定条件に該当するPort情報を返す。<br/>
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するPort情報
	 * @throws TException
	 */
	public List<Port> getByCondition(PortSearchCondition condition) throws TException {
		Connection conn = DBUtil.getConnection();

		List<Port> list = new ArrayList<Port>();
		try {

			// 件数をチェックする
			VCreator chk = new VCreator();
			chk.add("SELECT COUNT(1) ");
			chk.add("FROM CM_PORT_MST port");
			chk.add("LEFT JOIN COUNTRY_MST cou ON port.COU_CODE = cou.COU_CODE ");
			chk.add("WHERE 1 = 1");

			// WHERE条件追加
			addWhere(condition, chk);

			int count = DBUtil.selectOneInt(conn, chk.toSQL());
			if (count == 0) {
				return list;
			}

			VCreator sql = new VCreator();

			sql.add("SELECT ");
			sql.add("  port.KAI_CODE ");
			sql.add(" ,port.PORT_CODE ");
			sql.add(" ,port.PORT_NAME ");
			sql.add(" ,port.PORT_NAME_N ");
			sql.add(" ,port.PORT_NAME_S ");
			sql.add(" ,port.PORT_NAME_K ");
			sql.add(" ,port.UNLOCODE ");
			sql.add(" ,port.REGION_CODE ");
			sql.add(" ,code.CODE_NAME AS REGION_NAME ");
			sql.add(" ,port.MLIT_REGION_CODE ");
			sql.add(" ,port.MLIT_COUNTRY_CODE ");
			sql.add(" ,yjReg.REGION_NAME  AS MLIT_REGION_NAME  ");
			sql.add(" ,yjCou.COUNTRY_NAME AS MLIT_COUNTRY_NAME ");

			if (isUseBL) {
				sql.add(" ,port.IPP_FLG ");
				sql.add(" ,port.LINER_REMARKS ");
			}

			sql.add(" ,port.COU_CODE ");
			sql.add(" ,port.GMT_TIMEZONE ");
			sql.add(" ,port.S_ECA_FLG ");
			sql.add(" ,port.LAT ");
			sql.add(" ,port.LON ");
			sql.add(" ,port.INP_DATE ");
			sql.add(" ,port.UPD_DATE ");
			sql.add(" ,port.PRG_ID ");
			sql.add(" ,port.USR_ID ");
			sql.add(" ,port.STR_DATE ");
			sql.add(" ,port.END_DATE ");
			sql.add(" ,cou.COU_NAME ");
			sql.add(" ,port.REMARKS ");
			if (isUseEstPortCharge) {
				if (isUseEstAgent) {
					sql.add(" ,port.STD_PORT_AGENT_CODE ");
					sql.add(" ,ag.TRI_NAME AS STD_PORT_AGENT_NAME ");
				}
				sql.add(" ,port.PCG_EST_CUR_CODE ");
				sql.add(" ,port.PCG_EST_AMT ");
			}

			if (isTsPort) {
				sql.add(" ,port.TS_PORT_CODE ");
				sql.add(" ,ts.PORT_NAME_S TS_PORT_NAME ");
			}

			sql.add("FROM CM_PORT_MST port");
			sql.add("LEFT JOIN COUNTRY_MST cou ON port.COU_CODE = cou.COU_CODE ");
			sql.add("LEFT JOIN OP_CODE_MST code ON code.KAI_CODE = port.KAI_CODE ");
			sql.add("                          AND code.CODE = port.REGION_CODE ");
			sql.add("                          AND code.CODE_DIV = ? ", OPCodeDivision.SMR_TIME_REGION.getValue());
			sql.add("                          AND code.LCL_KBN = 0");
			sql.add("LEFT JOIN YJ_REGION_MST yjReg ON port.KAI_CODE = yjReg.KAI_CODE ");
			sql.add("                             AND port.MLIT_REGION_CODE = yjReg.REGION_CODE ");
			sql.add("LEFT JOIN YJ_COUNTRY_MST yjCou ON port.KAI_CODE = yjCou.KAI_CODE ");
			sql.add("                             AND  port.MLIT_COUNTRY_CODE = yjCou.COUNTRY_CODE ");
			sql.add("                             AND  yjReg.REGION_CODE = yjCou.REGION_CODE ");
			if (isTsPort) {
				sql.add("LEFT JOIN CM_PORT_MST ts ON port.KAI_CODE = ts.KAI_CODE ");
				sql.add("                        AND port.TS_PORT_CODE = ts.PORT_CODE ");
			}
			if (isUseEstPortCharge && isUseEstAgent) {
				sql.add("LEFT JOIN TRI_MST ag ON ag.KAI_CODE = port.KAI_CODE ");
				sql.add("                    AND ag.TRI_CODE = port.STD_PORT_AGENT_CODE ");
			}

			sql.add("WHERE 1 = 1 ");

			// WHERE条件追加
			addWhere(condition, sql);

			sql.add("ORDER BY ");
			sql.add("  port.KAI_CODE ");
			sql.add(" ,port.PORT_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			if (isUseBL && !list.isEmpty()) {

				List<String> codeList = new ArrayList<String>();

				for (Port bean : list) {
					codeList.add(bean.getCode());
				}

				PortSearchCondition param = new PortSearchCondition();
				param.setCompanyCode(condition.getCompanyCode());
				param.setCodeList(codeList);

				List<PortLinerCharge> linerChargeLst = getLinerChargeList(conn, param);

				// 詳細情報をつける
				setupDetailList(list, linerChargeLst);

			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;
	}

	/**
	 * 明細振り分け
	 * 
	 * @param list
	 * @param chargeList
	 */

	protected void setupDetailList(List<Port> list, List<PortLinerCharge> chargeList) {

		Map<String, List<PortLinerCharge>> portLinerMap = new HashMap<String, List<PortLinerCharge>>();
		if (chargeList != null) {
			for (PortLinerCharge bean : chargeList) {
				String key = bean.getCompanyCode() + "<>" + bean.getCode();
				List<PortLinerCharge> subList = portLinerMap.get(key);
				if (subList == null) {
					subList = new ArrayList<PortLinerCharge>();
					portLinerMap.put(key, subList);
				}
				subList.add(bean);
			}
		}
		for (Port bean : list) {
			String key = bean.getCompanyCode() + "<>" + bean.getCode();
			bean.setChargeList(portLinerMap.get(key));
		}
	}

	/**
	 * WHERE条件追加
	 * 
	 * @param condition
	 * @param sql
	 */
	protected void addWhere(PortSearchCondition condition, VCreator sql) {
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("  AND port.KAI_CODE = ? ", condition.getCompanyCode());
		}
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("  AND port.PORT_CODE = ? ", condition.getCode());
		}
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("  AND port.PORT_CODE >= ?", condition.getCodeFrom());
		}
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("  AND port.PORT_CODE <= ?", condition.getCodeTo());
		}
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			sql.add("  AND port.PORT_CODE IN ? ", condition.getCodeList());
		}
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("  AND port.PORT_CODE ? ", condition.getCodeLike());
		}
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql.addNLikeAmbi("  AND port.PORT_NAME_S ? ", condition.getNamesLike());
		}
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql.addNLikeAmbi("  AND port.PORT_NAME_K ? ", condition.getNamekLike());
		}
		// Add condition for MO0050PortMaster
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("  AND port.PORT_NAME ? ", condition.getNameLike());
		}
		if (!Util.isNullOrEmpty(condition.getUNLOCODE())) {
			sql.addNLikeAmbi("  AND port.UNLOCODE ? ", condition.getUNLOCODE());
		}
		if (!Util.isNullOrEmpty(condition.getCOU_CODE())) {
			sql.add("  AND port.COU_CODE = ? ", condition.getCOU_CODE());
		}
		if (!Util.isNullOrEmpty(condition.getCouName())) {
			sql.add("  AND cou.COU_NAME = ? ", condition.getCouName());
		}

		if (condition.getS_ECA_FLG() != -1) {
			sql.add("  AND port.S_ECA_FLG = ? ", condition.getS_ECA_FLG());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add(" AND    port.STR_DATE <= ? ", condition.getValidTerm());
			sql.add(" AND    port.END_DATE >= ? ", condition.getValidTerm());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (port.INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR port.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

	}

	/**
	 * Gets the hold consumption list.
	 * 
	 * @param conn db conneciton
	 * @param condition the condition
	 * @return the list
	 * @throws Exception
	 */
	public List<PortLinerCharge> getLinerChargeList(Connection conn, PortSearchCondition condition) throws Exception {

		String tableName = "CM_PORT_LINER_CHARGE_MST";

		VCreator where = new VCreator();
		where.add(" WHERE t.KAI_CODE = ? ", condition.getCompanyCode());
		// 船指定
		if (!Util.isNullOrEmpty(condition.getCode())) {
			where.add(" AND   t.PORT_CODE = ? ", condition.getCode());
		}
		// 船リスト指定
		if (condition.getCodeList() != null && !condition.getCodeList().isEmpty()) {
			where.add(" AND ( 1 = 2 ");
			for (String code : condition.getCodeList()) {
				where.add(" OR t.PORT_CODE = ? ", code);
			}
			where.add(" ) ");
		}

		VCreator sql = new VCreator();
		sql.add(" SELECT  t.* ");
		sql.add("      ,cur.DEC_KETA   DEC_KETA ");
		sql.add("      ,cat.CODE_NAME  CATEGORY_NAME ");
		sql.add("      ,crg.CRG_NAME   CARGO_TYPE ");
		sql.add(" FROM    " + tableName + " t ");
		sql.add("LEFT JOIN CUR_MST cur ON cur.CUR_CODE = t.CURRENCY ");
		sql.add("                     AND    cur.KAI_CODE = ?", condition.getCompanyCode());
		sql.add("LEFT JOIN OP_CODE_MST cat ON  cat.CODE = t.CATEGORY ");
		sql.add("                          AND    cat.KAI_CODE = ?", condition.getCompanyCode());
		sql.add("                          AND    cat.CODE_DIV = ?", OPCodeDivision.BL_CRG_TYPE.toString());
		sql.add("                          AND    cat.LCL_KBN = 0");
		sql.add("LEFT JOIN OP_CRG_MST crg ON crg.CRG_CODE = t.CARGO_CODE ");
		sql.add("                         AND    crg.KAI_CODE = ?", condition.getCompanyCode());
		sql.add(where);
		sql.add(" ORDER BY ");
		sql.add("    t.KAI_CODE ");
		sql.add("   ,t.PORT_CODE ");
		sql.add("   ,t.CHRG_NO ");

		Statement statement = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(statement, sql);

		List<PortLinerCharge> list = new ArrayList<PortLinerCharge>();

		while (rs.next()) {
			PortLinerCharge bean = mappingPortLinerCharge(rs);
			list.add(bean);
		}

		DBUtil.close(rs);
		DBUtil.close(statement);

		return list;
	}

	/**
	 * 指定コードに該当するPort情報を返す。
	 * 
	 * @param companyCode 会社コード
	 * @param portCode Portコード
	 * @return 指定コードに該当するPort情報
	 * @throws TException
	 */
	public Port get(String companyCode, String portCode) throws TException {

		PortSearchCondition condition = new PortSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode(portCode);

		List<Port> list = getByCondition(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(PortSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   CM_PORT_MST port ");
			sql.add(" WHERE  port.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (port.INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR port.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR port.INP_DATE IS NULL AND port.UPD_DATE IS NULL) ");
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
	 * Portを登録する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void insert(Port dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql = new VCreator();
			sql.add("INSERT  ");
			sql.add("INTO CM_PORT_MST(  ");
			sql.add("  KAI_CODE ");
			sql.add(" ,PORT_CODE ");
			sql.add(" ,PORT_NAME ");
			sql.add(" ,PORT_NAME_N ");
			sql.add(" ,PORT_NAME_S ");
			sql.add(" ,PORT_NAME_K ");
			sql.add(" ,MLIT_REGION_CODE ");
			sql.add(" ,MLIT_COUNTRY_CODE ");

			if (isUseBL) {
				sql.add(" ,IPP_FLG ");
				sql.add(" ,LINER_REMARKS ");
			}
			sql.add(" ,UNLOCODE ");
			sql.add(" ,REGION_CODE ");
			sql.add(" ,COU_CODE ");
			sql.add(" ,GMT_TIMEZONE ");
			sql.add(" ,S_ECA_FLG ");
			sql.add(" ,LAT ");
			sql.add(" ,LON ");
			sql.add(" ,INP_DATE ");
			sql.add(" ,PRG_ID ");
			sql.add(" ,USR_ID ");
			sql.add(" ,STR_DATE ");
			sql.add(" ,END_DATE ");
			sql.add(" ,REMARKS ");
			if (isTsPort) {
				sql.add(" ,TS_PORT_CODE ");
			}
			if (isUseEstPortCharge) {
				if (isUseEstAgent) {
					sql.add(" ,STD_PORT_AGENT_CODE");
				}
				sql.add(" ,PCG_EST_CUR_CODE");
				sql.add(" ,PCG_EST_AMT ");
			}
			sql.add(")  ");
			sql.add("VALUES (  ");
			sql.add("  ? ", dto.getCompanyCode());
			sql.add(" ,? ", dto.getCode());
			sql.add(" ,? ", dto.getName());
			sql.add(" ,? ", dto.getName_N());
			sql.add(" ,? ", dto.getNames());
			sql.add(" ,? ", dto.getNamek());
			sql.add("  ,? ", dto.getMLIT_REGION_CODE());
			sql.add("  ,? ", dto.getMLIT_COUNTRY_CODE());

			if (isUseBL) {
				sql.add("  ,? ", dto.getIPP_FLG());
				sql.add("  ,? ", dto.getLINER_REMARKS());
			}
			sql.add(" ,? ", dto.getUNLOCODE());
			sql.add(" ,? ", dto.getREGION_CODE());
			sql.add(" ,? ", dto.getCOU_CODE());
			sql.add(" ,? ", dto.getGMT_TIMEZONE());
			sql.add(" ,? ", dto.getS_ECA_FLG());
			sql.add(" ,? ", dto.getLAT());
			sql.add(" ,? ", dto.getLNG());
			sql.adt(" ,? ", getNow());
			sql.add(" ,? ", getProgramCode());
			sql.add(" ,? ", getUserCode());
			sql.adt(" ,? ", dto.getDateFrom());
			sql.adt(" ,? ", dto.getDateTo());
			sql.add(" ,? ", dto.getREMARKS());
			if (isTsPort) {
				sql.add(" ,? ", dto.getTS_PORT_CODE());
			}
			if (isUseEstPortCharge) {
				if (isUseEstAgent) {
					sql.add(" ,? ", dto.getSTD_PORT_AGENT_CODE());
				}
				sql.add(" ,? ", dto.getPCG_EST_CUR_CODE());
				sql.add(" ,? ", dto.getPCG_EST_AMT());
			}

			sql.add(")  ");

			DBUtil.execute(conn, sql);

			if (isUseBL) {
				deleteLinerCharge(conn, dto);
				if (dto.getChargeList() != null) {
					for (PortLinerCharge charge : dto.getChargeList()) {
						insertLinerCharge(conn, charge);
					}
				}
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Portを修正する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void update(Port dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql = new VCreator();
			sql.add("UPDATE CM_PORT_MST  ");
			sql.add("SET ");
			sql.add("     KAI_CODE = ? ", dto.getCompanyCode());
			sql.add("    ,PORT_CODE = ? ", dto.getCode());
			sql.add("    ,PORT_NAME = ? ", dto.getName());
			sql.add("    ,PORT_NAME_N = ? ", dto.getName_N());
			sql.add("    ,PORT_NAME_S = ? ", dto.getNames());
			sql.add("    ,PORT_NAME_K = ? ", dto.getNamek());
			sql.add("    ,MLIT_REGION_CODE = ? ", dto.getMLIT_REGION_CODE());
			sql.add("    ,MLIT_COUNTRY_CODE = ? ", dto.getMLIT_COUNTRY_CODE());

			if (isUseBL) {
				sql.add("    ,IPP_FLG = ? ", dto.getIPP_FLG());
				sql.add("    ,LINER_REMARKS = ? ", dto.getLINER_REMARKS());
			}

			sql.add("    ,UNLOCODE = ? ", dto.getUNLOCODE());
			sql.add("    ,REGION_CODE = ? ", dto.getREGION_CODE());
			sql.add("    ,COU_CODE = ? ", dto.getCOU_CODE());
			sql.add("    ,GMT_TIMEZONE = ? ", dto.getGMT_TIMEZONE());
			sql.add("    ,S_ECA_FLG = ? ", dto.getS_ECA_FLG());
			sql.add("    ,LAT = ? ", dto.getLAT());
			sql.add("    ,LON = ? ", dto.getLNG());
			sql.adt("    ,UPD_DATE = ? ", getNow());
			sql.add("    ,PRG_ID = ? ", getProgramCode());
			sql.add("    ,USR_ID = ? ", getUserCode());
			sql.adt("    ,STR_DATE = ? ", dto.getDateFrom());
			sql.adt("    ,END_DATE = ? ", dto.getDateTo());
			sql.add("    ,REMARKS = ? ", dto.getREMARKS());
			if (isTsPort) {
				sql.add("    ,TS_PORT_CODE = ? ", dto.getTS_PORT_CODE());
			}
			if (isUseEstPortCharge) {
				if (isUseEstAgent) {
					sql.add("    ,STD_PORT_AGENT_CODE = ? ", dto.getSTD_PORT_AGENT_CODE());
				}
				sql.add("    ,PCG_EST_CUR_CODE = ? ", dto.getPCG_EST_CUR_CODE());
				sql.add("    ,PCG_EST_AMT = ? ", dto.getPCG_EST_AMT());
			}

			sql.add("WHERE 1 = 1 ");
			sql.add("AND KAI_CODE = ? ", dto.getCompanyCode());
			sql.add("AND PORT_CODE = ? ", dto.getCode());

			DBUtil.execute(conn, sql);

			if (isUseBL) {
				deleteLinerCharge(conn, dto);
				for (PortLinerCharge charge : dto.getChargeList()) {
					updateLinerCharge(conn, charge);
				}
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Port Liner Chargeの削除
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void deleteLinerCharge(Connection conn, Port bean) throws TException {
		String tableName = "CM_PORT_LINER_CHARGE_MST";

		VCreator sql = new VCreator();
		sql.add(" DELETE FROM " + tableName);
		sql.add(" WHERE  1 = 1 ");
		sql.add(" AND    KAI_CODE = ? ", bean.getCompanyCode());
		sql.add(" AND    PORT_CODE = ? ", bean.getCode());

		DBUtil.execute(conn, sql);
	}

	/**
	 * VESSELスピードマスタの登録
	 * 
	 * @param bean
	 * @param conn
	 * @throws TException
	 */
	public void insertLinerCharge(Connection conn, PortLinerCharge bean) throws TException {
		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			String tableName = "CM_PORT_LINER_CHARGE_MST";
			sql = new VCreator();

			sql.add(" INSERT INTO " + tableName + " ( ");
			sql.add("    KAI_CODE ");
			sql.add("   ,PORT_CODE ");
			sql.add("   ,CHRG_NO ");
			sql.add("   ,LD_TYPE ");
			sql.add("   ,CHARGE_NAME ");
			sql.add("   ,CATEGORY ");
			sql.add("   ,CARGO_CODE ");
			sql.add("   ,CURRENCY ");
			sql.add("   ,AMOUNT ");
			sql.add("   ,PER ");
			sql.add("   ,MIN_AMT ");
			sql.add("   ,HEAVY_LIFT ");
			sql.add("   ,INP_DATE ");
			sql.add("   ,UPD_DATE ");
			sql.add("   ,PRG_ID ");
			sql.add("   ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("    ? ", bean.getCompanyCode());
			sql.add("   ,? ", bean.getCode());
			sql.add("   ,? ", bean.getCHRG_NO());
			sql.add("   ,? ", bean.getLD_TYPE());
			sql.add("   ,? ", bean.getCHARGE_NAME());
			sql.add("   ,? ", bean.getCATEGORY());
			sql.add("   ,? ", bean.getCARGO_CODE());
			sql.add("   ,? ", bean.getCURRENCY());
			sql.add("   ,? ", bean.getAMOUNT());
			sql.add("   ,? ", bean.getPER());
			sql.add("   ,? ", bean.getMIN_AMT());
			sql.add("   ,? ", bean.getHEAVY_LIFT());
			sql.adt("   ,? ", getNow());
			sql.adt("   ,? ", bean.getUPD_DATE());
			sql.add("   ,? ", getProgramCode());
			sql.add("   ,? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * Port Liner Chargeの変更
	 * 
	 * @param conn
	 * @param bean
	 * @throws TException
	 */
	public void updateLinerCharge(Connection conn, PortLinerCharge bean) throws TException {
		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql = new VCreator();
			sql.add("UPDATE CM_PORT_LINER_CHARGE_MST  ");
			sql.add("SET ");
			sql.add("	 KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("	,PORT_CODE = ? ", bean.getCode());
			sql.add("	,LD_TYPE = ? ", bean.getLD_TYPE());
			sql.add("	,CHARGE_NAME = ? ", bean.getCHARGE_NAME());
			sql.add("	,CATEGORY = ? ", bean.getCATEGORY());
			sql.add("	,CARGO_CODE = ? ", bean.getCARGO_CODE());
			sql.add("	,CURRENCY = ? ", bean.getCURRENCY());
			sql.add("	,AMOUNT = ? ", bean.getAMOUNT());
			sql.add("	,PER = ? ", bean.getPER());
			sql.add("	,MIN_AMT = ? ", bean.getMIN_AMT());
			sql.add("   ,HEAVY_LIFT = ? ", bean.getHEAVY_LIFT());
			sql.adt("   ,UPD_DATE = ? ", bean.getUPD_DATE());
			sql.add("	,PRG_ID = ? ", getProgramCode());
			sql.add("	,USR_ID = ? ", getUserCode());
			sql.add("WHERE 1 = 1 ");
			sql.add("AND KAI_CODE = ? ", bean.getCompanyCode());
			sql.add("AND PORT_CODE = ? ", bean.getCode());
			sql.add("AND CHRG_NO = ? ", bean.getCHRG_NO());

			int count = DBUtil.execute(conn, sql);

			if (count == 0) {
				sql = new VCreator();

				sql.add("INSERT  ");
				sql.add("INTO CM_PORT_LINER_CHARGE_MST ( ");
				sql.add("    KAI_CODE ");
				sql.add("   ,PORT_CODE ");
				sql.add("   ,CHRG_NO ");
				sql.add("   ,LD_TYPE ");
				sql.add("   ,CHARGE_NAME ");
				sql.add("   ,CATEGORY ");
				sql.add("   ,CARGO_CODE ");
				sql.add("   ,CURRENCY ");
				sql.add("   ,AMOUNT ");
				sql.add("   ,PER ");
				sql.add("   ,MIN_AMT ");
				sql.add("   ,HEAVY_LIFT ");
				sql.add("   ,INP_DATE ");
				sql.add("   ,PRG_ID ");
				sql.add("   ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("    ? ", bean.getCompanyCode());
				sql.add("   ,? ", bean.getCode());
				sql.add("   ,? ", bean.getCHRG_NO());
				sql.add("   ,? ", bean.getLD_TYPE());
				sql.add("   ,? ", bean.getCHARGE_NAME());
				sql.add("   ,? ", bean.getCATEGORY());
				sql.add("   ,? ", bean.getCARGO_CODE());
				sql.add("   ,? ", bean.getCURRENCY());
				sql.add("   ,? ", bean.getAMOUNT());
				sql.add("   ,? ", bean.getPER());
				sql.add("   ,? ", bean.getMIN_AMT());
				sql.add("   ,? ", bean.getHEAVY_LIFT());
				sql.adt("   ,? ", bean.getINP_DATE());
				sql.add("   ,? ", bean.getPRG_ID());
				sql.add("   ,? ", bean.getUSR_ID());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * Portを削除する。
	 * 
	 * @param dto Port
	 * @throws TException
	 */
	public void delete(Port dto) throws TException {
		Connection conn = null;

		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();

			sql.add("DELETE FROM CM_PORT_MST ");
			sql.add("  WHERE KAI_CODE  = ? ", dto.getCompanyCode());
			sql.add("  AND   PORT_CODE = ? ", dto.getCode());

			DBUtil.execute(conn, sql);

			if (isUseBL) {

				// HOLD CONS information erase
				deleteLinerCharge(conn, dto);
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
	 * @return entity
	 * @throws SQLException
	 */
	protected Port mapping(ResultSet rs) throws SQLException {
		Port entity = new Port();
		entity.setCompanyCode(rs.getString("KAI_CODE"));
		entity.setCode(rs.getString("PORT_CODE"));
		entity.setName(rs.getString("PORT_NAME"));
		entity.setName_N(rs.getString("PORT_NAME_N"));
		entity.setNames(rs.getString("PORT_NAME_S"));
		entity.setNamek(rs.getString("PORT_NAME_K"));
		entity.setREGION_CODE(rs.getString("REGION_CODE"));
		entity.setREGION_NAME(rs.getString("REGION_NAME"));
		entity.setMLIT_REGION_CODE(rs.getString("MLIT_REGION_CODE"));
		entity.setMLIT_COUNTRY_CODE(rs.getString("MLIT_COUNTRY_CODE"));
		entity.setMLIT_REGION_NAME(rs.getString("MLIT_REGION_NAME"));
		entity.setMLIT_COUNTRY_NAME(rs.getString("MLIT_COUNTRY_NAME"));

		if (isUseBL) {
			entity.setIPP_FLG(rs.getInt("IPP_FLG"));
			entity.setLINER_REMARKS(rs.getString("LINER_REMARKS"));
		}

		entity.setUNLOCODE(rs.getString("UNLOCODE"));
		entity.setCOU_CODE(rs.getString("COU_CODE"));
		entity.setCOU_NAME(rs.getString("COU_NAME"));
		entity.setGMT_TIMEZONE(rs.getBigDecimal("GMT_TIMEZONE"));
		entity.setS_ECA_FLG(rs.getInt("S_ECA_FLG"));
		entity.setLAT(rs.getBigDecimal("LAT"));
		entity.setLNG(rs.getBigDecimal("LON"));
		entity.setDateFrom(rs.getDate("STR_DATE"));
		entity.setDateTo(rs.getDate("END_DATE"));
		entity.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		entity.setUSR_ID(rs.getString("USR_ID"));
		entity.setREMARKS(rs.getString("REMARKS"));
		if (isTsPort) {
			entity.setTS_PORT_CODE(rs.getString("TS_PORT_CODE"));
			entity.setTS_PORT_NAME(rs.getString("TS_PORT_NAME"));
		}
		if (isUseEstPortCharge) {
			if (isUseEstAgent) {
				entity.setSTD_PORT_AGENT_CODE(rs.getString("STD_PORT_AGENT_CODE"));
				entity.setSTD_PORT_AGENT_NAME(rs.getString("STD_PORT_AGENT_NAME"));
			}
			entity.setPCG_EST_CUR_CODE(rs.getString("PCG_EST_CUR_CODE"));
			entity.setPCG_EST_AMT(rs.getBigDecimal("PCG_EST_AMT"));
		}
		return entity;
	}

	/**
	 * Mapping vessel hold comsumption.
	 * 
	 * @param rs the rs
	 * @return bean
	 * @throws Exception
	 */
	public PortLinerCharge mappingPortLinerCharge(ResultSet rs) throws Exception {
		PortLinerCharge bean = createPortLinerCharge();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("PORT_CODE"));
		bean.setCHRG_NO(rs.getInt("CHRG_NO"));
		bean.setCHARGE_NAME(rs.getString("CHARGE_NAME"));
		bean.setLD_TYPE(rs.getString("LD_TYPE"));
		bean.setCATEGORY(rs.getString("CATEGORY"));
		bean.setCATEGORY_NAME(rs.getString("CATEGORY_NAME"));
		bean.setCARGO_CODE(rs.getString("CARGO_CODE"));
		bean.setCARGO_TYPE(rs.getString("CARGO_TYPE"));
		bean.setCURRENCY(rs.getString("CURRENCY"));
		bean.setDEC_KETA(rs.getInt("DEC_KETA"));

		bean.setAMOUNT(rs.getBigDecimal("AMOUNT"));
		bean.setPER(rs.getString("PER"));
		bean.setMIN_AMT(rs.getBigDecimal("MIN_AMT"));
		bean.setHEAVY_LIFT(rs.getBigDecimal("HEAVY_LIFT"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID")); // プログラムID
		bean.setUSR_ID(rs.getString("USR_ID")); // ユーザーID
		return bean;
	}

	/**
	 * Create new {@link PortLinerCharge}
	 * 
	 * @return {@link PortLinerCharge}
	 */
	protected PortLinerCharge createPortLinerCharge() {
		return new PortLinerCharge();
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

package jp.co.ais.trans2.model.slip;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;

/**
 * SlipDaoの実装
 * 
 * @author AIS
 */
public class SlipDaoImpl extends TModel implements SlipDao {

	/** true:BS勘定消込機能有効 */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:AP勘定消込機能無効 */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR勘定消込機能無効 */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:伝票番号部分一致有効 */
	public static boolean isUseSlipNoAmbiLike = ServerConfig.isFlagOn("trans.slip.use.slip.no.ambi.like");

	/** true:ファイル添付機能有効 */
	public static boolean isUseAttachment = ServerConfig.isFlagOn("trans.slip.use.attachment");

	/** KMK_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKMK_MST = ServerConfig.isFlagOn("trans.slip.dao.KMK_MST.table");

	/** HKM_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseHKM_MST = ServerConfig.isFlagOn("trans.slip.dao.HKM_MST.table");

	/** UKM_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseUKM_MST = ServerConfig.isFlagOn("trans.slip.dao.UKM_MST.table");

	/** BMN_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseBMN_MST = ServerConfig.isFlagOn("trans.slip.dao.BMN_MST.table");

	/** TRI_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseTRI_MST = ServerConfig.isFlagOn("trans.slip.dao.TRI_MST.table");

	/** EMP_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseEMP_MST = ServerConfig.isFlagOn("trans.slip.dao.EMP_MST.table");

	/** DEN_SYU_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseDEN_SYU_MST = ServerConfig.isFlagOn("trans.slip.dao.DEN_SYU_MST.table");

	/** CUR_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseCUR_MST = ServerConfig.isFlagOn("trans.slip.dao.CUR_MST.table");

	/** AP_HOH_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseAP_HOH_MST = ServerConfig.isFlagOn("trans.slip.dao.AP_HOH_MST.table");

	/** AP_CBK_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseAP_CBK_MST = ServerConfig.isFlagOn("trans.slip.dao.AP_CBK_MST.table");

	/** TRI_SJ_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseTRI_SJ_MST = ServerConfig.isFlagOn("trans.slip.dao.TRI_SJ_MST.table");

	/** KNR1_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR1_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR1_MST.table");

	/** KNR2_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR2_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR2_MST.table");

	/** KNR3_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR3_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR3_MST.table");

	/** KNR4_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR4_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR4_MST.table");

	/** KNR5_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR5_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR5_MST.table");

	/** KNR6_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseKNR6_MST = ServerConfig.isFlagOn("trans.slip.dao.KNR6_MST.table");

	/** SZEI_MST結合 true:hdr.KAI_CODE、false:getCompanyCode() */
	public static boolean isUseSZEI_MST = ServerConfig.isFlagOn("trans.slip.dao.SZEI_MST.table");

	/** true:付箋機能有効 */
	public static boolean isUseTag = ServerConfig.isFlagOn("trans.slip.use.tag");

	/** true: 2023INVOICE制度対応を使用する */
	public static boolean isUseInvoice = ServerConfig.isFlagOn("trans.slip.use.invoice.tax");

	/**
	 * ヘッダーテーブル定数
	 */
	protected static final class HDR_TABLE {

		/** GL */
		public static final String GL = "GL";

		/** AP */
		public static final String AP = "AP";

		/** AR */
		public static final String AR = "AR";
	}

	/** 検索対象 */
	protected enum SearchTable {
		/** 伝票 */
		Slip,

		/** パターン */
		Pattern,

		/** 履歴 */
		History
	}

	/** 伝票検索条件 */
	protected SlipCondition param_ = null;

	/** 検索対象テーブル */
	protected SearchTable searchTable_ = null;

	/** 明細抽出するかどうか */
	protected boolean isDetail_ = false;

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @param searchTable 検索対象
	 * @return 結果リスト
	 */
	public List<SWK_HDR> findByCondition(SlipCondition param, SearchTable searchTable) {

		Connection conn = null;

		List<SWK_HDR> list = new ArrayList<SWK_HDR>();

		try {

			conn = DBUtil.getConnection();

			String sql = getDefaultSqlIncludeDetails(conn, param, searchTable, false);
			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingHeader(rs));
			}

			DBUtil.close(rs);
			if (param.isSearchWorkFlow()) {
				sql = getWorkflowApproveSQL(conn, param);
				rs = DBUtil.select(statement, sql);
				// 承認権限保持分のみを取得
				List<SWK_SYO_CTL> ctlList = new ArrayList();
				while (rs.next()) {
					SWK_SYO_CTL ctl = new SWK_SYO_CTL();
					ctl.setKAI_CODE(rs.getString("KAI_CODE"));
					ctl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
					ctl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
					ctl.setSEQ(rs.getInt("SEQ"));
					ctl.setUPD_KBN(rs.getInt("UPD_KBN"));
					ctl.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE"));
					ctl.setPREV_UPD_KBN(rs.getInt("PREV_UPD_KBN"));
					ctl.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
					ctl.setNEXT_APRV_ROLE_CODE(rs.getString("NEXT_APRV_ROLE_CODE"));
					ctl.setINP_DATE(rs.getDate("INP_DATE"));
					ctl.setPRG_ID(rs.getString("PRG_ID"));
					ctl.setUSR_ID(rs.getString("USR_ID"));

					ctl.setAprvRoleGroupName(rs.getString("APRV_ROLE_GRP_NAME"));
					ctl.setAprvRoleName(rs.getString("APRV_ROLE_NAME"));
					ctl.setNextAprvRoleName(rs.getString("NEXT_APRV_ROLE_NAME"));

					ctl.setAprvRoleSkippable(rs.getInt("APRV_SKIP_FLG") == 1);
					ctl.setNextAprvRoleSkippable(rs.getInt("NEXT_APRV_SKIP_FLG") == 1);

					ctlList.add(ctl);

				}
				DBUtil.close(rs);
				HDR: for (SWK_HDR hdr : list) {
					for (SWK_SYO_CTL ctl : ctlList) {
						if (Util.equals(ctl.getKAI_CODE(), hdr.getKAI_CODE())
							&& Util.equals(ctl.getSWK_DEN_NO(), hdr.getSWK_DEN_NO())
							&& DateUtil.equals(ctl.getSWK_DEN_DATE(), hdr.getSWK_DEN_DATE())) {
							hdr.setSyoCtl(ctl);
							// 追加して次のヘッダに
							continue HDR;
						}
					}
				}
			}
			DBUtil.close(statement);
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ワークフロー承認利用時
				setupApproveGroup(list);
			}

		} catch (SlipNoDataException e) {
			return list;
		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * @param list
	 * @throws TException
	 */
	protected void setupApproveGroup(List<SWK_HDR> list) throws TException {
		AprvRoleGroupManager mana = get(AprvRoleGroupManager.class);
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		List<AprvRoleGroup> grpList = mana.get(con);
		if (grpList == null || grpList.isEmpty()) {
			// マスタ非存在時終了
			return;
		}
		HDR: for (SWK_HDR h : list) {
			if (Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
				continue;
			}
			for (AprvRoleGroup g : grpList) {
				if (Util.equals(g.getKAI_CODE(), h.getKAI_CODE()) && //
					Util.equals(g.getAPRV_ROLE_GRP_CODE(), h.getSWK_APRV_GRP_CODE())) {
					h.setAprRoleGroup(g);
					continue HDR;
				}
			}
		}

	}

	/**
	 * @param list
	 * @throws TException
	 */
	protected void setupSlipApproveGroup(List<Slip> list) throws TException {
		AprvRoleGroupManager mana = get(AprvRoleGroupManager.class);
		AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
		List<AprvRoleGroup> grpList = mana.get(con);
		if (grpList == null || grpList.isEmpty()) {
			// マスタ非存在時終了
			return;
		}
		HDR: for (Slip s : list) {
			SWK_HDR h = s.getHeader();
			if (Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
				continue;
			}
			for (AprvRoleGroup g : grpList) {
				if (Util.equals(g.getKAI_CODE(), h.getKAI_CODE()) && //
					Util.equals(g.getAPRV_ROLE_GRP_CODE(), h.getSWK_APRV_GRP_CODE())) {
					h.setAprRoleGroup(g);
					continue HDR;
				}
			}
		}

	}


	/**
	 * ワークフロー承認の最新状況を取得
	 * 
	 * @param conn
	 * @param param
	 * @return ワークフロー承認の最新状況
	 */
	protected String getWorkflowApproveSQL(Connection conn, SlipCondition param) {
		SQLCreator sql = new SQLCreator();
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.GL, param));
		sql.add(" UNION ");
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.AP, param));
		sql.add(" UNION ");
		sql.add(getWorkflowApproveSQL(conn, HDR_TABLE.AR, param));
		return sql.toString();
	}

	/**
	 * ワークフロー承認の最新状況を取得
	 * 
	 * @param conn
	 * @param targetHdr
	 * @param param
	 * @return ワークフロー承認の最新状況
	 */
	protected String getWorkflowApproveSQL(Connection conn, String targetHdr, SlipCondition param) {
		SQLCreator sql = new SQLCreator();
		sql.add("SELECT ctl.KAI_CODE ");
		sql.add("      ,ctl.SWK_DEN_DATE ");
		sql.add("      ,ctl.SWK_DEN_NO ");
		sql.add("      ,ctl.SEQ ");
		sql.add("      ,ctl.UPD_KBN ");
		sql.add("      ,ctl.PREV_UPD_KBN ");
		sql.add("      ,ctl.APRV_ROLE_CODE ");
		sql.add("      ,ctl.NEXT_APRV_ROLE_CODE ");
		sql.add("      ,ctl.INP_DATE ");
		sql.add("      ,ctl.PRG_ID ");
		sql.add("      ,ctl.USR_ID ");

		sql.add("      ,hdr.SWK_APRV_GRP_CODE APRV_ROLE_GRP_CODE ");
		sql.add("      ,grp.APRV_ROLE_GRP_NAME ");
		sql.add("      ,rol.APRV_ROLE_NAME ");
		sql.add("      ,rol.APRV_SKIP_FLG ");
		sql.add("      ,nex.APRV_ROLE_NAME NEXT_APRV_ROLE_NAME ");
		sql.add("      ,nex.APRV_SKIP_FLG NEXT_APRV_SKIP_FLG ");

		sql.add(" FROM SWK_SYO_CTL ctl ");
		sql.add(" INNER JOIN " + targetHdr + "_SWK_HDR hdr ON hdr.KAI_CODE     = ctl.KAI_CODE ");
		sql.add("                       AND hdr.SWK_DEN_NO   = ctl.SWK_DEN_NO ");
		sql.add("                       AND hdr.SWK_DEN_DATE = ctl.SWK_DEN_DATE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT KAI_CODE ");
		sql.add("                           ,APRV_ROLE_GRP_CODE ");
		sql.add("                           ,APRV_ROLE_GRP_NAME ");
		sql.add("            FROM APRV_ROLE_GRP_MST ");
		sql.add("           ) grp ON grp.KAI_CODE           = ctl.KAI_CODE ");
		sql.add("                AND grp.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT r.KAI_CODE ");
		sql.add("                           ,g.APRV_ROLE_GRP_CODE ");
		sql.add("                           ,g.APRV_SKIP_FLG ");
		sql.add("                           ,r.APRV_ROLE_CODE ");
		sql.add("                           ,r.APRV_ROLE_NAME ");
		sql.add("            FROM APRV_ROLE_MST r ");
		sql.add("            INNER JOIN APRV_ROLE_GRP_MST g ON g.KAI_CODE = r.KAI_CODE ");
		sql.add("                                          AND g.APRV_ROLE_CODE = r.APRV_ROLE_CODE ");
		sql.add("           ) rol ON rol.KAI_CODE       = ctl.KAI_CODE ");
		sql.add("                AND rol.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add("                AND rol.APRV_ROLE_CODE     = ctl.APRV_ROLE_CODE ");
		sql.add(" LEFT JOIN (SELECT DISTINCT r.KAI_CODE ");
		sql.add("                           ,g.APRV_ROLE_GRP_CODE ");
		sql.add("                           ,g.APRV_SKIP_FLG ");
		sql.add("                           ,r.APRV_ROLE_CODE ");
		sql.add("                           ,r.APRV_ROLE_NAME ");
		sql.add("            FROM APRV_ROLE_MST r ");
		sql.add("            INNER JOIN APRV_ROLE_GRP_MST g ON g.KAI_CODE = r.KAI_CODE ");
		sql.add("                                          AND g.APRV_ROLE_CODE = r.APRV_ROLE_CODE ");
		sql.add("           ) nex ON nex.KAI_CODE           = ctl.KAI_CODE ");
		sql.add("                AND nex.APRV_ROLE_GRP_CODE = hdr.SWK_APRV_GRP_CODE ");
		sql.add("                AND nex.APRV_ROLE_CODE     = ctl.NEXT_APRV_ROLE_CODE ");
		sql.add(" WHERE 1=1 ");
		sql.add(" AND NOT EXISTS (SELECT 1 ");
		sql.add("                 FROM SWK_SYO_CTL ctl2 ");
		sql.add("                 WHERE ctl2.KAI_CODE     = ctl.KAI_CODE ");
		sql.add("                 AND   ctl2.SWK_DEN_NO   = ctl.SWK_DEN_NO ");
		sql.add("                 AND   ctl2.SWK_DEN_DATE = ctl.SWK_DEN_DATE ");
		sql.add("                 AND   ctl2.SEQ          > ctl.SEQ ");
		sql.add("                ) ");
		sql.add(getWhereSql(param, targetHdr));

		return sql.toString();
	}

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	public List<SWK_HDR> findByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.Slip);
	}

	/**
	 * 条件によるパターン検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	public List<SWK_HDR> findPatternByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.Pattern);
	}

	/**
	 * 条件による履歴検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	public List<SWK_HDR> findHistoryByCondition(SlipCondition param) {
		return findByCondition(param, SearchTable.History);
	}

	/**
	 * 検索デフォルトSQL
	 * 
	 * @param conn
	 * @param param 条件
	 * @param searchTable 検索対象
	 * @param isDetail true:明細含む false:ヘッダーのみ
	 * @return SQL
	 * @throws TException
	 */
	protected String getDefaultSqlIncludeDetails(Connection conn, SlipCondition param, SearchTable searchTable,
		boolean isDetail) throws TException {

		// 検索対象のテーブル
		String swkHeaderTableName = "_SWK_HDR hdr";
		String swkDetailTableName = "SWK_DTL swk";

		if (SearchTable.Pattern == searchTable) {
			swkHeaderTableName = "_SWK_PTN_HDR hdr";
			swkDetailTableName = "SWK_PTN_DTL swk";

		} else if (SearchTable.History == searchTable) {
			swkHeaderTableName = "_SWK_DEL_HDR hdr";
			swkDetailTableName = "SWK_DEL_DTL swk";
		}

		// SQL作成用プロパティ保存
		param_ = param;
		searchTable_ = searchTable;
		isDetail_ = isDetail;

		// カラム名
		String swkAdjKbnColumnName = "hdr.SWK_ADJ_KBN";
		String swkBookNoColumnName = "swk.SWK_BOOK_NO";

		String addonFinallyHeaderColumn = getAddonFinallyHeaderColumn(); // 最終抽出用のヘッダー追加項目
		String addonFinallyHeaderJoinSql = getAddonFinallyHeaderJoinSql(); // 最終抽出用のヘッダー追加JOIN文

		// 仕訳ジャーナル(明細)も結合する場合
		String addonDetailColumn = getAddonDetailColumn(); // 明細追加項目
		String addonFinallyDetailColumn = getAddonFinallyDetailColumn(); // 最終抽出用の明細追加項目
		String detailJoin = "";
		VCreator sqlDetail = new VCreator();
		VCreator sqlDetail2 = new VCreator();
		VCreator sqlWhere = new VCreator();
		VCreator aprv = new VCreator();
		VCreator aprvWhere = new VCreator();

		String companyCode = getCompanyCode();
		// 会社コード
		String companyCodeKMK_MST = isUseKMK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeHKM_MST = isUseHKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeUKM_MST = isUseUKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeBMN_MST = isUseBMN_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_MST = isUseTRI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeEMP_MST = isUseEMP_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeDEN_SYU_MST = isUseDEN_SYU_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeCUR_MST = isUseCUR_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeAP_HOH_MST = isUseAP_HOH_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeAP_CBK_MST = isUseAP_CBK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_SJ_MST = isUseTRI_SJ_MST ? "hdr.KAI_CODE" : "?";

		// 承認ロールマスタ連結
		if (param.isSearchWorkFlow()) {
			// 承認ロールマスタ連結
			aprv.add(" LEFT JOIN SWK_SYO_CTL syo ON syo.KAI_CODE     = hdr.KAI_CODE ");
			aprv.add("                          AND hdr.SWK_DEN_NO   = syo.SWK_DEN_NO ");
			aprv.add("                          AND hdr.SWK_DEN_DATE = syo.SWK_DEN_DATE ");
			aprv.add("                          AND NOT EXISTS (SELECT 1 ");
			aprv.add("                              FROM SWK_SYO_CTL ");
			aprv.add("                              WHERE KAI_CODE     = syo.KAI_CODE ");
			aprv.add("                              AND   SWK_DEN_DATE = syo.SWK_DEN_DATE ");
			aprv.add("                              AND   SWK_DEN_NO   = syo.SWK_DEN_NO ");
			aprv.add("                              AND   SEQ          > syo.SEQ ");
			aprv.add("                                          ) ");

		}

		// 承認ロールマスタ条件
		if (param.getAprvRoleList() != null && !param.getAprvRoleList().isEmpty()) {
			aprvWhere.add(" AND (1 = 2 ");
			aprvWhere.add(" OR syo.APRV_ROLE_CODE IN ? ", param.getAprvRoleList());
			aprvWhere.add(" OR syo.NEXT_APRV_ROLE_CODE IN ? ", param.getAprvRoleList());
			aprvWhere.add("     ) ");
		}

		if (param.isIncludeOtherSystem()) {
			sqlWhere.add(" INNER JOIN DEN_SYU_MST syu ON " + companyCodeDEN_SYU_MST + " = syu.KAI_CODE ", companyCode);
			sqlWhere.add("                           AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
		}

		if (isDetail) {
			sqlDetail.add(" ," + swkBookNoColumnName);
			sqlDetail.add(" ," + swkBookNoColumnName + " D_SWK_BOOK_NO ");
			sqlDetail.add(" ,swk.SWK_GYO_NO ");
			sqlDetail.add(" ,swk.SWK_NENDO         D_SWK_NENDO ");
			sqlDetail.add(" ,swk.SWK_TUKIDO        D_SWK_TUKIDO ");
			sqlDetail.add(" ,swk.SWK_DC_KBN        D_SWK_DC_KBN ");
			sqlDetail.add(" ,swk.SWK_KMK_CODE      D_SWK_KMK_CODE ");
			sqlDetail.add(" ,swk.SWK_HKM_CODE      D_SWK_HKM_CODE ");
			sqlDetail.add(" ,swk.SWK_UKM_CODE      D_SWK_UKM_CODE ");
			sqlDetail.add(" ,swk.SWK_DEP_CODE      D_SWK_DEP_CODE ");
			sqlDetail.add(" ,swk.SWK_ZEI_KBN       D_SWK_ZEI_KBN ");
			sqlDetail.add(" ,swk.SWK_KIN           D_SWK_KIN ");
			sqlDetail.add(" ,swk.SWK_ZEI_KIN       D_SWK_ZEI_KIN ");
			sqlDetail.add(" ,swk.SWK_ZEI_CODE      D_SWK_ZEI_CODE ");
			sqlDetail.add(" ,swk.SWK_ZEI_RATE      D_SWK_ZEI_RATE ");
			sqlDetail.add(" ,swk.SWK_GYO_TEK_CODE  D_SWK_GYO_TEK_CODE ");
			sqlDetail.add(" ,swk.SWK_GYO_TEK       D_SWK_GYO_TEK ");
			sqlDetail.add(" ,swk.SWK_TRI_CODE      D_SWK_TRI_CODE ");
			sqlDetail.add(" ,swk.SWK_EMP_CODE      D_SWK_EMP_CODE ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_1    D_SWK_KNR_CODE_1 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_2    D_SWK_KNR_CODE_2 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_3    D_SWK_KNR_CODE_3 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_4    D_SWK_KNR_CODE_4 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_5    D_SWK_KNR_CODE_5 ");
			sqlDetail.add(" ,swk.SWK_KNR_CODE_6    D_SWK_KNR_CODE_6 ");
			sqlDetail.add(" ,swk.SWK_HM_1          D_SWK_HM_1 ");
			sqlDetail.add(" ,swk.SWK_HM_2          D_SWK_HM_2 ");
			sqlDetail.add(" ,swk.SWK_HM_3          D_SWK_HM_3 ");
			sqlDetail.add(" ,swk.SWK_AUTO_KBN      D_SWK_AUTO_KBN ");
			sqlDetail.add(" ,swk.SWK_AT_KMK_CODE   D_SWK_AT_KMK_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_HKM_CODE   D_SWK_AT_HKM_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_UKM_CODE   D_SWK_AT_UKM_CODE ");
			sqlDetail.add(" ,swk.SWK_AT_DEP_CODE   D_SWK_AT_DEP_CODE ");
			sqlDetail.add(" ,swk.INP_DATE          D_INP_DATE ");
			sqlDetail.add(" ,swk.UPD_DATE          D_UPD_DATE ");
			sqlDetail.add(" ,swk.PRG_ID            D_PRG_ID ");
			sqlDetail.add(" ,swk.USR_ID            D_USR_ID ");
			sqlDetail.add(" ,swk.SWK_K_KAI_CODE    D_SWK_K_KAI_CODE ");
			sqlDetail.add(" ,swk.SWK_CUR_CODE      D_SWK_CUR_CODE ");
			sqlDetail.add(" ,swk.SWK_CUR_RATE      D_SWK_CUR_RATE ");
			sqlDetail.add(" ,swk.SWK_IN_KIN        D_SWK_IN_KIN ");
			sqlDetail.add(" ,swk.SWK_TUKE_KBN      D_SWK_TUKE_KBN ");
			sqlDetail.add(" ,swk.SWK_IN_ZEI_KIN    D_SWK_IN_ZEI_KIN ");
			sqlDetail.add(" ,swk.SWK_KESI_KBN      D_SWK_KESI_KBN ");
			sqlDetail.add(" ,swk.SWK_KESI_DATE     D_SWK_KESI_DATE ");
			sqlDetail.add(" ,swk.SWK_KESI_DEN_NO   D_SWK_KESI_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_SOUSAI_DATE   D_SWK_SOUSAI_DATE ");
			sqlDetail.add(" ,swk.SWK_SOUSAI_DEN_NO D_SWK_SOUSAI_DEN_NO ");
			sqlDetail.add(" ,swk.HAS_DATE          D_HAS_DATE ");
			// --PE追加分
			sqlDetail.add(" ,swk.SWK_EST_DEN_NO    D_EST_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_STL_DEN_NO    D_STL_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_MAE_DEN_NO    D_MAE_DEN_NO ");
			sqlDetail.add(" ,swk.SWK_MAE_GYO_NO    D_MAE_GYO_NO ");

			// 自由区分
			if (isUseInvoice) {
				sqlDetail.add(" ,swk.SWK_FREE_KBN      D_SWK_FREE_KBN ");
				sqlDetail.add(" ,swk.SWK_ORI_GYO_NO    D_SWK_ORI_GYO_NO ");
			}

			// BS勘定消込機能がONの場合
			if (isUseBS) {
				if (SearchTable.Slip.equals(getSearchTable())) {
					sqlDetail.add(" ,swk.SWK_SOUSAI_GYO_NO D_SWK_SOUSAI_GYO_NO ");
				} else {
					sqlDetail.add(" ,NULL                  D_SWK_SOUSAI_GYO_NO ");
				}
			}

			// AP/AR消込機能がONの場合
			if (!isNotUseAP || !isNotUseAR) {
				if (SearchTable.Slip.equals(getSearchTable())) {
					sqlDetail.add(" ,swk.SWK_APAR_KESI_KBN ");
					sqlDetail.add(" ,swk.SWK_APAR_DEN_NO ");
					sqlDetail.add(" ,swk.SWK_APAR_GYO_NO ");
				} else {
					sqlDetail.add(" ,0    SWK_APAR_KESI_KBN ");
					sqlDetail.add(" ,NULL SWK_APAR_DEN_NO ");
					sqlDetail.add(" ,NULL SWK_APAR_GYO_NO ");
				}
			}

			// 明細追加項目
			sqlDetail.add(" " + addonDetailColumn);

			sqlDetail2.add(" ,hdr.D_SWK_BOOK_NO ");
			sqlDetail2.add(" ,hdr.SWK_GYO_NO ");
			sqlDetail2.add(" ,hdr.D_SWK_NENDO ");
			sqlDetail2.add(" ,hdr.D_SWK_TUKIDO ");
			sqlDetail2.add(" ,hdr.D_SWK_DC_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KMK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_HKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_UKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_DEP_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_ZEI_RATE ");
			sqlDetail2.add(" ,hdr.D_SWK_GYO_TEK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_GYO_TEK ");
			sqlDetail2.add(" ,hdr.D_SWK_TRI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_EMP_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_1 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_2 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_3 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_4 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_5 ");
			sqlDetail2.add(" ,hdr.D_SWK_KNR_CODE_6 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_1 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_2 ");
			sqlDetail2.add(" ,hdr.D_SWK_HM_3 ");
			sqlDetail2.add(" ,hdr.D_SWK_AUTO_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_KMK_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_HKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_UKM_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_AT_DEP_CODE ");
			sqlDetail2.add(" ,hdr.D_INP_DATE ");
			sqlDetail2.add(" ,hdr.D_UPD_DATE ");
			sqlDetail2.add(" ,hdr.D_PRG_ID ");
			sqlDetail2.add(" ,hdr.D_USR_ID ");
			sqlDetail2.add(" ,hdr.D_SWK_K_KAI_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_CUR_CODE ");
			sqlDetail2.add(" ,hdr.D_SWK_CUR_RATE ");
			sqlDetail2.add(" ,hdr.D_SWK_IN_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_TUKE_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_IN_ZEI_KIN ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_KBN ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_DATE ");
			sqlDetail2.add(" ,hdr.D_SWK_KESI_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_DATE ");
			sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_HAS_DATE ");
			// --PE追加分
			sqlDetail2.add(" ,hdr.D_EST_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_STL_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_MAE_DEN_NO ");
			sqlDetail2.add(" ,hdr.D_MAE_GYO_NO ");
			sqlDetail2.add(" ,demp.EMP_NAME      D_EMP_NAME ");
			sqlDetail2.add(" ,demp.EMP_NAME_S    D_EMP_NAME_S ");
			sqlDetail2.add(" ,ddep.DEP_NAME      D_DEP_NAME ");
			sqlDetail2.add(" ,ddep.DEP_NAME_S    D_DEP_NAME_S ");
			sqlDetail2.add(" ,dkmk.KMK_NAME      D_KMK_NAME ");
			sqlDetail2.add(" ,dkmk.KMK_NAME_S    D_KMK_NAME_S ");
			sqlDetail2.add(" ,dkmk.HAS_FLG       D_KMK_HAS_FLG ");
			sqlDetail2.add(" ,dhkm.HKM_NAME      D_HKM_NAME ");
			sqlDetail2.add(" ,dhkm.HKM_NAME_S    D_HKM_NAME_S ");
			sqlDetail2.add(" ,dhkm.HAS_FLG       D_HKM_HAS_FLG ");
			sqlDetail2.add(" ,dukm.UKM_NAME      D_UKM_NAME ");
			sqlDetail2.add(" ,dukm.UKM_NAME_S    D_UKM_NAME_S ");
			sqlDetail2.add(" ,dukm.HAS_FLG       D_UKM_HAS_FLG ");
			sqlDetail2.add(" ,dcur.DEC_KETA      D_DEC_KETA ");
			sqlDetail2.add(" ,dzei.ZEI_NAME      D_ZEI_NAME ");
			sqlDetail2.add(" ,dzei.ZEI_NAME_S    D_ZEI_NAME_S ");
			sqlDetail2.add(" ,dtri.TRI_NAME      D_TRI_NAME ");
			sqlDetail2.add(" ,dtri.TRI_NAME_S    D_TRI_NAME_S ");
			sqlDetail2.add(" ,dknr1.KNR_NAME_1   D_KNR_NAME_1 ");
			sqlDetail2.add(" ,dknr1.KNR_NAME_S_1 D_KNR_NAME_S_1 ");
			sqlDetail2.add(" ,dknr2.KNR_NAME_2   D_KNR_NAME_2 ");
			sqlDetail2.add(" ,dknr2.KNR_NAME_S_2 D_KNR_NAME_S_2 ");
			sqlDetail2.add(" ,dknr3.KNR_NAME_3   D_KNR_NAME_3 ");
			sqlDetail2.add(" ,dknr3.KNR_NAME_S_3 D_KNR_NAME_S_3 ");
			sqlDetail2.add(" ,dknr4.KNR_NAME_4   D_KNR_NAME_4 ");
			sqlDetail2.add(" ,dknr4.KNR_NAME_S_4 D_KNR_NAME_S_4 ");
			sqlDetail2.add(" ,dknr5.KNR_NAME_5   D_KNR_NAME_5 ");
			sqlDetail2.add(" ,dknr5.KNR_NAME_S_5 D_KNR_NAME_S_5 ");
			sqlDetail2.add(" ,dknr6.KNR_NAME_6   D_KNR_NAME_6 ");
			sqlDetail2.add(" ,dknr6.KNR_NAME_S_6 D_KNR_NAME_S_6 ");

			// 自由区分
			if (isUseInvoice) {
				sqlDetail2.add(" ,hdr.D_SWK_FREE_KBN ");
				sqlDetail2.add(" ,hdr.D_SWK_ORI_GYO_NO ");
			}

			// BS勘定消込機能がONの場合
			if (isUseBS) {
				sqlDetail2.add(" ,hdr.D_SWK_SOUSAI_GYO_NO ");
			}

			// AP/AR消込機能がONの場合
			if (!isNotUseAP || !isNotUseAR) {
				sqlDetail2.add(" ,hdr.SWK_APAR_KESI_KBN ");
				sqlDetail2.add(" ,hdr.SWK_APAR_DEN_NO ");
				sqlDetail2.add(" ,hdr.SWK_APAR_GYO_NO ");
			}

			// 最終抽出用の明細追加項目
			sqlDetail2.add(" " + addonFinallyDetailColumn);

			sqlWhere.add(" INNER JOIN " + swkDetailTableName + " ON	hdr.KAI_CODE   = swk.KAI_CODE ");
			sqlWhere.add("                                      AND hdr.SWK_DEN_NO = swk.SWK_DEN_NO ");

			// パターンデータ抽出時、伝票日付NULLを考える
			if (SearchTable.Pattern == searchTable) {

				sqlWhere.add(" AND (hdr.SWK_DEN_DATE = swk.SWK_DEN_DATE ");
				sqlWhere.add("      OR (    hdr.SWK_DEN_DATE IS NULL ");
				sqlWhere.add("          AND swk.SWK_DEN_DATE IS NULL ");
				sqlWhere.add("         ) ");
				sqlWhere.add("     ) ");

			} else {
				sqlWhere.add(" AND hdr.SWK_DEN_DATE = swk.SWK_DEN_DATE ");
			}

			if (SearchTable.History == searchTable) {
				sqlWhere.add(" AND hdr.SWK_UPD_CNT = swk.SWK_UPD_CNT ");
			}

			// 調整区分
			if (param.getAdjDivisionList() != null && param.getAdjDivisionList().length != 0) {
				sqlWhere.add(" AND swk.SWK_ADJ_KBN IN " + SQLUtil.getIntInStatement(param.getAdjDivisionList()));

			}

			// 自動仕訳区分
			if (param.getAutoDivision() != -1) {
				sqlWhere.add(" AND swk.SWK_AUTO_KBN = ? ", param.getAutoDivision());

			}

			// 帳簿
			if (param.getBookNo() != 0) {
				sqlWhere.add(" AND swk.SWK_BOOK_NO = ? ", param.getBookNo());
			}

			detailJoin = getDetailJoinSql();
		}

		// GL/AP/AR共通
		// ヘッダー検索条件→必要の場合はテーブル結合
		if (!Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
			sqlWhere.add(" LEFT OUTER JOIN BMN_MST dep ");
			sqlWhere.add(" ON  hdr.KAI_CODE     = dep.KAI_CODE ");
			sqlWhere.add(" AND hdr.SWK_DEP_CODE = dep.DEP_CODE ");
		}
		// ヘッダー検索条件→必要の場合はテーブル結合
		if (!Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
			sqlWhere.add(" LEFT OUTER JOIN EMP_MST emp ");
			sqlWhere.add(" ON  hdr.KAI_CODE     = emp.KAI_CODE ");
			sqlWhere.add(" AND hdr.SWK_EMP_CODE = emp.EMP_CODE ");
		}

		// 一時テーブル名
		TempTableNameGetterSetting bgs = get(TempTableNameGetterSetting.class);
		String tempTableName = bgs.getTemporaryWorkTableName(param, "T_SLIP_DAO");
		{
			// 一時テーブルクリア
			DBUtil.execute(conn, " TRUNCATE TABLE " + tempTableName);
			if (DBUtil.isUseMySQL) {
				DBUtil.execute(conn, " DELETE FROM " + tempTableName);
			}

			int maxHeaderCount = param.getMaxHeaderCount(); // 最大ヘッダー件数
			int accuHeaderCount = 0;

			VCreator sql = new VCreator();

			// GL
			if (Util.isNullOrEmpty(param.getPaymentDayFrom()) && Util.isNullOrEmpty(param.getPaymentDayTo())
				&& Util.isNullOrEmpty(param.getReceiveDayFrom()) && Util.isNullOrEmpty(param.getReceiveDayTo())
				&& Util.isNullOrEmpty(param.getCashDayFrom()) && Util.isNullOrEmpty(param.getCashDayTo())) {

				// SELECT件数取得
				int glCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.GL);
				accuHeaderCount += glCount;

				if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
					// I00755">結果件数[{0}]が最大件数[{1}]を超えたために検索できません。\n絞り込み条件を変更して再度検索してください。
					String msg = "I00755";
					throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
				}

				if (glCount > 0) {
					// INSERT INTO
					sql = new VCreator();
					sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
					sql.add("        SELECT hdr.KAI_CODE ");
					sql.add("              ,hdr.SWK_DEN_DATE ");
					sql.add("              ,hdr.SWK_DEN_NO ");
					sql.add("              ,hdr.SWK_SEI_NO ");
					sql.add("              ,hdr.SWK_KMK_CODE ");
					sql.add("              ,hdr.SWK_HKM_CODE ");
					sql.add("              ,hdr.SWK_UKM_CODE ");
					sql.add("              ,hdr.SWK_DEP_CODE ");
					sql.add("              ,hdr.SWK_TRI_CODE ");
					sql.add("              ,hdr.SWK_EMP_CODE ");
					sql.add("              ,hdr.SWK_KIN ");
					sql.add("              ,hdr.SWK_DATA_KBN ");
					sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
					sql.add("              ,hdr.SWK_TEK_CODE ");
					sql.add("              ,hdr.SWK_TEK ");
					sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
					sql.add("              ,hdr.SWK_UPD_KBN ");
					sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
					sql.add("              ,hdr.SWK_SYO_DATE ");
					sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
					sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
					sql.add("              ,hdr.SWK_IRAI_DATE ");
					sql.add("              ,hdr.SWK_SHR_KBN ");
					sql.add("              ,hdr.SWK_KSN_KBN ");
					sql.add("              ,hdr.INP_DATE ");
					sql.add("              ,hdr.UPD_DATE ");
					sql.add("              ,hdr.PRG_ID ");
					sql.add("              ,hdr.USR_ID ");
					sql.add("              ,hdr.SWK_CUR_CODE ");
					sql.add("              ,hdr.SWK_CUR_RATE ");
					sql.add("              ,hdr.SWK_IN_KIN ");
					sql.add("              ,hdr.SWK_SYS_KBN ");
					sql.add("              ,hdr.SWK_DEN_SYU ");
					sql.add("              ,hdr.SWK_TUKE_KBN ");
					sql.add("              ,hdr.SWK_UPD_CNT ");
					sql.add("              ,NULL SWK_SIHA_KBN ");
					sql.add("              ,NULL SWK_SIHA_DATE ");
					sql.add("              ,NULL SWK_HOH_CODE ");
					sql.add("              ,NULL SWK_HORYU_KBN ");
					sql.add("              ,NULL SWK_KARI_KIN ");
					sql.add("              ,NULL SWK_KEIHI_KIN ");
					sql.add("              ,NULL SWK_SIHA_KIN ");
					sql.add("              ,NULL SWK_KARI_DR_DEN_NO ");
					sql.add("              ,NULL SWK_KARI_CR_DEN_NO ");
					sql.add("              ,NULL SWK_KESAI_KBN ");
					sql.add("              ,NULL SWK_KARI_DEP_CODE ");
					sql.add("              ,NULL SWK_INP_DATE ");
					sql.add("              ,NULL SWK_TJK_CODE ");
					sql.add("              ,NULL SWK_IN_KARI_KIN ");
					sql.add("              ,NULL SWK_IN_KEIHI_KIN ");
					sql.add("              ,NULL SWK_IN_SIHA_KIN ");
					sql.add("              ,NULL SWK_INV_CUR_CODE ");
					sql.add("              ,NULL SWK_INV_CUR_RATE ");
					sql.add("              ,NULL SWK_INV_KIN ");
					sql.add("              ,NULL SWK_CBK_CODE ");
					sql.add("              ,NULL SWK_SSY_DATE ");
					sql.add("              ,NULL SWK_UTK_NO ");
					sql.add("              ,NULL SWK_KARI_CUR_CODE ");
					sql.add("              ,NULL SWK_KARI_CUR_RATE ");
					sql.add("              ,NULL SWK_SEI_KBN ");
					sql.add("              ,NULL SWK_AR_DATE ");
					// --PE追加分
					sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
					sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
					sql.add("              ,hdr.SWK_APRV_GRP_CODE ");
					sql.add("              ," + swkAdjKbnColumnName);
					sql.add(sqlDetail);
					sql.add(getAddonHeaderColumn(HDR_TABLE.GL)); // ヘッダー追加項目
					sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.GL);
					sql.add("        FROM GL" + swkHeaderTableName);
					sql.add(aprv);
					sql.add(sqlWhere);

					// ヘッダーも条件つける
					// VCreator where = getWhereSql(param, true, HDR_TABLE.GL);
					VCreator where = getWhereSql(param, HDR_TABLE.GL);
					if (!isDetail) {
						sql.add(" WHERE 1 = 1 ");
						sql.add(aprvWhere);
					}
					sql.add(where);

					if (DBUtil.isUseMySQL) {
						sql.add(" LIMIT ? ", glCount);
					} else {
						sql.add(" AND ROWNUM <= ? ", glCount);
					}

					DBUtil.execute(conn, sql);
				}
			}

			// AP/ARのみ
			// ヘッダー検索条件→必要の場合はテーブル結合
			if (!Util.isNullOrEmpty(param.getCustmorNamesLike())) {
				sqlWhere.add(" LEFT OUTER JOIN TRI_MST tri ");
				sqlWhere.add(" ON  hdr.KAI_CODE     = tri.KAI_CODE ");
				sqlWhere.add(" AND hdr.SWK_TRI_CODE = tri.TRI_CODE ");
			}

			// AP
			sql = new VCreator();

			// SELECT件数取得
			int apCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.AP);
			accuHeaderCount += apCount;

			if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
				// I00755">結果件数[{0}]が最大件数[{1}]を超えたために検索できません。\n絞り込み条件を変更して再度検索してください。
				String msg = "I00755";
				throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
			}

			if (apCount > 0) {
				// INSERT INTO
				sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
				sql.add("        SELECT hdr.KAI_CODE ");
				sql.add("              ,hdr.SWK_DEN_DATE ");
				sql.add("              ,hdr.SWK_DEN_NO ");
				sql.add("              ,hdr.SWK_SEI_NO ");
				sql.add("              ,hdr.SWK_KMK_CODE ");
				sql.add("              ,hdr.SWK_HKM_CODE ");
				sql.add("              ,hdr.SWK_UKM_CODE ");
				sql.add("              ,hdr.SWK_DEP_CODE ");
				sql.add("              ,hdr.SWK_TRI_CODE ");
				sql.add("              ,hdr.SWK_EMP_CODE ");
				sql.add("              ,NULL SWK_KIN ");
				sql.add("              ,hdr.SWK_DATA_KBN ");
				sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
				sql.add("              ,hdr.SWK_TEK_CODE ");
				sql.add("              ,hdr.SWK_TEK ");
				sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
				sql.add("              ,hdr.SWK_UPD_KBN ");
				sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
				sql.add("              ,hdr.SWK_SYO_DATE ");
				sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DATE ");
				sql.add("              ,hdr.SWK_SHR_KBN ");
				sql.add("              ,hdr.SWK_KSN_KBN ");
				sql.add("              ,hdr.SWK_INP_DATE INP_DATE ");
				sql.add("              ,hdr.UPD_DATE ");
				sql.add("              ,hdr.PRG_ID ");
				sql.add("              ,hdr.USR_ID ");
				sql.add("              ,hdr.SWK_CUR_CODE ");
				sql.add("              ,hdr.SWK_CUR_RATE ");
				sql.add("              ,NULL SWK_IN_KIN ");
				sql.add("              ,hdr.SWK_SYS_KBN ");
				sql.add("              ,hdr.SWK_DEN_SYU ");
				sql.add("              ,hdr.SWK_TUKE_KBN ");
				sql.add("              ,hdr.SWK_UPD_CNT ");
				sql.add("              ,hdr.SWK_SIHA_KBN ");
				sql.add("              ,hdr.SWK_SIHA_DATE ");
				sql.add("              ,hdr.SWK_HOH_CODE ");
				sql.add("              ,hdr.SWK_HORYU_KBN ");
				sql.add("              ,hdr.SWK_KARI_KIN ");
				sql.add("              ,hdr.SWK_KEIHI_KIN ");
				sql.add("              ,hdr.SWK_SIHA_KIN ");
				sql.add("              ,hdr.SWK_KARI_DR_DEN_NO ");
				sql.add("              ,hdr.SWK_KARI_CR_DEN_NO ");
				sql.add("              ,hdr.SWK_KESAI_KBN ");
				sql.add("              ,hdr.SWK_KARI_DEP_CODE ");
				sql.add("              ,hdr.SWK_INP_DATE ");
				sql.add("              ,hdr.SWK_TJK_CODE ");
				sql.add("              ,hdr.SWK_IN_KARI_KIN ");
				sql.add("              ,hdr.SWK_IN_KEIHI_KIN ");
				sql.add("              ,hdr.SWK_IN_SIHA_KIN ");
				sql.add("              ,hdr.SWK_INV_CUR_CODE ");
				sql.add("              ,hdr.SWK_INV_CUR_RATE ");
				sql.add("              ,hdr.SWK_INV_KIN ");
				sql.add("              ,hdr.SWK_CBK_CODE ");
				sql.add("              ,hdr.SWK_SSY_DATE ");
				sql.add("              ,hdr.SWK_UTK_NO ");
				sql.add("              ,hdr.SWK_KARI_CUR_CODE ");
				sql.add("              ,hdr.SWK_KARI_CUR_RATE ");
				sql.add("              ,NULL SWK_SEI_KBN ");
				sql.add("              ,NULL SWK_AR_DATE ");
				// --PE追加分
				sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
				sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
				sql.add("              ,hdr.SWK_APRV_GRP_CODE ");
				sql.add("              ," + swkAdjKbnColumnName);
				sql.add(sqlDetail);
				sql.add(getAddonHeaderColumn(HDR_TABLE.AP)); // ヘッダー追加項目
				sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.AP);
				sql.add("        FROM AP" + swkHeaderTableName);
				sql.add(aprv);
				sql.add(sqlWhere);

				// ヘッダーも条件つける
				// VCreator where = getWhereSql(param, true, HDR_TABLE.AP);
				VCreator where = getWhereSql(param, HDR_TABLE.AP);
				if (!isDetail) {
					sql.add(" WHERE 1 = 1 ");
					sql.add(aprvWhere);
				}
				sql.add(where);

				if (DBUtil.isUseMySQL) {
					sql.add(" LIMIT ? ", apCount);
				} else {
					sql.add(" AND ROWNUM <= ? ", apCount);
				}

				DBUtil.execute(conn, sql);
			}

			// AR
			sql = new VCreator();

			// SELECT件数取得
			int arCount = getSwkCount(conn, param, isDetail, sqlWhere, swkHeaderTableName, HDR_TABLE.AR);
			accuHeaderCount += arCount;

			if (maxHeaderCount > 0 && accuHeaderCount > maxHeaderCount) {
				// I00755">結果件数[{0}]が最大件数[{1}]を超えたために検索できません。\n絞り込み条件を変更して再度検索してください。
				String msg = "I00755";
				throw new TWarningException(getMessage(msg, accuHeaderCount, maxHeaderCount));
			}

			if (arCount > 0) {
				// INSERT INTO
				sql.add(getTemporaryHeaderColumns(isDetail, tempTableName));
				sql.add("        SELECT hdr.KAI_CODE ");
				sql.add("              ,hdr.SWK_DEN_DATE ");
				sql.add("              ,hdr.SWK_DEN_NO ");
				sql.add("              ,hdr.SWK_SEI_NO ");
				sql.add("              ,hdr.SWK_KMK_CODE ");
				sql.add("              ,hdr.SWK_HKM_CODE ");
				sql.add("              ,hdr.SWK_UKM_CODE ");
				sql.add("              ,hdr.SWK_DEP_CODE ");
				sql.add("              ,hdr.SWK_TRI_CODE ");
				sql.add("              ,hdr.SWK_EMP_CODE ");
				sql.add("              ,hdr.SWK_KIN ");
				sql.add("              ,hdr.SWK_DATA_KBN ");
				sql.add("              ,hdr.SWK_UKE_DEP_CODE ");
				sql.add("              ,hdr.SWK_TEK_CODE ");
				sql.add("              ,hdr.SWK_TEK ");
				sql.add("              ,hdr.SWK_BEFORE_DEN_NO ");
				sql.add("              ,hdr.SWK_UPD_KBN ");
				sql.add("              ,hdr.SWK_SYO_EMP_CODE ");
				sql.add("              ,hdr.SWK_SYO_DATE ");
				sql.add("              ,hdr.SWK_IRAI_EMP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DEP_CODE ");
				sql.add("              ,hdr.SWK_IRAI_DATE ");
				sql.add("              ,hdr.SWK_SHR_KBN ");
				sql.add("              ,hdr.SWK_KSN_KBN ");
				sql.add("              ,hdr.INP_DATE ");
				sql.add("              ,hdr.UPD_DATE ");
				sql.add("              ,hdr.PRG_ID ");
				sql.add("              ,hdr.USR_ID ");
				sql.add("              ,hdr.SWK_CUR_CODE ");
				sql.add("              ,hdr.SWK_CUR_RATE ");
				sql.add("              ,hdr.SWK_IN_KIN ");
				sql.add("              ,hdr.SWK_SYS_KBN ");
				sql.add("              ,hdr.SWK_DEN_SYU ");
				sql.add("              ,hdr.SWK_TUKE_KBN ");
				sql.add("              ,hdr.SWK_UPD_CNT ");
				sql.add("              ,NULL SWK_SIHA_KBN ");
				sql.add("              ,NULL SWK_SIHA_DATE ");
				sql.add("              ,NULL SWK_HOH_CODE ");
				sql.add("              ,NULL SWK_HORYU_KBN ");
				sql.add("              ,NULL SWK_KARI_KIN ");
				sql.add("              ,NULL SWK_KEIHI_KIN ");
				sql.add("              ,NULL SWK_SIHA_KIN ");
				sql.add("              ,NULL SWK_KARI_DR_DEN_NO ");
				sql.add("              ,NULL SWK_KARI_CR_DEN_NO ");
				sql.add("              ,NULL SWK_KESAI_KBN ");
				sql.add("              ,NULL SWK_KARI_DEP_CODE ");
				sql.add("              ,NULL SWK_INP_DATE ");
				sql.add("              ,NULL SWK_TJK_CODE ");
				sql.add("              ,NULL SWK_IN_KARI_KIN ");
				sql.add("              ,NULL SWK_IN_KEIHI_KIN ");
				sql.add("              ,NULL SWK_IN_SIHA_KIN ");
				sql.add("              ,NULL SWK_INV_CUR_CODE ");
				sql.add("              ,NULL SWK_INV_CUR_RATE ");
				sql.add("              ,NULL SWK_INV_KIN ");
				sql.add("              ,hdr.SWK_CBK_CODE ");
				sql.add("              ,NULL SWK_SSY_DATE ");
				sql.add("              ,NULL SWK_UTK_NO ");
				sql.add("              ,NULL SWK_KARI_CUR_CODE ");
				sql.add("              ,NULL SWK_KARI_CUR_RATE ");
				sql.add("              ,hdr.SWK_SEI_KBN ");
				sql.add("              ,hdr.SWK_AR_DATE ");
				// --PE追加分
				sql.add("              ,hdr.SWK_EST_CANCEL_DEN_NO ");
				sql.add("              ,hdr.SWK_BASE_EST_DEN_NO ");
				sql.add("              ,hdr.SWK_APRV_GRP_CODE ");

				sql.add("              ," + swkAdjKbnColumnName);
				sql.add(sqlDetail);
				sql.add(getAddonHeaderColumn(HDR_TABLE.AR)); // ヘッダー追加項目
				sql.add("              ,? AS ROW_TYPE ", HDR_TABLE.AR);
				sql.add("        FROM AR" + swkHeaderTableName);
				sql.add(aprv);
				sql.add(sqlWhere);

				// ヘッダーも条件つける
				// VCreator where = getWhereSql(param, true, HDR_TABLE.AR);
				VCreator where = getWhereSql(param, HDR_TABLE.AR);
				if (!isDetail) {
					sql.add(" WHERE 1 = 1 ");
					sql.add(aprvWhere);
				}
				sql.add(where);

				if (DBUtil.isUseMySQL) {
					sql.add(" LIMIT ? ", arCount);
				} else {
					sql.add(" AND ROWNUM <= ? ", arCount);
				}
				DBUtil.execute(conn, sql);
			}
		}

		// 中間テーブルも件数チェック
		int daoCount = DBUtil.selectOneInt(conn, " SELECT COUNT(1) FROM " + tempTableName);
		if (daoCount == 0) {
			throw new SlipNoDataException();
		}

		// 本SQL
		VCreator sql = new VCreator();

		sql.add(getSelectKeyWord());

		if (isUseAttachment) {
			// 添付情報の有無を取得するためのSQL
			sql.add(" ( SELECT COUNT(*) ");
			sql.add("   FROM SWK_ATTACH atch ");
			sql.add("   WHERE atch.KAI_CODE   = hdr.KAI_CODE ");
			sql.add("     AND atch.SWK_DEN_NO = hdr.SWK_DEN_NO ");
			sql.add(" ) AS ATTACH_COUNT ");

		} else {
			sql.add(" 0    ATTACH_COUNT ");
		}
		if (isUseTag) {
			// 付箋情報の有無を取得するためのSQL
			sql.add(" , ( SELECT COUNT(*) ");
			sql.add("   FROM SWK_TAG tag ");
			sql.add("   WHERE tag.KAI_CODE   = hdr.KAI_CODE ");
			sql.add("     AND tag.SWK_DEN_NO = hdr.SWK_DEN_NO ");
			sql.add(" ) AS TAG_COUNT ");

		} else {
			sql.add(" , 0    TAG_COUNT ");
		}
		sql.add(" ,hdr.KAI_CODE ");
		sql.add(" ,env.KAI_NAME ");
		sql.add(" ,env.KAI_NAME_S ");
		sql.add(" ,hdr.SWK_DEN_DATE ");
		sql.add(" ,hdr.SWK_DEN_NO ");
		sql.add(" ,hdr.SWK_SEI_NO ");
		sql.add(" ,hdr.SWK_KMK_CODE ");
		sql.add(" ,kmk.KMK_NAME   SWK_KMK_NAME ");
		sql.add(" ,kmk.KMK_NAME_S SWK_KMK_NAME_S ");
		sql.add(" ,hdr.SWK_HKM_CODE ");
		sql.add(" ,hkm.HKM_NAME   SWK_HKM_NAME ");
		sql.add(" ,hkm.HKM_NAME_S SWK_HKM_NAME_S ");
		sql.add(" ,hdr.SWK_UKM_CODE ");
		sql.add(" ,ukm.UKM_NAME   SWK_UKM_NAME ");
		sql.add(" ,ukm.UKM_NAME_S SWK_UKM_NAME_S ");
		sql.add(" ,hdr.SWK_DEP_CODE ");
		sql.add(" ,dep.DEP_NAME   SWK_DEP_NAME ");
		sql.add(" ,dep.DEP_NAME_S SWK_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_TRI_CODE ");
		sql.add(" ,tri.TRI_NAME   SWK_TRI_NAME ");
		sql.add(" ,tri.TRI_NAME_S SWK_TRI_NAME_S ");
		sql.add(" ,hdr.SWK_EMP_CODE ");
		sql.add(" ,emp.EMP_NAME   SWK_EMP_NAME ");
		sql.add(" ,emp.EMP_NAME_S SWK_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_KIN ");
		sql.add(" ,hdr.SWK_DATA_KBN ");
		sql.add(" ,hdr.SWK_UKE_DEP_CODE ");
		sql.add(" ,depuke.DEP_NAME   SWK_UKE_DEP_NAME ");
		sql.add(" ,depuke.DEP_NAME_S SWK_UKE_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_TEK_CODE ");
		sql.add(" ,hdr.SWK_TEK ");
		sql.add(" ,hdr.SWK_BEFORE_DEN_NO ");
		sql.add(" ,hdr.SWK_UPD_KBN ");
		sql.add(" ,hdr.SWK_SYO_EMP_CODE ");
		sql.add(" ,empsyo.EMP_NAME   SWK_SYO_EMP_NAME ");
		sql.add(" ,empsyo.EMP_NAME_S SWK_SYO_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_SYO_DATE ");
		sql.add(" ,hdr.SWK_IRAI_EMP_CODE ");
		sql.add(" ,empirai.EMP_NAME   SWK_IRAI_EMP_NAME ");
		sql.add(" ,empirai.EMP_NAME_S SWK_IRAI_EMP_NAME_S ");
		sql.add(" ,hdr.SWK_IRAI_DEP_CODE ");
		sql.add(" ,depirai.DEP_NAME   SWK_IRAI_DEP_NAME ");
		sql.add(" ,depirai.DEP_NAME_S SWK_IRAI_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_IRAI_DATE ");
		sql.add(" ,hdr.SWK_SHR_KBN ");
		sql.add(" ,hdr.SWK_KSN_KBN ");
		sql.add(" ,hdr.INP_DATE ");
		sql.add(" ,hdr.UPD_DATE ");
		sql.add(" ,hdr.PRG_ID ");
		sql.add(" ,hdr.USR_ID ");
		sql.add(" ,hdr.SWK_CUR_CODE ");
		sql.add(" ,cur.DEC_KETA SWK_CUR_DEC_KETA ");
		sql.add(" ,hdr.SWK_CUR_RATE ");
		sql.add(" ,hdr.SWK_IN_KIN ");
		sql.add(" ,hdr.SWK_SYS_KBN ");
		sql.add(" ,hdr.SWK_DEN_SYU ");
		sql.add(" ,syu.DEN_SYU_NAME   SWK_DEN_SYU_NAME ");
		sql.add(" ,syu.DEN_SYU_NAME_S SWK_DEN_SYU_NAME_S ");
		sql.add(" ,syu.DEN_SYU_NAME_K SWK_DEN_SYU_NAME_K ");
		sql.add(" ,hdr.SWK_TUKE_KBN ");
		sql.add(" ,hdr.SWK_UPD_CNT ");
		sql.add(" ,hdr.SWK_SIHA_KBN ");
		sql.add(" ,hdr.SWK_SIHA_DATE ");
		sql.add(" ,hdr.SWK_HOH_CODE ");
		sql.add(" ,hoh.HOH_HOH_NAME SWK_HOH_NAME ");
		sql.add(" ,hoh.HOH_NAI_CODE SWK_HOH_NAI_CODE ");
		sql.add(" ,hdr.SWK_HORYU_KBN ");
		sql.add(" ,hdr.SWK_KARI_KIN ");
		sql.add(" ,hdr.SWK_KEIHI_KIN ");
		sql.add(" ,hdr.SWK_SIHA_KIN ");
		sql.add(" ,hdr.SWK_KARI_DR_DEN_NO ");
		sql.add(" ,hdr.SWK_KARI_CR_DEN_NO ");
		sql.add(" ,hdr.SWK_KESAI_KBN ");
		sql.add(" ,hdr.SWK_KARI_DEP_CODE ");
		sql.add(" ,depkari.DEP_NAME   SWK_KARI_DEP_NAME ");
		sql.add(" ,depkari.DEP_NAME_S SWK_KARI_DEP_NAME_S ");
		sql.add(" ,hdr.SWK_INP_DATE ");
		sql.add(" ,hdr.SWK_TJK_CODE ");
		sql.add(" ,trisj.YKN_KBN         TJK_YKN_KBN ");
		sql.add(" ,trisj.YKN_NO          TJK_YKN_NO ");
		sql.add(" ,trisj.YKN_KANA        TJK_YKN_KANA ");
		sql.add(" ,trisj.GS_BNK_NAME     SWK_TJK_GS_BNK_NAME ");
		sql.add(" ,trisj.GS_STN_NAME     SWK_TJK_GS_STN_NAME ");
		sql.add(" ,tjkbnk.BNK_NAME_S     SWK_TJK_BNK_NAME_S ");
		sql.add(" ,tjkbnk.BNK_STN_NAME_S SWK_TJK_BNK_STN_NAME_S ");
		sql.add(" ,hdr.SWK_IN_KARI_KIN ");
		sql.add(" ,hdr.SWK_IN_KEIHI_KIN ");
		sql.add(" ,hdr.SWK_IN_SIHA_KIN ");
		sql.add(" ,hdr.SWK_INV_CUR_CODE ");
		sql.add(" ,hdr.SWK_INV_CUR_RATE ");
		sql.add(" ,hdr.SWK_INV_KIN ");
		sql.add(" ,hdr.SWK_CBK_CODE ");
		sql.add(" ,cbk.CBK_NAME       SWK_CBK_NAME ");
		sql.add(" ,cbk.CBK_STN_CODE   SWK_CBK_STN_CODE ");
		sql.add(" ,cbk.CBK_YKN_KBN    SWK_CBK_YKN_KBN ");
		sql.add(" ,cbk.CBK_YKN_NO     SWK_CBK_YKN_NO ");
		sql.add(" ,bnk.BNK_NAME_S     SWK_BNK_NAME_S ");
		sql.add(" ,bnk.BNK_STN_NAME_S SWK_BNK_STN_NAME_S ");
		sql.add(" ,hdr.SWK_SSY_DATE ");
		sql.add(" ,hdr.SWK_UTK_NO ");
		sql.add(" ,hdr.SWK_KARI_CUR_CODE ");
		sql.add(" ,hdr.SWK_KARI_CUR_RATE ");
		sql.add(" ,hdr.SWK_SEI_KBN ");
		sql.add(" ,hdr.SWK_AR_DATE ");
		sql.add(" ,hdr.SWK_ADJ_KBN ");
		sql.add(" ,keyCur.CUR_CODE  KEY_CUR_CODE ");
		sql.add(" ,keyCur.DEC_KETA  KEY_CUR_DEC_KETA ");
		sql.add(" ,funcCur.CUR_CODE FUNC_CUR_CODE ");
		sql.add(" ,funcCur.DEC_KETA FUNC_CUR_DEC_KETA ");
		// --PE追加分
		sql.add(" ,hdr.SWK_EST_CANCEL_DEN_NO ");
		sql.add(" ,hdr.SWK_BASE_EST_DEN_NO ");
		// --承認グループ
		sql.add(" ,hdr.SWK_APRV_GRP_CODE ");
		sql.add(sqlDetail2);
		sql.add(" " + addonFinallyHeaderColumn); // 最終抽出用のヘッダーと明細追加項目

		sql.add(" FROM " + tempTableName + " hdr ");
		sql.add(" LEFT OUTER JOIN KMK_MST kmk ON " + companyCodeKMK_MST + " = kmk.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = kmk.KMK_CODE ");
		sql.add(" LEFT OUTER JOIN HKM_MST hkm ON " + companyCodeHKM_MST + " = hkm.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = hkm.KMK_CODE ");
		sql.add("                            AND hdr.SWK_HKM_CODE = hkm.HKM_CODE ");
		sql.add(" LEFT OUTER JOIN UKM_MST ukm ON " + companyCodeUKM_MST + " = ukm.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_KMK_CODE = ukm.KMK_CODE ");
		sql.add("                            AND hdr.SWK_HKM_CODE = ukm.HKM_CODE ");
		sql.add("                            AND hdr.SWK_UKM_CODE = ukm.UKM_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST dep ON " + companyCodeBMN_MST + " = dep.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_DEP_CODE = dep.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_MST tri ON " + companyCodeTRI_MST + " = tri.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_TRI_CODE = tri.TRI_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST emp ON " + companyCodeEMP_MST + " = emp.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_EMP_CODE = emp.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depuke ON " + companyCodeBMN_MST + " = depuke.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_UKE_DEP_CODE = depuke.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST empsyo ON " + companyCodeEMP_MST + " = empsyo.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_SYO_EMP_CODE = empsyo.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST empirai ON " + companyCodeEMP_MST + " = empirai.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_IRAI_EMP_CODE = empirai.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depirai ON " + companyCodeBMN_MST + " = depirai.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_IRAI_DEP_CODE = depirai.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ON " + companyCodeDEN_SYU_MST + " = syu.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_DEN_SYU = syu.DEN_SYU_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST cur ON " + companyCodeCUR_MST + " = cur.KAI_CODE ", companyCode);
		sql.add("                            AND hdr.SWK_CUR_CODE = cur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN AP_HOH_MST hoh ON " + companyCodeAP_HOH_MST + " = hoh.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_HOH_CODE = hoh.HOH_HOH_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST depkari ON " + companyCodeBMN_MST + " = depkari.KAI_CODE ", companyCode);
		sql.add("                                AND hdr.SWK_KARI_DEP_CODE = depkari.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN AP_CBK_MST cbk ON " + companyCodeAP_CBK_MST + " = cbk.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.SWK_CBK_CODE = cbk.CBK_CBK_CODE ");
		sql.add(" LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
		sql.add("                            AND cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_SJ_MST trisj ON " + companyCodeTRI_SJ_MST + " = trisj.KAI_CODE ", companyCode);
		sql.add("                                 AND hdr.SWK_TRI_CODE = trisj.TRI_CODE ");
		sql.add("                                 AND hdr.SWK_TJK_CODE = trisj.TJK_CODE ");
		sql.add(" LEFT OUTER JOIN BNK_MST tjkbnk ON trisj.BNK_CODE     = tjkbnk.BNK_CODE ");
		sql.add("                               AND trisj.BNK_STN_CODE = tjkbnk.BNK_STN_CODE ");
		sql.add(" LEFT OUTER JOIN CMP_MST cmp ON hdr.KAI_CODE = cmp.KAI_CODE ");
		sql.add(" LEFT OUTER JOIN ENV_MST env ON hdr.KAI_CODE = env.KAI_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST keyCur ON cmp.KAI_CODE = keyCur.KAI_CODE ");
		sql.add("                               AND cmp.CUR_CODE = keyCur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST funcCur ON cmp.KAI_CODE     = funcCur.KAI_CODE ");
		sql.add("                                AND cmp.FNC_CUR_CODE = funcCur.CUR_CODE ");
		sql.add(addonFinallyHeaderJoinSql);
		sql.add(detailJoin);

		sql.add(" WHERE 1 = 1 ");

		// 検索条件取得
		// VCreator where = getWhereSql(param, false, null);
		VCreator where = getWhereSql(param, null);
		sql.add(where);

		// 追加検索条件
		sql.add(getAddonSearchSQL());

		// ソート順
		sql.add(" ORDER BY ");
		if (!Util.isNullOrEmpty(param.getOrder())) {
			sql.add(param.getOrder());

		} else {
			if (SearchTable.History == searchTable) {
				sql.add("  hdr.KAI_CODE ");
				sql.add(" ,hdr.SWK_DEN_NO ");
				sql.add(" ,hdr.SWK_UPD_CNT ");
				sql.add(" ,hdr.SWK_DEN_DATE ");

			} else {

				if (ServerConfig.isFlagOn("trans.slip.transfer.page.no")) {
					// ログイン会社は最優先
					sql.add("  CASE WHEN hdr.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode());
					sql.add(" ,hdr.KAI_CODE ");
				} else {
					sql.add("  hdr.KAI_CODE ");
				}

				sql.add(" ,hdr.SWK_DEN_DATE ");
				sql.add(" ,hdr.SWK_DEN_NO ");

				if (isDetail) {
					sql.add(" ,hdr.SWK_GYO_NO ");
				}
			}
		}
		return sql.toSQL();
	}

	/**
	 * @return SELECT
	 */
	protected String getSelectKeyWord() {
		return " SELECT ";
	}

	/**
	 * SELECT件数取得
	 * 
	 * @param conn
	 * @param param
	 * @param isDetail
	 * @param sqlWhere
	 * @param swkHeaderTableName
	 * @param headerTableInitial
	 * @return 件数
	 * @throws TException
	 */
	protected int getSwkCount(Connection conn, SlipCondition param, boolean isDetail, VCreator sqlWhere,
		String swkHeaderTableName, String headerTableInitial) throws TException {

		int count = 0;

		try {
			VCreator sql = new VCreator();
			sql.add(" SELECT COUNT(hdr.KAI_CODE) COUNT ");
			sql.add(" FROM " + headerTableInitial + swkHeaderTableName);
			sql.add(sqlWhere);

			// VCreator where = getWhereSql(param, true, headerTableInitial);
			VCreator where = getWhereSql(param, headerTableInitial);
			if (!isDetail) {
				sql.add(" WHERE 1 = 1 ");
			}
			sql.add(where);

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				count = rs.getInt("COUNT");
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (SQLException e) {
			throw new TException(e);
		}

		return count;
	}

	/**
	 * @param isDetail
	 * @param tempTableName
	 * @return 一時テーブル用列名
	 */
	protected String getTemporaryHeaderColumns(boolean isDetail, String tempTableName) {
		VCreator sql = new VCreator();
		sql.add(" INSERT INTO " + tempTableName);
		sql.add("   (KAI_CODE ");
		sql.add("   ,SWK_DEN_DATE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SWK_SEI_NO ");
		sql.add("   ,SWK_KMK_CODE ");
		sql.add("   ,SWK_HKM_CODE ");
		sql.add("   ,SWK_UKM_CODE ");
		sql.add("   ,SWK_DEP_CODE ");
		sql.add("   ,SWK_TRI_CODE ");
		sql.add("   ,SWK_EMP_CODE ");
		sql.add("   ,SWK_KIN ");
		sql.add("   ,SWK_DATA_KBN ");
		sql.add("   ,SWK_UKE_DEP_CODE ");
		sql.add("   ,SWK_TEK_CODE ");
		sql.add("   ,SWK_TEK ");
		sql.add("   ,SWK_BEFORE_DEN_NO ");
		sql.add("   ,SWK_UPD_KBN ");
		sql.add("   ,SWK_SYO_EMP_CODE ");
		sql.add("   ,SWK_SYO_DATE ");
		sql.add("   ,SWK_IRAI_EMP_CODE ");
		sql.add("   ,SWK_IRAI_DEP_CODE ");
		sql.add("   ,SWK_IRAI_DATE ");
		sql.add("   ,SWK_SHR_KBN ");
		sql.add("   ,SWK_KSN_KBN ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,UPD_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add("   ,SWK_CUR_CODE ");
		sql.add("   ,SWK_CUR_RATE ");
		sql.add("   ,SWK_IN_KIN ");
		sql.add("   ,SWK_SYS_KBN ");
		sql.add("   ,SWK_DEN_SYU ");
		sql.add("   ,SWK_TUKE_KBN ");
		sql.add("   ,SWK_UPD_CNT ");
		sql.add("   ,SWK_SIHA_KBN ");
		sql.add("   ,SWK_SIHA_DATE ");
		sql.add("   ,SWK_HOH_CODE ");
		sql.add("   ,SWK_HORYU_KBN ");
		sql.add("   ,SWK_KARI_KIN ");
		sql.add("   ,SWK_KEIHI_KIN ");
		sql.add("   ,SWK_SIHA_KIN ");
		sql.add("   ,SWK_KARI_DR_DEN_NO ");
		sql.add("   ,SWK_KARI_CR_DEN_NO ");
		sql.add("   ,SWK_KESAI_KBN ");
		sql.add("   ,SWK_KARI_DEP_CODE ");
		sql.add("   ,SWK_INP_DATE ");
		sql.add("   ,SWK_TJK_CODE ");
		sql.add("   ,SWK_IN_KARI_KIN ");
		sql.add("   ,SWK_IN_KEIHI_KIN ");
		sql.add("   ,SWK_IN_SIHA_KIN ");
		sql.add("   ,SWK_INV_CUR_CODE ");
		sql.add("   ,SWK_INV_CUR_RATE ");
		sql.add("   ,SWK_INV_KIN ");
		sql.add("   ,SWK_CBK_CODE ");
		sql.add("   ,SWK_SSY_DATE ");
		sql.add("   ,SWK_UTK_NO ");
		sql.add("   ,SWK_KARI_CUR_CODE ");
		sql.add("   ,SWK_KARI_CUR_RATE ");
		sql.add("   ,SWK_SEI_KBN ");
		sql.add("   ,SWK_AR_DATE ");
		sql.add("   ,SWK_EST_CANCEL_DEN_NO ");
		sql.add("   ,SWK_BASE_EST_DEN_NO ");
		sql.add("   ,SWK_APRV_GRP_CODE ");
		sql.add("   ,SWK_ADJ_KBN ");

		if (isDetail) {
			sql.add("   ,SWK_BOOK_NO ");
			sql.add("   ,D_SWK_BOOK_NO ");
			sql.add("   ,SWK_GYO_NO ");
			sql.add("   ,D_SWK_NENDO ");
			sql.add("   ,D_SWK_TUKIDO ");
			sql.add("   ,D_SWK_DC_KBN ");
			sql.add("   ,D_SWK_KMK_CODE ");
			sql.add("   ,D_SWK_HKM_CODE ");
			sql.add("   ,D_SWK_UKM_CODE ");
			sql.add("   ,D_SWK_DEP_CODE ");
			sql.add("   ,D_SWK_ZEI_KBN ");
			sql.add("   ,D_SWK_KIN ");
			sql.add("   ,D_SWK_ZEI_KIN ");
			sql.add("   ,D_SWK_ZEI_CODE ");
			sql.add("   ,D_SWK_ZEI_RATE ");
			sql.add("   ,D_SWK_GYO_TEK_CODE ");
			sql.add("   ,D_SWK_GYO_TEK ");
			sql.add("   ,D_SWK_TRI_CODE ");
			sql.add("   ,D_SWK_EMP_CODE ");
			sql.add("   ,D_SWK_KNR_CODE_1 ");
			sql.add("   ,D_SWK_KNR_CODE_2 ");
			sql.add("   ,D_SWK_KNR_CODE_3 ");
			sql.add("   ,D_SWK_KNR_CODE_4 ");
			sql.add("   ,D_SWK_KNR_CODE_5 ");
			sql.add("   ,D_SWK_KNR_CODE_6 ");
			sql.add("   ,D_SWK_HM_1 ");
			sql.add("   ,D_SWK_HM_2 ");
			sql.add("   ,D_SWK_HM_3 ");
			sql.add("   ,D_SWK_AUTO_KBN ");
			sql.add("   ,D_SWK_AT_KMK_CODE ");
			sql.add("   ,D_SWK_AT_HKM_CODE ");
			sql.add("   ,D_SWK_AT_UKM_CODE ");
			sql.add("   ,D_SWK_AT_DEP_CODE ");
			sql.add("   ,D_INP_DATE ");
			sql.add("   ,D_UPD_DATE ");
			sql.add("   ,D_PRG_ID ");
			sql.add("   ,D_USR_ID ");
			sql.add("   ,D_SWK_K_KAI_CODE ");
			sql.add("   ,D_SWK_CUR_CODE ");
			sql.add("   ,D_SWK_CUR_RATE ");
			sql.add("   ,D_SWK_IN_KIN ");
			sql.add("   ,D_SWK_TUKE_KBN ");
			sql.add("   ,D_SWK_IN_ZEI_KIN ");
			sql.add("   ,D_SWK_KESI_KBN ");
			sql.add("   ,D_SWK_KESI_DATE ");
			sql.add("   ,D_SWK_KESI_DEN_NO ");
			sql.add("   ,D_SWK_SOUSAI_DATE ");
			sql.add("   ,D_SWK_SOUSAI_DEN_NO ");
			sql.add("   ,D_HAS_DATE ");
			sql.add("   ,D_EST_DEN_NO ");
			sql.add("   ,D_STL_DEN_NO ");
			sql.add("   ,D_MAE_DEN_NO ");
			sql.add("   ,D_MAE_GYO_NO ");

			// 自由区分
			if (isUseInvoice) {
				sql.add("   ,D_SWK_FREE_KBN ");
				sql.add("   ,D_SWK_ORI_GYO_NO ");
			}

			// BS勘定消込機能がONの場合
			if (isUseBS) {
				sql.add("   ,D_SWK_SOUSAI_GYO_NO ");
			}

			// AP/AR消込機能がONの場合
			if (!isNotUseAP || !isNotUseAR) {
				sql.add("   ,SWK_APAR_KESI_KBN ");
				sql.add("   ,SWK_APAR_DEN_NO ");
				sql.add("   ,SWK_APAR_GYO_NO ");
			}
		}

		sql.add("   ,ROW_TYPE) ");

		return sql.toSQL();
	}

	/**
	 * 検索条件取得
	 * 
	 * @param param 検索条件 // * @param isForHeaderOnly true:HDR用のみ
	 * @param initial
	 * @return 検索条件
	 */
	protected VCreator getWhereSql(SlipCondition param, String initial) {

		VCreator sql = new VCreator();

		// 会社コード
		if (!Util.isNullOrEmpty(param.getCompanyCode())) {
			sql.add(" AND hdr.KAI_CODE = ? ", param.getCompanyCode());
		}

		// 伝票番号
		if (!Util.isNullOrEmpty(param.getSlipNo())) {
			sql.add(" AND hdr.SWK_DEN_NO = ? ", param.getSlipNo());
		}

		// 調整区分
		if (param.getAdjDivisionList() != null && param.getAdjDivisionList().length != 0) {

			sql.add(" AND hdr.SWK_ADJ_KBN IN " + SQLUtil.getIntInStatement(param.getAdjDivisionList()));
		}

		// 摘要コード
		if (!Util.isNullOrEmpty(param.getRemarkCode())) {
			sql.add(" AND hdr.SWK_TEK_CODE = ? ", param.getRemarkCode());
		}

		// 伝票番号LIKE
		if (!Util.isNullOrEmpty(param.getSlipNoLike())) {
			if (isUseSlipNoAmbiLike) {
				// 部分一致
				sql.addLikeAmbi(" AND hdr.SWK_DEN_NO ? ", param.getSlipNoLike());
			} else {
				// 前方一致
				sql.addLikeFront(" AND hdr.SWK_DEN_NO ? ", param.getSlipNoLike());
			}
		}

		// 伝票番号From
		if (!Util.isNullOrEmpty(param.getSlipNoFrom())) {
			sql.add(" AND hdr.SWK_DEN_NO >= ? ", param.getSlipNoFrom());
		}

		// 伝票番号To
		if (!Util.isNullOrEmpty(param.getSlipNoTo())) {
			sql.add(" AND hdr.SWK_DEN_NO <= ? ", param.getSlipNoTo());
		}

		// データ区分
		if (param.getDataKindList() != null && param.getDataKindList().length != 0) {
			sql.add(" AND hdr.SWK_DATA_KBN IN " + SQLUtil.getInStatement(param.getDataKindList()));
		}

		// 伝票種別
		if (param.getSlipTypeList() != null && param.getSlipTypeList().length != 0) {
			if (param.isIncludeOtherSystem()) {
				// 他シス含む
				sql.add(" AND (   hdr.SWK_DEN_SYU IN " + SQLUtil.getInStatement(param.getSlipTypeList()));
				sql.add("      OR syu.TA_SYS_KBN = 1 "); // 他シス区分
				sql.add("     ) ");

			} else {
				sql.add(" AND hdr.SWK_DEN_SYU IN " + SQLUtil.getInStatement(param.getSlipTypeList()));
			}
		}

		// 更新区分
		if (param.getSlipStateList() != null && param.getSlipStateList().length != 0) {
			sql.add(" AND hdr.SWK_UPD_KBN IN " + SQLUtil.getIntInStatement(param.getSlipStateList()));
		}

		// 伝票日付
		if (!Util.isNullOrEmpty(param.getSlipDate())) {
			sql.add(" AND hdr.SWK_DEN_DATE = ? ", param.getSlipDate());
		}

		// 伝票日付FROM
		if (!Util.isNullOrEmpty(param.getSlipDateFrom())) {
			sql.add(" AND hdr.SWK_DEN_DATE >= ? ", param.getSlipDateFrom());
		}

		// 伝票日付TO
		if (!Util.isNullOrEmpty(param.getSlipDateTo())) {
			sql.add(" AND hdr.SWK_DEN_DATE <= ? ", param.getSlipDateTo());
		}

		// 依頼日FROM
		if (!Util.isNullOrEmpty(param.getRequestDateFrom())) {
			sql.add(" AND hdr.SWK_IRAI_DATE >= ? ", param.getRequestDateFrom());
		}

		// 依頼日TO
		if (!Util.isNullOrEmpty(param.getRequestDateTo())) {
			sql.add(" AND hdr.SWK_IRAI_DATE < ? ", DateUtil.addDay(param.getRequestDateTo(), 1));
		}

		// 依頼部門コード
		if (param.isRequestDeptCodeIncledNull()) {
			if (!Util.isNullOrEmpty(param.getRequestDeptCode())) {
				sql.add(" AND (   hdr.SWK_IRAI_DEP_CODE = ? ", param.getRequestDeptCode());
				sql.add("      OR hdr.SWK_IRAI_DEP_CODE IS NULL ");
				sql.add("     ) ");
			}
		} else {
			if (!Util.isNullOrEmpty(param.getRequestDeptCode())) {
				sql.add(" AND hdr.SWK_IRAI_DEP_CODE = ? ", param.getRequestDeptCode());
			}
		}

		// 依頼者コード
		if (param.isRequestEmpCodeIncledNull()) {
			if (!Util.isNullOrEmpty(param.getRequestEmpCode())) {
				sql.add(" AND (   hdr.SWK_IRAI_EMP_CODE = ? ", param.getRequestEmpCode());
				sql.add("      OR hdr.SWK_IRAI_EMP_CODE IS NULL ");
				sql.add("     ) ");
			}
		} else {
			if (!Util.isNullOrEmpty(param.getRequestEmpCode())) {
				sql.add(" AND hdr.SWK_IRAI_EMP_CODE = ? ", param.getRequestEmpCode());
			}
		}

		if (Util.isNullOrEmpty(initial) || Util.equals(HDR_TABLE.AP, initial)) {
			// 支払日FROM
			if (!Util.isNullOrEmpty(param.getPaymentDayFrom())) {
				sql.add(" AND hdr.SWK_SIHA_DATE >= ? ", param.getPaymentDayFrom());
			}

			// 支払日TO
			if (!Util.isNullOrEmpty(param.getPaymentDayTo())) {
				sql.add(" AND hdr.SWK_SIHA_DATE <= ? ", param.getPaymentDayTo());
			}
		}
		if (Util.equals(HDR_TABLE.AP, initial)) {
			// 出納日FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND hdr.SWK_SIHA_DATE >= ? ", param.getCashDayFrom());
			}

			// 出納日TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND hdr.SWK_SIHA_DATE <= ? ", param.getCashDayTo());
			}
		}

		if (Util.isNullOrEmpty(initial) || Util.equals(HDR_TABLE.AR, initial)) {
			// 入金日FROM
			if (!Util.isNullOrEmpty(param.getReceiveDayFrom())) {
				sql.add(" AND hdr.SWK_AR_DATE >= ? ", param.getReceiveDayFrom());
			}

			// 入金日TO
			if (!Util.isNullOrEmpty(param.getReceiveDayTo())) {
				sql.add(" AND hdr.SWK_AR_DATE <= ? ", param.getReceiveDayTo());
			}
		}
		if (Util.equals(HDR_TABLE.AR, initial)) {
			// 出納日FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND hdr.SWK_AR_DATE   >= ? ", param.getCashDayFrom());
			}

			// 出納日TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND hdr.SWK_AR_DATE   <= ? ", param.getCashDayTo());
			}
		}

		if (Util.isNullOrEmpty(initial)) {
			// 出納日FROM
			if (!Util.isNullOrEmpty(param.getCashDayFrom())) {
				sql.add(" AND (   hdr.SWK_SIHA_DATE >= ? ", param.getCashDayFrom());
				sql.add("      OR hdr.SWK_AR_DATE   >= ? ", param.getCashDayFrom());
				sql.add("     ) ");
			}

			// 出納日TO
			if (!Util.isNullOrEmpty(param.getCashDayTo())) {
				sql.add(" AND (   hdr.SWK_SIHA_DATE <= ? ", param.getCashDayTo());
				sql.add("      OR hdr.SWK_AR_DATE   <= ? ", param.getCashDayTo());
				sql.add("     ) ");
			}
		}

		// 会社間付替伝票区分
		if (param.getGroupAccountDivision() != -1) {
			sql.add(" AND hdr.SWK_TUKE_KBN = ? ", param.getGroupAccountDivision());
		}

		// 排他フラグ
		if (param.getLockState() != -1) {
			sql.add(" AND hdr.SWK_SHR_KBN = ? ", param.getLockState());
		}

		// 伝票番号(複数)
		if (param.getSlipNoList() != null && param.getSlipNoList().length != 0) {
			sql.add(" AND (  1 = 2 ");
			for (String slipNo : param.getSlipNoList()) {
				sql.add("  OR hdr.SWK_DEN_NO = ? ", slipNo);
			}
			sql.add("     ) ");
		}

		// 社員LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
		if (!Util.isNullOrEmpty(param.getEmployeeNamesLike())) {
			sql.addNLikeAmbi(" AND emp.EMP_NAME_S ? ", param.getEmployeeNamesLike());
		}

		// 摘要LIKE
		if (!Util.isNullOrEmpty(param.getRemarksLike())) {
			sql.addNLikeAmbi(" AND hdr.SWK_TEK ? ", param.getRemarksLike());
		}

		// 取引先略称LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getCustmorNamesLike())) {
		if (!Util.equals(HDR_TABLE.GL, initial) && !Util.isNullOrEmpty(param.getCustmorNamesLike())) {
			sql.addNLikeAmbi(" AND tri.TRI_NAME_S ? ", param.getCustmorNamesLike());
		}

		// 部門コード
		if (!Util.isNullOrEmpty(param.getDepartmentCode())) {
			sql.add(" AND hdr.SWK_DEP_CODE = ? ", param.getDepartmentCode());
		}

		// 部門コードLIKE
		if (!Util.isNullOrEmpty(param.getDepartmentCodeLike())) {
			sql.addLikeFront(" AND hdr.SWK_DEP_CODE ? ", param.getDepartmentCodeLike());
		}

		// 部門略称LIKE
		// if (!isForHeaderOnly && !Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
		if (!Util.isNullOrEmpty(param.getDepartmentNamesLike())) {
			sql.addNLikeAmbi(" AND dep.DEP_NAME_S ? ", param.getDepartmentNamesLike());
		}

		// 取引先コード
		if (!Util.isNullOrEmpty(param.getCustmorCode())) {
			sql.add(" AND hdr.SWK_TRI_CODE = ? ", param.getCustmorCode());
		}

		// 更新日付From
		if (param.getUpdateDateFrom() != null) {
			if (Util.isNullOrEmpty(initial)) {
				sql.add(" AND (   NVL(hdr.SWK_INP_DATE, hdr.INP_DATE) >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");

			} else if (Util.equals(HDR_TABLE.AP, initial)) {
				sql.add(" AND (   hdr.SWK_INP_DATE >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");

			} else {
				sql.add(" AND (   hdr.INP_DATE >= ? ", param.getUpdateDateFrom());
				sql.add("      OR hdr.UPD_DATE                        >= ? ", param.getUpdateDateFrom());
				sql.add("     ) ");
			}

		} else {
			// 登録日付FROM
			if (param.getEntryDateFrom() != null) {
				if (Util.isNullOrEmpty(initial)) {
					sql.add(" AND (   hdr.INP_DATE IS NULL ");
					sql.add("      OR hdr.INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");
					sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
					sql.add("      OR hdr.SWK_INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");

				} else if (Util.equals(HDR_TABLE.AP, initial)) {
					sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
					sql.add("      OR hdr.SWK_INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");

				} else {
					sql.add(" AND (   hdr.INP_DATE IS NULL ");
					sql.add("      OR hdr.INP_DATE >= ? ", param.getEntryDateFrom());
					sql.add("     ) ");
				}
			}
		}

		// 登録日付TO
		if (param.getEntryDateTo() != null) {

			if (Util.isNullOrEmpty(initial)) {
				sql.add(" AND (   hdr.INP_DATE IS NULL ");
				sql.add("      OR hdr.INP_DATE< ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");
				sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
				sql.add("      OR hdr.SWK_INP_DATE < ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");

			} else if (Util.equals(HDR_TABLE.AP, initial)) {
				sql.add(" AND (   hdr.SWK_INP_DATE IS NULL ");
				sql.add("      OR hdr.SWK_INP_DATE < ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");

			} else {
				sql.add(" AND (   hdr.INP_DATE IS NULL ");
				sql.add("      OR hdr.INP_DATE< ? ", DateUtil.addDay(param.getEntryDateTo(), 1));
				sql.add("     ) ");
			}
		}

		// 概算取消フラグ
		if (param.isEstimateCancelFlg()) {
			sql.add(" AND hdr.SWK_EST_CANCEL_DEN_NO IS NULL ");
		}

		return sql;
	}

	/**
	 * 最終抽出用のヘッダー追加JOIN文の取得
	 * 
	 * @return 最終抽出用のヘッダー追加JOIN文
	 */
	protected String getAddonFinallyHeaderJoinSql() {
		return "";
	}

	/**
	 * 最終取得時のJOIN文の取得
	 * 
	 * @return 最終取得時のJOIN文
	 */
	protected String getDetailJoinSql() {

		String companyCode = getCompanyCode();
		// 会社コード
		String companyCodeKMK_MST = isUseKMK_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeHKM_MST = isUseHKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeUKM_MST = isUseUKM_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeBMN_MST = isUseBMN_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeSZEI_MST = isUseSZEI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeTRI_MST = isUseTRI_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeEMP_MST = isUseEMP_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeCUR_MST = isUseCUR_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR1_MST = isUseKNR1_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR2_MST = isUseKNR2_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR3_MST = isUseKNR3_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR4_MST = isUseKNR4_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR5_MST = isUseKNR5_MST ? "hdr.KAI_CODE" : "?";
		String companyCodeKNR6_MST = isUseKNR6_MST ? "hdr.KAI_CODE" : "?";

		VCreator sql = new VCreator();
		sql.add(" LEFT OUTER JOIN KMK_MST dkmk ON " + companyCodeKMK_MST + " = dkmk.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dkmk.KMK_CODE ");
		sql.add(" LEFT OUTER JOIN HKM_MST dhkm ON " + companyCodeHKM_MST + " = dhkm.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dhkm.KMK_CODE ");
		sql.add("                             AND hdr.D_SWK_HKM_CODE = dhkm.HKM_CODE ");
		sql.add(" LEFT OUTER JOIN UKM_MST dukm ON " + companyCodeUKM_MST + " = dukm.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_KMK_CODE = dukm.KMK_CODE ");
		sql.add("                             AND hdr.D_SWK_HKM_CODE = dukm.HKM_CODE ");
		sql.add("                             AND hdr.D_SWK_UKM_CODE = dukm.UKM_CODE ");
		sql.add(" LEFT OUTER JOIN BMN_MST ddep ON " + companyCodeBMN_MST + " = ddep.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_DEP_CODE = ddep.DEP_CODE ");
		sql.add(" LEFT OUTER JOIN SZEI_MST dzei ON " + companyCodeSZEI_MST + " = dzei.KAI_CODE ", companyCode);
		sql.add("                              AND hdr.D_SWK_ZEI_CODE = dzei.ZEI_CODE ");
		sql.add(" LEFT OUTER JOIN TRI_MST dtri ON " + companyCodeTRI_MST + " = dtri.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_TRI_CODE = dtri.TRI_CODE ");
		sql.add(" LEFT OUTER JOIN EMP_MST demp ON " + companyCodeEMP_MST + " = demp.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_EMP_CODE = demp.EMP_CODE ");
		sql.add(" LEFT OUTER JOIN CUR_MST dcur ON " + companyCodeCUR_MST + " = dcur.KAI_CODE ", companyCode);
		sql.add("                             AND hdr.D_SWK_CUR_CODE = dcur.CUR_CODE ");
		sql.add(" LEFT OUTER JOIN KNR1_MST dknr1 ON " + companyCodeKNR1_MST + " = dknr1.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_1 = dknr1.KNR_CODE_1 ");
		sql.add(" LEFT OUTER JOIN KNR2_MST dknr2 ON " + companyCodeKNR2_MST + " = dknr2.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_2 = dknr2.KNR_CODE_2 ");
		sql.add(" LEFT OUTER JOIN KNR3_MST dknr3 ON " + companyCodeKNR3_MST + " = dknr3.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_3 = dknr3.KNR_CODE_3 ");
		sql.add(" LEFT OUTER JOIN KNR4_MST dknr4 ON " + companyCodeKNR4_MST + " = dknr4.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_4 = dknr4.KNR_CODE_4 ");
		sql.add(" LEFT OUTER JOIN KNR5_MST dknr5 ON " + companyCodeKNR5_MST + " = dknr5.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_5 = dknr5.KNR_CODE_5 ");
		sql.add(" LEFT OUTER JOIN KNR6_MST dknr6 ON " + companyCodeKNR6_MST + " = dknr6.KAI_CODE ", companyCode);
		sql.add("                               AND hdr.D_SWK_KNR_CODE_6 = dknr6.KNR_CODE_6 ");
		return sql.toSQL();
	}

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @param searchTable 検索対象
	 * @return 伝票リスト
	 */
	public List<Slip> findSlipByCondition(SlipCondition param, SearchTable searchTable) {

		Connection conn = null;

		List<Slip> list = new ArrayList<Slip>();

		try {
			conn = DBUtil.getConnection();

			String sql = getDefaultSqlIncludeDetails(conn, param, searchTable, true);
			Statement statement = DBUtil.getStatement(conn);

			ResultSet rs = DBUtil.select(statement, sql);

			String companyCode = null;
			String slipNo = null;
			int slipUpdateCount = -1;
			Slip slip = null;

			while (rs.next()) {

				// 会社コード、伝票番号、伝票修正回数のいずれかが変わったら新規伝票
				if (!Util.avoidNull(rs.getString("KAI_CODE")).equals(companyCode)
					|| !Util.avoidNull(rs.getString("SWK_DEN_NO")).equals(slipNo)
					|| rs.getInt("SWK_UPD_CNT") != slipUpdateCount) {

					if (companyCode != null) {
						list.add(slip);
					}

					SWK_HDR hdr = mappingHeader(rs);
					slip = new Slip(hdr);
				}

				// 明細
				slip.addDetail(mappingDetail(rs));

				// キー退避
				companyCode = Util.avoidNull(rs.getString("KAI_CODE"));
				slipNo = Util.avoidNull(rs.getString("SWK_DEN_NO"));
				slipUpdateCount = rs.getInt("SWK_UPD_CNT");
			}

			if (slip != null && slip.getDetails() != null && !slip.getDetails().isEmpty()) {
				list.add(slip);
			}

			DBUtil.close(rs);
			if (param.isSearchWorkFlow() && !Util.isNullOrEmpty(param.getSlipNo())) {
				sql = getWorkflowApproveSQL(conn, param);
				rs = DBUtil.select(statement, sql);
				// 承認権限保持分のみを取得
				List<SWK_SYO_CTL> ctlList = new ArrayList();
				while (rs.next()) {
					SWK_SYO_CTL ctl = new SWK_SYO_CTL();
					ctl.setKAI_CODE(rs.getString("KAI_CODE"));
					ctl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
					ctl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
					ctl.setSEQ(rs.getInt("SEQ"));
					ctl.setUPD_KBN(rs.getInt("UPD_KBN"));
					ctl.setAPRV_ROLE_GRP_CODE(rs.getString("APRV_ROLE_GRP_CODE"));
					ctl.setPREV_UPD_KBN(rs.getInt("PREV_UPD_KBN"));
					ctl.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
					ctl.setNEXT_APRV_ROLE_CODE(rs.getString("NEXT_APRV_ROLE_CODE"));
					ctl.setINP_DATE(rs.getDate("INP_DATE"));
					ctl.setPRG_ID(rs.getString("PRG_ID"));
					ctl.setUSR_ID(rs.getString("USR_ID"));

					ctl.setAprvRoleGroupName(rs.getString("APRV_ROLE_GRP_NAME"));
					ctl.setAprvRoleName(rs.getString("APRV_ROLE_NAME"));
					ctl.setNextAprvRoleName(rs.getString("NEXT_APRV_ROLE_NAME"));

					ctl.setAprvRoleSkippable(rs.getInt("APRV_SKIP_FLG") == 1);
					ctl.setNextAprvRoleSkippable(rs.getInt("NEXT_APRV_SKIP_FLG") == 1);

					ctlList.add(ctl);

				}
				DBUtil.close(rs);
				HDR: for (Slip s : list) {
					for (SWK_SYO_CTL ctl : ctlList) {
						if (Util.equals(ctl.getKAI_CODE(), s.getHeader().getKAI_CODE()) && Util.equals(ctl.getSWK_DEN_NO(), s.getHeader().getSWK_DEN_NO()) && DateUtil.equals(ctl.getSWK_DEN_DATE(), s.getHeader().getSWK_DEN_DATE())) {
							s.getHeader().setSyoCtl(ctl);
							// 追加して次のヘッダに
							continue HDR;
						}
					}
				}
			}
			DBUtil.close(statement);
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				// ワークフロー承認利用時
				setupSlipApproveGroup(list);
			}

		} catch (SlipNoDataException e) {
			return list;

		} catch (Exception e) {
			throw new TRuntimeException(e);

		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 伝票リスト
	 */
	public List<Slip> findSlipByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.Slip);
	}

	/**
	 * 条件によるパターン検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	public List<Slip> findSlipPatternByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.Pattern);
	}

	/**
	 * 条件による履歴検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	public List<Slip> findSlipHistoryByCondition(SlipCondition param) {
		return findSlipByCondition(param, SearchTable.History);
	}

	/**
	 * ヘッダーマッピング
	 * 
	 * @param rs
	 * @return ヘッダ
	 * @throws Exception
	 */
	protected SWK_HDR mappingHeader(ResultSet rs) throws Exception {

		SWK_HDR hdr = newHeader();
		hdr.setExistAttachment(rs.getInt("ATTACH_COUNT") != 0);
		hdr.setKAI_CODE(rs.getString("KAI_CODE"));
		hdr.setKAI_NAME(rs.getString("KAI_NAME"));
		hdr.setKAI_NAME_S(rs.getString("KAI_NAME_S"));
		hdr.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		hdr.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		hdr.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		hdr.setSWK_KMK_CODE(rs.getString("SWK_KMK_CODE"));
		hdr.setSWK_KMK_NAME(rs.getString("SWK_KMK_NAME"));
		hdr.setSWK_KMK_NAME_S(rs.getString("SWK_KMK_NAME_S"));
		hdr.setSWK_HKM_CODE(rs.getString("SWK_HKM_CODE"));
		hdr.setSWK_HKM_NAME(rs.getString("SWK_HKM_NAME"));
		hdr.setSWK_HKM_NAME_S(rs.getString("SWK_HKM_NAME_S"));
		hdr.setSWK_UKM_CODE(rs.getString("SWK_UKM_CODE"));
		hdr.setSWK_UKM_NAME(rs.getString("SWK_UKM_NAME"));
		hdr.setSWK_UKM_NAME_S(rs.getString("SWK_UKM_NAME_S"));
		hdr.setSWK_DEP_CODE(rs.getString("SWK_DEP_CODE"));
		hdr.setSWK_DEP_NAME(rs.getString("SWK_DEP_NAME"));
		hdr.setSWK_DEP_NAME_S(rs.getString("SWK_DEP_NAME_S"));
		hdr.setSWK_TRI_CODE(rs.getString("SWK_TRI_CODE"));
		hdr.setSWK_TRI_NAME(rs.getString("SWK_TRI_NAME"));
		hdr.setSWK_TRI_NAME_S(rs.getString("SWK_TRI_NAME_S"));
		hdr.setSWK_EMP_CODE(rs.getString("SWK_EMP_CODE"));
		hdr.setSWK_EMP_NAME(rs.getString("SWK_EMP_NAME"));
		hdr.setSWK_EMP_NAME_S(rs.getString("SWK_EMP_NAME_S"));
		hdr.setSWK_KIN(rs.getBigDecimal("SWK_KIN"));
		hdr.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		hdr.setSWK_UKE_DEP_CODE(rs.getString("SWK_UKE_DEP_CODE"));
		hdr.setSWK_UKE_DEP_NAME(rs.getString("SWK_UKE_DEP_NAME"));
		hdr.setSWK_UKE_DEP_NAME_S(rs.getString("SWK_UKE_DEP_NAME_S"));
		hdr.setSWK_TEK_CODE(rs.getString("SWK_TEK_CODE"));
		hdr.setSWK_TEK(rs.getString("SWK_TEK"));
		hdr.setSWK_BEFORE_DEN_NO(rs.getString("SWK_BEFORE_DEN_NO"));
		hdr.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		hdr.setSWK_SYO_EMP_CODE(rs.getString("SWK_SYO_EMP_CODE"));
		hdr.setSWK_SYO_EMP_NAME(rs.getString("SWK_SYO_EMP_NAME"));
		hdr.setSWK_SYO_EMP_NAME_S(rs.getString("SWK_SYO_EMP_NAME_S"));
		hdr.setSWK_SYO_DATE(rs.getTimestamp("SWK_SYO_DATE"));
		hdr.setSWK_IRAI_EMP_CODE(rs.getString("SWK_IRAI_EMP_CODE"));
		hdr.setSWK_IRAI_EMP_NAME(rs.getString("SWK_IRAI_EMP_NAME"));
		hdr.setSWK_IRAI_EMP_NAME_S(rs.getString("SWK_IRAI_EMP_NAME_S"));
		hdr.setSWK_IRAI_DEP_CODE(rs.getString("SWK_IRAI_DEP_CODE"));
		hdr.setSWK_IRAI_DEP_NAME(rs.getString("SWK_IRAI_DEP_NAME"));
		hdr.setSWK_IRAI_DEP_NAME_S(rs.getString("SWK_IRAI_DEP_NAME_S"));
		hdr.setSWK_IRAI_DATE(rs.getTimestamp("SWK_IRAI_DATE"));
		hdr.setSWK_SHR_KBN(rs.getInt("SWK_SHR_KBN"));
		hdr.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		hdr.setINP_DATE(rs.getTimestamp("INP_DATE"));
		hdr.setUPD_DATE(rs.getTimestamp("UPD_DATE"));
		hdr.setPRG_ID(rs.getString("PRG_ID"));
		hdr.setUSR_ID(rs.getString("USR_ID"));
		hdr.setSWK_CUR_CODE(rs.getString("SWK_CUR_CODE"));
		hdr.setSWK_CUR_DEC_KETA(rs.getInt("SWK_CUR_DEC_KETA"));
		hdr.setSWK_CUR_RATE(rs.getBigDecimal("SWK_CUR_RATE"));
		hdr.setSWK_IN_KIN(rs.getBigDecimal("SWK_IN_KIN"));
		hdr.setSWK_SYS_KBN(rs.getString("SWK_SYS_KBN"));
		hdr.setSWK_DEN_SYU(rs.getString("SWK_DEN_SYU"));
		hdr.setSWK_DEN_SYU_NAME(rs.getString("SWK_DEN_SYU_NAME"));
		hdr.setSWK_DEN_SYU_NAME_S(rs.getString("SWK_DEN_SYU_NAME_S"));
		hdr.setSWK_DEN_SYU_NAME_K(rs.getString("SWK_DEN_SYU_NAME_K"));
		hdr.setSWK_TUKE_KBN(rs.getInt("SWK_TUKE_KBN"));
		hdr.setSWK_UPD_CNT(rs.getInt("SWK_UPD_CNT"));
		hdr.setSWK_SIHA_KBN(rs.getString("SWK_SIHA_KBN"));
		hdr.setSWK_SIHA_DATE(rs.getDate("SWK_SIHA_DATE"));
		hdr.setSWK_HOH_CODE(rs.getString("SWK_HOH_CODE"));
		hdr.setSWK_HOH_NAME(rs.getString("SWK_HOH_NAME"));
		hdr.setSWK_HOH_NAI_CODE(rs.getString("SWK_HOH_NAI_CODE")); // 支払内部コード
		hdr.setSWK_HORYU_KBN(rs.getInt("SWK_HORYU_KBN"));
		hdr.setSWK_KARI_KIN(rs.getBigDecimal("SWK_KARI_KIN"));
		hdr.setSWK_KEIHI_KIN(rs.getBigDecimal("SWK_KEIHI_KIN"));
		hdr.setSWK_SIHA_KIN(rs.getBigDecimal("SWK_SIHA_KIN"));
		hdr.setSWK_KARI_DR_DEN_NO(rs.getString("SWK_KARI_DR_DEN_NO"));
		hdr.setSWK_KARI_CR_DEN_NO(rs.getString("SWK_KARI_CR_DEN_NO"));
		hdr.setSWK_KESAI_KBN(rs.getInt("SWK_KESAI_KBN"));
		hdr.setSWK_KARI_DEP_CODE(rs.getString("SWK_KARI_DEP_CODE"));
		hdr.setSWK_KARI_DEP_NAME(rs.getString("SWK_KARI_DEP_NAME"));
		hdr.setSWK_KARI_DEP_NAME_S(rs.getString("SWK_KARI_DEP_NAME_S"));
		hdr.setSWK_INP_DATE(rs.getTimestamp("SWK_INP_DATE"));
		hdr.setSWK_TJK_CODE(rs.getString("SWK_TJK_CODE"));
		hdr.setSWK_TJK_YKN_KBN(rs.getInt("TJK_YKN_KBN"));
		hdr.setSWK_TJK_YKN_NO(rs.getString("TJK_YKN_NO"));
		hdr.setSWK_TJK_YKN_KANA(rs.getString("TJK_YKN_KANA"));
		hdr.setSWK_TJK_GS_BNK_NAME(rs.getString("SWK_TJK_GS_BNK_NAME")); // 被仕向け・英文銀行
		hdr.setSWK_TJK_GS_STN_NAME(rs.getString("SWK_TJK_GS_STN_NAME")); // 被仕向け・英文支店
		hdr.setSWK_TJK_BNK_NAME_S(rs.getString("SWK_TJK_BNK_NAME_S"));
		hdr.setSWK_TJK_BNK_STN_NAME_S(rs.getString("SWK_TJK_BNK_STN_NAME_S"));
		hdr.setSWK_IN_KARI_KIN(rs.getBigDecimal("SWK_IN_KARI_KIN"));
		hdr.setSWK_IN_KEIHI_KIN(rs.getBigDecimal("SWK_IN_KEIHI_KIN"));
		hdr.setSWK_IN_SIHA_KIN(rs.getBigDecimal("SWK_IN_SIHA_KIN"));
		hdr.setSWK_INV_CUR_CODE(rs.getString("SWK_INV_CUR_CODE"));
		hdr.setSWK_INV_CUR_RATE(rs.getBigDecimal("SWK_INV_CUR_RATE"));
		hdr.setSWK_INV_KIN(rs.getBigDecimal("SWK_INV_KIN"));
		hdr.setSWK_CBK_CODE(rs.getString("SWK_CBK_CODE"));
		hdr.setSWK_CBK_NAME(rs.getString("SWK_CBK_NAME"));
		hdr.setSWK_CBK_STN_CODE(rs.getString("SWK_CBK_STN_CODE"));
		hdr.setSWK_CBK_YKN_KBN(rs.getInt("SWK_CBK_YKN_KBN"));
		hdr.setSWK_CBK_YKN_NO(rs.getString("SWK_CBK_YKN_NO"));
		hdr.setSWK_BNK_NAME_S(rs.getString("SWK_BNK_NAME_S"));
		hdr.setSWK_BNK_STN_NAME_S(rs.getString("SWK_BNK_STN_NAME_S"));
		hdr.setSWK_SSY_DATE(rs.getDate("SWK_SSY_DATE"));
		hdr.setSWK_UTK_NO(rs.getString("SWK_UTK_NO"));
		hdr.setSWK_KARI_CUR_CODE(rs.getString("SWK_KARI_CUR_CODE"));
		hdr.setSWK_KARI_CUR_RATE(rs.getBigDecimal("SWK_KARI_CUR_RATE"));
		hdr.setSWK_SEI_KBN(rs.getString("SWK_SEI_KBN"));
		hdr.setSWK_AR_DATE(rs.getDate("SWK_AR_DATE"));
		hdr.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		hdr.setKEY_CUR_CODE(rs.getString("KEY_CUR_CODE"));
		hdr.setKEY_CUR_DEC_KETA(rs.getInt("KEY_CUR_DEC_KETA"));
		hdr.setFUNC_CUR_CODE(rs.getString("FUNC_CUR_CODE"));
		hdr.setFUNC_CUR_DEC_KETA(rs.getInt("FUNC_CUR_DEC_KETA"));

		// --PE追加分
		hdr.setSWK_EST_CANCEL_DEN_NO(rs.getString("SWK_EST_CANCEL_DEN_NO"));// 概算取消伝票番号
		hdr.setSWK_BASE_EST_DEN_NO(rs.getString("SWK_BASE_EST_DEN_NO"));// 元概算伝票番号
		// 承認権限グループ
		hdr.setSWK_APRV_GRP_CODE(rs.getString("SWK_APRV_GRP_CODE"));

		if (isUseTag && rs.getInt("TAG_COUNT") != 0) {
			SlipTagManager tag = (SlipTagManager) getComponent(SlipTagManager.class);
			hdr.setSwkTags(tag.get(hdr.getKAI_CODE(), hdr.getSWK_DEN_NO()));
		} else {
			hdr.setSwkTags(new ArrayList<SWK_TAG>());
		}

		return hdr;

	}

	/**
	 * 明細マッピング
	 * 
	 * @param rs
	 * @return 明細
	 * @throws Exception
	 */
	protected SWK_DTL mappingDetail(ResultSet rs) throws Exception {

		SWK_DTL dtl = newDetail();
		dtl.setKAI_CODE(rs.getString("KAI_CODE"));
		dtl.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		dtl.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		dtl.setSWK_BOOK_NO(rs.getInt("D_SWK_BOOK_NO"));
		dtl.setSWK_GYO_NO(rs.getInt("SWK_GYO_NO"));
		dtl.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		dtl.setSWK_NENDO(rs.getInt("D_SWK_NENDO"));
		dtl.setSWK_TUKIDO(rs.getInt("D_SWK_TUKIDO"));
		dtl.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		dtl.setSWK_DC_KBN(rs.getInt("D_SWK_DC_KBN"));
		dtl.setSWK_KMK_CODE(rs.getString("D_SWK_KMK_CODE"));
		dtl.setSWK_HKM_CODE(rs.getString("D_SWK_HKM_CODE"));
		dtl.setSWK_UKM_CODE(rs.getString("D_SWK_UKM_CODE"));
		dtl.setSWK_DEP_CODE(rs.getString("D_SWK_DEP_CODE"));
		dtl.setSWK_ZEI_KBN(rs.getInt("D_SWK_ZEI_KBN"));
		dtl.setSWK_KIN(rs.getBigDecimal("D_SWK_KIN"));
		dtl.setSWK_ZEI_KIN(rs.getBigDecimal("D_SWK_ZEI_KIN"));
		dtl.setSWK_ZEI_CODE(rs.getString("D_SWK_ZEI_CODE"));
		dtl.setSWK_ZEI_RATE(rs.getBigDecimal("D_SWK_ZEI_RATE"));
		dtl.setSWK_GYO_TEK_CODE(rs.getString("D_SWK_GYO_TEK_CODE"));
		dtl.setSWK_GYO_TEK(rs.getString("D_SWK_GYO_TEK"));
		dtl.setSWK_TRI_CODE(rs.getString("D_SWK_TRI_CODE"));
		dtl.setSWK_EMP_CODE(rs.getString("D_SWK_EMP_CODE"));
		dtl.setSWK_KNR_CODE_1(rs.getString("D_SWK_KNR_CODE_1"));
		dtl.setSWK_KNR_CODE_2(rs.getString("D_SWK_KNR_CODE_2"));
		dtl.setSWK_KNR_CODE_3(rs.getString("D_SWK_KNR_CODE_3"));
		dtl.setSWK_KNR_CODE_4(rs.getString("D_SWK_KNR_CODE_4"));
		dtl.setSWK_KNR_CODE_5(rs.getString("D_SWK_KNR_CODE_5"));
		dtl.setSWK_KNR_CODE_6(rs.getString("D_SWK_KNR_CODE_6"));
		dtl.setSWK_HM_1(rs.getString("D_SWK_HM_1"));
		dtl.setSWK_HM_2(rs.getString("D_SWK_HM_2"));
		dtl.setSWK_HM_3(rs.getString("D_SWK_HM_3"));
		dtl.setSWK_AUTO_KBN(rs.getInt("D_SWK_AUTO_KBN"));
		dtl.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		dtl.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		dtl.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		dtl.setSWK_AT_KMK_CODE(rs.getString("D_SWK_AT_KMK_CODE"));
		dtl.setSWK_AT_HKM_CODE(rs.getString("D_SWK_AT_HKM_CODE"));
		dtl.setSWK_AT_UKM_CODE(rs.getString("D_SWK_AT_UKM_CODE"));
		dtl.setSWK_AT_DEP_CODE(rs.getString("D_SWK_AT_DEP_CODE"));
		dtl.setINP_DATE(rs.getDate("D_INP_DATE"));
		dtl.setUPD_DATE(rs.getDate("D_UPD_DATE"));
		dtl.setPRG_ID(rs.getString("D_PRG_ID"));
		dtl.setUSR_ID(rs.getString("D_USR_ID"));
		dtl.setSWK_K_KAI_CODE(rs.getString("D_SWK_K_KAI_CODE"));
		dtl.setSWK_CUR_CODE(rs.getString("D_SWK_CUR_CODE"));
		dtl.setSWK_CUR_RATE(rs.getBigDecimal("D_SWK_CUR_RATE"));
		dtl.setSWK_IN_KIN(rs.getBigDecimal("D_SWK_IN_KIN"));
		dtl.setSWK_TUKE_KBN(rs.getInt("D_SWK_TUKE_KBN"));
		dtl.setSWK_IN_ZEI_KIN(rs.getBigDecimal("D_SWK_IN_ZEI_KIN"));
		dtl.setSWK_KESI_KBN(rs.getInt("D_SWK_KESI_KBN"));
		dtl.setSWK_KESI_DATE(rs.getDate("D_SWK_KESI_DATE"));
		dtl.setSWK_KESI_DEN_NO(rs.getString("D_SWK_KESI_DEN_NO"));
		dtl.setSWK_SOUSAI_DATE(rs.getDate("D_SWK_SOUSAI_DATE"));
		dtl.setSWK_SOUSAI_DEN_NO(rs.getString("D_SWK_SOUSAI_DEN_NO"));

		// 自由区分
		if (isUseInvoice) {
			dtl.setSWK_FREE_KBN(rs.getInt("D_SWK_FREE_KBN"));
			dtl.setSWK_ORI_GYO_NO(rs.getInt("D_SWK_ORI_GYO_NO"));
		}

		// BS勘定消込機能がONの場合
		if (isUseBS && !Util.isNullOrEmpty(rs.getString("D_SWK_SOUSAI_GYO_NO"))) {
			dtl.setSWK_SOUSAI_GYO_NO(rs.getInt("D_SWK_SOUSAI_GYO_NO"));
		}

		// AP/AR消込機能がONの場合
		if (!isNotUseAP || !isNotUseAR) {
			dtl.setSWK_APAR_KESI_KBN(rs.getInt("SWK_APAR_KESI_KBN"));
			dtl.setSWK_APAR_DEN_NO(rs.getString("SWK_APAR_DEN_NO"));

			if (!Util.isNullOrEmpty(rs.getString("SWK_APAR_GYO_NO"))) {
				dtl.setSWK_APAR_GYO_NO(rs.getInt("SWK_APAR_GYO_NO"));
			}
		}

		dtl.setHAS_DATE(rs.getDate("D_HAS_DATE"));
		dtl.setDEN_SYU_CODE(rs.getString("SWK_DEN_SYU"));
		dtl.setSWK_K_KAI_NAME(rs.getString("KAI_NAME"));
		dtl.setSWK_K_KAI_NAME_S(rs.getString("KAI_NAME_S"));
		dtl.setSWK_EMP_NAME(rs.getString("D_EMP_NAME"));
		dtl.setSWK_EMP_NAME_S(rs.getString("D_EMP_NAME_S"));
		dtl.setSWK_DEP_NAME(rs.getString("D_DEP_NAME"));
		dtl.setSWK_DEP_NAME_S(rs.getString("D_DEP_NAME_S"));
		dtl.setSWK_KMK_NAME(rs.getString("D_KMK_NAME"));
		dtl.setSWK_KMK_NAME_S(rs.getString("D_KMK_NAME_S"));
		dtl.setSWK_HKM_NAME(rs.getString("D_HKM_NAME"));
		dtl.setSWK_HKM_NAME_S(rs.getString("D_HKM_NAME_S"));
		dtl.setSWK_UKM_NAME(rs.getString("D_UKM_NAME"));
		dtl.setSWK_UKM_NAME_S(rs.getString("D_UKM_NAME_S"));
		dtl.setCUR_DEC_KETA(rs.getInt("D_DEC_KETA"));
		dtl.setSWK_ZEI_NAME(rs.getString("D_ZEI_NAME"));
		dtl.setSWK_ZEI_NAME_S(rs.getString("D_ZEI_NAME_S"));
		dtl.setSWK_TRI_NAME(rs.getString("D_TRI_NAME"));
		dtl.setSWK_TRI_NAME_S(rs.getString("D_TRI_NAME_S"));
		dtl.setSWK_KNR_NAME_1(rs.getString("D_KNR_NAME_1"));
		dtl.setSWK_KNR_NAME_S_1(rs.getString("D_KNR_NAME_S_1"));
		dtl.setSWK_KNR_NAME_2(rs.getString("D_KNR_NAME_2"));
		dtl.setSWK_KNR_NAME_S_2(rs.getString("D_KNR_NAME_S_2"));
		dtl.setSWK_KNR_NAME_3(rs.getString("D_KNR_NAME_3"));
		dtl.setSWK_KNR_NAME_S_3(rs.getString("D_KNR_NAME_S_3"));
		dtl.setSWK_KNR_NAME_4(rs.getString("D_KNR_NAME_4"));
		dtl.setSWK_KNR_NAME_S_4(rs.getString("D_KNR_NAME_S_4"));
		dtl.setSWK_KNR_NAME_5(rs.getString("D_KNR_NAME_5"));
		dtl.setSWK_KNR_NAME_S_5(rs.getString("D_KNR_NAME_S_5"));
		dtl.setSWK_KNR_NAME_6(rs.getString("D_KNR_NAME_6"));
		dtl.setSWK_KNR_NAME_S_6(rs.getString("D_KNR_NAME_S_6"));
		dtl.setKEY_CUR_CODE(rs.getString("KEY_CUR_CODE"));
		dtl.setKEY_CUR_DEC_KETA(rs.getInt("KEY_CUR_DEC_KETA"));
		dtl.setFUNC_CUR_CODE(rs.getString("FUNC_CUR_CODE"));
		dtl.setFUNC_CUR_DEC_KETA(rs.getInt("FUNC_CUR_DEC_KETA"));

		// --PE追加分
		dtl.setSWK_EST_DEN_NO(rs.getString("D_EST_DEN_NO"));
		dtl.setSWK_STL_DEN_NO(rs.getString("D_STL_DEN_NO"));
		dtl.setSWK_MAE_DEN_NO(rs.getString("D_MAE_DEN_NO"));
		dtl.setSWK_MAE_GYO_NO(rs.getInt("D_MAE_GYO_NO"));

		// 発生日フラグ
		if (!Util.isNullOrEmpty(rs.getString("D_UKM_HAS_FLG"))) {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_UKM_HAS_FLG")));
		} else if (!Util.isNullOrEmpty(rs.getString("D_HKM_HAS_FLG"))) {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_HKM_HAS_FLG")));
		} else {
			dtl.setUseItemOccurDate(BooleanUtil.toBoolean(rs.getInt("D_KMK_HAS_FLG")));
		}

		return dtl;
	}

	/**
	 * ヘッダ生成
	 * 
	 * @return ヘッダ
	 */
	protected SWK_HDR newHeader() {
		return new SWK_HDR();
	}

	/**
	 * 明細生成
	 * 
	 * @return 明細
	 */
	protected SWK_DTL newDetail() {
		return new SWK_DTL();
	}

	/**
	 * ヘッダー追加項目の取得<br>
	 * <b>先頭「,」必要</b>
	 * 
	 * @param tableName
	 * @return ヘッダー追加項目
	 */
	@SuppressWarnings("unused")
	protected String getAddonHeaderColumn(String tableName) {
		return "";
	}

	/**
	 * 最終抽出用のヘッダー追加項目の取得<br>
	 * <b>先頭「,」必要</b>
	 * 
	 * @return 最終抽出用のヘッダー追加項目
	 */
	protected String getAddonFinallyHeaderColumn() {
		return "";
	}

	/**
	 * 明細追加項目の取得<br>
	 * <b>先頭「,」必要</b>
	 * 
	 * @return 明細追加項目
	 */
	protected String getAddonDetailColumn() {
		return "";
	}

	/**
	 * 最終抽出用の明細追加項目の取得<br>
	 * <b>先頭「,」必要</b>
	 * 
	 * @return 最終抽出用の明細追加項目
	 */
	protected String getAddonFinallyDetailColumn() {
		return "";
	}

	/**
	 * 追加伝票検索条件SQLの取得
	 * 
	 * @return 追加伝票検索条件SQL
	 */
	protected String getAddonSearchSQL() {
		return "";
	}

	/**
	 * 伝票検索条件の取得
	 * 
	 * @return param 伝票検索条件
	 */
	public SlipCondition getParam() {
		return param_;
	}

	/**
	 * 伝票検索条件の設定
	 * 
	 * @param param 伝票検索条件
	 */
	public void setParam(SlipCondition param) {
		this.param_ = param;
	}

	/**
	 * 検索対象テーブルの取得
	 * 
	 * @return searchTable 検索対象テーブル
	 */
	public SearchTable getSearchTable() {
		return searchTable_;
	}

	/**
	 * 検索対象テーブルの設定
	 * 
	 * @param searchTable 検索対象テーブル
	 */
	public void setSearchTable(SearchTable searchTable) {
		this.searchTable_ = searchTable;
	}

	/**
	 * 明細抽出するかどうかの取得
	 * 
	 * @return isDetail 明細抽出するかどうか
	 */
	public boolean isDetail() {
		return isDetail_;
	}

	/**
	 * 明細抽出するかどうかの設定
	 * 
	 * @param isDetail 明細抽出するかどうか
	 */
	public void setDetail(boolean isDetail) {
		this.isDetail_ = isDetail;
	}

	/**
	 * 検索対象データなしException
	 */
	protected class SlipNoDataException extends TException {
		// 識別のみ
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

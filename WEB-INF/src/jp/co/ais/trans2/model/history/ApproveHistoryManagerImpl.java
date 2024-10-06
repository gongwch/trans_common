package jp.co.ais.trans2.model.history;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.aprvrole.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 承認履歴消込マネージャ実装
 */
public class ApproveHistoryManagerImpl extends TModel implements ApproveHistoryManager {

	/**
	 * 承認履歴データの取得
	 * 
	 * @param condition 条件
	 * @return List データ
	 * @throws TException 取得失敗
	 */
	public List<ApproveHistory> get(ApproveHistoryCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<ApproveHistory> list = new ArrayList<ApproveHistory>();
		try {

			SQLCreator sql = new SQLCreator();
			sql.add("");
			sql.add(" SELECT his.* ");
			sql.add("       ,emp.EMP_NAME ");
			sql.add("       ,emp.EMP_NAME_S ");
			sql.add(" FROM   SWK_SYO_RRK his ");
			sql.add(" LEFT   JOIN EMP_MST emp ON his.KAI_CODE = emp.KAI_CODE ");
			sql.add("                        AND his.EMP_CODE = emp.EMP_CODE ");
			sql.add(" WHERE  his.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード
			sql.add(" AND    his.SWK_DEN_NO = ? ", condition.getSlipNo()); // 伝票番号

			sql.add(" ORDER BY his.INP_DATE DESC ");

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
	 * 承認コントロールの取得
	 * 
	 * @param condition 条件
	 * @return List データ
	 * @throws TException 取得失敗
	 */
	public List<ApproveHistory> getCtl(ApproveHistoryCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<ApproveHistory> list = new ArrayList<ApproveHistory>();
		try {

			SQLCreator sql = new SQLCreator();
			sql.add("");
			sql.add(" SELECT ");
			sql.add("        KAI_CODE ");
			sql.add("       ,SWK_DEN_DATE ");
			sql.add("       ,SWK_DEN_NO ");
			sql.add("       ,UPD_KBN ");
			sql.add("       ,PREV_UPD_KBN ");
			sql.add("       ,APRV_ROLE_CODE ");
			sql.add("       ,NEXT_APRV_ROLE_CODE ");
			sql.add(" FROM   SWK_SYO_CTL ");
			sql.add(" WHERE (KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO, SEQ) IN ( ");
			sql.add("     SELECT KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO, MAX(SEQ) ");
			sql.add("     FROM   SWK_SYO_CTL ");
			sql.add("     WHERE  KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード
			sql.add("     AND    SWK_DEN_NO = ? ", condition.getSlipNo()); // 伝票番号
			sql.add("     AND    NEXT_APRV_ROLE_CODE = ? ", condition.getAprvRoleCode()); // 承認ロールコード

			// 現場否認、経理否認以外
			List<Integer> slipStateList = new ArrayList<Integer>();
			slipStateList.add(SlipState.FIELD_DENY.value);
			slipStateList.add(SlipState.DENY.value);
			sql.add("     AND    UPD_KBN NOT IN ").addIntInStatement(slipStateList);
			sql.add("     GROUP BY KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO ) ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingCtl(rs));
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
	 * @return 承認履歴の承認履歴
	 * @throws Exception
	 */
	protected ApproveHistory mapping(ResultSet rs) throws Exception {

		ApproveHistory bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		bean.setEMP_CODE(rs.getString("EMP_CODE"));
		bean.setEMP_NAME(rs.getString("EMP_NAME"));
		bean.setEMP_NAME_S(rs.getString("EMP_NAME_S"));
		bean.setSYO_FLG(rs.getInt("SYO_FLG"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));

		return bean;
	}

	/**
	 * マッピング
	 * 
	 * @param rs
	 * @return 承認履歴の承認履歴
	 * @throws Exception
	 */
	protected ApproveHistory mappingCtl(ResultSet rs) throws Exception {

		ApproveHistory bean = createEntity();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		bean.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bean.setSWK_UPD_KBN(rs.getInt("UPD_KBN"));
		bean.setPREV_SWK_UPD_KBN(rs.getInt("PREV_UPD_KBN"));
		bean.setAPRV_ROLE_CODE(rs.getString("APRV_ROLE_CODE"));
		bean.setNEXT_APRV_ROLE_CODE(rs.getString("NEXT_APRV_ROLE_CODE"));

		return bean;
	}

	/**
	 * @return 承認履歴
	 */
	protected ApproveHistory createEntity() {
		return new ApproveHistory();
	}

	/**
	 * 承認履歴情報の登録
	 * 
	 * @param bean 承認履歴情報
	 * @throws TException
	 */
	public void entry(ApproveHistory bean) throws TException {

		Connection conn = null;

		try {
			// 承認履歴消込の登録
			conn = DBUtil.getConnection();

			SQLCreator sql = new SQLCreator();
			sql.add("");
			sql.add(" INSERT INTO SWK_SYO_RRK ");
			sql.add(" ( ");
			sql.add("    KAI_CODE ");
			sql.add("   ,SWK_DEN_DATE ");
			sql.add("   ,SWK_DEN_NO ");
			sql.add("   ,SWK_UPD_KBN ");
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				sql.add("   ,NEXT_APRV_ROLE_CODE ");
			}
			sql.add("   ,EMP_CODE ");
			sql.add("   ,SYO_FLG ");
			sql.add("   ,INP_DATE ");
			sql.add("   ,PRG_ID ");
			sql.add("   ,USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("    ? ", bean.getKAI_CODE());
			sql.add("   ,? ", bean.getSWK_DEN_DATE());
			sql.add("   ,? ", bean.getSWK_DEN_NO());
			sql.add("   ,? ", bean.getSWK_UPD_KBN());
			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				sql.add("   ,? ", bean.getNEXT_APRV_ROLE_CODE());
			}
			sql.add("   ,? ", bean.getEMP_CODE());
			sql.add("   ,? ", bean.getSYO_FLG());
			sql.adt("   ,? ", bean.getINP_DATE());
			sql.add("   ,? ", getProgramCode());
			sql.add("   ,? ", getUserCode());
			sql.add("   ) ");

			DBUtil.execute(conn, sql);

			if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
				entryControl(bean, conn);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * ワークフロー承認用コントロールを登録
	 * 
	 * @param bean
	 * @param conn
	 * @throws TException
	 * @throws SQLException
	 */
	protected void entryControl(ApproveHistory bean, Connection conn) throws TException, SQLException {
		SQLCreator sql;
		sql = new SQLCreator();
		sql.add("SELECT * FROM SWK_SYO_CTL syo");
		sql.add(" WHERE syo.KAI_CODE   = ?", bean.getKAI_CODE());
		sql.add(" AND syo.SWK_DEN_DATE = ?", bean.getSWK_DEN_DATE());
		sql.add(" AND syo.SWK_DEN_NO   = ?", bean.getSWK_DEN_NO());
		sql.add(" AND NOT EXISTS (SELECT 1 ");
		sql.add("                 FROM swk_syo_ctl ");
		sql.add("                 WHERE KAI_CODE = syo.KAI_CODE");
		sql.add("                 AND SWK_DEN_DATE = syo.SWK_DEN_DATE");
		sql.add("                 AND SWK_DEN_NO = syo.SWK_DEN_NO");
		sql.add("                 AND SEQ > syo.SEQ");
		sql.add("                )");

		Statement state = DBUtil.getStatement(conn);
		ResultSet rs = DBUtil.select(state, sql);
		int seq = 0;
		while (rs.next()) {
			seq = rs.getInt("SEQ");
			break;
		}
		seq++;
		DBUtil.close(rs);
		DBUtil.close(state);

		// 最新の処理状態を登録
		sql = new SQLCreator();
		sql.add("");
		sql.add(" INSERT INTO SWK_SYO_CTL ");
		sql.add(" ( ");
		sql.add("    KAI_CODE ");
		sql.add("   ,SWK_DEN_DATE ");
		sql.add("   ,SWK_DEN_NO ");
		sql.add("   ,SEQ ");
		sql.add("   ,UPD_KBN ");
		sql.add("   ,PREV_UPD_KBN ");
		sql.add("   ,APRV_ROLE_CODE ");
		sql.add("   ,NEXT_APRV_ROLE_CODE ");
		sql.add("   ,INP_DATE ");
		sql.add("   ,PRG_ID ");
		sql.add("   ,USR_ID ");
		sql.add(" ) VALUES ( ");
		sql.add("    ? ", bean.getKAI_CODE());
		sql.add("   ,? ", bean.getSWK_DEN_DATE());
		sql.add("   ,? ", bean.getSWK_DEN_NO());
		sql.add("   ,? ", seq);
		sql.add("   ,? ", bean.getSWK_UPD_KBN());
		sql.add("   ,? ", bean.getPREV_SWK_UPD_KBN());
		sql.add("   ,? ", bean.getAPRV_ROLE_CODE());
		sql.add("   ,? ", bean.getNEXT_APRV_ROLE_CODE());
		sql.addYMDHMSSSS("   ,? ", bean.getINP_DATE());
		sql.add("   ,? ", getProgramCode());
		sql.add("   ,? ", getUserCode());
		sql.add("   ) ");

		DBUtil.execute(conn, sql);

	}

	/**
	 * 承認履歴の作成
	 * 
	 * @param employee
	 * @param slip
	 * @param flag 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)
	 * @return 承認履歴
	 */
	public ApproveHistory getApproveHistory(Slip slip, Employee employee, int flag) {
		ApproveHistory bean = createEntity();

		bean.setKAI_CODE(slip.getCompanyCode());
		bean.setSWK_DEN_DATE(slip.getSlipDate());
		bean.setSWK_DEN_NO(slip.getSlipNo());
		bean.setSWK_UPD_KBN(slip.getHeader().getSWK_UPD_KBN());
		bean.setEMP_CODE(employee.getCode());
		bean.setSYO_FLG(flag);
		bean.setINP_DATE(getNow());

		// FIXME
		if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
			SWK_HDR h = slip.getHeader();
			if (Util.isNullOrEmpty(h.getSWK_APRV_GRP_CODE())) {
				return bean;
			}
			//
			try {
				AprvRoleGroupManager mana = get(AprvRoleGroupManager.class);
				AprvRoleGroupSearchCondition con = new AprvRoleGroupSearchCondition();
				con.setCompanyCode(getCompanyCode());
				con.setCode(h.getSWK_APRV_GRP_CODE());
				List<AprvRoleGroup> grpList = mana.get(con);
				if (grpList == null) {
					return bean;
				}
				AprvRoleGroup grp = grpList.get(0);
				bean.setNEXT_APRV_ROLE_CODE(grp.getFirstRoleCode());
			} catch (Exception e) {
				return bean;
			}
		}

		return bean;
	}

	/**
	 * 承認履歴の作成
	 * 
	 * @param employee
	 * @param dtl
	 * @param flag 承認フラグ(0:承認、1:キャンセル、2:否認、3:登録、4:修正、5:更新)
	 * @return 承認履歴
	 */
	public ApproveHistory getApproveHistory(SlipDen dtl, Employee employee, int flag) {
		ApproveHistory bean = createEntity();
		bean.setKAI_CODE(dtl.getKAI_CODE());
		bean.setSWK_DEN_DATE(dtl.getSWK_DEN_DATE());
		bean.setSWK_DEN_NO(dtl.getSWK_DEN_NO());
		bean.setSWK_UPD_KBN(dtl.getSWK_UPD_KBN());
		bean.setEMP_CODE(employee.getCode());
		bean.setSYO_FLG(flag);
		bean.setINP_DATE(getNow());

		if (getCompany().getAccountConfig().isUseWorkflowApprove()) {
			bean.setPREV_SWK_UPD_KBN(dtl.getSWK_BEFORE_UPD_KBN());
			bean.setNEXT_APRV_ROLE_CODE(dtl.getNEXT_APRV_ROLE_CODE());
			bean.setAPRV_ROLE_CODE(dtl.getAPRV_ROLE_CODE());
		}

		return bean;
	}

}
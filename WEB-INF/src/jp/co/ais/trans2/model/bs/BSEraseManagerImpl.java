package jp.co.ais.trans2.model.bs;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * B/S勘定消込マネージャ実装
 */
public class BSEraseManagerImpl extends TModel implements BSEraseManager {

	/** true:BS勘定は計上会社コードの入力値を利用<Server> */
	public static boolean isBsUseKCompany = ServerConfig.isFlagOn("trans.slip.bs.use.kcompany");

	/**
	 * 排他を掛ける
	 * 
	 * @param condition 対象BS勘定(条件)
	 * @throws TException 排他失敗
	 */
	public void lock(BSEraseCondition condition) throws TException {

		if (isOtherBsErased(condition)) {
			throw new TException(getMessage("I00115"));// 既に他ユーザによって消し込まれたデータが存在します。再度検索を行ってください。
		}

		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);

		// B：BS勘定消込
		codeExManager.exclude("B", condition.slipNoList, condition.rowNoList);
	}

	/**
	 * 他ユーザに先に消し込まれていないかチェック(BS)
	 * 
	 * @param condition 対象BS勘定(条件)
	 * @return true:消し込まれている
	 * @throws TException
	 */
	protected boolean isOtherBsErased(BSEraseCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// 排他コントロールマスタをロック
			try {
				DBUtil.execute(conn, "LOCK TABLE SWK_DTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TException("W01133");// 只今混み合っております。しばらくお待ちください。
			}

			AccountConfig conf = getCompany().getAccountConfig();

			VCreator sql = new VCreator();
			sql.add(" SELECT COUNT(*) ");
			sql.add(" FROM   SWK_DTL dtl ");
			sql.add(" WHERE  1 = 1");
			if (!isBsUseKCompany || !conf.isUseGroupAccount()) {
				// 計上会社利用してない、またはグループ会計ではない場合、会社コード条件付け
				sql.add(" AND  dtl.KAI_CODE  = ? ", getCompanyCode());
			}
			sql.add(" AND    dtl.SWK_UPD_KBN <> 4 "); // 更新区分が更新以外
			sql.add(" AND    dtl.SWK_BOOK_NO = 1  "); // 帳簿区分
			sql.add(" AND    dtl.SWK_ADJ_KBN IN (0, 1) "); // IFRS区分
			if (!Util.isNullOrEmpty(condition.getExcludeSlipNo())) {
				sql.add("    AND    dtl.SWK_DEN_NO <> ? ", condition.getExcludeSlipNo()); // 現状編集中伝票番号
			}
			sql.add(" AND   (1 = 2 ");
			for (int i = 0; i < condition.getSlipNoList().size(); i++) {
				sql.add("    OR  (dtl.SWK_SOUSAI_DEN_NO = ?  ", condition.getSlipNoList().get(i));
				sql.add("    AND  dtl.SWK_SOUSAI_GYO_NO = ?) ", condition.getRowNoList().get(i)); // 相殺行番号
			}
			sql.add("       )");

			int count = DBUtil.selectOneInt(conn, sql.toSQL());
			return count != 0;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 排他解除
	 * 
	 * @param condition 対象BS勘定(条件)
	 * @throws TException 排他失敗
	 */
	public void unlock(BSEraseCondition condition) throws TException {

		// B：BS勘定消込
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("B", condition.slipNoList, condition.rowNoList);
	}

	/**
	 * BS勘定消込データ取得
	 * 
	 * @param condition 条件
	 * @return BS勘定消込データ
	 */
	public List<SWK_DTL> get(BSEraseCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		AccountConfig conf = getCompany().getAccountConfig();

		List<SWK_DTL> list = new ArrayList<SWK_DTL>();
		try {

			VCreator sql = new VCreator();

			sql.add(" SELECT dtl.*,");
			sql.add("        env.KAI_NAME,");
			sql.add("        env.KAI_NAME_S,");
			sql.add("        kmk.KMK_NAME,");
			sql.add("        kmk.KMK_NAME_S,");
			sql.add("        hkm.HKM_NAME,");
			sql.add("        hkm.HKM_NAME_S,");
			sql.add("        ukm.UKM_NAME,");
			sql.add("        ukm.UKM_NAME_S,");
			sql.add("        tri.TRI_NAME,");
			sql.add("        tri.TRI_NAME_S,");
			sql.add("        dep.DEP_NAME,");
			sql.add("        dep.DEP_NAME_S,");
			sql.add("        emp.EMP_NAME,");
			sql.add("        emp.EMP_NAME_S,");
			sql.add("        knr1.KNR_NAME_1,");
			sql.add("        knr1.KNR_NAME_S_1,");
			sql.add("        knr2.KNR_NAME_2,");
			sql.add("        knr2.KNR_NAME_S_2,");
			sql.add("        knr3.KNR_NAME_3,");
			sql.add("        knr3.KNR_NAME_S_3,");
			sql.add("        knr4.KNR_NAME_4,");
			sql.add("        knr4.KNR_NAME_S_4,");
			sql.add("        knr5.KNR_NAME_5,");
			sql.add("        knr5.KNR_NAME_S_5,");
			sql.add("        knr6.KNR_NAME_6,");
			sql.add("        knr6.KNR_NAME_S_6,");
			sql.add("        cur.DEC_KETA");
			sql.add(" FROM   SWK_DTL dtl");
			sql.add(" INNER  JOIN ENV_MST env ON dtl.KAI_CODE = env.KAI_CODE");
			sql.add(" INNER  JOIN KMK_MST kmk ON dtl.KAI_CODE = kmk.KAI_CODE");
			sql.add("                        AND dtl.SWK_KMK_CODE = kmk.KMK_CODE");
			sql.add(" LEFT   JOIN HKM_MST hkm ON dtl.KAI_CODE = hkm.KAI_CODE");
			sql.add("                        AND dtl.SWK_KMK_CODE = hkm.KMK_CODE");
			sql.add("                        AND dtl.SWK_HKM_CODE = hkm.HKM_CODE");
			sql.add(" LEFT   JOIN UKM_MST ukm ON dtl.KAI_CODE = ukm.KAI_CODE");
			sql.add("                        AND dtl.SWK_KMK_CODE = ukm.KMK_CODE");
			sql.add("                        AND dtl.SWK_HKM_CODE = ukm.HKM_CODE");
			sql.add("                        AND dtl.SWK_UKM_CODE = ukm.UKM_CODE");
			sql.add(" LEFT   JOIN TRI_MST tri ON dtl.KAI_CODE = tri.KAI_CODE");
			sql.add("                        AND dtl.SWK_TRI_CODE = tri.TRI_CODE");
			sql.add(" LEFT   JOIN BMN_MST dep ON dtl.KAI_CODE = dep.KAI_CODE");
			sql.add("                        AND dtl.SWK_DEP_CODE = dep.DEP_CODE");
			sql.add(" LEFT   JOIN EMP_MST emp ON dtl.KAI_CODE = emp.KAI_CODE");
			sql.add("                        AND dtl.SWK_EMP_CODE = emp.EMP_CODE");
			sql.add(" LEFT   JOIN KNR1_MST knr1 ON dtl.KAI_CODE = knr1.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_1 = knr1.KNR_CODE_1");
			sql.add(" LEFT   JOIN KNR2_MST knr2 ON dtl.KAI_CODE = knr2.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_2 = knr2.KNR_CODE_2");
			sql.add(" LEFT   JOIN KNR3_MST knr3 ON dtl.KAI_CODE = knr3.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_3 = knr3.KNR_CODE_3");
			sql.add(" LEFT   JOIN KNR4_MST knr4 ON dtl.KAI_CODE = knr4.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_4 = knr4.KNR_CODE_4");
			sql.add(" LEFT   JOIN KNR5_MST knr5 ON dtl.KAI_CODE = knr5.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_5 = knr5.KNR_CODE_5");
			sql.add(" LEFT   JOIN KNR6_MST knr6 ON dtl.KAI_CODE = knr6.KAI_CODE");
			sql.add("                          AND dtl.SWK_KNR_CODE_6 = knr6.KNR_CODE_6");
			sql.add(" LEFT   JOIN CUR_MST cur ON dtl.KAI_CODE = cur.KAI_CODE");
			sql.add("                        AND dtl.SWK_CUR_CODE = cur.CUR_CODE");

			sql.add(" WHERE  1 = 1 ");
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add(" AND    dtl.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード
			}

			sql.add(" AND    dtl.SWK_UPD_KBN = 4"); // 更新区分
			sql.add(" AND    dtl.SWK_BOOK_NO = 1 "); // 帳簿区分
			sql.add(" AND    dtl.SWK_ADJ_KBN IN (0, 1) "); // IFRS区分

			// 伝票修正/複写ではない場合のみ、条件追加
			if (!condition.isForSlipModify()) {
				sql.add(" AND    kmk.KESI_KBN = 1"); // BS消込対象
				sql.add(" AND    dtl.SWK_DATA_KBN NOT IN ? ", getNotInDataDivs()); // 見積、見積取消は対象外
				sql.add(" AND   (dtl.SWK_DEN_NO, dtl.SWK_GYO_NO) "); // 行単位排他中は対象外
				sql.add("       NOT IN (SELECT haita.TRI_CODE,");
				sql.add("                       haita.GYO_NO");
				sql.add("                FROM   HAITA_CTL haita");
				sql.add("                WHERE  1 = 1 ");
				if (!isBsUseKCompany || !conf.isUseGroupAccount()) { // グループ会計ではない場合、会社コード条件付け
					sql.add("                AND    haita.KAI_CODE = dtl.KAI_CODE");
				}
				sql.add("                AND    haita.SHORI_KBN = 'B')");
				sql.add(" AND   (NVL(dtl.SWK_KESI_KBN, 0) = ? ", SWK_DTL.KESI_KBN.NOMAL); // 消込区分は0:通常
				if (!Util.isNullOrEmpty(condition.getExcludeSlipNo())) {
					sql.add(" OR    dtl.SWK_SOUSAI_DEN_NO = ? ", condition.getExcludeSlipNo());
				}
				sql.add("       ) ");

				// 条件追加
				sql.add(" AND (dtl.SWK_AUTO_KBN = 0 OR NOT EXISTS ( ");
				sql.add(" SELECT 1 FROM AP_ZAN apzan ");
				sql.add(" WHERE dtl.KAI_CODE               = apzan.KAI_CODE ");
				sql.add(" AND   dtl.SWK_DEN_DATE           = apzan.ZAN_DEN_DATE ");
				sql.add(" AND   dtl.SWK_DEN_NO             = apzan.ZAN_DEN_NO ");
				sql.add(" AND   dtl.SWK_KMK_CODE           = apzan.ZAN_KMK_CODE ");
				sql.add(" AND   NVL(dtl.SWK_HKM_CODE, ' ') = NVL(apzan.ZAN_HKM_CODE, ' ') ");
				sql.add(" AND   NVL(dtl.SWK_UKM_CODE, ' ') = NVL(apzan.ZAN_UKM_CODE, ' ') ");
				sql.add(" ) ");
				sql.add(" ) ");
				sql.add(" AND (dtl.SWK_AUTO_KBN = 0 OR NOT EXISTS ( ");
				sql.add(" SELECT 1 FROM AR_ZAN arzan ");
				sql.add(" WHERE dtl.KAI_CODE               = arzan.KAI_CODE ");
				sql.add(" AND	dtl.SWK_DEN_DATE           = arzan.ZAN_SEI_DEN_DATE ");
				sql.add(" AND	dtl.SWK_DEN_NO             = arzan.ZAN_SEI_DEN_NO ");
				sql.add(" AND	dtl.SWK_KMK_CODE           = arzan.ZAN_KMK_CODE ");
				sql.add(" AND	NVL(dtl.SWK_HKM_CODE, ' ') = NVL(arzan.ZAN_HKM_CODE, ' ') ");
				sql.add(" AND	NVL(dtl.SWK_UKM_CODE, ' ') = NVL(arzan.ZAN_UKM_CODE, ' ') ");
				sql.add(" ) ");
				sql.add(" ) ");
				sql.add(" AND NOT EXISTS ( ");
				sql.add(" SELECT 1 FROM AP_ZAN apkesizan ");
				sql.add(" WHERE dtl.KAI_CODE   = apkesizan.KAI_CODE ");
				sql.add(" AND   dtl.SWK_DEN_NO = apkesizan.ZAN_SIHA_DEN_NO ");
				sql.add(" AND   dtl.SWK_GYO_NO = CASE WHEN NVL(apkesizan.ZAN_SIHA_GYO_NO,0) = 0 THEN dtl.SWK_GYO_NO ELSE apkesizan.ZAN_SIHA_GYO_NO END ");
				sql.add(" ) ");
				sql.add(" AND NOT EXISTS ( ");
				sql.add(" SELECT 1 FROM AR_ZAN arkesizan ");
				sql.add(" WHERE dtl.KAI_CODE   = arkesizan.KAI_CODE ");
				sql.add(" AND   dtl.SWK_DEN_NO = arkesizan.ZAN_KESI_DEN_NO ");
				sql.add(" AND   dtl.SWK_GYO_NO = CASE WHEN NVL(arkesizan.ZAN_KESI_GYO_NO,0) = 0 THEN dtl.SWK_GYO_NO ELSE arkesizan.ZAN_KESI_GYO_NO END) ");

				// 開示セキュリティ
				String userRoleCode = getUser().getUserRole() != null ? getUser().getUserRole().getCode() : null;

				if (!Util.isNullOrEmpty(userRoleCode)) {
					sql.add("  AND (NOT EXISTS ( ");
					sql.add("          SELECT 1  ");
					sql.add("          FROM ROLE_KMK_KJL_MST kmkkjl  ");
					sql.add("          WHERE kmkkjl.KAI_CODE = dtl.KAI_CODE ");
					sql.add("          AND   kmkkjl.KMK_CODE = dtl.SWK_KMK_CODE  ");
					sql.add("          AND   kmkkjl.ROLE_CODE = ? ", userRoleCode);
					sql.add("          )  ");
					sql.add("      OR EXISTS (  ");
					sql.add("          SELECT 1  ");
					sql.add("          FROM ROLE_KMK_KJL_MST kmkkjl2  ");
					sql.add("          WHERE kmkkjl2.KAI_CODE = dtl.KAI_CODE ");
					sql.add("          AND   kmkkjl2.KMK_CODE = dtl.SWK_KMK_CODE  ");
					sql.add("          AND   kmkkjl2.ROLE_CODE = ? ", userRoleCode);
					sql.add("          AND   kmkkjl2.KMK_BMN_KBN = 0  ");
					sql.add("          )  ");
					sql.add("      OR (EXISTS (  ");
					sql.add("          SELECT 1  ");
					sql.add("          FROM ROLE_KMK_KJL_MST kmkkjl3  ");
					sql.add("          WHERE kmkkjl3.KAI_CODE = dtl.KAI_CODE ");
					sql.add("          AND   kmkkjl3.KMK_CODE = dtl.SWK_KMK_CODE  ");
					sql.add("          AND   kmkkjl3.ROLE_CODE = ? ", userRoleCode);
					sql.add("          AND   kmkkjl3.KMK_BMN_KBN = 1  ");
					sql.add("          )  ");
					sql.add("      AND EXISTS (  ");
					sql.add("          SELECT 1  ");
					sql.add("          FROM ROLE_BMN_KJL_MST bmnkjl  ");
					sql.add("          WHERE bmnkjl.KAI_CODE = dtl.KAI_CODE ");
					sql.add("          AND   bmnkjl.BMN_CODE = dtl.SWK_DEP_CODE  ");
					sql.add("          AND   bmnkjl.ROLE_CODE = ? ", userRoleCode);
					sql.add("          AND   bmnkjl.BMN_KBN = 1  ");
					sql.add("  )))  ");
				}
			}

			// 伝票日付
			if (!Util.isNullOrEmpty(condition.getSlipDate())) {
				sql.add("   AND dtl.SWK_DEN_DATE <= ?", condition.getSlipDate());
			}

			// 科目コード
			if (!Util.isNullOrEmpty(condition.getItemCode())) {
				sql.add("   AND dtl.SWK_KMK_CODE = ?", condition.getItemCode());
			}

			// 補助科目コード
			if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
				sql.add("   AND dtl.SWK_HKM_CODE = ?", condition.getSubItemCode());
			}

			// 内訳科目コード
			if (!Util.isNullOrEmpty(condition.getDetailItemCode())) {
				sql.add("   AND dtl.SWK_UKM_CODE = ?", condition.getDetailItemCode());
			}

			// 取引先コード
			if (!Util.isNullOrEmpty(condition.getCustomerCode())) {
				sql.add("   AND dtl.SWK_TRI_CODE = ?", condition.getCustomerCode());
			}

			// 部門コード
			if (!Util.isNullOrEmpty(condition.getDepartmentCode())) {
				sql.add("   AND dtl.SWK_DEP_CODE = ?", condition.getDepartmentCode());
			}

			// 社員コード
			if (!Util.isNullOrEmpty(condition.getEmployeeCode())) {
				sql.add("   AND dtl.SWK_EMP_CODE = ?", condition.getEmployeeCode());
			}

			// 管理1コード
			if (!Util.isNullOrEmpty(condition.getManegement1Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_1 = ?", condition.getManegement1Code());
			}

			// 管理2コード
			if (!Util.isNullOrEmpty(condition.getManegement2Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_2 = ?", condition.getManegement2Code());
			}

			// 管理3コード
			if (!Util.isNullOrEmpty(condition.getManegement3Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_3 = ?", condition.getManegement3Code());
			}

			// 管理4コード
			if (!Util.isNullOrEmpty(condition.getManegement4Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_4 = ?", condition.getManegement4Code());
			}

			// 管理5コード
			if (!Util.isNullOrEmpty(condition.getManegement5Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_5 = ?", condition.getManegement5Code());
			}

			// 管理6コード
			if (!Util.isNullOrEmpty(condition.getManegement6Code())) {
				sql.add("   AND dtl.SWK_KNR_CODE_6 = ?", condition.getManegement6Code());
			}

			// 非会計明細1
			if (!Util.isNullOrEmpty(condition.getHm1())) {
				sql.addNLikeAmbi("   AND dtl.SWK_HM_1 ?", condition.getHm1());
			}

			// 非会計明細2
			if (!Util.isNullOrEmpty(condition.getHm2())) {
				sql.addNLikeAmbi("   AND dtl.SWK_HM_2 ?", condition.getHm2());
			}

			// 非会計明細3
			if (!Util.isNullOrEmpty(condition.getHm3())) {
				sql.addNLikeAmbi("   AND dtl.SWK_HM_3 ?", condition.getHm3());
			}

			// 通貨コード
			if (!Util.isNullOrEmpty(condition.getCurrencyCode())) {
				sql.add("   AND dtl.SWK_CUR_CODE = ?", condition.getCurrencyCode());
			}

			// 伝票修正/複写場合、且つ 伝票修正/複写対象のBS勘定消込仕訳抽出
			if (condition.isForSlipModify() && !Util.isNullOrEmpty(condition.getSlipNo())) {
				sql.add(" AND dtl.SWK_SOUSAI_DEN_NO = ? ", condition.getSlipNo());
				sql.add(" AND NVL(dtl.SWK_KESI_KBN, 0) = ? ", SWK_DTL.KESI_KBN.BASE); // 消込区分は1:消込元
			}

			// 追加SQL
			sql.add(getAddonWhereSql(condition));

			sql.add(" ORDER BY ");
			sql.add("   dtl.SWK_TRI_CODE, ");
			sql.add("   dtl.SWK_DEP_CODE, ");
			sql.add("   dtl.SWK_KMK_CODE, ");
			sql.add("   dtl.SWK_HKM_CODE, ");
			sql.add("   dtl.SWK_UKM_CODE, ");
			sql.add("   dtl.SWK_DEN_NO, ");
			sql.add("   dtl.SWK_GYO_NO ");

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
	 * @return 対象外データ区分配列
	 */
	protected List<String> getNotInDataDivs() {
		List<String> list = new ArrayList<String>();
		list.add("14");
		list.add("15");
		return list;
	}

	/**
	 * 追加条件の取得
	 * 
	 * @param condition
	 * @return 追加条件
	 */
	@SuppressWarnings("unused")
	protected String getAddonWhereSql(BSEraseCondition condition) {
		return "";
	}

	/**
	 * マッピング
	 * 
	 * @param rs
	 * @return BS勘定消込の仕訳ジャーナル
	 * @throws SQLException
	 */
	protected SWK_DTL mapping(ResultSet rs) throws SQLException {

		SWK_DTL bs = createDetail();

		bs.setKAI_CODE(rs.getString("KAI_CODE"));
		bs.setSWK_DEN_DATE(rs.getDate("SWK_DEN_DATE"));
		bs.setSWK_DEN_NO(rs.getString("SWK_DEN_NO"));
		bs.setSWK_GYO_NO(rs.getInt("SWK_GYO_NO"));
		bs.setSWK_NENDO(rs.getInt("SWK_NENDO"));
		bs.setSWK_TUKIDO(rs.getInt("SWK_TUKIDO"));
		bs.setSWK_SEI_NO(rs.getString("SWK_SEI_NO"));
		bs.setSWK_DC_KBN(rs.getInt("SWK_DC_KBN"));
		bs.setSWK_KMK_CODE(rs.getString("SWK_KMK_CODE"));
		bs.setSWK_HKM_CODE(rs.getString("SWK_HKM_CODE"));
		bs.setSWK_UKM_CODE(rs.getString("SWK_UKM_CODE"));
		bs.setSWK_DEP_CODE(rs.getString("SWK_DEP_CODE"));
		bs.setSWK_ZEI_KBN(rs.getInt("SWK_ZEI_KBN"));
		bs.setSWK_KIN(rs.getBigDecimal("SWK_KIN"));
		bs.setSWK_ZEI_KIN(rs.getBigDecimal("SWK_ZEI_KIN"));
		bs.setSWK_ZEI_CODE(rs.getString("SWK_ZEI_CODE"));
		bs.setSWK_ZEI_RATE(rs.getBigDecimal("SWK_ZEI_RATE"));
		bs.setSWK_GYO_TEK_CODE(rs.getString("SWK_GYO_TEK_CODE"));
		bs.setSWK_GYO_TEK(rs.getString("SWK_GYO_TEK"));
		bs.setSWK_TRI_CODE(rs.getString("SWK_TRI_CODE"));
		bs.setSWK_EMP_CODE(rs.getString("SWK_EMP_CODE"));
		bs.setSWK_KNR_CODE_1(rs.getString("SWK_KNR_CODE_1"));
		bs.setSWK_KNR_CODE_2(rs.getString("SWK_KNR_CODE_2"));
		bs.setSWK_KNR_CODE_3(rs.getString("SWK_KNR_CODE_3"));
		bs.setSWK_KNR_CODE_4(rs.getString("SWK_KNR_CODE_4"));
		bs.setSWK_KNR_CODE_5(rs.getString("SWK_KNR_CODE_5"));
		bs.setSWK_KNR_CODE_6(rs.getString("SWK_KNR_CODE_6"));
		bs.setSWK_HM_1(rs.getString("SWK_HM_1"));
		bs.setSWK_HM_2(rs.getString("SWK_HM_2"));
		bs.setSWK_HM_3(rs.getString("SWK_HM_3"));
		bs.setSWK_AUTO_KBN(rs.getInt("SWK_AUTO_KBN"));
		bs.setSWK_DATA_KBN(rs.getString("SWK_DATA_KBN"));
		bs.setSWK_UPD_KBN(rs.getInt("SWK_UPD_KBN"));
		bs.setSWK_KSN_KBN(rs.getInt("SWK_KSN_KBN"));
		bs.setSWK_K_KAI_CODE(rs.getString("SWK_K_KAI_CODE"));
		bs.setSWK_CUR_CODE(rs.getString("SWK_CUR_CODE"));
		bs.setSWK_CUR_RATE(rs.getBigDecimal("SWK_CUR_RATE"));
		bs.setSWK_IN_KIN(rs.getBigDecimal("SWK_IN_KIN"));
		bs.setSWK_IN_ZEI_KIN(rs.getBigDecimal("SWK_IN_ZEI_KIN"));
		bs.setHAS_DATE(rs.getDate("HAS_DATE"));
		bs.setDEN_SYU_CODE(rs.getString("DEN_SYU_CODE"));
		bs.setSWK_BOOK_NO(rs.getInt("SWK_BOOK_NO"));
		bs.setSWK_ADJ_KBN(rs.getInt("SWK_ADJ_KBN"));
		bs.setDEN_SYU_CODE(rs.getString("DEN_SYU_CODE"));

		bs.setSWK_K_KAI_NAME("KAI_NAME");
		bs.setSWK_K_KAI_NAME_S("KAI_NAME_S");
		bs.setSWK_DEP_NAME(rs.getString("DEP_NAME"));
		bs.setSWK_DEP_NAME_S(rs.getString("DEP_NAME_S"));
		bs.setSWK_KMK_NAME(rs.getString("KMK_NAME"));
		bs.setSWK_KMK_NAME_S(rs.getString("KMK_NAME_S"));
		bs.setSWK_HKM_NAME(rs.getString("HKM_NAME"));
		bs.setSWK_HKM_NAME_S(rs.getString("HKM_NAME_S"));
		bs.setSWK_UKM_NAME(rs.getString("UKM_NAME"));
		bs.setSWK_UKM_NAME_S(rs.getString("UKM_NAME_S"));
		bs.setSWK_TRI_NAME(rs.getString("TRI_NAME"));
		bs.setSWK_TRI_NAME_S(rs.getString("TRI_NAME_S"));
		bs.setSWK_EMP_NAME(rs.getString("EMP_NAME"));
		bs.setSWK_EMP_NAME_S(rs.getString("EMP_NAME_S"));
		bs.setSWK_KNR_NAME_1(rs.getString("KNR_NAME_1"));
		bs.setSWK_KNR_NAME_S_1(rs.getString("KNR_NAME_S_1"));
		bs.setSWK_KNR_NAME_2(rs.getString("KNR_NAME_2"));
		bs.setSWK_KNR_NAME_S_2(rs.getString("KNR_NAME_S_2"));
		bs.setSWK_KNR_NAME_3(rs.getString("KNR_NAME_3"));
		bs.setSWK_KNR_NAME_S_3(rs.getString("KNR_NAME_S_3"));
		bs.setSWK_KNR_NAME_4(rs.getString("KNR_NAME_4"));
		bs.setSWK_KNR_NAME_S_4(rs.getString("KNR_NAME_S_4"));
		bs.setSWK_KNR_NAME_5(rs.getString("KNR_NAME_5"));
		bs.setSWK_KNR_NAME_S_5(rs.getString("KNR_NAME_S_5"));
		bs.setSWK_KNR_NAME_6(rs.getString("KNR_NAME_6"));
		bs.setSWK_KNR_NAME_S_6(rs.getString("KNR_NAME_S_6"));
		bs.setCUR_DEC_KETA(rs.getInt("DEC_KETA"));

		bs.setSWK_KESI_KBN(rs.getInt("SWK_KESI_KBN"));
		bs.setSWK_KESI_DATE(rs.getDate("SWK_KESI_DATE"));
		bs.setSWK_KESI_DEN_NO(rs.getString("SWK_KESI_DEN_NO"));
		bs.setSWK_SOUSAI_DATE(rs.getDate("SWK_SOUSAI_DATE"));
		bs.setSWK_SOUSAI_DEN_NO(rs.getString("SWK_SOUSAI_DEN_NO"));
		if (!Util.isNullOrEmpty(rs.getString("SWK_SOUSAI_GYO_NO"))) {
			bs.setSWK_SOUSAI_GYO_NO(rs.getInt("SWK_SOUSAI_GYO_NO"));
		}

		return bs;
	}

	/**
	 * @return 仕訳ジャーナル
	 */
	protected SWK_DTL createDetail() {
		return new SWK_DTL();
	}

	/**
	 * B/S勘定消込の復元
	 * 
	 * @param condition 条件(会社コード, 伝票番号)
	 */
	public void restoreBsBalance(SlipCondition condition) {

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();

			VCreator sql = new VCreator();
			sql.add("  ");
			sql.add(" UPDATE SWK_DTL ");
			sql.add("    SET SWK_KESI_KBN      = 0, ");
			sql.add("        SWK_KESI_DEN_NO   = NULL, ");
			sql.add("        SWK_KESI_DATE     = NULL, ");
			sql.add("        SWK_SOUSAI_DATE   = NULL, ");
			sql.add("        SWK_SOUSAI_DEN_NO = NULL, ");
			sql.add("        SWK_SOUSAI_GYO_NO = NULL, ");
			sql.add("        PRG_ID            = ?, ", condition.getProgramCode());
			sql.add("        USR_ID            = ?, ", condition.getUserCode());
			sql.addYMDHMS("        UPD_DATE          = ? ", getNow());
			sql.add("  WHERE 1 = 1 ");
			if (!isBsUseKCompany || !getCompany().getAccountConfig().isUseGroupAccount()) {
				sql.add("    AND KAI_CODE          = ? ", condition.getCompanyCode());
			}
			sql.add("    AND SWK_KESI_KBN      = 1 ");

			if (!Util.isNullOrEmpty(condition.getEraseNo())) {
				sql.add("    AND SWK_KESI_DEN_NO   = ? ", condition.getEraseNo());
			}
			if (!Util.isNullOrEmpty(condition.getSlipNo())) {
				sql.add("    AND SWK_SOUSAI_DEN_NO =? ", condition.getSlipNo());
			}

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * B/S勘定消込の更新
	 * 
	 * @param slip 伝票
	 */
	public void updateBsBalance(Slip slip) {

		Connection conn = null;

		try {
			// B/S勘定消込の更新
			conn = DBUtil.getConnection();

			if (slip != null && slip.getDetails() != null) {

				for (SWK_DTL dtl : slip.getDetails()) {
					if (dtl.isFunctionalCurrency()) {
						// IFRS仕訳は対象外
						continue;
					}

					SWK_DTL bean = dtl.getBsDetail();

					if (bean == null || bean.isAutoDetail() && !getCompanyCode().equals(bean.getKAI_CODE())) {
						continue;
					}

					VCreator sql = new VCreator();
					sql.add(" UPDATE SWK_DTL ");
					sql.add("    SET SWK_KESI_KBN      = 1, ");
					sql.add("        SWK_SOUSAI_DATE   = ?, ", dtl.getSWK_DEN_DATE());
					sql.add("        SWK_SOUSAI_DEN_NO = ?, ", dtl.getSWK_DEN_NO());
					sql.add("        SWK_SOUSAI_GYO_NO = ?, ", dtl.getSWK_GYO_NO());
					sql.add("        PRG_ID            = ?, ", getProgramCode());
					sql.add("        USR_ID            = ?, ", getUserCode());
					sql.addYMDHMS("        UPD_DATE          = ? ", getNow());
					sql.add("  WHERE KAI_CODE          = ? ", bean.getKAI_CODE());
					sql.add("    AND SWK_DEN_NO        = ? ", bean.getSWK_DEN_NO());
					sql.add("    AND SWK_GYO_NO        = ? ", bean.getSWK_GYO_NO());
					sql.add("    AND SWK_KESI_KBN      = 0 "); // 未消込仕訳が対象
					sql.add("    AND SWK_ADJ_KBN      <> 2 ");

					int count = DBUtil.execute(conn, sql);
					if (count == 0) {
						// I00115 既に他ユーザによって消し込まれたデータが存在します。再度検索を行ってください。
						throw new TRuntimeException("I00115");
					}

				}
			}
		} catch (Exception e) {
			throw new TRuntimeException(e);
		} finally {
			DBUtil.close(conn);
		}

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
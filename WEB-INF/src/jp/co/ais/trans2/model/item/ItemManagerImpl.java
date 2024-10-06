package jp.co.ais.trans2.model.item;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 科目情報の実装
 * 
 * @author AIS
 */
public class ItemManagerImpl extends TModel implements ItemManager {

	/** 航海収支計算フラグ使うかどうか */
	protected static boolean isUseVoyageCalcution = ServerConfig.isFlagOn("trans.MG0080.use.voyage.calculation");

	/** true:発生日は科目の機能有効 */
	protected static boolean isUseOccurDate = ServerConfig.isFlagOn("trans.MG0080.use.occurdate");

	/** 補助 固定部門 表示可否 */
	protected static boolean isUseKoteiDep = ServerConfig.isFlagOn("trans.use.sub.item.kotei.dep");

	/**
	 * 科目生成
	 * 
	 * @return 科目
	 */
	protected Item createItem() {
		return new Item();
	}

	/**
	 * 補助科目生成
	 * 
	 * @return 補助科目
	 */
	protected SubItem createSubItem() {
		return new SubItem();
	}

	/**
	 * 内訳科目生成
	 * 
	 * @return 内訳科目
	 */
	protected DetailItem createDetailItem() {
		return new DetailItem();
	}

	/**
	 * @return 科目検索項目
	 */
	protected String getItemSelectColumn() {
		String sql = "";
		sql += " kmk.KAI_CODE, ";
		sql += " kmk.KMK_CODE, ";
		sql += " kmk.KMK_NAME, ";
		sql += " kmk.KMK_NAME_S, ";
		sql += " kmk.KMK_NAME_K, ";
		sql += " kmk.HKM_KBN, ";
		sql += " kmk.ZEI_CODE, ";
		sql += " kmk.TRI_CODE_FLG, ";
		sql += " kmk.EMP_CODE_FLG, ";
		sql += " kmk.KNR_FLG_1, ";
		sql += " kmk.KNR_FLG_2, ";
		sql += " kmk.KNR_FLG_3, ";
		sql += " kmk.KNR_FLG_4, ";
		sql += " kmk.KNR_FLG_5, ";
		sql += " kmk.KNR_FLG_6, ";
		sql += " kmk.HM_FLG_1, ";
		sql += " kmk.HM_FLG_2, ";
		sql += " kmk.HM_FLG_3, ";
		sql += " kmk.URI_ZEI_FLG, ";
		sql += " kmk.SIR_ZEI_FLG, ";
		sql += " kmk.MCR_FLG, ";
		sql += " kmk.EXC_FLG, ";
		sql += " kmk.HAS_FLG, ";
		sql += " kmk.STR_DATE, ";
		sql += " kmk.END_DATE, ";
		sql += " kmk.KOTEI_DEP_CODE, ";
		sql += " zei.ZEI_NAME, ";
		sql += " zei.ZEI_NAME_S, ";
		sql += " zei.ZEI_NAME_K, ";
		sql += " zei.ZEI_RATE, ";
		sql += " zei.US_KBN ZEI_US_KBN, ";
		sql += " zei.STR_DATE ZEI_STR_DATE, ";
		sql += " zei.END_DATE ZEI_END_DATE,";
		sql += " kmk.SUM_KBN,";
		sql += " kmk.KMK_SHU,";
		sql += " kmk.DC_KBN,";
		sql += " kmk.KMK_CNT_GL,";
		sql += " kmk.KMK_CNT_AP,";
		sql += " kmk.KMK_CNT_AR,";
		sql += " kmk.KMK_CNT_BG,";
		sql += " kmk.KMK_CNT_SOUSAI,";
		sql += " kmk.KESI_KBN,";
		sql += " kmk.GL_FLG_1,";
		sql += " kmk.GL_FLG_2,";
		sql += " kmk.GL_FLG_3,";
		sql += " kmk.AP_FLG_1,";
		sql += " kmk.AP_FLG_2,";
		sql += " kmk.AR_FLG_1,";
		sql += " kmk.AR_FLG_2,";
		sql += " kmk.FA_FLG_1,";
		sql += " kmk.FA_FLG_2,";
		sql += (isUseVoyageCalcution ? " kmk.HWR_FLG," : "");
		sql += " bmn.DEP_CODE,";
		sql += " bmn.DEP_NAME";
		return sql;
	}

	/**
	 * @see jp.co.ais.trans2.model.item.ItemManager#get(jp.co.ais.trans2.model.item.ItemSearchCondition)
	 */
	public List<Item> get(ItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Item> list = new ArrayList<Item>();

		try {

			String sql = " SELECT " + getItemSelectColumn() + " FROM " + " KMK_MST kmk "
				+ " LEFT OUTER JOIN SZEI_MST zei" + "   ON zei.KAI_CODE = kmk.KAI_CODE"
				+ "  AND zei.ZEI_CODE = kmk.ZEI_CODE" + " LEFT OUTER JOIN  BMN_MST bmn"
				+ "   ON kmk.KAI_CODE = bmn.KAI_CODE" + "  AND kmk.KOTEI_DEP_CODE = bmn.DEP_CODE" +

				" WHERE 1 = 1 ";

			sql += getItemWhereSql(condition);

			sql = sql + " ORDER BY " + " kmk.KMK_CODE ";

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
	 * 科目のWHERE句SQL
	 * 
	 * @param condition 条件
	 * @return WHERE句SQL
	 */
	protected String getItemWhereSql(ItemSearchCondition condition) {
		String sql = "";

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql = sql + " AND	kmk.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql = sql + " AND	kmk.KMK_CODE = " + SQLUtil.getParam(condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql = sql + " AND	kmk.KMK_CODE " + SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
		}

		// 略称あいまい
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql = sql + " AND	kmk.KMK_NAME_S " + SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
		}

		// 検索名称あいまい
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql = sql + " AND	kmk.KMK_NAME_K " + SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql = sql + " AND	kmk.KMK_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql = sql + " AND	kmk.KMK_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql + " AND	kmk.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm())
				+ " AND	kmk.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}

		// 補助科目を含むか
		if (condition.isSubItem()) {
			sql = sql + "AND kmk.HKM_KBN = 1 ";
		}

		// 集計科目
		if (!condition.isSumItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 1 ";
		}

		// 見出科目
		if (!condition.isTitleItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 2 ";
		}

		// 固定部門
		if (!Util.isNullOrEmpty(condition.getDepartmentCode())) {
			sql = sql + " AND	(kmk.KOTEI_DEP_CODE IS NULL OR kmk.KOTEI_DEP_CODE = "
				+ SQLUtil.getParam(condition.getDepartmentCode()) + ")";
		}

		// 評価替
		if (condition.evaluation) {
			sql = sql + " AND	kmk.EXC_FLG <> 0 ";
		}

		// BS勘定消込区分
		if (condition.bsCalculateErase) {
			sql = sql + " AND	kmk.KESI_KBN <> 0 ";
		}

		// 資金科目
		if (condition.isCash()) {
			sql = sql + " AND	kmk.KMK_CNT_GL = '04' ";
		}

		// 伝票入力区分
		if (condition.getSlipInputType() != null) {
			switch (condition.getSlipInputType()) {
				case InputCashFlowSlip: // 入金伝票入力
					sql = sql + " AND	GL_FLG_1 = 1";
					break;

				case OutputCashFlowSlip: // 出金伝票入力
					sql = sql + " AND	GL_FLG_2 = 1";
					break;

				case TransferSlip: // 振替伝票入力
				case ReversingSlip: // 振戻伝票入力
					sql = sql + " AND	GL_FLG_3 = 1";
					break;

				case ExpenseSettlementSlip: // 経費精算伝票入力
					sql = sql + " AND	AP_FLG_1 = 1";
					break;

				case PaymentAppropriateSlip: // 請求書伝票入力
					sql = sql + " AND	AP_FLG_2 = 1";
					break;

				case ReceivableAppropriateSlip: // 債権計上伝票入力
					sql = sql + " AND	AR_FLG_1 = 1";
					break;

				case ReceivableErasingSlip: // 債権消込伝票入力
					sql = sql + " AND	AR_FLG_2 = 1";
					break;

				case AssetsEntrySlip: // 資産計上伝票入力
					sql = sql + " AND	FA_FLG_1 = 1";
					break;

				case PaymentRequestSlip: // 支払依頼伝票入力
					sql = sql + " AND	FA_FLG_2 = 1";
					break;
			}
		}

		// 債務科目
		if (condition.isPaymentItem()) {
			sql = sql + " AND	kmk.KMK_CNT_AP = '01' ";
		}

		// 債権科目
		if (condition.isReceiveItem()) {
			sql = sql + " AND	kmk.KMK_CNT_AR = '01' ";
		}

		// AR制御区分
		if (!condition.getArControlTypes().isEmpty()) {
			StringBuilder arControl = new StringBuilder();

			for (ItemSearchCondition.ARControlType type : condition.getArControlTypes()) {
				if (arControl.length() != 0) {
					arControl.append(", ");
				}

				switch (type) {
					case Nomal:
						arControl.append("'00'");
						break;

					case ARAccount:
						arControl.append("'01'");
						break;

					case ARTempolaryAccount:
						arControl.append("'02'");
						break;
				}
			}

			sql += " AND KMK_CNT_AR IN (" + arControl + ")";
		}

		// 管理項目1を選択しているか
		if (condition.isUseManagement1()) {
			sql += " AND kmk.KNR_FLG_1 = 1 ";
		}
		// 管理項目2を選択しているか
		if (condition.isUseManagement2()) {
			sql += " AND kmk.KNR_FLG_2 = 1 ";
		}
		// 管理項目3を選択しているか
		if (condition.isUseManagement3()) {
			sql += " AND kmk.KNR_FLG_3 = 1 ";
		}
		// 管理項目4を選択しているか
		if (condition.isUseManagement4()) {
			sql += " AND kmk.KNR_FLG_4 = 1 ";
		}
		// 管理項目5を選択しているか
		if (condition.isUseManagement5()) {
			sql += " AND kmk.KNR_FLG_5 = 1 ";
		}
		// 管理項目6を選択しているか
		if (condition.isUseManagement6()) {
			sql += " AND kmk.KNR_FLG_6 = 1 ";
		}

		// 管理項目1を非選択にしているか
		if (condition.isNotUseManagement1()) {
			sql += " AND kmk.KNR_FLG_1 = 0 ";
		}
		// 管理項目2を非選択にしているか
		if (condition.isNotUseManagement2()) {
			sql += " AND kmk.KNR_FLG_2 = 0 ";
		}
		// 管理項目3を非選択にしているか
		if (condition.isNotUseManagement3()) {
			sql += " AND kmk.KNR_FLG_3 = 0 ";
		}
		// 管理項目4を非選択にしているか
		if (condition.isNotUseManagement4()) {
			sql += " AND kmk.KNR_FLG_4 = 0 ";
		}
		// 管理項目5を非選択にしているか
		if (condition.isNotUseManagement5()) {
			sql += " AND kmk.KNR_FLG_5 = 0 ";
		}
		// 管理項目6を非選択にしているか
		if (condition.isNotUseManagement6()) {
			sql += " AND kmk.KNR_FLG_6 = 0 ";
		}
		// 多通貨入力が可能か
		if (condition.isUseForeignCurrency()) {
			sql += " AND kmk.MCR_FLG = 1 ";
		}
		// 社員入力が可能か
		if (condition.isUseEmployee()) {
			sql += " AND kmk.EMP_CODE_FLG = 1 ";
		}
		// 社員入力を非選択にしているか
		if (condition.isNoUseEmployee()) {
			sql += " AND kmk.EMP_CODE_FLG = 0 ";
		}
		// 消費税が入力されているか
		if (condition.isFlgZeiCode()) {
			sql += " AND kmk.ZEI_CODE IS NOT NULL ";
		}

		// 科目体系
		if (!Util.isNullOrEmpty(condition.getItemOrganizationCode())) {
			sql += "AND EXISTS ( " + " SELECT 1 " + " FROM 	KMK_SUM_MST kmksum "
				+ " WHERE kmk.KAI_CODE = kmksum.KAI_CODE " + " AND	kmk.KMK_CODE = kmksum.KMK_CODE "
				+ " AND	kmksum.KMT_CODE = " + SQLUtil.getParam(condition.getItemOrganizationCode()) + " ) ";
		}

		// 追加条件
		if (!Util.isNullOrEmpty(condition.getAddonWhereSql())) {
			sql += condition.getAddonWhereSql();
		}

		return sql;
	}

	/**
	 * 科目登録
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(Item bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO KMK_MST (");
			s.add("  KAI_CODE,");
			s.add("  KMK_CODE,");
			s.add("  KMK_NAME,");
			s.add("  KMK_NAME_S,");
			s.add("  KMK_NAME_K,");
			s.add("  STR_DATE,");
			s.add("  END_DATE,");
			s.add("  SUM_KBN,");
			s.add("  KMK_SHU,");
			s.add("  DC_KBN,");

			if (bean.getItemSumType() == ItemSumType.INPUT) {
				s.add("  HKM_KBN,");
				s.add("  ZEI_CODE,");
				s.add("  TRI_CODE_FLG,");
				s.add("  EMP_CODE_FLG,");
				s.add("  KNR_FLG_1,");
				s.add("  KNR_FLG_2,");
				s.add("  KNR_FLG_3,");
				s.add("  KNR_FLG_4,");
				s.add("  KNR_FLG_5,");
				s.add("  KNR_FLG_6,");
				s.add("  HM_FLG_1,");
				s.add("  HM_FLG_2,");
				s.add("  HM_FLG_3,");
				s.add("  URI_ZEI_FLG,");
				s.add("  SIR_ZEI_FLG,");
				s.add("  MCR_FLG,");
				s.add("  EXC_FLG,");
				s.add("  KOTEI_DEP_CODE,");
				s.add("  KMK_CNT_GL,");
				s.add("  KMK_CNT_AP,");
				s.add("  KMK_CNT_AR,");
				s.add("  KMK_CNT_BG,");
				s.add("  KMK_CNT_SOUSAI,");
				s.add("  KESI_KBN,");
				s.add("  HAS_FLG,");
				s.add("  GL_FLG_1,");
				s.add("  GL_FLG_2,");
				s.add("  GL_FLG_3,");
				s.add("  AP_FLG_1,");
				s.add("  AP_FLG_2,");
				s.add("  AR_FLG_1,");
				s.add("  AR_FLG_2,");
				s.add("  FA_FLG_1,");
				s.add("  FA_FLG_2,");
				if (isUseVoyageCalcution) {
					s.add("  HWR_FLG,");
				}
			}
			s.add("  INP_DATE,");
			s.add("  PRG_ID,");
			s.add("  USR_ID");
			s.add(") VALUES (");
			s.add("  ?,", bean.getCompanyCode());
			s.add("  ?,", bean.getCode());
			s.add("  ?,", bean.getName());
			s.add("  ?,", bean.getNames());
			s.add("  ?,", bean.getNamek());
			s.add("  ?,", bean.getDateFrom());
			s.add("  ?,", bean.getDateTo());
			s.add("  ?,", bean.getItemSumType().value);
			s.add("  ?,", bean.getItemType().value);
			s.add("  ?,", bean.getDc().value);

			if (bean.getItemSumType() == ItemSumType.INPUT) {
				s.add("  ?,", BooleanUtil.toInt(bean.hasSubItem()));
				s.add("  ?,", bean.getConsumptionTax().getCode());
				s.add("  ?,", bean.getClientType().value);
				s.add("  ?,", BooleanUtil.toInt(bean.isUseEmployee()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement1()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement2()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement3()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement4()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement5()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseManagement6()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseNonAccount1()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseNonAccount2()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseNonAccount3()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseSalesTaxation()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUsePurchaseTaxation()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseForeignCurrency()));
				s.add("  ?,", bean.getEvaluationMethod().value);
				s.add("  ?,", bean.getFixedDepartmentCode());
				s.add("  ?,", bean.getGlType().value);
				s.add("  ?,", bean.getApType().value);
				s.add("  ?,", bean.getArType().value);
				s.add("  ?,", bean.getBgType().value);
				s.add("  ?,", BooleanUtil.toInt(bean.isDoesOffsetItem()));
				s.add("  ?,", BooleanUtil.toInt(bean.isDoesBsOffset()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseOccurDate()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseInputCashFlowSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseOutputCashFlowSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseTransferSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseExpenseSettlementSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUsePaymentAppropriateSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseReceivableAppropriateSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseReceivableErasingSlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUseAssetsEntrySlip()));
				s.add("  ?,", BooleanUtil.toInt(bean.isUsePaymentRequestSlip()));
				if (isUseVoyageCalcution) {
					s.add("  ?,", BooleanUtil.toInt(bean.isUseVoyageCalculation()));
				}
			}

			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);
			DBUtil.close(conn);

		} catch (TException e) {
			throw e;

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 科目修正
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(Item bean) throws TException {
		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator s = new SQLCreator();
			s.add("  UPDATE KMK_MST SET");
			s.add("  KMK_NAME = ?,", bean.getName());
			s.add("  KMK_NAME_S = ?,", bean.getNames());
			s.add("  KMK_NAME_K = ?,", bean.getNamek());
			s.add("  STR_DATE = ?,", bean.getDateFrom());
			s.add("  END_DATE = ?,", bean.getDateTo());
			s.add("  SUM_KBN = ?, ", bean.getItemSumType().value);
			s.add("  KMK_SHU = ?,", bean.getItemType().value);
			s.add("  DC_KBN = ?,", bean.getDc().value);

			if (bean.getItemSumType() == ItemSumType.INPUT) {

				s.add("  HKM_KBN = ?,", BooleanUtil.toInt(bean.hasSubItem()));
				s.add("  ZEI_CODE = ?,", bean.getConsumptionTax().getCode());
				s.add("  TRI_CODE_FLG = ?,", bean.getClientType().value);
				s.add("  EMP_CODE_FLG = ?,", BooleanUtil.toInt(bean.isUseEmployee()));
				s.add("  KNR_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseManagement1()));
				s.add("  KNR_FLG_2 = ?,", BooleanUtil.toInt(bean.isUseManagement2()));
				s.add("  KNR_FLG_3 = ?,", BooleanUtil.toInt(bean.isUseManagement3()));
				s.add("  KNR_FLG_4 = ?,", BooleanUtil.toInt(bean.isUseManagement4()));
				s.add("  KNR_FLG_5 = ?,", BooleanUtil.toInt(bean.isUseManagement5()));
				s.add("  KNR_FLG_6 = ?,", BooleanUtil.toInt(bean.isUseManagement6()));
				s.add("  HM_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseNonAccount1()));
				s.add("  HM_FLG_2 = ?,", BooleanUtil.toInt(bean.isUseNonAccount2()));
				s.add("  HM_FLG_3 = ?,", BooleanUtil.toInt(bean.isUseNonAccount3()));
				s.add("  URI_ZEI_FLG = ?,", BooleanUtil.toInt(bean.isUseSalesTaxation()));
				s.add("  SIR_ZEI_FLG = ?,", BooleanUtil.toInt(bean.isUsePurchaseTaxation()));
				s.add("  MCR_FLG = ?,", BooleanUtil.toInt(bean.isUseForeignCurrency()));
				s.add("  EXC_FLG = ?,", bean.getEvaluationMethod().value);
				s.add("  KOTEI_DEP_CODE = ?,", bean.getFixedDepartmentCode());
				s.add("  KMK_CNT_GL = ?,", bean.getGlType().value);
				s.add("  KMK_CNT_AP = ?,", bean.getApType().value);
				s.add("  KMK_CNT_AR = ?,", bean.getArType().value);
				s.add("  KMK_CNT_BG = ?,", bean.getBgType().value);
				s.add("  KMK_CNT_SOUSAI = ?,", BooleanUtil.toInt(bean.isDoesOffsetItem()));
				s.add("  KESI_KBN = ?,", BooleanUtil.toInt(bean.isDoesBsOffset()));
				s.add("  HAS_FLG = ?,", BooleanUtil.toInt(bean.isUseOccurDate()));
				s.add("  GL_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseInputCashFlowSlip()));
				s.add("  GL_FLG_2 = ?,", BooleanUtil.toInt(bean.isUseOutputCashFlowSlip()));
				s.add("  GL_FLG_3 = ?,", BooleanUtil.toInt(bean.isUseTransferSlip()));
				s.add("  AP_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseExpenseSettlementSlip()));
				s.add("  AP_FLG_2 = ?,", BooleanUtil.toInt(bean.isUsePaymentAppropriateSlip()));
				s.add("  AR_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseReceivableAppropriateSlip()));
				s.add("  AR_FLG_2 = ?,", BooleanUtil.toInt(bean.isUseReceivableErasingSlip()));
				s.add("  FA_FLG_1 = ?,", BooleanUtil.toInt(bean.isUseAssetsEntrySlip()));
				s.add("  FA_FLG_2 = ?,", BooleanUtil.toInt(bean.isUsePaymentRequestSlip()));
				if (isUseVoyageCalcution) {
					s.add("  HWR_FLG = ?,", BooleanUtil.toInt(bean.isUseVoyageCalculation()));
				}
			} else {

				s.add("  HKM_KBN = null,");
				s.add("  ZEI_CODE = null,");
				s.add("  TRI_CODE_FLG = null,");
				s.add("  EMP_CODE_FLG = null,");
				s.add("  KNR_FLG_1 = null,");
				s.add("  KNR_FLG_2 = null,");
				s.add("  KNR_FLG_3 = null,");
				s.add("  KNR_FLG_4 = null,");
				s.add("  KNR_FLG_5 = null,");
				s.add("  KNR_FLG_6 = null,");
				s.add("  HM_FLG_1 = null,");
				s.add("  HM_FLG_2 = null,");
				s.add("  HM_FLG_3 = null,");
				s.add("  URI_ZEI_FLG = null,");
				s.add("  SIR_ZEI_FLG = null,");
				s.add("  MCR_FLG = null,");
				s.add("  EXC_FLG = null,");
				s.add("  KOTEI_DEP_CODE = null,");
				s.add("  KMK_CNT_GL = null,");
				s.add("  KMK_CNT_AP = null,");
				s.add("  KMK_CNT_AR = null,");
				s.add("  KMK_CNT_BG = null,");
				s.add("  KMK_CNT_SOUSAI = null,");
				s.add("  KESI_KBN = null,");
				s.add("  HAS_FLG = null,");
				s.add("  GL_FLG_1 = null,");
				s.add("  GL_FLG_2 = null,");
				s.add("  GL_FLG_3 = null,");
				s.add("  AP_FLG_1 = null,");
				s.add("  AP_FLG_2 = null,");
				s.add("  AR_FLG_1 = null,");
				s.add("  AR_FLG_2 = null,");
				s.add("  FA_FLG_1 = null,");
				s.add("  FA_FLG_2 = null,");
				s.add("  HWR_FLG = null,");

			}

			s.addYMDHMS("  UPD_DATE = ?,", getNow());
			s.add("  PRG_ID = ?,", getProgramCode());
			s.add("  USR_ID = ?", getUserCode());

			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add(" AND KMK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 科目削除
	 * 
	 * @param item
	 * @throws TException
	 */
	public void delete(Item item) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.ITEM);
		condition.setCompanyCode(item.getCompanyCode());
		condition.setItemCode(item.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM KMK_MST");
			s.add("WHERE KAI_CODE = ?", item.getCompanyCode());
			s.add("  AND KMK_CODE = ?", item.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 科目一覧をエクセル形式で返す
	 * 
	 * @param condition
	 * @return exl.getExcel(list)
	 * @throws TException
	 */
	public byte[] getExcel(ItemSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Item> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			ItemExcel exl = new ItemExcel(getUser().getLanguage(), getCompany());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Item mapping(ResultSet rs) throws Exception {

		Item bean = createItem();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KMK_CODE"));
		bean.setName(rs.getString("KMK_NAME"));
		bean.setNames(rs.getString("KMK_NAME_S"));
		bean.setNamek(rs.getString("KMK_NAME_K"));
		bean.setSubItem(rs.getInt("HKM_KBN") == 1);
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		// 取引先入力フラグ - TRI_CODE_FLG
		bean.setClientType(CustomerType.get(rs.getInt("TRI_CODE_FLG")));
		// 社員入力フラグ - EMP_CODE_FLG
		bean.setUseEmployee(BooleanUtil.toBoolean(rs.getInt("EMP_CODE_FLG")));
		// 管理１入力フラグ - KNR_FLG_1
		bean.setUseManagement1(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_1")));
		// 管理２入力フラグ - KNR_FLG_2
		bean.setUseManagement2(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_2")));
		// 管理３入力フラグ - KNR_FLG_3
		bean.setUseManagement3(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_3")));
		// 管理４入力フラグ - KNR_FLG_4
		bean.setUseManagement4(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_4")));
		// 管理５入力フラグ - KNR_FLG_5
		bean.setUseManagement5(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_5")));
		// 管理６入力フラグ - KNR_FLG_6
		bean.setUseManagement6(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_6")));
		// 非会計1入力フラグ - HM_FLG_1
		bean.setUseNonAccount1(BooleanUtil.toBoolean(rs.getInt("HM_FLG_1")));
		// 非会計2入力フラグ - HM_FLG_2
		bean.setUseNonAccount2(BooleanUtil.toBoolean(rs.getInt("HM_FLG_2")));
		// 非会計3入力フラグ - HM_FLG_3
		bean.setUseNonAccount3(BooleanUtil.toBoolean(rs.getInt("HM_FLG_3")));
		// 売上課税入力フラグ - URI_ZEI_FLG
		bean.setUseSalesTaxation(BooleanUtil.toBoolean(rs.getInt("URI_ZEI_FLG")));
		// 仕入課税入力フラグ - SIR_ZEI_FLG
		bean.setUsePurchaseTaxation(BooleanUtil.toBoolean(rs.getInt("SIR_ZEI_FLG")));
		// 多通貨入力フラグ - MCR_FLG
		bean.setUseForeignCurrency(BooleanUtil.toBoolean(rs.getInt("MCR_FLG")));
		// 評価替対象フラグ - EXC_FLG
		bean.setDoesCurrencyEvaluate(BooleanUtil.toBoolean(rs.getInt("EXC_FLG")));
		// 航海収支計算フラグ
		if (isUseVoyageCalcution) {
			bean.setUseVoyageCalculation(BooleanUtil.toBoolean(rs.getInt("HWR_FLG")));
		}
		// 発生日フラグ
		if (isUseOccurDate) {
			bean.setUseOccurDate(BooleanUtil.toBoolean(rs.getInt("HAS_FLG")));
		}

		// 消費税 - ZEI_CODE
		bean.setConsumptionTax(mappingConsumptionTax(rs));

		// 固定部門 - ZEI_CODE
		bean.setFixedDepartmentCode(rs.getString("KOTEI_DEP_CODE"));

		bean.setDc(Dc.getDc(rs.getInt("DC_KBN"))); // 貸借区分
		bean.setItemType(ItemType.get(rs.getInt("KMK_SHU"))); // 科目種別
		bean.setGlType(GLType.getGl(rs.getString("KMK_CNT_GL"))); // ＧＬ科目制御区分
		bean.setApType(APType.get(rs.getString("KMK_CNT_AP"))); // ＡＰ科目制御区分
		bean.setArType(ARType.get(rs.getString("KMK_CNT_AR"))); // ＡＲ科目制御区分
		bean.setBgType(BGType.get(rs.getString("KMK_CNT_BG"))); // ＢＧ科目制御区分
		bean.setDoesOffsetItem(BooleanUtil.toBoolean(rs.getInt("KMK_CNT_SOUSAI"))); // 相殺科目制御区分
		bean.setDoesBsOffset(BooleanUtil.toBoolean(rs.getInt("KESI_KBN"))); // BS勘定消込区分
		bean.setEvaluationMethod(EvaluationMethod.getEvaluationMethod(rs.getInt("EXC_FLG"))); // 評価替対象フラグ
		bean.setUseInputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_1"))); // 入金伝票入力フラグ
		bean.setUseOutputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_2"))); // 出金伝票入力フラグ
		bean.setUseTransferSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_3"))); // 振替伝票入力フラグ
		bean.setUseExpenseSettlementSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_1"))); // 経費精算伝票入力フラグ
		bean.setUsePaymentAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_2"))); // 債務計上伝票入力フラグ
		bean.setUseReceivableAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_1"))); // 債権計上伝票入力フラグ
		bean.setUseReceivableErasingSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_2"))); // 債権消込伝票入力フラグ
		bean.setUseAssetsEntrySlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_1"))); // 資産計上伝票入力フラグ
		bean.setUsePaymentRequestSlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_2"))); // 支払依頼伝票入力フラグ
		bean.setItemSumType(ItemSumType.get(rs.getInt("SUM_KBN"))); // 集計区分
		bean.setFixedDepartmentCode(rs.getString("DEP_CODE")); // 固定部門コード - KOTEI_DEP_CODE
		bean.setFixedDepartmentName(rs.getString("DEP_NAME")); // 固定部門名称 - KOTEI_DEP_NAME

		return bean;
	}

	public List<ItemOrganization> getItemOrganization(ItemOrganizationSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<ItemOrganization> list = new ArrayList<ItemOrganization>();

		try {

			String sql = " SELECT " + " kmt.KAI_CODE, " + " kmt.KMT_CODE, " + " kmt.KMT_NAME, " + " kmt.KMT_NAME_S, "
				+ " kmt.KMT_NAME_K " + " FROM " + " KMK_TK_MST kmt " + " WHERE 1 = 1 ";

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql = sql + " AND	kmt.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql = sql + " AND	kmt.KMT_CODE = " + SQLUtil.getParam(condition.getCode());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql = sql + " AND	kmt.KMT_CODE "
					+ SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
			}

			// 略称あいまい
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql = sql + " AND	kmt.KMT_NAME_S "
					+ SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
			}

			// 検索名称あいまい
			if (!Util.isNullOrEmpty(condition.getNamekLike())) {
				sql = sql + " AND	kmt.KMT_NAME_K "
					+ SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql = sql + " AND	kmt.KMT_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql = sql + " AND	kmt.KMT_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
			}

			sql = sql + " ORDER BY " + " kmt.KMT_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingItemOrganization(rs));
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
	 * O/Rマッピング(科目体系)
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected ItemOrganization mappingItemOrganization(ResultSet rs) throws Exception {

		ItemOrganization bean = createItemOrganization();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("KMT_CODE"));
		bean.setName(rs.getString("KMT_NAME"));
		bean.setNames(rs.getString("KMT_NAME_S"));
		bean.setNamek(rs.getString("KMT_NAME_K"));

		return bean;
	}

	/**
	 * データ登録処理(科目体系)
	 * 
	 * @param bean 登録データ
	 * @throws TException
	 */
	public void entryItemOrganization(ItemOrganization bean) throws TException {
		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add("");
			sql.add("INSERT INTO KMK_TK_MST (");
			sql.add("   KAI_CODE");
			sql.add("  ,KMT_CODE");
			sql.add("  ,KMT_NAME");
			sql.add("  ,KMT_NAME_S");
			sql.add("  ,KMT_NAME_K");
			sql.add("  ,INP_DATE");
			sql.add("  ,PRG_ID");
			sql.add("  ,USR_ID");
			sql.add(") VALUES (");
			sql.add("    ?,", getCompanyCode());
			sql.add("    ?,", bean.getCode());
			sql.add("    ?,", bean.getName());
			sql.add("    ?,", bean.getNames());
			sql.add("    ?,", bean.getNamek());
			sql.adt("    ?,", getNow());
			sql.add("    ?,", getProgramCode());
			sql.add("    ?", getUserCode());
			sql.add(")");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * データ更新処理(科目体系)
	 * 
	 * @param bean 更新データ
	 * @throws TException
	 */
	public void modifyItemOrganization(ItemOrganization bean) throws TException {

		// DB Connection 生成
		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add("UPDATE");
			sql.add("    KMK_TK_MST");
			sql.add("SET");
			sql.add("    KMT_NAME = ?", bean.getName());
			sql.add("    ,KMT_NAME_S = ?", bean.getNames());
			sql.add("    ,KMT_NAME_K = ?", bean.getNamek());
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			sql.add("AND");
			sql.add("    KMT_CODE = ?", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 科目体系名称マスタ削除
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteItemOrganization(ItemOrganization bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();
			sql.add(" DELETE FROM KMK_TK_MST ");
			sql.add(" WHERE	KAI_CODE = ? ", bean.getCompanyCode());
			sql.add(" AND	KMT_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 科目体系名称マスタ一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の科目体系名称マスタ一覧
	 * @throws TException
	 */
	public byte[] getExcelItemOrganization(ItemOrganizationSearchCondition condition) throws TException {

		List<ItemOrganization> ItemOrgList = getItemOrganization(condition);
		if (ItemOrgList == null || ItemOrgList.isEmpty()) {
			return null;
		}

		ItemOrganizationExcel layout = new ItemOrganizationExcel(getUser().getLanguage());
		byte[] data = layout.getExcel(ItemOrgList);

		return data;

	}

	/**
	 * 科目体系生成
	 * 
	 * @return 科目体系
	 */
	protected ItemOrganization createItemOrganization() {
		return new ItemOrganization();
	}

	/**
	 * 基本科目体系情報を返す
	 * 
	 * @param companyCode 会社コード
	 * @return 基本科目体系情報
	 * @throws TException
	 */
	public ItemOrganization getBaseItemOrganization(String companyCode) throws TException {

		ItemOrganizationSearchCondition condition = new ItemOrganizationSearchCondition();
		condition.setCompanyCode(companyCode);
		condition.setCode("00");

		List<ItemOrganization> list = getItemOrganization(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * @return 補助科目検索項目
	 */
	protected String getSubItemSelectColumn() {
		String sql = "";
		sql += " kmk.KAI_CODE, ";
		sql += " kmk.KMK_CODE, ";
		sql += " kmk.KMK_NAME, ";
		sql += " kmk.KMK_NAME_S, ";
		sql += " kmk.KMK_SHU,";
		sql += " kmk.DC_KBN,";
		sql += " kmk.KMK_CNT_GL,";
		sql += " kmk.KMK_CNT_AP,";
		sql += " kmk.KMK_CNT_AR,";
		sql += " kmk.KMK_CNT_BG,";
		sql += " kmk.KMK_CNT_SOUSAI,";
		sql += " kmk.KESI_KBN,";
		sql += (isUseVoyageCalcution ? " kmk.HWR_FLG," : "");
		sql += " hkm.HKM_CODE, ";
		sql += " hkm.HKM_NAME, ";
		sql += " hkm.HKM_NAME_S, ";
		sql += " hkm.HKM_NAME_K, ";
		sql += " hkm.UKM_KBN, ";
		// 固定部門
		if (isUseKoteiDep) {
			sql += " hkm.KOTEI_DEP_CODE HKM_KOTEI_DEP, ";
			sql += " bmn.DEP_NAME,";
		} else {
			sql += " NULL HKM_KOTEI_DEP, ";
			sql += " NULL DEP_NAME,";
		}
		sql += " hkm.ZEI_CODE, ";
		sql += " hkm.GL_FLG_1, ";
		sql += " hkm.GL_FLG_2, ";
		sql += " hkm.GL_FLG_3, ";
		sql += " hkm.AP_FLG_1, ";
		sql += " hkm.AP_FLG_2, ";
		sql += " hkm.AR_FLG_1, ";
		sql += " hkm.AR_FLG_2, ";
		sql += " hkm.FA_FLG_1, ";
		sql += " hkm.FA_FLG_2, ";
		sql += " hkm.TRI_CODE_FLG, ";
		sql += " hkm.EMP_CODE_FLG, ";
		sql += " hkm.KNR_FLG_1, ";
		sql += " hkm.KNR_FLG_2, ";
		sql += " hkm.KNR_FLG_3, ";
		sql += " hkm.KNR_FLG_4, ";
		sql += " hkm.KNR_FLG_5, ";
		sql += " hkm.KNR_FLG_6, ";
		sql += " hkm.HM_FLG_1, ";
		sql += " hkm.HM_FLG_2, ";
		sql += " hkm.HM_FLG_3, ";
		sql += " hkm.URI_ZEI_FLG, ";
		sql += " hkm.SIR_ZEI_FLG, ";
		sql += " hkm.MCR_FLG, ";
		sql += " hkm.EXC_FLG, ";
		sql += " hkm.HAS_FLG, ";
		sql += " hkm.STR_DATE, ";
		sql += " hkm.END_DATE, ";
		sql += " zei.ZEI_NAME, ";
		sql += " zei.ZEI_NAME_S, ";
		sql += " zei.ZEI_NAME_K, ";
		sql += " zei.ZEI_RATE, ";
		sql += " zei.US_KBN ZEI_US_KBN, ";
		sql += " zei.STR_DATE ZEI_STR_DATE, ";
		sql += " zei.END_DATE ZEI_END_DATE";

		return sql;
	}

	/**
	 * 指定条件に該当する補助科目情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する補助科目情報
	 * @throws TException
	 */
	public List<Item> get(SubItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Item> list = new ArrayList<Item>();

		try {

			String sql = " SELECT " + getSubItemSelectColumn() + " FROM " + " KMK_MST kmk ";

			if (condition.isGetNotExistSubItem()) {
				sql += " LEFT OUTER JOIN ";
			} else {
				sql += " INNER JOIN ";
			}
			sql += " HKM_MST hkm " + " ON	kmk.KAI_CODE = hkm.KAI_CODE " + " AND	kmk.KMK_CODE = hkm.KMK_CODE "
				+ " LEFT OUTER JOIN SZEI_MST zei" + "   ON zei.KAI_CODE = hkm.KAI_CODE"
				+ "  AND zei.ZEI_CODE = hkm.ZEI_CODE";
			if (isUseKoteiDep) {
				sql += " LEFT OUTER JOIN  BMN_MST bmn" + "   ON hkm.KAI_CODE = bmn.KAI_CODE"
					+ "  AND hkm.KOTEI_DEP_CODE = bmn.DEP_CODE";
			}
			sql += " WHERE 1 = 1 ";

			sql += getSubItemWhereSql(condition);

			sql = sql + " ORDER BY " + " kmk.KMK_CODE, " + " hkm.HKM_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingSubItem(rs));
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
	 * 補助科目のWHERE句SQL
	 * 
	 * @param condition 条件
	 * @return WHERE句SQL
	 */
	protected String getSubItemWhereSql(SubItemSearchCondition condition) {
		String sql = "";

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql = sql + " AND	kmk.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
		}

		// 科目コード
		if (!Util.isNullOrEmpty(condition.getItemCode())) {
			sql = sql + " AND	kmk.KMK_CODE = " + SQLUtil.getParam(condition.getItemCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql = sql + " AND	hkm.HKM_CODE = " + SQLUtil.getParam(condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql = sql + " AND	hkm.HKM_CODE " + SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
		}

		// 略称あいまい
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql = sql + " AND	hkm.HKM_NAME_S " + SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
		}

		// 検索名称あいまい
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql = sql + " AND	hkm.HKM_NAME_K " + SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql = sql + " AND	hkm.HKM_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql = sql + " AND	hkm.HKM_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
		}

		if (isUseKoteiDep) {
			// 固定部門
			if (!Util.isNullOrEmpty(condition.getDepartmentCode())) {
				sql = sql + " AND	(hkm.KOTEI_DEP_CODE IS NULL OR hkm.KOTEI_DEP_CODE = "
					+ SQLUtil.getParam(condition.getDepartmentCode()) + ")";
			}
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql + " AND	hkm.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm())
				+ " AND	hkm.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}

		// 補助科目を含むか
		if (condition.isSubItem()) {
			sql = sql + "AND kmk.HKM_KBN = 1 ";
		}

		// 内訳科目を含むか
		if (condition.isDetailItem()) {
			sql = sql + "AND hkm.UKM_KBN = 1 ";
		}

		// 集計科目
		if (!condition.isSumItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 1 ";
		}

		// 見出科目
		if (!condition.isTitleItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 2 ";
		}

		// 資金科目
		if (condition.isCash()) {
			sql = sql + " AND	kmk.KMK_CNT_GL = '04' ";
		}

		// 伝票入力区分
		if (condition.getSlipInputType() != null) {
			switch (condition.getSlipInputType()) {
				case InputCashFlowSlip: // 入金伝票入力
					sql = sql + " AND	hkm.GL_FLG_1 = 1";
					break;

				case OutputCashFlowSlip: // 出金伝票入力
					sql = sql + " AND	hkm.GL_FLG_2 = 1";
					break;

				case TransferSlip: // 振替伝票入力
				case ReversingSlip: // 振戻伝票入力
					sql = sql + " AND	hkm.GL_FLG_3 = 1";
					break;

				case ExpenseSettlementSlip: // 経費精算伝票入力
					sql = sql + " AND	hkm.AP_FLG_1 = 1";
					break;

				case PaymentAppropriateSlip: // 請求書伝票入力
					sql = sql + " AND	hkm.AP_FLG_2 = 1";
					break;

				case ReceivableAppropriateSlip: // 債権計上伝票入力
					sql = sql + " AND	hkm.AR_FLG_1 = 1";
					break;

				case ReceivableErasingSlip: // 債権消込伝票入力
					sql = sql + " AND	hkm.AR_FLG_2 = 1";
					break;

				case AssetsEntrySlip: // 資産計上伝票入力
					sql = sql + " AND	FA_FLG_1 = 1";
					break;

				case PaymentRequestSlip: // 支払依頼伝票入力
					sql = sql + " AND	hkm.FA_FLG_2 = 1";
					break;
			}
		}

		// 管理項目1を選択しているか
		if (condition.isUseManagement1()) {
			sql += " AND hkm.KNR_FLG_1 = 1 ";
		}
		// 管理項目2を選択しているか
		if (condition.isUseManagement2()) {
			sql += " AND hkm.KNR_FLG_2 = 1 ";
		}
		// 管理項目3を選択しているか
		if (condition.isUseManagement3()) {
			sql += " AND hkm.KNR_FLG_3 = 1 ";
		}
		// 管理項目4を選択しているか
		if (condition.isUseManagement4()) {
			sql += " AND hkm.KNR_FLG_4 = 1 ";
		}
		// 管理項目5を選択しているか
		if (condition.isUseManagement5()) {
			sql += " AND hkm.KNR_FLG_5 = 1 ";
		}
		// 管理項目6を選択しているか
		if (condition.isUseManagement6()) {
			sql += " AND hkm.KNR_FLG_6 = 1 ";
		}

		// 管理項目1を非選択にしているか
		if (condition.isNotUseManagement1()) {
			sql += " AND hkm.KNR_FLG_1 = 0 ";
		}
		// 管理項目2を非選択にしているか
		if (condition.isNotUseManagement2()) {
			sql += " AND hkm.KNR_FLG_2 = 0 ";
		}
		// 管理項目3を非選択にしているか
		if (condition.isNotUseManagement3()) {
			sql += " AND hkm.KNR_FLG_3 = 0 ";
		}
		// 管理項目4を非選択にしているか
		if (condition.isNotUseManagement4()) {
			sql += " AND hkm.KNR_FLG_4 = 0 ";
		}
		// 管理項目5を非選択にしているか
		if (condition.isNotUseManagement5()) {
			sql += " AND hkm.KNR_FLG_5 = 0 ";
		}
		// 管理項目6を非選択にしているか
		if (condition.isNotUseManagement6()) {
			sql += " AND hkm.KNR_FLG_6 = 0 ";
		}
		// 多通貨入力が可能か
		if (condition.isUseForeignCurrency()) {
			sql += " AND hkm.MCR_FLG = 1 ";
		}
		// 社員入力が可能か
		if (condition.isUseEmployee()) {
			sql += " AND hkm.EMP_CODE_FLG = 1 ";
		}
		// 社員入力を非選択にしているか
		if (condition.isNoUseEmployee()) {
			sql += " AND hkm.EMP_CODE_FLG = 0 ";
		}
		// 消費税が入力されているか
		if (condition.isFlgZeiCode()) {
			sql += " AND hkm.ZEI_CODE IS NOT NULL ";
		}

		// 科目体系
		if (!Util.isNullOrEmpty(condition.getItemOrganizationCode())) {
			sql += "AND EXISTS ( " + " SELECT 1 " + " FROM 	KMK_SUM_MST kmksum "
				+ " WHERE kmk.KAI_CODE = kmksum.KAI_CODE " + " AND	kmk.KMK_CODE = kmksum.KMK_CODE "
				+ " AND	kmksum.KMT_CODE = " + SQLUtil.getParam(condition.getItemOrganizationCode()) + " ) ";
		}

		// 追加条件
		if (!Util.isNullOrEmpty(condition.getAddonWhereSql())) {
			sql += condition.getAddonWhereSql();
		}

		return sql;
	}

	/**
	 * O/Rマッピング(補助科目)
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Item mappingSubItem(ResultSet rs) throws Exception {

		Item item = createItem();

		item.setCompanyCode(rs.getString("KAI_CODE"));
		item.setCode(rs.getString("KMK_CODE"));
		item.setName(rs.getString("KMK_NAME"));
		item.setNames(rs.getString("KMK_NAME_S"));
		item.setFixedDepartmentCode(rs.getString("HKM_KOTEI_DEP")); // 固定部門
		item.setDc(Dc.getDc(rs.getInt("DC_KBN"))); // 貸借区分
		item.setItemType(ItemType.get(rs.getInt("KMK_SHU"))); // 科目種別
		item.setGlType(GLType.getGl(rs.getString("KMK_CNT_GL"))); // ＧＬ科目制御区分
		item.setApType(APType.get(rs.getString("KMK_CNT_AP"))); // ＡＰ科目制御区分
		item.setArType(ARType.get(rs.getString("KMK_CNT_AR"))); // ＡＲ科目制御区分
		item.setBgType(BGType.get(rs.getString("KMK_CNT_BG"))); // ＢＧ科目制御区分
		item.setDoesOffsetItem(BooleanUtil.toBoolean(rs.getInt("KMK_CNT_SOUSAI"))); // 相殺科目制御区分
		item.setDoesBsOffset(BooleanUtil.toBoolean(rs.getInt("KESI_KBN"))); // BS勘定消込区分
		// 航海収支計算フラグ
		if (isUseVoyageCalcution) {
			item.setUseVoyageCalculation(BooleanUtil.toBoolean(rs.getInt("HWR_FLG")));
		}

		SubItem subItem = createSubItem();
		subItem.setCompanyCode(rs.getString("KAI_CODE"));
		subItem.setCode(rs.getString("HKM_CODE"));
		subItem.setName(rs.getString("HKM_NAME"));
		subItem.setNames(rs.getString("HKM_NAME_S"));
		subItem.setNamek(rs.getString("HKM_NAME_K"));
		subItem.setDateFrom(rs.getDate("STR_DATE"));
		subItem.setDateTo(rs.getDate("END_DATE"));
		subItem.setDetailItem(rs.getInt("UKM_KBN") == 1);

		if (isUseKoteiDep) {
			subItem.setFixedDepartmentCode(rs.getString("HKM_KOTEI_DEP")); // 固定部門 - DEP_CODE
			subItem.setFixedDepartmentName(rs.getString("DEP_NAME")); // 固定部門名称 - KOTEI_DEP_NAME
		}

		// 取引先入力フラグ - TRI_CODE_FLG
		subItem.setClientType(CustomerType.get(rs.getInt("TRI_CODE_FLG")));
		// 社員入力フラグ - EMP_CODE_FLG
		subItem.setUseEmployee(BooleanUtil.toBoolean(rs.getInt("EMP_CODE_FLG")));
		// 管理１入力フラグ - KNR_FLG_1
		subItem.setUseManagement1(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_1")));
		// 管理２入力フラグ - KNR_FLG_2
		subItem.setUseManagement2(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_2")));
		// 管理３入力フラグ - KNR_FLG_3
		subItem.setUseManagement3(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_3")));
		// 管理４入力フラグ - KNR_FLG_4
		subItem.setUseManagement4(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_4")));
		// 管理５入力フラグ - KNR_FLG_5
		subItem.setUseManagement5(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_5")));
		// 管理６入力フラグ - KNR_FLG_6
		subItem.setUseManagement6(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_6")));
		// 非会計1入力フラグ - HM_FLG_1
		subItem.setUseNonAccount1(BooleanUtil.toBoolean(rs.getInt("HM_FLG_1")));
		// 非会計2入力フラグ - HM_FLG_2
		subItem.setUseNonAccount2(BooleanUtil.toBoolean(rs.getInt("HM_FLG_2")));
		// 非会計3入力フラグ - HM_FLG_3
		subItem.setUseNonAccount3(BooleanUtil.toBoolean(rs.getInt("HM_FLG_3")));
		// 売上課税入力フラグ - URI_ZEI_FLG
		subItem.setUseSalesTaxation(BooleanUtil.toBoolean(rs.getInt("URI_ZEI_FLG")));
		// 仕入課税入力フラグ - SIR_ZEI_FLG
		subItem.setUsePurchaseTaxation(BooleanUtil.toBoolean(rs.getInt("SIR_ZEI_FLG")));
		// 多通貨入力フラグ - MCR_FLG
		subItem.setUseForeignCurrency(BooleanUtil.toBoolean(rs.getInt("MCR_FLG")));
		// 評価替対象フラグ - EXC_FLG
		subItem.setDoesCurrencyEvaluate(BooleanUtil.toBoolean(rs.getInt("EXC_FLG")));

		// 消費税 - ZEI_CODE
		subItem.setConsumptionTax(mappingConsumptionTax(rs));

		// 入金伝票入力フラグ - GL_FLG_1
		subItem.setUseInputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_1")));
		// 出金伝票入力フラグ - GL_FLG_2
		subItem.setUseOutputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_2")));
		// 振替伝票入力フラグ - GL_FLG_3
		subItem.setUseTransferSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_3")));
		// 経費精算伝票入力フラグ - AP_FLG_1
		subItem.setUseExpenseSettlementSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_1")));
		// 請求書伝票入力フラグ - AP_FLG_2
		subItem.setUsePaymentAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_2")));
		// 債権計上伝票入力フラグ - AR_FLG_1
		subItem.setUseReceivableAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_1")));
		// 債権消込伝票入力フラグ - AR_FLG_2
		subItem.setUseReceivableErasingSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_2")));
		// 資産計上伝票入力フラグ - FA_FLG_1
		subItem.setUseAssetsEntrySlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_1")));
		// 支払依頼伝票入力フラグ - FA_FLG_2
		subItem.setUsePaymentRequestSlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_2")));
		// 発生日フラグ
		if (isUseOccurDate) {
			subItem.setUseOccurDate(BooleanUtil.toBoolean(rs.getInt("HAS_FLG")));
		}

		item.setSubItem(subItem);

		return item;

	}

	/**
	 * 内訳科目削除処理
	 */
	public void deleteDetailItem(Item detailItem) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.DETAIL_ITEM);
		condition.setCompanyCode(detailItem.getDetailItem().getCompanyCode());
		condition.setItemCode(detailItem.getCode());
		condition.setSubItemCode(detailItem.getSubItem().getCode());
		condition.setDetailItemCode(detailItem.getDetailItem().getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM UKM_MST");
			s.add("WHERE KAI_CODE = ?", detailItem.getDetailItem().getCompanyCode());
			s.add("  AND KMK_CODE = ?", detailItem.getCode());
			s.add("  AND HKM_CODE = ?", detailItem.getSubItem().getCode());
			s.add("  AND UKM_CODE = ?", detailItem.getDetailItem().getCode());

			DBUtil.execute(conn, s);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 内訳科目新規登録
	 */
	public void entryDetailItem(Item detailItem) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO UKM_MST (");
			s.add("  KAI_CODE,");
			s.add("  KMK_CODE,");
			s.add("  HKM_CODE,");
			s.add("  UKM_CODE,");
			s.add("  UKM_NAME,");
			s.add("  UKM_NAME_S,");
			s.add("  UKM_NAME_K,");
			s.add("  ZEI_CODE,");
			s.add("  GL_FLG_1,");
			s.add("  GL_FLG_2,");
			s.add("  GL_FLG_3,");
			s.add("  AP_FLG_1,");
			s.add("  AP_FLG_2,");
			s.add("  AR_FLG_1,");
			s.add("  AR_FLG_2,");
			s.add("  FA_FLG_1,");
			s.add("  FA_FLG_2,");
			s.add("  TRI_CODE_FLG,");
			s.add("  EMP_CODE_FLG,");
			s.add("  KNR_FLG_1,");
			s.add("  KNR_FLG_2,");
			s.add("  KNR_FLG_3,");
			s.add("  KNR_FLG_4,");
			s.add("  KNR_FLG_5,");
			s.add("  KNR_FLG_6,");
			s.add("  HM_FLG_1,");
			s.add("  HM_FLG_2,");
			s.add("  HM_FLG_3,");
			s.add("  URI_ZEI_FLG,");
			s.add("  SIR_ZEI_FLG,");
			s.add("  MCR_FLG,");
			s.add("  EXC_FLG,");
			s.add("  HAS_FLG,");
			s.add("  STR_DATE,");
			s.add("  END_DATE,");
			s.add("  INP_DATE,");
			s.add("  PRG_ID,");
			s.add("  USR_ID");
			s.add(") VALUES (");
			s.add("  ?,", detailItem.getDetailItem().getCompanyCode());
			s.add("  ?,", detailItem.getCode());
			s.add("  ?,", detailItem.getSubItem().getCode());
			s.add("  ?,", detailItem.getDetailItem().getCode());
			s.add("  ?,", detailItem.getDetailItem().getName());
			s.add("  ?,", detailItem.getDetailItem().getNames());
			s.add("  ?,", detailItem.getDetailItem().getNamek());
			s.add("  ?,", detailItem.getDetailItem().getConsumptionTax().getCode());
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseInputCashFlowSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseOutputCashFlowSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseTransferSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseExpenseSettlementSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePaymentAppropriateSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseReceivableAppropriateSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseReceivableErasingSlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseAssetsEntrySlip()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePaymentRequestSlip()));
			s.add("  ?,", detailItem.getDetailItem().getClientType().value);
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseEmployee()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement1()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement2()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement3()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement4()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement5()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement6()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount1()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount2()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount3()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseSalesTaxation()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePurchaseTaxation()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseForeignCurrency()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isDoesCurrencyEvaluate()));
			s.add("  ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseOccurDate()));
			s.add("  ?,", detailItem.getDetailItem().getDateFrom());
			s.add("  ?,", detailItem.getDetailItem().getDateTo());
			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 内訳科目エクセル
	 */
	public byte[] getDetailItemExcel(DetailItemSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Item> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}
			DetailItemExcel exl = new DetailItemExcel(getUser().getLanguage(), getCompany());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 内訳科目編集処理
	 */
	public void modifyDetailItem(Item detailItem) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("  UPDATE UKM_MST SET");
			s.add("  UKM_NAME = ?,", detailItem.getDetailItem().getName());
			s.add("  UKM_NAME_S = ?,", detailItem.getDetailItem().getNames());
			s.add("  UKM_NAME_K = ?,", detailItem.getDetailItem().getNamek());
			s.add("  ZEI_CODE = ?,", detailItem.getDetailItem().getConsumptionTax().getCode());
			s.add("  GL_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseInputCashFlowSlip()));
			s.add("  GL_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseOutputCashFlowSlip()));
			s.add("  GL_FLG_3 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseTransferSlip()));
			s.add("  AP_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseExpenseSettlementSlip()));
			s.add("  AP_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePaymentAppropriateSlip()));
			s.add("  AR_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseReceivableAppropriateSlip()));
			s.add("  AR_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseReceivableErasingSlip()));
			s.add("  FA_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseAssetsEntrySlip()));
			s.add("  FA_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePaymentRequestSlip()));
			s.add("  TRI_CODE_FLG = ?,", detailItem.getDetailItem().getClientType().value);
			s.add("  EMP_CODE_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseEmployee()));
			s.add("  KNR_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement1()));
			s.add("  KNR_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement2()));
			s.add("  KNR_FLG_3 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement3()));
			s.add("  KNR_FLG_4 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement4()));
			s.add("  KNR_FLG_5 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement5()));
			s.add("  KNR_FLG_6 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseManagement6()));
			s.add("  HM_FLG_1 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount1()));
			s.add("  HM_FLG_2 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount2()));
			s.add("  HM_FLG_3 = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseNonAccount3()));
			s.add("  URI_ZEI_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseSalesTaxation()));
			s.add("  SIR_ZEI_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUsePurchaseTaxation()));
			s.add("  MCR_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseForeignCurrency()));
			s.add("  EXC_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isDoesCurrencyEvaluate()));
			s.add("  HAS_FLG = ?,", BooleanUtil.toInt(detailItem.getDetailItem().isUseOccurDate()));
			s.add("  STR_DATE = ?,", detailItem.getDetailItem().getDateFrom());
			s.add("  END_DATE = ?,", detailItem.getDetailItem().getDateTo());
			s.addYMDHMS("  UPD_DATE = ?,", getNow());
			s.add("  PRG_ID = ?,", getProgramCode());
			s.add("  USR_ID = ?", getUserCode());
			s.add("WHERE KAI_CODE = ?", detailItem.getDetailItem().getCompanyCode());
			s.add(" AND KMK_CODE = ?", detailItem.getCode());
			s.add(" AND HKM_CODE = ?", detailItem.getSubItemCode());
			s.add(" AND UKM_CODE = ?", detailItem.getDetailItemCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * @return 内訳科目検索項目
	 */
	protected String getDetailItemSelectColumn() {
		String sql = "";
		sql += " kmk.KAI_CODE, ";
		sql += " kmk.KMK_CODE, ";
		sql += " hkm.HKM_CODE, ";
		sql += " kmk.KMK_NAME, ";
		sql += " kmk.KMK_NAME_S, ";
		sql += " kmk.KOTEI_DEP_CODE, ";
		sql += " kmk.KMK_SHU,";
		sql += " kmk.DC_KBN,";
		sql += " kmk.KMK_CNT_GL,";
		sql += " kmk.KMK_CNT_AP,";
		sql += " kmk.KMK_CNT_AR,";
		sql += " kmk.KMK_CNT_BG,";
		sql += " kmk.KMK_CNT_SOUSAI,";
		sql += " kmk.KESI_KBN,";
		sql += (isUseVoyageCalcution ? " kmk.HWR_FLG," : "");
		sql += " hkm.HKM_NAME, ";
		sql += " hkm.HKM_NAME_S, ";
		// 固定部門
		if (isUseKoteiDep) {
			sql += " hkm.KOTEI_DEP_CODE HKM_KOTEI_DEP, ";
			sql += " bmn.DEP_NAME,";
		} else {
			sql += " NULL HKM_KOTEI_DEP, ";
			sql += " NULL DEP_NAME,";
		}
		sql += " ukm.UKM_CODE, ";
		sql += " ukm.UKM_NAME, ";
		sql += " ukm.UKM_NAME_S, ";
		sql += " ukm.UKM_NAME_K, ";
		sql += " ukm.ZEI_CODE, ";
		sql += " ukm.GL_FLG_1, ";
		sql += " ukm.GL_FLG_2, ";
		sql += " ukm.GL_FLG_3, ";
		sql += " ukm.AP_FLG_1, ";
		sql += " ukm.AP_FLG_2, ";
		sql += " ukm.AR_FLG_1, ";
		sql += " ukm.AR_FLG_2, ";
		sql += " ukm.FA_FLG_1, ";
		sql += " ukm.FA_FLG_2, ";
		sql += " ukm.TRI_CODE_FLG, ";
		sql += " ukm.EMP_CODE_FLG, ";
		sql += " ukm.KNR_FLG_1, ";
		sql += " ukm.KNR_FLG_2, ";
		sql += " ukm.KNR_FLG_3, ";
		sql += " ukm.KNR_FLG_4, ";
		sql += " ukm.KNR_FLG_5, ";
		sql += " ukm.KNR_FLG_6, ";
		sql += " ukm.HM_FLG_1, ";
		sql += " ukm.HM_FLG_2, ";
		sql += " ukm.HM_FLG_3, ";
		sql += " ukm.URI_ZEI_FLG, ";
		sql += " ukm.SIR_ZEI_FLG, ";
		sql += " ukm.MCR_FLG, ";
		sql += " ukm.EXC_FLG, ";
		sql += " ukm.HAS_FLG, ";
		sql += " ukm.STR_DATE, ";
		sql += " ukm.END_DATE, ";
		sql += " zei.ZEI_NAME, ";
		sql += " zei.ZEI_NAME_S, ";
		sql += " zei.ZEI_NAME_K, ";
		sql += " zei.ZEI_RATE, ";
		sql += " zei.US_KBN ZEI_US_KBN, ";
		sql += " zei.STR_DATE ZEI_STR_DATE, ";
		sql += " zei.END_DATE ZEI_END_DATE";

		return sql;
	}

	/**
	 * 指定条件に該当する内訳科目情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する内訳科目情報
	 * @throws TException
	 */
	public List<Item> get(DetailItemSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Item> list = new ArrayList<Item>();

		try {

			String sql = " SELECT " + getDetailItemSelectColumn() + " FROM " + " KMK_MST kmk ";

			if (condition.isGetNotExistDetailItem()) {
				sql += " LEFT OUTER JOIN ";
			} else {
				sql += " INNER JOIN ";
			}
			sql += " HKM_MST hkm " + " ON	kmk.KAI_CODE = hkm.KAI_CODE " + " AND	kmk.KMK_CODE = hkm.KMK_CODE ";
			if (isUseKoteiDep) {
				sql += " LEFT OUTER JOIN  BMN_MST bmn" + "   ON hkm.KAI_CODE = bmn.KAI_CODE"
					+ "  AND hkm.KOTEI_DEP_CODE = bmn.DEP_CODE";
			}

			if (condition.isGetNotExistDetailItem()) {
				sql += " LEFT OUTER JOIN ";
			} else {
				sql += " INNER JOIN ";
			}
			sql += " UKM_MST ukm " + " ON	hkm.KAI_CODE = ukm.KAI_CODE " + " AND	hkm.KMK_CODE = ukm.KMK_CODE "
				+ " AND	hkm.HKM_CODE = ukm.HKM_CODE " + " LEFT OUTER JOIN SZEI_MST zei"
				+ " ON 	ukm.KAI_CODE = zei.KAI_CODE " + " AND 	ukm.ZEI_CODE = zei.ZEI_CODE " + " WHERE 1 = 1 ";

			sql += getDetailItemWhereSql(condition);

			sql = sql + " ORDER BY " + " kmk.KMK_CODE, " + " hkm.HKM_CODE, " + " ukm.UKM_CODE ";

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingDetailItem(rs));
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
	 * 内訳科目WHERE句SQL
	 * 
	 * @param condition
	 * @return WHERE句SQL
	 */
	protected String getDetailItemWhereSql(DetailItemSearchCondition condition) {
		String sql = "";

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql = sql + " AND	kmk.KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode());
		}

		// 科目コード
		if (!Util.isNullOrEmpty(condition.getItemCode())) {
			sql = sql + " AND	kmk.KMK_CODE = " + SQLUtil.getParam(condition.getItemCode());
		}

		// 補助科目コード
		if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
			sql = sql + " AND	hkm.HKM_CODE = " + SQLUtil.getParam(condition.getSubItemCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql = sql + " AND	ukm.UKM_CODE = " + SQLUtil.getParam(condition.getCode());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql = sql + " AND	ukm.UKM_CODE " + SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT);
		}

		// 略称あいまい
		if (!Util.isNullOrEmpty(condition.getNamesLike())) {
			sql = sql + " AND	ukm.UKM_NAME_S " + SQLUtil.getLikeStatement(condition.getNamesLike(), SQLUtil.NCHAR_AMBI);
		}

		// 検索名称あいまい
		if (!Util.isNullOrEmpty(condition.getNamekLike())) {
			sql = sql + " AND	ukm.UKM_NAME_K " + SQLUtil.getLikeStatement(condition.getNamekLike(), SQLUtil.NCHAR_AMBI);
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql = sql + " AND	ukm.UKM_CODE >= " + SQLUtil.getParam(condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql = sql + " AND	ukm.UKM_CODE <= " + SQLUtil.getParam(condition.getCodeTo());
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql = sql + " AND	ukm.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm())
				+ " AND	ukm.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm());
		}

		// 補助科目を含むか
		if (condition.isSubItem()) {
			sql = sql + "AND kmk.HKM_KBN = 1 ";
		}

		// 内訳科目を含むか
		if (condition.isDetailItem()) {
			sql = sql + "AND hkm.UKM_KBN = 1 ";
		}

		// 集計科目
		if (!condition.isSumItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 1 ";
		}

		// 見出科目
		if (!condition.isTitleItem()) {
			sql = sql + " AND	kmk.SUM_KBN <> 2 ";
		}

		// 資金科目
		if (condition.isCash()) {
			sql = sql + " AND	kmk.KMK_CNT_GL = '04' ";
		}

		// 伝票入力区分
		if (condition.getSlipInputType() != null) {
			switch (condition.getSlipInputType()) {
				case InputCashFlowSlip: // 入金伝票入力
					sql = sql + " AND	ukm.GL_FLG_1 = 1";
					break;

				case OutputCashFlowSlip: // 出金伝票入力
					sql = sql + " AND	ukm.GL_FLG_2 = 1";
					break;

				case TransferSlip: // 振替伝票入力
				case ReversingSlip: // 振戻伝票入力
					sql = sql + " AND	ukm.GL_FLG_3 = 1";
					break;

				case ExpenseSettlementSlip: // 経費精算伝票入力
					sql = sql + " AND	ukm.AP_FLG_1 = 1";
					break;

				case PaymentAppropriateSlip: // 請求書伝票入力(債務計上)
					sql = sql + " AND	ukm.AP_FLG_2 = 1";
					break;

				case ReceivableAppropriateSlip: // 債権計上伝票入力
					sql = sql + " AND	ukm.AR_FLG_1 = 1";
					break;

				case ReceivableErasingSlip: // 債権消込伝票入力
					sql = sql + " AND	ukm.AR_FLG_2 = 1";
					break;

				case AssetsEntrySlip: // 資産計上伝票入力
					sql = sql + " AND	ukm.FA_FLG_1 = 1";
					break;

				case PaymentRequestSlip: // 支払依頼伝票入力
					sql = sql + " AND	ukm.FA_FLG_2 = 1";
					break;
			}
		}

		// 管理項目1を選択しているか
		if (condition.isUseManagement1()) {
			sql += " AND ukm.KNR_FLG_1 = 1 ";
		}
		// 管理項目2を選択しているか
		if (condition.isUseManagement2()) {
			sql += " AND ukm.KNR_FLG_2 = 1 ";
		}
		// 管理項目3を選択しているか
		if (condition.isUseManagement3()) {
			sql += " AND ukm.KNR_FLG_3 = 1 ";
		}
		// 管理項目4を選択しているか
		if (condition.isUseManagement4()) {
			sql += " AND ukm.KNR_FLG_4 = 1 ";
		}
		// 管理項目5を選択しているか
		if (condition.isUseManagement5()) {
			sql += " AND ukm.KNR_FLG_5 = 1 ";
		}
		// 管理項目6を選択しているか
		if (condition.isUseManagement6()) {
			sql += " AND ukm.KNR_FLG_6 = 1 ";
		}

		// 管理項目1を非選択にしているか
		if (condition.isNotUseManagement1()) {
			sql += " AND ukm.KNR_FLG_1 = 0 ";
		}
		// 管理項目2を非選択にしているか
		if (condition.isNotUseManagement2()) {
			sql += " AND ukm.KNR_FLG_2 = 0 ";
		}
		// 管理項目3を非選択にしているか
		if (condition.isNotUseManagement3()) {
			sql += " AND ukm.KNR_FLG_3 = 0 ";
		}
		// 管理項目4を非選択にしているか
		if (condition.isNotUseManagement4()) {
			sql += " AND ukm.KNR_FLG_4 = 0 ";
		}
		// 管理項目5を非選択にしているか
		if (condition.isNotUseManagement5()) {
			sql += " AND ukm.KNR_FLG_5 = 0 ";
		}
		// 管理項目6を非選択にしているか
		if (condition.isNotUseManagement6()) {
			sql += " AND ukm.KNR_FLG_6 = 0 ";
		}
		// 多通貨入力が可能か
		if (condition.isUseForeignCurrency()) {
			sql += " AND ukm.MCR_FLG = 1 ";
		}
		// 社員入力が可能か
		if (condition.isUseEmployee()) {
			sql += " AND ukm.EMP_CODE_FLG = 1 ";
		}
		// 社員入力を非選択にしているか
		if (condition.isNoUseEmployee()) {
			sql += " AND ukm.EMP_CODE_FLG = 0 ";
		}
		// 消費税が入力されているか
		if (condition.isFlgZeiCode()) {
			sql += " AND ukm.ZEI_CODE IS NOT NULL ";
		}

		// 科目体系
		if (!Util.isNullOrEmpty(condition.getItemOrganizationCode())) {
			sql += "AND EXISTS ( " + " SELECT 1 " + " FROM 	KMK_SUM_MST kmksum "
				+ " WHERE kmk.KAI_CODE = kmksum.KAI_CODE " + " AND	kmk.KMK_CODE = kmksum.KMK_CODE "
				+ " AND	kmksum.KMT_CODE = " + SQLUtil.getParam(condition.getItemOrganizationCode()) + " ) ";
		}

		// 追加条件
		if (!Util.isNullOrEmpty(condition.getAddonWhereSql())) {
			sql += condition.getAddonWhereSql();
		}

		return sql;
	}

	/**
	 * O/Rマッピング(内訳科目)
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected Item mappingDetailItem(ResultSet rs) throws Exception {

		Item item = createItem();

		item.setCompanyCode(rs.getString("KAI_CODE"));
		item.setCode(rs.getString("KMK_CODE"));
		item.setName(rs.getString("KMK_NAME"));
		item.setNames(rs.getString("KMK_NAME_S"));
		item.setDc(Dc.getDc(rs.getInt("DC_KBN"))); // 貸借区分
		item.setItemType(ItemType.get(rs.getInt("KMK_SHU"))); // 科目種別
		item.setGlType(GLType.getGl(rs.getString("KMK_CNT_GL"))); // ＧＬ科目制御区分
		item.setApType(APType.get(rs.getString("KMK_CNT_AP"))); // ＡＰ科目制御区分
		item.setArType(ARType.get(rs.getString("KMK_CNT_AR"))); // ＡＲ科目制御区分
		item.setBgType(BGType.get(rs.getString("KMK_CNT_BG"))); // ＢＧ科目制御区分
		item.setDoesOffsetItem(BooleanUtil.toBoolean(rs.getInt("KMK_CNT_SOUSAI"))); // 相殺科目制御区分
		item.setDoesBsOffset(BooleanUtil.toBoolean(rs.getInt("KESI_KBN"))); // BS勘定消込区分
		item.setFixedDepartmentCode(rs.getString("KOTEI_DEP_CODE")); // 固定部門 - ZEI_CODE
		// 航海収支計算フラグ
		if (isUseVoyageCalcution) {
			item.setUseVoyageCalculation(BooleanUtil.toBoolean(rs.getInt("HWR_FLG")));
		}

		SubItem subItem = createSubItem();
		subItem.setCompanyCode(rs.getString("KAI_CODE"));
		subItem.setCode(rs.getString("HKM_CODE"));
		subItem.setName(rs.getString("HKM_NAME"));
		subItem.setNames(rs.getString("HKM_NAME_S"));
		if (isUseKoteiDep) {
			subItem.setFixedDepartmentCode(rs.getString("HKM_KOTEI_DEP")); // 固定部門 - DEP_CODE
			subItem.setFixedDepartmentName(rs.getString("DEP_NAME")); // 固定部門名称 - KOTEI_DEP_NAME
		}
		item.setSubItem(subItem);

		DetailItem detailItem = createDetailItem();
		detailItem.setCompanyCode(rs.getString("KAI_CODE"));
		detailItem.setCode(rs.getString("UKM_CODE"));
		detailItem.setName(rs.getString("UKM_NAME"));
		detailItem.setNames(rs.getString("UKM_NAME_S"));
		detailItem.setNamek(rs.getString("UKM_NAME_K"));
		detailItem.setDateFrom(rs.getDate("STR_DATE"));
		detailItem.setDateTo(rs.getDate("END_DATE"));

		// 取引先入力フラグ - TRI_CODE_FLG
		detailItem.setClientType(CustomerType.get(rs.getInt("TRI_CODE_FLG")));
		// 社員入力フラグ - EMP_CODE_FLG
		detailItem.setUseEmployee(BooleanUtil.toBoolean(rs.getInt("EMP_CODE_FLG")));
		// 管理１入力フラグ - KNR_FLG_1
		detailItem.setUseManagement1(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_1")));
		// 管理２入力フラグ - KNR_FLG_2
		detailItem.setUseManagement2(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_2")));
		// 管理３入力フラグ - KNR_FLG_3
		detailItem.setUseManagement3(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_3")));
		// 管理４入力フラグ - KNR_FLG_4
		detailItem.setUseManagement4(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_4")));
		// 管理５入力フラグ - KNR_FLG_5
		detailItem.setUseManagement5(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_5")));
		// 管理６入力フラグ - KNR_FLG_6
		detailItem.setUseManagement6(BooleanUtil.toBoolean(rs.getInt("KNR_FLG_6")));
		// 非会計1入力フラグ - HM_FLG_1
		detailItem.setUseNonAccount1(BooleanUtil.toBoolean(rs.getInt("HM_FLG_1")));
		// 非会計2入力フラグ - HM_FLG_2
		detailItem.setUseNonAccount2(BooleanUtil.toBoolean(rs.getInt("HM_FLG_2")));
		// 非会計3入力フラグ - HM_FLG_3
		detailItem.setUseNonAccount3(BooleanUtil.toBoolean(rs.getInt("HM_FLG_3")));
		// 売上課税入力フラグ - URI_ZEI_FLG
		detailItem.setUseSalesTaxation(BooleanUtil.toBoolean(rs.getInt("URI_ZEI_FLG")));
		// 仕入課税入力フラグ - SIR_ZEI_FLG
		detailItem.setUsePurchaseTaxation(BooleanUtil.toBoolean(rs.getInt("SIR_ZEI_FLG")));
		// 多通貨入力フラグ - MCR_FLG
		detailItem.setUseForeignCurrency(BooleanUtil.toBoolean(rs.getInt("MCR_FLG")));
		// 評価替対象フラグ - EXC_FLG
		detailItem.setDoesCurrencyEvaluate(BooleanUtil.toBoolean(rs.getInt("EXC_FLG")));

		// 消費税 - ZEI_CODE
		detailItem.setConsumptionTax(mappingConsumptionTax(rs));

		// 入金伝票入力フラグ - GL_FLG_1
		detailItem.setUseInputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_1")));
		// 出金伝票入力フラグ - GL_FLG_2
		detailItem.setUseOutputCashFlowSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_2")));
		// 振替伝票入力フラグ - GL_FLG_3
		detailItem.setUseTransferSlip(BooleanUtil.toBoolean(rs.getInt("GL_FLG_3")));
		// 経費精算伝票入力フラグ - AP_FLG_1
		detailItem.setUseExpenseSettlementSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_1")));
		// 請求書伝票入力フラグ - AP_FLG_2
		detailItem.setUsePaymentAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AP_FLG_2")));
		// 債権計上伝票入力フラグ - AR_FLG_1
		detailItem.setUseReceivableAppropriateSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_1")));
		// 債権消込伝票入力フラグ - AR_FLG_2
		detailItem.setUseReceivableErasingSlip(BooleanUtil.toBoolean(rs.getInt("AR_FLG_2")));
		// 資産計上伝票入力フラグ - FA_FLG_1
		detailItem.setUseAssetsEntrySlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_1")));
		// 支払依頼伝票入力フラグ - FA_FLG_2
		detailItem.setUsePaymentRequestSlip(BooleanUtil.toBoolean(rs.getInt("FA_FLG_2")));
		// 発生日フラグ
		if (isUseOccurDate) {
			detailItem.setUseOccurDate(BooleanUtil.toBoolean(rs.getInt("HAS_FLG")));
		}

		item.getSubItem().setDetailItem(detailItem);

		return item;
	}

	/**
	 * 消費税O/Rマッピング
	 * 
	 * @param rs
	 * @return 消費税エンティティ
	 * @throws Exception
	 */
	protected ConsumptionTax mappingConsumptionTax(ResultSet rs) throws Exception {

		ConsumptionTax bean = new ConsumptionTax();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCode(rs.getString("ZEI_CODE"));
		bean.setName(rs.getString("ZEI_NAME"));
		bean.setNames(rs.getString("ZEI_NAME_S"));
		bean.setNamek(rs.getString("ZEI_NAME_K"));
		bean.setRate(rs.getBigDecimal("ZEI_RATE"));
		bean.setDateFrom(rs.getDate("ZEI_STR_DATE"));
		bean.setDateTo(rs.getDate("ZEI_END_DATE"));
		bean.setTaxType(TaxType.get(rs.getInt("ZEI_US_KBN")));

		return bean;
	}

	/**
	 * 科目情報を返す ※nullが返る場合に要注意
	 * 
	 * @param company 会社コード
	 * @param item 科目コード
	 * @param sub 補助科目コード
	 * @param detail 内訳科目コード
	 * @return 科目情報
	 * @throws TException
	 */
	public Item getItem(String company, String item, String sub, String detail) throws TException {

		if (Util.isNullOrEmpty(company) || Util.isNullOrEmpty(item)) {
			return null;
		}

		List<Item> list;
		if (!Util.isNullOrEmpty(detail)) {
			DetailItemSearchCondition condition = new DetailItemSearchCondition();
			condition.setCompanyCode(company);
			condition.setItemCode(item);
			condition.setSubItemCode(sub);
			condition.setCode(detail);

			list = get(condition);

		} else if (!Util.isNullOrEmpty(sub)) {
			SubItemSearchCondition condition = new SubItemSearchCondition();
			condition.setCompanyCode(company);
			condition.setItemCode(item);
			condition.setCode(sub);

			list = get(condition);

		} else {
			ItemSearchCondition condition = new ItemSearchCondition();
			condition.setCompanyCode(company);
			condition.setCode(item);

			list = get(condition);

		}

		if (list.isEmpty()) {
			return null;
		}

		return list.get(0);
	}

	/**
	 * 補助科目削除処理
	 */
	public void deleteSubItem(Item subItem) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.SUB_ITEM);
		condition.setCompanyCode(subItem.getSubItem().getCompanyCode());
		condition.setItemCode(subItem.getCode());
		condition.setSubItemCode(subItem.getSubItem().getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE FROM HKM_MST");
			s.add("WHERE KAI_CODE = ?", subItem.getSubItem().getCompanyCode());
			s.add("  AND KMK_CODE = ?", subItem.getCode());
			s.add("  AND HKM_CODE = ?", subItem.getSubItem().getCode());

			DBUtil.execute(conn, s);
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 補助科目新規登録
	 */
	public void entrySubItem(Item subItem) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO HKM_MST (");
			s.add("  KAI_CODE,");
			s.add("  KMK_CODE,");
			s.add("  HKM_CODE,");
			s.add("  HKM_NAME,");
			s.add("  HKM_NAME_S,");
			s.add("  HKM_NAME_K,");
			s.add("  UKM_KBN,");
			if (isUseKoteiDep) {
				s.add("  KOTEI_DEP_CODE,");
			}
			s.add("  ZEI_CODE,");
			s.add("  GL_FLG_1,");
			s.add("  GL_FLG_2,");
			s.add("  GL_FLG_3,");
			s.add("  AP_FLG_1,");
			s.add("  AP_FLG_2,");
			s.add("  AR_FLG_1,");
			s.add("  AR_FLG_2,");
			s.add("  FA_FLG_1,");
			s.add("  FA_FLG_2,");
			s.add("  TRI_CODE_FLG,");
			s.add("  EMP_CODE_FLG,");
			s.add("  KNR_FLG_1,");
			s.add("  KNR_FLG_2,");
			s.add("  KNR_FLG_3,");
			s.add("  KNR_FLG_4,");
			s.add("  KNR_FLG_5,");
			s.add("  KNR_FLG_6,");
			s.add("  HM_FLG_1,");
			s.add("  HM_FLG_2,");
			s.add("  HM_FLG_3,");
			s.add("  URI_ZEI_FLG,");
			s.add("  SIR_ZEI_FLG,");
			s.add("  MCR_FLG,");
			s.add("  EXC_FLG,");
			s.add("  HAS_FLG,");
			s.add("  STR_DATE,");
			s.add("  END_DATE,");
			s.add("  INP_DATE,");
			s.add("  PRG_ID,");
			s.add("  USR_ID");
			s.add(") VALUES (");
			s.add("  ?,", subItem.getSubItem().getCompanyCode());
			s.add("  ?,", subItem.getCode());
			s.add("  ?,", subItem.getSubItem().getCode());
			s.add("  ?,", subItem.getSubItem().getName());
			s.add("  ?,", subItem.getSubItem().getNames());
			s.add("  ?,", subItem.getSubItem().getNamek());
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().hasDetailItem()));
			if (isUseKoteiDep) {
				s.add("  ?,", subItem.getSubItem().getFixedDepartmentCode());
			}
			s.add("  ?,", subItem.getSubItem().getConsumptionTax().getCode());
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseInputCashFlowSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseOutputCashFlowSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseTransferSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseExpenseSettlementSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePaymentAppropriateSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseReceivableAppropriateSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseReceivableErasingSlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseAssetsEntrySlip()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePaymentRequestSlip()));
			s.add("  ?,", subItem.getSubItem().getClientType().value);
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseEmployee()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement1()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement2()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement3()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement4()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement5()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement6()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount1()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount2()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount3()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseSalesTaxation()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePurchaseTaxation()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseForeignCurrency()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isDoesCurrencyEvaluate()));
			s.add("  ?,", BooleanUtil.toInt(subItem.getSubItem().isUseOccurDate()));
			s.add("  ?,", subItem.getSubItem().getDateFrom());
			s.add("  ?,", subItem.getSubItem().getDateTo());
			s.addYMDHMS("  ?,", getNow());
			s.add("  ?,", getProgramCode());
			s.add("  ?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 補助科目エクセル
	 */
	public byte[] getSubItemExcel(SubItemSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<Item> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}
			SubItemExcel exl = new SubItemExcel(getUser().getLanguage(), getCompany());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 補助科目編集処理
	 */
	public void modifySubItem(Item subItem) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("  UPDATE HKM_MST SET");
			s.add("  HKM_NAME = ?,", subItem.getSubItem().getName());
			s.add("  HKM_NAME_S = ?,", subItem.getSubItem().getNames());
			s.add("  HKM_NAME_K = ?,", subItem.getSubItem().getNamek());
			s.add("  UKM_KBN = ?,", BooleanUtil.toInt(subItem.getSubItem().hasDetailItem()));
			if (isUseKoteiDep) {
				s.add("  KOTEI_DEP_CODE = ?,", subItem.getSubItem().getFixedDepartmentCode());
			}
			s.add("  ZEI_CODE = ?,", subItem.getSubItem().getConsumptionTax().getCode());
			s.add("  GL_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseInputCashFlowSlip()));
			s.add("  GL_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseOutputCashFlowSlip()));
			s.add("  GL_FLG_3 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseTransferSlip()));
			s.add("  AP_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseExpenseSettlementSlip()));
			s.add("  AP_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePaymentAppropriateSlip()));
			s.add("  AR_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseReceivableAppropriateSlip()));
			s.add("  AR_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseReceivableErasingSlip()));
			s.add("  FA_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseAssetsEntrySlip()));
			s.add("  FA_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePaymentRequestSlip()));
			s.add("  TRI_CODE_FLG = ?,", subItem.getSubItem().getClientType().value);
			s.add("  EMP_CODE_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseEmployee()));
			s.add("  KNR_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement1()));
			s.add("  KNR_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement2()));
			s.add("  KNR_FLG_3 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement3()));
			s.add("  KNR_FLG_4 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement4()));
			s.add("  KNR_FLG_5 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement5()));
			s.add("  KNR_FLG_6 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseManagement6()));
			s.add("  HM_FLG_1 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount1()));
			s.add("  HM_FLG_2 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount2()));
			s.add("  HM_FLG_3 = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseNonAccount3()));
			s.add("  URI_ZEI_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseSalesTaxation()));
			s.add("  SIR_ZEI_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isUsePurchaseTaxation()));
			s.add("  MCR_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseForeignCurrency()));
			s.add("  EXC_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isDoesCurrencyEvaluate()));
			s.add("  HAS_FLG = ?,", BooleanUtil.toInt(subItem.getSubItem().isUseOccurDate()));
			s.add("  STR_DATE = ?,", subItem.getSubItem().getDateFrom());
			s.add("  END_DATE = ?,", subItem.getSubItem().getDateTo());
			s.addYMDHMS("  UPD_DATE = ?,", getNow());
			s.add("  PRG_ID = ?,", getProgramCode());
			s.add("  USR_ID = ?", getUserCode());
			s.add("WHERE KAI_CODE = ?", subItem.getSubItem().getCompanyCode());
			s.add(" AND KMK_CODE = ?", subItem.getCode());
			s.add(" AND HKM_CODE = ?", subItem.getSubItemCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

}
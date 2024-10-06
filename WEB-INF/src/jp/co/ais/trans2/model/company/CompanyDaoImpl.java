package jp.co.ais.trans2.model.company;

import java.awt.*;
import java.sql.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.define.TransUtil;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 会社コントロールマスタDaoの実装
 * 
 * @author AIS
 */
public class CompanyDaoImpl extends TModel implements CompanyDao {

	/** INVOICE(会社基礎情報英語版)の利用可否 */
	protected boolean isShowInvoice = ServerConfig.isFlagOn("trans.company.master.show.company.eng");

	/** AR：英分請求書SIGNER表示可否 */
	protected boolean isShowARSignerEng = ServerConfig.isFlagOn("trans.company.master.show.ar.signer.eng");

	/** true: 2023INVOICE制度対応を使用する */
	public static boolean isInvoice = ServerConfig.isFlagOn("trans.slip.use.invoice.tax");

	public List<Company> get(CompanySearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<Company> list = new ArrayList<Company>();

		try {

			boolean isJoinDpkMst = !Util.isNullOrEmpty(condition.getOrganizationCode()) || condition.getLevel() >= 0;

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("	 cmp.KAI_CODE, ");
			sql.add("	 env.KAI_NAME, ");
			sql.add("	 env.KAI_NAME_S, ");
			sql.add("	 env.STR_DATE, ");
			sql.add("	 env.END_DATE, ");
			sql.add("	 env.ZIP, ");
			sql.add("	 env.JYU_KANA, ");
			sql.add("	 env.JYU_1, ");
			sql.add("	 env.JYU_2, ");
			sql.add("	 env.TEL, ");
			sql.add("	 env.FAX, ");
			sql.add("	 env.FORECOLOR, ");
			if (isShowInvoice) {
				sql.add("	 env.KAI_NAME_ENG, ");
				sql.add("	 env.KAI_NAME_S_ENG, ");
				sql.add("	 env.JYU_1_ENG, ");
				sql.add("	 env.JYU_2_ENG, ");
			}
			sql.add("	 cmp.CMP_KMK_NAME, ");
			sql.add("	 cmp.CMP_HKM_NAME, ");
			sql.add("	 cmp.CMP_UKM_KBN, ");
			sql.add("	 cmp.CMP_UKM_NAME, ");
			sql.add("	 cmp.KNR_KBN_1, ");
			sql.add("	 cmp.KNR_KBN_2, ");
			sql.add("	 cmp.KNR_KBN_3, ");
			sql.add("	 cmp.KNR_KBN_4, ");
			sql.add("	 cmp.KNR_KBN_5, ");
			sql.add("	 cmp.KNR_KBN_6, ");
			sql.add("	 cmp.KNR_NAME_1, ");
			sql.add("	 cmp.KNR_NAME_2, ");
			sql.add("	 cmp.KNR_NAME_3, ");
			sql.add("	 cmp.KNR_NAME_4, ");
			sql.add("	 cmp.KNR_NAME_5, ");
			sql.add("	 cmp.KNR_NAME_6, ");
			sql.add("	 cmp.CMP_HM_KBN_1, ");
			sql.add("	 cmp.CMP_HM_KBN_2, ");
			sql.add("	 cmp.CMP_HM_KBN_3, ");
			sql.add("	 cmp.CMP_HM_NAME_1, ");
			sql.add("	 cmp.CMP_HM_NAME_2, ");
			sql.add("	 cmp.CMP_HM_NAME_3, ");
			sql.add("	 cmp.CMP_KISYU, ");
			sql.add("	 cmp.JID_1, ");
			sql.add("	 cmp.JID_2, ");
			sql.add("	 cmp.JID_3, ");
			sql.add("	 cmp.AUTO_NO_KETA, ");
			sql.add("	 cmp.PRINT_KBN, ");
			sql.add("	 cmp.PRINT_DEF, ");
			sql.add("	 cmp.CMP_WORKFLOW_FLG, ");
			sql.add("	 cmp.WORKFLOW_BACK_FIRST, ");
			sql.add("	 cmp.CMP_G_SHONIN_FLG, ");
			sql.add("	 cmp.CMP_K_SHONIN_FLG, ");
			sql.add("	 cmp.CUR_CODE, ");
			sql.add("	 cur.DEC_KETA, ");
			sql.add("	 cur.CUR_NAME_S, ");
			sql.add("	 cmp.FNC_CUR_CODE, ");
			sql.add("	 fcur.DEC_KETA FNC_DEC_KETA, ");
			sql.add("	 fcur.CUR_NAME_S FNC_CUR_NAME_S, ");
			sql.add("	 'JPY' JPY_CUR_CODE, ");
			sql.add("	 jpycur.DEC_KETA JPY_DEC_KETA, ");
			sql.add("	 jpycur.CUR_NAME_S JPY_CUR_NAME_S, ");
			sql.add("	 'USD' USD_CUR_CODE, ");
			sql.add("	 usdcur.DEC_KETA USD_DEC_KETA, ");
			sql.add("	 usdcur.CUR_NAME_S USD_CUR_NAME_S, ");
			sql.add("	 cmp.RATE_KBN, ");
			sql.add("	 cmp.KU_KBN, ");
			sql.add("	 cmp.KB_KBN, ");
			sql.add("	 cmp.INP_DATE, ");
			sql.add("	 cmp.UPD_DATE, ");
			sql.add("	 cmp.PRG_ID, ");
			sql.add("	 cmp.USR_ID, ");
			sql.add("	 sim.NENDO, ");
			sql.add("	 sim.SIM_STR_DATE, ");
			sql.add("	 sim.SIM_END_DATE, ");
			sql.add("	 sim.SIM_MON, ");
			sql.add("	 sim.KSN_KBN, ");
			sql.add("	 glc.KSD_KBN, ");
			sql.add("	 glc.KSN_NYU_KBN, ");
			sql.add("	 glc.EXC_RATE_KBN, ");
			sql.add("	 cmp.CMP_GRP_KBN, ");
			sql.add("	 cmp.PRINT_KBN, ");
			sql.add("	 cmp.PRINT_DEF, ");
			sql.add("	 cmp.CONV_KBN, ");
			sql.add("	 cmp.IFRS_KBN, ");
			sql.add("	 cmp.HAS_DATE_CHK_KBN, ");
			sql.add("	 cmp.KSN_KURI_KBN, ");
			sql.add("	 cmp.DEP_CODE, ");
			sql.add("	 cmp.SIGNER_POSITION, ");
			sql.add("	 cmp.REMITTER_NAME, ");
			sql.add("	 cmp.PHONE_NO, ");
			sql.add("	 cmp.DEBITNOTE_INFO, ");
			sql.add("	 cmp.DEBITNOTE_JYU_1, ");
			sql.add("	 cmp.DEBITNOTE_JYU_2, ");
			sql.add("	 cmp.DEBITNOTE_JYU_3 ");
			if (isShowARSignerEng) {
				// 英文請求書SIGNER欄
				sql.add("	 , cmp.AR_SIGN_NAME ");
			} else {
				sql.add("	 , NULL AR_SIGN_NAME ");
			}

			if (isInvoice) {
				sql.add("	 , env.INV_REG_NO ");
				sql.add("	 , cmp.CMP_INV_CHK_FLG ");
			}

			if (isJoinDpkMst) {
				sql.add(", ");
				sql.add(" dpk.DPK_SSK, ");
				sql.add(" dpk.DPK_LVL ");

				if (condition.getLevel() > 0) {
					sql.add(", ");
					sql.add(" dpk.DPK_LVL_" + Integer.toString(condition.getLevel() - 1) + " SUPERIOR_KAI_CODE ");
				}
			}

			sql.add(" FROM ");
			sql.add("	 CMP_MST cmp ");
			sql.add("	 LEFT OUTER JOIN ENV_MST env ");
			sql.add("	 ON	cmp.KAI_CODE = env.KAI_CODE ");
			sql.add("	 LEFT OUTER JOIN SIM_CTL sim ");
			sql.add("	 ON	cmp.KAI_CODE = sim.KAI_CODE ");
			sql.add("	 LEFT OUTER JOIN GL_CTL_MST glc ");
			sql.add("	 ON	cmp.KAI_CODE = glc.KAI_CODE ");
			sql.add("	 LEFT OUTER JOIN CUR_MST cur ");
			sql.add("	 ON	cmp.KAI_CODE = cur.KAI_CODE ");
			sql.add("	 AND	cmp.CUR_CODE = cur.CUR_CODE ");
			sql.add("	 LEFT OUTER JOIN CUR_MST fcur ");
			sql.add("	 ON	cmp.KAI_CODE = fcur.KAI_CODE ");
			sql.add("	 AND	cmp.FNC_CUR_CODE = fcur.CUR_CODE ");
			sql.add("	 LEFT OUTER JOIN CUR_MST jpycur ");
			sql.add("	 ON	cmp.KAI_CODE = jpycur.KAI_CODE ");
			sql.add("	 AND	'JPY' = jpycur.CUR_CODE ");
			sql.add("	 LEFT OUTER JOIN CUR_MST usdcur ");
			sql.add("	 ON	cmp.KAI_CODE = usdcur.KAI_CODE ");
			sql.add("	 AND	'USD' = usdcur.CUR_CODE ");

			// 組織
			if (isJoinDpkMst) {

				sql.add(" INNER JOIN EVK_MST dpk ");
				sql.add(" ON 	dpk.KAI_CODE = ? ", getCompanyCode());
				sql.add(" AND	dpk.DPK_DEP_CODE = cmp.KAI_CODE ");

				// 組織
				if (!Util.isNullOrEmpty(condition.getOrganizationCode())) {
					sql.add(" AND	dpk.DPK_SSK = ?", condition.getOrganizationCode());
				}
				// レベル
				if (condition.isIncludeUnder()) {
					if (condition.getLevel() > 0) {
						sql.add(" AND	dpk.DPK_LVL = ?", condition.getLevel());
					} else if (condition.getLevel() == 0) {
						sql.add(" AND	dpk.DPK_LVL > ?", condition.getLevel());
					}
				} else {
					if (condition.getLevel() >= 0) {
						sql.add(" AND	dpk.DPK_LVL = ?", condition.getLevel());
					}
				}

				// 上位会社
				if (!Util.isNullOrEmpty(condition.getSuperiorCompanyCode())) {
					sql.add(" AND	dpk.DPK_LVL_?", condition.getLevel() - 1);
					sql.add(" = ?", condition.getSuperiorCompanyCode());
				}
			}

			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add(" AND	cmp.KAI_CODE = ? ", condition.getCode());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.addLikeFront(" AND	cmp.KAI_CODE ?", condition.getCodeLike());
			}

			// 略称あいまい
			if (!Util.isNullOrEmpty(condition.getNamesLike())) {
				sql.addNLikeAmbi(" AND	env.KAI_NAME_S ? ", condition.getNamesLike());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add(" AND	cmp.KAI_CODE >= ? ", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add(" AND	cmp.KAI_CODE <= ? ", condition.getCodeTo());
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add(" AND	env.STR_DATE <= ? ", condition.getValidTerm());
				sql.add(" AND	env.END_DATE >= ? ", condition.getValidTerm());
			}

			// グループ会計区分
			if (condition.getGroupAccountDivision() != -1) {
				sql.add(" AND	cmp.CMP_GRP_KBN = ? ", condition.getGroupAccountDivision());
			}

			// 基軸通貨
			if (!Util.isNullOrEmpty(condition.getKeyCurrencyCode())) {
				sql.add(" AND	cmp.CUR_CODE = ?", condition.getKeyCurrencyCode());
			}

			// 会社コード
			if (!condition.getCodeList().isEmpty()) {
				sql.add(" AND	cmp.KAI_CODE IN " + SQLUtil.getInStatement(condition.getCodeList()));
			}

			// 決算段階～以外
			if (condition.getSettlementStageOtherThan() != -1) {
				sql.add(" AND	glc.KSD_KBN <> ? ", condition.getSettlementStageOtherThan());
			}

			// 機能通貨
			if (!Util.isNullOrEmpty(condition.getFncCurrencyCode())) {
				sql.add(" AND	cmp.FNC_CUR_CODE = ? ", condition.getFncCurrencyCode());
			}

			// 指定会社コード以外
			if (!Util.isNullOrEmpty(condition.getExcludeCompanyCode())) {
				sql.add(" AND	cmp.KAI_CODE <> ? ", condition.getExcludeCompanyCode());
			}

			// 基軸通貨指定
			if (condition.getKeyCurrencyCodeList() != null && !condition.getKeyCurrencyCodeList().isEmpty()) {
				sql.add(" AND cmp.CUR_CODE IN ? ", condition.getKeyCurrencyCodeList());
			}

			// 追加検索条件
			if (!Util.isNullOrEmpty(condition.getAddonWhereSql())) {
				sql.add(condition.getAddonWhereSql());
			}

			sql.add(" ORDER BY cmp.KAI_CODE ");

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

		if (!list.isEmpty()) {
			// 先行日付の月数を設定する
			try {
				int priorOverMonths = Util.avoidNullAsInt(ServerConfig.getProperty("trans.slip.priorover.months"));
				if (priorOverMonths < 0) {
					priorOverMonths = 12;
				}

				for (Company bean : list) {
					FiscalPeriod fp = bean.getFiscalPeriod();
					if (fp != null) {
						fp.setPriorOverMonths(priorOverMonths);
					}
				}

			} catch (Throwable ex) {
				// エラーなし
			}
		}

		return list;

	}

	public Company get(String companyCode) throws TException {

		CompanySearchCondition condition = new CompanySearchCondition();
		condition.setCode(companyCode);

		List<Company> companies = get(condition);
		if (companies == null || companies.isEmpty()) {
			return null;
		}
		return companies.get(0);

	}

	public void entry(Company company) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			if (Util.isNullOrEmpty(company.getColor())) {
				company.setColor(Color.white);
			}

			// 環境設定マスタに登録
			VCreator sql = new VCreator();
			sql.add(" INSERT INTO ENV_MST ( ");
			sql.add("	 KAI_CODE, ");
			sql.add("	 KAI_NAME, ");
			sql.add("	 KAI_NAME_S, ");
			sql.add("	 STR_DATE, ");
			sql.add("	 END_DATE, ");
			sql.add("	 ZIP, ");
			sql.add("	 JYU_1, ");
			sql.add("	 JYU_2, ");
			sql.add("	 JYU_KANA, ");
			sql.add("	 TEL, ");
			sql.add("	 FAX, ");
			if (isInvoice) {
				sql.add("	 INV_REG_NO, ");
			}
			sql.add("	 FORECOLOR, ");
			if (isShowInvoice) {
				sql.add("	 KAI_NAME_ENG, ");
				sql.add("	 KAI_NAME_S_ENG, ");
				sql.add("	 JYU_1_ENG, ");
				sql.add("	 JYU_2_ENG, ");
			}
			sql.add("	 INP_DATE, ");
			sql.add("	 PRG_ID, ");
			sql.add("	 USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("	 ? ", company.getCode());
			sql.add("	,? ", company.getName());
			sql.add("	,? ", company.getNames());
			sql.add("	,? ", company.getDateFrom());
			sql.add("	,? ", company.getDateTo());
			sql.add("	,? ", company.getZip());
			sql.add("	,? ", company.getAddress1());
			sql.add("	,? ", company.getAddress2());
			sql.add("	,? ", company.getAddressKana());
			sql.add("	,? ", company.getPhoneNo());
			sql.add("	,? ", company.getFaxNo());
			if (isInvoice) {
				sql.add("	,? ", company.getInvRegNo());
			}
			sql.add("	,? ", Util.to16RGBColorCode(company.getColor()));
			if (isShowInvoice) {
				sql.add("	,? ", company.getKAI_NAME_ENG());
				sql.add("	,? ", company.getKAI_NAME_S_ENG());
				sql.add("	,? ", company.getJYU_1_ENG());
				sql.add("	,? ", company.getJYU_2_ENG());
			}
			sql.adt("	,? ", getNow());
			sql.add("	,? ", getProgramCode());
			sql.add("	,? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

			// 会社コントロールマスタに登録
			AccountConfig ac = company.getAccountConfig();
			FiscalPeriod fp = company.getFiscalPeriod();

			sql = new VCreator();
			sql.add(" INSERT INTO CMP_MST ( ");
			sql.add("	KAI_CODE, ");
			sql.add("	CMP_KMK_NAME, ");
			sql.add("	CMP_HKM_NAME, ");
			sql.add("	CMP_UKM_KBN, ");
			sql.add("	CMP_UKM_NAME, ");
			sql.add("	KNR_KBN_1, ");
			sql.add("	KNR_KBN_2, ");
			sql.add("	KNR_KBN_3, ");
			sql.add("	KNR_KBN_4, ");
			sql.add("	KNR_KBN_5, ");
			sql.add("	KNR_KBN_6, ");
			sql.add("	KNR_NAME_1, ");
			sql.add("	KNR_NAME_2, ");
			sql.add("	KNR_NAME_3, ");
			sql.add("	KNR_NAME_4, ");
			sql.add("	KNR_NAME_5, ");
			sql.add("	KNR_NAME_6, ");
			sql.add("	CMP_HM_KBN_1, ");
			sql.add("	CMP_HM_KBN_2, ");
			sql.add("	CMP_HM_KBN_3, ");
			sql.add("	CMP_HM_NAME_1, ");
			sql.add("	CMP_HM_NAME_2, ");
			sql.add("	CMP_HM_NAME_3, ");
			sql.add("	CMP_KISYU, ");
			sql.add("	JID_1, ");
			sql.add("	JID_2, ");
			sql.add("	JID_3, ");
			sql.add("	AUTO_NO_KETA, ");
			sql.add("	PRINT_KBN, ");
			sql.add("	PRINT_DEF, ");
			sql.add("	CMP_WORKFLOW_FLG, ");
			sql.add("	WORKFLOW_BACK_FIRST, ");
			sql.add("	CMP_G_SHONIN_FLG, ");
			sql.add("	CMP_K_SHONIN_FLG, ");
			sql.add("	CUR_CODE, ");
			sql.add("	FNC_CUR_CODE, ");
			sql.add("	RATE_KBN, ");
			sql.add("	KU_KBN, ");
			sql.add("	KB_KBN, ");
			sql.add("	INP_DATE, ");
			sql.add("	PRG_ID, ");
			sql.add("	USR_ID, ");
			sql.add("	CMP_GRP_KBN, ");
			sql.add("	CONV_KBN, ");
			sql.add("	IFRS_KBN, ");
			sql.add("	HAS_DATE_CHK_KBN, ");
			sql.add("	KSN_KURI_KBN, ");
			sql.add("	DEP_CODE, ");
			sql.add("	SIGNER_POSITION, ");
			sql.add("	REMITTER_NAME, ");
			sql.add("	PHONE_NO, ");
			sql.add("	DEBITNOTE_INFO, ");
			sql.add("	DEBITNOTE_JYU_1,");
			sql.add("	DEBITNOTE_JYU_2,");
			sql.add("	DEBITNOTE_JYU_3 ");
			if (isShowARSignerEng) {
				// 英文請求書SIGNER欄
				sql.add("    , AR_SIGN_NAME ");
			}

			if (isInvoice) {
				// インボイス制度使用するかどうか
				sql.add("	 , CMP_INV_CHK_FLG ");
			}
			sql.add(" ) VALUES ( ");
			sql.add("	 ? ", company.getCode());
			sql.add("	,? ", ac.getItemName());
			sql.add("	,? ", ac.getSubItemName());
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseDetailItem()));
			sql.add("	,? ", ac.getDetailItemName());
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement1()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement2()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement3()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement4()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement5()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseManagement6()));
			sql.add("	,? ", ac.getManagement1Name());
			sql.add("	,? ", ac.getManagement2Name());
			sql.add("	,? ", ac.getManagement3Name());
			sql.add("	,? ", ac.getManagement4Name());
			sql.add("	,? ", ac.getManagement5Name());
			sql.add("	,? ", ac.getManagement6Name());
			sql.add("	,? ", ac.getNonAccounting1().getValue());
			sql.add("	,? ", ac.getNonAccounting2().getValue());
			sql.add("	,? ", ac.getNonAccounting3().getValue());
			sql.add("	,? ", ac.getNonAccounting1Name());
			sql.add("	,? ", ac.getNonAccounting2Name());
			sql.add("	,? ", ac.getNonAccounting3Name());
			sql.add("	,? ", fp.getMonthBeginningOfPeriod());
			sql.add("	,? ", ac.getSlipNoAdopt1().getValue());
			sql.add("	,? ", ac.getSlipNoAdopt2().getValue());
			sql.add("	,? ", ac.getSlipNoAdopt3().getValue());
			sql.add("	,? ", ac.getSlipNoDigit());
			sql.add("	,? ", BooleanUtil.toInt(ac.isSlipPrint()));
			sql.add("	,? ", BooleanUtil.toInt(ac.getSlipPrintDefault()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseWorkflowApprove()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isBackFirstWhenWorkflowDeny()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseFieldApprove()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseApprove()));
			sql.add("	,? ", ac.getKeyCurrency().getCode());
			sql.add("	,? ", ac.getFunctionalCurrency().getCode());
			sql.add("	,? ", ac.getExchangeFraction().getValue());
			sql.add("	,? ", ac.getReceivingFunction().getValue());
			sql.add("	,? ", ac.getPaymentFunction().getValue());
			sql.adt("	,? ", getNow());
			sql.add("	,? ", getProgramCode());
			sql.add("	,? ", getUserCode());
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseGroupAccount()));
			sql.add("	,? ", ac.getConvertType().getValue());
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseIfrs()));
			sql.add("	,? ", BooleanUtil.toInt(ac.isUseHasDateCheck()));
			sql.add("	,? ", ac.isCarryJournalOfMidtermClosingForward() ? 0 : 1);
			sql.add("	,? ", company.getConnCompanyCode());
			sql.add("	,? ", company.getSignerPosition());
			sql.add("	,? ", company.getRemitterName());
			sql.add("	,? ", company.getConnPhoneNo());
			sql.add("	,? ", company.getDebitNoteInfo());
			sql.add("	,? ", company.getDebitNoteAddress1());
			sql.add("	,? ", company.getDebitNoteAddress2());
			sql.add("	,? ", company.getDebitNoteAddress3());
			if (isShowARSignerEng) {
				// 英文請求書SIGNER欄
				sql.add("	,? ", company.getAR_SIGN_NAME());
			}

			if (isInvoice) {
				// インボイス制度使用するかどうか
				sql.add("	,? ", BooleanUtil.toInt(company.isCMP_INV_CHK_FLG()));
			}

			sql.add(" ) ");

			DBUtil.execute(conn, sql);

			// 決算コントロールマスタに登録
			sql = new VCreator();
			sql.add(" INSERT INTO GL_CTL_MST ( ");
			sql.add("	KAI_CODE, ");
			sql.add("	KSD_KBN, ");
			sql.add("	KSN_NYU_KBN, ");
			sql.add("	EXC_RATE_KBN, ");
			sql.add("	INP_DATE, ");
			sql.add("	PRG_ID, ");
			sql.add("	USR_ID ");
			sql.add(" ) VALUES ( ");
			sql.add("	 ? ", company.getCode());
			sql.add("	,? ", fp.getMaxSettlementStage());
			sql.add("	,? ", fp.getSettlementTerm().getValue());
			sql.add("	,? ", ac.getEvaluationRateDate().getValue());
			sql.adt("	,? ", getNow());
			sql.add("	,? ", getProgramCode());
			sql.add("	,? ", getUserCode());
			sql.add(" ) ");

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void modify(Company company) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			if (Util.isNullOrEmpty(company.getColor())) {
				company.setColor(Color.white);
			}

			// 環境設定マスタを更新
			VCreator sql = new VCreator();
			sql.add(" UPDATE ENV_MST ");
			sql.add(" SET ");
			sql.add("	 KAI_NAME = ? ", company.getName());
			sql.add("	,KAI_NAME_S = ? ", company.getNames());
			sql.add("	,STR_DATE = ? ", company.getDateFrom());
			sql.add("	,END_DATE = ? ", company.getDateTo());
			sql.add("	,ZIP = ? ", company.getZip());
			sql.add("	,JYU_KANA = ? ", company.getAddressKana());
			sql.add("	,JYU_1 = ? ", company.getAddress1());
			sql.add("	,JYU_2 = ? ", company.getAddress2());
			sql.add("	,TEL = ? ", company.getPhoneNo());
			sql.add("	,FAX = ? ", company.getFaxNo());
			if (isInvoice) {
				sql.add("	,INV_REG_NO = ? ", company.getInvRegNo());
			}
			sql.add("	,FORECOLOR = ? ", Util.to16RGBColorCode(company.getColor()));
			if (isShowInvoice) {
				sql.add("	,KAI_NAME_ENG = ? ", company.getKAI_NAME_ENG());
				sql.add("	,KAI_NAME_S_ENG = ? ", company.getKAI_NAME_S_ENG());
				sql.add("	,JYU_1_ENG = ? ", company.getJYU_1_ENG());
				sql.add("	,JYU_2_ENG = ? ", company.getJYU_2_ENG());
			}
			sql.adt("	,UPD_DATE = ? ", getNow());
			sql.add("	,PRG_ID = ? ", getProgramCode());
			sql.add("	,USR_ID = ? ", getUserCode());
			sql.add(" WHERE KAI_CODE = ? ", company.getCode());

			DBUtil.execute(conn, sql);

			AccountConfig ac = company.getAccountConfig();
			FiscalPeriod fp = company.getFiscalPeriod();

			// 会社コントロールマスタを更新
			sql = new VCreator();
			sql.add(" UPDATE CMP_MST ");
			sql.add(" SET ");
			sql.add("	 CMP_KMK_NAME = ? ", ac.getItemName());
			sql.add("	,CMP_HKM_NAME = ? ", ac.getSubItemName());
			sql.add("	,CMP_UKM_KBN = ? ", BooleanUtil.toInt(ac.isUseDetailItem()));
			sql.add("	,CMP_UKM_NAME = ? ", ac.getDetailItemName());
			sql.add("	,KNR_KBN_1 = ? ", BooleanUtil.toInt(ac.isUseManagement1()));
			sql.add("	,KNR_KBN_2 = ? ", BooleanUtil.toInt(ac.isUseManagement2()));
			sql.add("	,KNR_KBN_3 = ? ", BooleanUtil.toInt(ac.isUseManagement3()));
			sql.add("	,KNR_KBN_4 = ? ", BooleanUtil.toInt(ac.isUseManagement4()));
			sql.add("	,KNR_KBN_5 = ? ", BooleanUtil.toInt(ac.isUseManagement5()));
			sql.add("	,KNR_KBN_6 = ? ", BooleanUtil.toInt(ac.isUseManagement6()));
			sql.add("	,KNR_NAME_1 = ? ", ac.getManagement1Name());
			sql.add("	,KNR_NAME_2 = ? ", ac.getManagement2Name());
			sql.add("	,KNR_NAME_3 = ? ", ac.getManagement3Name());
			sql.add("	,KNR_NAME_4 = ? ", ac.getManagement4Name());
			sql.add("	,KNR_NAME_5 = ? ", ac.getManagement5Name());
			sql.add("	,KNR_NAME_6 = ? ", ac.getManagement6Name());
			sql.add("	,CMP_HM_KBN_1 = ? ", ac.getNonAccounting1().getValue());
			sql.add("	,CMP_HM_KBN_2 = ? ", ac.getNonAccounting2().getValue());
			sql.add("	,CMP_HM_KBN_3 = ? ", ac.getNonAccounting3().getValue());
			sql.add("	,CMP_HM_NAME_1 = ? ", ac.getNonAccounting1Name());
			sql.add("	,CMP_HM_NAME_2 = ? ", ac.getNonAccounting2Name());
			sql.add("	,CMP_HM_NAME_3 = ? ", ac.getNonAccounting3Name());
			sql.add("	,CMP_KISYU = ? ", fp.getMonthBeginningOfPeriod());
			sql.add("	,JID_1 = ? ", ac.getSlipNoAdopt1().getValue());
			sql.add("	,JID_2 = ? ", ac.getSlipNoAdopt2().getValue());
			sql.add("	,JID_3 = ? ", ac.getSlipNoAdopt3().getValue());
			sql.add("	,AUTO_NO_KETA = ? ", ac.getSlipNoDigit());
			sql.add("	,PRINT_KBN = ? ", BooleanUtil.toInt(ac.isSlipPrint()));
			sql.add("	,PRINT_DEF = ? ", BooleanUtil.toInt(ac.getSlipPrintDefault()));
			sql.add("	,CMP_WORKFLOW_FLG = ? ", BooleanUtil.toInt(ac.isUseWorkflowApprove()));
			sql.add("	,WORKFLOW_BACK_FIRST = ? ", BooleanUtil.toInt(ac.isBackFirstWhenWorkflowDeny()));
			sql.add("	,CMP_G_SHONIN_FLG = ? ", BooleanUtil.toInt(ac.isUseFieldApprove()));
			sql.add("	,CMP_K_SHONIN_FLG = ? ", BooleanUtil.toInt(ac.isUseApprove()));
			sql.add("	,CUR_CODE = ? ", ac.getKeyCurrency().getCode());
			sql.add("	,FNC_CUR_CODE = ? ", ac.getFunctionalCurrency().getCode());
			sql.add("	,RATE_KBN = ? ", ac.getExchangeFraction().getValue());
			sql.add("	,KU_KBN = ? ", ac.getReceivingFunction().getValue());
			sql.add("	,KB_KBN = ? ", ac.getPaymentFunction().getValue());
			sql.adt("	,UPD_DATE = ? ", getNow());
			sql.add("	,PRG_ID = ? ", getProgramCode());
			sql.add("	,USR_ID = ? ", getUserCode());
			sql.add("	,CMP_GRP_KBN = ? ", BooleanUtil.toInt(ac.isUseGroupAccount()));
			sql.add("	,CONV_KBN = ? ", ac.getConvertType().getValue());
			sql.add("	,IFRS_KBN = ? ", BooleanUtil.toInt(ac.isUseIfrs()));
			sql.add("	,HAS_DATE_CHK_KBN = ? ", BooleanUtil.toInt(ac.isUseHasDateCheck()));
			sql.add("	,KSN_KURI_KBN = ? ", ac.isCarryJournalOfMidtermClosingForward() ? 0 : 1);
			sql.add("	,DEP_CODE = ? ", company.getConnCompanyCode());
			sql.add("	,SIGNER_POSITION = ? ", company.getSignerPosition());
			sql.add("	,REMITTER_NAME = ? ", company.getRemitterName());
			sql.add("	,PHONE_NO = ? ", company.getConnPhoneNo());
			sql.add("	,DEBITNOTE_INFO = ? ", company.getDebitNoteInfo());
			sql.add("	,DEBITNOTE_JYU_1 = ? ", company.getDebitNoteAddress1());
			sql.add("	,DEBITNOTE_JYU_2 = ? ", company.getDebitNoteAddress2());
			sql.add("	,DEBITNOTE_JYU_3 = ? ", company.getDebitNoteAddress3());
			if (isShowARSignerEng) {
				// 英文請求書SIGNER欄
				sql.add("	,AR_SIGN_NAME = ? ", company.getAR_SIGN_NAME());
			}

			if (isInvoice) {
				// インボイス制度使用するかどうか
				sql.add("	,CMP_INV_CHK_FLG = ? ", BooleanUtil.toInt(company.isCMP_INV_CHK_FLG()));
			}

			sql.add(" WHERE KAI_CODE = ? ", company.getCode());

			DBUtil.execute(conn, sql);

			// 決算コントロールマスタを更新
			sql = new VCreator();
			sql.add(" UPDATE GL_CTL_MST ");
			sql.add(" SET ");
			sql.add("	 KSD_KBN = ? ", fp.getMaxSettlementStage());
			sql.add("	,KSN_NYU_KBN = ? ", fp.getSettlementTerm().getValue());
			sql.add("	,EXC_RATE_KBN = ? ", ac.getEvaluationRateDate().getValue());
			sql.adt("	,UPD_DATE = ? ", getNow());
			sql.add("	,PRG_ID = ? ", getProgramCode());
			sql.add("	,USR_ID = ? ", getUserCode());
			sql.add(" WHERE KAI_CODE = ? ", company.getCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void delete(Company company) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();
			sql.add(" DELETE FROM ENV_MST WHERE KAI_CODE = ?", company.getCode());
			DBUtil.execute(conn, sql);

			sql = new VCreator();
			sql.add(" DELETE FROM CMP_MST WHERE KAI_CODE = ?", company.getCode());
			DBUtil.execute(conn, sql);

			sql = new VCreator();
			sql.add(" DELETE FROM GL_CTL_MST WHERE KAI_CODE = ?", company.getCode());
			DBUtil.execute(conn, sql);

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
	protected Company mapping(ResultSet rs) throws Exception {

		Company bean = createCompany();

		// 基本情報マッピング
		bean.setCode(rs.getString("KAI_CODE"));
		bean.setName(rs.getString("KAI_NAME"));
		bean.setNames(rs.getString("KAI_NAME_S"));
		bean.setAddress1(rs.getString("JYU_1"));
		bean.setAddress2(rs.getString("JYU_2"));
		bean.setAddressKana(rs.getString("JYU_KANA"));
		bean.setZip(rs.getString("ZIP"));
		bean.setPhoneNo(rs.getString("TEL"));
		bean.setFaxNo(rs.getString("FAX"));
		if (Util.isNullOrEmpty(rs.getString("FORECOLOR"))) {
			bean.setColor(Color.white);
		} else {
			int[] rgbCode = Util.toRGBColorCode(rs.getString("FORECOLOR"));
			bean.setColor(new Color(rgbCode[0], rgbCode[1], rgbCode[2]));
		}
		bean.setDateFrom(rs.getDate("STR_DATE"));
		bean.setDateTo(rs.getDate("END_DATE"));

		// INVOICE使用(会社基礎情報英語版)--追加
		if (isShowInvoice) {
			bean.setKAI_NAME_ENG(rs.getString("KAI_NAME_ENG"));
			bean.setKAI_NAME_S_ENG(rs.getString("KAI_NAME_S_ENG"));
			bean.setJYU_1_ENG(rs.getString("JYU_1_ENG"));
			bean.setJYU_2_ENG(rs.getString("JYU_2_ENG"));
		}
		// invoice制度使用フラグ
		if (isInvoice) {
			bean.setInvRegNo(rs.getString("INV_REG_NO"));
			bean.setCMP_INV_CHK_FLG(TransUtil.TRUE == rs.getInt("CMP_INV_CHK_FLG"));
		}
		bean.setAR_SIGN_NAME(rs.getString("AR_SIGN_NAME"));

		// SPC会社情報--追加
		bean.setConnCompanyCode(rs.getString("DEP_CODE"));
		bean.setSignerPosition(rs.getString("SIGNER_POSITION"));
		bean.setRemitterName(rs.getString("REMITTER_NAME"));
		bean.setConnPhoneNo(rs.getString("PHONE_NO"));
		bean.setDebitNoteAddress1(rs.getString("DEBITNOTE_JYU_1"));
		bean.setDebitNoteAddress2(rs.getString("DEBITNOTE_JYU_2"));
		bean.setDebitNoteAddress3(rs.getString("DEBITNOTE_JYU_3"));
		bean.setDebitNoteInfo(rs.getString("DEBITNOTE_INFO"));

		// 会計設定情報マッピング
		AccountConfig ac = createAccountConfig();
		ac.setItemName(rs.getString("CMP_KMK_NAME"));
		ac.setSubItemName(rs.getString("CMP_HKM_NAME"));
		ac.setDetailItemName(rs.getString("CMP_UKM_NAME"));
		ac.setUseDetailItem(TransUtil.TRUE == rs.getInt("CMP_UKM_KBN"));
		ac.setUseManagement1(TransUtil.TRUE == rs.getInt("KNR_KBN_1"));
		ac.setUseManagement2(TransUtil.TRUE == rs.getInt("KNR_KBN_2"));
		ac.setUseManagement3(TransUtil.TRUE == rs.getInt("KNR_KBN_3"));
		ac.setUseManagement4(TransUtil.TRUE == rs.getInt("KNR_KBN_4"));
		ac.setUseManagement5(TransUtil.TRUE == rs.getInt("KNR_KBN_5"));
		ac.setUseManagement6(TransUtil.TRUE == rs.getInt("KNR_KBN_6"));
		ac.setManagement1Name(rs.getString("KNR_NAME_1"));
		ac.setManagement2Name(rs.getString("KNR_NAME_2"));
		ac.setManagement3Name(rs.getString("KNR_NAME_3"));
		ac.setManagement4Name(rs.getString("KNR_NAME_4"));
		ac.setManagement5Name(rs.getString("KNR_NAME_5"));
		ac.setManagement6Name(rs.getString("KNR_NAME_6"));
		ac.setNonAccounting1(rs.getInt("CMP_HM_KBN_1"));
		ac.setNonAccounting2(rs.getInt("CMP_HM_KBN_2"));
		ac.setNonAccounting3(rs.getInt("CMP_HM_KBN_3"));
		ac.setNonAccounting1Name(rs.getString("CMP_HM_NAME_1"));
		ac.setNonAccounting2Name(rs.getString("CMP_HM_NAME_2"));
		ac.setNonAccounting3Name(rs.getString("CMP_HM_NAME_3"));
		ac.setSlipNoDigit(rs.getInt("AUTO_NO_KETA"));
		ac.setSlipNoAdopt1(SlipNoAdopt.get(rs.getInt("JID_1")));
		ac.setSlipNoAdopt2(SlipNoAdopt.get(rs.getInt("JID_2")));
		ac.setSlipNoAdopt3(SlipNoAdopt.get(rs.getInt("JID_3")));
		Currency currency = createCurrency();
		currency.setCompanyCode(rs.getString("KAI_CODE"));
		currency.setCode(rs.getString("CUR_CODE"));
		currency.setNames(rs.getString("CUR_NAME_S"));
		currency.setDecimalPoint(rs.getInt("DEC_KETA"));
		ac.setKeyCurrency(currency);
		Currency functionalCurrency = createCurrency();
		functionalCurrency.setCompanyCode(rs.getString("KAI_CODE"));
		functionalCurrency.setCode(rs.getString("FNC_CUR_CODE"));
		functionalCurrency.setNames(rs.getString("FNC_CUR_NAME_S"));
		functionalCurrency.setDecimalPoint(rs.getInt("FNC_DEC_KETA"));
		ac.setFunctionalCurrency(functionalCurrency);
		ac.setUseWorkflowApprove(rs.getInt("CMP_WORKFLOW_FLG") == 1);
		ac.setBackFirstWhenWorkflowDeny(rs.getInt("WORKFLOW_BACK_FIRST") == 1);
		ac.setUseFieldApprove(rs.getInt("CMP_G_SHONIN_FLG") == 1);
		ac.setUseApprove(rs.getInt("CMP_K_SHONIN_FLG") == 1);
		ac.setUseGroupAccount(TransUtil.TRUE == rs.getInt("CMP_GRP_KBN"));
		ac.setExchangeFraction(ExchangeFraction.getExchangeFraction(rs.getInt("RATE_KBN")));
		ac.setPaymentFunction(ExchangeFraction.getExchangeFraction(rs.getInt("KB_KBN")));
		ac.setReceivingFunction(ExchangeFraction.getExchangeFraction(rs.getInt("KU_KBN")));
		ac.setSlipPrint(BooleanUtil.toBoolean(rs.getInt("PRINT_KBN")));
		ac.setSlipPrintDefault(BooleanUtil.toBoolean(rs.getInt("PRINT_DEF")));
		ac.setConvertType(ConvertType.get(rs.getInt("CONV_KBN")));
		ac.setUseIfrs(TransUtil.TRUE == rs.getInt("IFRS_KBN"));
		ac.setUseHasDateCheck(TransUtil.TRUE == rs.getInt("HAS_DATE_CHK_KBN"));
		ac.setEvaluationRateDate(EvaluationRateDate.getEvaluationRateDate(rs.getInt("EXC_RATE_KBN")));
		ac.setCarryJournalOfMidtermClosingForward(rs.getInt("KSN_KURI_KBN") == 0);

		// 伝票有効期限のチェック有無
		ac.setSlipTermCheck(ServerConfig.isSlipTermCheck());

		bean.setAccountConfig(ac);

		// 会計期間情報マッピング
		FiscalPeriod fiscalPeriod = createFiscalPeriod();
		fiscalPeriod.setFiscalYear(rs.getInt("NENDO"));
		fiscalPeriod.setClosedMonth(rs.getInt("SIM_MON"));
		fiscalPeriod.setSettlementStage(rs.getInt("KSN_KBN"));
		fiscalPeriod.setMonthBeginningOfPeriod(rs.getInt("CMP_KISYU"));
		fiscalPeriod.setDateBeginningOfPeriod(rs.getDate("SIM_STR_DATE"));
		fiscalPeriod.setDateEndOfPeriod(rs.getDate("SIM_END_DATE"));
		int settlementTerm = rs.getInt("KSN_NYU_KBN");
		fiscalPeriod.setSettlementTerm(SettlementTerm.getSettlementTerm(settlementTerm));
		fiscalPeriod.setMaxSettlementStage(rs.getInt("KSD_KBN"));

		bean.setFiscalPeriod(fiscalPeriod);

		// 円通貨、ドル通貨の設定
		Currency jpy = new Currency();
		jpy.setCompanyCode(rs.getString("KAI_CODE"));
		jpy.setCode(rs.getString("JPY_CUR_CODE"));
		jpy.setNames(rs.getString("JPY_CUR_NAME_S"));
		jpy.setDecimalPoint(rs.getInt("JPY_DEC_KETA"));
		bean.setJPY(jpy);

		Currency usd = new Currency();
		usd.setCompanyCode(rs.getString("KAI_CODE"));
		usd.setCode(rs.getString("USD_CUR_CODE"));
		usd.setNames(rs.getString("USD_CUR_NAME_S"));
		usd.setDecimalPoint(rs.getInt("USD_DEC_KETA"));
		bean.setUSD(usd);

		return bean;
	}

	public List<TransferConfig> getTransferConfig(String fromCompanyCode, String toCompanyCode) throws TException {

		Connection conn = DBUtil.getConnection();

		List<TransferConfig> list = new ArrayList<TransferConfig>();

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("   ktk.KAI_CODE, ");
			sql.add("   ktk.KTK_KAI_CODE, ");
			sql.add("   ktk.KTK_DEP_CODE, ");
			sql.add("   ktk.KTK_KMK_CODE, ");
			sql.add("   ktk.KTK_HKM_CODE, ");
			sql.add("   ktk.KTK_UKM_CODE, ");
			sql.add("   ktk.KTK_TRI_CODE, ");
			sql.add("   dep.DEP_NAME, ");
			sql.add("   dep.DEP_NAME_S, ");
			sql.add("   kmk.KMK_NAME, ");
			sql.add("   kmk.KMK_NAME_S, ");
			sql.add("   hkm.HKM_NAME, ");
			sql.add("   hkm.HKM_NAME_S, ");
			sql.add("   ukm.UKM_NAME, ");
			sql.add("   ukm.UKM_NAME_S, ");
			sql.add("   tri.TRI_NAME, ");
			sql.add("   tri.TRI_NAME_S ");
			sql.add(" FROM ");
			sql.add(" KTK_MST ktk ");
			sql.add(" LEFT OUTER JOIN BMN_MST dep ");
			sql.add(" ON	ktk.KAI_CODE = dep.KAI_CODE ");
			sql.add(" AND	ktk.KTK_DEP_CODE = dep.DEP_CODE ");
			sql.add(" LEFT OUTER JOIN KMK_MST kmk ");
			sql.add(" ON	ktk.KAI_CODE = kmk.KAI_CODE ");
			sql.add(" AND	ktk.KTK_KMK_CODE = kmk.KMK_CODE ");
			sql.add(" LEFT OUTER JOIN HKM_MST hkm ");
			sql.add(" ON	ktk.KAI_CODE = hkm.KAI_CODE ");
			sql.add(" AND	ktk.KTK_KMK_CODE = hkm.KMK_CODE ");
			sql.add(" AND	ktk.KTK_HKM_CODE = hkm.HKM_CODE ");
			sql.add(" LEFT OUTER JOIN UKM_MST ukm ");
			sql.add(" ON	ktk.KAI_CODE = ukm.KAI_CODE ");
			sql.add(" AND	ktk.KTK_KMK_CODE = ukm.KMK_CODE ");
			sql.add(" AND	ktk.KTK_HKM_CODE = ukm.HKM_CODE ");
			sql.add(" AND	ktk.KTK_UKM_CODE = ukm.UKM_CODE ");
			sql.add(" LEFT OUTER JOIN TRI_MST tri ");
			sql.add(" ON	ktk.KAI_CODE = tri.KAI_CODE ");
			sql.add(" AND	ktk.KTK_TRI_CODE = tri.TRI_CODE ");
			sql.add(" WHERE 1 = 1 ");
			sql.add(" AND   (    ktk.KAI_CODE = ? ", fromCompanyCode);
			sql.add("        AND ktk.KTK_KAI_CODE = ? ", toCompanyCode);
			sql.add("       ) ");
			sql.add(" OR    (    ktk.KTK_KAI_CODE = ? ", fromCompanyCode);
			sql.add("       AND ktk.KAI_CODE = ? ", toCompanyCode);
			sql.add("       ) ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingTransferConfig(rs));
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
	 * 会社間付替O/Rマッピング
	 * 
	 * @param rs
	 * @return 会社間付替エンティティ
	 * @throws Exception
	 */
	protected TransferConfig mappingTransferConfig(ResultSet rs) throws Exception {

		TransferConfig bean = createTransferConfig();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setTransferCompanyCode(rs.getString("KTK_KAI_CODE"));
		bean.setTransferDepertmentCode(rs.getString("KTK_DEP_CODE"));
		bean.setTransferDepertmentName(rs.getString("DEP_NAME"));
		bean.setTransferDepertmentNames(rs.getString("DEP_NAME_S"));
		bean.setTransferItemCode(rs.getString("KTK_KMK_CODE"));
		bean.setTransferItemName(rs.getString("KMK_NAME"));
		bean.setTransferItemNames(rs.getString("KMK_NAME_S"));
		bean.setTransferSubItemCode(rs.getString("KTK_HKM_CODE"));
		bean.setTransferSubItemName(rs.getString("HKM_NAME"));
		bean.setTransferSubItemNames(rs.getString("HKM_NAME_S"));
		bean.setTransferDetailItemCode(rs.getString("KTK_UKM_CODE"));
		bean.setTransferDetailItemName(rs.getString("UKM_NAME"));
		bean.setTransferDetailItemNames(rs.getString("UKM_NAME_S"));
		bean.setTransferCustomerCode(rs.getString("KTK_TRI_CODE"));
		bean.setTransferCustomerName(rs.getString("TRI_NAME"));
		bean.setTransferCustomerNames(rs.getString("TRI_NAME_S"));

		return bean;
	}

	/**
	 * @return 会社間付替
	 */
	protected TransferConfig createTransferConfig() {
		return new TransferConfig();
	}

	/**
	 * @return 会計期間
	 */
	protected FiscalPeriod createFiscalPeriod() {
		return new FiscalPeriod();
	}

	/**
	 * @return 通貨
	 */
	protected Currency createCurrency() {
		return new Currency();
	}

	/**
	 * @return 会計の設定情報
	 */
	protected AccountConfig createAccountConfig() {
		return new AccountConfig();
	}

	/**
	 * @return 会社
	 */
	protected Company createCompany() {
		return new Company();
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  DISTINCT(DPK_SSK) DPK_SSK");
			sql.add("FROM EVK_MST ");
			sql.add("WHERE KAI_CODE = ?", getCompanyCode());
			sql.add("ORDER BY DPK_SSK ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingDepOrg(rs));
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
	 * 会社組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return CompanyOrganization
	 * @throws Exception
	 */
	protected CompanyOrganization mappingDepOrg(ResultSet rs) throws Exception {

		CompanyOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));

		return bean;
	}

	/**
	 * 指定条件に該当する会社組織情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する会社組織情報(会社階層マスタ用)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException {

		Connection conn = DBUtil.getConnection();
		List<CompanyOrganization> list = new ArrayList<CompanyOrganization>();

		try {

			VCreator sql = new VCreator();
			sql.add("SELECT");
			sql.add("  dpk.KAI_CODE");
			sql.add(" ,dpk.DPK_SSK");
			sql.add(" ,dpk.DPK_DEP_CODE");
			sql.add(" ,dep.DEP_NAME");
			sql.add(" ,dep.DEP_NAME_S");
			sql.add(" ,dpk.DPK_LVL");
			sql.add(" ,dpk.DPK_LVL_0");
			sql.add(" ,dep0.DEP_NAME DEP_NAME_0");
			sql.add(" ,dpk.DPK_LVL_1");
			sql.add(" ,dep1.DEP_NAME DEP_NAME_1");
			sql.add(" ,dpk.DPK_LVL_2");
			sql.add(" ,dep2.DEP_NAME DEP_NAME_2");
			sql.add(" ,dpk.DPK_LVL_3");
			sql.add(" ,dep3.DEP_NAME DEP_NAME_3");
			sql.add(" ,dpk.DPK_LVL_4");
			sql.add(" ,dep4.DEP_NAME DEP_NAME_4");
			sql.add(" ,dpk.DPK_LVL_5");
			sql.add(" ,dep5.DEP_NAME DEP_NAME_5");
			sql.add(" ,dpk.DPK_LVL_6");
			sql.add(" ,dep6.DEP_NAME DEP_NAME_6");
			sql.add(" ,dpk.DPK_LVL_7");
			sql.add(" ,dep7.DEP_NAME DEP_NAME_7");
			sql.add(" ,dpk.DPK_LVL_8");
			sql.add(" ,dep8.DEP_NAME DEP_NAME_8");
			sql.add(" ,dpk.DPK_LVL_9");
			sql.add(" ,dep9.DEP_NAME DEP_NAME_9");
			sql.add("FROM EVK_MST dpk");
			sql.add("LEFT JOIN BMN_MST dep ON dpk.KAI_CODE = dep.KAI_CODE ");
			sql.add("                     AND dpk.DPK_DEP_CODE = dep.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep0 ON dpk.KAI_CODE = dep0.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_0 = dep0.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep1 ON dpk.KAI_CODE = dep1.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_1 = dep1.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep2 ON dpk.KAI_CODE = dep2.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_2 = dep2.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep3 ON dpk.KAI_CODE = dep3.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_3 = dep3.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep4 ON dpk.KAI_CODE = dep4.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_4 = dep4.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep5 ON dpk.KAI_CODE = dep5.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_5 = dep5.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep6 ON dpk.KAI_CODE = dep6.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_6 = dep6.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep7 ON dpk.KAI_CODE = dep7.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_7 = dep7.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep8 ON dpk.KAI_CODE = dep8.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_8 = dep8.DEP_CODE ");
			sql.add("LEFT JOIN BMN_MST dep9 ON dpk.KAI_CODE = dep9.KAI_CODE ");
			sql.add("                     AND dpk.DPK_LVL_9 = dep9.DEP_CODE ");
			sql.add("WHERE dpk.KAI_CODE = ?", getCompanyCode());

			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("  AND dpk.DPK_SSK = ?", condition.getCode());
			}
			sql.add("ORDER BY CASE WHEN dpk.DPK_LVL = 0 THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_0");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_1 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_1 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_2 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_2 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_3 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_3 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_4 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_4 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_5 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_5 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_6 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_6 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_7 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_7 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_8 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_8 ");
			sql.add("        ,CASE WHEN dpk.DPK_LVL_9 IS NULL THEN 0 ELSE 1 END ");
			sql.add("        ,dpk.DPK_LVL_9 ");
			sql.add("        ,dpk.DPK_DEP_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mappingHierarchy(rs));
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
	 * 会社組織階層 O/Rマッピング
	 * 
	 * @param rs
	 * @return CompanyOrganization
	 * @throws Exception
	 */
	protected CompanyOrganization mappingHierarchy(ResultSet rs) throws Exception {

		CompanyOrganization bean = createOrganization();

		bean.setCode(rs.getString("DPK_SSK"));
		bean.setCmpCode(rs.getString("DPK_DEP_CODE"));
		bean.setCmpName(rs.getString("DEP_NAME"));
		bean.setCmpNames(rs.getString("DEP_NAME_S"));
		bean.setLevel(rs.getInt("DPK_LVL"));
		bean.setLevel0(rs.getString("DPK_LVL_0"));
		bean.setLevel0Name(rs.getString("DEP_NAME_0"));
		bean.setLevel1(rs.getString("DPK_LVL_1"));
		bean.setLevel1Name(rs.getString("DEP_NAME_1"));
		bean.setLevel2(rs.getString("DPK_LVL_2"));
		bean.setLevel2Name(rs.getString("DEP_NAME_2"));
		bean.setLevel3(rs.getString("DPK_LVL_3"));
		bean.setLevel3Name(rs.getString("DEP_NAME_3"));
		bean.setLevel4(rs.getString("DPK_LVL_4"));
		bean.setLevel4Name(rs.getString("DEP_NAME_4"));
		bean.setLevel5(rs.getString("DPK_LVL_5"));
		bean.setLevel5Name(rs.getString("DEP_NAME_5"));
		bean.setLevel6(rs.getString("DPK_LVL_6"));
		bean.setLevel6Name(rs.getString("DEP_NAME_6"));
		bean.setLevel7(rs.getString("DPK_LVL_7"));
		bean.setLevel7Name(rs.getString("DEP_NAME_7"));
		bean.setLevel8(rs.getString("DPK_LVL_8"));
		bean.setLevel8Name(rs.getString("DEP_NAME_8"));
		bean.setLevel9(rs.getString("DPK_LVL_9"));
		bean.setLevel9Name(rs.getString("DEP_NAME_9"));

		return bean;
	}

	/**
	 * @return 会社階層
	 */
	protected CompanyOrganization createOrganization() {
		return new CompanyOrganization();
	}

	/**
	 * 会社階層マスタに登録されていない会社リスト取得
	 * 
	 * @param companyCode
	 * @param condition
	 * @return 会社階層マスタに登録されていない会社リスト
	 * @throws TException
	 */
	public List<String> getNotExistCompanyList(String companyCode, CompanyOutputCondition condition) throws TException {

		List<String> list = new ArrayList<String>();
		Connection conn = DBUtil.getConnection();

		try {
			VCreator sql = new VCreator();

			sql.add(" SELECT e.KAI_CODE ");
			sql.add("       ,e.KAI_NAME ");
			sql.add(" FROM ENV_MST e ");
			sql.add(" WHERE NOT EXISTS (SELECT 1 ");
			sql.add("                   FROM EVK_MST dpk ");
			sql.add("                   WHERE dpk.KAI_CODE = ? ", companyCode);
			sql.add("                     AND dpk.DPK_DEP_CODE = e.KAI_CODE ");
			sql.add("                  )");
			sql.add(" ORDER BY e.KAI_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			StringBuilder sb = null;

			while (rs.next()) {
				sb = new StringBuilder();
				sb.append(Util.avoidNull(rs.getString("KAI_CODE")));
				sb.append(" ");
				sb.append(Util.avoidNull(rs.getString("KAI_NAME")));
				list.add(sb.toString());
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (TException ex) {
			throw ex;
		} catch (Exception ex) {
			throw new TException(ex);
		} finally {
			DBUtil.close(conn);
		}
		return list;

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

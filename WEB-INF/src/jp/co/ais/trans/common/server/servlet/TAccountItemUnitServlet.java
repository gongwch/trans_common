package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;

/**
 * 共通サーブレット
 */
public class TAccountItemUnitServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -5319125982776071994L;

	/**
	 * リクエストを処理する
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			// 判定用flag
			String flag = req.getParameter("FLAG");

			// 科目検索
			if ("KMK_MST".equals(flag)) {
				findKmkMst(req, resp);
			}
			// 補助科目検索
			else if ("HKM_MST".equals(flag)) {
				findHkmMst(req, resp);
			}
			// 内訳科目検索
			else if ("UKM_MST".equals(flag)) {
				findUkmMst(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * 科目マスタのデータを取得する
	 * 
	 * @param req
	 * @param resp
	 */
	private void findKmkMst(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 検索のパラメータを設定する。// 検索のパラメータを設定する。
			TUserInfo userInfo = refTServerUserInfo(req);
			String strEmpCode = userInfo.getEmployerCode(); // ログインユーザー社員コード

			// 会社コード
			String strKaiCode = req.getParameter("KAI_CODE");
			// 科目コード
			String strKmkCode = req.getParameter("KMK_CODE");
			// 伝票日付
			String strSlipDate = req.getParameter("SLIP_DATE");
			// BS勘定消込区分
			String strKesiKbn = req.getParameter("KESI_KBN");
			// 集計区分
			String strSumKbn = req.getParameter("SUM_KBN");
			// ＧＬ科目制御区分
			String strKmkCntGl = req.getParameter("KMK_CNT_GL");
			// 部門コード
			String strBmnCode = req.getParameter("BMN_CODE");
			// 評価替対象フラグ
			String strExcFlg = req.getParameter("EXC_FLG");
			// 入金伝票入力フラグ
			String strNyuKin = req.getParameter("NYU_KIN");
			// 出金伝票入力フラグ
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
			// 振替伝票入力フラグ
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// 経費精算伝票入力フラグ
			String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
			// 債務計上入力フラグ
			String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
			// 債権計上入力フラグ
			String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
			// 債権消込伝票入力フラグ
			String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
			// 資産計上伝票入力フラグ
			String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
			// 支払依頼伝票入力フラグ
			String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));
			// AR制御区分
			String strKmkCntAr = Util.avoidNull(req.getParameter("KMK_CNT_AR"));
			// AR制御区分(消込用)
			String strKmkCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_UN_AR"));
			// AP制御区分
			String strKmkCntAp = Util.avoidNull(req.getParameter("KMK_CNT_AP"));

			// 科目種別
			String strKmkShu = Util.avoidNull(req.getParameter("KMK_SHU"));
			// 貸借区分
			String strDcKbn = Util.avoidNull(req.getParameter("DC_KBN"));
			// 補助区分
			String strHkmKbn = Util.avoidNull(req.getParameter("HKM_KBN"));
			// 固定部門ｺｰﾄﾞ
			String strKoteiDepCode = Util.avoidNull(req.getParameter("KOTEI_DEP_CODE"));
			// 消費税ｺｰﾄﾞ
			String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
			// 取引先入力ﾌﾗｸﾞ
			String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
			// 発生日入力ﾌﾗｸﾞ
			String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
			// 社員入力ﾌﾗｸﾞ
			String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
			// 管理1
			String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
			// 管理2
			String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
			// 管理3
			String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
			// 管理4
			String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
			// 管理5
			String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
			// 管理6
			String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
			// 非会計1
			String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
			// 非会計2
			String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
			// 非会計3
			String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
			// 売上課税入力ﾌﾗｸﾞ
			String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
			// 仕入課税入力ﾌﾗｸﾞ
			String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
			// 多通貨入力ﾌﾗｸﾞ
			String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));
			// BG科目制御区分
			String strKmkCntBg = Util.avoidNull(req.getParameter("KMK_CNT_BG"));
			// 相殺科目制御区分
			String strKmkCntSousai = Util.avoidNull(req.getParameter("KMK_CNT_SOUSAI"));
			// 科目体系コード
			String kmkSystemCode = Util.avoidNull(req.getParameter("KMK_TK_CODE"));
			// 科目体系フラグ
			String kmkSystemFlg = Util.avoidNull(req.getParameter("KMK_TK_FLG"));

			// パラメーターを設定する
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
			inputParameter.setEmployeeCode(strEmpCode); // 社員ｺｰﾄﾞ
			inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
			inputParameter.setSlipDate(strSlipDate); // 伝票日付
			inputParameter.setBsAccountErasingDivision(strKesiKbn); // BS勘定消込区分
			inputParameter.setSummaryDivision(strSumKbn); // 集計区分
			inputParameter.setGlItemCtrlDivision(strKmkCntGl); // GL科目制御区分
			inputParameter.setDepartmentCode(strBmnCode); // 部門ｺｰﾄﾞ
			inputParameter.setRevaluationObjectFlag(strExcFlg); // 評価替対象フラグ
			inputParameter.setRecivingSlipInputFlag(strNyuKin); // 入出金伝票入力フラグ
			inputParameter.setDrawingSlipInputFlag(strShutsuKin); // 出金伝票入力フラグ
			inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // 振替伝票入力フラグ
			inputParameter.setExpenseInputFlag(strKeihiFlg); // 経費精算伝票入力フラグ
			inputParameter.setSaimuFlg(strSaimuFlg); // 債務計上入力フラグ
			inputParameter.setSaikenFlg(strSaikenFlg); // 債権計上入力フラグ
			inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // 債権消込伝票入力フラグ
			inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // 資産計上伝票入力フラグ
			inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // 支払依頼伝票入力フラグ
			inputParameter.setArItemCtrlDivision(strKmkCntAr); // AR科目制御区分
			inputParameter.setUnArItemCtrlDivision(strKmkCntUnAr); // AR制御区分(消込用)
			inputParameter.setApItemCtrlDivision(strKmkCntAp); // AP科目制御区分

			inputParameter.setItemType(strKmkShu); // 科目種別
			inputParameter.setDebitAndCreditDivision(strDcKbn); // 貸借区分
			inputParameter.setSubItemDivision(strHkmKbn); // 補助区分
			inputParameter.setFixedDepartment(strKoteiDepCode); // 固定部門ｺｰﾄﾞ
			inputParameter.setConsumptionTaxCode(strZeiCode); // 消費税ｺｰﾄﾞ
			inputParameter.setCustomerInputFlag(strTriCodeFlg); // 取引先入力ﾌﾗｸﾞ
			inputParameter.setAccrualDateInputFlag(strHasFlg); // 発生日入力ﾌﾗｸﾞ
			inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // 社員入力ﾌﾗｸﾞ
			inputParameter.setManagement1InputFlag(strKnrFlg1); // 管理1
			inputParameter.setManagement2InputFlag(strKnrFlg2); // 管理2
			inputParameter.setManagement3InputFlag(strKnrFlg3); // 管理3
			inputParameter.setManagement4InputFlag(strKnrFlg4); // 管理4
			inputParameter.setManagement5InputFlag(strKnrFlg5); // 管理5
			inputParameter.setManagement6InputFlag(strKnrFlg6); // 管理6
			inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // 非会計1
			inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // 非会計2
			inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // 非会計3
			inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // 売上課税入力ﾌﾗｸﾞ
			inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // 仕入課税入力ﾌﾗｸﾞ
			inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // 多通貨入力ﾌﾗｸﾞ
			inputParameter.setBgItemCtrlDivision(strKmkCntBg); // BG科目制御区分
			inputParameter.setCounterbalanceAdjustmentCtrlDivision(strKmkCntSousai); // 相殺科目制御区分
			inputParameter.setItemSystemCode(kmkSystemCode); // 科目体系コード
			inputParameter.setItemSystemFlg(kmkSystemFlg); // 科目体系フラグ

			S2Container container = SingletonS2ContainerFactory.getContainer();
			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

			// 科目を検索
			Map<String, Object> map = logic.getItemInfo(inputParameter);

			dispatchResultMap(req, resp, map);
		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * 補助科目マスタのデータを取得する
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void findHkmMst(HttpServletRequest req, HttpServletResponse resp) throws TException {

		// 検索のパラメータを設定する。// 検索のパラメータを設定する。
		TUserInfo userInfo = refTServerUserInfo(req);
		String strEmpCode = userInfo.getEmployerCode(); // ログインユーザー社員コード

		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 科目コード
		String strKmkCode = req.getParameter("KMK_CODE");
		// 補助科目コード
		String strHkmCode = req.getParameter("HKM_CODE");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 入金伝票入力フラグ
		String strNyuKin = req.getParameter("NYU_KIN");
		// 出金伝票入力フラグ
		String strShutsuKin = req.getParameter("SHUTSU_KIN");
		// 振替伝票入力フラグ
		String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
		// 経費精算伝票入力フラグ
		String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
		// 債務計上入力フラグ
		String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
		// 債権計上入力フラグ
		String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
		// 債権消込伝票入力フラグ
		String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
		// 資産計上伝票入力フラグ
		String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
		// 支払依頼伝票入力フラグ
		String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));

		// 評価替対象フラグ
		String strExcFlg = req.getParameter("EXC_FLG");
		// 内訳区分
		String strUkmKbn = req.getParameter("UKM_KBN");
		// 消費税ｺｰﾄﾞ
		String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
		// 取引先入力ﾌﾗｸﾞ
		String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
		// 発生日入力ﾌﾗｸﾞ
		String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
		// 社員入力ﾌﾗｸﾞ
		String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
		// 管理1
		String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
		// 管理2
		String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
		// 管理3
		String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
		// 管理4
		String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
		// 管理5
		String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
		// 管理6
		String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
		// 非会計1
		String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
		// 非会計2
		String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
		// 非会計3
		String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
		// 売上課税入力ﾌﾗｸﾞ
		String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
		// 仕入課税入力ﾌﾗｸﾞ
		String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
		// 多通貨入力ﾌﾗｸﾞ
		String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));

		// パラメーターを設定する
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
		inputParameter.setEmployeeCode(strEmpCode); // 社員ｺｰﾄﾞ
		inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
		inputParameter.setSubItemCode(strHkmCode); // 補助科目ｺｰﾄﾞ
		inputParameter.setSlipDate(strSlipDate); // 伝票日付
		inputParameter.setRecivingSlipInputFlag(strNyuKin); // 入出金伝票入力フラグ
		inputParameter.setDrawingSlipInputFlag(strShutsuKin); // 出金伝票入力フラグ
		inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // 振替伝票入力フラグ
		inputParameter.setExpenseInputFlag(strKeihiFlg); // 経費精算伝票入力フラグ
		inputParameter.setSaimuFlg(strSaimuFlg); // 債務計上入力フラグ
		inputParameter.setSaikenFlg(strSaikenFlg); // 債権計上入力フラグ
		inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // 債権消込伝票入力フラグ
		inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // 資産計上伝票入力フラグ
		inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // 支払依頼伝票入力フラグ

		inputParameter.setRevaluationObjectFlag(strExcFlg); // 評価替対象フラグ
		inputParameter.setBreakDownItemDivision(strUkmKbn); // 内訳区分
		inputParameter.setConsumptionTaxCode(strZeiCode); // 消費税ｺｰﾄﾞ
		inputParameter.setCustomerInputFlag(strTriCodeFlg); // 取引先入力ﾌﾗｸﾞ
		inputParameter.setAccrualDateInputFlag(strHasFlg); // 発生日入力ﾌﾗｸﾞ
		inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // 社員入力ﾌﾗｸﾞ
		inputParameter.setManagement1InputFlag(strKnrFlg1); // 管理1
		inputParameter.setManagement2InputFlag(strKnrFlg2); // 管理2
		inputParameter.setManagement3InputFlag(strKnrFlg3); // 管理3
		inputParameter.setManagement4InputFlag(strKnrFlg4); // 管理4
		inputParameter.setManagement5InputFlag(strKnrFlg5); // 管理5
		inputParameter.setManagement6InputFlag(strKnrFlg6); // 管理6
		inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // 非会計1
		inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // 非会計2
		inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // 非会計3
		inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // 売上課税入力ﾌﾗｸﾞ
		inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // 仕入課税入力ﾌﾗｸﾞ
		inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // 多通貨入力ﾌﾗｸﾞ

		S2Container container = SingletonS2ContainerFactory.getContainer();
		TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

		// 補助科目を検索
		Map<String, Object> map = logic.getSubItemInfo(inputParameter);

		dispatchResultMap(req, resp, map);
	}

	/**
	 * 内訳科目マスタのデータを取得する
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void findUkmMst(HttpServletRequest req, HttpServletResponse resp) throws TException {

		// 検索のパラメータを設定する
		TUserInfo userInfo = refTServerUserInfo(req);
		String strEmpCode = userInfo.getEmployerCode(); // ログインユーザー社員コード

		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 科目コード
		String strKmkCode = req.getParameter("KMK_CODE");
		// 補助科目コード
		String strHkmCode = req.getParameter("HKM_CODE");
		// 内訳科目コード
		String strUkmCode = req.getParameter("UKM_CODE");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 入金伝票入力フラグ
		String strNyuKin = req.getParameter("NYU_KIN");
		// 出金伝票入力フラグ
		String strShutsuKin = req.getParameter("SHUTSU_KIN");
		// 振替伝票入力フラグ
		String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
		// 経費精算伝票入力フラグ
		String strKeihiFlg = Util.avoidNull(req.getParameter("KEIHI_FLG"));
		// 債務計上入力フラグ
		String strSaimuFlg = Util.avoidNull(req.getParameter("SAIMU_FLG"));
		// 債権計上入力フラグ
		String strSaikenFlg = Util.avoidNull(req.getParameter("SAIKEN_FLG"));
		// 債権消込伝票入力フラグ
		String strSaikesiFlg = Util.avoidNull(req.getParameter("SAIKESI_FLG"));
		// 資産計上伝票入力フラグ
		String strSisanFlg = Util.avoidNull(req.getParameter("SISAN_FLG"));
		// 支払依頼伝票入力フラグ
		String strSiharaiFlg = Util.avoidNull(req.getParameter("SIHARAI_FLG"));

		// 評価替対象フラグ
		String strExcFlg = req.getParameter("EXC_FLG");
		// 消費税ｺｰﾄﾞ
		String strZeiCode = Util.avoidNull(req.getParameter("ZEI_CODE"));
		// 取引先入力ﾌﾗｸﾞ
		String strTriCodeFlg = Util.avoidNull(req.getParameter("TRI_CODE_FLG"));
		// 発生日入力ﾌﾗｸﾞ
		String strHasFlg = Util.avoidNull(req.getParameter("HAS_FLG"));
		// 社員入力ﾌﾗｸﾞ
		String strEmpCodeFlg = Util.avoidNull(req.getParameter("EMP_CODE_FLG"));
		// 管理1
		String strKnrFlg1 = Util.avoidNull(req.getParameter("KNR_FLG1"));
		// 管理2
		String strKnrFlg2 = Util.avoidNull(req.getParameter("KNR_FLG2"));
		// 管理3
		String strKnrFlg3 = Util.avoidNull(req.getParameter("KNR_FLG3"));
		// 管理4
		String strKnrFlg4 = Util.avoidNull(req.getParameter("KNR_FLG4"));
		// 管理5
		String strKnrFlg5 = Util.avoidNull(req.getParameter("KNR_FLG5"));
		// 管理6
		String strKnrFlg6 = Util.avoidNull(req.getParameter("KNR_FLG6"));
		// 非会計1
		String strHmFlg1 = Util.avoidNull(req.getParameter("HM_FLG1"));
		// 非会計2
		String strHmFlg2 = Util.avoidNull(req.getParameter("HM_FLG2"));
		// 非会計3
		String strHmFlg3 = Util.avoidNull(req.getParameter("HM_FLG3"));
		// 売上課税入力ﾌﾗｸﾞ
		String strUriZeiFlg = Util.avoidNull(req.getParameter("URI_ZEI_FLG"));
		// 仕入課税入力ﾌﾗｸﾞ
		String strSirZeiFlg = Util.avoidNull(req.getParameter("SIR_ZEI_FLG"));
		// 多通貨入力ﾌﾗｸﾞ
		String strMcrFlg = Util.avoidNull(req.getParameter("MCR_FLG"));

		// パラメーターを設定する
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
		inputParameter.setEmployeeCode(strEmpCode); // 社員ｺｰﾄﾞ
		inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
		inputParameter.setSubItemCode(strHkmCode); // 補助科目ｺｰﾄﾞ
		inputParameter.setBreakDownItemCode(strUkmCode); // 内訳科目ｺｰﾄﾞ
		inputParameter.setSlipDate(strSlipDate); // 伝票日付
		inputParameter.setRecivingSlipInputFlag(strNyuKin); // 入出金伝票入力フラグ
		inputParameter.setDrawingSlipInputFlag(strShutsuKin); // 出金伝票入力フラグ
		inputParameter.setTransferSlipInputFlag(strFurikaeFlg); // 振替伝票入力フラグ
		inputParameter.setExpenseInputFlag(strKeihiFlg); // 経費精算伝票入力フラグ
		inputParameter.setSaimuFlg(strSaimuFlg); // 債務計上入力フラグ
		inputParameter.setSaikenFlg(strSaikenFlg); // 債権計上入力フラグ
		inputParameter.setAccountsRecivableErasingSlipInputFlag(strSaikesiFlg); // 債権消込伝票入力フラグ
		inputParameter.setAssetsAppropriatingSlipInputFlag(strSisanFlg); // 資産計上伝票入力フラグ
		inputParameter.setPaymentRequestSlipInputFlag(strSiharaiFlg); // 支払依頼伝票入力フラグ

		inputParameter.setRevaluationObjectFlag(strExcFlg); // 評価替対象フラグ
		inputParameter.setConsumptionTaxCode(strZeiCode); // 消費税ｺｰﾄﾞ
		inputParameter.setCustomerInputFlag(strTriCodeFlg); // 取引先入力ﾌﾗｸﾞ
		inputParameter.setAccrualDateInputFlag(strHasFlg); // 発生日入力ﾌﾗｸﾞ
		inputParameter.setEmployeeInputFlag(strEmpCodeFlg); // 社員入力ﾌﾗｸﾞ
		inputParameter.setManagement1InputFlag(strKnrFlg1); // 管理1
		inputParameter.setManagement2InputFlag(strKnrFlg2); // 管理2
		inputParameter.setManagement3InputFlag(strKnrFlg3); // 管理3
		inputParameter.setManagement4InputFlag(strKnrFlg4); // 管理4
		inputParameter.setManagement5InputFlag(strKnrFlg5); // 管理5
		inputParameter.setManagement6InputFlag(strKnrFlg6); // 管理6
		inputParameter.setNonAccountingDetail1Flag(strHmFlg1); // 非会計1
		inputParameter.setNonAccountingDetail2Flag(strHmFlg2); // 非会計2
		inputParameter.setNonAccountingDetail3Flag(strHmFlg3); // 非会計3
		inputParameter.setSalesTaxInputFlag(strUriZeiFlg); // 売上課税入力ﾌﾗｸﾞ
		inputParameter.setPurchaseTaxationInputFlag(strSirZeiFlg); // 仕入課税入力ﾌﾗｸﾞ
		inputParameter.setMultipleCurrencyInputFlag(strMcrFlg); // 多通貨入力ﾌﾗｸﾞ

		S2Container container = SingletonS2ContainerFactory.getContainer();
		TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);

		// 内訳科目を検索
		Map<String, Object> map = logic.getBreakDownItemInfo(inputParameter);

		dispatchResultMap(req, resp, map);
	}
}

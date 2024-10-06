package jp.co.ais.trans.master.common.servlet;

import java.sql.*;
import java.text.*;
import java.util.*;
import java.util.Date;

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
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 検索ダイアログクラス<BR>
 * 共通検索ダイアログ画面のサーブレット
 * <p>
 * 
 * @author Yit
 */
public class REFDialogMasterServlet extends TServletBase {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/**
	 * doPost()メソッドでPOST形式で送信されたデータを処理
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ServletException 異常
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		// 判定用flag
		String strFlag = req.getParameter("FLAG");

		try {
			// 検索
			if ("BMN_MST".equals(strFlag)) {
				// 部門マスタ一覧
				searchBmnMstData(req, resp);
			} else if ("KMK_MST".equals(strFlag)) {
				// 科目検索
				searchKmkMstData(req, resp);
			} else if ("HKM_MST".equals(strFlag)) {
				// 補助科目マスタ一覧
				searchHkmMstData(req, resp);
			} else if ("UKM_MST".equals(strFlag)) {
				// 内訳科目マスタ一覧検索
				searchUkmMstData(req, resp);
			} else if ("ENV_MST".equals(strFlag)) {
				// 環境設定マスタ一覧検索
				searchEnvMstData(req, resp);
			} else if ("BankAccount".equals(strFlag)) {
				// 銀行口座マスタ一覧検索
				searchModifyReservationBankAccount(req, resp);
			} else if ("BankAccountB".equals(strFlag)) {
				// 銀行口座マスタ一覧検索(銀行名に銀行マスタ.銀行名+銀行マスタ.支店名)
				searchModifyReservationBankAccountB(req, resp);
			} else if ("refCustomer".equals(strFlag)) {
				// 取引先マスタ一覧検索
				searchRefCustomer(req, resp);
			} else if ("refCurrency".equals(strFlag)) {
				// 通貨マスタ一覧検索
				searchRefCurrency(req, resp);
			} else if ("refDepartment".equals(strFlag)) {
				// 部門マスタ一覧検索
				searchRefDepartment(req, resp);
			} else if ("refMemo".equals(strFlag)) {
				// 摘要マスタ一覧検索（行摘要、伝票摘要共通）
				searchRefMemo(req, resp);
			} else if ("refTax".equals(strFlag)) {
				// 消費税マスタ一覧検索
				searchRefTax(req, resp);
			} else if ("refEmployee".equals(strFlag)) {
				// 社員検索マスタ一覧検索
				searchRefEmployee(req, resp);
			} else if ("refPayment".equals(strFlag)) {
				// 支払い方法マスタ一覧検索
				searchRefPayment(req, resp);
			} else if ("refPaymentCondition".equals(strFlag)) {
				// 支払条件マスタ一覧検索
				searchRefPaymentCon(req, resp);
			} else if ("EMP_MST".equals(strFlag)) {
				// 社員マスタ一覧
				searchEmpMstData(req, resp);
			} else if ("KNR_MST".equals(strFlag)) {
				// 管理マスタ一覧
				searchKnrMstData(req, resp);
			} else if ("KMT_MST".equals(strFlag)) {
				// 科目体系マスタ一覧
				searchKmkTkMstData(req, resp);
			} else if ("APP_KAI".equals(strFlag)) {
				// 計上会社検索
				searchRefCompany(req, resp);
			} else if ("BNK_BNK_MST".equals(strFlag)) {
				// 銀行検索
				searchBMKMstData(req, resp);
			} else if ("BNK_STN_MST".equals(strFlag)) {
				// 銀行支店検索
				searchBMKSTNMstData(req, resp);
			}

		} catch (Exception e) {
			super.handledException(e, req, resp);
		}
	}

	/**
	 * 部門マスタ検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchBmnMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);

		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 部門コード
		String strDepCode = req.getParameter("DEP_CODE");
		// 部門略称
		String strDepName = req.getParameter("DEP_NAME");
		// 部門検索名称
		String strDepNameK = req.getParameter("DEP_NAME_K");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 組織コード
		String strDpkSsk = req.getParameter("DPK_SSK");
		// 配下部門(0:含む 1:含まない)
		String strBmnKbn = req.getParameter("BMN_KBN");
		// 上位部門ｺｰﾄﾞ
		String strUpBmnCode = req.getParameter("UP_BMN_CODE");
		// 階層ﾚﾍﾞﾙ
		String strDpkLvl = req.getParameter("DPK_LVL");

		// 初期部門ｺｰﾄﾞ
		String strBaseBmnCode = req.getParameter("BASE_BMN_CODE");
		// 初期階層ﾚﾍﾞﾙ
		String strBaseDpkLvl = req.getParameter("BASE_DPK_LVL");

		// Likeを利用するため
		strDepCode = DBUtil.getLikeStatement(strDepCode, DBUtil.NORMAL_CHAR);
		strDepName = DBUtil.getLikeStatement(strDepName, DBUtil.UNICODE_CHAR);
		strDepNameK = DBUtil.getLikeStatement(strDepNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// 結果を取得する。
		list = logic.searchBmnMstData(strKaiCode, strDepCode, strDepName, strDepNameK, strSlipDate, strDpkSsk,
			strBmnKbn, strUpBmnCode, strDpkLvl, strBaseDpkLvl, strBaseBmnCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * 環境設定マスタ一覧検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchEnvMstData(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		// 会社コード
		String compCode = Util.avoidNull(req.getParameter("KAI_CODE"));
		// 会社名称
		String shortName = Util.avoidNull(req.getParameter("KAI_NAME_S"));
		// ログイン会社コード
		String loginKaiCode = Util.avoidNull(req.getParameter("LOGIN_KAI_CODE"));
		// 組織コード
		String strDpkSsk = req.getParameter("DPK_SSK");
		// 配下会社(0:含む 1:含まない)
		String strCmpKbn = req.getParameter("COMPANY_KBN");
		// 上位会社ｺｰﾄﾞ
		String strUpCmpCode = req.getParameter("UP_COMPANY_CODE");
		// 階層ﾚﾍﾞﾙ
		String strDpkLvl = req.getParameter("DPK_LVL");
		// 初期会社ｺｰﾄﾞ
		String strBaseCmpCode = req.getParameter("BASE_COMPANY_CODE");
		// 初期階層ﾚﾍﾞﾙ
		String strBaseDpkLvl = req.getParameter("BASE_DPK_LVL");

		// Likeを利用するため
		if (!Util.isNullOrEmpty(compCode)) {
			compCode = DBUtil.getLikeStatement(compCode, DBUtil.NORMAL_CHAR);
		}

		if (!Util.isNullOrEmpty(shortName)) {
			shortName = DBUtil.getLikeStatement(shortName, DBUtil.UNICODE_CHAR);
		}

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CommonLogic logic = (CommonLogic) container.getComponent("EnvironmentalSettingLogic");

		Map param = new TreeMap();
		param.put("KAI_CODE", compCode);
		param.put("KAI_NAME_S", shortName);
		param.put("LOGIN_KAI_CODE", loginKaiCode);
		param.put("DPK_SSK", strDpkSsk);
		param.put("CMP_KBN", strCmpKbn);
		param.put("UP_CMP_CODE", strUpCmpCode);
		param.put("DPK_LVL", strDpkLvl);
		param.put("BASE_CMP_CODE", strBaseCmpCode);
		param.put("BASE_DPK_LVL", strBaseDpkLvl);

		dispatchResultListObject(req, resp, logic.findREF(param));
	}

	/**
	 * 科目一覧検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchKmkMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// 会社コード
			String strKaiCode = req.getParameter("KAI_CODE");
			// 科目コード
			String strKmkCode = req.getParameter("KMK_CODE");
			// 科目略称
			String strKmkName = req.getParameter("KMK_NAME");
			// 科目検索名称
			String strKmkNameK = req.getParameter("KMK_NAME_K");
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
			// AR科目制御区分
			String strKmKCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_AR"));
			// AR制御区分(消込用)
			String strKmkCntUnAr = Util.avoidNull(req.getParameter("KMK_CNT_UN_AR"));
			// AP科目制御区分
			String strKmkCntUnAp = req.getParameter("KMK_CNT_AP");
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
			String kmkTkCode = Util.avoidNull(req.getParameter("KMK_TK_CODE"));
			// 科目体系フラグ
			String kmkSystemFlg = Util.avoidNull(req.getParameter("KMK_TK_FLG"));

			// Likeを利用するため
			strKmkCode = DBUtil.getLikeStatement(strKmkCode, DBUtil.NORMAL_CHAR);
			strKmkName = DBUtil.getLikeStatement(strKmkName, DBUtil.UNICODE_CHAR);
			strKmkNameK = DBUtil.getLikeStatement(strKmkNameK, DBUtil.UNICODE_CHAR);

			// パラメーターを設定する
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
			inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
			inputParameter.setItemAbbrName(strKmkName); // 科目略称
			inputParameter.setItemNameForSearch(strKmkNameK); // 科目検索名称
			inputParameter.setSlipDate(strSlipDate); // 伝票日付
			inputParameter.setBsAccountErasingDivision(strKesiKbn); // BS勘定消込区分
			inputParameter.setSummaryDivision(strSumKbn); // 集計区分
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
			inputParameter.setGlItemCtrlDivision(strKmkCntGl); // GL科目制御区分
			inputParameter.setArItemCtrlDivision(strKmKCntUnAr); // AR科目制御区分
			inputParameter.setUnArItemCtrlDivision(strKmkCntUnAr); // AR制御区分(消込用)
			inputParameter.setApItemCtrlDivision(strKmkCntUnAp); // AP科目制御区分

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
			inputParameter.setItemSystemCode(kmkTkCode); // 科目体系コード
			inputParameter.setItemSystemFlg(kmkSystemFlg); // 科目体系フラグ

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // 開始コード
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // 終了コード

			dispatchResultListObject(req, resp, logic.getItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * 補助科目マスタ一覧検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchHkmMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// 会社コード
			String strKaiCode = req.getParameter("KAI_CODE");
			// 科目コード
			String strKmkCode = req.getParameter("KMK_CODE");
			// 補助科目コード
			String strHkmCode = req.getParameter("HKM_CODE");
			// 補助科目略称
			String strHkmName = req.getParameter("HKM_NAME");
			// 補助科目検索名称
			String strHkmNameK = req.getParameter("HKM_NAME_K");
			// 伝票日付
			String strSlipDate = req.getParameter("SLIP_DATE");
			// 振替伝票入力フラグ
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// 入金伝票入力フラグ
			String strNyuKin = req.getParameter("NYU_KIN");
			// 出金伝票入力フラグ
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
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

			// Likeを利用するため
			strHkmCode = DBUtil.getLikeStatement(strHkmCode, DBUtil.NORMAL_CHAR);
			strHkmName = DBUtil.getLikeStatement(strHkmName, DBUtil.UNICODE_CHAR);
			strHkmNameK = DBUtil.getLikeStatement(strHkmNameK, DBUtil.UNICODE_CHAR);

			// パラメーターを設定する
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
			inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
			inputParameter.setSubItemCode(strHkmCode); // 補助科目ｺｰﾄﾞ
			inputParameter.setSubItemAbbrName(strHkmName); // 補助科目略所
			inputParameter.setSubItemNameForSearch(strHkmNameK); // 補助科目検索名称
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

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // 開始コード
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // 終了コード

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

			dispatchResultListObject(req, resp, logic.getSubItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}

	}

	/**
	 * 内訳科目マスタ一覧検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchUkmMstData(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			TAccountItemUnitLogic logic = (TAccountItemUnitLogic) container.getComponent(TAccountItemUnitLogic.class);
			// 会社コード
			String strKaiCode = req.getParameter("KAI_CODE");
			// 科目コード
			String strKmkCode = req.getParameter("KMK_CODE");
			// 補助科目コード
			String strHkmCode = req.getParameter("HKM_CODE");
			// 内訳科目コード
			String strUkmCode = req.getParameter("UKM_CODE");
			// 内訳科目略称
			String strUkmName = req.getParameter("UKM_NAME");
			// 内訳科目検索名称
			String strUkmNameK = req.getParameter("UKM_NAME_K");
			// 伝票日付
			String strSlipDate = req.getParameter("SLIP_DATE");
			// 振替伝票入力フラグ
			String strFurikaeFlg = req.getParameter("FURIKAE_FLG");
			// 入金伝票入力フラグ
			String strNyuKin = req.getParameter("NYU_KIN");
			// 出金伝票入力フラグ
			String strShutsuKin = req.getParameter("SHUTSU_KIN");
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

			// Likeを利用するため
			strUkmCode = DBUtil.getLikeStatement(strUkmCode, DBUtil.NORMAL_CHAR);
			strUkmName = DBUtil.getLikeStatement(strUkmName, DBUtil.UNICODE_CHAR);
			strUkmNameK = DBUtil.getLikeStatement(strUkmNameK, DBUtil.UNICODE_CHAR);

			// パラメーターを設定する
			AccountItemInputParameter inputParameter = new AccountItemInputParameter();
			inputParameter.setCompanyCode(strKaiCode); // 会社ｺｰﾄﾞ
			inputParameter.setItemCode(strKmkCode); // 科目ｺｰﾄﾞ
			inputParameter.setSubItemCode(strHkmCode); // 補助科目ｺｰﾄﾞ
			inputParameter.setBreakDownItemCode(strUkmCode); // 内訳科目ｺｰﾄﾞ
			inputParameter.setBreakDownItemAbbrName(strUkmName); // 内訳略称
			inputParameter.setBreakDownItemNameForSearch(strUkmNameK); // 内訳検索名称
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

			inputParameter.setBeginCode(Util.avoidNull(req.getParameter("BEGIN_CODE"))); // 開始コード
			inputParameter.setEndCode(Util.avoidNull(req.getParameter("END_CODE"))); // 終了コード

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

			dispatchResultListObject(req, resp, logic.getBreakDownItemInfoAll(inputParameter));

		} catch (TException e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * 銀行口座検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchModifyReservationBankAccount(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		BankAccountLogic logic = (BankAccountLogic) container.getComponent(BankAccountLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("KaiCode");

		// コード
		String code = req.getParameter("Code");
		// 検索名称
		String nameS = req.getParameter("NameS");
		// 検索略称
		String nameK = req.getParameter("NameK");
		// 社員支払いフラグ
		boolean empKbn = BooleanUtil.toBoolean(req.getParameter("empKbn"));
		// 社外支払いフラグ
		boolean outKbn = BooleanUtil.toBoolean(req.getParameter("outKbn"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");
			try {
				BaseDate = DateUtil.toYMDDate(termBasisDate);
			} catch (ParseException e) {
				throw new TRuntimeException();
			}
			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.searchReservationBankAccount(kaiCode, code, nameS, nameK, outKbn, empKbn,
			stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 銀行口座マスタ一覧検索(銀行名に銀行マスタ.銀行名+銀行マスタ.支店名)
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchModifyReservationBankAccountB(HttpServletRequest req, HttpServletResponse resp)
		throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// daoの初期化
		AP_CBK_MSTDao dao = (AP_CBK_MSTDao) container.getComponent(AP_CBK_MSTDao.class);

		ApCbkMstParameter param = new ApCbkMstParameter();
		// 会社コード
		param.setKaiCode(req.getParameter("KaiCode"));

		// 銀行口座コード
		param.setLikeCbkCode(DBUtil.getLikeStatement(req.getParameter("Code"), DBUtil.NORMAL_CHAR));
		// 検索名称
		param.setLikeNameS(DBUtil.getLikeStatement(req.getParameter("NameS"), DBUtil.UNICODE_CHAR));
		// 口座番号
		param.setLikeCbkYknNo(DBUtil.getLikeStatement(req.getParameter("NameK"), DBUtil.NORMAL_CHAR));
		// 社員支払フラグ
		param.setEmpFbKbn(BooleanUtil.toBoolean(req.getParameter("empKbn")));
		// 社外支払フラグ
		param.setOutFbKbn(BooleanUtil.toBoolean(req.getParameter("outKbn")));
		// 銀行口座リスト
		param.setCbkCodes((List<String>) getObjectParameter(req));

		// 有効期間日付
		String termBasisDate = req.getParameter("termBasisDate");
		if (!Util.isNullOrEmpty(termBasisDate)) {
			param.setTermBasisDate(DateUtil.toYMDDate(termBasisDate));
		}

		// 結果を取得する。
		List<Object> list = dao.getApCbkMst(param);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 取引先検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefCustomer(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String code = req.getParameter("triCode");
		// 検索名称
		String nameS = req.getParameter("sName");
		// 検索略称
		String nameK = req.getParameter("kName");
		// 開始コード
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// 終了コード
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		// 仕入先と得意先フラグ
		boolean siire = BooleanUtil.toBoolean(req.getParameter("siire"));
		boolean tokui = BooleanUtil.toBoolean(req.getParameter("tokui"));

		// 有効期間
		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.refSearchCustomer(kaiCode, code, nameS, nameK, stampBasisDate, siire, tokui,
			beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 通貨ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefCurrency(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		CurrencyLogic logic = (CurrencyLogic) container.getComponent(CurrencyLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String code = req.getParameter("curCode");
		// 検索名称
		String nameS = req.getParameter("sName");
		// 検索略称
		String nameK = req.getParameter("kName");

		// 開始コード
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// 終了コード
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		code = DBUtil.getLikeStatement(code, DBUtil.NORMAL_CHAR);
		nameS = DBUtil.getLikeStatement(nameS, DBUtil.UNICODE_CHAR);
		nameK = DBUtil.getLikeStatement(nameK, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.refDailogCurrency(kaiCode, code, nameS, nameK, stampBasisDate, beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 計上会社ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefCompany(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		TAppropriateCompanyLogic logic = (TAppropriateCompanyLogic) container
			.getComponent(TAppropriateCompanyLogic.class);

		AppropriateCompany param = (AppropriateCompany) this.getDtoParameter(req, AppropriateCompany.class);
		// 操作区分
		param.setBlnOptKbn(true);

		// 基軸通貨コード
		param.setCUR_CODE(refTServerUserInfo(req).getCompanyInfo().getBaseCurrencyCode());

		// Likeを利用するため
		param.setKAI_CODE(DBUtil.getLikeStatement(param.getKAI_CODE(), DBUtil.NORMAL_CHAR));
		param.setKAI_NAME_S(DBUtil.getLikeStatement(param.getKAI_NAME_S(), DBUtil.UNICODE_CHAR));

		// 結果を取得する。
		List list = logic.conditionSearch(param);

		dispatchResultDtoList(req, resp, list);

	}

	/**
	 * 部門ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefDepartment(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String depCode = req.getParameter("depCode");
		// 検索名称
		String sName = req.getParameter("sName");
		// 検索略称
		String kName = req.getParameter("kName");
		// 組織
		String organization = req.getParameter("organization");
		// 上位部門
		String upperDepart = req.getParameter("upper");
		// 組織レベル - ヌル値との比較が終わったため、もとのレベルに戻す。
		int level = (Integer.parseInt(req.getParameter("level"))) - 1;
		// 集計
		boolean sumDepart = BooleanUtil.toBoolean(req.getParameter("sum"));
		// 入力
		boolean inputDepart = BooleanUtil.toBoolean(req.getParameter("input"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		depCode = DBUtil.getLikeStatement(depCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		// 結果を取得する。
		List<Object> list = logic.refSearchDepartment(kaiCode, depCode, sName, kName, stampBasisDate, organization,
			level, upperDepart, sumDepart, inputDepart, beginCode, endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 行摘要・伝票摘要ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefMemo(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;

		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		MemoLogic logic = (MemoLogic) container.getComponent(MemoLogic.class);

		TUserInfo userInfo = refTServerUserInfo(req);

		// 会社コード
		String kaiCode;
		if (!Util.isNullOrEmpty(req.getParameter("kaiCode"))) {
			kaiCode = req.getParameter("kaiCode");
		} else {
			kaiCode = userInfo.getCompanyCode();
		}

		// 取引先コード
		String tekCode = req.getParameter("tekCode");
		// 検索名称
		String sName = req.getParameter("sName");
		// 検索略称
		String kName = req.getParameter("kName");
		// データタイプ
		String slipType = req.getParameter("slipType");
		// 摘要区分
		int memoType = Integer.parseInt(req.getParameter("memoType"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		tekCode = DBUtil.getLikeStatement(tekCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.refDailog(kaiCode, tekCode, sName, kName, memoType, slipType, stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 消費税ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefTax(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		ConsumptionTaxLogic logic = (ConsumptionTaxLogic) container.getComponent(ConsumptionTaxLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String zeiCode = req.getParameter("zeiCode");
		// 検索名称
		String sName = req.getParameter("sName");
		// 検索略称
		String kName = req.getParameter("kName");

		String sales = req.getParameter("sales");

		String purchase = req.getParameter("purchase");

		String elseTax = req.getParameter("elseTax");

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		zeiCode = DBUtil.getLikeStatement(zeiCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.refDailog(kaiCode, zeiCode, sName, kName, sales, purchase, elseTax, stampBasisDate);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 社員検索ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */

	private void searchRefEmployee(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		EmployeeLogic logic = (EmployeeLogic) container.getComponent(EmployeeLogic.class);
		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 社員コード
		String empCode = req.getParameter("empCode");
		// 検索名称
		String sName = req.getParameter("sName");
		// 検索略称
		String kName = req.getParameter("kName");

		String user = req.getParameter("user");

		String depCode = req.getParameter("depCode");

		// 開始コード
		String beginCode = Util.avoidNull(req.getParameter("beginCode"));
		// 終了コード
		String endCode = Util.avoidNull(req.getParameter("endCode"));

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// Likeを利用するため
		empCode = DBUtil.getLikeStatement(empCode, DBUtil.NORMAL_CHAR);
		sName = DBUtil.getLikeStatement(sName, DBUtil.UNICODE_CHAR);
		kName = DBUtil.getLikeStatement(kName, DBUtil.UNICODE_CHAR);

		// 結果を取得する。
		List<Object> list = logic.refDailog(kaiCode, empCode, sName, kName, stampBasisDate, user, depCode, beginCode,
			endCode);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 支払い方法ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefPayment(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = null;
		/**
		 * メモリリーク対策
		 */
		container = SingletonS2ContainerFactory.getContainer();

		AP_HOH_MSTDao dao = (AP_HOH_MSTDao) container.getComponent(AP_HOH_MSTDao.class);

		ApHohMstParameter param = new ApHohMstParameter();
		// 会社コード
		param.setKaiCode(req.getParameter("kaiCode"));
		// 支払方法コード
		param.setLikeHohCode(DBUtil.getLikeStatement(req.getParameter("hohCode"), DBUtil.NORMAL_CHAR));
		// 略称
		param.setLikeHohName(DBUtil.getLikeStatement(req.getParameter("sName"), DBUtil.UNICODE_CHAR));
		// 検索名称
		param.setLikeHohNameK(DBUtil.getLikeStatement(req.getParameter("kName"), DBUtil.UNICODE_CHAR));
		// 支払対象区分
		param.setHohSihKbn(req.getParameter("sihKbn"));
		// 支払内部コード
		if (!Util.isNullOrEmpty(req.getParameter("naiCode"))) {
			param.setHohNaiCode(new String[] { req.getParameter("naiCode") });
		}
		// 支払内部コード（NOT条件）
		param.setNotHohNaiCode(req.getParameter("notNaiCode"));
		// 有効期間日付
		String termBasisDate = req.getParameter("termBasisDate");
		if (!Util.isNullOrEmpty(termBasisDate)) {
			param.setTermBasisDate(DateUtil.toYMDDate(termBasisDate));
		}

		// 支払方法コードリスト
		param.setHohCodes((List<String>) getObjectParameter(req));

		List<Object> list = dao.getApHohMst(param);

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 支払条件ダイアログ検索
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	private void searchRefPaymentCon(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String triCode = req.getParameter("triCode");

		// 検索条件コード
		String tjkCode = DBUtil.getLikeStatement(req.getParameter("tjkCode"), DBUtil.NORMAL_CHAR);

		String termBasisDate = "";
		Date BaseDate = null;
		Timestamp stampBasisDate = null;
		if (!Util.isNullOrEmpty(req.getParameter("termBasisDate"))) {
			termBasisDate = req.getParameter("termBasisDate");

			BaseDate = DateUtil.toYMDDate(termBasisDate);

			stampBasisDate = DateUtil.toTimestamp(BaseDate);
		}

		// 結果を取得する。
		List list = logic.refDailog(kaiCode, triCode, tjkCode, stampBasisDate);

		dispatchResultList(req, resp, list);
	}

	/**
	 * 社員マスタ検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchEmpMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		InputPersonLogic logic = (InputPersonLogic) container.getComponent(InputPersonLogic.class);
		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 社員コード
		String strEmpCode = req.getParameter("EMP_CODE");
		// 社員略称
		String strEmpName = req.getParameter("EMP_NAME");
		// 社員検索名称
		String strEmpNameK = req.getParameter("EMP_NAME_K");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 所属部門コード区分
		String strDepCodeKbn = Util.avoidNull(req.getParameter("DEP_CODE_KBN"));
		// 所属部門コード
		String strDepCode = Util.avoidNull(req.getParameter("DEP_CODE"));
		// Likeを利用するため
		strEmpCode = DBUtil.getLikeStatement(strEmpCode, DBUtil.NORMAL_CHAR);
		strEmpName = DBUtil.getLikeStatement(strEmpName, DBUtil.UNICODE_CHAR);
		strEmpNameK = DBUtil.getLikeStatement(strEmpNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// 結果を取得する。
		list = logic.searchEmpMstData(strKaiCode, strEmpCode, strEmpName, strEmpNameK, strSlipDate, strDepCodeKbn,
			strDepCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * 管理マスタ検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchKnrMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		ManagementLogic logic = (ManagementLogic) container.getComponent(ManagementLogic.class);
		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 社員コード
		String strKnrCode = req.getParameter("KNR_CODE");
		// 社員略称
		String strKnrName = req.getParameter("KNR_NAME");
		// 社員検索名称
		String strKnrNameK = req.getParameter("KNR_NAME_K");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 管理表フラグ
		String strKnrFlg = req.getParameter("KNR_FLAG");

		// 開始コード
		String startCode = req.getParameter("START_CODE");
		// 終了コード
		String endCode = req.getParameter("END_CODE");

		// Likeを利用するため
		strKnrCode = DBUtil.getLikeStatement(strKnrCode, DBUtil.NORMAL_CHAR);
		strKnrName = DBUtil.getLikeStatement(strKnrName, DBUtil.UNICODE_CHAR);
		strKnrNameK = DBUtil.getLikeStatement(strKnrNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// 結果を取得する。
		if ("1".equals(strKnrFlg)) {
			// 管理1マスタ
			list = logic.searchKnr1MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("2".equals(strKnrFlg)) {
			// 管理2マスタ
			list = logic.searchKnr2MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("3".equals(strKnrFlg)) {
			// 管理3マスタ
			list = logic.searchKnr3MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("4".equals(strKnrFlg)) {
			// 管理4マスタ
			list = logic.searchKnr4MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("5".equals(strKnrFlg)) {
			// 管理5マスタ
			list = logic.searchKnr5MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		} else if ("6".equals(strKnrFlg)) {
			// 管理6マスタ
			list = logic.searchKnr6MstData(strKaiCode, strKnrCode, strKnrName, strKnrNameK, strSlipDate, startCode,
				endCode);
		}

		dispatchResultListObject(req, resp, list);
	}

	/**
	 * 科目体系名称マスタ検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchKmkTkMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		KmkTkMstLogic logic = (KmkTkMstLogic) container.getComponent(KmkTkMstLogic.class);
		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 科目体系名称コード
		String strKmtCode = req.getParameter("KMT_CODE");
		// 科目体系名称略称
		String strKmtName = req.getParameter("KMT_NAME");
		// 科目体系名称検索名称
		String strKmtNameK = req.getParameter("KMT_NAME_K");
		// TODO コントロールパラメーター確認
		// 開始コード
		String startCode = req.getParameter("START_CODE");
		// 終了コード
		String endCode = req.getParameter("END_CODE");

		// Likeを利用するため
		strKmtCode = DBUtil.getLikeStatement(strKmtCode, DBUtil.NORMAL_CHAR);
		strKmtName = DBUtil.getLikeStatement(strKmtName, DBUtil.UNICODE_CHAR);
		strKmtNameK = DBUtil.getLikeStatement(strKmtNameK, DBUtil.UNICODE_CHAR);

		List list = null;
		// 結果を取得する。
		list = logic.searchKmtMstData(strKaiCode, strKmtCode, strKmtName, strKmtNameK, startCode, endCode);
		dispatchResultListObject(req, resp, list);

	}

	/**
	 * 銀行マスタ検索
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchBMKMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

		BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

		// Likeを利用するため
		param.setLikeBnkCode(DBUtil.getLikeStatement(param.getLikeBnkCode(), DBUtil.NORMAL_CHAR));
		param.setLikeBnkName(DBUtil.getLikeStatement(param.getLikeBnkName(), DBUtil.UNICODE_CHAR));
		param.setLikeBnkNameK(DBUtil.getLikeStatement(param.getLikeBnkNameK(), DBUtil.UNICODE_CHAR));

		dispatchResultObject(req, resp, logic.getBankList(param));
	}

	/**
	 * 銀行マスタ検索 （支店情報）
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void searchBMKSTNMstData(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

		BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

		// Likeを利用するため
		param.setLikeBnkStnCode(DBUtil.getLikeStatement(param.getLikeBnkStnCode(), DBUtil.NORMAL_CHAR));
		param.setLikeBnkStnName(DBUtil.getLikeStatement(param.getLikeBnkStnName(), DBUtil.UNICODE_CHAR));
		param.setLikeBnkStnNameK(DBUtil.getLikeStatement(param.getLikeBnkStnNameK(), DBUtil.UNICODE_CHAR));

		dispatchResultObject(req, resp, logic.getStnList(param));
	}

}
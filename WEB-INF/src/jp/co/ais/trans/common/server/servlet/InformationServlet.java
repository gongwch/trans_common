package jp.co.ais.trans.common.server.servlet;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.di.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 共通サーブレット
 */
public class InformationServlet extends TServletBase {

	/** UID */
	protected static final long serialVersionUID = -5319125982776071994L;

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

			if ("OrganizationCode".equals(flag)) {
				// 組織コードセット
				dispatchOrgCode(req, resp);
			} else if ("CmpOrganizationCode".equals(flag)) {
				// 会社単位の組織コードセット
				dispatchCmpOrgCode(req, resp);
			} else if ("OrganizationInfo".equals(flag)) {
				// 開示レベル情報
				dispatchOrgData(req, resp);
			} else if ("ItemInfo".equals(flag)) {
				// 科目情報
				dispatchItemData(req, resp);
			} else if ("isCode".equals(flag)) {
				// 略称を取得する
				dispatchgIsCode(req, resp);
			} else if ("CmpInfo".equals(flag)) {
				// 会社コントロールマスタ一覧
				dispatchCmpMstData(req, resp);
			} else if ("Knrmst".equals(flag)) {
				// 管理コードを取得
				dispatchKnrMstData(req, resp);
			} else if ("ACCOUNT_STAGE".equals(flag)) {
				// 決算段階を取得する
				findAccountStage(req, resp);
			} else if ("KSD_KBN".equals(flag)) {
				// 決算時期取得する
				getKsdKbn(req, resp);
			} else if ("FindCurData".equals(flag)) {
				// 通貨データを検索する
				findCurMst(req, resp);
			} else if ("convertForeign".equals(flag)) {
				// 外貨を基軸通貨に換算する
				convertForeign(req, resp);
			} else if ("FindRate".equals(flag)) {
				// レート検索
				findRate(req, resp);
			} else if ("GetItemSystem".equals(flag)) {
				// 基本科目体系名称を取得する
				getItemSystemValue(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * 組織コードセット
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void dispatchOrgCode(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		logic.setCompanyCode(refTServerUserInfo(req).getCompanyCode());

		String[] orgCodes = logic.getOrganizationCodeList();

		// 返信
		dispatchResult(req, resp, "orgCodes", StringUtil.toDelimitString(orgCodes));
	}

	/**
	 * 会社単位の組織コードセット
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void dispatchCmpOrgCode(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		logic.setCompanyCode(refTServerUserInfo(req).getCompanyCode());

		String[] orgCodes = logic.getCmpOrganizationCodeList();

		// 返信
		dispatchResult(req, resp, "orgCodes", StringUtil.toDelimitString(orgCodes));
	}

	/**
	 * 開示レベル情報セット
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchOrgData(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String strKaiCode = req.getParameter("KAI_CODE"); // 会社コード
			String strUsrId = req.getParameter("USR_ID"); // ログインユーザコード
			String strOrganizationCode = req.getParameter("ORGANIZATION_CODE"); // 組織コード
			String strKmtCode = req.getParameter("KMT_CODE"); // 科目体系コード

			S2Container container = SingletonS2ContainerFactory.getContainer();
			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

			logic.setCompanyCode(strKaiCode);
			logic.setUserCode(strUsrId);

			// データーを取得する
			Map resultMap = logic.getIndicationLevelData(strKmtCode, strOrganizationCode);

			dispatchResultMap(req, resp, resultMap);

		} catch (TException e) {
			// データが見つからなかった場合、何もdispatchしない。
			dispatchResultMap(req, resp, new TreeMap());
		}
	}

	/**
	 * 科目情報セット
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchItemData(HttpServletRequest req, HttpServletResponse resp) {
		try {

			String itemKind = Util.avoidNull(req.getParameter("ITEM_KIND")); // 科目種類
			String strKaiCode = Util.avoidNull(req.getParameter("KAI_CODE")); // 会社コード
			String kmkCode = Util.avoidNull(req.getParameter("KMK_CODE")); // 科目コード
			String hkmCode = Util.avoidNull(req.getParameter("HKM_CODE")); // 補助科目コード
			String ukmCode = Util.avoidNull(req.getParameter("UKM_CODE")); // 内訳科目コード

			S2Container container = SingletonS2ContainerFactory.getContainer();
			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

			logic.setCompanyCode(strKaiCode);

			Map resultMap;

			// パラメータ有無で対象科目を決める
			if ("Item".equals(itemKind)) {
				// 科目
				resultMap = logic.getItemData(kmkCode);

			} else if ("SubItem".equals(itemKind)) {
				// 補助科目
				resultMap = logic.getSubItemData(kmkCode, hkmCode);

			} else if ("BreakDownItem".equals(itemKind)) {
				// 内訳科目
				resultMap = logic.getBreakDownItemData(kmkCode, hkmCode, ukmCode);
			} else {
				dispatchError(req, resp, "S01001");
				return;
			}

			dispatchResultMap(req, resp, resultMap);

		} catch (TException e) {
			// データが見つからなかった場合、何もdispatchしない。
			dispatchResultMap(req, resp, new TreeMap());
		}
	}

	/**
	 * 略称を取得する
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchgIsCode(HttpServletRequest req, HttpServletResponse resp) {
		// ログインした会社コード
		String strKaiCode = String.valueOf(req.getParameter("kaiCode"));
		// 消費税ｺｰﾄﾞ/部門ｺｰﾄﾞ/配下部門ｺｰﾄﾞ
		String strFieldCode = String.valueOf(req.getParameter("fieldCode"));
		// 消費税ｺｰﾄﾞ/部門ｺｰﾄﾞ/配下部門ｺｰﾄﾞの区分
		String strType = String.valueOf(req.getParameter("type"));
		// 画面の組織ｺｰﾄﾞ
		String strOrganizationCode = String.valueOf(req.getParameter("organizationCode"));
		// 画面の階層ﾚﾍﾞﾙ
		Integer intHierarchicalLevel = Integer.valueOf(String.valueOf(req.getParameter("panelLevel")));
		// 開示レベル
		Integer intkjlLvl = Integer.valueOf(String.valueOf(req.getParameter("Level")));
		// 開示部門コード
		String strkjlDepCode = String.valueOf(req.getParameter("depCode"));
		// 上位部門コード
		String strUpCode = String.valueOf(req.getParameter("upDepCode"));

		// 返却略称
		String strNameReturn = "";

		// S2Container
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// 上位部門の場合
		if ("UpDep".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intHierarchicalLevelUp = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevelUp, intkjlLvl, strkjlDepCode, strType);
			// 部門の場合
		} else if ("Dep".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intpanelkjlLvl = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevel, intpanelkjlLvl, strUpCode, strType);
			// 上位会社の場合
		} else if ("UpCompany".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intHierarchicalLevelUp = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevelUp, intkjlLvl, strkjlDepCode, strType);
			// OwnerCompanyの場合
		} else if ("Company".equals(strType)) {

			InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
			Integer intpanelkjlLvl = Integer.valueOf(intHierarchicalLevel) - 1;
			strNameReturn = logic.organizationSearchNameS(strKaiCode, strOrganizationCode, strFieldCode,
				intHierarchicalLevel, intpanelkjlLvl, strUpCode, strType);
		}

		dispatchResult(req, resp, "key", strNameReturn);
	}

	/**
	 * 会社コントロールマスタ一覧取得
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchCmpMstData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = null;

		container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);
		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");

		Map map = new HashMap();
		// 結果を取得する。
		CMP_MST cmpMst = logic.getCmpMstDeta(strKaiCode);
		if (cmpMst != null) {
			map.put("G_SHONIN_FLG", String.valueOf(Util.avoidNull(cmpMst.getCMP_G_SHONIN_FLG())));
			map.put("K_SHONIN_FLG", String.valueOf(Util.avoidNull(cmpMst.getCMP_K_SHONIN_FLG())));
			map.put("CMP_HM_KBN_1", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_1())));
			map.put("CMP_HM_KBN_2", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_2())));
			map.put("CMP_HM_KBN_3", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_KBN_3())));
			map.put("CMP_HM_NAME_1", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_1())));
			map.put("CMP_HM_NAME_2", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_2())));
			map.put("CMP_HM_NAME_3", String.valueOf(Util.avoidNull(cmpMst.getCMP_HM_NAME_3())));
			map.put("FLAG", "1");
		} else {
			map.put("G_SHONIN_FLG", "");
			map.put("K_SHONIN_FLG", "");
			map.put("CMP_HM_KBN_1", "");
			map.put("CMP_HM_KBN_2", "");
			map.put("CMP_HM_KBN_3", "");
			map.put("CMP_HM_NAME_1", "");
			map.put("CMP_HM_NAME_2", "");
			map.put("CMP_HM_NAME_3", "");
			map.put("FLAG", "0");
		}
		dispatchResultMap(req, resp, map);

	}

	/**
	 * 管理マスタ情報取得
	 * 
	 * @param req
	 * @param resp
	 */
	protected void dispatchKnrMstData(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// 会社コード
		String strKaiCode = userInfo.getCompanyCode();
		// 管理コード
		String strManagementCode = req.getParameter("KNR_CODE");
		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 管理フラグ
		String strManagementFlag = req.getParameter("KNR_FLG");

		// 開始コード
		String beginCode = req.getParameter("BEGIN_CODE");
		// 終了コード
		String endCode = req.getParameter("END_CODE");

		S2Container container = SingletonS2ContainerFactory.getContainer();

		ManagementLogic logic = (ManagementLogic) container.getComponent(ManagementLogic.class);

		// パラメーターを設定する
		Map<String, String> mapCondition = new HashMap<String, String>();
		mapCondition.put("kaiCode", strKaiCode); // 会社コード
		mapCondition.put("slipDate", strSlipDate); // 伝票日付
		mapCondition.put("managementFlag", strManagementFlag); // 管理表フラグ
		mapCondition.put("managementCode", strManagementCode); // 管理コード
		mapCondition.put("beginCode", beginCode); // 開始コード
		mapCondition.put("endCode", endCode); // 終了コード

		Map<String, Object> map = new TreeMap<String, Object>();

		// 管理マスタ情報取得
		Map KnrInfoMap = logic.getKnrName(mapCondition);

		if (Util.isNullOrEmpty(KnrInfoMap.get("strKnrName"))) {
			map.put("existFlag", "0");
			map.put("KNR_NAME_S_1", "");
			map.put("KNR_NAME_S_2", "");
			map.put("KNR_NAME_S_3", "");
			map.put("KNR_NAME_S_4", "");
			map.put("KNR_NAME_S_5", "");
			map.put("KNR_NAME_S_6", "");
		} else {
			map.put("existFlag", "1");
			// 管理1を取得する場合
			if ("1".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_1", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// 管理2を取得する場合
			else if ("2".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_2", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// 管理3を取得する場合
			else if ("3".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_3", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// 管理4を取得する場合
			else if ("4".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_4", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// 管理5を取得する場合
			else if ("5".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_5", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
			// 管理6を取得する場合
			else if ("6".equals(strManagementFlag)) {
				map.put("KNR_NAME_S_6", KnrInfoMap.get("strKnrName"));
				map.put("STR_DATE", KnrInfoMap.get("strStrDate"));
				map.put("END_DATE", KnrInfoMap.get("strEndDate"));
			}
		}

		this.dispatchResultMap(req, resp, map);
	}

	/**
	 * 決算段階を取得する
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void findAccountStage(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) container.getComponent(InformationLogic.class);

		// 伝票日付
		String strSlipDate = req.getParameter("SLIP_DATE");
		// 会社コード
		String strKaiCode = req.getParameter("KAI_CODE");
		// 伝票日付の年度と月度を取得する
		int year = BizUtil.getFiscalYear(strSlipDate, strKaiCode);
		int month = BizUtil.getFiscalMonth(strSlipDate, strKaiCode);
		// SIM_MON = 0の場合はSIM_MON = 1として扱ってください。
		if (month == 0) {
			month = 1;
		}

		// 締めコントロールマスタ(SIM_CTL)から決算区分を取得する
		SIM_CTL simCtl = logic.findSimCtl(strKaiCode, year, month);

		Map<String, Object> map = new HashMap<String, Object>();
		if (simCtl != null) {
			map.put("KSN_KBN", String.valueOf(simCtl.getKSN_KBN() + 1));
		} else {
			// 存在しない場合は1を表示する。
			map.put("KSN_KBN", "1");
		}

		// GLコントロールより決算段階を取得（現段階の決算区分と比較するため）
		GL_CTL_MST glCtl = logic.findGlCtlMstInfo(strKaiCode);
		if (glCtl != null) {
			map.put("KSD_KBN", String.valueOf(glCtl.getKSD_KBN()));
		} else {
			// 存在しない場合は1を表示する。
			map.put("KSD_KBN", "1");
		}

		dispatchResultMap(req, resp, map);
	}

	/**
	 * 決算時期を取得する
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void getKsdKbn(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// 会社コード
		String strKaiCode = userInfo.getCompanyCode();

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

		Map<String, Object> map = new HashMap<String, Object>();

		// GLコントロールより決算段階を取得（現段階の決算区分と比較するため）
		GL_CTL_MST glCtl = logic.findGlCtlMstInfo(strKaiCode);
		if (glCtl != null) {
			map.put("KSD_KBN", String.valueOf(glCtl.getKSD_KBN()));
		} else {
			// 存在しない場合は1を表示する。
			map.put("KSD_KBN", "1");
		}

		// 戻る
		super.dispatchResultMap(req, resp, map);
	}

	/**
	 * 通貨マスタデータを検索
	 * 
	 * @param req
	 * @param resp
	 */
	protected void findCurMst(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container s2Container = SingletonS2ContainerFactory.getContainer();

			InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

			TUserInfo userInfo = refTServerUserInfo(req);
			// 会社コード
			String strKaiCode = userInfo.getCompanyCode();
			// 通貨ｺｰﾄﾞ
			String strCurCode = req.getParameter("CUR_CODE");
			// 伝票日付
			String strSlipDate = req.getParameter("DATE");

			Date slipDate = null;

			// 伝票日付
			if (!Util.isNullOrEmpty(strSlipDate)) {
				// 伝票日付
				slipDate = DateUtil.toYMDDate(strSlipDate);
			}

			Map<String, Object> map = new HashMap<String, Object>();

			// 通貨マスタデータを取得する
			CUR_MST curMst = logic.findCurMstInfo(strKaiCode, strCurCode, slipDate);
			if (curMst != null) {

				// 小数点以下桁数
				int intDecKeta = curMst.getDEC_KETA();
				map.put("DEC_KETA", String.valueOf(intDecKeta));

				// レート係数
				int rateRow = curMst.getRATE_POW();
				map.put("RATE_POW", String.valueOf(rateRow));

				// 換算区分
				int convKbn = curMst.getCONV_KBN();
				map.put("CONV_KBN", String.valueOf(convKbn));

			}

			dispatchResultMap(req, resp, map);
		} catch (ParseException e) {
			// ignore
		}
	}

	/**
	 * 外貨を基軸通貨に換算
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void convertForeign(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		InformationLogic logic = (InformationLogic) s2Container.getComponent(InformationLogic.class);

		// 検索のパラメータを設定する
		TUserInfo userInfo = refTServerUserInfo(req);
		// 会社コード
		String kaiCode = userInfo.getCompanyCode();
		String slipDate = req.getParameter("rateBaseDate"); // レート基準日付
		String baseCurCode = req.getParameter("baseCurCode");
		String foreginCurCode = req.getParameter("foreginCurCode");
		String money = req.getParameter("money");
		String rate = req.getParameter("rate");

		// 外貨金額
		BigDecimal foreignMoney = new BigDecimal(money);

		// 換算レート
		double numRate = Double.parseDouble(rate);

		CMP_MST cmpMst = logic.getCmpMstDeta(kaiCode);
		CUR_MST baseCurMst = logic.findCurMstInfo(kaiCode, baseCurCode, null);
		CUR_MST foreginCurMst = logic.findCurMstInfo(kaiCode, foreginCurCode, null);

		// 外貨を基軸通貨に換算金額
		BigDecimal baseMoney = BizUtil.convertToBaseCurrency(foreignMoney, numRate, baseCurMst, foreginCurMst, cmpMst);

		// 邦貨金額格納
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("baseMoney", String.valueOf(baseMoney));

		dispatchResultMap(req, resp, map);
	}

	/**
	 * 指定通貨の指定日付におけるレートを返す
	 * 
	 * @param req
	 * @param resp
	 * @throws ParseException
	 */
	protected void findRate(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		TUserInfo userInfo = refTServerUserInfo(req);
		// 会社コード
		String strKaiCode = userInfo.getCompanyCode();
		// 通貨ｺｰﾄﾞ
		String strCurCode = req.getParameter("CUR_CODE");
		// 指定日付
		String strDate = req.getParameter("OCCUR_DATE");

		// 指定通貨の指定日付におけるレートを返す
		double lngRate = BizUtil.getCurrencyRate(strCurCode, DateUtil.toYMDDate(strDate), strKaiCode);

		// レート格納
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("CUR_RATE", String.valueOf(lngRate));

		dispatchResultMap(req, resp, map);
	}

	/**
	 * 基本科目体系を取得する
	 * 
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 */
	protected void getItemSystemValue(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		// 会社コード
		String strKaiCode = userInfo.getCompanyCode();

		// パラメータのMap
		Map conditionMap = new HashMap();

		// 会社コードを取得する
		conditionMap.put("KAI_CODE", strKaiCode);
		// 基本科目体系コードを取得する
		conditionMap.put("ITEM_SYSTEM_CODE", req.getParameter("ITEM_SYSTEM_CODE"));

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		// KmkTkMstLogicを作成する
		KmkTkMstLogic logic = (KmkTkMstLogic) s2Container.getComponent(KmkTkMstLogic.class);

		// 戻る
		super.dispatchResultMap(req, resp, logic.getItemSystemValue(conditionMap));
	}

}

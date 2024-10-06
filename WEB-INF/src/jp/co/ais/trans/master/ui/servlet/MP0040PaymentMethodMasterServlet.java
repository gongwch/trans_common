package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 支払方法マスタ画面Servlet (MP0040)
 * 
 * @author ISFnet China
 */
public class MP0040PaymentMethodMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -203499160025958870L;

	@Override
	protected String getLogicClassName() {
		return "PaymentMethodLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 支払方法コードの設定
		map.put("hohHohCode", req.getParameter("hohHohCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 開始コードの設定
		map.put("beginHohHohCode", req.getParameter("beginHohHohCode"));
		// 終了コードの設定
		map.put("endHohHohCode", req.getParameter("endHohHohCode"));
		// 支払対象区分:社員支払
		map.put("includeEmployeePayment", req.getParameter("includeEmployeePayment"));
		// 支払対象区分:社外支払
		map.put("includeExternalPayment", req.getParameter("includeExternalPayment"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		AP_HOH_MST apHohMST = new AP_HOH_MST();
		// 会社コードの設定
		apHohMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 支払方法コードの設定
		apHohMST.setHOH_HOH_CODE(req.getParameter("hohHohCode"));
		// 支払方法名の設定
		apHohMST.setHOH_HOH_NAME(req.getParameter("hohHohName"));
		// 支払方法検索名称の設定
		apHohMST.setHOH_HOH_NAME_K(req.getParameter("hohHohName_K"));
		// 科目コードの設定
		apHohMST.setHOH_KMK_CODE(req.getParameter("hohKmkCode"));
		// 補助科目コードの設定
		apHohMST.setHOH_HKM_CODE(req.getParameter("hohHkmCode"));
		// 内訳科目コードの設定
		apHohMST.setHOH_UKM_CODE(req.getParameter("hohUkmCode"));
		// 計上部門コードの設定
		apHohMST.setHOH_DEP_CODE(req.getParameter("hohDepCode"));
		// 支払内部コードの設定
		apHohMST.setHOH_NAI_CODE(req.getParameter("hohNaiCode"));
		// 支払対象区分の取得
		int hohSihKbn = Integer.parseInt(req.getParameter("hohSihKbn"));
		// 支払対象区分の設定
		apHohMST.setHOH_SIH_KBN(hohSihKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		apHohMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		apHohMST.setEND_DATE(endDate);
		// プログラムIDの設定
		apHohMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return apHohMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new PaymentMethodMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refPayment".equals(flag)) {
			searchRefPayment(req, resp);
		}
	}

	private void searchRefPayment(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// daoの初期化
		AP_HOH_MSTDao dao = (AP_HOH_MSTDao) container.getComponent(AP_HOH_MSTDao.class);

		ApHohMstParameter param = (ApHohMstParameter) getObjectParameter(req);

		List<AP_HOH_MST> lst = dao.getApHohMst(param);

		if (lst.isEmpty()) {
			super.dispatchResultDto(req, resp, null);
		} else {
			super.dispatchResultDto(req, resp, lst.get(0));
		}

	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.util.*;
import java.text.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 銀行口座マスタ画面Servlet (MP0030)
 * 
 * @author ISFnet China
 */
public class MP0030BankAccountMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -4353848069041891953L;

	@Override
	protected String getLogicClassName() {
		return "BankAccountLogic";
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
		// 銀行口座コードの設定
		map.put("cbkCbkCode", req.getParameter("cbkCbkCode"));
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
		map.put("beginCbkCbkCode", req.getParameter("beginCbkCbkCode"));
		// 終了コードの設定
		map.put("endCbkCbkCode", req.getParameter("endCbkCbkCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		AP_CBK_MST apCbkMST = new AP_CBK_MST();
		// 会社コードの設定
		apCbkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 銀行口座コードの設定
		apCbkMST.setCBK_CBK_CODE(req.getParameter("cbkCbkCode"));
		// 銀行口座名称の設定
		apCbkMST.setCBK_NAME(req.getParameter("cbkName"));
		// 通貨コードの設定
		apCbkMST.setCUR_CODE(req.getParameter("curCode"));
		// 銀行コードの設定
		apCbkMST.setCBK_BNK_CODE(req.getParameter("cbkBnkCode"));
		// 支店コードの設定
		apCbkMST.setCBK_STN_CODE(req.getParameter("cbkStnCode"));
		// 振込依頼人コードの設定
		apCbkMST.setCBK_IRAI_EMP_CODE(req.getParameter("cbkIraiEmpCode"));
		// 振込依頼人名の設定
		apCbkMST.setCBK_IRAI_NAME(req.getParameter("cbkIraiName"));
		// 振込依頼人名（漢字）の設定
		apCbkMST.setCBK_IRAI_NAME_J(req.getParameter("cbkIraiName_J"));
		// 振込依頼人名（英字）の設定
		apCbkMST.setCBK_IRAI_NAME_E(req.getParameter("cbkIraiName_E"));
		// 口座番号の設定
		apCbkMST.setCBK_YKN_NO(req.getParameter("cbkYknNo"));
		// 計上部門コードの設定
		apCbkMST.setCBK_DEP_CODE(req.getParameter("cbkDepCode"));
		// 科目コードの設定
		apCbkMST.setCBK_KMK_CODE(req.getParameter("cbkKmkCode"));
		// 補助科目コードの設定
		apCbkMST.setCBK_HKM_CODE(req.getParameter("cbkHkmCode"));
		// 内訳科目コードの設定
		apCbkMST.setCBK_UKM_CODE(req.getParameter("cbkUkmCode"));
		// 社員ＦＢ区分の取得
		int cbkEmpFbKbn = Integer.parseInt(req.getParameter("cbkEmpFbKbn"));
		// 社員ＦＢ区分の設定
		apCbkMST.setCBK_EMP_FB_KBN(cbkEmpFbKbn);
		// 社外ＦＢ区分の取得
		int cbkOutFbKbn = Integer.parseInt(req.getParameter("cbkOutFbKbn"));
		// 社外ＦＢ区分の設定
		apCbkMST.setCBK_OUT_FB_KBN(cbkOutFbKbn);
		// 預金種目の取得
		int cbkYknKbn = Integer.parseInt(req.getParameter("cbkYknKbn"));
		// 預金種目の設定
		apCbkMST.setCBK_YKN_KBN(cbkYknKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		apCbkMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		apCbkMST.setEND_DATE(endDate);
		// プログラムIDの設定
		apCbkMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return apCbkMST;
	}

	/** 結果の取得 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		if ("defaultReceivedAccount".equals(req.getParameter("flag"))) {
			searchReceivedAccount(req, resp);
		} else if ("getCbkData".equals(req.getParameter("flag"))) {
			getCbkData(req, resp);
		} else {
			// getREFItems
			// containerの初期化
			S2Container container = null;
			try {

				container = SingletonS2ContainerFactory.getContainer();
				// ファイルの取得
				BankAccountLogic logic = (BankAccountLogic) container.getComponent(getLogicClassName());
				// keysの初期化
				Map keys = this.getPrimaryKeys(req);
				// 結果を取得
				List list = logic.getREFItems(keys);
				// 結果の設定
				super.dispatchResultList(req, resp, list);
			} catch (Throwable ex) {
				super.dispatchError(req, resp, ex.getMessage());
			}
		}
	}

	/**
	 * 銀行口座マスタのデータを取得する
	 * 
	 * @param req
	 * @param resp
	 */
	private void getCbkData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// daoの初期化
		AP_CBK_MSTDao dao = (AP_CBK_MSTDao) container.getComponent(AP_CBK_MSTDao.class);

		ApCbkMstParameter param = (ApCbkMstParameter) getObjectParameter(req);

		List<AP_CBK_MST> lst = dao.getApCbkMst(param);

		if (lst.isEmpty()) {
			super.dispatchResultDto(req, resp, null);
		} else {
			super.dispatchResultDto(req, resp, lst.get(0));
		}
	}

	/**
	 * デフォルトの支払条件取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchReceivedAccount(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			BankAccountLogic logic = (BankAccountLogic) container.getComponent(BankAccountLogic.class);

			// 会社コード
			String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

			dispatchResultMap(req, resp, logic.getDefaultReceivedAccount(kaiCode));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new BankAccountMasterReportExcelDefine();
	}
}

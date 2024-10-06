package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 取引先マスタ画面Servlet
 * 
 * @author ISFnet China
 */
public class MG0150CustomerMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 6009200918852626753L;

	@Override
	protected String getLogicClassName() {
		return "CustomerLogic";
	}

	@Override
	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** 主キーの取得 */
	@Override
	protected Map getPrimaryKeys(HttpServletRequest req) {

		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 取引先コードの設定
		map.put("triCode", req.getParameter("triCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	@Override
	protected Map getFindConditions(HttpServletRequest req) {

		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 開始コードの設定
		map.put("beginTriCode", req.getParameter("beginTriCode"));
		// 終了コードの設定
		map.put("endTriCode", req.getParameter("endTriCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	@Override
	protected Object getEntity(HttpServletRequest req) throws ParseException {

		// 実体の初期化
		TRI_MST triMST = new TRI_MST();
		// 会社コードの設定
		triMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 取引先コードの設定
		triMST.setTRI_CODE(req.getParameter("triCode"));
		// 取引先名称の設定
		triMST.setTRI_NAME(req.getParameter("triName"));
		// 取引先略称の設定
		triMST.setTRI_NAME_S(req.getParameter("triName_S"));
		// 取引先検索名称の設定
		triMST.setTRI_NAME_K(req.getParameter("triName_K"));
		// 郵便番号の設定
		triMST.setZIP(req.getParameter("zip"));
		// 住所カナの設定
		triMST.setJYU_KANA(req.getParameter("jyuKana"));
		// 住所１の設定
		triMST.setJYU_1(req.getParameter("jyu1"));
		// 住所２の設定
		triMST.setJYU_2(req.getParameter("jyu2"));
		// 電話番号の設定
		triMST.setTEL(req.getParameter("tel"));
		// FAX番号の設定
		triMST.setFAX(req.getParameter("fax"));
		// 集計相手先コードの設定
		triMST.setSUM_CODE(req.getParameter("sumCode"));
		// 入金条件締め日の設定
		triMST.setNJ_C_DATE(req.getParameter("njCDate"));
		// 入金条件締め後月の設定
		triMST.setNJ_R_MON(req.getParameter("njRMon"));
		// 入金条件入金日の設定
		triMST.setNJ_P_DATE(req.getParameter("njPDate"));
		// 入金銀行口座ｺｰﾄﾞの設定
		triMST.setNKN_CBK_CODE(req.getParameter("nknCbkCode"));
		// 取引形態区分の設定
		triMST.setTRI_KBN(req.getParameter("triKbn"));
		// スポット伝票番号の設定
		triMST.setSPOT_DEN_NO(req.getParameter("spotDenNo"));
		// 事業所名称の設定
		triMST.setJIG_NAME(req.getParameter("jigName"));
		// 振込依頼人名の設定
		triMST.setIRAI_NAME(req.getParameter("iraiName"));
		// 仕入先区分の取得
		int siireKbn = Integer.parseInt(req.getParameter("siireKbn"));
		// 仕入先区分の設定
		triMST.setSIIRE_KBN(siireKbn);
		// 得意先区分の取得
		int tokuiKbn = Integer.parseInt(req.getParameter("tokuiKbn"));
		// 得意先区分の設定
		triMST.setTOKUI_KBN(tokuiKbn);
		// 入金手数料区分の取得
		int nyuTesuKbn = Integer.parseInt(req.getParameter("nyuTesuKbn"));
		// 入金手数料区分の設定
		triMST.setNYU_TESU_KBN(nyuTesuKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		triMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		triMST.setEND_DATE(endDate);
		// プログラムIDの設定
		triMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return triMST;
	}

	/** リスト出力のExcel定義 */
	@Override
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new CustomerMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// 判定用flag
			String flag = req.getParameter("flag");

			// 検索
			if ("checkTriKbn".equals(flag)) {

				checkTriKbn(req, resp);

			} else if ("CustomerInfo".equals(flag)) {
				// 取引先データ取得
				dispatchCustomerData(req, resp);
			}
		} catch (ParseException e) {
			ClientErrorHandler.handledException(e);
		}
	}

	private void checkTriKbn(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		// logicの初期化
		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);
		// keysの初期化
		Map keys = new HashMap();
		keys.put("kaiCode", req.getParameter("kaiCode"));
		keys.put("triCode", req.getParameter("triCode"));

		// 実体の初期化
		TRI_MST entity = (TRI_MST) logic.findOne(keys);
		if (entity != null) {
			// 支払内部コードの取得
			Map data = new HashMap();
			data.put("tRI_KBN", entity.getTRI_KBN());
			// 結果の設定
			super.dispatchResultMap(req, resp, data);
		}
	}

	/**
	 * 取引先情報セット
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void dispatchCustomerData(HttpServletRequest req, HttpServletResponse resp) {

		String companyCode = req.getParameter("companyCode");
		String customerCode = req.getParameter("customerCode");
		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);
		logic.setCompanyCode(companyCode);

		TRI_MST entity = logic.findCustomer(customerCode, beginCode, endCode);

		super.dispatchResultObject(req, resp, entity);
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 取引先条件マスタ画面Servlet (MG0155)
 * 
 * @author ISFnet China
 */
public class MG0155CustomerConditionMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -8394624360629998607L;

	@Override
	protected String getLogicClassName() {
		return "CustomerConditionLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 取引先コードの設定
		map.put("triCode", req.getParameter("triCode"));
		// 取引先条件コードの設定
		map.put("tjkCode", req.getParameter("tjkCode"));
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
		map.put("beginTriSjCode", req.getParameter("beginTriSjCode"));
		// 終了コードの設定
		map.put("endTriSjCode", req.getParameter("endTriSjCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {

		// 実体の初期化
		TRI_SJ_MST triSjMST = new TRI_SJ_MST();
		// 会社コードの設定
		triSjMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 取引先コード
		triSjMST.setTRI_CODE(req.getParameter("triCode"));
		// 取引先条件コード
		triSjMST.setTJK_CODE(req.getParameter("tjkCode"));
		// 支払条件締め日
		triSjMST.setSJC_DATE(req.getParameter("sjcDate"));
		// 支払条件締め後月
		triSjMST.setSJR_MON(req.getParameter("sjrMon"));
		// 支払条件支払日
		triSjMST.setSJP_DATE(req.getParameter("sjpDate"));
		// 支払区分
		triSjMST.setSIHA_KBN(req.getParameter("sihaKbn"));
		// 支払方法
		triSjMST.setSIHA_HOU_CODE(req.getParameter("sihaHouCode"));
		// 振込振出銀行口座ｺｰﾄﾞ
		triSjMST.setFURI_CBK_CODE(req.getParameter("furiCbkCode"));
		// 銀行コード
		triSjMST.setBNK_CODE(req.getParameter("bnkCode"));
		// 支店コード
		triSjMST.setBNK_STN_CODE(req.getParameter("bnkStnCode"));
		// 預金種目
		triSjMST.setYKN_KBN(req.getParameter("yknKbn"));
		// 口座番号
		triSjMST.setYKN_NO(req.getParameter("yknNo"));
		// 口座名義カナ
		triSjMST.setYKN_KANA(req.getParameter("yknKana"));
		// 口座名義
		triSjMST.setYKN_NAME(req.getParameter("yknName"));
		// 送金目的名
		triSjMST.setGS_MKT_CODE(req.getParameter("gsMktCode"));
		// 被仕向支店名称
		triSjMST.setGS_STN_NAME(req.getParameter("gsStnName"));
		// 被仕向銀行名称
		triSjMST.setGS_BNK_NAME(req.getParameter("gsBnkName"));
		// 支払銀行名称
		triSjMST.setSIHA_BNK_NAME(req.getParameter("sihaBnkName"));
		// 支払支店名称
		triSjMST.setSIHA_STN_NAME(req.getParameter("sihaStnName"));
		// 支払銀行住所
		triSjMST.setSIHA_BNK_ADR(req.getParameter("sihaBnkAdr"));
		// 経由銀行名称
		triSjMST.setKEIYU_BNK_NAME(req.getParameter("keiyuBnkName"));
		// 経由支店名称
		triSjMST.setKEIYU_STN_NAME(req.getParameter("keiyuStnName"));
		// 経由銀行住所
		triSjMST.setKEIYU_BNK_ADR(req.getParameter("keiyuBnkAdr"));
		// 受取人住所
		triSjMST.setUKE_ADR(req.getParameter("ukeAdr"));
		// 振込手数料区分
		int furiTesuKen = Integer.parseInt(req.getParameter("furiTesuKen"));
		// 振込手数料区分
		triSjMST.setFURI_TESU_KBN(furiTesuKen);
		// 手数料区分
		int gsTesuKbn = Integer.parseInt(req.getParameter("gsTesuKbn"));
		// 手数料区分
		triSjMST.setGS_TESU_KBN(gsTesuKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		triSjMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		triSjMST.setEND_DATE(endDate);
		// プログラムIDの設定
		triSjMST.setPRG_ID(req.getParameter("prgID"));

		// 結果を返す
		return triSjMST;
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		if ("defaultPaymentCondition".equals(req.getParameter("flag"))) {
			searchDefaultPaymentCondition(req, resp);
		} else if ("paymentConditionInfo".equals(req.getParameter("flag"))) {
			searchPaymentConditionInfo(req, resp);
		} else if ("refPaymentCondition".equals(req.getParameter("flag"))) {
			searchRefPaymentName(req, resp);
		} else if ("findOneInfo".equals(req.getParameter("flag"))) {
			findOneInfo(req, resp);
		} else {
			searchCheckNaiCode(req, resp);
		}
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new CustomerConditionMasterReportExcelDefine();
	}

	/**
	 * 検索データを取得する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void findOneInfo(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// 会社コード
		String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

		// 取引先コード
		String triCode = Util.avoidNull(req.getParameter("triCode"));

		// 取引先条件コード
		String tjkCode = Util.avoidNull(req.getParameter("triSjCode"));

		List lst = new ArrayList();
		lst.add(logic.findOneInfo(kaiCode, triCode, tjkCode));
		dispatchResultListObject(req, resp, lst);

	}

	/**
	 * デフォルトの支払条件取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchDefaultPaymentCondition(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CustomerConditionLogic logic = (CustomerConditionLogic) container
				.getComponent(CustomerConditionLogic.class);

			// 会社コード
			String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

			// 取引先コード
			String triCode = Util.avoidNull(req.getParameter("triCode"));

			// 通貨コード
			String curCode = Util.avoidNull(req.getParameter("curCode"));

			dispatchResultMap(req, resp, logic.getDefaultPaymentCondition(kaiCode, triCode, curCode));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * 支払先条件コードに紐付いた情報を取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchPaymentConditionInfo(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CustomerConditionLogic logic = (CustomerConditionLogic) container
				.getComponent(CustomerConditionLogic.class);

			Map<String, String> map = new HashMap<String, String>();
			// 会社コード
			map.put("kaiCode", Util.avoidNull(req.getParameter("kaiCode")));

			// 取引先コード
			map.put("triCode", Util.avoidNull(req.getParameter("triCode")));

			// 取引先条件コード
			map.put("tjkCode", Util.avoidNull(req.getParameter("tjkCode")));

			// 伝票日付
			map.put("slipDate", Util.avoidNull(req.getParameter("slipDate")));

			dispatchResultMap(req, resp, logic.getPaymentConditionInfo(map));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * 支払内部コード検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchCheckNaiCode(HttpServletRequest req, HttpServletResponse resp) {
		// flag: "checkNaiCode"
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CommonLogic logic = (CommonLogic) container.getComponent("PaymentMethodLogic");
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("hohHohCode", req.getParameter("hohHohCode"));

			// 実体の初期化
			AP_HOH_MST entity = (AP_HOH_MST) logic.findOne(keys);
			if (entity != null) {
				// 支払内部コードの取得
				Map data = new HashMap();
				data.put("hOH_NAI_CODE", entity.getHOH_NAI_CODE());
				// 結果の設定
				super.dispatchResultMap(req, resp, data);
			}
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * 支払条件検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefPaymentName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// 会社コード
		String kaiCode = req.getParameter("kaiCode");

		// 取引先コード
		String triCode = req.getParameter("triCode");

		// 検索条件コード
		String tjkCode = req.getParameter("tjkCode");

		// 結果を取得する。
		List list = logic.searchName(kaiCode, triCode, tjkCode);

		dispatchResultList(req, resp, list);
	}
}

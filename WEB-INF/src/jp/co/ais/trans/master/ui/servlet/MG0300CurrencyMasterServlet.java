package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import java.text.*;

import javax.servlet.http.*;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.CurrencyLogic;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 通貨マスタ画面Servlet (MG0300)
 * 
 * @author AIS
 */
public class MG0300CurrencyMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 2192504901187041314L;

	@Override
	protected String getLogicClassName() {
		return "CurrencyLogic";
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
		// 通貨コードの設定
		map.put("curCode", req.getParameter("curCode"));
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
		map.put("beginCurCode", req.getParameter("beginCurCode"));
		// 終了コードの設定
		map.put("endCurCode", req.getParameter("endCurCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		CUR_MST curMST = new CUR_MST();
		// 会社コードの設定
		curMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 通貨ｺｰﾄﾞの設定
		curMST.setCUR_CODE(req.getParameter("curCode"));
		// 通貨名称の設定
		curMST.setCUR_NAME(req.getParameter("curName"));
		// 通貨略称の設定
		curMST.setCUR_NAME_S(req.getParameter("curName_S"));
		// 通貨検索名称の設定
		curMST.setCUR_NAME_K(req.getParameter("curName_K"));
		// レート係数の取得
		int decKeta = Integer.parseInt(req.getParameter("decKeta"));
		// 小数点以下桁数の取得
		int ratePow = Integer.parseInt(req.getParameter("ratePow"));
		// 換算区分の取得
		int convKbn = Integer.parseInt(req.getParameter("convKbn"));
		// レート係数の設定
		curMST.setDEC_KETA(decKeta);
		// 小数点以下桁数の設定
		curMST.setRATE_POW(ratePow);
		// 換算区分の設定
		curMST.setCONV_KBN(convKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		curMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		curMST.setEND_DATE(endDate);
		// プログラムIDの設定
		curMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return curMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new CurrencyMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refCurrency".equals(flag)) {
			searchRefCurrency(req, resp);
		}
	}

	/**
	 * 通貨情報検索
	 * 
	 * @param req
	 * @param resp
	 */
	protected void searchRefCurrency(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		try {
			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			CurrencyLogic logic = (CurrencyLogic) container.getComponent(CurrencyLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("curCode", req.getParameter("curCode"));

			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

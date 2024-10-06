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
 * 消費税マスタ画面Servlet (MG0130)
 * 
 * @author ISFnet China
 */
public class MG0130ConsumptionTaxMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 5856804226168005388L;

	@Override
	protected String getLogicClassName() {
		return "ConsumptionTaxLogic";
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
		// 消費税コード
		map.put("zeiCode", req.getParameter("zeiCode"));
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
		map.put("beginZeiCode", req.getParameter("beginZeiCode"));
		// 終了コードの設定
		map.put("endZeiCode", req.getParameter("endZeiCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		SZEI_MST szeiMST = new SZEI_MST();
		// 会社コードの設定
		szeiMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 消費税コードの設定
		szeiMST.setZEI_CODE(req.getParameter("zeiCode"));
		// 消費税名称の設定
		szeiMST.setZEI_NAME(req.getParameter("zeiName"));
		// 消費税略称の設定
		szeiMST.setZEI_NAME_S(req.getParameter("zeiName_S"));
		// 消費税検索名称の設定
		szeiMST.setZEI_NAME_K(req.getParameter("zeiName_K"));
		// 消費税計算書出力順序の設定
		szeiMST.setSZEI_KEI_KBN(req.getParameter("szeiKeiKbn"));
		// 売上仕入区分の取得
		int usKbn = Integer.parseInt(req.getParameter("usKbn"));
		// 売上仕入区分の設定
		szeiMST.setUS_KBN(usKbn);
		// 消費税率の取得
		Float zeiRate = Float.parseFloat(req.getParameter("zeiRate"));
		// 消費税率の設定
		szeiMST.setZEI_RATE(zeiRate);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		szeiMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		szeiMST.setEND_DATE(endDate);
		// プログラムIDの設定
		szeiMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return szeiMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new ConsumptionTaxMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refTax".equals(flag)) {
			searchRefTax(req, resp);
		}
	}

	// 消費税共通フィルド用メソッド
	private void searchRefTax(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			ConsumptionTaxLogic logic = (ConsumptionTaxLogic) container.getComponent(ConsumptionTaxLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("zeiCode", req.getParameter("zeiCode"));

			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

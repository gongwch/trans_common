package jp.co.ais.trans.master.ui.servlet;

import java.util.*;
import java.math.*;
import java.text.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * レートマスタ画面Servlet (MG0310)
 * 
 * @author IFSnet China
 */
public class MG0310RateMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 5753057878071009755L;

	@Override
	protected String getLogicClassName() {
		return "RateLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/**
	 * 主キーの取得
	 * 
	 * @throws ParseException
	 */
	protected Map getPrimaryKeys(HttpServletRequest req) throws ParseException {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 通貨コードの設定
		map.put("curCode", req.getParameter("curCode"));
		// 適用開始日付の取得
		Date strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 適用開始日付の設定
		map.put("strDate", strDate);
		// 結果を返す
		return map;
	}

	/**
	 * 検索条件の取得
	 */
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

	/**
	 * エンティティの取得
	 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		RATE_MST rateMST = new RATE_MST();
		// 会社コードの設定
		rateMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 通貨ｺｰﾄﾞの設定
		rateMST.setCUR_CODE(req.getParameter("curCode"));
		// レートﾞの取得
		BigDecimal curRate = new BigDecimal(req.getParameter("curRate"));
		// レートﾞの設定
		rateMST.setCUR_RATE(curRate);
		// 適用開始日付の初期化
		Date strDate = null;
		// 適用開始日付の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 適用開始日付の設定
		rateMST.setSTR_DATE(strDate);
		// プログラムIDの設定
		rateMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return rateMST;
	}

	/**
	 * リスト出力のExcel定義
	 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new RateMasterReportExcelDefine();
	}
}

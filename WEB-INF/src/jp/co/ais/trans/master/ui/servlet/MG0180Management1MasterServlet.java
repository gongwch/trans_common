package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 管理１マスタ画面Servlet (MG0180)
 * 
 * @author ISFnet China
 */
public class MG0180Management1MasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -8193884622639097969L;

	@Override
	protected String getLogicClassName() {
		return "Management1Logic";
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
		// 管理コードの設定
		map.put("knrCode", req.getParameter("knrCode"));
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
		map.put("beginknrCode", req.getParameter("beginknrCode"));
		// 終了コードの設定
		map.put("endknrCode", req.getParameter("endknrCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		KNR1_MST knr1MST = new KNR1_MST();
		// 会社コードの設定
		knr1MST.setKAI_CODE(req.getParameter("kaiCode"));
		// 管理１ｺｰﾄﾞの設定
		knr1MST.setKNR_CODE_1(req.getParameter("knrCode"));
		// 管理１名称の設定
		knr1MST.setKNR_NAME_1(req.getParameter("knrName"));
		// 管理１略称の設定
		knr1MST.setKNR_NAME_S_1(req.getParameter("knrName_S"));
		// 管理１検索名称の設定
		knr1MST.setKNR_NAME_K_1(req.getParameter("knrName_K"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		knr1MST.setSTR_DATE(strDate);
		// 終了年月日の設定
		knr1MST.setEND_DATE(endDate);
		// プログラムIDの設定
		knr1MST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return knr1MST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new Management1MasterReportExcelDefine();
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 環境設定マスタ画面Servlet (MG0010)
 * 
 * @author ISFnet China
 */
public class MG0010EnvironmentalSettingMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 2055447815160612175L;

	@Override
	protected String getLogicClassName() {
		return "EnvironmentalSettingLogic";
	}

	protected String getREFKeyFields() {
		return "";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		ENV_MST envMST = new ENV_MST();
		// 会社コードの設定
		envMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 会社名称の設定
		envMST.setKAI_NAME(req.getParameter("kaiName"));
		// 会社略称の設定
		envMST.setKAI_NAME_S(req.getParameter("kaiName_S"));
		// 郵便番号の設定
		envMST.setZIP(req.getParameter("zip"));
		// 住所カナの設定
		envMST.setJYU_KANA(req.getParameter("jyuKana"));
		// 住所１の設定
		envMST.setJYU_1(req.getParameter("jyu1"));
		// 住所２の設定
		envMST.setJYU_2(req.getParameter("jyu2"));
		// 電話番号の設定
		envMST.setTEL(req.getParameter("tel"));
		// FAX番号の設定
		envMST.setFAX(req.getParameter("fax"));
		// バックカラーの設定
		envMST.setFORECOLOR(req.getParameter("foreColor"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		envMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		envMST.setEND_DATE(endDate);
		// プログラムIDの設定
		envMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return envMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new EnvironmentalSettingMasterReportExcelDefine();
	}
}

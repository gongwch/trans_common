package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * カレンダーマスタ画面Servlet (MP0020)
 * 
 * @author ISFnet China
 */
public class MP0020CalendarMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 8762997493326096483L;

	@Override
	protected String getLogicClassName() {
		return "CalendarLogic";
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
		// 日付の設定
		map.put("calDate", req.getParameter("calDate"));
		// 月の設定
		map.put("calMonth", req.getParameter("calMonth"));
		// 日数の設定
		map.put("intLastDay", req.getParameter("intLastDay"));
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 開始日付の設定
		map.put("beginCalDate", req.getParameter("beginCalDate"));
		// 終了日付の設定
		map.put("endCalDate", req.getParameter("endCalDate"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		AP_CAL_MST apCalMST = new AP_CAL_MST();
		// 会社コードの設定
		apCalMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 日付の取得
		Date calDate = DateUtil.toYMDDate(req.getParameter("calDate"));
		// 日付の設定
		apCalMST.setCAL_DATE(calDate);
		// 社員支払対象日区分の取得
		int calHarai = Integer.parseInt(req.getParameter("calHarai"));
		// 社員支払対象日区分の設定
		apCalMST.setCAL_HARAI(calHarai);
		// 銀行休日区分の取得
		int calBnkKbn = Integer.parseInt(req.getParameter("calBnkKbn"));
		// 銀行休日区分の設定
		apCalMST.setCAL_BNK_KBN(calBnkKbn);
		// 臨時支払対象日区分の取得
		int calSha = Integer.parseInt(req.getParameter("calSha"));
		// 臨時支払対象日区分の設定
		apCalMST.setCAL_SHA(calSha);
		// プログラムIDの設定
		apCalMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return apCalMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return null;
	}
}

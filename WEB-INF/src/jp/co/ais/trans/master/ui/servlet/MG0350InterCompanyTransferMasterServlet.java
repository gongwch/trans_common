package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 会社間付替マスタ画面Servlet (MG0350)
 * 
 * @author ISFnet China
 */
public class MG0350InterCompanyTransferMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -8731325651481641035L;

	@Override
	protected String getLogicClassName() {
		return "InterCompanyTransferLogic";
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
		// 付替会社コードの設定
		map.put("ktkKaiCode", req.getParameter("ktkKaiCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 付替会社コードの設定
		map.put("ktkKaiCode", req.getParameter("ktkKaiCode"));
		// 開始コードの設定
		map.put("beginKtkKaiCode", req.getParameter("beginKtkKaiCode"));
		// 終了コードの設定
		map.put("endKtkKaiCode", req.getParameter("endKtkKaiCode"));
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		KTK_MST ktkMST = new KTK_MST();
		// 会社コードの設定
		ktkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 付替会社コード
		ktkMST.setKTK_KAI_CODE(req.getParameter("ktkKaiCode"));
		// 付替計上部門コード
		ktkMST.setKTK_DEP_CODE(req.getParameter("ktkDepCode"));
		// 付替科目コード
		ktkMST.setKTK_KMK_CODE(req.getParameter("ktkKmkCode"));
		// 付替補助科目コード
		ktkMST.setKTK_HKM_CODE(req.getParameter("ktkHkmCode"));
		// 付替内訳科目コード
		ktkMST.setKTK_UKM_CODE(req.getParameter("ktkUkmCode"));
		// プログラムIDの設定
		ktkMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return ktkMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new InterCompanyTransferMasterReportExcelDefine();
	}
}

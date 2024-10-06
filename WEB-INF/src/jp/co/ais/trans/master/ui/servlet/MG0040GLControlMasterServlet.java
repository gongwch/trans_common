package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * GLコントロールマスタ画面Servlet (MG0040)
 * 
 * @author ISFnet China
 */
public class MG0040GLControlMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 1389937286658315664L;

	@Override
	protected String getLogicClassName() {
		return "GLControlLogic";
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
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コード
		map.put("kaiCode", req.getParameter("kaiCode"));

		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		GL_CTL_MST glCtlMST = new GL_CTL_MST();
		// 会社コードの設定
		glCtlMST.setKAI_CODE(req.getParameter("kaiCode"));
		// KSD_KBNの取得
		int ksdKbn = Integer.parseInt(req.getParameter("ksdKbn"));
		// KSN_NYU_KBNの取得
		int ksnNyuKbn = Integer.parseInt(req.getParameter("ksnNyuKbn"));
		// 元帳日別残高表示区分の取得
		int mtZanHyjKbn = Integer.parseInt(req.getParameter("mtZanHyjKbn"));
		// 評価替レート区分の取得
		int excRateKbn = Integer.parseInt(req.getParameter("excRateKbn"));
		// KSD_KBNの設定
		glCtlMST.setKSD_KBN(ksdKbn);
		// KSN_NYU_KBNの設定
		glCtlMST.setKSN_NYU_KBN(ksnNyuKbn);
		// 元帳日別残高表示区分の設定
		glCtlMST.setMT_ZAN_HYJ_KBN(mtZanHyjKbn);
		// 評価替レート区分の設定
		glCtlMST.setEXC_RATE_KBN(excRateKbn);
		// プログラムIDの設定
		glCtlMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return glCtlMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new GLControlMasterReportExcelDefine();
	}
}

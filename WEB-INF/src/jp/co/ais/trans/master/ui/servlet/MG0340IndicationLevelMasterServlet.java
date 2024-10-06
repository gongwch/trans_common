package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 開示ﾚﾍﾞﾙﾏｽﾀ画面Servlet (MG0340)
 * 
 * @author ISFnet China
 */
public class MG0340IndicationLevelMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -769800516753993536L;

	@Override
	protected String getLogicClassName() {
		return "IndicationLevelLogic";
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
		// ユーザーコードの設定
		map.put("kjlUsrId", req.getParameter("kjlUsrId"));
		// 科目体系ｺｰﾄﾞの設定
		map.put("kjlKmtCode", req.getParameter("kjlKmtCode"));
		// 組織コードの設定
		map.put("kjlDpkSsk", req.getParameter("kjlDpkSsk"));
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ユーザーコードの設定
		map.put("kjlUsrId", req.getParameter("kjlUsrId"));
		// 開始コードの設定
		map.put("beginKjlUsrId", req.getParameter("beginKjlUsrId"));
		// 終了コードの設定
		map.put("endKjlUsrId", req.getParameter("endKjlUsrId"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		KJL_MST kjlMST = new KJL_MST();
		// 会社コードの設定
		kjlMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ユーザーコードの設定
		kjlMST.setKJL_USR_ID(req.getParameter("kjlUsrId"));
		// 科目体系ｺｰﾄﾞの設定
		kjlMST.setKJL_KMT_CODE(req.getParameter("kjlKmtCode"));
		// 組織コードの設定
		kjlMST.setKJL_DPK_SSK(req.getParameter("kjlDpkSsk"));
		// 開示レベルの取得
		int kjlLvl = Integer.parseInt(req.getParameter("kjlLvl"));
		// 開示レベルの設定
		kjlMST.setKJL_LVL(kjlLvl);
		// 上位部門コードの設定
		kjlMST.setKJL_UP_DEP_CODE(req.getParameter("kjlUpDepCode"));
		// 部門コードの設定
		kjlMST.setKJL_DEP_CODE(req.getParameter("kjlDepCode"));
		// プログラムIDの設定
		kjlMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return kjlMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new IndicationLevelMasterReportExcelDefine();
	}
}

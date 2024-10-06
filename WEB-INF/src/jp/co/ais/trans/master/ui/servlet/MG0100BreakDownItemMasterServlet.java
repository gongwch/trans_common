package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 内訳科目マスタ画面Servlet (MG0100)
 * 
 * @author ISFnet China
 */
public class MG0100BreakDownItemMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -1562841742271715056L;

	@Override
	protected String getLogicClassName() {
		return "BreakDownItemLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode,kmkCode,hkmCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 科目コードの設定
		map.put("kmkCode", req.getParameter("kmkCode"));
		// 補助科目コードの設定
		map.put("hkmCode", req.getParameter("hkmCode"));
		// 内訳科目コードの設定
		map.put("ukmCode", req.getParameter("ukmCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 科目コードの設定
		map.put("kmkCode", req.getParameter("kmkCode"));
		// 補助科目コードの設定
		map.put("hkmCode", req.getParameter("hkmCode"));
		// 開始コードの設定
		map.put("beginUkmCode", req.getParameter("beginUkmCode"));
		// 終了コードの設定
		map.put("endUkmCode", req.getParameter("endUkmCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		UKM_MST ukmMST = new UKM_MST();
		// 会社コードの設定
		ukmMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 科目コードの設定
		ukmMST.setKMK_CODE(req.getParameter("kmkCode"));
		// 補助科目コードの設定
		ukmMST.setHKM_CODE(req.getParameter("hkmCode"));
		// 内訳科目コードの設定
		ukmMST.setUKM_CODE(req.getParameter("ukmCode"));
		// 内訳科目名称の設定
		ukmMST.setUKM_NAME(req.getParameter("ukmName"));
		// 内訳科目略称の設定
		ukmMST.setUKM_NAME_S(req.getParameter("ukmName_S"));
		// 内訳科目検索名称の設定
		ukmMST.setUKM_NAME_K(req.getParameter("ukmName_K"));
		// 消費税コードの設定
		ukmMST.setZEI_CODE(req.getParameter("zeiCode"));
		// 入金伝票入力ﾌﾗｸﾞの取得
		int glFlg1 = Integer.parseInt(req.getParameter("glFlg1"));
		// 出金伝票入力ﾌﾗｸﾞの取得
		int glFlg2 = Integer.parseInt(req.getParameter("glFlg2"));
		// 振替伝票入力ﾌﾗｸﾞの取得
		int glFlg3 = Integer.parseInt(req.getParameter("glFlg3"));
		// 経費精算伝票入力ﾌﾗｸﾞの取得
		int apFlg1 = Integer.parseInt(req.getParameter("apFlg1"));
		// 請求書伝票入力ﾌﾗｸﾞの取得
		int apFlg2 = Integer.parseInt(req.getParameter("apFlg2"));
		// 債権計上伝票入力ﾌﾗｸﾞの取得
		int arFlg1 = Integer.parseInt(req.getParameter("arFlg1"));
		// 債権消込伝票入力ﾌﾗｸﾞの取得
		int arFlg2 = Integer.parseInt(req.getParameter("arFlg2"));
		// 資産計上伝票入力ﾌﾗｸﾞの取得
		int faFlg1 = Integer.parseInt(req.getParameter("faFlg1"));
		// 支払依頼伝票入力ﾌﾗｸﾞの取得
		int faFlg2 = Integer.parseInt(req.getParameter("faFlg2"));
		// 取引先入力フラグの取得
		int triCodeFlg = Integer.parseInt(req.getParameter("triCodeFlg"));
		// 発生日入力ﾌﾗｸﾞの取得
		int hasFlg = Integer.parseInt(req.getParameter("hasFlg"));
		// 社員入力フラグの取得
		int empCodeFlg = Integer.parseInt(req.getParameter("empCodeFlg"));
		// 管理１入力フラグの取得
		int knrFlg1 = Integer.parseInt(req.getParameter("knrFlg1"));
		// 管理2入力フラグの取得
		int knrFlg2 = Integer.parseInt(req.getParameter("knrFlg2"));
		// 管理3入力フラグの取得
		int knrFlg3 = Integer.parseInt(req.getParameter("knrFlg3"));
		// 管理4入力フラグの取得
		int knrFlg4 = Integer.parseInt(req.getParameter("knrFlg4"));
		// 管理5入力フラグの取得
		int knrFlg5 = Integer.parseInt(req.getParameter("knrFlg5"));
		// 管理6入力フラグの取得
		int knrFlg6 = Integer.parseInt(req.getParameter("knrFlg6"));
		// 非会計1入力フラグの取得
		int hmFlg1 = Integer.parseInt(req.getParameter("hmFlg1"));
		// 非会計2入力フラグの取得
		int hmFlg2 = Integer.parseInt(req.getParameter("hmFlg2"));
		// 非会計3入力フラグの取得
		int hmFlg3 = Integer.parseInt(req.getParameter("hmFlg3"));
		// 売上課税入力フラグの取得
		int uriZeiFlg = Integer.parseInt(req.getParameter("uriZeiFlg"));
		// 仕入課税入力フラグの取得
		int sirZeiFlg = Integer.parseInt(req.getParameter("sirZeiFlg"));
		// 多通貨入力フラグの取得
		int mcrFlg = Integer.parseInt(req.getParameter("mcrFlg"));
		// 評価替対象フラグの取得
		int excFlg = Integer.parseInt(req.getParameter("excFlg"));
		// 入金伝票入力ﾌﾗｸﾞの設定
		ukmMST.setGL_FLG_1(glFlg1);
		// 出金伝票入力ﾌﾗｸﾞの設定
		ukmMST.setGL_FLG_2(glFlg2);
		// 振替伝票入力ﾌﾗｸﾞの設定
		ukmMST.setGL_FLG_3(glFlg3);
		// 経費精算伝票入力ﾌﾗｸﾞの設定
		ukmMST.setAP_FLG_1(apFlg1);
		// 請求書伝票入力ﾌﾗｸﾞの設定
		ukmMST.setAP_FLG_2(apFlg2);
		// 債権計上伝票入力ﾌﾗｸﾞの設定
		ukmMST.setAR_FLG_1(arFlg1);
		// 債権消込伝票入力ﾌﾗｸﾞの設定
		ukmMST.setAR_FLG_2(arFlg2);
		// 資産計上伝票入力ﾌﾗｸﾞの設定
		ukmMST.setFA_FLG_1(faFlg1);
		// 支払依頼伝票入力ﾌﾗｸﾞの設定
		ukmMST.setFA_FLG_2(faFlg2);
		// 取引先入力フラグの設定
		ukmMST.setTRI_CODE_FLG(triCodeFlg);
		// 発生日入力ﾌﾗｸﾞの設定
		ukmMST.setHAS_FLG(hasFlg);
		// 社員入力フラグの設定
		ukmMST.setEMP_CODE_FLG(empCodeFlg);
		// 管理１入力フラグの設定
		ukmMST.setKNR_FLG_1(knrFlg1);
		// 管理2入力フラグの設定
		ukmMST.setKNR_FLG_2(knrFlg2);
		// 管理3入力フラグの設定
		ukmMST.setKNR_FLG_3(knrFlg3);
		// 管理4入力フラグの設定
		ukmMST.setKNR_FLG_4(knrFlg4);
		// 管理5入力フラグの設定
		ukmMST.setKNR_FLG_5(knrFlg5);
		// 管理6入力フラグの設定
		ukmMST.setKNR_FLG_6(knrFlg6);
		// 非会計1入力フラグの設定
		ukmMST.setHM_FLG_1(hmFlg1);
		// 非会計2入力フラグの設定
		ukmMST.setHM_FLG_2(hmFlg2);
		// 非会計3入力フラグの設定
		ukmMST.setHM_FLG_3(hmFlg3);
		// 売上課税入力フラグの設定
		ukmMST.setURI_ZEI_FLG(uriZeiFlg);
		// 仕入課税入力フラグの設定
		ukmMST.setSIR_ZEI_FLG(sirZeiFlg);
		// 多通貨入力フラグの設定
		ukmMST.setMCR_FLG(mcrFlg);
		// 評価替対象フラグの設定
		ukmMST.setEXC_FLG(excFlg);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		ukmMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		ukmMST.setEND_DATE(endDate);
		// プログラムIDの設定
		ukmMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return ukmMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new BreakDownItemMasterReportExcelDefine();
	}
}

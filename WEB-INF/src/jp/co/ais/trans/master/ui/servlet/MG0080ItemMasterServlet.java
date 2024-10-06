package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 科目マスタ画面Servlet (MG0080)
 * 
 * @author ISFnet China
 */
public class MG0080ItemMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 7511245514808750938L;

	@Override
	protected String getLogicClassName() {
		return "ItemLogic";
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
		// 科目コードの設定
		map.put("kmkCode", req.getParameter("kmkCode"));
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
		map.put("beginKmkCode", req.getParameter("beginKmkCode"));
		// 終了コードの設定
		map.put("endKmkCode", req.getParameter("endKmkCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		KMK_MST kmkMST = new KMK_MST();
		ServerLogger.debug("BS" + req.getParameter("kesiKbn"));
		ServerLogger.debug("OTHER" + req.getParameter("excFlg"));

		// 会社コードの設定
		kmkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 科目コードの設定
		kmkMST.setKMK_CODE(req.getParameter("kmkCode"));
		// 科目名称の設定
		kmkMST.setKMK_NAME(req.getParameter("kmkName"));
		// 科目略称の設定
		kmkMST.setKMK_NAME_S(req.getParameter("kmkName_S"));
		// 科目検索名称の設定
		kmkMST.setKMK_NAME_K(req.getParameter("kmkName_K"));
		// 科目種別の設定
		kmkMST.setKMK_SHU(req.getParameter("kmkShu"));
		// ＧＬ科目制御区分の設定
		kmkMST.setKMK_CNT_GL(req.getParameter("kmkCntGl"));
		// AP科目制御区分の設定
		kmkMST.setKMK_CNT_AP(req.getParameter("kmkCntAp"));
		// AR科目制御区分の設定
		kmkMST.setKMK_CNT_AR(req.getParameter("kmkCntAr"));
		// 固定部門ｺｰﾄﾞの設定
		kmkMST.setKOTEI_DEP_CODE(req.getParameter("koteiDepCode"));
		// 消費税コードの設定
		kmkMST.setZEI_CODE(req.getParameter("zeiCode"));
		// BG科目制御区分の設定
		kmkMST.setKMK_CNT_BG(req.getParameter("kmkCntBg"));
		// 借方資金コードの設定
		kmkMST.setSKN_CODE_DR(req.getParameter("sknCodeDr"));
		// 貸方資金コードの設定
		kmkMST.setSKN_CODE_CR(req.getParameter("sknCodeCr"));
		// 相殺科目制御区分の設定
		kmkMST.setKMK_CNT_SOUSAI(req.getParameter("kmkCntSousai"));
		// 集計区分の取得
		int sumKbn = Integer.parseInt(req.getParameter("sumKbn"));
		// 貸借区分の取得
		int dcKbn = Integer.parseInt(req.getParameter("dcKbn"));
		// 補助区分の取得
		int hkmKbn = Integer.parseInt(req.getParameter("hkmKbn"));
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
		// BS勘定消込区分の取得 2007/02/15
		int kesiKbn = Integer.parseInt(req.getParameter("kesiKbn"));
		// 集計区分の設定
		kmkMST.setSUM_KBN(sumKbn);
		// 貸借区分の設定
		kmkMST.setDC_KBN(dcKbn);
		// 補助区分の設定
		kmkMST.setHKM_KBN(hkmKbn);
		// 入金伝票入力ﾌﾗｸﾞの設定
		kmkMST.setGL_FLG_1(glFlg1);
		// 出金伝票入力ﾌﾗｸﾞの設定
		kmkMST.setGL_FLG_2(glFlg2);
		// 振替伝票入力ﾌﾗｸﾞの設定
		kmkMST.setGL_FLG_3(glFlg3);
		// 経費精算伝票入力ﾌﾗｸﾞの設定
		kmkMST.setAP_FLG_1(apFlg1);
		// 請求書伝票入力ﾌﾗｸﾞの設定
		kmkMST.setAP_FLG_2(apFlg2);
		// 債権計上伝票入力ﾌﾗｸﾞの設定
		kmkMST.setAR_FLG_1(arFlg1);
		// 債権消込伝票入力ﾌﾗｸﾞの設定
		kmkMST.setAR_FLG_2(arFlg2);
		// 資産計上伝票入力ﾌﾗｸﾞの設定
		kmkMST.setFA_FLG_1(faFlg1);
		// 支払依頼伝票入力ﾌﾗｸﾞの設定
		kmkMST.setFA_FLG_2(faFlg2);
		// 取引先入力フラグの設定
		kmkMST.setTRI_CODE_FLG(triCodeFlg);
		// 発生日入力ﾌﾗｸﾞの設定
		kmkMST.setHAS_FLG(hasFlg);
		// 社員入力フラグの設定
		kmkMST.setEMP_CODE_FLG(empCodeFlg);
		// 管理１入力フラグの設定
		kmkMST.setKNR_FLG_1(knrFlg1);
		// 管理2入力フラグの設定
		kmkMST.setKNR_FLG_2(knrFlg2);
		// 管理3入力フラグの設定
		kmkMST.setKNR_FLG_3(knrFlg3);
		// 管理4入力フラグの設定
		kmkMST.setKNR_FLG_4(knrFlg4);
		// 管理5入力フラグの設定
		kmkMST.setKNR_FLG_5(knrFlg5);
		// 管理6入力フラグの設定
		kmkMST.setKNR_FLG_6(knrFlg6);
		// 非会計1入力フラグの設定
		kmkMST.setHM_FLG_1(hmFlg1);
		// 非会計2入力フラグの設定
		kmkMST.setHM_FLG_2(hmFlg2);
		// 非会計3入力フラグの設定
		kmkMST.setHM_FLG_3(hmFlg3);
		// 売上課税入力フラグの設定
		kmkMST.setURI_ZEI_FLG(uriZeiFlg);
		// 仕入課税入力フラグの設定
		kmkMST.setSIR_ZEI_FLG(sirZeiFlg);
		// 多通貨入力フラグの設定
		kmkMST.setMCR_FLG(mcrFlg);
		// 評価替対象フラグの設定
		kmkMST.setEXC_FLG(excFlg);
		// BS勘定消込区分の設定 2007/02/15 Y
		kmkMST.setKESI_KBN(kesiKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		kmkMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		kmkMST.setEND_DATE(endDate);
		// プログラムIDの設定
		kmkMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return kmkMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new ItemMasterReportExcelDefine();
	}
}

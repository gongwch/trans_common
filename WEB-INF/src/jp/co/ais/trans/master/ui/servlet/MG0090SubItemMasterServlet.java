package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 補助科目マスタ画面Servlet (MG0090)
 * 
 * @author ISFnet China
 */
public class MG0090SubItemMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 1515595868487801800L;

	@Override
	protected String getLogicClassName() {
		return "SubItemLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode,kmkCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		Map map = new HashMap();
		map.put("kaiCode", req.getParameter("kaiCode"));
		map.put("kmkCode", req.getParameter("kmkCode"));
		map.put("hkmCode", req.getParameter("hkmCode"));
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		Map map = new HashMap();
		map.put("kaiCode", req.getParameter("kaiCode"));
		map.put("kmkCode", req.getParameter("kmkCode"));
		map.put("beginHkmCode", req.getParameter("beginHkmCode"));
		map.put("endHkmCode", req.getParameter("endHkmCode"));
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		HKM_MST hkmMST = new HKM_MST();

		hkmMST.setKAI_CODE(req.getParameter("kaiCode"));
		hkmMST.setKMK_CODE(req.getParameter("kmkCode"));
		hkmMST.setHKM_CODE(req.getParameter("hkmCode"));
		hkmMST.setHKM_NAME(req.getParameter("hkmName"));
		hkmMST.setHKM_NAME_S(req.getParameter("hkmName_S"));
		hkmMST.setHKM_NAME_K(req.getParameter("hkmName_K"));
		hkmMST.setZEI_CODE(req.getParameter("zeiCode"));

		int ukmKbn = Integer.parseInt(req.getParameter("ukmKbn"));
		int glFlg1 = Integer.parseInt(req.getParameter("glFlg1"));
		int glFlg2 = Integer.parseInt(req.getParameter("glFlg2"));
		int glFlg3 = Integer.parseInt(req.getParameter("glFlg3"));
		int apFlg1 = Integer.parseInt(req.getParameter("apFlg1"));
		int apFlg2 = Integer.parseInt(req.getParameter("apFlg2"));
		int arFlg1 = Integer.parseInt(req.getParameter("arFlg1"));
		int arFlg2 = Integer.parseInt(req.getParameter("arFlg2"));
		int faFlg1 = Integer.parseInt(req.getParameter("faFlg1"));
		int faFlg2 = Integer.parseInt(req.getParameter("faFlg2"));
		int triCodeFlg = Integer.parseInt(req.getParameter("triCodeFlg"));
		int hasFlg = Integer.parseInt(req.getParameter("hasFlg"));
		int empCodeFlg = Integer.parseInt(req.getParameter("empCodeFlg"));
		int knrFlg1 = Integer.parseInt(req.getParameter("knrFlg1"));
		int knrFlg2 = Integer.parseInt(req.getParameter("knrFlg2"));
		int knrFlg3 = Integer.parseInt(req.getParameter("knrFlg3"));
		int knrFlg4 = Integer.parseInt(req.getParameter("knrFlg4"));
		int knrFlg5 = Integer.parseInt(req.getParameter("knrFlg5"));
		int knrFlg6 = Integer.parseInt(req.getParameter("knrFlg6"));
		int hmFlg1 = Integer.parseInt(req.getParameter("hmFlg1"));
		int hmFlg2 = Integer.parseInt(req.getParameter("hmFlg2"));
		int hmFlg3 = Integer.parseInt(req.getParameter("hmFlg3"));
		int uriZeiFlg = Integer.parseInt(req.getParameter("uriZeiFlg"));
		int sirZeiFlg = Integer.parseInt(req.getParameter("sirZeiFlg"));
		int mcrFlg = Integer.parseInt(req.getParameter("mcrFlg"));
		int excFlg = Integer.parseInt(req.getParameter("excFlg"));
		hkmMST.setUKM_KBN(ukmKbn);
		hkmMST.setGL_FLG_1(glFlg1);
		hkmMST.setGL_FLG_2(glFlg2);
		hkmMST.setGL_FLG_3(glFlg3);
		hkmMST.setAP_FLG_1(apFlg1);
		hkmMST.setAP_FLG_2(apFlg2);
		hkmMST.setAR_FLG_1(arFlg1);
		hkmMST.setAR_FLG_2(arFlg2);
		hkmMST.setFA_FLG_1(faFlg1);
		hkmMST.setFA_FLG_2(faFlg2);
		hkmMST.setTRI_CODE_FLG(triCodeFlg);
		hkmMST.setHAS_FLG(hasFlg);
		hkmMST.setEMP_CODE_FLG(empCodeFlg);
		hkmMST.setKNR_FLG_1(knrFlg1);
		hkmMST.setKNR_FLG_2(knrFlg2);
		hkmMST.setKNR_FLG_3(knrFlg3);
		hkmMST.setKNR_FLG_4(knrFlg4);
		hkmMST.setKNR_FLG_5(knrFlg5);
		hkmMST.setKNR_FLG_6(knrFlg6);
		hkmMST.setHM_FLG_1(hmFlg1);
		hkmMST.setHM_FLG_2(hmFlg2);
		hkmMST.setHM_FLG_3(hmFlg3);
		hkmMST.setURI_ZEI_FLG(uriZeiFlg);
		hkmMST.setSIR_ZEI_FLG(sirZeiFlg);
		hkmMST.setMCR_FLG(mcrFlg);
		hkmMST.setEXC_FLG(excFlg);

		Date strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		Date endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		hkmMST.setSTR_DATE(strDate);
		hkmMST.setEND_DATE(endDate);

		hkmMST.setPRG_ID(req.getParameter("prgID")); // プログラムID

		return hkmMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		return new SubItemMasterReportExcelDefine();
	}
}

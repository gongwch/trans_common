package jp.co.ais.trans.master.ui.servlet;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 部門マスタ画面Servlet (MG0060)
 * 
 * @author AIS
 */
public class MG0060DepartmentMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -1501238776922033897L;

	@Override
	protected String getLogicClassName() {
		return "DepartmentLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode,dpkSsk,level,parentDepCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 部門コードの設定
		map.put("depCode", req.getParameter("depCode"));
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
		map.put("beginDepCode", req.getParameter("beginDepCode"));
		// 終了コードの設定
		map.put("endDepCode", req.getParameter("endDepCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		BMN_MST depMST = new BMN_MST();
		// 会社コードの設定
		depMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 部門コードの設定
		depMST.setDEP_CODE(req.getParameter("depCode"));
		// 部門名称の設定
		depMST.setDEP_NAME(req.getParameter("depName"));
		// 部門略称の設定
		depMST.setDEP_NAME_S(req.getParameter("depName_S"));
		// 部門検索名称の設定
		depMST.setDEP_NAME_K(req.getParameter("depName_K"));
		// 人員数１の取得
		int men1 = Integer.parseInt(req.getParameter("men1"));
		// 人員数２の取得
		int men2 = Integer.parseInt(req.getParameter("men2"));
		// 床面積の取得
		BigDecimal space = new BigDecimal(req.getParameter("space"));
		// 部門区分の取得
		int depKbn = Integer.parseInt(req.getParameter("depKbn"));
		// 人員数１の設定
		depMST.setMEN_1(men1);
		// 人員数２の設定
		depMST.setMEN_2(men2);
		// 床面積の設定
		depMST.setAREA(space);
		// 部門区分の設定
		depMST.setDEP_KBN(depKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		depMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		depMST.setEND_DATE(endDate);
		// プログラムIDの設定
		depMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return depMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new DepartmentMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");

		// 検索
		if ("DepartmentInfo".equals(flag)) {
			dispatchDeptData(req, resp);
		}
	}

	/**
	 * 単体検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void dispatchDeptData(HttpServletRequest req, HttpServletResponse resp) {

		String companyCode = req.getParameter("companyCode");
		String deptCode = req.getParameter("deptCode");
		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);
		logic.setCompanyCode(companyCode);

		BMN_MST entity = logic.findDepartment(deptCode, beginCode, endCode);

		super.dispatchResultDto(req, resp, entity);
	}
}

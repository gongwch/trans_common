package jp.co.ais.trans.master.ui.servlet;

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
 * 社員マスタ画面Servlet (MG0160)
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 2503184025898490906L;

	@Override
	protected String getLogicClassName() {
		return "EmployeeLogic";
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
		// 社員コードの設定
		map.put("empCode", req.getParameter("empCode"));
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
		map.put("beginEmpCode", req.getParameter("beginEmpCode"));
		// 終了コードの設定
		map.put("endEmpCode", req.getParameter("endEmpCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		EMP_MST empMST = new EMP_MST();
		// 会社コードの設定
		empMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 社員コードの設定
		empMST.setEMP_CODE(req.getParameter("empCode"));
		// 社員名称の設定
		empMST.setEMP_NAME(req.getParameter("empName"));
		// 社員略称の設定
		empMST.setEMP_NAME_S(req.getParameter("empName_S"));
		// 社員検索名称の設定
		empMST.setEMP_NAME_K(req.getParameter("empName_K"));
		// 振込銀行ＣＤの設定
		empMST.setEMP_FURI_BNK_CODE(req.getParameter("empFuriBnkCode"));
		// 振込支店ＣＤの設定
		empMST.setEMP_FURI_STN_CODE(req.getParameter("empFuriStnCode"));
		// 振込口座番号の設定
		empMST.setEMP_YKN_NO(req.getParameter("empYknNo"));
		// 振込口座預金種別の取得
		int empKozaKbn = Integer.parseInt(req.getParameter("empKozaKbn"));
		// 振込口座預金種別の設定
		empMST.setEMP_KOZA_KBN(empKozaKbn);
		// 口座名義カナの設定
		empMST.setEMP_YKN_KANA(req.getParameter("empYknKana"));
		// 振出銀行口座コードの設定
		empMST.setEMP_CBK_CODE(req.getParameter("empCbkCode"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		empMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		empMST.setEND_DATE(endDate);
		// プログラムIDの設定
		empMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return empMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new EmployeeMasterReportExcelDefine();
	}

	/**
	 * その他の場合
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refEmployee".equals(flag)) {
			searchRefEmployee(req, resp);
		}
	}

	/**
	 * 社員リスト検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefEmployee(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			EmployeeLogic logic = (EmployeeLogic) container.getComponent(EmployeeLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("empCode", req.getParameter("empCode"));

			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

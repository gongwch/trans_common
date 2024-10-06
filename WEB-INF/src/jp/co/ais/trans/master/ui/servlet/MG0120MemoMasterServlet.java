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
 * 摘要マスタ画面Servlet (MG0120)
 * 
 * @author ISFnet China
 */
public class MG0120MemoMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -8767366359332459952L;

	@Override
	protected String getLogicClassName() {
		return "MemoLogic";
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
		// 摘要コードの設定
		map.put("tekCode", req.getParameter("tekCode"));
		// 摘要区分の設定
		map.put("tekKbn", req.getParameter("tekKbn"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 摘要区分の設定
		map.put("tekKbn", req.getParameter("tekKbn"));
		// 開始コードの設定
		map.put("beginTekCode", req.getParameter("beginTekCode"));
		// 終了コードの設定
		map.put("endTekCode", req.getParameter("endTekCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		TEK_MST tekMST = new TEK_MST();
		// 会社コードの設定
		tekMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ﾃﾞｰﾀ区分の設定
		tekMST.setDATA_KBN(req.getParameter("dataKbn"));
		// 摘要コードの設定
		tekMST.setTEK_CODE(req.getParameter("tekCode"));
		// 摘要名称の設定
		tekMST.setTEK_NAME(req.getParameter("tekName"));
		// 摘要検索名称の設定
		tekMST.setTEK_NAME_K(req.getParameter("tekName_K"));
		// 摘要区分の取得
		int tekKbn = Integer.parseInt(req.getParameter("tekKbn"));
		// 摘要区分の設定
		tekMST.setTEK_KBN(tekKbn);
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		tekMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		tekMST.setEND_DATE(endDate);
		// プログラムIDの設定
		tekMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return tekMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new MemoMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 検索
		if ("refMemo".equals(flag)) {
			searchRefMemo(req, resp);
		}
	}

	private void searchRefMemo(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			MemoLogic logic = (MemoLogic) container.getComponent(MemoLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("tekCode", req.getParameter("tekCode"));
			keys.put("tekKbn", req.getParameter("tekKbn"));

			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

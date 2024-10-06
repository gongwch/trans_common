package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 自動仕訳科目マスタ画面Servlet (MG0430)
 * 
 * @author ISFnet China
 */
public class MG0430AutomaticJournalizingItemMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -2031916415598549009L;

	@Override
	protected String getLogicClassName() {
		return "AutomaticJournalizingItemLogic";
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
		// 科目制御区分の設定
		map.put("kmkCnt", req.getParameter("kmkCnt"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 科目制御区分の設定
		map.put("kmkCnt", req.getParameter("kmkCnt"));
		// 開始コードの設定
		map.put("beginKmkCnt", req.getParameter("beginKmkCnt"));
		// 終了コードの設定
		map.put("endKmkCnt", req.getParameter("endKmkCnt"));
		// 結果を返す
		return map;
	}

	/**
	 * 判定用
	 * 
	 * @param req
	 * @param resp
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {

		// 判定用flag
		String flag = req.getParameter("flag");

		// 検索
		if ("findKmkCntName".equals(flag)) {
			findKmkCntName(req, resp);
		}
	}

	/**
	 * 科目制御区分・科目制御名称の取得
	 * 
	 * @param req
	 * @param resp
	 */
	protected void findKmkCntName(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();

			CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());

			Map conditions = this.getFindConditions(req);
			List list = logic.find(conditions);

			// 科目制御区分・科目制御コードをMapにセット
			Map map = new TreeMap();
			int intKmkCnt = 0;
			String kmkCnt = null;
			String kmkCntName = null;

			Iterator iterator = list.iterator();
			for (int row = 0; iterator.hasNext(); row++) {
				SWK_KMK_MST value = (SWK_KMK_MST) iterator.next();
				intKmkCnt = value.getKMK_CNT();

				// Stringへ変換
				kmkCnt = Integer.toString(intKmkCnt);
				kmkCntName = value.getKMK_CNT_NAME();

				map.put(kmkCnt, kmkCntName);
			}

			super.dispatchResultMap(req, resp, map);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) {
		// 実体の初期化
		SWK_KMK_MST swkKmkMST = new SWK_KMK_MST();
		// 会社コードの設定
		swkKmkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 科目制御区分の取得
		int kmkCnt = Integer.parseInt(req.getParameter("kmkCnt"));
		// 科目制御区分の設定
		swkKmkMST.setKMK_CNT(kmkCnt);
		// 科目制御名称の設定
		swkKmkMST.setKMK_CNT_NAME(req.getParameter("kmkCntName"));
		// 科目コードの設定
		swkKmkMST.setKMK_CODE(req.getParameter("kmkCode"));
		// 補助科目コードの設定
		swkKmkMST.setHKM_CODE(req.getParameter("hkmCode"));
		// 内訳科目コードの設定
		swkKmkMST.setUKM_CODE(req.getParameter("ukmCode"));
		// 計上部門コードの設定
		swkKmkMST.setDEP_CODE(req.getParameter("depCode"));
		// 結果を返す
		return swkKmkMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return null;
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 部門階層マスタ画面Servlet
 * 
 * @author AIS
 */
public class MG0050DepartmentHierarchyMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 7691999673652241786L;

	@Override
	protected String getLogicClassName() {
		return "DepartmentHierarchyLogic";
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
		// 組織コードの設定
		map.put("dpkSsk", req.getParameter("dpkSsk"));
		// 部門コードの設定
		map.put("dpkDepCode", req.getParameter("dpkDepCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// 組織コードの設定
		map.put("dpkSsk", req.getParameter("dpkSsk"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		DPK_MST dpkMST = new DPK_MST();
		// 会社コードの設定
		dpkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// 組織コードの設定
		dpkMST.setDPK_SSK(req.getParameter("dpkSsk"));
		// 部門コードの設定
		dpkMST.setDPK_DEP_CODE(req.getParameter("dpkDepCode"));
		// レベルの取得
		int dpkLvl = Integer.parseInt(req.getParameter("dpkLvl"));
		// レベルの設定
		dpkMST.setDPK_LVL(dpkLvl);
		// 管理部門コードレベル０の設定
		dpkMST.setDPK_LVL_0(req.getParameter("dpkLvl0"));
		// 管理部門コードレベル1の設定
		dpkMST.setDPK_LVL_1(req.getParameter("dpkLvl1"));
		// 管理部門コードレベル2の設定
		dpkMST.setDPK_LVL_2(req.getParameter("dpkLvl2"));
		// 管理部門コードレベル3の設定
		dpkMST.setDPK_LVL_3(req.getParameter("dpkLvl3"));
		// 管理部門コードレベル4の設定
		dpkMST.setDPK_LVL_4(req.getParameter("dpkLvl4"));
		// 管理部門コードレベル5の設定
		dpkMST.setDPK_LVL_5(req.getParameter("dpkLvl5"));
		// 管理部門コードレベル6の設定
		dpkMST.setDPK_LVL_6(req.getParameter("dpkLvl6"));
		// 管理部門コードレベル7の設定
		dpkMST.setDPK_LVL_7(req.getParameter("dpkLvl7"));
		// 管理部門コードレベル8の設定
		dpkMST.setDPK_LVL_8(req.getParameter("dpkLvl8"));
		// 管理部門コードレベル9の設定
		dpkMST.setDPK_LVL_9(req.getParameter("dpkLvl9"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		dpkMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		dpkMST.setEND_DATE(endDate);
		// プログラムIDの設定
		dpkMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return dpkMST;
	}

	/** その他の場合 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 処理種別の取得
		String flag = req.getParameter("flag");
		// containerの初期化
		S2Container container = null;
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// ファイルの取得
			// DepartmentHierarchyLogicImpl logic =
			// (DepartmentHierarchyLogicImpl) container
			// .getComponent("logic");
			DepartmentHierarchyLogic logic = (DepartmentHierarchyLogic) container.getComponent(getLogicClassName());
			// ユーザコード設定
			logic.setUserCode(refTServerUserInfo(req).getUserCode());
			if ("getorganizations".equals(flag)) {
				// 会社コードの取得
				String kaiCode = req.getParameter("kaiCode");
				// 結果の取得
				List list = logic.getOrganizations(kaiCode);
				// 結果の設定
				super.dispatchResultListObject(req, resp, list);
			}
			// "createOrganization"
			else if ("createorganization".equals(flag)) {
				// 会社コードの取得
				String kaiCode = req.getParameter("kaiCode");
				// 組織コードの取得
				String dpkSsk = req.getParameter("dpkSsk");
				// 部門コードの取得
				String dpkDepCode = req.getParameter("dpkDepCode");
				// 実体の初期化
				DPK_MST dpkMST = new DPK_MST();
				// 会社コードの設定
				dpkMST.setKAI_CODE(kaiCode);
				// 組織コードの設定
				dpkMST.setDPK_SSK(dpkSsk);
				// 部門コードの設定
				dpkMST.setDPK_DEP_CODE(dpkDepCode);
				// レベルの設定
				dpkMST.setDPK_LVL(0);
				// 管理部門コードレベル０の設定
				dpkMST.setDPK_LVL_0(dpkDepCode);
				// 管理部門コードレベル1の設定
				dpkMST.setDPK_LVL_1(null);
				// 管理部門コードレベル2の設定
				dpkMST.setDPK_LVL_2(null);
				// 管理部門コードレベル3の設定
				dpkMST.setDPK_LVL_3(null);
				// 管理部門コードレベル4の設定
				dpkMST.setDPK_LVL_4(null);
				// 管理部門コードレベル5の設定
				dpkMST.setDPK_LVL_5(null);
				// 管理部門コードレベル6の設定
				dpkMST.setDPK_LVL_6(null);
				// 管理部門コードレベル7の設定
				dpkMST.setDPK_LVL_7(null);
				// 管理部門コードレベル8の設定
				dpkMST.setDPK_LVL_8(null);
				// 管理部門コードレベル9の設定
				dpkMST.setDPK_LVL_9(null);

				// Date strDate = null;
				// Date endDate = null;
				// strDate = Util
				// .convertToDate(req.getParameter("strDate"));
				// endDate = Util
				// .convertToDate(req.getParameter("endDate"));
				// dpkMST.setSTR_DATE(strDate);
				// dpkMST.setEND_DATE(endDate);
				// プログラムIDの設定
				dpkMST.setPRG_ID(req.getParameter("prgID"));
				try {
					// 結果の取得
					logic.insert(dpkMST);
					// 結果の設定
					super.dispatchResultSuccess(req, resp);
				} catch (TException ex) {
					// 正常に処理されませんでした
					super.dispatchError(req, resp, ex.getMessage(), ex.getMessageArgs());
				}
			}
			// checkSskCode
			else if ("checksskcode".equals(flag)) {
				// 会社コードの取得
				String kaiCode = req.getParameter("kaiCode");
				// 組織コードの取得
				String dpkSsk = req.getParameter("dpkSsk");
				// mapの初期化
				Map conditions = new HashMap();
				// 会社コードの設定
				conditions.put("kaiCode", kaiCode);
				// 組織コードの設定
				conditions.put("dpkSsk", dpkSsk);
				// 結果の取得
				List list = logic.find(conditions);
				// データ集の初期化
				List result = new ArrayList(1);
				if (list != null && list.size() > 0) {
					// stoの初期化
					StringTransferObject sto = new StringTransferObject();
					// stoの設定
					sto.setValue("true");
					// 結果の追加
					result.add(sto);
				}
				// 結果の設定
				super.dispatchResultListObject(req, resp, result);
			} else if ("deleteorganization".equals(flag)) {
				// 会社コードの取得
				String kaiCode = req.getParameter("kaiCode");
				// 組織コードの取得
				String dpkSsk = req.getParameter("dpkSsk");
				// mapの初期化
				Map conditions = new HashMap();
				// 会社コードの設定
				conditions.put("kaiCode", kaiCode);
				// 組織コードの設定
				conditions.put("dpkSsk", dpkSsk);
				// 結果の取得
				List list = logic.find(conditions);
				if (list != null) {
					// 結果の内容を取得する
					Iterator ite = list.iterator();
					while (ite.hasNext()) {
						// entityの初期化
						DPK_MST entity = (DPK_MST) ite.next();
						// データを削除する
						logic.delete(entity);
					}
				}
				// 結果の設定
				super.dispatchResultSuccess(req, resp);
			}
			// "save"
			else if ("savessk".equals(flag)) {
				// validの初期化
				boolean valid = true;
				// データの取得
				String[] data = req.getParameterValues("data");
				if (data.length > 0) {
					// データの取得
					String[] record = StringUtil.toArrayFromHTMLEscapeString(data[0]);
					// 会社コードの取得
					String kaiCode = record[0];
					// 組織コードの取得
					String dpkSsk = record[1];
					// mapの初期化
					Map conditions = new HashMap();
					// 会社コードの設定
					conditions.put("kaiCode", kaiCode);
					// 組織コードの設定
					conditions.put("dpkSsk", dpkSsk);
					// 結果の取得
					List list = logic.find(conditions);
					// 結果の内容を取得する
					Iterator ite = list.iterator();
					while (ite.hasNext()) {
						// entityの初期化
						DPK_MST entity = (DPK_MST) ite.next();
						if (entity.getDPK_LVL() != 0) {
							// データを削除する
							logic.delete(entity);
						} else {
							// the root department
							logic.update(entity);
						}
					}
				}
				for (int i = 1; i < data.length; i++) {
					// データの取得
					String[] record = StringUtil.toArrayFromHTMLEscapeString(data[i]);
					// 実体の初期化
					DPK_MST dpkMST = new DPK_MST();
					// 会社コードの設定
					dpkMST.setKAI_CODE(record[0]);
					// 組織コードの設定
					dpkMST.setDPK_SSK(record[1]);
					// 部門コードの設定
					dpkMST.setDPK_DEP_CODE(record[2]);
					// レベルの取得
					int dpkLvl = Integer.parseInt(record[3]);
					// レベルの設定
					dpkMST.setDPK_LVL(dpkLvl);
					// 管理部門コードレベル０の設定
					dpkMST.setDPK_LVL_0(record[4]);
					// 管理部門コードレベル1の設定
					dpkMST.setDPK_LVL_1(record[5]);
					// 管理部門コードレベル2の設定
					dpkMST.setDPK_LVL_2(record[6]);
					// 管理部門コードレベル3の設定
					dpkMST.setDPK_LVL_3(record[7]);
					// 管理部門コードレベル4の設定
					dpkMST.setDPK_LVL_4(record[8]);
					// 管理部門コードレベル5の設定
					dpkMST.setDPK_LVL_5(record[9]);
					// 管理部門コードレベル6の設定
					dpkMST.setDPK_LVL_6(record[10]);
					// 管理部門コードレベル7の設定
					dpkMST.setDPK_LVL_7(record[11]);
					// 管理部門コードレベル8の設定
					dpkMST.setDPK_LVL_8(record[12]);
					// 管理部門コードレベル9の設定
					dpkMST.setDPK_LVL_9(record[13]);

					// Date strDate = null;
					// Date endDate = null;
					// strDate = Util
					// .convertToDate(record[14]);
					// endDate = Util
					// .convertToDate(record[15]);
					// dpkMST.setSTR_DATE(strDate);
					// dpkMST.setEND_DATE(endDate);
					// プログラムIDの設定
					dpkMST.setPRG_ID(record[18]);
					// ユーザIDの設定
					dpkMST.setUSR_ID(record[19]);
					try {
						// データを登録する
						logic.insert(dpkMST);
					} catch (TException ex) {
						// 結果の設定
						super.dispatchError(req, resp, ex.getMessage(), ex.getMessageArgs());
						// validの設定
						valid = false;
						// 処理を終了する
						break;
					}
				}
				// 結果の設定
				if (valid) super.dispatchResultSuccess(req, resp);
				// 上位部門レベル検索
			} else if ("refHierarchy".equals(flag)) {
				searchRefHierarchy(req, resp);
			}
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new DepartmentHierarchyMasterReportExcelDefine();
	}

	/**
	 * 部門レベル検索
	 * 
	 * @param req
	 * @param resp
	 */
	protected void searchRefHierarchy(HttpServletRequest req, HttpServletResponse resp) {
		// containerの初期化
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logicの初期化
			DepartmentHierarchyLogic logic = (DepartmentHierarchyLogic) container
				.getComponent(DepartmentHierarchyLogic.class);
			// keysの初期化
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("dpkSsk", req.getParameter("dpkSsk"));
			keys.put("dpkDepCode", req.getParameter("dpkDepCode"));

			// 実体の初期化
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

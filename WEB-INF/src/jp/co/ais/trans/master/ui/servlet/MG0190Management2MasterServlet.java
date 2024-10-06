package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
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
import jp.co.ais.trans2.model.check.*;

/**
 * 管理2マスタ画面Servlet (MG0190)
 * 
 * @author ISFnet China
 */
public class MG0190Management2MasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = -4395497894914514285L;

	@Override
	protected String getLogicClassName() {
		return "Management2Logic";
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
		// 管理コードの設定
		map.put("knrCode", req.getParameter("knrCode"));
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
		map.put("beginknrCode", req.getParameter("beginknrCode"));
		// 終了コードの設定
		map.put("endknrCode", req.getParameter("endknrCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		KNR2_MST knr2MST = new KNR2_MST();
		// 会社コードの設定
		knr2MST.setKAI_CODE(req.getParameter("kaiCode"));
		// 管理２ｺｰﾄﾞの設定
		knr2MST.setKNR_CODE_2(req.getParameter("knrCode"));
		// 管理２名称の設定
		knr2MST.setKNR_NAME_2(req.getParameter("knrName"));
		// 管理２略称の設定
		knr2MST.setKNR_NAME_S_2(req.getParameter("knrName_S"));
		// 管理２検索名称の設定
		knr2MST.setKNR_NAME_K_2(req.getParameter("knrName_K"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		knr2MST.setSTR_DATE(strDate);
		// 終了年月日の設定
		knr2MST.setEND_DATE(endDate);
		// プログラムIDの設定
		knr2MST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return knr2MST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new Management2MasterReportExcelDefine();
	}

	/**
	 * 削除
	 * 
	 * @param req
	 */
	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_2);
		condition.setCompanyCode(req.getParameter("kaiCode"));
		condition.setManagement2Code(req.getParameter("knrCode"));

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) container.getComponent(CheckMasterUseDao.class);
		try {
			cd.check(condition);
		} catch (TException e) {
			throw new TRuntimeException(e.getMessage());
		}

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = this.getPrimaryKeys(req);
		logic.delete(keys);

		super.dispatchResultSuccess(req, resp);
	}
}

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
 * 管理3マスタ画面Servlet (MG0200)
 * 
 * @author ISFnet China
 */
public class MG0200Management3MasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 1964208912613374507L;

	@Override
	protected String getLogicClassName() {
		return "Management3Logic";
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
		KNR3_MST knr3MST = new KNR3_MST();
		// 会社コードの設定
		knr3MST.setKAI_CODE(req.getParameter("kaiCode"));
		// 管理３ｺｰﾄﾞの設定
		knr3MST.setKNR_CODE_3(req.getParameter("knrCode"));
		// 管理３名称の設定
		knr3MST.setKNR_NAME_3(req.getParameter("knrName"));
		// 管理３略称の設定
		knr3MST.setKNR_NAME_S_3(req.getParameter("knrName_S"));
		// 管理３検索名称の設定
		knr3MST.setKNR_NAME_K_3(req.getParameter("knrName_K"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		knr3MST.setSTR_DATE(strDate);
		// 終了年月日の設定
		knr3MST.setEND_DATE(endDate);
		// プログラムIDの設定
		knr3MST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return knr3MST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new Management3MasterReportExcelDefine();
	}

	/**
	 * 削除
	 * 
	 * @param req
	 */
	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_3);
		condition.setCompanyCode(req.getParameter("kaiCode"));
		condition.setManagement3Code(req.getParameter("knrCode"));

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

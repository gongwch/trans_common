package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * プログラムマスタ画面Servlet (MG0240)
 * 
 * @author ISFnet China
 */
public class MG0240ProgramMasterServlet extends MasterServletBase {

	@Override
	protected String getLogicClassName() {
		return "ProgramLogic";
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
		// システムコードの設定
		map.put("sysCode", req.getParameter("sysCode"));
		// プログラムコードの設定
		map.put("prgCode", req.getParameter("prgCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("kaiCode", req.getParameter("kaiCode"));
		// システムコードの設定
		map.put("sysCode", req.getParameter("sysCode"));
		// 開始コードの設定
		map.put("beginPrgCode", req.getParameter("beginPrgCode"));
		// 終了コードの設定
		map.put("endPrgCode", req.getParameter("endPrgCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		PRG_MST prgMST = new PRG_MST();
		// 会社コードの設定
		prgMST.setKAI_CODE(req.getParameter("kaiCode"));
		// プログラムコード
		prgMST.setPRG_CODE(req.getParameter("prgCode"));
		// プログラム名称
		prgMST.setPRG_NAME(req.getParameter("prgName"));
		// プログラム略称
		prgMST.setPRG_NAME_S(req.getParameter("prgName_S"));
		// プログラム検索名称
		prgMST.setPRG_NAME_K(req.getParameter("prgName_K"));
		// システムコード
		prgMST.setSYS_CODE(req.getParameter("sysCode"));
		// 権限レベル
		String ken1 = req.getParameter("ken");

		if (!Util.isNullOrEmpty(ken1)) {
			// 権限レベル
			prgMST.setKEN(new Integer(ken1));
		}

		// コメント
		prgMST.setCOM(req.getParameter("com"));
		// ﾛｰﾄﾞﾓｼﾞｭｰﾙﾌｧｲﾙ名
		prgMST.setLD_NAME(req.getParameter("ldName"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		prgMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		prgMST.setEND_DATE(endDate);
		// 親プログラムコード
		prgMST.setPARENT_PRG_CODE(req.getParameter("parentPrgCode"));
		// メニュー区分の取得
		String menuKbn1 = req.getParameter("menuKbn");
		if (!(menuKbn1 == null || menuKbn1.trim().length() == 0)) {
			// メニュー区分の初期化
			int menuKbn = Integer.parseInt(menuKbn1);
			// メニュー区分の設定
			prgMST.setMENU_KBN(menuKbn);
		} else {
			// メニュー区分の初期化
			int temp = 0;
			// メニュー区分の設定
			prgMST.setMENU_KBN(temp);
		}

		// 表示順序の設定
		String dispIndex = req.getParameter("dispIndex");
		if (!(dispIndex == null || dispIndex.trim().length() == 0)) {
			prgMST.setDISP_INDEX(Integer.parseInt(dispIndex));
		} else {
			// 表示順序の初期化
			int temp = 0;
			// 表示順序の設定
			prgMST.setDISP_INDEX(temp);
		}

		// プログラムIDの設定
		prgMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return prgMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new ProgramMasterReportExcelDefine();
	}

	/**
	 * その他の画面個別のロジック
	 * 
	 * @param req
	 * @param resp
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {

		String flag = req.getParameter("flag");

		// ユーザ検索
		if ("searchPrgList".equals(flag)) {
			searchPrg(req, resp);
		}
	}

	/**
	 * 会社別ユーザリスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchPrg(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
			TUserInfo userInfo = refTServerUserInfo(req);

			// mapの初期化(会社コードをセット）
			Map map = new HashMap();
			map.put("kaiCode", userInfo.getCompanyCode());

			// 会社コードでユーザリストを習得
			List dtoList;

			dtoList = logic.find(map);

			super.dispatchResultDtoList(req, resp, dtoList);
		} catch (ParseException e) {
			ServerErrorHandler.handledException(e);
		}

	}
}

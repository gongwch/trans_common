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
 * 銀行マスタ画面Servlet (MG0140)
 * 
 * @author ISFnet China
 */
public class MG0140BankMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 7691999673652241786L;

	@Override
	protected String getLogicClassName() {
		return "BankLogic";
	}

	protected String getREFKeyFields() {
		return "bnkCode";
	}

	/** 主キーの取得 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 会社コードの設定
		map.put("bnkCode", req.getParameter("bnkCode"));
		// 銀行支店コードの設定
		map.put("bnkStnCode", req.getParameter("bnkStnCode"));
		// 結果を返す
		return map;
	}

	/** 検索条件の取得 */
	protected Map getFindConditions(HttpServletRequest req) {
		// mapの初期化
		Map map = new HashMap();
		// 銀行コードの設定
		map.put("bnkCode", req.getParameter("bnkCode"));
		// 開始コードの設定
		map.put("beginBnkStnCode", req.getParameter("beginBnkStnCode"));
		// 終了コードの設定
		map.put("endBnkStnCode", req.getParameter("endBnkStnCode"));
		// 結果を返す
		return map;
	}

	/** エンティティの取得 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// 実体の初期化
		BNK_MST bnkMST = new BNK_MST();
		// 銀行コードの設定
		bnkMST.setBNK_CODE(req.getParameter("bnkCode"));
		// 銀行支店コードの設定
		bnkMST.setBNK_STN_CODE(req.getParameter("bnkStnCode"));
		// 銀行名の設定
		bnkMST.setBNK_NAME_S(req.getParameter("bnkName_S"));
		// 銀行カナ名称の設定
		bnkMST.setBNK_KANA(req.getParameter("bnkKana"));
		// 銀行検索名称の設定
		bnkMST.setBNK_NAME_K(req.getParameter("bnkName_K"));
		// 銀行支店名の設定
		bnkMST.setBNK_STN_NAME_S(req.getParameter("bnkStnName_S"));
		// 銀行支店カナ名称の設定
		bnkMST.setBNK_STN_KANA(req.getParameter("bnkStnKana"));
		// 銀行支店検索名称の設定
		bnkMST.setBNK_STN_NAME_K(req.getParameter("bnkStnName_K"));
		// 開始年月日の初期化
		Date strDate = null;
		// 終了年月日の初期化
		Date endDate = null;
		// 開始年月日の取得
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// 終了年月日の取得
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// 開始年月日の設定
		bnkMST.setSTR_DATE(strDate);
		// 終了年月日の設定
		bnkMST.setEND_DATE(endDate);
		// プログラムIDの設定
		bnkMST.setPRG_ID(req.getParameter("prgID"));
		// 結果を返す
		return bnkMST;
	}

	/** リスト出力のExcel定義 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excelを返す
		return new BankMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// 判定用flag
		String flag = req.getParameter("flag");
		// 銀行名
		if ("nameBank".equals(flag)) {
			searchNameBnk(req, resp);
			// 銀行支店名
		} else if ("nameBnkStn".equals(flag)) {
			searchNameBnkStn(req, resp);

		}
	}

	/**
	 * 銀行名検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchNameBnk(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

			BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

			dispatchResultObject(req, resp, logic.getBankName(param));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * 銀行支店検索
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchNameBnkStn(HttpServletRequest req, HttpServletResponse resp) {

		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

			BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

			dispatchResultObject(req, resp, logic.getStnName(param));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * 実行ログ参照サーブレット
 */
public class MG0028ExecutedLogServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -3190789785708056184L;

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			String flag = req.getParameter("flag");

			if ("findLog".equals(flag)) {
				// ログ一覧取得
				dispatchLogDataList(req, resp);

			} else if ("report".equals(flag)) {
				// レポート出力
				getExcelDataList(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

	/**
	 * 実行ログ取得
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ParseException
	 */
	private void dispatchLogDataList(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		super.dispatchResultDtoList(req, resp, getLogDataList(req));
	}

	/**
	 * 実行ログのエクセル出力
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 * @throws ParseException
	 * @throws IOException
	 * @throws TException
	 */
	private void getExcelDataList(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException,
		TException {

		// 実行ログリスト
		List dtoList = getLogDataList(req);

		if (dtoList == null || dtoList.isEmpty()) {
			// データがありません。
			throw new TException("W00100");
		}

		if (dtoList.size() >= 65535) {
			throw new TException("W00213");
		}

		TUserInfo userInfo = refTServerUserInfo(req);

		// エクセルフォマット取得
		ReportExcelDefine red = new ExecutedLogReportExcelDefine(userInfo.getUserLanguage());
		red.setKaiCode(userInfo.getCompanyCode()); // 会社コード
		red.setLangCode(userInfo.getUserLanguage()); // 言語コード

		// エクセル形式に変更
		OutputUtil util = new OutputUtil(red, userInfo.getUserLanguage());
		util.createExcel(dtoList);

		super.dispatchExcel(req, resp, red.getFileName(), util.getBinary());
	}

	/**
	 * 検索条件でログリストを取得
	 * 
	 * @param req
	 * @return ログリスト
	 * @throws ParseException
	 */
	private List getLogDataList(HttpServletRequest req) throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		ExecutedLogLogic logic = (ExecutedLogLogic) container.getComponent("ExecutedLogLogic");
		TUserInfo userInfo = refTServerUserInfo(req);

		// ロジック側に渡すパラメータを設定
		logic.setCompanyCode(userInfo.getCompanyCode());
		logic.setStartDate(DateUtil.toYMDDate(req.getParameter("startDate")));
		logic.setEndDate(DateUtil.toYMDDate(req.getParameter("endDate")));
		logic.setStartUserCode(req.getParameter("startUser"));
		logic.setEndUserCode(req.getParameter("endUser"));
		logic.setStartProgramCode(req.getParameter("startPrg"));
		logic.setEndProgramCode(req.getParameter("endPrg"));
		logic.setLangCode(userInfo.getUserLanguage());

		if (BooleanUtil.toBoolean(req.getParameter("loginFlag"))) {
			logic.onLogin();
		} else {
			logic.offLogin();
		}

		int sort = Integer.parseInt(req.getParameter("sort"));

		logic.setSort(sort);

		// 実行ログリスト取得
		return logic.getExecutedLogList();
	}

}

package jp.co.ais.trans.common.server.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 拡張スプレッドのservlet
 * 
 * @author Yanwei
 */
public class TEnhanceTableServlet extends TServletBase {

	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String status = req.getParameter("flag");

			if ("getWidths".equals(status)) {
				getWidths(req, resp);
			} else if ("saveColumnWidths".equals(status)) {
				saveColumnWidths(req, resp);
			}

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * ユーザー設定幅情報を取得する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void getWidths(HttpServletRequest req, HttpServletResponse resp) {
		// 会社コード
		String kaiCode = refTServerUserInfo(req).getCompanyCode();
		// ユーザーコード
		String userID = refTServerUserInfo(req).getUserCode();
		// プログラムID
		String prgID = req.getParameter("programId");

		// ユーザー設定幅情報を取得する
		S2Container container = SingletonS2ContainerFactory.getContainer();
		COL_SLT_MSTDao colSltDao = (COL_SLT_MSTDao) container.getComponent(COL_SLT_MSTDao.class);
		COL_SLT_MST bean = colSltDao.getCOL_SLT_MSTByKaicodeUsridPgrid(kaiCode, userID, prgID);

		dispatchResultDto(req, resp, bean);
	}

	/**
	 * ユーザー設定幅情報を取得する
	 * 
	 * @param req リクエスト
	 * @param resp レスポンス
	 */
	private void saveColumnWidths(HttpServletRequest req, HttpServletResponse resp) {
		// スプレッドシート制御マスタ
		COL_SLT_MST bean = (COL_SLT_MST) this.getDtoParameter(req, COL_SLT_MST.class);

		String kaiCode = refTServerUserInfo(req).getCompanyCode();// 会社コード
		String userID = refTServerUserInfo(req).getUserCode();// ユーザーコード
		String prgID = bean.getPRG_ID();// プログラムID
		bean.setUSR_ID(userID);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		COL_SLT_MSTDao colSltDao = (COL_SLT_MSTDao) container.getComponent(COL_SLT_MSTDao.class);

		// カラム幅を保存する
		COL_SLT_MST colMstT = colSltDao.getCOL_SLT_MSTByKaicodeUsridPgrid(kaiCode, userID, prgID);
		if (colMstT == null) {
			colSltDao.insert(bean);// データが存在しない場合
		} else {
			colSltDao.update(bean);// データが存在する場合
		}

		dispatchResultSuccess(req, resp);
	}

}

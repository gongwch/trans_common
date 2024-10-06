package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * バッチ排他強制解除マスタ画面Servlet
 * 
 * @author 細谷（ais_y)
 */
public class MG0380BatchUnLockServlet extends TServletBase {

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String flag = req.getParameter("flag");

			// 検索
			if ("find".equals(flag)) {
				find(req, resp);

				// 削除
			} else if ("delete".equals(flag)) {
				delete(req, resp);

				// プログラムリスト取得
			} else if ("getProgram".equals(flag)) {
				getProgramList(req, resp);

				// ユーザリスト取得
			} else if ("getUser".equals(flag)) {
				getUserList(req, resp);
			}
		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * 該当会社の排他状態のバッチリスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		BatchUnLockLogic logic = (BatchUnLockLogic) container.getComponent(BatchUnLockLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// バッチ排他情報リスト取得
		List<BAT_CTL> list = logic.getBatchListByCompanyCode(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, list);

	}

	/**
	 * プログラムリスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void getProgramList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ProgramLogic logic = (ProgramLogic) container.getComponent(ProgramLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// プログラムリスト取得
		List<PRG_MST> programlist = logic.searchProgramList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, programlist);
	}

	/**
	 * ユーザリスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// ユーザリスト取得
		List<USR_MST> usrlist = logic.searchUsrList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, usrlist);
	}

	/**
	 * バッチリストに該当する排他データを削除
	 * 
	 * @param req
	 * @param resp
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		BatchUnLockLogic logic = (BatchUnLockLogic) container.getComponent(BatchUnLockLogic.class);

		// バッチリストに該当する排他データを削除
		List<BAT_CTL> list = (List<BAT_CTL>) getObjectParameter(req);
		logic.delete(list);
		dispatchResultSuccess(req, resp);

	}

}

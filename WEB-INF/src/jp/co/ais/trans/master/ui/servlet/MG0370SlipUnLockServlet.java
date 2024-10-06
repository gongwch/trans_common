package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;

import jp.co.ais.trans.logic.system.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 伝票排他強制解除マスタ画面Servlet
 * 
 * @author 盧（ais_y)
 */
public class MG0370SlipUnLockServlet extends TServletBase {

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

				// ユーザリスト取得
			} else if ("getUser".equals(flag)) {
				getUserList(req, resp);

				// システムリスト取得
			} else if ("getSystem".equals(flag)) {
				getSystemList(req, resp);
			}
		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * 該当会社の排他状態の伝票リスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);
		TUserInfo userInfo = refTServerUserInfo(req);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// 伝票排他情報リスト取得
		List<SlipUnlockObject> list = logic.getExclusiveSlip();
		super.dispatchResultObject(req, resp, list);
	}

	/**
	 * ユーザリストに該当する排他データを削除
	 * 
	 * @param req
	 * @param resp
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		Lock logic = (Lock) container.getComponent(Lock.class);
		TUserInfo userInfo = refTServerUserInfo(req);
		List<SlipUnlockObject> list = (List<SlipUnlockObject>) getObjectParameter(req);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());
		logic.unlockSlip(list);
		dispatchResultSuccess(req, resp);
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
	 * システム区分リスト取得
	 * 
	 * @param req
	 * @param resp
	 */
	private void getSystemList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		SystemDivisionLogic logic = (SystemDivisionLogic) container.getComponent(SystemDivisionLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// システムリスト取得
		List<SYS_MST> systemList = logic.getSystemList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, systemList);
	}
}

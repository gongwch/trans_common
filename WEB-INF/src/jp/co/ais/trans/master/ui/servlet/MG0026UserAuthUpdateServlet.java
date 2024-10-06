package jp.co.ais.trans.master.ui.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * ユーザ承認管理サーブレット
 */
public class MG0026UserAuthUpdateServlet extends TServletBase {

	/**
	 * ロジック名取得
	 * 
	 * @return ロジック名
	 */
	protected String getLogicClassName() {
		return "UserAuthLogic";
	}

	/** UID */
	private static final long serialVersionUID = 4838788441767153309L;

	/**
	 * 会社の認証設定を習得
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchOneRow(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserAuthLogic logic = (UserAuthLogic) container.getComponent(getLogicClassName());

		// 会社コード習得
		String kaiCode = refTServerUserInfo(req).getCompanyCode();
		USR_AUTH_MST usrAuth = logic.find(kaiCode);
		super.dispatchResultDto(req, resp, usrAuth);
	}

	/**
	 * 更新及び登録
	 * 
	 * @param req
	 * @param resp
	 */
	private void updateAuthDto(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserAuthLogic logic = (UserAuthLogic) container.getComponent(getLogicClassName());
		TUserInfo userInfo = refTServerUserInfo(req);

		// 会社コード登録
		String kaiCode = userInfo.getCompanyCode();

		// 更新ユーザコード登録
		String userCode = Util.avoidNull(userInfo.getUserCode());

		// パラメータの習得
		USR_AUTH_MST usrAuth = (USR_AUTH_MST) getDtoParameter(req, USR_AUTH_MST.class);
		usrAuth.setKAI_CODE(kaiCode);
		usrAuth.setUSR_ID(userCode);

		// 更新
		logic.update(usrAuth);

		// 操作成功
		super.dispatchResultSuccess(req, resp);

	}

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String flag = req.getParameter("flag");
		try {

			// 認証設定取得
			if ("findByKaiCode".equals(flag)) {
				searchOneRow(req, resp);

				// 認証情報更新
			} else if ("updateAuthDto".equals(flag)) {
				updateAuthDto(req, resp);
			}
		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

}

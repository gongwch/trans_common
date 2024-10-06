package jp.co.ais.trans.common.server.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * サーバ側にプログラム開始、終了ログを通知する
 */
public class ProgramServlet extends TServletBase {

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String status = req.getParameter("status");

			// 終了であれば、排他解除
			if ("endPrg".equals(status)) {
				unLockPrg(req);
			} else if ("end".equals(status)) {
				unLockAll(req);
			}

			// ログ出力
			if ("start".equals(status)) {
				log(req, true);
			} else {
				log(req, false);
			}

			// 正常終了
			super.dispatchResultSuccess(req, resp);

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * ログ出力
	 * 
	 * @param req
	 * @param isStart
	 */
	private void log(HttpServletRequest req, boolean isStart) {

		TUserInfo userInfo = refTServerUserInfo(req);

		if (userInfo == null) {
			return;
		}

		// ログロジックにパラメータを渡す
		String userCode = userInfo.getUserCode();
		String userName = userInfo.getUserName();
		String prgCode = Util.avoidNull(req.getParameter("prgCode"));
		String status = isStart ? ExecutedLogger.IN : ExecutedLogger.OUT;
		String ip = Util.avoidNull(req.getSession().getAttribute(TServerEnv.SYS_PREFIX + "ipadd"));

		// ファイル登録
		ExecutedLogger logger = ExecutedLogger.getInstance(userInfo.getCompanyCode());

		logger.log(userCode, userName, ip, prgCode, status);
	}

	/**
	 * 一括排他解除
	 * 
	 * @param req
	 */
	private void unLockAll(HttpServletRequest req) {

		TUserInfo userInfo = refTServerUserInfo(req);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);

		// 会社コード, ユーザコード
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// 機能排他 強制解除
		logic.unlockAll();
	}

	/**
	 * 個別排他解除（プログラム指定で排他を解除する）
	 * 
	 * @param req
	 */
	private void unLockPrg(HttpServletRequest req) {

		TUserInfo userInfo = refTServerUserInfo(req);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);

		// 会社コード, ユーザコード, プログラムコード
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode(), req.getParameter("prgCode"));

		// バッチ, コード 個別排他解除
		logic.unlockPrg();

	}
}

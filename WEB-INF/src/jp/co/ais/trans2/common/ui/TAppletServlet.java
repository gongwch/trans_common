package jp.co.ais.trans2.common.ui;

import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.server.servlet.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;

/**
 * ログインのサーブレット
 * 
 * @author AIS
 */
public class TAppletServlet extends TLoginServlet {

	/**
	 * WEBアクセス時セッション情報を確立する.
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// イベントハンドリング
			String methodName = Util.avoidNull(req.getParameter("methodName"));

			if ("authenticateUser".equals(methodName)) {
				List<Object> arg = (List<Object>) super.getObjectParameter(req, "methodArg");

				String compCode = (String) arg.get(0);
				String userCode = (String) arg.get(1);
				String password = (String) arg.get(2);

				// ユーザ認証
				authenticateUser(compCode, userCode, password);

				// ログイン証明を元に、セッション証明を構築する
				String sessionID = initSession(req, new LoginCertification(compCode, userCode));

				// ログインユーザ情報構築(TRANS1.0用)
				S2Container container = SingletonS2ContainerFactory.getContainer();
				jp.co.ais.trans.logic.system.UserManager logic = (jp.co.ais.trans.logic.system.UserManager) container
					.getComponent("old-UserManager");
				logic.setCompanyCode(compCode);
				logic.setUserCode(userCode);

				// ログイン規定数チェック
				if (ServerConfig.getRegulatedNumber() > 0) {

					PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) container.getComponent(PCI_CHK_CTLDao.class);
					int currentCount = pciDao.getCount(compCode);

					// デバッグログ
					ServerLogger.debug("regulated number:" + ServerConfig.getRegulatedNumber() + "、current:"
						+ currentCount);

					if (currentCount >= ServerConfig.getRegulatedNumber()) {
						throw new TException("W01005"); // 規定数オーバー
					}
				}

				req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "userinfo", logic.makeUserInfo());

				// ログイン処理なし

				// ログイン処理終了ログなし

				dispatchResultObject(req, resp, sessionID);
			}

		} catch (Exception e) {
			dispatchError(req, resp, e);
		}
	}

}

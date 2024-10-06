package jp.co.ais.trans.common.server;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * セッションイベントを受け取る為のリスナ.<br>
 * セッション生成時やタイムアウト時に顧客毎のニーズがあれば、このクラスを拡張してweb.xmlに登録する.
 */
public class TSessionListener implements HttpSessionListener {

	/**
	 * セッション生成時
	 */
	public void sessionCreated(HttpSessionEvent evt) {
		// 処理無し
	}

	/**
	 * セッションタイムアウト時.<br>
	 * ログアウト処理を行う
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();

		if (session != null) {
			// セッションタイムアウトでログアウトされていない場合、ログアウト処理を行う

			String kaiCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ccd");
			String usrCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ucd");

			Boolean isLogout = (Boolean) session.getAttribute(TServerEnv.SYS_PREFIX + "isLogout");
			if (Util.isNullOrEmpty(kaiCode) || Util.isNullOrEmpty(usrCode)
				|| (!Util.isNullOrEmpty(isLogout) && isLogout)) {
				return;
			}

			ServerLogger.info("timeout logout:" + session.getId() + ", " + kaiCode + ", " + usrCode);

			S2Container container = SingletonS2ContainerFactory.getContainer();
			UserManager logic = (UserManager) container.getComponent("old-UserManager");
			logic.setCompanyCode(kaiCode);
			logic.setUserCode(usrCode);

			logic.logout();
		}
	}
}

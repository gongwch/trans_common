package jp.co.ais.trans.common.server.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.logic.system.*;

/**
 * common：パスワード変更Servlet
 */
public class TChangePasswordServlet extends TServletBase {

	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			TUserInfo info = refTServerUserInfo(req);

			String compCode = info.getCompanyCode(); // 会社コード
			String userCode = info.getUserCode(); // ユーザID
			String password = req.getParameter("newPassword"); // パスワード
			String prgID = req.getParameter("prgID"); // プログラムID

			S2Container container = SingletonS2ContainerFactory.getContainer();
			UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
			logic.setCode(compCode, userCode);

			// パスワード管理する場合は、各チェックを行う
			boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(password);

			if (!assertPwd) {
				dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
				return;
			}

			// チェック不備なし→更新(履歴も含む)
			logic.update(password, prgID);

			super.dispatchResultSuccess(req, resp);

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

}

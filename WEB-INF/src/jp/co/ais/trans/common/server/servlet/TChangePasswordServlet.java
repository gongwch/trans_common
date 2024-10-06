package jp.co.ais.trans.common.server.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.logic.system.*;

/**
 * common�F�p�X���[�h�ύXServlet
 */
public class TChangePasswordServlet extends TServletBase {

	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			TUserInfo info = refTServerUserInfo(req);

			String compCode = info.getCompanyCode(); // ��ЃR�[�h
			String userCode = info.getUserCode(); // ���[�UID
			String password = req.getParameter("newPassword"); // �p�X���[�h
			String prgID = req.getParameter("prgID"); // �v���O����ID

			S2Container container = SingletonS2ContainerFactory.getContainer();
			UserPassword logic = (UserPassword) container.getComponent(UserPassword.class);
			logic.setCode(compCode, userCode);

			// �p�X���[�h�Ǘ�����ꍇ�́A�e�`�F�b�N���s��
			boolean assertPwd = !logic.isPasswordManaged() || logic.assertPassword(password);

			if (!assertPwd) {
				dispatchError(req, resp, logic.getErrorMessageId(), logic.getErrorArgs());
				return;
			}

			// �`�F�b�N�s���Ȃ����X�V(�������܂�)
			logic.update(password, prgID);

			super.dispatchResultSuccess(req, resp);

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

}

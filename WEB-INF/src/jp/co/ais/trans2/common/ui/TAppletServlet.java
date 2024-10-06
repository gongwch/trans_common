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
 * ���O�C���̃T�[�u���b�g
 * 
 * @author AIS
 */
public class TAppletServlet extends TLoginServlet {

	/**
	 * WEB�A�N�Z�X���Z�b�V���������m������.
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// �C�x���g�n���h�����O
			String methodName = Util.avoidNull(req.getParameter("methodName"));

			if ("authenticateUser".equals(methodName)) {
				List<Object> arg = (List<Object>) super.getObjectParameter(req, "methodArg");

				String compCode = (String) arg.get(0);
				String userCode = (String) arg.get(1);
				String password = (String) arg.get(2);

				// ���[�U�F��
				authenticateUser(compCode, userCode, password);

				// ���O�C���ؖ������ɁA�Z�b�V�����ؖ����\�z����
				String sessionID = initSession(req, new LoginCertification(compCode, userCode));

				// ���O�C�����[�U���\�z(TRANS1.0�p)
				S2Container container = SingletonS2ContainerFactory.getContainer();
				jp.co.ais.trans.logic.system.UserManager logic = (jp.co.ais.trans.logic.system.UserManager) container
					.getComponent("old-UserManager");
				logic.setCompanyCode(compCode);
				logic.setUserCode(userCode);

				// ���O�C���K�萔�`�F�b�N
				if (ServerConfig.getRegulatedNumber() > 0) {

					PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) container.getComponent(PCI_CHK_CTLDao.class);
					int currentCount = pciDao.getCount(compCode);

					// �f�o�b�O���O
					ServerLogger.debug("regulated number:" + ServerConfig.getRegulatedNumber() + "�Acurrent:"
						+ currentCount);

					if (currentCount >= ServerConfig.getRegulatedNumber()) {
						throw new TException("W01005"); // �K�萔�I�[�o�[
					}
				}

				req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "userinfo", logic.makeUserInfo());

				// ���O�C�������Ȃ�

				// ���O�C�������I�����O�Ȃ�

				dispatchResultObject(req, resp, sessionID);
			}

		} catch (Exception e) {
			dispatchError(req, resp, e);
		}
	}

}

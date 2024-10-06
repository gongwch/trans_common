package jp.co.ais.trans.common.server;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * �Z�b�V�����C�x���g���󂯎��ׂ̃��X�i.<br>
 * �Z�b�V������������^�C���A�E�g���Ɍڋq���̃j�[�Y������΁A���̃N���X���g������web.xml�ɓo�^����.
 */
public class TSessionListener implements HttpSessionListener {

	/**
	 * �Z�b�V����������
	 */
	public void sessionCreated(HttpSessionEvent evt) {
		// ��������
	}

	/**
	 * �Z�b�V�����^�C���A�E�g��.<br>
	 * ���O�A�E�g�������s��
	 */
	public void sessionDestroyed(HttpSessionEvent evt) {
		HttpSession session = evt.getSession();

		if (session != null) {
			// �Z�b�V�����^�C���A�E�g�Ń��O�A�E�g����Ă��Ȃ��ꍇ�A���O�A�E�g�������s��

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

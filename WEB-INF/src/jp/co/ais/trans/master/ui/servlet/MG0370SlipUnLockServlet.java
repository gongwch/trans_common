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
 * �`�[�r�����������}�X�^���Servlet
 * 
 * @author ḁiais_y)
 */
public class MG0370SlipUnLockServlet extends TServletBase {

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String flag = req.getParameter("flag");

			// ����
			if ("find".equals(flag)) {
				find(req, resp);

				// �폜
			} else if ("delete".equals(flag)) {
				delete(req, resp);

				// ���[�U���X�g�擾
			} else if ("getUser".equals(flag)) {
				getUserList(req, resp);

				// �V�X�e�����X�g�擾
			} else if ("getSystem".equals(flag)) {
				getSystemList(req, resp);
			}
		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * �Y����Ђ̔r����Ԃ̓`�[���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);
		TUserInfo userInfo = refTServerUserInfo(req);
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// �`�[�r����񃊃X�g�擾
		List<SlipUnlockObject> list = logic.getExclusiveSlip();
		super.dispatchResultObject(req, resp, list);
	}

	/**
	 * ���[�U���X�g�ɊY������r���f�[�^���폜
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
	 * ���[�U���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void getUserList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserLogic logic = (UserLogic) container.getComponent(UserLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// ���[�U���X�g�擾
		List<USR_MST> usrlist = logic.searchUsrList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, usrlist);
	}

	/**
	 * �V�X�e���敪���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void getSystemList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		SystemDivisionLogic logic = (SystemDivisionLogic) container.getComponent(SystemDivisionLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// �V�X�e�����X�g�擾
		List<SYS_MST> systemList = logic.getSystemList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, systemList);
	}
}

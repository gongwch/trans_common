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
 * ���[�U���F�Ǘ��T�[�u���b�g
 */
public class MG0026UserAuthUpdateServlet extends TServletBase {

	/**
	 * ���W�b�N���擾
	 * 
	 * @return ���W�b�N��
	 */
	protected String getLogicClassName() {
		return "UserAuthLogic";
	}

	/** UID */
	private static final long serialVersionUID = 4838788441767153309L;

	/**
	 * ��Ђ̔F�ؐݒ���K��
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchOneRow(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserAuthLogic logic = (UserAuthLogic) container.getComponent(getLogicClassName());

		// ��ЃR�[�h�K��
		String kaiCode = refTServerUserInfo(req).getCompanyCode();
		USR_AUTH_MST usrAuth = logic.find(kaiCode);
		super.dispatchResultDto(req, resp, usrAuth);
	}

	/**
	 * �X�V�y�ѓo�^
	 * 
	 * @param req
	 * @param resp
	 */
	private void updateAuthDto(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserAuthLogic logic = (UserAuthLogic) container.getComponent(getLogicClassName());
		TUserInfo userInfo = refTServerUserInfo(req);

		// ��ЃR�[�h�o�^
		String kaiCode = userInfo.getCompanyCode();

		// �X�V���[�U�R�[�h�o�^
		String userCode = Util.avoidNull(userInfo.getUserCode());

		// �p�����[�^�̏K��
		USR_AUTH_MST usrAuth = (USR_AUTH_MST) getDtoParameter(req, USR_AUTH_MST.class);
		usrAuth.setKAI_CODE(kaiCode);
		usrAuth.setUSR_ID(userCode);

		// �X�V
		logic.update(usrAuth);

		// ���쐬��
		super.dispatchResultSuccess(req, resp);

	}

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		String flag = req.getParameter("flag");
		try {

			// �F�ؐݒ�擾
			if ("findByKaiCode".equals(flag)) {
				searchOneRow(req, resp);

				// �F�؏��X�V
			} else if ("updateAuthDto".equals(flag)) {
				updateAuthDto(req, resp);
			}
		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

}

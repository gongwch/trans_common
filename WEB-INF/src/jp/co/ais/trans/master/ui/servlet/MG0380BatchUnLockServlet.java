package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;

import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �o�b�`�r�����������}�X�^���Servlet
 * 
 * @author �גJ�iais_y)
 */
public class MG0380BatchUnLockServlet extends TServletBase {

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

				// �v���O�������X�g�擾
			} else if ("getProgram".equals(flag)) {
				getProgramList(req, resp);

				// ���[�U���X�g�擾
			} else if ("getUser".equals(flag)) {
				getUserList(req, resp);
			}
		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * �Y����Ђ̔r����Ԃ̃o�b�`���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		BatchUnLockLogic logic = (BatchUnLockLogic) container.getComponent(BatchUnLockLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// �o�b�`�r����񃊃X�g�擾
		List<BAT_CTL> list = logic.getBatchListByCompanyCode(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, list);

	}

	/**
	 * �v���O�������X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void getProgramList(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		ProgramLogic logic = (ProgramLogic) container.getComponent(ProgramLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// �v���O�������X�g�擾
		List<PRG_MST> programlist = logic.searchProgramList(userInfo.getCompanyCode());
		super.dispatchResultObject(req, resp, programlist);
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
	 * �o�b�`���X�g�ɊY������r���f�[�^���폜
	 * 
	 * @param req
	 * @param resp
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		BatchUnLockLogic logic = (BatchUnLockLogic) container.getComponent(BatchUnLockLogic.class);

		// �o�b�`���X�g�ɊY������r���f�[�^���폜
		List<BAT_CTL> list = (List<BAT_CTL>) getObjectParameter(req);
		logic.delete(list);
		dispatchResultSuccess(req, resp);

	}

}

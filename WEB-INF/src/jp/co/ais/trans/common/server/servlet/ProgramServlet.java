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
 * �T�[�o���Ƀv���O�����J�n�A�I�����O��ʒm����
 */
public class ProgramServlet extends TServletBase {

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String status = req.getParameter("status");

			// �I���ł���΁A�r������
			if ("endPrg".equals(status)) {
				unLockPrg(req);
			} else if ("end".equals(status)) {
				unLockAll(req);
			}

			// ���O�o��
			if ("start".equals(status)) {
				log(req, true);
			} else {
				log(req, false);
			}

			// ����I��
			super.dispatchResultSuccess(req, resp);

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * ���O�o��
	 * 
	 * @param req
	 * @param isStart
	 */
	private void log(HttpServletRequest req, boolean isStart) {

		TUserInfo userInfo = refTServerUserInfo(req);

		if (userInfo == null) {
			return;
		}

		// ���O���W�b�N�Ƀp�����[�^��n��
		String userCode = userInfo.getUserCode();
		String userName = userInfo.getUserName();
		String prgCode = Util.avoidNull(req.getParameter("prgCode"));
		String status = isStart ? ExecutedLogger.IN : ExecutedLogger.OUT;
		String ip = Util.avoidNull(req.getSession().getAttribute(TServerEnv.SYS_PREFIX + "ipadd"));

		// �t�@�C���o�^
		ExecutedLogger logger = ExecutedLogger.getInstance(userInfo.getCompanyCode());

		logger.log(userCode, userName, ip, prgCode, status);
	}

	/**
	 * �ꊇ�r������
	 * 
	 * @param req
	 */
	private void unLockAll(HttpServletRequest req) {

		TUserInfo userInfo = refTServerUserInfo(req);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);

		// ��ЃR�[�h, ���[�U�R�[�h
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode());

		// �@�\�r�� ��������
		logic.unlockAll();
	}

	/**
	 * �ʔr�������i�v���O�����w��Ŕr������������j
	 * 
	 * @param req
	 */
	private void unLockPrg(HttpServletRequest req) {

		TUserInfo userInfo = refTServerUserInfo(req);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		Lock logic = (Lock) container.getComponent(Lock.class);

		// ��ЃR�[�h, ���[�U�R�[�h, �v���O�����R�[�h
		logic.setCode(userInfo.getCompanyCode(), userInfo.getUserCode(), req.getParameter("prgCode"));

		// �o�b�`, �R�[�h �ʔr������
		logic.unlockPrg();

	}
}

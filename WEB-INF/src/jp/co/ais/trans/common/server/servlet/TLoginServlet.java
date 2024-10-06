package jp.co.ais.trans.common.server.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * ���O�C������ ���O�A�E�g����
 * 
 * @author AIS
 */
public final class TLoginServlet extends TServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 1543576010924868389L;

	/** �����p�X���[�h���� */
	protected static final int PWD_CHK = 4;

	/** �������O�C���i�F�؍ς݁j */
	protected static final int PWD_UNCHK = 5;

	/**
	 * Post method ����. <br>
	 * �Z�b�V�����F�؂��Ȃ�.
	 * 
	 * @param req
	 * @param resp
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) {

		super.doPost(req, resp, false);
	}

	/**
	 * doPost()���\�b�h��POST�`���ő��M���ꂽ�f�[�^������
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {

		try {
			String flag = Util.avoidNull(req.getParameter("flag"));

			if ("noticeLogin".equals(flag)) {
				// ���O�C�����O
				logLogin(req, true);
				dispatchResultSuccess(req, resp);
				return;
			}

			login(req, resp);

		} catch (TException e) {

			dispatchJspErrorForLink(req, resp, e.getMessageID(), e.getMessageArgs());
		} catch (TRuntimeException e) {

			ServerErrorHandler.handledException(e);
			dispatchJspErrorForLink(req, resp, e.getMessageID(), e.getMessageArgs());
		} catch (Exception e) {

			ServerErrorHandler.handledException(e);
			dispatchJspErrorForLink(req, resp, "E00009");
		}
	}

	/**
	 * ���O�C������
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws IOException
	 * @throws ServletException
	 * @throws TException
	 */
	protected void login(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException,
		TException {

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserManager logic = (UserManager) container.getComponent("old-UserManager");

		String flagStr = req.getParameter("flag");

		if (Util.isNullOrEmpty(flagStr) || !Util.isNumber(flagStr.trim())) {
			ServerLogger.warning("access failed. " + "/login?flag=" + flagStr);

			// ��񂪌����Ă���ꍇ�A�g�b�v�y�[�W�֑J��
			getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR.toString()).forward(req, resp);
			return;
		}

		// flag=0:���O�C�� flag=1:���O�A�E�g
		int flag = Integer.parseInt(flagStr.trim());

		if (flag == 0 || flag == 3 || flag == PWD_CHK || flag == PWD_UNCHK) {
			// ���O�C������ ���[�U�F�؃`�F�b�N�A���O�C�����̏����A�����r������

			String kaiCode = req.getParameter("kaiCode");
			String usrCode = req.getParameter("usrCode");
			String password = req.getParameter("password");

			logic.setCompanyCode(kaiCode);
			logic.setUserCode(usrCode);

			if (flag == PWD_CHK) {
				UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
				pwLogic.setCode(kaiCode, usrCode);
				req.setAttribute("kaiCode", kaiCode);
				req.setAttribute("usrCode", usrCode);
				req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
				req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
				req.setAttribute("label.pwd", getWord(req, "C00742"));
				req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

				// �p�X���[�h�L�������؂ꃁ�b�Z�[�W�ʒm�ŁuOK�v��I�����p�X���[�h�X�V��ʂɈړ�
				getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp").forward(req, resp);
				return;
			}

			if (flag != PWD_UNCHK) {
				if (flag == 0) {
					// ���O�C����ʂ��痈���ꍇ

					// ���[�U�F��(���݁A�p�X���[�h�A���b�N�A�E�g)
					if (!logic.collatedUser(password)) {

						// ���O�C���F�؎��s�A�Ή�����errorMsg�\��
						req.setAttribute("errorMsg", super.getMessage(req, logic.getErrorMsg()));

						// �G���[�t���g�b�v�y�[�W���J��
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "index.jsp").forward(req, resp);

						return;
					}

					UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
					pwLogic.setCode(kaiCode, usrCode);

					// �p�X���[�h�L�����Ԃ̃`�F�b�N(���Ԃ��߂����ꍇ�A�p�X���[�h�X�V��ʂ��J���j
					if (pwLogic.isPasswordManaged() && pwLogic.isPasswordTermOrver()) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("errorMsg", getMessage(req, "I00026")); // �p�X���[�h�L�����Ԃ��߂��Ă��܂�

						// ��ʕ\������
						req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
						req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
						req.setAttribute("label.pwd", getWord(req, "C00742"));
						req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

						// �p�X���[�h�X�V��ʂɈړ�
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp")
							.forward(req, resp);
						return;
					}

					// �p�X���[�h�L�������؂ꃁ�b�Z�[�W�ʒm�̃`�F�b�N
					if (!pwLogic.isPwdLimitMsgNotice()) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("termMsg", getMessage(req, "Q90003", pwLogic.getPwdLimitMsg()));

						// �p�X���[�h�L�������؂ꃁ�b�Z�[�W�ʒm�Łu�͂��v��I�����p�X���[�h�X�V��ʂɈړ�
						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "passwordmsg.jsp").forward(req,
							resp);
						return;

					}

				} else if (flag == 3) {
					// �p�X���[�h�ύX���痈���ꍇ
					UserPassword pwLogic = (UserPassword) container.getComponent(UserPassword.class);
					pwLogic.setCode(kaiCode, usrCode);

					// �p�X���[�h�Ǘ�����ꍇ�́A�e�`�F�b�N���s��
					boolean assertPwd = !pwLogic.isPasswordManaged() || pwLogic.assertPassword(password);

					if (!assertPwd) {
						req.setAttribute("kaiCode", kaiCode);
						req.setAttribute("usrCode", usrCode);
						req.setAttribute("errorMsg", getMessage(req, pwLogic.getErrorMessageId(), pwLogic
							.getErrorArgs()));

						// ��ʕ\������
						req.setAttribute("alert.msg.brank", getMessage(req, "W00009", "C00597"));
						req.setAttribute("alert.msg.notEquals", getMessage(req, "W00074"));
						req.setAttribute("label.pwd", getWord(req, "C00742"));
						req.setAttribute("label.pwd.confirm", getWord(req, "C00305"));

						getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "password.jsp")
							.forward(req, resp);
						return;
					}

					// �p�X���[�h�ύX�A�����쐬
					pwLogic.update(password, usrCode);
				}
			}

			// ���O�C���ؖ������ɁA�Z�b�V�����ؖ����\�z����
			initSession(req, new LoginCertification(kaiCode, usrCode));

			// ���O�C�����[�U���\�z
			req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "userinfo", logic.makeUserInfo());

			// ���O�C������
			logic.login();

			ServerLogger.debug("login:" + getSession(req).getId() + ", " + kaiCode + ", " + usrCode);

			// ���C���y�[�W���J��
			getServletContext().getRequestDispatcher(TServerEnv.TOP_DIR + "appletindex.jsp").forward(req, resp);

		} else {

			HttpSession session = req.getSession(false);

			// ���O�A�E�g���� ���O�C�����̏��� �r������
			if (session != null) {
				String kaiCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ccd");
				String usrCode = (String) session.getAttribute(TServerEnv.SYS_PREFIX + "ucd");

				ServerLogger.debug("logout:" + session.getId() + ", " + kaiCode + ", " + usrCode);

				// ���O�A�E�g���O�o��
				logLogin(req, false);

				// ���W�b�N�ɓn���p�����[�^��ݒ�
				logic.setCompanyCode(kaiCode);
				logic.setUserCode(usrCode);

				// ���O�A�E�g����
				logic.logout();

				session.setAttribute(TServerEnv.SYS_PREFIX + "isLogout", true);

				// �Z�b�V�������j��
				session.invalidate();
			}
		}
	}

	/**
	 * ���O�C�������Z�b�V�����֍\�z����
	 * 
	 * @param req Request
	 * @param cer ���O�C���ؖ��N���X
	 */
	protected void initSession(HttpServletRequest req, LoginCertification cer) {
		if (req == null || cer == null) {
			throw new IllegalArgumentException("HttpServletRequest or LoginCertification is null.");
		}

		if (!cer.isCertified()) {
			throw new RuntimeException("not login authentication.");
		}

		HttpSession session = req.getSession(true);

		// �F�؏���
		TServerCertification cret = new TServerCertification();
		cret.setCertified(true);
		session.setAttribute(TServerEnv.SYS_PREFIX + "certificationification", cret);

		// ���O�C����ЃR�[�h, ���[�U�R�[�h
		session.setAttribute(TServerEnv.SYS_PREFIX + "ccd", cer.getCompanyCode());
		session.setAttribute(TServerEnv.SYS_PREFIX + "ucd", cer.getUserCode());
	}

	/**
	 * ���O�C���E���O�A�E�g�̎��s���O�o��
	 * 
	 * @param req ���N�G�X�g
	 * @param isLogin true:���O�C���Afalse:���O�A�E�g
	 */
	protected void logLogin(HttpServletRequest req, boolean isLogin) {

		TUserInfo userInfo = refTServerUserInfo(req);

		if (userInfo == null) {
			return;
		}

		String userCode = userInfo.getUserCode();
		String userName = userInfo.getUserName();

		String ip = Util.avoidNull(req.getSession().getAttribute(TServerEnv.SYS_PREFIX + "ipadd"));

		// ���O�o��
		ExecutedLogger logger = ExecutedLogger.getInstance(userInfo.getCompanyCode());

		if (isLogin) {
			logger.logLogin(userCode, userName, ip);

		} else {
			logger.logLogout(userCode, userName, ip);
		}
	}
}
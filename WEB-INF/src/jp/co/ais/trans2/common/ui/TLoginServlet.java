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
import jp.co.ais.trans2.common.fw.*;
import jp.co.ais.trans2.common.fw.TContainer;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.program.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���O�C���̃T�[�u���b�g
 * 
 * @author AIS
 */
public class TLoginServlet extends TServletBase {

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
	 * WEB�A�N�Z�X���Z�b�V���������m������.
	 */
	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) {
		try {

			String reqVersion = Util.avoidNull(req.getParameter("systemVersion"));

			ServerLogger.debug("systemVersion:" + version);
			ServerLogger.debug("reqVersion:" + reqVersion);

			// E00041">�����p�[���̃V�X�e���o�[�W�����ƃT�[�o�̃V�X�e���o�[�W�������قȂ�܂��B\n�N�����̃A�v���P�[�V������S�ĕ�����A�ēx���O�C�����s���Ă��������B
			if (!Util.isNullOrEmpty(reqVersion)) {
				if (!"local".equals(version) && !"local".equals(reqVersion)) {
					if (!version.equals(reqVersion)) {
						super.dispatchResultObject(req, resp, new TException("E00041"));
						return;
					}
				}
			}

			// �C�x���g�n���h�����O
			String methodName = Util.avoidNull(req.getParameter("methodName"));
			List<Object> arg = (List<Object>) super.getObjectParameter(req, "methodArg");

			if ("authenticateUser".equals(methodName)) {

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

				// ���O�C������
				entry(compCode, userCode);

				// ���O�C�������I��
				ServerLogger.debug("login:" + getSession(req).getId() + ", " + compCode + ", " + userCode);

				dispatchResultObject(req, resp, sessionID);

			} else if ("canEntry".equals(methodName)) {

				// ���b�N����

				UserExclusiveManager manager = (UserExclusiveManager) getContainer().getComponent(
					UserExclusiveManager.class);
				String companyCode = Util.avoidNull(arg.get(0));
				String userCode = Util.avoidNull(arg.get(1));
				String password = Util.avoidNull(arg.get(2));
				UserExclusive userExclusive = manager.canEntry(companyCode, userCode, password);
				dispatchResultObject(req, resp, userExclusive);

			} else if ("cancelExclude".equals(methodName)) {

				// ��������

				UserExclusiveManager manager = (UserExclusiveManager) getContainer().getComponent(
					UserExclusiveManager.class);
				String companyCode = Util.avoidNull(arg.get(0));
				String userCode = Util.avoidNull(arg.get(1));
				if (!Util.isNullOrEmpty(companyCode) && !Util.isNullOrEmpty(userCode)) {
					manager.cancelExclude(companyCode, userCode);
				}

				{
					// ���O����
					Log log = new Log();

					// dummy���
					Company company = new Company();
					company.setCode(companyCode);
					// dummy���[�U
					User user = new User();
					user.setCode(userCode);
					user.setName(getWord(req, "C30176")); // ��������

					log.setDate(Util.getCurrentDate());
					log.setCompany(company);
					log.setUser(user);
					Program program = new Program();
					program.setCode(null);
					log.setProgram(program);
					try {
						log.setIp(req.getRemoteAddr());
					} catch (Exception e) {
						log.setIp("Unknown");
					}
					log.setMessage("LOGOUT");

					LogManagerImpl logger = (LogManagerImpl) getContainer().getComponent(LogManager.class);
					logger.setCompany(company);
					logger.setUser(user);
					logger.setNow(log.getDate());
					logger.log(log);
				}

				dispatchResultSuccess(req, resp);

			}

		} catch (Exception e) {
			dispatchError(req, resp, e);
		}
	}

	/**
	 * ���[�U�[�F��
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param password �p�X���[�h
	 * @throws TException
	 */
	public void authenticateUser(String companyCode, String userCode, String password) throws TException {
		TContainer container = TContainerFactory.getContainer();
		UserAuthenticater ua = (UserAuthenticater) container.getComponent(UserAuthenticater.class);
		ua.authenticateUser(companyCode, userCode, password);
	}

	/**
	 * ���O�C������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void entry(String companyCode, String userCode) throws TException {
		TContainer container = TContainerFactory.getContainer();
		SystemManager manager = (SystemManager) container.getComponent(SystemManager.class);
		manager.entryUser(companyCode, userCode);
	}

	/**
	 * ���O�C�������Z�b�V�����֍\�z����
	 * 
	 * @param req Request
	 * @param cer ���O�C���ؖ��N���X
	 * @return �Z�b�V����ID
	 */
	protected String initSession(HttpServletRequest req, LoginCertification cer) {
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

		return session.getId();
	}
}

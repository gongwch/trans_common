package jp.co.ais.trans.common.server.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;

/**
 * ���[�U�擾�pServlet
 */
public class TUserInfoServlet extends TServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 4298618255568532379L;

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

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {

			String flag = req.getParameter("flag");

			if ("base".equals(flag)) {
				dispatchBaseInfo(req, resp);

			} else if ("currency".equals(flag)) {
				dispatchCurrencyDigit(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * ��{���ݒ�
	 * 
	 * @param req
	 * @param resp
	 * @throws TException
	 */
	private void dispatchBaseInfo(HttpServletRequest req, HttpServletResponse resp) throws TException {

		// �p�����[�^�̎擾
		String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));
		String usrCode = Util.avoidNull(req.getParameter("usrCode"));
		String ipadd = Util.avoidNull(req.getParameter("ipadd"));

		req.getSession().setAttribute(TServerEnv.SYS_PREFIX + "ipadd", ipadd);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserManager logic = (UserManager) container.getComponent("old-UserManager");

		logic.setCompanyCode(kaiCode);
		logic.setUserCode(usrCode);

		// ���[�U���&��Џ��
		TUserInfo info = logic.makeUserInfo();
		Map map = info.toStringMap();
		map.putAll(info.getCompanyInfo().toStringMap());

		super.dispatchResultMap(req, resp, map);
	}

	/**
	 * �ʉݖ��̏����_�����ݒ�
	 * 
	 * @param req
	 * @param resp
	 */
	private void dispatchCurrencyDigit(HttpServletRequest req, HttpServletResponse resp) {
		// �p�����[�^�̎擾
		String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserManager logic = (UserManager) container.getComponent("old-UserManager");

		logic.setCompanyCode(kaiCode);

		// �ʉ݃R�[�h,�����_�������
		super.dispatchResultMap(req, resp, logic.getCompanyCurrency());
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �v���Ѓt�B�[���hServlet
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyFieldServlet extends TServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 219250234324041314L;

	/**
	 * doPost()���\�b�h��POST�`���ő��M���ꂽ�f�[�^������
	 */
	public void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			// ����pflag
			String flag = req.getParameter("flag");
			if (flag != null) {
				flag = flag.toLowerCase();
			}

			// ����
			if ("find".equals(flag)) {
				find(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}
	}

	/**
	 * ����
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = SingletonS2ContainerFactory.getContainer();

		AppropriateCompany param = (AppropriateCompany) super.getDtoParameter(req, AppropriateCompany.class);
		// logic�̏�����
		TAppropriateCompanyLogic logic = (TAppropriateCompanyLogic) container
			.getComponent(TAppropriateCompanyLogic.class);

		// ���̂̏�����
		List list = logic.conditionSearch(param);

		this.dispatchResultDtoList(req, resp, list);

	}
}

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
 * ���b�N�A�E�g�T�[�u���b�g
 * 
 * @author roh
 */
public class MG0027LockOutMasterServlet extends TServletBase {

	/** uid */
	private static final long serialVersionUID = -6938937798894373544L;

	/**
	 * �p�����ꂽ���\�b�h
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String flag = req.getParameter("flag");

			// ����
			if ("find".equals(flag)) {
				find(req, resp);

				// �폜
			} else if ("delete".equals(flag)) {
				delete(req, resp);
			}

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * ��ЃR�[�h�Ń��b�N�A�E�g�f�[�^���K��
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {

		TUserInfo userInfo = refTServerUserInfo(req);
		String kaiCode = userInfo.getCompanyCode();

		S2Container container = SingletonS2ContainerFactory.getContainer();

		UserAuthLogic userLogic = (UserAuthLogic) container.getComponent(UserAuthLogic.class);

		USR_AUTH_MST userDto = userLogic.find(kaiCode);

		if (userDto != null) {
			int lockoutCount = userDto.getLOCK_OUT_ARR_CNT();

			// �f�[�^���X�g�擾
			LockOutLogic lockoutLogic = (LockOutLogic) container.getComponent(LockOutLogic.class);
			List usrLockList = lockoutLogic.findWithUserName(kaiCode, lockoutCount);

			super.dispatchResultDtoList(req, resp, usrLockList);

		} else {
			// �F�؃f�[�^���Ȃ��ꍇ�f�[�^�擾���Ȃ�
			super.dispatchResultDtoList(req, resp, new ArrayList(0));
		}
	}

	/**
	 * ��M�������b�N�A�E�g���X�g���폜����B
	 * 
	 * @param req
	 * @param resp
	 */
	private void delete(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		LockOutLogic logic = (LockOutLogic) container.getComponent(LockOutLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);
		String kaiCode = userInfo.getCompanyCode();

		// ���b�N�A�E�g���X�g�K��
		List<String> list = getListParameter(req);
		List<LOCK_OUT_TBL> dtoList = new LinkedList<LOCK_OUT_TBL>();

		// �K���������X�g���G���e�B�e�B���X�g�`���Ń��W�b�N�ɓn���B
		for (String row : list) {
			LOCK_OUT_TBL dto = new LOCK_OUT_TBL();
			dto.setUSR_CODE(row);
			dto.setKAI_CODE(kaiCode);
			dtoList.add(dto);
		}

		logic.delete(dtoList);

		// ���쐬��
		super.dispatchResultSuccess(req, resp);
	}

}

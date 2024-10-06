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
 * �r���Ǘ��}�X�^�T�[�u���b�g
 */
public class MG0360UserUnLockServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = 7715294568502366975L;

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
			}

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * �Y����Ђ̔r����Ԃ̃��[�U���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void find(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		UserUnLockLogic logic = (UserUnLockLogic) container.getComponent(UserUnLockLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);

		// ���O�C���r����񃊃X�g�擾
		List list = logic.getPCIListByCompanyCode(userInfo.getCompanyCode());
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

		UserUnLockLogic logic = (UserUnLockLogic) container.getComponent(UserUnLockLogic.class);
		TUserInfo userInfo = refTServerUserInfo(req);
		List<String> list = getListParameter(req);
		List<PCI_CHK_CTL> dtoList = new LinkedList<PCI_CHK_CTL>();

		// �K���������[�U�R�[�h���X�g���G���e�B�e�B���X�g�`���Ń��W�b�N�ɓn���B
		for (String row : list) {
			PCI_CHK_CTL dto = new PCI_CHK_CTL();
			dto.setUSR_ID(row);
			dto.setKAI_CODE(userInfo.getCompanyCode());
			dtoList.add(dto);
		}

		logic.delete(dtoList);
		dispatchResultSuccess(req, resp);
	}
}

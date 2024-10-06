package jp.co.ais.trans.common.server.servlet;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.server.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �g���X�v���b�h��servlet
 * 
 * @author Yanwei
 */
public class TEnhanceTableServlet extends TServletBase {

	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {
			String status = req.getParameter("flag");

			if ("getWidths".equals(status)) {
				getWidths(req, resp);
			} else if ("saveColumnWidths".equals(status)) {
				saveColumnWidths(req, resp);
			}

		} catch (Exception ex) {
			handledException(ex, req, resp);
		}
	}

	/**
	 * ���[�U�[�ݒ蕝�����擾����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void getWidths(HttpServletRequest req, HttpServletResponse resp) {
		// ��ЃR�[�h
		String kaiCode = refTServerUserInfo(req).getCompanyCode();
		// ���[�U�[�R�[�h
		String userID = refTServerUserInfo(req).getUserCode();
		// �v���O����ID
		String prgID = req.getParameter("programId");

		// ���[�U�[�ݒ蕝�����擾����
		S2Container container = SingletonS2ContainerFactory.getContainer();
		COL_SLT_MSTDao colSltDao = (COL_SLT_MSTDao) container.getComponent(COL_SLT_MSTDao.class);
		COL_SLT_MST bean = colSltDao.getCOL_SLT_MSTByKaicodeUsridPgrid(kaiCode, userID, prgID);

		dispatchResultDto(req, resp, bean);
	}

	/**
	 * ���[�U�[�ݒ蕝�����擾����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void saveColumnWidths(HttpServletRequest req, HttpServletResponse resp) {
		// �X�v���b�h�V�[�g����}�X�^
		COL_SLT_MST bean = (COL_SLT_MST) this.getDtoParameter(req, COL_SLT_MST.class);

		String kaiCode = refTServerUserInfo(req).getCompanyCode();// ��ЃR�[�h
		String userID = refTServerUserInfo(req).getUserCode();// ���[�U�[�R�[�h
		String prgID = bean.getPRG_ID();// �v���O����ID
		bean.setUSR_ID(userID);

		S2Container container = SingletonS2ContainerFactory.getContainer();
		COL_SLT_MSTDao colSltDao = (COL_SLT_MSTDao) container.getComponent(COL_SLT_MSTDao.class);

		// �J��������ۑ�����
		COL_SLT_MST colMstT = colSltDao.getCOL_SLT_MSTByKaicodeUsridPgrid(kaiCode, userID, prgID);
		if (colMstT == null) {
			colSltDao.insert(bean);// �f�[�^�����݂��Ȃ��ꍇ
		} else {
			colSltDao.update(bean);// �f�[�^�����݂���ꍇ
		}

		dispatchResultSuccess(req, resp);
	}

}

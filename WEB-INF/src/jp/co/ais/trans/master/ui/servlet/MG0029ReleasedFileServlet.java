package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.server.TServletBase;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �����[�X�t�@�C���ꗗ�T�[�u���b�g
 */
public class MG0029ReleasedFileServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = 3717257283354795101L;

	@Override
	/**
	 * �����[�X�t�@�C���ꗗ���擾
	 */
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();

			ReleasedFileLogic logic = (ReleasedFileLogic) container.getComponent(ReleasedFileLogic.class);
			TUserInfo userInfo = refTServerUserInfo(req);

			// �T�[�u���b�g���烋�[�g�t�H���_���擾
			String rootPath = getServletContext().getRealPath("");

			// ���s���O���X�g
			List dtoList = logic.getReleasedFileList(rootPath);

			if (dtoList == null || dtoList.isEmpty()) {
				// �f�[�^������܂���B
				throw new TException("W00100");
			}

			if (dtoList.size() >= 65535) {
				throw new TException("W00213");
			}

			// �G�N�Z���t�H�}�b�g�擾
			ReportExcelDefine red = new ReleasedFileReportExcelDefine(userInfo.getUserLanguage());

			// ��ЃR�[�h���Z�b�g
			red.setKaiCode(userInfo.getCompanyCode());

			// ����R�[�h���Z�b�g
			red.setLangCode(userInfo.getUserLanguage());
			OutputUtil util = new OutputUtil(red, userInfo.getUserLanguage());

			// �G�N�Z���`���ɕύX
			util.createExcel(dtoList);
			byte[] bytes = util.getBinary();

			// �o�C�g���M
			super.dispatchExcel(req, resp, red.getFileName(), bytes);
		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

}

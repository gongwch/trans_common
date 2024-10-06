package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
import java.text.*;
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
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ���s���O�Q�ƃT�[�u���b�g
 */
public class MG0028ExecutedLogServlet extends TServletBase {

	/** UID */
	private static final long serialVersionUID = -3190789785708056184L;

	@Override
	protected void doMethodPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException {

		try {
			String flag = req.getParameter("flag");

			if ("findLog".equals(flag)) {
				// ���O�ꗗ�擾
				dispatchLogDataList(req, resp);

			} else if ("report".equals(flag)) {
				// ���|�[�g�o��
				getExcelDataList(req, resp);
			}

		} catch (Exception e) {
			handledException(e, req, resp);
		}

	}

	/**
	 * ���s���O�擾
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ParseException
	 */
	private void dispatchLogDataList(HttpServletRequest req, HttpServletResponse resp) throws ParseException {

		super.dispatchResultDtoList(req, resp, getLogDataList(req));
	}

	/**
	 * ���s���O�̃G�N�Z���o��
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 * @throws ParseException
	 * @throws IOException
	 * @throws TException
	 */
	private void getExcelDataList(HttpServletRequest req, HttpServletResponse resp) throws ParseException, IOException,
		TException {

		// ���s���O���X�g
		List dtoList = getLogDataList(req);

		if (dtoList == null || dtoList.isEmpty()) {
			// �f�[�^������܂���B
			throw new TException("W00100");
		}

		if (dtoList.size() >= 65535) {
			throw new TException("W00213");
		}

		TUserInfo userInfo = refTServerUserInfo(req);

		// �G�N�Z���t�H�}�b�g�擾
		ReportExcelDefine red = new ExecutedLogReportExcelDefine(userInfo.getUserLanguage());
		red.setKaiCode(userInfo.getCompanyCode()); // ��ЃR�[�h
		red.setLangCode(userInfo.getUserLanguage()); // ����R�[�h

		// �G�N�Z���`���ɕύX
		OutputUtil util = new OutputUtil(red, userInfo.getUserLanguage());
		util.createExcel(dtoList);

		super.dispatchExcel(req, resp, red.getFileName(), util.getBinary());
	}

	/**
	 * ���������Ń��O���X�g���擾
	 * 
	 * @param req
	 * @return ���O���X�g
	 * @throws ParseException
	 */
	private List getLogDataList(HttpServletRequest req) throws ParseException {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		ExecutedLogLogic logic = (ExecutedLogLogic) container.getComponent("ExecutedLogLogic");
		TUserInfo userInfo = refTServerUserInfo(req);

		// ���W�b�N���ɓn���p�����[�^��ݒ�
		logic.setCompanyCode(userInfo.getCompanyCode());
		logic.setStartDate(DateUtil.toYMDDate(req.getParameter("startDate")));
		logic.setEndDate(DateUtil.toYMDDate(req.getParameter("endDate")));
		logic.setStartUserCode(req.getParameter("startUser"));
		logic.setEndUserCode(req.getParameter("endUser"));
		logic.setStartProgramCode(req.getParameter("startPrg"));
		logic.setEndProgramCode(req.getParameter("endPrg"));
		logic.setLangCode(userInfo.getUserLanguage());

		if (BooleanUtil.toBoolean(req.getParameter("loginFlag"))) {
			logic.onLogin();
		} else {
			logic.offLogin();
		}

		int sort = Integer.parseInt(req.getParameter("sort"));

		logic.setSort(sort);

		// ���s���O���X�g�擾
		return logic.getExecutedLogList();
	}

}

package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;

/**
 * ����}�X�^Servlet
 */
public class LanguageMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getLogicClassName() {
		return "LanguageLogic";
	}

	@Override
	protected String getREFKeyFields() {
		return "kaiCode";
	}

	@Override
	protected Map getPrimaryKeys(HttpServletRequest req) {
		return null;
	}

	@Override
	protected Map getFindConditions(HttpServletRequest req) {
		return null;
	}

	@Override
	protected Object getEntity(HttpServletRequest req) {
		return null;
	}

	@Override
	protected ReportExcelDefine getReportExcelDefine() {
		return null;
	}

}

package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;

/**
 * �����ړI�}�X�^Servlet
 */
public class ApMktMstServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getLogicClassName() {
		return "ApMktMstLogic";
	}

	@Override
	protected String getREFKeyFields() {
		return "";
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

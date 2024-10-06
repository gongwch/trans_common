package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;

/**
 * 資金コードマスタServlet
 */
public class FundMasterServlet extends MasterServletBase {

	/** シリアルUID */
	private static final long serialVersionUID = 1L;

	@Override
	protected String getLogicClassName() {
		return "FundLogic";
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

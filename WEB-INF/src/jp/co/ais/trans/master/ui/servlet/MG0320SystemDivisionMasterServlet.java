package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �V�X�e���敪�}�X�^���Servlet (MG0320)
 * 
 * @author ISFnet China
 */
public class MG0320SystemDivisionMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 4526667456217311234L;

	@Override
	protected String getLogicClassName() {
		return "SystemDivisionLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		Map map = new HashMap();
		map.put("kaiCode", req.getParameter("kaiCode"));
		map.put("sysKbn", req.getParameter("sysKbn"));
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		Map map = new HashMap();
		map.put("kaiCode", req.getParameter("kaiCode"));
		map.put("beginSysKbn", req.getParameter("beginSysKbn"));
		map.put("endSysKbn", req.getParameter("endSysKbn"));
		return map;
	}

	/**
	 * �G���e�B�e�B�̎擾
	 */
	protected Object getEntity(HttpServletRequest req) {
		SYS_MST sysMST = new SYS_MST();

		sysMST.setKAI_CODE(req.getParameter("kaiCode"));
		sysMST.setSYS_KBN(req.getParameter("sysKbn"));
		sysMST.setSYS_KBN_NAME(req.getParameter("sysName"));
		sysMST.setSYS_KBN_NAME_S(req.getParameter("sysName_S"));
		sysMST.setSYS_KBN_NAME_K(req.getParameter("sysName_K"));
		sysMST.setOSY_KBN(req.getParameter("osyKbn"));

		sysMST.setPRG_ID(req.getParameter("prgID")); // �v���O����ID

		return sysMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		return new SystemDivisionMasterReportExcelDefine();
	}
}

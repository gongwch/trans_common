package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ��Њԕt�փ}�X�^���Servlet (MG0350)
 * 
 * @author ISFnet China
 */
public class MG0350InterCompanyTransferMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -8731325651481641035L;

	@Override
	protected String getLogicClassName() {
		return "InterCompanyTransferLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �t�։�ЃR�[�h�̐ݒ�
		map.put("ktkKaiCode", req.getParameter("ktkKaiCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �t�։�ЃR�[�h�̐ݒ�
		map.put("ktkKaiCode", req.getParameter("ktkKaiCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginKtkKaiCode", req.getParameter("beginKtkKaiCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endKtkKaiCode", req.getParameter("endKtkKaiCode"));
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		KTK_MST ktkMST = new KTK_MST();
		// ��ЃR�[�h�̐ݒ�
		ktkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �t�։�ЃR�[�h
		ktkMST.setKTK_KAI_CODE(req.getParameter("ktkKaiCode"));
		// �t�֌v�㕔��R�[�h
		ktkMST.setKTK_DEP_CODE(req.getParameter("ktkDepCode"));
		// �t�։ȖڃR�[�h
		ktkMST.setKTK_KMK_CODE(req.getParameter("ktkKmkCode"));
		// �t�֕⏕�ȖڃR�[�h
		ktkMST.setKTK_HKM_CODE(req.getParameter("ktkHkmCode"));
		// �t�֓���ȖڃR�[�h
		ktkMST.setKTK_UKM_CODE(req.getParameter("ktkUkmCode"));
		// �v���O����ID�̐ݒ�
		ktkMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return ktkMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new InterCompanyTransferMasterReportExcelDefine();
	}
}

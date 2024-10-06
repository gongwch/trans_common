package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �J������Ͻ����Servlet (MG0340)
 * 
 * @author ISFnet China
 */
public class MG0340IndicationLevelMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -769800516753993536L;

	@Override
	protected String getLogicClassName() {
		return "IndicationLevelLogic";
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
		// ���[�U�[�R�[�h�̐ݒ�
		map.put("kjlUsrId", req.getParameter("kjlUsrId"));
		// �Ȗڑ̌n���ނ̐ݒ�
		map.put("kjlKmtCode", req.getParameter("kjlKmtCode"));
		// �g�D�R�[�h�̐ݒ�
		map.put("kjlDpkSsk", req.getParameter("kjlDpkSsk"));
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ���[�U�[�R�[�h�̐ݒ�
		map.put("kjlUsrId", req.getParameter("kjlUsrId"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginKjlUsrId", req.getParameter("beginKjlUsrId"));
		// �I���R�[�h�̐ݒ�
		map.put("endKjlUsrId", req.getParameter("endKjlUsrId"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		KJL_MST kjlMST = new KJL_MST();
		// ��ЃR�[�h�̐ݒ�
		kjlMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ���[�U�[�R�[�h�̐ݒ�
		kjlMST.setKJL_USR_ID(req.getParameter("kjlUsrId"));
		// �Ȗڑ̌n���ނ̐ݒ�
		kjlMST.setKJL_KMT_CODE(req.getParameter("kjlKmtCode"));
		// �g�D�R�[�h�̐ݒ�
		kjlMST.setKJL_DPK_SSK(req.getParameter("kjlDpkSsk"));
		// �J�����x���̎擾
		int kjlLvl = Integer.parseInt(req.getParameter("kjlLvl"));
		// �J�����x���̐ݒ�
		kjlMST.setKJL_LVL(kjlLvl);
		// ��ʕ���R�[�h�̐ݒ�
		kjlMST.setKJL_UP_DEP_CODE(req.getParameter("kjlUpDepCode"));
		// ����R�[�h�̐ݒ�
		kjlMST.setKJL_DEP_CODE(req.getParameter("kjlDepCode"));
		// �v���O����ID�̐ݒ�
		kjlMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return kjlMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new IndicationLevelMasterReportExcelDefine();
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * GL�R���g���[���}�X�^���Servlet (MG0040)
 * 
 * @author ISFnet China
 */
public class MG0040GLControlMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 1389937286658315664L;

	@Override
	protected String getLogicClassName() {
		return "GLControlLogic";
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
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h
		map.put("kaiCode", req.getParameter("kaiCode"));

		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		GL_CTL_MST glCtlMST = new GL_CTL_MST();
		// ��ЃR�[�h�̐ݒ�
		glCtlMST.setKAI_CODE(req.getParameter("kaiCode"));
		// KSD_KBN�̎擾
		int ksdKbn = Integer.parseInt(req.getParameter("ksdKbn"));
		// KSN_NYU_KBN�̎擾
		int ksnNyuKbn = Integer.parseInt(req.getParameter("ksnNyuKbn"));
		// �������ʎc���\���敪�̎擾
		int mtZanHyjKbn = Integer.parseInt(req.getParameter("mtZanHyjKbn"));
		// �]���փ��[�g�敪�̎擾
		int excRateKbn = Integer.parseInt(req.getParameter("excRateKbn"));
		// KSD_KBN�̐ݒ�
		glCtlMST.setKSD_KBN(ksdKbn);
		// KSN_NYU_KBN�̐ݒ�
		glCtlMST.setKSN_NYU_KBN(ksnNyuKbn);
		// �������ʎc���\���敪�̐ݒ�
		glCtlMST.setMT_ZAN_HYJ_KBN(mtZanHyjKbn);
		// �]���փ��[�g�敪�̐ݒ�
		glCtlMST.setEXC_RATE_KBN(excRateKbn);
		// �v���O����ID�̐ݒ�
		glCtlMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return glCtlMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new GLControlMasterReportExcelDefine();
	}
}

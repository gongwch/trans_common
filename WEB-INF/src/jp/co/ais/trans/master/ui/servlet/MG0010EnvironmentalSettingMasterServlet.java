package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ���ݒ�}�X�^���Servlet (MG0010)
 * 
 * @author ISFnet China
 */
public class MG0010EnvironmentalSettingMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 2055447815160612175L;

	@Override
	protected String getLogicClassName() {
		return "EnvironmentalSettingLogic";
	}

	protected String getREFKeyFields() {
		return "";
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
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		ENV_MST envMST = new ENV_MST();
		// ��ЃR�[�h�̐ݒ�
		envMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ��Ж��̂̐ݒ�
		envMST.setKAI_NAME(req.getParameter("kaiName"));
		// ��З��̂̐ݒ�
		envMST.setKAI_NAME_S(req.getParameter("kaiName_S"));
		// �X�֔ԍ��̐ݒ�
		envMST.setZIP(req.getParameter("zip"));
		// �Z���J�i�̐ݒ�
		envMST.setJYU_KANA(req.getParameter("jyuKana"));
		// �Z���P�̐ݒ�
		envMST.setJYU_1(req.getParameter("jyu1"));
		// �Z���Q�̐ݒ�
		envMST.setJYU_2(req.getParameter("jyu2"));
		// �d�b�ԍ��̐ݒ�
		envMST.setTEL(req.getParameter("tel"));
		// FAX�ԍ��̐ݒ�
		envMST.setFAX(req.getParameter("fax"));
		// �o�b�N�J���[�̐ݒ�
		envMST.setFORECOLOR(req.getParameter("foreColor"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		envMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		envMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		envMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return envMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new EnvironmentalSettingMasterReportExcelDefine();
	}
}

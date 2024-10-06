package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �Ǘ��P�}�X�^���Servlet (MG0180)
 * 
 * @author ISFnet China
 */
public class MG0180Management1MasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -8193884622639097969L;

	@Override
	protected String getLogicClassName() {
		return "Management1Logic";
	}

	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �Ǘ��R�[�h�̐ݒ�
		map.put("knrCode", req.getParameter("knrCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginknrCode", req.getParameter("beginknrCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endknrCode", req.getParameter("endknrCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		KNR1_MST knr1MST = new KNR1_MST();
		// ��ЃR�[�h�̐ݒ�
		knr1MST.setKAI_CODE(req.getParameter("kaiCode"));
		// �Ǘ��P���ނ̐ݒ�
		knr1MST.setKNR_CODE_1(req.getParameter("knrCode"));
		// �Ǘ��P���̂̐ݒ�
		knr1MST.setKNR_NAME_1(req.getParameter("knrName"));
		// �Ǘ��P���̂̐ݒ�
		knr1MST.setKNR_NAME_S_1(req.getParameter("knrName_S"));
		// �Ǘ��P�������̂̐ݒ�
		knr1MST.setKNR_NAME_K_1(req.getParameter("knrName_K"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		knr1MST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		knr1MST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		knr1MST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return knr1MST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new Management1MasterReportExcelDefine();
	}
}

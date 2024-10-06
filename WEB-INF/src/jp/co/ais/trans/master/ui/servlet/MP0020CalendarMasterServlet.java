package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �J�����_�[�}�X�^���Servlet (MP0020)
 * 
 * @author ISFnet China
 */
public class MP0020CalendarMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 8762997493326096483L;

	@Override
	protected String getLogicClassName() {
		return "CalendarLogic";
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
		// ���t�̐ݒ�
		map.put("calDate", req.getParameter("calDate"));
		// ���̐ݒ�
		map.put("calMonth", req.getParameter("calMonth"));
		// �����̐ݒ�
		map.put("intLastDay", req.getParameter("intLastDay"));
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �J�n���t�̐ݒ�
		map.put("beginCalDate", req.getParameter("beginCalDate"));
		// �I�����t�̐ݒ�
		map.put("endCalDate", req.getParameter("endCalDate"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		AP_CAL_MST apCalMST = new AP_CAL_MST();
		// ��ЃR�[�h�̐ݒ�
		apCalMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ���t�̎擾
		Date calDate = DateUtil.toYMDDate(req.getParameter("calDate"));
		// ���t�̐ݒ�
		apCalMST.setCAL_DATE(calDate);
		// �Ј��x���Ώۓ��敪�̎擾
		int calHarai = Integer.parseInt(req.getParameter("calHarai"));
		// �Ј��x���Ώۓ��敪�̐ݒ�
		apCalMST.setCAL_HARAI(calHarai);
		// ��s�x���敪�̎擾
		int calBnkKbn = Integer.parseInt(req.getParameter("calBnkKbn"));
		// ��s�x���敪�̐ݒ�
		apCalMST.setCAL_BNK_KBN(calBnkKbn);
		// �Վ��x���Ώۓ��敪�̎擾
		int calSha = Integer.parseInt(req.getParameter("calSha"));
		// �Վ��x���Ώۓ��敪�̐ݒ�
		apCalMST.setCAL_SHA(calSha);
		// �v���O����ID�̐ݒ�
		apCalMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return apCalMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return null;
	}
}

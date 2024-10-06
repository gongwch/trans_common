package jp.co.ais.trans.master.ui.servlet;

import java.util.*;
import java.math.*;
import java.text.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ���[�g�}�X�^���Servlet (MG0310)
 * 
 * @author IFSnet China
 */
public class MG0310RateMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 5753057878071009755L;

	@Override
	protected String getLogicClassName() {
		return "RateLogic";
	}

	protected String getREFKeyFields() {
		return null;
	}

	/**
	 * ��L�[�̎擾
	 * 
	 * @throws ParseException
	 */
	protected Map getPrimaryKeys(HttpServletRequest req) throws ParseException {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �ʉ݃R�[�h�̐ݒ�
		map.put("curCode", req.getParameter("curCode"));
		// �K�p�J�n���t�̎擾
		Date strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �K�p�J�n���t�̐ݒ�
		map.put("strDate", strDate);
		// ���ʂ�Ԃ�
		return map;
	}

	/**
	 * ���������̎擾
	 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginCurCode", req.getParameter("beginCurCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endCurCode", req.getParameter("endCurCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/**
	 * �G���e�B�e�B�̎擾
	 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		RATE_MST rateMST = new RATE_MST();
		// ��ЃR�[�h�̐ݒ�
		rateMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �ʉݺ��ނ̐ݒ�
		rateMST.setCUR_CODE(req.getParameter("curCode"));
		// ���[�gނ̎擾
		BigDecimal curRate = new BigDecimal(req.getParameter("curRate"));
		// ���[�gނ̐ݒ�
		rateMST.setCUR_RATE(curRate);
		// �K�p�J�n���t�̏�����
		Date strDate = null;
		// �K�p�J�n���t�̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �K�p�J�n���t�̐ݒ�
		rateMST.setSTR_DATE(strDate);
		// �v���O����ID�̐ݒ�
		rateMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return rateMST;
	}

	/**
	 * ���X�g�o�͂�Excel��`
	 */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new RateMasterReportExcelDefine();
	}
}

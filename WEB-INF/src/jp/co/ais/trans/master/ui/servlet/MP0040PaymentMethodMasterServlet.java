package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �x�����@�}�X�^���Servlet (MP0040)
 * 
 * @author ISFnet China
 */
public class MP0040PaymentMethodMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -203499160025958870L;

	@Override
	protected String getLogicClassName() {
		return "PaymentMethodLogic";
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
		// �x�����@�R�[�h�̐ݒ�
		map.put("hohHohCode", req.getParameter("hohHohCode"));
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
		map.put("beginHohHohCode", req.getParameter("beginHohHohCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endHohHohCode", req.getParameter("endHohHohCode"));
		// �x���Ώۋ敪:�Ј��x��
		map.put("includeEmployeePayment", req.getParameter("includeEmployeePayment"));
		// �x���Ώۋ敪:�ЊO�x��
		map.put("includeExternalPayment", req.getParameter("includeExternalPayment"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		AP_HOH_MST apHohMST = new AP_HOH_MST();
		// ��ЃR�[�h�̐ݒ�
		apHohMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �x�����@�R�[�h�̐ݒ�
		apHohMST.setHOH_HOH_CODE(req.getParameter("hohHohCode"));
		// �x�����@���̐ݒ�
		apHohMST.setHOH_HOH_NAME(req.getParameter("hohHohName"));
		// �x�����@�������̂̐ݒ�
		apHohMST.setHOH_HOH_NAME_K(req.getParameter("hohHohName_K"));
		// �ȖڃR�[�h�̐ݒ�
		apHohMST.setHOH_KMK_CODE(req.getParameter("hohKmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		apHohMST.setHOH_HKM_CODE(req.getParameter("hohHkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		apHohMST.setHOH_UKM_CODE(req.getParameter("hohUkmCode"));
		// �v�㕔��R�[�h�̐ݒ�
		apHohMST.setHOH_DEP_CODE(req.getParameter("hohDepCode"));
		// �x�������R�[�h�̐ݒ�
		apHohMST.setHOH_NAI_CODE(req.getParameter("hohNaiCode"));
		// �x���Ώۋ敪�̎擾
		int hohSihKbn = Integer.parseInt(req.getParameter("hohSihKbn"));
		// �x���Ώۋ敪�̐ݒ�
		apHohMST.setHOH_SIH_KBN(hohSihKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		apHohMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		apHohMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		apHohMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return apHohMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new PaymentMethodMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refPayment".equals(flag)) {
			searchRefPayment(req, resp);
		}
	}

	private void searchRefPayment(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = SingletonS2ContainerFactory.getContainer();

		// dao�̏�����
		AP_HOH_MSTDao dao = (AP_HOH_MSTDao) container.getComponent(AP_HOH_MSTDao.class);

		ApHohMstParameter param = (ApHohMstParameter) getObjectParameter(req);

		List<AP_HOH_MST> lst = dao.getApHohMst(param);

		if (lst.isEmpty()) {
			super.dispatchResultDto(req, resp, null);
		} else {
			super.dispatchResultDto(req, resp, lst.get(0));
		}

	}
}

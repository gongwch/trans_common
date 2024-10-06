package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �Ј��}�X�^���Servlet (MG0160)
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 2503184025898490906L;

	@Override
	protected String getLogicClassName() {
		return "EmployeeLogic";
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
		// �Ј��R�[�h�̐ݒ�
		map.put("empCode", req.getParameter("empCode"));
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
		map.put("beginEmpCode", req.getParameter("beginEmpCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endEmpCode", req.getParameter("endEmpCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		EMP_MST empMST = new EMP_MST();
		// ��ЃR�[�h�̐ݒ�
		empMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �Ј��R�[�h�̐ݒ�
		empMST.setEMP_CODE(req.getParameter("empCode"));
		// �Ј����̂̐ݒ�
		empMST.setEMP_NAME(req.getParameter("empName"));
		// �Ј����̂̐ݒ�
		empMST.setEMP_NAME_S(req.getParameter("empName_S"));
		// �Ј��������̂̐ݒ�
		empMST.setEMP_NAME_K(req.getParameter("empName_K"));
		// �U����s�b�c�̐ݒ�
		empMST.setEMP_FURI_BNK_CODE(req.getParameter("empFuriBnkCode"));
		// �U���x�X�b�c�̐ݒ�
		empMST.setEMP_FURI_STN_CODE(req.getParameter("empFuriStnCode"));
		// �U�������ԍ��̐ݒ�
		empMST.setEMP_YKN_NO(req.getParameter("empYknNo"));
		// �U�������a����ʂ̎擾
		int empKozaKbn = Integer.parseInt(req.getParameter("empKozaKbn"));
		// �U�������a����ʂ̐ݒ�
		empMST.setEMP_KOZA_KBN(empKozaKbn);
		// �������`�J�i�̐ݒ�
		empMST.setEMP_YKN_KANA(req.getParameter("empYknKana"));
		// �U�o��s�����R�[�h�̐ݒ�
		empMST.setEMP_CBK_CODE(req.getParameter("empCbkCode"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		empMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		empMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		empMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return empMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new EmployeeMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refEmployee".equals(flag)) {
			searchRefEmployee(req, resp);
		}
	}

	/**
	 * �Ј����X�g����
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefEmployee(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			EmployeeLogic logic = (EmployeeLogic) container.getComponent(EmployeeLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("empCode", req.getParameter("empCode"));

			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

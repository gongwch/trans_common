package jp.co.ais.trans.master.ui.servlet;

import java.math.*;
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
 * ����}�X�^���Servlet (MG0060)
 * 
 * @author AIS
 */
public class MG0060DepartmentMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -1501238776922033897L;

	@Override
	protected String getLogicClassName() {
		return "DepartmentLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode,dpkSsk,level,parentDepCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// ����R�[�h�̐ݒ�
		map.put("depCode", req.getParameter("depCode"));
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
		map.put("beginDepCode", req.getParameter("beginDepCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endDepCode", req.getParameter("endDepCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		BMN_MST depMST = new BMN_MST();
		// ��ЃR�[�h�̐ݒ�
		depMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ����R�[�h�̐ݒ�
		depMST.setDEP_CODE(req.getParameter("depCode"));
		// ���喼�̂̐ݒ�
		depMST.setDEP_NAME(req.getParameter("depName"));
		// ���嗪�̂̐ݒ�
		depMST.setDEP_NAME_S(req.getParameter("depName_S"));
		// ���匟�����̂̐ݒ�
		depMST.setDEP_NAME_K(req.getParameter("depName_K"));
		// �l�����P�̎擾
		int men1 = Integer.parseInt(req.getParameter("men1"));
		// �l�����Q�̎擾
		int men2 = Integer.parseInt(req.getParameter("men2"));
		// ���ʐς̎擾
		BigDecimal space = new BigDecimal(req.getParameter("space"));
		// ����敪�̎擾
		int depKbn = Integer.parseInt(req.getParameter("depKbn"));
		// �l�����P�̐ݒ�
		depMST.setMEN_1(men1);
		// �l�����Q�̐ݒ�
		depMST.setMEN_2(men2);
		// ���ʐς̐ݒ�
		depMST.setAREA(space);
		// ����敪�̐ݒ�
		depMST.setDEP_KBN(depKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		depMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		depMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		depMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return depMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new DepartmentMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");

		// ����
		if ("DepartmentInfo".equals(flag)) {
			dispatchDeptData(req, resp);
		}
	}

	/**
	 * �P�̌���
	 * 
	 * @param req
	 * @param resp
	 */
	private void dispatchDeptData(HttpServletRequest req, HttpServletResponse resp) {

		String companyCode = req.getParameter("companyCode");
		String deptCode = req.getParameter("deptCode");
		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		DepartmentLogic logic = (DepartmentLogic) container.getComponent(DepartmentLogic.class);
		logic.setCompanyCode(companyCode);

		BMN_MST entity = logic.findDepartment(deptCode, beginCode, endCode);

		super.dispatchResultDto(req, resp, entity);
	}
}

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
 * ����Ń}�X�^���Servlet (MG0130)
 * 
 * @author ISFnet China
 */
public class MG0130ConsumptionTaxMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 5856804226168005388L;

	@Override
	protected String getLogicClassName() {
		return "ConsumptionTaxLogic";
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
		// ����ŃR�[�h
		map.put("zeiCode", req.getParameter("zeiCode"));
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
		map.put("beginZeiCode", req.getParameter("beginZeiCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endZeiCode", req.getParameter("endZeiCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		SZEI_MST szeiMST = new SZEI_MST();
		// ��ЃR�[�h�̐ݒ�
		szeiMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ����ŃR�[�h�̐ݒ�
		szeiMST.setZEI_CODE(req.getParameter("zeiCode"));
		// ����Ŗ��̂̐ݒ�
		szeiMST.setZEI_NAME(req.getParameter("zeiName"));
		// ����ŗ��̂̐ݒ�
		szeiMST.setZEI_NAME_S(req.getParameter("zeiName_S"));
		// ����Ō������̂̐ݒ�
		szeiMST.setZEI_NAME_K(req.getParameter("zeiName_K"));
		// ����Ōv�Z���o�͏����̐ݒ�
		szeiMST.setSZEI_KEI_KBN(req.getParameter("szeiKeiKbn"));
		// ����d���敪�̎擾
		int usKbn = Integer.parseInt(req.getParameter("usKbn"));
		// ����d���敪�̐ݒ�
		szeiMST.setUS_KBN(usKbn);
		// ����ŗ��̎擾
		Float zeiRate = Float.parseFloat(req.getParameter("zeiRate"));
		// ����ŗ��̐ݒ�
		szeiMST.setZEI_RATE(zeiRate);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		szeiMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		szeiMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		szeiMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return szeiMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new ConsumptionTaxMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refTax".equals(flag)) {
			searchRefTax(req, resp);
		}
	}

	// ����ŋ��ʃt�B���h�p���\�b�h
	private void searchRefTax(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			ConsumptionTaxLogic logic = (ConsumptionTaxLogic) container.getComponent(ConsumptionTaxLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("zeiCode", req.getParameter("zeiCode"));

			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

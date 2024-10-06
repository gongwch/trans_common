package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �����}�X�^���Servlet
 * 
 * @author ISFnet China
 */
public class MG0150CustomerMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 6009200918852626753L;

	@Override
	protected String getLogicClassName() {
		return "CustomerLogic";
	}

	@Override
	protected String getREFKeyFields() {
		return "kaiCode";
	}

	/** ��L�[�̎擾 */
	@Override
	protected Map getPrimaryKeys(HttpServletRequest req) {

		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �����R�[�h�̐ݒ�
		map.put("triCode", req.getParameter("triCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	@Override
	protected Map getFindConditions(HttpServletRequest req) {

		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginTriCode", req.getParameter("beginTriCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endTriCode", req.getParameter("endTriCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	@Override
	protected Object getEntity(HttpServletRequest req) throws ParseException {

		// ���̂̏�����
		TRI_MST triMST = new TRI_MST();
		// ��ЃR�[�h�̐ݒ�
		triMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �����R�[�h�̐ݒ�
		triMST.setTRI_CODE(req.getParameter("triCode"));
		// ����於�̂̐ݒ�
		triMST.setTRI_NAME(req.getParameter("triName"));
		// ����旪�̂̐ݒ�
		triMST.setTRI_NAME_S(req.getParameter("triName_S"));
		// ����挟�����̂̐ݒ�
		triMST.setTRI_NAME_K(req.getParameter("triName_K"));
		// �X�֔ԍ��̐ݒ�
		triMST.setZIP(req.getParameter("zip"));
		// �Z���J�i�̐ݒ�
		triMST.setJYU_KANA(req.getParameter("jyuKana"));
		// �Z���P�̐ݒ�
		triMST.setJYU_1(req.getParameter("jyu1"));
		// �Z���Q�̐ݒ�
		triMST.setJYU_2(req.getParameter("jyu2"));
		// �d�b�ԍ��̐ݒ�
		triMST.setTEL(req.getParameter("tel"));
		// FAX�ԍ��̐ݒ�
		triMST.setFAX(req.getParameter("fax"));
		// �W�v�����R�[�h�̐ݒ�
		triMST.setSUM_CODE(req.getParameter("sumCode"));
		// �����������ߓ��̐ݒ�
		triMST.setNJ_C_DATE(req.getParameter("njCDate"));
		// �����������ߌ㌎�̐ݒ�
		triMST.setNJ_R_MON(req.getParameter("njRMon"));
		// ���������������̐ݒ�
		triMST.setNJ_P_DATE(req.getParameter("njPDate"));
		// ������s�������ނ̐ݒ�
		triMST.setNKN_CBK_CODE(req.getParameter("nknCbkCode"));
		// ����`�ԋ敪�̐ݒ�
		triMST.setTRI_KBN(req.getParameter("triKbn"));
		// �X�|�b�g�`�[�ԍ��̐ݒ�
		triMST.setSPOT_DEN_NO(req.getParameter("spotDenNo"));
		// ���Ə����̂̐ݒ�
		triMST.setJIG_NAME(req.getParameter("jigName"));
		// �U���˗��l���̐ݒ�
		triMST.setIRAI_NAME(req.getParameter("iraiName"));
		// �d����敪�̎擾
		int siireKbn = Integer.parseInt(req.getParameter("siireKbn"));
		// �d����敪�̐ݒ�
		triMST.setSIIRE_KBN(siireKbn);
		// ���Ӑ�敪�̎擾
		int tokuiKbn = Integer.parseInt(req.getParameter("tokuiKbn"));
		// ���Ӑ�敪�̐ݒ�
		triMST.setTOKUI_KBN(tokuiKbn);
		// �����萔���敪�̎擾
		int nyuTesuKbn = Integer.parseInt(req.getParameter("nyuTesuKbn"));
		// �����萔���敪�̐ݒ�
		triMST.setNYU_TESU_KBN(nyuTesuKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		triMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		triMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		triMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return triMST;
	}

	/** ���X�g�o�͂�Excel��` */
	@Override
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new CustomerMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		try {
			// ����pflag
			String flag = req.getParameter("flag");

			// ����
			if ("checkTriKbn".equals(flag)) {

				checkTriKbn(req, resp);

			} else if ("CustomerInfo".equals(flag)) {
				// �����f�[�^�擾
				dispatchCustomerData(req, resp);
			}
		} catch (ParseException e) {
			ClientErrorHandler.handledException(e);
		}
	}

	private void checkTriKbn(HttpServletRequest req, HttpServletResponse resp) throws ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();
		// logic�̏�����
		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);
		// keys�̏�����
		Map keys = new HashMap();
		keys.put("kaiCode", req.getParameter("kaiCode"));
		keys.put("triCode", req.getParameter("triCode"));

		// ���̂̏�����
		TRI_MST entity = (TRI_MST) logic.findOne(keys);
		if (entity != null) {
			// �x�������R�[�h�̎擾
			Map data = new HashMap();
			data.put("tRI_KBN", entity.getTRI_KBN());
			// ���ʂ̐ݒ�
			super.dispatchResultMap(req, resp, data);
		}
	}

	/**
	 * �������Z�b�g
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void dispatchCustomerData(HttpServletRequest req, HttpServletResponse resp) {

		String companyCode = req.getParameter("companyCode");
		String customerCode = req.getParameter("customerCode");
		String beginCode = req.getParameter("beginCode");
		String endCode = req.getParameter("endCode");

		S2Container container = SingletonS2ContainerFactory.getContainer();
		CustomerLogic logic = (CustomerLogic) container.getComponent(CustomerLogic.class);
		logic.setCompanyCode(companyCode);

		TRI_MST entity = logic.findCustomer(customerCode, beginCode, endCode);

		super.dispatchResultObject(req, resp, entity);
	}
}

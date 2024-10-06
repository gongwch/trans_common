package jp.co.ais.trans.master.ui.servlet;

import java.util.*;
import java.text.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ��s�����}�X�^���Servlet (MP0030)
 * 
 * @author ISFnet China
 */
public class MP0030BankAccountMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -4353848069041891953L;

	@Override
	protected String getLogicClassName() {
		return "BankAccountLogic";
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
		// ��s�����R�[�h�̐ݒ�
		map.put("cbkCbkCode", req.getParameter("cbkCbkCode"));
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
		map.put("beginCbkCbkCode", req.getParameter("beginCbkCbkCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endCbkCbkCode", req.getParameter("endCbkCbkCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		AP_CBK_MST apCbkMST = new AP_CBK_MST();
		// ��ЃR�[�h�̐ݒ�
		apCbkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// ��s�����R�[�h�̐ݒ�
		apCbkMST.setCBK_CBK_CODE(req.getParameter("cbkCbkCode"));
		// ��s�������̂̐ݒ�
		apCbkMST.setCBK_NAME(req.getParameter("cbkName"));
		// �ʉ݃R�[�h�̐ݒ�
		apCbkMST.setCUR_CODE(req.getParameter("curCode"));
		// ��s�R�[�h�̐ݒ�
		apCbkMST.setCBK_BNK_CODE(req.getParameter("cbkBnkCode"));
		// �x�X�R�[�h�̐ݒ�
		apCbkMST.setCBK_STN_CODE(req.getParameter("cbkStnCode"));
		// �U���˗��l�R�[�h�̐ݒ�
		apCbkMST.setCBK_IRAI_EMP_CODE(req.getParameter("cbkIraiEmpCode"));
		// �U���˗��l���̐ݒ�
		apCbkMST.setCBK_IRAI_NAME(req.getParameter("cbkIraiName"));
		// �U���˗��l���i�����j�̐ݒ�
		apCbkMST.setCBK_IRAI_NAME_J(req.getParameter("cbkIraiName_J"));
		// �U���˗��l���i�p���j�̐ݒ�
		apCbkMST.setCBK_IRAI_NAME_E(req.getParameter("cbkIraiName_E"));
		// �����ԍ��̐ݒ�
		apCbkMST.setCBK_YKN_NO(req.getParameter("cbkYknNo"));
		// �v�㕔��R�[�h�̐ݒ�
		apCbkMST.setCBK_DEP_CODE(req.getParameter("cbkDepCode"));
		// �ȖڃR�[�h�̐ݒ�
		apCbkMST.setCBK_KMK_CODE(req.getParameter("cbkKmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		apCbkMST.setCBK_HKM_CODE(req.getParameter("cbkHkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		apCbkMST.setCBK_UKM_CODE(req.getParameter("cbkUkmCode"));
		// �Ј��e�a�敪�̎擾
		int cbkEmpFbKbn = Integer.parseInt(req.getParameter("cbkEmpFbKbn"));
		// �Ј��e�a�敪�̐ݒ�
		apCbkMST.setCBK_EMP_FB_KBN(cbkEmpFbKbn);
		// �ЊO�e�a�敪�̎擾
		int cbkOutFbKbn = Integer.parseInt(req.getParameter("cbkOutFbKbn"));
		// �ЊO�e�a�敪�̐ݒ�
		apCbkMST.setCBK_OUT_FB_KBN(cbkOutFbKbn);
		// �a����ڂ̎擾
		int cbkYknKbn = Integer.parseInt(req.getParameter("cbkYknKbn"));
		// �a����ڂ̐ݒ�
		apCbkMST.setCBK_YKN_KBN(cbkYknKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		apCbkMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		apCbkMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		apCbkMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return apCbkMST;
	}

	/** ���ʂ̎擾 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		if ("defaultReceivedAccount".equals(req.getParameter("flag"))) {
			searchReceivedAccount(req, resp);
		} else if ("getCbkData".equals(req.getParameter("flag"))) {
			getCbkData(req, resp);
		} else {
			// getREFItems
			// container�̏�����
			S2Container container = null;
			try {

				container = SingletonS2ContainerFactory.getContainer();
				// �t�@�C���̎擾
				BankAccountLogic logic = (BankAccountLogic) container.getComponent(getLogicClassName());
				// keys�̏�����
				Map keys = this.getPrimaryKeys(req);
				// ���ʂ��擾
				List list = logic.getREFItems(keys);
				// ���ʂ̐ݒ�
				super.dispatchResultList(req, resp, list);
			} catch (Throwable ex) {
				super.dispatchError(req, resp, ex.getMessage());
			}
		}
	}

	/**
	 * ��s�����}�X�^�̃f�[�^���擾����
	 * 
	 * @param req
	 * @param resp
	 */
	private void getCbkData(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		// dao�̏�����
		AP_CBK_MSTDao dao = (AP_CBK_MSTDao) container.getComponent(AP_CBK_MSTDao.class);

		ApCbkMstParameter param = (ApCbkMstParameter) getObjectParameter(req);

		List<AP_CBK_MST> lst = dao.getApCbkMst(param);

		if (lst.isEmpty()) {
			super.dispatchResultDto(req, resp, null);
		} else {
			super.dispatchResultDto(req, resp, lst.get(0));
		}
	}

	/**
	 * �f�t�H���g�̎x�������擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchReceivedAccount(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			BankAccountLogic logic = (BankAccountLogic) container.getComponent(BankAccountLogic.class);

			// ��ЃR�[�h
			String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

			dispatchResultMap(req, resp, logic.getDefaultReceivedAccount(kaiCode));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new BankAccountMasterReportExcelDefine();
	}
}

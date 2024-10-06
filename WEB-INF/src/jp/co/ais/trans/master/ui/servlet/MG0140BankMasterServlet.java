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
 * ��s�}�X�^���Servlet (MG0140)
 * 
 * @author ISFnet China
 */
public class MG0140BankMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 7691999673652241786L;

	@Override
	protected String getLogicClassName() {
		return "BankLogic";
	}

	protected String getREFKeyFields() {
		return "bnkCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("bnkCode", req.getParameter("bnkCode"));
		// ��s�x�X�R�[�h�̐ݒ�
		map.put("bnkStnCode", req.getParameter("bnkStnCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��s�R�[�h�̐ݒ�
		map.put("bnkCode", req.getParameter("bnkCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginBnkStnCode", req.getParameter("beginBnkStnCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endBnkStnCode", req.getParameter("endBnkStnCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		BNK_MST bnkMST = new BNK_MST();
		// ��s�R�[�h�̐ݒ�
		bnkMST.setBNK_CODE(req.getParameter("bnkCode"));
		// ��s�x�X�R�[�h�̐ݒ�
		bnkMST.setBNK_STN_CODE(req.getParameter("bnkStnCode"));
		// ��s���̐ݒ�
		bnkMST.setBNK_NAME_S(req.getParameter("bnkName_S"));
		// ��s�J�i���̂̐ݒ�
		bnkMST.setBNK_KANA(req.getParameter("bnkKana"));
		// ��s�������̂̐ݒ�
		bnkMST.setBNK_NAME_K(req.getParameter("bnkName_K"));
		// ��s�x�X���̐ݒ�
		bnkMST.setBNK_STN_NAME_S(req.getParameter("bnkStnName_S"));
		// ��s�x�X�J�i���̂̐ݒ�
		bnkMST.setBNK_STN_KANA(req.getParameter("bnkStnKana"));
		// ��s�x�X�������̂̐ݒ�
		bnkMST.setBNK_STN_NAME_K(req.getParameter("bnkStnName_K"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		bnkMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		bnkMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		bnkMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return bnkMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new BankMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ��s��
		if ("nameBank".equals(flag)) {
			searchNameBnk(req, resp);
			// ��s�x�X��
		} else if ("nameBnkStn".equals(flag)) {
			searchNameBnkStn(req, resp);

		}
	}

	/**
	 * ��s������
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchNameBnk(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();
			BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

			BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

			dispatchResultObject(req, resp, logic.getBankName(param));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * ��s�x�X����
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchNameBnkStn(HttpServletRequest req, HttpServletResponse resp) {

		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			BankLogic logic = (BankLogic) container.getComponent(BankLogic.class);

			BnkMstParameter param = (BnkMstParameter) getObjectParameter(req);

			dispatchResultObject(req, resp, logic.getStnName(param));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

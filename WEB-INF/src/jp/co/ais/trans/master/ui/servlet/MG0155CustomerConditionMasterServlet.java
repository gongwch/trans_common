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
 * ���������}�X�^���Servlet (MG0155)
 * 
 * @author ISFnet China
 */
public class MG0155CustomerConditionMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -8394624360629998607L;

	@Override
	protected String getLogicClassName() {
		return "CustomerConditionLogic";
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
		// �����R�[�h�̐ݒ�
		map.put("triCode", req.getParameter("triCode"));
		// ���������R�[�h�̐ݒ�
		map.put("tjkCode", req.getParameter("tjkCode"));
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
		map.put("beginTriSjCode", req.getParameter("beginTriSjCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endTriSjCode", req.getParameter("endTriSjCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {

		// ���̂̏�����
		TRI_SJ_MST triSjMST = new TRI_SJ_MST();
		// ��ЃR�[�h�̐ݒ�
		triSjMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �����R�[�h
		triSjMST.setTRI_CODE(req.getParameter("triCode"));
		// ���������R�[�h
		triSjMST.setTJK_CODE(req.getParameter("tjkCode"));
		// �x���������ߓ�
		triSjMST.setSJC_DATE(req.getParameter("sjcDate"));
		// �x���������ߌ㌎
		triSjMST.setSJR_MON(req.getParameter("sjrMon"));
		// �x�������x����
		triSjMST.setSJP_DATE(req.getParameter("sjpDate"));
		// �x���敪
		triSjMST.setSIHA_KBN(req.getParameter("sihaKbn"));
		// �x�����@
		triSjMST.setSIHA_HOU_CODE(req.getParameter("sihaHouCode"));
		// �U���U�o��s��������
		triSjMST.setFURI_CBK_CODE(req.getParameter("furiCbkCode"));
		// ��s�R�[�h
		triSjMST.setBNK_CODE(req.getParameter("bnkCode"));
		// �x�X�R�[�h
		triSjMST.setBNK_STN_CODE(req.getParameter("bnkStnCode"));
		// �a�����
		triSjMST.setYKN_KBN(req.getParameter("yknKbn"));
		// �����ԍ�
		triSjMST.setYKN_NO(req.getParameter("yknNo"));
		// �������`�J�i
		triSjMST.setYKN_KANA(req.getParameter("yknKana"));
		// �������`
		triSjMST.setYKN_NAME(req.getParameter("yknName"));
		// �����ړI��
		triSjMST.setGS_MKT_CODE(req.getParameter("gsMktCode"));
		// ��d���x�X����
		triSjMST.setGS_STN_NAME(req.getParameter("gsStnName"));
		// ��d����s����
		triSjMST.setGS_BNK_NAME(req.getParameter("gsBnkName"));
		// �x����s����
		triSjMST.setSIHA_BNK_NAME(req.getParameter("sihaBnkName"));
		// �x���x�X����
		triSjMST.setSIHA_STN_NAME(req.getParameter("sihaStnName"));
		// �x����s�Z��
		triSjMST.setSIHA_BNK_ADR(req.getParameter("sihaBnkAdr"));
		// �o�R��s����
		triSjMST.setKEIYU_BNK_NAME(req.getParameter("keiyuBnkName"));
		// �o�R�x�X����
		triSjMST.setKEIYU_STN_NAME(req.getParameter("keiyuStnName"));
		// �o�R��s�Z��
		triSjMST.setKEIYU_BNK_ADR(req.getParameter("keiyuBnkAdr"));
		// ���l�Z��
		triSjMST.setUKE_ADR(req.getParameter("ukeAdr"));
		// �U���萔���敪
		int furiTesuKen = Integer.parseInt(req.getParameter("furiTesuKen"));
		// �U���萔���敪
		triSjMST.setFURI_TESU_KBN(furiTesuKen);
		// �萔���敪
		int gsTesuKbn = Integer.parseInt(req.getParameter("gsTesuKbn"));
		// �萔���敪
		triSjMST.setGS_TESU_KBN(gsTesuKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		triSjMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		triSjMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		triSjMST.setPRG_ID(req.getParameter("prgID"));

		// ���ʂ�Ԃ�
		return triSjMST;
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		if ("defaultPaymentCondition".equals(req.getParameter("flag"))) {
			searchDefaultPaymentCondition(req, resp);
		} else if ("paymentConditionInfo".equals(req.getParameter("flag"))) {
			searchPaymentConditionInfo(req, resp);
		} else if ("refPaymentCondition".equals(req.getParameter("flag"))) {
			searchRefPaymentName(req, resp);
		} else if ("findOneInfo".equals(req.getParameter("flag"))) {
			findOneInfo(req, resp);
		} else {
			searchCheckNaiCode(req, resp);
		}
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new CustomerConditionMasterReportExcelDefine();
	}

	/**
	 * �����f�[�^���擾����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void findOneInfo(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// ��ЃR�[�h
		String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

		// �����R�[�h
		String triCode = Util.avoidNull(req.getParameter("triCode"));

		// ���������R�[�h
		String tjkCode = Util.avoidNull(req.getParameter("triSjCode"));

		List lst = new ArrayList();
		lst.add(logic.findOneInfo(kaiCode, triCode, tjkCode));
		dispatchResultListObject(req, resp, lst);

	}

	/**
	 * �f�t�H���g�̎x�������擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchDefaultPaymentCondition(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CustomerConditionLogic logic = (CustomerConditionLogic) container
				.getComponent(CustomerConditionLogic.class);

			// ��ЃR�[�h
			String kaiCode = Util.avoidNull(req.getParameter("kaiCode"));

			// �����R�[�h
			String triCode = Util.avoidNull(req.getParameter("triCode"));

			// �ʉ݃R�[�h
			String curCode = Util.avoidNull(req.getParameter("curCode"));

			dispatchResultMap(req, resp, logic.getDefaultPaymentCondition(kaiCode, triCode, curCode));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * �x��������R�[�h�ɕR�t���������擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchPaymentConditionInfo(HttpServletRequest req, HttpServletResponse resp) {
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CustomerConditionLogic logic = (CustomerConditionLogic) container
				.getComponent(CustomerConditionLogic.class);

			Map<String, String> map = new HashMap<String, String>();
			// ��ЃR�[�h
			map.put("kaiCode", Util.avoidNull(req.getParameter("kaiCode")));

			// �����R�[�h
			map.put("triCode", Util.avoidNull(req.getParameter("triCode")));

			// ���������R�[�h
			map.put("tjkCode", Util.avoidNull(req.getParameter("tjkCode")));

			// �`�[���t
			map.put("slipDate", Util.avoidNull(req.getParameter("slipDate")));

			dispatchResultMap(req, resp, logic.getPaymentConditionInfo(map));

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * �x�������R�[�h����
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchCheckNaiCode(HttpServletRequest req, HttpServletResponse resp) {
		// flag: "checkNaiCode"
		try {

			S2Container container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CommonLogic logic = (CommonLogic) container.getComponent("PaymentMethodLogic");
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("hohHohCode", req.getParameter("hohHohCode"));

			// ���̂̏�����
			AP_HOH_MST entity = (AP_HOH_MST) logic.findOne(keys);
			if (entity != null) {
				// �x�������R�[�h�̎擾
				Map data = new HashMap();
				data.put("hOH_NAI_CODE", entity.getHOH_NAI_CODE());
				// ���ʂ̐ݒ�
				super.dispatchResultMap(req, resp, data);
			}
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/**
	 * �x����������
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchRefPaymentName(HttpServletRequest req, HttpServletResponse resp) {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CustomerConditionLogic logic = (CustomerConditionLogic) container.getComponent(CustomerConditionLogic.class);

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// �����R�[�h
		String triCode = req.getParameter("triCode");

		// ���������R�[�h
		String tjkCode = req.getParameter("tjkCode");

		// ���ʂ��擾����B
		List list = logic.searchName(kaiCode, triCode, tjkCode);

		dispatchResultList(req, resp, list);
	}
}

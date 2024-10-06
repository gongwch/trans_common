package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import java.text.*;

import javax.servlet.http.*;

import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.CurrencyLogic;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �ʉ݃}�X�^���Servlet (MG0300)
 * 
 * @author AIS
 */
public class MG0300CurrencyMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 2192504901187041314L;

	@Override
	protected String getLogicClassName() {
		return "CurrencyLogic";
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
		// �ʉ݃R�[�h�̐ݒ�
		map.put("curCode", req.getParameter("curCode"));
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
		map.put("beginCurCode", req.getParameter("beginCurCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endCurCode", req.getParameter("endCurCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		CUR_MST curMST = new CUR_MST();
		// ��ЃR�[�h�̐ݒ�
		curMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �ʉݺ��ނ̐ݒ�
		curMST.setCUR_CODE(req.getParameter("curCode"));
		// �ʉݖ��̂̐ݒ�
		curMST.setCUR_NAME(req.getParameter("curName"));
		// �ʉݗ��̂̐ݒ�
		curMST.setCUR_NAME_S(req.getParameter("curName_S"));
		// �ʉ݌������̂̐ݒ�
		curMST.setCUR_NAME_K(req.getParameter("curName_K"));
		// ���[�g�W���̎擾
		int decKeta = Integer.parseInt(req.getParameter("decKeta"));
		// �����_�ȉ������̎擾
		int ratePow = Integer.parseInt(req.getParameter("ratePow"));
		// ���Z�敪�̎擾
		int convKbn = Integer.parseInt(req.getParameter("convKbn"));
		// ���[�g�W���̐ݒ�
		curMST.setDEC_KETA(decKeta);
		// �����_�ȉ������̐ݒ�
		curMST.setRATE_POW(ratePow);
		// ���Z�敪�̐ݒ�
		curMST.setCONV_KBN(convKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		curMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		curMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		curMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return curMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new CurrencyMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refCurrency".equals(flag)) {
			searchRefCurrency(req, resp);
		}
	}

	/**
	 * �ʉݏ�񌟍�
	 * 
	 * @param req
	 * @param resp
	 */
	protected void searchRefCurrency(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		try {
			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			CurrencyLogic logic = (CurrencyLogic) container.getComponent(CurrencyLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("curCode", req.getParameter("curCode"));

			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);
		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

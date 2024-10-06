package jp.co.ais.trans.master.ui.servlet;

import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * �����d��Ȗڃ}�X�^���Servlet (MG0430)
 * 
 * @author ISFnet China
 */
public class MG0430AutomaticJournalizingItemMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -2031916415598549009L;

	@Override
	protected String getLogicClassName() {
		return "AutomaticJournalizingItemLogic";
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
		// �Ȗڐ���敪�̐ݒ�
		map.put("kmkCnt", req.getParameter("kmkCnt"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �Ȗڐ���敪�̐ݒ�
		map.put("kmkCnt", req.getParameter("kmkCnt"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginKmkCnt", req.getParameter("beginKmkCnt"));
		// �I���R�[�h�̐ݒ�
		map.put("endKmkCnt", req.getParameter("endKmkCnt"));
		// ���ʂ�Ԃ�
		return map;
	}

	/**
	 * ����p
	 * 
	 * @param req
	 * @param resp
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {

		// ����pflag
		String flag = req.getParameter("flag");

		// ����
		if ("findKmkCntName".equals(flag)) {
			findKmkCntName(req, resp);
		}
	}

	/**
	 * �Ȗڐ���敪�E�Ȗڐ��䖼�̂̎擾
	 * 
	 * @param req
	 * @param resp
	 */
	protected void findKmkCntName(HttpServletRequest req, HttpServletResponse resp) {

		S2Container container = null;

		try {

			container = SingletonS2ContainerFactory.getContainer();

			CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());

			Map conditions = this.getFindConditions(req);
			List list = logic.find(conditions);

			// �Ȗڐ���敪�E�Ȗڐ���R�[�h��Map�ɃZ�b�g
			Map map = new TreeMap();
			int intKmkCnt = 0;
			String kmkCnt = null;
			String kmkCntName = null;

			Iterator iterator = list.iterator();
			for (int row = 0; iterator.hasNext(); row++) {
				SWK_KMK_MST value = (SWK_KMK_MST) iterator.next();
				intKmkCnt = value.getKMK_CNT();

				// String�֕ϊ�
				kmkCnt = Integer.toString(intKmkCnt);
				kmkCntName = value.getKMK_CNT_NAME();

				map.put(kmkCnt, kmkCntName);
			}

			super.dispatchResultMap(req, resp, map);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		SWK_KMK_MST swkKmkMST = new SWK_KMK_MST();
		// ��ЃR�[�h�̐ݒ�
		swkKmkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �Ȗڐ���敪�̎擾
		int kmkCnt = Integer.parseInt(req.getParameter("kmkCnt"));
		// �Ȗڐ���敪�̐ݒ�
		swkKmkMST.setKMK_CNT(kmkCnt);
		// �Ȗڐ��䖼�̂̐ݒ�
		swkKmkMST.setKMK_CNT_NAME(req.getParameter("kmkCntName"));
		// �ȖڃR�[�h�̐ݒ�
		swkKmkMST.setKMK_CODE(req.getParameter("kmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		swkKmkMST.setHKM_CODE(req.getParameter("hkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		swkKmkMST.setUKM_CODE(req.getParameter("ukmCode"));
		// �v�㕔��R�[�h�̐ݒ�
		swkKmkMST.setDEP_CODE(req.getParameter("depCode"));
		// ���ʂ�Ԃ�
		return swkKmkMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return null;
	}
}

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
 * �E�v�}�X�^���Servlet (MG0120)
 * 
 * @author ISFnet China
 */
public class MG0120MemoMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -8767366359332459952L;

	@Override
	protected String getLogicClassName() {
		return "MemoLogic";
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
		// �E�v�R�[�h�̐ݒ�
		map.put("tekCode", req.getParameter("tekCode"));
		// �E�v�敪�̐ݒ�
		map.put("tekKbn", req.getParameter("tekKbn"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �E�v�敪�̐ݒ�
		map.put("tekKbn", req.getParameter("tekKbn"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginTekCode", req.getParameter("beginTekCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endTekCode", req.getParameter("endTekCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		TEK_MST tekMST = new TEK_MST();
		// ��ЃR�[�h�̐ݒ�
		tekMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �ް��敪�̐ݒ�
		tekMST.setDATA_KBN(req.getParameter("dataKbn"));
		// �E�v�R�[�h�̐ݒ�
		tekMST.setTEK_CODE(req.getParameter("tekCode"));
		// �E�v���̂̐ݒ�
		tekMST.setTEK_NAME(req.getParameter("tekName"));
		// �E�v�������̂̐ݒ�
		tekMST.setTEK_NAME_K(req.getParameter("tekName_K"));
		// �E�v�敪�̎擾
		int tekKbn = Integer.parseInt(req.getParameter("tekKbn"));
		// �E�v�敪�̐ݒ�
		tekMST.setTEK_KBN(tekKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		tekMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		tekMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		tekMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return tekMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new MemoMasterReportExcelDefine();
	}

	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refMemo".equals(flag)) {
			searchRefMemo(req, resp);
		}
	}

	private void searchRefMemo(HttpServletRequest req, HttpServletResponse resp) {
		// container�̏�����
		S2Container container = null;

		// flag: "checkNaiCode"
		try {

			container = SingletonS2ContainerFactory.getContainer();
			// logic�̏�����
			MemoLogic logic = (MemoLogic) container.getComponent(MemoLogic.class);
			// keys�̏�����
			Map keys = new HashMap();
			keys.put("kaiCode", req.getParameter("kaiCode"));
			keys.put("tekCode", req.getParameter("tekCode"));
			keys.put("tekKbn", req.getParameter("tekKbn"));

			// ���̂̏�����
			List list = logic.getREFItems(keys);

			super.dispatchResultList(req, resp, list);

		} catch (Throwable ex) {
			super.dispatchError(req, resp, ex.getMessage());
		}
	}
}

package jp.co.ais.trans.master.ui.servlet;

import java.io.*;
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
import jp.co.ais.trans2.model.check.*;

/**
 * �Ǘ�6�}�X�^���Servlet (MG0230)
 * 
 * @author ISFnet China
 */
public class MG0230Management6MasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -6930625653179511008L;

	@Override
	protected String getLogicClassName() {
		return "Management6Logic";
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
		// �Ǘ��R�[�h�̐ݒ�
		map.put("knrCode", req.getParameter("knrCode"));
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
		map.put("beginknrCode", req.getParameter("beginknrCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endknrCode", req.getParameter("endknrCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		KNR6_MST knr6MST = new KNR6_MST();
		// ��ЃR�[�h�̐ݒ�
		knr6MST.setKAI_CODE(req.getParameter("kaiCode"));
		// �Ǘ��U���ނ̐ݒ�
		knr6MST.setKNR_CODE_6(req.getParameter("knrCode"));
		// �Ǘ��U���̂̐ݒ�
		knr6MST.setKNR_NAME_6(req.getParameter("knrName"));
		// �Ǘ��U���̂̐ݒ�
		knr6MST.setKNR_NAME_S_6(req.getParameter("knrName_S"));
		// �Ǘ��U�������̂̐ݒ�
		knr6MST.setKNR_NAME_K_6(req.getParameter("knrName_K"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		knr6MST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		knr6MST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		knr6MST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return knr6MST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new Management6MasterReportExcelDefine();
	}

	/**
	 * �폜
	 * 
	 * @param req
	 */
	@Override
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws IOException, ParseException {
		S2Container container = SingletonS2ContainerFactory.getContainer();

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.MANAGEMENT_6);
		condition.setCompanyCode(req.getParameter("kaiCode"));
		condition.setManagement6Code(req.getParameter("knrCode"));

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) container.getComponent(CheckMasterUseDao.class);
		try {
			cd.check(condition);
		} catch (TException e) {
			throw new TRuntimeException(e.getMessage());
		}

		CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
		Map keys = this.getPrimaryKeys(req);
		logic.delete(keys);

		super.dispatchResultSuccess(req, resp);
	}
}

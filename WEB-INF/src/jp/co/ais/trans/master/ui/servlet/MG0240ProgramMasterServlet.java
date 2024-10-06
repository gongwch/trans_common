package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import org.seasar.framework.container.*;
import org.seasar.framework.container.factory.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �v���O�����}�X�^���Servlet (MG0240)
 * 
 * @author ISFnet China
 */
public class MG0240ProgramMasterServlet extends MasterServletBase {

	@Override
	protected String getLogicClassName() {
		return "ProgramLogic";
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
		// �V�X�e���R�[�h�̐ݒ�
		map.put("sysCode", req.getParameter("sysCode"));
		// �v���O�����R�[�h�̐ݒ�
		map.put("prgCode", req.getParameter("prgCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �V�X�e���R�[�h�̐ݒ�
		map.put("sysCode", req.getParameter("sysCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginPrgCode", req.getParameter("beginPrgCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endPrgCode", req.getParameter("endPrgCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		PRG_MST prgMST = new PRG_MST();
		// ��ЃR�[�h�̐ݒ�
		prgMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �v���O�����R�[�h
		prgMST.setPRG_CODE(req.getParameter("prgCode"));
		// �v���O��������
		prgMST.setPRG_NAME(req.getParameter("prgName"));
		// �v���O��������
		prgMST.setPRG_NAME_S(req.getParameter("prgName_S"));
		// �v���O������������
		prgMST.setPRG_NAME_K(req.getParameter("prgName_K"));
		// �V�X�e���R�[�h
		prgMST.setSYS_CODE(req.getParameter("sysCode"));
		// �������x��
		String ken1 = req.getParameter("ken");

		if (!Util.isNullOrEmpty(ken1)) {
			// �������x��
			prgMST.setKEN(new Integer(ken1));
		}

		// �R�����g
		prgMST.setCOM(req.getParameter("com"));
		// ۰��Ӽޭ��̧�ٖ�
		prgMST.setLD_NAME(req.getParameter("ldName"));
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		prgMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		prgMST.setEND_DATE(endDate);
		// �e�v���O�����R�[�h
		prgMST.setPARENT_PRG_CODE(req.getParameter("parentPrgCode"));
		// ���j���[�敪�̎擾
		String menuKbn1 = req.getParameter("menuKbn");
		if (!(menuKbn1 == null || menuKbn1.trim().length() == 0)) {
			// ���j���[�敪�̏�����
			int menuKbn = Integer.parseInt(menuKbn1);
			// ���j���[�敪�̐ݒ�
			prgMST.setMENU_KBN(menuKbn);
		} else {
			// ���j���[�敪�̏�����
			int temp = 0;
			// ���j���[�敪�̐ݒ�
			prgMST.setMENU_KBN(temp);
		}

		// �\�������̐ݒ�
		String dispIndex = req.getParameter("dispIndex");
		if (!(dispIndex == null || dispIndex.trim().length() == 0)) {
			prgMST.setDISP_INDEX(Integer.parseInt(dispIndex));
		} else {
			// �\�������̏�����
			int temp = 0;
			// �\�������̐ݒ�
			prgMST.setDISP_INDEX(temp);
		}

		// �v���O����ID�̐ݒ�
		prgMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return prgMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new ProgramMasterReportExcelDefine();
	}

	/**
	 * ���̑��̉�ʌʂ̃��W�b�N
	 * 
	 * @param req
	 * @param resp
	 */
	protected void other(HttpServletRequest req, HttpServletResponse resp) {

		String flag = req.getParameter("flag");

		// ���[�U����
		if ("searchPrgList".equals(flag)) {
			searchPrg(req, resp);
		}
	}

	/**
	 * ��Еʃ��[�U���X�g�擾
	 * 
	 * @param req
	 * @param resp
	 */
	private void searchPrg(HttpServletRequest req, HttpServletResponse resp) {
		try {
			S2Container container = SingletonS2ContainerFactory.getContainer();

			CommonLogic logic = (CommonLogic) container.getComponent(getLogicClassName());
			TUserInfo userInfo = refTServerUserInfo(req);

			// map�̏�����(��ЃR�[�h���Z�b�g�j
			Map map = new HashMap();
			map.put("kaiCode", userInfo.getCompanyCode());

			// ��ЃR�[�h�Ń��[�U���X�g���K��
			List dtoList;

			dtoList = logic.find(map);

			super.dispatchResultDtoList(req, resp, dtoList);
		} catch (ParseException e) {
			ServerErrorHandler.handledException(e);
		}

	}
}

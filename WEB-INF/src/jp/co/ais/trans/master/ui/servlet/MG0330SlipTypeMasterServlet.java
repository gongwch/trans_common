package jp.co.ais.trans.master.ui.servlet;

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
 * �`�[��ʃ}�X�^���Servlet (MG0330)
 * 
 * @author ISFnet China
 */
public class MG0330SlipTypeMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -8074345142450019509L;

	@Override
	protected String getLogicClassName() {
		return "SlipTypeLogic";
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
		// �`�[��ʺ��ނ̐ݒ�
		map.put("denSyuCode", req.getParameter("denSyuCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �`�[��ʺ��ނ̐ݒ�
		map.put("denSyuCode", req.getParameter("denSyuCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginDenSyuCode", req.getParameter("beginDenSyuCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endDenSyuCode", req.getParameter("endDenSyuCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) {
		// ���̂̏�����
		DEN_SYU_MST denSyuMST = new DEN_SYU_MST();
		// ��ЃR�[�h�̐ݒ�
		denSyuMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �`�[��ʺ��ނ̐ݒ�
		denSyuMST.setDEN_SYU_CODE(req.getParameter("denSyuCode"));
		// �V�X�e���敪 �̐ݒ�
		denSyuMST.setSYS_KBN(req.getParameter("sysKbn"));
		// �`�[��ʖ��̂̐ݒ�
		denSyuMST.setDEN_SYU_NAME(req.getParameter("denSyuName"));
		// �`�[��ʗ��̂̐ݒ�
		denSyuMST.setDEN_SYU_NAME_S(req.getParameter("denSyuName_S"));
		// ���[�^�C�g���̐ݒ�
		denSyuMST.setDEN_SYU_NAME_K(req.getParameter("denSyuName_K"));
		// �ް��敪�̐ݒ�
		denSyuMST.setDATA_KBN(req.getParameter("dataKbn"));
		// �����ы敪�̎擾
		int taSysKbn = Integer.parseInt(req.getParameter("taSysKbn"));
		// �����ы敪�̐ݒ�
		denSyuMST.setTA_SYS_KBN(taSysKbn);
		// �`�[�ԍ��̔��׸ނ̎擾
		int datSaibanFlg = Integer.parseInt(req.getParameter("datSaiBanFlg"));
		// �`�[�ԍ��̔��׸ނ̐ݒ�
		denSyuMST.setDAT_SAIBAN_FLG(datSaibanFlg);
		// ����P�ʂ̎擾
		int tani = Integer.parseInt(req.getParameter("tani"));
		// ����P�ʂ̐ݒ�
		denSyuMST.setTANI(tani);
		// ����Ōv�Z�敪�̎擾
		int zeiKbn = Integer.parseInt(req.getParameter("zeiKbn"));
		// ����Ōv�Z�敪�̐ݒ�
		denSyuMST.setZEI_KBN(zeiKbn);
		// �d��C���^�[�t�F�[�X�敪�̎擾
		int swkInKbn = Integer.parseInt(req.getParameter("swkInKbn"));
		// �d��C���^�[�t�F�[�X�敪�̐ݒ�
		denSyuMST.setSWK_IN_KBN(swkInKbn);
		// �v���O����ID�̐ݒ�
		denSyuMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return denSyuMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new SlipTypeMasterReportExcelDefine();
	}

	/**
	 * ���̑��̏ꍇ
	 */
	@Override
	protected void other(HttpServletRequest req, HttpServletResponse resp) {
		// ����pflag
		String flag = req.getParameter("flag");
		// ����
		if ("refSlip".equals(flag)) {
			searchRefSlip(req, resp);
		}
	}

	/**
	 * ��������ʃf�[�^�擾����
	 * 
	 * @param req ���N�G�X�g
	 * @param resp ���X�|���X
	 */
	private void searchRefSlip(HttpServletRequest req, HttpServletResponse resp) {

		// ��ЃR�[�h
		String kaiCode = req.getParameter("kaiCode");

		// true:���V�X�e�������荞�񂾓`�[��ʂ�ΏۂƂ���
		boolean isIncludeSystemEls = BooleanUtil.toBoolean(req.getParameter("isIncludeSystemEls"));

		S2Container s2Container = SingletonS2ContainerFactory.getContainer();

		SlipTypeLogic logic = (SlipTypeLogic) s2Container.getComponent(SlipTypeLogic.class);

		// �`�[��ʗ��̃��X�g
		List lstDenSyuNames = logic.getDenSyuNames(kaiCode, isIncludeSystemEls);

		super.dispatchResultDtoList(req, resp, lstDenSyuNames);
	}

}

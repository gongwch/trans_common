package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * ����Ȗڃ}�X�^���Servlet (MG0100)
 * 
 * @author ISFnet China
 */
public class MG0100BreakDownItemMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -1562841742271715056L;

	@Override
	protected String getLogicClassName() {
		return "BreakDownItemLogic";
	}

	protected String getREFKeyFields() {
		return "kaiCode,kmkCode,hkmCode";
	}

	/** ��L�[�̎擾 */
	protected Map getPrimaryKeys(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �ȖڃR�[�h�̐ݒ�
		map.put("kmkCode", req.getParameter("kmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		map.put("hkmCode", req.getParameter("hkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		map.put("ukmCode", req.getParameter("ukmCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �ȖڃR�[�h�̐ݒ�
		map.put("kmkCode", req.getParameter("kmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		map.put("hkmCode", req.getParameter("hkmCode"));
		// �J�n�R�[�h�̐ݒ�
		map.put("beginUkmCode", req.getParameter("beginUkmCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endUkmCode", req.getParameter("endUkmCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		UKM_MST ukmMST = new UKM_MST();
		// ��ЃR�[�h�̐ݒ�
		ukmMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �ȖڃR�[�h�̐ݒ�
		ukmMST.setKMK_CODE(req.getParameter("kmkCode"));
		// �⏕�ȖڃR�[�h�̐ݒ�
		ukmMST.setHKM_CODE(req.getParameter("hkmCode"));
		// ����ȖڃR�[�h�̐ݒ�
		ukmMST.setUKM_CODE(req.getParameter("ukmCode"));
		// ����Ȗږ��̂̐ݒ�
		ukmMST.setUKM_NAME(req.getParameter("ukmName"));
		// ����Ȗڗ��̂̐ݒ�
		ukmMST.setUKM_NAME_S(req.getParameter("ukmName_S"));
		// ����Ȗڌ������̂̐ݒ�
		ukmMST.setUKM_NAME_K(req.getParameter("ukmName_K"));
		// ����ŃR�[�h�̐ݒ�
		ukmMST.setZEI_CODE(req.getParameter("zeiCode"));
		// �����`�[�����׸ނ̎擾
		int glFlg1 = Integer.parseInt(req.getParameter("glFlg1"));
		// �o���`�[�����׸ނ̎擾
		int glFlg2 = Integer.parseInt(req.getParameter("glFlg2"));
		// �U�֓`�[�����׸ނ̎擾
		int glFlg3 = Integer.parseInt(req.getParameter("glFlg3"));
		// �o��Z�`�[�����׸ނ̎擾
		int apFlg1 = Integer.parseInt(req.getParameter("apFlg1"));
		// �������`�[�����׸ނ̎擾
		int apFlg2 = Integer.parseInt(req.getParameter("apFlg2"));
		// ���v��`�[�����׸ނ̎擾
		int arFlg1 = Integer.parseInt(req.getParameter("arFlg1"));
		// �������`�[�����׸ނ̎擾
		int arFlg2 = Integer.parseInt(req.getParameter("arFlg2"));
		// ���Y�v��`�[�����׸ނ̎擾
		int faFlg1 = Integer.parseInt(req.getParameter("faFlg1"));
		// �x���˗��`�[�����׸ނ̎擾
		int faFlg2 = Integer.parseInt(req.getParameter("faFlg2"));
		// �������̓t���O�̎擾
		int triCodeFlg = Integer.parseInt(req.getParameter("triCodeFlg"));
		// �����������׸ނ̎擾
		int hasFlg = Integer.parseInt(req.getParameter("hasFlg"));
		// �Ј����̓t���O�̎擾
		int empCodeFlg = Integer.parseInt(req.getParameter("empCodeFlg"));
		// �Ǘ��P���̓t���O�̎擾
		int knrFlg1 = Integer.parseInt(req.getParameter("knrFlg1"));
		// �Ǘ�2���̓t���O�̎擾
		int knrFlg2 = Integer.parseInt(req.getParameter("knrFlg2"));
		// �Ǘ�3���̓t���O�̎擾
		int knrFlg3 = Integer.parseInt(req.getParameter("knrFlg3"));
		// �Ǘ�4���̓t���O�̎擾
		int knrFlg4 = Integer.parseInt(req.getParameter("knrFlg4"));
		// �Ǘ�5���̓t���O�̎擾
		int knrFlg5 = Integer.parseInt(req.getParameter("knrFlg5"));
		// �Ǘ�6���̓t���O�̎擾
		int knrFlg6 = Integer.parseInt(req.getParameter("knrFlg6"));
		// ���v1���̓t���O�̎擾
		int hmFlg1 = Integer.parseInt(req.getParameter("hmFlg1"));
		// ���v2���̓t���O�̎擾
		int hmFlg2 = Integer.parseInt(req.getParameter("hmFlg2"));
		// ���v3���̓t���O�̎擾
		int hmFlg3 = Integer.parseInt(req.getParameter("hmFlg3"));
		// ����ېœ��̓t���O�̎擾
		int uriZeiFlg = Integer.parseInt(req.getParameter("uriZeiFlg"));
		// �d���ېœ��̓t���O�̎擾
		int sirZeiFlg = Integer.parseInt(req.getParameter("sirZeiFlg"));
		// ���ʉݓ��̓t���O�̎擾
		int mcrFlg = Integer.parseInt(req.getParameter("mcrFlg"));
		// �]���֑Ώۃt���O�̎擾
		int excFlg = Integer.parseInt(req.getParameter("excFlg"));
		// �����`�[�����׸ނ̐ݒ�
		ukmMST.setGL_FLG_1(glFlg1);
		// �o���`�[�����׸ނ̐ݒ�
		ukmMST.setGL_FLG_2(glFlg2);
		// �U�֓`�[�����׸ނ̐ݒ�
		ukmMST.setGL_FLG_3(glFlg3);
		// �o��Z�`�[�����׸ނ̐ݒ�
		ukmMST.setAP_FLG_1(apFlg1);
		// �������`�[�����׸ނ̐ݒ�
		ukmMST.setAP_FLG_2(apFlg2);
		// ���v��`�[�����׸ނ̐ݒ�
		ukmMST.setAR_FLG_1(arFlg1);
		// �������`�[�����׸ނ̐ݒ�
		ukmMST.setAR_FLG_2(arFlg2);
		// ���Y�v��`�[�����׸ނ̐ݒ�
		ukmMST.setFA_FLG_1(faFlg1);
		// �x���˗��`�[�����׸ނ̐ݒ�
		ukmMST.setFA_FLG_2(faFlg2);
		// �������̓t���O�̐ݒ�
		ukmMST.setTRI_CODE_FLG(triCodeFlg);
		// �����������׸ނ̐ݒ�
		ukmMST.setHAS_FLG(hasFlg);
		// �Ј����̓t���O�̐ݒ�
		ukmMST.setEMP_CODE_FLG(empCodeFlg);
		// �Ǘ��P���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_1(knrFlg1);
		// �Ǘ�2���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_2(knrFlg2);
		// �Ǘ�3���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_3(knrFlg3);
		// �Ǘ�4���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_4(knrFlg4);
		// �Ǘ�5���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_5(knrFlg5);
		// �Ǘ�6���̓t���O�̐ݒ�
		ukmMST.setKNR_FLG_6(knrFlg6);
		// ���v1���̓t���O�̐ݒ�
		ukmMST.setHM_FLG_1(hmFlg1);
		// ���v2���̓t���O�̐ݒ�
		ukmMST.setHM_FLG_2(hmFlg2);
		// ���v3���̓t���O�̐ݒ�
		ukmMST.setHM_FLG_3(hmFlg3);
		// ����ېœ��̓t���O�̐ݒ�
		ukmMST.setURI_ZEI_FLG(uriZeiFlg);
		// �d���ېœ��̓t���O�̐ݒ�
		ukmMST.setSIR_ZEI_FLG(sirZeiFlg);
		// ���ʉݓ��̓t���O�̐ݒ�
		ukmMST.setMCR_FLG(mcrFlg);
		// �]���֑Ώۃt���O�̐ݒ�
		ukmMST.setEXC_FLG(excFlg);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		ukmMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		ukmMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		ukmMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return ukmMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new BreakDownItemMasterReportExcelDefine();
	}
}

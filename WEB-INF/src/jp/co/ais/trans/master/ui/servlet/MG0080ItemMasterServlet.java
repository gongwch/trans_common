package jp.co.ais.trans.master.ui.servlet;

import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.report.*;

/**
 * �Ȗڃ}�X�^���Servlet (MG0080)
 * 
 * @author ISFnet China
 */
public class MG0080ItemMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = 7511245514808750938L;

	@Override
	protected String getLogicClassName() {
		return "ItemLogic";
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
		// �ȖڃR�[�h�̐ݒ�
		map.put("kmkCode", req.getParameter("kmkCode"));
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
		map.put("beginKmkCode", req.getParameter("beginKmkCode"));
		// �I���R�[�h�̐ݒ�
		map.put("endKmkCode", req.getParameter("endKmkCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		KMK_MST kmkMST = new KMK_MST();
		ServerLogger.debug("BS" + req.getParameter("kesiKbn"));
		ServerLogger.debug("OTHER" + req.getParameter("excFlg"));

		// ��ЃR�[�h�̐ݒ�
		kmkMST.setKAI_CODE(req.getParameter("kaiCode"));
		// �ȖڃR�[�h�̐ݒ�
		kmkMST.setKMK_CODE(req.getParameter("kmkCode"));
		// �Ȗږ��̂̐ݒ�
		kmkMST.setKMK_NAME(req.getParameter("kmkName"));
		// �Ȗڗ��̂̐ݒ�
		kmkMST.setKMK_NAME_S(req.getParameter("kmkName_S"));
		// �Ȗڌ������̂̐ݒ�
		kmkMST.setKMK_NAME_K(req.getParameter("kmkName_K"));
		// �Ȗڎ�ʂ̐ݒ�
		kmkMST.setKMK_SHU(req.getParameter("kmkShu"));
		// �f�k�Ȗڐ���敪�̐ݒ�
		kmkMST.setKMK_CNT_GL(req.getParameter("kmkCntGl"));
		// AP�Ȗڐ���敪�̐ݒ�
		kmkMST.setKMK_CNT_AP(req.getParameter("kmkCntAp"));
		// AR�Ȗڐ���敪�̐ݒ�
		kmkMST.setKMK_CNT_AR(req.getParameter("kmkCntAr"));
		// �Œ蕔�庰�ނ̐ݒ�
		kmkMST.setKOTEI_DEP_CODE(req.getParameter("koteiDepCode"));
		// ����ŃR�[�h�̐ݒ�
		kmkMST.setZEI_CODE(req.getParameter("zeiCode"));
		// BG�Ȗڐ���敪�̐ݒ�
		kmkMST.setKMK_CNT_BG(req.getParameter("kmkCntBg"));
		// �ؕ������R�[�h�̐ݒ�
		kmkMST.setSKN_CODE_DR(req.getParameter("sknCodeDr"));
		// �ݕ������R�[�h�̐ݒ�
		kmkMST.setSKN_CODE_CR(req.getParameter("sknCodeCr"));
		// ���E�Ȗڐ���敪�̐ݒ�
		kmkMST.setKMK_CNT_SOUSAI(req.getParameter("kmkCntSousai"));
		// �W�v�敪�̎擾
		int sumKbn = Integer.parseInt(req.getParameter("sumKbn"));
		// �ݎ؋敪�̎擾
		int dcKbn = Integer.parseInt(req.getParameter("dcKbn"));
		// �⏕�敪�̎擾
		int hkmKbn = Integer.parseInt(req.getParameter("hkmKbn"));
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
		// BS��������敪�̎擾 2007/02/15
		int kesiKbn = Integer.parseInt(req.getParameter("kesiKbn"));
		// �W�v�敪�̐ݒ�
		kmkMST.setSUM_KBN(sumKbn);
		// �ݎ؋敪�̐ݒ�
		kmkMST.setDC_KBN(dcKbn);
		// �⏕�敪�̐ݒ�
		kmkMST.setHKM_KBN(hkmKbn);
		// �����`�[�����׸ނ̐ݒ�
		kmkMST.setGL_FLG_1(glFlg1);
		// �o���`�[�����׸ނ̐ݒ�
		kmkMST.setGL_FLG_2(glFlg2);
		// �U�֓`�[�����׸ނ̐ݒ�
		kmkMST.setGL_FLG_3(glFlg3);
		// �o��Z�`�[�����׸ނ̐ݒ�
		kmkMST.setAP_FLG_1(apFlg1);
		// �������`�[�����׸ނ̐ݒ�
		kmkMST.setAP_FLG_2(apFlg2);
		// ���v��`�[�����׸ނ̐ݒ�
		kmkMST.setAR_FLG_1(arFlg1);
		// �������`�[�����׸ނ̐ݒ�
		kmkMST.setAR_FLG_2(arFlg2);
		// ���Y�v��`�[�����׸ނ̐ݒ�
		kmkMST.setFA_FLG_1(faFlg1);
		// �x���˗��`�[�����׸ނ̐ݒ�
		kmkMST.setFA_FLG_2(faFlg2);
		// �������̓t���O�̐ݒ�
		kmkMST.setTRI_CODE_FLG(triCodeFlg);
		// �����������׸ނ̐ݒ�
		kmkMST.setHAS_FLG(hasFlg);
		// �Ј����̓t���O�̐ݒ�
		kmkMST.setEMP_CODE_FLG(empCodeFlg);
		// �Ǘ��P���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_1(knrFlg1);
		// �Ǘ�2���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_2(knrFlg2);
		// �Ǘ�3���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_3(knrFlg3);
		// �Ǘ�4���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_4(knrFlg4);
		// �Ǘ�5���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_5(knrFlg5);
		// �Ǘ�6���̓t���O�̐ݒ�
		kmkMST.setKNR_FLG_6(knrFlg6);
		// ���v1���̓t���O�̐ݒ�
		kmkMST.setHM_FLG_1(hmFlg1);
		// ���v2���̓t���O�̐ݒ�
		kmkMST.setHM_FLG_2(hmFlg2);
		// ���v3���̓t���O�̐ݒ�
		kmkMST.setHM_FLG_3(hmFlg3);
		// ����ېœ��̓t���O�̐ݒ�
		kmkMST.setURI_ZEI_FLG(uriZeiFlg);
		// �d���ېœ��̓t���O�̐ݒ�
		kmkMST.setSIR_ZEI_FLG(sirZeiFlg);
		// ���ʉݓ��̓t���O�̐ݒ�
		kmkMST.setMCR_FLG(mcrFlg);
		// �]���֑Ώۃt���O�̐ݒ�
		kmkMST.setEXC_FLG(excFlg);
		// BS��������敪�̐ݒ� 2007/02/15 Y
		kmkMST.setKESI_KBN(kesiKbn);
		// �J�n�N�����̏�����
		Date strDate = null;
		// �I���N�����̏�����
		Date endDate = null;
		// �J�n�N�����̎擾
		strDate = DateUtil.toYMDDate(req.getParameter("strDate"));
		// �I���N�����̎擾
		endDate = DateUtil.toYMDDate(req.getParameter("endDate"));
		// �J�n�N�����̐ݒ�
		kmkMST.setSTR_DATE(strDate);
		// �I���N�����̐ݒ�
		kmkMST.setEND_DATE(endDate);
		// �v���O����ID�̐ݒ�
		kmkMST.setPRG_ID(req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return kmkMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return new ItemMasterReportExcelDefine();
	}
}

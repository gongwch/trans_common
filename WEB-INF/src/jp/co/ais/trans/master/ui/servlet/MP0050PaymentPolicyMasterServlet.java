package jp.co.ais.trans.master.ui.servlet;

import java.math.*;
import java.text.*;
import java.util.*;

import javax.servlet.http.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.common.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �x�����j�}�X�^���Servlet (MP0050)
 * 
 * @author ISFnet China
 */
public class MP0050PaymentPolicyMasterServlet extends MasterServletBase {

	/** �V���A��UID */
	private static final long serialVersionUID = -3113517618787449938L;

	@Override
	protected String getLogicClassName() {
		return "PaymentPolicyLogic";
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
		// ���ʂ�Ԃ�
		return map;
	}

	/** ���������̎擾 */
	protected Map getFindConditions(HttpServletRequest req) {
		// map�̏�����
		Map map = new HashMap();
		// ��ЃR�[�h�̐ݒ�
		map.put("kaiCode", req.getParameter("kaiCode"));
		// �萔���ȖڃR�[�h�̐ݒ�
		map.put("kmkCode", req.getParameter("kmkCode"));
		// �萔���⏕�ȖڃR�[�h�̐ݒ�
		map.put("hkmCode", req.getParameter("hkmCode"));
		// �萔������ȖڃR�[�h�̐ݒ�
		map.put("ukmCode", req.getParameter("ukmCode"));
		// �萔���v�㕔��R�[�h�̐ݒ�
		map.put("hohDepCode", req.getParameter("hohDepCode"));
		// �萔������ŃR�[�h�̐ݒ�
		map.put("shhTesuZeiCode", req.getParameter("shhTesuZeiCode"));
		// ���ʂ�Ԃ�
		return map;
	}

	/** �G���e�B�e�B�̎擾 */
	protected Object getEntity(HttpServletRequest req) throws ParseException {
		// ���̂̏�����
		AP_SHH_MST apShhMST = new AP_SHH_MST();
		// ��ЃR�[�h�̐ݒ�
		apShhMST.setKAI_CODE( req.getParameter("kaiCode"));
		// �萔���ȖڃR�[�h �̐ݒ�
		apShhMST.setSHH_TESU_KMK_CODE( req.getParameter("shhTesuKmkCode"));
		// �萔���⏕�ȖڃR�[�h�̐ݒ�
		apShhMST.setSHH_TESU_HKM_CODE( req.getParameter("shhTesuHkmCode"));
		// �萔������ȖڃR�[�h�̐ݒ�
		apShhMST.setSHH_TESU_UKM_CODE( req.getParameter("shhTesuUkmCode"));
		// �萔���v�㕔��R�[�h�̐ݒ�
		apShhMST.setSHH_TESU_DEP_CODE( req.getParameter("shhTesuDepCode"));
		// �萔������ŃR�[�h�̐ݒ�
		apShhMST.setSHH_TESU_ZEI_CODE( req.getParameter("shhTesuZeiCode"));
		// �O�������쐬�t���O�̐ݒ�
		apShhMST.setSHH_GS_PRINT_KBN( req.getParameter("shhGsPrintKbn"));
		// �������ԍ��t���O�̐ݒ�
		apShhMST.setSHH_SEI_NO_KBN( req.getParameter("shhSeiNoKbn"));
		// �莞�x���i�P���j�̎擾
		int shhSiha1 = Integer.parseInt( req.getParameter("shhSiha1"));
		// �莞�x���i5���j�̎擾
		int shhSiha5 = Integer.parseInt( req.getParameter("shhSiha5"));
		// �莞�x���i10���j�̎擾
		int shhSiha10 = Integer.parseInt( req.getParameter("shhSiha10"));
		// �莞�x���i15���j�̎擾
		int shhSiha15 = Integer.parseInt( req.getParameter("shhSiha15"));
		// �莞�x���i20���j�̎擾
		int shhSiha20 = Integer.parseInt( req.getParameter("shhSiha20"));
		// �莞�x���i25���j�̎擾
		int shhSiha25 = Integer.parseInt( req.getParameter("shhSiha25"));
		// �莞�x���i�����j�̎擾
		int shhSiha30 = Integer.parseInt( req.getParameter("shhSiha30"));
		// ��s�x���敪�i1���j�̎擾
		int shhBnkKbn1 = Integer.parseInt( req.getParameter("shhBnkKbn1"));
		// ��s�x���敪�i5���j�̎擾
		int shhBnkKbn5 = Integer.parseInt( req.getParameter("shhBnkKbn5"));
		// ��s�x���敪�i10���j�̎擾
		int shhBnkKbn10 = Integer.parseInt( req.getParameter("shhBnkKbn10"));
		// ��s�x���敪�i15���j�̎擾
		int shhBnkKbn15 = Integer.parseInt( req.getParameter("shhBnkKbn15"));
		// ��s�x���敪�i20���j�̎擾
		int shhBnkKbn20 = Integer.parseInt( req.getParameter("shhBnkKbn20"));
		// ��s�x���敪�i25���j�̎擾
		int shhBnkKbn25 = Integer.parseInt( req.getParameter("shhBnkKbn25"));
		// ��s�x���敪�i�����j�̎擾
		int shhBnkKbn30 = Integer.parseInt( req.getParameter("shhBnkKbn30"));
		// �x�������z�̎擾
		BigDecimal shhSihaMin = new BigDecimal( req.getParameter("shhSihaMin"));
		// �U���萔�������z�̎擾
		BigDecimal shhFuriMin = new BigDecimal( req.getParameter("shhFuriMin"));
		// �莞�x���i�P���j�̐ݒ�
		apShhMST.setSHH_SIHA_1(shhSiha1);
		// �莞�x���i5���j�̐ݒ�
		apShhMST.setSHH_SIHA_5(shhSiha5);
		// �莞�x���i10���j�̐ݒ�
		apShhMST.setSHH_SIHA_10(shhSiha10);
		// �莞�x���i15���j�̐ݒ�
		apShhMST.setSHH_SIHA_15(shhSiha15);
		// �莞�x���i20���j�̐ݒ�
		apShhMST.setSHH_SIHA_20(shhSiha20);
		// �莞�x���i25���j�̐ݒ�
		apShhMST.setSHH_SIHA_25(shhSiha25);
		// �莞�x���i�����j�̐ݒ�
		apShhMST.setSHH_SIHA_30(shhSiha30);
		// ��s�x���敪�i1���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_1(shhBnkKbn1);
		// ��s�x���敪�i5���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_5(shhBnkKbn5);
		// ��s�x���敪�i10���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_10(shhBnkKbn10);
		// ��s�x���敪�i15���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_15(shhBnkKbn15);
		// ��s�x���敪�i20���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_20(shhBnkKbn20);
		// ��s�x���敪�i25���j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_25(shhBnkKbn25);
		// ��s�x���敪�i�����j�̐ݒ�
		apShhMST.setSHH_BNK_KBN_30(shhBnkKbn30);
		// �x�������z�̐ݒ�
		apShhMST.setSHH_SIHA_MIN(shhSihaMin);
		// �U���萔�������z�̐ݒ�
		apShhMST.setSHH_FURI_MIN(shhFuriMin);
		// �J�n�N�����̏�����
		Date shhInpDate = null;
		// �I���N�����̏�����
		Date updDate = null;
		// �J�n�N�����̎擾
		shhInpDate = DateUtil.toYMDDate( req.getParameter("shhInpDate"));
		// �I���N�����̎擾
		updDate = DateUtil.toYMDDate( req.getParameter("updDate"));
		// �J�n�N�����̐ݒ�
		apShhMST.setSHH_INP_DATE(shhInpDate);
		// �I���N�����̐ݒ�
		apShhMST.setUPD_DATE(updDate);
		// �v���O����ID�̐ݒ�
		apShhMST.setPRG_ID( req.getParameter("prgID"));
		// ���ʂ�Ԃ�
		return apShhMST;
	}

	/** ���X�g�o�͂�Excel��` */
	protected ReportExcelDefine getReportExcelDefine() {
		// Excel��Ԃ�
		return null;
	}
}

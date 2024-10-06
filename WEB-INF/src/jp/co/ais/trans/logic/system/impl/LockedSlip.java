package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �r���`�[�����r�W�l�X���W�b�N
 */
public class LockedSlip extends SlipLockInterceptor {

	/** �������ꂽ�`�[���X�g */
	private List<SlipUnlockObject> sliplist;

	/** ��ЃR�[�h */
	private String companyCode;

	/** S2�R���e�C�i */
	private S2Container container;

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = invocation.proceed();
		sliplist = new LinkedList<SlipUnlockObject>();
		unlockSlipList(invocation);
		return ret;
	}

	/**
	 * �`�[�r�� ������������
	 * 
	 * @param invocation MethodInvocation
	 */
	public void unlockSlipList(MethodInvocation invocation) {

		companyCode = getCompanyCode(invocation);

		container = getContainer(invocation);

		setGLHdrList();

		setGLPtnList();

		setAPHdrList();

		setAPPtnList();

		setARHdrList();

		setARPtnList();

		DEN_SYU_MSTDao denDao = (DEN_SYU_MSTDao) container.getComponent(DEN_SYU_MSTDao.class);
		List<DEN_SYU_MST> slipKbnList = denDao.getAllDEN_SYU_MST2(companyCode);

		// �`�[��ʖ��̂��擾
		for (SlipUnlockObject slip : sliplist) {
			for (DEN_SYU_MST den_syu_mst : slipKbnList) {
				if (den_syu_mst.getDEN_SYU_CODE().equals(slip.getSilpName())) {
					slip.setSilpName(den_syu_mst.getDEN_SYU_NAME());
					break;
				}
			}
		}

		// �r���`�[���X�g��ǉ�
		addSliplist(invocation, sliplist);
	}

	/**
	 * GL�d��w�b�_�̔r���`�[���X�g�ǉ�
	 */
	private void setGLHdrList() {
		GL_SWK_HDRDao glshDao = (GL_SWK_HDRDao) container.getComponent(GL_SWK_HDRDao.class);
		List<GL_SWK_HDR> hdrList = glshDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (GL_SWK_HDR gl_swk_hdr : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(gl_swk_hdr.getUPD_DATE());
			slip.setSilpDate(gl_swk_hdr.getSWK_DEN_DATE());
			slip.setSilpNo(gl_swk_hdr.getSWK_DEN_NO());
			slip.setSilpName(gl_swk_hdr.getSWK_DEN_SYU());
			slip.setSilpTek(gl_swk_hdr.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.GL_HDR);
			slip.setUserCode(gl_swk_hdr.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

	/**
	 * GL�d��p�^�[���̔r���`�[���X�g�ǉ�
	 */
	private void setGLPtnList() {
		// GL�d��p�^�[���w�b�_Dao
		GL_SWK_PTN_HDRDao glsphDao = (GL_SWK_PTN_HDRDao) container.getComponent(GL_SWK_PTN_HDRDao.class);
		List<GL_SWK_PTN_HDR> hdrList = glsphDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (GL_SWK_PTN_HDR gl_swk_ptn : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(gl_swk_ptn.getUPD_DATE());
			slip.setSilpDate(gl_swk_ptn.getSWK_DEN_DATE());
			slip.setSilpNo(gl_swk_ptn.getSWK_DEN_NO());
			slip.setSilpName(gl_swk_ptn.getSWK_DEN_SYU());
			slip.setSilpTek(gl_swk_ptn.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.GL_PTN);
			slip.setUserCode(gl_swk_ptn.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

	/**
	 * AP�d��w�b�_�̔r���`�[���X�g�ǉ�
	 */
	private void setAPHdrList() {
		// AP�d��w�b�_Dao
		AP_SWK_HDRDao apshDao = (AP_SWK_HDRDao) container.getComponent(AP_SWK_HDRDao.class);
		List<AP_SWK_HDR> hdrList = apshDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (AP_SWK_HDR ap_swk_hdr : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(ap_swk_hdr.getUPD_DATE());
			slip.setSilpDate(ap_swk_hdr.getSWK_DEN_DATE());
			slip.setSilpNo(ap_swk_hdr.getSWK_DEN_NO());
			slip.setSilpName(ap_swk_hdr.getSWK_DEN_SYU());
			slip.setSilpTek(ap_swk_hdr.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.AP_HDR);
			slip.setUserCode(ap_swk_hdr.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

	/**
	 * AP�d��p�^���̔r���`�[���X�g�ǉ�
	 */
	private void setAPPtnList() {
		// AP�d��w�b�_Dao
		AP_SWK_PTN_HDRDao apsphDao = (AP_SWK_PTN_HDRDao) container.getComponent(AP_SWK_PTN_HDRDao.class);
		List<AP_SWK_PTN_HDR> hdrList = apsphDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (AP_SWK_PTN_HDR ap_swk_ptn : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(ap_swk_ptn.getUPD_DATE());
			slip.setSilpDate(ap_swk_ptn.getSWK_DEN_DATE());
			slip.setSilpNo(ap_swk_ptn.getSWK_DEN_NO());
			slip.setSilpName(ap_swk_ptn.getSWK_DEN_SYU());
			slip.setSilpTek(ap_swk_ptn.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.AP_PTN);
			slip.setUserCode(ap_swk_ptn.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

	/**
	 * AR�d��w�b�_�̔r���`�[���X�g�ǉ�
	 */
	private void setARHdrList() {
		// AP�d��w�b�_Dao
		AR_SWK_HDRDao arshDao = (AR_SWK_HDRDao) container.getComponent(AR_SWK_HDRDao.class);
		List<AR_SWK_HDR> hdrList = arshDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (AR_SWK_HDR ar_swk_hdr : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(ar_swk_hdr.getUPD_DATE());
			slip.setSilpDate(ar_swk_hdr.getSWK_DEN_DATE());
			slip.setSilpNo(ar_swk_hdr.getSWK_DEN_NO());
			slip.setSilpName(ar_swk_hdr.getSWK_DEN_SYU());
			slip.setSilpTek(ar_swk_hdr.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.AR_HDR);
			slip.setUserCode(ar_swk_hdr.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

	/**
	 * AR�d��p�^���̔r���`�[���X�g�ǉ�
	 */
	private void setARPtnList() {
		// AR�d��p�^�[���w�b�_Dao
		AR_SWK_PTN_HDRDao arsphDao = (AR_SWK_PTN_HDRDao) container.getComponent(AR_SWK_PTN_HDRDao.class);
		List<AR_SWK_PTN_HDR> hdrList = arsphDao.getLockSlip(companyCode);
		if (hdrList == null) {
			return;
		}
		List<SlipUnlockObject> list = new LinkedList<SlipUnlockObject>();
		for (AR_SWK_PTN_HDR ar_swk_ptn : hdrList) {
			SlipUnlockObject slip = new SlipUnlockObject();
			slip.setCompanyCode(companyCode);
			slip.setExclusiveDate(ar_swk_ptn.getUPD_DATE());
			slip.setSilpDate(ar_swk_ptn.getSWK_DEN_DATE());
			slip.setSilpNo(ar_swk_ptn.getSWK_DEN_NO());
			slip.setSilpName(ar_swk_ptn.getSWK_DEN_SYU());
			slip.setSilpTek(ar_swk_ptn.getSWK_TEK());
			slip.setSubSystemCode(SYS_MST.AR_PTN);
			slip.setUserCode(ar_swk_ptn.getUSR_ID());
			list.add(slip);
		}
		sliplist.addAll(list);
	}

}

package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 排他伝票検索ビジネスロジック
 */
public class LockedSlip extends SlipLockInterceptor {

	/** 検索された伝票リスト */
	private List<SlipUnlockObject> sliplist;

	/** 会社コード */
	private String companyCode;

	/** S2コンテイナ */
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
	 * 伝票排他 強制解除処理
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

		// 伝票種別名称を取得
		for (SlipUnlockObject slip : sliplist) {
			for (DEN_SYU_MST den_syu_mst : slipKbnList) {
				if (den_syu_mst.getDEN_SYU_CODE().equals(slip.getSilpName())) {
					slip.setSilpName(den_syu_mst.getDEN_SYU_NAME());
					break;
				}
			}
		}

		// 排他伝票リストを追加
		addSliplist(invocation, sliplist);
	}

	/**
	 * GL仕訳ヘッダの排他伝票リスト追加
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
	 * GL仕訳パターンの排他伝票リスト追加
	 */
	private void setGLPtnList() {
		// GL仕訳パターンヘッダDao
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
	 * AP仕訳ヘッダの排他伝票リスト追加
	 */
	private void setAPHdrList() {
		// AP仕訳ヘッダDao
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
	 * AP仕訳パタンの排他伝票リスト追加
	 */
	private void setAPPtnList() {
		// AP仕訳ヘッダDao
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
	 * AR仕訳ヘッダの排他伝票リスト追加
	 */
	private void setARHdrList() {
		// AP仕訳ヘッダDao
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
	 * AR仕訳パタンの排他伝票リスト追加
	 */
	private void setARPtnList() {
		// AR仕訳パターンヘッダDao
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

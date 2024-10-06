package jp.co.ais.trans.logic.system.impl;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;

/**
 * 伝票排他 強制解除処理
 */
public class SlipListUnlock extends LockInterceptor {

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = invocation.proceed();

		unlockSlipList(invocation);

		return ret;
	}

	/**
	 * 伝票排他 強制解除処理
	 * 
	 * @param invocation MethodInvocation
	 */
	public void unlockSlipList(MethodInvocation invocation) {

		String companyCode = getCompanyCode(invocation);
		String userCode = getUserCode(invocation);

		S2Container container = getContainer(invocation);

		// GL仕訳ヘッダDao
		GL_SWK_HDRDao glshDao = (GL_SWK_HDRDao) container.getComponent(GL_SWK_HDRDao.class);

		// GL仕訳パターンヘッダDao
		GL_SWK_PTN_HDRDao glsphDao = (GL_SWK_PTN_HDRDao) container.getComponent(GL_SWK_PTN_HDRDao.class);

		// AP仕訳ヘッダDao
		AP_SWK_HDRDao apshDao = (AP_SWK_HDRDao) container.getComponent(AP_SWK_HDRDao.class);

		// AP仕訳パターンヘッダDao
		AP_SWK_PTN_HDRDao apsphDao = (AP_SWK_PTN_HDRDao) container.getComponent(AP_SWK_PTN_HDRDao.class);

		// AP仕訳ヘッダDao
		AR_SWK_HDRDao arshDao = (AR_SWK_HDRDao) container.getComponent(AR_SWK_HDRDao.class);

		// AR仕訳パターンヘッダDao
		AR_SWK_PTN_HDRDao arsphDao = (AR_SWK_PTN_HDRDao) container.getComponent(AR_SWK_PTN_HDRDao.class);

		// 強制解除 GL
		glshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// 強制解除 パターンGL
		glsphDao.updateUnlockShareByUser(companyCode, userCode, "");

		// 強制解除 AP
		apshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// 強制解除 パターンAP
		apsphDao.updateUnlockShareByUser(companyCode, userCode, "");

		// 強制解除 AR
		arshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// 強制解除 パターンAR
		arsphDao.updateUnlockShareByUser(companyCode, userCode, "");
	}
}

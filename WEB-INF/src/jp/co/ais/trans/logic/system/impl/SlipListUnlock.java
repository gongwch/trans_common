package jp.co.ais.trans.logic.system.impl;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;

/**
 * �`�[�r�� ������������
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
	 * �`�[�r�� ������������
	 * 
	 * @param invocation MethodInvocation
	 */
	public void unlockSlipList(MethodInvocation invocation) {

		String companyCode = getCompanyCode(invocation);
		String userCode = getUserCode(invocation);

		S2Container container = getContainer(invocation);

		// GL�d��w�b�_Dao
		GL_SWK_HDRDao glshDao = (GL_SWK_HDRDao) container.getComponent(GL_SWK_HDRDao.class);

		// GL�d��p�^�[���w�b�_Dao
		GL_SWK_PTN_HDRDao glsphDao = (GL_SWK_PTN_HDRDao) container.getComponent(GL_SWK_PTN_HDRDao.class);

		// AP�d��w�b�_Dao
		AP_SWK_HDRDao apshDao = (AP_SWK_HDRDao) container.getComponent(AP_SWK_HDRDao.class);

		// AP�d��p�^�[���w�b�_Dao
		AP_SWK_PTN_HDRDao apsphDao = (AP_SWK_PTN_HDRDao) container.getComponent(AP_SWK_PTN_HDRDao.class);

		// AP�d��w�b�_Dao
		AR_SWK_HDRDao arshDao = (AR_SWK_HDRDao) container.getComponent(AR_SWK_HDRDao.class);

		// AR�d��p�^�[���w�b�_Dao
		AR_SWK_PTN_HDRDao arsphDao = (AR_SWK_PTN_HDRDao) container.getComponent(AR_SWK_PTN_HDRDao.class);

		// �������� GL
		glshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// �������� �p�^�[��GL
		glsphDao.updateUnlockShareByUser(companyCode, userCode, "");

		// �������� AP
		apshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// �������� �p�^�[��AP
		apsphDao.updateUnlockShareByUser(companyCode, userCode, "");

		// �������� AR
		arshDao.updateUnlockShareByUser(companyCode, userCode, "");
		// �������� �p�^�[��AR
		arsphDao.updateUnlockShareByUser(companyCode, userCode, "");
	}
}

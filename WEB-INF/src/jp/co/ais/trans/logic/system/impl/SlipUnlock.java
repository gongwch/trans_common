package jp.co.ais.trans.logic.system.impl;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �`�[�r�� ������������
 */
public class SlipUnlock extends LockInterceptor {

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = invocation.proceed();

		unlockSlipForced(invocation);

		return ret;
	}

	/**
	 * �`�[�r�� ������������
	 * 
	 * @param invocation MethodInvocation
	 */
	public void unlockSlipForced(MethodInvocation invocation) {

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

		// �������� �T�u�V�X�e�����胆�[�U��
		if (ServerConfig.isSystemRegulatedNumberCheck()) {
			SystemManager systemMng = (SystemManager) container.getComponent(SystemManager.class);
			try {
				systemMng.closeAllProgram(companyCode, userCode);
			} catch (TException e) {
				throw new TRuntimeException(e);
			}
		}
	}
}

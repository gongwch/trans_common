package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import org.aopalliance.intercept.*;
import org.seasar.framework.container.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �`�[���X�g�����r���������W�b�N
 */
public class UnlockSlipList extends SlipLockInterceptor {

	/** �������ꂽ�`�[���X�g */
	private List<SlipUnlockObject> slipList;

	/** ��ЃR�[�h */
	private String companyCode;

	/** S2�R���e�C�i */
	private S2Container container;

	/** GL�d��w�b�_ */
	private GL_SWK_HDRDao glshDao;

	/** GL�d��p�^�� */
	private GL_SWK_PTN_HDRDao glsphDao;

	/** AP�d��w�b�_ */
	private AP_SWK_HDRDao apshDao;

	/** AP�d��p�^�� */
	private AP_SWK_PTN_HDRDao apsphDao;

	/** AR�d��w�b�_ */
	private AR_SWK_HDRDao arshDao;

	/** AR�d��p�^�� */
	private AR_SWK_PTN_HDRDao arsphDao;

	/**
	 * @see org.aopalliance.intercept.MethodInterceptor#invoke(org.aopalliance.intercept.MethodInvocation)
	 */
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object ret = invocation.proceed();
		slipList = new LinkedList<SlipUnlockObject>();

		unlockSlipList(invocation);

		return ret;
	}

	/**
	 * �n���ꂽ�`�[���X�g�̔r�����폜
	 * 
	 * @param invocation
	 */
	public void unlockSlipList(MethodInvocation invocation) {

		companyCode = getCompanyCode(invocation);

		container = getContainer(invocation);

		slipList = getUnlockSlipList(invocation);

		glshDao = (GL_SWK_HDRDao) container.getComponent(GL_SWK_HDRDao.class);
		glsphDao = (GL_SWK_PTN_HDRDao) container.getComponent(GL_SWK_PTN_HDRDao.class);
		apshDao = (AP_SWK_HDRDao) container.getComponent(AP_SWK_HDRDao.class);
		apsphDao = (AP_SWK_PTN_HDRDao) container.getComponent(AP_SWK_PTN_HDRDao.class);
		arshDao = (AR_SWK_HDRDao) container.getComponent(AR_SWK_HDRDao.class);
		arsphDao = (AR_SWK_PTN_HDRDao) container.getComponent(AR_SWK_PTN_HDRDao.class);

		for (SlipUnlockObject slip : slipList) {
			int sysKbn = slip.getSubSystemCode();
			switch (sysKbn) {
				case SYS_MST.GL_HDR:
					deleteGLHdr(slip);
					break;
				case SYS_MST.GL_PTN:
					deleteGLPtn(slip);
					break;
				case SYS_MST.AP_HDR:
					deleteAPHdr(slip);
					break;
				case SYS_MST.AP_PTN:
					deleteAPPtn(slip);
					break;
				case SYS_MST.AR_HDR:
					deleteARHdr(slip);
					break;
				case SYS_MST.AR_PTN:
					deltetARPtn(slip);
					break;
				default:
					break;
			}
		}
	}

	/**
	 * GL�d��w�b�_�̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deleteGLHdr(SlipUnlockObject slip) {
		GL_SWK_HDR glHdr = glshDao.getGL_SWK_HDRByKaicodeSwkdendateSwkdenno(companyCode, slip.getSilpDate(), slip
			.getSilpNo());
		glHdr.setSWK_SHR_KBN(0);
		glHdr.setUPD_DATE(Util.getCurrentDate());
		glshDao.update(glHdr);
	}

	/**
	 * GL�d��p�^�[���̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deleteGLPtn(SlipUnlockObject slip) {
		GL_SWK_PTN_HDR glPtn = glsphDao.getGL_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno(companyCode, slip.getSilpDate(),
			slip.getSilpNo());
		glPtn.setSWK_SHR_KBN(0);
		glPtn.setUPD_DATE(Util.getCurrentDate());
		// GL�d��p�^�[���w�b�_Dao
		glsphDao.update(glPtn);
	}

	/**
	 * AP�d��w�b�_�̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deleteAPHdr(SlipUnlockObject slip) {
		AP_SWK_HDR apHdr = apshDao.getAP_SWK_HDRByKaicodeSwkdendateSwkdenno(companyCode, slip.getSilpDate(), slip
			.getSilpNo());
		apHdr.setSWK_SHR_KBN(0);
		apHdr.setUPD_DATE(Util.getCurrentDate());
		// AP�d��w�b�_Dao
		apshDao.update(apHdr);
	}

	/**
	 * AP�d��p�^���̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deleteAPPtn(SlipUnlockObject slip) {
		AP_SWK_PTN_HDR apPtn = apsphDao.getAP_SWK_PTN_HDRByKaicodeSwkdendateSwkdenno(companyCode, slip.getSilpDate(),
			slip.getSilpNo());
		apPtn.setSWK_SHR_KBN(0);
		apPtn.setUPD_DATE(Util.getCurrentDate());
		// AP�d��w�b�_Dao
		apsphDao.update(apPtn);
	}

	/**
	 * AR�d��w�b�_�̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deleteARHdr(SlipUnlockObject slip) {
		AR_SWK_HDR arHdr = arshDao.getAR_SWK_HDRByKaicodeSwkdendateSwkdenno(companyCode, slip.getSilpDate(), slip
			.getSilpNo());
		arHdr.setSWK_SHR_KBN(0);
		arHdr.setUPD_DATE(Util.getCurrentDate());
		// AP�d��w�b�_Dao
		arshDao.update(arHdr);
	}

	/**
	 * AR�d��p�^���̓`�[�r������
	 * 
	 * @param slip
	 */
	private void deltetARPtn(SlipUnlockObject slip) {
		AR_SWK_PTN_HDR arPtn = arsphDao.getAR_SWK_PTN_HDRByKaicodeSwkdendateSwkdennno(companyCode, slip.getSilpDate(),
			slip.getSilpNo());
		arPtn.setSWK_SHR_KBN(0);
		arPtn.setUPD_DATE(Util.getCurrentDate());
		// AR�d��p�^�[���w�b�_Dao
		arsphDao.update(arPtn);
	}
}

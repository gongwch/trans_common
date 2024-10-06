package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.user.*;
import jp.co.ais.trans2.model.userunlock.*;

/**
 * ���O�C�����[�U����(PCI_CHK_CTL�e�[�u��)�}�l�[�W�� �����N���X
 */
public class UserExclusiveManagerImpl extends TModel implements UserExclusiveManager, TExclusiveControlMethod {

	/**
	 * ���O�C���\���ǂ���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return true:�\
	 */
	public boolean canEntry(String companyCode, String userCode) {
		PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) getComponent(PCI_CHK_CTLDao.class);

		// ���d���O�C���`�F�b�N
		PCI_CHK_CTL pciChkCtl = pciDao.getPCI_CHK_CTLByKaicodeUsrid(companyCode, userCode);
		return pciChkCtl == null;
	}

	/**
	 * /**
	 * 
	 * @see jp.co.ais.trans2.model.security.TExclusiveControlMethod#exclude()
	 */
	public void exclude() {
		this.exclude(getCompanyCode(), getUserCode());
	}

	/**
	 * @see jp.co.ais.trans2.model.security.UserExclusiveManager#exclude(String, String)
	 */
	public void exclude(String companyCode, String userCode) {
		PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) getComponent(PCI_CHK_CTLDao.class);

		PCI_CHK_CTL dto = new PCI_CHK_CTL();
		dto.setKAI_CODE(companyCode);
		dto.setUSR_ID(userCode);
		dto.setPCI_CHECK_IN_TIME(Util.getCurrentDate());
		pciDao.insert(dto);
	}

	/**
	 * @see jp.co.ais.trans2.model.security.UserExclusiveManager#cancelExclude()
	 */
	public void cancelExclude() {

		try {
			UserUnLockManager manager = (UserUnLockManager) getComponent(UserUnLockManager.class);
			UserUnLock bean = new UserUnLock();
			bean.setKAI_CODE(getCompanyCode());
			bean.setUSR_ID(getUserCode());
			manager.delete(bean);
		} catch (TException e) {
			e.printStackTrace();
		}

	}

	/**
	 * �r����������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 */
	public void cancelExclude(String companyCode, String userCode) {
		try {
			UserUnLockManager manager = (UserUnLockManager) getComponent(UserUnLockManager.class);
			UserUnLock bean = new UserUnLock();
			bean.setKAI_CODE(companyCode);
			bean.setUSR_ID(userCode);
			manager.delete(bean);
		} catch (TException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���O�C���\���ǂ���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param password �p�X���[�h
	 * @return true:�\
	 * @throws TException
	 */
	public UserExclusive canEntry(String companyCode, String userCode, String password) throws TException {
		PCI_CHK_CTLDao pciDao = (PCI_CHK_CTLDao) getComponent(PCI_CHK_CTLDao.class);

		// ���d���O�C���`�F�b�N
		PCI_CHK_CTL pciChkCtl = pciDao.getPCI_CHK_CTLByKaicodeUsrid(companyCode, userCode);
		if (pciChkCtl == null) {
			return UserExclusive.CanEntry;
		}

		UserManager userManager = (UserManager) getComponent(UserManager.class);
		User user = userManager.get(companyCode, userCode, password);

		if (user == null) {
			return UserExclusive.WrongPasswordOrElse;
		}

		return UserExclusive.Locked;
	}
}

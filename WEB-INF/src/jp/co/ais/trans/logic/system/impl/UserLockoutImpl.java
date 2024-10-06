package jp.co.ais.trans.logic.system.impl;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ���b�N�A�E�g���W�b�N
 */
public class UserLockoutImpl implements UserLockout {

	/** �R���e�i */
	private S2Container container;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���[�U�R�[�h */
	private String userCode;

	/** ���[�U�F�؃}�X�^DAO */
	private USR_AUTH_MSTDao usrAuthDao;

	/** ���b�N�A�E�g�}�X�^DAO */
	private LOCK_OUT_TBLDao lockDao;

	/** ���b�N�A�E�g�f�[�^ */
	private LOCK_OUT_TBL lockOutTbl;

	/** ���[�U�F�؃f�[�^ */
	private USR_AUTH_MST usrAuthMst;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param container �R���e�i
	 */
	public UserLockoutImpl(S2Container container) {
		this.container = container;
	}

	/**
	 * @param usrAuthDao
	 */
	public void setUsrAuthDao(USR_AUTH_MSTDao usrAuthDao) {
		this.usrAuthDao = usrAuthDao;
	}

	/**
	 * @param lockdao
	 */
	public void setLockDao(LOCK_OUT_TBLDao lockdao) {
		this.lockDao = lockdao;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param compCode ��ЃR�[�h
	 */
	public void setCode(String compCode, String userCode) {
		this.companyCode = compCode;
		this.userCode = userCode;

		this.lockOutTbl = lockDao.findWithPK(compCode, userCode);
		this.usrAuthMst = usrAuthDao.findByKaiCode(compCode);
	}

	/**
	 * ���b�N�A�E�g�Ǘ����s�����ǂ���
	 * 
	 * @return true:���b�N�A�E�g�Ǘ�����
	 */
	public boolean isLockoutManaged() {
		return usrAuthMst != null;
	}

	/**
	 * �Y�����[�U�����b�N�A�E�g���ǂ����𔻕�<br>
	 * 
	 * @return boolean true:���b�N�A�E�g���
	 */
	public boolean isLockoutStatus() {

		// �F�ؐݒ肪���݂��Ȃ�(�`�F�b�N���Ȃ�)
		if (usrAuthMst == null) {
			return false;
		}

		// �F�ؐݒ肪0�̏ꍇ�`�F�b�N�Ȃ�(�`�F�b�N���Ȃ�)
		if (usrAuthMst.getLOCK_OUT_ARR_CNT() == 0) {
			return false;
		}

		// ���b�N�A�E�g��񂪑��݂��Ȃ�
		if (lockOutTbl == null) {
			return false;
		}

		// ���b�N�A�E�g�񐔂ƔF�؉񐔂̔�r
		if (lockOutTbl.getFAIL_COUNT() < usrAuthMst.getLOCK_OUT_ARR_CNT()) {
			return false;
		}

		// ���b�N�A�E�g�����J��

		// ���ꂼ���DATE�l��mileSecond�ɕς���B
		long releaseTime = usrAuthMst.getLOCK_OUT_RELEASE_TIME();
		long sysDate = (Util.getCurrentDate().getTime()) / 60000;
		long failDate = (lockOutTbl.getFAIL_DATE().getTime()) / 60000;

		// �J�����Ԑݒ�0�̎��͎����J���Ȃ��B
		if (releaseTime != 0 && (sysDate - failDate) > releaseTime) {
			return false;
		}

		// ���b�N�A�E�g���
		return true;
	}

	/**
	 * ���b�N�A�E�g���P���� <br>
	 * �V�K�̏ꍇ�͐V�K�L�^ �X�V�̏ꍇ�J�E���g���P����
	 */
	public void countUp() {

		// �F�ؐݒ肪���݂��Ȃ��ƃJ�E���g�A�b�v�Ȃ�
		if (usrAuthMst == null) {
			return;
		}

		// �J�E���g�A�b�v�ݒ肪0��������J�E���g�A�b�v���Ȃ�
		if (usrAuthMst.getLOCK_OUT_ARR_CNT() == 0) {
			return;
		}

		if (lockOutTbl == null) {
			// �f�[�^�Ȃ��ꍇ�C���T�[�g
			lockOutTbl = (LOCK_OUT_TBL) container.getComponent("LOCK_OUT_TBL");
			lockOutTbl.setKAI_CODE(companyCode);
			lockOutTbl.setUSR_CODE(userCode);
			lockOutTbl.setFAIL_COUNT(1);
			lockOutTbl.setFAIL_DATE(Util.getCurrentDate());
			lockOutTbl.setINP_DATE(Util.getCurrentDate());

			lockDao.insert(lockOutTbl);

		} else {
			// �J�E���g�A�b�v
			lockOutTbl.setFAIL_COUNT(lockOutTbl.getFAIL_COUNT() + 1);
			lockOutTbl.setFAIL_DATE(Util.getCurrentDate());

			lockDao.update(lockOutTbl);
		}
	}

	/**
	 * ���b�N�A�E�g����
	 */
	public void clearLockout() {

		if (lockOutTbl != null) {
			lockDao.delete(lockOutTbl);
		}

		lockOutTbl = null;
	}

}

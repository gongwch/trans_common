package jp.co.ais.trans.logic.system.impl;

import java.util.*;

import jp.co.ais.trans.logic.system.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;

/**
 * �r�����䃍�W�b�N
 */
public class LockImpl implements Lock {

	/** BAT�R���g���[��Dao */
	protected BAT_CTLDao batDao;

	/** �r������DAO */
	protected HAITA_CTLDao haitaDao;

	/** ��ЃR�[�h */
	protected String companyCode;

	/** ���[�U�R�[�h */
	protected String userCode;

	/** �v���O�����R�[�h */
	protected String prgCode;

	/** �r�������`�[���X�g */
	protected List<SlipUnlockObject> slipList;

	/** �r���`�[���X�g */
	protected List<SlipUnlockObject> returnSlipList;

	/**
	 * �R���X�g���N�^
	 */
	public LockImpl() {
		slipList = new LinkedList<SlipUnlockObject>();
		returnSlipList = new LinkedList<SlipUnlockObject>();
	}

	/**
	 * @param batDao
	 */
	public void setBatDao(BAT_CTLDao batDao) {
		this.batDao = batDao;
	}

	/**
	 * @param haitaDao
	 */
	public void setHaitaDao(HAITA_CTLDao haitaDao) {
		this.haitaDao = haitaDao;
	}

	/**
	 * �R�[�h�ݒ�
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param compCode ��ЃR�[�h
	 */
	public void setCode(String compCode, String userCode) {
		this.companyCode = compCode;
		this.userCode = userCode;
	}

	/**
	 * �R�[�h�ݒ�i�ʔr�������j
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param compCode ��ЃR�[�h
	 */
	public void setCode(String compCode, String userCode, String prgCode) {
		this.companyCode = compCode;
		this.userCode = userCode;
		this.prgCode = prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getCompanyCode()
	 */
	public String getCompanyCode() {
		return this.companyCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUserCode()
	 */
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUserCode()
	 */
	public String getPrgCode() {
		return this.prgCode;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#unlockAll()
	 */
	public void unlockAll() {
		// �@�\�r�� ��������
		this.deleteForced();

		// �R�[�h�r�� ��������
		this.deleteHaitaForced();
	}

	/**
	 * �@�\�r�� ������������
	 */
	public void deleteForced() {

		// �����f���[�g
		batDao.deleteForced(companyCode, userCode);
	}

	/**
	 * �R�[�h�r�� ������������
	 */
	public void deleteHaitaForced() {

		// �f�[�^���폜
		haitaDao.deleteLockUser(companyCode, userCode);
	}

	/**
	 * �o�b�`, �R�[�h �ʔr������
	 */
	public void unlockPrg() {
		// �o�b�`�r�� �ʔr������
		this.batctlDeleteByUsrPrg();

		// �R�[�h�r�� �ʔr������
		this.haitactlDeleteByUsrPrg();
	}

	/**
	 * �o�b�`�r�� �ʔr������
	 */
	public void batctlDeleteByUsrPrg() {
		batDao.deleteByUsrPrg(companyCode, prgCode, userCode);
	}

	/**
	 * �R�[�h�r�� �ʔr������
	 */
	public void haitactlDeleteByUsrPrg() {
		haitaDao.deleteByUsrPrg(companyCode, userCode, prgCode);
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#unlockSlip(java.util.List)
	 */
	public void unlockSlip(List<SlipUnlockObject> sliplist) {
		this.slipList = sliplist;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getExclusiveSlip()
	 */
	public List<SlipUnlockObject> getExclusiveSlip() {
		returnSlipList = new LinkedList<SlipUnlockObject>();
		return this.returnSlipList;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#getUnlockSlipList()
	 */
	public List<SlipUnlockObject> getUnlockSlipList() {
		return this.slipList;
	}

	/**
	 * @see jp.co.ais.trans.logic.system.Lock#addSliplist(java.util.List)
	 */
	public void addSliplist(List<SlipUnlockObject> exclusiveSlipList) {
		this.returnSlipList.addAll(exclusiveSlipList);
	}

}

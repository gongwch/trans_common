package jp.co.ais.trans.master.logic.impl;

import java.util.List;

import jp.co.ais.trans.master.dao.BAT_CTLDao;
import jp.co.ais.trans.master.entity.BAT_CTL;
import jp.co.ais.trans.master.logic.BatchUnLockLogic;

/**
 * �r���Ǘ��}�X�^�r�W�l�X���W�b�N
 */
public class BatchUnLockLogicImpl implements BatchUnLockLogic {

	/** �r���Ǘ��}�X�^Dao */
	private BAT_CTLDao dao;

	/**
	 * �f�t�H���g�R���X�g���N�^.
	 * 
	 * @param dao PCI_CHK_CTLDao
	 */
	public BatchUnLockLogicImpl(BAT_CTLDao dao) {
		this.dao = dao;
	}

	/**
	 * @see jp.co.ais.trans.master.logic.BatchUnLockLogic#getBatchListByCompanyCode(java.lang.String)
	 */
	public List<BAT_CTL> getBatchListByCompanyCode(String companyCode) {
		return dao.getBatchListByCompanyCode(companyCode);
	}

	/**
	 * @see jp.co.ais.trans.master.logic.UserUnLockLogic#delete(java.util.List)
	 */
	public void delete(List<BAT_CTL> dtoList) {
		dao.deleteList(dtoList);
	}
}

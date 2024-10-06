package jp.co.ais.trans.master.logic.impl;

import java.util.List;

import jp.co.ais.trans.master.dao.BAT_CTLDao;
import jp.co.ais.trans.master.entity.BAT_CTL;
import jp.co.ais.trans.master.logic.BatchUnLockLogic;

/**
 * 排他管理マスタビジネスロジック
 */
public class BatchUnLockLogicImpl implements BatchUnLockLogic {

	/** 排他管理マスタDao */
	private BAT_CTLDao dao;

	/**
	 * デフォルトコンストラクタ.
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

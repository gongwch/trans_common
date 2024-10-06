package jp.co.ais.trans.master.logic.impl;

import java.util.*;

import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans.master.logic.*;

/**
 * 計上会社フィールドImpl
 * 
 * @author zhangbo
 */
public class TAppropriateCompanyLogicImpl implements TAppropriateCompanyLogic {

	/** 計上会社フィールドDao */
	private AppropriateCompanyDao dao;

	/**
	 * デフォルトコンストラクタ.
	 * 
	 * @param dao AppropriateCompanyDao
	 */
	public TAppropriateCompanyLogicImpl(AppropriateCompanyDao dao) {
		// 計上会社フィールドDaoを返す
		this.dao = dao;
	}

	/**
	 * 検索
	 * 
	 * @param param 計上会社フィールドエンティティ
	 * @return List 計上会社フィールドエンティティ
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param) {

		return dao.conditionSearch(param);
	}
}

package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 計上会社フィールドロジック
 * 
 * @author zhangbo
 */
public interface TAppropriateCompanyLogic {

	/**
	 * 検索
	 * 
	 * @param param 計上会社フィールドエンティティ
	 * @return List 計上会社フィールドエンティティ
	 */
	public List<AppropriateCompany> conditionSearch(AppropriateCompany param);

}

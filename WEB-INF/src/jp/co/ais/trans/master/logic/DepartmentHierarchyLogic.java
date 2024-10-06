package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 部門階層マスタビジネスロジック
 * 
 * @author roh
 */
public interface DepartmentHierarchyLogic extends CommonLogic {

	/**
	 * @param kaiCode
	 * @return List
	 */
	List getOrganizations(String kaiCode);

	List find(Map conditions);

	/**
	 * @param entity
	 */
	void delete(DPK_MST entity);

	/**
	 * @param keys
	 * @return List
	 */
	List getREFItems(Map keys);

}

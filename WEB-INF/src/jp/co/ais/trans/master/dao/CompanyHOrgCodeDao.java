package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * Company Hierarchical Dao
 */
public interface CompanyHOrgCodeDao {

	/** */
	public Class BEAN = CompanyHierarchicalOrgCode.class;

	/**  */
	public String getAllOrgCode_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllOrgCode(String KAI_CODE);

	/**  */
	public String delete_ARGS = "KAI_CODE,DPK_SSK";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 */
	public void delete(String KAI_CODE, String DPK_SSK);
}

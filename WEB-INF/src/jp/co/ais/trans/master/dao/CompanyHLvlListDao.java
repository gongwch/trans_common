package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * Company level Dao
 */
public interface CompanyHLvlListDao {

	/** */
	public Class BEAN = CompanyHLvlList.class;

	/**  */
	public String getComLvl_ARGS = "KAI_CODE,DPK_SSK";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @return List
	 */
	public List getComLvl(String KAI_CODE, String DPK_SSK);

}

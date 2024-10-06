package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * Company Dao
 */
public interface CompanyHGetENVListDao {

	/** */
	public Class BEAN = CompanyHGetENVList.class;

	/**  */
	public String getWithOutCom_ARGS = "KAI_CODE,DPK_SSK";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @return List
	 */
	public List getWithOutCom(String KAI_CODE, String DPK_SSK);

	/**  */
	public String getENVListNewSsk_ARGS = "kaiCode";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getENVListNewSsk(String kaiCode);

}

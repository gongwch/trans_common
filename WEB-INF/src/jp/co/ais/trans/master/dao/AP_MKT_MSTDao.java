package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 送金目的マスタDao
 */
public interface AP_MKT_MSTDao {

	/** */
	public Class BEAN = AP_MKT_MST.class;

	/** ソート順 */
	public String getAllAP_MKT_MST_QUERY = "ORDER BY MKT_CODE";

	/**
	 * @return 送金目的全データ
	 */
	public List getAllAP_MKT_MST();

	/** 条件検索 */
	public String getAP_MKT_MST_ARGS = "MKT_CODE";

	/**
	 * @param mktCode
	 * @return AP_MKT_MST
	 */
	public AP_MKT_MST getAP_MKT_MST(String mktCode);

	/** 条件検索 */
	public String conditionSearch_ARGS = "mktCode,mktName";

	/**
	 * @param mktCode
	 * @param mktName
	 * @return List
	 */
	public List conditionSearch(String mktCode, String mktName);

	/**
	 * @param dto
	 */
	public void insert(AP_MKT_MST dto);

	/**
	 * @param dto
	 */
	public void update(AP_MKT_MST dto);

	/**
	 * @param dto
	 */
	public void delete(AP_MKT_MST dto);
}

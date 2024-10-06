package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 資金コードマスタDao
 */
public interface CM_SKN_MSTDao {

	/** */
	public Class BEAN = CM_SKN_MST.class;

	/**
	 * @return List
	 */
	public List getAllCM_SKN_MST();

	/**
	 * @param dto
	 */
	public void insert(CM_SKN_MST dto);

	// テーブルCM_SKN_MSTはPrimary Key がないですので、
	// updateとdeleteのSQLファイルの作成必要

	/**
	 * @param dto
	 */
	public void update(CM_SKN_MST dto);

	/**
	 * @param dto
	 */
	public void delete(CM_SKN_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getCM_SKN_MST_ARGS = "SKN_KAI_CODE, SKN_CODE";

	/**
	 * @param kaiCode
	 * @param skncode
	 * @return CM_SKN_MST
	 */
	public CM_SKN_MST getCM_SKN_MST(String kaiCode, String skncode);

	// 条件検索

	/**  */
	public String conditionSearch_ARGS = "sknKaiCode,sknCode,sknName_S,sknName_K,kind";

	/**
	 * @param sknKaiCode
	 * @param sknCode
	 * @param sknName_S
	 * @param sknName_K
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String sknKaiCode, String sknCode, String sknName_S, String sknName_K, String kind);

}

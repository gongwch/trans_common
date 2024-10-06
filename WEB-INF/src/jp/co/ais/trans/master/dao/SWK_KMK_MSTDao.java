package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface SWK_KMK_MSTDao {

	/**  */
	public Class BEAN = SWK_KMK_MST.class;

	/**
	 * @return List
	 */
	public List getAllSWK_KMK_MST();

	/**  */
	public String getSwkKmkInfo_QUERY = "KAI_CODE = ? AND KMK_CNT BETWEEN ? AND ? ORDER BY KMK_CNT ";

	/**
	 * @param KAI_CODE
	 * @param KMK_CNT_FROM
	 * @param KMK_CNT_TO
	 * @return List
	 */
	public List getSwkKmkInfo(String KAI_CODE, int KMK_CNT_FROM, int KMK_CNT_TO);

	/**  */
	public String getSwkKmkInfoByKaiCode_QUERY = "KAI_CODE = ?  AND KMK_CNT IN('4', '5', '8', '9') ";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getSwkKmkInfoByKaiCode(String KAI_CODE);

	// 指定されたデータの取得
	/**  */
	public String getSWK_KMK_MSTByKaicodeKmkcnt_ARGS = "KAI_CODE, KMK_CNT";

	/**
	 * @param kaiCode
	 * @param kmkCnt
	 * @return SWK_KMK_MST
	 */
	public SWK_KMK_MST getSWK_KMK_MSTByKaicodeKmkcnt(String kaiCode, int kmkCnt);

	// 指定されたデータの取得
	/**  */
	public String getSWK_KMK_MSTByKaicode_ARGS = "KAI_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getSWK_KMK_MSTByKaicode(String kaiCode);

	/**
	 * @param dto
	 */
	public void insert(SWK_KMK_MST dto);

	/**
	 * @param dto
	 */
	public void update(SWK_KMK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(SWK_KMK_MST dto);

}

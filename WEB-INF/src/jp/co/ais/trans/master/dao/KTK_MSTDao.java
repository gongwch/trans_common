package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface KTK_MSTDao {

	/**  */
	public Class BEAN = KTK_MST.class;

	/**
	 * @return List
	 */
	public List getAllKTK_MST();

	/**  */
	public String getKtkInfo_QUERY = "KAI_CODE = ? AND KTK_KAI_CODE BETWEEN ? AND ? ORDER BY KTK_KAI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param KTK_KAI_CODE_FROM
	 * @param KTK_KAI_CODE_TO
	 * @return List
	 */
	public List getKtkInfo(String KAI_CODE, String KTK_KAI_CODE_FROM, String KTK_KAI_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getKTK_MSTByKaicodeKtkkaicode_ARGS = "KAI_CODE, KTK_KAI_CODE";

	/**
	 * @param kaiCode
	 * @param ktkkaiCode
	 * @return KTK_MST
	 */
	public KTK_MST getKTK_MSTByKaicodeKtkkaicode(String kaiCode, String ktkkaiCode);

	/**
	 * @param dto
	 */
	public void insert(KTK_MST dto);

	/**
	 * @param dto
	 */
	public void update(KTK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KTK_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllKTK_MST2_QUERY = "KAI_CODE = ? ORDER BY KTK_KAI_CODE ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKTK_MST2(String kaiCode);

	/**  */
	public String getKtkInfoFrom_QUERY = "KAI_CODE = ? AND KTK_KAI_CODE >= ? ORDER BY KTK_KAI_CODE ";

	/**
	 * @param kaiCode
	 * @param ktkKaiCodeFrom
	 * @return List
	 */
	public List getKtkInfoFrom(String kaiCode, String ktkKaiCodeFrom);

	/**  */
	public String getKtkInfoTo_QUERY = "KAI_CODE = ? AND KTK_KAI_CODE <= ? ORDER BY KTK_KAI_CODE";

	/**
	 * @param kaiCode
	 * @param ktkKaiCodeTo
	 * @return List
	 */
	public List getKtkInfoTo(String kaiCode, String ktkKaiCodeTo);
}

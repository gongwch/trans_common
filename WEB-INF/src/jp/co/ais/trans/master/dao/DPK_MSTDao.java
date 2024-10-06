package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface DPK_MSTDao {

	/**  */
	public Class BEAN = DPK_MST.class;

	/**
	 * @return List
	 */
	public List getAllDPK_MST();

	/** パラメーター */
	public String getDpkSsk_SQL = "SELECT DISTINCT(T.DPK_SSK) FROM DPK_MST T WHERE T.KAI_CODE = ? ORDER BY T.DPK_SSK";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getDpkSsk(String kaiCode);

	/**  */
	public String getDpkInfo_QUERY = "KAI_CODE = ? AND DPK_SSK = ? AND DPK_DEP_CODE BETWEEN ? AND ? ORDER BY DPK_DEP_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DPK_SSK
	 * @param DPK_DEP_CODE_FROM
	 * @param DPK_DEP_CODE_TO
	 * @return List
	 */
	public List getDpkInfo(String KAI_CODE, String DPK_SSK, String DPK_DEP_CODE_FROM, String DPK_DEP_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getDPK_MSTByKaicodeDpksskDpkdepcode_ARGS = "KAI_CODE, DPK_SSK, DPK_DEP_CODE";

	/**
	 * @param kaiCode
	 * @param dpkSSK
	 * @param dpkdepCode
	 * @return DPK_MST
	 */
	public DPK_MST getDPK_MSTByKaicodeDpksskDpkdepcode(String kaiCode, String dpkSSK, String dpkdepCode);

	/** パラメータ */
	public String getDPK_MSTByKaicodeDepcode_QUERY = "KAI_CODE = ? AND DPK_DEP_CODE = ? ORDER BY DPK_SSK";

	/**
	 * 会社コードと部門コードで組織体系情報取得
	 * 
	 * @param kaiCode
	 * @param depCode
	 * @return 組織情報
	 */
	public List<DPK_MST> getDPK_MSTByKaicodeDepcode(String kaiCode, String depCode);

	/**
	 * @param dto
	 */
	public void insert(DPK_MST dto);

	/**
	 * @param dto
	 */
	public void update(DPK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(DPK_MST dto);

	// 下記は ISFnet China 追加分

	// 該当組織のレコードを取得
	/**  */
	public String find_ARGS = "kaiCode,dpkSsk";

	/**
	 * @param kaiCode
	 * @param dpkSsK
	 * @return List
	 */
	public List find(String kaiCode, String dpkSsK);

	// 全部の組織を取得
	/**  */
	public String getOrganizations_ARGS = "kaiCode";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getOrganizations(String kaiCode);
}

package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface PRG_MSTDao {

	/**  */
	public Class BEAN = PRG_MST.class;

	/**
	 * @return List
	 */
	public List getAllPRG_MST();

	/**  */
	public String getPrgInfo_QUERY = "KAI_CODE = ? AND PRG_CODE BETWEEN ? AND ? ORDER BY SYS_CODE, PRG_CODE ";

	/**
	 * @param KAI_CODE
	 * @param PRG_CODE_FROM
	 * @param PRG_CODE_TO
	 * @return List
	 */
	public List getPrgInfo(String KAI_CODE, String PRG_CODE_FROM, String PRG_CODE_TO);

	/**  */
	public String getPrgAuthority_QUERY = "KAI_CODE = ? AND KEN >= ? ";

	/**
	 * @param KAI_CODE
	 * @param KEN
	 * @return List
	 */
	public List getPrgAuthority(String KAI_CODE, int KEN);

	// 指定されたデータの取得
	/**  */
	public String getPRG_MSTByKaicodeSyscodePrgcode_ARGS = "KAI_CODE, SYS_CODE, PRG_CODE";

	/**
	 * @param kaiCode
	 * @param sysCode
	 * @param prgCode
	 * @return PRG_MST
	 */
	public PRG_MST getPRG_MSTByKaicodeSyscodePrgcode(String kaiCode, String sysCode, String prgCode);

	/**
	 * @param dto
	 */
	public void insert(PRG_MST dto);

	/**
	 * @param dto
	 */
	public void update(PRG_MST dto);

	/**
	 * @param dto
	 */
	public void delete(PRG_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllPRG_MST2_QUERY = "KAI_CODE = ? ORDER BY PRG_CODE ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllPRG_MST2(String kaiCode);

	/**  */
	public String getPrgInfoFrom_QUERY = "KAI_CODE = ? AND PRG_CODE >= ? ORDER BY PRG_CODE ";

	/**
	 * @param kaiCode
	 * @param prgCodeFrom
	 * @return List
	 */
	public List getPrgInfoFrom(String kaiCode, String prgCodeFrom);

	/**  */
	public String getPrgInfoTo_QUERY = "KAI_CODE = ? AND PRG_CODE <= ? ORDER BY PRG_CODE ";

	/**
	 * @param kaiCode
	 * @param prgCodeTo
	 * @return List
	 */
	public List getPrgInfoTo(String kaiCode, String prgCodeTo);

	/**  */
	public String getPrgInfo2_QUERY = "KAI_CODE = ? AND PRG_CODE BETWEEN ? AND ? ORDER BY PRG_CODE ";

	/**
	 * @param KAI_CODE
	 * @param PRG_CODE_FROM
	 * @param PRG_CODE_TO
	 * @return List
	 */
	public List getPrgInfo2(String KAI_CODE, String PRG_CODE_FROM, String PRG_CODE_TO);

	// REF検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,PRG_CODE,PRG_NAME_S,PRG_NAME_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param prgCode
	 * @param prgName_S
	 * @param prgName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String prgCode, String prgName_S, String prgName_K, String beginCode,
		String endCode);

	/**  */
	public String getPRG_MSTByKaicodePrgcode_ARGS = "KAI_CODE, PRG_CODE";

	/**
	 * @param kaiCode
	 * @param prgCode
	 * @return PRG_MST
	 */
	public PRG_MST getPRG_MSTByKaicodePrgcode(String kaiCode, String prgCode);
}

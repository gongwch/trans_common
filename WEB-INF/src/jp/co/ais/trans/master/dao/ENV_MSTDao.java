package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface ENV_MSTDao {

	/**  */
	public Class BEAN = ENV_MST.class;

	/**
	 * @return List
	 */
	public List getAllENV_MST();

	/**  */
	public String getENV_MST_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return EVK_MST
	 */
	public ENV_MST getENV_MST(String KAI_CODE);

	/**  */
	public String getENV_MSTByKaicode_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return EVK_MST
	 */
	public ENV_MST getENV_MSTByKaicode(String KAI_CODE);

	/**
	 * @param dto
	 */
	public void insert(ENV_MST dto);

	/**
	 * @param dto
	 */
	public void update(ENV_MST dto);

	/**
	 * @param dto
	 */
	public void delete(ENV_MST dto);

	// 下記は ISFnet China 追加分

	// 条件検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KAI_NAME_S";

	/**
	 * @param kaiCode
	 * @param kaiName_S
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kaiName_S);

	/** パラメーター */
	public String getCmpNameS_ARGS = "KaiCode, DpkSsk, DepCode, panelLevel, kjlLvl, kjlDepCode";

	/** 会社略称を取得 */
	/**
	 * @param KaiCode
	 * @param DpkSsk
	 * @param DepCode
	 * @param panelLevel
	 * @param kjlLvl
	 * @param kjlDepCode
	 * @return String
	 */
	public String getCmpNameS(String KaiCode, String DpkSsk, String DepCode, int panelLevel, int kjlLvl,
		String kjlDepCode);

	/** 組織検索 */
	public String conditionLvlSearch_ARGS = "loginKaiCode, kaiCode, kaiName_S, dpkSsk, cmpKbn, upCmpCode, dpkLvl, baseCmpCode, baseDpkLvl";

	/**
	 * 組織検索
	 * 
	 * @param loginKaiCode ログイン会社コード
	 * @param kaiCode 会社コード
	 * @param kaiName_S 会社名称
	 * @param dpkSsk 組織コード
	 * @param cmpKbn 配下会社コード
	 * @param upCmpCode 上位会社コード
	 * @param dpkLvl 階層レベル
	 * @param baseCmpCode 初期会社コード
	 * @param baseDpkLvl 初期階層レベル
	 * @return list
	 */
	public List conditionLvlSearch(String loginKaiCode, String kaiCode, String kaiName_S, String dpkSsk, String cmpKbn,
		String upCmpCode, String dpkLvl, String baseCmpCode, String baseDpkLvl);

	// ISFnet 追加
	/**  */
	public String getENV_MSTByKaicode1_QUERY = "ORDER BY KAI_CODE";

	/**
	 * @return List
	 */
	public List getENV_MSTByKaicode1();
	
	/** パラメーター */
	public String getEnvMstData_ARGS = "kaiCode, slipDate";

	/** 会社略称を取得 */
	/**
	 * @param kaiCode
	 * @param slipDate
	 * @return List
	 */
	public List getEnvMstData(String kaiCode, Date slipDate);
}

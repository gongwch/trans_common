package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 内訳科目マスタDao
 */
/**
 * 
 */
public interface UKM_MSTDao {

	// BEANの初期化
	/**  */
	public Class BEAN = UKM_MST.class;

	/**
	 * @return List
	 */
	public List getAllUKM_MST();

	/**  */
	public String getUkmInfo_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE = ? AND UKM_CODE BETWEEN ? AND ? ORDER BY UKM_CODE ";

	/**
	 * @param KAI_CODE
	 * @param KMK_CODE
	 * @param HKM_CODE
	 * @param UKM_CODE_FROM
	 * @param UKM_CODE_TO
	 * @return List
	 */
	public List getUkmInfo(String KAI_CODE, String KMK_CODE, String HKM_CODE, String UKM_CODE_FROM, String UKM_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode_ARGS = "KAI_CODE, KMK_CODE, HKM_CODE, UKM_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @return UKM_MST
	 */
	public UKM_MST getUKM_MSTByKaicodeKmkcodeHkmcodeUkmcode(String kaiCode, String kmkCode, String hkmCode,
		String ukmCode);

	/**
	 * @param dto
	 */
	public void insert(UKM_MST dto);

	/**
	 * @param dto
	 */
	public void update(UKM_MST dto);

	/**
	 * @param dto
	 */
	public void delete(UKM_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllUKM_MST1_QUERY = "KAI_CODE = ? ORDER BY KMK_CODE,HKM_CODE,UKM_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllUKM_MST1(String kaiCode);

	/**  */
	public String getAllUKM_MST2_QUERY = "KAI_CODE = ? AND KMK_CODE = ? ORDER BY HKM_CODE,UKM_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @return List
	 */
	public List getAllUKM_MST2(String kaiCode, String kmkCode);

	/**  */
	public String getAllUKM_MST3_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE = ? ORDER BY UKM_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @return List
	 */
	public List getAllUKM_MST3(String kaiCode, String kmkCode, String hkmCode);

	/**  */
	public String getUkmInfoFrom_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE = ? AND UKM_CODE >= ? ORDER BY UKM_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCodeFrom
	 * @return List
	 */
	public List getUkmInfoFrom(String kaiCode, String kmkCode, String hkmCode, String ukmCodeFrom);

	/**  */
	public String getUkmInfoTo_QUERY = "KAI_CODE = ? AND KMK_CODE = ? AND HKM_CODE = ? AND UKM_CODE <= ? ORDER BY UKM_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCodeTo
	 * @return List
	 */
	public List getUkmInfoTo(String kaiCode, String kmkCode, String hkmCode, String ukmCodeTo);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "kaiCode,kmkCode,hkmCode,ukmCode,ukmName_S,ukmName_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param hkmCode
	 * @param ukmCode
	 * @param ukmName_S
	 * @param ukmName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kmkCode, String hkmCode, String ukmCode, String ukmName_S,
		String ukmName_K, String beginCode, String endCode);

	/** findListByParameter()引数 */
	public static final String findListByParameter_ARGS = "param";

	/** findByParameter()引数 */
	public static final String findByParameter_ARGS = "param";

	/**
	 * 内訳科目コード検索
	 * 
	 * @param param Inputパラメータ
	 * @return 内訳科目リスト
	 */
	public List findListByParameter(AccountItemInputParameter param);

	/**
	 * 内訳科目を取得
	 * 
	 * @param param Inputパラメータ
	 * @return 内訳科目マスタデータ
	 */
	public UKM_MST findByParameter(AccountItemInputParameter param);
}

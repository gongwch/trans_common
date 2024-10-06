package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.common.bizui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 科目マスタDao
 */
public interface KMK_MSTDao {

	/**  */
	public Class BEAN = KMK_MST.class;

	/**
	 * @return List
	 */
	public List getAllKMK_MST();

	/**  */
	public String getKmkInfo_QUERY = "KAI_CODE = ? AND KMK_CODE BETWEEN ? AND ? ORDER BY KMK_CODE ";

	/**
	 * @param KAI_CODE
	 * @param KMK_CODE_FROM
	 * @param KMK_CODE_TO
	 * @return List
	 */
	public List getKmkInfo(String KAI_CODE, String KMK_CODE_FROM, String KMK_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getKMK_MSTByKaicodeKmkcode_ARGS = "KAI_CODE, KMK_CODE";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @return KMK_MST
	 */
	public KMK_MST getKMK_MSTByKaicodeKmkcode(String kaiCode, String kmkCode);

	// 指定されたデータの取得
	/**  */
	public String getKMK_MSTByKaicodeKmkcodeSumKbn_ARGS = "KAI_CODE, KMK_CODE, SUM_KBN";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param sumKbn
	 * @return KMK_MST
	 */
	public KMK_MST getKMK_MSTByKaicodeKmkcodeSumKbn(String kaiCode, String kmkCode, String sumKbn);

	/**
	 * @param dto
	 */
	public void insert(KMK_MST dto);

	/**
	 * @param dto
	 */
	public void update(KMK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KMK_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllKMK_MST2_QUERY = "KAI_CODE = ? ORDER BY KMK_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKMK_MST2(String kaiCode);

	/**  */
	public String getKmkInfoFrom_QUERY = "KAI_CODE = ? AND KMK_CODE >= ? ORDER BY KMK_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCodeFrom
	 * @return List
	 */
	public List getKmkInfoFrom(String kaiCode, String kmkCodeFrom);

	/**  */
	public String getKmkInfoTo_QUERY = "KAI_CODE = ? AND KMK_CODE <= ? ORDER BY KMK_CODE ";

	/**
	 * @param kaiCode
	 * @param kmkCodeTo
	 * @return List
	 */
	public List getKmkInfoTo(String kaiCode, String kmkCodeTo);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "kaiCode,kmkCode,kmkName_S,kmkName_K,beginCode,endCode,kind";

	/**
	 * @param kaiCode
	 * @param kmkCode
	 * @param kmkName_S
	 * @param kmkName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kmkCode, String kmkName_S, String kmkName_K, String beginCode,
		String endCode, String kind);

	/** findListByParameter()引数 */
	public static final String findListByParameter_ARGS = "param";

	/** findByParameter()引数 */
	public static final String findByParameter_ARGS = "param";

	/**
	 * 科目コード検索
	 * 
	 * @param param Inputパラメータ
	 * @return 科目リスト
	 */
	public List findListByParameter(AccountItemInputParameter param);

	/**
	 * 科目マスタデータの科目略称を検索
	 * 
	 * @param param Inputパラメータ
	 * @return 科目マスタデータ
	 */
	public KMK_MST findByParameter(AccountItemInputParameter param);
}

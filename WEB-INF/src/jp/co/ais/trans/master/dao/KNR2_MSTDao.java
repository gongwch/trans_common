package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理２マスタDao
 */
public interface KNR2_MSTDao {

	/**  */
	public Class BEAN = KNR2_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR2_MST();

	/**  */
	public String getKnr2Info_QUERY = "KAI_CODE = ? AND KNR_CODE_2 BETWEEN ? AND ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_2_FROM
	 * @param KNR_CODE_2_TO
	 * @return List
	 */
	public List getKnr2Info(String KAI_CODE, String KNR_CODE_2_FROM, String KNR_CODE_2_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR2_MSTByKaicodeKnrcode2_ARGS = "KAI_CODE,KNR_CODE_2";

	/**
	 * @param kaiCode
	 * @param knrCode2
	 * @return KNR2_MST
	 */
	public KNR2_MST getKNR2_MSTByKaicodeKnrcode2(String kaiCode, String knrCode2);

	/**
	 * @param dto
	 */
	public void insert(KNR2_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR2_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR2_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR2_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_2";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR2_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr2InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_2 >= ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param kaiCode
	 * @param knrCode2From
	 * @return List
	 */
	public List getKnr2InfoFrom(String kaiCode, String knrCode2From);

	// 区間のデータを取得
	/**  */
	public String getKnr2InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_2 <= ? ORDER BY KNR_CODE_2 ";

	/**
	 * @param kaiCode
	 * @param knrCode2To
	 * @return List
	 */
	public List getKnr2InfoTo(String kaiCode, String knrCode2To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_2,KNR_NAME_S_2,KNR_NAME_K_2,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param knrCode
	 * @param knrName_S
	 * @param knrName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String knrCode, String knrName_S, String knrName_K, String beginCode,
		String endCode);

	/** 引数 */
	public String searchKnrMstData_ARGS = "kaiCode,knrCode,knrName,knrName_K,slipDate,startCode,endCode";

	/**
	 * 管理マスタ検索
	 * 
	 * @param kaiCode 会社コー
	 * @param knrCode 管理コード
	 * @param knrName 管理略称
	 * @param knrName_K 管理検索名
	 * @param slipDate 伝票日付
	 * @param startCode 開始コード
	 * @param endCode 終了コード
	 * @return 管理マスタリスト
	 */
	public List searchKnrMstData(String kaiCode, String knrCode, String knrName, String knrName_K, Date slipDate,
		String startCode, String endCode);

	/** パラメーター */
	public String searchKnrNameS2_ARGS = "slipDate, kaiCode, knrCode, beginCode, endCode";

	/**
	 * 管理2マスタデータの管理2略称を検索<BR>
	 * 管理2マスタデータの管理2略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理2コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理2マスタヘッダーデータ
	 */
	public KNR2_MST searchKnrNameS2(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR2_MST> getData(KnrMstParam param);

	/** パラメーター */
	public String getKnr2MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR2_MST> getKnr2MstData(KnrMstParam param);

	
}

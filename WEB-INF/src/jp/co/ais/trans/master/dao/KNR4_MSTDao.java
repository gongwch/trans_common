package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理４マスタDao
 */
public interface KNR4_MSTDao {

	/**  */
	public Class BEAN = KNR4_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR4_MST();

	/**  */
	public String getKnr4Info_QUERY = "KAI_CODE = ? AND KNR_CODE_4 BETWEEN ? AND ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_4_FROM
	 * @param KNR_CODE_4_TO
	 * @return List
	 */
	public List getKnr4Info(String KAI_CODE, String KNR_CODE_4_FROM, String KNR_CODE_4_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR4_MSTByKaicodeKnrcode4_ARGS = "KAI_CODE,KNR_CODE_4";

	/**
	 * @param kaiCode
	 * @param knrCode4
	 * @return KNR4_MST
	 */
	public KNR4_MST getKNR4_MSTByKaicodeKnrcode4(String kaiCode, String knrCode4);

	/**
	 * @param dto
	 */
	public void insert(KNR4_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR4_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR4_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR4_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_4";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR4_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr4InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_4 >= ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param kaiCode
	 * @param knrCode4From
	 * @return List
	 */
	public List getKnr4InfoFrom(String kaiCode, String knrCode4From);

	// 区間のデータを取得
	/**  */
	public String getKnr4InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_4 <= ? ORDER BY KNR_CODE_4 ";

	/**
	 * @param kaiCode
	 * @param knrCode4To
	 * @return List
	 */
	public List getKnr4InfoTo(String kaiCode, String knrCode4To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_4,KNR_NAME_S_4,KNR_NAME_K_4,beginCode,endCode";

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
	 * 管理マスタ4検索
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
	public String searchKnrNameS4_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * 管理4マスタデータの管理4略称を検索<BR>
	 * 管理4マスタデータの管理4略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理4コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理4マスタヘッダーデータ
	 */
	public KNR4_MST searchKnrNameS4(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);



	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR4_MST> getData(KnrMstParam param);
	
	/** パラメーター */
	public String getKnr4MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR4_MST> getKnr4MstData(KnrMstParam param);

}

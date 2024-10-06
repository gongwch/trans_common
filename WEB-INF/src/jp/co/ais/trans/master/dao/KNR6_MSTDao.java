package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理６マスタDao
 */
public interface KNR6_MSTDao {

	/**  */
	public Class BEAN = KNR6_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR6_MST();

	/**  */
	public String getKnr6Info_QUERY = "KAI_CODE = ? AND KNR_CODE_6 BETWEEN ? AND ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_6_FROM
	 * @param KNR_CODE_6_TO
	 * @return List
	 */
	public List getKnr6Info(String KAI_CODE, String KNR_CODE_6_FROM, String KNR_CODE_6_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR6_MSTByKaicodeKnrcode6_ARGS = "KAI_CODE,KNR_CODE_6";

	/**
	 * @param kaiCode
	 * @param knrCode6
	 * @return KNR6_MST
	 */
	public KNR6_MST getKNR6_MSTByKaicodeKnrcode6(String kaiCode, String knrCode6);

	/**
	 * @param dto
	 */
	public void insert(KNR6_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR6_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR6_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR6_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_6";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR6_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr6InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_6 >= ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param kaiCode
	 * @param knrCode6From
	 * @return List
	 */
	public List getKnr6InfoFrom(String kaiCode, String knrCode6From);

	// 区間のデータを取得
	/**  */
	public String getKnr6InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_6 <= ? ORDER BY KNR_CODE_6 ";

	/**
	 * @param kaiCode
	 * @param knrCode6To
	 * @return List
	 */
	public List getKnr6InfoTo(String kaiCode, String knrCode6To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_6,KNR_NAME_S_6,KNR_NAME_K_6,beginCode,endCode";

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
	public String searchKnrNameS6_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * 管理6マスタデータの管理6略称を検索<BR>
	 * 管理6マスタデータの管理6略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理6コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理61マスタヘッダーデータ
	 */
	public KNR6_MST searchKnrNameS6(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR6_MST> getData(KnrMstParam param);

	/** パラメーター */
	public String getKnr6MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR6_MST> getKnr6MstData(KnrMstParam param);

}

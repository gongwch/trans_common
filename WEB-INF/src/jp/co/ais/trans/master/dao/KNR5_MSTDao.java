package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理５マスタDao
 */
public interface KNR5_MSTDao {

	/**  */
	public Class BEAN = KNR5_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR5_MST();

	/**  */
	public String getKnr5Info_QUERY = "KAI_CODE = ? AND KNR_CODE_5 BETWEEN ? AND ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_5_FROM
	 * @param KNR_CODE_5_TO
	 * @return List
	 */
	public List getKnr5Info(String KAI_CODE, String KNR_CODE_5_FROM, String KNR_CODE_5_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR5_MSTByKaicodeKnrcode5_ARGS = "KAI_CODE,KNR_CODE_5";

	/**
	 * @param kaiCode
	 * @param knrCode5
	 * @return KNR5_MST
	 */
	public KNR5_MST getKNR5_MSTByKaicodeKnrcode5(String kaiCode, String knrCode5);

	/**
	 * @param dto
	 */
	public void insert(KNR5_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR5_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR5_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR5_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_5";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR5_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr5InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_5 >= ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param kaiCode
	 * @param knrCode5From
	 * @return List
	 */
	public List getKnr5InfoFrom(String kaiCode, String knrCode5From);

	// 区間のデータを取得
	/**  */
	public String getKnr5InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_5 <= ? ORDER BY KNR_CODE_5 ";

	/**
	 * @param kaiCode
	 * @param knrCode5To
	 * @return List
	 */
	public List getKnr5InfoTo(String kaiCode, String knrCode5To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_5,KNR_NAME_S_5,KNR_NAME_K_5,beginCode,endCode";

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
	public String searchKnrNameS5_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * 管理5マスタデータの管理5略称を検索<BR>
	 * 管理5マスタデータの管理5略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理5コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理5マスタヘッダーデータ
	 */
	public KNR5_MST searchKnrNameS5(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);



	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR5_MST> getData(KnrMstParam param);
	
	/** パラメーター */
	public String getKnr5MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR5_MST> getKnr5MstData(KnrMstParam param);

}

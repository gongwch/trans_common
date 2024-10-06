package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理１マスタDao
 */
public interface KNR1_MSTDao {

	/**  */
	public Class BEAN = KNR1_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR1_MST();

	/**  */
	public String getKnr1Info_QUERY = "KAI_CODE = ? AND KNR_CODE_1 BETWEEN ? AND ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_1_FROM
	 * @param KNR_CODE_1_TO
	 * @return List
	 */
	public List getKnr1Info(String KAI_CODE, String KNR_CODE_1_FROM, String KNR_CODE_1_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR1_MSTByKaicodeKnrcode1_ARGS = "KAI_CODE,KNR_CODE_1";

	/**
	 * @param kaiCode
	 * @param knrCode1
	 * @return KNR1_MST
	 */
	public KNR1_MST getKNR1_MSTByKaicodeKnrcode1(String kaiCode, String knrCode1);

	/**
	 * @param dto
	 */
	public void insert(KNR1_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR1_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR1_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR1_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR1_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr1InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_1 >= ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @param knrCode1From
	 * @return List
	 */
	public List getKnr1InfoFrom(String kaiCode, String knrCode1From);

	// 区間のデータを取得
	/**  */
	public String getKnr1InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_1 <= ? ORDER BY KNR_CODE_1 ";

	/**
	 * @param kaiCode
	 * @param knrCode1To
	 * @return List
	 */
	public List getKnr1InfoTo(String kaiCode, String knrCode1To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_1,KNR_NAME_S_1,KNR_NAME_K_1,beginCode,endCode";

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
	public String searchKnrNameS1_ARGS = "slipDate, kaiCode, knrCode,beginCode,endCode";

	/**
	 * 管理1マスタデータの管理1略称を検索<BR>
	 * 管理1マスタデータの管理1略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理1コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理1マスタヘッダーデータ
	 */
	public KNR1_MST searchKnrNameS1(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR1_MST> getData(KnrMstParam param);
	
	/** パラメーター */
	public String getKnr1MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR1_MST> getKnr1MstData(KnrMstParam param);

	
}

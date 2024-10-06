package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 管理３マスタDao
 */
public interface KNR3_MSTDao {

	/**  */
	public Class BEAN = KNR3_MST.class;

	/**
	 * @return List
	 */
	public List getAllKNR3_MST();

	/**  */
	public String getKnr3Info_QUERY = "KAI_CODE = ? AND KNR_CODE_3 BETWEEN ? AND ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param KAI_CODE
	 * @param KNR_CODE_3_FROM
	 * @param KNR_CODE_3_TO
	 * @return List
	 */
	public List getKnr3Info(String KAI_CODE, String KNR_CODE_3_FROM, String KNR_CODE_3_TO);

	// 指定されたデータの取得
	/**  */
	public String getKNR3_MSTByKaicodeKnrcode3_ARGS = "KAI_CODE,KNR_CODE_3";

	/**
	 * @param kaiCode
	 * @param knrCode3
	 * @return KNR3_MST
	 */
	public KNR3_MST getKNR3_MSTByKaicodeKnrcode3(String kaiCode, String knrCode3);

	/**
	 * @param dto
	 */
	public void insert(KNR3_MST dto);

	/**
	 * @param dto
	 */
	public void update(KNR3_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KNR3_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllKNR3_MST2_QUERY = "KAI_CODE = ? ORDER BY KNR_CODE_3";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKNR3_MST2(String kaiCode);

	// 区間のデータを取得
	/**  */
	public String getKnr3InfoFrom_QUERY = "KAI_CODE = ? AND KNR_CODE_3 >= ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param kaiCode
	 * @param knrCode3From
	 * @return List
	 */
	public List getKnr3InfoFrom(String kaiCode, String knrCode3From);

	// 区間のデータを取得
	/**  */
	public String getKnr3InfoTo_QUERY = "KAI_CODE = ? AND KNR_CODE_3 <= ? ORDER BY KNR_CODE_3 ";

	/**
	 * @param kaiCode
	 * @param knrCode3To
	 * @return List
	 */
	public List getKnr3InfoTo(String kaiCode, String knrCode3To);

	// ＲＥＦ検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,KNR_CODE_3,KNR_NAME_S_3,KNR_NAME_K_3,beginCode,endCode";

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
	 * 管理マスタ3検索
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
	public String searchKnrNameS3_ARGS = "slipDate, kaiCode, knrCode, beginCode, endCode";

	/**
	 * 管理3マスタデータの管理3略称を検索<BR>
	 * 管理3マスタデータの管理3略称を検索
	 * 
	 * @param slipDate 伝票日付
	 * @param kaiCode 会社コード
	 * @param knrCode 管理3コード
	 * @param beginCode
	 * @param endCode
	 * @return 管理3マスタヘッダーデータ
	 */
	public KNR3_MST searchKnrNameS3(Date slipDate, String kaiCode, String knrCode, String beginCode, String endCode);


	/** パラメーター */
	public String getData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR3_MST> getData(KnrMstParam param);

	/** パラメーター */
	public String getKnr3MstData_ARGS = "param";
	
	/**
	 * 管理マスタデータ取得
	 * 
	 * @param param
	 * @return 管理マスタリスト
	 */
	public List<KNR3_MST> getKnr3MstData(KnrMstParam param);

}

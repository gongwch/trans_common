package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 銀行マスタDao
 */
public interface BNK_MSTDao {

	/**  */
	public Class BEAN = BNK_MST.class;

	/**
	 * @return List
	 */
	public List getAllBNK_MST();

	/**  */
	public String getBnkInfo_QUERY = "BNK_CODE = ? AND BNK_STN_CODE BETWEEN ? AND ? ORDER BY BNK_CODE, BNK_STN_CODE ";

	/**
	 * @param BNK_CODE
	 * @param BNK_STN_CODE_FROM
	 * @param BNK_STN_CODE_TO
	 * @return List
	 */
	public List getBnkInfo(String BNK_CODE, String BNK_STN_CODE_FROM, String BNK_STN_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getBNK_MSTByBnkcodeBnkStncode_ARGS = "BNK_CODE, BNK_STN_CODE";

	/**
	 * @param bnkCode
	 * @param BnkStnCode
	 * @return BNK_MST
	 */
	public BNK_MST getBNK_MSTByBnkcodeBnkStncode(String bnkCode, String BnkStnCode);

	/**
	 * @param dto
	 */
	public void insert(BNK_MST dto);

	/**
	 * @param dto
	 */
	public void update(BNK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(BNK_MST dto);

	// 下記は ISFnet China 追加分

	// 該当銀行のすべてのレコードを取得
	/**  */
	public String getAllBNK_MST2_QUERY = "ORDER BY BNK_CODE, BNK_STN_CODE";

	/**
	 * @return List
	 */
	public List getAllBNK_MST2();

	/**  */
	public String getBnkInfo1_QUERY = "BNK_CODE = ? ORDER BY BNK_CODE,BNK_STN_CODE";

	/**
	 * @param bnkCode
	 * @return List
	 */
	public List getBnkInfo1(String bnkCode);

	// 区間のデータを取得
	/**  */
	public String getBnkInfo2From_QUERY = "BNK_CODE = ? AND BNK_STN_CODE >= ? ORDER BY BNK_CODE,BNK_STN_CODE ";

	/**
	 * @param bnkCode
	 * @param stnCodeFrom
	 * @return List
	 */
	public List getBnkInfo2From(String bnkCode, String stnCodeFrom);

	// 区間のデータを取得
	/**  */
	public String getBnkInfo2To_QUERY = "BNK_CODE = ? AND BNK_STN_CODE <= ? ORDER BY BNK_CODE,BNK_STN_CODE ";

	/**
	 * @param bnkCode
	 * @param stnCodeTo
	 * @return List
	 */
	public List getBnkInfo2To(String bnkCode, String stnCodeTo);

	// 区間のデータを取得
	/**  */
	public String getBnkInfo2FromTo_QUERY = "BNK_CODE = ? AND BNK_STN_CODE BETWEEN ? AND ? ORDER BY BNK_CODE,BNK_STN_CODE";

	/**
	 * @param bnkCode
	 * @param stnCodeFrom
	 * @param stnCodeTo
	 * @return List
	 */
	public List getBnkInfo2FromTo(String bnkCode, String stnCodeFrom, String stnCodeTo);

	// 銀行のＲＥＦ検索
	/**  */
	public String conditionSearch1_ARGS = "bnkCode,bnkName_S,bnkName_K";

	/**
	 * @param bnkCode
	 * @param bnkName_S
	 * @param bnkName_K
	 * @return List
	 */
	public List conditionSearch1(String bnkCode, String bnkName_S, String bnkName_K);

	// 銀行支店のＲＥＦ検索
	/**  */
	public String conditionSearch2_ARGS = "bnkCode,stnCode,stnName_S,stnName_K,beginCode,endCode";

	/**
	 * @param bnkCode
	 * @param stnCode
	 * @param stnName_S
	 * @param stnName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch2(String bnkCode, String stnCode, String stnName_S, String stnName_K, String beginCode,
		String endCode);

	// 銀行を取得
	/**  */
	public String getBNK_MST2_ARGS = "BNK_CODE";

	/**
	 * @param bnkCode
	 * @return BNK_MST
	 */
	public BNK_MST getBNK_MST2(String bnkCode);

	/** パラメータ */
	public String getBankList_ARGS = "param";

	/**
	 * 銀行口座情報取得 PK検索（支店コード除外）
	 * 
	 * @param param
	 * @return 銀行情報
	 */
	public List<BNK_MST> getBankList(BnkMstParameter param);

	/** パラメータ */
	public String getStnList_ARGS = "param";

	/**
	 * 銀行支店情報取得 PK検索
	 * 
	 * @param param
	 * @return 銀行支店情報リスト
	 */
	public List<BNK_MST> getStnList(BnkMstParameter param);

}

package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface TEK_MSTDao {

	/**  */
	public Class BEAN = TEK_MST.class;

	/**
	 * @return List
	 */
	public List getAllTEK_MST();

	/**  */
	public String getTekInfo_QUERY = "KAI_CODE = ? AND DATA_KBN BETWEEN ? AND ? AND TEK_KBN BETWEEN ? AND ? ORDER BY DATA_KBN, TEK_KBN ";

	/**
	 * @param KAI_CODE
	 * @param DATA_KBN_FROM
	 * @param DATA_KBN_TO
	 * @param TEK_KBN_FROM
	 * @param TEK_KBN_TO
	 * @return List
	 */
	public List getTekInfo(String KAI_CODE, String DATA_KBN_FROM, String DATA_KBN_TO, int TEK_KBN_FROM, int TEK_KBN_TO);

	// 指定されたデータの取得
	/**  */
	public String getTEK_MSTByKaicodeDatekbnTekkbn_ARGS = "KAI_CODE, DATA_KBN, TEK_KBN";

	/**
	 * @param kaiCode
	 * @param DATA_KBN
	 * @param TEK_KBN
	 * @return TEK_MST
	 */
	public TEK_MST getTEK_MSTByKaicodeDatekbnTekkbn(String kaiCode, String DATA_KBN, int TEK_KBN);

	/**
	 * @param dto
	 */
	public void insert(TEK_MST dto);

	/**
	 * @param dto
	 */
	public void update(TEK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(TEK_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllTEK_MST2_QUERY = "KAI_CODE = ? ORDER BY TEK_KBN, TEK_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllTEK_MST2(String kaiCode);

	/**  */
	public String getAllCode_MST2_QUERY = "KAI_CODE = ? AND TEK_CODE BETWEEN ? AND ? AND TEK_KBN=? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeFrom
	 * @param tekCodeTo
	 * @param tekKbn
	 * @return List
	 */
	public List getAllCode_MST2(String kaiCode, String tekCodeFrom, String tekCodeTo, String tekKbn);

	/**  */
	public String getTekInfo2_QUERY = "KAI_CODE = ? AND TEK_CODE BETWEEN ? AND ? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeFrom
	 * @param tekCodeTo
	 * @return List
	 */
	public List getTekInfo2(String kaiCode, String tekCodeFrom, String tekCodeTo);

	/**  */
	public String getTekInfoFrom_QUERY = "KAI_CODE = ? AND TEK_CODE >= ? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeFrom
	 * @return List
	 */
	public List getTekInfoFrom(String kaiCode, String tekCodeFrom);

	/**  */
	public String getTekInfoTo_QUERY = "KAI_CODE = ? AND TEK_CODE <= ? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeTo
	 * @return List
	 */
	public List getTekInfoTo(String kaiCode, String tekCodeTo);

	//
	/**  */
	public String getAllTEK_MST3_ARGS = "KAI_CODE,TEK_KBN";

	/**
	 * @param kaiCode
	 * @param tekKbn
	 * @return List
	 */
	public List getAllTEK_MST3(String kaiCode, String tekKbn);

	/**  */
	public String getTekInfoFrom2_QUERY = "KAI_CODE = ? AND TEK_CODE >= ? AND TEK_KBN = ? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeFrom
	 * @param tekKbn
	 * @return List
	 */
	public List getTekInfoFrom2(String kaiCode, String tekCodeFrom, String tekKbn);

	/**  */
	public String getTekInfoTo2_QUERY = "KAI_CODE = ? AND TEK_CODE <= ? AND TEK_KBN = ? ORDER BY TEK_KBN, TEK_CODE ";

	/**
	 * @param kaiCode
	 * @param tekCodeTo
	 * @param tekKbn
	 * @return List
	 */
	public List getTekInfoTo2(String kaiCode, String tekCodeTo, String tekKbn);

	// 指定されたデータの取得
	/**  */
	public String getTEK_MST_ARGS = "KAI_CODE, TEK_KBN, TEK_CODE";

	/**
	 * @param kaiCode
	 * @param tekKbn
	 * @param tekCode
	 * @return TEK_MST
	 */
	public TEK_MST getTEK_MST(String kaiCode, int tekKbn, String tekCode);

	// 指定されたデータの取得
	/**  */
	public String getTEK_MSTData_ARGS = "KAI_CODE, TEK_KBN, TEK_CODE, DATA_KBN";

	/**
	 * @param kaiCode
	 * @param tekKbn
	 * @param tekCode
	 * @param dataKbn 
	 * @return TEK_MST
	 */
	public TEK_MST getTEK_MSTData(String kaiCode, int tekKbn, String tekCode, String dataKbn);

	// REF検索
	/**  */
	public String conditionSearch_ARGS = "kaiCode,tekCode,tekName,tekName_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param tekCode
	 * @param tekName_S
	 * @param tekName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String tekCode, String tekName_S, String tekName_K, String beginCode,
		String endCode);

	/**  */
	public String conditionSearch2_ARGS = "KAI_CODE,TEK_CODE";

	/**
	 * @param kaiCode
	 * @param tekCode
	 * @return List
	 */
	public List conditionSearch2(String kaiCode, String tekCode);

	// 共通ダイアログ用SQL文
	/**  */
	public String refDialogSearch_ARGS = "kaiCode,tekCode,kName,sName,memoType,slipType,termBasisDate";

	/**
	 * @param kaiCode
	 * @param tekCode
	 * @param kName
	 * @param sName
	 * @param memoType
	 * @param slipType
	 * @param termBasisDate
	 * @return List
	 */
	public List<Object> refDialogSearch(String kaiCode, String tekCode, String kName, String sName, int memoType,
		String slipType, Timestamp termBasisDate);

	/** パラメーター */
	public String getTekMstData_ARGS = "kaiCode,tekCode,tekKbn,dataKbn,slipDate";

	/**
	 * 摘要マスタ検索
	 * 
	 * @param kaiCode
	 * @param tekCode
	 * @param tekKbn
	 * @param dataKbn 
	 * @param slipDate
	 * @return List
	 */
	public List getTekMstData(String kaiCode, String tekCode, int tekKbn,String dataKbn, Date slipDate);

}

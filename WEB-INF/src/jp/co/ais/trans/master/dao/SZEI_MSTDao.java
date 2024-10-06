package jp.co.ais.trans.master.dao;

import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.master.entity.*;

/**
 * 消費税マスタDao
 */
/**
 * 
 */
public interface SZEI_MSTDao {

	/** */
	public Class BEAN = SZEI_MST.class;

	/**
	 * @return List
	 */
	public List getAllSZEI_MST();

	/**  */
	public String getSzeiInfo_QUERY = "KAI_CODE = ? AND ZEI_CODE BETWEEN ? AND ? ORDER BY ZEI_CODE ";

	/**
	 * @param KAI_CODE
	 * @param ZEI_CODE_FROM
	 * @param ZEI_CODE_TO
	 * @return List
	 */
	public List getSzeiInfo(String KAI_CODE, String ZEI_CODE_FROM, String ZEI_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getSZEI_MSTByKaicodeSzeicode_ARGS = "KAI_CODE, ZEI_CODE";

	/**
	 * @param kaiCode
	 * @param zeiCode
	 * @return SZEI_MST
	 */
	public SZEI_MST getSZEI_MSTByKaicodeSzeicode(String kaiCode, String zeiCode);

	/**
	 * @param dto
	 */
	public void insert(SZEI_MST dto);

	/**
	 * @param dto
	 */
	public void update(SZEI_MST dto);

	/**
	 * @param dto
	 */
	public void delete(SZEI_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllSZEI_MST2_QUERY = "KAI_CODE = ? ORDER BY ZEI_CODE";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllSZEI_MST2(String kaiCode);

	/**  */
	public String getSzeiInfoFrom_QUERY = "KAI_CODE = ? AND ZEI_CODE >= ? ORDER BY ZEI_CODE ";

	/**
	 * @param kaiCode
	 * @param zeiCodeFrom
	 * @return List
	 */
	public List getSzeiInfoFrom(String kaiCode, String zeiCodeFrom);

	/**  */
	public String getSzeiInfoTo_QUERY = "KAI_CODE = ? AND ZEI_CODE <= ? ORDER BY ZEI_CODE ";

	/**
	 * @param kaiCode
	 * @param zeiCodeTo
	 * @return List
	 */
	public List getSzeiInfoTo(String kaiCode, String zeiCodeTo);

	// REF検索
	/**  */
	public String conditionSearch_ARGS = "kaiCode,zeiCode,zeiName_S,zeiName_K,beginCode,endCode,kind";

	/**
	 * @param kaiCode
	 * @param zeiCode
	 * @param zeiName_S
	 * @param zeiName_K
	 * @param beginCode
	 * @param endCode
	 * @param kind
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String zeiCode, String zeiName_S, String zeiName_K, String beginCode,
		String endCode, String kind);

	// 共通ダイアログ用DAOメソッド
	/**  */
	public String refDialogSearch_ARGS = "kaiCode,zeiCode,sName,kName,sales,purchase,elseTax,termBasisDate";

	/**
	 * @param kaiCode
	 * @param zeiCode
	 * @param sName
	 * @param kName
	 * @param sales
	 * @param purchase
	 * @param elseTax
	 * @param termBasisDate
	 * @return List
	 */
	public List<Object> refDialogSearch(String kaiCode, String zeiCode, String sName, String kName, String sales,
		String purchase, String elseTax, Timestamp termBasisDate);
	
	/** パラメーター  */
	public String getSzeiMstData_ARGS = "kaiCode,zeiCode,slipDate";

	/**
	 * 消費税マスタデータの取得
	 * 
	 * @param kaiCode
	 * @param zeiCode
	 * @param slipDate 
	 * @return List
	 */
	public List getSzeiMstData(String kaiCode, String zeiCode, Date slipDate);
}

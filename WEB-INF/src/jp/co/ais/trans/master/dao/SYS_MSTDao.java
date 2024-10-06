package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * システム区分マスタDao
 */
public interface SYS_MSTDao {

	/** Bean */
	public Class BEAN = SYS_MST.class;

	/**
	 * 全体検索
	 * 
	 * @return システム区分
	 */
	public List getAllSYS_MST();

	/** パラメータ */
	public String getSysInfo_QUERY = "KAI_CODE = ? AND SYS_KBN BETWEEN ? AND ? ORDER BY SYS_KBN ";

	/**
	 * 範囲検索
	 * 
	 * @param KAI_CODE
	 * @param SYS_KBNE_FROM
	 * @param SYS_KBN_TO
	 * @return システム区分
	 */
	public List getSysInfo(String KAI_CODE, String SYS_KBNE_FROM, String SYS_KBN_TO);

	/** パラメータ */
	public String getSYS_MSTByKaicodeSyskbn_ARGS = "KAI_CODE, SYS_KBN";

	/**
	 * 指定されたシステム区分の取得
	 * 
	 * @param kaiCode
	 * @param sysKBN
	 * @return システム区分
	 */
	public SYS_MST getSYS_MSTByKaicodeSyskbn(String kaiCode, String sysKBN);

	/**
	 * @param dto
	 */
	public void insert(SYS_MST dto);

	/**
	 * @param dto
	 */
	public void update(SYS_MST dto);

	/**
	 * @param dto
	 */
	public void delete(SYS_MST dto);

	// 下記は ISFnet China 追加分
	/** パラメータ */
	public String getAllSYS_MST2_QUERY = "KAI_CODE = ? ORDER BY SYS_KBN";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllSYS_MST2(String kaiCode);

	/** パラメータ */
	public String getSysInfoFrom_QUERY = "KAI_CODE = ? AND SYS_KBN >= ? ORDER BY SYS_KBN ";

	/**
	 * @param kaiCode
	 * @param sysKbnFrom
	 * @return List
	 */
	public List getSysInfoFrom(String kaiCode, String sysKbnFrom);

	/** パラメータ */
	public String getSysInfoTo_QUERY = "KAI_CODE = ? AND SYS_KBN <= ? ORDER BY SYS_KBN ";

	/**
	 * @param kaiCode
	 * @param sysKbnTo
	 * @return List
	 */
	public List getSysInfoTo(String kaiCode, String sysKbnTo);

	/** パラメータ */
	public String conditionSearch_ARGS = "KAI_CODE,SYS_KBN,SYS_NAME_S,SYS_NAME_K,beginCode,endCode";

	/**
	 * REF検索
	 * 
	 * @param kaiCode
	 * @param sysKbn
	 * @param sysName_S
	 * @param sysName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String sysKbn, String sysName_S, String sysName_K, String beginCode,
		String endCode);
}

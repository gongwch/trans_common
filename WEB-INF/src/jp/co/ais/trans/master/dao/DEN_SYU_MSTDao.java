package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 伝票種別マスタDAO
 */
public interface DEN_SYU_MSTDao {

	/**  */
	public Class BEAN = DEN_SYU_MST.class;

	/**
	 * @return List
	 */
	public List getAllDEN_SYU_MST();

	/**  */
	public String getDenSyuInfo_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE BETWEEN ? AND ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param KAI_CODE
	 * @param DEN_SYU_CODE_FROM
	 * @param DEN_SYU_CODE_TO
	 * @return List
	 */
	public List getDenSyuInfo(String KAI_CODE, String DEN_SYU_CODE_FROM, String DEN_SYU_CODE_TO);

	// 指定されたデータの取得
	/**  */
	public String getDEN_SYU_MSTByKaicodeDensyucode_ARGS = "KAI_CODE, DEN_SYU_CODE";

	/**
	 * @param kaiCode
	 * @param densyuCode
	 * @return DEN_SYU_MST
	 */
	public DEN_SYU_MST getDEN_SYU_MSTByKaicodeDensyucode(String kaiCode, String densyuCode);

	/**  */
	public String getDEN_SYU_MSTByKaicodeSyskbnDatakbn_QUERY = " KAI_CODE = ? AND SYS_KBN = ? AND DATA_KBN IN ('13','23','31')";

	/**
	 * @param dto
	 */
	public void insert(DEN_SYU_MST dto);

	/**
	 * @param dto
	 */
	public void update(DEN_SYU_MST dto);

	/**
	 * @param dto
	 */
	public void delete(DEN_SYU_MST dto);

	/**
	 * システム区分とデータ区分を指定したデータを取得
	 * 
	 * @param strKaiCode
	 * @param strSysKbn
	 * @return List
	 */
	public List getDEN_SYU_MSTByKaicodeSyskbnDatakbn(String strKaiCode, String strSysKbn);

	/** パラメーター */
	public String getOtherSystem_ARGS = "KAI_CODE, DEN_SYU_CODE";

	/**
	 * 伝票種別マスタを検索する
	 * 
	 * @param kaiCode 会社コード
	 * @param DenSyuCode 伝票種別コード
	 * @return DEN_SYU_MST
	 */
	public DEN_SYU_MST getOtherSystem(String kaiCode, String DenSyuCode);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllDEN_SYU_MST2_QUERY = "KAI_CODE = ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllDEN_SYU_MST2(String kaiCode);

	/**  */
	public String getDenSyuInfoFrom_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE>= ? ORDER BY DEN_SYU_CODE";

	/**
	 * @param kaiCode
	 * @param denSyuCodeFrom
	 * @return List
	 */
	public List getDenSyuInfoFrom(String kaiCode, String denSyuCodeFrom);

	/**  */
	public String getDenSyuInfoTo_QUERY = "KAI_CODE = ? AND DEN_SYU_CODE<= ? ORDER BY DEN_SYU_CODE ";

	/**
	 * @param kaiCode
	 * @param denSyuCodeTo
	 * @return List
	 */
	public List getDenSyuInfoTo(String kaiCode, String denSyuCodeTo);

	// 条件検索
	/**  */
	public String conditionSearch_ARGS = "KAI_CODE,DEN_SYU_CODE,DEN_SYU_NAME_S,DEN_SYU_NAME_K,beginCode,endCode";

	/**
	 * @param kaiCode
	 * @param denSyuCode
	 * @param denSyuName_S
	 * @param denSyuName_K
	 * @param beginCode
	 * @param endCode
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String denSyuCode, String denSyuName_S, String denSyuName_K,
		String beginCode, String endCode);

	/** 伝種リスト検索引数 */
	public String getDenSyuList_ARGS = "kaiCode,isIncludeSystemEls";

	/**
	 * @param kaiCode 会社コード
	 * @param isIncludeSystemEls true:他システムから取り込んだ伝票種別を対象とする
	 * @return 結果リスト
	 */
	public List<DEN_SYU_MST> getDenSyuList(String kaiCode, boolean isIncludeSystemEls);

	/** 伝票種別コード取得 SQL */
	public String getDataKbnDenSyu_SQL = "SELECT MIN(DEN_SYU_CODE) FROM DEN_SYU_MST WHERE KAI_CODE = ? AND SYS_KBN = ? AND DATA_KBN = ?";

	/**
	 * データ区分に当る伝票種別コード取得
	 * 
	 * @param kaiCode 会社コード
	 * @param sysKbn システム区分
	 * @param dataKbn データ区分
	 * @return 伝票種別コード
	 */
	public String getDataKbnDenSyu(String kaiCode, String sysKbn, String dataKbn);
}

package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 科目体系名称マスタDao
 */
public interface KMK_TK_MSTDao {

	/**  */
	public Class BEAN = KMK_TK_MST.class;

	/**  */
	public String getKMK_TK_MST_ARGS = "KAI_CODE, KMT_CODE";

	/**
	 * @param kaiCode
	 * @param kmtCode
	 * @return KMK_TK_MST
	 */
	public KMK_TK_MST getKMK_TK_MST(String kaiCode, String kmtCode);

	// 条件検索

	/**  */
	public String conditionSearch_ARGS = "kaiCode,kmtCode,kmtName_S,katName_K";

	/**
	 * @param kaiCode
	 * @param kmtCode
	 * @param kmtName_S
	 * @param katName_K
	 * @return List
	 */
	public List conditionSearch(String kaiCode, String kmtCode, String kmtName_S, String katName_K);

	/**
	 * @param dto
	 */
	public void insert(KMK_TK_MST dto);

	/**
	 * @param dto
	 */
	public void update(KMK_TK_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KMK_TK_MST dto);

	/** パラメーター */
	public String searchKmtMstData_ARGS = "kaiCode,kmtCode,kmtName,kmtName_K,startCode,endCode";

	/**
	 * 科目体系マスタ検索
	 * 
	 * @param kaiCode 会社コード
	 * @param kmtCode 科目体系コード
	 * @param kmtName 科目体系略称
	 * @param kmtName_K 科目体系検索名
	 * @param startCode 開始コード
	 * @param endCode 終了コード
	 * @return 科目体系リスト
	 */
	public List searchKmtMstData(String kaiCode, String kmtCode, String kmtName, String kmtName_K, String startCode,
		String endCode);

	/** パラメーター */
	public String getKmkTkMst_ARGS = "KAI_CODE, KMT_CODE";

	/**
	 * 科目体系名称データ検索 (1行)
	 * 
	 * @param kaiCode 会社コード
	 * @param kmtCode 科目体系名称コード
	 * @return KMK_TK_MST 科目体系名称データ
	 */
	public KMK_TK_MST getKmkTkMst(String kaiCode, String kmtCode);
}

package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface SWK_PTN_DTLDao {

	/**  */
	public Class BEAN = SWK_PTN_DTL.class;

	/**
	 * @return List
	 */
	public List getAllSWK_PTN_DTL();

	/**  */
	public String getSwkptnDtlInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return List
	 */
	public List getSwkptnDtlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	// 指定されたデータの取得
	/**  */
	public String getSWK_PTN_DTLByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return SWK_PTN_DTL
	 */
	public SWK_PTN_DTL getSWK_PTN_DTLByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**  */
	public String getSWK_PTN_DTLByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @return SWK_PTN_DTL
	 */
	public SWK_PTN_DTL getSWK_PTN_DTLByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/**
	 * @param dto
	 */
	public void insert(SWK_PTN_DTL dto);

	/**
	 * @param dto
	 */
	public void update(SWK_PTN_DTL dto);

	/**
	 * @param dto
	 */
	public void delete(SWK_PTN_DTL dto);

	/** 伝票の削除 */
	public static final String deleteByDenno_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ?";

	/**
	 * 指定されたKeyに紐づくデータを全て削除する
	 * 
	 * @param kaiCode
	 * @param slipNo 伝票番号
	 */
	public void deleteByDenno(String kaiCode, String slipNo);
}

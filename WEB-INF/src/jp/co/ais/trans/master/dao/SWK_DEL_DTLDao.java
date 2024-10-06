package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface SWK_DEL_DTLDao {

	/**  */
	public Class BEAN = SWK_DEL_DTL.class;

	/**
	 * @return List
	 */
	public List getAllSWK_DEL_DTL();

	/**  */
	public String getSwkDelDtlInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? AND SWK_GYO_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO, SWK_GYO_NO ";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @param SWK_GYO_NO_FROM
	 * @param SWK_GYO_NO_TO
	 * @return List
	 */
	public List getSwkDelDtlInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO, int SWK_GYO_NO_FROM, int SWK_GYO_NO_TO);

	// 指定されたデータの取得
	/**  */
	public String getSWK_DEL_DTLByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO ";

	/**
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return SWK_DEL_DTL
	 */
	public SWK_DEL_DTL getSWK_DEL_DTLByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**  */
	public String getSWK_DEL_DTLByKaicodeSwkdenno_ARGS = "KAI_CODE, SWK_DEN_NO ";

	/**
	 * @param kaiCode
	 * @param swkdenNO
	 * @return SWK_DEL_DTL
	 */
	public SWK_DEL_DTL getSWK_DEL_DTLByKaicodeSwkdenno(String kaiCode, String swkdenNO);

	/**
	 * @param dto
	 */
	public void insert(SWK_DEL_DTL dto);

	/**
	 * @param dto
	 */
	public void update(SWK_DEL_DTL dto);

	/**
	 * @param dto
	 */
	public void delete(SWK_DEL_DTL dto);
}

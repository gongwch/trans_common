package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface AR_SWK_DEL_HDRDao {

	/**  */
	public Class BEAN = AR_SWK_DEL_HDR.class;

	/**
	 * @return List
	 */
	public List getAllAR_SWK_DEL_HDR();

	/**  */
	public String getArSwkDelInfo_QUERY = "KAI_CODE = ? AND SWK_DEN_DATE BETWEEN ? AND ? AND SWK_DEN_NO BETWEEN ? AND ? ORDER BY SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * @param KAI_CODE
	 * @param SWK_DEN_DATE_FROM
	 * @param SWK_DEN_DATE_TO
	 * @param SWK_DEN_NO_FROM
	 * @param SWK_DEN_NO_TO
	 * @return List
	 */
	public List getArSwkDelInfo(String KAI_CODE, Date SWK_DEN_DATE_FROM, Date SWK_DEN_DATE_TO, String SWK_DEN_NO_FROM,
		String SWK_DEN_NO_TO);

	// 指定されたデータの取得
	/**  */
	public String getAR_SWK_DEL_HDRByKaicodeSwkdendateSwkdenno_ARGS = "KAI_CODE, SWK_DEN_DATE, SWK_DEN_NO";

	/**
	 * @param kaiCode
	 * @param swkdenDATE
	 * @param swkdenNO
	 * @return AR_SWK_DEL_HDR
	 */
	public AR_SWK_DEL_HDR getAR_SWK_DEL_HDRByKaicodeSwkdendateSwkdenno(String kaiCode, Date swkdenDATE, String swkdenNO);

	/**
	 * @param dto
	 */
	public void insert(AR_SWK_DEL_HDR dto);

	/**
	 * @param dto
	 */
	public void update(AR_SWK_DEL_HDR dto);

	/**
	 * @param dto
	 */
	public void delete(AR_SWK_DEL_HDR dto);
}

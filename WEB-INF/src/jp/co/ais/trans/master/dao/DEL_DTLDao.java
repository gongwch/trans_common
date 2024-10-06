package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface DEL_DTLDao {

	/**  */
	public Class BEAN = DEL_DTL.class;

	/**
	 * @return List
	 */
	public List getAllDEL_DTL();

	/**  */
	public String getDelDtlInfo_QUERY = "KAI_CODE = ? AND DEL_DEN_NO BETWEEN ? AND ? AND DEL_DEN_DATE BETWEEN ? AND ? ORDER BY DEL_DEN_NO, DEL_DEN_DATE ";

	/**
	 * @param KAI_CODE
	 * @param DEL_DEN_NO_FROM
	 * @param DEL_DEN_NO_TO
	 * @param DEL_DEN_DATE_FROM
	 * @param DEL_DEN_DATE_TO
	 * @return List
	 */
	public List getDelDtlInfo(String KAI_CODE, String DEL_DEN_NO_FROM, String DEL_DEN_NO_TO, Date DEL_DEN_DATE_FROM,
		Date DEL_DEN_DATE_TO);

	// 指定されたデータの取得
	/**  */
	public String getDEL_DTLByKaicodeDeldennoDeldendate_ARGS = "KAI_CODE, DEL_DEN_NO, DEL_DEN_DATE";

	/**
	 * @param kaiCode
	 * @param deldenNO
	 * @param deldenDATE
	 * @return DEL_DTL
	 */
	public DEL_DTL getDEL_DTLByKaicodeDeldennoDeldendate(String kaiCode, String deldenNO, Date deldenDATE);

	/**
	 * @param dto
	 */
	public void insert(DEL_DTL dto);

	/**
	 * @param dto
	 */
	public void update(DEL_DTL dto);

	/**
	 * @param dto
	 */
	public void delete(DEL_DTL dto);
}

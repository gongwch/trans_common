package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface AR_KAI_DATDao {

	/**  */
	public Class BEAN = AR_KAI_DAT.class;

	/**
	 * @return List
	 */
	public List getAllAR_KAI_DAT();

	/**  */
	public String getArKaiDatInfo_QUERY = "KAI_CODE = ? AND KAI_DEN_DATE BETWEEN ? AND ? AND KAI_DEN_NO BETWEEN ? AND ? AND KAI_GYO_NO BETWEEN ? AND ? ORDER BY KAI_DEN_DATE, KAI_DEN_NO, KAI_GYO_NO ";

	/**
	 * @param KAI_CODE
	 * @param KAI_DEN_DATE_FROM
	 * @param KAI_DEN_DATE_TO
	 * @param KAI_DEN_NO_FROM
	 * @param KAI_DEN_NO_TO
	 * @param KAI_GYO_NO_FROM
	 * @param KAI_GYO_NO_TO
	 * @return List
	 */
	public List getArKaiDatInfo(String KAI_CODE, Date KAI_DEN_DATE_FROM, Date KAI_DEN_DATE_TO, String KAI_DEN_NO_FROM,
		String KAI_DEN_NO_TO, int KAI_GYO_NO_FROM, int KAI_GYO_NO_TO);

	// 指定されたデータの取得
	/**  */
	public String getAR_KAI_DATByKaicodeKaidendateKaidennoKaigyono_ARGS = "KAI_CODE, KAI_DEN_DATE, KAI_DEN_NO, KAI_GYO_NO";

	/**
	 * @param kaiCode
	 * @param kaidenDATE
	 * @param kaidenNO
	 * @param kaigyoNO
	 * @return AR_KAI_DAT
	 */
	public AR_KAI_DAT getAR_KAI_DATByKaicodeKaidendateKaidennoKaigyono(String kaiCode, Date kaidenDATE,
		String kaidenNO, int kaigyoNO);

	/**
	 * @param dto
	 */
	public void insert(AR_KAI_DAT dto);

	/**
	 * @param dto
	 */
	public void update(AR_KAI_DAT dto);

	/**
	 * @param dto
	 */
	public void delete(AR_KAI_DAT dto);
}

package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface KJL_MSTDao {

	/**  */
	public Class BEAN = KJL_MST.class;

	/**
	 * @return List
	 */
	public List getAllKJL_MST();

	/**  */
	public String getKjlInfo_QUERY = "KAI_CODE = ? AND KJL_USR_ID BETWEEN ? AND ? AND KJL_KMT_CODE BETWEEN ? AND ? AND KJL_DPK_SSK BETWEEN ? AND ? ORDER BY KJL_USR_ID, KJL_KMT_CODE,KJL_DPK_SSK ";

	/**
	 * @param KAI_CODE
	 * @param KJL_USR_ID_FROM
	 * @param KJL_USR_ID_TO
	 * @param KJL_KMT_CODE_FROM
	 * @param KJL_KMT_CODE_TO
	 * @param KJL_DPK_SSK_FROM
	 * @param KJL_DPK_SSK_TO
	 * @return List
	 */
	public List getKjlInfo(String KAI_CODE, String KJL_USR_ID_FROM, String KJL_USR_ID_TO, String KJL_KMT_CODE_FROM,
		String KJL_KMT_CODE_TO, String KJL_DPK_SSK_FROM, String KJL_DPK_SSK_TO);

	// 指定されたデータの取得
	/**  */
	public String getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk_ARGS = "KAI_CODE, KJL_USR_ID, KJL_KMT_CODE, KJL_DPK_SSK";

	/**
	 * @param kaiCode
	 * @param kjlusrID
	 * @param kjlkmtCode
	 * @param kjldpkSSK
	 * @return KJL_MST
	 */
	public KJL_MST getKJL_MSTByKaicodeKjlusridKjlkmtcodeKjldpkssk(String kaiCode, String kjlusrID, String kjlkmtCode,
		String kjldpkSSK);

	/**
	 * @param dto
	 */
	public void insert(KJL_MST dto);

	/**
	 * @param dto
	 */
	public void update(KJL_MST dto);

	/**
	 * @param dto
	 */
	public void delete(KJL_MST dto);

	// 下記は ISFnet China 追加分

	/**  */
	public String getAllKJL_MST2_QUERY = "KAI_CODE = ? ORDER BY KJL_USR_ID";

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllKJL_MST2(String kaiCode);

	/**  */
	public String getIndicationLevelInfo_QUERY = "KAI_CODE = ? AND KJL_USR_ID BETWEEN ? AND ? ORDER BY KJL_USR_ID ";

	/**
	 * @param kaiCode
	 * @param kjlUsrIdFrom
	 * @param kjlUsrIdTo
	 * @return List
	 */
	public List getIndicationLevelInfo(String kaiCode, String kjlUsrIdFrom, String kjlUsrIdTo);

	/**  */
	public String getIndicationLevelInfoFrom_QUERY = "KAI_CODE = ? AND KJL_USR_ID >= ? ORDER BY KJL_USR_ID ";

	/**
	 * @param kaiCode
	 * @param kjlUsrIdFrom
	 * @return List
	 */
	public List getIndicationLevelInfoFrom(String kaiCode, String kjlUsrIdFrom);

	/**  */
	public String getIndicationLevelInfoTo_QUERY = "KAI_CODE = ? AND KJL_USR_ID <= ? ORDER BY KJL_USR_ID";

	/**
	 * @param kaiCode
	 * @param kjlUsrIdTo
	 * @return List
	 */
	public List getIndicationLevelInfoTo(String kaiCode, String kjlUsrIdTo);
}

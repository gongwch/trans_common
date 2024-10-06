package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * レートマスタDao
 */
public interface RATE_MSTDao {

	/**  */
	public Class BEAN = RATE_MST.class;

	/**
	 * @return List
	 */
	public List getAllRATE_MST();

	/**  */
	public String getNewRate_QUERY = "KAI_CODE = ? AND CUR_CODE = ? AND STR_DATE <= ? ORDER BY STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE
	 * @param START_DATE
	 * @return List
	 */
	public List getNewRate(String KAI_CODE, String CUR_CODE, Date START_DATE);

	/**  */
	public String getRateInfo_QUERY = "KAI_CODE = ? AND CUR_CODE BETWEEN ? AND ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_FROM
	 * @param CUR_CODE_TO
	 * @return List
	 */
	public List getRateInfo(String KAI_CODE, String CUR_CODE_FROM, String CUR_CODE_TO);

	/**  */
	public String getRateInfoFrom_QUERY = "KAI_CODE = ? AND CUR_CODE >= ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_FROM
	 * @return List
	 */
	public List getRateInfoFrom(String KAI_CODE, String CUR_CODE_FROM);

	/**  */
	public String getRateInfoTo_QUERY = "KAI_CODE = ? AND CUR_CODE <= ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_TO
	 * @return List
	 */
	public List getRateInfoTo(String KAI_CODE, String CUR_CODE_TO);

	/**
	 * @param dto
	 */
	public void insert(RATE_MST dto);

	/**
	 * @param dto
	 */
	public void update(RATE_MST dto);

	/**
	 * @param dto
	 */
	public void delete(RATE_MST dto);

	// 下記は ISFnet China 追加分

	// 当会社の全部レコード
	/**  */
	public String getAllRATE_MST2_QUERY = "KAI_CODE = ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @return List
	 */
	public List getAllRATE_MST2(String KAI_CODE);

	// 一つレコードを取得
	/**  */
	public String getRATE_MST_ARGS = "KAI_CODE, CUR_CODE, STR_DATE";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE
	 * @param STR_DATE
	 * @return RATE_MST
	 */
	public RATE_MST getRATE_MST(String KAI_CODE, String CUR_CODE, Date STR_DATE);

	/**  */
	public String getRateInfoFrom2_QUERY = "KAI_CODE = ? AND CUR_CODE >= ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_FROM
	 * @return List
	 */
	public List getRateInfoFrom2(String KAI_CODE, String CUR_CODE_FROM);

	/**  */
	public String getRateInfoTo2_QUERY = "KAI_CODE = ? AND CUR_CODE <= ? ORDER BY CUR_CODE, STR_DATE DESC";

	/**
	 * @param KAI_CODE
	 * @param CUR_CODE_TO
	 * @return List
	 */
	public List getRateInfoTo2(String KAI_CODE, String CUR_CODE_TO);
}

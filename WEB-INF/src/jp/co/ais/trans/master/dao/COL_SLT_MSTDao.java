package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface COL_SLT_MSTDao {

	/**  */
	public Class BEAN = COL_SLT_MST.class;

	/**
	 * @return List
	 */
	public List getAllCOL_SLT_MST();

	/**  */
	public String getColSltnfo_QUERY = "KAI_CODE = ? AND USR_ID BETWEEN ? AND ? AND PRG_ID BETWEEN ? AND ? ORDER BY USR_ID, PRG_ID ";

	/**
	 * @param KAI_CODE
	 * @param USR_ID_FROM
	 * @param USR_ID_TO
	 * @param PRG_ID_FROM
	 * @param PRG_ID_TO
	 * @return List
	 */
	public List getColSltInfo(String KAI_CODE, String USR_ID_FROM, String USR_ID_TO, String PRG_ID_FROM,
		String PRG_ID_TO);

	// 指定されたデータの取得
	/**  */
	public String getCOL_SLT_MSTByKaicodeUsridPgrid_ARGS = "KAI_CODE, USR_ID, PRG_ID";

	/**
	 * @param kaiCode
	 * @param usrID
	 * @param prgID
	 * @return COL_SLT_MST
	 */
	public COL_SLT_MST getCOL_SLT_MSTByKaicodeUsridPgrid(String kaiCode, String usrID, String prgID);

	/**
	 * @param dto
	 */
	public void insert(COL_SLT_MST dto);

	/**
	 * @param dto
	 */
	public void update(COL_SLT_MST dto);

	/**
	 * @param dto
	 */
	public void delete(COL_SLT_MST dto);
}

package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 自動仕訳科目マスタDao
 */
public interface AUTO_CTLDao {

	/**  */
	public Class BEAN = AUTO_CTL.class;

	/**
	 * @return List
	 */
	public List getAllAUTO_CTL();

	/**  */
	public String getAutoCtlInfo_QUERY = "KAI_CODE = ? AND PRIFIX BETWEEN ? AND ? ORDER BY PRIFIX ";

	/**
	 * @param KAI_CODE
	 * @param PRIFIX_FROM
	 * @param PRIFIX_TO
	 * @return List
	 */
	public List getAutoCtlInfo(String KAI_CODE, String PRIFIX_FROM, String PRIFIX_TO);

	// 指定されたデータの取得
	/**  */
	public String getAUTO_CTLByKaicodePrifix_ARGS = "KAI_CODE, PRIFIX";

	/**
	 * @param kaiCode
	 * @param Prifix
	 * @return AUTO_CTL
	 */
	public AUTO_CTL getAUTO_CTLByKaicodePrifix(String kaiCode, String Prifix);

	/**
	 * @param dto
	 */
	public void insert(AUTO_CTL dto);

	/**
	 * @param dto
	 */
	public void update(AUTO_CTL dto);

	/**
	 * @param dto
	 */
	public void delete(AUTO_CTL dto);
}

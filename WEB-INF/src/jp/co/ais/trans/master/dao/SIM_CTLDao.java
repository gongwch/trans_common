package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface SIM_CTLDao {

	/**  */
	public Class BEAN = SIM_CTL.class;

	/**
	 * @return List
	 */
	public List getAllSIM_CTL();

	/**  */
	public String getSIM_CTL_ARGS = "KAI_CODE";

	/**
	 * @param KAI_CODE
	 * @return SIM_CTL
	 */
	public SIM_CTL getSIM_CTL(String KAI_CODE);

	/**  */
	public String getSIM_CTLByIKaicode_ARGS = "KAI_CODE";

	/**
	 * @param kAI_CODE
	 * @return SIM_CTL
	 */
	public SIM_CTL getSIM_CTLByIKaicode(String kAI_CODE);

	/** パラメーター */
	public static final String getSimCtlByKaiCodeNendoSimmon_ARGS = "KAI_CODE, NENDO, SIM_MON";

	/**
	 * 締め制御テーブルデータを検索する
	 * 
	 * @param kaiCode 会社コード
	 * @param year 年度
	 * @param month 締め月
	 * @return 締め制御テーブルデータ
	 */
	public SIM_CTL getSimCtlByKaiCodeNendoSimmon(String kaiCode, int year, int month);

	/**
	 * @param dto
	 */
	public void insert(SIM_CTL dto);

	/**
	 * @param dto
	 */
	public void update(SIM_CTL dto);

	/**
	 * @param dto
	 */
	public void delete(SIM_CTL dto);
}

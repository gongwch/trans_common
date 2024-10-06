package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface AR_ZANDao {

	/**  */
	public Class BEAN = AR_ZAN.class;

	/**
	 * @return List
	 */
	public List getAllAR_ZAN();

	// 指定されたデータの取得
	/**  */
	public String getAR_ZANTByKaicodeZandennoZangyono_ARGS = "KAI_CODE, ZAN_DEN_NO, ZAN_GYO_NO";

	/**
	 * @param kaiCode
	 * @param zandenNO
	 * @param zangyoNO
	 * @return AR_ZAN
	 */
	public AR_ZAN getAR_ZANByKaicodeZandennoZangyono(String kaiCode, String zandenNO, int zangyoNO);

	/**
	 * @param dto
	 */
	public void insert(AR_ZAN dto);

	/**
	 * @param dto
	 */
	public void update(AR_ZAN dto);

	/**
	 * @param dto
	 */
	public void delete(AR_ZAN dto);
}

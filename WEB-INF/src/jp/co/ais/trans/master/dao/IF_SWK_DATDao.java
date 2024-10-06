package jp.co.ais.trans.master.dao;

import jp.co.ais.trans.master.entity.*;

/**
 * 
 */
public interface IF_SWK_DATDao {

	/**  */
	public Class BEAN = IF_SWK_DAT.class;

	// 指定されたデータの取得
	/**  */
	public String insert_ARGS = "dto";

	/**
	 * @param dto
	 */
	public void insert(IF_SWK_DAT dto);
}
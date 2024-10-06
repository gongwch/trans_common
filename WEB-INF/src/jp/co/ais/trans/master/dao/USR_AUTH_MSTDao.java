package jp.co.ais.trans.master.dao;

import java.io.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ユーザ認証マスタDAO
 * 
 * @author roh
 */
public interface USR_AUTH_MSTDao extends Serializable {

	/** ユーザ認証マスタBean */
	public Class BEAN = USR_AUTH_MST.class;

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(USR_AUTH_MST dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(USR_AUTH_MST dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(USR_AUTH_MST dto);

	/** findByKaiCode SQL */
	public static final String findByKaiCode_ARGS = "KAI_CODE";

	/**
	 * 会社の認証情報を習得
	 * 
	 * @param KAI_CODE
	 * @return 認証情報
	 */
	public USR_AUTH_MST findByKaiCode(String KAI_CODE);
}

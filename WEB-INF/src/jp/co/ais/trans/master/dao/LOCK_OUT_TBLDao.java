package jp.co.ais.trans.master.dao;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ロックアウト管理DAOクラス
 * 
 * @author roh
 */
public interface LOCK_OUT_TBLDao {

	/**  */
	public Class BEAN = LOCK_OUT_TBL.class;

	/**  */
	public String findWithPK_ARGS = "KAI_CODE,USR_CODE";

	/**
	 * ユーザコードを指定しロックアウト情報を習得
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return LOCK_OUT_TBL
	 */
	public LOCK_OUT_TBL findWithPK(String kaiCode, String usrCode);

	/**  */
	public String findWithUserName_ARGS = "kaiCode,arrCount";

	/**
	 * ロックアウトされたデータを習得
	 * 
	 * @param kaiCode
	 * @param arrCount
	 * @return List<LOCK_OUT_TBL>
	 */
	public List<LOCK_OUT_TBL> findWithUserName(String kaiCode, int arrCount);

	/**
	 * @param dto
	 */
	public void insert(LOCK_OUT_TBL dto);

	/**
	 * @param dto
	 */
	public void update(LOCK_OUT_TBL dto);

	/**
	 * @param dto
	 */
	public void delete(LOCK_OUT_TBL dto);

	/** ログインチェック削除SQL */
	public String deleteByPK_SQL = "DELETE FROM LOCK_OUT_TBL WHERE KAI_CODE = ? AND USR_CODE = ?";

	/**
	 * ログインチェック削除
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 */
	public void deleteByPK(String kaiCode, String usrCode);
}

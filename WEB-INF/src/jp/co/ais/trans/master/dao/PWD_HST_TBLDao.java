package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;
import jp.co.ais.trans.master.entity.*;

/**
 * 履歴管理マスタDAO
 * 
 * @author roh
 */
public interface PWD_HST_TBLDao extends Serializable {

	/** 履歴管理マスタBean */
	public Class BEAN = PWD_HST_TBL.class;

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(PWD_HST_TBL dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(PWD_HST_TBL dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(PWD_HST_TBL dto);

	/** findPassword SQL */
	public String findPassword_QUERY = "KAI_CODE = ? AND USR_CODE = ? ORDER BY INP_DATE";

	/**
	 * 履歴情報を習得
	 * 
	 * @param kaiCode
	 * @param usrCode
	 * @return 履歴りすと
	 */
	public List<PWD_HST_TBL> findPassword(String kaiCode, String usrCode);

	/** ログインチェック削除SQL */
	public String deleteByPK_SQL = "DELETE FROM PWD_HST_TBL WHERE KAI_CODE = ? AND USR_CODE = ?";

	/**
	 * ログインチェック削除
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 */
	public void deleteByPK(String kaiCode, String usrCode);

}

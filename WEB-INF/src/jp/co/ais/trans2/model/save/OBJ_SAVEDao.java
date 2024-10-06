package jp.co.ais.trans2.model.save;

import java.util.*;

/**
 * オブジェクト保存Dao
 */
public interface OBJ_SAVEDao {

	/** BEAN */
	public Class BEAN = OBJ_SAVE.class;

	/** 引数 */
	public String findByKey_ARGS = "kAI_CODE, uSR_ID, pRG_ID, sEQ";

	/**
	 * 検索
	 * 
	 * @param kAI_CODE 会社コード
	 * @param uSR_ID ユーザID
	 * @param pRG_ID プログラムID
	 * @param sEQ シーケンスNo.
	 * @return 結果
	 */
	public List<OBJ_SAVE> findByKey(String kAI_CODE, String uSR_ID, String pRG_ID, Integer sEQ);

	/**
	 * 新規作成
	 * 
	 * @param entity
	 */
	public void insert(OBJ_SAVE entity);

	/**
	 * 削除
	 * 
	 * @param entity
	 */
	public void delete(OBJ_SAVE entity);

	/** 引数 */
	public String deleteBySeq_ARGS = "kAI_CODE, uSR_ID, pRG_ID, sEQ";

	/**
	 * 削除
	 * 
	 * @param kAI_CODE 会社コード
	 * @param uSR_ID ユーザID
	 * @param pRG_ID プログラムID
	 * @param sEQ シーケンスNo.
	 */
	public void deleteBySeq(String kAI_CODE, String uSR_ID, String pRG_ID, Integer sEQ);

	/** 引数 */
	public String updateSeq_ARGS = "kAI_CODE, uSR_ID, pRG_ID";

	/**
	 * SEQ更新
	 * 
	 * @param kAI_CODE 会社コード
	 * @param uSR_ID ユーザID
	 * @param pRG_ID プログラムID
	 */
	public void updateSeq(String kAI_CODE, String uSR_ID, String pRG_ID);

}

package jp.co.ais.trans.master.dao;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * バッチ制御Dao
 */
public interface BAT_CTLDao extends Serializable {

	/** Bean */
	public Class BEAN = BAT_CTL.class;

	/**
	 * 全データ取得
	 * 
	 * @return データリスト
	 */
	public List getAllBAT_CTL();

	/** getBatchListByCompanyCode QUERY */
	public String getBatchListByCompanyCode_QUERY = "KAI_CODE = ? ORDER BY BAT_ID";

	/**
	 * 会社コードでバッチ排他リストを取得
	 * 
	 * @param companyCode
	 * @return バッチ排他リスト
	 */
	public List<BAT_CTL> getBatchListByCompanyCode(String companyCode);

	/** 別プログラムでの排他確認 */
	public String getBatchListByID_ARGS = "kaiCode, batId, prgId";

	/** 別プログラムでの排他確認 */
	public String getBatchListByID_QUERY = "KAI_CODE = /*kaiCode*/  AND BAT_ID = /*batId*/ AND PRG_ID NOT IN /*prgId*/('', '')";

	/**
	 * 別プログラムでの排他確認
	 * 
	 * @param kaiCode
	 * @param batId
	 * @param prgId
	 * @return バッチ排他リスト
	 */
	public List<BAT_CTL> getBatchListByID(String kaiCode, String batId, List<String> prgId);

	/** getBatCtlInfo QUERY */
	public String getBatCtlByPK_ARGS = "KAI_CODE, BAT_ID";

	/**
	 * PKでデータを取得
	 * 
	 * @param kaiCode 会社コード
	 * @param batId バッチコントロールID
	 * @return BAT_CTL
	 */
	public BAT_CTL getBatCtlByPK(String kaiCode, String batId);

	/** getBAT_CTLById ARGS */
	public String getBAT_CTLById_ARGS = "KAI_CODE, BAT_ID, PRG_ID, USR_ID";

	/**
	 * 指定条件でデータを取得
	 * 
	 * @param kaiCode 会社コード
	 * @param batId バッチコントロールID
	 * @param prgId プログラムID
	 * @param usrId ユーザID
	 * @return BAT_CTL
	 */
	public BAT_CTL getBAT_CTLById(String kaiCode, String batId, String prgId, String usrId);

	/**
	 * 新規登録
	 * 
	 * @param dto
	 */
	public void insert(BAT_CTL dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(BAT_CTL dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(BAT_CTL dto);

	/**
	 * 削除
	 * 
	 * @param list
	 * @return 削除件数
	 */
	public int deleteList(List<BAT_CTL> list);

	/** deleteForced */
	public String deleteForced_ARGS = "KAI_CODE, USR_ID";

	/**
	 * ログイン・ログアウト時の強制デリート
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザID
	 * @return 削除件数
	 */
	public int deleteForced(String kaiCode, String usrId);

	/** deleteByUsrPrg */
	public String deleteByUsrPrg_ARGS = "KAI_CODE, PRG_ID, USR_ID";

	/**
	 * プログラム指定の強制デリート
	 * 
	 * @param kaiCode 会社コード
	 * @param prgId プログラムID
	 * @param usrId ユーザID
	 * @return 削除件数
	 */
	public int deleteByUsrPrg(String kaiCode, String prgId, String usrId);

	/** deleteById */
	public String deleteById_QUERY = "KAI_CODE = ? AND BAT_ID = ? AND PRG_ID = ? AND USR_ID = ?";

	/**
	 * IDを指定した削除
	 * 
	 * @param kaiCode 会社コード
	 * @param batId バッチコントロールID
	 * @param prgId プログラムID
	 * @param usrId ユーザID
	 * @return 削除件数
	 */
	public int deleteById(String kaiCode, String batId, String prgId, String usrId);

}

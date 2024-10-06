package jp.co.ais.trans2.model.balance;

import java.util.*;

/**
 * 債務残高Dao
 * 
 * @author AIS
 */
public interface AP_ZANDao {

	/** Bean */
	public Class BEAN = AP_ZAN.class;

	/** キー検索 */
	public String findByPrimaryKey_ARGS = "KAI_CODE, ZAN_DEN_NO, ZAN_GYO_NO";

	/**
	 * キー検索
	 * 
	 * @param compCode 会社コード
	 * @param slipNo 伝票番号
	 * @param slipLineNo 行番号
	 * @return 債務残高
	 */
	public AP_ZAN findByPrimaryKey(String compCode, String slipNo, int slipLineNo);

	/**
	 * 検索
	 * 
	 * @param param 検索パラメータ
	 * @return 結果リスト
	 */
	public List<AP_ZAN> findByCondition(BalanceCondition param);

	/**
	 * 存在(消込状態)チェック用検索
	 * 
	 * @param param 検索パラメータ
	 * @return 結果リスト
	 */
	public List<AP_ZAN> findForExists(BalanceCondition param);

	/**
	 * 支払済みにする
	 * 
	 * @param param 条件
	 */
	public void updateBalance(BalanceCondition param);

	/**
	 * 支払済みを取り消す
	 * 
	 * @param param 条件
	 */
	public void updateCancelBalance(BalanceCondition param);

	/**
	 * 登録
	 * 
	 * @param entity エンティティ
	 */
	public void insert(AP_ZAN entity);

	/**
	 * 登録
	 * 
	 * @param entity エンティティ
	 */
	public void insertByUpdateDate(AP_ZAN entity);

	/**
	 * 更新
	 * 
	 * @param entity エンティティ
	 * @param sysDate システム日付
	 * @return int
	 */
	public int updateByUpdateDate(AP_ZAN entity, Date sysDate);

	/** 更新 */
	public String updateByUpdateDate_ARGS = "entity,sysDate";

	/**
	 * 削除
	 * 
	 * @param entity 残高リスト
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN entity);

}

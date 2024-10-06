package jp.co.ais.trans2.model.balance;

import java.util.*;

/**
 * 債権残高Dao
 */
public interface AR_ZANDao {

	/** Bean */
	public Class BEAN = AR_ZAN.class;

	/** キー検索 */
	public String findByPrimaryKey_QUERY = "KAI_CODE = ? AND ZAN_SEI_DEN_NO = ? AND ZAN_SEI_GYO_NO = ? AND ZAN_KESI_DEN_NO IS NULL";

	/**
	 * キー検索
	 * 消込行ではない元のデータを取得
	 * 
	 * @param compCode 会社コード
	 * @param slipNo 伝票番号
	 * @param slipLineNo 行番号
	 * @return 債務残高
	 */
	public AR_ZAN findByPrimaryKey(String compCode, String slipNo, int slipLineNo);

	/**
	 * 集計検索
	 * 
	 * @param param 検索パラメータ
	 * @return 結果リスト
	 */
	public List<AR_ZAN> findSummry(BalanceCondition param);

	/**
	 * 検索
	 * 
	 * @param param 検索パラメータ
	 * @return 結果リスト
	 */
	public List<AR_ZAN> findByCondition(BalanceCondition param);

	/**
	 * 存在(消込状態)チェック用検索
	 * 
	 * @param param 検索パラメータ
	 * @return 結果リスト
	 */
	public List<AR_ZAN> findForExists(BalanceCondition param);

	/** 削除 */
	public String deleteBySlipNo_QUERY = "KAI_CODE = ? AND ZAN_KESI_DEN_NO = ?";

	/**
	 * 削除
	 * 
	 * @param companyCode 会社コード
	 * @param eraseSlipNo 消込伝票番号
	 */
	public void deleteBySlipNo(String companyCode, String eraseSlipNo);

	/**
	 * 登録
	 * 
	 * @param entity エンティティ
	 */
	public void insert(AR_ZAN entity);

}

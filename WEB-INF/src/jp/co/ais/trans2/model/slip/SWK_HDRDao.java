package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * 仕訳ヘッダーDao(抽象)
 */
public interface SWK_HDRDao {

	/**
	 * 登録
	 * 
	 * @param entity 対象エンティティ
	 */
	void insert(SWK_HDR entity);

	/**
	 * 条件による削除
	 * 
	 * @param param 条件
	 */
	void deleteByCondition(SlipCondition param);

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<SWK_HDR> findByCondition(SlipCondition param);

	/**
	 * 排他状態変更
	 * 
	 * @param param 条件
	 */
	void updateLockState(SlipCondition param);

	/** LOCK情報確認用 */
	public String getLockInfo_ARGS = "companyCode, slipNo";

	/**
	 * LOCK情報確認用SWK_SHR_KBNとSWK_UPD_KBNを返す
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return LOCK情報確認用SWK_SHR_KBNとSWK_UPD_KBN
	 */
	SWK_HDR getLockInfo(String companyCode, String slipNo);
}

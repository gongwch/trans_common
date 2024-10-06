package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * 伝票用Dao
 */
public interface SlipDao {

	/** Entity */
	public Class BEAN = SWK_HDR.class;

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<SWK_HDR> findByCondition(SlipCondition param);

	/**
	 * 条件によるパターン検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<SWK_HDR> findPatternByCondition(SlipCondition param);

	/**
	 * 条件による履歴検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<SWK_HDR> findHistoryByCondition(SlipCondition param);

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<Slip> findSlipByCondition(SlipCondition param);

	/**
	 * 条件によるパターン検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<Slip> findSlipPatternByCondition(SlipCondition param);

	/**
	 * 条件による履歴検索
	 * 
	 * @param param 条件
	 * @return 結果リスト
	 */
	List<Slip> findSlipHistoryByCondition(SlipCondition param);

}

package jp.co.ais.trans2.model.slip;

import java.util.*;

/**
 * 仕訳明細Dao
 */
public interface SWK_DTLDao {

	/** Entity */
	public Class BEAN = SWK_DTL.class;

	/**
	 * 追加
	 * 
	 * @param list データリスト
	 */
	public void insert(List<SWK_DTL> list);

	/**
	 * 条件による削除
	 * 
	 * @param param 条件
	 */
	public void deleteByCondition(SlipCondition param);

	/**
	 * 条件による検索
	 * 
	 * @param param 条件
	 * @return 明細リスト
	 */
	public List<SWK_DTL> findByCondition(SlipCondition param);

	/**
	 * 条件による消込状態更新
	 * 
	 * @param entity 対象エンティティ
	 * @return 更新件数
	 */
	public int updateEraseState(SWK_DTL entity);

	/**
	 * 条件による消込状態更新
	 * 
	 * @param param 条件
	 * @return 更新件数
	 */
	public int updateEraseState2(SlipCondition param);

	/** パラメータ */
	public String getDtl_QUERY = "KAI_CODE = ? AND SWK_DEN_NO = ? AND SWK_GYO_NO = ? AND SWK_ADJ_KBN = 0";

	/**
	 * @param companyCode
	 * @param slipNo
	 * @param rowNo
	 * @return SWK_DTL
	 */
	public SWK_DTL getDtl(String companyCode, String slipNo, int rowNo);

}

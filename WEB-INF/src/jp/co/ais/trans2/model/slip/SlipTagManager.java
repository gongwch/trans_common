package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * 伝票付箋機能インターフェース。
 * 
 * @author AIS
 */
public interface SlipTagManager {

	/**
	 * 特定の伝票の付箋機能情報を設定する
	 * 
	 * @param slip 伝票
	 * @return 付箋機能情報<ファイル名, バイナリ>
	 * @throws TException
	 */
	public List<SWK_TAG> get(Slip slip) throws TException;

	/**
	 * 特定の伝票の付箋機能情報を設定する
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @return 付箋機能情報<ファイル名, バイナリ>
	 * @throws TException
	 */
	public List<SWK_TAG> get(String companyCode, String slipNo) throws TException;

	/**
	 * 伝票付箋機能の登録
	 * 
	 * @param slip 伝票
	 */
	public void entry(Slip slip);

	/**
	 * 付箋機能の登録
	 * 
	 * @param entity 付箋機能
	 */
	public void entry(SWK_TAG entity);

	/**
	 * 付箋機能情報の削除
	 * 
	 * @param list List<SWK_TAG>
	 */
	public void delete(List<SWK_TAG> list);

	/**
	 * 特定の伝票の付箋機能情報の削除
	 * 
	 * @param companyCode
	 * @param slipNo
	 */
	public void delete(String companyCode, String slipNo);

	/**
	 * 特定の伝票の付箋機能情報の削除
	 * 
	 * @param companyCode
	 * @param slipNo
	 * @param sEQ SEQ
	 */
	public void delete(String companyCode, String slipNo, Integer sEQ);
}

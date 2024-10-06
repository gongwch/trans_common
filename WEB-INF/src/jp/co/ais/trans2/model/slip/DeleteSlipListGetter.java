package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.except.*;

/**
 * 削除伝票リスト抽出インターフェース
 * 
 * @author AIS
 */
public interface DeleteSlipListGetter {

	/**
	 * 削除伝票リストを設定する
	 * 
	 * @param condition
	 * @return SlipCondition
	 * @throws TException
	 */
	public DeleteSlipListBook get(SlipCondition condition) throws TException;

}

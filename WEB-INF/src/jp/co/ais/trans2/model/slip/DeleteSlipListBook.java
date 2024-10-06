package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.common.ledger.*;

/**
 * 削除伝票リスト
 * 
 * @author AIS
 */
public class DeleteSlipListBook extends LedgerBook {

	/** 検索条件 */
	protected SlipCondition condition = null;
	
	/**
	 * タイトル設定
	 */
	public DeleteSlipListBook() {
		setTitle("C01610"); // 削除伝票リスト
	}

	/**
	 * 削除伝票リストの抽出条件
	 * 
	 * @return SlipCondition
	 */
	public SlipCondition getCondition() {
		return condition;
	}

	/**
	 * 削除伝票リストの設定する
	 * 
	 * @param condition
	 */
	public void setCondition(SlipCondition condition) {
		this.condition = condition;
	}
	
	/**
	 * 削除ユーザーの表示区分を取得する。
	 * 
	 * @return 表示区分
	 */

	public boolean isShowUserInfo() {
		if (condition != null && condition instanceof DeleteSlipListCondition) {
			return ((DeleteSlipListCondition) condition).isShowUserInfo();
		} else {
		    return false;
		}
	}
	
}

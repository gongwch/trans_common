package jp.co.ais.trans2.model.slip;

/**
 * 削除伝票条件
 */
public class DeleteSlipListCondition extends SlipCondition {

	/** 削除ユーザーの表示区分 */
	protected boolean showUserInfo = true;

	/**
	 * クローン
	 */
	@Override
	public DeleteSlipListCondition clone() {
		return (DeleteSlipListCondition) super.clone();
	}

	/**
	 * 削除ユーザーの表示区分を取得。
	 * 
	 * @return showUserInfo 表示区分
	 */
	public boolean isShowUserInfo() {
		return showUserInfo;
	}

	/**
	 * 削除ユーザーの表示区分を設定する。
	 * 
	 * @param showUserInfo 表示区分
	 */
	public void setShowUserInfo(boolean showUserInfo) {
		this.showUserInfo = showUserInfo;
	}

}

package jp.co.ais.trans.logic.system;

/**
 * ユーザのロックアウトロジック
 */
public interface UserLockout {

	/**
	 * コード設定
	 * 
	 * @param userCode ユーザコード
	 * @param compCode 会社コード
	 */
	public void setCode(String compCode, String userCode);

	/**
	 * ロックアウト管理を行うかどうか
	 * 
	 * @return true:ロックアウト管理する
	 */
	public boolean isLockoutManaged();

	/**
	 * 該当ユーザがロックアウトかどうかを判別<br>
	 * 
	 * @return boolean true:ロックアウト状態
	 */
	public boolean isLockoutStatus();

	/**
	 * ロックアウトカウントアップ
	 */
	public void countUp();

	/**
	 * ロックアウト解除
	 */
	public void clearLockout();
}

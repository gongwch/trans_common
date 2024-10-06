package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;

/**
 * ログインユーザ制御(PCI_CHK_CTLテーブル)マネージャ インターフェース
 */

public interface UserExclusiveManager {

	/**
	 * ログイン可能かどうか
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @return true:可能
	 */
	public boolean canEntry(String companyCode, String userCode);

	/**
	 * 排他する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 */
	public void exclude(String companyCode, String userCode);

	/**
	 * 排他解除する
	 */
	public void cancelExclude();

	/**
	 * 排他解除する
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 */
	public void cancelExclude(String companyCode, String userCode);

	/**
	 * ログイン可能かどうか
	 * 
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param password パスワード
	 * @return true:可能
	 * @throws TException
	 */
	public UserExclusive canEntry(String companyCode, String userCode, String password) throws TException;

}

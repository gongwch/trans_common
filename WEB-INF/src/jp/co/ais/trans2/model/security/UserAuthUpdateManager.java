package jp.co.ais.trans2.model.security;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ユーザ認証マスタマネージャ インターフェース
 */

public interface UserAuthUpdateManager {

	/**
	 * 確定
	 * 
	 * @param 更新
	 */
	public void modify(USR_AUTH_MST userauthupdate) throws TException;

	/**
	 * 確定
	 * 
	 * @param 既存データの存在チェック
	 */
	public USR_AUTH_MST get() throws TException;
}

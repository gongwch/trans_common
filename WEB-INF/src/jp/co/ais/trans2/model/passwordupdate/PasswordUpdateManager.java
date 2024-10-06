package jp.co.ais.trans2.model.passwordupdate;

import jp.co.ais.trans.common.except.*;

/**
 * MG0025 - パスワード変更
 * 
 * @author AIS
 */
public interface PasswordUpdateManager {

	/**
	 * 情報修正 (UPDATE)
	 * 
	 * @param bean 入力情報
	 * @throws TException
	 */
	public void modify(PasswordUpdate bean) throws TException;

}
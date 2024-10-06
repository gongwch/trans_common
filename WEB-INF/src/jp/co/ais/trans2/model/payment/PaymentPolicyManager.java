package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;

/**
 * AP：支払方針 インターフェース
 */
public interface PaymentPolicyManager {

	/**
	 * 支払方針マスタのFBデータ保存先情報を抽出する
	 * 
	 * @return 支払方針情報
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException;

	/**
	 * 支払方針マスタの検索
	 * 
	 * @return 支払方針マスタ情報
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException;

}
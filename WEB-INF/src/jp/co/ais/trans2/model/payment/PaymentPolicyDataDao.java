package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;

/**
 * 支払方針データ抽出Dao
 */
public interface PaymentPolicyDataDao {

	/**
	 * 支払方針マスタのFBデータ出力先検索
	 * 
	 * @return 支払方針マスタFBデータ出力先情報
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
package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;

/**
 * APFx¥ûj C^[tF[X
 */
public interface PaymentPolicyManager {

	/**
	 * x¥ûj}X^ÌFBf[^Û¶æîñðo·é
	 * 
	 * @return x¥ûjîñ
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException;

	/**
	 * x¥ûj}X^Ìõ
	 * 
	 * @return x¥ûj}X^îñ
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException;

}
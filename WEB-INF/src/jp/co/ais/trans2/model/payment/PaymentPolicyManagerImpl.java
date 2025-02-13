package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * x¥ûj}l[W
 */
public class PaymentPolicyManagerImpl extends TModel implements PaymentPolicyManager {

	/**
	 * Ðõx¥ûj}X^ÌFBf[^Û¶æîño
	 * 
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.getFolderPass();
	}

	/**
	 * x¥ûjf[^õ
	 * 
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.get();
	}

}
package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払方針マネージャ
 */
public class PaymentPolicyManagerImpl extends TModel implements PaymentPolicyManager {

	/**
	 * 社員支払方針マスタのFBデータ保存先情報抽出
	 * 
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.getFolderPass();
	}

	/**
	 * 支払方針データ検索
	 * 
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.get();
	}

}
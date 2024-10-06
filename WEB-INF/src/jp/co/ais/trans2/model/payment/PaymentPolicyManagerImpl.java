package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�����j�}�l�[�W��
 */
public class PaymentPolicyManagerImpl extends TModel implements PaymentPolicyManager {

	/**
	 * �Ј��x�����j�}�X�^��FB�f�[�^�ۑ����񒊏o
	 * 
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.getFolderPass();
	}

	/**
	 * �x�����j�f�[�^����
	 * 
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException {
		PaymentPolicyDataDao dao = (PaymentPolicyDataDao) getComponent(PaymentPolicyDataDao.class);
		return dao.get();
	}

}
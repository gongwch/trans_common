package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;

/**
 * �x�����j�f�[�^���oDao
 */
public interface PaymentPolicyDataDao {

	/**
	 * �x�����j�}�X�^��FB�f�[�^�o�͐挟��
	 * 
	 * @return �x�����j�}�X�^FB�f�[�^�o�͐���
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException;

	/**
	 * �x�����j�}�X�^�̌���
	 * 
	 * @return �x�����j�}�X�^���
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException;
}
package jp.co.ais.trans2.model.payment;

import jp.co.ais.trans.common.except.*;

/**
 * AP�F�x�����j �C���^�[�t�F�[�X
 */
public interface PaymentPolicyManager {

	/**
	 * �x�����j�}�X�^��FB�f�[�^�ۑ�����𒊏o����
	 * 
	 * @return �x�����j���
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
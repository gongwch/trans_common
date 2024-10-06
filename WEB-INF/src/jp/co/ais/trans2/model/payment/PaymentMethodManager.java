package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �x�����@�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface PaymentMethodManager {

	/**
	 * �w������ɊY������x�����@����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������x�����@���
	 * @throws TException
	 */
	public List<PaymentMethod> get(PaymentMethodSearchCondition condition) throws TException;

	/**
	 * �x�����@��o�^����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void entry(PaymentMethod bean) throws TException;

	/**
	 * �x�����@���C������B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void modify(PaymentMethod bean) throws TException;

	/**
	 * �x�����@���폜����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void delete(PaymentMethod bean) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(PaymentMethodSearchCondition condition) throws TException;

	/**
	 * �G�N�Z���t�@�C�����쐬����
	 * 
	 * @param condition
	 * @return �G�N�Z���t�@�C��
	 * @throws TException
	 */
	public byte[] getExcel(PaymentMethodSearchCondition condition) throws TException;

}

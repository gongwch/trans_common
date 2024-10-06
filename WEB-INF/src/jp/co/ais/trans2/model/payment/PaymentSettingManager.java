package jp.co.ais.trans2.model.payment;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * �x�������C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface PaymentSettingManager {

	/**
	 * �w������ɊY������x�����@����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������x�����@���
	 * @throws TException
	 */
	public List<PaymentSetting> get(PaymentSettingSearchCondition condition) throws TException;

	/**
	 * �x�����@��o�^����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void entry(PaymentSetting bean) throws TException;

	/**
	 * �x�����@���C������B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void modify(PaymentSetting bean) throws TException;

	/**
	 * �x�����@���폜����B
	 * 
	 * @param bean �x�����@
	 * @throws TException
	 */
	public void delete(PaymentSetting bean) throws TException;

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �x���������
	 * @throws TException
	 */
	public byte[] getExcel(PaymentSettingSearchCondition condition) throws TException;

	/**
	 * �x�����ꗗ���G�N�Z���`���ŕԂ�(�C�O�p)
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̈ꗗ
	 * @throws TException
	 */
	public byte[] getExcelForGlobal(PaymentSettingSearchCondition condition) throws TException;

}

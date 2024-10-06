package jp.co.ais.trans2.model.tax;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ����ŏ��C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface ConsumptionTaxManager {

	/**
	 * �w������ɊY���������ŏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY���������ŏ��
	 * @throws TException
	 */
	public List<ConsumptionTax> get(ConsumptionTaxSearchCondition condition) throws TException;

	/**
	 * ����ŏ���o�^����B
	 * 
	 * @param tax
	 * @throws TException
	 */
	public void entry(ConsumptionTax tax) throws TException;

	/**
	 * ����ŏ����C������B
	 * 
	 * @param tax
	 * @param isTriMstUpdate �C���{�C�X�p
	 * @throws TException
	 */
	public void modify(ConsumptionTax tax, boolean isTriMstUpdate) throws TException;

	/**
	 * ����ŏ����폜����B
	 * 
	 * @param tax
	 * @throws TException
	 */
	public void delete(ConsumptionTax tax) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(ConsumptionTaxSearchCondition condition) throws TException;

	/**
	 * �v���O����(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �v���O����
	 * @throws TException
	 */
	public byte[] getExcel(ConsumptionTaxSearchCondition condition) throws TException;

}

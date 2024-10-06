package jp.co.ais.trans2.model.remittance;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �����ړI�C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface RemittanceManager {

	/**
	 * �w������ɊY�����鑗���ړI����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������E�v���
	 * @throws TException
	 */
	public List<Remittance> get(RemittanceSearchCondition condition) throws TException;

	/**
	 * �����ړI����o�^����B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void entry(Remittance entity) throws TException;

	/**
	 * �����ړI�����C������B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void modify(Remittance entity) throws TException;

	/**
	 * �����ړI�����폜����B
	 * 
	 * @param entity
	 * @throws TException
	 */
	public void delete(Remittance entity) throws TException;

	/**
	 * �G�N�Z�����o�͂���B
	 * 
	 * @param condition
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcel(RemittanceSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�����鑗���ړI����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����鑗���ړI���
	 * @throws TException
	 */
	public List<Remittance> getRemittance(RemittanceSearchCondition condition) throws TException;

	/**
	 * �����ړI����o�^����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryRemittance(Remittance bean) throws TException;

	/**
	 * �����ړI�����C������B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modifyRemittance(Remittance bean) throws TException;

	/**
	 * �����ړI�����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteRemittance(Remittance bean) throws TException;

	/**
	 * �G�N�Z�����o�͂���B
	 * 
	 * @param condition
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcelRemittance(RemittanceSearchCondition condition) throws TException;

	/**
	 * �G�N�Z�����o�͂���B
	 * 
	 * @param condition
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcelRemittancePurpose(RemittanceSearchCondition condition) throws TException;

}

package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ��ЃR���g���[���}�X�^
 * 
 * @author AIS
 */
public interface CompanyOrganizationManager {

	/**
	 * �w������ɊY�������Џ���Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Џ��
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���(��ЊK�w�}�X�^�p)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���(��ЊK�w�}�X�^�p)
	 * @throws TException
	 */
	public CompanyOrganization getCompanyOrganizationName(CompanyOrganizationSearchCondition condition)
		throws TException;

	/**
	 * ��ЊK�w���̂�o�^����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyNameOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ��ЊK�wLEVEL0�o�^(�V�K)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryCompanyOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ��ЊK�w�폜
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void deleteCompanyOrganization(CompanyOrganization bean) throws TException;

	/**
	 * ��ЊK�w���̈ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̕���K�w�ꗗ
	 * @throws TException
	 */
	public byte[] getCompanyOrganizationNameExcel(CompanyOrganizationSearchCondition condition) throws TException;

	/**
	 * ��ЊK�w��o�^����B
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list ��ЊK�w
	 * @throws TException
	 */
	public void entryCompanyOrganization(String sskCode, String sskName, List<CompanyOrganization> list)
		throws TException;

	/**
	 * �w������ɊY�������Џ���Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Џ��
	 * @throws TException
	 */
	public List<String> getCompanyCodeList(CompanyOutputCondition condition) throws TException;

}

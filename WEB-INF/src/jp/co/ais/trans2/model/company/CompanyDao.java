package jp.co.ais.trans2.model.company;

import java.util.List;

import jp.co.ais.trans.common.except.TException;

/**
 * ��ЃR���g���[���}�X�^Dao
 * 
 * @author AIS
 */
public interface CompanyDao {

	/**
	 * �w������ɊY�������Џ���Ԃ��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Џ��
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException;

	/**
	 * �w��R�[�h�ɕR�t����Џ���Ԃ��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return �w��R�[�h�ɕR�t����Џ��
	 * @throws TException
	 */
	public Company get(String companyCode) throws TException;

	/**
	 * ��Ђ�o�^����B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void entry(Company company) throws TException;

	/**
	 * ��Ђ��C������B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void modify(Company company) throws TException;

	/**
	 * ��Ђ��폜����B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void delete(Company company) throws TException;

	/**
	 * �t�֏��̎擾
	 * 
	 * @param fromCompanyCode �t�֌���ЃR�[�h
	 * @param toCompanyCode �t�֐��ЃR�[�h
	 * @return �e��Еt�֏��
	 * @throws TException
	 */
	public List<TransferConfig> getTransferConfig(String fromCompanyCode, String toCompanyCode) throws TException;

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
	 * ��ЊK�w�}�X�^�ɓo�^����Ă��Ȃ���Ѓ��X�g�擾
	 * 
	 * @param companyCode
	 * @param condition
	 * @return ��ЊK�w�}�X�^�ɓo�^����Ă��Ȃ���Ѓ��X�g
	 * @throws TException
	 */
	public List<String> getNotExistCompanyList(String companyCode, CompanyOutputCondition condition) throws TException;
}

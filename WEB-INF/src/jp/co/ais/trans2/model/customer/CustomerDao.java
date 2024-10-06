package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �������C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface CustomerDao {

	/**
	 * �w������ɊY�������������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> get(CustomerSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������������Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getRef(CustomerSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�������������Ԃ�<br>
	 * �C���N�������g�T�[�`�p
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getCustomerForSearch(CustomerSearchCondition condition) throws TException;

	/**
	 * ������o�^����B
	 * 
	 * @param customer �����
	 * @throws TException
	 */
	public void entry(Customer customer) throws TException;

	/**
	 * �������C������B
	 * 
	 * @param customer �����
	 * @throws TException
	 */
	public void modify(Customer customer) throws TException;

	/**
	 * �������폜����B
	 * 
	 * @param customer �����
	 * @throws TException
	 */
	public void delete(Customer customer) throws TException;

	/**
	 * �����̐U���˗��l�����C������B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param customerCode �����R�[�h
	 * @param clientName �U���˗��l��
	 * @throws TException
	 */
	public void modifyBankAccountClientName(String companyCode, String customerCode, String clientName)
		throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CustomerSearchCondition condition) throws TException;

	/**
	 * �w������ɊY����������S���ҏ���Ԃ�
	 * 
	 * @param triCode �����R�[�h
	 * @return �w������ɊY����������S���ҏ��
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MST(String triCode) throws TException;

	/**
	 * �����S���Ґݒ��o�^����B
	 * 
	 * @param customerUser �����S���Ґݒ�
	 * @throws TException
	 */
	public void entryTRI_USR_MST(List<CustomerUser> customerUser) throws TException;

	/**
	 * �w������ɊY����������S���ҏ���Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY����������S���ҏ��
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MSTExcel(CustomerSearchCondition condition) throws TException;

}

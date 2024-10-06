package jp.co.ais.trans2.model.customer;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;

/**
 * �������}�l�[�W�������N���X
 * 
 * @author AIS
 */
public class CustomerManagerImpl extends TModel implements CustomerManager {

	/**
	 * Dao�t�@�N�g��
	 * 
	 * @return Dao
	 */
	protected CustomerDao getDao() {
		CustomerDao dao = (CustomerDao) getComponent(CustomerDao.class);
		return dao;
	}

	public List<Customer> get(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.get(condition);
	}

	/**
	 * �w������ɊY�������������Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getRef(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getRef(condition);
	}

	/**
	 * �w������ɊY�������������Ԃ�<br>
	 * �C���N�������g�T�[�`�p
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<Customer> getCustomerForSearch(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getCustomerForSearch(condition);
	}

	public void entry(Customer customer) throws TException {
		CustomerDao dao = getDao();
		dao.entry(customer);
	}

	public void modify(Customer customer) throws TException {
		CustomerDao dao = getDao();
		dao.modify(customer);
	}

	public void delete(Customer customer) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.CUSTOMER);
		condition.setCompanyCode(customer.getCompanyCode());
		condition.setCustomerCode(customer.getCode());

		// �g�p�ς݃`�F�b�N
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		CustomerDao dao = getDao();
		dao.delete(customer);
	}

	/**
	 * �����̐U���˗��l�����C������B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param customerCode �����R�[�h
	 * @param clientName �U���˗��l��
	 * @throws TException
	 */
	public void modifyBankAccountClientName(String companyCode, String customerCode, String clientName)
		throws TException {

		CustomerDao dao = getDao();
		dao.modifyBankAccountClientName(companyCode, customerCode, clientName);

	}

	/**
	 * �����}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̎����}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(CustomerSearchCondition condition) throws TException {

		List<Customer> customers = get(condition);
		if (customers == null || customers.isEmpty()) {
			return null;
		}

		CustomerExcel layout = getLayout(getUser().getLanguage(), condition);
		byte[] data = layout.getExcel(customers);

		return data;

	}

	/**
	 * EXCEL���C�A�E�g�t�@�N�g��
	 * 
	 * @param language
	 * @param condition
	 * @return EXCEL���C�A�E�g�t�@�N�g��
	 */
	protected CustomerExcel getLayout(String language, CustomerSearchCondition condition) {
		return new CustomerExcel(language, condition);
	}

	/**
	 * EXCEL���C�A�E�g�t�@�N�g��(�S���҈ꗗ�o�͗p)
	 * 
	 * @param language
	 * @return EXCEL���C�A�E�g�t�@�N�g��
	 */
	protected CustomerUsrExcel getUsrLayout(String language) {
		return new CustomerUsrExcel(language);
	}

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.hasDelete(condition);
	}

	/**
	 * TRI_USR_MST�̌���
	 * 
	 * @param triCode
	 * @return TRI_USR_MST���
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MST(String triCode) throws TException {
		CustomerDao dao = getDao();
		return dao.getTRI_USR_MST(triCode);
	}

	/**
	 * TRI_USR_MST�̓o�^
	 * 
	 * @param list
	 * @throws TException
	 */
	public void entryTRI_USR_MST(List<CustomerUser> list) throws TException {
		CustomerDao dao = getDao();
		dao.entryTRI_USR_MST(list);
	}

	/**
	 * TRI_USR_MST�̌����i�ꗗ�o�͗p�j
	 * 
	 * @param condition
	 * @return TRI_USR_MST���i�ꗗ�o�͗p�j
	 * @throws TException
	 */
	public List<CustomerUser> getTRI_USR_MSTExcel(CustomerSearchCondition condition) throws TException {
		CustomerDao dao = getDao();
		return dao.getTRI_USR_MSTExcel(condition);
	}

	/**
	 * �����S���҃}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̎����S���҃}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getUsrExcel(CustomerSearchCondition condition) throws TException {

		List<CustomerUser> customers = getTRI_USR_MSTExcel(condition);
		if (customers == null || customers.isEmpty()) {
			return null;
		}

		CustomerUsrExcel layout = getUsrLayout(getUser().getLanguage());
		byte[] data = layout.getExcel(customers);

		return data;

	}

}

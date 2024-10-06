package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * ��ЊǗ��̎����N���X�ł��B
 * 
 * @author AIS
 */
public class CompanyManagerImpl extends TModel implements CompanyManager {

	/**
	 * �w��R�[�h�ɕR�t����Џ���Ԃ��܂��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @return �w��R�[�h�ɕR�t����Џ��
	 * @throws TException
	 */
	public Company get(String companyCode) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.get(companyCode);
	}

	/**
	 * �w������ɊY�������Џ���Ԃ��܂��B
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Џ��
	 * @throws TException
	 */
	public List<Company> get(CompanySearchCondition condition) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.get(condition);
	}

	/**
	 * ��Ђ�o�^����B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void entry(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.entry(company);
	}

	/**
	 * ��Ђ��C������B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void modify(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.modify(company);
	}

	/**
	 * ��Ђ��폜����B
	 * 
	 * @param company ���
	 * @throws TException
	 */
	public void delete(Company company) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		dao.delete(company);
	}

	/**
	 * �t�֏��̎擾
	 * 
	 * @param fromCompanyCode �t�֌���ЃR�[�h
	 * @param toCompanyCode �t�֐��ЃR�[�h
	 * @return �e��Еt�֏��
	 * @throws TException
	 */
	public List<TransferConfig> getTransferConfig(String fromCompanyCode, String toCompanyCode) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getTransferConfig(fromCompanyCode, toCompanyCode);
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param condition
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(CompanySearchCondition condition) throws TException {

		try {

			// ��Ѓf�[�^�𒊏o
			List<Company> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			CompanyExcel exl = new CompanyExcel(getUser().getLanguage(), condition);
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * @return �T�[�o�[�V�X�e������
	 * @throws TException
	 */
	public Date getSystemDate() throws TException {
		return getNow();
	}

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganization(CompanyOrganizationSearchCondition condition)
		throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getCompanyOrganization(condition);
	}

	/**
	 * �w������ɊY�������Бg�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�������Бg�D���(��ЊK�w�}�X�^�p)
	 * @throws TException
	 */
	public List<CompanyOrganization> getCompanyOrganizationData(CompanyOrganizationSearchCondition condition)
		throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getCompanyOrganizationData(condition);
	}

	/**
	 * ��ЊK�w�}�X�^�ɓo�^����Ă��Ȃ���Ѓ��X�g�擾
	 * 
	 * @param companyCode
	 * @param condition
	 * @return ��ЊK�w�}�X�^�ɓo�^����Ă��Ȃ���Ѓ��X�g
	 * @throws TException
	 */
	public List<String> getNotExistCompanyList(String companyCode, CompanyOutputCondition condition) throws TException {
		CompanyDao dao = (CompanyDao) getComponent(CompanyDao.class);
		return dao.getNotExistCompanyList(companyCode, condition);
	}
}

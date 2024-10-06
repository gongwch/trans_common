package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ����C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface DepartmentManager {

	/**
	 * �w������ɊY�����镔�����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔����
	 * @throws TException
	 */
	public List<Department> get(DepartmentSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�����镔�����Ԃ�<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔����
	 * @throws TException
	 */
	public List<Department> getRef(DepartmentSearchCondition condition) throws TException;

	/**
	 * �����o�^����B
	 * 
	 * @param department ����
	 * @throws TException
	 */
	public void entry(Department department) throws TException;

	/**
	 * ������C������B
	 * 
	 * @param department ����
	 * @throws TException
	 */
	public void modify(Department department) throws TException;

	/**
	 * ������폜����B
	 * 
	 * @param department ����
	 * @throws TException
	 */
	public void delete(Department department) throws TException;

	/**
	 * �G�N�Z��
	 * 
	 * @param condition
	 * @return ������
	 * @throws TException
	 */
	public byte[] getExcel(DepartmentSearchCondition condition) throws TException;

	/**
	 * �w������ɊY�����镔��g�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔��g�D���
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganization(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * �w������ɊY�����镔��g�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔��g�D���(����K�w�}�X�^�p)
	 * @throws TException
	 */
	public List<DepartmentOrganization> getDepartmentOrganizationData(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * ����K�wLEVEL0��o�^����B(�V�K)
	 * 
	 * @param departmentOrganization ����K�wLEVEL0
	 * @throws TException
	 */
	public void entryDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * ����K�wLEVEL0�A�O�̑g�D�R�[�h�̕���K�w���V�g�D�R�[�h�̕���K�w�փR�s�[����B(����)
	 * 
	 * @param departmentOrganization ����K�wLEVEL0�A�V�g�D�R�[�h
	 * @param oldSskCode �O�̑g�D�R�[�h
	 * @throws TException
	 */
	public void entryCopyDepartmentOrganization(DepartmentOrganization departmentOrganization, String oldSskCode)
		throws TException;

	/**
	 * ����K�w��o�^����B
	 * 
	 * @param sskCode
	 * @param list ����K�w
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, List<DepartmentOrganization> list) throws TException;

	/**
	 * ����K�w���폜����B
	 * 
	 * @param departmentOrganization ����K�w
	 * @throws TException
	 */
	public void deleteDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * ����K�w�G�N�Z��
	 * 
	 * @param condition
	 * @return ����K�w���
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationExcel(DepartmentOrganizationSearchCondition condition) throws TException;

	/**
	 * ����K�w�}�X�^�ɓo�^����Ă��Ȃ����僊�X�g�擾
	 * 
	 * @param companyCode
	 * @param depCondition
	 * @return ����K�w�}�X�^�ɓo�^����Ă��Ȃ����僊�X�g
	 * @throws TException
	 */
	public List<String> getNotExistDepartmentList(String companyCode, DepartmentOutputCondition depCondition)
		throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(DepartmentSearchCondition condition) throws TException;
}

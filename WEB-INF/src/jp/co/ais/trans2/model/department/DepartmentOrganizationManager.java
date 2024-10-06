package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ����K�w�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface DepartmentOrganizationManager {

	/**
	 * �w������ɊY�����镔�����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔����
	 * @throws TException
	 */
	public List<Department> get(DepartmentSearchCondition condition) throws TException;

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
	 * �w������ɊY�����镔��g�D����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����镔��g�D���(����K�w�}�X�^�p)
	 * @throws TException
	 */
	public DepartmentOrganization getDepartmentOrganizationName(DepartmentOrganizationSearchCondition condition)
		throws TException;

	/**
	 * ����K�wLEVEL0��o�^����B(�V�K)
	 * 
	 * @param departmentOrganization ����K�wLEVEL0
	 * @throws TException
	 */
	public void entryDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * ����K�w�Ƒg�D���̂�o�^����B
	 * 
	 * @param sskCode
	 * @param sskName
	 * @param list ����K�w
	 * @throws TException
	 */
	public void entryDepartmentOrganization(String sskCode, String sskName, List<DepartmentOrganization> list)
		throws TException;

	/**
	 * ����K�wLEVEL���̓o�^(�V�K)
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentOrganizationName(DepartmentOrganization bean) throws TException;

	/**
	 * ����K�w���폜����B
	 * 
	 * @param departmentOrganization ����K�w
	 * @throws TException
	 */
	public void deleteDepartmentOrganization(DepartmentOrganization departmentOrganization) throws TException;

	/**
	 * ����K�w���̈ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̕���K�w�ꗗ
	 * @throws TException
	 */
	public byte[] getDepartmentOrganizationNameExcel(DepartmentOrganizationSearchCondition condition) throws TException;

	/**
	 * ����K�w���̂�o�^����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entryDepartmentNameOrganization(DepartmentOrganization bean) throws TException;
}

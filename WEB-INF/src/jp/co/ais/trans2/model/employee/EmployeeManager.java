package jp.co.ais.trans2.model.employee;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0160EmployeeMaster - �Ј��}�X�^ - Interface Class
 * 
 * @author AIS
 */
public interface EmployeeManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @param condition ��������
	 * @return �Ј��}�X�^���
	 * @throws TException
	 */
	public List<Employee> get(EmployeeSearchCondition condition) throws TException;

	/**
	 * ��񌟍� (SELECT)<br>
	 * �S��Џo�́FREF��ʗp
	 * 
	 * @param condition ��������
	 * @return �Ј��}�X�^���
	 * @throws TException
	 */
	public List<Employee> getRef(EmployeeSearchCondition condition) throws TException;

	/**
	 * ���o�^ (INSERT)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void entry(Employee bean) throws TException;

	/**
	 * ���C�� (UPDATE)
	 * 
	 * @param bean ���͏��
	 * @throws TException
	 */
	public void modify(Employee bean) throws TException;

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param bean �I�����
	 * @throws TException
	 */
	public void delete(Employee bean) throws TException;

	/**
	 * ��񌟍�-Excel�o�� (SELECT)
	 * 
	 * @param condition ��������
	 * @return �Ј��}�X�^���
	 * @throws TException
	 */
	public byte[] getExcel(EmployeeSearchCondition condition) throws TException;

	/**
	 * �폜�f�[�^�����݂��Ă��邩�ǂ���
	 * 
	 * @param condition
	 * @return true:�폜�f�[�^�����݂��Ă���
	 * @throws TException
	 */
	public boolean hasDelete(EmployeeSearchCondition condition) throws TException;
}
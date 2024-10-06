package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans2.model.*;

/**
 * ����̏o�͒P�ʏ���
 * 
 * @author AIS
 */
public class DepartmentOutputCondition extends BasicOutputCondition<Department> {

	/**
	 * @return �J�n����
	 */
	public Department getDepartmentFrom() {
		return getFrom();
	}

	/**
	 * �J�n����
	 * 
	 * @param departmentFrom
	 */
	public void setDepartmentFrom(Department departmentFrom) {
		setFrom(departmentFrom);
	}

	/**
	 * @return �g�D�R�[�h
	 */
	public String getDepartmentOrganizationCode() {
		return getOrganizationCode();
	}

	/**
	 * �g�D�R�[�h
	 * 
	 * @param departmentOrganizationCode
	 */
	public void setDepartmentOrganizationCode(String departmentOrganizationCode) {
		setOrganizationCode(departmentOrganizationCode);
	}

	/**
	 * @return �I������
	 */
	public Department getDepartmentTo() {
		return getTo();
	}

	/**
	 * �I������
	 * 
	 * @param departmentTo
	 */
	public void setDepartmentTo(Department departmentTo) {
		setTo(departmentTo);
	}

	/**
	 * @return �z��������܂ނ�
	 */
	public boolean isIncludeUnderDepartment() {
		return isIncludeUnder();
	}

	/**
	 * �z��������܂ނ�
	 * 
	 * @param includeUnderDepartment
	 */
	public void setIncludeUnderDepartment(boolean includeUnderDepartment) {
		setIncludeUnder(includeUnderDepartment);
	}

	/**
	 * @return �ʑI�𕔖�
	 */
	public List<Department> getOptionalDepartments() {
		return getOptionalEntities();
	}

	/**
	 * �ʑI�𕔖�
	 * 
	 * @param optionalDepartments
	 */
	public void setOptionalDepartments(List<Department> optionalDepartments) {
		setOptionalEntities(optionalDepartments);
	}

	/**
	 * @return ��ʕ���
	 */
	public Department getSuperiorDepartment() {
		return getSuperior();
	}

	/**
	 * ��ʕ���
	 * 
	 * @param superiorDepartment
	 */
	public void setSuperiorDepartment(Department superiorDepartment) {
		setSuperior(superiorDepartment);
	}

	/**
	 * �ʑI�����ꂽ����̃R�[�h���X�g��Ԃ�
	 * 
	 * @return �ʑI�����ꂽ����̃R�[�h���X�g
	 */
	public List<String> getOptionalDepartmentsCode() {
		return getOptionalEntitiesCode();
	}

	/**
	 * �e�[�u��ID�̎擾
	 * 
	 * @return tableID �e�[�u��ID
	 */
	@Override
	public String getTableID() {
		return "DPK_MST";
	}

}

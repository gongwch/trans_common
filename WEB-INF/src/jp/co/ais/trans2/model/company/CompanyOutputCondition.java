package jp.co.ais.trans2.model.company;

import java.util.*;

import jp.co.ais.trans2.model.*;

/**
 * ��Ђ̏o�͒P�ʏ���
 * 
 * @author AIS
 */
public class CompanyOutputCondition extends BasicOutputCondition<Company> {

	/**
	 * @return �J�n���
	 */
	public Company getCompanyFrom() {
		return getFrom();
	}

	/**
	 * �J�n���
	 * 
	 * @param departmentFrom
	 */
	public void setCompanyFrom(Company departmentFrom) {
		setFrom(departmentFrom);
	}

	/**
	 * @return �g�D�R�[�h
	 */
	public String getCompanyOrganizationCode() {
		return getOrganizationCode();
	}

	/**
	 * �g�D�R�[�h
	 * 
	 * @param departmentOrganizationCode
	 */
	public void setCompanyOrganizationCode(String departmentOrganizationCode) {
		setOrganizationCode(departmentOrganizationCode);
	}

	/**
	 * @return �I�����
	 */
	public Company getCompanyTo() {
		return getTo();
	}

	/**
	 * �I�����
	 * 
	 * @param departmentTo
	 */
	public void setCompanyTo(Company departmentTo) {
		setTo(departmentTo);
	}

	/**
	 * @return �z����Ђ��܂ނ�
	 */
	public boolean isIncludeUnderCompany() {
		return isIncludeUnder();
	}

	/**
	 * �z����Ђ��܂ނ�
	 * 
	 * @param includeUnderCompany
	 */
	public void setIncludeUnderCompany(boolean includeUnderCompany) {
		setIncludeUnder(includeUnderCompany);
	}

	/**
	 * @return �ʑI�����
	 */
	public List<Company> getOptionalCompanys() {
		return getOptionalEntities();
	}

	/**
	 * �ʑI�����
	 * 
	 * @param optionalCompanys
	 */
	public void setOptionalCompanys(List<Company> optionalCompanys) {
		setOptionalEntities(optionalCompanys);
	}

	/**
	 * @return ��ʉ��
	 */
	public Company getSuperiorCompany() {
		return getSuperior();
	}

	/**
	 * ��ʉ��
	 * 
	 * @param superiorCompany
	 */
	public void setSuperiorCompany(Company superiorCompany) {
		setSuperior(superiorCompany);
	}

	/**
	 * �ʑI�����ꂽ��Ђ̃R�[�h���X�g��Ԃ�
	 * 
	 * @return �ʑI�����ꂽ��Ђ̃R�[�h���X�g
	 */
	public List<String> getOptionalCompanysCode() {
		return getOptionalEntitiesCode();
	}

	/**
	 * �e�[�u��ID�̎擾
	 * 
	 * @return tableID �e�[�u��ID
	 */
	@Override
	public String getTableID() {
		return "EVK_MST";
	}

}

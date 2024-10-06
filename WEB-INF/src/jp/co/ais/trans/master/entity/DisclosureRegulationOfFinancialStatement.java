package jp.co.ais.trans.master.entity;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �������\�ɑ΂��郆�[�U�[�̊J���Z�L�����e�B�ł�
 * @author AIS
 *
 */
public class DisclosureRegulationOfFinancialStatement extends TransferBase {

	private static final long serialVersionUID = 8207616769170731748L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/** �Ȗڑ̌n�R�[�h */
	protected String itemOrganizationCode = null;

	/** �g�D�R�[�h */
	protected String organizationCode = null;

	/** �K�w���x�� */
	protected int departmentLevel = -1;

	/** ��ʕ���R�[�h */
	protected String upperDepartmentCode = null;

	/** ����R�[�h */
	protected String departmentCode = null;

	/** �z��������܂ނ� */
	protected boolean includeLowerDepartment = false;

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return ����R�[�h��߂��܂��B
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * @param departmentCode ����R�[�h��ݒ肵�܂��B
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * @return �K�w���x����߂��܂��B
	 */
	public int getDepartmentLevel() {
		return departmentLevel;
	}

	/**
	 * @param departmentLevel �K�w���x����ݒ肵�܂��B
	 */
	public void setDepartmentLevel(int departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	/**
	 * @return �z��������܂ނ���߂��܂��B
	 */
	public boolean isIncludeLowerDepartment() {
		return includeLowerDepartment;
	}

	/**
	 * @param includeLowerDepartment �z��������܂ނ���ݒ肵�܂��B
	 */
	public void setIncludeLowerDepartment(boolean includeLowerDepartment) {
		this.includeLowerDepartment = includeLowerDepartment;
	}

	/**
	 * @return �Ȗڑ̌n�R�[�h��߂��܂��B
	 */
	public String getItemOrganizationCode() {
		return itemOrganizationCode;
	}

	/**
	 * @param itemOrganizationCode �Ȗڑ̌n�R�[�h��ݒ肵�܂��B
	 */
	public void setItemOrganizationCode(String itemOrganizationCode) {
		this.itemOrganizationCode = itemOrganizationCode;
	}

	/**
	 * @return �g�D�R�[�h��߂��܂��B
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * @param organizationCode �g�D�R�[�h��ݒ肵�܂��B
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return ��ʕ���R�[�h��߂��܂��B
	 */
	public String getUpperDepartmentCode() {
		return upperDepartmentCode;
	}

	/**
	 * @param upperDepartmentCode ��ʕ���R�[�h��ݒ肵�܂��B
	 */
	public void setUpperDepartmentCode(String upperDepartmentCode) {
		this.upperDepartmentCode = upperDepartmentCode;
	}

	/**
	 * @return ���[�U�[�R�[�h��߂��܂��B
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode ���[�U�[�R�[�h��ݒ肵�܂��B
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}

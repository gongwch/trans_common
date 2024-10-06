package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ����K�wEntity
 * 
 * @author AIS
 */
public class DepartmentDisp extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �e�v���O�����R�[�h */
	protected String parentCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ����O���[�v�ɑ�����v���O�����Q */
	protected Department department = null;

	/** �o�^���t */
	protected Date inpDate;

	/**
	 * �m�[�h�ɕ\�����閼�̂��擾����
	 * 
	 * @return �m�[�h�ɕ\�����閼��
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * �I�u�W�F�N�g�̃I���W�i�������\����Ԃ�.
	 * 
	 * @return �R�[�h
	 */
	public String toStringCode() {
		return this.code;
	}

	/**
	 * �I�u�W�F�N�g�̃I���W�i�������\����Ԃ�.
	 * 
	 * @return ����
	 */
	@Override
	public String toString() {
		return this.code + " " + this.name;
	}

	/**
	 * �G���e�B�e�B�̔�r�Ɏg�p����B
	 * 
	 * @param obj ��r
	 * @return boolean
	 */
	public boolean equals(DepartmentDisp obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
		}
	}

	/**
	 * ��ЃR�[�h���擾����B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �e�v���O�����R�[�h���擾����B
	 * 
	 * @return �e�v���O�����R�[�h
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * �e�v���O�����R�[�h��ݒ肷��B
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * ����K�w�R�[�h���擾����B
	 * 
	 * @return ���j���[�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * ����K�w�R�[�h��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ����K�w���̂��擾����B
	 * 
	 * @return ���j���[����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ����K�w���̂�ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ����O���[�v�����擾����B
	 * 
	 * @return ����O���[�v
	 */
	public Department getDepartment() {
		return department;
	}

	/**
	 * ����O���[�v��ݒ肷��B
	 * 
	 * @param department
	 */
	public void setDepartment(Department department) {
		this.department = department;
	}

	/**
	 * �o�^���t���擾����B
	 * 
	 * @return inpDate
	 */
	public Date getInpDate() {
		return inpDate;
	}

	/**
	 * * �o�^���t��ݒ肷��B
	 * 
	 * @param inpDate
	 */
	public void setInpDate(Date inpDate) {
		this.inpDate = inpDate;
	}

}

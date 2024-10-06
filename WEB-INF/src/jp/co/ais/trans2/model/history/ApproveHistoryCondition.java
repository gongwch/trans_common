package jp.co.ais.trans2.model.history;

import jp.co.ais.trans.common.dt.*;

/**
 * ���F��������
 */
public class ApproveHistoryCondition extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �Ј��R�[�h */
	protected String employeeCode = null;

	/** �`�[�ԍ� */
	protected String slipNo = null;

	/** ���F���[���R�[�h */
	protected String aprvRoleCode = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �Ј��R�[�h�̎擾
	 * 
	 * @return employeeCode �Ј��R�[�h
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * �Ј��R�[�h�̐ݒ�
	 * 
	 * @param employeeCode �Ј��R�[�h
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * �`�[�ԍ��̎擾
	 * 
	 * @return slipNo �`�[�ԍ�
	 */
	public String getSlipNo() {
		return slipNo;
	}

	/**
	 * �`�[�ԍ��̐ݒ�
	 * 
	 * @param slipNo �`�[�ԍ�
	 */
	public void setSlipNo(String slipNo) {
		this.slipNo = slipNo;
	}

	/**
	 * ���F���[���R�[�h�̎擾
	 * 
	 * @return aprvRoleCode ���F���[���R�[�h
	 */
	public String getAprvRoleCode() {
		return aprvRoleCode;
	}

	/**
	 * ���F���[���R�[�h�̐ݒ�
	 * 
	 * @param aprvRoleCode ���F���[���R�[�h
	 */
	public void setAprvRoleCode(String aprvRoleCode) {
		this.aprvRoleCode = aprvRoleCode;
	}

}

package jp.co.ais.trans2.model.check;

import jp.co.ais.trans.common.dt.*;

/**
 * �`�F�b�N����
 */
public class CheckCondition extends TransferBase {

	/**
	 * �`�F�b�N�^�C�v
	 */
	public static class CHECK_TYPE {

		/** �Ȗ� */
		public static final int ITEM = 1;

		/** �⏕�Ȗ� */
		public static final int SUB_ITEM = 2;

		/** ����Ȗ� */
		public static final int DETAIL_ITEM = 3;

		/** �v�㕔�� */
		public static final int DEPARTMENT = 4;

		/** ����� */
		public static final int CUSTOMER = 5;

		/** �����x������ */
		public static final int PAYMENT_SETTING = 6;

		/** �Ј� */
		public static final int EMPLOYEE = 7;

		/** �Ǘ��P */
		public static final int MANAGEMENT_1 = 8;

		/** �Ǘ��Q */
		public static final int MANAGEMENT_2 = 9;

		/** �Ǘ��R */
		public static final int MANAGEMENT_3 = 10;

		/** �Ǘ��S */
		public static final int MANAGEMENT_4 = 11;

		/** �Ǘ��T */
		public static final int MANAGEMENT_5 = 12;

		/** �Ǘ��U */
		public static final int MANAGEMENT_6 = 13;
	}

	/** �`�F�b�N�^�C�v */
	protected int type = -1;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** ����ȖڃR�[�h */
	protected String detailItemCode = null;

	/** �v�㕔��R�[�h */
	protected String departmentCode = null;

	/** �����R�[�h */
	protected String customerCode = null;

	/** �����x�������R�[�h */
	protected String paymentSettingCode = null;

	/** �Ј��R�[�h */
	protected String employeeCode = null;

	/** �Ǘ��P�R�[�h */
	protected String management1Code = null;

	/** �Ǘ��Q�R�[�h */
	protected String management2Code = null;

	/** �Ǘ��R�R�[�h */
	protected String management3Code = null;

	/** �Ǘ��S�R�[�h */
	protected String management4Code = null;

	/** �Ǘ��T�R�[�h */
	protected String management5Code = null;

	/** �Ǘ��U�R�[�h */
	protected String management6Code = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param type
	 */
	public CheckCondition(int type) {
		this.type = type;
	}

	/**
	 * �`�F�b�N�^�C�v�̎擾
	 * 
	 * @return type �`�F�b�N�^�C�v
	 */
	public int getType() {
		return type;
	}

	/**
	 * �`�F�b�N�^�C�v�̐ݒ�
	 * 
	 * @param type �`�F�b�N�^�C�v
	 */
	public void setType(int type) {
		this.type = type;
	}

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
	 * �ȖڃR�[�h�̎擾
	 * 
	 * @return itemCode �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �ȖڃR�[�h�̐ݒ�
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h�̎擾
	 * 
	 * @return subItemCode �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h�̐ݒ�
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * ����ȖڃR�[�h�̎擾
	 * 
	 * @return detailItemCode ����ȖڃR�[�h
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * ����ȖڃR�[�h�̐ݒ�
	 * 
	 * @param detailItemCode ����ȖڃR�[�h
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * �v�㕔��R�[�h�̎擾
	 * 
	 * @return departmentCode �v�㕔��R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * �v�㕔��R�[�h�̐ݒ�
	 * 
	 * @param departmentCode �v�㕔��R�[�h
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * �����R�[�h�̎擾
	 * 
	 * @return customerCode �����R�[�h
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * �����R�[�h�̐ݒ�
	 * 
	 * @param customerCode �����R�[�h
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * �����x�������R�[�h�̎擾
	 * 
	 * @return paymentSettingCode �����x�������R�[�h
	 */
	public String getPaymentSettingCode() {
		return paymentSettingCode;
	}

	/**
	 * �����x�������R�[�h�̐ݒ�
	 * 
	 * @param paymentSettingCode �����x�������R�[�h
	 */
	public void setPaymentSettingCode(String paymentSettingCode) {
		this.paymentSettingCode = paymentSettingCode;
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
	 * �Ǘ��P�R�[�h�̎擾
	 * 
	 * @return management1Code �Ǘ��P�R�[�h
	 */
	public String getManagement1Code() {
		return management1Code;
	}

	/**
	 * �Ǘ��P�R�[�h�̐ݒ�
	 * 
	 * @param management1Code �Ǘ��P�R�[�h
	 */
	public void setManagement1Code(String management1Code) {
		this.management1Code = management1Code;
	}

	/**
	 * �Ǘ��Q�R�[�h�̎擾
	 * 
	 * @return management2Code �Ǘ��Q�R�[�h
	 */
	public String getManagement2Code() {
		return management2Code;
	}

	/**
	 * �Ǘ��Q�R�[�h�̐ݒ�
	 * 
	 * @param management2Code �Ǘ��Q�R�[�h
	 */
	public void setManagement2Code(String management2Code) {
		this.management2Code = management2Code;
	}

	/**
	 * �Ǘ��R�R�[�h�̎擾
	 * 
	 * @return management3Code �Ǘ��R�R�[�h
	 */
	public String getManagement3Code() {
		return management3Code;
	}

	/**
	 * �Ǘ��R�R�[�h�̐ݒ�
	 * 
	 * @param management3Code �Ǘ��R�R�[�h
	 */
	public void setManagement3Code(String management3Code) {
		this.management3Code = management3Code;
	}

	/**
	 * �Ǘ��S�R�[�h�̎擾
	 * 
	 * @return management4Code �Ǘ��S�R�[�h
	 */
	public String getManagement4Code() {
		return management4Code;
	}

	/**
	 * �Ǘ��S�R�[�h�̐ݒ�
	 * 
	 * @param management4Code �Ǘ��S�R�[�h
	 */
	public void setManagement4Code(String management4Code) {
		this.management4Code = management4Code;
	}

	/**
	 * �Ǘ��T�R�[�h�̎擾
	 * 
	 * @return management5Code �Ǘ��T�R�[�h
	 */
	public String getManagement5Code() {
		return management5Code;
	}

	/**
	 * �Ǘ��T�R�[�h�̐ݒ�
	 * 
	 * @param management5Code �Ǘ��T�R�[�h
	 */
	public void setManagement5Code(String management5Code) {
		this.management5Code = management5Code;
	}

	/**
	 * �Ǘ��U�R�[�h�̎擾
	 * 
	 * @return management6Code �Ǘ��U�R�[�h
	 */
	public String getManagement6Code() {
		return management6Code;
	}

	/**
	 * �Ǘ��U�R�[�h�̐ݒ�
	 * 
	 * @param management6Code �Ǘ��U�R�[�h
	 */
	public void setManagement6Code(String management6Code) {
		this.management6Code = management6Code;
	}

}

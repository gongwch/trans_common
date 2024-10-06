package jp.co.ais.trans2.model.company;

import jp.co.ais.trans.common.dt.*;

/**
 * �t�֏��
 */
public class TransferConfig extends TransferBase {

	/** ��ЃR�[�h */
	private String companyCode;

	/** �t�։�ЃR�[�h */
	private String transferCompanyCode;

	/** �t�֌v�㕔��R�[�h */
	private String transferDepertmentCode;

	/** �t�֌v�㕔�喼�� */
	private String transferDepertmentName;

	/** �t�֌v�㕔�嗪�� */
	private String transferDepertmentNames;

	/** �t�։ȖڃR�[�h */
	private String transferItemCode;

	/** �t�։Ȗږ��� */
	private String transferItemName;

	/** �t�։Ȗڗ��� */
	private String transferItemNames;

	/** �t�֕⏕�ȖڃR�[�h */
	private String transferSubItemCode;

	/** �t�֕⏕�Ȗږ��� */
	private String transferSubItemName;

	/** �t�֕⏕�Ȗڗ��� */
	private String transferSubItemNames;

	/** �t�֓���ȖڃR�[�h */
	private String transferDetailItemCode;

	/** �t�֓���Ȗږ��� */
	private String transferDetailItemName;

	/** �t�֓���Ȗڗ��� */
	private String transferDetailItemNames;

	/** �t�֎����R�[�h */
	private String transferCustomerCode;

	/** �t�֎���於�� */
	private String transferCustomerName;

	/** �t�֎���旪�� */
	private String transferCustomerNames;

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �t�։�ЃR�[�h
	 * 
	 * @return �t�։�ЃR�[�h
	 */
	public String getTransferCompanyCode() {
		return transferCompanyCode;
	}

	/**
	 * �t�։�ЃR�[�h
	 * 
	 * @param transferCompanyCode �t�։�ЃR�[�h
	 */
	public void setTransferCompanyCode(String transferCompanyCode) {
		this.transferCompanyCode = transferCompanyCode;
	}

	/**
	 * �t�֌v�㕔��R�[�h
	 * 
	 * @return �t�֌v�㕔��R�[�h
	 */
	public String getTransferDepertmentCode() {
		return transferDepertmentCode;
	}

	/**
	 * �t�֌v�㕔��R�[�h
	 * 
	 * @param transferDepertmentCode �t�֌v�㕔��R�[�h
	 */
	public void setTransferDepertmentCode(String transferDepertmentCode) {
		this.transferDepertmentCode = transferDepertmentCode;
	}

	/**
	 * �t�֓���ȖڃR�[�h
	 * 
	 * @return �t�֓���ȖڃR�[�h
	 */
	public String getTransferDetailItemCode() {
		return transferDetailItemCode;
	}

	/**
	 * �t�֓���ȖڃR�[�h
	 * 
	 * @param transferDetailItemCode �t�֓���ȖڃR�[�h
	 */
	public void setTransferDetailItemCode(String transferDetailItemCode) {
		this.transferDetailItemCode = transferDetailItemCode;
	}

	/**
	 * �t�։ȖڃR�[�h
	 * 
	 * @return �t�։ȖڃR�[�h
	 */
	public String getTransferItemCode() {
		return transferItemCode;
	}

	/**
	 * �t�։ȖڃR�[�h
	 * 
	 * @param transferItemCode �t�։ȖڃR�[�h
	 */
	public void setTransferItemCode(String transferItemCode) {
		this.transferItemCode = transferItemCode;
	}

	/**
	 * �t�֕⏕�ȖڃR�[�h
	 * 
	 * @return �t�֕⏕�ȖڃR�[�h
	 */
	public String getTransferSubItemCode() {
		return transferSubItemCode;
	}

	/**
	 * �t�֕⏕�ȖڃR�[�h
	 * 
	 * @param transferSubItemCode �t�֕⏕�ȖڃR�[�h
	 */
	public void setTransferSubItemCode(String transferSubItemCode) {
		this.transferSubItemCode = transferSubItemCode;
	}

	/**
	 * �t�֌v�㕔�喼��
	 * 
	 * @return �t�֌v�㕔�喼��
	 */
	public String getTransferDepertmentName() {
		return transferDepertmentName;
	}

	/**
	 * �t�֌v�㕔�喼��
	 * 
	 * @param transferDepertmentName �t�֌v�㕔�喼��
	 */
	public void setTransferDepertmentName(String transferDepertmentName) {
		this.transferDepertmentName = transferDepertmentName;
	}

	/**
	 * �t�֌v�㕔�嗪��
	 * 
	 * @return �t�֌v�㕔�嗪��
	 */
	public String getTransferDepertmentNames() {
		return transferDepertmentNames;
	}

	/**
	 * �t�֌v�㕔�嗪��
	 * 
	 * @param transferDepertmentNames �t�֌v�㕔�嗪��
	 */
	public void setTransferDepertmentNames(String transferDepertmentNames) {
		this.transferDepertmentNames = transferDepertmentNames;
	}

	/**
	 * �t�֓���Ȗږ���
	 * 
	 * @return �t�֓���Ȗږ���
	 */
	public String getTransferDetailItemName() {
		return transferDetailItemName;
	}

	/**
	 * �t�֓���Ȗږ���
	 * 
	 * @param transferDetailItemName �t�֓���Ȗږ���
	 */
	public void setTransferDetailItemName(String transferDetailItemName) {
		this.transferDetailItemName = transferDetailItemName;
	}

	/**
	 * �t�֓���Ȗڗ���
	 * 
	 * @return �t�֓���Ȗڗ���
	 */
	public String getTransferDetailItemNames() {
		return transferDetailItemNames;
	}

	/**
	 * �t�֓���Ȗڗ���
	 * 
	 * @param transferDetailItemNames �t�֓���Ȗڗ���
	 */
	public void setTransferDetailItemNames(String transferDetailItemNames) {
		this.transferDetailItemNames = transferDetailItemNames;
	}

	/**
	 * �t�։Ȗږ���
	 * 
	 * @return �t�։Ȗږ���
	 */
	public String getTransferItemName() {
		return transferItemName;
	}

	/**
	 * �t�։Ȗږ���
	 * 
	 * @param transferItemName �t�։Ȗږ���
	 */
	public void setTransferItemName(String transferItemName) {
		this.transferItemName = transferItemName;
	}

	/**
	 * �t�։Ȗڗ���
	 * 
	 * @return �t�։Ȗڗ���
	 */
	public String getTransferItemNames() {
		return transferItemNames;
	}

	/**
	 * �t�։Ȗڗ���
	 * 
	 * @param transferItemNames �t�։Ȗڗ���
	 */
	public void setTransferItemNames(String transferItemNames) {
		this.transferItemNames = transferItemNames;
	}

	/**
	 * �t�֕⏕�Ȗږ���
	 * 
	 * @return �t�֕⏕�Ȗږ���
	 */
	public String getTransferSubItemName() {
		return transferSubItemName;
	}

	/**
	 * �t�֕⏕�Ȗږ���
	 * 
	 * @param transferSubItemName �t�֕⏕�Ȗږ���
	 */
	public void setTransferSubItemName(String transferSubItemName) {
		this.transferSubItemName = transferSubItemName;
	}

	/**
	 * �t�֕⏕�Ȗڗ���
	 * 
	 * @return �t�֕⏕�Ȗڗ���
	 */
	public String getTransferSubItemNames() {
		return transferSubItemNames;
	}

	/**
	 * �t�֕⏕�Ȗڗ���
	 * 
	 * @param transferSubItemNames �t�֕⏕�Ȗڗ���
	 */
	public void setTransferSubItemNames(String transferSubItemNames) {
		this.transferSubItemNames = transferSubItemNames;
	}

	/**
	 * �t�֎����R�[�h
	 * 
	 * @return �t�֎����R�[�h
	 */
	public String getTransferCustomerCode() {
		return transferCustomerCode;
	}

	/**
	 * �t�֎����R�[�h
	 * 
	 * @param transferCustomerCode �t�֎����R�[�h
	 */
	public void setTransferCustomerCode(String transferCustomerCode) {
		this.transferCustomerCode = transferCustomerCode;
	}

	/**
	 * �t�֎���於��
	 * 
	 * @return �t�֎���於��
	 */
	public String getTransferCustomerName() {
		return transferCustomerName;
	}

	/**
	 * �t�֎���於��
	 * 
	 * @param transferCustomerName �t�֎���於��
	 */
	public void setTransferCustomerName(String transferCustomerName) {
		this.transferCustomerName = transferCustomerName;
	}

	/**
	 * �t�֎���旪��
	 * 
	 * @return �t�֎���旪��
	 */
	public String getTransferCustomerNames() {
		return transferCustomerNames;
	}

	/**
	 * �t�֎���旪��
	 * 
	 * @param transferCustomerNames �t�֎���旪��
	 */
	public void setTransferCustomerNames(String transferCustomerNames) {
		this.transferCustomerNames = transferCustomerNames;
	}
}

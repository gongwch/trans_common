package jp.co.ais.trans.common.bizui;

import java.io.*;
import java.util.*;

/**
 * �Ȗڏo�̓p�����[�^�[
 */
public class AccountItemOutputParameter implements Serializable {

	/** �w�肳�ꂽ�R�[�h�����݂��Ă��邩�ǂ��� */
	private boolean isExist;

	/** �ȖڃR�[�h */
	private String itemCode = "";

	/** �Ȗڗ��� */
	private String itemAbbrName = "";

	/** �⏕�ȖڃR�[�h */
	private String subItemCode = "";

	/** �⏕�Ȗڗ��� */
	private String subItemAbbrName = "";

	/** ����ȖڃR�[�h */
	private String breakDownItemCode = "";

	/** ����Ȗڗ��� */
	private String breakDownItemAbbrName = "";

	/** �⏕�敪 */
	private String subItemFlag = "";

	/** ����敪 */
	private String breakDownItemFlag = "";

	/** ����ېœ��̓t���O */
	private String salesTaxInputFlag = "";

	/** �d���ېœ��̓t���O */
	private String purchaseTaxationInputFlag = "";

	/** ����ź��� */
	private String consumptionTaxCode = "";

	/** ����ŗ��� */
	private String consumptionTaxAbbrName = "";

	/** ����ŋ敪 */
	private String consumptionTaxDivision = "";

	/** ���������׸� */
	private String customerInputFlag = "0";

	/** �Ј������׸� */
	private String employeeInputFlag = "";

	/** �Ј����� */
	private String employeeCode = "";

	/** �Ј����� */
	private String employeeAbbrName = "";

	/** �Ǘ�1�����׸� */
	private String management1InputFlag = "";

	/** �Ǘ�2�����׸� */
	private String management2InputFlag = "";

	/** �Ǘ�3�����׸� */
	private String management3InputFlag = "";

	/** �Ǘ�4�����׸� */
	private String management4InputFlag = "";

	/** �Ǘ�5�����׸� */
	private String management5InputFlag = "";

	/** �Ǘ�6�����׸� */
	private String management6InputFlag = "";

	/** ���v1���̓t���O */
	private String nonAccountingDetail1Flag = "";

	/** ���v2���̓t���O */
	private String nonAccountingDetail2Flag = "";

	/** ���v3���̓t���O */
	private String nonAccountingDetail3Flag = "";

	/** ���������̓t���O */
	private String accrualDateInputFlag = "";

	/** ���ʉ݃t���O */
	private String multipleCurrencyInputFlag = "";

	/** GL�Ȗڐ���敪 */
	private String kmkCntGl = "";

	/** AP�Ȗڐ���敪 */
	private String kmkCntAp = "";

	/** AR�Ȗڐ���敪 */
	private String kmkCntAr = "";

	/** �Ȗڑ̌n�R�[�h */
	private String itemSystemCode = "";

	/** �J�n�� */
	private Date strDate;

	/** �I���� */
	private Date endDate;

	/**
	 * �I�������擾����B
	 * 
	 * @return Date endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * �I������ݒ肷��B
	 * 
	 * @param endDate
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * �J�n�����擾����B
	 * 
	 * @return Date strDate
	 */
	public Date getStrDate() {
		return strDate;
	}

	/**
	 * �J�n����ݒ肷��B
	 * 
	 * @param strDate
	 */
	public void setStrDate(Date strDate) {
		this.strDate = strDate;
	}

	/**
	 * �w�肳�ꂽ�R�[�h�����݂��Ă��邩�ǂ���
	 * 
	 * @return true:����
	 */
	public boolean isExist() {
		return isExist;
	}

	/**
	 * �w�肳�ꂽ�R�[�h�����݂��Ă��邩�ǂ���
	 * 
	 * @param isExist true:����
	 */
	public void setExist(boolean isExist) {
		this.isExist = isExist;
	}

	/**
	 * �ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param itemCode �ȖڃR�[�h
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * �ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return itemCode �ȖڃR�[�h
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * �Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param itemAbbrName �Ȗڗ���
	 */
	public void setItemAbbrName(String itemAbbrName) {
		this.itemAbbrName = itemAbbrName;
	}

	/**
	 * �Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return itemAbbrName �Ȗڗ���
	 */
	public String getItemAbbrName() {
		return itemAbbrName;
	}

	/**
	 * �⏕�ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param subItemCode �⏕�ȖڃR�[�h
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * �⏕�ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return subItemCode �⏕�ȖڃR�[�h
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * �⏕�Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param subItemAbbrName �⏕�Ȗڗ���
	 */
	public void setSubItemAbbrName(String subItemAbbrName) {
		this.subItemAbbrName = subItemAbbrName;
	}

	/**
	 * �⏕�Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return subItemAbbrName �⏕�Ȗڗ���
	 */
	public String getSubItemAbbrName() {
		return subItemAbbrName;
	}

	/**
	 * ����ȖڃR�[�h��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemCode ����ȖڃR�[�h
	 */
	public void setBreakDownItemCode(String breakDownItemCode) {
		this.breakDownItemCode = breakDownItemCode;
	}

	/**
	 * ����ȖڃR�[�h��Ԃ��B<BR>
	 * 
	 * @return breakDownItemCode ����ȖڃR�[�h
	 */
	public String getBreakDownItemCode() {
		return breakDownItemCode;
	}

	/**
	 * ����Ȗڗ��̂�ݒ肷��B<BR>
	 * 
	 * @param breakDownItemAbbrName ����Ȗڗ���
	 */
	public void setBreakDownItemAbbrName(String breakDownItemAbbrName) {
		this.breakDownItemAbbrName = breakDownItemAbbrName;
	}

	/**
	 * ����Ȗڗ��̂�Ԃ��B<BR>
	 * 
	 * @return breakDownItemAbbrName ����Ȗڗ���
	 */
	public String getBreakDownItemAbbrName() {
		return breakDownItemAbbrName;
	}

	/**
	 * �⏕�敪��ݒ肷��B<BR>
	 * 
	 * @param subItemFlag �⏕�敪 0:�Ȃ� 1:����
	 */
	public void setSubItemFlag(String subItemFlag) {
		this.subItemFlag = subItemFlag;
	}

	/**
	 * �⏕�敪��Ԃ��B<BR>
	 * 
	 * @return subItemFlag �⏕�敪 true:���� false:�Ȃ�
	 */
	public boolean isIncludeSubItem() {
		return "1".equals(this.subItemFlag);
	}

	/**
	 * ����敪��ݒ肷��B<BR>
	 * 
	 * @param breakDownItemFlag ����敪 0:�Ȃ� 1:����
	 */
	public void setBreakDownItemFlag(String breakDownItemFlag) {
		this.breakDownItemFlag = breakDownItemFlag;
	}

	/**
	 * ����敪��Ԃ��B<BR>
	 * 
	 * @return breakDownItemFlag ����敪 true:���� false:�Ȃ�
	 */
	public boolean isIncludeBreakDownItem() {
		return "1".equals(this.breakDownItemFlag);
	}

	/**
	 * ����ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param salesTaxInputFlag ����ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setSalesTaxInputFlag(String salesTaxInputFlag) {
		this.salesTaxInputFlag = salesTaxInputFlag;
	}

	/**
	 * ����ېœ��̓t���O��Ԃ��B<BR>
	 * 
	 * @return salesTaxInputFlag ����ېœ��̓t���O true:���͉� false:���͕s��
	 */
	public boolean isIncludeSalesTaxInputFlag() {
		return "1".equals(this.salesTaxInputFlag);
	}

	/**
	 * �d���ېœ��̓t���O��ݒ肷��B<BR>
	 * 
	 * @param purchaseTaxationInputFlag �d���ېœ��̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setPurchaseTaxationInputFlag(String purchaseTaxationInputFlag) {
		this.purchaseTaxationInputFlag = purchaseTaxationInputFlag;
	}

	/**
	 * �d���ېœ��̓t���O��Ԃ��B<BR>
	 * 
	 * @return purchaseTaxationInputFlag �d���ېœ��̓t���O true:���͉� false:���͕s��
	 */
	public boolean isIncludePurchaseTaxationInputFlag() {
		return "1".equals(this.purchaseTaxationInputFlag);
	}

	/**
	 * ����ź��ނ�ݒ肷��B<BR>
	 * 
	 * @param consumptionTaxCode ����ŃR�[�h
	 */
	public void setConsumptionTaxCode(String consumptionTaxCode) {
		this.consumptionTaxCode = consumptionTaxCode;
	}

	/**
	 * ����ź��ނ�Ԃ��B<BR>
	 * 
	 * @return consumptionTaxCode ����ŃR�[�h
	 */
	public String getConsumptionTaxCode() {
		return consumptionTaxCode;
	}

	/**
	 * ����ŗ��̂�ݒ肷��B<BR>
	 * 
	 * @param consumptionTaxAbbrName ����ŗ���
	 */
	public void setConsumptionTaxAbbrName(String consumptionTaxAbbrName) {
		this.consumptionTaxAbbrName = consumptionTaxAbbrName;
	}

	/**
	 * ����ŗ��̂�Ԃ��B<BR>
	 * 
	 * @return consumptionTaxAbbrName ����ŗ���
	 */
	public String getConsumptionTaxAbbrName() {
		return consumptionTaxAbbrName;
	}

	/**
	 * ����ŋ敪��ݒ肷��B<BR>
	 * 
	 * @param consumptionTaxDivision ����ŋ敪
	 */
	public void setConsumptionTaxDivision(String consumptionTaxDivision) {
		this.consumptionTaxDivision = consumptionTaxDivision;
	}

	/**
	 * ����ŋ敪��Ԃ��B<BR>
	 * 
	 * @return consumptionTaxDivision ����ŋ敪
	 */
	public String getconsumptionTaxDivision() {
		return consumptionTaxDivision;
	}

	/**
	 * �������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param customerInputFlag �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 */
	public void setCustomerInputFlag(String customerInputFlag) {
		this.customerInputFlag = customerInputFlag;
	}

	/**
	 * �������̓t���O��Ԃ��B<BR>
	 * 
	 * @return customerInputFlag �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 */
	public String getCustomerInputFlag() {
		return customerInputFlag;
	}

	/**
	 * �������̓t���O��Ԃ��B
	 * 
	 * @return �������̓t���O 0:���͕s�� 2:���Ӑ� 3:�d���� 4:���Ӑ恕�d����
	 */
	public boolean isIncludeCustomerInputFlag() {
		return !"0".equals(this.customerInputFlag);
	}

	/**
	 * �Ј����̓t���O��ݒ肷��B<BR>
	 * 
	 * @param employeeInputFlag �Ј����̓t���O 0:���͕s�� 1:���͕K�{
	 */
	public void setEmployeeInputFlag(String employeeInputFlag) {
		this.employeeInputFlag = employeeInputFlag;
	}

	/**
	 * �Ј����̓t���O��Ԃ��B<BR>
	 * 
	 * @return employeeInputFlag �Ј����̓t���O true:���͉� false:���͕s��
	 */
	public boolean isIncludeEmployeeInputFlag() {
		return "1".equals(this.employeeInputFlag);
	}

	/**
	 * �Ј����ނ�ݒ肷��B<BR>
	 * 
	 * @param employeeCode �Ј�����
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * �Ј����ނ�Ԃ��B<BR>
	 * 
	 * @return employeeCode �Ј�����
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * �Ј����̂�ݒ肷��B<BR>
	 * 
	 * @param employeeAbbrName �Ј�����
	 */
	public void setEmployeeAbbrName(String employeeAbbrName) {
		this.employeeAbbrName = employeeAbbrName;
	}

	/**
	 * �Ј����̂�Ԃ��B<BR>
	 * 
	 * @return employeeAbbrName �Ј�����
	 */
	public String getEmployeeAbbrName() {
		return employeeAbbrName;
	}

	/**
	 * �Ǘ�1�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management1InputFlag �Ǘ�1�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement1InputFlag(String management1InputFlag) {
		this.management1InputFlag = management1InputFlag;
	}

	/**
	 * �Ǘ�1�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management1InputFlag �Ǘ�1�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement1InputFlag() {
		return "1".equals(this.management1InputFlag);
	}

	/**
	 * �Ǘ�2�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management2InputFlag �Ǘ�2�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement2InputFlag(String management2InputFlag) {
		this.management2InputFlag = management2InputFlag;
	}

	/**
	 * �Ǘ�2�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management2InputFlag �Ǘ�1�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement2InputFlag() {
		return "1".equals(this.management2InputFlag);
	}

	/**
	 * �Ǘ�3�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management3InputFlag �Ǘ�3�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement3InputFlag(String management3InputFlag) {
		this.management3InputFlag = management3InputFlag;
	}

	/**
	 * �Ǘ�3�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management3InputFlag �Ǘ�3�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement3InputFlag() {
		return "1".equals(this.management3InputFlag);
	}

	/**
	 * �Ǘ�4�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management4InputFlag �Ǘ�4�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement4InputFlag(String management4InputFlag) {
		this.management4InputFlag = management4InputFlag;
	}

	/**
	 * �Ǘ�4�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management4InputFlag �Ǘ�4�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement4InputFlag() {
		return "1".equals(this.management4InputFlag);
	}

	/**
	 * �Ǘ�5�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management5InputFlag �Ǘ�5�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement5InputFlag(String management5InputFlag) {
		this.management5InputFlag = management5InputFlag;
	}

	/**
	 * �Ǘ�5�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management5InputFlag �Ǘ�5�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement5InputFlag() {
		return "1".equals(this.management5InputFlag);
	}

	/**
	 * �Ǘ�6�����׸ނ�ݒ肷��B<BR>
	 * 
	 * @param management6InputFlag �Ǘ�6�����׸� 0:���͕s�� 1:���͕K�{
	 */
	public void setManagement6InputFlag(String management6InputFlag) {
		this.management6InputFlag = management6InputFlag;
	}

	/**
	 * �Ǘ�6�����׸ނ�Ԃ��B<BR>
	 * 
	 * @return management6InputFlag �Ǘ�6�����׸� true:���͉� false:���͕s��
	 */
	public boolean isIncludeManagement6InputFlag() {
		return "1".equals(this.management6InputFlag);
	}

	/**
	 * ���v1���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail1Flag ���v1���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setNonAccountingDetail1Flag(String nonAccountingDetail1Flag) {
		this.nonAccountingDetail1Flag = nonAccountingDetail1Flag;
	}

	/**
	 * ���v1���̓t���O��Ԃ��B<BR>
	 * 
	 * @return management6InputFlag ���v1���̓t���O� true:���͉� false:���͕s��
	 */
	public boolean isIncludeNonAccountingDetail1Flag() {
		return "1".equals(this.nonAccountingDetail1Flag);
	}

	/**
	 * ���v2���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail2Flag ���v2���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setNonAccountingDetail2Flag(String nonAccountingDetail2Flag) {
		this.nonAccountingDetail2Flag = nonAccountingDetail2Flag;
	}

	/**
	 * ���v2���̓t���O��Ԃ��B<BR>
	 * 
	 * @return nonAccountingDetail2Flag ���v2���̓t���O� true:���͉� false:���͕s��
	 */
	public boolean isIncludeNonAccountingDetail2Flag() {
		return "1".equals(this.nonAccountingDetail2Flag);
	}

	/**
	 * ���v3���̓t���O��ݒ肷��B<BR>
	 * 
	 * @param nonAccountingDetail3Flag ���v3���̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setNonAccountingDetail3Flag(String nonAccountingDetail3Flag) {
		this.nonAccountingDetail3Flag = nonAccountingDetail3Flag;
	}

	/**
	 * ���v3���̓t���O��Ԃ��B<BR>
	 * 
	 * @return nonAccountingDetail3Flag ���v3���̓t���O� true:���͉� false:���͕s��
	 */
	public boolean isIncludeNonAccountingDetail3Flag() {
		return "1".equals(this.nonAccountingDetail3Flag);
	}

	/**
	 * ���������̓t���O��ݒ肷��B<BR>
	 * 
	 * @param accrualDateInputFlag ���������̓t���O 0:���͕s�� 1:���͉�
	 */
	public void setAccrualDateInputFlag(String accrualDateInputFlag) {
		this.accrualDateInputFlag = accrualDateInputFlag;
	}

	/**
	 * ���������̓t���O��Ԃ��B<BR>
	 * 
	 * @return accrualDateInputFlag ���������̓t���O� true:���͉� false:���͕s��
	 */
	public boolean isIncludeAccrualDateInputFlag() {
		return "1".equals(this.accrualDateInputFlag);
	}

	/**
	 * ���ʉ݃t���O��ݒ肷��B<BR>
	 * 
	 * @param multipleCurrencyInputFlag ���ʉ݃t���O 0:���͕s�� 1:���͉�
	 */
	public void setMultipleCurrencyInputFlag(String multipleCurrencyInputFlag) {
		this.multipleCurrencyInputFlag = multipleCurrencyInputFlag;
	}

	/**
	 * ���ʉ݃t���O��Ԃ��B<BR>
	 * 
	 * @return multipleCurrencyInputFlag ���ʉ݃t���O true:���͉� false:���͕s��
	 */
	public boolean isIncludeMultipleCurrencyInputFlag() {
		return "1".equals(this.multipleCurrencyInputFlag);
	}

	/**
	 * GL�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param kmkCntGl GL�Ȗڐ���敪
	 */
	public void setKmkCntGl(String kmkCntGl) {
		this.kmkCntGl = kmkCntGl;
	}

	/**
	 * GL�Ȗڐ���敪��Ԃ��B<BR>
	 * 
	 * @return kmkCntGl GL�Ȗڐ���敪
	 */
	public String getKmkCntGl() {
		return kmkCntGl;
	}

	/**
	 * AP�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param kmkCntAp AP�Ȗڐ���敪
	 */
	public void setKmkCntAp(String kmkCntAp) {
		this.kmkCntAp = kmkCntAp;
	}

	/**
	 * AP�Ȗڐ���敪��Ԃ��B<BR>
	 * 
	 * @return kmkCntAp AP�Ȗڐ���敪
	 */
	public String getKmkCntAp() {
		return kmkCntAp;
	}

	/**
	 * AR�Ȗڐ���敪��ݒ肷��B<BR>
	 * 
	 * @param kmkCntAr AR�Ȗڐ���敪
	 */
	public void setKmkCntAr(String kmkCntAr) {
		this.kmkCntAr = kmkCntAr;
	}

	/**
	 * AR�Ȗڐ���敪��Ԃ��B<BR>
	 * 
	 * @return kmkCntAr AR�Ȗڐ���敪
	 */
	public String getKmkCntAr() {
		return kmkCntAr;
	}

	/**
	 * �Ȗڑ̌n�R�[�h��Ԃ��B<BR>
	 * 
	 * @param itemSystemCode �Ȗڑ̌n�R�[�h
	 */
	public void setItemSystemCode(String itemSystemCode) {
		this.itemSystemCode = itemSystemCode;
	}

	/**
	 * �Ȗڑ̌n�R�[�h��ݒ肷��B<BR>
	 * 
	 * @return itemSystemCode �Ȗڑ̌n�R�[�h
	 */
	public String getItemSystemCode() {
		return itemSystemCode;
	}

	/**
	 * �l�������l�ɖ߂�
	 */
	public void clear() {
		isExist = false;
		itemCode = "";
		itemAbbrName = "";
		subItemCode = "";
		subItemAbbrName = "";
		breakDownItemCode = "";
		breakDownItemAbbrName = "";
		subItemFlag = "";
		breakDownItemFlag = "";
		salesTaxInputFlag = "";
		purchaseTaxationInputFlag = "";
		consumptionTaxCode = "";
		consumptionTaxAbbrName = "";
		consumptionTaxDivision = "";
		customerInputFlag = "0";
		employeeInputFlag = "";
		employeeCode = "";
		employeeAbbrName = "";
		management1InputFlag = "";
		management2InputFlag = "";
		management3InputFlag = "";
		management4InputFlag = "";
		management5InputFlag = "";
		management6InputFlag = "";
		nonAccountingDetail1Flag = "";
		nonAccountingDetail2Flag = "";
		nonAccountingDetail3Flag = "";
		accrualDateInputFlag = "";
		multipleCurrencyInputFlag = "";
		kmkCntGl = "";
		kmkCntAp = "";
		kmkCntAr = "";
		itemSystemCode = "";
	}

}

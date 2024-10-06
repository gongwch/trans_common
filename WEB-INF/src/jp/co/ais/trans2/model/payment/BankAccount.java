package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * ��s�������
 * 
 * @author AIS
 */
public class BankAccount extends TransferBase implements AutoCompletable, FilterableEntity {

	/** serialVersionUID */
	private static final long serialVersionUID = 1201636486328477363L;

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ��s�������� */
	protected String name = null;

	/** ��s�������� */
	protected String nameS = null;

	/** �ʉ݃R�[�h */
	protected String currencyCode = null;

	/** �ʉݏ�� */
	protected Currency currency = null;

	/** �ʉ݌� */
	protected int decimalPoint = 0;

	/** ��s�R�[�h */
	protected String bankCode = null;

	/** ��s���� */
	protected String bankName = null;

	/** ��s�J�i */
	protected String bankKana = null;

	/** �x�X�R�[�h */
	protected String branchCode = null;

	/** �x�X���� */
	protected String branchName = null;

	/** �x�X�J�i */
	protected String branchKana = null;

	/** ��s�������� */
	protected String bankNamek = null;

	/** �x�X�������� */
	protected String branchNamek = null;

	/** ��s���́i�p���j */
	protected String bankNameE = null;

	/** �x�X���́i�p���j */
	protected String branchNameE = null;

	/** �U���˗��l�R�[�h */
	protected String clientCode = null;

	/** �U���˗��l�� */
	protected String clientName = null;

	/** �U���˗��l���i�����j */
	protected String clientNameJ = null;

	/** �U���˗��l���i�p���j */
	protected String clientNameE = null;

	/** �a����� */
	protected DepositKind depositKind = null;

	/** �����ԍ� */
	protected String accountNo = null;

	/** �v�㕔��R�[�h */
	protected String departmentCode = null;

	/** �v�㕔�喼�� */
	protected String departmentName = null;

	/** �v�㕔�嗪�� */
	protected String departmentNames = null;

	/** �v�㕔�匟������ */
	protected String departmentNamek = null;

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �Ȗږ��� */
	protected String itemName = null;

	/** �Ȗڗ��� */
	protected String itemNames = null;

	/** �Ȗڌ������� */
	protected String itemNamek = null;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** �⏕�Ȗږ��� */
	protected String subItemName = null;

	/** �⏕�Ȗڗ��� */
	protected String subItemNames = null;

	/** �⏕�Ȗڌ������� */
	protected String subItemNamek = null;

	/** ����ȖڃR�[�h */
	protected String detailItemCode = null;

	/** ����Ȗږ��� */
	protected String detailItemName = null;

	/** ����Ȗڗ��� */
	protected String detailItemNames = null;

	/** ����Ȗڌ������� */
	protected String detailItemNamek = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �Ј��e�a�Ŏg�p���邩 */
	protected boolean useEmployeePayment = false;

	/** �ЊO�e�a�Ŏg�p���邩 */
	protected boolean useExPayment = false;

	/** IBAN�R�[�h */
	protected String iBan = null;

	/** ��s���ʎq */
	protected String bankIndentify = null;

	/** �������ʎq */
	protected String bankAccountIndentify = null;

	/** SWIFT�R�[�h */
	protected String swift = null;
	
	/** BANK COUNTRY */
	protected String bankCountry = null;

	/** �Z��1 */
	protected String bnkAdr1 = null;

	/** �Z��2 */
	protected String bnkAdr2 = null;

	/** �Z��1�i�p���j */
	protected String bnkAdr1E = null;

	/** �Z��2�i�p���j */
	protected String bnkAdr2E = null;

	/**
	 * @return names
	 */
	public String getNames() {
		return getNameS();
	}

	/**
	 * @return namek
	 */
	public String getNamek() {
		return getName();
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCode() + " " + getName();
	}

	/**
	 * �����ԍ����擾����B
	 * 
	 * @return String accountNo
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * �����ԍ���ݒ肷��B
	 * 
	 * @param accountNo
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * ��s�R�[�h���擾����B
	 * 
	 * @return String bankCode
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * ��s�R�[�h��ݒ肷��B
	 * 
	 * @param bankCode
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * ��s�����擾����B
	 * 
	 * @return String bankname
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * ��s����ݒ肷��B
	 * 
	 * @param bankname
	 */
	public void setBankName(String bankname) {
		this.bankName = bankname;
	}

	/**
	 * ��s�J�i�̎擾
	 * 
	 * @return bankKana ��s�J�i
	 */
	public String getBankKana() {
		return bankKana;
	}

	/**
	 * ��s�J�i�̐ݒ�
	 * 
	 * @param bankKana ��s�J�i
	 */
	public void setBankKana(String bankKana) {
		this.bankKana = bankKana;
	}

	/**
	 * ��s�������̂̎擾
	 * 
	 * @return bankNamek ��s��������
	 */
	public String getBankNamek() {
		return bankNamek;
	}

	/**
	 * ��s�������̂̐ݒ�
	 * 
	 * @param bankNamek ��s��������
	 */
	public void setBankNamek(String bankNamek) {
		this.bankNamek = bankNamek;
	}

	/**
	 * branchCode���擾����B
	 * 
	 * @return String branchCode
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * branchCode��ݒ肷��B
	 * 
	 * @param branchCode
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * branchName���擾����B
	 * 
	 * @return String branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * branchName��ݒ肷��B
	 * 
	 * @param branchName
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * �x�X�J�i�̎擾
	 * 
	 * @return branchKana �x�X�J�i
	 */
	public String getBranchKana() {
		return branchKana;
	}

	/**
	 * �x�X�J�i�̐ݒ�
	 * 
	 * @param branchKana �x�X�J�i
	 */
	public void setBranchKana(String branchKana) {
		this.branchKana = branchKana;
	}

	/**
	 * �x�X�������̂̎擾
	 * 
	 * @return branchNamek �x�X��������
	 */
	public String getBranchNamek() {
		return branchNamek;
	}

	/**
	 * �x�X�������̂̐ݒ�
	 * 
	 * @param branchNamek �x�X��������
	 */
	public void setBranchNamek(String branchNamek) {
		this.branchNamek = branchNamek;
	}

	/**
	 * ��s���i�p���j���擾����B
	 * 
	 * @return String bankNameE
	 */
	public String getBankNameE() {
		return bankNameE;
	}

	/**
	 * ��s���i�p���j��ݒ肷��B
	 * 
	 * @param bankNameE
	 */
	public void setBankNameE(String bankNameE) {
		this.bankNameE = bankNameE;
	}

	/**
	 * �x�X���i�p���j���擾����B
	 * 
	 * @return String branchNameE
	 */
	public String getBranchNameE() {
		return branchNameE;
	}

	/**
	 * �x�X���i�p���j��ݒ肷��B
	 * 
	 * @param branchNameE
	 */
	public void setBranchNameE(String branchNameE) {
		this.branchNameE = branchNameE;
	}

	/**
	 * �a����ʂ��擾����B
	 * 
	 * @return String �a�����
	 */
	public String getDepositKindName() {
		return DepositKind.getDepositKindName(depositKind);
	}

	/**
	 * �a����� + �����ԍ����擾����B
	 * 
	 * @return String ��s�� + �x�X��
	 */
	public String getDepositKindAndAcountNoNo() {
		return DepositKind.getDepositKindName(depositKind) + " " + Util.avoidNull(accountNo);
	}

	/**
	 * ��s�� + �x�X�����擾����B
	 * 
	 * @return String ��s�� + �x�X��
	 */
	public String getBankAndBranchName() {
		return Util.avoidNull(bankName) + " " + Util.avoidNull(branchName);
	}

	/**
	 * �U���˗��l�R�[�h���擾����B
	 * 
	 * @return String clientCode
	 */
	public String getClientCode() {
		return clientCode;
	}

	/**
	 * �U���˗��l�R�[�h��ݒ肷��B
	 * 
	 * @param clientCode
	 */
	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	/**
	 * �U���˗��l�����擾����B
	 * 
	 * @return String clientName
	 */
	public String getClientName() {
		return clientName;
	}

	/**
	 * �U���˗��l����ݒ肷��B
	 * 
	 * @param clientName
	 */
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	/**
	 * �U���˗��l���i�p���j���擾����B
	 * 
	 * @return String clientNameE
	 */
	public String getClientNameE() {
		return clientNameE;
	}

	/**
	 * �U���˗��l���i�p���j��ݒ肷��B
	 * 
	 * @param clientNameE
	 */
	public void setClientNameE(String clientNameE) {
		this.clientNameE = clientNameE;
	}

	/**
	 * �U���˗��l���i�����j���擾����B
	 * 
	 * @return String clientNameJ
	 */
	public String getClientNameJ() {
		return clientNameJ;
	}

	/**
	 * �U���˗��l���i�����j��ݒ肷��B
	 * 
	 * @param clientNameJ
	 */
	public void setClientNameJ(String clientNameJ) {
		this.clientNameJ = clientNameJ;
	}

	/**
	 * code���擾����B
	 * 
	 * @return String code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * code��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * companyCode���擾����B
	 * 
	 * @return String companyCode
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * companyCode��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * currencyCode���擾����B
	 * 
	 * @return String currencyCode
	 */
	public String getCurrencyCode() {
		return currencyCode;
	}

	/**
	 * currencyCode��ݒ肷��B
	 * 
	 * @param currencyCode
	 */
	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	/**
	 * �ʉݏ��̎擾
	 * 
	 * @return currency �ʉݏ��
	 */
	public Currency getCurrency() {
		return currency;
	}

	/**
	 * �ʉݏ��̐ݒ�
	 * 
	 * @param currency �ʉݏ��
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	/**
	 * dateFrom���擾����B
	 * 
	 * @return Date dateFrom
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * dateFrom��ݒ肷��B
	 * 
	 * @param dateFrom
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * dateTo���擾����B
	 * 
	 * @return Date dateTo
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * dateTo��ݒ肷��B
	 * 
	 * @param dateTo
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * departmentCode���擾����B
	 * 
	 * @return String departmentCode
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * departmentCode��ݒ肷��B
	 * 
	 * @param departmentCode
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * �a����ڂ��擾����B
	 * 
	 * @return DepositKind depositKind
	 */
	public DepositKind getDepositKind() {
		return depositKind;
	}

	/**
	 * �a����ڂ�ݒ肷��B
	 * 
	 * @param depositKind
	 */
	public void setDepositKind(DepositKind depositKind) {
		this.depositKind = depositKind;
	}

	/**
	 * detailItemCode���擾����B
	 * 
	 * @return String detailItemCode
	 */
	public String getDetailItemCode() {
		return detailItemCode;
	}

	/**
	 * detailItemCode��ݒ肷��B
	 * 
	 * @param detailItemCode
	 */
	public void setDetailItemCode(String detailItemCode) {
		this.detailItemCode = detailItemCode;
	}

	/**
	 * itemCode���擾����B
	 * 
	 * @return String itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * itemCode��ݒ肷��B
	 * 
	 * @param itemCode
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * name���擾����B
	 * 
	 * @return String name
	 */
	public String getName() {
		return name;
	}

	/**
	 * name��ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * subItemCode���擾����B
	 * 
	 * @return String subItemCode
	 */
	public String getSubItemCode() {
		return subItemCode;
	}

	/**
	 * subItemCode��ݒ肷��B
	 * 
	 * @param subItemCode
	 */
	public void setSubItemCode(String subItemCode) {
		this.subItemCode = subItemCode;
	}

	/**
	 * useEmployeePayment���擾����B
	 * 
	 * @return boolean useEmployeePayment
	 */
	public boolean isUseEmployeePayment() {
		return useEmployeePayment;
	}

	/**
	 * useEmployeePayment��ݒ肷��B
	 * 
	 * @param useEmployeePayment
	 */
	public void setUseEmployeePayment(boolean useEmployeePayment) {
		this.useEmployeePayment = useEmployeePayment;
	}

	/**
	 * useExPayment���擾����B
	 * 
	 * @return boolean useExPayment
	 */
	public boolean isUseExPayment() {
		return useExPayment;
	}

	/**
	 * useExPayment��ݒ肷��B
	 * 
	 * @param useExPayment
	 */
	public void setUseExPayment(boolean useExPayment) {
		this.useExPayment = useExPayment;
	}

	/**
	 * �ʉ݌��̎擾
	 * 
	 * @return decimalPoint �ʉ݌�
	 */
	public int getDecimalPoint() {
		return decimalPoint;
	}

	/**
	 * �ʉ݌��̐ݒ�
	 * 
	 * @param decimalPoint �ʉ݌�
	 */
	public void setDecimalPoint(int decimalPoint) {
		this.decimalPoint = decimalPoint;
	}

	/**
	 * ��s�������̂̎擾
	 * 
	 * @return nameS ��s��������
	 */
	public String getNameS() {
		return nameS;
	}

	/**
	 * ��s�������̂̐ݒ�
	 * 
	 * @param nameS ��s��������
	 */
	public void setNameS(String nameS) {
		this.nameS = nameS;
	}

	/**
	 * �v�㕔�喼�̂̎擾
	 * 
	 * @return departmentName �v�㕔�喼��
	 */
	public String getDepartmentName() {
		return departmentName;
	}

	/**
	 * �v�㕔�喼�̂̐ݒ�
	 * 
	 * @param departmentName �v�㕔�喼��
	 */
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	/**
	 * �v�㕔�嗪�̂̎擾
	 * 
	 * @return departmentNames �v�㕔�嗪��
	 */
	public String getDepartmentNames() {
		return departmentNames;
	}

	/**
	 * �v�㕔�嗪�̂̐ݒ�
	 * 
	 * @param departmentNames �v�㕔�嗪��
	 */
	public void setDepartmentNames(String departmentNames) {
		this.departmentNames = departmentNames;
	}

	/**
	 * �v�㕔�匟�����̂̎擾
	 * 
	 * @return departmentNamek �v�㕔�匟������
	 */
	public String getDepartmentNamek() {
		return departmentNamek;
	}

	/**
	 * �v�㕔�匟�����̂̐ݒ�
	 * 
	 * @param departmentNamek �v�㕔�匟������
	 */
	public void setDepartmentNamek(String departmentNamek) {
		this.departmentNamek = departmentNamek;
	}

	/**
	 * �Ȗږ��̂̎擾
	 * 
	 * @return itemName �Ȗږ���
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * �Ȗږ��̂̐ݒ�
	 * 
	 * @param itemName �Ȗږ���
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * �Ȗڗ��̂̎擾
	 * 
	 * @return itemNames �Ȗڗ���
	 */
	public String getItemNames() {
		return itemNames;
	}

	/**
	 * �Ȗڗ��̂̐ݒ�
	 * 
	 * @param itemNames �Ȗڗ���
	 */
	public void setItemNames(String itemNames) {
		this.itemNames = itemNames;
	}

	/**
	 * �Ȗڌ������̂̎擾
	 * 
	 * @return itemNamek �Ȗڌ�������
	 */
	public String getItemNamek() {
		return itemNamek;
	}

	/**
	 * �Ȗڌ������̂̐ݒ�
	 * 
	 * @param itemNamek �Ȗڌ�������
	 */
	public void setItemNamek(String itemNamek) {
		this.itemNamek = itemNamek;
	}

	/**
	 * �⏕�Ȗږ��̂̎擾
	 * 
	 * @return subItemName �⏕�Ȗږ���
	 */
	public String getSubItemName() {
		return subItemName;
	}

	/**
	 * �⏕�Ȗږ��̂̐ݒ�
	 * 
	 * @param subItemName �⏕�Ȗږ���
	 */
	public void setSubItemName(String subItemName) {
		this.subItemName = subItemName;
	}

	/**
	 * �⏕�Ȗڗ��̂̎擾
	 * 
	 * @return subItemNames �⏕�Ȗڗ���
	 */
	public String getSubItemNames() {
		return subItemNames;
	}

	/**
	 * �⏕�Ȗڗ��̂̐ݒ�
	 * 
	 * @param subItemNames �⏕�Ȗڗ���
	 */
	public void setSubItemNames(String subItemNames) {
		this.subItemNames = subItemNames;
	}

	/**
	 * �⏕�Ȗڌ������̂̎擾
	 * 
	 * @return subItemNamek �⏕�Ȗڌ�������
	 */
	public String getSubItemNamek() {
		return subItemNamek;
	}

	/**
	 * �⏕�Ȗڌ������̂̐ݒ�
	 * 
	 * @param subItemNamek �⏕�Ȗڌ�������
	 */
	public void setSubItemNamek(String subItemNamek) {
		this.subItemNamek = subItemNamek;
	}

	/**
	 * ����Ȗږ��̂̎擾
	 * 
	 * @return detailItemName ����Ȗږ���
	 */
	public String getDetailItemName() {
		return detailItemName;
	}

	/**
	 * ����Ȗږ��̂̐ݒ�
	 * 
	 * @param detailItemName ����Ȗږ���
	 */
	public void setDetailItemName(String detailItemName) {
		this.detailItemName = detailItemName;
	}

	/**
	 * ����Ȗڗ��̂̎擾
	 * 
	 * @return detailItemNames ����Ȗڗ���
	 */
	public String getDetailItemNames() {
		return detailItemNames;
	}

	/**
	 * ����Ȗڗ��̂̐ݒ�
	 * 
	 * @param detailItemNames ����Ȗڗ���
	 */
	public void setDetailItemNames(String detailItemNames) {
		this.detailItemNames = detailItemNames;
	}

	/**
	 * ����Ȗڌ������̂̎擾
	 * 
	 * @return detailItemNamek ����Ȗڌ�������
	 */
	public String getDetailItemNamek() {
		return detailItemNamek;
	}

	/**
	 * ����Ȗڌ������̂̐ݒ�
	 * 
	 * @param detailItemNamek ����Ȗڌ�������
	 */
	public void setDetailItemNamek(String detailItemNamek) {
		this.detailItemNamek = detailItemNamek;
	}

	/**
	 * IBAN�R�[�h�̎擾
	 * 
	 * @return iBan IBAN�R�[�h
	 */
	public String getIBan() {
		return iBan;
	}

	/**
	 * IBAN�R�[�h�̐ݒ�
	 * 
	 * @param iBan IBAN�R�[�h
	 */
	public void setIBan(String iBan) {
		this.iBan = iBan;
	}

	/**
	 * ��s���ʎq�̎擾
	 * 
	 * @return bankIndentify ��s���ʎq
	 */
	public String getBankIndentify() {
		return bankIndentify;
	}

	/**
	 * ��s���ʎq�̐ݒ�
	 * 
	 * @param bankIndentify ��s���ʎq
	 */
	public void setBankIndentify(String bankIndentify) {
		this.bankIndentify = bankIndentify;
	}

	/**
	 * �������ʎq�̎擾
	 * 
	 * @return bankAccountIndentify �������ʎq
	 */
	public String getBankAccountIndentify() {
		return bankAccountIndentify;
	}

	/**
	 * �������ʎq�̐ݒ�
	 * 
	 * @param bankAccountIndentify �������ʎq
	 */
	public void setBankAccountIndentify(String bankAccountIndentify) {
		this.bankAccountIndentify = bankAccountIndentify;
	}

	/**
	 * SWIFT�R�[�h�̎擾
	 * 
	 * @return swift SWIFT�R�[�h
	 */
	public String getSwift() {
		return swift;
	}

	/**
	 * SWIFT�R�[�h�̐ݒ�
	 * 
	 * @param swift SWIFT�R�[�h
	 */
	public void setSwift(String swift) {
		this.swift = swift;
	}

	/**
	 * �Z��1�̎擾
	 * 
	 * @return bnkAdr1 �Z��1
	 */
	public String getBnkAdr1() {
		return bnkAdr1;
	}

	/**
	 * �Z��1�̐ݒ�
	 * 
	 * @param bnkAdr1 �Z��1
	 */
	public void setBnkAdr1(String bnkAdr1) {
		this.bnkAdr1 = bnkAdr1;
	}

	/**
	 * �Z��2�̎擾
	 * 
	 * @return bnkAdr2 �Z��2
	 */
	public String getBnkAdr2() {
		return bnkAdr2;
	}

	/**
	 * �Z��2�̐ݒ�
	 * 
	 * @param bnkAdr2 �Z��2
	 */
	public void setBnkAdr2(String bnkAdr2) {
		this.bnkAdr2 = bnkAdr2;
	}

	/**
	 * �Z��1�i�p���j�̎擾
	 * 
	 * @return bnkAdr1E �Z��1�i�p���j
	 */
	public String getBnkAdr1E() {
		return bnkAdr1E;
	}

	/**
	 * �Z��1�i�p���j�̐ݒ�
	 * 
	 * @param bnkAdr1E �Z��1�i�p���j
	 */
	public void setBnkAdr1E(String bnkAdr1E) {
		this.bnkAdr1E = bnkAdr1E;
	}

	/**
	 * �Z��2�i�p���j�̎擾
	 * 
	 * @return bnkAdr2E �Z��2�i�p���j
	 */
	public String getBnkAdr2E() {
		return bnkAdr2E;
	}

	/**
	 * �Z��2�i�p���j�̐ݒ�
	 * 
	 * @param bnkAdr2E �Z��2�i�p���j
	 */
	public void setBnkAdr2E(String bnkAdr2E) {
		this.bnkAdr2E = bnkAdr2E;
	}

	/**
	 * BANK COUNTRY�̎擾
	 * @return bankCountry
	 */
	public String getBankCountry() {
		return bankCountry;
	}

	
	/**
	 * BANK COUNTRY�̐ݒ�
	 * @param bankCountry
	 */
	public void setBankCountry(String bankCountry) {
		this.bankCountry = bankCountry;
	}
}

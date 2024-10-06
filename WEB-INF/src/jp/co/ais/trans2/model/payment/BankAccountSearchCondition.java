package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * ��s�����̌�������
 * 
 * @author AIS
 */
public class BankAccountSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
	OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ��s���i��s�� + �x�X���jlike */
	protected String nameLike = null;

	/** �����ԍ� like */
	protected String accountNokLike = null;

	/** �����ԍ� */
	protected String accountNo = null;

	/** �Ј��e�a�敪 */
	protected boolean useEmployeePayment = false;

	/** �ЊO�e�a�敪 */
	protected boolean useExPayment = false;

	/** �L������ */
	protected Date validTerm = null;

	/** ��s�R�[�h */
	protected String bankCode = null;

	/** �x�X�R�[�h */
	protected String branchCode = null;

	/** �a����� */
	protected DepositKind depositKind = null;

	/** �����ԍ�(10��0����) */
	protected String accountNoFillZero = null;

	/** �ʉ݃R�[�h */
	protected String currencyCode = null;

	/** ��s�����R�[�h�ŏ��l�擾�敪 */
	protected boolean minimumOnly = false;

	/** IBAN�R�[�h */
	protected String iBanCode = null;

	/** ��s���ʎq */
	protected String bankIdentify = null;

	/** �������ʎq */
	protected String accountIdentify = null;

	/** SWIFT�R�[�h */
	protected String swiftCode = null;

	/** �Z��1 */
	protected String bnkAdr1 = null;

	/** �Z��2 */
	protected String bnkAdr2 = null;

	/** �ȖڃR�[�h */
	protected String itemCode = null;

	/** �⏕�ȖڃR�[�h */
	protected String subItemCode = null;

	/** ����ȖڃR�[�h */
	protected String detailItemCode = null;

	/** ��t�ԍ� */
	protected String acceptNo = null;

	/** ��s�����R�[�h(����) */
	protected List<String> bankAccountCodeList = new LinkedList<String>();

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;
	
	/** ����R�[�h */
	protected String deptCode;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * currencyCode���擾����B
	 * 
	 * @return currencyCode
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
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public BankAccountSearchCondition clone() {
		try {
			return (BankAccountSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * accountNokLike���擾����B
	 * 
	 * @return String accountNokLike
	 */
	public String getAccountNokLike() {
		return accountNokLike;
	}

	/**
	 * accountNokLike��ݒ肷��B
	 * 
	 * @param accountNokLike
	 */
	public void setAccountNokLike(String accountNokLike) {
		this.accountNokLike = accountNokLike;
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
	 * codeFrom���擾����B
	 * 
	 * @return String codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * codeFrom��ݒ肷��B
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * codeLike���擾����B
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLike��ݒ肷��B
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * codeTo���擾����B
	 * 
	 * @return String codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * codeTo��ݒ肷��B
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
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
	 * nameLike���擾����B
	 * 
	 * @return String nameLike
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * nameLike��ݒ肷��B
	 * 
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
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
	 * validTerm���擾����B
	 * 
	 * @return Date validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * validTerm��ݒ肷��B
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * �����ԍ��̎擾
	 * 
	 * @return accountNo �����ԍ�
	 */
	public String getAccountNo() {
		return accountNo;
	}

	/**
	 * �����ԍ��̐ݒ�
	 * 
	 * @param accountNo �����ԍ�
	 */
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	/**
	 * �����ԍ�(10��0����)�̎擾
	 * 
	 * @return accountNoFillZero �����ԍ�(10��0����)
	 */
	public String getAccountNoFillZero() {
		return accountNoFillZero;
	}

	/**
	 * �����ԍ�(10��0����)�̐ݒ�
	 * 
	 * @param accountNoFillZero �����ԍ�(10��0����)
	 */
	public void setAccountNoFillZero(String accountNoFillZero) {
		this.accountNoFillZero = accountNoFillZero;
	}

	/**
	 * ��s�R�[�h�̎擾
	 * 
	 * @return bankCode ��s�R�[�h
	 */
	public String getBankCode() {
		return bankCode;
	}

	/**
	 * ��s�R�[�h�̐ݒ�
	 * 
	 * @param bankCode ��s�R�[�h
	 */
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	/**
	 * �x�X�R�[�h�̎擾
	 * 
	 * @return branchCode �x�X�R�[�h
	 */
	public String getBranchCode() {
		return branchCode;
	}

	/**
	 * �x�X�R�[�h�̐ݒ�
	 * 
	 * @param branchCode �x�X�R�[�h
	 */
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * �a����ڂ̎擾
	 * 
	 * @return depositKind �a�����
	 */
	public DepositKind getDepositKind() {
		return depositKind;
	}

	/**
	 * �a����ڂ̐ݒ�
	 * 
	 * @param depositKind �a�����
	 */
	public void setDepositKind(DepositKind depositKind) {
		this.depositKind = depositKind;
	}

	/**
	 * minimumOnly���擾����B
	 * 
	 * @return boolean minimumOnly
	 */
	public boolean isMinimumOnly() {
		return minimumOnly;
	}

	/**
	 * minimumOnly��ݒ肷��B
	 * 
	 * @param minimumOnly
	 */
	public void setMinimumOnly(boolean minimumOnly) {
		this.minimumOnly = minimumOnly;
	}

	/**
	 * IBAN�R�[�h�̎擾
	 * 
	 * @return iBanCode IBAN�R�[�h
	 */
	public String getIBanCode() {
		return iBanCode;
	}

	/**
	 * IBAN�R�[�h�̐ݒ�
	 * 
	 * @param iBanCode IBAN�R�[�h
	 */
	public void setIBanCode(String iBanCode) {
		this.iBanCode = iBanCode;
	}

	/**
	 * ��s���ʎq�̎擾
	 * 
	 * @return bankIdentify ��s���ʎq
	 */
	public String getBankIdentify() {
		return bankIdentify;
	}

	/**
	 * ��s���ʎq�̐ݒ�
	 * 
	 * @param bankIdentify ��s���ʎq
	 */
	public void setBankIdentify(String bankIdentify) {
		this.bankIdentify = bankIdentify;
	}

	/**
	 * �������ʎq�̎擾
	 * 
	 * @return accountIdentify �������ʎq
	 */
	public String getAccountIdentify() {
		return accountIdentify;
	}

	/**
	 * �������ʎq�̐ݒ�
	 * 
	 * @param accountIdentify �������ʎq
	 */
	public void setAccountIdentify(String accountIdentify) {
		this.accountIdentify = accountIdentify;
	}

	/**
	 * SWIFT�R�[�h�̎擾
	 * 
	 * @return swiftCode SWIFT�R�[�h
	 */
	public String getSwiftCode() {
		return swiftCode;
	}

	/**
	 * SWIFT�R�[�h�̐ݒ�
	 * 
	 * @param swiftCode SWIFT�R�[�h
	 */
	public void setSwiftCode(String swiftCode) {
		this.swiftCode = swiftCode;
	}

	/**
	 * �Z��1
	 * 
	 * @return bnkAdr1 �Z��1
	 */
	public String getBnkAdr1() {
		return bnkAdr1;
	}

	/**
	 * �Z��1
	 * 
	 * @param bnkAdr1 �Z��1
	 */
	public void setBnkAdr1(String bnkAdr1) {
		this.bnkAdr1 = bnkAdr1;
	}

	/**
	 * �Z��2
	 * 
	 * @return bnkAdr2 �Z��2
	 */
	public String getBnkAdr2() {
		return bnkAdr2;
	}

	/**
	 * �Z��2
	 * 
	 * @param bnkAdr2 �Z��2
	 */
	public void setBnkAdr2(String bnkAdr2) {
		this.bnkAdr2 = bnkAdr2;
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
	 * ��t�ԍ��̎擾
	 * 
	 * @return acceptNo ��t�ԍ�
	 */
	public String getAcceptNo() {
		return acceptNo;
	}

	/**
	 * ��t�ԍ��̐ݒ�
	 * 
	 * @param acceptNo ��t�ԍ�
	 */
	public void setAcceptNo(String acceptNo) {
		this.acceptNo = acceptNo;
	}

	/**
	 * @return �����܂�
	 */
	public String getNamekLike() {
		return nameLike;
	}

	/**
	 * @return �����܂�
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * ��s�����R�[�h(����)�̎擾
	 * 
	 * @return ��s�����R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.bankAccountCodeList;
	}

	/**
	 * ��s�����R�[�h(����)�̎擾
	 * 
	 * @return ��s�����R�[�h(����)
	 */
	public String[] getBankAccountCodeList() {
		if (bankAccountCodeList.isEmpty()) {
			return null;
		}

		return bankAccountCodeList.toArray(new String[bankAccountCodeList.size()]);
	}

	/**
	 * ��s�����R�[�h(����)�̐ݒ�
	 * 
	 * @param bankAccountCodeList ��s�����R�[�h(����)
	 */
	public void setBankAccountCodeList(String[] bankAccountCodeList) {
		addBankAccountSettingCode(bankAccountCodeList);
	}

	/**
	 * ��s�����R�[�h(����)�̐ݒ�
	 * 
	 * @param list ��s�����R�[�h(����)
	 */
	public void addBankAccountSettingCode(String... list) {
		for (String bankAccountCode : list) {
			this.bankAccountCodeList.add(bankAccountCode);
		}
	}

	/**
	 * ��s�����R�[�h(����)�̃N���A
	 */
	public void clearBankAccountCodeList() {
		bankAccountCodeList.clear();
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	/**
	 * ����R�[�h�̎擾
	 * 
	 * @return ����R�[�h
	 */
	public String getDeptCode() {
		return deptCode;
	}
	
	/**
	 * ����R�[�h�̐ݒ�
	 * 
	 * @param deptCode
	 */
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}

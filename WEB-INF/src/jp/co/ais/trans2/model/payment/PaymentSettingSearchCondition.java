package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�������}�X�^�̌�������
 * 
 * @author AIS
 */
public class PaymentSettingSearchCondition extends TransferBase implements Cloneable, FilterableCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �����R�[�h */
	protected String customerCode = null;

	/** �����R�[�h(�J�n) */
	protected String customerCodeFrom = null;

	/** �����R�[�h(�I��) */
	protected String customerCodeTo = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String nameLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �x�����������ԍ� */
	protected String yknNo = null;

	/** �x�������R�[�h(����) */
	protected List<String> paymentSettingCodeList = new LinkedList<String>();

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PaymentSettingSearchCondition clone() {
		try {
			return (PaymentSettingSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
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
	 * customerCode���擾����B
	 * 
	 * @return String customerCode
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * customerCode��ݒ肷��B
	 * 
	 * @param customerCode
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
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
	 * �����R�[�h(�J�n)
	 * 
	 * @return �����R�[�h(�J�n)
	 */
	public String getCustomerCodeFrom() {
		return this.customerCodeFrom;
	}

	/**
	 * �����R�[�h(�J�n)
	 * 
	 * @param customerCcodeFrom �����R�[�h(�J�n)
	 */
	public void setCustomerCodeFrom(String customerCcodeFrom) {
		this.customerCodeFrom = customerCcodeFrom;
	}

	/**
	 * �����R�[�h(�I��)
	 * 
	 * @return �����R�[�h(�I��)
	 */
	public String getCustomerCodeTo() {
		return this.customerCodeTo;
	}

	/**
	 * �����R�[�h(�I��)
	 * 
	 * @param customerCodeTo �����R�[�h(�I��)
	 */
	public void setCustomerCodeTo(String customerCodeTo) {
		this.customerCodeTo = customerCodeTo;
	}

	/**
	 * �x�����������ԍ��̎擾
	 * 
	 * @return yknNo �x�����������ԍ�
	 */
	public String getYknNo() {
		return yknNo;
	}

	/**
	 * �x�����������ԍ��̐ݒ�
	 * 
	 * @param yknNo �x�����������ԍ�
	 */
	public void setYknNo(String yknNo) {
		this.yknNo = yknNo;
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
	 * �x�������R�[�h(����)�̎擾
	 * 
	 * @return �x�������R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.paymentSettingCodeList;
	}

	/**
	 * �x�������R�[�h(����)�̎擾
	 * 
	 * @return �x�������R�[�h(����)
	 */
	public String[] getPaymentSettingCodeList() {
		if (paymentSettingCodeList.isEmpty()) {
			return null;
		}

		return paymentSettingCodeList.toArray(new String[paymentSettingCodeList.size()]);
	}

	/**
	 * �x�������R�[�h(����)�̐ݒ�
	 * 
	 * @param paymentSettingCodeList �x�������R�[�h(����)
	 */
	public void setPaymentSettingCodeList(String[] paymentSettingCodeList) {
		addPaymentSettingCode(paymentSettingCodeList);
	}

	/**
	 * �x�������R�[�h(����)�̐ݒ�
	 * 
	 * @param list �x�������R�[�h(����)
	 */
	public void addPaymentSettingCode(String... list) {
		for (String paymentSettingCode : list) {
			this.paymentSettingCodeList.add(paymentSettingCode);
		}
	}

	/**
	 * �x�������R�[�h(����)�̃N���A
	 */
	public void clearPaymentSettingCodeList() {
		paymentSettingCodeList.clear();
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

}

package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * �x�����@�̌�������
 * 
 * @author AIS
 */
public class PaymentMethodSearchCondition extends TransferBase implements Cloneable, FilterableCondition,
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

	/** ����like */
	protected String nameLike = null;

	/** �������� like */
	protected String namekLike = null;

	/** �Ј��x�� */
	protected boolean useEmployeePayment = false;

	/** �ЊO�x�� */
	protected boolean useExPayment = false;

	/** �L������ */
	protected Date validTerm = null;

	/** �x�����@�R�[�h */
	protected List<String> codeList = new LinkedList<String>();

	/** �܂߂Ȃ��x�����@�����R�[�h���X�g */
	protected List<PaymentKind> notPaymentKindList = new LinkedList<PaymentKind>();

	/** �x�����@�����R�[�h���X�g */
	protected List<PaymentKind> paymentKindList = new LinkedList<PaymentKind>();

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PaymentMethodSearchCondition clone() {
		try {
			return (PaymentMethodSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * accountNokLike���擾����B
	 * 
	 * @return String namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * accountNokLike��ݒ肷��B
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
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
	 * �x�����@�R�[�h(����)
	 * 
	 * @return �x�����@�R�[�h(����)
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * �x�����@�R�[�h(����)
	 * 
	 * @param lsit �x�����@�R�[�h(����)
	 */
	public void setCodeList(List<String> lsit) {
		this.codeList = lsit;
	}

	/**
	 * �x�����@�R�[�h(����)
	 * 
	 * @param lsit �x�����@�R�[�h(����)
	 */
	public void addCode(String... lsit) {
		for (String no : lsit) {
			this.codeList.add(no);
		}
	}

	/**
	 * �x�����@�R�[�h(����)�̃N���A
	 */
	public void clearCodeList() {
		codeList.clear();
	}

	/**
	 * �܂߂Ȃ��x�����@�����R�[�h
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public List<PaymentKind> getNotPaymentKindList() {
		return notPaymentKindList;
	}

	/**
	 * �܂߂Ȃ��x�����@�����R�[�h
	 * 
	 * @param paymentKindList �x�����@�����R�[�h
	 */
	public void setNotPaymentKindList(List<PaymentKind> paymentKindList) {
		this.notPaymentKindList = paymentKindList;
	}

	/**
	 * �܂߂Ȃ��x�����@�����R�[�h
	 * 
	 * @param kinds �x�����@�����R�[�h
	 */
	public void addNotPaymentKind(PaymentKind... kinds) {
		for (PaymentKind kind : kinds) {
			this.notPaymentKindList.add(kind);
		}
	}

	/**
	 * �܂߂Ȃ��x�����@�����R�[�h
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public String[] getNotPaymentKinds() {
		String[] arry = new String[notPaymentKindList.size()];
		int i = 0;
		for (PaymentKind kind : notPaymentKindList) {
			arry[i] = kind.value;
			i++;
		}

		return arry;
	}

	/**
	 * �x�����@�����R�[�h
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public List<PaymentKind> getPaymentKindList() {
		return paymentKindList;
	}

	/**
	 * �x�����@�����R�[�h
	 * 
	 * @param paymentKindList �x�����@�����R�[�h
	 */
	public void setPaymentKindList(List<PaymentKind> paymentKindList) {
		this.paymentKindList = paymentKindList;
	}

	/**
	 * �x�����@�����R�[�h
	 * 
	 * @param kinds �x�����@�����R�[�h
	 */
	public void addPaymentKind(PaymentKind... kinds) {
		for (PaymentKind kind : kinds) {
			this.paymentKindList.add(kind);
		}
	}

	/**
	 * �x�����@�����R�[�h
	 * 
	 * @return �x�����@�����R�[�h
	 */
	public String[] getPaymentKinds() {
		String[] arry = new String[paymentKindList.size()];
		int i = 0;
		for (PaymentKind kind : paymentKindList) {
			arry[i] = kind.value;
			i++;
		}

		return arry;
	}

	/**
	 * @return �����܂�
	 */
	public String getNamesLike() {
		return nameLike;
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

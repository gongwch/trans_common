package jp.co.ais.trans2.model.payment;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * �x�������}�X�^���
 * 
 * @author AIS
 */
public class PaymentSetting extends TransferBase implements Cloneable, AutoCompletable, FilterableEntity {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ����� */
	protected Customer customer;

	/** ���������R�[�h */
	protected String code;

	/** �U���萔���敪 */
	protected CashInFeeType cashInFeeType;

	/** �x���������ߓ� */
	protected String closeDay;

	/** �x���������ߌ㌎ */
	protected String nextMonth;

	/** �x�������x���� */
	protected String cashDay;

	/** �x���敪 */
	protected PaymentDateType paymentDateType;

	/** �x�����@ */
	protected PaymentMethod paymentMethod;

	/** �U���U�o��s���� */
	protected BankAccount bankAccount;

	/** ��s�R�[�h */
	protected String bankCode;

	/** ��s�� */
	protected String bankName;

	/** �x�X�R�[�h */
	protected String branchCode;

	/** �x�X�� */
	protected String branchName;

	/** �a����� */
	protected DepositKind depositKind;

	/** �a����ږ��� */
	protected String depositKindName;

	/** �����ԍ� */
	protected String accountNo;

	/** �������` */
	protected String accountName;

	/** �������`�J�i */
	protected String accountNameKana;

	/** �����ړI */
	protected Remittance remittancePurpose;

	/** ��d���x�X���� */
	protected String sendBranchName;

	/** ��d����s���� */
	protected String sendBankName;

	/** �萔���敪 */
	protected CommissionPaymentType commissionPaymentType;

	/** �x����s���� */
	protected String payBankName;

	/** �x���x�X���� */
	protected String payBranchName;

	/** �x����s�Z�� */
	protected String payBankAddress;

	/** �o�R��s���� */
	protected String viaBankName;

	/** �o�R�x�X���� */
	protected String viaBranchName;

	/** �o�R��s�Z�� */
	protected String viaBankAddress;

	/** ���l�Z�� */
	protected String receiverAddress;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** ��s�� */
	protected Country bankCountry;

	/** ��sSWIFT�R�[�h */
	protected String bankSwiftCode;

	/** �x����s�� */
	protected Country paymentBankCountry;

	/** �x����sSWIFT�R�[�h */
	protected String paymentBankSwiftCode;

	/** �o�R��s�� */
	protected Country viaBankCountry;

	/** �o�R��sSWIFT�R�[�h */
	protected String viaBankSwiftCode;

	/** ���l�� */
	protected Country recipientCountry;

	/** �o���N�`���[�W�敪 */
	protected BankChargeType bankChargeType;

	/**
	 * �I�u�W�F�N�g��Clone
	 */
	@Override
	public PaymentSetting clone() {
		try {

			return (PaymentSetting) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return name
	 */
	public String getName() {
		return getBankAndBranchName();
	}

	/**
	 * @return names
	 */
	public String getNames() {
		return getBankAndBranchName();
	}

	/**
	 * @return namek
	 */
	public String getNamek() {
		return getBankAndBranchName();
	}

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		return getCode() + " " + getBankAndBranchName() + " " + getDepositKindAndAcountNo();
	}

	/**
	 * �a����� + �����ԍ����擾����B
	 * 
	 * @return String �a����� + �����ԍ�
	 */
	public String getDepositKindAndAcountNoNo() {
		return DepositKind.getDepositKindName(depositKind) + " " + Util.avoidNull(accountNo);
	}

	/**
	 * �a����� + �����ԍ����擾����B
	 * 
	 * @return String �a����� + �����ԍ�
	 */
	public String getDepositKindAndAcountNo() {
		return depositKindName + " " + Util.avoidNull(accountNo);
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
	 * �����̎擾
	 * 
	 * @return customer �����
	 */
	public Customer getCustomer() {
		return customer;
	}

	/**
	 * �����̐ݒ�
	 * 
	 * @param customer �����
	 */
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	/**
	 * ���������R�[�h�̎擾
	 * 
	 * @return code ���������R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * ���������R�[�h�̐ݒ�
	 * 
	 * @param code ���������R�[�h
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * �U���萔���敪�̎擾
	 * 
	 * @return cashInFeeType �U���萔���敪
	 */
	public CashInFeeType getCashInFeeType() {
		return cashInFeeType;
	}

	/**
	 * �U���萔���敪�̐ݒ�
	 * 
	 * @param cashInFeeType �U���萔���敪
	 */
	public void setCashInFeeType(CashInFeeType cashInFeeType) {
		this.cashInFeeType = cashInFeeType;
	}

	/**
	 * �x���������ߓ��̎擾
	 * 
	 * @return closeDay �x���������ߓ�
	 */
	public String getCloseDay() {
		return closeDay;
	}

	/**
	 * �x���������ߓ��̐ݒ�
	 * 
	 * @param closeDay �x���������ߓ�
	 */
	public void setCloseDay(String closeDay) {
		this.closeDay = closeDay;
	}

	/**
	 * �x���������ߌ㌎�̎擾
	 * 
	 * @return nextMonth �x���������ߌ㌎
	 */
	public String getNextMonth() {
		return nextMonth;
	}

	/**
	 * �x���������ߌ㌎�̐ݒ�
	 * 
	 * @param nextMonth �x���������ߌ㌎
	 */
	public void setNextMonth(String nextMonth) {
		this.nextMonth = nextMonth;
	}

	/**
	 * �x�������x�����̎擾
	 * 
	 * @return cashDay �x�������x����
	 */
	public String getCashDay() {
		return cashDay;
	}

	/**
	 * �x�������x�����̐ݒ�
	 * 
	 * @param cashDay �x�������x����
	 */
	public void setCashDay(String cashDay) {
		this.cashDay = cashDay;
	}

	/**
	 * �x���敪�̎擾
	 * 
	 * @return paymentDateType �x���敪
	 */
	public PaymentDateType getPaymentDateType() {
		return paymentDateType;
	}

	/**
	 * �x���敪�̐ݒ�
	 * 
	 * @param paymentDateType �x���敪
	 */
	public void setPaymentDateType(PaymentDateType paymentDateType) {
		this.paymentDateType = paymentDateType;
	}

	/**
	 * �x�����@�̎擾
	 * 
	 * @return paymentMethod �x�����@
	 */
	public PaymentMethod getPaymentMethod() {
		return paymentMethod;
	}

	/**
	 * �x�����@�̐ݒ�
	 * 
	 * @param paymentMethod �x�����@
	 */
	public void setPaymentMethod(PaymentMethod paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/**
	 * �U���U�o��s�����̎擾
	 * 
	 * @return bankAccount �U���U�o��s����
	 */
	public BankAccount getBankAccount() {
		return bankAccount;
	}

	/**
	 * �U���U�o��s�����̐ݒ�
	 * 
	 * @param bankAccount �U���U�o��s����
	 */
	public void setBankAccount(BankAccount bankAccount) {
		this.bankAccount = bankAccount;
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
	 * ��s���̎擾
	 * 
	 * @return bankName ��s��
	 */
	public String getBankName() {
		return bankName;
	}

	/**
	 * ��s���̐ݒ�
	 * 
	 * @param bankName ��s��
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
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
	 * �x�X���̎擾
	 * 
	 * @return branchName �x�X��
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * �x�X���̐ݒ�
	 * 
	 * @param branchName �x�X��
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
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
	 * �a����ږ��̂̎擾
	 * 
	 * @return depositKindName �a����ږ���
	 */
	public String getDepositKindName() {
		return depositKindName;
	}

	/**
	 * �a����ږ��̂̐ݒ�
	 * 
	 * @param depositKindName �a����ږ���
	 */
	public void setDepositKindName(String depositKindName) {
		this.depositKindName = depositKindName;
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
	 * �������`�̎擾
	 * 
	 * @return accountName �������`
	 */
	public String getAccountName() {
		return accountName;
	}

	/**
	 * �������`�̐ݒ�
	 * 
	 * @param accountName �������`
	 */
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}

	/**
	 * �������`�J�i�̎擾
	 * 
	 * @return accountNameKana �������`�J�i
	 */
	public String getAccountNameKana() {
		return accountNameKana;
	}

	/**
	 * �������`�J�i�̐ݒ�
	 * 
	 * @param accountNameKana �������`�J�i
	 */
	public void setAccountNameKana(String accountNameKana) {
		this.accountNameKana = accountNameKana;
	}

	/**
	 * �����ړI�̎擾
	 * 
	 * @return remittancePurpose �����ړI
	 */
	public Remittance getRemittancePurpose() {
		return remittancePurpose;
	}

	/**
	 * �����ړI�̐ݒ�
	 * 
	 * @param remittancePurpose �����ړI
	 */
	public void setRemittancePurpose(Remittance remittancePurpose) {
		this.remittancePurpose = remittancePurpose;
	}

	/**
	 * ��d���x�X���̂̎擾
	 * 
	 * @return sendBranchName ��d���x�X����
	 */
	public String getSendBranchName() {
		return sendBranchName;
	}

	/**
	 * ��d���x�X���̂̐ݒ�
	 * 
	 * @param sendBranchName ��d���x�X����
	 */
	public void setSendBranchName(String sendBranchName) {
		this.sendBranchName = sendBranchName;
	}

	/**
	 * ��d����s���̂̎擾
	 * 
	 * @return sendBankName ��d����s����
	 */
	public String getSendBankName() {
		return sendBankName;
	}

	/**
	 * ��d����s���̂̐ݒ�
	 * 
	 * @param sendBankName ��d����s����
	 */
	public void setSendBankName(String sendBankName) {
		this.sendBankName = sendBankName;
	}

	/**
	 * �萔���敪�̎擾
	 * 
	 * @return commissionPaymentType �萔���敪
	 */
	public CommissionPaymentType getCommissionPaymentType() {
		return commissionPaymentType;
	}

	/**
	 * �萔���敪�̐ݒ�
	 * 
	 * @param commissionPaymentType �萔���敪
	 */
	public void setCommissionPaymentType(CommissionPaymentType commissionPaymentType) {
		this.commissionPaymentType = commissionPaymentType;
	}

	/**
	 * �x����s���̂̎擾
	 * 
	 * @return payBankName �x����s����
	 */
	public String getPayBankName() {
		return payBankName;
	}

	/**
	 * �x����s���̂̐ݒ�
	 * 
	 * @param payBankName �x����s����
	 */
	public void setPayBankName(String payBankName) {
		this.payBankName = payBankName;
	}

	/**
	 * �x���x�X���̂̎擾
	 * 
	 * @return payBranchName �x���x�X����
	 */
	public String getPayBranchName() {
		return payBranchName;
	}

	/**
	 * �x���x�X���̂̐ݒ�
	 * 
	 * @param payBranchName �x���x�X����
	 */
	public void setPayBranchName(String payBranchName) {
		this.payBranchName = payBranchName;
	}

	/**
	 * �x����s�Z���̎擾
	 * 
	 * @return payBankAddress �x����s�Z��
	 */
	public String getPayBankAddress() {
		return payBankAddress;
	}

	/**
	 * �x����s�Z���̐ݒ�
	 * 
	 * @param payBankAddress �x����s�Z��
	 */
	public void setPayBankAddress(String payBankAddress) {
		this.payBankAddress = payBankAddress;
	}

	/**
	 * �o�R��s���̂̎擾
	 * 
	 * @return viaBankName �o�R��s����
	 */
	public String getViaBankName() {
		return viaBankName;
	}

	/**
	 * �o�R��s���̂̐ݒ�
	 * 
	 * @param viaBankName �o�R��s����
	 */
	public void setViaBankName(String viaBankName) {
		this.viaBankName = viaBankName;
	}

	/**
	 * �o�R�x�X���̂̎擾
	 * 
	 * @return viaBranchName �o�R�x�X����
	 */
	public String getViaBranchName() {
		return viaBranchName;
	}

	/**
	 * �o�R�x�X���̂̐ݒ�
	 * 
	 * @param viaBranchName �o�R�x�X����
	 */
	public void setViaBranchName(String viaBranchName) {
		this.viaBranchName = viaBranchName;
	}

	/**
	 * �o�R��s�Z���̎擾
	 * 
	 * @return viaBankAddress �o�R��s�Z��
	 */
	public String getViaBankAddress() {
		return viaBankAddress;
	}

	/**
	 * �o�R��s�Z���̐ݒ�
	 * 
	 * @param viaBankAddress �o�R��s�Z��
	 */
	public void setViaBankAddress(String viaBankAddress) {
		this.viaBankAddress = viaBankAddress;
	}

	/**
	 * ���l�Z���̎擾
	 * 
	 * @return receiverAddress ���l�Z��
	 */
	public String getReceiverAddress() {
		return receiverAddress;
	}

	/**
	 * ���l�Z���̐ݒ�
	 * 
	 * @param receiverAddress ���l�Z��
	 */
	public void setReceiverAddress(String receiverAddress) {
		this.receiverAddress = receiverAddress;
	}

	/**
	 * �L�����ԊJ�n�̎擾
	 * 
	 * @return dateFrom �L�����ԊJ�n
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * �L�����ԊJ�n�̐ݒ�
	 * 
	 * @param dateFrom �L�����ԊJ�n
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * �L�����ԏI���̎擾
	 * 
	 * @return dateTo �L�����ԏI��
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * �L�����ԏI���̐ݒ�
	 * 
	 * @param dateTo �L�����ԏI��
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
	}

	/**
	 * ��s���̎擾
	 * 
	 * @return bankCountry ��s��
	 */
	public Country getBankCountry() {
		return bankCountry;
	}

	/**
	 * ��s���̐ݒ�
	 * 
	 * @param bankCountry ��s��
	 */
	public void setBankCountry(Country bankCountry) {
		this.bankCountry = bankCountry;
	}

	/**
	 * ��sSWIFT�R�[�h�̎擾
	 * 
	 * @return bankSwiftCode ��sSWIFT�R�[�h
	 */
	public String getBankSwiftCode() {
		return bankSwiftCode;
	}

	/**
	 * ��sSWIFT�R�[�h�̐ݒ�
	 * 
	 * @param bankSwiftCode ��sSWIFT�R�[�h
	 */
	public void setBankSwiftCode(String bankSwiftCode) {
		this.bankSwiftCode = bankSwiftCode;
	}

	/**
	 * �x����s���̎擾
	 * 
	 * @return paymentBankCountry �x����s��
	 */
	public Country getPaymentBankCountry() {
		return paymentBankCountry;
	}

	/**
	 * �x����s���̐ݒ�
	 * 
	 * @param paymentBankCountry �x����s��
	 */
	public void setPaymentBankCountry(Country paymentBankCountry) {
		this.paymentBankCountry = paymentBankCountry;
	}

	/**
	 * �x����sSWIFT�R�[�h�̎擾
	 * 
	 * @return paymentBankSwiftCode �x����sSWIFT�R�[�h
	 */
	public String getPaymentBankSwiftCode() {
		return paymentBankSwiftCode;
	}

	/**
	 * �x����sSWIFT�R�[�h�̐ݒ�
	 * 
	 * @param paymentBankSwiftCode �x����sSWIFT�R�[�h
	 */
	public void setPaymentBankSwiftCode(String paymentBankSwiftCode) {
		this.paymentBankSwiftCode = paymentBankSwiftCode;
	}

	/**
	 * �o�R��s���̎擾
	 * 
	 * @return viaBankCountry �o�R��s��
	 */
	public Country getViaBankCountry() {
		return viaBankCountry;
	}

	/**
	 * �o�R��s���̐ݒ�
	 * 
	 * @param viaBankCountry �o�R��s��
	 */
	public void setViaBankCountry(Country viaBankCountry) {
		this.viaBankCountry = viaBankCountry;
	}

	/**
	 * �o�R��sSWIFT�R�[�h�̎擾
	 * 
	 * @return viaBankSwiftCode �o�R��sSWIFT�R�[�h
	 */
	public String getViaBankSwiftCode() {
		return viaBankSwiftCode;
	}

	/**
	 * �o�R��sSWIFT�R�[�h�̐ݒ�
	 * 
	 * @param viaBankSwiftCode �o�R��sSWIFT�R�[�h
	 */
	public void setViaBankSwiftCode(String viaBankSwiftCode) {
		this.viaBankSwiftCode = viaBankSwiftCode;
	}

	/**
	 * ���l���̎擾
	 * 
	 * @return recipientCountry ���l��
	 */
	public Country getRecipientCountry() {
		return recipientCountry;
	}

	/**
	 * ���l���̐ݒ�
	 * 
	 * @param recipientCountry ���l��
	 */
	public void setRecipientCountry(Country recipientCountry) {
		this.recipientCountry = recipientCountry;
	}

	/**
	 * �o���N�`���[�W�敪�̎擾
	 * 
	 * @return bankChargeType �o���N�`���[�W�敪
	 */
	public BankChargeType getBankChargeType() {
		return bankChargeType;
	}

	/**
	 * �o���N�`���[�W�敪�̐ݒ�
	 * 
	 * @param bankChargeType �o���N�`���[�W�敪
	 */
	public void setBankChargeType(BankChargeType bankChargeType) {
		this.bankChargeType = bankChargeType;
	}

	/**
	 * �x�����Z�o ���ߓ��A�㌎�����ɒ莞�x�������Z�o
	 * 
	 * @param date ���
	 * @return �莞�x����
	 */
	public Date getRegularPaymentDate(Date date) {

		int closeDay_ = Integer.parseInt(closeDay);
		int nextMonth_ = Integer.parseInt(nextMonth);
		int cashDay_ = Integer.parseInt(cashDay);

		// ����̓����擾
		int baseday = DateUtil.getDay(date);

		// 29���ȍ~�͑S�Č�������(��99)�ɕϊ�
		if (closeDay_ > 29) {
			closeDay_ = 99;
		}

		// ��r ���ߓ��𒴂������̏ꍇ�A�������
		if (closeDay_ < baseday) {
			date = DateUtil.addMonth(date, 1);
		}

		// �㌎�����炷
		date = DateUtil.addMonth(date, nextMonth_);

		// �������� 29���ȍ~�͑S�Č�������
		if (cashDay_ > 28) {
			date = DateUtil.getLastDate(DateUtil.getYear(date), DateUtil.getMonth(date));
		} else {
			date = DateUtil.getDate(DateUtil.getYear(date), DateUtil.getMonth(date), cashDay_);
		}

		return date;
	}
}

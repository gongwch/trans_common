package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * �����x�������}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �U�����p�l�� */
	public TPanel pnlInformation;

	/** �莞�x�������p�l�� */
	public TPanel pnlRegularPayment;

	/** �����R�[�h���� */
	public TCustomerReference ctrlCustomerReference;

	/** �x�����@���� */
	public TPaymentMethodReference ctrlPaymentMethodReference;

	/** �U���U�o��s�����R�[�h���� */
	public TBankAccountReference ctrlBankAccountReference;

	/** ��s */
	public TBankReference ctrlBank;

	/** �x�X */
	public TBranchReference ctrlBranch;

	/** �����ړI������ */
	public TRemittanceReference ctrlRemittanceReference;

	/** �x���敪�R���{�{�b�N�X */
	public TLabelComboBox ctrlcboPaymentDataType;

	/** �a����ڃR���{�{�b�N�X */
	public TLabelComboBox ctrlcboDepositKind;

	/** �U���萔���R���{�{�b�N�X */
	public TLabelComboBox ctrlcboCashInFee;

	/** �萔���x���敪�R���{�{�b�N�X */
	public TLabelComboBox ctrlcboCommissionPaymentType;

	/** �x���������� */
	public TNumericField ctrlpaymentConditionDay;

	/** �x���������㌎ */
	public TNumericField ctrlpaymentConditionMonth;

	/** �x�������x���� */
	public TNumericField ctrlpaymentConditionPayDay;

	/** �`������ */
	public TLabel closeDay;

	/** �`������ */
	public TLabel nextMonth;

	/** �`������ */
	public TLabel cashDay;

	/** ���ӏ����P */
	public TLabel caution1;

	/** ���ӏ����Q */
	public TLabel caution2;

	/** ���������R�[�h */
	public TLabelField ctrlCustomerConditionCode;

	/** �����ԍ� */
	public TLabelField ctrlAccountNumber;

	/** �p����s�� */
	public TLabelField ctrlEnglishBankName;

	/** �p���x�X�� */
	public TLabelField ctrlEnglishBranchName;

	/** �p����s�Z�� */
	public TLabelField ctrlEnglishBankAddress;

	/** �������`�J�i���x�� */
	public TLabel lblAccountKana;

	/** �������`�J�i */
	public THalfAngleTextField ctrlAccountKana;

	/** �x����s���� */
	public TLabelField ctrlPaymentBankName;

	/** �x���x�X���� */
	public TLabelField ctrlPaymentBranchName;

	/** �x����s�Z�� */
	public TLabelField ctrlPaymentBankAddress;

	/** �o�R��s���� */
	public TLabelField ctrlViaBankName;

	/** �o�R�x�X���� */
	public TLabelField ctrlViaBranchName;

	/** �o�R��s�Z�� */
	public TLabelField ctrlViaBankAddress;

	/** ���l�Z�� */
	public TLabelField ctrlRecipientAddress;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrldtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrldtEndDate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0155CustomerPaymentSettingMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		pnlInformation = new TPanel();
		pnlRegularPayment = new TPanel();
		ctrlCustomerReference = new TCustomerReference();
		ctrlPaymentMethodReference = new TPaymentMethodReference();
		ctrlBankAccountReference = new TBankAccountReference();
		ctrlBranch = new TBranchReference();
		ctrlBank = new TBankReference();
		ctrlRemittanceReference = new TRemittanceReference();
		ctrlcboPaymentDataType = new TLabelComboBox();
		ctrlcboDepositKind = new TLabelComboBox();
		ctrlcboCashInFee = new TLabelComboBox();
		ctrlcboCommissionPaymentType = new TLabelComboBox();
		ctrlpaymentConditionDay = new TNumericField();
		ctrlpaymentConditionMonth = new TNumericField();
		ctrlpaymentConditionPayDay = new TNumericField();
		closeDay = new TLabel();
		nextMonth = new TLabel();
		cashDay = new TLabel();
		caution1 = new TLabel();
		caution2 = new TLabel();
		ctrlCustomerConditionCode = new TLabelField();
		ctrlAccountNumber = new TLabelField();
		ctrlEnglishBankName = new TLabelField();
		ctrlEnglishBranchName = new TLabelField();
		ctrlEnglishBankAddress = new TLabelField();
		lblAccountKana = new TLabel();
		ctrlAccountKana = new THalfAngleTextField();
		ctrlPaymentBankName = new TLabelField();
		ctrlPaymentBranchName = new TLabelField();
		ctrlPaymentBankAddress = new TLabelField();
		ctrlViaBankName = new TLabelField();
		ctrlViaBranchName = new TLabelField();
		ctrlViaBankAddress = new TLabelField();
		ctrlRecipientAddress = new TLabelField();
		ctrldtBeginDate = new TLabelPopupCalendar();
		ctrldtEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(850, 770);

		// �m��{�^��
		int x = 600;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		x = 720;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// �o�^���
		x = 40;

		// �����R�[�h����
		ctrlCustomerReference.setLocation(x, 10);
		pnlBody.add(ctrlCustomerReference);

		// ���������R�[�h
		ctrlCustomerConditionCode.setLabelSize(100);
		ctrlCustomerConditionCode.setFieldSize(80);
		ctrlCustomerConditionCode.setLocation(x - 25, ctrlCustomerReference.getY() + ctrlCustomerReference.getHeight()
			+ 5);
		ctrlCustomerConditionCode.setLangMessageID("C00788");
		ctrlCustomerConditionCode.setMaxLength(10);
		ctrlCustomerConditionCode.setImeMode(false);
		ctrlCustomerConditionCode.setAllowedSpace(false);
		pnlBody.add(ctrlCustomerConditionCode);

		// �x�����@
		ctrlPaymentMethodReference.setLocation(x,
			ctrlCustomerConditionCode.getY() + ctrlCustomerConditionCode.getHeight() + 5);
		pnlBody.add(ctrlPaymentMethodReference);

		// �x���敪�R���{�{�b�N�X
		ctrlcboPaymentDataType.setSize(150, 20);
		ctrlcboPaymentDataType.setComboSize(60);
		ctrlcboPaymentDataType.setLabelSize(80);
		ctrlcboPaymentDataType.setLocation(ctrlCustomerReference.getX() + ctrlCustomerReference.getWidth() + 20,
			ctrlCustomerReference.getY());
		ctrlcboPaymentDataType.setLangMessageID("C00682");
		setPaymentDataTypeItem(ctrlcboPaymentDataType.getComboBox());
		pnlBody.add(ctrlcboPaymentDataType);

		// �莞�x�������p�l��
		pnlRegularPayment.setLayout(null);
		pnlRegularPayment.setLangMessageID("C01244");
		pnlRegularPayment.setSize(260, 50);
		pnlRegularPayment.setLocation(ctrlcboPaymentDataType.getX() + 25, ctrlcboPaymentDataType.getY()
			+ ctrlcboPaymentDataType.getHeight() + 5);
		pnlBody.add(pnlRegularPayment);

		// �x����������
		ctrlpaymentConditionDay.setSize(30, 20);
		ctrlpaymentConditionDay.setLocation(20, 20);
		ctrlpaymentConditionDay.setMaxLength(2);
		ctrlpaymentConditionDay.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionDay);

		// �`������
		closeDay.setLangMessageID("C01265");
		closeDay.setSize(40, 20);
		closeDay.setLocation(ctrlpaymentConditionDay.getX() + ctrlpaymentConditionDay.getWidth() + 2, 20);
		pnlRegularPayment.add(closeDay);

		// �x���������㌎
		ctrlpaymentConditionMonth.setSize(30, 20);
		ctrlpaymentConditionMonth.setLocation(closeDay.getX() + closeDay.getWidth() + 5, 20);
		ctrlpaymentConditionMonth.setMaxLength(2);
		ctrlpaymentConditionMonth.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionMonth);

		// �`������
		nextMonth.setLangMessageID("C01493");
		nextMonth.setSize(40, 20);
		nextMonth.setLocation(ctrlpaymentConditionMonth.getX() + ctrlpaymentConditionMonth.getWidth() + 2, 20);
		pnlRegularPayment.add(nextMonth);

		// �x�������x����
		ctrlpaymentConditionPayDay.setSize(30, 20);
		ctrlpaymentConditionPayDay.setLocation(nextMonth.getX() + nextMonth.getWidth() + 5, 20);
		ctrlpaymentConditionPayDay.setMaxLength(2);
		ctrlpaymentConditionPayDay.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionPayDay);

		// �`������
		cashDay.setLangMessageID("C00448");
		cashDay.setSize(40, 20);
		cashDay.setLocation(ctrlpaymentConditionPayDay.getX() + ctrlpaymentConditionPayDay.getWidth() + 2, 20);
		pnlRegularPayment.add(cashDay);

		// �U�����

		// �U�����p�l��
		pnlInformation.setLayout(null);
		pnlInformation.setLangMessageID("C01184");
		pnlInformation.setSize(770, 510);
		pnlInformation.setLocation(x, ctrlPaymentMethodReference.getY() + ctrlPaymentMethodReference.getHeight() + 10);
		pnlBody.add(pnlInformation);

		// �U���U�o��s�����R�[�h����
		ctrlBankAccountReference.setLocation(68, 20);
		ctrlBankAccountReference.btn.setLangMessageID("C00946");
		pnlInformation.add(ctrlBankAccountReference);

		// ��s����
		ctrlBank.setLocation(ctrlBankAccountReference.getX(), ctrlBankAccountReference.getY()
			+ ctrlBankAccountReference.getHeight() + 5);
		pnlInformation.add(ctrlBank);

		// �x�X����
		ctrlBranch.setLocation(ctrlBank.getX(), ctrlBank.getY() + ctrlBank.getHeight() + 5);
		pnlInformation.add(ctrlBranch);

		// �����ړI������
		ctrlRemittanceReference.setLocation(ctrlBranch.getX(), ctrlBranch.getY() + ctrlBranch.getHeight() + 5);
		pnlInformation.add(ctrlRemittanceReference);

		// �U���萔���R���{�{�b�N�X
		ctrlcboCashInFee.setComboSize(150);
		ctrlcboCashInFee.setLabelSize(140);
		ctrlcboCashInFee.setLocation(2, ctrlRemittanceReference.getY() + ctrlRemittanceReference.getHeight() + 5);
		ctrlcboCashInFee.setLangMessageID(getWord("C01340") + getWord("C01183"));
		setCashInFeeItem(ctrlcboCashInFee.getComboBox());
		pnlInformation.add(ctrlcboCashInFee);

		// �萔���x���敪�R���{�{�b�N�X
		ctrlcboCommissionPaymentType.setComboSize(150);
		ctrlcboCommissionPaymentType.setLabelSize(140);
		ctrlcboCommissionPaymentType.setLocation(ctrlcboCashInFee.getX(),
			ctrlcboCashInFee.getY() + ctrlcboCashInFee.getHeight() + 5);
		ctrlcboCommissionPaymentType.setLangMessageID(getWord("C01334") + getWord("C01139"));
		setCommissionPaymentTypeItem(ctrlcboCommissionPaymentType.getComboBox());
		pnlInformation.add(ctrlcboCommissionPaymentType);

		// �a����ڃR���{�{�b�N�X
		ctrlcboDepositKind.setComboSize(150);
		ctrlcboDepositKind.setLabelSize(140);
		ctrlcboDepositKind.setLocation(ctrlcboCommissionPaymentType.getX(), ctrlcboCommissionPaymentType.getY()
			+ ctrlcboCommissionPaymentType.getHeight() + 5);
		ctrlcboDepositKind.setLangMessageID("C01326");
		setDepositKindItem(ctrlcboDepositKind.getComboBox());
		pnlInformation.add(ctrlcboDepositKind);

		// �����ԍ�
		ctrlAccountNumber.setLabelSize(140);
		ctrlAccountNumber.setFieldSize(400);
		ctrlAccountNumber.setLocation(ctrlcboDepositKind.getX(),
			ctrlcboDepositKind.getY() + ctrlcboDepositKind.getHeight() + 5);
		ctrlAccountNumber.setLangMessageID("C00794");
		ctrlAccountNumber.setMaxLength(34);
		ctrlAccountNumber.setImeMode(false);
		pnlInformation.add(ctrlAccountNumber);

		// �p����s��
		ctrlEnglishBankName.setLabelSize(140);
		ctrlEnglishBankName.setFieldSize(400);
		ctrlEnglishBankName.setLocation(ctrlAccountNumber.getX(),
			ctrlAccountNumber.getY() + ctrlAccountNumber.getHeight() + 5);
		ctrlEnglishBankName.setLangMessageID("C00795");
		ctrlEnglishBankName.setMaxLength(35);
		ctrlEnglishBankName.setImeMode(false);
		pnlInformation.add(ctrlEnglishBankName);

		// �p���x�X��
		ctrlEnglishBranchName.setLabelSize(140);
		ctrlEnglishBranchName.setFieldSize(400);
		ctrlEnglishBranchName.setLocation(ctrlEnglishBankName.getX(),
			ctrlEnglishBankName.getY() + ctrlEnglishBankName.getHeight() + 5);
		ctrlEnglishBranchName.setLangMessageID("C00796");
		ctrlEnglishBranchName.setMaxLength(35);
		ctrlEnglishBranchName.setImeMode(false);
		pnlInformation.add(ctrlEnglishBranchName);

		// �p����s�Z��
		ctrlEnglishBankAddress.setLabelSize(140);
		ctrlEnglishBankAddress.setFieldSize(400);
		ctrlEnglishBankAddress.setLocation(ctrlEnglishBranchName.getX(), ctrlEnglishBranchName.getY()
			+ ctrlEnglishBranchName.getHeight() + 5);
		ctrlEnglishBankAddress.setLangMessageID("C00797");
		ctrlEnglishBankAddress.setMaxLength(70);
		ctrlEnglishBankAddress.setImeMode(false);
		pnlInformation.add(ctrlEnglishBankAddress);

		// �������`�J�i���x��
		lblAccountKana.setLangMessageID("C02394");
		lblAccountKana.setSize(140, 20);
		lblAccountKana.setLocation(ctrlEnglishBankAddress.getX() + 15, ctrlEnglishBankAddress.getY()
			+ ctrlEnglishBankAddress.getHeight() + 5);
		pnlInformation.add(lblAccountKana);
		// �������`�J�i
		ctrlAccountKana.setSize(400, 20);
		ctrlAccountKana.setLocation(lblAccountKana.getWidth() + 7, ctrlEnglishBankAddress.getY()
			+ ctrlEnglishBankAddress.getHeight() + 5);
		ctrlAccountKana.setMaxLength(70);
		pnlInformation.add(ctrlAccountKana);

		// �x����s����
		ctrlPaymentBankName.setLabelSize(140);
		ctrlPaymentBankName.setFieldSize(400);
		ctrlPaymentBankName.setLocation(lblAccountKana.getX() - 15,
			ctrlAccountKana.getY() + ctrlAccountKana.getHeight() + 5);
		ctrlPaymentBankName.setLangMessageID("C00799");
		ctrlPaymentBankName.setMaxLength(35);
		ctrlPaymentBankName.setImeMode(false);
		pnlInformation.add(ctrlPaymentBankName);

		// ���ӏ����P
		caution1.setSize(200, 20);
		caution1.setLangMessageID("C01583"); // ���x����s�͎d����s�̃R�����X��
		caution1.setLocation(ctrlPaymentBankName.getX() + ctrlPaymentBankName.getWidth() + 5,
			ctrlPaymentBankName.getY());
		pnlInformation.add(caution1);

		// �x���x�X����
		ctrlPaymentBranchName.setLabelSize(140);
		ctrlPaymentBranchName.setFieldSize(400);
		ctrlPaymentBranchName.setLocation(ctrlPaymentBankName.getX(),
			ctrlPaymentBankName.getY() + ctrlPaymentBankName.getHeight() + 5);
		ctrlPaymentBranchName.setLangMessageID("C00800");
		ctrlPaymentBranchName.setMaxLength(35);
		ctrlPaymentBranchName.setImeMode(false);
		pnlInformation.add(ctrlPaymentBranchName);

		// �x����s�Z��
		ctrlPaymentBankAddress.setLabelSize(140);
		ctrlPaymentBankAddress.setFieldSize(400);
		ctrlPaymentBankAddress.setLocation(ctrlPaymentBranchName.getX(), ctrlPaymentBranchName.getY()
			+ ctrlPaymentBranchName.getHeight() + 5);
		ctrlPaymentBankAddress.setLangMessageID("C00801");
		ctrlPaymentBankAddress.setMaxLength(70);
		ctrlPaymentBankAddress.setImeMode(false);
		pnlInformation.add(ctrlPaymentBankAddress);

		// �o�R��s����
		ctrlViaBankName.setLabelSize(140);
		ctrlViaBankName.setFieldSize(400);
		ctrlViaBankName.setLocation(ctrlPaymentBankAddress.getX(), ctrlPaymentBankAddress.getY()
			+ ctrlPaymentBankAddress.getHeight() + 5);
		ctrlViaBankName.setLangMessageID("C00802");
		ctrlViaBankName.setMaxLength(35);
		ctrlViaBankName.setImeMode(false);
		pnlInformation.add(ctrlViaBankName);

		// ���ӏ����Q
		caution2.setSize(210, 20);
		caution2.setLangMessageID("C01584"); // ���o�R��s�͎d����s�̔�R�����X��
		caution2.setLocation(ctrlViaBankName.getX() + ctrlViaBankName.getWidth() + 5, ctrlViaBankName.getY());
		pnlInformation.add(caution2);

		// �o�R�x�X����
		ctrlViaBranchName.setLabelSize(140);
		ctrlViaBranchName.setFieldSize(400);
		ctrlViaBranchName.setLocation(ctrlViaBankName.getX(), ctrlViaBankName.getY() + ctrlViaBankName.getHeight() + 5);
		ctrlViaBranchName.setLangMessageID("C00803");
		ctrlViaBranchName.setMaxLength(35);
		ctrlViaBranchName.setImeMode(false);
		pnlInformation.add(ctrlViaBranchName);

		// �o�R��s�Z��
		ctrlViaBankAddress.setLabelSize(140);
		ctrlViaBankAddress.setFieldSize(400);
		ctrlViaBankAddress.setLocation(ctrlViaBranchName.getX(),
			ctrlViaBranchName.getY() + ctrlViaBranchName.getHeight() + 5);
		ctrlViaBankAddress.setLangMessageID("C00804");
		ctrlViaBankAddress.setMaxLength(70);
		ctrlViaBankAddress.setImeMode(false);
		pnlInformation.add(ctrlViaBankAddress);

		// ���l�Z��
		ctrlRecipientAddress.setLabelSize(140);
		ctrlRecipientAddress.setFieldSize(400);
		ctrlRecipientAddress.setLocation(ctrlViaBankAddress.getX(),
			ctrlViaBankAddress.getY() + ctrlViaBankAddress.getHeight() + 5);
		ctrlRecipientAddress.setLangMessageID("C00805");
		ctrlRecipientAddress.setMaxLength(70);
		ctrlRecipientAddress.setImeMode(false);
		pnlInformation.add(ctrlRecipientAddress);

		// �J�n�N����
		ctrldtBeginDate.setLabelSize(80);
		ctrldtBeginDate.setLocation(x - 20, pnlInformation.getY() + pnlInformation.getHeight() + 10);
		ctrldtBeginDate.setLangMessageID("C00055");
		pnlBody.add(ctrldtBeginDate);

		// �I���N����
		ctrldtEndDate.setLabelSize(80);
		ctrldtEndDate.setLocation(x - 20, ctrldtBeginDate.getY() + ctrldtBeginDate.getHeight() + 10);
		ctrldtEndDate.setLangMessageID("C00261");
		pnlBody.add(ctrldtEndDate);
	}

	/**
	 * �x���敪�R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setPaymentDataTypeItem(TComboBox comboBox) {
		comboBox.addTextValueItem(PaymentDateType.TEMPORARY,
			getWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.TEMPORARY)));
		comboBox.addTextValueItem(PaymentDateType.REGULAR,
			getWord(PaymentDateType.getPaymentDateTypeName(PaymentDateType.REGULAR)));
	}

	/**
	 * �a����ڃR���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setDepositKindItem(TComboBox comboBox) {
		comboBox.addTextValueItem(DepositKind.ORDINARY, getWord(DepositKind.getDepositKindName(DepositKind.ORDINARY)));
		comboBox.addTextValueItem(DepositKind.CURRENT, getWord(DepositKind.getDepositKindName(DepositKind.CURRENT)));
		comboBox.addTextValueItem(DepositKind.FOREIGN_CURRENCY,
			getWord(DepositKind.getDepositKindName(DepositKind.FOREIGN_CURRENCY)));
		comboBox.addTextValueItem(DepositKind.SAVINGS, getWord(DepositKind.getDepositKindName(DepositKind.SAVINGS)));
		comboBox.addTextValueItem(DepositKind.OTHERS, getWord(DepositKind.getDepositKindName(DepositKind.OTHERS)));
	}

	/**
	 * �U���萔���R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setCashInFeeItem(TComboBox comboBox) {
		comboBox.addTextValueItem(CashInFeeType.Our, getWord(CashInFeeType.getCashInFeeTypeName(CashInFeeType.Our)));
		comboBox
			.addTextValueItem(CashInFeeType.Other, getWord(CashInFeeType.getCashInFeeTypeName(CashInFeeType.Other)));
	}

	/**
	 * �萔���x���敪�R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setCommissionPaymentTypeItem(TComboBox comboBox) {
		comboBox.addTextValueItem(CommissionPaymentType.Receiver,
			getWord("C00330") + getWord(CommissionPaymentType.getName(CommissionPaymentType.Receiver)));
		comboBox.addTextValueItem(CommissionPaymentType.Payer,
			getWord("C00330") + getWord(CommissionPaymentType.getName(CommissionPaymentType.Payer)));
	}

	@Override
	public void setTabIndex() {
		int i = 1;

		ctrlCustomerReference.setTabControlNo(i++);
		ctrlCustomerConditionCode.setTabControlNo(i++);
		ctrlPaymentMethodReference.setTabControlNo(i++);
		ctrlcboPaymentDataType.setTabControlNo(i++);
		ctrlpaymentConditionDay.setTabControlNo(i++);
		ctrlpaymentConditionMonth.setTabControlNo(i++);
		ctrlpaymentConditionPayDay.setTabControlNo(i++);
		ctrlBankAccountReference.setTabControlNo(i++);
		ctrlBank.setTabControlNo(i++);
		ctrlBranch.setTabControlNo(i++);
		ctrlRemittanceReference.setTabControlNo(i++);
		ctrlcboCashInFee.setTabControlNo(i++);
		ctrlcboCommissionPaymentType.setTabControlNo(i++);
		ctrlcboDepositKind.setTabControlNo(i++);
		ctrlAccountNumber.setTabControlNo(i++);
		ctrlEnglishBankName.setTabControlNo(i++);
		ctrlEnglishBranchName.setTabControlNo(i++);
		ctrlEnglishBankAddress.setTabControlNo(i++);
		ctrlAccountKana.setTabControlNo(i++);
		ctrlPaymentBankName.setTabControlNo(i++);
		ctrlPaymentBranchName.setTabControlNo(i++);
		ctrlPaymentBankAddress.setTabControlNo(i++);
		ctrlViaBankName.setTabControlNo(i++);
		ctrlViaBranchName.setTabControlNo(i++);
		ctrlViaBankAddress.setTabControlNo(i++);
		ctrlRecipientAddress.setTabControlNo(i++);
		ctrldtBeginDate.setTabControlNo(i++);
		ctrldtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
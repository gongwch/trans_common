package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.model.ui.payment.*;
import jp.co.ais.trans2.define.*;

/**
 * �����x�������}�X�^(�C�O�p)�̕ҏW���
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �U�����p�l�� */
	public TPanel pnlInformation;

	/** �莞�x�������p�l�� */
	public TPanel pnlRegularPayment;

	/** �����R�[�h���� */
	public TCustomerReference ctrlCustomer;

	/** ���������R�[�h */
	public TLabelField txtCustomerConditionCode;

	/** �x�����@���� */
	public TPaymentMethodReference ctrlPaymentMethod;

	/** �x���敪�R���{�{�b�N�X */
	public TLabelComboBox cbxPaymentDataType;

	/** �`������ */
	public TLabel lblCloseDay;

	/** �`������ */
	public TLabel lblNextMonth;

	/** �`������ */
	public TLabel lblCashDay;

	/** �x���������� */
	public TNumericField numPaymentDay;

	/** �x���������㌎ */
	public TNumericField numPaymentMonth;

	/** �x�������x���� */
	public TNumericField numPaymentPayDay;

	/** �U���U�o��s�����R�[�h���� */
	public TBankAccountReference ctrlBankAccount;

	/** ��s */
	public TBankReference ctrlBank;

	/** �x�X */
	public TBranchReference ctrlBranch;

	/** �����ړI������ */
	public TRemittanceReference ctrlRemittanceReference;

	/** �a����ڃR���{�{�b�N�X */
	public TLabelComboBox cbxDepositKind;

	/** �o���N�`���[�W�敪 */
	public TLabelComboBox cbxBankChargeType;

	/** �U���萔���R���{�{�b�N�X */
	public TLabelComboBox cbxCashInFee;

	/** �萔���x���敪�R���{�{�b�N�X */
	public TLabelComboBox cbxCommissionPaymentType;

	/** �����ԍ� */
	public TLabelField txtAccountNumber;

	/** ��sSWIFT�R�[�h */
	public TLabelField txtBankSwiftCode;

	/** ��s���R�[�h */
	public TCountryReference ctrlBankCountry;

	/** �p����s�� */
	public TLabelField txtEnBankName;

	/** �p���x�X�� */
	public TLabelField txtEnBranchName;

	/** �p����s�Z�� */
	public TLabelField txtEnBankAddress;

	/** �������`�J�i���x�� */
	public TLabel lblAccountKana;

	/** �������`�J�i */
	public THalfAngleTextField txtAccountKana;

	/** �o�R��sSWIFT�R�[�h */
	public TLabelField txtViaBankSwiftCode;

	/** �o�R��s���R�[�h */
	public TCountryReference ctrlViaBankCountry;

	/** �o�R��s���� */
	public TLabelField txtViaBankName;

	/** ���ӏ����Q */
	public TAreaLabel lblViaCaution;

	/** �o�R�x�X���� */
	public TLabelField txtViaBranchName;

	/** �o�R��s�Z�� */
	public TLabelField txtViaBankAddress;

	/** ���l���R�[�h */
	public TCountryReference ctrlRecipientCountry;

	/** ���l�Z�� */
	public TLabelField txtRecipientAddress;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param parent
	 */
	public MG0156CustomerPaymentSettingMasterDialog(Frame parent) {
		super(parent, true);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#initComponents()
	 */
	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();

		pnlInformation = new TPanel();
		pnlRegularPayment = new TPanel();
		ctrlCustomer = new TCustomerReference();
		ctrlPaymentMethod = new TPaymentMethodReference();
		ctrlBankAccount = new TBankAccountReference();
		ctrlBranch = new TBranchReference();
		ctrlRemittanceReference = new TRemittanceReference();
		ctrlBank = new TBankReference();
		cbxPaymentDataType = new TLabelComboBox();
		cbxDepositKind = new TLabelComboBox();
		numPaymentDay = new TNumericField();
		numPaymentMonth = new TNumericField();
		numPaymentPayDay = new TNumericField();
		lblCloseDay = new TLabel();
		lblNextMonth = new TLabel();
		lblCashDay = new TLabel();
		lblViaCaution = new TAreaLabel();
		txtCustomerConditionCode = new TLabelField();
		txtAccountNumber = new TLabelField();
		txtEnBankName = new TLabelField();
		txtEnBranchName = new TLabelField();
		txtEnBankAddress = new TLabelField();
		lblAccountKana = new TLabel();
		txtAccountKana = new THalfAngleTextField();
		txtViaBankName = new TLabelField();
		txtViaBranchName = new TLabelField();
		txtViaBankAddress = new TLabelField();
		txtRecipientAddress = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

		ctrlBankCountry = new TCountryReference();
		txtBankSwiftCode = new TLabelField();
		ctrlViaBankCountry = new TCountryReference();
		txtViaBankSwiftCode = new TLabelField();
		ctrlRecipientCountry = new TCountryReference();
		cbxBankChargeType = new TLabelComboBox();
		cbxCashInFee = new TLabelComboBox();
		cbxCommissionPaymentType = new TLabelComboBox();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(850, 725);

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
		ctrlCustomer.setLocation(x, 10);
		pnlBody.add(ctrlCustomer);

		// ���������R�[�h
		txtCustomerConditionCode.setLabelSize(100);
		txtCustomerConditionCode.setFieldSize(80);
		txtCustomerConditionCode.setLocation(x - 25, ctrlCustomer.getY() + ctrlCustomer.getHeight() + 5);
		txtCustomerConditionCode.setLangMessageID("C00788");
		txtCustomerConditionCode.setMaxLength(10);
		txtCustomerConditionCode.setImeMode(false);
		txtCustomerConditionCode.setAllowedSpace(false);
		pnlBody.add(txtCustomerConditionCode);

		// �x�����@
		ctrlPaymentMethod.setLocation(x, txtCustomerConditionCode.getY() + txtCustomerConditionCode.getHeight() + 5);
		pnlBody.add(ctrlPaymentMethod);

		// �x���敪�R���{�{�b�N�X
		cbxPaymentDataType.setSize(150, 20);
		cbxPaymentDataType.setComboSize(100);
		cbxPaymentDataType.setLabelSize(80);
		cbxPaymentDataType.setLocation(ctrlCustomer.getX() + ctrlCustomer.getWidth() + 20, ctrlCustomer.getY());
		cbxPaymentDataType.setLangMessageID("C00682");
		pnlBody.add(cbxPaymentDataType);

		// �莞�x�������p�l��
		pnlRegularPayment.setLayout(null);
		pnlRegularPayment.setLangMessageID("C01244");
		pnlRegularPayment.setSize(330, 50);
		pnlRegularPayment.setLocation(cbxPaymentDataType.getX() + 25,
			cbxPaymentDataType.getY() + cbxPaymentDataType.getHeight() + 5);
		pnlBody.add(pnlRegularPayment);

		// �x����������
		numPaymentDay.setSize(30, 20);
		numPaymentDay.setLocation(20, 20);
		numPaymentDay.setMaxLength(2);
		numPaymentDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentDay);

		// �`������
		lblCloseDay.setLangMessageID("C01265");
		lblCloseDay.setSize(60, 20);
		lblCloseDay.setLocation(numPaymentDay.getX() + numPaymentDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCloseDay);

		// �x���������㌎
		numPaymentMonth.setSize(30, 20);
		numPaymentMonth.setLocation(lblCloseDay.getX() + lblCloseDay.getWidth() + 5, 20);
		numPaymentMonth.setMaxLength(2);
		numPaymentMonth.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentMonth);

		// �`������
		lblNextMonth.setLangMessageID("C01493");
		lblNextMonth.setSize(60, 20);
		lblNextMonth.setLocation(numPaymentMonth.getX() + numPaymentMonth.getWidth() + 2, 20);
		pnlRegularPayment.add(lblNextMonth);

		// �x�������x����
		numPaymentPayDay.setSize(30, 20);
		numPaymentPayDay.setLocation(lblNextMonth.getX() + lblNextMonth.getWidth() + 5, 20);
		numPaymentPayDay.setMaxLength(2);
		numPaymentPayDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentPayDay);

		// �`������
		lblCashDay.setLangMessageID("C00448");
		lblCashDay.setSize(60, 20);
		lblCashDay.setLocation(numPaymentPayDay.getX() + numPaymentPayDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCashDay);

		// �U�����
		int X = 5;

		// �U�����p�l��
		pnlInformation.setLayout(null);
		pnlInformation.setLangMessageID("C01184");
		pnlInformation.setSize(770, 485);
		pnlInformation.setLocation(x, ctrlPaymentMethod.getY() + ctrlPaymentMethod.getHeight() + 10);
		pnlBody.add(pnlInformation);

		// �U���U�o��s�����R�[�h
		ctrlBankAccount.btn.setLangMessageID("C00946");
		ctrlBankAccount.setButtonSize(110);
		ctrlBankAccount.setLocation(X + 35, 20);
		pnlInformation.add(ctrlBankAccount);

		// ��s����
		ctrlBank.setButtonSize(110);
		ctrlBank.setLocation(ctrlBankAccount.getX(), ctrlBankAccount.getY() + ctrlBankAccount.getHeight() + 5);
		pnlInformation.add(ctrlBank);

		// �x�X����
		ctrlBranch.setButtonSize(110);
		ctrlBranch.setLocation(ctrlBank.getX(), ctrlBank.getY() + ctrlBank.getHeight() + 5);
		pnlInformation.add(ctrlBranch);

		// �����ړI������
		ctrlRemittanceReference.setButtonSize(110);
		ctrlRemittanceReference.setLocation(ctrlBranch.getX(), ctrlBranch.getY() + ctrlBranch.getHeight() + 5);
		pnlInformation.add(ctrlRemittanceReference);

		// �a����ڃR���{�{�b�N�X
		cbxDepositKind.setLangMessageID("C01326");
		cbxDepositKind.setLabelSize(140);
		cbxDepositKind.setComboSize(120);
		cbxDepositKind.setLocation(ctrlBankAccount.getX() + ctrlBankAccount.getWidth() + 5, ctrlBankAccount.getY());
		pnlInformation.add(cbxDepositKind);

		int y = cbxDepositKind.getY() + cbxDepositKind.getHeight() + 5;

		// �o���N�`���[�W�敪�R���{�{�b�N�X
		cbxBankChargeType.setLangMessageID("C11423");
		cbxBankChargeType.setLabelSize(140);
		cbxBankChargeType.setComboSize(120);
		cbxBankChargeType.setLocation(cbxDepositKind.getX(), y);
		pnlInformation.add(cbxBankChargeType);

		y += cbxBankChargeType.getHeight() + 5;

		// �U���萔���R���{�{�b�N�X
		cbxCashInFee.setComboSize(150);
		cbxCashInFee.setLabelSize(140);
		cbxCashInFee.setLocation(cbxDepositKind.getX(), y);
		cbxCashInFee.setLangMessageID("C01183");
		pnlInformation.add(cbxCashInFee);

		y += cbxCashInFee.getHeight() + 5;

		// �萔���x���敪�R���{�{�b�N�X
		cbxCommissionPaymentType.setComboSize(150);
		cbxCommissionPaymentType.setLabelSize(140);
		cbxCommissionPaymentType.setLocation(cbxDepositKind.getX(), y);
		cbxCommissionPaymentType.setLangMessageID("C01139");
		pnlInformation.add(cbxCommissionPaymentType);

		// �����ԍ�
		txtAccountNumber.setLangMessageID("C00794");
		txtAccountNumber.setMaxLength(34);
		txtAccountNumber.setImeMode(false);
		txtAccountNumber.setLabelSize(140);
		txtAccountNumber.setFieldSize(400);
		txtAccountNumber.setLocation(X, ctrlRemittanceReference.getY() + ctrlRemittanceReference.getHeight() + 5);
		pnlInformation.add(txtAccountNumber);

		// ��sSWIFT�R�[�h
		txtBankSwiftCode.setLangMessageID("C11418");
		txtBankSwiftCode.setImeMode(false);
		txtBankSwiftCode.setLabelSize(140);
		txtBankSwiftCode.setFieldSize(100);
		txtBankSwiftCode.setMaxLength(11);
		txtBankSwiftCode.setAllowedSpace(false);
		txtBankSwiftCode.setLocation(X, txtAccountNumber.getY() + txtAccountNumber.getHeight() + 5);
		pnlInformation.add(txtBankSwiftCode);

		// �p����s��
		txtEnBankName.setLangMessageID("C00795");
		txtEnBankName.setMaxLength(35);
		txtEnBankName.setLabelSize(140);
		txtEnBankName.setFieldSize(400);
		txtEnBankName.setImeMode(false);
		txtEnBankName.setLocation(X, txtBankSwiftCode.getY() + txtBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtEnBankName);

		// �p���x�X��
		txtEnBranchName.setLangMessageID("C00796");
		txtEnBranchName.setMaxLength(35);
		txtEnBranchName.setLabelSize(140);
		txtEnBranchName.setFieldSize(400);
		txtEnBranchName.setImeMode(false);
		txtEnBranchName.setLocation(X, txtEnBankName.getY() + txtEnBankName.getHeight() + 5);
		pnlInformation.add(txtEnBranchName);

		// ��s��
		ctrlBankCountry.btn.setLangMessageID("C11425");
		ctrlBankCountry.setButtonSize(110);
		ctrlBankCountry.setLocation(X + 35, txtEnBranchName.getY() + txtEnBranchName.getHeight() + 5);
		pnlInformation.add(ctrlBankCountry);

		// �p����s�Z��
		txtEnBankAddress.setLangMessageID("C00797");
		txtEnBankAddress.setMaxLength(80);
		txtEnBankAddress.setLabelSize(140);
		txtEnBankAddress.setFieldSize(400);
		txtEnBankAddress.setImeMode(false);
		txtEnBankAddress.setLocation(X, ctrlBankCountry.getY() + ctrlBankCountry.getHeight() + 5);
		pnlInformation.add(txtEnBankAddress);

		// �o�R��sSWIFT�R�[�h
		txtViaBankSwiftCode.setLangMessageID("C11422");
		txtViaBankSwiftCode.setImeMode(false);
		txtViaBankSwiftCode.setLabelSize(140);
		txtViaBankSwiftCode.setFieldSize(100);
		txtViaBankSwiftCode.setMaxLength(11);
		txtViaBankSwiftCode.setAllowedSpace(false);
		txtViaBankSwiftCode.setLocation(X, txtEnBankAddress.getY() + txtEnBankAddress.getHeight() + 5);
		pnlInformation.add(txtViaBankSwiftCode);

		// �o�R��s����
		txtViaBankName.setLangMessageID("C00802");
		txtViaBankName.setMaxLength(35);
		txtViaBankName.setLabelSize(140);
		txtViaBankName.setFieldSize(400);
		txtViaBankName.setImeMode(false);
		txtViaBankName.setLocation(X, txtViaBankSwiftCode.getY() + txtViaBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtViaBankName);

		// ���ӏ���
		lblViaCaution.setSize(210, 60);
		lblViaCaution.setLocation(txtViaBankName.getX() + txtViaBankName.getWidth() + 5, txtViaBankName.getY());
		pnlInformation.add(lblViaCaution);

		// �o�R�x�X����
		txtViaBranchName.setLangMessageID("C00803");
		txtViaBranchName.setMaxLength(35);
		txtViaBranchName.setLabelSize(140);
		txtViaBranchName.setFieldSize(400);
		txtViaBranchName.setImeMode(false);
		txtViaBranchName.setLocation(X, txtViaBankName.getY() + txtViaBankName.getHeight() + 5);
		pnlInformation.add(txtViaBranchName);

		// �o�R��s��
		ctrlViaBankCountry.btn.setLangMessageID("C11427");
		ctrlViaBankCountry.setButtonSize(110);
		ctrlViaBankCountry.setLocation(X + 35, txtViaBranchName.getY() + txtViaBranchName.getHeight() + 5);
		pnlInformation.add(ctrlViaBankCountry);

		// �o�R��s�Z��
		txtViaBankAddress.setLangMessageID("C00804");
		txtViaBankAddress.setMaxLength(70);
		txtViaBankAddress.setLabelSize(140);
		txtViaBankAddress.setFieldSize(400);
		txtViaBankAddress.setImeMode(false);
		txtViaBankAddress.setLocation(X, ctrlViaBankCountry.getY() + ctrlViaBankCountry.getHeight() + 5);
		pnlInformation.add(txtViaBankAddress);

		// �������`�J�i���x��
		lblAccountKana.setLangMessageID("C02394");
		lblAccountKana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccountKana.setSize(125, 20);
		lblAccountKana.setLocation(txtViaBankAddress.getX() + 15,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		pnlInformation.add(lblAccountKana);

		// �������`�J�i
		txtAccountKana.setSize(400, 20);
		txtAccountKana.setLocation(lblAccountKana.getWidth() + 25,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		txtAccountKana.setMaxLength(70);
		pnlInformation.add(txtAccountKana);

		// ���l��
		ctrlRecipientCountry.btn.setLangMessageID("C11428");
		ctrlRecipientCountry.setButtonSize(110);
		ctrlRecipientCountry.setLocation(X + 35, txtAccountKana.getY() + txtAccountKana.getHeight() + 5);
		pnlInformation.add(ctrlRecipientCountry);

		// ���l�Z��
		txtRecipientAddress.setLangMessageID("C00805");
		txtRecipientAddress.setMaxLength(70);
		txtRecipientAddress.setLabelSize(140);
		txtRecipientAddress.setFieldSize(400);
		txtRecipientAddress.setImeMode(false);
		txtRecipientAddress.setLocation(X, ctrlRecipientCountry.getY() + ctrlRecipientCountry.getHeight() + 5);
		pnlInformation.add(txtRecipientAddress);

		// �J�n�N����
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelSize(80);
		dtBeginDate.setLocation(x - 20, pnlInformation.getY() + pnlInformation.getHeight() + 10);
		pnlBody.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setLabelSize(80);
		dtEndDate.setLocation(x - 20, dtBeginDate.getY() + dtBeginDate.getHeight() + 10);
		pnlBody.add(dtEndDate);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;

		ctrlCustomer.setTabControlNo(i++);
		txtCustomerConditionCode.setTabControlNo(i++);
		ctrlPaymentMethod.setTabControlNo(i++);
		cbxPaymentDataType.setTabControlNo(i++);
		numPaymentDay.setTabControlNo(i++);
		numPaymentMonth.setTabControlNo(i++);
		numPaymentPayDay.setTabControlNo(i++);

		ctrlBankAccount.setTabControlNo(i++);
		ctrlBank.setTabControlNo(i++);
		ctrlBranch.setTabControlNo(i++);
		ctrlRemittanceReference.setTabControlNo(i++);
		cbxDepositKind.setTabControlNo(i++);
		cbxBankChargeType.setTabControlNo(i++);

		txtAccountNumber.setTabControlNo(i++);
		txtBankSwiftCode.setTabControlNo(i++);
		txtEnBankName.setTabControlNo(i++);
		txtEnBranchName.setTabControlNo(i++);
		ctrlBankCountry.setTabControlNo(i++);
		txtEnBankAddress.setTabControlNo(i++);

		txtViaBankSwiftCode.setTabControlNo(i++);
		txtViaBankName.setTabControlNo(i++);
		txtViaBranchName.setTabControlNo(i++);
		ctrlViaBankCountry.setTabControlNo(i++);
		txtViaBankAddress.setTabControlNo(i++);

		txtAccountKana.setTabControlNo(i++);
		ctrlRecipientCountry.setTabControlNo(i++);
		txtRecipientAddress.setTabControlNo(i++);

		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
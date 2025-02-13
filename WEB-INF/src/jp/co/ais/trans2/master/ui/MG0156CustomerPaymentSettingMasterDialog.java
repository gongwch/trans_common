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
 * æøæx¥ð}X^(COp)ÌÒWæÊ
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterDialog extends TDialog {

	/** mè{^ */
	public TImageButton btnSettle;

	/** ßé{^ */
	public TButton btnClose;

	/** Uîñpl */
	public TPanel pnlInformation;

	/** èx¥ðpl */
	public TPanel pnlRegularPayment;

	/** æøæR[hõ */
	public TCustomerReference ctrlCustomer;

	/** æøæðR[h */
	public TLabelField txtCustomerConditionCode;

	/** x¥û@õ */
	public TPaymentMethodReference ctrlPaymentMethod;

	/** x¥æªR{{bNX */
	public TLabelComboBox cbxPaymentDataType;

	/** `ú÷ß */
	public TLabel lblCloseDay;

	/** `ã */
	public TLabel lblNextMonth;

	/** `ú¥¢ */
	public TLabel lblCashDay;

	/** x¥ð÷ú */
	public TNumericField numPaymentDay;

	/** x¥ð÷ã */
	public TNumericField numPaymentMonth;

	/** x¥ðx¥ú */
	public TNumericField numPaymentPayDay;

	/** UUoâsûÀR[hõ */
	public TBankAccountReference ctrlBankAccount;

	/** âs */
	public TBankReference ctrlBank;

	/** xX */
	public TBranchReference ctrlBranch;

	/** àÚI¼õ */
	public TRemittanceReference ctrlRemittanceReference;

	/** aàíÚR{{bNX */
	public TLabelComboBox cbxDepositKind;

	/** oN`[Wæª */
	public TLabelComboBox cbxBankChargeType;

	/** Uè¿R{{bNX */
	public TLabelComboBox cbxCashInFee;

	/** è¿x¥æªR{{bNX */
	public TLabelComboBox cbxCommissionPaymentType;

	/** ûÀÔ */
	public TLabelField txtAccountNumber;

	/** âsSWIFTR[h */
	public TLabelField txtBankSwiftCode;

	/** âsR[h */
	public TCountryReference ctrlBankCountry;

	/** p¶âs¼ */
	public TLabelField txtEnBankName;

	/** p¶xX¼ */
	public TLabelField txtEnBranchName;

	/** p¶âsZ */
	public TLabelField txtEnBankAddress;

	/** ûÀ¼`Jix */
	public TLabel lblAccountKana;

	/** ûÀ¼`Ji */
	public THalfAngleTextField txtAccountKana;

	/** oRâsSWIFTR[h */
	public TLabelField txtViaBankSwiftCode;

	/** oRâsR[h */
	public TCountryReference ctrlViaBankCountry;

	/** oRâs¼Ì */
	public TLabelField txtViaBankName;

	/** Ó«Q */
	public TAreaLabel lblViaCaution;

	/** oRxX¼Ì */
	public TLabelField txtViaBranchName;

	/** oRâsZ */
	public TLabelField txtViaBankAddress;

	/** óælR[h */
	public TCountryReference ctrlRecipientCountry;

	/** óælZ */
	public TLabelField txtRecipientAddress;

	/** JnNú */
	public TLabelPopupCalendar dtBeginDate;

	/** I¹Nú */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * RXgN^
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

		// mè{^
		int x = 600;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// ßé{^
		x = 720;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// o^îñ
		x = 40;

		// æøæR[hõ
		ctrlCustomer.setLocation(x, 10);
		pnlBody.add(ctrlCustomer);

		// æøæðR[h
		txtCustomerConditionCode.setLabelSize(100);
		txtCustomerConditionCode.setFieldSize(80);
		txtCustomerConditionCode.setLocation(x - 25, ctrlCustomer.getY() + ctrlCustomer.getHeight() + 5);
		txtCustomerConditionCode.setLangMessageID("C00788");
		txtCustomerConditionCode.setMaxLength(10);
		txtCustomerConditionCode.setImeMode(false);
		txtCustomerConditionCode.setAllowedSpace(false);
		pnlBody.add(txtCustomerConditionCode);

		// x¥û@
		ctrlPaymentMethod.setLocation(x, txtCustomerConditionCode.getY() + txtCustomerConditionCode.getHeight() + 5);
		pnlBody.add(ctrlPaymentMethod);

		// x¥æªR{{bNX
		cbxPaymentDataType.setSize(150, 20);
		cbxPaymentDataType.setComboSize(100);
		cbxPaymentDataType.setLabelSize(80);
		cbxPaymentDataType.setLocation(ctrlCustomer.getX() + ctrlCustomer.getWidth() + 20, ctrlCustomer.getY());
		cbxPaymentDataType.setLangMessageID("C00682");
		pnlBody.add(cbxPaymentDataType);

		// èx¥ðpl
		pnlRegularPayment.setLayout(null);
		pnlRegularPayment.setLangMessageID("C01244");
		pnlRegularPayment.setSize(330, 50);
		pnlRegularPayment.setLocation(cbxPaymentDataType.getX() + 25,
			cbxPaymentDataType.getY() + cbxPaymentDataType.getHeight() + 5);
		pnlBody.add(pnlRegularPayment);

		// x¥ð÷ú
		numPaymentDay.setSize(30, 20);
		numPaymentDay.setLocation(20, 20);
		numPaymentDay.setMaxLength(2);
		numPaymentDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentDay);

		// `ú÷ß
		lblCloseDay.setLangMessageID("C01265");
		lblCloseDay.setSize(60, 20);
		lblCloseDay.setLocation(numPaymentDay.getX() + numPaymentDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCloseDay);

		// x¥ð÷ã
		numPaymentMonth.setSize(30, 20);
		numPaymentMonth.setLocation(lblCloseDay.getX() + lblCloseDay.getWidth() + 5, 20);
		numPaymentMonth.setMaxLength(2);
		numPaymentMonth.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentMonth);

		// `ã
		lblNextMonth.setLangMessageID("C01493");
		lblNextMonth.setSize(60, 20);
		lblNextMonth.setLocation(numPaymentMonth.getX() + numPaymentMonth.getWidth() + 2, 20);
		pnlRegularPayment.add(lblNextMonth);

		// x¥ðx¥ú
		numPaymentPayDay.setSize(30, 20);
		numPaymentPayDay.setLocation(lblNextMonth.getX() + lblNextMonth.getWidth() + 5, 20);
		numPaymentPayDay.setMaxLength(2);
		numPaymentPayDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentPayDay);

		// `ú¥¢
		lblCashDay.setLangMessageID("C00448");
		lblCashDay.setSize(60, 20);
		lblCashDay.setLocation(numPaymentPayDay.getX() + numPaymentPayDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCashDay);

		// Uîñ
		int X = 5;

		// Uîñpl
		pnlInformation.setLayout(null);
		pnlInformation.setLangMessageID("C01184");
		pnlInformation.setSize(770, 485);
		pnlInformation.setLocation(x, ctrlPaymentMethod.getY() + ctrlPaymentMethod.getHeight() + 10);
		pnlBody.add(pnlInformation);

		// UUoâsûÀR[h
		ctrlBankAccount.btn.setLangMessageID("C00946");
		ctrlBankAccount.setButtonSize(110);
		ctrlBankAccount.setLocation(X + 35, 20);
		pnlInformation.add(ctrlBankAccount);

		// âsõ
		ctrlBank.setButtonSize(110);
		ctrlBank.setLocation(ctrlBankAccount.getX(), ctrlBankAccount.getY() + ctrlBankAccount.getHeight() + 5);
		pnlInformation.add(ctrlBank);

		// xXõ
		ctrlBranch.setButtonSize(110);
		ctrlBranch.setLocation(ctrlBank.getX(), ctrlBank.getY() + ctrlBank.getHeight() + 5);
		pnlInformation.add(ctrlBranch);

		// àÚI¼õ
		ctrlRemittanceReference.setButtonSize(110);
		ctrlRemittanceReference.setLocation(ctrlBranch.getX(), ctrlBranch.getY() + ctrlBranch.getHeight() + 5);
		pnlInformation.add(ctrlRemittanceReference);

		// aàíÚR{{bNX
		cbxDepositKind.setLangMessageID("C01326");
		cbxDepositKind.setLabelSize(140);
		cbxDepositKind.setComboSize(120);
		cbxDepositKind.setLocation(ctrlBankAccount.getX() + ctrlBankAccount.getWidth() + 5, ctrlBankAccount.getY());
		pnlInformation.add(cbxDepositKind);

		int y = cbxDepositKind.getY() + cbxDepositKind.getHeight() + 5;

		// oN`[WæªR{{bNX
		cbxBankChargeType.setLangMessageID("C11423");
		cbxBankChargeType.setLabelSize(140);
		cbxBankChargeType.setComboSize(120);
		cbxBankChargeType.setLocation(cbxDepositKind.getX(), y);
		pnlInformation.add(cbxBankChargeType);

		y += cbxBankChargeType.getHeight() + 5;

		// Uè¿R{{bNX
		cbxCashInFee.setComboSize(150);
		cbxCashInFee.setLabelSize(140);
		cbxCashInFee.setLocation(cbxDepositKind.getX(), y);
		cbxCashInFee.setLangMessageID("C01183");
		pnlInformation.add(cbxCashInFee);

		y += cbxCashInFee.getHeight() + 5;

		// è¿x¥æªR{{bNX
		cbxCommissionPaymentType.setComboSize(150);
		cbxCommissionPaymentType.setLabelSize(140);
		cbxCommissionPaymentType.setLocation(cbxDepositKind.getX(), y);
		cbxCommissionPaymentType.setLangMessageID("C01139");
		pnlInformation.add(cbxCommissionPaymentType);

		// ûÀÔ
		txtAccountNumber.setLangMessageID("C00794");
		txtAccountNumber.setMaxLength(34);
		txtAccountNumber.setImeMode(false);
		txtAccountNumber.setLabelSize(140);
		txtAccountNumber.setFieldSize(400);
		txtAccountNumber.setLocation(X, ctrlRemittanceReference.getY() + ctrlRemittanceReference.getHeight() + 5);
		pnlInformation.add(txtAccountNumber);

		// âsSWIFTR[h
		txtBankSwiftCode.setLangMessageID("C11418");
		txtBankSwiftCode.setImeMode(false);
		txtBankSwiftCode.setLabelSize(140);
		txtBankSwiftCode.setFieldSize(100);
		txtBankSwiftCode.setMaxLength(11);
		txtBankSwiftCode.setAllowedSpace(false);
		txtBankSwiftCode.setLocation(X, txtAccountNumber.getY() + txtAccountNumber.getHeight() + 5);
		pnlInformation.add(txtBankSwiftCode);

		// p¶âs¼
		txtEnBankName.setLangMessageID("C00795");
		txtEnBankName.setMaxLength(35);
		txtEnBankName.setLabelSize(140);
		txtEnBankName.setFieldSize(400);
		txtEnBankName.setImeMode(false);
		txtEnBankName.setLocation(X, txtBankSwiftCode.getY() + txtBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtEnBankName);

		// p¶xX¼
		txtEnBranchName.setLangMessageID("C00796");
		txtEnBranchName.setMaxLength(35);
		txtEnBranchName.setLabelSize(140);
		txtEnBranchName.setFieldSize(400);
		txtEnBranchName.setImeMode(false);
		txtEnBranchName.setLocation(X, txtEnBankName.getY() + txtEnBankName.getHeight() + 5);
		pnlInformation.add(txtEnBranchName);

		// âs
		ctrlBankCountry.btn.setLangMessageID("C11425");
		ctrlBankCountry.setButtonSize(110);
		ctrlBankCountry.setLocation(X + 35, txtEnBranchName.getY() + txtEnBranchName.getHeight() + 5);
		pnlInformation.add(ctrlBankCountry);

		// p¶âsZ
		txtEnBankAddress.setLangMessageID("C00797");
		txtEnBankAddress.setMaxLength(80);
		txtEnBankAddress.setLabelSize(140);
		txtEnBankAddress.setFieldSize(400);
		txtEnBankAddress.setImeMode(false);
		txtEnBankAddress.setLocation(X, ctrlBankCountry.getY() + ctrlBankCountry.getHeight() + 5);
		pnlInformation.add(txtEnBankAddress);

		// oRâsSWIFTR[h
		txtViaBankSwiftCode.setLangMessageID("C11422");
		txtViaBankSwiftCode.setImeMode(false);
		txtViaBankSwiftCode.setLabelSize(140);
		txtViaBankSwiftCode.setFieldSize(100);
		txtViaBankSwiftCode.setMaxLength(11);
		txtViaBankSwiftCode.setAllowedSpace(false);
		txtViaBankSwiftCode.setLocation(X, txtEnBankAddress.getY() + txtEnBankAddress.getHeight() + 5);
		pnlInformation.add(txtViaBankSwiftCode);

		// oRâs¼Ì
		txtViaBankName.setLangMessageID("C00802");
		txtViaBankName.setMaxLength(35);
		txtViaBankName.setLabelSize(140);
		txtViaBankName.setFieldSize(400);
		txtViaBankName.setImeMode(false);
		txtViaBankName.setLocation(X, txtViaBankSwiftCode.getY() + txtViaBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtViaBankName);

		// Ó«
		lblViaCaution.setSize(210, 60);
		lblViaCaution.setLocation(txtViaBankName.getX() + txtViaBankName.getWidth() + 5, txtViaBankName.getY());
		pnlInformation.add(lblViaCaution);

		// oRxX¼Ì
		txtViaBranchName.setLangMessageID("C00803");
		txtViaBranchName.setMaxLength(35);
		txtViaBranchName.setLabelSize(140);
		txtViaBranchName.setFieldSize(400);
		txtViaBranchName.setImeMode(false);
		txtViaBranchName.setLocation(X, txtViaBankName.getY() + txtViaBankName.getHeight() + 5);
		pnlInformation.add(txtViaBranchName);

		// oRâs
		ctrlViaBankCountry.btn.setLangMessageID("C11427");
		ctrlViaBankCountry.setButtonSize(110);
		ctrlViaBankCountry.setLocation(X + 35, txtViaBranchName.getY() + txtViaBranchName.getHeight() + 5);
		pnlInformation.add(ctrlViaBankCountry);

		// oRâsZ
		txtViaBankAddress.setLangMessageID("C00804");
		txtViaBankAddress.setMaxLength(70);
		txtViaBankAddress.setLabelSize(140);
		txtViaBankAddress.setFieldSize(400);
		txtViaBankAddress.setImeMode(false);
		txtViaBankAddress.setLocation(X, ctrlViaBankCountry.getY() + ctrlViaBankCountry.getHeight() + 5);
		pnlInformation.add(txtViaBankAddress);

		// ûÀ¼`Jix
		lblAccountKana.setLangMessageID("C02394");
		lblAccountKana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccountKana.setSize(125, 20);
		lblAccountKana.setLocation(txtViaBankAddress.getX() + 15,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		pnlInformation.add(lblAccountKana);

		// ûÀ¼`Ji
		txtAccountKana.setSize(400, 20);
		txtAccountKana.setLocation(lblAccountKana.getWidth() + 25,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		txtAccountKana.setMaxLength(70);
		pnlInformation.add(txtAccountKana);

		// óæl
		ctrlRecipientCountry.btn.setLangMessageID("C11428");
		ctrlRecipientCountry.setButtonSize(110);
		ctrlRecipientCountry.setLocation(X + 35, txtAccountKana.getY() + txtAccountKana.getHeight() + 5);
		pnlInformation.add(ctrlRecipientCountry);

		// óælZ
		txtRecipientAddress.setLangMessageID("C00805");
		txtRecipientAddress.setMaxLength(70);
		txtRecipientAddress.setLabelSize(140);
		txtRecipientAddress.setFieldSize(400);
		txtRecipientAddress.setImeMode(false);
		txtRecipientAddress.setLocation(X, ctrlRecipientCountry.getY() + ctrlRecipientCountry.getHeight() + 5);
		pnlInformation.add(txtRecipientAddress);

		// JnNú
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelSize(80);
		dtBeginDate.setLocation(x - 20, pnlInformation.getY() + pnlInformation.getHeight() + 10);
		pnlBody.add(dtBeginDate);

		// I¹Nú
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
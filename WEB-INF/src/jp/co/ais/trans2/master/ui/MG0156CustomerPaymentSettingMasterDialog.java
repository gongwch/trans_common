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
 * 取引先支払条件マスタ(海外用)の編集画面
 * 
 * @author AIS
 */
public class MG0156CustomerPaymentSettingMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 振込情報パネル */
	public TPanel pnlInformation;

	/** 定時支払条件パネル */
	public TPanel pnlRegularPayment;

	/** 取引先コード検索 */
	public TCustomerReference ctrlCustomer;

	/** 取引先条件コード */
	public TLabelField txtCustomerConditionCode;

	/** 支払方法検索 */
	public TPaymentMethodReference ctrlPaymentMethod;

	/** 支払区分コンボボックス */
	public TLabelComboBox cbxPaymentDataType;

	/** ～日締め */
	public TLabel lblCloseDay;

	/** ～ヵ月後 */
	public TLabel lblNextMonth;

	/** ～日払い */
	public TLabel lblCashDay;

	/** 支払条件締日 */
	public TNumericField numPaymentDay;

	/** 支払条件締後月 */
	public TNumericField numPaymentMonth;

	/** 支払条件支払日 */
	public TNumericField numPaymentPayDay;

	/** 振込振出銀行口座コード検索 */
	public TBankAccountReference ctrlBankAccount;

	/** 銀行 */
	public TBankReference ctrlBank;

	/** 支店 */
	public TBranchReference ctrlBranch;

	/** 送金目的名検索 */
	public TRemittanceReference ctrlRemittanceReference;

	/** 預金種目コンボボックス */
	public TLabelComboBox cbxDepositKind;

	/** バンクチャージ区分 */
	public TLabelComboBox cbxBankChargeType;

	/** 振込手数料コンボボックス */
	public TLabelComboBox cbxCashInFee;

	/** 手数料支払区分コンボボックス */
	public TLabelComboBox cbxCommissionPaymentType;

	/** 口座番号 */
	public TLabelField txtAccountNumber;

	/** 銀行SWIFTコード */
	public TLabelField txtBankSwiftCode;

	/** 銀行国コード */
	public TCountryReference ctrlBankCountry;

	/** 英文銀行名 */
	public TLabelField txtEnBankName;

	/** 英文支店名 */
	public TLabelField txtEnBranchName;

	/** 英文銀行住所 */
	public TLabelField txtEnBankAddress;

	/** 口座名義カナラベル */
	public TLabel lblAccountKana;

	/** 口座名義カナ */
	public THalfAngleTextField txtAccountKana;

	/** 経由銀行SWIFTコード */
	public TLabelField txtViaBankSwiftCode;

	/** 経由銀行国コード */
	public TCountryReference ctrlViaBankCountry;

	/** 経由銀行名称 */
	public TLabelField txtViaBankName;

	/** 注意書き２ */
	public TAreaLabel lblViaCaution;

	/** 経由支店名称 */
	public TLabelField txtViaBranchName;

	/** 経由銀行住所 */
	public TLabelField txtViaBankAddress;

	/** 受取人国コード */
	public TCountryReference ctrlRecipientCountry;

	/** 受取人住所 */
	public TLabelField txtRecipientAddress;

	/** 開始年月日 */
	public TLabelPopupCalendar dtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * コンストラクタ
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

		// 確定ボタン
		int x = 600;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 720;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// 登録情報
		x = 40;

		// 取引先コード検索
		ctrlCustomer.setLocation(x, 10);
		pnlBody.add(ctrlCustomer);

		// 取引先条件コード
		txtCustomerConditionCode.setLabelSize(100);
		txtCustomerConditionCode.setFieldSize(80);
		txtCustomerConditionCode.setLocation(x - 25, ctrlCustomer.getY() + ctrlCustomer.getHeight() + 5);
		txtCustomerConditionCode.setLangMessageID("C00788");
		txtCustomerConditionCode.setMaxLength(10);
		txtCustomerConditionCode.setImeMode(false);
		txtCustomerConditionCode.setAllowedSpace(false);
		pnlBody.add(txtCustomerConditionCode);

		// 支払方法
		ctrlPaymentMethod.setLocation(x, txtCustomerConditionCode.getY() + txtCustomerConditionCode.getHeight() + 5);
		pnlBody.add(ctrlPaymentMethod);

		// 支払区分コンボボックス
		cbxPaymentDataType.setSize(150, 20);
		cbxPaymentDataType.setComboSize(100);
		cbxPaymentDataType.setLabelSize(80);
		cbxPaymentDataType.setLocation(ctrlCustomer.getX() + ctrlCustomer.getWidth() + 20, ctrlCustomer.getY());
		cbxPaymentDataType.setLangMessageID("C00682");
		pnlBody.add(cbxPaymentDataType);

		// 定時支払条件パネル
		pnlRegularPayment.setLayout(null);
		pnlRegularPayment.setLangMessageID("C01244");
		pnlRegularPayment.setSize(330, 50);
		pnlRegularPayment.setLocation(cbxPaymentDataType.getX() + 25,
			cbxPaymentDataType.getY() + cbxPaymentDataType.getHeight() + 5);
		pnlBody.add(pnlRegularPayment);

		// 支払条件締日
		numPaymentDay.setSize(30, 20);
		numPaymentDay.setLocation(20, 20);
		numPaymentDay.setMaxLength(2);
		numPaymentDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentDay);

		// ～日締め
		lblCloseDay.setLangMessageID("C01265");
		lblCloseDay.setSize(60, 20);
		lblCloseDay.setLocation(numPaymentDay.getX() + numPaymentDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCloseDay);

		// 支払条件締後月
		numPaymentMonth.setSize(30, 20);
		numPaymentMonth.setLocation(lblCloseDay.getX() + lblCloseDay.getWidth() + 5, 20);
		numPaymentMonth.setMaxLength(2);
		numPaymentMonth.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentMonth);

		// ～ヵ月後
		lblNextMonth.setLangMessageID("C01493");
		lblNextMonth.setSize(60, 20);
		lblNextMonth.setLocation(numPaymentMonth.getX() + numPaymentMonth.getWidth() + 2, 20);
		pnlRegularPayment.add(lblNextMonth);

		// 支払条件支払日
		numPaymentPayDay.setSize(30, 20);
		numPaymentPayDay.setLocation(lblNextMonth.getX() + lblNextMonth.getWidth() + 5, 20);
		numPaymentPayDay.setMaxLength(2);
		numPaymentPayDay.setPositiveOnly(true);
		pnlRegularPayment.add(numPaymentPayDay);

		// ～日払い
		lblCashDay.setLangMessageID("C00448");
		lblCashDay.setSize(60, 20);
		lblCashDay.setLocation(numPaymentPayDay.getX() + numPaymentPayDay.getWidth() + 2, 20);
		pnlRegularPayment.add(lblCashDay);

		// 振込情報
		int X = 5;

		// 振込情報パネル
		pnlInformation.setLayout(null);
		pnlInformation.setLangMessageID("C01184");
		pnlInformation.setSize(770, 485);
		pnlInformation.setLocation(x, ctrlPaymentMethod.getY() + ctrlPaymentMethod.getHeight() + 10);
		pnlBody.add(pnlInformation);

		// 振込振出銀行口座コード
		ctrlBankAccount.btn.setLangMessageID("C00946");
		ctrlBankAccount.setButtonSize(110);
		ctrlBankAccount.setLocation(X + 35, 20);
		pnlInformation.add(ctrlBankAccount);

		// 銀行検索
		ctrlBank.setButtonSize(110);
		ctrlBank.setLocation(ctrlBankAccount.getX(), ctrlBankAccount.getY() + ctrlBankAccount.getHeight() + 5);
		pnlInformation.add(ctrlBank);

		// 支店検索
		ctrlBranch.setButtonSize(110);
		ctrlBranch.setLocation(ctrlBank.getX(), ctrlBank.getY() + ctrlBank.getHeight() + 5);
		pnlInformation.add(ctrlBranch);

		// 送金目的名検索
		ctrlRemittanceReference.setButtonSize(110);
		ctrlRemittanceReference.setLocation(ctrlBranch.getX(), ctrlBranch.getY() + ctrlBranch.getHeight() + 5);
		pnlInformation.add(ctrlRemittanceReference);

		// 預金種目コンボボックス
		cbxDepositKind.setLangMessageID("C01326");
		cbxDepositKind.setLabelSize(140);
		cbxDepositKind.setComboSize(120);
		cbxDepositKind.setLocation(ctrlBankAccount.getX() + ctrlBankAccount.getWidth() + 5, ctrlBankAccount.getY());
		pnlInformation.add(cbxDepositKind);

		int y = cbxDepositKind.getY() + cbxDepositKind.getHeight() + 5;

		// バンクチャージ区分コンボボックス
		cbxBankChargeType.setLangMessageID("C11423");
		cbxBankChargeType.setLabelSize(140);
		cbxBankChargeType.setComboSize(120);
		cbxBankChargeType.setLocation(cbxDepositKind.getX(), y);
		pnlInformation.add(cbxBankChargeType);

		y += cbxBankChargeType.getHeight() + 5;

		// 振込手数料コンボボックス
		cbxCashInFee.setComboSize(150);
		cbxCashInFee.setLabelSize(140);
		cbxCashInFee.setLocation(cbxDepositKind.getX(), y);
		cbxCashInFee.setLangMessageID("C01183");
		pnlInformation.add(cbxCashInFee);

		y += cbxCashInFee.getHeight() + 5;

		// 手数料支払区分コンボボックス
		cbxCommissionPaymentType.setComboSize(150);
		cbxCommissionPaymentType.setLabelSize(140);
		cbxCommissionPaymentType.setLocation(cbxDepositKind.getX(), y);
		cbxCommissionPaymentType.setLangMessageID("C01139");
		pnlInformation.add(cbxCommissionPaymentType);

		// 口座番号
		txtAccountNumber.setLangMessageID("C00794");
		txtAccountNumber.setMaxLength(34);
		txtAccountNumber.setImeMode(false);
		txtAccountNumber.setLabelSize(140);
		txtAccountNumber.setFieldSize(400);
		txtAccountNumber.setLocation(X, ctrlRemittanceReference.getY() + ctrlRemittanceReference.getHeight() + 5);
		pnlInformation.add(txtAccountNumber);

		// 銀行SWIFTコード
		txtBankSwiftCode.setLangMessageID("C11418");
		txtBankSwiftCode.setImeMode(false);
		txtBankSwiftCode.setLabelSize(140);
		txtBankSwiftCode.setFieldSize(100);
		txtBankSwiftCode.setMaxLength(11);
		txtBankSwiftCode.setAllowedSpace(false);
		txtBankSwiftCode.setLocation(X, txtAccountNumber.getY() + txtAccountNumber.getHeight() + 5);
		pnlInformation.add(txtBankSwiftCode);

		// 英文銀行名
		txtEnBankName.setLangMessageID("C00795");
		txtEnBankName.setMaxLength(35);
		txtEnBankName.setLabelSize(140);
		txtEnBankName.setFieldSize(400);
		txtEnBankName.setImeMode(false);
		txtEnBankName.setLocation(X, txtBankSwiftCode.getY() + txtBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtEnBankName);

		// 英文支店名
		txtEnBranchName.setLangMessageID("C00796");
		txtEnBranchName.setMaxLength(35);
		txtEnBranchName.setLabelSize(140);
		txtEnBranchName.setFieldSize(400);
		txtEnBranchName.setImeMode(false);
		txtEnBranchName.setLocation(X, txtEnBankName.getY() + txtEnBankName.getHeight() + 5);
		pnlInformation.add(txtEnBranchName);

		// 銀行国
		ctrlBankCountry.btn.setLangMessageID("C11425");
		ctrlBankCountry.setButtonSize(110);
		ctrlBankCountry.setLocation(X + 35, txtEnBranchName.getY() + txtEnBranchName.getHeight() + 5);
		pnlInformation.add(ctrlBankCountry);

		// 英文銀行住所
		txtEnBankAddress.setLangMessageID("C00797");
		txtEnBankAddress.setMaxLength(80);
		txtEnBankAddress.setLabelSize(140);
		txtEnBankAddress.setFieldSize(400);
		txtEnBankAddress.setImeMode(false);
		txtEnBankAddress.setLocation(X, ctrlBankCountry.getY() + ctrlBankCountry.getHeight() + 5);
		pnlInformation.add(txtEnBankAddress);

		// 経由銀行SWIFTコード
		txtViaBankSwiftCode.setLangMessageID("C11422");
		txtViaBankSwiftCode.setImeMode(false);
		txtViaBankSwiftCode.setLabelSize(140);
		txtViaBankSwiftCode.setFieldSize(100);
		txtViaBankSwiftCode.setMaxLength(11);
		txtViaBankSwiftCode.setAllowedSpace(false);
		txtViaBankSwiftCode.setLocation(X, txtEnBankAddress.getY() + txtEnBankAddress.getHeight() + 5);
		pnlInformation.add(txtViaBankSwiftCode);

		// 経由銀行名称
		txtViaBankName.setLangMessageID("C00802");
		txtViaBankName.setMaxLength(35);
		txtViaBankName.setLabelSize(140);
		txtViaBankName.setFieldSize(400);
		txtViaBankName.setImeMode(false);
		txtViaBankName.setLocation(X, txtViaBankSwiftCode.getY() + txtViaBankSwiftCode.getHeight() + 5);
		pnlInformation.add(txtViaBankName);

		// 注意書き
		lblViaCaution.setSize(210, 60);
		lblViaCaution.setLocation(txtViaBankName.getX() + txtViaBankName.getWidth() + 5, txtViaBankName.getY());
		pnlInformation.add(lblViaCaution);

		// 経由支店名称
		txtViaBranchName.setLangMessageID("C00803");
		txtViaBranchName.setMaxLength(35);
		txtViaBranchName.setLabelSize(140);
		txtViaBranchName.setFieldSize(400);
		txtViaBranchName.setImeMode(false);
		txtViaBranchName.setLocation(X, txtViaBankName.getY() + txtViaBankName.getHeight() + 5);
		pnlInformation.add(txtViaBranchName);

		// 経由銀行国
		ctrlViaBankCountry.btn.setLangMessageID("C11427");
		ctrlViaBankCountry.setButtonSize(110);
		ctrlViaBankCountry.setLocation(X + 35, txtViaBranchName.getY() + txtViaBranchName.getHeight() + 5);
		pnlInformation.add(ctrlViaBankCountry);

		// 経由銀行住所
		txtViaBankAddress.setLangMessageID("C00804");
		txtViaBankAddress.setMaxLength(70);
		txtViaBankAddress.setLabelSize(140);
		txtViaBankAddress.setFieldSize(400);
		txtViaBankAddress.setImeMode(false);
		txtViaBankAddress.setLocation(X, ctrlViaBankCountry.getY() + ctrlViaBankCountry.getHeight() + 5);
		pnlInformation.add(txtViaBankAddress);

		// 口座名義カナラベル
		lblAccountKana.setLangMessageID("C02394");
		lblAccountKana.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAccountKana.setSize(125, 20);
		lblAccountKana.setLocation(txtViaBankAddress.getX() + 15,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		pnlInformation.add(lblAccountKana);

		// 口座名義カナ
		txtAccountKana.setSize(400, 20);
		txtAccountKana.setLocation(lblAccountKana.getWidth() + 25,
			txtViaBankAddress.getY() + txtViaBankAddress.getHeight() + 5);
		txtAccountKana.setMaxLength(70);
		pnlInformation.add(txtAccountKana);

		// 受取人国
		ctrlRecipientCountry.btn.setLangMessageID("C11428");
		ctrlRecipientCountry.setButtonSize(110);
		ctrlRecipientCountry.setLocation(X + 35, txtAccountKana.getY() + txtAccountKana.getHeight() + 5);
		pnlInformation.add(ctrlRecipientCountry);

		// 受取人住所
		txtRecipientAddress.setLangMessageID("C00805");
		txtRecipientAddress.setMaxLength(70);
		txtRecipientAddress.setLabelSize(140);
		txtRecipientAddress.setFieldSize(400);
		txtRecipientAddress.setImeMode(false);
		txtRecipientAddress.setLocation(X, ctrlRecipientCountry.getY() + ctrlRecipientCountry.getHeight() + 5);
		pnlInformation.add(txtRecipientAddress);

		// 開始年月日
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelSize(80);
		dtBeginDate.setLocation(x - 20, pnlInformation.getY() + pnlInformation.getHeight() + 10);
		pnlBody.add(dtBeginDate);

		// 終了年月日
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
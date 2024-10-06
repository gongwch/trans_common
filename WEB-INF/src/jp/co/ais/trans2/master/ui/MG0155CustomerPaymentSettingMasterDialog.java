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
 * 取引先支払条件マスタの編集画面
 * 
 * @author AIS
 */
public class MG0155CustomerPaymentSettingMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 振込情報パネル */
	public TPanel pnlInformation;

	/** 定時支払条件パネル */
	public TPanel pnlRegularPayment;

	/** 取引先コード検索 */
	public TCustomerReference ctrlCustomerReference;

	/** 支払方法検索 */
	public TPaymentMethodReference ctrlPaymentMethodReference;

	/** 振込振出銀行口座コード検索 */
	public TBankAccountReference ctrlBankAccountReference;

	/** 銀行 */
	public TBankReference ctrlBank;

	/** 支店 */
	public TBranchReference ctrlBranch;

	/** 送金目的名検索 */
	public TRemittanceReference ctrlRemittanceReference;

	/** 支払区分コンボボックス */
	public TLabelComboBox ctrlcboPaymentDataType;

	/** 預金種目コンボボックス */
	public TLabelComboBox ctrlcboDepositKind;

	/** 振込手数料コンボボックス */
	public TLabelComboBox ctrlcboCashInFee;

	/** 手数料支払区分コンボボックス */
	public TLabelComboBox ctrlcboCommissionPaymentType;

	/** 支払条件締日 */
	public TNumericField ctrlpaymentConditionDay;

	/** 支払条件締後月 */
	public TNumericField ctrlpaymentConditionMonth;

	/** 支払条件支払日 */
	public TNumericField ctrlpaymentConditionPayDay;

	/** ～日締め */
	public TLabel closeDay;

	/** ～ヵ月後 */
	public TLabel nextMonth;

	/** ～日払い */
	public TLabel cashDay;

	/** 注意書き１ */
	public TLabel caution1;

	/** 注意書き２ */
	public TLabel caution2;

	/** 取引先条件コード */
	public TLabelField ctrlCustomerConditionCode;

	/** 口座番号 */
	public TLabelField ctrlAccountNumber;

	/** 英文銀行名 */
	public TLabelField ctrlEnglishBankName;

	/** 英文支店名 */
	public TLabelField ctrlEnglishBranchName;

	/** 英文銀行住所 */
	public TLabelField ctrlEnglishBankAddress;

	/** 口座名義カナラベル */
	public TLabel lblAccountKana;

	/** 口座名義カナ */
	public THalfAngleTextField ctrlAccountKana;

	/** 支払銀行名称 */
	public TLabelField ctrlPaymentBankName;

	/** 支払支店名称 */
	public TLabelField ctrlPaymentBranchName;

	/** 支払銀行住所 */
	public TLabelField ctrlPaymentBankAddress;

	/** 経由銀行名称 */
	public TLabelField ctrlViaBankName;

	/** 経由支店名称 */
	public TLabelField ctrlViaBranchName;

	/** 経由銀行住所 */
	public TLabelField ctrlViaBankAddress;

	/** 受取人住所 */
	public TLabelField ctrlRecipientAddress;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrldtBeginDate;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrldtEndDate;

	/**
	 * コンストラクタ
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
		ctrlCustomerReference.setLocation(x, 10);
		pnlBody.add(ctrlCustomerReference);

		// 取引先条件コード
		ctrlCustomerConditionCode.setLabelSize(100);
		ctrlCustomerConditionCode.setFieldSize(80);
		ctrlCustomerConditionCode.setLocation(x - 25, ctrlCustomerReference.getY() + ctrlCustomerReference.getHeight()
			+ 5);
		ctrlCustomerConditionCode.setLangMessageID("C00788");
		ctrlCustomerConditionCode.setMaxLength(10);
		ctrlCustomerConditionCode.setImeMode(false);
		ctrlCustomerConditionCode.setAllowedSpace(false);
		pnlBody.add(ctrlCustomerConditionCode);

		// 支払方法
		ctrlPaymentMethodReference.setLocation(x,
			ctrlCustomerConditionCode.getY() + ctrlCustomerConditionCode.getHeight() + 5);
		pnlBody.add(ctrlPaymentMethodReference);

		// 支払区分コンボボックス
		ctrlcboPaymentDataType.setSize(150, 20);
		ctrlcboPaymentDataType.setComboSize(60);
		ctrlcboPaymentDataType.setLabelSize(80);
		ctrlcboPaymentDataType.setLocation(ctrlCustomerReference.getX() + ctrlCustomerReference.getWidth() + 20,
			ctrlCustomerReference.getY());
		ctrlcboPaymentDataType.setLangMessageID("C00682");
		setPaymentDataTypeItem(ctrlcboPaymentDataType.getComboBox());
		pnlBody.add(ctrlcboPaymentDataType);

		// 定時支払条件パネル
		pnlRegularPayment.setLayout(null);
		pnlRegularPayment.setLangMessageID("C01244");
		pnlRegularPayment.setSize(260, 50);
		pnlRegularPayment.setLocation(ctrlcboPaymentDataType.getX() + 25, ctrlcboPaymentDataType.getY()
			+ ctrlcboPaymentDataType.getHeight() + 5);
		pnlBody.add(pnlRegularPayment);

		// 支払条件締日
		ctrlpaymentConditionDay.setSize(30, 20);
		ctrlpaymentConditionDay.setLocation(20, 20);
		ctrlpaymentConditionDay.setMaxLength(2);
		ctrlpaymentConditionDay.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionDay);

		// ～日締め
		closeDay.setLangMessageID("C01265");
		closeDay.setSize(40, 20);
		closeDay.setLocation(ctrlpaymentConditionDay.getX() + ctrlpaymentConditionDay.getWidth() + 2, 20);
		pnlRegularPayment.add(closeDay);

		// 支払条件締後月
		ctrlpaymentConditionMonth.setSize(30, 20);
		ctrlpaymentConditionMonth.setLocation(closeDay.getX() + closeDay.getWidth() + 5, 20);
		ctrlpaymentConditionMonth.setMaxLength(2);
		ctrlpaymentConditionMonth.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionMonth);

		// ～ヵ月後
		nextMonth.setLangMessageID("C01493");
		nextMonth.setSize(40, 20);
		nextMonth.setLocation(ctrlpaymentConditionMonth.getX() + ctrlpaymentConditionMonth.getWidth() + 2, 20);
		pnlRegularPayment.add(nextMonth);

		// 支払条件支払日
		ctrlpaymentConditionPayDay.setSize(30, 20);
		ctrlpaymentConditionPayDay.setLocation(nextMonth.getX() + nextMonth.getWidth() + 5, 20);
		ctrlpaymentConditionPayDay.setMaxLength(2);
		ctrlpaymentConditionPayDay.setPositiveOnly(true);
		pnlRegularPayment.add(ctrlpaymentConditionPayDay);

		// ～日払い
		cashDay.setLangMessageID("C00448");
		cashDay.setSize(40, 20);
		cashDay.setLocation(ctrlpaymentConditionPayDay.getX() + ctrlpaymentConditionPayDay.getWidth() + 2, 20);
		pnlRegularPayment.add(cashDay);

		// 振込情報

		// 振込情報パネル
		pnlInformation.setLayout(null);
		pnlInformation.setLangMessageID("C01184");
		pnlInformation.setSize(770, 510);
		pnlInformation.setLocation(x, ctrlPaymentMethodReference.getY() + ctrlPaymentMethodReference.getHeight() + 10);
		pnlBody.add(pnlInformation);

		// 振込振出銀行口座コード検索
		ctrlBankAccountReference.setLocation(68, 20);
		ctrlBankAccountReference.btn.setLangMessageID("C00946");
		pnlInformation.add(ctrlBankAccountReference);

		// 銀行検索
		ctrlBank.setLocation(ctrlBankAccountReference.getX(), ctrlBankAccountReference.getY()
			+ ctrlBankAccountReference.getHeight() + 5);
		pnlInformation.add(ctrlBank);

		// 支店検索
		ctrlBranch.setLocation(ctrlBank.getX(), ctrlBank.getY() + ctrlBank.getHeight() + 5);
		pnlInformation.add(ctrlBranch);

		// 送金目的名検索
		ctrlRemittanceReference.setLocation(ctrlBranch.getX(), ctrlBranch.getY() + ctrlBranch.getHeight() + 5);
		pnlInformation.add(ctrlRemittanceReference);

		// 振込手数料コンボボックス
		ctrlcboCashInFee.setComboSize(150);
		ctrlcboCashInFee.setLabelSize(140);
		ctrlcboCashInFee.setLocation(2, ctrlRemittanceReference.getY() + ctrlRemittanceReference.getHeight() + 5);
		ctrlcboCashInFee.setLangMessageID(getWord("C01340") + getWord("C01183"));
		setCashInFeeItem(ctrlcboCashInFee.getComboBox());
		pnlInformation.add(ctrlcboCashInFee);

		// 手数料支払区分コンボボックス
		ctrlcboCommissionPaymentType.setComboSize(150);
		ctrlcboCommissionPaymentType.setLabelSize(140);
		ctrlcboCommissionPaymentType.setLocation(ctrlcboCashInFee.getX(),
			ctrlcboCashInFee.getY() + ctrlcboCashInFee.getHeight() + 5);
		ctrlcboCommissionPaymentType.setLangMessageID(getWord("C01334") + getWord("C01139"));
		setCommissionPaymentTypeItem(ctrlcboCommissionPaymentType.getComboBox());
		pnlInformation.add(ctrlcboCommissionPaymentType);

		// 預金種目コンボボックス
		ctrlcboDepositKind.setComboSize(150);
		ctrlcboDepositKind.setLabelSize(140);
		ctrlcboDepositKind.setLocation(ctrlcboCommissionPaymentType.getX(), ctrlcboCommissionPaymentType.getY()
			+ ctrlcboCommissionPaymentType.getHeight() + 5);
		ctrlcboDepositKind.setLangMessageID("C01326");
		setDepositKindItem(ctrlcboDepositKind.getComboBox());
		pnlInformation.add(ctrlcboDepositKind);

		// 口座番号
		ctrlAccountNumber.setLabelSize(140);
		ctrlAccountNumber.setFieldSize(400);
		ctrlAccountNumber.setLocation(ctrlcboDepositKind.getX(),
			ctrlcboDepositKind.getY() + ctrlcboDepositKind.getHeight() + 5);
		ctrlAccountNumber.setLangMessageID("C00794");
		ctrlAccountNumber.setMaxLength(34);
		ctrlAccountNumber.setImeMode(false);
		pnlInformation.add(ctrlAccountNumber);

		// 英文銀行名
		ctrlEnglishBankName.setLabelSize(140);
		ctrlEnglishBankName.setFieldSize(400);
		ctrlEnglishBankName.setLocation(ctrlAccountNumber.getX(),
			ctrlAccountNumber.getY() + ctrlAccountNumber.getHeight() + 5);
		ctrlEnglishBankName.setLangMessageID("C00795");
		ctrlEnglishBankName.setMaxLength(35);
		ctrlEnglishBankName.setImeMode(false);
		pnlInformation.add(ctrlEnglishBankName);

		// 英文支店名
		ctrlEnglishBranchName.setLabelSize(140);
		ctrlEnglishBranchName.setFieldSize(400);
		ctrlEnglishBranchName.setLocation(ctrlEnglishBankName.getX(),
			ctrlEnglishBankName.getY() + ctrlEnglishBankName.getHeight() + 5);
		ctrlEnglishBranchName.setLangMessageID("C00796");
		ctrlEnglishBranchName.setMaxLength(35);
		ctrlEnglishBranchName.setImeMode(false);
		pnlInformation.add(ctrlEnglishBranchName);

		// 英文銀行住所
		ctrlEnglishBankAddress.setLabelSize(140);
		ctrlEnglishBankAddress.setFieldSize(400);
		ctrlEnglishBankAddress.setLocation(ctrlEnglishBranchName.getX(), ctrlEnglishBranchName.getY()
			+ ctrlEnglishBranchName.getHeight() + 5);
		ctrlEnglishBankAddress.setLangMessageID("C00797");
		ctrlEnglishBankAddress.setMaxLength(70);
		ctrlEnglishBankAddress.setImeMode(false);
		pnlInformation.add(ctrlEnglishBankAddress);

		// 口座名義カナラベル
		lblAccountKana.setLangMessageID("C02394");
		lblAccountKana.setSize(140, 20);
		lblAccountKana.setLocation(ctrlEnglishBankAddress.getX() + 15, ctrlEnglishBankAddress.getY()
			+ ctrlEnglishBankAddress.getHeight() + 5);
		pnlInformation.add(lblAccountKana);
		// 口座名義カナ
		ctrlAccountKana.setSize(400, 20);
		ctrlAccountKana.setLocation(lblAccountKana.getWidth() + 7, ctrlEnglishBankAddress.getY()
			+ ctrlEnglishBankAddress.getHeight() + 5);
		ctrlAccountKana.setMaxLength(70);
		pnlInformation.add(ctrlAccountKana);

		// 支払銀行名称
		ctrlPaymentBankName.setLabelSize(140);
		ctrlPaymentBankName.setFieldSize(400);
		ctrlPaymentBankName.setLocation(lblAccountKana.getX() - 15,
			ctrlAccountKana.getY() + ctrlAccountKana.getHeight() + 5);
		ctrlPaymentBankName.setLangMessageID("C00799");
		ctrlPaymentBankName.setMaxLength(35);
		ctrlPaymentBankName.setImeMode(false);
		pnlInformation.add(ctrlPaymentBankName);

		// 注意書き１
		caution1.setSize(200, 20);
		caution1.setLangMessageID("C01583"); // ※支払銀行は仕向銀行のコルレス先
		caution1.setLocation(ctrlPaymentBankName.getX() + ctrlPaymentBankName.getWidth() + 5,
			ctrlPaymentBankName.getY());
		pnlInformation.add(caution1);

		// 支払支店名称
		ctrlPaymentBranchName.setLabelSize(140);
		ctrlPaymentBranchName.setFieldSize(400);
		ctrlPaymentBranchName.setLocation(ctrlPaymentBankName.getX(),
			ctrlPaymentBankName.getY() + ctrlPaymentBankName.getHeight() + 5);
		ctrlPaymentBranchName.setLangMessageID("C00800");
		ctrlPaymentBranchName.setMaxLength(35);
		ctrlPaymentBranchName.setImeMode(false);
		pnlInformation.add(ctrlPaymentBranchName);

		// 支払銀行住所
		ctrlPaymentBankAddress.setLabelSize(140);
		ctrlPaymentBankAddress.setFieldSize(400);
		ctrlPaymentBankAddress.setLocation(ctrlPaymentBranchName.getX(), ctrlPaymentBranchName.getY()
			+ ctrlPaymentBranchName.getHeight() + 5);
		ctrlPaymentBankAddress.setLangMessageID("C00801");
		ctrlPaymentBankAddress.setMaxLength(70);
		ctrlPaymentBankAddress.setImeMode(false);
		pnlInformation.add(ctrlPaymentBankAddress);

		// 経由銀行名称
		ctrlViaBankName.setLabelSize(140);
		ctrlViaBankName.setFieldSize(400);
		ctrlViaBankName.setLocation(ctrlPaymentBankAddress.getX(), ctrlPaymentBankAddress.getY()
			+ ctrlPaymentBankAddress.getHeight() + 5);
		ctrlViaBankName.setLangMessageID("C00802");
		ctrlViaBankName.setMaxLength(35);
		ctrlViaBankName.setImeMode(false);
		pnlInformation.add(ctrlViaBankName);

		// 注意書き２
		caution2.setSize(210, 20);
		caution2.setLangMessageID("C01584"); // ※経由銀行は仕向銀行の非コルレス先
		caution2.setLocation(ctrlViaBankName.getX() + ctrlViaBankName.getWidth() + 5, ctrlViaBankName.getY());
		pnlInformation.add(caution2);

		// 経由支店名称
		ctrlViaBranchName.setLabelSize(140);
		ctrlViaBranchName.setFieldSize(400);
		ctrlViaBranchName.setLocation(ctrlViaBankName.getX(), ctrlViaBankName.getY() + ctrlViaBankName.getHeight() + 5);
		ctrlViaBranchName.setLangMessageID("C00803");
		ctrlViaBranchName.setMaxLength(35);
		ctrlViaBranchName.setImeMode(false);
		pnlInformation.add(ctrlViaBranchName);

		// 経由銀行住所
		ctrlViaBankAddress.setLabelSize(140);
		ctrlViaBankAddress.setFieldSize(400);
		ctrlViaBankAddress.setLocation(ctrlViaBranchName.getX(),
			ctrlViaBranchName.getY() + ctrlViaBranchName.getHeight() + 5);
		ctrlViaBankAddress.setLangMessageID("C00804");
		ctrlViaBankAddress.setMaxLength(70);
		ctrlViaBankAddress.setImeMode(false);
		pnlInformation.add(ctrlViaBankAddress);

		// 受取人住所
		ctrlRecipientAddress.setLabelSize(140);
		ctrlRecipientAddress.setFieldSize(400);
		ctrlRecipientAddress.setLocation(ctrlViaBankAddress.getX(),
			ctrlViaBankAddress.getY() + ctrlViaBankAddress.getHeight() + 5);
		ctrlRecipientAddress.setLangMessageID("C00805");
		ctrlRecipientAddress.setMaxLength(70);
		ctrlRecipientAddress.setImeMode(false);
		pnlInformation.add(ctrlRecipientAddress);

		// 開始年月日
		ctrldtBeginDate.setLabelSize(80);
		ctrldtBeginDate.setLocation(x - 20, pnlInformation.getY() + pnlInformation.getHeight() + 10);
		ctrldtBeginDate.setLangMessageID("C00055");
		pnlBody.add(ctrldtBeginDate);

		// 終了年月日
		ctrldtEndDate.setLabelSize(80);
		ctrldtEndDate.setLocation(x - 20, ctrldtBeginDate.getY() + ctrldtBeginDate.getHeight() + 10);
		ctrldtEndDate.setLangMessageID("C00261");
		pnlBody.add(ctrldtEndDate);
	}

	/**
	 * 支払区分コンボボックスの値設定
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
	 * 預金種目コンボボックスの値設定
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
	 * 振込手数料コンボボックスの値設定
	 * 
	 * @param comboBox
	 */
	protected void setCashInFeeItem(TComboBox comboBox) {
		comboBox.addTextValueItem(CashInFeeType.Our, getWord(CashInFeeType.getCashInFeeTypeName(CashInFeeType.Our)));
		comboBox
			.addTextValueItem(CashInFeeType.Other, getWord(CashInFeeType.getCashInFeeTypeName(CashInFeeType.Other)));
	}

	/**
	 * 手数料支払区分コンボボックスの値設定
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
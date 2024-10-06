package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * @author zhangzhenxing
 */
public class MG0080EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0080EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid
	 */
	MG0080EditDisplayDialog(Frame parent, boolean modal, MG0080EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(870, 620);

		super.initDialog();
	}

	/**
	 * 
	 */
	public void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpSummaryDivision = new ButtonGroup();
		btngrpDebitCreditDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		pnlSummaryDivision = new TPanel();
		rdoInput = new TRadioButton();
		rdoCaption = new TRadioButton();
		rdoSummary = new TRadioButton();
		pnlDebitCreditDivision = new TPanel();
		rdoDebit = new TRadioButton();
		rdoCredit = new TRadioButton();
		ctrlItemType = new TLabelComboBox();
		ctrlSubDivision = new TLabelComboBox();
		pnlDetail = new TPanel();
		pnlDetail1 = new TPanel();
		dtEndDate = new TLabelPopupCalendar();
		dtBeginDate = new TLabelPopupCalendar();
		ctrlItemCode = new TLabelField();
		ctrlItemName = new TLabelField();
		ctrlItemAbbreviationName = new TLabelField();
		ctrlItemNameForSearch = createForSearchCtrl();
		ctrlFixedDepartment = new TButtonField();
		ctrlConsumptionTax = new TButtonField();
		ctrlDebitFund = new TButtonField();
		ctrlCreditFund = new TButtonField();
		ctrlGlItemControlDivision = new TLabelComboBox();
		ctrlApItemControlDivision = new TLabelComboBox();
		ctrlArItemControlDivision = new TLabelComboBox();
		ctrlBgItemControlDivision = new TLabelComboBox();
		ctrlCustomerInputFlag = new TLabelComboBox();
		ctrlOffsettingItemControlDivision = new TLabelComboBox();
		ctrlRevaluationObjectFlag = new TLabelComboBox();
		ctrlBsAccountErasingDivision = new TLabelComboBox();
		pnlDetail2 = new TPanel();
		chkReceivingSlipInputFlag = new TCheckBox();
		chkPaymentSlipInputFlag = new TCheckBox();
		chkTransferSlipInputFlag = new TCheckBox();
		chkExpenseInputFlag = new TCheckBox();
		chkAccountsPayableAppropriateSlipInputFlag = new TCheckBox();
		chkAccountsReceivableAppropriateSlipInputFlag = new TCheckBox();
		chkAccountsReceivableErasingSlipInputFlag = new TCheckBox();
		chkAssetsAppropriateSlipInputFlag = new TCheckBox();
		chkPaymentRequestSlipInputFlag = new TCheckBox();
		chkMultiCurrencyInputFlag = new TCheckBox();
		chkEmployeeInputFlag = new TCheckBox();
		chkManagement1InputFlag = new TCheckBox();
		chkManagement2InputFlag = new TCheckBox();
		chkManagement3InputFlag = new TCheckBox();
		chkManagement4InputFlag = new TCheckBox();
		chkManagement5InputFlag = new TCheckBox();
		chkHasFlg = new TCheckBox();
		chkNotAccounting1InputFlag = new TCheckBox();
		chkNotAccounting2InputFlag = new TCheckBox();
		chkNotAccounting3InputFlag = new TCheckBox();
		chkSalesTaxInputFlag = new TCheckBox();
		chkPurchesesTaxInputFlag = new TCheckBox();
		chkManagement6InputFlag = new TCheckBox();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(870, 47));
		pnlButton.setMinimumSize(new Dimension(870, 47));
		pnlButton.setPreferredSize(new Dimension(870, 47));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(47);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(4, 500, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(48);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(4, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(630, 58));
		pnlHeader.setMinimumSize(new Dimension(630, 58));
		pnlHeader.setPreferredSize(new Dimension(630, 58));
		pnlSummaryDivision.setLayout(new GridBagLayout());

		pnlSummaryDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlSummaryDivision.setLangMessageID("C01148");
		pnlSummaryDivision.setMaximumSize(new Dimension(200, 50));
		pnlSummaryDivision.setMinimumSize(new Dimension(200, 50));
		pnlSummaryDivision.setPreferredSize(new Dimension(200, 50));
		rdoInput.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSummaryDivision.add(rdoInput);
		rdoInput.setTabControlNo(1);
		rdoInput.setLangMessageID("C01275");
		rdoInput.setMargin(new Insets(0, 0, 0, 0));
		rdoInput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.changeInput();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlSummaryDivision.add(rdoInput, gridBagConstraints);

		rdoSummary.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSummaryDivision.add(rdoSummary);
		rdoSummary.setLangMessageID("C00255");
		rdoSummary.setTabControlNo(1);
		rdoSummary.setMargin(new Insets(0, 0, 0, 0));
		rdoSummary.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.changeInput();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlSummaryDivision.add(rdoSummary, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlHeader.add(pnlSummaryDivision, gridBagConstraints);
		rdoCaption.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSummaryDivision.add(rdoCaption);
		rdoCaption.setTabControlNo(1);
		rdoCaption.setLangMessageID("C00506");
		rdoCaption.setMargin(new Insets(0, 0, 0, 0));
		rdoCaption.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.changeInput();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlSummaryDivision.add(rdoCaption, gridBagConstraints);

		pnlDebitCreditDivision.setLayout(new GridBagLayout());

		pnlDebitCreditDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlDebitCreditDivision.setLangMessageID("C01226");
		pnlDebitCreditDivision.setMaximumSize(new Dimension(120, 50));
		pnlDebitCreditDivision.setMinimumSize(new Dimension(120, 50));
		pnlDebitCreditDivision.setPreferredSize(new Dimension(120, 50));
		rdoDebit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDebitCreditDivision.add(rdoDebit);
		rdoDebit.setSelected(true);
		rdoDebit.setLangMessageID("C00080");
		rdoDebit.setMargin(new Insets(0, 0, 0, 0));
		rdoDebit.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlDebitCreditDivision.add(rdoDebit, gridBagConstraints);

		rdoCredit.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDebitCreditDivision.add(rdoCredit);
		rdoCredit.setLangMessageID("C00068");
		rdoCredit.setMargin(new Insets(0, 0, 0, 0));
		rdoCredit.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlDebitCreditDivision.add(rdoCredit, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(5, 15, 5, 0);
		pnlHeader.add(pnlDebitCreditDivision, gridBagConstraints);

		ctrlItemType.setLabelSize(55);
		ctrlItemType.setLangMessageID("C01007");
		ctrlItemType.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlHeader.add(ctrlItemType, gridBagConstraints);

		ctrlSubDivision.setLabelSize(55);
		ctrlSubDivision.setLangMessageID("C01314");
		ctrlSubDivision.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 15, 0, 0);
		pnlHeader.add(ctrlSubDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(870, 480));
		pnlDetail.setMinimumSize(new Dimension(870, 480));
		pnlDetail.setPreferredSize(new Dimension(870, 480));
		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(625, 480));
		pnlDetail1.setMinimumSize(new Dimension(625, 480));
		pnlDetail1.setPreferredSize(new Dimension(625, 480));
		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(125);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(46);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(dtEndDate, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(125);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(45);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 17;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(dtBeginDate, gridBagConstraints);

		ctrlItemCode.setFieldSize(120);
		ctrlItemCode.setLangMessageID("C00572");
		ctrlItemCode.getField().setAllowedSpace(false);
		ctrlItemCode.setMaxLength(10);
		ctrlItemCode.setTabControlNo(5);
		ctrlItemCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlItemCode, gridBagConstraints);

		ctrlItemName.setFieldSize(450);
		ctrlItemName.setLangMessageID("C00700");
		ctrlItemName.setMaxLength(40);
		ctrlItemName.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlItemName, gridBagConstraints);

		ctrlItemAbbreviationName.setFieldSize(230);
		ctrlItemAbbreviationName.setLangMessageID("C00730");
		ctrlItemAbbreviationName.setMaxLength(20);
		ctrlItemAbbreviationName.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlItemAbbreviationName, gridBagConstraints);

		ctrlItemNameForSearch.setFieldSize(450);
		ctrlItemNameForSearch.setLangMessageID("C00731");
		ctrlItemNameForSearch.setMaxLength(40);
		ctrlItemNameForSearch.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlItemNameForSearch, gridBagConstraints);

		ctrlFixedDepartment.setButtonSize(85);
		ctrlFixedDepartment.setFieldSize(120);
		ctrlFixedDepartment.setLangMessageID("C00732");
		ctrlFixedDepartment.setMaxLength(10);
		ctrlFixedDepartment.setNoticeSize(370);
		ctrlFixedDepartment.setTabControlNo(9);
		ctrlFixedDepartment.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail1.add(ctrlFixedDepartment, gridBagConstraints);

		ctrlConsumptionTax.setButtonSize(85);
		ctrlConsumptionTax.setFieldSize(120);
		ctrlConsumptionTax.setLangMessageID("C01171");
		ctrlConsumptionTax.setMaxLength(2);
		ctrlConsumptionTax.setNoticeSize(370);
		ctrlConsumptionTax.setTabControlNo(10);
		ctrlConsumptionTax.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail1.add(ctrlConsumptionTax, gridBagConstraints);

		ctrlDebitFund.setButtonSize(85);
		ctrlDebitFund.setFieldSize(120);
		ctrlDebitFund.setLangMessageID("C00734");
		ctrlDebitFund.setMaxLength(10);
		ctrlDebitFund.setNoticeSize(370);
		ctrlDebitFund.setTabControlNo(11);
		ctrlDebitFund.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail1.add(ctrlDebitFund, gridBagConstraints);

		ctrlCreditFund.setButtonSize(85);
		ctrlCreditFund.setFieldSize(120);
		ctrlCreditFund.setLangMessageID("C00735");
		ctrlCreditFund.setMaxLength(10);
		ctrlCreditFund.setNoticeSize(370);
		ctrlCreditFund.setTabControlNo(12);
		ctrlCreditFund.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlDetail1.add(ctrlCreditFund, gridBagConstraints);

		ctrlGlItemControlDivision.setLabelSize(125);
		ctrlGlItemControlDivision.setLangMessageID("C00968");
		ctrlGlItemControlDivision.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlGlItemControlDivision, gridBagConstraints);

		ctrlApItemControlDivision.setLabelSize(125);
		ctrlApItemControlDivision.setLangMessageID("C00959");
		ctrlApItemControlDivision.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlApItemControlDivision, gridBagConstraints);

		ctrlArItemControlDivision.setLabelSize(125);
		ctrlArItemControlDivision.setLangMessageID("C00960");
		ctrlArItemControlDivision.setTabControlNo(15);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlArItemControlDivision, gridBagConstraints);

		ctrlBgItemControlDivision.setLabelSize(125);
		ctrlBgItemControlDivision.setLangMessageID("C00962");
		ctrlBgItemControlDivision.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 11;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlBgItemControlDivision, gridBagConstraints);

		ctrlCustomerInputFlag.setLabelSize(125);
		ctrlCustomerInputFlag.setLangMessageID("C01134");
		ctrlCustomerInputFlag.setTabControlNo(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlCustomerInputFlag, gridBagConstraints);

		ctrlOffsettingItemControlDivision.setLabelSize(125);
		ctrlOffsettingItemControlDivision.setLangMessageID("C01217");
		ctrlOffsettingItemControlDivision.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 14;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlOffsettingItemControlDivision, gridBagConstraints);

		ctrlBsAccountErasingDivision.setLabelSize(125);
		ctrlBsAccountErasingDivision.setLangMessageID("C02078");
		ctrlBsAccountErasingDivision.setTabControlNo(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlBsAccountErasingDivision, gridBagConstraints);

		ctrlRevaluationObjectFlag.setLabelSize(125);
		ctrlRevaluationObjectFlag.setLangMessageID("C01301");
		ctrlRevaluationObjectFlag.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 16;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlRevaluationObjectFlag, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 30, 0);
		pnlDetail.add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(220, 480));
		pnlDetail2.setMinimumSize(new Dimension(220, 480));
		pnlDetail2.setPreferredSize(new Dimension(220, 480));
		chkReceivingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setLangMessageID("C01272");
		chkReceivingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 10);
		pnlDetail2.add(chkReceivingSlipInputFlag, gridBagConstraints);

		chkPaymentSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setLangMessageID("C01155");
		chkPaymentSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkPaymentSlipInputFlag, gridBagConstraints);

		chkTransferSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkTransferSlipInputFlag.setLangMessageID("C01188");
		chkTransferSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkTransferSlipInputFlag.setTabControlNo(24);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkTransferSlipInputFlag, gridBagConstraints);

		chkExpenseInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkExpenseInputFlag.setLangMessageID("C01049");
		chkExpenseInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkExpenseInputFlag.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkExpenseInputFlag, gridBagConstraints);

		chkAccountsPayableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setLangMessageID("C01083");
		chkAccountsPayableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkAccountsPayableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setLangMessageID("C01079");
		chkAccountsReceivableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setTabControlNo(27);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkAccountsReceivableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableErasingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setLangMessageID("C01081");
		chkAccountsReceivableErasingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkAccountsReceivableErasingSlipInputFlag, gridBagConstraints);

		chkAssetsAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setLangMessageID("C01102");
		chkAssetsAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setTabControlNo(29);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkAssetsAppropriateSlipInputFlag, gridBagConstraints);

		chkPaymentRequestSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setLangMessageID("C01094");
		chkPaymentRequestSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setTabControlNo(30);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkPaymentRequestSlipInputFlag, gridBagConstraints);

		chkMultiCurrencyInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setLangMessageID("C01223");
		chkMultiCurrencyInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setTabControlNo(31);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkMultiCurrencyInputFlag, gridBagConstraints);

		chkEmployeeInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkEmployeeInputFlag.setLangMessageID("C01120");
		chkEmployeeInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkEmployeeInputFlag.setTabControlNo(32);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkEmployeeInputFlag, gridBagConstraints);

		chkManagement1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement1InputFlag.setLangMessageID("C01026");
		chkManagement1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement1InputFlag.setTabControlNo(33);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 11;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkManagement1InputFlag, gridBagConstraints);

		chkManagement2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement2InputFlag.setLangMessageID("C01028");
		chkManagement2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement2InputFlag.setTabControlNo(34);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkManagement2InputFlag, gridBagConstraints);

		chkManagement3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement3InputFlag.setLangMessageID("C01030");
		chkManagement3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement3InputFlag.setTabControlNo(35);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 13;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkManagement3InputFlag, gridBagConstraints);

		chkManagement4InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement4InputFlag.setLangMessageID("C01032");
		chkManagement4InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement4InputFlag.setTabControlNo(36);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 14;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkManagement4InputFlag, gridBagConstraints);

		chkManagement5InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement5InputFlag.setLangMessageID("C01034");
		chkManagement5InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement5InputFlag.setTabControlNo(37);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkManagement5InputFlag, gridBagConstraints);

		chkHasFlg.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkHasFlg.setLangMessageID("C01284");
		chkHasFlg.setTabControlNo(39);
		chkHasFlg.setMargin(new Insets(0, 0, 0, 0));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 17;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkHasFlg, gridBagConstraints);

		chkNotAccounting1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setLangMessageID("C01288");
		chkNotAccounting1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setTabControlNo(40);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 18;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkNotAccounting1InputFlag, gridBagConstraints);

		chkNotAccounting2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting2InputFlag.setLangMessageID("C01289");
		chkNotAccounting2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting2InputFlag.setTabControlNo(41);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 19;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkNotAccounting2InputFlag, gridBagConstraints);

		chkNotAccounting3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setLangMessageID("C01290");
		chkNotAccounting3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setTabControlNo(42);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 20;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkNotAccounting3InputFlag, gridBagConstraints);

		chkSalesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSalesTaxInputFlag.setLangMessageID("C01282");
		chkSalesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkSalesTaxInputFlag.setTabControlNo(43);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 21;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkSalesTaxInputFlag, gridBagConstraints);

		chkPurchesesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setLangMessageID("C01088");
		chkPurchesesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setTabControlNo(44);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 22;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 10);
		pnlDetail2.add(chkPurchesesTaxInputFlag, gridBagConstraints);

		chkManagement6InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement6InputFlag.setLangMessageID("C01036");
		chkManagement6InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement6InputFlag.setTabControlNo(38);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 16;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 0);
		pnlDetail2.add(chkManagement6InputFlag, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 10, 10);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(pnlDetail2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtBeginDate.setValue(dtBeginDate.getCalendar().getMinimumDate());

		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtEndDate.setValue(dtEndDate.getCalendar().getMaximumDate());

		pack();
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	TButton btnReturn;

	TImageButton btnSettle;

	ButtonGroup btngrpDebitCreditDivision;

	ButtonGroup btngrpSummaryDivision;

	TCheckBox chkAccountsPayableAppropriateSlipInputFlag;

	TCheckBox chkAccountsReceivableAppropriateSlipInputFlag;

	TCheckBox chkAccountsReceivableErasingSlipInputFlag;

	TCheckBox chkAssetsAppropriateSlipInputFlag;

	TCheckBox chkEmployeeInputFlag;

	TCheckBox chkExpenseInputFlag;

	TCheckBox chkManagement1InputFlag;

	TCheckBox chkManagement2InputFlag;

	TCheckBox chkManagement3InputFlag;

	TCheckBox chkManagement4InputFlag;

	TCheckBox chkManagement5InputFlag;

	TCheckBox chkManagement6InputFlag;

	TCheckBox chkMultiCurrencyInputFlag;

	TCheckBox chkHasFlg;

	TCheckBox chkNotAccounting1InputFlag;

	TCheckBox chkNotAccounting2InputFlag;

	TCheckBox chkNotAccounting3InputFlag;

	TCheckBox chkPaymentSlipInputFlag;

	TCheckBox chkPurchesesTaxInputFlag;

	TCheckBox chkReceivingSlipInputFlag;

	TCheckBox chkSalesTaxInputFlag;

	TCheckBox chkTransferSlipInputFlag;

	TCheckBox chkPaymentRequestSlipInputFlag;

	TLabelComboBox ctrlApItemControlDivision;

	TLabelComboBox ctrlArItemControlDivision;

	TLabelComboBox ctrlBgItemControlDivision;

	TButtonField ctrlConsumptionTax;

	TButtonField ctrlCreditFund;

	TLabelComboBox ctrlCustomerInputFlag;

	TButtonField ctrlDebitFund;

	TButtonField ctrlFixedDepartment;

	TLabelComboBox ctrlGlItemControlDivision;

	TLabelField ctrlItemAbbreviationName;

	TLabelField ctrlItemCode;

	TLabelField ctrlItemName;

	TLabelField ctrlItemNameForSearch;

	TLabelComboBox ctrlItemType;

	TLabelComboBox ctrlOffsettingItemControlDivision;

	TLabelComboBox ctrlRevaluationObjectFlag;

	TLabelComboBox ctrlBsAccountErasingDivision;

	TLabelComboBox ctrlSubDivision;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDebitCreditDivision;

	TPanel pnlDetail;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlHeader;

	TPanel pnlSummaryDivision;

	TRadioButton rdoCaption;

	TRadioButton rdoCredit;

	TRadioButton rdoDebit;

	TRadioButton rdoInput;

	TRadioButton rdoSummary;

}

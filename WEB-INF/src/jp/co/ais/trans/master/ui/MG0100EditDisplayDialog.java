package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

import jp.co.ais.trans.common.bizui.*;

/**
 * 内訳科目マスタ編集画面
 * 
 * @author zhangzhenxing
 */
public class MG0100EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0100EditDisplayDialogCtrl ctrl;

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
	MG0100EditDisplayDialog(Frame parent, boolean modal, MG0100EditDisplayDialogCtrl ctrl, String titleid) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleid);
		setSize(820, 500);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlItem = new TItemField();
		ctrlBreakDownItemCode = new TLabelField();
		ctrlBreakDownItemName = new TLabelField();
		ctrlBreakDownItemAbbreviationName = new TLabelField();
		ctrlBreakDownItemNameForSearch = createForSearchCtrl();
		ctrlConsumptionTax = new TButtonField();
		ctrlSubItem = new TSubItemField();
		ctrlCustomerInputFlag = new TLabelComboBox();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		pnlDetail = new TPanel();
		chkReceivingSlipInputFlag = new TCheckBox();
		chkPaymentSlipInputFlag = new TCheckBox();
		chkTransferSlipInputFlag = new TCheckBox();
		chkExpenseInputFlag = new TCheckBox();
		chkAccountsPayableAppropriateSlipInputFlag = new TCheckBox();
		chkAccountsReceivableAppropriateSlipInputFlag = new TCheckBox();
		chkAccountsReceivableErasingSlipInputFlag = new TCheckBox();
		chkAssetsAppropriateSlipInputFlag = new TCheckBox();
		chkPaymentRequestSlipInputFlag = new TCheckBox();
		chkOccurrenceDateInputFlag = new TCheckBox();
		chkEmployeeInputFlag = new TCheckBox();
		chkManagement1InputFlag = new TCheckBox();
		chkManagement2InputFlag = new TCheckBox();
		chkManagement3InputFlag = new TCheckBox();
		chkManagement4InputFlag = new TCheckBox();
		chkManagement5InputFlag = new TCheckBox();
		chkManagement6InputFlag = new TCheckBox();
		chkNotAccounting1InputFlag = new TCheckBox();
		chkNotAccounting2InputFlag = new TCheckBox();
		chkNotAccounting3InputFlag = new TCheckBox();
		chkSalesTaxInputFlag = new TCheckBox();
		chkPurchesesTaxInputFlag = new TCheckBox();
		chkMultiCurrencyInputFlag = new TCheckBox();
		ctrlRevaluationObjectFlag = new TCheckBox();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(35);
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = true;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 530, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(36);
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
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
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 180));
		pnlHeader.setMinimumSize(new Dimension(800, 180));
		pnlHeader.setPreferredSize(new Dimension(800, 180));
		ctrlItem.setButtonSize(75);
		ctrlItem.setFieldSize(120);
		ctrlItem.setLangMessageID("C01006");
		ctrlItem.setMaxLength(10);
		ctrlItem.setNoticeSize(300);
		ctrlItem.setTabControlNo(1);
		ctrlItem.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlItem, gridBagConstraints);

		ctrlBreakDownItemCode.setFieldSize(120);
		ctrlBreakDownItemCode.setLabelSize(80);
		ctrlBreakDownItemCode.setLangMessageID("C00876");
		ctrlBreakDownItemCode.getField().setAllowedSpace(false);
		ctrlBreakDownItemCode.setMaxLength(10);
		ctrlBreakDownItemCode.setTabControlNo(3);
		ctrlBreakDownItemCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlBreakDownItemCode, gridBagConstraints);

		ctrlBreakDownItemName.setFieldSize(450);
		ctrlBreakDownItemName.setLabelSize(80);
		ctrlBreakDownItemName.setLangMessageID("C00877");
		ctrlBreakDownItemName.setMaxLength(40);
		ctrlBreakDownItemName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlBreakDownItemName, gridBagConstraints);

		ctrlBreakDownItemAbbreviationName.setFieldSize(230);
		ctrlBreakDownItemAbbreviationName.setLabelSize(80);
		ctrlBreakDownItemAbbreviationName.setLangMessageID("C00878");
		ctrlBreakDownItemAbbreviationName.setMaxLength(20);
		ctrlBreakDownItemAbbreviationName.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlBreakDownItemAbbreviationName, gridBagConstraints);

		ctrlBreakDownItemNameForSearch.setFieldSize(450);
		ctrlBreakDownItemNameForSearch.setLabelSize(80);
		ctrlBreakDownItemNameForSearch.setLangMessageID("C00879");
		ctrlBreakDownItemNameForSearch.setMaxLength(40);
		ctrlBreakDownItemNameForSearch.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlBreakDownItemNameForSearch, gridBagConstraints);

		ctrlConsumptionTax.setButtonSize(75);
		ctrlConsumptionTax.setFieldSize(35);
		ctrlConsumptionTax.setLangMessageID("C01172");
		ctrlConsumptionTax.setMaxLength(2);
		ctrlConsumptionTax.setNoticeSize(450);
		ctrlConsumptionTax.setTabControlNo(7);
		ctrlConsumptionTax.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlConsumptionTax, gridBagConstraints);

		ctrlSubItem.setButtonSize(75);
		ctrlSubItem.setFieldSize(120);
		ctrlSubItem.setLangMessageID("C01494");
		ctrlSubItem.setMaxLength(10);
		ctrlSubItem.setNoticeSize(300);
		ctrlSubItem.setTabControlNo(2);
		ctrlSubItem.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlSubItem, gridBagConstraints);

		ctrlCustomerInputFlag.setLabelHAlignment(3);
		ctrlCustomerInputFlag.setLabelSize(105);
		ctrlCustomerInputFlag.setLangMessageID("C01134");
		ctrlCustomerInputFlag.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlCustomerInputFlag, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(95);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(33);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlHeader.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(95);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(34);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlHeader.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(800, 170));
		pnlDetail.setMinimumSize(new Dimension(800, 170));
		pnlDetail.setPreferredSize(new Dimension(800, 170));
		chkReceivingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setLangMessageID("C01272");
		chkReceivingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		pnlDetail.add(chkReceivingSlipInputFlag, gridBagConstraints);

		chkPaymentSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setLangMessageID("C01155");
		chkPaymentSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(chkPaymentSlipInputFlag, gridBagConstraints);

		chkTransferSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkTransferSlipInputFlag.setLangMessageID("C01188");
		chkTransferSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkTransferSlipInputFlag.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(chkTransferSlipInputFlag, gridBagConstraints);

		chkExpenseInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkExpenseInputFlag.setLangMessageID("C01049");
		chkExpenseInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkExpenseInputFlag.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(chkExpenseInputFlag, gridBagConstraints);

		chkAccountsPayableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setLangMessageID("C01083");
		chkAccountsPayableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(chkAccountsPayableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setLangMessageID("C01079");
		chkAccountsReceivableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(chkAccountsReceivableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableErasingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setLangMessageID("C01081");
		chkAccountsReceivableErasingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setTabControlNo(15);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 0);
		pnlDetail.add(chkAccountsReceivableErasingSlipInputFlag, gridBagConstraints);

		chkAssetsAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setLangMessageID("C01102");
		chkAssetsAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 40, 0, 0);
		pnlDetail.add(chkAssetsAppropriateSlipInputFlag, gridBagConstraints);

		chkPaymentRequestSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setLangMessageID("C01094");
		chkPaymentRequestSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setTabControlNo(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkPaymentRequestSlipInputFlag, gridBagConstraints);

		chkOccurrenceDateInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkOccurrenceDateInputFlag.setLangMessageID("C01284");
		chkOccurrenceDateInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkOccurrenceDateInputFlag.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkOccurrenceDateInputFlag, gridBagConstraints);

		chkEmployeeInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkEmployeeInputFlag.setLangMessageID("C01120");
		chkEmployeeInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkEmployeeInputFlag.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 40, 0, 0);
		pnlDetail.add(chkEmployeeInputFlag, gridBagConstraints);

		chkManagement1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement1InputFlag.setLangMessageID("C01026");
		chkManagement1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement1InputFlag.setTabControlNo(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkManagement1InputFlag, gridBagConstraints);

		chkManagement2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement2InputFlag.setLangMessageID("C01028");
		chkManagement2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement2InputFlag.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkManagement2InputFlag, gridBagConstraints);

		chkManagement3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement3InputFlag.setLangMessageID("C01030");
		chkManagement3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement3InputFlag.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkManagement3InputFlag, gridBagConstraints);

		chkManagement4InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement4InputFlag.setLangMessageID("C01032");
		chkManagement4InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement4InputFlag.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkManagement4InputFlag, gridBagConstraints);

		chkManagement5InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement5InputFlag.setLangMessageID("C01034");
		chkManagement5InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement5InputFlag.setTabControlNo(24);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkManagement5InputFlag, gridBagConstraints);

		chkManagement6InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement6InputFlag.setLangMessageID("C01036");
		chkManagement6InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement6InputFlag.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 30, 0);
		pnlDetail.add(chkManagement6InputFlag, gridBagConstraints);

		chkNotAccounting1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setLangMessageID("C01288");
		chkNotAccounting1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 40, 0, 0);
		pnlDetail.add(chkNotAccounting1InputFlag, gridBagConstraints);

		chkNotAccounting2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting2InputFlag.setLangMessageID("C01289");
		chkNotAccounting2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting2InputFlag.setTabControlNo(27);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkNotAccounting2InputFlag, gridBagConstraints);

		chkNotAccounting3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setLangMessageID("C01290");
		chkNotAccounting3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkNotAccounting3InputFlag, gridBagConstraints);

		chkSalesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSalesTaxInputFlag.setLangMessageID("C01282");
		chkSalesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkSalesTaxInputFlag.setTabControlNo(29);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkSalesTaxInputFlag, gridBagConstraints);

		chkPurchesesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setLangMessageID("C01088");
		chkPurchesesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setTabControlNo(30);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkPurchesesTaxInputFlag, gridBagConstraints);

		chkMultiCurrencyInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setLangMessageID("C01223");
		chkMultiCurrencyInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setTabControlNo(31);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 0, 0);
		pnlDetail.add(chkMultiCurrencyInputFlag, gridBagConstraints);

		ctrlRevaluationObjectFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		ctrlRevaluationObjectFlag.setLangMessageID("C01301");
		ctrlRevaluationObjectFlag.setMargin(new Insets(0, 0, 0, 0));
		ctrlRevaluationObjectFlag.setTabControlNo(32);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 40, 30, 0);
		pnlDetail.add(ctrlRevaluationObjectFlag, gridBagConstraints);

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

		// 「科目」ボタンを押下
		ctrlItem.addCallControl(new CallBackListener() {

			@Override
			public void before() {
				ctrl.setItemData();
			}

			@Override
			public void after() {
				ctrlSubItem.getField().setText("");
				ctrlSubItem.getField().setEditableEnabled(ctrlItem.getOutputParameter().isIncludeSubItem());
				ctrlSubItem.getNotice().setText("");
				ctrlSubItem.getButton().setEnabled(ctrlItem.getOutputParameter().isIncludeSubItem());
			}
		});

		// 「補助科目」ボタンを押下
		ctrlSubItem.addCallControl(new CallBackListener() {

			@Override
			public void before() {
				ctrl.setSubItemData();
			}
		});

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

	TCheckBox chkNotAccounting1InputFlag;

	TCheckBox chkNotAccounting2InputFlag;

	TCheckBox chkNotAccounting3InputFlag;

	TCheckBox chkOccurrenceDateInputFlag;

	TCheckBox chkPaymentSlipInputFlag;

	TCheckBox chkPurchesesTaxInputFlag;

	TCheckBox chkReceivingSlipInputFlag;

	TCheckBox chkSalesTaxInputFlag;

	TCheckBox chkTransferSlipInputFlag;

	TCheckBox chkPaymentRequestSlipInputFlag;

	TLabelField ctrlBreakDownItemAbbreviationName;

	TLabelField ctrlBreakDownItemCode;

	TLabelField ctrlBreakDownItemName;

	TLabelField ctrlBreakDownItemNameForSearch;

	TButtonField ctrlConsumptionTax;

	TLabelComboBox ctrlCustomerInputFlag;

	TItemField ctrlItem;

	TCheckBox ctrlRevaluationObjectFlag;

	TSubItemField ctrlSubItem;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlHeader;

}

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
 * @author zhangzhenxing
 */
public class MG0090EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0090EditDisplayDialogCtrl ctrl;

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
	MG0090EditDisplayDialog(Frame parent, boolean modal, MG0090EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(750, 450);
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpBreakDownDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlItem = new TItemField();
		ctrlSubItemCode = new TLabelField();
		ctrlSubItemName = new TLabelField();
		ctrlSubItemAbbreviatedName = new TLabelField();
		ctrlSubItemNameForSearch = createForSearchCtrl();
		ctrlConsumptionTax = new TButtonField();
		pnlBreakDownDivision = new TPanel();
		rdoNothing = new TRadioButton();
		rdoBeing = new TRadioButton();
		pnlDetail1 = new TPanel();
		ctrlCustomerInputFlag = new TLabelComboBox();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
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

		pnlButton.setMaximumSize(new Dimension(700, 40));
		pnlButton.setMinimumSize(new Dimension(700, 40));
		pnlButton.setPreferredSize(new Dimension(700, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(36);
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
		gridBagConstraints.insets = new Insets(10, 450, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(37);
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

		pnlHeader.setMaximumSize(new Dimension(740, 155));
		pnlHeader.setMinimumSize(new Dimension(740, 155));
		pnlHeader.setPreferredSize(new Dimension(740, 155));
		ctrlItem.setButtonSize(75);
		ctrlItem.setFieldSize(120);
		ctrlItem.setLangMessageID("C01509");
		ctrlItem.setMaxLength(10);
		ctrlItem.setNoticeSize(410);
		ctrlItem.setTabControlNo(1);
		ctrlItem.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlHeader.add(ctrlItem, gridBagConstraints);

		ctrlSubItemCode.setLabelSize(80);
		ctrlSubItemCode.setFieldSize(120);
		ctrlSubItemCode.setLangMessageID("C00890");
		ctrlSubItemCode.getField().setAllowedSpace(false);
		ctrlSubItemCode.setMaxLength(10);
		ctrlSubItemCode.setTabControlNo(2);
		ctrlSubItemCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlSubItemCode, gridBagConstraints);

		ctrlSubItemName.setLabelSize(80);
		ctrlSubItemName.setFieldSize(450);
		ctrlSubItemName.setLangMessageID("C00934");
		ctrlSubItemName.setMaxLength(40);
		ctrlSubItemName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlSubItemName, gridBagConstraints);

		ctrlSubItemAbbreviatedName.setLabelSize(80);
		ctrlSubItemAbbreviatedName.setFieldSize(230);
		ctrlSubItemAbbreviatedName.setLangMessageID("C00933");
		ctrlSubItemAbbreviatedName.setMaxLength(20);
		ctrlSubItemAbbreviatedName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlSubItemAbbreviatedName, gridBagConstraints);

		ctrlSubItemNameForSearch.setLabelSize(80);
		ctrlSubItemNameForSearch.setFieldSize(450);
		ctrlSubItemNameForSearch.setLangMessageID("C00935");
		ctrlSubItemNameForSearch.setMaxLength(40);
		ctrlSubItemNameForSearch.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlSubItemNameForSearch, gridBagConstraints);

		ctrlConsumptionTax.setButtonSize(75);
		ctrlConsumptionTax.setFieldSize(35);
		ctrlConsumptionTax.setLangMessageID("C01171");
		ctrlConsumptionTax.setMaxLength(2);
		ctrlConsumptionTax.setNoticeSize(450);
		ctrlConsumptionTax.setTabControlNo(6);
		ctrlConsumptionTax.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 5, 0);
		pnlHeader.add(ctrlConsumptionTax, gridBagConstraints);

		pnlBreakDownDivision.setLayout(new GridBagLayout());

		pnlBreakDownDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u5185\u8a33\u533a\u5206"));
		pnlBreakDownDivision.setLangMessageID("C01264");
		pnlBreakDownDivision.setMaximumSize(new Dimension(170, 50));
		pnlBreakDownDivision.setMinimumSize(new Dimension(170, 50));
		pnlBreakDownDivision.setPreferredSize(new Dimension(170, 50));
		rdoNothing.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpBreakDownDivision.add(rdoNothing);
		rdoNothing.setSelected(true);
		rdoNothing.setLangMessageID("C00412");
		rdoNothing.setMargin(new Insets(0, 0, 0, 0));
		rdoNothing.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlBreakDownDivision.add(rdoNothing, gridBagConstraints);

		rdoBeing.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpBreakDownDivision.add(rdoBeing);
		rdoBeing.setLangMessageID("C00006");
		rdoBeing.setMargin(new Insets(0, 0, 0, 0));
		rdoBeing.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 30, 5, 0);
		pnlBreakDownDivision.add(rdoBeing, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlHeader.add(pnlBreakDownDivision, gridBagConstraints);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());
		pnlDetail1.setMaximumSize(new Dimension(700, 30));
		pnlDetail1.setMinimumSize(new Dimension(700, 30));
		pnlDetail1.setPreferredSize(new Dimension(700, 30));
		ctrlCustomerInputFlag.setLabelSize(110);
		ctrlCustomerInputFlag.setLangMessageID("C01134");
		ctrlCustomerInputFlag.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlDetail1.add(ctrlCustomerInputFlag, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(65);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(9);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlDetail1.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(65);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(10);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 20, 0, 5);
		pnlDetail1.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());
		pnlDetail2.setMaximumSize(new Dimension(700, 170));
		pnlDetail2.setMinimumSize(new Dimension(700, 170));
		pnlDetail2.setPreferredSize(new Dimension(700, 170));
		chkReceivingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setLangMessageID("C01272");
		chkReceivingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkReceivingSlipInputFlag.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		pnlDetail2.add(chkReceivingSlipInputFlag, gridBagConstraints);

		chkPaymentSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setLangMessageID("C01155");
		chkPaymentSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentSlipInputFlag.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail2.add(chkPaymentSlipInputFlag, gridBagConstraints);

		chkTransferSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkTransferSlipInputFlag.setLangMessageID("C01188");
		chkTransferSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkTransferSlipInputFlag.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail2.add(chkTransferSlipInputFlag, gridBagConstraints);

		chkExpenseInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkExpenseInputFlag.setLangMessageID("C01049");
		chkExpenseInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkExpenseInputFlag.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail2.add(chkExpenseInputFlag, gridBagConstraints);

		chkAccountsPayableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setLangMessageID("C01083");
		chkAccountsPayableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsPayableAppropriateSlipInputFlag.setTabControlNo(15);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail2.add(chkAccountsPayableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setLangMessageID("C01079");
		chkAccountsReceivableAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableAppropriateSlipInputFlag.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail2.add(chkAccountsReceivableAppropriateSlipInputFlag, gridBagConstraints);

		chkAccountsReceivableErasingSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setLangMessageID("C01081");
		chkAccountsReceivableErasingSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableErasingSlipInputFlag.setTabControlNo(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 0);
		pnlDetail2.add(chkAccountsReceivableErasingSlipInputFlag, gridBagConstraints);

		chkAssetsAppropriateSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setLangMessageID("C01102");
		chkAssetsAppropriateSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkAssetsAppropriateSlipInputFlag.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 30, 0, 0);
		pnlDetail2.add(chkAssetsAppropriateSlipInputFlag, gridBagConstraints);

		chkPaymentRequestSlipInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setLangMessageID("C01094");
		chkPaymentRequestSlipInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPaymentRequestSlipInputFlag.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkPaymentRequestSlipInputFlag, gridBagConstraints);

		chkOccurrenceDateInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkOccurrenceDateInputFlag.setLangMessageID("C01284");
		chkOccurrenceDateInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkOccurrenceDateInputFlag.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkOccurrenceDateInputFlag, gridBagConstraints);

		chkEmployeeInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkEmployeeInputFlag.setLangMessageID("C01120");
		chkEmployeeInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkEmployeeInputFlag.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 30, 0, 0);
		pnlDetail2.add(chkEmployeeInputFlag, gridBagConstraints);

		chkManagement1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement1InputFlag.setLangMessageID("C01026");
		chkManagement1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement1InputFlag.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkManagement1InputFlag, gridBagConstraints);

		chkManagement2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement2InputFlag.setLangMessageID("C01028");
		chkManagement2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement2InputFlag.setTabControlNo(24);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkManagement2InputFlag, gridBagConstraints);

		chkManagement3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement3InputFlag.setLangMessageID("C01030");
		chkManagement3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement3InputFlag.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkManagement3InputFlag, gridBagConstraints);

		chkManagement4InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement4InputFlag.setLangMessageID("C01032");
		chkManagement4InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement4InputFlag.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkManagement4InputFlag, gridBagConstraints);

		chkManagement5InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement5InputFlag.setLangMessageID("C01034");
		chkManagement5InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement5InputFlag.setTabControlNo(27);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkManagement5InputFlag, gridBagConstraints);

		chkManagement6InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagement6InputFlag.setLangMessageID("C01036");
		chkManagement6InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkManagement6InputFlag.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 30, 0);
		pnlDetail2.add(chkManagement6InputFlag, gridBagConstraints);

		chkNotAccounting1InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setLangMessageID("C01288");
		chkNotAccounting1InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting1InputFlag.setTabControlNo(29);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 30, 0, 0);
		pnlDetail2.add(chkNotAccounting1InputFlag, gridBagConstraints);

		chkNotAccounting2InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkNotAccounting2InputFlag.setLangMessageID("C01289");
		chkNotAccounting2InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting2InputFlag.setTabControlNo(30);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkNotAccounting2InputFlag, gridBagConstraints);

		chkNotAccounting3InputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setLangMessageID("C01290");
		chkNotAccounting3InputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkNotAccounting3InputFlag.setTabControlNo(31);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkNotAccounting3InputFlag, gridBagConstraints);

		chkSalesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSalesTaxInputFlag.setLangMessageID("C01282");
		chkSalesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkSalesTaxInputFlag.setTabControlNo(32);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkSalesTaxInputFlag, gridBagConstraints);

		chkPurchesesTaxInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setLangMessageID("C01088");
		chkPurchesesTaxInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkPurchesesTaxInputFlag.setTabControlNo(33);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkPurchesesTaxInputFlag, gridBagConstraints);

		chkMultiCurrencyInputFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setLangMessageID("C01223");
		chkMultiCurrencyInputFlag.setMargin(new Insets(0, 0, 0, 0));
		chkMultiCurrencyInputFlag.setTabControlNo(34);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 0, 0);
		pnlDetail2.add(chkMultiCurrencyInputFlag, gridBagConstraints);

		ctrlRevaluationObjectFlag.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		ctrlRevaluationObjectFlag.setLangMessageID("C01301");
		ctrlRevaluationObjectFlag.setMargin(new Insets(0, 0, 0, 0));
		ctrlRevaluationObjectFlag.setTabControlNo(35);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 30, 30, 0);
		pnlDetail2.add(ctrlRevaluationObjectFlag, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail2, gridBagConstraints);
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

	/** */
	public TButton btnReturn;

	/** */
	public TImageButton btnSettle;

	/** */
	public ButtonGroup btngrpBreakDownDivision;

	/** */
	public TCheckBox chkAccountsPayableAppropriateSlipInputFlag;

	/** */
	public TCheckBox chkAccountsReceivableAppropriateSlipInputFlag;

	/** */
	public TCheckBox chkAccountsReceivableErasingSlipInputFlag;

	/** */
	public TCheckBox chkAssetsAppropriateSlipInputFlag;

	/** */
	public TCheckBox chkEmployeeInputFlag;

	/** */
	public TCheckBox chkExpenseInputFlag;

	/** */
	public TCheckBox chkManagement1InputFlag;

	/** */
	public TCheckBox chkManagement2InputFlag;

	/** */
	public TCheckBox chkManagement3InputFlag;

	/** */
	public TCheckBox chkManagement4InputFlag;

	/** */
	public TCheckBox chkManagement5InputFlag;

	/** */
	public TCheckBox chkManagement6InputFlag;

	/** */
	public TCheckBox chkMultiCurrencyInputFlag;

	/** */
	public TCheckBox chkNotAccounting1InputFlag;

	/** */
	public TCheckBox chkNotAccounting2InputFlag;

	/** */
	public TCheckBox chkNotAccounting3InputFlag;

	/** */
	public TCheckBox chkOccurrenceDateInputFlag;

	/** */
	public TCheckBox chkPaymentRequestSlipInputFlag;

	/** */
	public TCheckBox chkPaymentSlipInputFlag;

	/** */
	public TCheckBox chkPurchesesTaxInputFlag;

	/** */
	public TCheckBox chkReceivingSlipInputFlag;

	/** */
	public TCheckBox chkSalesTaxInputFlag;

	/** */
	public TCheckBox chkTransferSlipInputFlag;

	/** */
	public TButtonField ctrlConsumptionTax;

	/** */
	public TLabelComboBox ctrlCustomerInputFlag;

	/** */
	public TItemField ctrlItem;

	/** */
	public TCheckBox ctrlRevaluationObjectFlag;

	/** */
	public TLabelField ctrlSubItemAbbreviatedName;

	/** */
	public TLabelField ctrlSubItemCode;

	/** */
	public TLabelField ctrlSubItemName;

	/** */
	public TLabelField ctrlSubItemNameForSearch;

	/** */
	public TLabelPopupCalendar dtBeginDate;

	/** */
	public TLabelPopupCalendar dtEndDate;

	/** */
	public TPanel pnlBreakDownDivision;

	/** */
	public TPanel pnlButton;

	/** */
	public TPanel pnlDetail1;

	/** */
	public TPanel pnlDetail2;

	/** */
	public TPanel pnlHeader;

	/** */
	public TRadioButton rdoBeing;

	/** */
	public TRadioButton rdoNothing;

}

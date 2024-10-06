package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans.common.bizui.*;

/**
 * @author yanwei
 */

public class MP0050PaymentPolicyMasterPanel extends TPanelBusiness {

	/**  */
	private static final long serialVersionUID = 2224353615443663376L;

	/** コントロールクラス */
	private MP0050PaymentPolicyMasterPanelCtrl ctrl;

	boolean isSettle = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MP0050PaymentPolicyMasterPanel(MP0050PaymentPolicyMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();
	}

	protected void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		btngrpDate1 = new javax.swing.ButtonGroup();
		btngrpDate5 = new javax.swing.ButtonGroup();
		btngrpDate10 = new javax.swing.ButtonGroup();
		btngrpDate15 = new javax.swing.ButtonGroup();
		btngrpDate20 = new javax.swing.ButtonGroup();
		btngrpDate25 = new javax.swing.ButtonGroup();
		btngrpLastDay = new javax.swing.ButtonGroup();
		pnlBusiness = new TPanel();
		pnlButton = new TMainHeaderPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnCancellation = new TButton();
		pnlHeader = new TPanel();
		lblOnTimePaymentBankHolidayDivision = new TLabel();
		pnlDetail1 = new TPanel();
		pnlDate1 = new TPanel();
		chkDate1 = new TCheckBox();
		rdoDate1DayBefore = new TRadioButton();
		rdoDate1DayAfter = new TRadioButton();
		pnlDate5 = new TPanel();
		chkDate5 = new TCheckBox();
		rdoDate5DayBefore = new TRadioButton();
		rdoDate5DayAfter = new TRadioButton();
		pnlDate10 = new TPanel();
		chkDate10 = new TCheckBox();
		rdoDate10DayBefore = new TRadioButton();
		rdoDate10DayAfter = new TRadioButton();
		pnlDate15 = new TPanel();
		chkDate15 = new TCheckBox();
		rdoDate15DayBefore = new TRadioButton();
		rdoDate15DayAfter = new TRadioButton();
		pnlDate20 = new TPanel();
		chkDate20 = new TCheckBox();
		rdoDate20DayBefore = new TRadioButton();
		rdoDate20DayAfter = new TRadioButton();
		pnlDate25 = new TPanel();
		chkDate25 = new TCheckBox();
		rdoDate25DayBefore = new TRadioButton();
		rdoDate25DayAfter = new TRadioButton();
		pnlLastDay = new TPanel();
		chkDateLast = new TCheckBox();
		rdoDateLastDayBefore = new TRadioButton();
		rdoDateLastDayAfter = new TRadioButton();
		pnlDetail2 = new TPanel();
		pnlTransferCommission = new TPanel();
		pnlRemittanceCommissionLowerValue = new TPanel();
		numRemittanceCommissionLowerValue = new TLabelNumericField();
		lblYen = new TLabel();
		pnlDetail100 = new TPanel();
		ctrlCommissionConsumptionTaxCode = new TButtonField();
		ctrlAppropriateDepartment = new TButtonField();
		pnlDetail3 = new TPanel();
		numPaymentLowerValue = new TLabelNumericField();
		lbllblYen1 = new TLabel();
		chkAbroadRemittanceMakeFlag = new TCheckBox();
		chkInvoiceNumberInputFlag = new TCheckBox();
		ctrlItemUnit = new TAccountItemUnit();
		pnlFooter = new TPanel();

		setLayout(new java.awt.GridBagLayout());

		setMaximumSize(new java.awt.Dimension(800, 600));
		setMinimumSize(new java.awt.Dimension(800, 600));
		setPreferredSize(new java.awt.Dimension(800, 600));

		pnlBusiness.setLayout(new java.awt.GridBagLayout());

		pnlBusiness.setMaximumSize(new java.awt.Dimension(800, 600));
		pnlBusiness.setMinimumSize(new java.awt.Dimension(800, 600));
		pnlBusiness.setPreferredSize(new java.awt.Dimension(800, 600));
		pnlButton.setLayout(new java.awt.GridBagLayout());

		pnlButton.setMaximumSize(new java.awt.Dimension(800, 40));
		pnlButton.setMinimumSize(new java.awt.Dimension(800, 40));
		pnlButton.setPreferredSize(new java.awt.Dimension(800, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setTabControlNo(31);
		btnSettle.setMaximumSize(new java.awt.Dimension(110, 25));
		btnSettle.setMinimumSize(new java.awt.Dimension(110, 25));
		btnSettle.setPreferredSize(new java.awt.Dimension(110, 25));
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 450, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnCancellation.setLangMessageID("C00405");
		btnCancellation.setShortcutKey(KeyEvent.VK_F12);
		btnCancellation.setMaximumSize(new java.awt.Dimension(110, 25));
		btnCancellation.setMinimumSize(new java.awt.Dimension(110, 25));
		btnCancellation.setPreferredSize(new java.awt.Dimension(110, 25));
		btnCancellation.setTabControlNo(32);
		btnCancellation.setForClose(true);
		btnCancellation.addActionListener(new ActionListener() {

			/**
			 * 戻りボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = false;
				ctrl.disposeDialogCancel();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
		pnlButton.add(btnCancellation, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		pnlBusiness.add(pnlButton, gridBagConstraints);

		// 境界線
		JSeparator sep = new JSeparator();
		TGuiUtil.setComponentSize(sep, 0, 3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		pnlBusiness.add(sep, gridBagConstraints);

		pnlHeader.setLayout(new java.awt.GridBagLayout());

		pnlHeader.setMaximumSize(new java.awt.Dimension(680, 20));
		pnlHeader.setMinimumSize(new java.awt.Dimension(680, 20));
		pnlHeader.setPreferredSize(new java.awt.Dimension(680, 20));

		lblOnTimePaymentBankHolidayDivision.setLangMessageID("C01495");
		lblOnTimePaymentBankHolidayDivision.setMaximumSize(new java.awt.Dimension(136, 15));
		lblOnTimePaymentBankHolidayDivision.setMinimumSize(new java.awt.Dimension(136, 15));
		lblOnTimePaymentBankHolidayDivision.setPreferredSize(new java.awt.Dimension(136, 15));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 520);
		pnlHeader.add(lblOnTimePaymentBankHolidayDivision, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 60, 0, 0);
		pnlBusiness.add(pnlHeader, gridBagConstraints);

		pnlDetail1.setLayout(new java.awt.GridBagLayout());

		pnlDetail1.setMaximumSize(new java.awt.Dimension(680, 80));
		pnlDetail1.setMinimumSize(new java.awt.Dimension(680, 80));
		pnlDetail1.setPreferredSize(new java.awt.Dimension(680, 80));

		pnlDate1.setLayout(new java.awt.GridBagLayout());
		pnlDate1.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate1.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate1.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate1.setPreferredSize(new java.awt.Dimension(90, 80));
		chkDate1.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkDate1.setLangMessageID("C00955");
		chkDate1.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate1.setTabControlNo(1);
		chkDate1.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet1();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate1.add(chkDate1, gridBagConstraints);

		rdoDate1DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate1.add(rdoDate1DayBefore);
		rdoDate1DayBefore.setSelected(true);

		rdoDate1DayBefore.setLangMessageID("C00322");
		rdoDate1DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate1DayBefore.setTabControlNo(2);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate1.add(rdoDate1DayBefore, gridBagConstraints);

		rdoDate1DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate1.add(rdoDate1DayAfter);

		rdoDate1DayAfter.setLangMessageID("C01327");
		rdoDate1DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate1DayAfter.setTabControlNo(3);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate1.add(rdoDate1DayAfter, gridBagConstraints);

		pnlDetail1.add(pnlDate1, new java.awt.GridBagConstraints());

		pnlDate5.setLayout(new java.awt.GridBagLayout());
		pnlDate5.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate5.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate5.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate5.setPreferredSize(new java.awt.Dimension(90, 80));
		chkDate5.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkDate5.setLangMessageID("C00958");
		chkDate5.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate5.setTabControlNo(4);
		chkDate5.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet5();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate5.add(chkDate5, gridBagConstraints);

		rdoDate5DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate5.add(rdoDate5DayBefore);
		rdoDate5DayBefore.setSelected(true);

		rdoDate5DayBefore.setLangMessageID("C00322");
		rdoDate5DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate5DayBefore.setTabControlNo(5);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate5.add(rdoDate5DayBefore, gridBagConstraints);

		rdoDate5DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate5.add(rdoDate5DayAfter);

		rdoDate5DayAfter.setLangMessageID("C01327");
		rdoDate5DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate5DayAfter.setTabControlNo(6);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate5.add(rdoDate5DayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlDate5, gridBagConstraints);

		pnlDate10.setLayout(new java.awt.GridBagLayout());
		pnlDate10.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate10.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate10.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate10.setPreferredSize(new java.awt.Dimension(90, 80));
		chkDate10.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkDate10.setLangMessageID("C00953");
		chkDate10.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate10.setTabControlNo(7);
		chkDate10.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet10();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate10.add(chkDate10, gridBagConstraints);

		rdoDate10DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate10.add(rdoDate10DayBefore);
		rdoDate10DayBefore.setSelected(true);

		rdoDate10DayBefore.setLangMessageID("C00322");
		rdoDate10DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate10DayBefore.setTabControlNo(8);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate10.add(rdoDate10DayBefore, gridBagConstraints);

		rdoDate10DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate10.add(rdoDate10DayAfter);

		rdoDate10DayAfter.setLangMessageID("C01327");
		rdoDate10DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate10DayAfter.setTabControlNo(9);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate10.add(rdoDate10DayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlDate10, gridBagConstraints);

		pnlDate15.setLayout(new java.awt.GridBagLayout());
		pnlDate15.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate15.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate15.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate15.setPreferredSize(new java.awt.Dimension(90, 80));
		chkDate15.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkDate15.setLangMessageID("C00954");
		chkDate15.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate15.setTabControlNo(10);
		chkDate15.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet15();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate15.add(chkDate15, gridBagConstraints);

		rdoDate15DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate15.add(rdoDate15DayBefore);
		rdoDate15DayBefore.setSelected(true);

		rdoDate15DayBefore.setLangMessageID("C00322");
		rdoDate15DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate15DayBefore.setTabControlNo(11);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate15.add(rdoDate15DayBefore, gridBagConstraints);

		rdoDate15DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate15.add(rdoDate15DayAfter);

		rdoDate15DayAfter.setLangMessageID("C01327");
		rdoDate15DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate15DayAfter.setTabControlNo(12);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate15.add(rdoDate15DayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlDate15, gridBagConstraints);

		pnlDate20.setLayout(new java.awt.GridBagLayout());
		pnlDate20.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate20.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate20.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate20.setPreferredSize(new java.awt.Dimension(90, 80));
		chkDate20.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));

		chkDate20.setLangMessageID("C00956");
		chkDate20.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate20.setTabControlNo(13);
		chkDate20.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet20();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate20.add(chkDate20, gridBagConstraints);

		rdoDate20DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate20.add(rdoDate20DayBefore);
		rdoDate20DayBefore.setSelected(true);

		rdoDate20DayBefore.setLangMessageID("C00322");
		rdoDate20DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate20DayBefore.setTabControlNo(14);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate20.add(rdoDate20DayBefore, gridBagConstraints);

		rdoDate20DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate20.add(rdoDate20DayAfter);

		rdoDate20DayAfter.setLangMessageID("C01327");
		rdoDate20DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate20DayAfter.setTabControlNo(15);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate20.add(rdoDate20DayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlDate20, gridBagConstraints);

		pnlDate25.setLayout(new java.awt.GridBagLayout());
		pnlDate25.setBorder(BorderFactory.createTitledBorder(""));
		pnlDate25.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlDate25.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlDate25.setPreferredSize(new java.awt.Dimension(90, 80));

		chkDate25.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkDate25.setLangMessageID("C00957");
		chkDate25.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDate25.setTabControlNo(16);
		chkDate25.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSet25();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlDate25.add(chkDate25, gridBagConstraints);

		rdoDate25DayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate25.add(rdoDate25DayBefore);
		rdoDate25DayBefore.setSelected(true);
		rdoDate25DayBefore.setLangMessageID("C00322");
		rdoDate25DayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate25DayBefore.setTabControlNo(17);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate25.add(rdoDate25DayBefore, gridBagConstraints);

		rdoDate25DayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDate25.add(rdoDate25DayAfter);
		rdoDate25DayAfter.setLangMessageID("C01327");
		rdoDate25DayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDate25DayAfter.setTabControlNo(18);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlDate25.add(rdoDate25DayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlDate25, gridBagConstraints);

		pnlLastDay.setLayout(new java.awt.GridBagLayout());
		pnlLastDay.setBorder(BorderFactory.createTitledBorder(""));
		pnlLastDay.setMaximumSize(new java.awt.Dimension(80, 80));
		pnlLastDay.setMinimumSize(new java.awt.Dimension(80, 80));
		pnlLastDay.setPreferredSize(new java.awt.Dimension(90, 80));

		chkDateLast.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkDateLast.setLangMessageID("C00309");
		chkDateLast.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkDateLast.setTabControlNo(19);
		chkDateLast.addActionListener(new java.awt.event.ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent e) {
				ctrl.selectedSetLast();
			}
		});
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		pnlLastDay.add(chkDateLast, gridBagConstraints);

		rdoDateLastDayBefore.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpLastDay.add(rdoDateLastDayBefore);
		rdoDateLastDayBefore.setSelected(true);
		rdoDateLastDayBefore.setLangMessageID("C00322");
		rdoDateLastDayBefore.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDateLastDayBefore.setTabControlNo(20);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlLastDay.add(rdoDateLastDayBefore, gridBagConstraints);

		rdoDateLastDayAfter.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpLastDay.add(rdoDateLastDayAfter);
		rdoDateLastDayAfter.setLangMessageID("C01327");
		rdoDateLastDayAfter.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoDateLastDayAfter.setTabControlNo(21);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlLastDay.add(rdoDateLastDayAfter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlDetail1.add(pnlLastDay, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 60, 0, 0);
		pnlBusiness.add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new java.awt.GridBagLayout());

		pnlDetail2.setMaximumSize(new java.awt.Dimension(665, 190));
		pnlDetail2.setMinimumSize(new java.awt.Dimension(665, 190));
		pnlDetail2.setPreferredSize(new java.awt.Dimension(665, 190));
		pnlTransferCommission.setLayout(new java.awt.GridBagLayout());

		pnlTransferCommission.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory
			.createEtchedBorder()));
		pnlTransferCommission.setLangMessageID("C01183");
		pnlTransferCommission.setMaximumSize(new java.awt.Dimension(590, 180));
		pnlTransferCommission.setMinimumSize(new java.awt.Dimension(590, 180));
		pnlTransferCommission.setPreferredSize(new java.awt.Dimension(590, 180));
		pnlRemittanceCommissionLowerValue.setLayout(new java.awt.GridBagLayout());

		pnlRemittanceCommissionLowerValue.setMaximumSize(new java.awt.Dimension(450, 25));
		pnlRemittanceCommissionLowerValue.setMinimumSize(new java.awt.Dimension(450, 25));
		pnlRemittanceCommissionLowerValue.setPreferredSize(new java.awt.Dimension(450, 25));
		numRemittanceCommissionLowerValue.setFieldHAlignment(2);
		numRemittanceCommissionLowerValue.setFieldSize(135);
		numRemittanceCommissionLowerValue.setLabelSize(110);
		numRemittanceCommissionLowerValue.setLangMessageID("C00891");
		numRemittanceCommissionLowerValue.setMaxLength(13);
		numRemittanceCommissionLowerValue.setNumericFormat("#,###,###,###,###");
		numRemittanceCommissionLowerValue.setTabControlNo(22);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 170, 0, 0);
		pnlRemittanceCommissionLowerValue.add(numRemittanceCommissionLowerValue, gridBagConstraints);

		lblYen.setToolTipText("");
		lblYen.setLangMessageID("C00036");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
		pnlRemittanceCommissionLowerValue.add(lblYen, gridBagConstraints);

		pnlTransferCommission.add(pnlRemittanceCommissionLowerValue, new java.awt.GridBagConstraints());

		pnlDetail100.setLayout(new java.awt.GridBagLayout());

		pnlDetail100.setMaximumSize(new java.awt.Dimension(530, 130));
		pnlDetail100.setMinimumSize(new java.awt.Dimension(530, 130));
		pnlDetail100.setPreferredSize(new java.awt.Dimension(530, 130));

		setItemSize();
		ctrlItemUnit.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		pnlDetail100.add(ctrlItemUnit, gridBagConstraints);

		ctrlCommissionConsumptionTaxCode.setButtonSize(140);
		ctrlCommissionConsumptionTaxCode.setFieldSize(35);
		ctrlCommissionConsumptionTaxCode.setLangMessageID("C00889");
		ctrlCommissionConsumptionTaxCode.setMaxLength(2);
		ctrlCommissionConsumptionTaxCode.setNoticeSize(280);
		ctrlCommissionConsumptionTaxCode.setTabControlNo(27);
		ctrlCommissionConsumptionTaxCode.setImeMode(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 15, 0, 0);
		pnlDetail100.add(ctrlCommissionConsumptionTaxCode, gridBagConstraints);

		ctrlAppropriateDepartment.setButtonSize(85);
		ctrlAppropriateDepartment.setFieldSize(120);
		ctrlAppropriateDepartment.setLangMessageID("C00863");
		ctrlAppropriateDepartment.setMaxLength(10);
		ctrlAppropriateDepartment.setMaximumSize(new java.awt.Dimension(420, 20));
		ctrlAppropriateDepartment.setMinimumSize(new java.awt.Dimension(420, 20));
		ctrlAppropriateDepartment.setNoticeSize(280);
		ctrlAppropriateDepartment.setTabControlNo(26);
		ctrlAppropriateDepartment.setImeMode(false);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
		pnlDetail100.add(ctrlAppropriateDepartment, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 10, 0);
		pnlTransferCommission.add(pnlDetail100, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 174);
		pnlDetail2.add(pnlTransferCommission, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 68, 0, 0);
		pnlBusiness.add(pnlDetail2, gridBagConstraints);

		pnlDetail3.setLayout(new java.awt.GridBagLayout());

		pnlDetail3.setMaximumSize(new java.awt.Dimension(700, 25));
		pnlDetail3.setMinimumSize(new java.awt.Dimension(700, 25));
		pnlDetail3.setPreferredSize(new java.awt.Dimension(700, 25));
		numPaymentLowerValue.setFieldHAlignment(2);
		numPaymentLowerValue.setFieldSize(135);
		numPaymentLowerValue.setLabelSize(80);
		numPaymentLowerValue.setLangMessageID("C00888");
		numPaymentLowerValue.setMaxLength(13);
		numPaymentLowerValue.setNumericFormat("#,###,###,###,###");
		numPaymentLowerValue.setTabControlNo(28);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail3.add(numPaymentLowerValue, gridBagConstraints);

		lbllblYen1.setToolTipText("");
		lbllblYen1.setLangMessageID("C00036");
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 40);
		pnlDetail3.add(lbllblYen1, gridBagConstraints);

		chkAbroadRemittanceMakeFlag.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAbroadRemittanceMakeFlag.setLangMessageID("C01017");
		chkAbroadRemittanceMakeFlag.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkAbroadRemittanceMakeFlag.setTabControlNo(29);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 0);
		pnlDetail3.add(chkAbroadRemittanceMakeFlag, gridBagConstraints);

		chkInvoiceNumberInputFlag.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkInvoiceNumberInputFlag.setLangMessageID("C01204");
		chkInvoiceNumberInputFlag.setMargin(new java.awt.Insets(0, 0, 0, 0));
		chkInvoiceNumberInputFlag.setTabControlNo(30);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new java.awt.Insets(0, 50, 0, 0);
		pnlDetail3.add(chkInvoiceNumberInputFlag, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 40, 0, 0);
		pnlBusiness.add(pnlDetail3, gridBagConstraints);

		pnlFooter.setLayout(new java.awt.GridBagLayout());
		pnlFooter.setMaximumSize(new java.awt.Dimension(800, 235));
		pnlFooter.setMinimumSize(new java.awt.Dimension(800, 235));
		pnlFooter.setPreferredSize(new java.awt.Dimension(800, 235));

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlBusiness.add(pnlFooter, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlBusiness, gridBagConstraints);

	}

	/**
	 * 
	 */
	public void setItemSize() {
		this.setMaximumSize(new Dimension(520, 80));
		this.setMinimumSize(new Dimension(520, 80));
		this.setPreferredSize(new Dimension(520, 80));

		GridBagConstraints gridBagConstraints;

		ctrlItemUnit.getTBasePanel().setLayout(new GridBagLayout());

		ctrlItemUnit.getTBasePanel().setMaximumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setMinimumSize(new Dimension(520, 80));
		ctrlItemUnit.getTBasePanel().setPreferredSize(new Dimension(520, 80));
		ctrlItemUnit.getTItemField().setButtonSize(85);
		ctrlItemUnit.getTItemField().setFieldSize(120);
		ctrlItemUnit.getTItemField().setNoticeSize(280);
		ctrlItemUnit.getInputParameter().setSummaryDivision("0");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTItemField(), gridBagConstraints);

		ctrlItemUnit.getTSubItemField().setButtonSize(85);
		ctrlItemUnit.getTSubItemField().setFieldSize(120);
		ctrlItemUnit.getTSubItemField().setNoticeSize(280);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTSubItemField(), gridBagConstraints);

		ctrlItemUnit.getTBreakDownItemField().setButtonSize(85);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(120);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(280);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTBreakDownItemField(), gridBagConstraints);
	}

	TButton btnCancellation;

	TImageButton btnSettle;

	javax.swing.ButtonGroup btngrpDate1;

	javax.swing.ButtonGroup btngrpDate10;

	javax.swing.ButtonGroup btngrpDate15;

	javax.swing.ButtonGroup btngrpDate20;

	javax.swing.ButtonGroup btngrpDate25;

	javax.swing.ButtonGroup btngrpDate5;

	javax.swing.ButtonGroup btngrpLastDay;

	TCheckBox chkAbroadRemittanceMakeFlag;

	TCheckBox chkDate1;

	TCheckBox chkDate10;

	TCheckBox chkDate15;

	TCheckBox chkDate20;

	TCheckBox chkDate25;

	TCheckBox chkDate5;

	TCheckBox chkDateLast;

	TCheckBox chkInvoiceNumberInputFlag;

	TButtonField ctrlAppropriateDepartment;

	TButtonField ctrlCommissionConsumptionTaxCode;

	TLabel lblOnTimePaymentBankHolidayDivision;

	TLabel lblYen;

	TLabel lbllblYen1;

	TLabelNumericField numPaymentLowerValue;

	TLabelNumericField numRemittanceCommissionLowerValue;

	TMainHeaderPanel pnlButton;

	TPanel pnlDate1;

	TPanel pnlDate10;

	TPanel pnlDate15;

	TPanel pnlDate20;

	TPanel pnlDate25;

	TPanel pnlDate5;

	TPanel pnlDetail1;

	TPanel pnlDetail100;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TPanel pnlHeader;

	TPanel pnlLastDay;

	TPanel pnlRemittanceCommissionLowerValue;

	TPanel pnlTransferCommission;

	TRadioButton rdoDate10DayAfter;

	TRadioButton rdoDate10DayBefore;

	TRadioButton rdoDate15DayAfter;

	TRadioButton rdoDate15DayBefore;

	TRadioButton rdoDate1DayAfter;

	TRadioButton rdoDate1DayBefore;

	TRadioButton rdoDate20DayAfter;

	TRadioButton rdoDate20DayBefore;

	TRadioButton rdoDate25DayAfter;

	TRadioButton rdoDate25DayBefore;

	TRadioButton rdoDate5DayAfter;

	TRadioButton rdoDate5DayBefore;

	TRadioButton rdoDateLastDayAfter;

	TRadioButton rdoDateLastDayBefore;

	TPanel pnlBusiness;

	TAccountItemUnit ctrlItemUnit;

	TPanel pnlFooter;
}

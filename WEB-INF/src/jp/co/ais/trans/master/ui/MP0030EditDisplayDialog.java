package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

import jp.co.ais.trans.common.bizui.*;

/**
 * @author yanwei
 */

public class MP0030EditDisplayDialog extends jp.co.ais.trans.common.gui.TDialog {

	/**
	 * UID
	 */
	private static final long serialVersionUID = -8667803039760307814L;

	/** コントロールクラス */
	private MP0030EditDisplayDialogCtrl ctrl;

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
	MP0030EditDisplayDialog(Frame parent, boolean modal, MP0030EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		setSize(800, 600);
		super.initDialog();
	}

	protected void initComponents() {
		java.awt.GridBagConstraints gridBagConstraints;

		pnlBankAccount = new TPanel();
		btngrpEmployeeFBDivision = new javax.swing.ButtonGroup();
		btngrpExternalFBDivision = new javax.swing.ButtonGroup();
		pnlButton = new jp.co.ais.trans.common.gui.TPanel();
		btnSettle = new jp.co.ais.trans2.common.gui.TImageButton(IconType.SETTLE);
		btnReturn = new jp.co.ais.trans.common.gui.TButton();
		pnlDetail1 = new jp.co.ais.trans.common.gui.TPanel();
		ctrlBankAccount = new jp.co.ais.trans.common.gui.TLabelField();
		txtBankAccountName = new jp.co.ais.trans.common.gui.TTextField();
		ctrlCurrency = new jp.co.ais.trans.common.gui.TButtonField();
		ctrlBranch = new jp.co.ais.trans.common.gui.TButtonField();
		ctrlBank = new jp.co.ais.trans.common.gui.TButtonField();
		pnlDetail2 = new jp.co.ais.trans.common.gui.TPanel();
		ctrlTransferRequesterKanaName = new jp.co.ais.trans.common.gui.TLabelHarfSizeCharConvertField();
		ctrlRemittanceRequesterKanjiName = new jp.co.ais.trans.common.gui.TLabelField();
		ctrlRemittanceRequesterEnglishName = new jp.co.ais.trans.common.gui.TLabelField();
		ctrlAccountNumber = new jp.co.ais.trans.common.gui.TLabelField();
		ctrlTransferRequesterCode = new jp.co.ais.trans.common.gui.TLabelField();
		ctrlDepositType = new jp.co.ais.trans.common.gui.TLabelComboBox();
		pnlDetail3 = new jp.co.ais.trans.common.gui.TPanel();
		pnlEmployeeFBDivision = new jp.co.ais.trans.common.gui.TPanel();
		rdoEmployeeFBDivisionException = new jp.co.ais.trans.common.gui.TRadioButton();
		rdoEmployeeFBUse = new jp.co.ais.trans.common.gui.TRadioButton();
		pnlExternalFBDivision = new jp.co.ais.trans.common.gui.TPanel();
		rdoExternalFBException = new jp.co.ais.trans.common.gui.TRadioButton();
		rdoExternalFBUse = new jp.co.ais.trans.common.gui.TRadioButton();
		pnlDetail4 = new jp.co.ais.trans.common.gui.TPanel();
		ctrlAppropriateDepartment = new jp.co.ais.trans.common.gui.TButtonField();
		dtBeginDate = new jp.co.ais.trans.common.gui.TLabelPopupCalendar();
		dtEndDate = new jp.co.ais.trans.common.gui.TLabelPopupCalendar();
		ctrlItemUnit = new jp.co.ais.trans.common.bizui.TAccountItemUnit();

		getContentPane().setLayout(new java.awt.GridBagLayout());

		pnlButton.setLayout(new java.awt.GridBagLayout());
		pnlButton.setMaximumSize(new java.awt.Dimension(550, 40));
		pnlButton.setMinimumSize(new java.awt.Dimension(550, 40));
		pnlButton.setPreferredSize(new java.awt.Dimension(550, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new java.awt.Dimension(110, 25));
		btnSettle.setMinimumSize(new java.awt.Dimension(110, 25));
		btnSettle.setPreferredSize(new java.awt.Dimension(110, 25));
		btnSettle.setTabControlNo(22);
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
		gridBagConstraints.insets = new java.awt.Insets(10, 328, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new java.awt.Dimension(110, 25));
		btnReturn.setMinimumSize(new java.awt.Dimension(110, 25));
		btnReturn.setPreferredSize(new java.awt.Dimension(110, 25));
		btnReturn.setTabControlNo(23);
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

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 10, 5, 10);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(10, 20, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new java.awt.GridBagLayout());

		pnlDetail1.setMaximumSize(new java.awt.Dimension(700, 110));
		pnlDetail1.setMinimumSize(new java.awt.Dimension(700, 110));
		pnlDetail1.setPreferredSize(new java.awt.Dimension(700, 110));

		ctrlBankAccount.setLabelSize(90);
		ctrlBankAccount.setFieldSize(120);
		ctrlBankAccount.setLangMessageID("C01879");
		ctrlBankAccount.getField().setAllowedSpace(false);
		ctrlBankAccount.setMaxLength(10);
		ctrlBankAccount.setTabControlNo(1);
		ctrlBankAccount.setImeMode(false);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
		pnlBankAccount.add(ctrlBankAccount, gridBagConstraints);

		txtBankAccountName.setMaximumSize(new java.awt.Dimension(380, 20));
		txtBankAccountName.setMinimumSize(new java.awt.Dimension(380, 20));
		txtBankAccountName.setPreferredSize(new java.awt.Dimension(380, 20));
		txtBankAccountName.setMaxLength(40);
		txtBankAccountName.setTabControlNo(2);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 300, 0, 0);
		pnlBankAccount.add(txtBankAccountName, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 10, 0, 0);

		pnlDetail1.add(pnlBankAccount, gridBagConstraints);

		ctrlCurrency.setButtonSize(85);
		ctrlCurrency.setFieldSize(45);
		ctrlCurrency.setLangMessageID("C01241");
		ctrlCurrency.setMaxLength(3);
		ctrlCurrency.setNoticeSize(410);
		ctrlCurrency.setTabControlNo(3);
		ctrlCurrency.setImeMode(false);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 0);
		pnlDetail1.add(ctrlCurrency, gridBagConstraints);

		ctrlBranch.setButtonSize(85);
		ctrlBranch.setFieldSize(45);
		ctrlBranch.setLangMessageID("C01093");
		ctrlBranch.setMaxLength(3);
		ctrlBranch.setNoticeSize(410);
		ctrlBranch.setTabControlNo(5);
		ctrlBranch.setImeMode(false);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 20, 5, 0);
		pnlDetail1.add(ctrlBranch, gridBagConstraints);

		ctrlBank.setButtonSize(85);
		ctrlBank.setFieldSize(55);
		ctrlBank.setLangMessageID("C01043");
		ctrlBank.setMaxLength(4);
		ctrlBank.setNoticeSize(410);
		ctrlBank.setTabControlNo(4);
		ctrlBank.setImeMode(false);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 20, 0, 0);
		pnlDetail1.add(ctrlBank, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(5, 35, 0, 100);

		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new java.awt.GridBagLayout());

		pnlDetail2.setMaximumSize(new java.awt.Dimension(700, 150));
		pnlDetail2.setMinimumSize(new java.awt.Dimension(700, 150));
		pnlDetail2.setPreferredSize(new java.awt.Dimension(700, 150));
		ctrlTransferRequesterKanaName.setFieldSize(450);
		ctrlTransferRequesterKanaName.setLangMessageID("C00859");
		ctrlTransferRequesterKanaName.setMaxLength(40);
		ctrlTransferRequesterKanaName.setTabControlNo(7);
		ctrlTransferRequesterKanaName.setLabelSize(130);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 8, 0, 0);
		pnlDetail2.add(ctrlTransferRequesterKanaName, gridBagConstraints);

		ctrlRemittanceRequesterKanjiName.setFieldSize(450);
		ctrlRemittanceRequesterKanjiName.setLangMessageID("C00860");
		ctrlRemittanceRequesterKanjiName.setMaxLength(40);
		ctrlRemittanceRequesterKanjiName.setTabControlNo(8);
		ctrlRemittanceRequesterKanjiName.setLabelSize(130);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 8, 0, 0);
		pnlDetail2.add(ctrlRemittanceRequesterKanjiName, gridBagConstraints);

		ctrlRemittanceRequesterEnglishName.setFieldSize(450);
		ctrlRemittanceRequesterEnglishName.setLangMessageID("C00861");
		ctrlRemittanceRequesterEnglishName.setMaxLength(70);
		ctrlRemittanceRequesterEnglishName.setTabControlNo(9);
		ctrlRemittanceRequesterEnglishName.setLabelSize(130);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 8, 0, 20);
		pnlDetail2.add(ctrlRemittanceRequesterEnglishName, gridBagConstraints);

		ctrlAccountNumber.setFieldSize(120);
		ctrlAccountNumber.setLangMessageID("C00794");
		ctrlAccountNumber.getField().setAllowedSpace(false);
		ctrlAccountNumber.setMaxLength(10);
		ctrlAccountNumber.setTabControlNo(10);
		ctrlAccountNumber.setImeMode(false);
		ctrlAccountNumber.setLabelSize(130);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 8, 0, 0);
		pnlDetail2.add(ctrlAccountNumber, gridBagConstraints);

		ctrlTransferRequesterCode.setFieldSize(120);
		ctrlTransferRequesterCode.setLangMessageID("C00858");
		ctrlTransferRequesterCode.getField().setAllowedSpace(false);
		ctrlTransferRequesterCode.setMaxLength(10);
		ctrlTransferRequesterCode.setTabControlNo(6);
		ctrlTransferRequesterCode.setImeMode(false);
		ctrlTransferRequesterCode.setLabelSize(130);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 8, 0, 0);
		pnlDetail2.add(ctrlTransferRequesterCode, gridBagConstraints);

		ctrlDepositType.setComboSize(75);
		ctrlDepositType.setLabelSize(100);
		ctrlDepositType.setLangMessageID("C01326");
		ctrlDepositType.setTabControlNo(11);
		ctrlDepositType.setLabelSize(130);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 8, 0, 0);
		pnlDetail2.add(ctrlDepositType, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 180);

		getContentPane().add(pnlDetail2, gridBagConstraints);

		pnlDetail3.setLayout(new java.awt.GridBagLayout());

		pnlDetail3.setMaximumSize(new java.awt.Dimension(700, 80));
		pnlDetail3.setMinimumSize(new java.awt.Dimension(700, 80));
		pnlDetail3.setPreferredSize(new java.awt.Dimension(700, 80));
		pnlEmployeeFBDivision.setLayout(new java.awt.GridBagLayout());

		pnlEmployeeFBDivision.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory
			.createEtchedBorder(), "\u793e\u54e1\uff26\uff22\u533a\u5206"));
		pnlEmployeeFBDivision.setLangMessageID("C01117");
		pnlEmployeeFBDivision.setMaximumSize(new java.awt.Dimension(120, 70));
		pnlEmployeeFBDivision.setMinimumSize(new java.awt.Dimension(120, 70));
		pnlEmployeeFBDivision.setPreferredSize(new java.awt.Dimension(120, 70));

		rdoEmployeeFBDivisionException.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpEmployeeFBDivision.add(rdoEmployeeFBDivisionException);
		rdoEmployeeFBDivisionException.setSelected(true);
		rdoEmployeeFBDivisionException.setLangMessageID("C01116");
		rdoEmployeeFBDivisionException.setMargin(new java.awt.Insets(0, 0, 0, 0));
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		rdoEmployeeFBDivisionException.setTabControlNo(12);
		pnlEmployeeFBDivision.add(rdoEmployeeFBDivisionException, new java.awt.GridBagConstraints());

		rdoEmployeeFBUse.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpEmployeeFBDivision.add(rdoEmployeeFBUse);
		rdoEmployeeFBUse.setLangMessageID("C01118");
		rdoEmployeeFBUse.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoEmployeeFBUse.setTabControlNo(13);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlEmployeeFBDivision.add(rdoEmployeeFBUse, gridBagConstraints);

		pnlDetail3.add(pnlEmployeeFBDivision, new java.awt.GridBagConstraints());

		pnlExternalFBDivision.setLayout(new java.awt.GridBagLayout());
		pnlExternalFBDivision.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory
			.createEtchedBorder(), "\u793e\u5916\uff26\uff22\u533a\u5206"));
		pnlExternalFBDivision.setLangMessageID("C01122");
		pnlExternalFBDivision.setMaximumSize(new java.awt.Dimension(120, 70));
		pnlExternalFBDivision.setMinimumSize(new java.awt.Dimension(120, 70));
		pnlExternalFBDivision.setPreferredSize(new java.awt.Dimension(120, 70));

		rdoExternalFBException.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpExternalFBDivision.add(rdoExternalFBException);
		rdoExternalFBException.setSelected(true);
		rdoExternalFBException.setLangMessageID("C01121");
		rdoExternalFBException.setMargin(new java.awt.Insets(0, 0, 0, 0));
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		rdoExternalFBException.setTabControlNo(14);
		pnlExternalFBDivision.add(rdoExternalFBException, new java.awt.GridBagConstraints());

		rdoExternalFBUse.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpExternalFBDivision.add(rdoExternalFBUse);
		rdoExternalFBUse.setLangMessageID("C01123");
		rdoExternalFBUse.setMargin(new java.awt.Insets(0, 0, 0, 0));
		rdoExternalFBUse.setTabControlNo(15);
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlExternalFBDivision.add(rdoExternalFBUse, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.insets = new java.awt.Insets(0, 15, 0, 0);
		pnlDetail3.add(pnlExternalFBDivision, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;

		getContentPane().add(pnlDetail3, gridBagConstraints);

		pnlDetail4.setLayout(new java.awt.GridBagLayout());

		pnlDetail4.setMaximumSize(new java.awt.Dimension(700, 132));
		pnlDetail4.setMinimumSize(new java.awt.Dimension(700, 132));
		pnlDetail4.setPreferredSize(new java.awt.Dimension(700, 132));

		setItemSize();
		ctrlItemUnit.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		AccountItemInputParameter inputParameter = new AccountItemInputParameter();
		inputParameter.setSummaryDivision("0");
		ctrlItemUnit.getTItemField().setInputParameter(inputParameter);
		pnlDetail4.add(ctrlItemUnit, gridBagConstraints);

		ctrlAppropriateDepartment.setButtonSize(85);
		ctrlAppropriateDepartment.setFieldSize(120);
		ctrlAppropriateDepartment.setLangMessageID("C00863");
		ctrlAppropriateDepartment.setMaxLength(10);
		ctrlAppropriateDepartment.setMaximumSize(new java.awt.Dimension(410, 20));
		ctrlAppropriateDepartment.setMinimumSize(new java.awt.Dimension(410, 20));
		ctrlAppropriateDepartment.setNoticeSize(410);
		ctrlAppropriateDepartment.setTabControlNo(18);
		ctrlAppropriateDepartment.setImeMode(false);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(0, 70, 0, 0);
		pnlDetail4.add(ctrlAppropriateDepartment, gridBagConstraints);

		dtBeginDate.setLabelSize(65);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(20);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 89, 0, 0);
		pnlDetail4.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelSize(65);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(21);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
		gridBagConstraints.insets = new java.awt.Insets(5, 50, 0, 0);
		pnlDetail4.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new java.awt.GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new java.awt.Insets(0, 0, 20, 96);
		getContentPane().add(pnlDetail4, gridBagConstraints);

		pack();
	}

	/**
	 * 
	 *
	 */
	public void setItemSize() {
		this.setMaximumSize(new Dimension(690, 80));
		this.setMinimumSize(new Dimension(690, 80));
		this.setPreferredSize(new Dimension(690, 80));

		GridBagConstraints gridBagConstraints;

		ctrlItemUnit.getTBasePanel().setLayout(new GridBagLayout());

		ctrlItemUnit.getTBasePanel().setMaximumSize(new Dimension(690, 80));
		ctrlItemUnit.getTBasePanel().setMinimumSize(new Dimension(690, 80));
		ctrlItemUnit.getTBasePanel().setPreferredSize(new Dimension(690, 80));
		ctrlItemUnit.getTItemField().setButtonSize(85);
		ctrlItemUnit.getTItemField().setFieldSize(120);
		ctrlItemUnit.getTItemField().setNoticeSize(410);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(10, 70, 0, 0);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTItemField(), gridBagConstraints);

		ctrlItemUnit.getTSubItemField().setButtonSize(85);
		ctrlItemUnit.getTSubItemField().setFieldSize(120);
		ctrlItemUnit.getTSubItemField().setNoticeSize(410);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 70, 0, 0);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTSubItemField(), gridBagConstraints);

		ctrlItemUnit.getTBreakDownItemField().setButtonSize(85);
		ctrlItemUnit.getTBreakDownItemField().setFieldSize(120);
		ctrlItemUnit.getTBreakDownItemField().setNoticeSize(410);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 70, 0, 00);
		ctrlItemUnit.getTBasePanel().add(ctrlItemUnit.getTBreakDownItemField(), gridBagConstraints);
	}

	TPanel pnlBankAccount;

	TButton btnReturn;

	TImageButton btnSettle;

	ButtonGroup btngrpEmployeeFBDivision;

	ButtonGroup btngrpExternalFBDivision;

	TLabelField ctrlAccountNumber;

	TButtonField ctrlAppropriateDepartment;

	TButtonField ctrlBank;

	TTextField txtBankAccountName;

	TLabelField ctrlBankAccount;

	TButtonField ctrlBranch;

	TButtonField ctrlCurrency;

	TLabelComboBox ctrlDepositType;

	TLabelField ctrlRemittanceRequesterEnglishName;

	TLabelField ctrlRemittanceRequesterKanjiName;

	TLabelField ctrlTransferRequesterCode;

	TLabelHarfSizeCharConvertField ctrlTransferRequesterKanaName;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TPanel pnlDetail4;

	TPanel pnlEmployeeFBDivision;

	TPanel pnlExternalFBDivision;

	TRadioButton rdoEmployeeFBDivisionException;

	TRadioButton rdoEmployeeFBUse;

	TRadioButton rdoExternalFBException;

	TRadioButton rdoExternalFBUse;

	TAccountItemUnit ctrlItemUnit;

}

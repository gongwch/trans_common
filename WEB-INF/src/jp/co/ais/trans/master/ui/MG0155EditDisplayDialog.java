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
 * 取引先支払条件マスタ編集画面
 * 
 * @author zhangzhenxing
 */
public class MG0155EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0155EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid タイトル
	 */
	MG0155EditDisplayDialog(Frame parent, boolean modal, MG0155EditDisplayDialogCtrl ctrl, String titleid) {

		// 親フレームを設定
		super(parent, modal);

		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);

		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);

		// 画面の設定
		setSize(850, 650);

		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpPaymentDivision = new ButtonGroup();
		btngrpDepositType = new ButtonGroup();
		btngrpTransferCommission = new ButtonGroup();
		btngrpCommissionPaymentDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlCustomer = new TButtonField();
		ctrlCustomerAbbreviationName = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlCustomerConditionCode = new TLabelField();
		ctrlPaymentMethod = new TButtonField();
		pnlPaymentDivision = new TPanel();
		rdoTemporary = new TRadioButton();
		rdoOntime = new TRadioButton();
		pnlOntimePaymentCondition = new TPanel();
		numPaymentTermsCutoffDate = new TNumericField();
		lblPaymentTermsCutoffDate = new TLabel();
		numPaymentTermsAfterCutoffMonth = new TNumericField();
		lblPaymentTermsAfterCutoffMonth = new TLabel();
		numPaymentTermsCashInDate = new TNumericField();
		lblPaymentTermsCashInDate = new TLabel();
		pnlDetail = new TPanel();
		pnlTransferInfo = new TPanel();
		ctrlTransferDrawingBank = new TButtonField();
		ctrlBank = new TButtonField();
		ctrlBranch = new TButtonField();
		ctrlRemittancePurpose = new TButtonField();
		ctrlAccountNumber = new TLabelField();
		ctrlEnglishBankName = new TLabelField();
		ctrlEnglishBranchName = new TLabelField();
		ctrlEnglishBankAddress = new TLabelField();
		ctrlAccountName = new TLabelField();
		ctrlPaymentBankName = new TLabelField();
		ctrlPaymentBranchName = new TLabelField();
		ctrlPaymentBankAddress = new TLabelField();
		ctrlViaBankName = new TLabelField();
		ctrlViaBranchName = new TLabelField();
		ctrlViaBankAddress = new TLabelField();
		txtDepositTypeAccountNumber = new TTextField();
		pnlDepositType = new TPanel();
		rdoNormally = new TRadioButton();
		rdoForeignCurrency = new TRadioButton();
		rdoInterim = new TRadioButton();
		rdoSavings = new TRadioButton();
		pnlTransferCommission = new TPanel();
		rdoMyCompanyPay = new TRadioButton();
		rdoCustomerPay = new TRadioButton();
		pnlCommissionPaymentDivision = new TPanel();
		rdoRemittanceRecipient = new TRadioButton();
		rdoRemittanceMaker = new TRadioButton();
		lblViaBankName1 = new TLabel();
		lblPaymentBankName1 = new TLabel();
		ctrlRecipientAddress = new TLabelField();
		lblViaBankName2 = new TLabel();
		lblPaymentBankName2 = new TLabel();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(770, 40));
		pnlButton.setMinimumSize(new Dimension(770, 40));
		pnlButton.setPreferredSize(new Dimension(770, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(38);
		btnSettle.setPreferredSize(new Dimension(110, 25));
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
		gridBagConstraints.insets = new Insets(10, 575, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(39);
		btnReturn.setForClose(true);
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
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 40);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());
		ctrlCustomer.setButtonSize(85);
		ctrlCustomer.setFieldSize(75);
		ctrlCustomer.setLangMessageID("C00408");
		ctrlCustomer.setNoticeSize(0);
		ctrlCustomer.setMaxLength(10);
		ctrlCustomer.setTabControlNo(1);
		ctrlCustomer.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlHeader.add(ctrlCustomer, gridBagConstraints);

		ctrlCustomerAbbreviationName.setLabelSize(75);
		ctrlCustomerAbbreviationName.setEnabled(false);
		ctrlCustomerAbbreviationName.setLangMessageID("C00787");
		ctrlCustomerAbbreviationName.setMaxLength(40);
		ctrlCustomerAbbreviationName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlCustomerAbbreviationName, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(70);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(36);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(70);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(37);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 9;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 25, 0, 5);
		pnlHeader.add(dtEndDate, gridBagConstraints);

		ctrlCustomerConditionCode.setFieldSize(120);
		ctrlCustomerConditionCode.setLabelSize(100);
		ctrlCustomerConditionCode.setLangMessageID("C00788");
		ctrlCustomerConditionCode.getField().setAllowedSpace(false);
		ctrlCustomerConditionCode.setMaxLength(10);
		ctrlCustomerConditionCode.setTabControlNo(4);
		ctrlCustomerConditionCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 8, 0, 0);
		pnlHeader.add(ctrlCustomerConditionCode, gridBagConstraints);

		ctrlPaymentMethod.setButtonSize(85);
		ctrlPaymentMethod.setFieldSize(75);
		ctrlPaymentMethod.setLangMessageID("C00233");
		ctrlPaymentMethod.setMaxLength(3);
		ctrlPaymentMethod.setNoticeSize(135);
		ctrlPaymentMethod.setTabControlNo(5);
		ctrlPaymentMethod.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 7, 0, 0);
		pnlHeader.add(ctrlPaymentMethod, gridBagConstraints);

		pnlPaymentDivision.setLayout(new GridBagLayout());

		pnlPaymentDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "")));
		pnlPaymentDivision.setLangMessageID("C00682");
		pnlPaymentDivision.setMaximumSize(new Dimension(110, 50));
		pnlPaymentDivision.setMinimumSize(new Dimension(110, 50));
		pnlPaymentDivision.setPreferredSize(new Dimension(110, 50));
		rdoTemporary.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoTemporary);
		rdoTemporary.setSelected(true);
		rdoTemporary.setLangMessageID("C00555");
		rdoTemporary.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporary.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlPaymentDivision.add(rdoTemporary, gridBagConstraints);

		rdoOntime.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpPaymentDivision.add(rdoOntime);
		rdoOntime.setLangMessageID("C00380");
		rdoOntime.setMargin(new Insets(0, 0, 0, 0));
		rdoOntime.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlPaymentDivision.add(rdoOntime, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlHeader.add(pnlPaymentDivision, gridBagConstraints);

		pnlOntimePaymentCondition.setLayout(new GridBagLayout());

		pnlOntimePaymentCondition.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlOntimePaymentCondition.setLangMessageID("C01244");
		pnlOntimePaymentCondition.setMaximumSize(new Dimension(270, 50));
		pnlOntimePaymentCondition.setMinimumSize(new Dimension(270, 50));
		pnlOntimePaymentCondition.setPreferredSize(new Dimension(270, 50));
		numPaymentTermsCutoffDate.setMaxLength(2);
		numPaymentTermsCutoffDate.setMaximumSize(new Dimension(35, 20));
		numPaymentTermsCutoffDate.setMinimumSize(new Dimension(35, 20));
		numPaymentTermsCutoffDate.setPreferredSize(new Dimension(35, 20));
		numPaymentTermsCutoffDate.setNumericFormat("##");
		numPaymentTermsCutoffDate.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlOntimePaymentCondition.add(numPaymentTermsCutoffDate, gridBagConstraints);

		lblPaymentTermsCutoffDate.setLangMessageID("C01265");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 2, 5, 0);
		pnlOntimePaymentCondition.add(lblPaymentTermsCutoffDate, gridBagConstraints);

		numPaymentTermsAfterCutoffMonth.setMaxLength(2);
		numPaymentTermsAfterCutoffMonth.setMaximumSize(new Dimension(35, 20));
		numPaymentTermsAfterCutoffMonth.setMinimumSize(new Dimension(35, 20));
		numPaymentTermsAfterCutoffMonth.setPreferredSize(new Dimension(35, 20));
		numPaymentTermsAfterCutoffMonth.setNumericFormat("##");
		numPaymentTermsAfterCutoffMonth.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlOntimePaymentCondition.add(numPaymentTermsAfterCutoffMonth, gridBagConstraints);

		lblPaymentTermsAfterCutoffMonth.setLangMessageID("C00979");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 1, 5, 0);
		pnlOntimePaymentCondition.add(lblPaymentTermsAfterCutoffMonth, gridBagConstraints);

		numPaymentTermsCashInDate.setMaxLength(2);
		numPaymentTermsCashInDate.setMaximumSize(new Dimension(35, 20));
		numPaymentTermsCashInDate.setMinimumSize(new Dimension(35, 20));
		numPaymentTermsCashInDate.setPreferredSize(new Dimension(35, 20));
		numPaymentTermsCashInDate.setNumericFormat("##");
		numPaymentTermsCashInDate.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlOntimePaymentCondition.add(numPaymentTermsCashInDate, gridBagConstraints);

		lblPaymentTermsCashInDate.setLangMessageID("C00448");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 2, 5, 0);
		pnlOntimePaymentCondition.add(lblPaymentTermsCashInDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 6;
		gridBagConstraints.gridheight = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlHeader.add(pnlOntimePaymentCondition, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(770, 455));
		pnlDetail.setMinimumSize(new Dimension(770, 455));
		pnlDetail.setPreferredSize(new Dimension(770, 455));
		pnlTransferInfo.setLayout(new GridBagLayout());

		pnlTransferInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlTransferInfo.setLangMessageID("C01184");
		pnlTransferInfo.setMaximumSize(new Dimension(770, 445));
		pnlTransferInfo.setMinimumSize(new Dimension(770, 445));
		pnlTransferInfo.setPreferredSize(new Dimension(770, 445));
		ctrlTransferDrawingBank.setButtonSize(90);
		ctrlTransferDrawingBank.setFieldSize(75);
		ctrlTransferDrawingBank.setLangMessageID("C00946");
		ctrlTransferDrawingBank.setNoticeSize(255);
		ctrlTransferDrawingBank.setMaxLength(10);
		ctrlTransferDrawingBank.setTabControlNo(11);
		ctrlTransferDrawingBank.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlTransferDrawingBank, gridBagConstraints);

		ctrlBank.setButtonSize(90);
		ctrlBank.setFieldSize(40);
		ctrlBank.setLangMessageID("C01501");
		ctrlBank.setNoticeSize(255);
		ctrlBank.setMaxLength(4);
		ctrlBank.setTabControlNo(13);
		ctrlBank.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlBank, gridBagConstraints);

		ctrlBranch.setButtonSize(90);
		ctrlBranch.setFieldSize(40);
		ctrlBranch.setLangMessageID("C01502");
		ctrlBranch.setNoticeSize(255);
		ctrlBranch.setMaxLength(3);
		ctrlBranch.setTabControlNo(14);
		ctrlBranch.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlBranch, gridBagConstraints);

		ctrlRemittancePurpose.setButtonSize(90);
		ctrlRemittancePurpose.setFieldSize(40);
		ctrlRemittancePurpose.setLangMessageID("C00947");
		ctrlRemittancePurpose.setNoticeSize(255);
		ctrlRemittancePurpose.setMaxLength(4);
		ctrlRemittancePurpose.setTabControlNo(15);
		ctrlRemittancePurpose.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlRemittancePurpose, gridBagConstraints);

		ctrlAccountNumber.setFieldSize(190);
		ctrlAccountNumber.setLabelSize(150);
		ctrlAccountNumber.setLangMessageID("C00794");
		ctrlAccountNumber.getField().setAllowedSpace(false);
		ctrlAccountNumber.setMaxLength(34);
		ctrlAccountNumber.setTabControlNo(24);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlAccountNumber, gridBagConstraints);

		ctrlEnglishBankName.setFieldSize(435);
		ctrlEnglishBankName.setLabelSize(150);
		ctrlEnglishBankName.setLangMessageID("C00795");
		ctrlEnglishBankName.setMaxLength(35);
		ctrlEnglishBankName.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlEnglishBankName, gridBagConstraints);

		ctrlEnglishBranchName.setFieldSize(435);
		ctrlEnglishBranchName.setLabelSize(150);
		ctrlEnglishBranchName.setLangMessageID("C00796");
		ctrlEnglishBranchName.setMaxLength(35);
		ctrlEnglishBranchName.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlEnglishBranchName, gridBagConstraints);

		ctrlEnglishBankAddress.setFieldSize(435);
		ctrlEnglishBankAddress.setLabelSize(150);
		ctrlEnglishBankAddress.setLangMessageID("C00797");
		ctrlEnglishBankAddress.setMaxLength(80);
		ctrlEnglishBankAddress.setTabControlNo(27);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlEnglishBankAddress, gridBagConstraints);

		ctrlAccountName.setFieldSize(385);
		ctrlAccountName.setLabelSize(150);
		ctrlAccountName.setLangMessageID("C02394");
		ctrlAccountName.setTabEnabled(false);
		ctrlAccountName.setMaxLength(70);
		ctrlAccountName.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlAccountName, gridBagConstraints);

		ctrlPaymentBankName.setMaxLength(35);
		ctrlPaymentBankName.setFieldSize(435);
		ctrlPaymentBankName.setLabelSize(150);
		ctrlPaymentBankName.setLangMessageID("C00948");
		ctrlPaymentBankName.setTabControlNo(29);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlPaymentBankName, gridBagConstraints);

		ctrlPaymentBranchName.setMaxLength(35);
		ctrlPaymentBranchName.setFieldSize(435);
		ctrlPaymentBranchName.setLabelSize(150);
		ctrlPaymentBranchName.setLangMessageID("C00949");
		ctrlPaymentBranchName.setTabControlNo(30);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlPaymentBranchName, gridBagConstraints);

		ctrlPaymentBankAddress.setMaxLength(70);
		ctrlPaymentBankAddress.setFieldSize(435);
		ctrlPaymentBankAddress.setLabelSize(150);
		ctrlPaymentBankAddress.setLangMessageID("C00801");
		ctrlPaymentBankAddress.setTabControlNo(31);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 11;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlPaymentBankAddress, gridBagConstraints);

		ctrlViaBankName.setMaxLength(35);
		ctrlViaBankName.setFieldSize(435);
		ctrlViaBankName.setLabelSize(150);
		ctrlViaBankName.setLangMessageID("C00950");
		ctrlViaBankName.setTabControlNo(32);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlViaBankName, gridBagConstraints);

		ctrlViaBranchName.setMaxLength(35);
		ctrlViaBranchName.setFieldSize(435);
		ctrlViaBranchName.setLabelSize(150);
		ctrlViaBranchName.setLangMessageID("C00951");
		ctrlViaBranchName.setTabControlNo(33);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 13;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlViaBranchName, gridBagConstraints);

		ctrlViaBankAddress.setMaxLength(70);
		ctrlViaBankAddress.setFieldSize(435);
		ctrlViaBankAddress.setLabelSize(150);
		ctrlViaBankAddress.setLangMessageID("C00804");
		ctrlViaBankAddress.setTabControlNo(34);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 14;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(ctrlViaBankAddress, gridBagConstraints);

		txtDepositTypeAccountNumber.setEnabled(true);
		txtDepositTypeAccountNumber.setEditable(false);
		txtDepositTypeAccountNumber.setMaximumSize(new Dimension(170, 20));
		txtDepositTypeAccountNumber.setMinimumSize(new Dimension(170, 20));
		txtDepositTypeAccountNumber.setPreferredSize(new Dimension(170, 20));
		txtDepositTypeAccountNumber.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(txtDepositTypeAccountNumber, gridBagConstraints);

		pnlDepositType.setLayout(new GridBagLayout());

		pnlDepositType.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlDepositType.setLangMessageID("C01326");
		pnlDepositType.setMaximumSize(new Dimension(120, 70));
		pnlDepositType.setMinimumSize(new Dimension(120, 70));
		pnlDepositType.setPreferredSize(new Dimension(120, 70));
		rdoNormally.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepositType.add(rdoNormally);
		rdoNormally.setLangMessageID("C00463");
		rdoNormally.setMargin(new Insets(0, 0, 0, 0));
		rdoNormally.setTabControlNo(16);
		pnlDepositType.add(rdoNormally, new GridBagConstraints());

		rdoInterim.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepositType.add(rdoInterim);
		rdoInterim.setLangMessageID("C00397");
		rdoInterim.setMargin(new Insets(0, 0, 0, 0));
		rdoInterim.setTabControlNo(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlDepositType.add(rdoInterim, gridBagConstraints);

		rdoForeignCurrency.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepositType.add(rdoForeignCurrency);
		rdoForeignCurrency.setLangMessageID("C00045");
		rdoForeignCurrency.setMargin(new Insets(0, 0, 0, 0));
		rdoForeignCurrency.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlDepositType.add(rdoForeignCurrency, gridBagConstraints);

		rdoSavings.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepositType.add(rdoSavings);
		rdoSavings.setLangMessageID("C00368");
		rdoSavings.setMargin(new Insets(0, 0, 0, 0));
		rdoSavings.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 5, 5, 0);
		pnlDepositType.add(rdoSavings, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.gridheight = 4;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(pnlDepositType, gridBagConstraints);

		pnlTransferCommission.setLayout(new GridBagLayout());

		pnlTransferCommission.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlTransferCommission.setLangMessageID("C01183");
		pnlTransferCommission.setMaximumSize(new Dimension(120, 70));
		pnlTransferCommission.setMinimumSize(new Dimension(120, 70));
		pnlTransferCommission.setPreferredSize(new Dimension(120, 70));
		rdoMyCompanyPay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransferCommission.add(rdoMyCompanyPay);
		rdoMyCompanyPay.setLangMessageID("C00399");
		rdoMyCompanyPay.setMargin(new Insets(0, 0, 0, 0));
		rdoMyCompanyPay.setTabControlNo(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlTransferCommission.add(rdoMyCompanyPay, gridBagConstraints);

		rdoCustomerPay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransferCommission.add(rdoCustomerPay);
		rdoCustomerPay.setLangMessageID("C00327");
		rdoCustomerPay.setMargin(new Insets(0, 0, 0, 0));
		rdoCustomerPay.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlTransferCommission.add(rdoCustomerPay, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridheight = 4;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlTransferInfo.add(pnlTransferCommission, gridBagConstraints);

		pnlCommissionPaymentDivision.setLayout(new GridBagLayout());

		pnlCommissionPaymentDivision
			.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlCommissionPaymentDivision.setLangMessageID("C01139");
		pnlCommissionPaymentDivision.setMaximumSize(new Dimension(120, 70));
		pnlCommissionPaymentDivision.setMinimumSize(new Dimension(120, 70));
		pnlCommissionPaymentDivision.setPreferredSize(new Dimension(120, 70));
		rdoRemittanceRecipient.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCommissionPaymentDivision.add(rdoRemittanceRecipient);
		rdoRemittanceRecipient.setLangMessageID("C01220");
		rdoRemittanceRecipient.setMargin(new Insets(0, 0, 0, 0));
		rdoRemittanceRecipient.setTabControlNo(22);
		pnlCommissionPaymentDivision.add(rdoRemittanceRecipient, new GridBagConstraints());

		rdoRemittanceMaker.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCommissionPaymentDivision.add(rdoRemittanceMaker);
		rdoRemittanceMaker.setLangMessageID("C01219");
		rdoRemittanceMaker.setMargin(new Insets(0, 0, 0, 0));
		rdoRemittanceMaker.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlCommissionPaymentDivision.add(rdoRemittanceMaker, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridheight = 4;
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlTransferInfo.add(pnlCommissionPaymentDivision, gridBagConstraints);

		lblViaBankName1.setHorizontalAlignment(SwingConstants.LEFT);
		lblViaBankName1.setLangMessageID("C02357");
		lblViaBankName1.setMaximumSize(new Dimension(156, 20));
		lblViaBankName1.setMinimumSize(new Dimension(156, 20));
		lblViaBankName1.setPreferredSize(new Dimension(156, 20));
		lblViaBankName1.setVerticalTextPosition(SwingConstants.BOTTOM);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(lblViaBankName1, gridBagConstraints);

		lblPaymentBankName1.setLangMessageID("C02356");
		lblPaymentBankName1.setMaximumSize(new Dimension(156, 20));
		lblPaymentBankName1.setMinimumSize(new Dimension(156, 20));
		lblPaymentBankName1.setPreferredSize(new Dimension(156, 20));
		lblPaymentBankName1.setVerticalTextPosition(SwingConstants.BOTTOM);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlTransferInfo.add(lblPaymentBankName1, gridBagConstraints);

		ctrlRecipientAddress.setMaxLength(70);
		ctrlRecipientAddress.setFieldSize(435);
		ctrlRecipientAddress.setLabelSize(150);
		ctrlRecipientAddress.setLangMessageID("C00805");
		ctrlRecipientAddress.setTabControlNo(35);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 15;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 15, 0);
		pnlTransferInfo.add(ctrlRecipientAddress, gridBagConstraints);

		lblViaBankName2.setHorizontalAlignment(SwingConstants.LEFT);
		lblViaBankName2.setLangMessageID("C02174");
		lblViaBankName2.setMaximumSize(new Dimension(156, 20));
		lblViaBankName2.setMinimumSize(new Dimension(156, 20));
		lblViaBankName2.setPreferredSize(new Dimension(156, 20));
		lblViaBankName2.setVerticalTextPosition(SwingConstants.TOP);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 13;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		pnlTransferInfo.add(lblViaBankName2, gridBagConstraints);

		lblPaymentBankName2.setHorizontalAlignment(SwingConstants.LEFT);
		lblPaymentBankName2.setLangMessageID("C02172");
		lblPaymentBankName2.setMaximumSize(new Dimension(156, 20));
		lblPaymentBankName2.setMinimumSize(new Dimension(156, 20));
		lblPaymentBankName2.setPreferredSize(new Dimension(156, 20));
		lblPaymentBankName2.setVerticalTextPosition(SwingConstants.TOP);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
		pnlTransferInfo.add(lblPaymentBankName2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 20, 0);
		pnlDetail.add(pnlTransferInfo, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	TImageButton btnSettle;

	ButtonGroup btngrpCommissionPaymentDivision;

	ButtonGroup btngrpDepositType;

	ButtonGroup btngrpPaymentDivision;

	ButtonGroup btngrpTransferCommission;

	TLabelField ctrlAccountName;

	TLabelField ctrlAccountNumber;

	TButtonField ctrlBank;

	TButtonField ctrlBranch;

	TLabelField ctrlCustomerAbbreviationName;

	TLabelField ctrlCustomerConditionCode;

	TLabelField ctrlEnglishBankAddress;

	TLabelField ctrlEnglishBankName;

	TLabelField ctrlEnglishBranchName;

	TLabelField ctrlPaymentBankAddress;

	TLabelField ctrlPaymentBankName;

	TLabelField ctrlPaymentBranchName;

	TButtonField ctrlPaymentMethod;

	TButtonField ctrlCustomer;

	TLabelField ctrlRecipientAddress;

	TButtonField ctrlRemittancePurpose;

	TButtonField ctrlTransferDrawingBank;

	TLabelField ctrlViaBankAddress;

	TLabelField ctrlViaBankName;

	TLabelField ctrlViaBranchName;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TButton btnReturn;

	TLabel lblPaymentBankName1;

	TLabel lblPaymentBankName2;

	TLabel lblPaymentTermsAfterCutoffMonth;

	TLabel lblPaymentTermsCashInDate;

	TLabel lblPaymentTermsCutoffDate;

	TLabel lblViaBankName1;

	TLabel lblViaBankName2;

	TNumericField numPaymentTermsAfterCutoffMonth;

	TNumericField numPaymentTermsCashInDate;

	TNumericField numPaymentTermsCutoffDate;

	TPanel pnlButton;

	TPanel pnlCommissionPaymentDivision;

	TPanel pnlDepositType;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlOntimePaymentCondition;

	TPanel pnlPaymentDivision;

	TPanel pnlTransferCommission;

	TPanel pnlTransferInfo;

	TRadioButton rdoCustomerPay;

	TRadioButton rdoForeignCurrency;

	TRadioButton rdoInterim;

	TRadioButton rdoMyCompanyPay;

	TRadioButton rdoNormally;

	TRadioButton rdoOntime;

	TRadioButton rdoRemittanceMaker;

	TRadioButton rdoRemittanceRecipient;

	TRadioButton rdoSavings;

	TRadioButton rdoTemporary;

	TTextField txtDepositTypeAccountNumber;

}

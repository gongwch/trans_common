package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * @author zhangzhenxing
 */
public class MG0150EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0150EditDisplayDialogCtrl ctrl;

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
	MG0150EditDisplayDialog(Frame parent, boolean modal, MG0150EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);

		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);

		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);

		// 画面の設定
		setSize(700, 550);

		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpCashInCommission = new ButtonGroup();
		btngrpVenderDivision = new ButtonGroup();
		btngrpCustomerDivision = new ButtonGroup();
		btngrpTransactionModeDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlCustomerCode = new TLabelField();
		ctrlCustomerName = new TLabelField();
		ctrlOfficeName = new TLabelField();
		ctrlCustomerAbbreviationName = new TLabelField();
		ctrlCustomerNameForSearch = new TLabelField();
		ctrlPostcode = new TLabelField();
		ctrlTelephoneNumber = new TLabelField();
		ctrlFaxNumber = new TLabelField();
		ctrlAddress1 = new TLabelField();
		ctrlAddress2 = new TLabelField();
		ctrlAddressKana = new TLabelField();
		pnlDetail = new TPanel();
		pnlCustomerInfo = new TPanel();
		ctrlInputBankAccountCode = new TButtonField();
		txtDepositTypeAccountNumber = new TTextField();
		ctrlTransferRequesterKanaName = new TLabelField();
		pnlOntimeCashInCondition = new TPanel();
		lblCashInConditionCutoffDay = new TLabel();
		lblCashInConditionAfterCutoffMonth = new TLabel();
		lblCashInConditionCashInDay = new TLabel();
		numCashInConditionCutoffDay = new TNumericField();
		numCashInConditionAfterCutoffMonth = new TNumericField();
		numCashInConditionCashInDay = new TNumericField();
		pnlCashInCommission = new TPanel();
		rdoMyCompanyPay = new TRadioButton();
		rdoCustomerPay = new TRadioButton();
		pnlVendorDivision = new TPanel();
		rdoNotVendor = new TRadioButton();
		rdoVendor = new TRadioButton();
		pnlCustomerDivision = new TPanel();
		rdoNotCustomer = new TRadioButton();
		rdoCustomer = new TRadioButton();
		pnlTransactionModeDivision = new TPanel();
		rdoNormally = new TRadioButton();
		rdoSpot = new TRadioButton();
		ctrlSummaryCode = new TButtonField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

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
		btnSettle.setTabControlNo(29);
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
		gridBagConstraints.insets = new Insets(10, 355, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(30);
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
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(700, 225));
		pnlHeader.setMinimumSize(new Dimension(700, 225));
		pnlHeader.setPreferredSize(new Dimension(700, 225));
		ctrlCustomerCode.setFieldSize(120);
		ctrlCustomerCode.setLabelSize(80);
		ctrlCustomerCode.setLangMessageID("C00786");
		ctrlCustomerCode.getField().setAllowedSpace(false);
		ctrlCustomerCode.setMaxLength(10);
		ctrlCustomerCode.setTabControlNo(1);
		ctrlCustomerCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlCustomerCode, gridBagConstraints);

		ctrlCustomerName.setFieldSize(450);
		ctrlCustomerName.setLabelSize(80);
		ctrlCustomerName.setLangMessageID("C00830");
		ctrlCustomerName.setMaxLength(40);
		ctrlCustomerName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlCustomerName, gridBagConstraints);

		ctrlOfficeName.setFieldSize(450);
		ctrlOfficeName.setLabelSize(80);
		ctrlOfficeName.setLangMessageID("C00581");
		ctrlOfficeName.setMaxLength(40);
		ctrlOfficeName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlOfficeName, gridBagConstraints);

		ctrlCustomerAbbreviationName.setFieldSize(450);
		ctrlCustomerAbbreviationName.setLabelSize(80);
		ctrlCustomerAbbreviationName.setLangMessageID("C00787");
		ctrlCustomerAbbreviationName.setMaxLength(40);
		ctrlCustomerAbbreviationName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlCustomerAbbreviationName, gridBagConstraints);

		ctrlCustomerNameForSearch.setFieldSize(500);
		ctrlCustomerNameForSearch.setLabelSize(100);
		ctrlCustomerNameForSearch.setLangMessageID("C00836");
		ctrlCustomerNameForSearch.setMaxLength(80);
		ctrlCustomerNameForSearch.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlCustomerNameForSearch, gridBagConstraints);

		ctrlPostcode.setFieldSize(100);
		ctrlPostcode.setLabelSize(80);
		ctrlPostcode.setLangMessageID("C00527");
		ctrlPostcode.setMaxLength(10);
		ctrlPostcode.setTabControlNo(6);
		ctrlPostcode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlPostcode, gridBagConstraints);

		ctrlTelephoneNumber.setFieldSize(100);
		ctrlTelephoneNumber.setLabelSize(80);
		ctrlTelephoneNumber.setLangMessageID("C00393");
		ctrlTelephoneNumber.setMaxLength(12);
		ctrlTelephoneNumber.setTabControlNo(7);
		ctrlTelephoneNumber.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlHeader.add(ctrlTelephoneNumber, gridBagConstraints);

		ctrlFaxNumber.setFieldSize(100);
		ctrlFaxNumber.setLabelSize(80);
		ctrlFaxNumber.setLangMessageID("C00690");
		ctrlFaxNumber.setMaxLength(12);
		ctrlFaxNumber.setTabControlNo(8);
		ctrlFaxNumber.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlHeader.add(ctrlFaxNumber, gridBagConstraints);

		ctrlAddress1.setFieldSize(560);
		ctrlAddress1.setLabelSize(80);
		ctrlAddress1.setLangMessageID("C01150");
		ctrlAddress1.setMaxLength(50);
		ctrlAddress1.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlAddress1, gridBagConstraints);

		ctrlAddress2.setFieldSize(560);
		ctrlAddress2.setLabelSize(80);
		ctrlAddress2.setLangMessageID("C01151");
		ctrlAddress2.setMaxLength(50);
		ctrlAddress2.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlAddress2, gridBagConstraints);

		ctrlAddressKana.setFieldSize(560);
		ctrlAddressKana.setLabelSize(80);
		ctrlAddressKana.setLangMessageID("C01152");
		ctrlAddressKana.setMaxLength(80);
		ctrlAddressKana.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 20, 0, 0);
		pnlHeader.add(ctrlAddressKana, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(665, 240));
		pnlDetail.setMinimumSize(new Dimension(665, 240));
		pnlDetail.setPreferredSize(new Dimension(665, 240));
		pnlCustomerInfo.setLayout(new GridBagLayout());

		pnlCustomerInfo.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlCustomerInfo.setLangMessageID("C01262");
		pnlCustomerInfo.setMaximumSize(new Dimension(480, 150));
		pnlCustomerInfo.setMinimumSize(new Dimension(480, 150));
		pnlCustomerInfo.setPreferredSize(new Dimension(480, 150));
		ctrlInputBankAccountCode.setButtonSize(120);
		ctrlInputBankAccountCode.setFieldSize(75);
		ctrlInputBankAccountCode.setLangMessageID("C00647");
		ctrlInputBankAccountCode.setMaxLength(10);
		ctrlInputBankAccountCode.setNoticeSize(135);
		ctrlInputBankAccountCode.setTabControlNo(12);
		ctrlInputBankAccountCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlCustomerInfo.add(ctrlInputBankAccountCode, gridBagConstraints);

		txtDepositTypeAccountNumber.setEnabled(true);
		txtDepositTypeAccountNumber.setEditable(false);
		txtDepositTypeAccountNumber.setMaximumSize(new Dimension(120, 20));
		txtDepositTypeAccountNumber.setMinimumSize(new Dimension(120, 20));
		txtDepositTypeAccountNumber.setPreferredSize(new Dimension(120, 20));
		txtDepositTypeAccountNumber.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlCustomerInfo.add(txtDepositTypeAccountNumber, gridBagConstraints);

		ctrlTransferRequesterKanaName.setFieldSize(330);
		ctrlTransferRequesterKanaName.setLabelSize(120);
		ctrlTransferRequesterKanaName.setLangMessageID("C00859");
		ctrlTransferRequesterKanaName.setMaxLength(48);
		ctrlTransferRequesterKanaName.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlCustomerInfo.add(ctrlTransferRequesterKanaName, gridBagConstraints);

		pnlOntimeCashInCondition.setLayout(new GridBagLayout());

		pnlOntimeCashInCondition.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlOntimeCashInCondition.setLangMessageID("C01245");
		pnlOntimeCashInCondition.setMaximumSize(new Dimension(300, 50));
		pnlOntimeCashInCondition.setMinimumSize(new Dimension(300, 50));
		pnlOntimeCashInCondition.setPreferredSize(new Dimension(300, 50));
		lblCashInConditionCutoffDay.setLangMessageID("C01265");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(lblCashInConditionCutoffDay, gridBagConstraints);

		lblCashInConditionAfterCutoffMonth.setLangMessageID("C01493");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(lblCashInConditionAfterCutoffMonth, gridBagConstraints);

		lblCashInConditionCashInDay.setLangMessageID("C00448");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(lblCashInConditionCashInDay, gridBagConstraints);

		numCashInConditionCutoffDay.setMaxLength(2);
		numCashInConditionCutoffDay.setMaximumSize(new Dimension(35, 20));
		numCashInConditionCutoffDay.setMinimumSize(new Dimension(35, 20));
		numCashInConditionCutoffDay.setPreferredSize(new Dimension(35, 20));
		numCashInConditionCutoffDay.setNumericFormat("##");
		numCashInConditionCutoffDay.setTabControlNo(15);
		numCashInConditionCutoffDay.setPositiveOnly(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(numCashInConditionCutoffDay, gridBagConstraints);

		numCashInConditionAfterCutoffMonth.setMaxLength(2);
		numCashInConditionAfterCutoffMonth.setMaximumSize(new Dimension(35, 20));
		numCashInConditionAfterCutoffMonth.setMinimumSize(new Dimension(35, 20));
		numCashInConditionAfterCutoffMonth.setPreferredSize(new Dimension(35, 20));
		numCashInConditionAfterCutoffMonth.setNumericFormat("##");
		numCashInConditionAfterCutoffMonth.setTabControlNo(16);
		numCashInConditionAfterCutoffMonth.setPositiveOnly(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(numCashInConditionAfterCutoffMonth, gridBagConstraints);

		numCashInConditionCashInDay.setMaxLength(2);
		numCashInConditionCashInDay.setMaximumSize(new Dimension(35, 20));
		numCashInConditionCashInDay.setMinimumSize(new Dimension(35, 20));
		numCashInConditionCashInDay.setPreferredSize(new Dimension(35, 20));
		numCashInConditionCashInDay.setNumericFormat("##");
		numCashInConditionCashInDay.setTabControlNo(17);
		numCashInConditionCashInDay.setPositiveOnly(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 5, 0);
		pnlOntimeCashInCondition.add(numCashInConditionCashInDay, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlCustomerInfo.add(pnlOntimeCashInCondition, gridBagConstraints);

		pnlCashInCommission.setLayout(new GridBagLayout());

		pnlCashInCommission.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlCashInCommission.setLangMessageID("C01269");
		pnlCashInCommission.setMaximumSize(new Dimension(120, 70));
		pnlCashInCommission.setMinimumSize(new Dimension(120, 70));
		pnlCashInCommission.setPreferredSize(new Dimension(120, 70));
		rdoMyCompanyPay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCashInCommission.add(rdoMyCompanyPay);
		rdoMyCompanyPay.setSelected(true);
		rdoMyCompanyPay.setLangMessageID("C00399");
		rdoMyCompanyPay.setMargin(new Insets(0, 0, 0, 0));
		rdoMyCompanyPay.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlCashInCommission.add(rdoMyCompanyPay, gridBagConstraints);

		rdoCustomerPay.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCashInCommission.add(rdoCustomerPay);
		rdoCustomerPay.setLangMessageID("C00327");
		rdoCustomerPay.setMargin(new Insets(0, 0, 0, 0));
		rdoCustomerPay.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlCashInCommission.add(rdoCustomerPay, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlCustomerInfo.add(pnlCashInCommission, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(pnlCustomerInfo, gridBagConstraints);

		pnlVendorDivision.setLayout(new GridBagLayout());

		pnlVendorDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u4ed5\u5165\u5148\u533a\u5206"));
		pnlVendorDivision.setLangMessageID("C01089");
		pnlVendorDivision.setMaximumSize(new Dimension(100, 70));
		pnlVendorDivision.setMinimumSize(new Dimension(100, 70));
		pnlVendorDivision.setPreferredSize(new Dimension(100, 70));
		rdoNotVendor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpVenderDivision.add(rdoNotVendor);
		rdoNotVendor.setSelected(true);
		rdoNotVendor.setLangMessageID("C01295");
		rdoNotVendor.setMargin(new Insets(0, 0, 0, 0));
		rdoNotVendor.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlVendorDivision.add(rdoNotVendor, gridBagConstraints);

		rdoVendor.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpVenderDivision.add(rdoVendor);
		rdoVendor.setLangMessageID("C00203");
		rdoVendor.setMargin(new Insets(0, 0, 0, 0));
		rdoVendor.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlVendorDivision.add(rdoVendor, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(5, 25, 0, 0);
		pnlDetail.add(pnlVendorDivision, gridBagConstraints);

		pnlCustomerDivision.setLayout(new GridBagLayout());

		pnlCustomerDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u5f97\u610f\u5148\u533a\u5206"));
		pnlCustomerDivision.setLangMessageID("C01261");
		pnlCustomerDivision.setMaximumSize(new Dimension(100, 70));
		pnlCustomerDivision.setMinimumSize(new Dimension(100, 70));
		pnlCustomerDivision.setPreferredSize(new Dimension(100, 70));
		rdoNotCustomer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCustomerDivision.add(rdoNotCustomer);
		rdoNotCustomer.setSelected(true);
		rdoNotCustomer.setLangMessageID("C01296");
		rdoNotCustomer.setMargin(new Insets(0, 0, 0, 0));
		rdoNotCustomer.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlCustomerDivision.add(rdoNotCustomer, gridBagConstraints);

		rdoCustomer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpCustomerDivision.add(rdoCustomer);
		rdoCustomer.setLangMessageID("C00401");
		rdoCustomer.setMargin(new Insets(0, 0, 0, 0));
		rdoCustomer.setTabControlNo(24);

		rdoCustomer.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {

				ctrl.changedCustomerSelect(e.getStateChange() == ItemEvent.SELECTED);
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlCustomerDivision.add(rdoCustomer, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 25, 0, 0);
		pnlDetail.add(pnlCustomerDivision, gridBagConstraints);

		pnlTransactionModeDivision.setLayout(new GridBagLayout());

		pnlTransactionModeDivision.setLangMessageID("C01130");
		pnlTransactionModeDivision.setMaximumSize(new Dimension(100, 70));
		pnlTransactionModeDivision.setMinimumSize(new Dimension(100, 70));
		pnlTransactionModeDivision.setPreferredSize(new Dimension(100, 70));
		rdoNormally.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransactionModeDivision.add(rdoNormally);
		rdoNormally.setSelected(true);
		rdoNormally.setLangMessageID("C00372");
		rdoNormally.setMargin(new Insets(0, 0, 0, 0));
		rdoNormally.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlTransactionModeDivision.add(rdoNormally, gridBagConstraints);

		rdoSpot.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransactionModeDivision.add(rdoSpot);
		rdoSpot.setEnabled(false);
		rdoSpot.setLangMessageID("C00308");
		rdoSpot.setMargin(new Insets(0, 0, 0, 0));
		rdoSpot.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlTransactionModeDivision.add(rdoSpot, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.insets = new Insets(0, 25, 20, 0);
		pnlDetail.add(pnlTransactionModeDivision, gridBagConstraints);

		ctrlSummaryCode.setButtonSize(120);
		ctrlSummaryCode.setFieldSize(120);
		ctrlSummaryCode.setLangMessageID("C00871");
		ctrlSummaryCode.setMaxLength(10);
		ctrlSummaryCode.setNoticeSize(250);
		ctrlSummaryCode.setTabControlNo(20);
		ctrlSummaryCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		pnlDetail.add(ctrlSummaryCode, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(100);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(27);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 10, 20, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(100);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(28);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 20, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	ButtonGroup btngrpCashInCommission;

	ButtonGroup btngrpCustomerDivision;

	ButtonGroup btngrpTransactionModeDivision;

	ButtonGroup btngrpVenderDivision;

	TLabelField ctrlAddress1;

	TLabelField ctrlAddress2;

	TLabelField ctrlAddressKana;

	TLabelField ctrlCustomerAbbreviationName;

	TLabelField ctrlCustomerCode;

	TLabelField ctrlCustomerName;

	TLabelField ctrlCustomerNameForSearch;

	TLabelField ctrlFaxNumber;

	TLabelField ctrlOfficeName;

	TLabelField ctrlPostcode;

	TButtonField ctrlSummaryCode;

	TLabelField ctrlTelephoneNumber;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	/** 入金銀行口座コード */
	TButtonField ctrlInputBankAccountCode;

	/** 振込依頼人名(カナ) */
	TLabelField ctrlTransferRequesterKanaName;

	/** 入金条件 締め日 ラベル */
	TLabel lblCashInConditionCutoffDay;

	/** 入金条件 締め後月ラベル */
	TLabel lblCashInConditionAfterCutoffMonth;

	/** 入金条件 支払日 ラベル */
	TLabel lblCashInConditionCashInDay;

	/** 入金条件 締め日 */
	TNumericField numCashInConditionCutoffDay;

	/** 入金条件 締め後月 */
	TNumericField numCashInConditionAfterCutoffMonth;

	/** 入金条件 支払日 */
	TNumericField numCashInConditionCashInDay;

	TPanel pnlButton;

	TPanel pnlCashInCommission;

	TPanel pnlCustomerDivision;

	TPanel pnlCustomerInfo;

	TPanel pnlDetail;

	TPanel pnlHeader;

	TPanel pnlOntimeCashInCondition;

	TPanel pnlTransactionModeDivision;

	TPanel pnlVendorDivision;

	/** 入金手数料 先方負担 */
	TRadioButton rdoCustomerPay;

	/** 入金手数料 当社負担 */
	TRadioButton rdoMyCompanyPay;

	/** 仕入先 */
	TRadioButton rdoVendor;

	/** 非仕入先 */
	TRadioButton rdoNotVendor;

	/** 得意先 */
	TRadioButton rdoCustomer;

	/** 非得意先 */
	TRadioButton rdoNotCustomer;

	/** 取引形態区分 通常 */
	TRadioButton rdoNormally;

	/** 取引形態区分 スポット */
	TRadioButton rdoSpot;

	TTextField txtDepositTypeAccountNumber;

}

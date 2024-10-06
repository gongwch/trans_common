package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ユーザーマスタDialog
 * 
 * @author liuchengcheng
 */
public class MG0020EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -1724205089133557188L;

	/** コントロールクラス */
	protected MG0020EditDisplayDialogCtrl ctrl;

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
	MG0020EditDisplayDialog(Frame parent, boolean modal, MG0020EditDisplayDialogCtrl ctrl, String titleid) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleid);
		setSize(650, 600);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;
		btngrpAccountingPersonInChargeDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail1 = new TPanel();
		ctrlUserCode = new TLabelField();
		ctrlUserName = new TLabelField();
		ctrlUserAbbreviatedName = new TLabelField();
		txtUserNameForSearch = new TLabelField();
		ctrlPassword = new TLabelField();
		pnlDetail2 = new TPanel();
		pnlSystemDivision = new TPanel();
		chkBasicAccounting = new TCheckBox();
		chkFixedAsset = new TCheckBox();
		chkManagementAccounting = new TCheckBox();
		chkMultiCurrencyAccounting = new TCheckBox();
		chkSubsidiaryCompanyConnection = new TCheckBox();
		chkAccountsPayableManagement = new TCheckBox();
		chkNotePayable = new TCheckBox();
		chkParentCompanyConnection = new TCheckBox();
		chkBudgetManagement = new TCheckBox();
		chkGroupAccounting = new TCheckBox();
		chkAccountsReceivableManagement = new TCheckBox();
		chkBillReceivable = new TCheckBox();
		chkHeadquartersBranchAccounting = new TCheckBox();
		chkFundAdministration = new TCheckBox();
		chkPortfolioManagement = new TCheckBox();
		pnlDetail3 = new TPanel();
		pnlAuthorityLevel = new TPanel();
		ctrlUpdateAuthorityLevel = new TLabelComboBox();
		numProcessingAuthorityLevel = new TLabelNumericField();
		pnlAccountingPersonInChargeDivision = new TPanel();
		rdoAccountingPersonExcluding = new TRadioButton();
		rdoAccountingPerson = new TRadioButton();
		pnlDetail4 = new TPanel();
		ctrlCompanyCode = new TButtonField();
		ctrlEmployeeCode = new TButtonField();
		ctrlDepartmentCode = new TButtonField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlLanguageCode = new TButtonField();

		getContentPane().setLayout(new GridBagLayout());

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(620, 40));
		pnlButton.setMinimumSize(new Dimension(620, 40));
		pnlButton.setPreferredSize(new Dimension(620, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setTabControlNo(31);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 300, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */
			public void actionPerformed(ActionEvent evt) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setTabControlNo(32);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

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
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(670, 130));
		pnlDetail1.setMinimumSize(new Dimension(670, 130));
		pnlDetail1.setPreferredSize(new Dimension(670, 130));
		ctrlUserCode.setFieldSize(120);
		ctrlUserCode.setLangMessageID("C00589");
		ctrlUserCode.setMaxLength(10);
		ctrlUserCode.setTabControlNo(1);
		ctrlUserCode.setImeMode(false);
		ctrlUserCode.getField().setAllowedSpace(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 5);
		pnlDetail1.add(ctrlUserCode, gridBagConstraints);

		ctrlUserName.setFieldSize(450);
		ctrlUserName.setLangMessageID("C00691");
		ctrlUserName.setMaxLength(40);
		ctrlUserName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlUserName, gridBagConstraints);

		ctrlUserAbbreviatedName.setFieldSize(450);
		ctrlUserAbbreviatedName.setLangMessageID("C00692");
		ctrlUserAbbreviatedName.setMaxLength(40);
		ctrlUserAbbreviatedName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlUserAbbreviatedName, gridBagConstraints);

		txtUserNameForSearch.setFieldSize(450);
		txtUserNameForSearch.setLangMessageID("C00693");
		txtUserNameForSearch.setMaxLength(40);
		txtUserNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(txtUserNameForSearch, gridBagConstraints);

		ctrlPassword.setFieldSize(120);
		ctrlPassword.setLangMessageID("C00597");
		ctrlPassword.setMaxLength(10);
		ctrlPassword.setTabControlNo(5);
		ctrlPassword.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 5);
		pnlDetail1.add(ctrlPassword, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(350, 130));
		pnlSystemDivision.setLayout(new GridBagLayout());

		pnlSystemDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlSystemDivision.setLangMessageID("C00217");
		pnlSystemDivision.setMaximumSize(new Dimension(340, 140));
		pnlSystemDivision.setMinimumSize(new Dimension(340, 140));
		pnlSystemDivision.setPreferredSize(new Dimension(340, 140));
		chkBasicAccounting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkBasicAccounting.setLangMessageID("C00112");
		TGuiUtil.setComponentSize(chkBasicAccounting, new Dimension(105, 20));
		chkBasicAccounting.setMargin(new Insets(0, 0, 0, 0));
		chkBasicAccounting.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlSystemDivision.add(chkBasicAccounting, gridBagConstraints);

		chkFixedAsset.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkFixedAsset.setLangMessageID("C00180");
		TGuiUtil.setComponentSize(chkFixedAsset, new Dimension(105, 20));
		chkFixedAsset.setMargin(new Insets(0, 0, 0, 0));
		chkFixedAsset.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 0);
		pnlSystemDivision.add(chkFixedAsset, gridBagConstraints);

		chkManagementAccounting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementAccounting.setLangMessageID("C00096");
		TGuiUtil.setComponentSize(chkManagementAccounting, new Dimension(105, 20));
		chkManagementAccounting.setMargin(new Insets(0, 0, 0, 0));
		chkManagementAccounting.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 0);
		pnlSystemDivision.add(chkManagementAccounting, gridBagConstraints);

		chkMultiCurrencyAccounting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkMultiCurrencyAccounting.setLangMessageID("C01222");
		TGuiUtil.setComponentSize(chkMultiCurrencyAccounting, new Dimension(105, 20));
		chkMultiCurrencyAccounting.setMargin(new Insets(0, 0, 0, 0));
		chkMultiCurrencyAccounting.setTabControlNo(15);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 0);
		pnlSystemDivision.add(chkMultiCurrencyAccounting, gridBagConstraints);

		chkSubsidiaryCompanyConnection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSubsidiaryCompanyConnection.setLangMessageID("C00176");
		TGuiUtil.setComponentSize(chkSubsidiaryCompanyConnection, new Dimension(105, 20));
		chkSubsidiaryCompanyConnection.setMargin(new Insets(0, 0, 0, 0));
		chkSubsidiaryCompanyConnection.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 0, 0, 0);
		pnlSystemDivision.add(chkSubsidiaryCompanyConnection, gridBagConstraints);

		chkAccountsPayableManagement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsPayableManagement.setLangMessageID("C01082");
		TGuiUtil.setComponentSize(chkAccountsPayableManagement, new Dimension(105, 20));
		chkAccountsPayableManagement.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsPayableManagement.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlSystemDivision.add(chkAccountsPayableManagement, gridBagConstraints);

		chkNotePayable.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkNotePayable.setLangMessageID("C00230");
		TGuiUtil.setComponentSize(chkNotePayable, new Dimension(105, 20));
		chkNotePayable.setMargin(new Insets(0, 0, 0, 0));
		chkNotePayable.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkNotePayable, gridBagConstraints);

		chkParentCompanyConnection.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkParentCompanyConnection.setLangMessageID("C00041");
		TGuiUtil.setComponentSize(chkParentCompanyConnection, new Dimension(105, 20));
		chkParentCompanyConnection.setMargin(new Insets(0, 0, 0, 0));
		chkParentCompanyConnection.setTabControlNo(13);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkParentCompanyConnection, gridBagConstraints);

		chkBudgetManagement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkBudgetManagement.setLangMessageID("C00537");
		TGuiUtil.setComponentSize(chkBudgetManagement, new Dimension(105, 20));
		chkBudgetManagement.setMargin(new Insets(0, 0, 0, 0));
		chkBudgetManagement.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkBudgetManagement, gridBagConstraints);

		chkGroupAccounting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkGroupAccounting.setLangMessageID("C00130");
		TGuiUtil.setComponentSize(chkGroupAccounting, new Dimension(105, 20));
		chkGroupAccounting.setMargin(new Insets(0, 0, 0, 0));
		chkGroupAccounting.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkGroupAccounting, gridBagConstraints);

		chkAccountsReceivableManagement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAccountsReceivableManagement.setLangMessageID("C01078");
		TGuiUtil.setComponentSize(chkAccountsReceivableManagement, new Dimension(105, 20));
		chkAccountsReceivableManagement.setMargin(new Insets(0, 0, 0, 0));
		chkAccountsReceivableManagement.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlSystemDivision.add(chkAccountsReceivableManagement, gridBagConstraints);

		chkBillReceivable.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkBillReceivable.setLangMessageID("C00020");
		TGuiUtil.setComponentSize(chkBillReceivable, new Dimension(105, 20));
		chkBillReceivable.setMargin(new Insets(0, 0, 0, 0));
		chkBillReceivable.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkBillReceivable, gridBagConstraints);

		chkHeadquartersBranchAccounting.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkHeadquartersBranchAccounting.setLangMessageID("C00491");
		TGuiUtil.setComponentSize(chkHeadquartersBranchAccounting, new Dimension(105, 20));
		chkHeadquartersBranchAccounting.setMargin(new Insets(0, 0, 0, 0));
		chkHeadquartersBranchAccounting.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkHeadquartersBranchAccounting, gridBagConstraints);

		chkFundAdministration.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkFundAdministration.setLangMessageID("C00206");
		TGuiUtil.setComponentSize(chkFundAdministration, new Dimension(105, 20));
		chkFundAdministration.setMargin(new Insets(0, 0, 0, 0));
		chkFundAdministration.setTabControlNo(17);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkFundAdministration, gridBagConstraints);

		chkPortfolioManagement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPortfolioManagement.setLangMessageID("C01324");
		TGuiUtil.setComponentSize(chkPortfolioManagement, new Dimension(105, 20));
		chkPortfolioManagement.setMargin(new Insets(0, 0, 0, 0));
		chkPortfolioManagement.setTabControlNo(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(3, 5, 0, 0);
		pnlSystemDivision.add(chkPortfolioManagement, gridBagConstraints);

		pnlDetail2.add(pnlSystemDivision, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		pnlDetail3.setLayout(new GridBagLayout());

		pnlDetail3.setMaximumSize(new Dimension(600, 45));
		pnlDetail3.setMinimumSize(new Dimension(600, 45));
		pnlDetail3.setPreferredSize(new Dimension(600, 45));
		pnlAuthorityLevel.setLayout(new GridBagLayout());

		pnlAuthorityLevel.setMaximumSize(new Dimension(320, 40));
		pnlAuthorityLevel.setMinimumSize(new Dimension(320, 40));
		pnlAuthorityLevel.setPreferredSize(new Dimension(320, 40));
		ctrlUpdateAuthorityLevel.setComboSize(100);
		ctrlUpdateAuthorityLevel.setLabelSize(90);
		ctrlUpdateAuthorityLevel.setLangMessageID("C00170");
		ctrlUpdateAuthorityLevel.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		pnlAuthorityLevel.add(ctrlUpdateAuthorityLevel, gridBagConstraints);

		numProcessingAuthorityLevel.setNumericFormat("#");
		// 処理権限レベル 初期値9
		numProcessingAuthorityLevel.getField().setText("9");
		numProcessingAuthorityLevel.setFieldHAlignment(2);
		numProcessingAuthorityLevel.setFieldSize(20);
		numProcessingAuthorityLevel.setLabelSize(90);
		numProcessingAuthorityLevel.setLangMessageID("C00297");
		numProcessingAuthorityLevel.setMaxLength(1);
		numProcessingAuthorityLevel.setTabControlNo(21);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlAuthorityLevel.add(numProcessingAuthorityLevel, gridBagConstraints);

		pnlDetail3.add(pnlAuthorityLevel, new GridBagConstraints());

		pnlAccountingPersonInChargeDivision.setLayout(new GridBagLayout());

		pnlAccountingPersonInChargeDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "\u7d4c\u7406\u62c5\u5f53\u8005\u533a\u5206"));
		pnlAccountingPersonInChargeDivision.setLangMessageID("C00139");
		pnlAccountingPersonInChargeDivision.setMaximumSize(new Dimension(230, 45));
		pnlAccountingPersonInChargeDivision.setMinimumSize(new Dimension(230, 45));
		pnlAccountingPersonInChargeDivision.setPreferredSize(new Dimension(230, 45));
		rdoAccountingPersonExcluding.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpAccountingPersonInChargeDivision.add(rdoAccountingPersonExcluding);
		rdoAccountingPersonExcluding.setLangMessageID("C00140");
		rdoAccountingPersonExcluding.setMargin(new Insets(0, 0, 0, 0));
		rdoAccountingPersonExcluding.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlAccountingPersonInChargeDivision.add(rdoAccountingPersonExcluding, gridBagConstraints);
		rdoAccountingPerson.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpAccountingPersonInChargeDivision.add(rdoAccountingPerson);
		rdoAccountingPerson.setLangMessageID("C01050");
		rdoAccountingPerson.setMargin(new Insets(0, 0, 0, 0));
		rdoAccountingPerson.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlAccountingPersonInChargeDivision.add(rdoAccountingPerson, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 10);
		pnlDetail3.add(pnlAccountingPersonInChargeDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		getContentPane().add(pnlDetail3, gridBagConstraints);

		pnlDetail4.setLayout(new GridBagLayout());
		pnlDetail4.setMaximumSize(new Dimension(670, 135));
		pnlDetail4.setMinimumSize(new Dimension(670, 135));
		pnlDetail4.setPreferredSize(new Dimension(670, 135));
		ctrlCompanyCode.setButtonSize(85);
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setNoticeSize(410);
		ctrlCompanyCode.setTabControlNo(24);
		ctrlCompanyCode.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlDetail4.add(ctrlCompanyCode, gridBagConstraints);

		ctrlEmployeeCode.setButtonSize(85);
		ctrlEmployeeCode.setFieldSize(120);
		ctrlEmployeeCode.setLangMessageID("C00697");
		ctrlEmployeeCode.setMaxLength(10);
		ctrlEmployeeCode.setNoticeSize(410);
		ctrlEmployeeCode.setTabControlNo(25);
		ctrlEmployeeCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail4.add(ctrlEmployeeCode, gridBagConstraints);

		ctrlDepartmentCode.setButtonSize(85);
		ctrlDepartmentCode.setFieldSize(120);
		ctrlDepartmentCode.setLangMessageID("C00698");
		ctrlDepartmentCode.setMaxLength(10);
		ctrlDepartmentCode.setNoticeSize(410);
		ctrlDepartmentCode.setTabControlNo(26);
		ctrlDepartmentCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 5, 0);
		pnlDetail4.add(ctrlDepartmentCode, gridBagConstraints);

		dtBeginDate.setLabelSize(85);
		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(27);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlDetail4.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(85);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(28);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlDetail4.add(dtEndDate, gridBagConstraints);

		ctrlLanguageCode.setMaxLength(10);
		ctrlLanguageCode.setNoticeSize(120);
		ctrlLanguageCode.setTabControlNo(29);
		ctrlLanguageCode.setImeMode(false);
		ctrlLanguageCode.setMaximumSize(new Dimension(250, 20));
		ctrlLanguageCode.setMinimumSize(new Dimension(250, 20));
		ctrlLanguageCode.getButton().setMaximumSize(new Dimension(80, 20));
		ctrlLanguageCode.getButton().setMinimumSize(new Dimension(80, 20));
		ctrlLanguageCode.getButton().setPreferredSize(new Dimension(80, 20));
		ctrlLanguageCode.setPreferredSize(new Dimension(250, 20));
		ctrlLanguageCode.getNotice().setVisible(false);
		ctrlLanguageCode.setLangMessageID("C00699");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlDetail4.add(ctrlLanguageCode, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		getContentPane().add(pnlDetail4, gridBagConstraints);

		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtBeginDate.setValue(dtBeginDate.getCalendar().getMinimumDate());

		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtEndDate.setValue(dtEndDate.getCalendar().getMaximumDate());

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	ButtonGroup btngrpAccountingPersonInChargeDivision;

	TCheckBox chkAccountsPayableManagement;

	TCheckBox chkAccountsReceivableManagement;

	TCheckBox chkBasicAccounting;

	TCheckBox chkBillReceivable;

	TCheckBox chkBudgetManagement;

	TCheckBox chkFixedAsset;

	TCheckBox chkFundAdministration;

	TCheckBox chkGroupAccounting;

	TCheckBox chkHeadquartersBranchAccounting;

	TCheckBox chkManagementAccounting;

	TCheckBox chkMultiCurrencyAccounting;

	TCheckBox chkNotePayable;

	TCheckBox chkParentCompanyConnection;

	TCheckBox chkPortfolioManagement;

	TCheckBox chkSubsidiaryCompanyConnection;

	TButtonField ctrlCompanyCode;

	TButtonField ctrlDepartmentCode;

	TButtonField ctrlEmployeeCode;

	TLabelField ctrlPassword;

	TLabelComboBox ctrlUpdateAuthorityLevel;

	TLabelField ctrlUserAbbreviatedName;

	TLabelField ctrlUserCode;

	TLabelField ctrlUserName;

	TLabelField txtUserNameForSearch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TLabelNumericField numProcessingAuthorityLevel;

	TPanel pnlAccountingPersonInChargeDivision;

	TPanel pnlAuthorityLevel;

	TPanel pnlButton;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TPanel pnlDetail4;

	TPanel pnlSystemDivision;

	TRadioButton rdoAccountingPerson;

	TRadioButton rdoAccountingPersonExcluding;

	TButtonField ctrlLanguageCode;

}

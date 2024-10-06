package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 会社コントロールマスタ 編集画面
 * 
 * @author liuchengcheng
 */
public class MG0030EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	protected MG0030EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleid タイトルID
	 */
	public MG0030EditDisplayDialog(Frame parent, boolean modal, MG0030EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);

		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);

		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);

		// 画面の設定
		setSize(840, 500);

		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;
		btngrpTemporaryPaymentConsumptionTaxFractionProcessing = new ButtonGroup();
		btngrpTemporaryReceivingConsumptionTaxFractionProcessing = new ButtonGroup();
		btngrpRateConversionFractionProcessing = new ButtonGroup();
		pnlDetail1 = new TPanel();
		pnlNameControlItem = new TPanel();
		ctrlItemName = new TLabelField();
		ctrlSubItemName = new TLabelField();
		lblBreakDownItemManagement = new TLabel();
		chkBreakDownItemManagement = new TCheckBox();
		txtBreakDownItemManagement = new TTextField();
		chkManagementDivision3 = new TCheckBox();
		txtManagementDivision3 = new TTextField();
		chkManagementDivision6 = new TCheckBox();
		txtManagementDivision6 = new TTextField();
		ctrlNotAccountingDetailDivision3 = new TLabelComboBox();
		txtNotAccountingDetailDivision3 = new TTextField();
		chkManagementDivision1 = new TCheckBox();
		txtManagementDivision1 = new TTextField();
		chkManagementDivision4 = new TCheckBox();
		txtManagementDivision4 = new TTextField();
		ctrlNotAccountingDetailDivision1 = new TLabelComboBox();
		txtNotAccountingDetailDivision2 = new TTextField();
		chkManagementDivision2 = new TCheckBox();
		txtManagementDivision2 = new TTextField();
		chkManagementDivision5 = new TCheckBox();
		txtManagementDivision5 = new TTextField();
		ctrlNotAccountingDetailDivision2 = new TLabelComboBox();
		txtNotAccountingDetailDivision1 = new TTextField();
		pnlDetail2 = new TPanel();
		pnlDetail4 = new TPanel();
		ctrlAutomaticSettingItem1 = new TLabelComboBox();
		chkSlipPrintDivision = new TCheckBox();
		chkPrintTimeInitialValue = new TCheckBox();
		ctrlAutomaticSettingItem2 = new TLabelComboBox();
		ctrlAutomaticSettingItem3 = new TLabelComboBox();
		numAutomaticNumberingDigits = new TLabelNumericField();
		pnlDetail5 = new TPanel();
		chkSiteApproval = new TCheckBox();
		chkAdministrationApproval = new TCheckBox();
		ctrlKeyCurrency = new TButtonField();
		numBeginningOfPeriodMonth = new TLabelNumericField();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlCompanyCode = new TLabelField();
		ctrlCompany = new TTextField();
		pnlDetail3 = new TPanel();
		pnlRateConversionFractionProcessing = new TPanel();
		rdoRateConversionRoundDown = new TRadioButton();
		rdoRateConversionHalfAdjust = new TRadioButton();
		pnlTemporaryReceivingConsumptionTaxFractionProcessing = new TPanel();
		rdoTemporaryReceivingConsumptionTaxRoundDown = new TRadioButton();
		rdoTemporaryReceivingConsumptionTaxHalfAdjust = new TRadioButton();
		rdoTemporaryReceivingConsumptionRoundUp = new TRadioButton();
		pnlTemporaryPaymentConsumptionTaxFractionProcessing = new TPanel();
		rdoTemporaryPaymentConsumptionTaxRoundDown = new TRadioButton();
		rdoTemporaryPaymentConsumptionTaxHalfAdjust = new TRadioButton();

		getContentPane().setLayout(new GridBagLayout());
		setLangMessageID("C00977");
		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(820, 125));
		pnlNameControlItem.setLayout(new GridBagLayout());

		pnlNameControlItem.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlNameControlItem.setLangMessageID("C01321");
		pnlNameControlItem.setMaximumSize(new Dimension(820, 125));
		pnlNameControlItem.setMinimumSize(new Dimension(820, 125));
		pnlNameControlItem.setPreferredSize(new Dimension(820, 125));
		ctrlItemName.setFieldSize(120);
		ctrlItemName.setLabelSize(70);
		ctrlItemName.setLangMessageID("C00700");
		ctrlItemName.setMaxLength(10);
		ctrlItemName.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(ctrlItemName, gridBagConstraints);

		ctrlSubItemName.setFieldSize(120);
		ctrlSubItemName.setLabelSize(80);
		ctrlSubItemName.setLangMessageID("C00701");
		ctrlSubItemName.setMaxLength(10);
		ctrlSubItemName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(ctrlSubItemName, gridBagConstraints);

		chkBreakDownItemManagement.setMaximumSize(new java.awt.Dimension(110, 20));
		chkBreakDownItemManagement.setMinimumSize(new java.awt.Dimension(110, 20));
		chkBreakDownItemManagement.setPreferredSize(new java.awt.Dimension(110, 20));
		chkBreakDownItemManagement.setLangMessageID("C00942");
		chkBreakDownItemManagement.setHorizontalTextPosition(SwingConstants.LEFT);
		chkBreakDownItemManagement.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
		chkBreakDownItemManagement.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkBreakDownItemManagement.setMargin(new Insets(0, 0, 0, 0));
		chkBreakDownItemManagement.setTabControlNo(5);
		chkBreakDownItemManagement.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 5);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(chkBreakDownItemManagement, gridBagConstraints);

		txtBreakDownItemManagement.setMaxLength(10);
		txtBreakDownItemManagement.setMaximumSize(new Dimension(120, 20));
		txtBreakDownItemManagement.setMinimumSize(new Dimension(120, 20));
		txtBreakDownItemManagement.setPreferredSize(new Dimension(120, 20));
		txtBreakDownItemManagement.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 5;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlNameControlItem.add(txtBreakDownItemManagement, gridBagConstraints);

		chkManagementDivision3.setLangMessageID("C02361");
		chkManagementDivision3.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision3.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision3.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision3.setTabControlNo(11);
		chkManagementDivision3.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlNameControlItem.add(chkManagementDivision3, gridBagConstraints);

		txtManagementDivision3.setMaxLength(10);
		txtManagementDivision3.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision3.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision3.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision3.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 0);
		pnlNameControlItem.add(txtManagementDivision3, gridBagConstraints);

		chkManagementDivision6.setLangMessageID("C02364");
		chkManagementDivision6.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision6.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision6.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision6.setTabControlNo(17);
		chkManagementDivision6.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 10, 10, 0);
		pnlNameControlItem.add(chkManagementDivision6, gridBagConstraints);

		txtManagementDivision6.setMaxLength(10);
		txtManagementDivision6.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision6.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision6.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision6.setTabControlNo(18);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(5, 5, 10, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(txtManagementDivision6, gridBagConstraints);

		ctrlNotAccountingDetailDivision3.setComboSize(120);
		ctrlNotAccountingDetailDivision3.setLabelSize(100);
		ctrlNotAccountingDetailDivision3.setLangMessageID("C02393");
		ctrlNotAccountingDetailDivision3.setTabControlNo(23);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 10, 10, 0);
		pnlNameControlItem.add(ctrlNotAccountingDetailDivision3, gridBagConstraints);

		txtNotAccountingDetailDivision3.setMaxLength(10);
		txtNotAccountingDetailDivision3.setMaximumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision3.setMinimumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision3.setPreferredSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision3.setTabControlNo(24);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(5, 1, 10, 0);
		pnlNameControlItem.add(txtNotAccountingDetailDivision3, gridBagConstraints);

		chkManagementDivision1.setLangMessageID("C02359");
		chkManagementDivision1.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision1.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision1.setTabControlNo(7);
		chkManagementDivision1.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlNameControlItem.add(chkManagementDivision1, gridBagConstraints);

		txtManagementDivision1.setMaxLength(10);
		txtManagementDivision1.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision1.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision1.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision1.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlNameControlItem.add(txtManagementDivision1, gridBagConstraints);

		chkManagementDivision4.setLangMessageID("C02362");
		chkManagementDivision4.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision4.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision4.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision4.setTabControlNo(13);
		chkManagementDivision4.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlNameControlItem.add(chkManagementDivision4, gridBagConstraints);

		txtManagementDivision4.setMaxLength(10);
		txtManagementDivision4.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision4.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision4.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision4.setTabControlNo(14);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(txtManagementDivision4, gridBagConstraints);

		ctrlNotAccountingDetailDivision1.setComboSize(120);
		ctrlNotAccountingDetailDivision1.setLabelSize(100);
		ctrlNotAccountingDetailDivision1.setLangMessageID("C00943");
		ctrlNotAccountingDetailDivision1.setTabControlNo(19);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlNameControlItem.add(ctrlNotAccountingDetailDivision1, gridBagConstraints);

		txtNotAccountingDetailDivision2.setMaxLength(10);
		txtNotAccountingDetailDivision2.setMaximumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision2.setMinimumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision2.setPreferredSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision2.setTabControlNo(22);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 1, 0, 0);
		pnlNameControlItem.add(txtNotAccountingDetailDivision2, gridBagConstraints);

		chkManagementDivision2.setLangMessageID("C02360");
		chkManagementDivision2.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision2.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision2.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision2.setTabControlNo(9);
		chkManagementDivision2.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlNameControlItem.add(chkManagementDivision2, gridBagConstraints);

		txtManagementDivision2.setMaxLength(10);
		txtManagementDivision2.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision2.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision2.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision2.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlNameControlItem.add(txtManagementDivision2, gridBagConstraints);

		chkManagementDivision5.setLangMessageID("C02363");
		chkManagementDivision5.setHorizontalTextPosition(SwingConstants.LEFT);
		chkManagementDivision5.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkManagementDivision5.setMargin(new Insets(0, 0, 0, 0));
		chkManagementDivision5.setTabControlNo(15);
		chkManagementDivision5.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkClicked();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlNameControlItem.add(chkManagementDivision5, gridBagConstraints);

		txtManagementDivision5.setMaxLength(10);
		txtManagementDivision5.setMaximumSize(new Dimension(120, 20));
		txtManagementDivision5.setMinimumSize(new Dimension(120, 20));
		txtManagementDivision5.setPreferredSize(new Dimension(120, 20));
		txtManagementDivision5.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.EAST;
		pnlNameControlItem.add(txtManagementDivision5, gridBagConstraints);

		ctrlNotAccountingDetailDivision2.setComboSize(120);
		ctrlNotAccountingDetailDivision2.setLabelSize(100);
		ctrlNotAccountingDetailDivision2.setLangMessageID("C02392");
		ctrlNotAccountingDetailDivision2.setTabControlNo(21);
		ctrlNotAccountingDetailDivision2.setTabEnabled(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 4;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 10, 0, 0);
		pnlNameControlItem.add(ctrlNotAccountingDetailDivision2, gridBagConstraints);

		txtNotAccountingDetailDivision1.setMaxLength(10);
		txtNotAccountingDetailDivision1.setMaximumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision1.setMinimumSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision1.setPreferredSize(new Dimension(120, 20));
		txtNotAccountingDetailDivision1.setTabControlNo(20);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 6;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 1, 0, 0);
		pnlNameControlItem.add(txtNotAccountingDetailDivision1, gridBagConstraints);

		pnlDetail1.add(pnlNameControlItem, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(780, 95));
		pnlDetail4.setLayout(new GridBagLayout());

		pnlDetail4.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u4f1d\u7968\u95a2\u9023\u8a2d\u5b9a\u9805\u76ee"));
		pnlDetail4.setLangMessageID("C01252");
		pnlDetail4.setMaximumSize(new Dimension(370, 95));
		pnlDetail4.setMinimumSize(new Dimension(370, 95));
		pnlDetail4.setPreferredSize(new Dimension(370, 95));
		ctrlAutomaticSettingItem1.setComboSize(105);
		ctrlAutomaticSettingItem1.setLabelSize(90);
		ctrlAutomaticSettingItem1.setLangMessageID("C00713");
		ctrlAutomaticSettingItem1.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		pnlDetail4.add(ctrlAutomaticSettingItem1, gridBagConstraints);

		chkSlipPrintDivision.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSlipPrintDivision.setLangMessageID("C01248");
		chkSlipPrintDivision.setMargin(new Insets(0, 0, 0, 0));
		chkSlipPrintDivision.setTabControlNo(26);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 0);
		pnlDetail4.add(chkSlipPrintDivision, gridBagConstraints);

		chkPrintTimeInitialValue.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkPrintTimeInitialValue.setLangMessageID("C01000");
		chkPrintTimeInitialValue.setMargin(new Insets(0, 0, 0, 0));
		chkPrintTimeInitialValue.setTabControlNo(27);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 0);
		pnlDetail4.add(chkPrintTimeInitialValue, gridBagConstraints);

		ctrlAutomaticSettingItem2.setComboSize(105);
		ctrlAutomaticSettingItem2.setLabelSize(90);
		ctrlAutomaticSettingItem2.setLangMessageID("C00714");
		ctrlAutomaticSettingItem2.setTabControlNo(28);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail4.add(ctrlAutomaticSettingItem2, gridBagConstraints);

		ctrlAutomaticSettingItem3.setComboSize(105);
		ctrlAutomaticSettingItem3.setLabelSize(90);
		ctrlAutomaticSettingItem3.setLangMessageID("C00715");
		ctrlAutomaticSettingItem3.setTabControlNo(29);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlDetail4.add(ctrlAutomaticSettingItem3, gridBagConstraints);

		numAutomaticNumberingDigits.setNumericFormat("##");
		numAutomaticNumberingDigits.setFieldHAlignment(2);
		numAutomaticNumberingDigits.setFieldSize(25);
		numAutomaticNumberingDigits.setLabelSize(120);
		numAutomaticNumberingDigits.setLangMessageID("C00224");
		numAutomaticNumberingDigits.setMaxLength(2);
		numAutomaticNumberingDigits.setTabControlNo(25);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail4.add(numAutomaticNumberingDigits, gridBagConstraints);

		pnlDetail2.add(pnlDetail4, new GridBagConstraints());

		pnlDetail5.setLayout(new GridBagLayout());

		pnlDetail5.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u305d\u306e\u4ed6\u5236\u5fa1\u9805\u76ee"));
		pnlDetail5.setLangMessageID("C00983");
		pnlDetail5.setMaximumSize(new Dimension(450, 95));
		pnlDetail5.setMinimumSize(new Dimension(450, 95));
		pnlDetail5.setPreferredSize(new Dimension(450, 95));
		chkSiteApproval.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkSiteApproval.setLangMessageID("C00157");
		chkSiteApproval.setMargin(new Insets(0, 0, 0, 0));
		chkSiteApproval.setTabControlNo(32);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 49, 0, 0);
		pnlDetail5.add(chkSiteApproval, gridBagConstraints);

		chkAdministrationApproval.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		chkAdministrationApproval.setLangMessageID("C01616");
		chkAdministrationApproval.setMargin(new Insets(0, 0, 0, 0));
		chkAdministrationApproval.setTabControlNo(33);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		pnlDetail5.add(chkAdministrationApproval, gridBagConstraints);

		ctrlKeyCurrency.setButtonSize(85);
		ctrlKeyCurrency.setFieldSize(45);
		ctrlKeyCurrency.setLangMessageID("C00494");
		ctrlKeyCurrency.setMaxLength(3);
		ctrlKeyCurrency.setNoticeSize(300);
		ctrlKeyCurrency.setTabControlNo(34);
		ctrlKeyCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.insets = new Insets(5, 0, 10, 0);
		pnlDetail5.add(ctrlKeyCurrency, gridBagConstraints);

		numBeginningOfPeriodMonth.setNumericFormat("##");
		numBeginningOfPeriodMonth.setFieldHAlignment(2);
		numBeginningOfPeriodMonth.setFieldSize(25);
		numBeginningOfPeriodMonth.setLabelSize(85);
		numBeginningOfPeriodMonth.setLangMessageID("C00105");
		numBeginningOfPeriodMonth.setMaxLength(2);
		numBeginningOfPeriodMonth.setTabControlNo(31);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		pnlDetail5.add(numBeginningOfPeriodMonth, gridBagConstraints);

		pnlDetail2.add(pnlDetail5, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));
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
		gridBagConstraints.insets = new Insets(10, 500, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(39);
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
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(780, 25));
		pnlHeader.setMinimumSize(new Dimension(780, 25));
		pnlHeader.setPreferredSize(new Dimension(780, 25));
		ctrlCompanyCode.setEditable(false);
		ctrlCompanyCode.setEnabled(false);
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLabelSize(60);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setTabControlNo(1);
		ctrlCompanyCode.setTabEnabled(false);
		pnlHeader.add(ctrlCompanyCode, new GridBagConstraints());

		ctrlCompany.setEnabled(true);
		ctrlCompany.setEditable(false);
		ctrlCompany.setMaxLength(40);
		ctrlCompany.setMaximumSize(new Dimension(410, 20));
		ctrlCompany.setMinimumSize(new Dimension(410, 20));
		ctrlCompany.setPreferredSize(new Dimension(410, 20));
		ctrlCompany.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 130);
		pnlHeader.add(ctrlCompany, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail3.setLayout(new GridBagLayout());

		pnlDetail3.setMaximumSize(new Dimension(780, 40));
		pnlDetail3.setMinimumSize(new Dimension(780, 40));
		pnlDetail3.setPreferredSize(new Dimension(780, 40));
		pnlRateConversionFractionProcessing.setLayout(new GridBagLayout());

		pnlRateConversionFractionProcessing.setBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "\u30ec\u30fc\u30c8\u63db\u7b97\u7aef\u6570\u51e6\u7406"));
		pnlRateConversionFractionProcessing.setLangMessageID("C00557");
		pnlRateConversionFractionProcessing.setMaximumSize(new Dimension(160, 40));
		pnlRateConversionFractionProcessing.setMinimumSize(new Dimension(160, 40));
		pnlRateConversionFractionProcessing.setPreferredSize(new Dimension(160, 40));
		rdoRateConversionRoundDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpRateConversionFractionProcessing.add(rdoRateConversionRoundDown);
		rdoRateConversionRoundDown.setSelected(true);
		rdoRateConversionRoundDown.setLangMessageID("C00121");
		rdoRateConversionRoundDown.setMargin(new Insets(0, 0, 0, 0));
		rdoRateConversionRoundDown.setTabControlNo(35);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlRateConversionFractionProcessing.add(rdoRateConversionRoundDown, gridBagConstraints);

		rdoRateConversionHalfAdjust.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpRateConversionFractionProcessing.add(rdoRateConversionHalfAdjust);
		rdoRateConversionHalfAdjust.setLangMessageID("C00215");
		rdoRateConversionHalfAdjust.setMargin(new Insets(0, 0, 0, 0));
		rdoRateConversionHalfAdjust.setTabControlNo(35);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlRateConversionFractionProcessing.add(rdoRateConversionHalfAdjust, gridBagConstraints);

		pnlDetail3.add(pnlRateConversionFractionProcessing, new GridBagConstraints());

		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setLayout(new GridBagLayout());

		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "\u4eee\u53d7\u6d88\u8cbb\u7a0e\u7aef\u6570\u51e6\u7406"));
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setLangMessageID("C00082");
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setMaximumSize(new Dimension(200, 40));
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setMinimumSize(new Dimension(200, 40));
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.setPreferredSize(new Dimension(200, 40));
		rdoTemporaryReceivingConsumptionTaxRoundDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionTaxRoundDown);
		rdoTemporaryReceivingConsumptionTaxRoundDown.setSelected(true);
		rdoTemporaryReceivingConsumptionTaxRoundDown.setLangMessageID("C00121");
		rdoTemporaryReceivingConsumptionTaxRoundDown.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporaryReceivingConsumptionTaxRoundDown.setTabControlNo(36);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionTaxRoundDown,
			gridBagConstraints);

		rdoTemporaryReceivingConsumptionRoundUp.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionRoundUp);
		rdoTemporaryReceivingConsumptionRoundUp.setLangMessageID("C00120");
		rdoTemporaryReceivingConsumptionRoundUp.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporaryReceivingConsumptionRoundUp.setTabControlNo(36);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionRoundUp,
			gridBagConstraints);

		rdoTemporaryReceivingConsumptionTaxHalfAdjust.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionTaxHalfAdjust);
		rdoTemporaryReceivingConsumptionTaxHalfAdjust.setLangMessageID("C00215");
		rdoTemporaryReceivingConsumptionTaxHalfAdjust.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporaryReceivingConsumptionTaxHalfAdjust.setTabControlNo(36);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlTemporaryReceivingConsumptionTaxFractionProcessing.add(rdoTemporaryReceivingConsumptionTaxHalfAdjust,
			gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlDetail3.add(pnlTemporaryReceivingConsumptionTaxFractionProcessing, gridBagConstraints);

		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setLayout(new GridBagLayout());

		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "\u4eee\u6255\u6d88\u8cbb\u7a0e\u7aef\u6570\u51e6\u7406"));
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setLangMessageID("C00083");
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setMaximumSize(new Dimension(160, 40));
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setMinimumSize(new Dimension(160, 40));
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.setPreferredSize(new Dimension(160, 40));
		rdoTemporaryPaymentConsumptionTaxRoundDown.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTemporaryPaymentConsumptionTaxFractionProcessing.add(rdoTemporaryPaymentConsumptionTaxRoundDown);
		rdoTemporaryPaymentConsumptionTaxRoundDown.setSelected(true);
		rdoTemporaryPaymentConsumptionTaxRoundDown.setLangMessageID("C00121");
		rdoTemporaryPaymentConsumptionTaxRoundDown.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporaryPaymentConsumptionTaxRoundDown.setTabControlNo(37);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.add(rdoTemporaryPaymentConsumptionTaxRoundDown,
			gridBagConstraints);

		rdoTemporaryPaymentConsumptionTaxHalfAdjust.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTemporaryPaymentConsumptionTaxFractionProcessing.add(rdoTemporaryPaymentConsumptionTaxHalfAdjust);
		rdoTemporaryPaymentConsumptionTaxHalfAdjust.setLangMessageID("C00215");
		rdoTemporaryPaymentConsumptionTaxHalfAdjust.setMargin(new Insets(0, 0, 0, 0));
		rdoTemporaryPaymentConsumptionTaxHalfAdjust.setTabControlNo(37);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlTemporaryPaymentConsumptionTaxFractionProcessing.add(rdoTemporaryPaymentConsumptionTaxHalfAdjust,
			gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 0, 0);
		pnlDetail3.add(pnlTemporaryPaymentConsumptionTaxFractionProcessing, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(0, 0, 15, 0);
		getContentPane().add(pnlDetail3, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	ButtonGroup btngrpRateConversionFractionProcessing;

	ButtonGroup btngrpTemporaryPaymentConsumptionTaxFractionProcessing;

	ButtonGroup btngrpTemporaryReceivingConsumptionTaxFractionProcessing;

	TCheckBox chkAdministrationApproval;

	TCheckBox chkBreakDownItemManagement;

	TCheckBox chkManagementDivision1;

	TCheckBox chkManagementDivision2;

	TCheckBox chkManagementDivision3;

	TCheckBox chkManagementDivision4;

	TCheckBox chkManagementDivision5;

	TCheckBox chkManagementDivision6;

	TCheckBox chkPrintTimeInitialValue;

	TCheckBox chkSiteApproval;

	TCheckBox chkSlipPrintDivision;

	TLabelComboBox ctrlAutomaticSettingItem1;

	TLabelComboBox ctrlAutomaticSettingItem2;

	TLabelComboBox ctrlAutomaticSettingItem3;

	TLabelField ctrlCompanyCode;

	TLabelField ctrlItemName;

	TButtonField ctrlKeyCurrency;

	TLabelComboBox ctrlNotAccountingDetailDivision1;

	TLabelComboBox ctrlNotAccountingDetailDivision2;

	TLabelComboBox ctrlNotAccountingDetailDivision3;

	TLabelField ctrlSubItemName;

	TLabel lblBreakDownItemManagement;

	TLabelNumericField numAutomaticNumberingDigits;

	TLabelNumericField numBeginningOfPeriodMonth;

	TPanel pnlButton;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TPanel pnlDetail4;

	TPanel pnlDetail5;

	TPanel pnlHeader;

	TPanel pnlNameControlItem;

	TPanel pnlRateConversionFractionProcessing;

	TPanel pnlTemporaryPaymentConsumptionTaxFractionProcessing;

	TPanel pnlTemporaryReceivingConsumptionTaxFractionProcessing;

	TRadioButton rdoRateConversionHalfAdjust;

	TRadioButton rdoRateConversionRoundDown;

	TRadioButton rdoTemporaryPaymentConsumptionTaxHalfAdjust;

	TRadioButton rdoTemporaryPaymentConsumptionTaxRoundDown;

	TRadioButton rdoTemporaryReceivingConsumptionRoundUp;

	TRadioButton rdoTemporaryReceivingConsumptionTaxHalfAdjust;

	TRadioButton rdoTemporaryReceivingConsumptionTaxRoundDown;

	TTextField txtBreakDownItemManagement;

	TTextField ctrlCompany;

	TTextField txtManagementDivision1;

	TTextField txtManagementDivision2;

	TTextField txtManagementDivision3;

	TTextField txtManagementDivision4;

	TTextField txtManagementDivision5;

	TTextField txtManagementDivision6;

	TTextField txtNotAccountingDetailDivision1;

	TTextField txtNotAccountingDetailDivision2;

	TTextField txtNotAccountingDetailDivision3;

}

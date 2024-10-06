package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 決算コントロールマスタ編集ダイアログ
 * 
 * @author ISFnet China
 */
public class MG0040EditDisplayDialog extends TDialog {

	/** シリアルUID */
	protected static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	protected MG0040EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 */

	/**
	 * Creates new form MG0040EditDisplayDialog
	 * 
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param titleid
	 */
	public MG0040EditDisplayDialog(Frame parent, boolean modal, MG0040EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(650, 450);
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpClosingAccountsStageDivision = new ButtonGroup();
		btngrpSettlementSlipInputDivision = new ButtonGroup();
		btngrpLedgerEachDayBalanceDisplayDivision = new ButtonGroup();
		btngrpEvaluationRateDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();
		pnlDetail1 = new TPanel();
		ctrlCompanyCode = new TLabelField();
		txtCompanyName = new TTextField();
		pnlDetail2 = new TPanel();
		pnlClosingAccountsStageDivision = new TPanel();
		rdoDo = new TRadioButton();
		numClosingAccountsStage = new TLabelNumericField();
		lblNext = new TLabel();
		rdoDonot = new TRadioButton();
		pnlSettlementSlipInputDivision = new TPanel();
		rdoOneYear = new TRadioButton();
		rdoHalfYear = new TRadioButton();
		rdoQuarter = new TRadioButton();
		rdoMonthly = new TRadioButton();
		pnlDetail3 = new TPanel();
		pnlLedgerEachDayBalanceDisplayDivision = new TPanel();
		rdoVisible = new TRadioButton();
		rdoNotVisible = new TRadioButton();
		pnlRevaluationRateDivision = new TPanel();
		rdoEndMonthRate = new TRadioButton();
		rdoNextMonthBeginRate = new TRadioButton();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(490, 40));
		pnlButton.setMinimumSize(new Dimension(490, 40));
		pnlButton.setPreferredSize(new Dimension(490, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(8);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 166, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 0);
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

		pnlDetail1.setMaximumSize(new Dimension(630, 30));
		pnlDetail1.setMinimumSize(new Dimension(630, 30));
		pnlDetail1.setPreferredSize(new Dimension(630, 30));
		ctrlCompanyCode.setEditable(false);
		ctrlCompanyCode.setEnabled(true);
		ctrlCompanyCode.setFieldSize(120);
		ctrlCompanyCode.setLabelSize(60);
		ctrlCompanyCode.setLangMessageID("C00596");
		ctrlCompanyCode.setMaxLength(10);
		ctrlCompanyCode.setTabControlNo(1);
		pnlDetail1.add(ctrlCompanyCode, new GridBagConstraints());

		txtCompanyName.setEnabled(true);
		txtCompanyName.setEditable(false);
		txtCompanyName.setMaxLength(40);
		txtCompanyName.setMaximumSize(new Dimension(410, 20));
		txtCompanyName.setMinimumSize(new Dimension(410, 20));
		txtCompanyName.setPreferredSize(new Dimension(410, 20));
		txtCompanyName.setTabControlNo(2);
		pnlDetail1.add(txtCompanyName, new GridBagConstraints());

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(450, 70));
		pnlDetail2.setMinimumSize(new Dimension(450, 70));
		pnlDetail2.setPreferredSize(new Dimension(450, 70));
		pnlClosingAccountsStageDivision.setLayout(new GridBagLayout());

		pnlClosingAccountsStageDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u6c7a\u7b97\u6bb5\u968e\u533a\u5206"));
		pnlClosingAccountsStageDivision.setLangMessageID("C00145");
		pnlClosingAccountsStageDivision.setMaximumSize(new Dimension(150, 70));
		pnlClosingAccountsStageDivision.setMinimumSize(new Dimension(150, 70));
		pnlClosingAccountsStageDivision.setPreferredSize(new Dimension(150, 70));
		rdoDo.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpClosingAccountsStageDivision.add(rdoDo);
		rdoDo.setSelected(true);
		rdoDo.setLangMessageID("C00037");
		rdoDo.setTabControlNo(3);
		rdoDo.setMargin(new Insets(0, 0, 0, 0));
		rdoDo.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.selectedSet();
			}
		});
		pnlClosingAccountsStageDivision.add(rdoDo, new GridBagConstraints());

		numClosingAccountsStage.setNumericFormat("#");
		numClosingAccountsStage.setFieldHAlignment(2);
		numClosingAccountsStage.setFieldSize(20);
		numClosingAccountsStage.setLabelSize(0);
		numClosingAccountsStage.setLangMessageID("numClosingAccountsStage");
		numClosingAccountsStage.setMaxLength(1);
		numClosingAccountsStage.setTabControlNo(3);
		pnlClosingAccountsStageDivision.add(numClosingAccountsStage, new GridBagConstraints());

		lblNext.setLangMessageID("C00373");
		pnlClosingAccountsStageDivision.add(lblNext, new GridBagConstraints());

		rdoDonot.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpClosingAccountsStageDivision.add(rdoDonot);
		rdoDonot.setTabControlNo(4);
		rdoDonot.setLangMessageID("C00038");
		rdoDonot.setMargin(new Insets(0, 0, 0, 0));
		rdoDonot.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.selectedSet();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlClosingAccountsStageDivision.add(rdoDonot, gridBagConstraints);

		pnlDetail2.add(pnlClosingAccountsStageDivision, new GridBagConstraints());

		pnlSettlementSlipInputDivision.setLayout(new GridBagLayout());

		pnlSettlementSlipInputDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u6c7a\u7b97\u4f1d\u7968\u5165\u529b\u533a\u5206"));
		pnlSettlementSlipInputDivision.setLangMessageID("C01056");
		pnlSettlementSlipInputDivision.setMaximumSize(new Dimension(270, 45));
		pnlSettlementSlipInputDivision.setMinimumSize(new Dimension(270, 45));
		pnlSettlementSlipInputDivision.setPreferredSize(new Dimension(270, 45));
		rdoOneYear.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSettlementSlipInputDivision.add(rdoOneYear);
		rdoOneYear.setSelected(true);
		rdoOneYear.setLangMessageID("C00009");
		rdoOneYear.setMargin(new Insets(0, 0, 0, 0));
		rdoOneYear.setTabControlNo(5);
		pnlSettlementSlipInputDivision.add(rdoOneYear, new GridBagConstraints());

		rdoHalfYear.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSettlementSlipInputDivision.add(rdoHalfYear);
		rdoHalfYear.setLangMessageID("C00435");
		rdoHalfYear.setMargin(new Insets(0, 0, 0, 0));
		rdoHalfYear.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlSettlementSlipInputDivision.add(rdoHalfYear, gridBagConstraints);

		rdoQuarter.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSettlementSlipInputDivision.add(rdoQuarter);
		rdoQuarter.setLangMessageID("C00239");
		rdoQuarter.setMargin(new Insets(0, 0, 0, 0));
		rdoQuarter.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlSettlementSlipInputDivision.add(rdoQuarter, gridBagConstraints);

		rdoMonthly.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpSettlementSlipInputDivision.add(rdoMonthly);
		rdoMonthly.setLangMessageID("C00147");
		rdoMonthly.setMargin(new Insets(0, 0, 0, 0));
		rdoMonthly.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlSettlementSlipInputDivision.add(rdoMonthly, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlDetail2.add(pnlSettlementSlipInputDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		pnlDetail3.setLayout(new GridBagLayout());

		pnlDetail3.setMaximumSize(new Dimension(450, 45));
		pnlDetail3.setMinimumSize(new Dimension(450, 45));
		pnlDetail3.setPreferredSize(new Dimension(450, 45));
		pnlLedgerEachDayBalanceDisplayDivision.setLayout(new GridBagLayout());

		pnlLedgerEachDayBalanceDisplayDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory
			.createEtchedBorder(), "\u5143\u5e33\u65e5\u5225\u6b8b\u9ad8\u8868\u793a\u533a\u5206"));
		pnlLedgerEachDayBalanceDisplayDivision.setLangMessageID("C00524");
		pnlLedgerEachDayBalanceDisplayDivision.setMaximumSize(new Dimension(210, 45));
		pnlLedgerEachDayBalanceDisplayDivision.setMinimumSize(new Dimension(210, 45));
		pnlLedgerEachDayBalanceDisplayDivision.setPreferredSize(new Dimension(210, 45));
		rdoVisible.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpLedgerEachDayBalanceDisplayDivision.add(rdoVisible);
		rdoVisible.setSelected(true);
		rdoVisible.setLangMessageID("C00455");
		rdoVisible.setMargin(new Insets(0, 0, 0, 0));
		rdoVisible.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlLedgerEachDayBalanceDisplayDivision.add(rdoVisible, gridBagConstraints);

		rdoNotVisible.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpLedgerEachDayBalanceDisplayDivision.add(rdoNotVisible);
		rdoNotVisible.setLangMessageID("C00456");
		rdoNotVisible.setMargin(new Insets(0, 0, 0, 0));
		rdoNotVisible.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlLedgerEachDayBalanceDisplayDivision.add(rdoNotVisible, gridBagConstraints);

		pnlDetail3.add(pnlLedgerEachDayBalanceDisplayDivision, new GridBagConstraints());

		pnlRevaluationRateDivision.setLayout(new GridBagLayout());

		pnlRevaluationRateDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(),
			"\u8a55\u4fa1\u66ff\u30ec\u30fc\u30c8\u533a\u5206"));
		pnlRevaluationRateDivision.setLangMessageID("C00454");
		pnlRevaluationRateDivision.setMaximumSize(new Dimension(210, 45));
		pnlRevaluationRateDivision.setMinimumSize(new Dimension(210, 45));
		pnlRevaluationRateDivision.setPreferredSize(new Dimension(210, 45));
		rdoEndMonthRate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpEvaluationRateDivision.add(rdoEndMonthRate);
		rdoEndMonthRate.setSelected(true);
		rdoEndMonthRate.setLangMessageID("C00148");
		rdoEndMonthRate.setMargin(new Insets(0, 0, 0, 0));
		rdoEndMonthRate.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlRevaluationRateDivision.add(rdoEndMonthRate, gridBagConstraints);

		rdoNextMonthBeginRate.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpEvaluationRateDivision.add(rdoNextMonthBeginRate);
		rdoNextMonthBeginRate.setLangMessageID("C00535");
		rdoNextMonthBeginRate.setMargin(new Insets(0, 0, 0, 0));
		rdoNextMonthBeginRate.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 5, 0);
		pnlRevaluationRateDivision.add(rdoNextMonthBeginRate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 10, 0, 0);
		pnlDetail3.add(pnlRevaluationRateDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(0, 0, 15, 0);
		getContentPane().add(pnlDetail3, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TButton btnSettle;

	ButtonGroup btngrpClosingAccountsStageDivision;

	ButtonGroup btngrpEvaluationRateDivision;

	ButtonGroup btngrpLedgerEachDayBalanceDisplayDivision;

	ButtonGroup btngrpSettlementSlipInputDivision;

	TLabelField ctrlCompanyCode;

	TLabel lblNext;

	TLabelNumericField numClosingAccountsStage;

	TPanel pnlButton;

	TPanel pnlClosingAccountsStageDivision;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TPanel pnlLedgerEachDayBalanceDisplayDivision;

	TPanel pnlRevaluationRateDivision;

	TPanel pnlSettlementSlipInputDivision;

	TRadioButton rdoDo;

	TRadioButton rdoDonot;

	TRadioButton rdoEndMonthRate;

	TRadioButton rdoHalfYear;

	TRadioButton rdoMonthly;

	TRadioButton rdoNextMonthBeginRate;

	TRadioButton rdoNotVisible;

	TRadioButton rdoOneYear;

	TRadioButton rdoQuarter;

	TRadioButton rdoVisible;

	TTextField txtCompanyName;

}

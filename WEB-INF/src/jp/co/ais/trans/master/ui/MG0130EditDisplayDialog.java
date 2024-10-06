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
 * 消費税マスタ編集ダイアログ
 * 
 * @author ISFnet China
 */
public class MG0130EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0130EditDisplayDialogCtrl ctrl;

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
	MG0130EditDisplayDialog(Frame parent, boolean modal, MG0130EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(620, 450);

		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpConsumptionTaxAccountSheet = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail = new TPanel();
		pnlConsumptionTaxAccountSheet = new TPanel();
		rdoDisUse = new TRadioButton();
		rdoUse = new TRadioButton();
		numOutputOrder = new TLabelNumericField();
		ctrlSalesPurchesesDivision = new TLabelComboBox();
		ctrlConsumptionTaxCode = new TLabelField();
		ctrlConsumptionTaxName = new TLabelField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlConsumptionTaxNameForSearch = createForSearchCtrl();
		ctrlConsumptionTaxAbbreviationName = new TLabelField();
		numConsumptionTaxRate = new TLabelNumericField();
		lblConsumptionTaxRate = new TLabel();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(610, 40));
		pnlButton.setMinimumSize(new Dimension(610, 40));
		pnlButton.setPreferredSize(new Dimension(610, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 320, 5, 0);
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
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(13);
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
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

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(650, 275));
		pnlDetail.setMinimumSize(new Dimension(650, 275));
		pnlDetail.setPreferredSize(new Dimension(650, 275));
		pnlConsumptionTaxAccountSheet.setLayout(new GridBagLayout());

		pnlConsumptionTaxAccountSheet.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlConsumptionTaxAccountSheet.setLangMessageID("C01176");
		pnlConsumptionTaxAccountSheet.setMaximumSize(new Dimension(410, 50));
		pnlConsumptionTaxAccountSheet.setMinimumSize(new Dimension(410, 50));
		pnlConsumptionTaxAccountSheet.setPreferredSize(new Dimension(410, 50));

		// 使用しない
		rdoDisUse.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		TGuiUtil.setComponentSize(rdoDisUse, 105, 15);
		btngrpConsumptionTaxAccountSheet.add(rdoDisUse);
		rdoDisUse.setSelected(true);
		rdoDisUse.setIconTextGap(6);
		rdoDisUse.setTabControlNo(6);
		rdoDisUse.setLangMessageID("C00282");
		rdoDisUse.setMargin(new Insets(0, 0, 0, 0));
		rdoDisUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.selectedSet();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlConsumptionTaxAccountSheet.add(rdoDisUse, gridBagConstraints);

		// 使用する
		rdoUse.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		TGuiUtil.setComponentSize(rdoUse, 85, 15);
		btngrpConsumptionTaxAccountSheet.add(rdoUse);
		rdoUse.setIconTextGap(7);
		rdoUse.setTabControlNo(7);
		rdoUse.setLangMessageID("C00281");
		rdoUse.setMargin(new Insets(0, 0, 0, 0));
		rdoUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.selectedSet();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 5, 0);
		pnlConsumptionTaxAccountSheet.add(rdoUse, gridBagConstraints);

		numOutputOrder.setFieldSize(20);
		numOutputOrder.setFieldHAlignment(2);
		numOutputOrder.setLabelSize(65);
		numOutputOrder.setLangMessageID("C00776");
		numOutputOrder.setNumericFormat("#");
		numOutputOrder.setMaxLength(1);
		numOutputOrder.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 20, 7, 0);
		pnlConsumptionTaxAccountSheet.add(numOutputOrder, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridwidth = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 128, 0, 0);
		pnlDetail.add(pnlConsumptionTaxAccountSheet, gridBagConstraints);

		ctrlSalesPurchesesDivision.setComboSize(75);
		ctrlSalesPurchesesDivision.setLabelSize(125);
		ctrlSalesPurchesesDivision.setLangMessageID("C01283");
		ctrlSalesPurchesesDivision.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.gridwidth = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlSalesPurchesesDivision, gridBagConstraints);

		ctrlConsumptionTaxCode.setFieldSize(35);
		ctrlConsumptionTaxCode.setLabelSize(125);
		ctrlConsumptionTaxCode.setLangMessageID("C00573");
		ctrlConsumptionTaxCode.getField().setAllowedSpace(false);
		ctrlConsumptionTaxCode.setMaxLength(2);
		ctrlConsumptionTaxCode.setTabControlNo(1);
		ctrlConsumptionTaxCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlConsumptionTaxCode, gridBagConstraints);

		ctrlConsumptionTaxName.setFieldSize(450);
		ctrlConsumptionTaxName.setLabelSize(125);
		ctrlConsumptionTaxName.setLangMessageID("C00774");
		ctrlConsumptionTaxName.setMaxLength(40);
		ctrlConsumptionTaxName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlConsumptionTaxName, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setLabelSize(125);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(10);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setLabelSize(125);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(11);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 20, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		ctrlConsumptionTaxNameForSearch.setFieldSize(450);
		ctrlConsumptionTaxNameForSearch.setLabelSize(125);
		ctrlConsumptionTaxNameForSearch.setLangMessageID("C00828");
		ctrlConsumptionTaxNameForSearch.setMaxLength(40);
		ctrlConsumptionTaxNameForSearch.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlConsumptionTaxNameForSearch, gridBagConstraints);

		ctrlConsumptionTaxAbbreviationName.setFieldSize(230);
		ctrlConsumptionTaxAbbreviationName.setLabelSize(125);
		ctrlConsumptionTaxAbbreviationName.setLangMessageID("C00775");
		ctrlConsumptionTaxAbbreviationName.setMaxLength(20);
		ctrlConsumptionTaxAbbreviationName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlConsumptionTaxAbbreviationName, gridBagConstraints);

		numConsumptionTaxRate.setFieldHAlignment(-1);
		numConsumptionTaxRate.setFieldSize(35);
		numConsumptionTaxRate.setFieldHAlignment(2);
		numConsumptionTaxRate.setLabelSize(125);
		numConsumptionTaxRate.setLangMessageID("C00777");
		numConsumptionTaxRate.setMaxLength(3);
		numConsumptionTaxRate.setTabControlNo(9);
		numConsumptionTaxRate.setNumericFormat("##,###,##0.0");
		numConsumptionTaxRate.setValue(("0.0"));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(numConsumptionTaxRate, gridBagConstraints);

		lblConsumptionTaxRate.setLangMessageID("C01335");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(lblConsumptionTaxRate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 0, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

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

	ButtonGroup btngrpConsumptionTaxAccountSheet;

	TLabelField ctrlConsumptionTaxAbbreviationName;

	TLabelField ctrlConsumptionTaxCode;

	TLabelField ctrlConsumptionTaxName;

	TLabelField ctrlConsumptionTaxNameForSearch;

	TLabelComboBox ctrlSalesPurchesesDivision;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TLabel lblConsumptionTaxRate;

	TLabelNumericField numConsumptionTaxRate;

	TLabelNumericField numOutputOrder;

	TPanel pnlButton;

	TPanel pnlConsumptionTaxAccountSheet;

	TPanel pnlDetail;

	TRadioButton rdoDisUse;

	TRadioButton rdoUse;

}

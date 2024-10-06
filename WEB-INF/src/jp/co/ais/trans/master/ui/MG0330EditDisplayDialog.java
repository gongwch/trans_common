package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 伝票種別マスタ
 */
public class MG0330EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = 5579217218434816273L;

	/** コントロールクラス */
	private MG0330EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent 親フレーム
	 * @param modal モーダルダイアログ可否(true: モーダルモード)
	 * @param ctrl コントロールクラス
	 * @param titleID
	 */
	MG0330EditDisplayDialog(Frame parent, boolean modal, MG0330EditDisplayDialogCtrl ctrl, String titleID) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleID);
		// 画面の設定
		setSize(650, 400);

		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlHeader = new TPanel();
		ctrlSlipTypeCode = new TLabelField();
		ctrlSystemDivision = new TButtonField();
		pnlDetail = new TPanel();
		ctrlSlipTypeName = new TLabelField();
		ctrlSlipTypeAbbreviatedName = new TLabelField();
		ctrlSlipTypeNameForSearch = new TLabelField();
		pnlDetail1 = new TPanel();
		ctrlDataDivision = new TLabelComboBox();
		ctrlSlipNumberCollectFlag = new TLabelComboBox();
		ctrlAnotherSystemDivision = new TLabelComboBox();
		ctrlUnitReceipt = new TLabelComboBox();
		ctrlConsumptionTaxCalculationDivision = new TLabelComboBox();
		ctrlJournalizingInterfaceDivision = new TLabelComboBox();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(650, 40));
		pnlButton.setMinimumSize(new Dimension(650, 40));
		pnlButton.setPreferredSize(new Dimension(650, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(12);
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
		gridBagConstraints.insets = new Insets(10, 313, 5, 0);

		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(13);
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
		gridBagConstraints.insets = new Insets(10, 20, 0, 20);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(650, 50));
		pnlHeader.setMinimumSize(new Dimension(650, 50));
		pnlHeader.setPreferredSize(new Dimension(650, 50));
		ctrlSlipTypeCode.setFieldSize(45);
		ctrlSlipTypeCode.setLabelSize(96);
		ctrlSlipTypeCode.setLangMessageID("C00837");
		ctrlSlipTypeCode.getField().setAllowedSpace(false);
		ctrlSlipTypeCode.setMaxLength(3);
		ctrlSlipTypeCode.setTabControlNo(1);
		ctrlSlipTypeCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 430);
		pnlHeader.add(ctrlSlipTypeCode, gridBagConstraints);

		ctrlSystemDivision.setButtonSize(85);
		ctrlSystemDivision.setFieldSize(35);
		ctrlSystemDivision.setLangMessageID("C00980");
		ctrlSystemDivision.setMaxLength(2);
		ctrlSystemDivision.setNoticeSize(255);
		ctrlSystemDivision.setTabControlNo(2);
		ctrlSystemDivision.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 173);
		pnlHeader.add(ctrlSystemDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 20, 0, 20);
		getContentPane().add(pnlHeader, gridBagConstraints);

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(650, 75));
		pnlDetail.setMinimumSize(new Dimension(650, 75));
		pnlDetail.setPreferredSize(new Dimension(650, 75));
		ctrlSlipTypeName.setFieldSize(350);
		ctrlSlipTypeName.setLabelSize(100);
		ctrlSlipTypeName.setLangMessageID("C00838");
		ctrlSlipTypeName.setMaxLength(40);
		ctrlSlipTypeName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 130);
		pnlDetail.add(ctrlSlipTypeName, gridBagConstraints);

		ctrlSlipTypeAbbreviatedName.setFieldSize(230);
		ctrlSlipTypeAbbreviatedName.setLabelSize(100);
		ctrlSlipTypeAbbreviatedName.setLangMessageID("C00839");
		ctrlSlipTypeAbbreviatedName.setMaxLength(20);
		ctrlSlipTypeAbbreviatedName.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 210);
		pnlDetail.add(ctrlSlipTypeAbbreviatedName, gridBagConstraints);

		ctrlSlipTypeNameForSearch.setFieldSize(350);
		ctrlSlipTypeNameForSearch.setLabelSize(100);
		ctrlSlipTypeNameForSearch.setLangMessageID("C02757");
		ctrlSlipTypeNameForSearch.setMaxLength(40);
		ctrlSlipTypeNameForSearch.setTabControlNo(5);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 130);
		pnlDetail.add(ctrlSlipTypeNameForSearch, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(650, 75));
		pnlDetail1.setMinimumSize(new Dimension(650, 75));
		pnlDetail1.setPreferredSize(new Dimension(650, 75));
		ctrlDataDivision.setComboSize(145);
		ctrlDataDivision.setLabelSize(120);
		ctrlDataDivision.setLangMessageID("C00567");
		ctrlDataDivision.setMaximumSize(new Dimension(275, 20));
		ctrlDataDivision.setMinimumSize(new Dimension(275, 20));
		ctrlDataDivision.setPreferredSize(new Dimension(275, 20));
		ctrlDataDivision.setTabControlNo(6);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		pnlDetail1.add(ctrlDataDivision, gridBagConstraints);

		ctrlSlipNumberCollectFlag.setLabelSize(145);
		ctrlSlipNumberCollectFlag.setComboSize(175);
		ctrlSlipNumberCollectFlag.setLangMessageID("C01256");
		ctrlSlipNumberCollectFlag.setTabControlNo(7);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 0, 18);
		pnlDetail1.add(ctrlSlipNumberCollectFlag, gridBagConstraints);

		ctrlAnotherSystemDivision.setComboSize(145);
		ctrlAnotherSystemDivision.setLabelSize(120);
		ctrlAnotherSystemDivision.setLangMessageID("C00355");
		ctrlAnotherSystemDivision.setTabControlNo(8);
		ctrlAnotherSystemDivision.setMaximumSize(new Dimension(275, 20));
		ctrlAnotherSystemDivision.setMinimumSize(new Dimension(275, 20));
		ctrlAnotherSystemDivision.setPreferredSize(new Dimension(275, 20));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlAnotherSystemDivision, gridBagConstraints);

		ctrlUnitReceipt.setLabelSize(145);
		ctrlUnitReceipt.setComboSize(175);
		ctrlUnitReceipt.setLangMessageID("C00018");
		ctrlUnitReceipt.setTabControlNo(9);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 0, 0, 18);
		pnlDetail1.add(ctrlUnitReceipt, gridBagConstraints);

		ctrlConsumptionTaxCalculationDivision.setComboSize(145);
		ctrlConsumptionTaxCalculationDivision.setLabelSize(120);
		ctrlConsumptionTaxCalculationDivision.setLangMessageID("C00287");
		ctrlConsumptionTaxCalculationDivision.setMaximumSize(new Dimension(275, 20));
		ctrlConsumptionTaxCalculationDivision.setMinimumSize(new Dimension(275, 20));
		ctrlConsumptionTaxCalculationDivision.setPreferredSize(new Dimension(275, 20));
		ctrlConsumptionTaxCalculationDivision.setTabControlNo(10);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlConsumptionTaxCalculationDivision, gridBagConstraints);

		ctrlJournalizingInterfaceDivision.setLabelSize(145);
		ctrlJournalizingInterfaceDivision.setComboSize(175);
		ctrlJournalizingInterfaceDivision.setLangMessageID("C00299");
		ctrlJournalizingInterfaceDivision.setTabControlNo(11);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 18);
		pnlDetail1.add(ctrlJournalizingInterfaceDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(0, 0, 15, 22);
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pack();
	}

	TButton btnReturn;

	TImageButton btnSettle;

	TLabelComboBox ctrlAnotherSystemDivision;

	TLabelComboBox ctrlConsumptionTaxCalculationDivision;

	TLabelComboBox ctrlDataDivision;

	TLabelComboBox ctrlJournalizingInterfaceDivision;

	TLabelComboBox ctrlSlipNumberCollectFlag;

	TLabelField ctrlSlipTypeAbbreviatedName;

	TLabelField ctrlSlipTypeCode;

	TLabelField ctrlSlipTypeName;

	TLabelField ctrlSlipTypeNameForSearch;

	TButtonField ctrlSystemDivision;

	TLabelComboBox ctrlUnitReceipt;

	TPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlDetail1;

	TPanel pnlHeader;

}

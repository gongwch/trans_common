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
 * 社員マスタ
 */
public class MG0160EditDisplayDialog extends TDialog {

	/** シリアルUID */
	protected static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	protected MG0160EditDisplayDialogCtrl ctrl;

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
	MG0160EditDisplayDialog(Frame parent, boolean modal, MG0160EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(820, 400);

		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpTransferAccountDepositType = new ButtonGroup();
		pnlDetail = new TPanel();
		ctrlEmployeeCode = new TLabelField();
		ctrlEmployeeName = new TLabelField();
		ctrlEmployeeAbbreviationName = new TLabelField();
		ctrlEmployeeNameForSearch = createForSearchCtrl();
		ctrlTransferAccountNumber = new TLabelField();
		ctrlAccountName = new TLabelField();
		ctrlDrawingBankAccount = new TButtonField();
		ctrlTransferBranch = new TButtonField();
		ctrlTransferBank = new TButtonField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		pnlTransferAccountDepositType = new TPanel();
		rdoNormally = new TRadioButton();
		rdoInterim = new TRadioButton();
		txtTransferAccountDepositType = new TTextField();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();

		getContentPane().setLayout(new GridBagLayout());

		pnlDetail.setLayout(new GridBagLayout());

		pnlDetail.setMaximumSize(new Dimension(800, 280));
		pnlDetail.setMinimumSize(new Dimension(800, 280));
		pnlDetail.setPreferredSize(new Dimension(800, 280));
		ctrlEmployeeCode.setFieldSize(120);
		ctrlEmployeeCode.setLabelHAlignment(-1);
		ctrlEmployeeCode.setLabelSize(100);
		ctrlEmployeeCode.setLangMessageID("C00697");
		ctrlEmployeeCode.getField().setAllowedSpace(false);
		ctrlEmployeeCode.setMaxLength(10);
		ctrlEmployeeCode.setTabControlNo(1);
		ctrlEmployeeCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlEmployeeCode, gridBagConstraints);

		ctrlEmployeeName.setDoubleBuffered(false);
		ctrlEmployeeName.setFieldSize(410);
		ctrlEmployeeName.setLabelSize(100);
		ctrlEmployeeName.setLabelHAlignment(-1);
		ctrlEmployeeName.setLangMessageID("C00807");
		ctrlEmployeeName.setMaxLength(40);
		ctrlEmployeeName.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlEmployeeName, gridBagConstraints);

		ctrlEmployeeAbbreviationName.setFieldSize(410);
		ctrlEmployeeAbbreviationName.setLabelSize(100);
		ctrlEmployeeAbbreviationName.setLabelHAlignment(-1);
		ctrlEmployeeAbbreviationName.setLangMessageID("C00808");
		ctrlEmployeeAbbreviationName.setMaxLength(20);
		ctrlEmployeeAbbreviationName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlEmployeeAbbreviationName, gridBagConstraints);

		ctrlEmployeeNameForSearch.setFieldSize(410);
		ctrlEmployeeNameForSearch.setLabelSize(100);
		ctrlEmployeeNameForSearch.setLabelHAlignment(-1);
		ctrlEmployeeNameForSearch.setLangMessageID("C00809");
		ctrlEmployeeNameForSearch.setMaxLength(40);
		ctrlEmployeeNameForSearch.setTabControlNo(4);
		ctrlEmployeeNameForSearch.setVerifyInputWhenFocusTarget(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlEmployeeNameForSearch, gridBagConstraints);

		ctrlTransferAccountNumber.setFieldSize(85);
		ctrlTransferAccountNumber.setLabelSize(100);
		ctrlTransferAccountNumber.setLabelHAlignment(-1);
		ctrlTransferAccountNumber.setLangMessageID("C00813");
		ctrlTransferAccountNumber.getField().setAllowedSpace(false);
		ctrlTransferAccountNumber.setMaxLength(7);
		ctrlTransferAccountNumber.setTabControlNo(11);
		ctrlTransferAccountNumber.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlTransferAccountNumber, gridBagConstraints);

		ctrlAccountName.setFieldSize(310);
		ctrlAccountName.setLabelHAlignment(-1);
		ctrlAccountName.setLabelSize(100);
		ctrlAccountName.setLangMessageID("C01068");
		ctrlAccountName.setMaxLength(30);
		ctrlAccountName.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlAccountName, gridBagConstraints);

		ctrlDrawingBankAccount.setButtonSize(100);
		ctrlDrawingBankAccount.setFieldSize(120);
		ctrlDrawingBankAccount.setLangMessageID("C00475");
		ctrlDrawingBankAccount.setMaxLength(10);
		ctrlDrawingBankAccount.setNoticeSize(380);
		ctrlDrawingBankAccount.setTabControlNo(5);
		ctrlDrawingBankAccount.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlDrawingBankAccount, gridBagConstraints);

		ctrlTransferBranch.setButtonSize(100);
		ctrlTransferBranch.setFieldSize(45);
		ctrlTransferBranch.setLangMessageID("C00472");
		ctrlTransferBranch.setMaxLength(3);
		ctrlTransferBranch.setTabControlNo(7);
		ctrlTransferBranch.setImeMode(false);
		ctrlTransferBranch.setNoticeSize(310);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlTransferBranch, gridBagConstraints);

		ctrlTransferBank.setButtonSize(100);
		ctrlTransferBank.setFieldSize(55);
		ctrlTransferBank.setLangMessageID("C00952");
		ctrlTransferBank.setMaxLength(4);
		ctrlTransferBank.setTabControlNo(6);
		ctrlTransferBank.setImeMode(false);
		ctrlTransferBank.setNoticeSize(310);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail.add(ctrlTransferBank, gridBagConstraints);

		dtBeginDate.setLabelSize(100);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(13);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 30, 0);
		pnlDetail.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelSize(100);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(14);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 225, 30, 0);
		pnlDetail.add(dtEndDate, gridBagConstraints);

		pnlTransferAccountDepositType.setLayout(new GridBagLayout());

		pnlTransferAccountDepositType.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlTransferAccountDepositType.setLangMessageID("C00471");
		pnlTransferAccountDepositType.setMaximumSize(new Dimension(140, 50));
		pnlTransferAccountDepositType.setMinimumSize(new Dimension(140, 50));
		pnlTransferAccountDepositType.setPreferredSize(new Dimension(140, 50));
		rdoNormally.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransferAccountDepositType.add(rdoNormally);
		rdoNormally.setSelected(true);
		rdoNormally.setLangMessageID("C00463");
		rdoNormally.setMargin(new Insets(0, 0, 0, 0));
		rdoNormally.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 1, 5, 11);
		pnlTransferAccountDepositType.add(rdoNormally, gridBagConstraints);

		rdoInterim.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpTransferAccountDepositType.add(rdoInterim);
		rdoInterim.setLangMessageID("C00397");
		rdoInterim.setMargin(new Insets(0, 0, 0, 0));
		rdoInterim.setTabControlNo(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlTransferAccountDepositType.add(rdoInterim, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.gridheight = 3;
		gridBagConstraints.insets = new Insets(0, 0, 14, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(pnlTransferAccountDepositType, gridBagConstraints);

		txtTransferAccountDepositType.setEditable(false);
		txtTransferAccountDepositType.setMaximumSize(new Dimension(135, 20));
		txtTransferAccountDepositType.setMinimumSize(new Dimension(135, 20));
		txtTransferAccountDepositType.setPreferredSize(new Dimension(135, 20));
		txtTransferAccountDepositType.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(5, 3, 0, 0);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail.add(txtTransferAccountDepositType, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 10, 10);
		getContentPane().add(pnlDetail, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(15);
		btnSettle.addActionListener(new ActionListener() {

			/**
			 * 確定ボタン押下の処理
			 */

			public void actionPerformed(ActionEvent e) {
				isSettle = true;
				ctrl.disposeDialog();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 510, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(16);
		btnReturn.setForClose(true);
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
		gridBagConstraints.insets = new Insets(15, 10, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(10, 10, 0, 10);
		getContentPane().add(pnlButton, gridBagConstraints);

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

	ButtonGroup btngrpTransferAccountDepositType;

	TLabelField ctrlAccountName;

	TButtonField ctrlDrawingBankAccount;

	TLabelField ctrlEmployeeAbbreviationName;

	TLabelField ctrlEmployeeCode;

	TLabelField ctrlEmployeeName;

	TLabelField ctrlEmployeeNameForSearch;

	TLabelField ctrlTransferAccountNumber;

	TButtonField ctrlTransferBank;

	TButtonField ctrlTransferBranch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TPanel pnlButton;

	TPanel pnlDetail;

	TPanel pnlTransferAccountDepositType;

	TRadioButton rdoInterim;

	TRadioButton rdoNormally;

	TTextField txtTransferAccountDepositType;

}

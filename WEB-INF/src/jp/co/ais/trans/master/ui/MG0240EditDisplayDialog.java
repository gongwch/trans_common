package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * プログラムマスタ編集画面
 */
public class MG0240EditDisplayDialog extends TDialog {

	/** コントロールクラス */
	private MG0240EditDisplayDialogCtrl ctrl;

	/** 確定されたかどうか */
	protected boolean isSettle = false;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param modal
	 * @param ctrl
	 * @param titleid
	 */
	public MG0240EditDisplayDialog(Frame parent, boolean modal, MG0240EditDisplayDialogCtrl ctrl, String titleid) {
		super(parent, modal);
		this.ctrl = ctrl;
		this.setResizable(false);
		initComponents();
		setLangMessageID(titleid);

		super.initDialog();
	}

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param modal
	 */
	public MG0240EditDisplayDialog(Frame parent, boolean modal) {
		super(parent, modal);
		initComponents();
		super.initDialog();
	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpMenuDivision = new ButtonGroup();
		pnlDeatil = new TPanel();
		ctrlProgramCode = new TLabelField();
		ctrlProgramName = new TLabelField();
		ctrlProgramAbbreviationName = new TLabelField();
		ctrlProgramNameForSearch = createForSearchCtrl();
		ctrlComment = new TLabelField();
		ctrlLoadModuleFileName = new TLabelField();
		numAuthorityLevel = new TLabelNumericField();
		ctrlOrderDisplay = new TLabelNumericField();
		ctrlParentsProgramCode = new TButtonField();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlSystemDivision = new TLabelComboBox();
		pnlMenuDivision = new TPanel();
		rdoScreenHaving = new TRadioButton();
		rdoMenuOnly = new TRadioButton();
		pnlButton = new TPanel();
		btnSettle = new TButton();
		btnReturn = new TButton();

		getContentPane().setLayout(new GridBagLayout());

		pnlDeatil.setLayout(new GridBagLayout());

		pnlDeatil.setMaximumSize(new Dimension(770, 380));
		pnlDeatil.setMinimumSize(new Dimension(770, 380));
		pnlDeatil.setPreferredSize(new Dimension(770, 380));
		ctrlProgramCode.setFieldSize(120);
		ctrlProgramCode.setLabelHAlignment(-1);
		ctrlProgramCode.setLangMessageID("C00818");
		ctrlProgramCode.getField().setAllowedSpace(false);
		ctrlProgramCode.setLabelSize(118);
		ctrlProgramCode.setMaxLength(10);
		ctrlProgramCode.setTabControlNo(1);
		ctrlProgramCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlDeatil.add(ctrlProgramCode, gridBagConstraints);

		ctrlProgramName.setFieldSize(450);
		ctrlProgramName.setLabelHAlignment(-1);
		ctrlProgramName.setLangMessageID("C00819");
		ctrlProgramName.setLabelSize(118);
		ctrlProgramName.setMaxLength(40);
		ctrlProgramName.setTabControlNo(3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlDeatil.add(ctrlProgramName, gridBagConstraints);

		ctrlProgramAbbreviationName.setFieldSize(230);
		ctrlProgramAbbreviationName.setLabelHAlignment(-1);
		ctrlProgramAbbreviationName.setLangMessageID("C00820");
		ctrlProgramAbbreviationName.setLabelSize(118);
		ctrlProgramAbbreviationName.setMaxLength(20);
		ctrlProgramAbbreviationName.setTabControlNo(4);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlDeatil.add(ctrlProgramAbbreviationName, gridBagConstraints);

		ctrlProgramNameForSearch.setFieldSize(230);
		ctrlProgramNameForSearch.setLabelHAlignment(-1);
		ctrlProgramNameForSearch.setLabelSize(118);
		ctrlProgramNameForSearch.setLangMessageID("C00821");
		ctrlProgramNameForSearch.setMaxLength(20);
		ctrlProgramNameForSearch.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlDeatil.add(ctrlProgramNameForSearch, gridBagConstraints);

		ctrlComment.setFieldSize(530);
		ctrlComment.setLabelHAlignment(-1);
		ctrlComment.setLabelSize(63);
		ctrlComment.setLangMessageID("C00183");
		ctrlComment.setMaxLength(80);
		ctrlComment.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 105, 0, 0);
		pnlDeatil.add(ctrlComment, gridBagConstraints);

		ctrlLoadModuleFileName.setImeMode(false);
		ctrlLoadModuleFileName.setFieldSize(530);
		ctrlLoadModuleFileName.setLabelHAlignment(-1);
		ctrlLoadModuleFileName.setLabelSize(162);
		ctrlLoadModuleFileName.setLangMessageID("C00823");
		ctrlLoadModuleFileName.setMaxLength(255);
		ctrlLoadModuleFileName.setTabControlNo(8);
		ctrlLoadModuleFileName.setMaximumSize(new Dimension(700, 20));
		ctrlLoadModuleFileName.setMinimumSize(new Dimension(700, 20));
		ctrlLoadModuleFileName.setPreferredSize(new Dimension(700, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 7;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 0, 50);
		pnlDeatil.add(ctrlLoadModuleFileName, gridBagConstraints);

		numAuthorityLevel.setNumericFormat("#");
		numAuthorityLevel.setFieldSize(20);
		numAuthorityLevel.setFieldHAlignment(2);
		numAuthorityLevel.setLabelHAlignment(-1);
		numAuthorityLevel.setLabelSize(118);
		numAuthorityLevel.setLangMessageID("C00822");
		numAuthorityLevel.setMaxLength(1);
		numAuthorityLevel.setPositiveOnly(true); // 正数のみ
		numAuthorityLevel.setNumberValue(9);
		numAuthorityLevel.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 50, 0, 0);
		pnlDeatil.add(numAuthorityLevel, gridBagConstraints);

		ctrlParentsProgramCode.setButtonSize(125);
		ctrlParentsProgramCode.setFieldSize(120);
		ctrlParentsProgramCode.setLangMessageID("C00824");
		ctrlParentsProgramCode.setMaxLength(10);
		ctrlParentsProgramCode.setNoticeSize(410);
		ctrlParentsProgramCode.setTabControlNo(11);
		ctrlParentsProgramCode.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 10;
		gridBagConstraints.gridwidth = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 43, 0, 0);
		pnlDeatil.add(ctrlParentsProgramCode, gridBagConstraints);

		dtBeginDate.setLabelHAlignment(-1);
		dtBeginDate.setLabelSize(85);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setTabControlNo(9);

		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 8;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 83, 0, 0);
		pnlDeatil.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelHAlignment(-1);
		dtEndDate.setLabelSize(85);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setTabControlNo(10);

		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 9;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 83, 0, 0);
		pnlDeatil.add(dtEndDate, gridBagConstraints);

		ctrlSystemDivision.setComboSize(120);
		ctrlSystemDivision.setLabelSize(98);
		ctrlSystemDivision.setLangMessageID("C00980");
		ctrlSystemDivision.setTabControlNo(2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 70, 0, 0);
		pnlDeatil.add(ctrlSystemDivision, gridBagConstraints);

		ctrlOrderDisplay.setNumericFormat("###");
		ctrlOrderDisplay.setFieldSize(40);
		ctrlOrderDisplay.setLabelHAlignment(-1);
		ctrlOrderDisplay.setLabelSize(115);
		ctrlOrderDisplay.setLangMessageID("C02397");
		ctrlOrderDisplay.setMaxLength(3);
		ctrlOrderDisplay.setTabControlNo(12);
		ctrlOrderDisplay.setPositiveOnly(true);
		ctrlOrderDisplay.setNumberValue(0);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 11;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 53, 0, 300);
		pnlDeatil.add(ctrlOrderDisplay, gridBagConstraints);

		pnlMenuDivision.setLayout(new GridBagLayout());

		pnlMenuDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder()));
		pnlMenuDivision.setLangMessageID("C00520");
		pnlMenuDivision.setMaximumSize(new Dimension(200, 50));
		pnlMenuDivision.setMinimumSize(new Dimension(200, 50));
		pnlMenuDivision.setPreferredSize(new Dimension(200, 50));

		rdoMenuOnly.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpMenuDivision.add(rdoMenuOnly);
		rdoMenuOnly.setLangMessageID("C00519");
		rdoMenuOnly.setMargin(new Insets(0, 0, 0, 0));
		rdoMenuOnly.setTabControlNo(13);
		rdoMenuOnly.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkCtrl(false);
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 5, 0);
		pnlMenuDivision.add(rdoMenuOnly, gridBagConstraints);

		rdoScreenHaving.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpMenuDivision.add(rdoScreenHaving);
		rdoScreenHaving.setLangMessageID("C00076");
		rdoScreenHaving.setTabControlNo(14);
		rdoScreenHaving.setMargin(new Insets(0, 0, 0, 0));
		rdoScreenHaving.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.checkCtrl(true);
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 15, 5, 0);
		pnlMenuDivision.add(rdoScreenHaving, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 12;
		gridBagConstraints.insets = new Insets(5, 0, 30, 170);
		pnlDeatil.add(pnlMenuDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 50, 10, 50);
		getContentPane().add(pnlDeatil, gridBagConstraints);

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(230, 40));
		pnlButton.setMinimumSize(new Dimension(230, 40));
		pnlButton.setPreferredSize(new Dimension(230, 40));
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(15);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.fill = GridBagConstraints.VERTICAL;
		gridBagConstraints.anchor = GridBagConstraints.SOUTH;
		gridBagConstraints.insets = new Insets(15, 0, 5, 10);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnSettleActionPerformed();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(16);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(15, 0, 5, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				btnReturnActionPerformed();
			}
		});
		btnReturn.setForClose(true);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 475, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pack();
		this.setSize(805, 500);
	}

	/**
	 * 検索名称フィールド作成
	 * 
	 * @return 検索名称フィールド
	 */
	protected TLabelField createForSearchCtrl() {
		return new TLabelField();
	}

	/**
	 * main
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		EventQueue.invokeLater(new Runnable() {

			public void run() {
				new MG0240EditDisplayDialog(new JFrame(), true).setVisible(true);
			}
		});
	}

	/**
	 * 確定ボタン押下の処理
	 */
	protected void btnSettleActionPerformed() {
		isSettle = true;
		ctrl.disposeDialog();
	}

	/**
	 * 戻るボタン押下の処理
	 */
	protected void btnReturnActionPerformed() {
		isSettle = false;
		ctrl.disposeDialog();
	}

	TButton btnReturn;

	TButton btnSettle;

	ButtonGroup btngrpMenuDivision;

	TLabelField ctrlProgramCode;

	TLabelField ctrlComment;

	TLabelField ctrlLoadModuleFileName;

	TButtonField ctrlParentsProgramCode;

	TLabelField ctrlProgramAbbreviationName;

	TLabelField ctrlProgramName;

	TLabelField ctrlProgramNameForSearch;

	TLabelComboBox ctrlSystemDivision;

	TLabelPopupCalendar dtBeginDate;

	TLabelNumericField ctrlOrderDisplay;

	TLabelPopupCalendar dtEndDate;

	TLabelNumericField numAuthorityLevel;

	TPanel pnlButton;

	TPanel pnlDeatil;

	TPanel pnlMenuDivision;

	TRadioButton rdoMenuOnly;

	TRadioButton rdoScreenHaving;

}

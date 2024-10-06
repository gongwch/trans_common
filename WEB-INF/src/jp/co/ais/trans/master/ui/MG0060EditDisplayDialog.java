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
 * 部門マスタ
 * 
 * @author liuchengcheng
 */
public class MG0060EditDisplayDialog extends TDialog {

	/** シリアルUID */
	private static final long serialVersionUID = -3465356597300046007L;

	/** コントロールクラス */
	private MG0060EditDisplayDialogCtrl ctrl;

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
	MG0060EditDisplayDialog(Frame parent, boolean modal, MG0060EditDisplayDialogCtrl ctrl, String titleid) {
		// 親フレームを設定
		super(parent, modal);
		// コントロールクラスを設定
		this.ctrl = ctrl;
		this.setResizable(false);
		// 画面の初期化
		initComponents();
		setLangMessageID(titleid);
		// 画面の設定
		setSize(600, 370);
		super.initDialog();
	}

	/**
	 * Creates new form MG0060EditDisplayDialog
	 * 
	 * @param parent
	 * @param modal
	 */
	public MG0060EditDisplayDialog(Frame parent, boolean modal) {
		// 親フレームを設定
		super(parent, modal);
		// 画面の初期化
		initComponents();
		super.initDialog();
	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		btngrpDepartmentDivision = new ButtonGroup();
		pnlButton = new TPanel();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnReturn = new TButton();
		pnlDetail1 = new TPanel();
		ctrlDepartmentCode = new TLabelField();
		ctrlDepartmentName = new TLabelField();
		ctrlDepartmentAbbreviatedName = new TLabelField();
		ctrlDepartmentNameForSearch = createForSearchCtrl();
		numPersonNumber1 = new TLabelNumericField();
		numPersonNumber2 = new TLabelNumericField();
		numFloorArea = new TLabelNumericField();
		lblPerson1 = new TLabel();
		lblSqr = new TLabel();
		lblPerson2 = new TLabel();
		pnlDetail2 = new TPanel();
		pnlDepartmentDivision = new TPanel();
		rdoInput = new TRadioButton();
		rdoSummary = new TRadioButton();
		pnlDetail3 = new TPanel();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();

		getContentPane().setLayout(new GridBagLayout());
		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(600, 40));
		pnlButton.setMinimumSize(new Dimension(600, 40));
		pnlButton.setPreferredSize(new Dimension(600, 40));

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(11);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 300, 0, 0);
		pnlButton.add(btnSettle, gridBagConstraints);
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = true;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});

		btnReturn.setLangMessageID("C01747");
		btnReturn.setShortcutKey(KeyEvent.VK_F12);
		btnReturn.setMaximumSize(new Dimension(110, 25));
		btnReturn.setMinimumSize(new Dimension(110, 25));
		btnReturn.setPreferredSize(new Dimension(110, 25));
		btnReturn.setTabControlNo(12);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 15, 0, 0);
		pnlButton.add(btnReturn, gridBagConstraints);
		btnReturn.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// 開ける種別の設定
				isSettle = false;
				// 画面を閉める
				ctrl.disposeDialog();
			}
		});
		btnReturn.setForClose(true);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 0, 0);
		getContentPane().add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(600, 180));
		pnlDetail1.setMinimumSize(new Dimension(600, 180));
		pnlDetail1.setPreferredSize(new Dimension(600, 180));
		ctrlDepartmentCode.setFieldSize(120);
		ctrlDepartmentCode.setLabelSize(80);
		ctrlDepartmentCode.setLangMessageID("C00698");
		ctrlDepartmentCode.setMaxLength(10);
		ctrlDepartmentCode.setTabControlNo(1);
		ctrlDepartmentCode.setImeMode(false);
		ctrlDepartmentCode.getField().setAllowedSpace(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.gridwidth = 2;
		pnlDetail1.add(ctrlDepartmentCode, gridBagConstraints);

		ctrlDepartmentName.setFieldSize(450);
		ctrlDepartmentName.setLabelSize(80);
		ctrlDepartmentName.setLangMessageID("C00723");
		ctrlDepartmentName.setMaxLength(40);
		ctrlDepartmentName.setTabControlNo(2);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlDepartmentName, gridBagConstraints);

		ctrlDepartmentAbbreviatedName.setFieldSize(230);
		ctrlDepartmentAbbreviatedName.setLabelSize(80);
		ctrlDepartmentAbbreviatedName.setLangMessageID("C00724");
		ctrlDepartmentAbbreviatedName.setMaxLength(20);
		ctrlDepartmentAbbreviatedName.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlDepartmentAbbreviatedName, gridBagConstraints);

		ctrlDepartmentNameForSearch.setFieldSize(450);
		ctrlDepartmentNameForSearch.setLabelSize(80);
		ctrlDepartmentNameForSearch.setLangMessageID("C00725");
		ctrlDepartmentNameForSearch.setMaxLength(40);
		ctrlDepartmentNameForSearch.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.gridwidth = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(ctrlDepartmentNameForSearch, gridBagConstraints);
		numPersonNumber1.setFieldHAlignment(2);
		numPersonNumber1.setFieldSize(90);
		numPersonNumber1.setLabelSize(80);
		numPersonNumber1.setLangMessageID("C00726");
		numPersonNumber1.setMaxLength(8);
		numPersonNumber1.setNumericFormat("##,###,###,###");
		numPersonNumber1.setValue(("0"));
		numPersonNumber1.setTabControlNo(5);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(numPersonNumber1, gridBagConstraints);

		numPersonNumber2.setFieldHAlignment(2);
		numPersonNumber2.setFieldSize(90);
		numPersonNumber2.setLabelSize(80);
		numPersonNumber2.setLangMessageID("C00727");
		numPersonNumber2.setMaxLength(8);
		numPersonNumber2.setNumericFormat("##,###,###,###");
		numPersonNumber2.setValue(("0"));
		numPersonNumber2.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(numPersonNumber2, gridBagConstraints);

		numFloorArea.setFieldHAlignment(2);
		numFloorArea.setFieldSize(90);
		numFloorArea.setLabelSize(80);
		numFloorArea.setLangMessageID("C00728");
		numFloorArea.setMaxLength(10);
		numFloorArea.setNumericFormat("##,###,##0.00");
		numFloorArea.setValue(("0.00"));
		numFloorArea.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 0, 0, 0);
		pnlDetail1.add(numFloorArea, gridBagConstraints);

		lblPerson1.setLangMessageID("C01195");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 4;
		gridBagConstraints.insets = new Insets(5, 5, 0, 340);
		pnlDetail1.add(lblPerson1, gridBagConstraints);

		lblSqr.setLangMessageID("C00969");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 6;
		gridBagConstraints.insets = new Insets(5, 5, 0, 340);
		pnlDetail1.add(lblSqr, gridBagConstraints);

		lblPerson2.setLangMessageID("C01195");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 5;
		gridBagConstraints.insets = new Insets(5, 5, 0, 340);
		pnlDetail1.add(lblPerson2, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		getContentPane().add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(600, 40));
		pnlDetail2.setMinimumSize(new Dimension(600, 40));
		pnlDetail2.setPreferredSize(new Dimension(600, 40));
		pnlDepartmentDivision.setLayout(new GridBagLayout());

		pnlDepartmentDivision.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), ""));
		pnlDepartmentDivision.setLangMessageID("C01303");
		pnlDepartmentDivision.setMaximumSize(new Dimension(180, 40));
		pnlDepartmentDivision.setMinimumSize(new Dimension(180, 40));
		pnlDepartmentDivision.setPreferredSize(new Dimension(180, 40));
		rdoInput.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepartmentDivision.add(rdoInput);
		rdoInput.setSelected(true);
		rdoInput.setLangMessageID("C01275");
		rdoInput.setMargin(new Insets(0, 0, 0, 0));
		rdoInput.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 9, 0);
		pnlDepartmentDivision.add(rdoInput, gridBagConstraints);

		rdoSummary.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
		btngrpDepartmentDivision.add(rdoSummary);
		rdoSummary.setLangMessageID("C00255");
		rdoSummary.setMargin(new Insets(0, 0, 0, 0));
		rdoSummary.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 30, 9, 0);
		pnlDepartmentDivision.add(rdoSummary, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 27, 0, 215);
		pnlDetail2.add(pnlDepartmentDivision, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		getContentPane().add(pnlDetail2, gridBagConstraints);

		pnlDetail3.setLayout(new GridBagLayout());

		pnlDetail3.setMaximumSize(new Dimension(600, 60));
		pnlDetail3.setMinimumSize(new Dimension(600, 60));
		pnlDetail3.setPreferredSize(new Dimension(600, 60));
		dtBeginDate.setLabelSize(75);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelHAlignment(3);
		dtBeginDate.setTabControlNo(9);
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 45, 0, 400);
		pnlDetail3.add(dtBeginDate, gridBagConstraints);

		dtEndDate.setLabelSize(75);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setLabelHAlignment(3);
		dtEndDate.setTabControlNo(10);
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(5, 45, 0, 400);
		pnlDetail3.add(dtEndDate, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(0, 0, 15, 0);
		getContentPane().add(pnlDetail3, gridBagConstraints);
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtBeginDate.setValue(dtBeginDate.getCalendar().getMinimumDate());

		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 1, 1));
		dtEndDate.setValue(dtEndDate.getCalendar().getMaximumDate());

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

	ButtonGroup btngrpDepartmentDivision;

	TLabelField ctrlDepartmentAbbreviatedName;

	TLabelField ctrlDepartmentCode;

	TLabelField ctrlDepartmentName;

	TLabelField ctrlDepartmentNameForSearch;

	TLabelPopupCalendar dtBeginDate;

	TLabelPopupCalendar dtEndDate;

	TLabel lblPerson1;

	TLabel lblPerson2;

	TLabel lblSqr;

	TLabelNumericField numFloorArea;

	TLabelNumericField numPersonNumber1;

	TLabelNumericField numPersonNumber2;

	TPanel pnlButton;

	TPanel pnlDepartmentDivision;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlDetail3;

	TRadioButton rdoInput;

	TRadioButton rdoSummary;

}

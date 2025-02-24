package jp.co.ais.trans2.common.model.ui.slip;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TDialog;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * `[õ
 */
public class TSlipSearchDialog extends TDialog {

	/** e[uñ¼ñÌ */
	public enum SC {
		/** îf[^ */
		bean,

		/** `[Ô */
		slipNo,

		/** `[út */
		slipDate,

		/** åR[h */
		depCode,

		/** åªÌ */
		depNames,

		/** Ev */
		remarks,

		/** XVæª */
		updDivision;
	}

	/** _CAO TCY */
	public int dialogWidth = getDialogWidth();

	/** ê */
	public TTable tbl;

	/** ð `[Ô */
	public TTextField txtSlipNo;

	/** ð `[út */
	public THalfwayDateField txtSlipDate;

	/** ð åR[h */
	public TTextField txtDepCode;

	/** ð åªÌ */
	public TTextField txtDepNames;

	/** ð `[Ev */
	public TTextField txtSlipRemarks;

	/** ð XVæª */
	public TComboBox cmbUpdDivision;

	/** õ{^ */
	public TButton btnSearch;

	/** mè{^ */
	public TButton btnEnter;

	/** æÁ{^ */
	public TButton btnCancel;

	/** ¡Ê[hIðpl */
	public TRadioPanel ctrlCopyMode;

	/** Cpl */
	protected TPanel pnlMain;

	/** XvbhÌæ */
	protected TPanel pnlSS;

	/** tB[hÌæ */
	protected TPanel pnlField;

	/** {^Ìæ */
	protected TPanel pnlButton;

	/** trueAõðÉåÇÁ */
	protected static boolean useDepartmentSearch = ClientConfig.isFlagOn("trans.slip.use.search.depcode");

	/**
	 * RXgN^.
	 * 
	 * @param panel pl
	 */
	public TSlipSearchDialog(TPanel panel) {
		this(panel.getParentFrame());
	}

	/**
	 * _CAOÌå«³
	 * 
	 * @return TCY
	 */
	public int getDialogWidth() {
		return 850;
	}

	/**
	 * RXgN^.
	 * 
	 * @param parent et[
	 */
	public TSlipSearchDialog(Frame parent) {
		super(parent, true);

		initComponents();
		initSpreadSheet();
		allocateComponents();
		setTabIndex();

		initDialog();
	}

	/**
	 * R|[lgì¬
	 */
	public void initComponents() {
		this.setLangMessageID("C00391");// `[ê
		this.setResizable(true);

		pnlMain = new TPanel();
		pnlSS = new TPanel();
		pnlField = new TPanel();
		pnlButton = new TPanel();

		tbl = new TTable();

		txtSlipNo = new TTextField();
		txtSlipDate = new THalfwayDateField();
		txtDepCode = new TTextField();
		txtDepNames = new TTextField();
		txtSlipRemarks = new TTextField();
		cmbUpdDivision = new TComboBox();

		btnSearch = new TButton();
		btnEnter = new TButton();
		btnCancel = new TButton();

		ctrlCopyMode = new TRadioPanel();
	}

	/**
	 * XvbhV[gÝè
	 */
	public void initSpreadSheet() {
		tbl.addColumn(SC.bean, "", -1);
		tbl.addColumn(SC.slipNo, "C00605", 150);// `[Ô
		tbl.addColumn(SC.slipDate, "C00599", 80, SwingConstants.CENTER);// `[út
		if (useDepartmentSearch) {
			tbl.addColumn(SC.depCode, "C00698", 80);// åR[h
			tbl.addColumn(SC.depNames, "C00724", 100);// åªÌ
		} else {
			tbl.addColumn(SC.depCode, "C00698", 0, false);
			tbl.addColumn(SC.depNames, "C00724", 0, false);
		}
		tbl.addColumn(SC.remarks, "C00569", dialogWidth - 420);// `[Ev
		tbl.addColumn(SC.updDivision, "C01069", 100, SwingConstants.CENTER);// XVæª

		tbl.setRowColumnWidth(38);
	}

	/**
	 * R|[lgzu
	 */
	public void allocateComponents() {

		int depX = 0;

		this.setSize(dialogWidth, 600);
		this.setLayout(new BorderLayout());

		pnlMain.setLayout(new GridBagLayout());

		GridBagConstraints gc = new GridBagConstraints();
		gc.weightx = 1.0d;

		// ê
		pnlSS.setLayout(new BorderLayout());
		TGuiUtil.setComponentSize(pnlSS, 0, 0);

		tbl.addSpreadSheetSelectChange(btnEnter);
		tbl.setEnterToButton(true);
		tbl.getTableHeader().setReorderingAllowed(false);
		TGuiUtil.setComponentSize(tbl, new Dimension(0, 0));
		pnlSS.add(tbl, BorderLayout.CENTER);

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weighty = 1.0d;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets = new Insets(10, 10, 0, 10);
		pnlMain.add(pnlSS, gc);

		// õð
		pnlField.setLayout(null);
		TGuiUtil.setComponentSize(pnlField, 0, 30);

		int x = tbl.getRowColumnWidth();

		// `[Ô
		txtSlipNo.setImeMode(false);
		txtSlipNo.setMaxLength(20);
		TGuiUtil.setComponentSize(txtSlipNo, 150, 20);
		txtSlipNo.setLocation(x, 0);
		pnlField.add(txtSlipNo);

		// `[út
		TGuiUtil.setComponentSize(txtSlipDate, 80, 20);
		txtSlipDate.setLocation(txtSlipNo.getX() + txtSlipNo.getWidth(), 0);
		pnlField.add(txtSlipDate);

		if (useDepartmentSearch) {
			depX = 180;
			this.setSize(dialogWidth + depX, 600);

			// åR[h
			txtDepCode.setImeMode(false);
			txtDepCode.setMaxLength(10);
			TGuiUtil.setComponentSize(txtDepCode, 80, 20);
			txtDepCode.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth(), 0);
			pnlField.add(txtDepCode);

			// åªÌ
			txtDepNames.setMaxLength(40);
			TGuiUtil.setComponentSize(txtDepNames, 100, 20);
			txtDepNames.setLocation(txtDepCode.getX() + txtDepCode.getWidth(), 0);
			pnlField.add(txtDepNames);
		}

		// Ev
		TGuiUtil.setComponentSize(txtSlipRemarks, dialogWidth - 420, 20);
		txtSlipRemarks.setLocation(txtSlipDate.getX() + txtSlipDate.getWidth() + depX, 0);
		pnlField.add(txtSlipRemarks);

		// XVæª
		TGuiUtil.setComponentSize(cmbUpdDivision, 100, 20);
		cmbUpdDivision.setLocation(txtSlipRemarks.getX() + txtSlipRemarks.getWidth(), 0);
		pnlField.add(cmbUpdDivision);

		gc.gridx = 0;
		gc.gridy = 1;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(5, 10, 0, 10);
		pnlMain.add(pnlField, gc);

		// {^
		pnlButton.setLayout(null);
		TGuiUtil.setComponentSize(pnlButton, 0, 45);

		// õ{^
		btnSearch.setLangMessageID("C00155");// õ
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		TGuiUtil.setComponentSize(btnSearch, 25, 100);
		btnSearch.setLocation(0, 10);
		pnlButton.add(btnSearch);

		// mè{^
		btnEnter.setLangMessageID("C01019");// mè
		btnEnter.setShortcutKey(KeyEvent.VK_F8);
		btnEnter.setEnabled(false);
		TGuiUtil.setComponentSize(btnEnter, 25, 100);
		btnEnter.setLocation(btnSearch.getX() + btnSearch.getWidth() + 2, 10);
		pnlButton.add(btnEnter);

		// LZ{^
		btnCancel.setLangMessageID("C00405");// æÁ
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		TGuiUtil.setComponentSize(btnCancel, 25, 100);
		btnCancel.setLocation(dialogWidth - btnCancel.getWidth() - 30, 10);
		pnlButton.add(btnCancel);

		// ¡Ê[hIðpl
		ctrlCopyMode.setSize(ctrlCopyMode.getWidth(), ctrlCopyMode.getHeight() + 1);
		ctrlCopyMode.setLangMessageID("C00459");// ¡Ê
		ctrlCopyMode.addRadioButton(getWord("C10758"), 80);// `¡Ê
		ctrlCopyMode.addRadioButton(getWord("C10759"), 80);// Ô`¡Ê
		ctrlCopyMode.addRadioButton(getWord("C10760"), 80);// t`¡Ê
		ctrlCopyMode.setSelectON(0);
		ctrlCopyMode.setVisible(false);
		ctrlCopyMode.setLocation(btnEnter.getX() + btnEnter.getWidth() + 30, -1);
		pnlButton.add(ctrlCopyMode);

		gc.gridx = 0;
		gc.gridy = 2;
		gc.weighty = 0d;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets = new Insets(0, 10, 0, 10);
		pnlMain.add(pnlButton, gc);

		add(pnlMain);
	}

	/**
	 * ^uÝè
	 */
	public void setTabIndex() {
		int i = 1;

		txtSlipNo.setTabControlNo(i++);
		txtSlipDate.setTabControlNo(i++);
		txtDepCode.setTabControlNo(i++);
		txtDepNames.setTabControlNo(i++);
		txtSlipRemarks.setTabControlNo(i++);
		cmbUpdDivision.setTabControlNo(i++);
		btnSearch.setTabControlNo(i++);
		btnEnter.setTabControlNo(i++);
		ctrlCopyMode.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);

		tbl.setTabControlNo(i++);
	}
}

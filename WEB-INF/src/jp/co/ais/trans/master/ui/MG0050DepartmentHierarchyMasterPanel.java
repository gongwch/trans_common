package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TTable;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 部門階層マスタ画面
 * 
 * @author liuchengcheng
 */
public class MG0050DepartmentHierarchyMasterPanel extends TPanelBusiness {

	private static final long serialVersionUID = -7756724724264635617L;

	protected MG0050DepartmentHierarchyMasterPanelCtrl ctrl;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds1 = new JCVectorDataSource();

	JCVectorDataSource ds2 = new JCVectorDataSource();

	/**
	 * @param ctrl
	 */
	public MG0050DepartmentHierarchyMasterPanel(MG0050DepartmentHierarchyMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;

		// 画面の初期化
		initComponents();

		initSpreadSheet();

		super.initPanel();
	}

	/**
	 * フレーム取得
	 */
	public Frame getParentFrame() {
		return super.getParentFrame();
	}

	private void initSpreadSheet() {

		// SpreadSheet を init する
		String[] columnLabelMessageIDs1 = new String[] { "C00596", "C00335", "C00698", "C01739", "C00991", "C01751",
				"C01752", "C01753", "C01754", "C01755", "C01756", "C01757", "C01758", "C01759" };
		int[] columnWidths1 = new int[] { 0, 0, 10, 4, 0, 40, 40, 40, 40, 40, 40, 40, 40, 40 };

		// 列、行表題のスタイル設定
		ssHierarchyList.initSpreadSheet(columnLabelMessageIDs1, columnWidths1);

		// スプレッドイベントの初期化
		ssHierarchyList.addSpreadSheetSelectChange(btnDepartmentCancellation);

		// Scroll位置設定
		ssHierarchyList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssHierarchyList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds1.setNumColumns(columnWidths1.length);
		ds1.setNumRows(0);

		ssHierarchyList.setDataSource(ds1);

		String[] columnLabelMessageIDs2 = new String[] { "C00698", "C00723" };
		int[] columnWidths2 = new int[] { 10, 40 };
		ssDepartmentList.initSpreadSheet(columnLabelMessageIDs2, columnWidths2);

		// 列、行表題のスタイル設定
		ssDepartmentList.initSpreadSheet(columnLabelMessageIDs2, columnWidths2);

		// スプレッドイベントの初期化
		ssDepartmentList.addSpreadSheetSelectChange(btnDepartmentAdd);

		// Scroll位置設定
		ssDepartmentList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssDepartmentList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds2.setNumColumns(columnWidths2.length);
		ds2.setNumRows(0);

		ssDepartmentList.setDataSource(ds2);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setHierarchyList(Vector cells) {

		// ssHierarchyListのデータを設定する
		ds1.setCells(cells);
		ds1.setNumRows(cells.size());
		ssHierarchyList.setDataSource(ds1);

		// 数値を右寄せする
		CellStyleModel defaultStyle = ssHierarchyList.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssHierarchyList.setCellStyle(JCTableEnum.ALLCELLS, 3, rightStyle);

		btnDepartmentAdd.setEnabled(cells.size() > 0);
		btnDepartmentCancellation.setEnabled(cells.size() > 0);
		btnUpperDepartment.setEnabled(cells.size() > 0);
		btnLowerDepartment.setEnabled(cells.size() > 0);

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDepartmentList(Vector cells) {

		ds2.setCells(cells);
		ds2.setNumRows(cells.size());
		ssDepartmentList.setDataSource(ds2);

		btnDepartmentAdd.setEnabled(cells.size() > 0);

	}

	/**
	 * 画面構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		btnSettle = new TImageButton(IconType.SETTLE);
		pnlDetail1 = new TPanel();
		ctrlOrganizationCode = new TLabelComboBox();
		txtDepartmentName = new TTextField();
		btnUpperDepartment = new TButton();
		btnLowerDepartment = new TButton();
		ctrlLevel0 = new TLabelField();
		pnlDetail2 = new TPanel();
		pnlDepartmentList = new TPanel();
		ssDepartmentList = new TTable();
		pnlButtonDepartment = new TPanel();
		btnDepartmentAdd = new TButton();
		btnDepartmentCancellation = new TButton();
		pnlLabelList = new TPanel();
		ssHierarchyList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(2);

		btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.createSsk();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);

		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(3);
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.deleteSsk();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);

		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(4);
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.excelOutput();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setMaximumSize(new Dimension(110, 25));
		btnSettle.setTabControlNo(5);
		btnSettle.setMinimumSize(new Dimension(110, 25));
		btnSettle.setPreferredSize(new Dimension(110, 25));
		btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.saveSsk();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 300, 5, 0);
		pnlButton.add(btnSettle, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(800, 60));
		pnlDetail1.setMinimumSize(new Dimension(800, 60));
		pnlDetail1.setPreferredSize(new Dimension(800, 60));

		ctrlOrganizationCode.setComboSize(80);
		ctrlOrganizationCode.setLabelSize(60);
		ctrlOrganizationCode.setLangMessageID("C00335");
		ctrlOrganizationCode.setTabControlNo(1);
		ctrlOrganizationCode.getComboBox().addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent ev) {
				ctrl.loadSsk();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridwidth = 1;
		gridBagConstraints.anchor = GridBagConstraints.SOUTHWEST;
		gridBagConstraints.insets = new Insets(5, 1, 5, 0);
		pnlDetail1.add(ctrlOrganizationCode, gridBagConstraints);
		// pnlDetail1.add(ctrlOrganizationCode, new GridBagConstraints());

		txtDepartmentName.setEnabled(false);
		txtDepartmentName.setMaxLength(40);
		txtDepartmentName.setMaximumSize(new Dimension(410, 20));
		txtDepartmentName.setMinimumSize(new Dimension(410, 20));
		txtDepartmentName.setPreferredSize(new Dimension(410, 20));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.gridwidth = 3;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlDetail1.add(txtDepartmentName, gridBagConstraints);

		btnUpperDepartment.setLangMessageID("C00719");
		btnUpperDepartment.setShortcutKey(KeyEvent.VK_F7);
		btnUpperDepartment.setMaximumSize(new Dimension(110, 25));
		btnUpperDepartment.setMinimumSize(new Dimension(110, 25));
		btnUpperDepartment.setPreferredSize(new Dimension(110, 25));
		btnUpperDepartment.setTabControlNo(6);

		btnUpperDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.chooseParent();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 350, 5, 0);
		pnlDetail1.add(btnUpperDepartment, gridBagConstraints);

		btnLowerDepartment.setLangMessageID("C00720");
		btnLowerDepartment.setShortcutKey(KeyEvent.VK_F8);
		btnLowerDepartment.setMaximumSize(new Dimension(110, 25));
		btnLowerDepartment.setMinimumSize(new Dimension(110, 25));
		btnLowerDepartment.setPreferredSize(new Dimension(110, 25));
		btnLowerDepartment.setTabControlNo(7);

		btnLowerDepartment.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.chooseChild();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(5, 10, 5, 0);
		pnlDetail1.add(btnLowerDepartment, gridBagConstraints);

		ctrlLevel0.setEditable(false);
		ctrlLevel0.setEnabled(false);
		ctrlLevel0.setFieldSize(120);
		ctrlLevel0.setLabelSize(50);
		ctrlLevel0.setLangMessageID("C00722");
		ctrlLevel0.setMaxLength(10);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 11, 10, 0);
		pnlDetail1.add(ctrlLevel0, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new GridBagLayout());

		pnlDetail2.setMaximumSize(new Dimension(780, 500));
		pnlDetail2.setMinimumSize(new Dimension(780, 500));
		pnlDetail2.setPreferredSize(new Dimension(780, 500));

		pnlDepartmentList.setLayout(new BoxLayout(pnlDepartmentList, BoxLayout.X_AXIS));

		pnlDepartmentList.setMaximumSize(new Dimension(280, 460));
		pnlDepartmentList.setMinimumSize(new Dimension(280, 460));
		pnlDepartmentList.setPreferredSize(new Dimension(280, 460));
		ssDepartmentList.setTabControlNo(10);
		pnlDepartmentList.add(ssDepartmentList);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlDetail2.add(pnlDepartmentList, gridBagConstraints);

		pnlButtonDepartment.setLayout(new GridBagLayout());

		pnlButtonDepartment.setMaximumSize(new Dimension(115, 460));
		pnlButtonDepartment.setMinimumSize(new Dimension(115, 460));
		pnlButtonDepartment.setPreferredSize(new Dimension(115, 460));

		btnDepartmentAdd.setLangMessageID("C03827");
		btnDepartmentAdd.setShortcutKey(KeyEvent.VK_F2);
		btnDepartmentAdd.setMaximumSize(new Dimension(115, 25));
		btnDepartmentAdd.setMinimumSize(new Dimension(115, 25));
		btnDepartmentAdd.setPreferredSize(new Dimension(115, 25));
		btnDepartmentAdd.setTabControlNo(8);
		btnDepartmentAdd.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.addDepartment();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 0);
		pnlButtonDepartment.add(btnDepartmentAdd, gridBagConstraints);

		btnDepartmentCancellation.setLangMessageID("C03828");
		btnDepartmentCancellation.setShortcutKey(KeyEvent.VK_F3);
		btnDepartmentCancellation.setMaximumSize(new Dimension(115, 25));
		btnDepartmentCancellation.setMinimumSize(new Dimension(115, 25));
		btnDepartmentCancellation.setPreferredSize(new Dimension(115, 25));
		btnDepartmentCancellation.setTabControlNo(9);
		btnDepartmentCancellation.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ctrl.removeDepartment();
			}
		});
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		pnlButtonDepartment.add(btnDepartmentCancellation, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 0, 10);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 0;
		gridBagConstraints.weighty = 1;
		pnlDetail2.add(pnlButtonDepartment, gridBagConstraints);

		pnlLabelList.setLayout(new BoxLayout(pnlLabelList, BoxLayout.X_AXIS));

		pnlLabelList.setMaximumSize(new Dimension(365, 460));
		pnlLabelList.setMinimumSize(new Dimension(365, 460));
		pnlLabelList.setPreferredSize(new Dimension(365, 460));
		ssHierarchyList.setTabControlNo(11);
		pnlLabelList.add(ssHierarchyList);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 0, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlDetail2.add(pnlLabelList, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail2, gridBagConstraints);

	}

	TImageButton btnDelete;

	TButton btnDepartmentAdd;

	TButton btnDepartmentCancellation;

	TImageButton btnListOutput;

	TButton btnLowerDepartment;

	TImageButton btnNew;

	TImageButton btnSettle;

	TButton btnUpperDepartment;

	TLabelField ctrlLevel0;

	TLabelComboBox ctrlOrganizationCode;

	TMainHeaderPanel pnlButton;

	TPanel pnlButtonDepartment;

	TPanel pnlDepartmentList;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlLabelList;

	TTable ssDepartmentList;

	TTable ssHierarchyList;

	TTextField txtDepartmentName;
}

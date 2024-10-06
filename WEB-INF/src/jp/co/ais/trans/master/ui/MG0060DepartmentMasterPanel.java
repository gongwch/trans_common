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
 */
public class MG0060DepartmentMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	private MG0060DepartmentMasterPanelCtrl ctrl;

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/** スプレッドシートDataSource */
	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0060DepartmentMasterPanel(MG0060DepartmentMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();
		super.initPanel();
		this.btnEdit.setEnabled(false);
		this.btnCopy.setEnabled(false);
		this.btnDelete.setEnabled(false);

	}

	/**
	 * スプレッドの初期化
	 */
	private void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00698", "C00723", "C00724", "C00725", "C00726",
				"C00727", "C00728", "C01303", "C00055", "C00261" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 10, 40, 20, 40, 6, 6, 8, 7, 6, 6, 0, 0 };
		ssDepartmentList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッドイベントの初期化
		ssDepartmentList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssDepartmentList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssDepartmentList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(11);
		ds.setNumRows(0);

		ssDepartmentList.setDataSource(ds);

	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssDepartmentList.setDataSource(ds);
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssDepartmentList.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 5, rightStyle);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 6, rightStyle);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 7, rightStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);
		ssDepartmentList.setCellStyle(JCTableEnum.ALLCELLS, 10, centerStyle);

		// 会社コードを非表示する
		ssDepartmentList.setColumnHidden(0, true);
		// 登録年月日を非表示する
		ssDepartmentList.setColumnHidden(11, true);
		// 更新年月日を非表示する
		ssDepartmentList.setColumnHidden(12, true);
		// プログラムＩＤを非表示する
		ssDepartmentList.setColumnHidden(13, true);
		// ユーザーＩＤを非表示する
		ssDepartmentList.setColumnHidden(14, true);
		// 部門区分を非表示する
		ssDepartmentList.setColumnHidden(15, true);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

	}

	private void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();

		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlDetail1 = new TPanel();
		pnlRangeSpecification = new TPanel();
		lblBeginDepartment = new TLabel();
		lblEndDepartment = new TLabel();
		ctrlBeginDepartment = new TButtonField();
		ctrlEndDepartment = new TButtonField();
		pnlDetail2 = new TPanel();
		ssDepartmentList = new TTable();

		setLayout(new GridBagLayout());
		setMaximumSize(new Dimension(800, 600));
		setMinimumSize(new Dimension(800, 600));
		setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));
		/**
		 * 新規ボタン
		 */
		btnNew.setLangMessageID("C00303");
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(4);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
			GridBagConstraints.NONE, new Insets(10, 0, 5, 0), 0, 0));
		btnNew.addActionListener(new ActionListener() {

			// 新規ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.insert();
			}
		});

		/**
		 * 検索ボタン
		 */
		btnSearch.setLangMessageID("C00155");
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
		btnSearch.setPreferredSize(new Dimension(110, 25));
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0, GridBagConstraints.CENTER,
			GridBagConstraints.NONE, new Insets(10, 10, 5, 0), 0, 0));
		btnSearch.addActionListener(new ActionListener() {

			// 検索ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.find();
			}
		});

		/**
		 * 編集ボタン
		 */
		btnEdit.setLangMessageID("C00481");
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnEdit, gridBagConstraints);
		btnEdit.addActionListener(new ActionListener() {

			// 編集ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.update();
			}
		});

		/**
		 * 複写ボタン
		 */
		btnCopy.setLangMessageID("C00459");
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(7);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnCopy, gridBagConstraints);
		btnCopy.addActionListener(new ActionListener() {

			// 複写ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.copy();
			}
		});

		/**
		 * 削除ボタン
		 */
		btnDelete.setLangMessageID("C01544");
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(8);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnDelete, gridBagConstraints);
		btnDelete.addActionListener(new ActionListener() {

			// 削除ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.delete();
			}
		});

		/**
		 * リスト出力ボタン
		 */
		btnListOutput.setLangMessageID("C01545");
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
		btnListOutput.setMaximumSize(new Dimension(130, 25));
		btnListOutput.setMinimumSize(new Dimension(130, 25));
		btnListOutput.setPreferredSize(new Dimension(130, 25));
		btnListOutput.setTabControlNo(9);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnListOutput, gridBagConstraints);
		btnListOutput.addActionListener(new ActionListener() {

			// リスト出力ボタンを押下
			public void actionPerformed(ActionEvent evt) {
				ctrl.outptExcel();
			}
		});

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		pnlDetail1.setLayout(new GridBagLayout());

		pnlDetail1.setMaximumSize(new Dimension(800, 83));
		pnlDetail1.setMinimumSize(new Dimension(800, 83));
		pnlDetail1.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(660, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(660, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(660, 80));

		lblBeginDepartment.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBeginDepartment, gridBagConstraints);

		lblEndDepartment.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEndDepartment, gridBagConstraints);

		ctrlBeginDepartment.setButtonSize(85);
		ctrlBeginDepartment.setFieldSize(120);
		ctrlBeginDepartment.setLangMessageID("C01302");
		ctrlBeginDepartment.setMaxLength(10);
		ctrlBeginDepartment.setNoticeSize(350);
		ctrlBeginDepartment.setTabControlNo(1);
		ctrlBeginDepartment.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginDepartment, gridBagConstraints);

		ctrlEndDepartment.setButtonSize(85);
		ctrlEndDepartment.setFieldSize(120);
		ctrlEndDepartment.setLangMessageID("C01302");
		ctrlEndDepartment.setMaxLength(10);
		ctrlEndDepartment.setNoticeSize(350);
		ctrlEndDepartment.setTabControlNo(2);
		ctrlEndDepartment.setImeMode(false);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndDepartment, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 60);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		pnlDetail1.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlDetail1, gridBagConstraints);

		pnlDetail2.setLayout(new BoxLayout(pnlDetail2, BoxLayout.X_AXIS));

		pnlDetail2.setMaximumSize(new Dimension(780, 460));
		pnlDetail2.setMinimumSize(new Dimension(780, 460));
		pnlDetail2.setPreferredSize(new Dimension(780, 460));
		pnlDetail2.add(ssDepartmentList);
		ssDepartmentList.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlDetail2, gridBagConstraints);

	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlBeginDepartment;

	TButtonField ctrlEndDepartment;

	TLabel lblBeginDepartment;

	TLabel lblEndDepartment;

	TMainHeaderPanel pnlButton;

	TPanel pnlDetail1;

	TPanel pnlDetail2;

	TPanel pnlRangeSpecification;

	TTable ssDepartmentList;

}

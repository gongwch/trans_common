package jp.co.ais.trans.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;

/**
 * レートマスタ
 */
public class MG0310RateMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	private MG0310RateMasterPanelCtrl ctrl;

	JCVectorDataSource ds = new JCVectorDataSource();

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0310RateMasterPanel(MG0310RateMasterPanelCtrl ctrl) {

		this.ctrl = ctrl;
		// 画面の初期化
		initComponents();
		// スプレッドの初期化
		initSpreadSheet();
		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();

		btnCopy.setEnabled(false);
		btnDelete.setEnabled(false);
		btnEdit.setEnabled(false);
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00665", "C02046", "C00556" };
		// スプレッド列幅の初期化
		int[] columnWidths = new int[] { 0, 6, 7, 11, 0, 0, 0, 0 };
		// 列、行表題のスタイル設定
		ssRateList.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		// スプレッド事件の初期化
		ssRateList.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssRateList.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssRateList.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(4);
		ds.setNumRows(0);
		ssRateList.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssRateList.setDataSource(ds);
		// 数値を右寄せする
		CellStyleModel defaultStyle = ssRateList.getDefaultCellStyle();
		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssRateList.setCellStyle(JCTableEnum.ALLCELLS, 3, rightStyle);

		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssRateList.setCellStyle(JCTableEnum.ALLCELLS, 1, centerStyle);
		ssRateList.setCellStyle(JCTableEnum.ALLCELLS, 2, centerStyle);

		// 会社コードを非表示する
		ssRateList.setColumnHidden(0, true);
		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);

	}

	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		GridBagConstraints gridBagConstraints3 = new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
			GridBagConstraints.CENTER, GridBagConstraints.NONE, new Insets(10, 10, 0, 0), 0, 0);
		gridBagConstraints3.gridx = 1;
		gridBagConstraints3.gridy = 0;
		pnlBusiness = new TPanel();
		pnlButton = new TPanel();
		btnNew = new TButton();
		btnSearch = new TButton();
		btnEdit = new TButton();
		btnCopy = new TButton();
		btnDelete = new TButton();
		btnListOutput = new TButton();
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		ctrlBeginCurrency = new TButtonField();
		ctrlEndCurrency = new TButtonField();
		lblBegin = new TLabel();
		lblEnd = new TLabel();
		pnlJournal = new TPanel();
		ssRateList = new TTable();

		setLayout(new GridBagLayout());

		pnlBusiness.setLayout(new GridBagLayout());

		pnlBusiness.setMaximumSize(new Dimension(800, 600));
		pnlBusiness.setMinimumSize(new Dimension(800, 600));
		pnlBusiness.setPreferredSize(new Dimension(800, 600));

		pnlButton.setLayout(new GridBagLayout());

		pnlButton.setMaximumSize(new Dimension(800, 40));
		pnlButton.setMinimumSize(new Dimension(800, 40));
		pnlButton.setPreferredSize(new Dimension(800, 40));

		/**
		 * 新規ボタン
		 */
		btnNew.setLangMessageID("C00303");
		btnNew.setMaximumSize(new Dimension(110, 25));
		btnNew.setMinimumSize(new Dimension(110, 25));
		btnNew.setPreferredSize(new Dimension(110, 25));
		btnNew.setTabControlNo(4);
		btnNew.setShortcutKey(KeyEvent.VK_F1);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 0, 5, 0);
		pnlButton.add(btnNew, gridBagConstraints);
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
		btnSearch.setMaximumSize(new Dimension(110, 25));
		btnSearch.setMinimumSize(new Dimension(110, 25));
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
		btnSearch.setShortcutKey(KeyEvent.VK_F2);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(10, 10, 5, 0);
		pnlButton.add(btnSearch, gridBagConstraints);
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
		btnEdit.setMaximumSize(new Dimension(110, 25));
		btnEdit.setMinimumSize(new Dimension(110, 25));
		btnEdit.setPreferredSize(new Dimension(110, 25));
		btnEdit.setTabControlNo(6);
		btnEdit.setShortcutKey(KeyEvent.VK_F3);
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
		btnCopy.setMaximumSize(new Dimension(110, 25));
		btnCopy.setMinimumSize(new Dimension(110, 25));
		btnCopy.setPreferredSize(new Dimension(110, 25));
		btnCopy.setTabControlNo(7);
		btnCopy.setShortcutKey(KeyEvent.VK_F4);
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
		btnDelete.setMaximumSize(new Dimension(110, 25));
		btnDelete.setMinimumSize(new Dimension(110, 25));
		btnDelete.setPreferredSize(new Dimension(110, 25));
		btnDelete.setTabControlNo(8);
		btnDelete.setShortcutKey(KeyEvent.VK_F5);
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
		btnListOutput.setLangMessageID("C03084");
		btnListOutput.setMaximumSize(new Dimension(120, 25));
		btnListOutput.setMinimumSize(new Dimension(120, 25));
		btnListOutput.setPreferredSize(new Dimension(120, 25));
		btnListOutput.setTabControlNo(9);
		btnListOutput.setShortcutKey(KeyEvent.VK_F6);
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
		pnlBusiness.add(pnlButton, gridBagConstraints);

		pnlHeader.setLayout(new GridBagLayout());

		pnlHeader.setMaximumSize(new Dimension(800, 83));
		pnlHeader.setMinimumSize(new Dimension(800, 83));
		pnlHeader.setPreferredSize(new Dimension(800, 83));

		pnlRangeSpecification.setLayout(new GridBagLayout());
		pnlRangeSpecification.setLangMessageID("C00433");
		pnlRangeSpecification.setMaximumSize(new Dimension(645, 80));
		pnlRangeSpecification.setMinimumSize(new Dimension(645, 80));
		pnlRangeSpecification.setPreferredSize(new Dimension(645, 80));

		ctrlBeginCurrency.setButtonSize(85);
		ctrlBeginCurrency.setFieldSize(45);
		ctrlBeginCurrency.setImeMode(false);
		ctrlBeginCurrency.setLangMessageID("C01241");
		ctrlBeginCurrency.setMaxLength(3);
		ctrlBeginCurrency.setMaximumSize(new Dimension(420, 20));
		ctrlBeginCurrency.setMinimumSize(new Dimension(420, 20));
		ctrlBeginCurrency.setNoticeSize(410);
		ctrlBeginCurrency.setTabControlNo(1);
		ctrlBeginCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginCurrency, gridBagConstraints);

		ctrlEndCurrency.setButtonSize(85);
		ctrlEndCurrency.setFieldSize(45);
		ctrlEndCurrency.setLangMessageID("C01241");
		ctrlEndCurrency.setMaxLength(3);
		ctrlEndCurrency.setNoticeSize(410);
		ctrlEndCurrency.setTabControlNo(2);
		ctrlEndCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndCurrency, gridBagConstraints);

		lblBegin.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblBegin, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 75);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		pnlBusiness.add(pnlHeader, gridBagConstraints);

		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		ssRateList.setTabControlNo(3);
		pnlJournal.add(ssRateList);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		pnlBusiness.add(pnlJournal, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlBusiness, gridBagConstraints);

	}

	TButton btnCopy;

	TButton btnDelete;

	TButton btnEdit;

	TButton btnListOutput;

	TButton btnNew;

	TButton btnSearch;

	TButtonField ctrlBeginCurrency;

	TButtonField ctrlEndCurrency;

	TLabel lblBegin;

	TLabel lblEnd;

	TPanel pnlButton;

	TPanel pnlHeader;

	TPanel pnlJournal;

	TPanel pnlRangeSpecification;

	TTable ssRateList;

	TPanel pnlBusiness;

}

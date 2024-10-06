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
 * 通貨マスタ画面
 */
public class MG0300CurrencyMasterPanel extends TPanelBusiness {

	/** コントロールクラス */
	private MG0300CurrencyMasterPanelCtrl ctrl;

	JCVectorDataSource ds = new JCVectorDataSource();

	/**
	 * コンストラクタ.
	 * 
	 * @param ctrl コントロールクラス
	 */
	MG0300CurrencyMasterPanel(MG0300CurrencyMasterPanelCtrl ctrl) {
		this.ctrl = ctrl;
		initComponents();
		initSpreadSheet();

		// ** messageID変換, tab順登録のため、initComponents()の後に必ず呼ぶこと */
		super.initPanel();

		btnCopy.setEnabled(false);
		btnDelete.setEnabled(false);
		btnEdit.setEnabled(false);
	}

	protected void initSpreadSheet() {
		// SpreadSheet を init する
		String[] columnLabelMessageIDs = new String[] { "C00596", "C00665", "C00880", "C00881", "C00882", "C00896",
				"C00897", "C00884", "C00055", "C00261" };
		int[] columnWidths = new int[] { 0, 6, 40, 20, 20, 6, 5, 10, 6, 6, 0, 0, 0, 0, 0, 0, 0 };
		ssJournal.initSpreadSheet(columnLabelMessageIDs, columnWidths);
		ssJournal.addSpreadSheetSelectChange(btnEdit);

		// Scroll位置設定
		ssJournal.setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		ssJournal.setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		ds.setNumColumns(10);
		ds.setNumRows(0);
		ssJournal.setDataSource(ds);
	}

	/**
	 * スプレッドデータの設定
	 * 
	 * @param cells スプレッドデータ
	 */
	void setDataList(Vector cells) {
		ds.setCells(cells);
		ds.setNumRows(cells.size());
		ssJournal.setDataSource(ds);

		// 数値を右寄せするCellStyleModel
		CellStyleModel defaultStyle = ssJournal.getDefaultCellStyle();
		JCCellStyle centerStyle = new JCCellStyle(defaultStyle);
		centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 1, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 6, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 8, centerStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 9, centerStyle);

		JCCellStyle leftStyle = new JCCellStyle(defaultStyle);
		leftStyle.setHorizontalAlignment(CellStyleModel.LEFT);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 2, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 3, leftStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 4, leftStyle);

		JCCellStyle rightStyle = new JCCellStyle(defaultStyle);
		rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 5, rightStyle);
		ssJournal.setCellStyle(JCTableEnum.ALLCELLS, 7, rightStyle);

		this.btnCopy.setEnabled(cells.size() > 0);
		this.btnDelete.setEnabled(cells.size() > 0);
		this.btnEdit.setEnabled(cells.size() > 0);
	}

	/** シリアルUID */
	private static final long serialVersionUID = 6975993877695662983L;

	/**
	 * 画面コンポーネントの構築
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		pnlButton = new TMainHeaderPanel();
		btnNew = new TImageButton(IconType.NEW);
		btnSearch = new TImageButton(IconType.SEARCH);
		btnEdit = new TImageButton(IconType.EDIT);
		btnCopy = new TImageButton(IconType.COPY);
		btnDelete = new TImageButton(IconType.DELETE);
		btnListOutput = new TImageButton(IconType.EXCEL);
		pnlHeader = new TPanel();
		pnlRangeSpecification = new TPanel();
		ctrlBeginCurrency = new TButtonField();
		ctrlEndCurrency = new TButtonField();
		lblStart = new TLabel();
		lblEnd = new TLabel();
		pnlJournal = new TPanel();
		ssJournal = new TTable();

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
		btnSearch.setPreferredSize(new Dimension(110, 25));
		btnSearch.setTabControlNo(5);
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
		gridBagConstraints.insets = new Insets(0, 0, 0, 0);
		gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
		add(pnlButton, gridBagConstraints);

		// 境界線
		JSeparator sep = new JSeparator();
		TGuiUtil.setComponentSize(sep, 0, 3);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridy = 1;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		add(sep, gridBagConstraints);

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
		ctrlBeginCurrency.setMaximumSize(new Dimension(420, 20));
		ctrlBeginCurrency.setMinimumSize(new Dimension(420, 20));
		ctrlBeginCurrency.setTabControlNo(1);
		ctrlBeginCurrency.setNoticeSize(410);
		ctrlBeginCurrency.setLangMessageID("C01241");
		ctrlBeginCurrency.setMaxLength(3);
		ctrlBeginCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlBeginCurrency, gridBagConstraints);

		/**
		 * 終了通貨コード検索
		 */
		ctrlEndCurrency.setButtonSize(85);
		ctrlEndCurrency.setFieldSize(45);
		ctrlEndCurrency.setNoticeSize(410);
		ctrlEndCurrency.setLangMessageID("C01241");
		ctrlEndCurrency.setMaxLength(3);
		ctrlEndCurrency.setTabControlNo(2);
		ctrlEndCurrency.setImeMode(false);
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 0, 5, 10);
		pnlRangeSpecification.add(ctrlEndCurrency, gridBagConstraints);

		lblStart.setLangMessageID("C01497");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblStart, gridBagConstraints);

		lblEnd.setLangMessageID("C01503");
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 1;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 10, 5, 5);
		pnlRangeSpecification.add(lblEnd, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.insets = new Insets(0, 0, 0, 75);
		pnlHeader.add(pnlRangeSpecification, gridBagConstraints);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 2;
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(pnlHeader, gridBagConstraints);

		pnlJournal.setLayout(new BoxLayout(pnlJournal, BoxLayout.X_AXIS));

		pnlJournal.setMaximumSize(new Dimension(780, 460));
		pnlJournal.setMinimumSize(new Dimension(780, 460));
		pnlJournal.setPreferredSize(new Dimension(780, 460));
		pnlJournal.add(ssJournal);
		ssJournal.setTabControlNo(3);

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 3;
		gridBagConstraints.insets = new Insets(5, 5, 10, 5);
		gridBagConstraints.anchor = GridBagConstraints.WEST;
		gridBagConstraints.fill = GridBagConstraints.BOTH;
		gridBagConstraints.weightx = 1;
		gridBagConstraints.weighty = 1;
		add(pnlJournal, gridBagConstraints);
	}

	TImageButton btnCopy;

	TImageButton btnDelete;

	TImageButton btnEdit;

	TImageButton btnListOutput;

	TImageButton btnNew;

	TImageButton btnSearch;

	TButtonField ctrlEndCurrency;

	TButtonField ctrlBeginCurrency;

	TLabel lblEnd;

	TLabel lblStart;

	TMainHeaderPanel pnlButton;

	TPanel pnlHeader;

	TPanel pnlJournal;

	TPanel pnlRangeSpecification;

	TTable ssJournal;

}

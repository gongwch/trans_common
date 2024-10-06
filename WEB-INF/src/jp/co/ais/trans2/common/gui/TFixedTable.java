package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * 固定列テーブルコンポーネント
 * 
 * @author AIS
 */
public class TFixedTable extends TTable {

	/** 固定列Table */
	public BaseTable fixedTable;

	/** 固定列Tableの初期化Ctrl */
	public TFixedTableInitial ctrl;

	/** モデール */
	public TableModel tblModel;

	/**
	 * コンストラクター
	 * 
	 * @param ctrl
	 */
	public TFixedTable(TFixedTableInitial ctrl) {
		super();

		this.ctrl = ctrl;
		init(this.ctrl.getFrozenCols());
	}

	/**
	 * 初期処理
	 */
	@Override
	public void init() {
		// 処理なし
	}

	/**
	 * @return 行ヘッダー用Table
	 */
	@Override
	protected BaseTable createRowHeaderTable() {
		return new BaseTable();
	}

	/**
	 * @return 行番号スプレッド
	 */
	public BaseTable getRowHeaderTable() {
		return (BaseTable) rowHeaderComp;
	}

	/**
	 * 列固定設定
	 * 
	 * @param frozenCols
	 */
	public void init(int frozenCols) {
		// 通常テーブルの設定

		// ScrollPane透過
		this.setOpaque(false);
		this.getViewport().setOpaque(false);

		table = createBaseTable();
		setViewportTable(table);
		table.adapter = createTTableAdapter();
		adapter = table.adapter;
		adapter.setTable(table);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setSurrendersFocusOnKeystroke(true);

		table.setRowHeight(rowHeight);

		// 行番号
		tblModel = table.getModel();
		rowHeaderComp = createBaseTable(tblModel);
		rowHeaderView = getRowHeaderView();

		this.setRowHeaderView(rowHeaderComp);
		getRowHeader().setOpaque(false); // 透過

		// 固定列テーブル
		fixedTable = getRowHeaderTable();

		fixedTable.adapter = table.adapter;
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fixedTable.setDefaultEditor(Object.class, null);
		fixedTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		fixedTable.setSurrendersFocusOnKeystroke(true);

		fixedTable.setRowHeight(rowHeight);

		// 列設定
		ctrl.initSpread(this);

		// 固定列テーブルも初期化
		JTableHeader header = fixedTable.getTableHeader();
		TableCellRenderer hr = header.getDefaultRenderer();

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) fixedTable.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {

			columns.get(i).setColumn(columnModel.getColumn(i));

			int width = columns.get(i).getWidth();
			boolean visible = columns.get(i).isVisible();

			if (width < 0 || !visible) {
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setMaxWidth(0);
				columnModel.getColumn(i).setPreferredWidth(0);

			} else {
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setPreferredWidth(width);
			}

			TableColumn col = fixedTable.getColumnModel().getColumn(i);
			TTableComponent colComponent = columns.get(i).getComponent();

			// タイトル
			if (colComponent != null && colComponent instanceof TTableColumnHeader) {
				// チャートの場合、ヘッダーは特別対応
				TTableColumnHeader colHeader = (TTableColumnHeader) colComponent;
				TableCellRenderer renderer = createTableCellRenderer(colHeader);
				col.setHeaderRenderer(renderer);
			} else {
				HeaderRenderer headerRenderer = createHeaderRenderer(hr);
				headerRenderer.setVerticalAlign(columns.get(i).getVerticalAlign());
				col.setHeaderRenderer(headerRenderer);
			}

			// セル
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(columns.get(i).getHorizontalAlign());
			columnModel.getColumn(i).setCellRenderer(createCellRenderer(r));
			if (colComponent != null) {
				if (colComponent.getCellRenderer(this) != null) {
					fixedTable.getColumnModel().getColumn(i).setCellRenderer(colComponent.getCellRenderer(this));
				}
				if (colComponent.getCellEditor(this) != null) {
					fixedTable.getColumnModel().getColumn(i).setCellEditor(colComponent.getCellEditor(this));
				}
			}
		}

		// 固定列設定

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		fixedTable.setSelectionModel(table.getSelectionModel());
		for (int i = model.getColumnCount() - 1; i >= 0; i--) {
			if (i < frozenCols) {
				table.removeColumn(table.getColumnModel().getColumn(i));
				fixedTable.getColumnModel().getColumn(i).setResizable(false);
			} else {
				fixedTable.removeColumn(fixedTable.getColumnModel().getColumn(i));
			}
		}

		// スクロール設定
		fixedTable.setPreferredScrollableViewportSize(fixedTable.getPreferredSize());
		setRowHeaderView(fixedTable);
		setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
		getViewport().setBackground(Color.WHITE);
		getRowHeader().setBackground(Color.WHITE);

		// 連動処理
		getRowHeader().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JViewport vp = (JViewport) e.getSource();
				getVerticalScrollBar().setValue(vp.getViewPosition().y);
			}
		});

		// 通常テーブルのイベント設定

		addKeyEvent();
		addKeyListener();
		addMouseListener();

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter); // 固定部のソート同期

		// 右クリックメニュー初期化
		initPopupMenu();
	}

	/**
	 * ベーステーブル
	 * 
	 * @param model
	 * @return ベーステーブル
	 */
	protected BaseTable createBaseTable(TableModel model) {
		return new BaseTable(model);
	}

	@Override
	public void removeRow() {
		table.setRowSorter(null);
		fixedTable.setRowSorter(null);

		try {
			super.removeRow();
		} catch (IndexOutOfBoundsException ex) {
			// エラー無視
		}

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter);
	}

	@Override
	public void removeRow(int row) {
		table.setRowSorter(null);
		fixedTable.setRowSorter(null);

		try {
			super.removeRow(row);
		} catch (IndexOutOfBoundsException ex) {
			// エラー無視
		}

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter);
	}

	/**
	 * Mouse Right Clicked
	 * 
	 * @param e
	 */
	@Override
	protected void onMouseRightClicked(MouseEvent e) {
		// 左上余白の右クリック
		// 一旦封印
	}

	/**
	 * TableHeader MouseClicked
	 * 
	 * @param e
	 */
	@Override
	protected void onTableHeaderMouseClicked(MouseEvent e) {
		// #51334 表示設定クリア機能の対応、一部封印解除
		// →自動幅調整はまだ障害があるため、封印中
		// if (SwingUtilities.isRightMouseButton(e)) {
		// // 一旦封印
		// return;
		// }

		super.onTableHeaderMouseClicked(e);

		// 固定列のヘッダクリック処理
		if (ctrl.getFrozenCols() > 0) {
			// チェックボックスのヘッダーをクリックした場合は全選択
			int fixedTblCol = fixedTable.getTableHeader().columnAtPoint(e.getPoint());

			DefaultTableColumnModel columnModel = (DefaultTableColumnModel) fixedTable.getColumnModel();

			// ヘッダーをクリック前
			adapter.beforeHeaderClicked(fixedTblCol);

			if (fixedTblCol >= 0 && columnModel.getColumn(fixedTblCol).getCellEditor() instanceof TCheckBoxEditor
				&& getRowCount() > 0 && allCheckWhenHeaderClicked) {

				// 編集中の場合は一旦確定
				if (fixedTable.isEditing()) {
					fixedTable.getCellEditor().stopCellEditing();
				}

				// 1行目のチェック状態を反転。他の行もその状態に従い、設定する
				boolean isSelected = getNextSwitchBoolean(fixedTable, fixedTblCol);

				for (int i = 0; i < getRowCount(); i++) {
					boolean isEditable = fixedTable.isCellEditable(i, fixedTblCol);

					TCheckBoxEditor editor = null;
					if (columnModel.getColumn(fixedTblCol).getCellEditor() instanceof TCheckBoxEditor) {
						editor = (TCheckBoxEditor) columnModel.getColumn(fixedTblCol).getCellEditor();
						isEditable &= editor.isCellEditable(i, fixedTblCol);
					}

					if (isEditable) {
						fixedTable.setValueAt(isSelected, fixedTable.convertRowIndexToModel(i), fixedTblCol);

						if (editor != null) {
							TCheckBox chk = (TCheckBox) editor.getComponent();
							chk.setSelected(isSelected);
							chk.setIndex(i);
							if (chk.getActionListeners() != null || chk.getActionListeners().length != 0) {
								for (int lc = 0; lc < chk.getActionListeners().length; lc++) {
									chk.getActionListeners()[lc].actionPerformed(null);
								}
							}
						}
					}
				}

				fixedTable.repaint();
			}

			// ヘッダーをクリック後
			adapter.afterHeaderClicked(fixedTblCol);
		}
	}

	/**
	 * 右クリックメニュー初期化
	 */
	@Override
	protected void initPopupMenu() {

		// 右クリックメニュー初期化
		popupMenu = new JPopupMenu();

		JMenuItem clear = new JMenuItem(popupWords[0]);
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTableInformation();
			}
		});

		popupMenu.add(clear);

		// #51334 表示設定クリア機能の対応、一部封印解除
		// →自動幅調整はまだ障害があるため、封印中
		// JMenuItem autosizeHeaderAndContents = new JMenuItem(popupWords[1]);
		// autosizeHeaderAndContents.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // 幅自動調整
		// adjustTableInformation(AutoSizeType.HeaderAndContents);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeHeaderAndContents);
		//
		// JMenuItem autosizeHeader = new JMenuItem(popupWords[2]);
		// autosizeHeader.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // 幅自動調整
		// adjustTableInformation(AutoSizeType.HeaderOnly);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeHeader);
		//
		// JMenuItem autosizeContents = new JMenuItem(popupWords[3]);
		// autosizeContents.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // 幅自動調整
		// adjustTableInformation(AutoSizeType.ContentsOnly);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeContents);
	}

	/**
	 * テーブル情報をクリア
	 */
	@Override
	protected void clearTableInformation() {
		FileUtil.removeTempolaryFile(getTableKeyName() + ".table");

		// 列順復元
		if (ctrl.getFrozenCols() > 0) {
			restoreColumns(initTableInformation, fixedTable);
		}
		restoreColumns(initTableInformation, table);

		// 列幅復元
		if (ctrl.getFrozenCols() > 0) {
			restoreWidth(initTableInformation, fixedTable);
		}
		restoreWidth(initTableInformation, table);

		// テーブル情報保存
		saveTableInformation();

		if (ctrl.getFrozenCols() > 0) {
			fixedTable.repaint();
		}
		table.repaint();
	}

	// /**
	// * テーブル幅自動調整
	// *
	// * @param type 幅自動調整のタイプ
	// */
	// @Override
	// protected void adjustTableInformation(AutoSizeType type) {
	//
	// // 列順復元
	// if (ctrl.getFrozenCols() > 0) {
	// restoreColumns(initTableInformation, fixedTable);
	// }
	// restoreColumns(initTableInformation, table);
	//
	// // 幅自動調整
	// if (ctrl.getFrozenCols() > 0) {
	// autosizeColumnWidth(false, type, fixedTable);
	// }
	// autosizeColumnWidth(false, type, table);
	//
	// // テーブル情報保存
	// saveTableInformation();
	// }

	/**
	 * カラム状態の復元
	 */
	@Override
	public void restoreComponent() {
		restoreComponent(getKey());
	}

	/**
	 * @param key
	 */
	@Override
	public void restoreComponent(TStorableKey key) {
		try {
			if (Util.isNullOrEmpty(tableKeyName) && key != null) {
				tableKeyName = key.getKey();
			}

			if (Util.isNullOrEmpty(tableKeyName)) {
				return;
			}

			TTableInformation tableInformation = getPreviousTableInformation();

			if (tableInformation == null) {
				return;
			}

			// 列順復元
			if (ctrl.getFrozenCols() > 0) {
				restoreColumns(initTableInformation, fixedTable);
			}
			restoreColumns(initTableInformation, table);

			// 列幅復元
			if (ctrl.getFrozenCols() > 0) {
				restoreWidth(initTableInformation, fixedTable);
			}
			restoreWidth(initTableInformation, table);

			if (ctrl.getFrozenCols() > 0) {
				fixedTable.repaint();
			}
			table.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * カラム位置復元
	 * 
	 * @param tableInformation テーブル情報
	 * @param tbl
	 */
	protected void restoreColumns(TTableInformation tableInformation, JTable tbl) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> dispOrderList = tableInformation.getDisplayOrder();
		int addon = 0;

		if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
			// 右テーブル
			addon = ctrl.getFrozenCols();
		}

		if (dispOrderList != null && !dispOrderList.isEmpty() && dispOrderList.size() - addon >= columnCount) {
			for (int i = 0; i < columnCount; i++) {
				int to = i;
				int index = i + addon;

				int displayNo = dispOrderList.get(index);

				// 現在地特定
				int j = 0;
				now: for (; j < columnCount; j++) {
					if (columnModel.getColumn(j).getModelIndex() == displayNo) {
						break now;
					}
				}

				tbl.moveColumn(j, to);
			}
		}
	}

	/**
	 * 列幅復元
	 * 
	 * @param tableInformation テーブル情報
	 * @param tbl
	 */
	public void restoreWidth(TTableInformation tableInformation, JTable tbl) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> widthList = tableInformation.getWidthList();
		int addon = 0;

		if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
			// 右テーブル
			addon = ctrl.getFrozenCols();
		}

		if (widthList != null && !widthList.isEmpty() && widthList.size() + addon >= columnCount) {

			for (int i = 0; i < columnCount; i++) {
				int index = i + addon;

				int width = widthList.get(index);

				if (width < 0 || !columns.get(index).isVisible()) {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setMaxWidth(0);
					columnModel.getColumn(i).setPreferredWidth(0);

				} else {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setPreferredWidth(width);
				}

				if (!columns.get(index).isVisible()) {
					columns.get(index).setWidth(width);
				}
			}
		}
	}

	// /**
	// * 列幅自動計算<br>
	// *
	// * @param isZeroAdjust <br>
	// * true:幅がゼロの場合、自動計算する false:幅がゼロの場合、自動計算しない
	// * @param type 幅自動調整のタイプ
	// * @param tbl
	// */
	// public void autosizeColumnWidth(boolean isZeroAdjust, AutoSizeType type, JTable tbl) {
	//
	// DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
	// int columnCount = columnModel.getColumnCount();
	//
	// List<Integer> widthList = initTableInformation.getWidthList();
	// int addon = 0;
	//
	// if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
	// // 右テーブル
	// addon = ctrl.getFrozenCols();
	// }
	//
	// if (widthList != null && !widthList.isEmpty() && widthList.size() >= columnCount) {
	//
	// for (int i = 0; i < columnCount; i++) {
	//
	// int index = i + addon;
	//
	// int width = widthList.get(index);
	//
	// if (width < 0 || !columns.get(index).isVisible()) {
	// columnModel.getColumn(i).setMinWidth(0);
	// columnModel.getColumn(i).setMaxWidth(0);
	// columnModel.getColumn(i).setPreferredWidth(0);
	//
	// } else {
	// columnModel.getColumn(i).setMinWidth(0);
	//
	// if (isZeroAdjust || width != 0) {
	// // 列幅自動計算(useTitle=trueの場合、列タイトルの最大幅で)
	// width = getAdjustColumnWidth(columnModel.getColumn(i), width, type, index, tbl);
	// }
	// columnModel.getColumn(i).setPreferredWidth(width);
	// }
	//
	// if (!columns.get(index).isVisible()) {
	// columns.get(index).setWidth(width);
	// }
	// }
	// }
	// }
	//
	// /**
	// * 列幅自動計算値の取得
	// *
	// * @param column 列
	// * @param colWidth 設定列幅
	// * @param type 幅自動調整のタイプ
	// * @param colIndex 指定列インデックス
	// * @param tbl
	// * @return 列幅
	// */
	// public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex, JTable tbl) {
	//
	// if (fm == null) {
	// fm = tbl.getFontMetrics(tbl.getFont());
	// }
	//
	// String title = null;
	// if (AutoSizeType.HeaderAdjust.equals(type)) {
	// // 英語ユーザの場合、列幅自動計算
	//
	// String header = Util.avoidNullNT(column.getHeaderValue());
	// header = header.replaceAll("\\<html\\>", "");
	// header = header.replaceAll("\\</html\\>", "");
	// header = header.replaceAll("\\<center\\>", "");
	// header = header.replaceAll("\\</center\\>", "");
	// header = header.replaceAll("\\<br\\>", "");
	//
	// title = header;
	// int width = getWidthWithHtml(title);
	//
	// // 足りない場合計算値を戻る
	// return Math.max(colWidth, width);
	//
	// } else {
	// int width = getMaxWidth(column, colIndex, type, tbl);
	// return width;
	//
	// }
	//
	// }
	//
	// /**
	// * 指定列の最大文字列の取得
	// *
	// * @param column 指定列オブジェクト
	// * @param col 指定列インデックス
	// * @param type 幅自動調整のタイプ
	// * @param tbl
	// * @return 指定列の最大文字列
	// */
	// public int getMaxWidth(TableColumn column, int col, AutoSizeType type, JTable tbl) {
	//
	// int addon = 0;
	//
	// if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
	// // 右テーブル
	// addon = ctrl.getFrozenCols();
	// }
	//
	// int colIndex = tbl.convertColumnIndexToModel(col - addon);// 実列インデックス
	// int addonWidth = fm.stringWidth("W"); // デフォルト幅
	//
	// String value = "";
	// if (AutoSizeType.HeaderAndContents.equals(type) || AutoSizeType.HeaderOnly.equals(type)) {
	// // ヘッダー含んだ場合のみ
	// value = Util.avoidNullNT(column.getHeaderValue());
	// }
	//
	// int valueWidth = getWidthWithHtml(value);
	//
	// if (AutoSizeType.HeaderOnly.equals(type)) {
	// // ヘッダーのみの場合、戻る(値は計算しない)
	// return addonWidth + valueWidth;
	// }
	//
	// for (int i = 0; i < getRowCount(); i++) {
	//
	// // コンポかどうか判定（設定値がStringではない場合、変更なし）
	// if (getCellEditor(i, colIndex) instanceof TBaseCellEditor
	// || getCellEditor(i, colIndex) instanceof TableCellRenderer
	// || !(getRowValueAt(i, colIndex) instanceof String)) {
	// return initTableInformation.getWidthList().get(colIndex);
	// }
	//
	// // 値
	// String cellValue = Util.avoidNullNT(getRowValueAt(i, colIndex));
	// int cellWidth = getWidthWithHtml(cellValue);
	//
	// // 一番長い幅取得
	// if (valueWidth < cellWidth) {
	// valueWidth = cellWidth;
	// }
	// }
	//
	// return addonWidth + valueWidth;
	// }

	/**
	 * テーブル情報保存のためマウスイベント追加
	 */
	@Override
	protected void addMouseListener() {
		super.addMouseListener();

		// テーブルヘッダリスナー登録
		fixedTable.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				onTableHeaderMouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (fixedTable.isEditing()) {
					fixedTable.getCellEditor().stopCellEditing();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					// テーブル情報保存
					saveTableInformation();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}

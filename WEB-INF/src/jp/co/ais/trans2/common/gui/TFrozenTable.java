package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;

/**
 * 固定列ありテーブル <br>
 * <b>TFixedTable</b>を利用してください。
 * 
 * @deprecated
 */
public class TFrozenTable extends jp.co.ais.trans.common.gui.TTable {

	/** ヘッダーのカラー */
	protected static Color columnColor = new Color(90, 90, 90);

	/** ヘッダーのフォントカラー */
	protected static Color columnFontColor = Color.WHITE;

	/** 未選択時のカラー */
	protected static Color notSelectedColor = new Color(255, 255, 255);

	/** 未選択時のカラー(別色) */
	protected static Color notSelectedColor2 = new Color(230, 240, 250);

	/** 未選択時のフォントカラー */
	protected static Color cellFontColor = Color.BLACK;

	/** 行の高さ */
	protected static int rowHeight = 25;

	/** 右寄せセルスタイル */
	protected static JCCellStyle rightStyle = null;

	/** 中央寄せセルスタイル */
	protected static JCCellStyle centerStyle = null;

	/** カラム */
	protected List<TTableColumn> columns = null;

	/** カラムインデックス */
	protected Map<Enum, Integer> indexs = null;

	/**
	 * コンストラクタ
	 */
	public TFrozenTable() {
		//
		initStyle();
	}

	/**
	 * TTableデフォルト設定
	 */
	protected void initStyle() {

		// 初期値取得
		Color columnColor_ = ClientConfig.getTableLabelColor();
		Color columnFontColor_ = ClientConfig.getTableLabelFontColor();
		Color notSelectedColor_ = ClientConfig.getTableCellBackColor1();
		Color notSelectedColor2_ = ClientConfig.getTableCellBackColor2();
		Color cellFontColor_ = ClientConfig.getTableCellFontColor();
		int rowHeight_ = ClientConfig.getTableCellHeight();

		if (columnColor_ != null) {
			columnColor = columnColor_;
		}

		if (columnFontColor_ != null) {
			columnFontColor = columnFontColor_;
		}

		if (notSelectedColor_ != null) {
			notSelectedColor = notSelectedColor_;
		}

		if (notSelectedColor2_ != null) {
			notSelectedColor2 = notSelectedColor2_;
		}

		if (cellFontColor_ != null) {
			cellFontColor = cellFontColor_;
		}

		if (rowHeight_ != 0) {
			rowHeight = rowHeight_;
		}

		// 標準コンポ
		TTextField comp = new TTextField();

		// 表題スタイル
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setFont(comp.getFont());
		labelStyle.setForeground(columnFontColor);
		labelStyle.setBackground(columnColor);

		// セルスタイル
		CellStyleModel cellStyle = this.getDefaultCellStyle();
		Color[] cellBackColor = { notSelectedColor2, notSelectedColor };
		cellStyle.setForeground(cellFontColor);
		cellStyle.setRepeatBackgroundColors(cellBackColor);

		// セルボーダー
		cellStyle.setCellBorder(new JCCellBorder(JCTableEnum.BORDER_THIN));

		// 行の高さ
		this.setPixelHeight(JCTableEnum.ALL, rowHeight);

		// カーソル
		setTrackCursor(false);

		// セルスタイル初期化
		if (rightStyle == null) {
			rightStyle = new JCCellStyle(getDefaultCellStyle());
			rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		}

		if (centerStyle == null) {
			centerStyle = new JCCellStyle(getDefaultCellStyle());
			centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		}
	}

	/**
	 * 初期化
	 */
	public void init() {

		String[] colMessageIDs = new String[columns.size()];
		int[] colWidths = new int[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			TTableColumn column = columns.get(i);
			colMessageIDs[i] = column.getTitle();
			int ceil = column.getWidth() % 12;
			colWidths[i] = (column.getWidth() - ceil) / 12 + (ceil > 0 ? 1 : 0);

			// セルスタイル設定
			if (column.getHorizontalAlign() == SwingConstants.RIGHT) {
				setCellStyle(JCTableEnum.ALLCELLS, i, rightStyle);
			} else if (column.getHorizontalAlign() == SwingConstants.CENTER) {
				setCellStyle(JCTableEnum.ALLCELLS, i, centerStyle);
			}
		}

		// 列、行表題のスタイル設定
		initSpreadSheet(colMessageIDs, colWidths);

		// Scroll位置設定
		setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// 初期表示データの構築
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		if (ds == null) {
			ds = new JCVectorDataSource();
		}

		ds.setNumColumns(columns.size());
		ds.setNumRows(0);

		setDataSource(ds);

		// ソート可能
		setSort(true);
	}

	/**
	 * 行追加
	 * 
	 * @param list 行データ
	 */
	public void addRow(List<Object> list) {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		Vector<Vector> cells = ds.getCells();

		if (cells == null) {
			cells = new Vector<Vector>();
		}

		Vector<Object> row = new Vector<Object>();
		row.setSize(columns.size());

		for (int i = 0; i < list.size(); i++) {
			row.set(i, list.get(i));
		}

		int rowIndex = cells.size();
		cells.add(rowIndex, row);

		ds.setCells(cells);
		ds.setNumRows(cells.size());

		setDataSource(ds);
	}

	/**
	 * 全ての行削除
	 */
	public void removeRow() {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		ds.clearCells();
		ds.setNumRows(0);
		setDataSource(ds);
	}

	/**
	 * 行数の取得
	 * 
	 * @return 行数
	 */
	public int getRowCount() {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		return ds.getNumRows();
	}

	/**
	 * 選択モード設定<br>
	 * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION:複数行選択モード<br>
	 * 以外、行選択モード
	 * 
	 * @param model
	 */
	public void setSelectionMode(int model) {
		switch (model) {
			case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION: {
				super.setSelectMultiRange(true);
				break;
			}
			default: {
				super.setSelectionPolicy(JCTableEnum.SELECT_SINGLE);
			}

		}
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 */
	public void addColumn(Enum e, String title, int width) {
		addColumn(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * 列のタイトル、幅、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param visible 表示/非表示
	 */
	public void addColumn(Enum e, String title, int width, boolean visible) {
		addColumn(e, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 */
	public void addColumn(String title, int width) {
		addColumn(null, title, width, SwingConstants.LEFT);
	}

	/**
	 * 列のタイトル、幅を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 */
	public void addColumn(String title, int width, int horizontalAlign) {
		addColumn(null, title, width, horizontalAlign);
	}

	/**
	 * 列のタイトル、幅、表示/非表示を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param visible 表示/非表示
	 */
	public void addColumn(String title, int width, boolean visible) {
		addColumn(null, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign));
	}

	/**
	 * 列のタイトル、幅、水平方向表示位置、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param horizontalAlign 水平方向表示位置
	 * @param visible 表示/非表示
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign, visible));
	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 */
	public void addColumn(Enum e, String title, int width, Class<? extends TTableComponent> component) {
		try {
			TTableComponent instance = component.newInstance();
			addColumn(new TTableColumn(e, title, width, instance.getDefaultCellRendererHorizontalAlignment(), instance));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component) {
		try {

			addColumn(new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネント、表示/非表示を指定して列を追加する
	 * 
	 * @param e 列識別番号
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 * @param visible 表示/非表示
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネントを指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 */
	public void addColumn(String title, int width, TTableComponent component) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, true));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 列のタイトル、幅、セルにセットするコンポーネント、表示/非表示を指定して列を追加する
	 * 
	 * @param title 列のタイトル
	 * @param width 列幅
	 * @param component 列にセットするコンポーネント
	 * @param visible 表示/非表示
	 */
	public void addColumn(String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 列を追加する
	 * 
	 * @param column 列
	 */
	public void addColumn(TTableColumn column) {
		if (columns == null) {
			columns = new ArrayList<TTableColumn>();
		}

		if (indexs == null) {
			indexs = new HashMap<Enum, Integer>();
		}

		indexs.put(column.e, columns.size());
		columns.add(column);
	}

	/**
	 * 行選択設定
	 * 
	 * @param row 行番号
	 */
	public void setRowSelection(int row) {
		super.setRowSelection(row, row);
	}

	/**
	 * 指定行のセルデータの取得
	 * 
	 * @param row 行番号
	 * @param e 列Enum
	 * @return セルデータ
	 */
	public Object getRowValueAt(int row, Enum e) {
		TableDataModel model = getDataView();
		return model.getTableDataItem(row, indexs.get(e));
	}

	/**
	 * 日付型のカラムインデックス指定 <br>
	 * YYYY/MM/DDの形式のString変換し、表示した場合。
	 * 
	 * @param columns
	 */
	public void setDateStringColumn(int... columns) {
		for (int col : columns) {
			dateSortIndexList.add(col);
		}
	}

	/**
	 * Number型のカラムインデックス指定<br>
	 * 数字をStringに変換し、表示したカラムの場合。
	 * 
	 * @param columns
	 */
	public void setNumberStringColumn(int... columns) {
		for (int col : columns) {
			numberSortIndexList.add(col);
		}
	}

}

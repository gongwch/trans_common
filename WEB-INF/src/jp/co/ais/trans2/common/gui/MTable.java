package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * マルチTable
 * 
 * @param <T>
 */
public class MTable<T extends TNumericField> extends TPanel implements TStorable {

	/** テーブル */
	public TTable tbl;

	/** テーブル本体 */
	public BaseTable table;

	/** フッター */
	public MFooter footer;

	/** GC:テーブル */
	protected GridBagConstraints gcTable;

	/** GC:フッター */
	protected GridBagConstraints gcFooter;

	/** フィールドClass */
	protected Class<T> fieldClass;

	/** 文字フィールドClass */
	protected Class<? extends TTextField> textClass;

	/** 検索日付フィールドClass */
	protected Class<? extends THalfwayDateField> searchDateClass;

	/** 日付フィールドClass */
	protected Class<? extends TCalendar> calClass;

	/**
	 * コンストラクタ
	 * 
	 * @param clazz
	 */
	public MTable(Class clazz) {
		this(clazz, null, null, null);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param fieldClass
	 * @param textClass
	 * @param searchDateClass
	 * @param calClass
	 */
	public MTable(Class fieldClass, Class textClass, Class searchDateClass, Class calClass) {
		this.fieldClass = fieldClass;
		this.textClass = textClass;
		this.searchDateClass = searchDateClass;
		this.calClass = calClass;

		init();
	}

	/**
	 * 初期処理
	 */
	public void init() {

		tbl = createBaseTable();
		table = tbl.table;
		footer = createFooter();

		TGuiUtil.setComponentHeight(footer, 20);

		setLayout(new GridBagLayout());

		gcTable = new GridBagConstraints();
		gcTable.anchor = GridBagConstraints.NORTHWEST;
		gcTable.fill = GridBagConstraints.BOTH;
		gcTable.gridx = 0;
		gcTable.gridy = 0;
		gcTable.weightx = 1.0d;
		gcTable.weighty = 1.0d;
		add(tbl, gcTable);

		gcFooter = new GridBagConstraints();
		gcFooter.anchor = GridBagConstraints.NORTHWEST;
		gcFooter.fill = GridBagConstraints.BOTH;
		gcFooter.gridx = 0;
		gcFooter.gridy = 1;
		gcFooter.weightx = 1.0d;
		add(footer, gcFooter);
	}

	/**
	 * ベーステーブル
	 * 
	 * @return ベーステーブル
	 */
	protected TTable createBaseTable() {
		return new TTable() {

			/**
			 * テーブル列変更
			 */
			@Override
			protected void fireTableColumnChanged() {
				footer.resizeComponents();

				// 列変更処理
				adapter.fireTableColumnChanged();
			}

		};
	}

	/**
	 * フッター列設定<br>
	 * 最初は1:LABELを設定する必要
	 * 
	 * @param cols 全て列
	 * @param types 0:BLANK:空欄、1:LABEL:ラベル表示用、2:SUM:合計列
	 */
	public void setFooterColumns(Enum[] cols, int[] types) {
		footer.setColumns(cols, types);
		footer.init();
	}

	/**
	 * @param col
	 * @return フッター合計フィールド
	 */
	public T getFooterCompAt(Enum col) {
		return getFooterCompAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return フッター合計フィールド
	 */
	public T getFooterCompAt(int index) {
		return (T) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return フッターテキストフィールド
	 */
	public TTextField getFooterTextAt(Enum col) {
		return getFooterTextAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return フッターテキストフィールド
	 */
	public TTextField getFooterTextAt(int index) {
		return (TTextField) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return フッター日付フィールド
	 */
	public TCalendar getFooterCalendarAt(Enum col) {
		return getFooterCalendarAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return フッター日付フィールド
	 */
	public TCalendar getFooterCalendarAt(int index) {
		return (TCalendar) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return フッター検索日付フィールド
	 */
	public THalfwayDateField getFooterSearchDateAt(Enum col) {
		return getFooterSearchDateAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return フッター検索日付フィールド
	 */
	public THalfwayDateField getFooterSearchDateAt(int index) {
		return (THalfwayDateField) footer.compList.get(index);
	}

	/**
	 * フッター初期化 <br>
	 * <br>
	 * <b>列設定(setFooterColumns)を先にする必要<b>
	 */
	public void initFooter() {
		footer.init();
	}

	/**
	 * フッター
	 * 
	 * @return フッター
	 */
	protected MFooter createFooter() {
		return new MFooter(tbl, fieldClass, textClass, searchDateClass, calClass);
	}

	/**
	 * @return JTableとのAdapter
	 */
	protected TTableAdapter createTTableAdapter() {
		return tbl.createTTableAdapter();
	}

	/**
	 * @return 行ヘッダー用Table
	 */
	protected JComponent createRowHeaderTable() {
		return tbl.createRowHeaderTable();
	}

	/**
	 * @return ソーター
	 */
	protected TableRowSorter<TableModel> createTableRowSorter() {
		return tbl.createTableRowSorter();
	}

	/**
	 * ソート機能を外す
	 */
	public void setSortOff() {
		tbl.setSortOff();
	}

	/**
	 * true:TABは外に移動しない、false:全画面移動の取得
	 * 
	 * @return tabTraverseCell true:TABは外に移動しない、false:全画面移動
	 */
	public boolean isTabTraverseCell() {
		return tbl.isTabTraverseCell();
	}

	/**
	 * true:TABは外に移動しない、false:全画面移動の設定
	 * 
	 * @param tabTraverseCell true:TABは外に移動しない、false:全画面移動
	 */
	public void setTabTraverseCell(boolean tabTraverseCell) {
		tbl.setTabTraverseCell(tabTraverseCell);
	}

	/**
	 * TABキーで次のセルに移動するように設定する.
	 */
	public void setTabTraverseCell() {
		setTabTraverseCell(true);
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
			addColumn(
				new TTableColumn(e, title, width, instance.getDefaultCellRendererHorizontalAlignment(), instance));
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

			addColumn(
				new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(), component));
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
		tbl.addColumn(column);

		// TODO:フッター
		// footer.addColumn(column);
	}

	/**
	 * ヘッダー表現の作成
	 * 
	 * @param colHeader
	 * @return ヘッダー表現
	 */
	protected TableCellRenderer createTableCellRenderer(TTableColumnHeader colHeader) {
		return tbl.createTableCellRenderer(colHeader);
	}

	/**
	 * 列タイトルの取得
	 * 
	 * @param column
	 * @param lang
	 * @return 列タイトル
	 */
	protected String getColumnTitle(TTableColumn column, String lang) {
		return tbl.getColumnTitle(column, lang);
	}

	/**
	 * @param hr
	 * @return テーブルヘッダーのセルレンダラ
	 */
	protected HeaderRenderer createHeaderRenderer(TableCellRenderer hr) {
		return tbl.createHeaderRenderer(hr);
	}

	/**
	 * @param r
	 * @return セルレンダラ
	 */
	protected CellRenderer createCellRenderer(TableCellRenderer r) {
		return tbl.createCellRenderer(r);
	}

	/**
	 * Enumにある列のTableColumnオブジェクトを 返します。
	 * 
	 * @param e 要求する列
	 * @return TableColumn
	 */
	public TableColumn getColumn(Enum e) {
		return tbl.getColumn(e);
	}

	/**
	 * テーブルを復元する
	 */
	public void restoreTable() {
		tbl.restoreTable();
	}

	/**
	 * 行追加 Listを追加
	 * 
	 * @param list データリスト
	 */
	public void addRow(List list) {
		tbl.addRow(list);
	}

	/**
	 * 行追加 Object型の配列を追加
	 * 
	 * @param data データ
	 */
	public void addRow(Object[] data) {
		tbl.addRow(data);
	}

	/**
	 * 選択されている行を修正する
	 * 
	 * @param list データリスト
	 */
	public void modifySelectedRow(List list) {
		tbl.modifySelectedRow(list);
	}

	/**
	 * 選択されている行を修正する
	 * 
	 * @param data 修正データ
	 */
	public void modifySelectedRow(Object[] data) {
		tbl.modifySelectedRow(data);
	}

	/**
	 * 指定行を修正する
	 * 
	 * @param row 行
	 * @param list データリスト
	 */
	public void modifyRow(int row, List list) {
		tbl.modifyRow(row, list);
	}

	/**
	 * 指定行を修正する
	 * 
	 * @param row 行
	 * @param data 修正データ
	 */
	public void modifyRow(int row, Object[] data) {
		tbl.modifyRow(row, data);
	}

	/**
	 * 指定の行を削除する
	 * 
	 * @param row 行削除
	 */
	public void removeRow(int row) {
		tbl.removeRow(row);
	}

	/**
	 * 選択されている行を削除する
	 */
	public void removeSelectedRow() {
		tbl.removeSelectedRow();
	}

	/**
	 * 選択されている行を削除する(複数行)
	 */
	public void removeSelectedRows() {
		tbl.removeSelectedRows();
	}

	/**
	 * 選択されている行を削除する(複数行)
	 * 
	 * @param rows 選択中の行番号
	 */
	public void removeRows(int[] rows) {
		tbl.removeRows(rows);
	}

	/**
	 * 全ての行を削除する
	 */
	public void removeRow() {
		tbl.removeRow();
	}

	/**
	 * テーブル情報を返す。
	 * 
	 * @return テーブル情報
	 */
	public TTableInformation getTableInformation() {
		return tbl.getTableInformation();
	}

	/**
	 * 前回保存したテーブル情報を返す。
	 * 
	 * @return 前回保存したテーブル情報
	 */
	public TTableInformation getPreviousTableInformation() {
		return tbl.getPreviousTableInformation();
	}

	/**
	 * テーブル情報を保存する
	 */
	public void saveTableInformation() {
		tbl.saveTableInformation();
	}

	/**
	 * 指定のテーブル情報を保存する
	 * 
	 * @param tblInformation テーブル情報
	 */
	protected void saveTableInformation(TTableInformation tblInformation) {
		tbl.saveTableInformation(tblInformation);
	}

	/**
	 * テーブル情報をクリア
	 */
	protected void clearTableInformation() {
		tbl.clearTableInformation();
	}

	/**
	 * テーブル幅自動調整
	 * 
	 * @param type 幅自動調整のタイプ
	 */
	protected void adjustTableInformation(AutoSizeType type) {
		tbl.adjustTableInformation(type);
	}

	/**
	 * @return tblKeyNameを戻します。
	 */
	public String getTableKeyName() {
		return tbl.getTableKeyName();
	}

	/**
	 * @param tblKeyName tblKeyNameを設定します。
	 */
	public void setTableKeyName(String tblKeyName) {
		tbl.setTableKeyName(tblKeyName);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getDecimalValueAt(int row, Enum column) {
		return tbl.getDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getDecimalValueAt(int row, int column) {
		return tbl.getDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getValueAt(int row, Enum column) {
		return tbl.getValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getValueAt(int row, int column) {
		return tbl.getValueAt(row, column);
	}

	/**
	 * 選択されている行の指定カラムの値を返します。
	 * 
	 * @param column 値を取りたい列識別enum
	 * @return 選択されている行の指定カラムの値
	 */
	public Object getSelectedRowValueAt(Enum column) {
		return tbl.getSelectedRowValueAt(column);
	}

	/**
	 * 指定行の指定カラムの値を返します。
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public Object getRowValueAt(int row, Enum column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値を返します。
	 * 
	 * @param row 行番号
	 * @param column 列番号
	 * @return 指定行の指定カラムの値
	 */
	public Object getRowValueAt(int row, int column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する<BR>
	 * ※ソート、列入れ替え対応
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getRowDecimalValueAt(int row, Enum column) {
		return tbl.getRowDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * ソートがされていた場合、ソート後の行番号で取得する<BR>
	 * ※ソート、列入れ替え対応
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	public BigDecimal getRowDecimalValueAt(int row, int column) {
		return tbl.getRowDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, Enum column) {
		return tbl.getModelDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, int column) {
		return tbl.getModelDecimalValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, Enum column) {
		return tbl.getModelComboBoxValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値をBigDecimalで返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, int column) {
		return tbl.getModelComboBoxValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelValueAt(int row, Enum column) {
		return tbl.getModelValueAt(row, column);
	}

	/**
	 * 指定行の指定カラムの値を返します。<BR>
	 * TableModel基準<br>
	 * <b>ソート後に正しく取得できない不具合あり</b>
	 * 
	 * @param row 行番号
	 * @param column 値を取りたい列識別enum
	 * @return 指定行の指定カラムの値
	 */
	@Deprecated
	public Object getModelValueAt(int row, int column) {
		return tbl.getModelValueAt(row, column);
	}

	/**
	 * スプレッドシートのダブルクリックイベントとボタンイベントの紐付け
	 * 
	 * @param btn
	 */
	public void addSpreadSheetSelectChange(TButton btn) {
		tbl.addSpreadSheetSelectChange(btn);
	}

	/**
	 * テーブルの行を移動する。(複数行移動)<br>
	 * startからend までの1行または複数行を、toの位置に移動
	 * 
	 * @param start startからend までの1行または複数行を入れ替え
	 * @param end startからend までの1行または複数行を入れ替え
	 * @param to 移動先の列番号
	 */
	public void moveRow(int start, int end, int to) {
		tbl.moveRow(start, end, to);
	}

	/**
	 * テーブルの行を移動する。
	 * 
	 * @param row 移動対象行
	 * @param to 移動先の列番号
	 */
	public void moveRow(int row, int to) {
		tbl.moveRow(row, to);
	}

	/**
	 * ヘッダーの高さを設定する
	 * 
	 * @param height
	 */
	public void setHeaderRowHeight(int height) {
		tbl.setHeaderRowHeight(height);
	}

	/**
	 * 対象セルを選択する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void setRowSelectionInterval(int index1, int index2) {
		tbl.setRowSelectionInterval(index1, index2);
	}

	/**
	 * 対象セルを追加選択する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void addRowSelectionInterval(int index1, int index2) {
		tbl.addRowSelectionInterval(index1, index2);
	}

	/**
	 * 対象セルの選択を解除する
	 * 
	 * @param index1
	 * @param index2
	 */
	public void removeRowSelectionInterval(int index1, int index2) {
		tbl.removeRowSelectionInterval(index1, index2);
	}

	/**
	 * ALL選択
	 */
	public void selectAll() {
		tbl.selectAll();
	}

	/**
	 * 対象行を選択する
	 * 
	 * @param rowIndex 行番号
	 */
	public void setRowSelection(int rowIndex) {
		tbl.setRowSelection(rowIndex);
	}

	/**
	 * 対象行を追加選択する
	 * 
	 * @param rowIndex 行番号
	 */
	public void addRowSelection(int rowIndex) {
		tbl.addRowSelection(rowIndex);
	}

	/**
	 * 対象行の選択を解除する
	 * 
	 * @param rowIndex 行番号
	 */
	public void removeRowSelection(int rowIndex) {
		tbl.removeRowSelection(rowIndex);
	}

	/**
	 * 表示されている行数を取得する
	 * 
	 * @return 行数
	 */
	public int getRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * 行数を返す(非表示行も含む)
	 * 
	 * @return 行数
	 */
	public int getModelRowCount() {
		return tbl.getModelRowCount();
	}

	/**
	 * 行の高さ
	 * 
	 * @param height 高さ
	 */
	public void setRowHeight(int height) {
		tbl.setRowHeight(height);
	}

	/**
	 * 行カラムの幅
	 * 
	 * @param width 幅
	 */
	public void setRowColumnWidth(int width) {
		tbl.setRowColumnWidth(width);

		// gcFooter.insets.left = width;
		// add(footer, gcFooter);
		// TODO

		repaint();
	}

	/**
	 * 行カラムの幅getter
	 * 
	 * @return 行カラムの幅
	 */
	public int getRowColumnWidth() {
		return tbl.getRowColumnWidth();
	}

	/**
	 * 指定されるEnumの列幅の設定
	 * 
	 * @param e 指定されるEnum
	 * @param width 幅
	 */
	public void setColumnWidth(Enum e, int width) {
		tbl.setColumnWidth(e, width);
	}

	/**
	 * 指定されるEnumの列幅の取得
	 * 
	 * @param e 指定されるEnum
	 * @return PreferredWidth 幅
	 */
	public int getColumnWidth(Enum e) {
		return tbl.getColumnWidth(e);
	}

	/**
	 * @param model
	 */
	public void setSelectionMode(int model) {
		tbl.setSelectionMode(model);
	}

	/**
	 * 選択行を取得する
	 * 
	 * @return 選択行
	 */
	public int getSelectedRow() {
		return tbl.getSelectedRow();
	}

	/**
	 * 選択行を取得する
	 * 
	 * @return 選択行
	 */
	public int[] getSelectedRows() {
		return tbl.getSelectedRows();
	}

	/**
	 * TableHeaderを取得する
	 * 
	 * @return ヘッダー
	 */
	public JTableHeader getTableHeader() {
		return tbl.getTableHeader();
	}

	/**
	 * rowのlabelに項番を振るか選択 ※デフォルト設定は項番あり
	 * 
	 * @param isView 項番あり：true /項番なし：false
	 */
	public void setRowLabelNumber(boolean isView) {
		tbl.setRowLabelNumber(isView);
	}

	/**
	 * ColumnModelを取得する
	 * 
	 * @return ColumnModel
	 */
	public TableColumnModel getColumnModel() {
		return tbl.getColumnModel();
	}

	/**
	 * CellEditorを取得する
	 * 
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor() {
		return tbl.getCellEditor();
	}

	/**
	 * フォーカス順
	 * 
	 * @return フォーカス順
	 */
	public int getTabControlNo() {
		return tbl.getTabControlNo();
	}

	/**
	 * @param no
	 */
	public void setTabControlNo(int no) {
		tbl.setTabControlNo(no);
	}

	/**
	 * フォーカス判定
	 * 
	 * @return true:フォーカス可
	 */
	public boolean isTabEnabled() {
		return tbl.isTabEnabled();
	}

	/**
	 * @param bool
	 */
	public void setTabEnabled(boolean bool) {
		tbl.setTabEnabled(bool);
	}

	@Override
	public void requestFocus() {
		tbl.requestFocus();
	}

	@Override
	public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
		tbl.setFocusTraversalPolicy(policy);
	}

	@Override
	public void transferFocus() {
		tbl.transferFocus();
	}

	@Override
	public void transferFocusBackward() {
		tbl.transferFocusBackward();
	}

	@Override
	public boolean requestFocusInWindow() {
		return tbl.requestFocusInWindow();
	}

	/**
	 * 列入れ替え可能かどうか
	 * 
	 * @param bln true:可能、false:なし
	 */
	public void setReorderingAllowed(boolean bln) {
		tbl.setReorderingAllowed(bln);
	}

	/**
	 * 行選択できるかどうか
	 * 
	 * @param bln true:あり、false:なし
	 */
	public void setRowSelectionAllowed(boolean bln) {
		tbl.setRowSelectionAllowed(bln);
	}

	/**
	 * 背景色を取得する
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return 背景色
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {
		return tbl.getBackgroundColor(row, isSelected, hasFocus);
	}

	/**
	 * フォントカラーを取得する
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return フォントカラー
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {
		return tbl.getCellFontColor(isSelected, hasFocus);
	}

	/**
	 * 行非選択1の背景色を取得する
	 * 
	 * @return 行非選択1の背景色
	 */
	public Color getNotSelectedColor() {
		return tbl.getNotSelectedColor();
	}

	/**
	 * 行非選択2の背景色を取得する
	 * 
	 * @return 行非選択2の背景色
	 */
	public Color getNotSelectedColor2() {
		return tbl.getNotSelectedColor2();
	}

	/**
	 * 行選択時の背景色を取得する
	 * 
	 * @return 行選択時の背景色
	 */
	public Color getSelectedColor() {
		return tbl.getSelectedColor();
	}

	/**
	 * 行選択時のフォントカラーを取得する
	 * 
	 * @return 行選択時の背景色
	 */
	public Color getSelectedFontColor() {
		return tbl.getSelectedFontColor();
	}

	/**
	 * ListSelectionModelを取得する
	 * 
	 * @return ListSelectionModel
	 */
	public ListSelectionModel getSelectionModel() {
		return tbl.getSelectionModel();
	}

	/**
	 * 未選択状態にする
	 */
	public void clearSelection() {
		tbl.clearSelection();
	}

	/**
	 * ListSelectionListenerを設定
	 * 
	 * @param listener ListSelectionListener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		tbl.addListSelectionListener(listener);
	}

	/**
	 * rowHeightを取得する。
	 * 
	 * @return int rowHeight
	 */
	public int getRowHeight() {
		return tbl.getRowHeight();
	}

	/**
	 * Enterキーでボタン実行を行うかどうか
	 * 
	 * @param isEnterToButton true:ボタン実行を行う(連動させる)
	 */
	public void setEnterToButton(final boolean isEnterToButton) {
		tbl.setEnterToButton(isEnterToButton);
	}

	/**
	 * フォーカスを受け入れるかどうか
	 * 
	 * @param isFocusable
	 */
	@Override
	public void setFocusable(boolean isFocusable) {
		tbl.setFocusable(isFocusable);
	}

	/**
	 * セル選択を許可するかどうか
	 * 
	 * @param cellSelectionEnabled
	 */
	public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
		tbl.setCellSelectionEnabled(cellSelectionEnabled);
	}

	/**
	 * テーブルのCELLが編集中かどうか
	 * 
	 * @return isEditing
	 */
	public boolean isEditing() {
		return tbl.isEditing();
	}

	/**
	 * テーブルの編集を確定する
	 */
	public void stopCellEditing() {
		tbl.stopCellEditing();
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param strText データ
	 * @param rowIndex 行
	 * @param col カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(String strText, int rowIndex, int col) {
		tbl.setValueAt(strText, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param column カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(Object obj, int rowIndex, Enum column) {
		tbl.setValueAt(obj, rowIndex, column);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param col カラム
	 * @deprecated 新規での使用禁止<br>
	 *             setRowValueAtを利用すること
	 */
	public void setValueAt(Object obj, int rowIndex, int col) {
		tbl.setValueAt(obj, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param strText データ
	 * @param rowIndex 行
	 * @param col カラム
	 */
	public void setModelValueAt(String strText, int rowIndex, int col) {
		tbl.setModelValueAt(strText, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param column カラム
	 */
	public void setModelValueAt(Object obj, int rowIndex, Enum column) {
		tbl.setModelValueAt(obj, rowIndex, column);
	}

	/**
	 * 指定行/カラムデータを設定する
	 * 
	 * @param obj データ
	 * @param rowIndex 行
	 * @param col カラム
	 */
	public void setModelValueAt(Object obj, int rowIndex, int col) {
		tbl.setModelValueAt(obj, rowIndex, col);
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param strText データ
	 * @param row 行
	 * @param col カラム
	 */
	public void setRowValueAt(String strText, int row, int col) {
		tbl.setRowValueAt(strText, row, col);
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param obj データ
	 * @param row 行
	 * @param column カラム
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		tbl.setRowValueAt(obj, row, column);
	}

	/**
	 * 指定行/カラムデータを設定する ※行ソート、列入れ替え対応
	 * 
	 * @param obj データ
	 * @param row 行
	 * @param col カラム
	 */
	public void setRowValueAt(Object obj, int row, int col) {
		tbl.setRowValueAt(obj, row, col);
	}

	/**
	 * 編集行/カラムを設定する
	 * 
	 * @param row 行
	 * @param col カラム
	 */
	public void editCellAt(int row, int col) {
		tbl.editCellAt(row, col);
	}

	/**
	 * 指定の位置に行を追加する
	 * 
	 * @param row 行
	 * @param data データ
	 */
	public void insertRow(int row, Object[] data) {
		tbl.insertRow(row, data); // 追加
	}

	/**
	 * 指定の位置に行を追加する
	 * 
	 * @param row 行
	 * @param list データリスト
	 */
	public void insertRow(int row, List list) {
		tbl.insertRow(row, list);
	}

	/**
	 * CellEditorを取得する
	 * 
	 * @param row 行
	 * @param col カラム
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		return tbl.getCellEditor(row, col);
	}

	/**
	 * CellRendererを取得する
	 * 
	 * @param col カラム
	 * @return CellRenderer
	 */
	public TableCellRenderer getComponentRenderer(int col) {
		return tbl.getComponentRenderer(col);
	}

	/**
	 * テーブルモデルを返す
	 * 
	 * @return テーブルモデル
	 */
	public TableModel getModel() {
		return tbl.getModel();
	}

	/**
	 * 指定カラムがチェックボックスの場合、チェックされている行数を返す
	 * 
	 * @param e
	 * @return チェック行数
	 */
	public int getCheckedRowCount(Enum e) {
		return tbl.getCheckedRowCount(e);
	}

	/**
	 * 指定カラムがチェックボックスの場合、チェックされている最初の行のインデックスを返す
	 * 
	 * @param e
	 * @return 指定カラムがチェックボックスの場合、チェックされている最初の行のインデックス
	 */
	public int getFirstCheckedRowIndex(Enum e) {
		return tbl.getFirstCheckedRowIndex(e);
	}

	/**
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	@Override
	public void addFocusListener(FocusListener listener) {
		tbl.addFocusListener(listener);
	}

	/**
	 * TTableColumnを返す
	 * 
	 * @return TTableColumn
	 */
	public List<TTableColumn> getTableColumn() {
		return tbl.getTableColumn();
	}

	/**
	 * 指定インデックスのTTableColumnを返す
	 * 
	 * @param index インデックス
	 * @return TTableColumn
	 */
	public TTableColumn getTableColumnAt(int index) {
		return tbl.getTableColumnAt(index);
	}

	/**
	 * ヘッダーをクリックした際に、チェックボックスのカラムならば<br>
	 * 全チェック機能を有効にするか
	 * 
	 * @return true:有効
	 */
	public boolean isAllCheckWhenHeaderClicked() {
		return tbl.isAllCheckWhenHeaderClicked();
	}

	/**
	 * ヘッダーをクリックした際に、チェックボックスのカラムならば<br>
	 * 全チェック機能を有効にするか
	 * 
	 * @param allCheckWhenHeaderClicked true:有効
	 */
	public void setAllCheckWhenHeaderClicked(boolean allCheckWhenHeaderClicked) {
		tbl.setAllCheckWhenHeaderClicked(allCheckWhenHeaderClicked);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#saveComponent(jp.co.ais.trans2.common.gui.TStorableKey,
	 *      java.io.Serializable)
	 */
	public void saveComponent(TStorableKey key, Serializable obj) {
		tbl.saveComponent(key, obj);
	}

	/**
	 * カラム状態の復元
	 */
	public void restoreComponent() {
		tbl.restoreComponent();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#restoreComponent(jp.co.ais.trans2.common.gui.TStorableKey)
	 */
	public void restoreComponent(TStorableKey key) {
		tbl.restoreComponent(key);
	}

	/**
	 * カラム位置復元
	 * 
	 * @param tblInformation テーブル情報
	 */
	protected void restoreColumns(TTableInformation tblInformation) {
		tbl.restoreColumns(tblInformation);
	}

	/**
	 * 列幅復元
	 * 
	 * @param tblInformation テーブル情報
	 */
	public void restoreWidth(TTableInformation tblInformation) {
		tbl.restoreWidth(tblInformation);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#getKey()
	 */
	public TStorableKey getKey() {
		return tbl.getKey();
	}

	/**
	 * Sorter取得
	 * 
	 * @return Sorter
	 */
	public TableRowSorter<? extends TableModel> getSorter() {
		return tbl.getSorter();
	}

	/**
	 * Sorter設定
	 * 
	 * @param sorter Sorter
	 */
	public void setSorter(TableRowSorter<? extends TableModel> sorter) {
		tbl.setSorter(sorter);
	}

	/**
	 * ソート可能か
	 * 
	 * @return sortbl true:可能
	 */
	public boolean isSortable() {
		return tbl.isSortable();
	}

	/**
	 * ソート可能か
	 * 
	 * @param sortbl true:可能
	 */
	public void setSortable(boolean sortbl) {
		tbl.setSortable(sortbl);
	}

	/**
	 * 数値比較使うかどうか
	 * 
	 * @return useNumberComparator 数値比較使うかどうか
	 */
	public boolean isUseNumberComparator() {
		return tbl.isUseNumberComparator();
	}

	/**
	 * 数値比較使うかどうかの設定
	 * 
	 * @param flag 数値比較使うかどうか true:使う<b>右寄せの列は数値と暫定</b>
	 */
	public void setUseNumberComparator(boolean flag) {
		tbl.setUseNumberComparator(flag);
	}

	/**
	 * アダプター取得
	 * 
	 * @return adapter アダプター
	 */
	public TTableAdapter getAdapter() {
		return tbl.getAdapter();
	}

	/**
	 * アダプター設定
	 * 
	 * @param adapter アダプター
	 */
	public void setAdapter(TTableAdapter adapter) {
		tbl.setAdapter(adapter);
	}

	/**
	 * 指定カラムの小数点以下桁数を設定 ※列入れ替えをした場合不具合あり。原則使用禁止とする。
	 * 
	 * @param column カラム
	 * @param digits 小数点以下桁数
	 */
	public void setDecimalPointAt(Enum column, int digits) {
		tbl.setDecimalPointAt(column, digits);
	}

	/**
	 * 指定カラムの小数点以下桁数を設定
	 * 
	 * @param column カラム
	 * @param digits 小数点以下桁数
	 */
	public void setDecimalPoint(Enum column, int digits) {
		tbl.setDecimalPoint(column, digits);
	}

	/**
	 * 行選択されているかどうか
	 * 
	 * @return true:選択
	 */
	public boolean isSelectedRow() {
		return tbl.isSelectedRow();
	}

	/**
	 * スクロールバーを最上行に移動する。
	 */
	public void setScrollTop() {
		tbl.setScrollTop();
	}

	/**
	 * スクロールバーを最下行に移動する。
	 */
	public void setScrollUnder() {
		tbl.setScrollUnder();
	}

	/**
	 * スクロールバーを左に移動する。
	 */
	public void setScrollLeft() {
		tbl.setScrollLeft();
	}

	/**
	 * スクロールバーを初期位置に移動する。
	 */
	public void setScrollDefault() {
		tbl.setScrollDefault();
	}

	/**
	 * チェックボックスの表示変更を追加.
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enable) {
		tbl.setEnabled(enable);

		// フッター
	}

	/**
	 * コンポーネントブランク表示用のアダプター(※チェックボックス限定)
	 * 
	 * @param e 対象列
	 * @param adapter アダプター
	 */
	public void setComponentViewAdapter(Enum e, TComponentViewAdapter adapter) {
		tbl.setComponentViewAdapter(e, adapter);
	}

	/**
	 * 全ての列を隠し
	 */
	public void hideAllColumn() {
		tbl.hideAllColumn();
	}

	/**
	 * 全ての列を表示
	 */
	public void showAllColumn() {
		tbl.showAllColumn();
	}

	/**
	 * 初期状態の取得
	 * 
	 * @return 値
	 */
	public TTableInformation getInitTableInformation() {
		return tbl.getInitTableInformation();
	}

	/**
	 * 列幅自動計算値の取得
	 * 
	 * @param column 列
	 * @param colWidth 設定列幅
	 * @return 列幅
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth) {
		return tbl.getAdjustColumnWidth(column, colWidth);
	}

	/**
	 * 列幅自動計算値の取得
	 * 
	 * @param column 列
	 * @param colWidth 設定列幅
	 * @param type 幅自動調整のタイプ
	 * @param colIndex 指定列インデックス
	 * @return 列幅
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex) {
		return tbl.getAdjustColumnWidth(column, colWidth, type, colIndex);
	}

	/**
	 * 指定列の最大文字列の取得
	 * 
	 * @param column 指定列オブジェクト
	 * @param col 指定列インデックス
	 * @param type 幅自動調整のタイプ
	 * @return 指定列の最大文字列
	 */
	public int getMaxWidth(TableColumn column, int col, AutoSizeType type) {
		return tbl.getMaxWidth(column, col, type);
	}

	/**
	 * HTML含み文字の幅分析
	 * 
	 * @param cellValue
	 * @return HTML含み文字の幅
	 */
	protected int getWidthWithHtml(String cellValue) {
		return tbl.getWidthWithHtml(cellValue);
	}

	/**
	 * 列幅自動計算<br>
	 * 幅がゼロの場合、自動計算しない
	 */
	public void autosizeColumnWidth() {
		tbl.autosizeColumnWidth();
	}

	/**
	 * 列幅自動計算<br>
	 * 幅がゼロの場合、自動計算しない
	 * 
	 * @param type 幅自動調整のタイプ
	 */
	public void autosizeColumnWidth(AutoSizeType type) {
		tbl.autosizeColumnWidth(type);
	}

	/**
	 * 列幅自動計算<br>
	 * 
	 * @param isZeroAdjust <br>
	 *            true:幅がゼロの場合、自動計算する false:幅がゼロの場合、自動計算しない
	 * @param type 幅自動調整のタイプ
	 */
	public void autosizeColumnWidth(boolean isZeroAdjust, AutoSizeType type) {
		tbl.autosizeColumnWidth(isZeroAdjust, type);
	}

	/**
	 * TTableのV.ScrollBar
	 * 
	 * @return V.ScrollBar
	 */
	public JScrollBar createVerticalScrollBar() {
		return tbl.createVerticalScrollBar();
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @return true:選択行の行番号は「*」で表示
	 */
	public boolean isShowRowHeaderStar() {
		return tbl.isShowRowHeaderStar();
	}

	/**
	 * 行ヘッダーの「*」表示行番号の取得
	 * 
	 * @return 行ヘッダーの「*」表示行番号
	 */
	public int getRowHeaderStarIndex() {
		return tbl.getRowHeaderStarIndex();
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @param flag true:選択行の行番号は「*」で表示
	 */
	public void setShowRowHeaderStar(boolean flag) {
		tbl.setShowRowHeaderStar(flag);
	}

	/**
	 * 行ヘッダーは「*」で表示するかどうか
	 * 
	 * @param row 指定行の行番号は「*」で表示
	 */
	public void setShowRowHeaderStar(int row) {
		tbl.setShowRowHeaderStar(row);
	}

	/**
	 * 行タイトルリストの取得
	 * 
	 * @return rowHeaderMessage 行タイトルリスト
	 */
	public List<String> getRowHeaderMessage() {
		return tbl.getRowHeaderMessage();
	}

	/**
	 * 行タイトルリストの設定
	 * 
	 * @param rowHeaderMessage 行タイトルリスト
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		tbl.setRowHeaderMessage(rowHeaderMessage);
	}

	/**
	 * 指定行列のセルのフォーカスを当てる
	 * 
	 * @param row 行
	 * @param column 列
	 */
	public void requestFocus(int row, int column) {
		tbl.requestFocus(row, column);
	}

	/**
	 * 指定行列のセルのフォーカスを当てる
	 * 
	 * @param row 行
	 * @param e 列Enum
	 */
	public void requestFocus(int row, Enum e) {
		tbl.requestFocus(row, e);
	}

	/**
	 * 列タイトルの取得
	 * 
	 * @param e Enum
	 * @return 列タイトル
	 */
	public String getColumnTitle(Enum e) {
		return tbl.getColumnTitle(e);
	}

	/**
	 * 列追加時の設定の取得
	 * 
	 * @return columnSetting 列追加時の設定
	 */
	public ColumnSetting getColumnSetting() {
		return tbl.getColumnSetting();
	}

	/**
	 * 列追加時の設定の設定
	 * 
	 * @param columnSetting 列追加時の設定
	 */
	public void setColumnSetting(ColumnSetting columnSetting) {
		tbl.setColumnSetting(columnSetting);
	}

	/**
	 * SPACE押下対象チェックボックス列の追加
	 * 
	 * @param col SPACE押下対象チェックボックス列
	 */
	public void addCheckBoxColumn(int col) {
		tbl.addCheckBoxColumn(col);
	}

	/**
	 * SPACE押下対象チェックボックス列リストの取得
	 * 
	 * @return checkBoxColumns SPACE押下対象チェックボックス列リスト
	 */
	public List<Integer> getCheckBoxColumns() {
		return tbl.getCheckBoxColumns();
	}

	/**
	 * SPACE押下対象チェックボックス列リストの設定
	 * 
	 * @param checkBoxColumns SPACE押下対象チェックボックス列リスト
	 */
	public void setCheckBoxColumns(List<Integer> checkBoxColumns) {
		tbl.setCheckBoxColumns(checkBoxColumns);
	}

	/**
	 * SPACE押下対象チェックボックス列リストのクリア
	 */
	public void clearCheckBoxColumns() {
		tbl.clearCheckBoxColumns();
	}

	/**
	 * 貼り付け機能使うか。true:使うの取得
	 * 
	 * @return useTablePaste 貼り付け機能使うか。true:使う
	 */
	public boolean isUseTablePaste() {
		return tbl.isUseTablePaste();
	}

	/**
	 * 貼り付け機能使うか。true:使うの設定
	 * 
	 * @param useTablePaste 貼り付け機能使うか。true:使う
	 */
	public void setUseTablePaste(boolean useTablePaste) {
		tbl.setUseTablePaste(useTablePaste);
	}
}

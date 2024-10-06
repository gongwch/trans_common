package jp.co.ais.trans2.common.gui;

import javax.swing.event.*;
import javax.swing.table.*;

/**
 * 行番号表示用データモデル
 */
public class RowHeaderDataModel extends DefaultTableModel implements TableModelListener {

	/** 元テーブル */
	protected TTable tbl;

	/**
	 * コンストラクタ.
	 * 
	 * @param tbl 元テーブル
	 */
	public RowHeaderDataModel(TTable tbl) {
		this.tbl = tbl; // データ用テーブル

		TableModel dataModel = getTable().getModel();
		dataModel.removeTableModelListener(this);
		dataModel.addTableModelListener(this);
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (getTable().getRowHeaderStarIndex() != -1 && getTable().getRowHeaderStarIndex() == row) {
			return "*";
		}
		if (getTable().getRowHeaderMessage() != null && getTable().getRowHeaderMessage().size() > row) {
			return getTable().getRowHeaderMessage().get(row);
		}
		return row + tbl.rowNumPlus;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return Integer.class;
	}

	@Override
	public void setValueAt(Object aValue, int row, int column) {
		// 値の登録は行わない
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false; // 編集不可
	}

	@Override
	public int getRowCount() {
		if (getTable() != null) {
			return getTable().getRowCount(); // データテーブルの表示行数を返す
		}
		return 0;
	}

	/**
	 * TableModelListenerのメソッドであり、データ用TableModelに変更があったときに呼ばれる。
	 */
	public void tableChanged(TableModelEvent e) {
		switch (e.getType()) {
			case TableModelEvent.INSERT: // 行追加
			case TableModelEvent.DELETE: // 行削除
				super.fireTableChanged(e);
				break;

			default:
				break;
		}
	}

	/**
	 * @return BaseTable
	 */
	protected BaseTable getTable() {
		if (tbl == null) {
			return null;
		}
		return tbl.table;
	}
}
package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * チェックボックス用セルエディタ
 */
public class TCheckBoxEditor extends TBaseCellEditor {

	/** ブランク表示Adapter */
	protected TComponentViewAdapter viewAdapter;

	/**
	 * コンストラクタ
	 * 
	 * @param table
	 */
	public TCheckBoxEditor(TTable table) {
		this(new TCheckBox(), table);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param checkBox
	 * @param table
	 */
	public TCheckBoxEditor(TCheckBox checkBox, TTable table) {

		super(checkBox, table);

		// チェックボックスのレイアウトを指定
		checkBox.setHorizontalAlignment(SwingConstants.CENTER);
		checkBox.setOpaque(true);

	}

	/**
	 * ブランク表示アダプター
	 * 
	 * @param adapter アダプター
	 */
	public void setViewAdapter(TComponentViewAdapter adapter) {
		this.viewAdapter = adapter;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		if (viewAdapter != null && viewAdapter.isBlank(row, column)) {
			return null;
		}

		// コンポーネントの取得
		TCheckBox checkBox = (TCheckBox) getComponent();

		// 背景色の設定
		checkBox.setBackground(tbl.getBackgroundColor(row, isSelected, isSelected));
		checkBox.setForeground(tbl.getSelectedFontColor());

		// 対象行番号の設定
		checkBox.setRowIndex(row);
		checkBox.setModelIndex(table.convertRowIndexToModel(row));

		return super.getTableCellEditorComponent(table, value, isSelected, row, column);

	}

	/**
	 * 対象セルが編集可能かどうか
	 * 
	 * @param row
	 * @param column
	 * @return true 編集可
	 */
	@Override
	public boolean isCellEditable(int row, int column) {
		if (viewAdapter == null) {
			return super.isCellEditable(row, column);
		}

		return !viewAdapter.isBlank(row, column) && viewAdapter.isEnable(row, column);
	}
}
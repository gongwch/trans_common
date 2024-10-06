package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 日付編集用セルエディタ
 */
public class TDateEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ
	 * 
	 * @param table
	 */
	public TDateEditor(TTable table) {
		this(TCalendar.TYPE_YMD, table);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param calType フォーマット
	 * @param table
	 */
	public TDateEditor(int calType, TTable table) {

		this(new TCalendar(calType), table);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TDateEditor(TCalendar editor, TTable table) {
		super(editor, table);

		// エディタ用コンポーネントの設定
		editor.setBorder(BorderFactory.createEmptyBorder());
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TCalendar editor = (TCalendar) getComponent();

		// 対象行番号の設定
		editor.setRowIndex(row);

		// 値の設定
		if (value instanceof Date) {
			editor.setValue((Date) value);

		} else {
			editor.setValue(value);
		}

		if (editor.isUseTablePaste()) {
			TTablePasteUtil.handleKeyEvent(editor, tbl, row, column, editor.getCellTypes());
		}

		return editor;
	}

}
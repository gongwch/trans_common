package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 数値編集用セルエディタ
 */
public class TNumberCellValueEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TNumberCellValueEditor(final TNumericField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		TNumericField txt = (TNumericField) editorComponent;

		NumberCellValue cellValue = new NumberCellValue();
		cellValue.setNumber(txt.getBigDecimal());
		cellValue.setMaxLength(txt.getMaxLength());
		cellValue.setDecimalPoint(txt.getDecimalPoint());

		return cellValue;
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TNumericField editor = (TNumericField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());

		// 対象行番号の設定
		editor.setRowIndex(row);

		if (value != null) {
			// 値の設定
			editor.setMaxLength(((NumberCellValue) value).getMaxLength());
			editor.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
			editor.setNumber(((NumberCellValue) value).getNumber());
		}

		return editor;
	}
}
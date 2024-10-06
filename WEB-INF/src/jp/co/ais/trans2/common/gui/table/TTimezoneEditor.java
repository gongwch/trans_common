package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TZ編集用セルエディタ
 */
public class TTimezoneEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TTimezoneEditor(TTimezoneField editor, TTable table) {
		super(editor, table);
	}

	/**
	 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TTimezoneField editor = (TTimezoneField) getComponent();
		editor.setBorder(BorderFactory.createEmptyBorder());

		// if (editor.isUseTableFont()) {
		// editor.setFont(table.getFont());
		// }

		// 対象行番号の設定
		editor.setRowIndex(row);

		// 値の設定
		setValue(editor, value);

		if (editor.isUseTablePaste()) {
			TTablePasteUtil.handleKeyEvent(editor, tbl, row, column, editor.getCellTypes());
		}

		return editor;
	}

	/**
	 * 値の設定
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TTimezoneField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			txt.setText(Util.avoidNull(value));

		}
	}

}
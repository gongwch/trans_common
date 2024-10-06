package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 金額編集用セルエディタ
 */
public class TAmountEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 * @param table
	 */
	public TAmountEditor(TNumericField editor, TTable table) {
		super(editor, table);
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

		if (editor.isUseTableFont()) {
			editor.setFont(table.getFont());
		}

		// 対象行番号の設定
		editor.setRowIndex(row);

		if (value != null && value instanceof NumberCellValue) {
			// 値の設定
			editor.setMaxLength(((NumberCellValue) value).getMaxLength());
			editor.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
			editor.setNumber(((NumberCellValue) value).getNumber());
		}

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
	protected void setValue(TNumericField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			BigDecimal num = TTablePasteUtil.getNumber(Util.avoidNull(value));
			txt.setNumber(num);
		}
	}

	/**
	 * 小数点設定
	 * 
	 * @param txt
	 * @param value
	 * @param point
	 */
	protected void setDecimalPoint(TNumericField txt, Object value, int point) {
		txt.setDecimalPoint(point);
		setValue(txt, value);
	}
}
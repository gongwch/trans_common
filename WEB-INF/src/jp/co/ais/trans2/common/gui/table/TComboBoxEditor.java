package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.TComboBox.TTextValue;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * コンボボックス用セルエディタ
 */
public class TComboBoxEditor extends TBaseCellEditor {

	/**
	 * コンストラクタ.
	 * 
	 * @param cmbBox
	 * @param table
	 */
	public TComboBoxEditor(TComboBox cmbBox, TTable table) {
		super(cmbBox, table);
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getCellEditorValue()
	 */
	@Override
	public Object getCellEditorValue() {
		TComboBox comp = (TComboBox) editorComponent;

		Object obj = comp.getSelectedTextValue();

		if (obj != null) {
			return obj;
		}

		return super.getCellEditorValue();
	}

	/**
	 * @see javax.swing.DefaultCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean,
	 *      int, int)
	 */
	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		// コンポーネントの取得
		TComboBox cmbBox = (TComboBox) getComponent();

		// 色の設定
		cmbBox.setBackground(tbl.getBackgroundColor(row, isSelected, isSelected));
		cmbBox.setForeground(tbl.getSelectedFontColor());

		// 対象行番号の設定
		cmbBox.setRowIndex(row);

		if (value == null) {
			cmbBox.setSelectedIndex(0);

		} else if (value instanceof TTextValue) {
			cmbBox.setSelectedItemValue(((TTextValue) value).getValue());

		} else if (value instanceof String) {
			cmbBox.setSelectedText((String) value);

		} else {
			cmbBox.setSelectedItemValue(value);
		}

		return cmbBox;
	}

}
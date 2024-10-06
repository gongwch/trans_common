package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ƒ‰ƒWƒIRenderer
 */
public class RadioButtonRenderer implements TableCellRenderer {

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		TRadioButton btn = new TRadioButton();
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.setOpaque(true);

		if (isSelected) {
			btn.setBackground(TTable.selectedColor);
		} else {
			if (row % 2 == 0) {
				btn.setBackground(TTable.notSelectedColor2);
			} else {
				btn.setBackground(TTable.notSelectedColor);
			}
		}

		if (value == null || !(value instanceof Boolean)) {
			return btn;
		}
		btn.setSelected((Boolean) value);
		return btn;
	}
}
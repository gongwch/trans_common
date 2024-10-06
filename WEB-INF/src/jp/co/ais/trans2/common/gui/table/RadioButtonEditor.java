package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ラジオEditor
 */
public class RadioButtonEditor extends DefaultCellEditor implements ItemListener {

	/** ラジオ */
	public TRadioButton btn = new TRadioButton();

	/**
	 * @param btn
	 */
	public RadioButtonEditor(TRadioButton btn) {
		super(new JCheckBox());
		this.btn = btn;
		btn.setHorizontalAlignment(SwingConstants.CENTER);
		btn.addItemListener(this);
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {

		btn.setSelected(false);
		if (value == null || !(value instanceof Boolean)) {
			return btn;
		}

		if (((Boolean) value).booleanValue()) {
			btn.setSelected(true);
		}

		return btn;
	}

	@Override
	public Object getCellEditorValue() {
		if (btn.isSelected() == true) {
			return new Boolean(true);
		} else {
			return new Boolean(false);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}
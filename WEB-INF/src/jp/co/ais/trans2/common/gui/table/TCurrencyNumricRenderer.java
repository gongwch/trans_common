package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���z�ҏW�p�Z�������_��
 */
public class TCurrencyNumricRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	private final TCurrencyNumericField renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TCurrencyNumricRenderer(TCurrencyNumericField renderer, TTable table) {

		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		JLabel lbl = new JLabel();

		lbl.setOpaque(true);
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setFont(table.getFont());

		String txt = TCurrencyNumericField.getCellText(renderer, value);

		// �F�̐ݒ�
		lbl.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (!renderer.isChangeRedOfMinus()) {
			lbl.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		} else if (txt.contains("(")) {
			lbl.setForeground(Color.red);
		} else {
			lbl.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// �{�[�_�[�ݒ�
		setBorder(lbl, isSelected, hasFocus);

		lbl.setText(txt);

		return lbl;
	}

	/**
	 * �l�̐ݒ�
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TCurrencyNumericField txt, Object value) {

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
	 * �����_�ݒ�
	 * 
	 * @param txt
	 * @param value
	 * @param point
	 * @param isSelected
	 * @param hasFocus
	 */
	protected void setDecimalPoint(TCurrencyNumericField txt, Object value, int point, boolean isSelected,
		boolean hasFocus) {
		txt.setDecimalPoint(point);
		setValue(txt, value);

		if (!txt.isChangeRedOfMinus() || isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}
	}
}
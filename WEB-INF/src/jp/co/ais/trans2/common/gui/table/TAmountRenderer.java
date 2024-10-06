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
public class TAmountRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	public final TNumericField renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TAmountRenderer(TNumericField renderer, TTable table) {

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

		TNumericField txt = new TNumericField();
		txt.setTableCellEditor(true);
		txt.setChangeRedOfMinus(renderer.isChangeRedOfMinus());

		// �l�̐ݒ�
		if (value != null && value instanceof NumberCellValue) {
			// �l�̐ݒ�
			txt.setMaxLength(((NumberCellValue) value).getMaxLength(), ((NumberCellValue) value).getDecimalPoint());
			txt.setNumber(((NumberCellValue) value).getNumber());
		} else {
			// �l�̐ݒ�
			txt.setNumericFormat(renderer.getNumericFormat());
			txt.setMaxLength(renderer.getMaxLength());
			txt.setDecimalPoint(renderer.getDecimalPoint());
			setValue(txt, value);
		}

		txt.setBorder(BorderFactory.createEmptyBorder());

		if (renderer.isUseTableFont()) {
			txt.setFont(table.getFont());
		}

		// �F�̐ݒ�
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (!txt.isChangeRedOfMinus() || isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// �{�[�_�[�ݒ�
		setBorder(txt, isSelected, hasFocus);

		return txt;
	}

	/**
	 * �l�̐ݒ�
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
	 * �����_�ݒ�
	 * 
	 * @param txt
	 * @param value
	 * @param point
	 * @param isSelected
	 * @param hasFocus
	 */
	protected void setDecimalPoint(TNumericField txt, Object value, int point, boolean isSelected, boolean hasFocus) {
		txt.setDecimalPoint(point);
		setValue(txt, value);

		if (!txt.isChangeRedOfMinus() || isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}
	}
}
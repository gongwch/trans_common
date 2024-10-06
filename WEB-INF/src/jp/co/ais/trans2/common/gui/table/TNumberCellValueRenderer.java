package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * ���l�ҏW�p�Z�������_��
 */
public class TNumberCellValueRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	private final TNumericField renderer;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TNumberCellValueRenderer(TNumericField renderer, TTable table) {

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
		txt.setNumericFormat(renderer.getNumericFormat());
		txt.setChangeRedOfMinus(renderer.isChangeRedOfMinus());

		// �l�̐ݒ�
		if (value != null) {
			if (value instanceof NumberCellValue) {
				// �l�̐ݒ�
				txt.setMaxLength(((NumberCellValue) value).getMaxLength());
				txt.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
				txt.setNumber(((NumberCellValue) value).getNumber());
			} else if (value instanceof Number) {
				txt.setNumber(((Number) value));
			}
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

}
package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * �Z�������_��
 */
public class CellRenderer implements TableCellRenderer {

	/** �����_�� */
	protected TableCellRenderer cellRenderer;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cellRenderer
	 */
	public CellRenderer(TableCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}

	public Component getTableCellRendererComponent(JTable table_, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		JLabel label = getCellRendererLabel(table_, value, isSelected, hasFocus, row, column, cellRenderer);
		return label;
	}

	/**
	 * �Z�����x���̎擾
	 * 
	 * @param table_
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * @param tcr
	 * @return �Z�����x��
	 */
	protected JLabel getCellRendererLabel(JTable table_, Object value, boolean isSelected, boolean hasFocus, int row,
		int column, TableCellRenderer tcr) {

		JLabel label = (JLabel) tcr.getTableCellRendererComponent(table_, value, isSelected, hasFocus, row, column);

		label.setBackground(getBackgroundColor(row, isSelected, hasFocus));
		label.setForeground(getCellFontColor(isSelected, hasFocus));

		return label;
	}

	/**
	 * �w�i�F���擾����
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return �w�i�F
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {
		// �w�i�F�̎擾

		if (row % 2 == 0) {
			if (isSelected) {
				return TTable.selectedColor;
			}
			return TTable.notSelectedColor2;

		}

		if (isSelected) {
			return TTable.selectedColor;
		}
		if (hasFocus) {
			// �t�H�[�J�X
		}

		return TTable.notSelectedColor;
	}

	/**
	 * �t�H���g�J���[���擾����
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return �t�H���g�J���[
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {
		// �t�H���g�J���[�̎擾

		if (isSelected) {
			return TTable.selectedCellFontColor;
		}
		if (hasFocus) {
			// �t�H�[�J�X
		}
		return TTable.cellFontColor;

	}
}

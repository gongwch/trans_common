package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * �e�[�u���w�b�_�[�̃Z�������_��
 * 
 * @author AIS
 */
public class HeaderRenderer implements TableCellRenderer {

	/** �����_�� */
	protected TableCellRenderer cellRenderer;

	/** ������ */
	protected int verticalAlign = SwingConstants.CENTER;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param cellRenderer
	 */
	public HeaderRenderer(TableCellRenderer cellRenderer) {
		this.cellRenderer = cellRenderer;
	}

	public Component getTableCellRendererComponent(JTable table_, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		JLabel label = getHeaderRendererLabel(table_, value, isSelected, hasFocus, row, column, verticalAlign,
			cellRenderer);
		return label;
	}

	/**
	 * �w�b�_�[���x���̎擾
	 * 
	 * @param table_
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * @param verticalAlignment
	 * @param tcr
	 * @return �w�b�_�[���x��
	 */
	protected JLabel getHeaderRendererLabel(JTable table_, Object value, boolean isSelected, boolean hasFocus, int row,
		int column, int verticalAlignment, TableCellRenderer tcr) {

		JLabel label = (JLabel) tcr.getTableCellRendererComponent(table_, value, isSelected, hasFocus, row, column);

		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(verticalAlignment);
		label.setBackground(TTable.columnColor);
		label.setForeground(TTable.columnFontColor);
		label.setBorder(new TTableHeaderBorder());

		return label;
	}

	/**
	 * �����񂹂̎擾
	 * 
	 * @return verticalAlign ������
	 */
	public int getVerticalAlign() {
		return verticalAlign;
	}

	/**
	 * �����񂹂̐ݒ�
	 * 
	 * @param verticalAlign ������
	 */
	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

}
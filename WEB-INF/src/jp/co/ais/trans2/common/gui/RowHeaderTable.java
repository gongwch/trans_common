package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * �s�w�b�_�[�pTable
 */
public class RowHeaderTable extends JTable {

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param tbl �f�[�^�pTTable
	 * @param width �s�ԍ���
	 * @param height �e��̍���
	 */
	public RowHeaderTable(TTable tbl, int width, int height) {
		this(tbl, new RowHeaderDataModel(tbl), new RowHeaderSelectionModel(tbl), width, height);
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param tbl �f�[�^�pTTable
	 * @param dModel �s�ԍ��\���p�f�[�^���f��
	 * @param sModel �s�ԍ��\���pSelect���f��
	 * @param width �s�ԍ���
	 * @param height �e��̍���
	 */
	public RowHeaderTable(TTable tbl, RowHeaderDataModel dModel, RowHeaderSelectionModel sModel, int width, int height) {
		super(dModel, null, sModel);

		// ����������
		init(tbl, width, height);
	}

	/**
	 * ����������
	 * 
	 * @param tbl �f�[�^�pTTable
	 * @param width �s�ԍ���
	 * @param height �e��̍���
	 */
	protected void init(final TTable tbl, int width, int height) {
		super.setEnabled(false);
		setPreferredScrollableViewportSize(new Dimension(width, 0));
		setRowHeight(height);

		// �B��̗�
		TableColumn tc = new TableColumn(0, width);
		tc.setResizable(false); // �T�C�Y�ύX�֎~

		// ���������E�w�i�D�F�̃����_���[��o�^����
		DefaultTableCellRenderer r = new DefaultTableCellRenderer() {

			@Override
			public Component getTableCellRendererComponent(JTable table_, Object value, boolean isSelected,
				boolean hasFocus, int row, int column) {

				JLabel label = (JLabel) super.getTableCellRendererComponent(table_, value, isSelected, hasFocus, row,
					column);
				label.setBackground(isSelected ? TTable.selectedColor : TTable.columnColor);
				label.setForeground(isSelected ? TTable.selectedCellFontColor : TTable.columnFontColor);

				if (tbl.table.getRowHeaderBackground() != null && tbl.table.getRowHeaderBackground().size() > row) {
					Color color = tbl.table.getRowHeaderBackground().get(row);
					if (color != null) {
						if (isSelected) {
							// ��������
							color = color.brighter();
						}
						label.setBackground(color);
					}
				}
				if (tbl.table.getRowHeaderForeground() != null && tbl.table.getRowHeaderForeground().size() > row) {
					Color color = tbl.table.getRowHeaderForeground().get(row);
					if (color != null) {
						label.setForeground(color);
					}
				}

				return label;
			}
		};
		r.setHorizontalAlignment(SwingConstants.CENTER);
		r.setBackground(TTable.columnColor);
		r.setForeground(TTable.columnFontColor);
		tc.setCellRenderer(r);

		addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				if (!SwingUtilities.isLeftMouseButton(e)) {
					return;
				}

				int row = rowAtPoint(e.getPoint());
				tbl.table.setRowSelectionInterval(row, row);
				tbl.table.processMouseEvent(e);
			}
		});

		super.addColumn(tc);
	}

	/**
	 * @see javax.swing.JTable#updateUI()
	 */
	@Override
	public void updateUI() {
		int oldRowHeight = rowHeight;
		super.updateUI();
		this.setRowHeight(oldRowHeight);
	}
}

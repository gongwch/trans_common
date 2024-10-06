package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.plaf.*;

/**
 * �`��UI
 */
public class TGroupableTableHeaderUI extends BaseTableHeaderUI {

	@Override
	public void paint(Graphics g, JComponent c) {
		if (header.getColumnModel() == null) return;

		TGroupableTableHeader hdr = (TGroupableTableHeader) header;
		hdr.setColumnMargin();

		Dimension drawHeaderSize = getPreferredSize(c);
		Dimension size = hdr.getSize();
		int columnMargin = 0;
		int startY = 0;
		int tableHeight = hdr.getTable().getRowHeight();

		int groupCount = hdr.getGroupCount();

		// �����s�`��
		List<Integer> groupColumnList = new ArrayList<Integer>();

		// �S�Ă̌����s���Ƃɕ`�悷��
		for (int rowNo = 0; rowNo < groupCount; rowNo++) {
			int column = 0;

			Hashtable h = new Hashtable();
			Rectangle cellRect = new Rectangle(0, startY, Math.min(drawHeaderSize.width, size.width), size.height);
			Enumeration enumeration = hdr.getColumnModel().getColumns();
			int groupHeight = 0;

			// �S��Enum�Ŕ�����s��
			while (enumeration.hasMoreElements()) {
				cellRect.height = size.height; // ������
				TableColumn aColumn = (TableColumn) enumeration.nextElement();
				List list = hdr.getGroupColumns(rowNo, aColumn);

				// ���YEnum��͌����O���[�v�ɏ��������ꍇ�ɁA�������[�h�ŕ`�悷��
				if (list != null) {
					for (Object obj : list) {
						if (obj instanceof TGroupColumn) {

							// �L������
							groupColumnList.add(column);

							TGroupColumn col = (TGroupColumn) obj;
							Rectangle groupRect = (Rectangle) h.get(col);
							if (groupRect == null) {
								groupRect = new Rectangle(cellRect);
								Dimension d = col.getSize(hdr.getTable());
								groupRect.width = d.width;
								groupRect.height = tableHeight;
								h.put(col, groupRect);

								// �����w�b�_�`��
								paintCell(g, groupRect, col);
								groupHeight += groupRect.height;
							}
							cellRect.height = size.height - groupHeight;
						}
					}
				}

				// ���̗�̕��͕ς��B�J�nX�͕ς��B
				cellRect.width = aColumn.getWidth() + columnMargin;
				cellRect.x += cellRect.width;
				column++;
			}

			// ���̌����s�̊J�nY������
			startY += tableHeight;
		}

		{
			// �ʏ�w�b�_�`��
			int width = Math.min(drawHeaderSize.width, size.width);
			Rectangle cellRect = new Rectangle(0, 0, width, size.height);
			Enumeration enumeration = hdr.getColumnModel().getColumns();
			int column = 0;

			while (enumeration.hasMoreElements()) {
				TableColumn aColumn = (TableColumn) enumeration.nextElement();
				cellRect.width = aColumn.getWidth() + columnMargin;

				if (groupColumnList.contains(column)) {
					// �L�����������Z���̏ꍇ�AY�͈قȂ�
					cellRect.y = startY;
					cellRect.height = size.height - startY;
				} else {
					cellRect.y = 0;
					cellRect.height = size.height;
				}

				// �ʏ�w�b�_�[��`�悷��
				paintCell(g, cellRect, column++);
				cellRect.x += cellRect.width;
			}
		}

	}

	/**
	 * �ʏ�w�b�_�[��`�悷��
	 * 
	 * @param g
	 * @param cellRect
	 * @param columnIndex
	 */
	@Override
	protected void paintCell(Graphics g, Rectangle cellRect, int columnIndex) {
		TableColumn col = header.getColumnModel().getColumn(columnIndex);

		JLabel lbl = new JLabel();
		lbl.setForeground(TTable.columnFontColor);
		lbl.setBackground(TTable.columnColor);
		lbl.setFont(header.getParent().getFont());

		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setText(col.getHeaderValue().toString());
		lbl.setBorder(new TGroupableTableHeaderBorder());

		rendererPane.add(lbl);
		rendererPane.paintComponent(g, lbl, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * �����w�b�_�`��
	 * 
	 * @param g
	 * @param cellRect
	 * @param cGroup
	 */
	protected void paintCell(Graphics g, Rectangle cellRect, TGroupColumn cGroup) {

		JLabel lbl = new JLabel();
		lbl.setForeground(TTable.columnFontColor);
		lbl.setBackground(TTable.columnColor);
		lbl.setFont(header.getParent().getFont());
		lbl.getFont().isBold();

		lbl.setHorizontalAlignment(SwingConstants.CENTER);
		lbl.setText(cGroup.getHeaderValue().toString());
		lbl.setBorder(new TGroupableTableHeaderBorder());

		rendererPane.add(lbl);
		rendererPane.paintComponent(g, lbl, header, cellRect.x, cellRect.y, cellRect.width, cellRect.height, true);
	}

	/**
	 * @return ����
	 */
	protected int getHeaderHeight() {
		int height = 0;
		TableColumnModel columnModel = header.getColumnModel();
		for (int column = 0; column < columnModel.getColumnCount(); column++) {
			TableColumn aColumn = columnModel.getColumn(column);
			TableCellRenderer renderer = aColumn.getHeaderRenderer();
			if (renderer == null) {
				return 60;
			}

			TGroupableTableHeader hdr = ((TGroupableTableHeader) header);
			int groupCount = hdr.getGroupCount();

			// �����w�b�_�̍����͐��{�P�Ōv�Z����
			int cHeight = hdr.getTable().getRowHeight() * (groupCount + 1);

			height = Math.max(height, cHeight);
		}
		return height;
	}

	/**
	 * @param width
	 * @return �����񕝍��v
	 */
	protected Dimension createHeaderSize(long width) {
		TableColumnModel columnModel = header.getColumnModel();
		width += columnModel.getColumnMargin() * columnModel.getColumnCount();
		if (width > Integer.MAX_VALUE) {
			width = Integer.MAX_VALUE;
		}
		return new Dimension((int) width, getHeaderHeight());
	}

	@Override
	public Dimension getPreferredSize(JComponent c) {
		long width = 0;
		Enumeration enumeration = header.getColumnModel().getColumns();
		while (enumeration.hasMoreElements()) {
			TableColumn aColumn = (TableColumn) enumeration.nextElement();
			width = width + aColumn.getPreferredWidth();
		}
		return createHeaderSize(width);
	}
}
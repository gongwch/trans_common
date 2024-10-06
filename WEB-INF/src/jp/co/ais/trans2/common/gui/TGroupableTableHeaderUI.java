package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.plaf.*;

/**
 * 描画UI
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

		// 結合行描画
		List<Integer> groupColumnList = new ArrayList<Integer>();

		// 全ての結合行ごとに描画する
		for (int rowNo = 0; rowNo < groupCount; rowNo++) {
			int column = 0;

			Hashtable h = new Hashtable();
			Rectangle cellRect = new Rectangle(0, startY, Math.min(drawHeaderSize.width, size.width), size.height);
			Enumeration enumeration = hdr.getColumnModel().getColumns();
			int groupHeight = 0;

			// 全列Enumで判定を行う
			while (enumeration.hasMoreElements()) {
				cellRect.height = size.height; // 初期化
				TableColumn aColumn = (TableColumn) enumeration.nextElement();
				List list = hdr.getGroupColumns(rowNo, aColumn);

				// 当該Enum列は結合グループに所属した場合に、結合モードで描画する
				if (list != null) {
					for (Object obj : list) {
						if (obj instanceof TGroupColumn) {

							// 記憶する
							groupColumnList.add(column);

							TGroupColumn col = (TGroupColumn) obj;
							Rectangle groupRect = (Rectangle) h.get(col);
							if (groupRect == null) {
								groupRect = new Rectangle(cellRect);
								Dimension d = col.getSize(hdr.getTable());
								groupRect.width = d.width;
								groupRect.height = tableHeight;
								h.put(col, groupRect);

								// 結合ヘッダ描画
								paintCell(g, groupRect, col);
								groupHeight += groupRect.height;
							}
							cellRect.height = size.height - groupHeight;
						}
					}
				}

				// 次の列の幅は変わる。開始Xは変わる。
				cellRect.width = aColumn.getWidth() + columnMargin;
				cellRect.x += cellRect.width;
				column++;
			}

			// 次の結合行の開始Yを持つ
			startY += tableHeight;
		}

		{
			// 通常ヘッダ描画
			int width = Math.min(drawHeaderSize.width, size.width);
			Rectangle cellRect = new Rectangle(0, 0, width, size.height);
			Enumeration enumeration = hdr.getColumnModel().getColumns();
			int column = 0;

			while (enumeration.hasMoreElements()) {
				TableColumn aColumn = (TableColumn) enumeration.nextElement();
				cellRect.width = aColumn.getWidth() + columnMargin;

				if (groupColumnList.contains(column)) {
					// 記憶した結合セルの場合、Yは異なる
					cellRect.y = startY;
					cellRect.height = size.height - startY;
				} else {
					cellRect.y = 0;
					cellRect.height = size.height;
				}

				// 通常ヘッダーを描画する
				paintCell(g, cellRect, column++);
				cellRect.x += cellRect.width;
			}
		}

	}

	/**
	 * 通常ヘッダーを描画する
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
	 * 結合ヘッダ描画
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
	 * @return 高さ
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

			// 結合ヘッダの高さは数＋１で計算する
			int cHeight = hdr.getTable().getRowHeight() * (groupCount + 1);

			height = Math.max(height, cHeight);
		}
		return height;
	}

	/**
	 * @param width
	 * @return 結合列幅合計
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
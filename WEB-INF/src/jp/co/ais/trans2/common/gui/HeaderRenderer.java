package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * テーブルヘッダーのセルレンダラ
 * 
 * @author AIS
 */
public class HeaderRenderer implements TableCellRenderer {

	/** レンダラ */
	protected TableCellRenderer cellRenderer;

	/** 垂直寄せ */
	protected int verticalAlign = SwingConstants.CENTER;

	/**
	 * コンストラクタ.
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
	 * ヘッダーラベルの取得
	 * 
	 * @param table_
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * @param verticalAlignment
	 * @param tcr
	 * @return ヘッダーラベル
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
	 * 垂直寄せの取得
	 * 
	 * @return verticalAlign 垂直寄せ
	 */
	public int getVerticalAlign() {
		return verticalAlign;
	}

	/**
	 * 垂直寄せの設定
	 * 
	 * @param verticalAlign 垂直寄せ
	 */
	public void setVerticalAlign(int verticalAlign) {
		this.verticalAlign = verticalAlign;
	}

}
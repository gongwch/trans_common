package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * セルレンダラ
 */
public class CellRenderer implements TableCellRenderer {

	/** レンダラ */
	protected TableCellRenderer cellRenderer;

	/**
	 * コンストラクタ.
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
	 * セルラベルの取得
	 * 
	 * @param table_
	 * @param value
	 * @param isSelected
	 * @param hasFocus
	 * @param row
	 * @param column
	 * @param tcr
	 * @return セルラベル
	 */
	protected JLabel getCellRendererLabel(JTable table_, Object value, boolean isSelected, boolean hasFocus, int row,
		int column, TableCellRenderer tcr) {

		JLabel label = (JLabel) tcr.getTableCellRendererComponent(table_, value, isSelected, hasFocus, row, column);

		label.setBackground(getBackgroundColor(row, isSelected, hasFocus));
		label.setForeground(getCellFontColor(isSelected, hasFocus));

		return label;
	}

	/**
	 * 背景色を取得する
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return 背景色
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {
		// 背景色の取得

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
			// フォーカス
		}

		return TTable.notSelectedColor;
	}

	/**
	 * フォントカラーを取得する
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return フォントカラー
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {
		// フォントカラーの取得

		if (isSelected) {
			return TTable.selectedCellFontColor;
		}
		if (hasFocus) {
			// フォーカス
		}
		return TTable.cellFontColor;

	}
}

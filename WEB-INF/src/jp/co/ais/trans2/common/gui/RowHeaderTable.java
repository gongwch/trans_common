package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.table.*;

/**
 * 行ヘッダー用Table
 */
public class RowHeaderTable extends JTable {

	/**
	 * コンストラクター
	 * 
	 * @param tbl データ用TTable
	 * @param width 行番号幅
	 * @param height 各列の高さ
	 */
	public RowHeaderTable(TTable tbl, int width, int height) {
		this(tbl, new RowHeaderDataModel(tbl), new RowHeaderSelectionModel(tbl), width, height);
	}

	/**
	 * コンストラクター
	 * 
	 * @param tbl データ用TTable
	 * @param dModel 行番号表示用データモデル
	 * @param sModel 行番号表示用Selectモデル
	 * @param width 行番号幅
	 * @param height 各列の高さ
	 */
	public RowHeaderTable(TTable tbl, RowHeaderDataModel dModel, RowHeaderSelectionModel sModel, int width, int height) {
		super(dModel, null, sModel);

		// 初期化処理
		init(tbl, width, height);
	}

	/**
	 * 初期化処理
	 * 
	 * @param tbl データ用TTable
	 * @param width 行番号幅
	 * @param height 各列の高さ
	 */
	protected void init(final TTable tbl, int width, int height) {
		super.setEnabled(false);
		setPreferredScrollableViewportSize(new Dimension(width, 0));
		setRowHeight(height);

		// 唯一の列
		TableColumn tc = new TableColumn(0, width);
		tc.setResizable(false); // サイズ変更禁止

		// 中央揃え・背景灰色のレンダラーを登録する
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
							// 薄くする
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

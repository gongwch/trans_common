package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * テキスト編集用セルレンダラ
 */
public class TTextAreaRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	private final TTextArea renderer;

	/** スプレッド */
	private final TTable spread;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTextAreaRenderer(TTextArea renderer, TTable table) {
		super(table);

		// レンダラ用コンポーネントの設定
		this.renderer = renderer;
		this.spread = table;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		// 値の設定
		renderer.setText(Util.avoidNullNT(value));

		// 色の設定
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// ボーダー設定
		setBorder(renderer, isSelected, hasFocus);

		if (renderer.isAllowAutoAdjustTableHeight()) {

			// 改行後サイズを取得するためにBounds再度設定する
			Rectangle rect = table.getCellRect(row, column, false);
			renderer.setBounds(rect);

			int height = Math.max(spread.getRowHeight(), renderer.getPreferredSize().height);
			adjustRowHeight(row, height);
		}

		return renderer;
	}

	/**
	 * 自動調整
	 * 
	 * @param row
	 * @param height
	 */
	public void adjustRowHeight(int row, int height) {

		if (spread.table.getRowHeight(row) != height) {
			spread.table.setRowHeight(row, height);
		}

		if (spread.getRowHeaderView().getRowHeight(row) != height) {
			spread.getRowHeaderView().setRowHeight(row, height);
		}
	}

}
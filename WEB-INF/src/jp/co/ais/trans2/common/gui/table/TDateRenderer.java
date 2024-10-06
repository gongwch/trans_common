package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 日付編集用セルレンダラ
 */
public class TDateRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	private final TCalendar renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TDateRenderer(TCalendar renderer, TTable table) {
		super(table);

		// レンダラ用コンポーネントのインスタンス化
		this.renderer = renderer;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		// 値の設定
		if (value instanceof Date) {
			renderer.setValue((Date) value);

		} else {
			renderer.setValue(value);
		}

		// 色の設定
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// ボーダー設定
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

}
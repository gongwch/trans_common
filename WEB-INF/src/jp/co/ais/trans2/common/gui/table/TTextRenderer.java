package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * テキスト編集用セルレンダラ
 */
public class TTextRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	protected final TTextField renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTextRenderer(TTextField renderer, TTable table) {
		super(table);

		// レンダラ用コンポーネントの設定
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
		renderer.setValue(value);

		// 色の設定
		this.renderer.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));
		this.renderer.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

		// ボーダー設定
		setBorder(renderer, isSelected, hasFocus);

		return renderer;
	}

}
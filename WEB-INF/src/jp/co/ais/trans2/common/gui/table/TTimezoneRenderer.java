package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * TZ編集用セルレンダラ
 */
public class TTimezoneRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	private final TTimezoneField renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TTimezoneRenderer(TTimezoneField renderer, TTable table) {

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

		TTimezoneField txt = new TTimezoneField();
		txt.setTableCellEditor(true);
		txt.setMinus15Only(renderer.isMinus15Only());

		txt.setBorder(BorderFactory.createEmptyBorder());

		// if (renderer.isUseTableFont()) {
		// txt.setFont(table.getFont());
		// }

		// 値の設定
		setValue(txt, value);

		// 色の設定
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// ボーダー設定
		setBorder(txt, isSelected, hasFocus);

		return txt;
	}

	/**
	 * 値の設定
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TTimezoneField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			txt.setText(Util.avoidNull(value));

		}
	}
}
package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.math.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 金額編集用セルレンダラ
 */
public class TCurrencyNumricRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	private final TCurrencyNumericField renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TCurrencyNumricRenderer(TCurrencyNumericField renderer, TTable table) {

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

		JLabel lbl = new JLabel();

		lbl.setOpaque(true);
		lbl.setHorizontalAlignment(SwingConstants.RIGHT);
		lbl.setFont(table.getFont());

		String txt = TCurrencyNumericField.getCellText(renderer, value);

		// 色の設定
		lbl.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (!renderer.isChangeRedOfMinus()) {
			lbl.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		} else if (txt.contains("(")) {
			lbl.setForeground(Color.red);
		} else {
			lbl.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}

		// ボーダー設定
		setBorder(lbl, isSelected, hasFocus);

		lbl.setText(txt);

		return lbl;
	}

	/**
	 * 値の設定
	 * 
	 * @param txt
	 * @param value
	 */
	protected void setValue(TCurrencyNumericField txt, Object value) {

		if (Util.isNullOrEmpty(value)) {
			txt.clear();

		} else if (value instanceof Number) {
			txt.setNumber((Number) value);

		} else {
			BigDecimal num = TTablePasteUtil.getNumber(Util.avoidNull(value));
			txt.setNumber(num);
		}
	}

	/**
	 * 小数点設定
	 * 
	 * @param txt
	 * @param value
	 * @param point
	 * @param isSelected
	 * @param hasFocus
	 */
	protected void setDecimalPoint(TCurrencyNumericField txt, Object value, int point, boolean isSelected,
		boolean hasFocus) {
		txt.setDecimalPoint(point);
		setValue(txt, value);

		if (!txt.isChangeRedOfMinus() || isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}
	}
}
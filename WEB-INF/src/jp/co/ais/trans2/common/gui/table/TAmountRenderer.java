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
public class TAmountRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	public final TNumericField renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TAmountRenderer(TNumericField renderer, TTable table) {

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

		TNumericField txt = new TNumericField();
		txt.setTableCellEditor(true);
		txt.setChangeRedOfMinus(renderer.isChangeRedOfMinus());

		// 値の設定
		if (value != null && value instanceof NumberCellValue) {
			// 値の設定
			txt.setMaxLength(((NumberCellValue) value).getMaxLength(), ((NumberCellValue) value).getDecimalPoint());
			txt.setNumber(((NumberCellValue) value).getNumber());
		} else {
			// 値の設定
			txt.setNumericFormat(renderer.getNumericFormat());
			txt.setMaxLength(renderer.getMaxLength());
			txt.setDecimalPoint(renderer.getDecimalPoint());
			setValue(txt, value);
		}

		txt.setBorder(BorderFactory.createEmptyBorder());

		if (renderer.isUseTableFont()) {
			txt.setFont(table.getFont());
		}

		// 色の設定
		txt.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus));

		if (!txt.isChangeRedOfMinus() || isSelected) {
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
	protected void setValue(TNumericField txt, Object value) {

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
	protected void setDecimalPoint(TNumericField txt, Object value, int point, boolean isSelected, boolean hasFocus) {
		txt.setDecimalPoint(point);
		setValue(txt, value);

		if (!txt.isChangeRedOfMinus() || isSelected) {
			txt.setForeground(tbl.getCellFontColor(isSelected, hasFocus));
		}
	}
}
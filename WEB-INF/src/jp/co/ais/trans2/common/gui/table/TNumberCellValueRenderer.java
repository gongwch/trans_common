package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;

/**
 * 数値編集用セルレンダラ
 */
public class TNumberCellValueRenderer extends TBaseCellRenderer {

	/** レンダラ用コンポーネント */
	private final TNumericField renderer;

	/**
	 * コンストラクタ
	 * 
	 * @param renderer
	 * @param table
	 */
	public TNumberCellValueRenderer(TNumericField renderer, TTable table) {

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
		txt.setNumericFormat(renderer.getNumericFormat());
		txt.setChangeRedOfMinus(renderer.isChangeRedOfMinus());

		// 値の設定
		if (value != null) {
			if (value instanceof NumberCellValue) {
				// 値の設定
				txt.setMaxLength(((NumberCellValue) value).getMaxLength());
				txt.setDecimalPoint(((NumberCellValue) value).getDecimalPoint());
				txt.setNumber(((NumberCellValue) value).getNumber());
			} else if (value instanceof Number) {
				txt.setNumber(((Number) value));
			}
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

}